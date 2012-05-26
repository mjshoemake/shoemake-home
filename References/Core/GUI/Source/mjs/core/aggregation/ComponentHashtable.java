//
// file: WSComponentHashtable.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/aggregation/ComponentHashtable.java-arc  $
// $Author:Mike Shoemake$
// $Revision:7$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.aggregation;

// Java imports
import java.util.Enumeration;
import java.util.Collection;
// Witness imports
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;
import mjs.core.components.Component;


/**
 * WSComponentHashtable:  This is a hashtable implementation that accepts
 * int, long, and object key values.  It only accepts objects that implement the WSComponent interface.
 */
public class ComponentHashtable
{
   // Key Types

   /**
    * No items have been added to the hashtable.
    */
   public static final int NO_KEY = -1;

   /**
    * All keys are int values.
    */
   public static final int INT_KEY = 0;

   /**
    * All keys are long values.
    */
   public static final int LONG_KEY = 1;

   /**
    * All keys are Strings.
    */
   public static final int STRING_KEY = 2;

   /**
    * All keys are Objects.
    */
   public static final int OBJECT_KEY = 3;
   // ****** Default Values ******

   /**
    * The type of key currently accepted by the hashtable.  This is set when
    * the first item is added to the hashtable.  Whatever type that is (int,
    * long, etc.) will become the key type for the hashtable.  All subsequent
    * items will need to be of the same type as the first.
    */
   private int nKeyType = -1;

   /**
    * The hashtable used internally to store the items.
    */
   private java.util.Hashtable htList = new java.util.Hashtable();

   /**
    * Constructor.
    */
   public ComponentHashtable()
   {
   }
   // ****** Functionality Methods ******

   /**
    * Add items to the hashtable.
    * @param  nIntKey   The key value.
    * @param  obj       The component to add to the hashtable.
    */
   public void add(int nIntKey, Component obj)
   {
      if (nKeyType == -1)
      { nKeyType = INT_KEY; }
      if (nKeyType == INT_KEY)
      {
         // Int Key.  Continue processing
         put("" + nIntKey, obj);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10412,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
      }
   }

   /**
    * Add items to the hashtable.
    * @param  lLongKey  The key value.
    * @param  obj       The component to add to the hashtable.
    */
   public void add(long lLongKey, Component obj)
   {
      if (nKeyType == -1)
      { nKeyType = LONG_KEY; }
      if (nKeyType == LONG_KEY)
      {
         // Long Key.  Continue processing
         put("" + lLongKey, obj);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10412,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
      }
   }

   /**
    * Add items to the hashtable.
    * @param  sKey      The key value.
    * @param  obj       The component to add to the hashtable.
    */
   public void add(String sKey, Component obj)
   {
      if (nKeyType == -1)
      { nKeyType = STRING_KEY; }
      if (nKeyType == STRING_KEY)
      {
         // String Key.  Continue processing
         put(sKey, obj);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10412,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
      }
   }

   /**
    * Add items to the hashtable.
    * @param  oKey      The key value.
    * @param  obj       The component to add to the hashtable.
    */
   public void add(Object oKey, Component obj)
   {
      if (nKeyType == -1)
      { nKeyType = OBJECT_KEY; }
      if (nKeyType == OBJECT_KEY)
      {
         // Object Key.  Continue processing
         put(oKey, obj);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10412,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
      }
   }

