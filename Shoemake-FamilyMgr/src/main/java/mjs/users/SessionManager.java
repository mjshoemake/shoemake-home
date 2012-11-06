package mjs.users;

import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SessionManager {

   /**
    * The log4j logger to use when writing log messages.
    */
   protected static final Logger log = Logger.getLogger("Servlet");

   /**
    * Singleton instance.
    */
   private static SessionManager instance = null;

   private static HashMap<String, HttpSession> userInfo = new HashMap<String, HttpSession>();

   /**
    * Singleton instance.
    * @return SessionManager
    */
   public static SessionManager getInstance() {
      if (instance == null) {
         instance = new SessionManager();
         return instance;
      } else {
         return instance;
      }
   }

   
   //public HashMap<String, HttpSession> getMap() {
   //   return this.userInfo;
   //}

   public HttpSession getUserSession(String username) {
      HttpSession prevSession = null;
      if (userInfo.containsKey(username)) {
         prevSession = userInfo.get(username);
      }
      return prevSession;
   }

   public boolean invalidatePrevSession(String username, HttpSession newSession) {
      HttpSession prevSession = null;
      prevSession = getUserSession(username);
      if (prevSession != null) {
         if (!prevSession.getId().equals(newSession.getId())) {
            try {
               prevSession.invalidate();
            }
            catch (IllegalStateException e) {

               log.debug("The session has already been invalidated");
            }
            userInfo.put(username, newSession);
         }
         return true;
      } else {
         userInfo.put(username, newSession);
         return false;
      }
   }
}
