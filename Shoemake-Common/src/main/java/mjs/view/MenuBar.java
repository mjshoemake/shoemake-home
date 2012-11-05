package mjs.view;

import java.util.ArrayList;


/**
 * A MenuBar contains ActionLink objects and is used to create a
 * navigation menu on a JSP page.
 */
public class MenuBar
{
   /**
    * The list of menu items available from this menu bar.
    */
   private ArrayList<ActionLink> menuitems = new ArrayList<ActionLink>();

   /**
    * Constructor.
    *
    * @param items
    */
   public MenuBar(ArrayList<ActionLink> items)
   {
      menuitems = items;
   }

   /**
    * Constructor.
    */
   public MenuBar()
   {
   }


   /**
    * The list of menu items available from this menu bar.
    *
    * @return   The value of the MenuItems property.
    */
   public ArrayList<ActionLink> getMenuItems()
   {
      return menuitems;
   }

   /**
    * Add the list of menu items in the specified list to the list of
    * items for this menu.
    *
    * @param items  Description of Parameter
    */
   public void append(ArrayList<ActionLink> items)
   {
      for (int C = 0; C <= items.size() - 1; C++)
         menuitems.add(items.get(C));
   }

   /**
    * Clear the contents of the menu. Only menu items that are NOT
    * marked as permanent will be removed.
    */
   public void clear()
   {
      for (int C = menuitems.size() - 1; C >= 0; C--)
      {
         ActionLink item = (ActionLink)menuitems.get(C);

         if (! item.isPermanent())
            menuitems.remove(C);
      }
   }

   /**
    * Add this menu item to the child list.
    *
    * @param item
    */
   public void add(ActionLink item)
   {
      menuitems.add(item);
   }
}
