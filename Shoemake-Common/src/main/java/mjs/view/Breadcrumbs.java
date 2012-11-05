package mjs.view;

import java.util.ArrayList;
import org.apache.log4j.Logger;


/**
 * A Stack implementation (first in, first out) that stores the
 * breadcrumb links. Each link is stored as an ActionLink object with
 * a caption and an ActionForward definition.
 */
@SuppressWarnings("rawtypes")
public class Breadcrumbs
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Model".
    */
   protected Logger log = Logger.getLogger("Core");

   /**
    * The list of menu items available from this menu bar.
    */
   private ArrayList<ActionLink> items = new ArrayList<ActionLink>();

   /**
    * Constructor.
    *
    * @param items
    */
   public Breadcrumbs(ArrayList<ActionLink> items)
   {
      this.items = items;
   }

   /**
    * Constructor.
    */
   public Breadcrumbs()
   {
   }


   /**
    * The list of menu items available from this menu bar.
    *
    * @return   The value of the Links property.
    */
   public ArrayList getLinks()
   {
      return (ArrayList)items.clone();
   }

   /**
    * Clear the contents of the menu. Only menu items that are NOT
    * marked as permanent will be removed.
    */
   public void clear()
   {
      items.clear();
   }

   /**
    * Add this link to the end of the breadcrumb list.
    *
    * @param caption  String
    * @param action   String
    */
   public void add(String caption, String action)
   {
      log.debug("Adding breadcrumb: " + caption + " / " + action);
      ActionLink link = new ActionLink(action, caption);
      if (action == null) {
         log.error("Attempt to add caption '" + caption + "' and action '" + action + "' to breadcrumbs list failed.  Action is null.");
         return;
      }
      if (caption == null) {
         log.error("Attempt to add caption '" + caption + "' and action '" + action + "' to breadcrumbs list failed.  Action is null.");
         return;
      }
      int foundAt = -1;
      for (int i=0; i <= items.size()-1; i++) {
         // Find action in list if exists.
         ActionLink nextLink = (ActionLink)items.get(i);
         if (action.equals(nextLink.getAction())) {
            // Found.  Remove.
            log.debug("   Found!  '" + action + "' = nextLink.getAction() -> '" + nextLink.getAction() + "'");
            foundAt = i;
            break;
         }
      }
      if (foundAt > -1) {
         for (int i=items.size()-1; i >= 0; i--) {
            // Find action in list if exists.
            //ActionLink nextLink = (ActionLink)items.get(i);
            if (i >= foundAt) {
               items.remove(i); 
               log.debug("   Removing: " + i);
            }
         }
      }
      log.debug("Items.size() = " + items.size());
      items.add(link);
      traceContents();
   }

   /**
    * Remove the current (most recent) item in the list.
    */
   public void removeCurrent()
   {
      items.remove(items.size() - 1);
      traceContents();
   }

   /**
    * Get the current (most recent) item in the list.
    *
    * @return   The value of the Current property.
    */
   public ActionLink getCurrent()
   {
      return (ActionLink)items.get(items.size() - 1);
   }

   /**
    * Remove the current (most recent) item in the list.
    *
    * @param caption  Description of Parameter
    * @param action   Description of Parameter
    */
   public void replaceCurrent(String caption, String action)
   {
      items.remove(items.size() - 1);

      ActionLink link = new ActionLink(action, caption);

      items.add(link);
      traceContents();
   }

   /**
    * Trace out the contents of this breadcrumb list.
    */
   private void traceContents()
   {
      log.debug("Breadcrumbs:");
      for (int C = 0; C <= items.size() - 1; C++)
      {
         ActionLink link = (ActionLink)items.get(C);

         log.debug("   " + C + ": caption=" + link.getCaption() + "  action=" + link.getAction());
      }
   }
}
