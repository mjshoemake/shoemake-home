//
// file: DefaultTextAreaDocument
// desc: This class limits the text that can be entered in a TextField.
//
// proj: ER 6.0
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/models/WSTextDocument.java-arc  $
// $Author:   MBirdsong  $
// $Revision:   1.6  $
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.models;

// Import java classes
import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * This class is used to limit the characters entered in TextField object to the
 * characters in the Acceptable Characters List or all characters. If the
 * Acceptable Characters List is empty, all characters are allowed. If not, all
 * other characters are disguarded. <p>
 *
 * It also enforces a maximum length of the text.
 *
 * @author    Mark Patsy
 * @created   December 18, 2002
 * @since     JDK1.2
 */
public class DefaultTextAreaDocument extends PlainDocument
{
   /**
    * Pound Sign character Constant
    */
   private final char    POUNDSIGN = '#';

   /**
    * Zero character Constant
    */
   private final char    ZEROCHAR = '0';

   /**
    * Nine character Constant
    */
   private final char    NINECHAR = '9';

   /**
    * Empty String Constant
    */
   private final String  EMPTYSTRING = "";

   /**
    * The maximum number of characters allowed.
    */
   private int           theMaxLength;

   /**
    * A String of acceptable character allowed
    */
   private String        theAcceptableChars = EMPTYSTRING;

   /**
    * Holds the newly created string based on the new character added
    */
   private StringBuffer  theBuffer = new StringBuffer();


   /**
    * Constructor used to build a Text Document.
    *
    * @param maxLength  The max number of characters allowed in the field.
    */
   public DefaultTextAreaDocument(int maxLength)
   {
      theMaxLength = maxLength;
   }


   /**
    * Constructor used to build a Text Document.
    *
    * @param acceptableChars  A String of the only characters that are allowed.
    * @param maxLength        The max number of characters allowed in the field.
    */
   public DefaultTextAreaDocument(String acceptableChars, int maxLength)
   {
      theAcceptableChars = acceptableChars;
      theMaxLength = maxLength;
   }


   /**
    * Handles Textfield editing. All characters are tested to make sure that
    * they are in a acceptable list of character or all character. All other
    * characters are disguarded.
    *
    * @param offset                 Position in text string to add character.
    * @param str                    Current string in the TextField.
    * @param as                     AttributeSet that contains data about how to
    *      display the test.
    * @throws BadLocationException  Thrown if the index for the new location is
    *      not valid.
    */
   public void insertString(int offset, String str, AttributeSet as) throws BadLocationException
   {
      for (int i = 0; i < str.length(); i++)
      {
         if (getLength() < theMaxLength)
         {
            char chin = str.charAt(i);
            boolean valid = false;

            if (theAcceptableChars == EMPTYSTRING)
            {
               valid = true;
            }
            else
            {
               for (int f = 0; f < theAcceptableChars.length(); f++)
               {
                  char chcheck = theAcceptableChars.charAt(f);

                  switch (chcheck)
                  {
                     case POUNDSIGN:
                        if (chin >= ZEROCHAR && chin <= NINECHAR)
                        {
                           valid = true;
                        }
                        break;
                     default:
                        if (chin == chcheck)
                        {
                           valid = true;
                        }
                        break;
                  }
               }
            }

            if (valid)
            {
               theBuffer.insert(0, chin);
               theBuffer.setLength(1);
               super.insertString(offset + i, theBuffer.toString(), as);
            }
            else
            {// Not an acceptable charecter

               Toolkit.getDefaultToolkit().beep();
            }
         }
         else
         {// Document exceeds specified length

            Toolkit.getDefaultToolkit().beep();
            break;
         }
      }
   }
}

// $Log:   N:/REPOS/Archives/eQuality/web/java/com/witsys/models/WSTextDocument.java-arc  $
//
//   Rev 1.6   Jul 30 2002 14:32:12   MBirdsong
//Added JavaDoc Comments.
//
//   Rev 1.5   Mar 28 2001 14:49:30   ilinsdell
//Add Date mask for input validation
//
//   Rev 1.4   Jul 19 2000 10:10:54   dkomarlu
//Modified insertString method to beep if the document length exceeds the max length.
//
//   Rev 1.3   Mar 20 2000 15:49:50   TDensmore
//Added PVCS tags
