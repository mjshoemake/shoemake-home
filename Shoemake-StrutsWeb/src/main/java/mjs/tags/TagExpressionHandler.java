package mjs.tags;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Enumeration;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import mjs.exceptions.CoreException;
import mjs.utils.BeanUtils;
import mjs.utils.StringUtils;

/**
 * This is a utility class which handles operations related to
 * the expression language for core tags.  The expression language
 * is modeled after the one found in JSTL.  This allows us to write
 * custom tags using basically the same expression syntax as that 
 * provided by JSTL.  
 * <p>
 * The core tag expression language supports the following 
 * functionality:
 * <p><pre>
 *     1. Attribute name
 *        Ex.  "attributeName" or "${attributeName}"
 *        Equivalent to:  
 *           session.getAttribute("attributeName")
 * 
 *     2. Properties
 *        Ex.  "${attribute.name}", "${attribute.name.length}"
 *        Equivalent to:  
 *           request.getAttribute("attribute").getName()
 *           request.getAttribute("attribute").getName().getLength()
 * 
 *     3. Logical Comparisons
 *        Ex.  "${length <= 50}", "${name1 == name2}"
 * 
 *     4. When looking for the value for an attribute, it searches
 *        all scopes in this order:
 *          a.  Page scope
 *          b.  Request scope
 *          c.  Session scope
 *          d.  Application scope
 * </pre>
 */
public class TagExpressionHandler
{
   /**
    * The log4j logger to use when writing Perc-specific log messages.
    * The percLogger traces messages to the "Core" category rather
    * than the servlet category. The percLogger should be used for
    * information that is only relevant when debugging Perc itself.
    */
   private static Logger log = Logger.getLogger("Core");
 
   /**
    * The log4j logger to use when writing Perc-specific log messages.
    * The percLogger traces messages to the "Core" category rather
    * than the servlet category. The percLogger should be used for
    * information that is only relevant when debugging Perc itself.
    */
   private static Logger logTags = Logger.getLogger("Tags");
 

   /**
    * Evaluate this expression and return a value.
    * @param page
    * @param expression
    * @return
    */
   public static Object evaluateExpression(PageContext page, String expression)
   {
      try
      {
         boolean parendsFound = false;
         boolean plusFound = false;
         boolean minusFound = false;
         boolean multiplyFound = false;
         boolean divisionFound = false;
         boolean dotFound = false;
         boolean logicalOperatorFound = false;
         
         if (expression.startsWith("${"))
         {
            // Expression.
            logTags.info("Expression: "+expression);
            String strippedExpression = expression.substring(2, expression.length()-1);          
            logTags.debug("Stripped expression: "+strippedExpression);
            
            // Check for parenthesis and act accordingly.
            parendsFound = strippedExpression.indexOf('(') > -1;
            if (parendsFound)
               throw new TagException("Error: The use of parends () is not currently supported in the core tag expression language.");            
            plusFound = strippedExpression.indexOf('+') > -1;
            if (plusFound)
               throw new TagException("Error: The use of a plus sign (+) is not currently supported in the core tag expression language.");            
            minusFound = strippedExpression.indexOf('-') > -1;
            if (minusFound)
               throw new TagException("Error: The use of a minus sign (-) is not currently supported in the core tag expression language.");            
            multiplyFound = strippedExpression.indexOf('*') > -1;
            if (multiplyFound)
               throw new TagException("Error: The use of a multiplication sign (*) is not currently supported in the core tag expression language.");            
            divisionFound = strippedExpression.indexOf('/') > -1;
            if (divisionFound)
               throw new TagException("Error: The use of a division sign (*) is not currently supported in the core tag expression language.");            
            
            logicalOperatorFound = strippedExpression.indexOf('<') > -1 ||
               strippedExpression.indexOf("<=") > -1 ||
               strippedExpression.indexOf('>') > -1 ||
               strippedExpression.indexOf(">=") > -1 ||
               strippedExpression.indexOf("==") > -1 ||
               strippedExpression.indexOf("!=") > -1;
            
            dotFound = strippedExpression.indexOf('.') > -1;
            if (logicalOperatorFound)
            {
               logTags.debug("Logical operator found.  Parsing logical expression...");
               return parseLogical(page, strippedExpression);
            }
            else if (dotFound)
            {
               // Parse the dots to extract the value.
               logTags.debug("Not a logical expression.  Parsing to get value...");
               return parseDots(page, strippedExpression);
            }
            else
            {
               // Expression is name of attribute.
               logTags.debug("Expression is name of attribute ("+strippedExpression+").  Getting value...");
               return page.findAttribute(strippedExpression);          
            }
         }
         else
         {
            // Expression is name of attribute.
            logicalOperatorFound = expression.indexOf('<') > -1 ||
               expression.indexOf("<=") > -1 ||
               expression.indexOf('>') > -1 ||
               expression.indexOf(">=") > -1 ||
               expression.indexOf("==") > -1 ||
               expression.indexOf("!=") > -1;
            dotFound = expression.indexOf('.') > -1;
            
            if (logicalOperatorFound)
               throw new TagException("Expression without ${...} indicates a single attribute and not an expression, however logical operators found.  If logical expression is required, expression must be contained in ${...}.  Expression: "+expression);

            if (dotFound)
               throw new TagException("Expression without ${...} indicates a single attribute and not an expression, however dot (.) operator found.  If an expression is required, the expression must be contained in ${...}.  Expression: "+expression);
            
            logTags.debug("Expression is name of attribute ("+expression+").  Getting value...");
            return page.findAttribute(expression);          
         }
      }
      catch (Exception e)
      {
         log.error("An error occurred evalating expression '" + expression + "' for JSP tag.", e);
         return "ERROR";         
      }
   }

