package mjs.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This is a utility class which contains String conversion utility functions.
 */
public class StringUtils {
    /**
     * Right justifies the input String by padding spaces to the left, making it the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String rightJustify(String value,
                                      int length) {
        StringBuffer buffer = new StringBuffer();

        for (int C = 0; C <= (length - value.length()) - 1; C++)
            buffer.append(" ");
        buffer.append(value);
        return buffer.toString();
    }

    /**
     * Right justifies the input String by padding zeros to the left, making it the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String leadingZeros(int value,
                                      int length) {
        StringBuffer buffer = new StringBuffer();
        String input = value + "";

        for (int C = 0; C <= (length - input.length()) - 1; C++)
            buffer.append("0");
        buffer.append(input);
        return buffer.toString();
    }

    /**
     * Left justifies the input String by padding spaces to the right, making it the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String leftJustify(String value,
                                     int length) {
        StringBuffer buffer = new StringBuffer();

        if (value == null)
            value = "null";
        buffer.append(value);
        for (int C = 0; C <= (length - value.length()) - 1; C++)
            buffer.append(" ");
        return buffer.toString();
    }

    /**
     * Remove the package specifier from this class name.
     * 
     * @param classname String
     * @return String
     */
    public static String removePackage(String classname) {
        int lastDot = classname.lastIndexOf(".");

        return classname.substring(lastDot + 1);
    }

    /**
     * Convert an ArrayList object into an array of String objects.
     * 
     * @param list ArrayList
     * @return String[]
     */
    public static String[] toArray(ArrayList<String> list) {
        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }

    /**
     * Force the length of the the specified String, truncating if necessary.
     * 
     * @param value String
     * @param length value
     * @return String
     */
    public static String forceLength(String value,
                                     int length) {
        return forceLength(value, length, 3);
    }

    /**
     * Force the length of the the specified String, truncating if necessary.
     * 
     * @param value String
     * @param length value
     * @return String
     */
    public static String forceLength(String value,
                                     int length,
                                     int buffer) {
        if (value == null)
            value = "null";
        int current = value.length();
        if (current > length - buffer)
            current = length - buffer;
        return leftJustify(value.substring(0, current), length);
    }

    /**
     * Truncates a string after last occurrence of the specified character. The specified character remains but everything after
     * the last occurrence of that character is removed from the String.
     * 
     * @param input String
     * @param marker
     * @return String - result
     */
    public static String truncateAfterLastOccurrence(String input,
                                                     String marker) {
        int pos = input.lastIndexOf(marker);
        if (pos == -1)
            return input;
        else
            return input.substring(0, (pos + marker.length()));
    }

    /**
     * Accepts Date and converts to a String with a default date format.
     * 
     * @param date
     * @return String
     */
    public static String dateToString(Date date) {
        return dateToString(date, null);
    }

