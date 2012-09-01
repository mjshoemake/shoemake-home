package mjs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import mjs.exceptions.CoreException;

/**
 * This is a utility class which contains bean manipulation utility functions.
 */
public class FileUtils {

    /**
     * The log4j logger.
     */
    private static final Logger log = Logger.getLogger("Core");
    
    public static void sortFile(String inputFile, 
                                String outputFile, 
                                long bufferSize, 
                                boolean dedupe, 
                                boolean overwriteFiles) throws Exception {

        FileReader reader = null;

        try {
            
            int i = 0;
            int fileCount = 0;
            inputFile = FileUtils.forceAbsoluteFilePath(inputFile);
            outputFile = FileUtils.forceAbsoluteFilePath(outputFile);
            
            reader = new FileReader(inputFile);
            ArrayList<String> buffer = new ArrayList<String>();
            String baseFileName = FileUtils.extractBaseFileName(outputFile);
            long bytes = 0;
            
            log.info("Start");
            while (! reader.eof()) {
                String nextLine = reader.readLine();
                bytes += nextLine.length();
                i++;
                buffer.add(nextLine);
                
                if (bytes >= bufferSize) {
                    // Reached buffer size.  Sort what's in the buffer.
                    fileCount++;
                    writeBufferToFile(buffer, baseFileName, fileCount);
                    i = 0;
                    bytes = 0;
                }
            }

            if (i > 0) {
                fileCount++;
                writeBufferToFile(buffer, baseFileName, fileCount);
                i = 0;
                bytes = 0;
            }
            
            ArrayList<String> sortedFiles = new ArrayList<String>();
            for (int j=1; j <= fileCount; j++)
                sortedFiles.add(baseFileName + ".s" + j);    
            mergeSortedFiles(sortedFiles, outputFile, true, dedupe, overwriteFiles);
            
            log.info("End");
        } finally {
            
            reader.close();
        }
    }

    /**
     * Merge (and possibly dedupe) the specified sorted input files into
     * one sorted output file.
     *  
     * @param inputFiles ArrayList<String>
     * @param outputFile String
     */
    public static void mergeSortedFiles(ArrayList<String> inputFiles,
                                        String outputFile,
                                        boolean deleteInputFiles,
                                        boolean dedupe,
                                        boolean overwriteFiles) throws Exception {

        FileWriter out = null;
        ArrayList<FileReader> readers = new ArrayList<FileReader>();
        
        try {
            
            String firstString = null;

            String[] lastread = new String[inputFiles.size()];
            boolean[] eof = new boolean[inputFiles.size()];
            String[] updatedFileNames = new String[inputFiles.size()];
            
            out = new FileWriter(outputFile, overwriteFiles);
            long lineCount = 0;

            // Create FileReader objects for each input file.
            for (int i=0; i <= inputFiles.size()-1; i++) {

                // If input file does not include path then prepend
                // current working directory.
                updatedFileNames[i] = forceAbsoluteFilePath(inputFiles.get(i));
                
                readers.add(new FileReader(updatedFileNames[i]));
                // Read first line for each file.
                if (! readers.get(i).eof()) {
                    lastread[i] = readers.get(i).readLine();
                    lineCount++;
                    eof[i] = false;
                } else
                    eof[i] = true;
            }
            
            while (! doneProcessing(eof)) {
                
                // Find the first string (lowest string in sort order).
                firstString = null;
                for (int i=0; i <= inputFiles.size()-1; i++) {
                    if (! eof[i]) {
                        if (firstString == null) {
                            firstString = lastread[i];
                        } else {
                            int comp = lastread[i].compareTo(firstString);
                            // Replace if this string precedes firstString 
                            // alphabetically.
                            if (comp < 0) {
                                firstString = lastread[i];
                            }    
                        }    
                    }
                }
                
                if (dedupe) {
                    // Dedupe the list.
                    int occurrences = 0;
                    for (int i=0; i <= inputFiles.size()-1; i++) {
                        while ((! eof[i]) && lastread[i].equals(firstString)) {
                            if (! readers.get(i).eof()) {
                                lastread[i] = readers.get(i).readLine();
                                lineCount++;
                                if (lineCount % 500000 == 0)
                                    log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                                eof[i] = false;
                            } else {
                                eof[i] = true;
                                log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                                readers.get(i).close();
                            }    
                            occurrences++;
                        }    
                    }
                    out.println(firstString);
                } else {
                    int occurrences = 0;
                    for (int i=0; i <= inputFiles.size()-1; i++) {
                        while ((! eof[i]) && lastread[i].equals(firstString)) {
                            if (! readers.get(i).eof()) {
                                lastread[i] = readers.get(i).readLine();
                                lineCount++;
                                if (lineCount % 500000 == 0)
                                    log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                                eof[i] = false;
                            } else {
                                eof[i] = true;
                                log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                                readers.get(i).close();
                            }    
                            out.println(firstString);
                            occurrences++;
                        }    
                    }
                }
            }
            
            log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));

