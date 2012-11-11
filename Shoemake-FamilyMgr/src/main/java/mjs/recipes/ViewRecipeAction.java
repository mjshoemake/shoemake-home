
package mjs.recipes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mjs.core.AbstractAction;
import mjs.exceptions.ActionException;
import mjs.model.RecipeForm;
import mjs.utils.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ViewRecipeAction extends AbstractAction
{
   /**
    * Execute this action.
    *
    * @param mapping
    * @param form
    * @param req                Description of Parameter
    * @param res                Description of Parameter
    * @return                   ActionForward
    * @exception Exception      Description of Exception
    */
   public ActionForward processRequest(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception
   {
      metrics.startEvent("GetRecipe", "action");
      RecipeForm recipeForm = (RecipeForm)form;
      SearchManager dbMgr = null;

      try
      {
         //Create the instance of SampleDataManager
         dbMgr = new SearchManager();
         String pk = req.getParameter(Constants.PARAM_ID);
         addBreadcrumbs(req, "View Recipe", "../ViewRecipe.do?id=" + pk);
         
         // Validate form data.
         //String mappingFile = "mjs/recipes/RecipeMapping.xml";
         //OrderedMap dataMapping = driver.loadMapping(mappingFile);
         log.debug("DataMapping file loaded.");
         if (pk != null)
         {
        	 log.debug("Form validated successfully.");
             // Insert recipe.
             try {
                 dbMgr.open();
                 dbMgr.getRecipeForView(Integer.parseInt(pk), recipeForm);
                 dbMgr.close(true);
             } catch (Exception e) {
                 dbMgr.close(false);
                 throw e;
             }
             
             String directions = recipeForm.getDirections();
             String ingredients = recipeForm.getIngredients();
             String nutrition = recipeForm.getNutrition();
             recipeForm.setDirections(formatMemo(directions));
             recipeForm.setIngredients(formatMemo(ingredients));
             recipeForm.setNutrition(formatMemo(nutrition));
             
             return (mapping.findForward("success"));
         }
         else
         {
        	// Validation failed. 
            log.debug("Primary key is missing or invalid.");
            return (mapping.findForward("invalid"));
         }
      }
      catch (java.lang.Exception e)
      {
         ActionException ex = new ActionException("Error trying to save a new recipe.", e);
         throw ex;
      } finally {
          metrics.endEvent("GetRecipe", "action");
          metrics.writeMetricsToLog();
      }
   }

   private String formatMemo(String value) {
	   StringBuilder builder = new StringBuilder();
	   
	   log.debug("Value: " + value);
	   //byte[] bytes = value.getBytes();
	   //char[] chars = value.toCharArray();
	   //for (int i=0; i <= bytes.length - 1; i++) {
	   //   log.debug("  " + i + ":  " + bytes[i] + "/" + chars[i]);
	   //}
	   byte[] delim = new byte[1];
	   delim[0] = 10;
	   String[] items = value.split(new String(delim)); 
	   
	   for (int i=0; i <= items.length-1; i++) {
	      builder.append(items[i]);
		  builder.append("<br/>");
	   }
	   
	   return builder.toString();
   }
   
}
