package mjs.view;

import java.util.ArrayList;


/**
 * An ActionLink is an object representation of a link to an action
 * from a JSP page. It contains the action name and the display
 * caption. This can be used for hyperlinks, menu items, and other
 * types of navigation.
 */
@SuppressWarnings("rawtypes")
public class ActionLink
{
   /**
    * The action to execute when the link is activated.
    */
   private String action = null;

   /**
    * The text to display to the user for this link.
    */
   private String caption = null;

   /**
    * Whether or not this menu option should stay in the menu even
    * after clear() is called.
    */
   private boolean permanent = false;

   /**
    * The list of submenus available from this menu.
    */
   private ArrayList submenus = new ArrayList();

   /**
    * Constructor.
    *
    * @param action
    * @param caption
    * @param permanent
    */
   public ActionLink(String action, String caption, boolean permanent)
   {
      this.action = action;
      this.caption = caption;
      this.permanent = permanent;
   }

   /**
    * Constructor.
    *
    * @param action   The name of the action to execute.
    * @param caption  The caption text to display.
    */
   public ActionLink(String action, String caption)
   {

      this.action = action;
      this.caption = caption;
      this.permanent = false;
   }


   /**
    * The action to execute when the link is activated.
    *
    * @return   The value of the Action property.
    */
   public String getAction()
   {
      return action;
   }

   /**
    * The action to execute when the link is activated.
    *
    * @param value  The new Action value.
    */
   public void setAction(String value)
   {
      action = value;
   }

   /**
    * The text to display to the user for this link.
    *
    * @return   The value of the Caption property.
    */
   public String getCaption()
   {
      return caption;
   }

   /**
    * The text to display to the user for this link.
    *
    * @param value  The new Caption value.
    */
   public void setCaption(String value)
   {
      caption = value;
   }

   /**
    * Whether or not this menu option should stay in the menu even
    * after clear() is called.
    *
    * @return   The value of the Permanent property.
    */
   public boolean isPermanent()
   {
      return permanent;
   }

   /**
    * Whether or not this menu option should stay in the menu even
    * after clear() is called.
    *
    * @param value  The new Permanent value.
    */
   public void setPermanent(boolean value)
   {
      permanent = value;
   }

   /**
    * The list of submenus available from this menu.
    *
    * @return   The value of the SubMenus property.
    */
   public ArrayList getSubMenus()
   {
      return submenus;
   }

   /**
    * The list of submenus available from this menu.
    *
    * @param list  The new SubMenus value.
    */
   public void setSubMenus(ArrayList list)
   {
      submenus = list;
   }

   /**
    * Add this menu item to the child list.
    *
    * @param item
    */
   @SuppressWarnings("unchecked")
   public void add(ActionLink item)
   {
      submenus.add(item);
   }

}
