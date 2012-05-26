//
// file: ComponentUtils.java
// desc:
// proj: ER 6.1
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/utils/ComponentUtils.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.utils;

// Core Java Imports

import java.util.Locale;

// Witness System Imports

/**
 * This class contains getLocale() static method.
 */
public class ComponentUtils
{

   /**
    * Creates and returns the Locale for the system.
    * @return     The system Locale.
    */
   public static Locale getLocale()
   {
      String region;
      String JREVersion = System.getProperty( "java.version" );
      String language = System.getProperty( "user.language" );
      if ( JREVersion.regionMatches( true, 0, "1.4", 0, 3 ) )
      {
         region = System.getProperty( "user.country" );
      }
      else
      {
         region = System.getProperty( "user.region" );
      }
      Locale locale = new Locale( language, region );
      MessageLog.Debug("SocketManager:: initializeLocale() - Locale = " + locale);
      return locale;
   }

   // TOGETHERSOFT DEPENDENCY.  DO NOT REMOVE.
   /**
    * @link dependency
    */
   /*# MessageLog lnkMessageLog; */
}
// $Log:
//  2    Balance   1.1         3/28/2003 2:48:14 PM   Mike Shoemake   Added
//       Together diagram dependencies.
//  1    Balance   1.0         1/28/2003 1:51:42 PM   Helen Faynzilberg
// $
