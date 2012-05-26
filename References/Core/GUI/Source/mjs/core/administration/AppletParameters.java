//
// file: WSAppletParams.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/administration/AppletParameters.java-arc  $
// $Author:Mike Shoemake$
// $Revision:4$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.administration;

// Java imports
import java.util.*;
// Witness imports
import mjs.core.components.Applet;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;

/**
 * WSAppletParams:  This class is intended to get a list of expected
 * parameter names from the WSApplet, retrieve the values for each
 * parameter, and give the rest of the applet static access to the parameter values.
 */
public class AppletParameters
{
   /**
    * The hashtable list of parameter values with the parameter names as keys.
    */
   private static Hashtable htParams = new Hashtable();

   /**
    * The applet reference.
    */
   private static Applet m_Applet = null;

   /**
    * Constructor.
    * @param  applet   The applet to which the params apply.
    */
   public AppletParameters(Applet applet)
   {
      // Save reference to applet
      m_Applet = applet;

      // Get the parameter info from the applet.
      Vector vctParamNames = applet.getParamList();
      //checked for null pointer
      if (vctParamNames == null)
      {
         for (int C = 0; C <= vctParamNames.size() - 1; C++)
         {
            // Get the next parameter and it's value.
            String sParam = (String)(vctParamNames.get(C));
            String sValue = applet.getParameter(sParam);
            try
            {
               // Add this parameter to the params hashtable.
               htParams.put(sParam, sValue);
            }
            catch (Exception e)
            {
               // Throw exception.
               // Create message object
               Message message = MessageFactory.createMessage( 10418,
                                                              "Balance",
                                                              "Evaluations",
                                                              "",
                                                              Message.INTERNAL,
                                                              InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                              applet.getLocale(),
                                                              new String[]{ sParam } );

               // Display message
               MessageDialogHandler.showMessage( message, applet, "AppletParams" );
            }
         }
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10420,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        applet.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, applet, "AppletParams" );
      }
   }

   /**
    * Get this param value.
    * @param  sParamName   The name associated with the param.  This will be the lookup value for the param hashtable.
    */
   public static String getParam(String sParamName)
   {
      if (htParams.containsKey(sParamName))
      {
         // Key found.
         return (String)(htParams.get(sParamName));
      }
      else
      {
         // Key not found.  Return NULL instead.
         return null;
      }
   }

   /**
    * Get this param value.  Convert the format and return as an int instead of a String.
    * @param  sParamName   The name associated with the param.  This will be the lookup value for the param hashtable.
    */
   public static int getParam_Int(String sParamName)
   {
      String sValue = null;
      Integer iValue = null;
      if (htParams.containsKey(sParamName))
      {
         // Key found.
         sValue = (String)(htParams.get(sParamName));
         try
         {
            iValue = new Integer(sValue);
            return iValue.intValue();
         }
         catch (Exception e)
         {
            // Create message object
            Message message = MessageFactory.createMessage( 10410,
                                                           "Balance",
                                                           "Evaluations",
                                                           e.getMessage(),
                                                           Message.INTERNAL,
                                                           InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                           m_Applet.getLocale() );

            // Display message
            MessageDialogHandler.showMessage( message, m_Applet, "AppletParams" );
            return -1;
         }
      }
      else
      {
         // Key not found.  Return -1 instead.
         return -1;
      }
   }
}
