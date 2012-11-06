package mjs.mocks;

import javax.servlet.jsp.JspWriter;
import org.apache.log4j.Logger;


/**
 * A mock version of the JspWriter abstract class.
 */
public class MockJspWriter extends JspWriter
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Test". See the public methods
    * debug(), info(), etc.
    */
   private Logger log = Logger.getLogger("Test");

   /**
    * Constructor.
    */
   public MockJspWriter()
   {
      super(0, true);
   }

   public void println(long value)
   {
   }

   public void println(int value)
   {
   }

   public void println(boolean value)
   {
   }

   public void println(double value)
   {
   }

   public void println(float value)
   {
   }

   public void println(String value)
   {
   }

   public void println(Object value)
   {
   }

   public void println(char[] value)
   {
   }

   public void println(char value)
   {
   }

   public void println()
   {
   }

   public void print(boolean value)
   {
   }

   public void print(int value)
   {
   }

   public void print(long value)
   {
   }

   public void print(double value)
   {
   }

   public void print(float value)
   {
   }

   public void print(String value)
   {
   }

   public void print(Object value)
   {
   }

   public void print(char[] value)
   {
   }

   public void print(char value)
   {
   }

   public void write(char[] v1, int v2, int v3)
   {
   }

   public void newLine()
   {
   }

   public void clear()
   {
   }

   public void clearBuffer()
   {
   }

   public void flush()
   {
   }

   public void close()
   {
   }

   public int getRemaining()
   {
      return 0;
   }
}