   /**
    * Get items from the hashtable based on the key.
    * @param  nIntKey   The key value.
    */
   public Component get(int nIntKey)
   {
      if ((nKeyType == -1) || (nKeyType == INT_KEY))
      {
         // Int Key.  Continue processing
         return (Component)htList.get("" + nIntKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Get items from the hashtable based on the key.
    * @param  lLongKey   The key value.
    */
   public Component get(long lLongKey)
   {
      if ((nKeyType == -1) || (nKeyType == LONG_KEY))
      {
         // Long Key.  Continue processing
         return (Component)htList.get("" + lLongKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Get items from the hashtable based on the key.
    * @param  sKey   The key value.
    */
   public Component get(String sKey)
   {
      if ((nKeyType == -1) || (nKeyType == STRING_KEY))
      {
         // String Key.  Continue processing
         return (Component)htList.get(sKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Get items from the hashtable based on the key.
    * @param  oKey   The key value.
    */
   public Component get(Object oKey)
   {
      if ((nKeyType == -1) || (nKeyType == OBJECT_KEY))
      {
         // Object Key.  Continue processing
         return (Component)htList.get(oKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Does the hashtable contain this key?
    * @param  nIntKey   The key value.
    */
   public boolean containsKey(int nIntKey)
   {
      if ((nKeyType == -1) || (nKeyType == INT_KEY))
      {
         // Int Key.  Continue processing
         return htList.containsKey("" + nIntKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return false;
      }
   }

   /**
    * Does the hashtable contain this key?
    * @param  lLongKey   The key value.
    */
   public boolean containsKey(long lLongKey)
   {
      if ((nKeyType == -1) || (nKeyType == LONG_KEY))
      {
         // Long Key.  Continue processing
         return htList.containsKey("" + lLongKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return false;
      }
   }

   /**
    * Does the hashtable contain this key?
    * @param  sKey   The key value.
    */
   public boolean containsKey(String sKey)
   {
      if ((nKeyType == -1) || (nKeyType == STRING_KEY))
      {
         // String Key.  Continue processing
         return htList.containsKey(sKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return false;
      }
   }

   /**
    * Does the hashtable contain this key?
    * @param  oKey   The key value.
    */
   public boolean containsKey(Object oKey)
   {
      if ((nKeyType == -1) || (nKeyType == OBJECT_KEY))
      {
         // Object Key.  Continue processing
         return htList.containsKey(oKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return false;
      }
   }

   /**
    * Remove object that corresponds to this key.
    * @param  nIntKey   The key value.
    */
   public Component remove(int nIntKey)
   {
      if ((nKeyType == -1) || (nKeyType == INT_KEY))
      {
         // Int Key.  Continue processing
         return (Component)htList.remove("" + nIntKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Remove object that corresponds to this key.
    * @param  lLongKey   The key value.
    */
   public Component remove(long lLongKey)
   {
      if ((nKeyType == -1) || (nKeyType == LONG_KEY))
      {
         // Long Key.  Continue processing
         return (Component)htList.remove("" + lLongKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Remove object that corresponds to this key.
    * @param  sKey   The key value.
    */
   public Component remove(String sKey)
   {
      if ((nKeyType == -1) || (nKeyType == STRING_KEY))
      {
         // String Key.  Continue processing
         return (Component)htList.remove(sKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Remove object that corresponds to this key.
    * @param  oKey   The key value.
    */
   public Component remove(Object oKey)
   {
      if ((nKeyType == -1) || (nKeyType == OBJECT_KEY))
      {
         // Object Key.  Continue processing
         return (Component)htList.remove(oKey);
      }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10414,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
         return null;
      }
   }

   /**
    * Add this component and key pair to the hashtable.
    * @param  oKey   The key value.
    * @param  obj    The object.
    */
   private Object put(Object oKey, Component obj)
   {
      // Is this a duplicate key?
      if (htList.containsKey(oKey))
      {
         // Key already exists.  Raise exception.
         // Create message object
         Message message = MessageFactory.createMessage( 10411,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSComponentHashtable" );
      }

      /* Add to the list (whether duplicate or not).
         If duplicate, the new value will replace the old one.  */

      return htList.put(oKey, obj);
   }

   /**
    * Clear the hashtable.
    */
   public void clear()
   {
      htList.clear();
   }

   /**
    * Get the list of objects as an Enumeration.
    */
   public Enumeration elements()
   {
      return htList.elements();
   }

   /**
    * Is this hashtable empty?
    */
   public boolean isEmpty()
   {
      return htList.isEmpty();
   }

   /**
    * The list of keys for the hashtable as an enumeration.
    */
   public Enumeration keys()
   {
      return htList.keys();
   }

   /**
    * The number of elements in the hashtable.
    */
   public int size()
   {
      return htList.size();
   }

   /**
    * The list of objects as an Enumeration.
    */
   public Collection values()
   {
      return htList.values();
   }

   // TOGETHER DIAGRAM DEPENDENCIES.
   // PLEASE DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# MessageDialogHandler lnkMessageDialogHandler; */

   /**
    * @link dependency
    */
   /*# InternationalizationStrings lnkInternationalizationStrings; */

}
// $Log:
//  7    Balance   1.6         6/6/2003 8:40:18 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  6    Balance   1.5         3/18/2003 1:24:03 PM   Mike Shoemake   Added
//       Together diagram links.
//  5    Balance   1.4         3/7/2003 9:15:55 AM    Jeff Giesler    Fixed
//       rerference problem.
//  4    Balance   1.3         1/29/2003 4:47:11 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  3    Balance   1.2         1/17/2003 8:50:39 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  2    Balance   1.1         10/11/2002 8:54:08 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:44:00 PM   Mike Shoemake
// $

//
//     Rev 1.1   Oct 11 2002 08:54:08   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:44:00   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:25:22   mshoemake
//  Initial revision.
//
//   Rev 1.1   Dec 07 2001 09:07:28   mshoemake
//Update to WSExceptionFactory class.  Changed throwException() to handleException().


