/* File name:     GetUserListForm.java
 * Package name:  mjs.home.struts.actions
 * Project name:  Home - Struts
 * Date Created:  Aug 13, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.home.struts.actions;

// Java imports
import java.util.Vector;
import org.apache.struts.action.ActionForm;

/**
 * This class is the server-side temporary storage for 
 * the information entered by the user into the form.
 * The selected items array is used to figure out which 
 * items the user has selected for deletion.  The index
 * in the array maps to the index in the userlist Vector.
 * 
 * @author mshoemake
 */
public class GetUserListForm extends ActionForm
{
   /**
    * The list of selected items.
    */
   private String[] selectedItems = {}; 

   /**
    * The list of users in sorted order.
    */
   private Vector userList = new Vector();

   /**
    * Constructor.
    */
   public GetUserListForm()
   {
      super();
   }

   /**
    * The list of users in sorted order.
    */
   public Vector getUserList()
   {
      return userList;
   }

   /**
    * The list of users in sorted order.
    */
   public void setUserList(Vector users)
   {
      userList = users;
   }

   /**
    * The list of selected items.
    */
   public String[] getSelectedItems()
   {
      return selectedItems;
   }

   /**
    * The list of selected items.
    */
   public void setSelectedItems(String[] items)
   {
      selectedItems = items;
   }
}