   /**
    * This method is unfinished.  The idea is to parse the expression
    * into tokens (operators, attribute names, etc.) so we can perform
    * arithmetic evaluation of expressions if necessary.  For now, that
    * functionality is not needed, so this can stay unfinished and 
    * unused.  I'm leaving it here to remind me of what the intent was
    * incase arithmetic expressions are needed.  
    * @param expression String
    * @return ArrayList
    */
/*   
   private ArrayList parseExpression(String expression)
   {
      StringBuffer buffer = new StringBuffer();
      ArrayList result = new ArrayList();
      ArrayList stack = new ArrayList();
      ArrayList current = result;
      for (int C=0; C <= expression.length()-1; C++)
      {
         char ch = expression.charAt(C);
         
         // find the tokens (operators and names) and convert
         // the String expression into a tree so we can evaluate
         // properly.
                     
            
      }
      
      return result;
   }
*/
   
   /**
    * Get the attribute value for this attribute.  This method will
    * search all contexts to get the information it needs.  Returns
    * null if no attribute is found for the specified attribute name.
    * @param page PageContext
    * @param attributeName String
    * @return String
    */
   public static Object findAttribute(PageContext page, String attributeName)
   {
      HttpServletRequest req = (HttpServletRequest)page.getRequest();      
      
      Object result = page.getAttribute(attributeName);
      if (result == null)
         result = req.getAttribute(attributeName);
      if (result == null)
         result = req.getSession().getAttribute(attributeName);
      
      return result;
   }
   