    /**
     * Accepts Date and converts to a String with the specified format.
     * 
     * @param date
     * @param format
     * @return String
     */
    public static String dateToString(Date date,
                                      String format) {
        if (format == null)
            format = "MM-dd-yyyy HH:mm:ss-SSS";

        SimpleDateFormat df = new SimpleDateFormat(format);

        df.setLenient(false);
        return df.format(date);
    }
    /**
     * Accepts time and converts to a String with the specified format.
     * 
     * @param long
     * @param format
     * @return String
     */
    public static String timeToDateString(long time,
                                      String format) {
        if (format == null)
            format = "MM-dd-yyyy HH:mm:ss-SSS";

        SimpleDateFormat df = new SimpleDateFormat(format);

        df.setLenient(false);
        return df.format(new Date(time));
    }
    /**
     * Reports current date in a string format that is naturally (alphanumerically) sortable.
     */
    public static String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
        df.setLenient(false);
        return df.format(new Date());
    }

    /**
     * Convert a String object to an InputStream objct.
     * 
     * @param value String
     * @return InputStream
     * @throws Exception
     */
    public static InputStream stringToInputStream(String value) throws Exception {
        return new ByteArrayInputStream(value.getBytes("UTF-8"));
    }

    /**
     * Convert an InputStream object to a String object.
     * 
     * @param is InputStream
     * @return String
     * @throws Exception
     */
    public static String inputStreamToString(InputStream is) throws Exception {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Parses command-line arguments/flags into a map. This method skips any positional arguments. All flags must be preceded with
     * a dash. Flags can have values of not. For example, "-arg1 -output file -v" gets returned as a map: arg1->null,
     * output->file, v->null.
     */
    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<String, String>(args.length);
        String lastArg = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (lastArg != null)
                    map.put(lastArg, null);
                lastArg = arg;
            } else {
                if (lastArg != null) {
                    map.put(lastArg, arg);
                    lastArg = null;
                }
            }
        }

        if (lastArg != null)
            map.put(lastArg, null);

        return map;
    }

    /**
     * Converts version string like 5.0.1.0.0 to number 5000100000, 5.0 to 5000000000. Numeric number allows version comparisons
     * for greater/smaller. Any version strings beyond 5 digits are stripped at the end. Also, handle special values which start
     * with 0, such as "018". These get treated as 0.1.8 (or 0.1.8.0.0) which gets translated to 10800000, in this example.
     */
    public static long convertVersionToNumber(String version) {
        if (version == null || version.length() == 0)
            return 0;

        // handle special version strings which have no dots
        if (version.indexOf('.') == -1) {
            // handle special values exactly 3 digits with one leading 0
            if (version.startsWith("0") && version.length() == 3) {
                long part1 = (long) Integer.valueOf(version.charAt(1) + "") * 10000000;
                long part2 = (long) Integer.valueOf(version.charAt(2) + "") * 100000;
                return part1 + part2;
            }

            // assume the number is just a major version
            if (version.length() > 1)
                return (long) Integer.valueOf(version) * 100000000;
            else
                return (long) Integer.valueOf(version) * 1000000000;
        }

        // split the string
        StringTokenizer st = new StringTokenizer(version, ".");
        long multiplier = 1000000000;
        long swVersion = 0;
        int count = 0;
        while (st.hasMoreElements()) {
            long num = Integer.valueOf((String) st.nextElement()).intValue();
            if (num > 9)
                swVersion += num * multiplier / 10;
            else
                swVersion += num * multiplier;

            multiplier = multiplier / 100;
            count++;
            if (count == 5)
                break;
        }

        return swVersion;
    }

    /**
     * Parse CSV line. Un-quote any values containing a comma. Skip commas in quotes strings. Line has to be passed in without any
     * line breaks
     */
    public static List<String> parseCsvLine(String line) {
        List<String> list = new ArrayList<String>();
        int startIndex = 0;
        boolean quoted = false;
        boolean newValue = true;

        StringBuffer inputLine = new StringBuffer(line);
    
        for (int i = 0; i < inputLine.length(); i++) {
            if (newValue) {
                if (inputLine.charAt(i) == '"') {
                    quoted = true;
                    startIndex = i + 1;
                    newValue = false;
                    continue;
                } else {
                    quoted = false;
                    startIndex = i;
                    newValue = false;
                }
            }

            // look for end of quoted string
            if (quoted) {
                if (inputLine.charAt(i) == '"') {
                	
                	//Handling embedded quotes
                	if (inputLine.length() != i + 1 && inputLine.charAt(i+1) == '"') {
                		inputLine.deleteCharAt(i);
                		continue;
                    }
                	
                    // check for end of string
                    if (inputLine.length() == i + 1) {
                        list.add(inputLine.substring(startIndex, inputLine.length() - 1));
                        break;
                    }

                    // check for comma following a quote, otherwise it can be a quoted quote
                    else if (inputLine.charAt(i + 1) == ',') {
                        list.add(inputLine.substring(startIndex, i));
                        startIndex = i + 3;
                        quoted = false;
                        newValue = true;

                        // check for end of line
                        if (inputLine.length() == i + 2) {
                            list.add("");
                            break;
                        }

                        i++;
                        continue;
                    }
                }

                // if did not find quote or end of line, skip until we do
                else
                    continue;
            }

            if (inputLine.charAt(i) == ',') {
                list.add(inputLine.substring(startIndex, i));
                newValue = true;
                startIndex = i + 1;

                // check for empty end of line
                if (inputLine.length() == i + 1) {
                    list.add("");
                }

                continue;
            }

            // check for end of line
            if (inputLine.length() == i + 1) {
                list.add(inputLine.substring(startIndex));
                break;
            }
        }

        return list;
    }

    /**
     * Writes CVS line. Quotes any values containing a comma.
     */
    public static void writeCsvLine(Collection<?> values,
                                    PrintWriter output) {
        boolean first = true;
        for (Object value : values) {
            if (first)
                first = false;
            else
                output.print(',');

            if (value == null)
                continue;

            // escape string which contain commas
            if (value.toString().indexOf(',') != -1) {
                output.print('"');
                output.print(value);
                output.print('"');
            } else
                output.print(value);
        }
        output.println();
    }
    
    /**
     * Replace the "from" char with the "to" char in the 
     * specified String object. 
     * @param target String
     * @param from char
     * @param to char
     * @return String
     */
    public static String replaceAll(String target, char from, char to) {
       StringBuilder builder = new StringBuilder();
       for (int i=0; i <= target.length()-1; i++) {
          if (target.charAt(i) == from) {
             builder.append(to);
          } else {
             builder.append(target.charAt(i));
          }
       }
       return builder.toString();
    }

    /**
     * Break this string into tokens using the specified delimiter. The tokens will be returned as an array of String objects.
     * 
     * @param text
     * @param delimiter
     * @return String[]
     */
    public static String[] getTokens(String text,
                                     char delimiter) {
        // Remove underscore characters.
        ArrayList<String> list = new ArrayList<String>();

        int next = text.indexOf(delimiter);

        while (next > -1) {
            list.add(text.substring(0, next));
            text = text.substring(next + 1);
            next = text.indexOf(delimiter);
        }
        list.add(text);

        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }

    /**
     * Break this string into tokens using the specified delimiter. The tokens will be returned as an array of String objects.
     * 
     * @param text
     * @param delimiter
     * @return String[]
     */
    public static String[] getTokens(String text,
                                     String delimiter) {
        // Remove underscore characters.
        ArrayList<String> list = new ArrayList<String>();

        int next = text.indexOf(delimiter);

        while (next > -1) {
            list.add(text.substring(0, next).trim());
            text = text.substring(next + delimiter.length());
            next = text.indexOf(delimiter);
        }
        list.add(text.trim());

        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }
    
    /**
	 * Parse the date which contains 'T' and without 'T'
	 * 
	 * @param format String
	 * @param date  String
	 * @return Date
	 */
	public static Date parseDateString(String format, String date)
	throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date formatdate = null;
		try {
			formatdate = dateFormat.parse(date);

		} catch (Exception e) {
			throw new Exception("Parser Exception while parsing date string "
					+ e.getMessage());
		}
		return formatdate;

	}

	/**
	 * Get the difference of eventTime date and lastGPS date with respect to the hours
	 * 
	 * @param Date msgEventDate
	 * @param Date  lastGpsDate
	 * @return int
	 */
	public static int getDateDifferenceInHours(Date msgEventDate,
			Date lastGpsDate) {
		Calendar lastGpscal = Calendar.getInstance();
		lastGpscal.setTime(lastGpsDate);
		long msgEventTimeMilliseconds = msgEventTimeInMilliseconds(msgEventDate);
		long lastGpsMilliseconds = lastGpscal.getTimeInMillis();
		long diff = msgEventTimeMilliseconds - lastGpsMilliseconds;
		long diffHours = diff / (60 * 60 * 1000);
		return (int) diffHours;
	}

	public static long msgEventTimeInMilliseconds(Date msgEventDate) {
		Calendar msgEventTimecal = Calendar.getInstance();
		msgEventTimecal.setTime(msgEventDate);
		long msgEventTimeMilliseconds = msgEventTimecal.getTimeInMillis();
		return msgEventTimeMilliseconds;
	}


}