            // Delete input files.
            if (deleteInputFiles) {
                
                for (int i=0; i <= inputFiles.size()-1; i++)
                    deleteFile(updatedFileNames[i]);
            }
            
        } finally {
            for (int i=0; i <= readers.size()-1; i++)
                readers.get(i).close();
                
            out.close();
        }
    }

    /**
     * Compare the specified sorted input files to determine 
     * differences.  This method assumes that the two files have been 
     * sorted alphabetically before the comparison.  Sorted files can 
     * be processed without size restrictions because the files will not
     * need to be read into memory prior to the compare.  Unsorted files
     * must be loaded into memory.
     *  
     * @param inputFile1 String
     * @param inputFile2 String
     * @param outputFile String
     * @param ignoreWhiteSpace boolean
     * @param onlyWriteDifferences boolean
     * @param overwriteFiles boolean
     */
    public static void compareSortedFiles(String inputFile1,
                                          String inputFile2,
                                          String outputFile,
                                          boolean ignoreWhiteSpace,
                                          boolean onlyWriteDifferences,
                                          boolean overwriteFiles) throws Exception {
        compareSortedFiles(inputFile1,
                           inputFile2,
                           outputFile,
                           ignoreWhiteSpace,
                           onlyWriteDifferences,
                           overwriteFiles,
                           null,
                           null,
                           null);
    }
        
    
    
    
    /**
     * Compare the specified sorted input files to determine 
     * differences.  This method assumes that the two files have been 
     * sorted alphabetically before the comparison.  Sorted files can 
     * be processed without size restrictions because the files will not
     * need to be read into memory prior to the compare.  Unsorted files
     * must be loaded into memory.
     *  
     * @param inputFile1 String
     * @param inputFile2 String
     * @param outputFile String
     * @param ignoreWhiteSpace boolean
     * @param onlyWriteDifferences boolean
     * @param overwriteFiles boolean
     * @param file1Tag String
     * @param file2Tag String
     * @param bothTag String
     */
    public static void compareSortedFiles(String inputFile1,
                                          String inputFile2,
                                          String outputFile,
                                          boolean ignoreWhiteSpace,
                                          boolean onlyWriteDifferences,
                                          boolean overwriteFiles,
                                          String file1Tag,
                                          String file2Tag,
                                          String bothTag) throws Exception {
        
        ArrayList<FileReader> readers = new ArrayList<FileReader>();
        // Same output file        
        FileWriter same = null;
        // Diff output file        
        FileWriter diff = null;
        
        try {
            
            ArrayList<String> inputFiles = new ArrayList<String>();
            inputFiles.add(inputFile1);
            inputFiles.add(inputFile2);
            String[] lastread = new String[inputFiles.size()];
            boolean[] eof = new boolean[inputFiles.size()];
            String[] updatedFileNames = new String[inputFiles.size()];
            int[] fileLineCount = new int[inputFiles.size()];
            
            outputFile = FileUtils.extractBaseFileName(FileUtils.forceAbsoluteFilePath(outputFile));
            long lineCount = 0;
            
            // Same output file        
            same = new FileWriter(outputFile+".same", overwriteFiles);
            // Diff output file        
            diff = new FileWriter(outputFile+".diff", overwriteFiles);
            
            // Defect 2185 fix
            for (int i=0; i <= inputFiles.size()-1; i++) {
                lastread[i]="";            
            }
            // Create FileReader objects for each input file.
            for (int i=0; i <= inputFiles.size()-1; i++) {

                // If input file does not include path then prepend
                // current working directory.
                updatedFileNames[i] = forceAbsoluteFilePath(inputFiles.get(i));
                fileLineCount[i] = 0;
                
                readers.add(new FileReader(updatedFileNames[i]));
                // Read first line for each file.
                if (! readers.get(i).eof()) {
                    lastread[i] = readers.get(i).readLine();
                    fileLineCount[i]++;
                    log.debug("First line read (file #" + (i+1) + ": " + lastread[i]);               
                    lineCount++;
                    eof[i] = false;
                } else {
                    eof[i] = true;
                    log.debug("EOF (file #" + (i+1) + ").");
                }    
            }
            
            while (! doneProcessing(eof)) {
                // Find the first string (lowest string in sort order).
                int comp = 0;
                if (! ignoreWhiteSpace)
                    comp = lastread[0].compareTo(lastread[1]);
                else
                    comp = lastread[0].trim().compareTo(lastread[1].trim());
                    
                log.debug("file1=" + lastread[0] + "  file2=" + lastread[1] + "| (comp = " + comp + ").");
                    
                while (comp == 0 && eof[0] == false && eof[1] == false) {
                    // Same.  No difference.  Discard both lines and read in 
                    // next two.
                    log.debug("SAME: " + lastread[0]); 
                    same.println(formatDiffFile(0, fileLineCount[0], fileLineCount[1], lastread[0], file1Tag, file2Tag, bothTag));
                    if (! onlyWriteDifferences)
                        diff.println(formatDiffFile(0, fileLineCount[0], fileLineCount[1], lastread[0], file1Tag, file2Tag, bothTag));
                    //same.println(formatDiffFile(2, fileLineCount[0], fileLineCount[1], lastread[0]));
                    for (int i=0; i <= 1; i++) {
                        if (! readers.get(i).eof()) {
                            lastread[i] = readers.get(i).readLine();
                            fileLineCount[i]++;
                            lineCount++;
                            if (lineCount % 500000 == 0)
                                log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                            eof[i] = false;
                        } else {
                            eof[i] = true;
                            log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                            readers.get(i).close();
                        }    
                    }

                    // Find the first string (lowest string in sort order).
                    if (eof[0] == false && eof[1] == false)
                        comp = lastread[0].compareTo(lastread[1]);
                }

                if (eof[0] == false || eof[1] == false) {
                    // Different.
                    int i = -1;
                    
                    if (eof[0]== true){
                        i = 1;
                        diff.println(formatDiffFile(2, fileLineCount[0], fileLineCount[1], lastread[1], file1Tag, file2Tag, bothTag));
                    }
                    if (eof[1]== true){
                        i = 0;
                        diff.println(formatDiffFile(1, fileLineCount[0], fileLineCount[1], lastread[0], file1Tag, file2Tag, bothTag));
                    } 
                    
                    if (comp < 0 && eof[0]== false && eof[1]== false) {
                        i = 0;
                        diff.println(formatDiffFile(1, fileLineCount[0], fileLineCount[1], lastread[0], file1Tag, file2Tag, bothTag));
                    } else 
                    if (comp > 0 && eof[0]== false && eof[1]== false){ 
                        i = 1;
                        diff.println(formatDiffFile(2, fileLineCount[0], fileLineCount[1], lastread[1], file1Tag, file2Tag, bothTag));
                    }
                        
                    if (! readers.get(i).eof()) {
                        lastread[i] = readers.get(i).readLine();
                        fileLineCount[i]++;
                        lineCount++;
                        if (lineCount % 500000 == 0)
                            log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                        eof[i] = false;
                    } else {
                        eof[i] = true;
                        log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
                        readers.get(i).close();
                    }    
                }
                
            }
            
            log.debug("Lines processed: " + lineCount + "  " + checkDoneProcessing(eof));
        } finally {
            
            for (int i=0; i <= readers.size()-1; i++)
                readers.get(i).close();
            same.close();
            diff.close();
        }
    }
    /**
     * Compare the specified unsorted input files to determine the differences.  
     *  
     * @param unsortInputFile1 String
     * @param unsortInputFile2 String
     * @param outputFile String
     * @param ignoreWhiteSpace boolean
     * @param onlyWriteDifferences boolean
     * @param overwriteFiles boolean
     */
    public static void compareUnsortedFiles(String unsortInputFile1,
                                    String unsortInputFile2,
                                    String outputFile,
                                    boolean ignoreWhiteSpace,
                                    boolean onlyWriteDifferences,
                                    boolean overwriteFiles) throws Exception {

        FileReader reader1 = null;
        FileReader reader2 = null;
        FileReader reader3 = null;
        FileReader reader4 = null;
        
        FileWriter diff = null;
        FileWriter same = null;        
        
        String inputFile1=unsortInputFile1+".srt1";
        String inputFile2=unsortInputFile2+".srt2";        
        
        try {
            
            //sort the input files
            sortFile(unsortInputFile1, inputFile1, 14000000, false, overwriteFiles);
            sortFile(unsortInputFile2, inputFile2, 14000000, false, overwriteFiles);
            
            inputFile1 = forceAbsoluteFilePath(inputFile1);
            inputFile2 = forceAbsoluteFilePath(inputFile2);
            
            reader1 = new FileReader(inputFile1);
            reader2 = new FileReader(inputFile2);            
            reader3 = new FileReader(unsortInputFile1);
            reader4 = new FileReader(unsortInputFile2);
            
            ArrayList <String> file1 = new ArrayList<String>();
            ArrayList <String> file2 = new ArrayList<String>();            
            ArrayList <String> file3 = new ArrayList<String>();
            ArrayList <String> file4 = new ArrayList<String>();
            
            outputFile = FileUtils.extractBaseFileName(FileUtils.forceAbsoluteFilePath(outputFile));
            
            int file1Line = 0;
            int file2Line = 0;
            
            // Diff output file        
            diff = new FileWriter(outputFile+".diff", overwriteFiles);
            // Same output file        
            same = new FileWriter(outputFile+".same", overwriteFiles);

            // Read each input file into an array of String objects.
            while (! reader1.eof())
                file1.add(reader1.readLine());
            while (! reader2.eof())
                file2.add(reader2.readLine());
            while (! reader3.eof())
                file3.add(reader3.readLine());
            while (! reader4.eof())
                file4.add(reader4.readLine());
            
            log.debug("File1: " + file1.size() + " lines read.");
            log.debug("File2: " + file2.size() + " lines read.");    
            
            if (ignoreWhiteSpace)
                log.info("Note - IgnoreWhiteSpace is not supported");
            
            // Lines left to compare from both files.  Continue...
            String origLine1="";
            if (file1.size()!=0){
                origLine1= file1.get(0);
                file1.remove(0);
                file1Line++;
            }
            String origLine2="";
            if (file2.size()!=0)
            {
                origLine2 = file2.get(0);
                file2.remove(0);
                file2Line++;
            }
            String line1 = origLine1;
            String line2 = origLine2;

            boolean neofFlag=true;
            while (file1.size() >= 0 && file2.size() >= 0 && neofFlag) {                
                log.debug("Next (file1) [" + file1Line + "]: " + line1 + "  Sizes: F1=" + file1.size() + "  F2=" + file2.size());                  
                    
                if (line1.equals(line2)) {                   
                   if (origLine1!=null && origLine1.length()!=0) {
                       log.debug("SAME: " + origLine1); 
                       same.println(origLine1);                   
                       if (! onlyWriteDifferences)
                           diff.println(formatDiffFile(0, findPositionInList(origLine1, file3)+1, findPositionInList(origLine1, file4)+1, origLine1));
                   }                   
                   // Lines left to compare from both files.  Continue...
                   if (file1.size()!=0)
                   {
                       origLine1 = file1.get(0);
                       file1.remove(0);
                       file1Line++;
                       line1 = origLine1;                       
                       }
                   else{
                       neofFlag=false;                       
                       line1 = null;
                   }                    
                   if (file2.size()!=0)
                   {
                       origLine2 = file2.get(0);
                       file2.remove(0);
                       file2Line++;                       
                       line2 = origLine2;
                   }
                   else{
                       neofFlag=false;                       
                       line2 = null;
                   }
                } 
                else {
                    // Lines are different.  Handle differences.
                    int line1Pos = 0;
                    int line2Pos = 0;                    
                    int line1PosFoundAt = findPositionInList(line1, file2);
                    int line2PosFoundAt = findPositionInList(line2, file1);
                    int smallestFile = Math.min(file1.size(), file2.size());
                    int lowestPos = Math.min(line1PosFoundAt, line2PosFoundAt);
                    lowestPos = Math.min(lowestPos, smallestFile);
                    int i=0;
                    if (lowestPos == Integer.MAX_VALUE)
                        lowestPos = smallestFile;
    
                    String testline1 = line1;
                    String testline2 = line2;
                    log.debug("DIFFERENT [" + file1Line + "]  FILE1: " + testline1); 
                    log.debug("          [" + file2Line + "]  FILE2: " + testline2); 
                    log.debug("          INITIAL: line1Pos=" + line1Pos + " line2Pos=" + line2Pos + " line1PosFoundAt=" + line1PosFoundAt + " line2PosFoundAt=" + line2PosFoundAt + " lowestPos=" + lowestPos); 
    
                    if (line1PosFoundAt == Integer.MAX_VALUE && line2PosFoundAt == Integer.MAX_VALUE) {
                        log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                        log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                        
                        if (origLine1.length()!=0)
                            diff.println(formatDiffFile(1, findPositionInList(origLine1, file3)+1, findPositionInList(line2, file4), origLine1));                       
                        
                        if (origLine2.length()!=0)
                            diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(origLine2, file4)+1, origLine2));                    
                        
                        // Get next lines.
                        if (file1.size()!=0)
                        {
                            origLine1 = file1.get(0);
                            file1.remove(0);
                            file1Line++;
                            line1 = origLine1;                            
                        }
                        else{
                            neofFlag=false;
                            line1 = null;                                
                        }
                        
                        if (file2.size()!=0)
                        {
                            origLine2 = file2.get(0);
                            file2.remove(0);
                            file2Line++;                            
                            line2 = origLine2;
                        }
                        else{
                            neofFlag=false;                                
                            line2 = null;
                        }                       
                    } else {
                        while (i < lowestPos) {
                            int nextLine1PosFoundAt = findPositionInList(testline1, file2);
                            if (nextLine1PosFoundAt < line1PosFoundAt) {
                                line1Pos = i;
                                line1PosFoundAt = nextLine1PosFoundAt;
                            }
                            
                            int nextLine2PosFoundAt = findPositionInList(testline2, file1);
                            if (nextLine2PosFoundAt < line2PosFoundAt) {
                                line2Pos = i;
                                line2PosFoundAt = nextLine2PosFoundAt;
                            }    
                            int min = Math.min(line1PosFoundAt, line2PosFoundAt);
                            if (min < lowestPos)
                                lowestPos = min;
                            
                            testline1 = file1.get(i);
                            testline2 = file2.get(i);
                            i++;
                        }
                        
                        log.debug("          line1Pos=" + line1Pos + " line2Pos=" + line2Pos + " line1PosFoundAt=" + line1PosFoundAt + " line2PosFoundAt=" + line2PosFoundAt + " lowestPos=" + lowestPos); 
    
                        if (line1PosFoundAt == Integer.MAX_VALUE &&
                            line2PosFoundAt == Integer.MAX_VALUE) {
                            // No similarities between files.
                            log.debug("NO SIMILARITIES.");
                            diff.println(formatDiffFile(1, findPositionInList(line1, file3)+1, findPositionInList(origLine1, file4), origLine1));                            
                            for (int j=0; j <= file1.size()-1; j++)
                            {
                                diff.println(formatDiffFile(1, findPositionInList(file1.get(j), file3)+1, findPositionInList(line2, file4), file1.get(j)));                                
                            }
                            
                            file1.clear();
                            diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(origLine2, file4)+1, origLine2));                            
                            for (int j=0; j <= file2.size()-1; j++)
                            {
                                diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(file2.get(j), file4)+1, file2.get(j)));                                
                            }                            
                            file2.clear();
                        } else if (line1PosFoundAt <= line2PosFoundAt) {
                            int k=0;
                            log.debug("Writing differences...");
                            log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                            diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(origLine2, file4)+1, origLine2));                           
                            while (k < line1PosFoundAt) {
                                file2Line++;
                                log.debug("   File2 - Line " + file2Line + ": " + file2.get(k));
                                diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(file2.get(k), file4)+1, file2.get(k)));                                
                                k++;
                            }
                            for (k=line1PosFoundAt; k >= 0; k--) {
                                origLine2 = file2.get(0);
                                file2.remove(0);                          
                            }    
                            file2Line++;
                            line2 = origLine2;
                        } else if (line2PosFoundAt < line1PosFoundAt) {
                            int k=0;
                            log.debug("Writing differences...");
                            log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                            diff.println(formatDiffFile(1, findPositionInList(origLine1, file3)+1, findPositionInList(line2, file4), origLine1));                          
                            while (k < line2PosFoundAt) {
                                file1Line++;
                                log.debug("   File1 - Line " + file1Line + ": " + file1.get(k));
                                diff.println(formatDiffFile(1, findPositionInList(file1.get(k), file3)+1, findPositionInList(line2, file4), file1.get(k)));                            
                                k++;
                            }
                            for (k=line2PosFoundAt; k >= 0; k--) {
                                origLine1 = file1.get(0);
                                file1.remove(0);
                            }
                            file1Line++;
                            line1 = origLine1;
                        }
                    }
                }                
            }            
            log.info("EXITING....   file1 remaining = " + file1.size() + ", file2 remaining = " + file2.size());
            log.info("   line1=" + line1 + ", line2=" + line2);
            
            if (line1 != null && line2 != null && line1.length()!=0 && line2.length()!=0) {
                if (line1.equals(line2)){
                log.debug("SAME: " + origLine1); 
                same.println(origLine1);
                if (! onlyWriteDifferences)
                    diff.println(formatDiffFile(0, findPositionInList(origLine1, file3)+1, findPositionInList(origLine1, file4)+1, origLine1));                    
                }               
            } else if ((line1 == null ||line1.length()==0) && line2 != null) {                
                log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(origLine2, file4)+1, origLine2));                
                
            } else if ((line2 == null || line2.length()==0) && line1 != null) {
                log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                diff.println(formatDiffFile(1, findPositionInList(origLine1, file3)+1, findPositionInList(line2, file4), origLine1));                
            }
            
            // End of one of the files.
            if (file1.size() == 0) {
                log.debug("EOF File 1.");
                while (file2.size() > 0) {
                    file2Line++;
                    log.debug("   File2 - Line " + file2Line + ": " + file2.get(0));                    
                    diff.println(formatDiffFile(2, findPositionInList(line1, file3), findPositionInList(file2.get(0), file4)+1, file2.get(0)));               
                    file2.remove(0);                                                
                }
            } 
            if (file2.size() == 0) {
                log.debug("EOF File 2.");
                while (file1.size() > 0) {
                    file1Line++;
                    log.debug("   File1 - Line " + file1Line + ": " + file1.get(0));
                    diff.println(formatDiffFile(1, findPositionInList(file1.get(0), file3)+1, findPositionInList(line2, file4), file1.get(0)));                    
                    file1.remove(0);                                                
                }
            }
        } finally {
            //Close File Readers
            if (reader1!=null)
                reader1.close();
            if (reader2!=null)
                reader2.close();
            if (reader3!=null)
                reader3.close();
            if (reader4!=null)
                reader4.close();

            //Close File Writers
            if (same!=null)
                same.close();
            if(diff!=null)
                diff.close();            
        }
    }
    
    /**
     * Compare the specified input files to determine differences. 
     * This method assumes that the two files have been sorted 
     * alphabetically before the comparison.  Sorted files can 
     * be processed without size restrictions because the files will
     * not need to be read into memory prior to the compare.  Unsorted
     * files must be loaded into memory.
     *  
     * @param inputFile1 String
     * @param inputFile2 String
     * @param outputFile String
     */
    public static void compareFiles(String inputFile1,
                                    String inputFile2,
                                    String outputFile,
                                    boolean ignoreWhiteSpace,
                                    boolean onlyWriteDifferences,
                                    boolean overwriteFiles) throws Exception {

        FileReader reader1 = null;
        FileReader reader2 = null;
        FileWriter diff = null;
        FileWriter same = null;
        int diffCount = 0;

        try {
            
            inputFile1 = forceAbsoluteFilePath(inputFile1);
            inputFile2 = forceAbsoluteFilePath(inputFile2);
            reader1 = new FileReader(inputFile1);
            reader2 = new FileReader(inputFile2);
            ArrayList <String> file1 = new ArrayList<String>();
            ArrayList <String> file2 = new ArrayList<String>();
            outputFile = FileUtils.extractBaseFileName(FileUtils.forceAbsoluteFilePath(outputFile));
            int file1Line = 0;
            int file2Line = 0;
            
            // Diff output file        
            diff = new FileWriter(outputFile+".diff", overwriteFiles);

            // Same output file        
            same = new FileWriter(outputFile+".same", overwriteFiles);

            // Read each input file into an array of String objects.
            while (! reader1.eof())
                file1.add(reader1.readLine());
            while (! reader2.eof())
                file2.add(reader2.readLine());
            
            log.debug("File1: " + file1.size() + " lines read.");
            log.debug("File2: " + file2.size() + " lines read.");
            
            // Lines left to compare from both files.  Continue...
            String origLine1 = file1.get(0);
            file1.remove(0);
            file1Line++;
            String origLine2 = file2.get(0);
            file2.remove(0);
            file2Line++;
            String line1 = origLine1;
            String line2 = origLine2;

            while (file1.size() > 0 || file2.size() > 0) {
                log.debug("Next (file1) [" + file1Line + "]: " + line1 + "  Sizes: F1=" + file1.size() + "  F2=" + file2.size());
                
                if (ignoreWhiteSpace) {
                    line1 = line1.trim();
                    line2 = line2.trim();
                    // Ignore blank lines after trim.
                    while (line1.equals("") && (file1.size() > 0)) {
                        log.debug("SAME: " + origLine1); 
                        same.println(origLine1);
                        origLine1 = file1.get(0);
                        line1 = origLine1.trim();
                        file1.remove(0);
                        file1Line++;
                    }
                    // Ignore blank lines after trim.
                    while (line2.equals("") && (file2.size() > 0)) {
                        origLine2 = file2.get(0);
                        line2 = origLine2.trim();
                        file2.remove(0);
                        file2Line++;
                    }
                }
                
                if (file1.size() > 0 && file2.size() > 0) {
                    
                    if (line1.equals(line2)) {
                       // Same.  Do nothing.
                       log.debug("SAME: " + origLine1); 
                       same.println(origLine1);
                       if (! onlyWriteDifferences)
                           diff.println(formatDiffFile(0, file1Line, file2Line, origLine1));

                       // Lines left to compare from both files.  Continue...
                       origLine1 = file1.get(0);
                       file1.remove(0);
                       file1Line++;
                       origLine2 = file2.get(0);
                       file2.remove(0);
                       file2Line++;
                       line1 = origLine1;
                       line2 = origLine2;

                    } else {
                        // Lines are different.  Handle differences.
                        int line1Pos = 0;
                        int line2Pos = 0;
                        diffCount++;
                        int line1PosFoundAt = findPositionInList(line1, file2);
                        int line2PosFoundAt = findPositionInList(line2, file1);
                        int smallestFile = Math.min(file1.size(), file2.size());
                        int lowestPos = Math.min(line1PosFoundAt, line2PosFoundAt);
                        lowestPos = Math.min(lowestPos, smallestFile);
                        int i=1;
                        if (lowestPos == Integer.MAX_VALUE)
                            lowestPos = smallestFile;

                        String testline1 = line1;
                        String testline2 = line2;
                        log.debug("DIFFERENT [" + file1Line + "]  FILE1: " + testline1); 
                        log.debug("          [" + file2Line + "]  FILE2: " + testline2); 
                        log.debug("          INITIAL: line1Pos=" + line1Pos + " line2Pos=" + line2Pos + " line1PosFoundAt=" + line1PosFoundAt + " line2PosFoundAt=" + line2PosFoundAt + " lowestPos=" + lowestPos); 

                        if (line1PosFoundAt == Integer.MAX_VALUE && line2PosFoundAt == Integer.MAX_VALUE) {
                            log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                            log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                            diff.println(formatDiffFile(1, file1Line, file2Line, origLine1));
                            diff.println(formatDiffFile(2, file1Line, file2Line, origLine2));
                            
                            // Get next lines.
                            origLine1 = file1.get(0);
                            file1.remove(0);
                            file1Line++;
                            origLine2 = file2.get(0);
                            file2.remove(0);
                            file2Line++;
                        } else {
                            while (i <= lowestPos) {
                                int nextLine1PosFoundAt = findPositionInList(testline1, file2);
                                if (nextLine1PosFoundAt < line1PosFoundAt) {
                                    line1Pos = i;
                                    line1PosFoundAt = nextLine1PosFoundAt;
                                }
                                
                                int nextLine2PosFoundAt = findPositionInList(testline2, file1);
                                if (nextLine2PosFoundAt < line2PosFoundAt) {
                                    line2Pos = i;
                                    line2PosFoundAt = nextLine2PosFoundAt;
                                }

                                int min = Math.min(line1PosFoundAt, line2PosFoundAt);
                                if (min < lowestPos)
                                    lowestPos = min;
                                
                                testline1 = file1.get(i);
                                testline2 = file2.get(i);
                                i++;
                            }
                            
                            log.debug("          line1Pos=" + line1Pos + " line2Pos=" + line2Pos + " line1PosFoundAt=" + line1PosFoundAt + " line2PosFoundAt=" + line2PosFoundAt + " lowestPos=" + lowestPos); 

                            if (line1PosFoundAt == Integer.MAX_VALUE &&
                                line2PosFoundAt == Integer.MAX_VALUE) {
                                // No similarities between files.
                                log.debug("NO SIMILARITIES.");
                                diff.println(formatDiffFile(2, file1Line, file2Line, origLine1));
                                for (int j=0; j <= file1.size()-1; j++)
                                    diff.println(formatDiffFile(1, file1Line, file2Line, file1.get(j)));
                                file1.clear();
                                diff.println(formatDiffFile(2, file1Line, file2Line, origLine2));
                                for (int j=0; j <= file2.size()-1; j++)
                                    diff.println(formatDiffFile(2, file1Line, file2Line, file2.get(j)));
                                file2.clear();
                            } else if (line1PosFoundAt <= line2PosFoundAt) {
                                int k=0;
                                log.debug("Writing differences...");
                                log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                                diff.println(formatDiffFile(2, file1Line, file2Line, origLine2));
                                while (k < line1PosFoundAt) {
                                    file2Line++;
                                    log.debug("   File2 - Line " + file2Line + ": " + file2.get(k));
                                    diff.println(formatDiffFile(2, file1Line, file2Line, file2.get(k)));
                                    k++;
                                }
                                for (k=line1PosFoundAt; k >= 0; k--) {
                                    origLine2 = file2.get(0);
                                    file2.remove(0);
                                }    
                                file2Line++;
                                line2 = origLine2;
                            } else if (line2PosFoundAt < line1PosFoundAt) {
                                int k=0;
                                log.debug("Writing differences...");
                                log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                                diff.println(formatDiffFile(1, file1Line, file2Line, origLine1));
                                while (k < line2PosFoundAt) {
                                    file1Line++;
                                    log.debug("   File1 - Line " + file1Line + ": " + file1.get(k));
                                    diff.println(formatDiffFile(1, file1Line, file2Line, file1.get(k)));
                                    k++;
                                }
                                for (k=line2PosFoundAt; k >= 0; k--) {
                                    origLine1 = file1.get(0);
                                    file1.remove(0);
                                }
                                file1Line++;
                                line1 = origLine1;
                            }
                        }
                    }
                } 
            }
            
            log.info("EXITING....   file1 remaining = " + file1.size() + ", file2 remaining = " + file2.size());
            log.info("   line1=" + line1 + ", line2=" + line2);
            
            if (line1 != null && line2 != null && line1.equals(line2)) {
                log.debug("SAME: " + origLine1); 
                same.println(origLine1);
                if (! onlyWriteDifferences)
                    diff.println(formatDiffFile(0, file1Line, file2Line, origLine1));
            } else if (line1 == null && line2 != null) {
                diffCount++;
                log.debug("   File2 - Line " + file2Line + ": " + origLine2);
                diff.println(formatDiffFile(2, file1Line, file2Line, origLine2));
            } else if (line2 == null && line1 != null) {
                diffCount++;
                log.debug("   File1 - Line " + file1Line + ": " + origLine1);
                diff.println(formatDiffFile(1, file1Line, file2Line, origLine1));
            }
            
            // End of one of the files.
            if (file1.size() == 0) {
                log.debug("EOF File 1.");
                while (file2.size() > 0) {
                    diffCount++;
                    file2Line++;
                    log.debug("   File2 - Line " + file2Line + ": " + file2.get(0));
                    diff.println(formatDiffFile(2, file1Line, file2Line, file2.get(0)));
                    file2.remove(0);                                                
                }
            } 
            if (file2.size() == 0) {
                log.debug("EOF File 2.");
                while (file1.size() > 0) {
                    diffCount++;
                    file1Line++;
                    log.debug("   File1 - Line " + file1Line + ": " + file1.get(0));
                    diff.println(formatDiffFile(1, file1Line, file2Line, file1.get(0)));
                    file1.remove(0);                                                
                }
            }
        } finally {
            
            reader1.close();
            reader2.close();
            same.close();
            diff.close();
            
            // Clear diff file if no differences (set byte count to 0).
            if (diffCount == 0) {
                diff = new FileWriter(outputFile+".diff", overwriteFiles);
                diff.close();
            }
        }
        
        

    }
    
    /**
     * Returns Integer.MAX_VALUE if String not found in list.
     * 
     * @param line String
     * @param list ArrayList<String>
     * @return int - First position of specified String in ArrayList.
     */
    private static int findPositionInList(String line, List<String> list) {
        int i = 0;
        if (! list.contains(line))
            return Integer.MAX_VALUE;
        while (i <= list.size()-1) {
            if (list.get(i).equals(line))
                return i;
            i++;                
        }
        
        return Integer.MAX_VALUE;
    }
    
    /**
     * Return the prefix to use for the diff file.
     * @param filenum
     * @param file1LineNum
     * @param file2LineNum
     * @param text
     * @return
     */
    private static String formatDiffFile(int filenum, int file1LineNum, int file2LineNum, String text) {
        return formatDiffFile(filenum, file1LineNum, file2LineNum, text, null, null, null);
    }
    
    /**
     * Return the prefix to use for the diff file.
     * @param filenum
     * @param file1LineNum
     * @param file2LineNum
     * @param text
     * @param file1Tag String
     * @param file2Tag String
     * @param bothTag String
     * @return
     */
    private static String formatDiffFile(int filenum, int file1LineNum, int file2LineNum, String text, String file1Tag, String file2Tag, String bothTag) {
        StringBuffer buffer = new StringBuffer();
        
        if (file1Tag == null)
            file1Tag = "File1";
        if (file2Tag == null)
            file2Tag = "File2";
        if (bothTag == null)
            bothTag = "File1/2";
        if (filenum <= 0) {
            buffer.append(StringUtils.leftJustify(bothTag, 10));
            buffer.append(StringUtils.leftJustify(file1LineNum+"", 8));
            buffer.append(StringUtils.leftJustify(file2LineNum+"", 8));
        } else if (filenum == 1) { 
            buffer.append(StringUtils.leftJustify(file1Tag, 10));
            buffer.append(StringUtils.leftJustify(file1LineNum+"", 8));
            buffer.append(StringUtils.leftJustify("", 8));
        } else if (filenum == 2) {
            buffer.append(StringUtils.leftJustify(file2Tag, 10));
            buffer.append(StringUtils.leftJustify("", 8));
            buffer.append(StringUtils.leftJustify(file2LineNum+"", 8));
        }
        buffer.append(" - ");
        buffer.append(text);
        return buffer.toString();            
    }
    
    
    /**
     * Merge (ie. concatenate) the specified unsorted input files into
     * one unsorted output file.
     *  
     * @param inputFiles ArrayList<String>
     * @param outputFile String
     */
    public static void mergeFiles(ArrayList<String> inputFiles,
                                  String outputFile,
                                  boolean overwriteFile) throws Exception {

        FileWriter out = new FileWriter(outputFile, overwriteFile);
        FileReader reader = null;

        try {
            
            long lineCount = 0;

            // Create FileReader objects for each input file.
            for (int i=0; i <= inputFiles.size()-1; i++) {
                String filePath = forceAbsoluteFilePath(inputFiles.get(i));
                reader = new FileReader(filePath);
                while (! reader.eof()) {
                    String nextLine = reader.readLine();
                    out.println(nextLine);
                    lineCount++;
                }
                reader.close();
            }
            
            log.debug("Lines processed: " + lineCount);
        } finally {
            out.close();
            reader.close();
        }
    }
    
    /**
     * Are we done processing?  Returns true if all input files are
     * at eof=true, false otherwise.
     * 
     * @param eof boolean[]
     * @return boolean
     */
    private static boolean doneProcessing(boolean[] eof) {
        for (int i=0; i <= eof.length-1; i++) {
            if (! eof[i]) {
                return false;
            }    
        }
        return true;
    }
    
    /**
     * Are we done processing?  Returns true if all input files are
     * at eof=true, false otherwise.
     * 
     * @param eof boolean[]
     * @return boolean
     */
    private static String checkDoneProcessing(boolean[] eof) {
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i <= eof.length-1; i++) {
            if (! eof[i])
                buffer.append(" N");
            else
                buffer.append(" Y");
        }
        return buffer.toString();
    }
    
    /**
     * Write the buffer (ArrayList of String objects) to the
     * specified output file.
     * 
     * @param buffer ArrayList<String>
     * @param baseFileName String
     * @param fileCount int
     * @throws Exception
     */
    private static void writeBufferToFile(ArrayList<String> buffer,
                                          String baseFileName, 
                                          int fileCount) throws Exception {

        FileWriter out = null;
        try {
            
            // Reached buffer size.  Sort what's in the buffer.
            log.info("Sorting file " + fileCount + "...");
            Collections.sort(buffer);
            // Write to output file.
            log.info("Writing file " + fileCount + "...");
            out = new FileWriter(baseFileName + ".s" + fileCount);
            for (int j=0; j <= buffer.size()-1; j++)
                out.println(buffer.get(j));
            log.info("Writing file " + fileCount + "...  DONE.");
            buffer.clear();
        } finally {
            
            out.close();
        }

    }

    /**
     * Scan the file to see how many lines are in the file.
     * 
     * @param inputFile String
     * @throws Exception
     */
    public static void scanFile(String inputFile) throws Exception {

        FileReader reader = null;
        int i = 0;

        try {
            
            inputFile = forceAbsoluteFilePath(inputFile);
            reader = new FileReader(inputFile);
            while (! reader.eof()) {
                reader.readLine();
                i++;
            }
            
        } finally {
            reader.close();
        }
        
        log.info("File processed (" + inputFile + ").  Lines read = " + i + ".");
    }

    /** System independent line separator. */
    public final static String NEW_LINE = System.getProperty("line.separator");

    /**
     * This method can be used retrieve the size of a file pre-existing file.
     * 
     * @param fileName
     *           The path + filename to check for existence.
     * 
     * @return -1 if the file doesn't exist, otherwise the size of the file
     */
    public static long getFileSize(String fileName) {

        long fileSize = -1;

        synchronized (SyncReference.getInstance().get(fileName)) {
            File file = new File(fileName);

            if (file.exists()) {
                fileSize = file.length();
            }
        }

        return fileSize;
    }

    /**
     * This method can be used to retrieve a file's last modified date.
     * 
     * @param fileName
     *           The path + filename to check for existence.
     * 
     * @return A <code>long</code> value representing the time the file was
     *         last modified, measured in milliseconds since the epoch (00:00:00
     *         GMT, January 1, 1970), or <code>0L</code> if the file does not
     *         exist or if an I/O error occurs
     */
    public static long getLastModified(String fileName) {
        long lastModified = 0;

        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(fileName)) {
            File file = new File(fileName);

            if (file.exists()) {
                lastModified = file.lastModified();
            }
        }
        return lastModified;
    }

    /**
     * This method can be used to set a file's last modified date.
     * 
     * @param fileName
     *           The path + filename to check for existence and set modified
     *           date.
     * @param dateModified
     * @return
     */
    public static boolean setLastModified(String fileName, Date dateModified) {
        boolean fileUpdated = false;

        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(fileName)) {
            File file = new File(fileName);

            if (file.exists()) {

                if (dateModified != null) {
                    /* if date specified, then use the dateModified time */
                    fileUpdated = file.setLastModified(dateModified.getTime());
                } else {
                    /* if date not specified, then use the current time */
                    fileUpdated = file.setLastModified(System.currentTimeMillis());
                }
            }
        }

        return fileUpdated;
    }

    /**
     * This method can be used to return an array of the files in a particular
     * directory.
     * 
     * @param filePath
     *           The directory to retrieve the list of files from.
     * 
     * @return The <code>java.io.File[]</code> of the files in the filePath.
     */
    public static File[] getFiles(String filePath) {
        File[] listOfFiles = null;

        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(filePath)) {
            File file = new File(filePath);

            if (file.exists()) {
                listOfFiles = file.listFiles();
                Arrays.sort(listOfFiles);
            }
        }

        return listOfFiles;
    }

    /**
     * Utility method to delete a local file.
     * 
     * @param fileName
     *           The name of the file to delete
     * 
     * @return true if the file was deleted, false otherwise.
     */
    public static boolean deleteFile(String fileName) throws Exception {
        boolean deleted = false;

        try {
            
            File file = new File(fileName);

            // synchronize on a common reference "filename",
            // allow only one synchronized block at a time access
            synchronized (SyncReference.getInstance().get(fileName)) {
                if (file.exists()) {
                    deleted = file.delete();
                    log.debug("File deleted (" + fileName + ").");
                } else {
                    log.debug("File (" + fileName + ") does not exist.");
                    deleted = true;
                }
            }

            return deleted;
        } catch (Exception e) {
            log.debug("Unable to delete file (" + fileName + ").  " + e.getMessage());
            throw e;                          
        }
    }

    /**
     * Move a file from one location to another.
     * 
     * @param sourcePath String
     * @param destinationPath String
     * @return boolean - successful
     */
    public static boolean moveFile(String sourcePath, String destinationPath) {
        boolean moved = false;

        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(sourcePath)) {
            if (source.exists()) {
                moved = source.renameTo(destination);
            } else {
                moved = false;
            }
        }

        return moved;
    }
    
    /**
     * Copy a file from one location to another.
     * 
     * @param sourcePath String
     * @param destinationPath String
     * @return boolean - successful
     */
    public static boolean copyFile(File sourceFile, File destinationFile) throws Exception {
        
    	boolean copied = false;
        InputStream fin = null;
        OutputStream fout = null;
        

         // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(sourceFile.getAbsolutePath())) {
        	try{
        	      fin = new FileInputStream(sourceFile);
        	      fout = new FileOutputStream(destinationFile);

        	      byte[] buffer = new byte[1024];
        	      int len;
        	      while ((len = fin.read(buffer)) > 0){
        	    	  fout.write(buffer, 0, len);
        	      }
        	      copied = true;
        	    }
        	catch (Exception e) {
                throw e;                          
            }
        	finally {
      		  if(fin != null) {
      			fin.close();
      		  }
      		  if(fout != null) {
      			fout.close();
      			}
           }
        }
        return copied;
    }

    
    
    /**
     * This method can be used to determine if a file is present or not.
     * 
     * @param fileName The path + filename to check for existence.
     * 
     * @return true if the file exists.
     */
    public static boolean doesFileExist(String fileName) {
    	return doesFileExist(fileName, false);
    }
    
    /**
     * This method can be used to determine if a file is present or not.
     * <p>
     * If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system.
     * 
     * @param fileName The path + filename to check for existence.
     * 
     * @return true if the file exists.
     */
    public static boolean doesFileExist(String fileName, boolean readFilesFromClassLoader) {

    	if (fileName == null) {
    		return false;
    	}
    	
        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(fileName)) {
        	
            File file = null;
        	if (readFilesFromClassLoader) {
        		InputStream stream = FileUtils.class.getResourceAsStream(fileName);
        		if (stream == null) {
        			return false;
        		} else {
        			return true;
        		}
        	} else {
                file = new File(fileName);
        	}

            if (file.exists() && file.isFile()) {
            	return true;
            } else {
            	return false;
            }
        }    
    }

    /**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.
     * 
     * @param fileName The path + fileName to read the contents from.
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    public static Properties getContents(String fileName) throws CoreException {
    	String timestamp = StringUtils.dateToString(new Date(), "yyyyMMdd-HHmmss"); 
    	return getContents(fileName, null, timestamp, false, false);
    }

    /**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.
     * <p>
     * If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system.
     * 
     * @param fileName The path + fileName to read the contents from.
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    public static Properties getContents(String fileName, boolean readFilesFromClassLoader) throws CoreException {
    	String timestamp = StringUtils.dateToString(new Date(), "yyyyMMdd-HHmmss"); 
    	return getContents(fileName, null, timestamp, false, readFilesFromClassLoader);
    }

	/**
     * This method is utilized to read the contents of a file:
     * <code>fileName</code> in as a FileInputStream and load it into the
     * Properties parameter and returned.  
     * <p>
     * If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system.
     * 
     * @param fileName The path + fileName to read the contents from.
     * @param enableEnvironmentVariableReplacement Replace ${name} with
     *        environment variable value.  Ex. ${RMS_CONF}/dcc.properties  
     * 
     * @return A Properties initialized with the contents of the fileName.
     *         Properties.isEmpty() = true if the fileName could not be found or
     *         it is empty.
     */
    public static Properties getContents(String fileName,
                                         String scriptName,
                                         String timestamp,
    		                             boolean enableParameterSubstitution,
    		                             boolean readFilesFromClassLoader) throws CoreException {
        String logPrefix = "FileUtil - getContents(): ";

        // Properties object to load the file into
        Properties tempProps = new Properties();
        Properties props = new Properties();

        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(fileName)) {
        	
            File file = null;
            InputStream stream = null;
        	
            try {
                if (readFilesFromClassLoader) {
                    try {
                		stream = FileUtils.class.getResourceAsStream(fileName);
                		if (stream == null) {
                			return new Properties();
                		}
                    } catch (Exception e) {
                    	throw new CoreException("Failed to load file " + fileName + ".  " + e.getMessage(), e);
                    }
                    
            	} else {
                    file = new File(fileName);
                    if (! file.exists()) {
            			return new Properties();
            		}
                    stream = new FileInputStream(file);
            	}

                tempProps.load(stream);
                Iterator it = tempProps.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    key = (key != null) ? key.trim() : key;
                    
                    // Cleanup value (trim, parameter substitution, etc.). 
                    if (enableParameterSubstitution) {
                    	// Trim happens inside this method.
                        value = StringUtils.replaceTokensWithEnvVariables(value, scriptName, timestamp);
                    } else {
                        value = (value != null) ? value.trim() : value;
                    }
                    props.put(key, value);
                }
            } catch (FileNotFoundException fnfe) {
                throw new CoreException("Could not find file: " + fileName, fnfe);
            } catch (IOException ioe) {
                throw new CoreException("Could not read from file: " + fileName, ioe);
            } catch (Exception e) {
            	throw new CoreException("Failed to get contents of file: " + fileName, e);
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException ioe) {
                	if (LogUtils.isLog4jConfigured()) {
                        log.info(logPrefix + "Exception caught while closing "
                            + "FileInputStream: " + ioe.getMessage());
                	} else {
                		System.out.println("Error loading properties file (" + fileName + ").");
                		ioe.printStackTrace();
                	}
                }
            }
        }

        return props;
    }

    /**
     * Read this file into an InputStream and return the stream to the
     * caller.  If readFilesFromClassLoader is true, the filepath will be
     * used to do a lookup of the configuration file in the classpath using
     * the classloader. If not, it will look in the file system. 
     * 
     * @param fileName String
     * @param readFilesFromClassLoader boolean
     * @return InputStream
     * @throws CoreException
     */
    public static InputStream getFileAsStream(String fileName,
    		                                  boolean readFilesFromClassLoader) throws CoreException {
        // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(fileName)) {
        	
            File file = null;
            InputStream stream = null;
        	
            try {
                if (readFilesFromClassLoader) {
                    try {
                		return FileUtils.class.getResourceAsStream(fileName);
                    } catch (Exception e) {
                    	throw new CoreException("Failed to load file " + fileName + ".  " + e.getMessage(), e);
                    }
                    
            	} else {
                    file = new File(fileName);
                    if (! file.exists()) {
                    	throw new FileNotFoundException("Could not find the file " + fileName + ".");
            		}
                    return new FileInputStream(file);
            	}
            } catch (FileNotFoundException fnfe) {
                throw new CoreException("Could not find file: " + fileName, fnfe);
            } catch (Exception e) {
            	throw new CoreException("Failed to get contents of file: " + fileName, e);
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException ioe) {
                	if (LogUtils.isLog4jConfigured()) {
                        log.info("Exception caught while closing file.  FileInputStream: " + ioe.getMessage());
                	} else {
                        System.out.println("Exception caught while closing file.  FileInputStream: " + ioe.getMessage());
                		ioe.printStackTrace();
                	}
                }
            }
        }
    }
    
    /**
     * Get the base file name (everything before the last '.').
     * 
     * @param filename String
     * @return String - The new filename.
     */
    public static String extractBaseFileName(String filename) {
       int pos = filename.lastIndexOf('.');
       return filename.substring(0, pos);
    }

    /**
     * Get the base file name (everything before the last '.').
     * 
     * @param filename String
     * @return String - The new filename.
     */
    public static String changeFileExtension(String filename,
                                             String newExtension) {
       int pos = filename.lastIndexOf('.');
       String baseName = filename.substring(0, pos);
       return baseName + "." + newExtension;
    }

    /**
     * Returns the actual filename after stripping off the path
     * info.
     * 
     * @param filename String
     * @return String - The new filename.
     */
    public static String stripFilePath(String filename) {
       int pos = filename.lastIndexOf('/');
       String after = null;
       if (pos != -1) 
           after = filename.substring(pos+1);
       else
           after = filename;
       filename = after;
       pos = filename.lastIndexOf('\\');
       if (pos != -1) 
           after = filename.substring(pos+1);
       else
           after = filename;
       filename = after;
       return filename;
    }
    
    /**
     * Add the timestamp to the specified filename.  This method
     * adds an underscore and the date/time right before the . in the 
     * filename.
     * 
     * @param filename String
     * @return String - The new filename.
     */
    public static String extractFilePath(String filename) {
       int pos = filename.lastIndexOf('/');
       String after = null;
       if (pos != -1) 
           after = filename.substring(0, pos);
       else
           after = filename;
       filename = after;
       pos = filename.lastIndexOf('\\');
       if (pos != -1) 
           after = filename.substring(0, pos);
       else
           after = filename;
       filename = after;
       return filename;
    }
    
    /**
     * Returns the actual file name with no path 
     * information.
     * 
     * @param filename String
     * @return String - The new filename.
     */
    public static String removeFilePath(String filename) {
       try {
           int pos = filename.lastIndexOf('/');
           String after = null;
           if (pos != -1) 
               after = filename.substring(pos+1);
           else
               after = filename;
           filename = after;
           pos = filename.lastIndexOf('\\');
           if (pos != -1) 
               after = filename.substring(pos+1);
           else
               after = filename;
           filename = after;
           return filename;
       } catch (Exception e) {
           return filename;           
       }
    }
    
    /**
     * 
     * @param filename
     * @return
     */
    public static String forceAbsoluteFilePath(String filePath) throws Exception {

        if (filePath == null)
            throw new CoreException("The input file was not specified.");
        
        // If only filename specified then prepend current working
        // directory.
        String workingDirectory = System.getProperty("user.dir") + "/";
        if (! (filePath.contains("/") || filePath.contains("\\"))) {
            // Path not included in filename.  Adding working directory.
            filePath = workingDirectory + filePath;
        }
        return filePath;
    }
    
    /**
     * Runs an executable file from Java (shell script, batch file,
     * bin, etc.).
     * 
     * @param command String
     * @return List<String> The error results.
     * @throws IOException
     */
    public static OutputStreams runExecutable(String command) throws IOException {

        // start the fping command
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);

        StreamGobblerRunnable outRunnable = new StreamGobblerRunnable(proc.getInputStream());
        StreamGobblerRunnable errRunnable = new StreamGobblerRunnable(proc.getErrorStream());
        StreamGobbler outGobbler = new StreamGobbler(outRunnable);
        StreamGobbler errGobbler = new StreamGobbler(errRunnable);
        outGobbler.start();
        errGobbler.start();

        // wait for fping to finish
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            log.warn("InterruptedException waiting for a process to complete" 
                          +  stackTraceToString(e));
        }

        // wait for the error buffer to finish writing.
        while (! errGobbler.isDone()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        // wait for the out buffer to finish writing.
        while (!outGobbler.isDone()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        return new OutputStreams(outGobbler.getReadData(),
                                 errGobbler.getReadData());
    }
    
    /**
     * Runs an executable file from Java (shell script, batch file,
     * bin, etc.).
     * 
     * @param command String
     * @return List<String> The error results.
     * @throws IOException
     */
    public static int runCmdExecutable(String command) throws IOException {

        // start the fping command
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);

        StreamGobblerRunnable outRunnable = new StreamGobblerRunnable(proc.getInputStream());
        StreamGobblerRunnable errRunnable = new StreamGobblerRunnable(proc.getErrorStream());
        StreamGobbler outGobbler = new StreamGobbler(outRunnable);
        StreamGobbler errGobbler = new StreamGobbler(errRunnable);
        outGobbler.start();
        errGobbler.start();

        // wait for fping to finish
        int rtn = -1;
        try {
            rtn = proc.waitFor();
        } catch (InterruptedException e) {
            log.warn("InterruptedException waiting for a process to complete" 
                          +  stackTraceToString(e));
        }

        // wait for the error buffer to finish writing.
        while (! errGobbler.isDone()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        // wait for the out buffer to finish writing.
        while (!outGobbler.isDone()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        return rtn;
    }

    
    /**
     * Method that sends an Exceptions stack trace to a String
     * 
     * @param ex
     *            Exception to get stack trace of
     * @return String of stack trace
     */
    private static String stackTraceToString(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        return stackTrace;
    }
    
    /**
     * Deletes directory by deleting all of its child directories and files first
     */
    public static void deleteDir(File file) throws IOException
    {
    	for(File child : file.listFiles())
    	{
    		if(child.isDirectory())
    		{
    			deleteDir(child);
    		}
    		child.delete();
    	}
    	file.delete();
    }
    
    /**
     * Method inserts line in to the file
     * 
     * @param inFile
	 *				  Input File
	 *		  lineNumber
	 *				  Line number at which line to be inserted
	 *		  lineToBeInserted
	 *				  line to be inserted  
     * @throws Exception
     */

    public static void insertLineInFile(File inFile,String lineNumber, Collection<?> lineToBeInserted) throws Exception 
    {
    	int lineno= Integer.parseInt(lineNumber);
    	File outFile = new File(inFile.getAbsolutePath()+".temp");
    	FileInputStream fis  = new FileInputStream(inFile);
        BufferedReader into = new BufferedReader(new InputStreamReader(fis));
          
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);

    	 boolean first=true;
    	            	  
         String thisLine = "";
         int i =1;
         while ((thisLine = into.readLine()) != null) {
           if(i == lineno)
           {
        	   for(Object value : lineToBeInserted)
       		   {
       		    if(first) first = false;
       			else {
       				out.print(',');
           			}
       			    			
       		    if(value == null) continue;
       			
       			// escape string which contain commas
       			if(value.toString().indexOf(',') == -1)
       			{
       		    out.print(value);
   				}
       			else{
       				out.print("\"");
       				out.print(value);
       				out.print("\"");
       			}
       				
       		}
        	   out.println();
        	   
           }
        	   out.println(thisLine);
               i++;
           }
        out.flush();
        out.close();
        into.close();
       
        inFile.delete();
        outFile.renameTo(inFile);
        }
    public static boolean writeToFile(String filePath,String fileContent) throws Exception
    {
    	boolean wroteToFile = false;
        OutputStream fout = null;

         // synchronize on a common reference "filename",
        // allow only one synchronized block at a time access
        synchronized (SyncReference.getInstance().get(filePath)) {
        	try{
        	      fout = new FileOutputStream(filePath);
        	      fout.write(fileContent.getBytes(), 0, fileContent.length());
        	      wroteToFile = true;
        	}
        	finally {
      		  if(fout != null) {
      			fout.close();
      			}
           }
        }
        return wroteToFile;
    }

    /**
     * Returns the actual classname without the package information.
     *
     * @param filename String
     * @return String - The new filename.
     */
    public static String stripClassPath(String fullClassName) {
        int pos = fullClassName.lastIndexOf('.');
        String after = null;
        if (pos != -1)
            after = fullClassName.substring(pos + 1);
        else
            after = fullClassName;
        return after;
    }

}