   /**
    * Get the attribute value using the specified variable name.
    * @param page PageContext
    * @param var String
    */
   @SuppressWarnings("rawtypes")
   private static Object parseDots(PageContext page, String var) throws TagException
   {
      Method getter = null;
      Class[] classes = new Class[0];
      try
      {
         // Deal with null values.
         if (var.equalsIgnoreCase("null"))
            return null;
         
         // String literal.
         if (var.charAt(0) == '\'')
         {
            if (var.charAt(var.length()-1) == '\'')
            {
               if (logTags.isDebugEnabled())
                  logTags.debug("Found String Literal: "+var.substring(1, var.length()-1));
               return var.substring(1, var.length()-1);   
            }
         }
         
         // Integer literal.
         if (isValidInteger(var))
         {
            if (logTags.isDebugEnabled())
               logTags.debug("Found Integer value: "+var);
            return var;
         }   
            
         // Break the listVar into tokens.  This allows nested properties to be 
         // referenced from the listVar attribute (ie. "UserForm.resultSet").
         String[] tokens = StringUtils.getTokens(var, '.');
         Object[] args = new Object[0];
         Object obj = null;
         HttpServletRequest req = (HttpServletRequest)page.getRequest();

         if (logTags.isDebugEnabled())
         {
            logTags.debug("Tokens:");
            for (int C=0; C <= tokens.length-1; C++)
               logTags.debug(C+": "+tokens[C]);
         }   
         
         // Check all contexts for the attribute.
         int start = 1;
         if (tokens.length > 0)
         {
            if (tokens[0].equalsIgnoreCase("pageScope") && tokens.length > 1)
            {
               obj = page.getAttribute(tokens[1]);
               if (obj == null && logTags.isDebugEnabled())
               {
                  Enumeration enumeration = page.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
                  if (logTags.isDebugEnabled())
                  {
                     logTags.debug("pageScope specified, but attribute "+tokens[1]+" not found.");
                     logTags.debug("Page attributes:");
                     while (enumeration.hasMoreElements())
                        logTags.debug("   "+enumeration.nextElement());
                  }   
               }
               start = 2;
            }
            else if (tokens[0].equalsIgnoreCase("requestScope") && tokens.length > 1)
            {
               obj = req.getAttribute(tokens[1]);
               if (obj == null && logTags.isDebugEnabled())
               {
                  Enumeration enumeration = req.getAttributeNames();
                  if (logTags.isDebugEnabled())
                  {
                     logTags.debug("requestScope specified, but attribute "+tokens[1]+" not found.");
                     logTags.debug("Request attributes:");
                     while (enumeration.hasMoreElements())
                        logTags.debug("   "+enumeration.nextElement());
                  }   
               }
               start = 2;
            }
            else if (tokens[0].equalsIgnoreCase("sessionScope") && tokens.length > 1) 
            {
               obj = req.getSession().getAttribute(tokens[1]);
               if (obj == null && logTags.isDebugEnabled())
               {
                  Enumeration enumeration = req.getSession().getAttributeNames();
                  if (logTags.isDebugEnabled())
                  {                  
                     logTags.debug("sessionScope specified, but attribute "+tokens[1]+" not found.");
                     logTags.debug("Session attributes:");
                     while (enumeration.hasMoreElements())
                        logTags.debug("   "+enumeration.nextElement());
                  }   
               }
               start = 2;
            }

            if (obj == null)
               obj = page.getAttribute(tokens[0]);
            if (obj == null)
               obj = req.getAttribute(tokens[0]);
            if (obj == null)
               obj = req.getSession().getAttribute(tokens[0]);

            // Not found.
            if (obj == null && tokens[0].equalsIgnoreCase("null"))
               return null;
            else if (obj == null)
               return null;
         }

         // Skip the first token as it was processed earlier.
         for (int C=start; C <= tokens.length-1; C++)
         {
            boolean found = false;
            
            try
            {
               getter = obj.getClass().getMethod("get"+(tokens[C].charAt(0)+"").toUpperCase()+tokens[C].substring(1, tokens[C].length()), classes);
               obj = getter.invoke(obj, args);
               found = true;
            }
            catch(Exception e)
            {
               log.error("Unable to find getter method (get...) for property "+tokens[C]+".  Tried "+"get"+(tokens[C].charAt(0)+"").toUpperCase()+tokens[C].substring(1, tokens[C].length()));
            }
            
            if (! found)
            {
               try
               {
                  getter = obj.getClass().getMethod("is"+(tokens[C].charAt(0)+"").toUpperCase()+tokens[C].substring(1, tokens[C].length()), classes);
                  obj = getter.invoke(obj, args);
                  found = true;
               }
               catch(Exception e)
               {
                  log.error("Unable to find getter method (is...) for property "+tokens[C]+".  Tried "+"is"+(tokens[C].charAt(0)+"").toUpperCase()+tokens[C].substring(1, tokens[C].length()));
               }
            }

            if (! found)
            {
               try
               {
                  getter = obj.getClass().getMethod(tokens[C], classes);
                  obj = getter.invoke(obj, args);
                  found = true;
               }
               catch(Exception e)
               {
                  log.error("Unable to find getter method (is...) for property "+tokens[C]+".  Tried "+tokens[C]);
               }
            }

            if (! found)
            {
               PropertyDescriptor[] props = BeanUtils.getPropertyDescriptors(obj.getClass());
               
               if (props == null || props.length == 0)
                  throw new CoreException("Unable to parse expression.  Bean Descriptors are missing for class "+obj.getClass().getName()+".");
                      
               int D=0;
               
               // Search for the property in the property decriptor list.
               while (D <= props.length-1 && ! props[D].getName().equals(tokens[C]))
                  D++;

               if (D > props.length-1)
               {
                  logTags.error("Unable to parse expression.  Property '"+tokens[C]+"' not found for class "+obj.getClass().getName()+".");
                  for (int E=0; E <= props.length-1; E++)
                     logTags.error("   "+E+": "+props[E].getName());
                  throw new CoreException("Unable to parse expression.  Property '"+props[D]+"' not found for class "+obj.getClass().getName()+".");
               }   

               getter = props[D].getReadMethod();
               String className = obj.getClass().getName();
               obj = getter.invoke(obj, args);
               
               if (obj == null)
               {
                  if (logTags.isInfoEnabled())
                     log.info("Expression: '"+var+"':  Token '"+tokens[C]+"' returned null.  Read method = "+getter.getName()+"  Object type: "+className);
                  return null;
               }   
               
               if (logTags.isDebugEnabled())
                  logTags.debug("Token '"+props[D].getName()+":"+"  read method = "+getter.getName()+"  obj="+obj.toString());
            }
         }

         return obj;
      }
      catch (Exception e)
      {
         throw new TagException("Unable to parse expression.", e);
      }
   }
   
