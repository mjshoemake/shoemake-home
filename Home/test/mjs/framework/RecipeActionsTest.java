package mjs.framework;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import mjs.database.DatabaseDriver;
import mjs.database.PaginatedList;
import mjs.mocks.MockActionMapping;
import mjs.mocks.MockHttpServletRequest;
import mjs.mocks.MockHttpServletResponse;
import mjs.mocks.MockHttpSession;
import mjs.recipes.RecipeForm;
import mjs.recipes.RecipeManager;
import mjs.recipes.GetRecipesByLetterAction;
import mjs.recipes.ViewRecipeAction;
import mjs.utils.LogUtils;
import mjs.utils.SingletonInstanceManager;

@SuppressWarnings("rawtypes")
public class RecipeActionsTest extends AbstractServletTest {

   @Before
   public void setUp() throws Exception {
      // Start server.
      startServer();
   }

   /**
    * Test method.
    */
   @Test
   public void testGetRecipesByLetter() {

      try {
         MockActionMapping mapping = new MockActionMapping();
         MockHttpServletRequest request = new MockHttpServletRequest();
         MockHttpServletResponse response = new MockHttpServletResponse();
         request.setParameter("letter", "G");
         GetRecipesByLetterAction action = new GetRecipesByLetterAction();
         action.execute(mapping, null, request, response);

         System.out.println("Test complete.  Exiting.");

      }
      catch (Exception e) {
         e.printStackTrace();
         assertFailed("Execution with no exceptions.  " + e.getMessage());
      }
      finally {
         // reportResults();
      }
   }

   @Test
   public void testViewRecipe() {

      try {
         MockActionMapping mapping = new MockActionMapping();
         MockHttpServletRequest request = new MockHttpServletRequest();
         MockHttpServletResponse response = new MockHttpServletResponse();
         // request.setParameter("id", recipe.getRecipes_pk());
         request.setParameter("id", "7");
         RecipeForm recipeForm = new RecipeForm();
         ViewRecipeAction action = new ViewRecipeAction();
         action.execute(mapping, recipeForm, request, response);

         System.out.println("Test complete.  Exiting.");

      }
      catch (Exception e) {
         e.printStackTrace();
         assertFailed("Execution with no exceptions.  " + e.getMessage());
      }
      finally {
         // reportResults();
      }
   }

}
