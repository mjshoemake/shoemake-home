<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored ="false" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/femto" prefix="f" %>
<%@ taglib uri="/tags/jstl-core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
  <head>
    <html:base/>
    <f:defaultHeader/>

    <script type="text/javascript">
       function editButton()
       {
          window.location="/home/EditRecipe.do?id=<c:out value='${requestScope.RecipeForm.recipes_pk}'/>";
       }
      </script>

  </head>
  <body>
    <div id="wrapper">
      <f:topNav/>

      <div id="page">
        <div id="page-bgtop">
          <div id="page-bgbtm">
            <div id="content">
              <table width="900px">
                 <tr>
                   <td width="40%"><h2>View Recipe</h2></td>
                   <td width="60%">
                     <input type="button" value="Edit Recipe"  onclick="editButton()" alt="Edit the Current Recipe" />
                   </td>
                </tr>
              </table>
              </br><f:breadcrumbs/></p>
              <table width="900px">
                 <tr>
                 <td width="2%">&nbsp;</td>
                   <td width="17%">&nbsp;</td>
                   <td width="3%">&nbsp;</td>
                   <td width="78%">&nbsp;</td>
                </tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Recipe Name:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.name}"/></td>
                </tr>  
                <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Ingredients:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.ingredients}" escapeXml="false"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Directions:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.directions}" escapeXml="false"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Nutrition:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.nutrition}" escapeXml="false"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Servings:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.servings}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Cookbook:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.cookbook_pk}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Meal:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.meals_pk}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Food Category:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.meal_categories_pk}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Calories Per Serving:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.calories_per_serving}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Serving Size:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.serving_size}"/></td>
                </tr>
                <tr><td class="form" colspan="4">&nbsp;</td></tr>
                <tr>
                  <td class="form">&nbsp;</td>
                  <td class="form"><b>Notes:</b></td>
                  <td class="form">&nbsp;</td>
                  <td class="form"><c:out value="${requestScope.RecipeForm.notes}" escapeXml="false"/></td>
                </tr>
              </table>
              <p>&nbsp;
            </div>
            <div style="clear: both;"> </div>
          </div>
        </div>
      </div>
      <!-- end #page -->
    </div>
    <div id="footer">
      <p>Copyright (c) 2008 Sitename.com. All rights reserved. Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.<!--%@##--> Design provided by <a href="http://www.freewebtemplates.com">Free Website Templates</a>.<!--##@%--></p>
    </div>
    <!-- end #footer -->
  </body>
</html:html>