   /**
    * Get the value using the specified expression.  This method
    * is intended to deal with logical expressions, so the result
    * should be either "true" or "false".
    * @param page PageContext
    * @param var String
    */
   private static Object parseLogical(PageContext page, String text) throws TagException
   {
      String[] tokens = null;
      String delimiter = null;
      boolean isInteger1 = false;
      boolean isInteger2 = false;
      try
      {
         // Break the listVar into tokens, dividing at the logical
         // operator.
         if (text.indexOf("<=") > -1)
         {
            tokens = StringUtils.getTokens(text, "<=");
            delimiter = "<=";
         }
         else if (text.indexOf(">=") > -1)
         {
            tokens = StringUtils.getTokens(text, ">=");
            delimiter = ">=";
         }
         else if (text.indexOf("<") > -1)
         {
            tokens = StringUtils.getTokens(text, "<");
            delimiter = "<";
         }
         else if (text.indexOf(">") > -1)
         {
            tokens = StringUtils.getTokens(text, ">");
            delimiter = ">";
         }
         else if (text.indexOf("!=") > -1)
         {
            tokens = StringUtils.getTokens(text, "!=");
            delimiter = "!=";
         }
         else if (text.indexOf("==") > -1)
         {
            tokens = StringUtils.getTokens(text, "==");
            delimiter = "==";
         }

         if (tokens.length > 2)
            throw new TagException("Error: Only one operator (==, !=, etc.) can be present in a logical expression.");
         
         if (tokens[0] == null || tokens[0].equals("") ||
             tokens[1] == null || tokens[1].equals(""))  
            throw new TagException("Error: At least one side of the logical expression is missing ("+text+").");

         Object obj1 = parseDots(page, tokens[0]);
         Object obj2 = parseDots(page, tokens[1]);

         // See if obj1 can convert to an int cleanly.
         if (obj1 instanceof String)
         {
            try
            {
               Integer.parseInt(obj1.toString());
               isInteger1 = true; 
            }
            catch (Exception e) {}
         }

         // See if obj2 can convert to an int cleanly.
         if (obj2 instanceof String)
         {
            try
            {
               Integer.parseInt(obj2.toString());
               isInteger2 = true; 
            }
            catch (Exception e) {}
         }

         logTags.debug("Phase 1:  obj1="+obj1+"  obj2="+obj2);
         boolean result = false;
         
         if (obj2 == null)
         {
            logTags.debug("   Path A:  obj2 == null.  obj1: "+obj1);
            if (delimiter.equals("<=") ||
                delimiter.equals(">=") ||
                delimiter.equals("<") ||
                delimiter.equals(">"))
               result = false;
            else if (delimiter.equals("=="))
               result = obj1 == null;
            else if (delimiter.equals("!="))
               result = obj1 != null;
         }
         else if (obj1 == null)
         {
            logTags.debug("   Path B:  obj1 == null.  obj2: "+obj2);
            if (delimiter.equals("<=") ||
                delimiter.equals(">=") ||
                delimiter.equals("<") ||
                delimiter.equals(">"))
               result = false;
            else if (delimiter.equals("=="))
               result = obj2 == null;
            else if (delimiter.equals("!="))
               result = obj2 != null;
         }
         else if (obj1 instanceof Integer && obj2 instanceof Integer)
         {
            // Compare integers.
            log.debug("   Path C:  both integers");
            Integer int1 = (Integer)obj1;
            Integer int2 = (Integer)obj2;
            result = compareIntegers(int1.intValue(), int2.intValue(), delimiter);
         }
         else if (obj1 instanceof Integer && obj2 instanceof String && isInteger2)
         {
            // Convert obj2 to integer and compare integers.
            log.debug("   Path D:  obj1: integer, obj2: String");
            Integer int1 = (Integer)obj1;
            int int2 = Integer.parseInt(obj2.toString());
            result = compareIntegers(int1.intValue(), int2, delimiter);
         }
         else if (obj1 instanceof String && obj2 instanceof Integer && isInteger1)
         {
            // Convert obj1 to integer and compare integers.
            log.debug("   Path E:  obj1: String, obj2: integer");
            int int1 = Integer.parseInt(obj1.toString());
            Integer int2 = (Integer)obj2;
            result = compareIntegers(int1, int2.intValue(), delimiter);
         }
         else if (obj1 instanceof Float && obj2 instanceof Float)
         {
            // Compare floats.
            log.debug("   Path F:  both floats");
            Float f1 = (Float)obj1;
            Float f2 = (Float)obj2;
            if (delimiter.equals("<="))
               result = f1.floatValue() <= f2.floatValue();
            else if (delimiter.equals(">="))
               result = f1.floatValue() >= f2.floatValue();
            else if (delimiter.equals("<"))
               result = f1.floatValue() < f2.floatValue();
            else if (delimiter.equals(">"))
               result = f1.floatValue() > f2.floatValue();
            else if (delimiter.equals("=="))
               result = f1.floatValue() == f2.floatValue();
            else if (delimiter.equals("!="))
               result = f1.floatValue() != f2.floatValue();
         }
         else if (obj1 instanceof Double && obj2 instanceof Double)
         {
            // Compare doubles.
            log.debug("   Path G:  both double");
            Double d1 = (Double)obj1;
            Double d2 = (Double)obj2;
            if (delimiter.equals("<="))
               result = d1.doubleValue() <= d2.doubleValue();
            else if (delimiter.equals(">="))
               result = d1.doubleValue() >= d2.doubleValue();
            else if (delimiter.equals("<"))
               result = d1.doubleValue() < d2.doubleValue();
            else if (delimiter.equals(">"))
               result = d1.doubleValue() > d2.doubleValue();
            else if (delimiter.equals("=="))
               result = d1.doubleValue() == d2.doubleValue();
            else if (delimiter.equals("!="))
               result = d1.doubleValue() != d2.doubleValue();
         }
         else if (obj1 instanceof BigDecimal && obj2 instanceof BigDecimal)
         {
            log.debug("   Path H:  both BigDecimal");
            BigDecimal d1 = (BigDecimal)obj1;
            BigDecimal d2 = (BigDecimal)obj2;
            if (delimiter.equals("<="))
               result = d1.compareTo(d2) <= 0;
            else if (delimiter.equals(">="))
               result = d1.compareTo(d2) >= 0;
            else if (delimiter.equals("<"))
               result = d1.compareTo(d2) < 0;
            else if (delimiter.equals(">"))
               result = d1.compareTo(d2) > 0;
            else if (delimiter.equals("=="))
               result = d1.compareTo(d2) == 0;
            else if (delimiter.equals("!="))
               result = d1.compareTo(d2) != 0;
         }
         else if (obj1 instanceof String && obj2 instanceof String)
         {
            // Compare String objects.
            log.debug("   Path I:  both String");
            String s1 = (String)obj1;
            String s2 = (String)obj2;
            if (delimiter.equals("<="))
               result = s1.compareTo(s2) <= 0;
            else if (delimiter.equals(">="))
               result = s1.compareTo(s2) >= 0;
            else if (delimiter.equals("<"))
               result = s1.compareTo(s2) < 0;
            else if (delimiter.equals(">"))
               result = s1.compareTo(s2) > 0;
            else if (delimiter.equals("!="))
               result = s1.compareTo(s2) != 0;
            else if (delimiter.equals("=="))
               result = s1.compareTo(s2) == 0;
         }
         else if (! obj1.getClass().equals(obj2.getClass()))
            throw new TagException("Error: Types must be the same for logical comparison: "+obj1.getClass().getName()+" and "+obj2.getClass().getName()+".");
         else   
            throw new TagException("Unsupported types for logical expression: "+obj1.getClass().getName()+" and "+obj2.getClass().getName()+".");
         
         if (result)
            return "true";
         else
            return "false";
      }
      catch (TagException e)
      {
         throw e;
      }
      catch (Exception e)
      {
         throw new TagException("Unable to parse expression.", e);
      }
   }

   /**
    * Compare these two integers based on the delimiter specified ("<=",
    * ">=", "!=", "==", "<", ">").  Return true or false.
    * @param value1 int
    * @param value2 int
    * @param delimiter String
    * @return boolean
    * @throws TagException
    */
   private static boolean compareIntegers(int value1, int value2, String delimiter) throws TagException
   {
      if (delimiter.equals("=="))
         return value1 == value2;
      else if (delimiter.equals("!="))
         return value1 != value2;
      else if (delimiter.equals("<="))
         return value1 <= value2;
      else if (delimiter.equals(">="))
         return value1 >= value2;
      else if (delimiter.equals("<"))
         return value1 < value2;
      else if (delimiter.equals(">"))
         return value1 > value2;
      else
         throw new TagException("Error: Invalid comparison operator ("+delimiter+").");
   }
   
   /**
    * If the input parameter can be parsed successfully into an int, 
    * return true.  Otherwise, false.
    * @param value String
    * @return boolean
    */
   private static boolean isValidInteger(String value)
   {
      try
      {
         Integer.parseInt(value);
         return true;
      }
      catch (Exception e)
      {
         return false;
      }
   }
}
