<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored ="false" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/femto" prefix="f" %>
<%@ taglib uri="/tags/jstl-core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
  <head>
    <html:base/>
    <f:defaultHeader/>

  </head>
  <body>
    <div id="wrapper">
      <f:topNav/>
      <f:traceAttributes/>
      <f:log  traceObject="RecipeForm"/>
      <!-- end #menu -->
      <div id="page">
        <div id="page-bgtop">
          <div id="page-bgbtm">
            <div id="content">
              <html:form action="/UpdateRecipe" focus="name">
              <table width="100%">
                <tr>
                  <td width="40%"><h2>Edit Recipe</h2></td>
                  <td width="60%">
                    &nbsp;&nbsp;&nbsp;
                  </td>
                </tr>
              </table>
              </br><f:breadcrumbs/></p>
              <html:hidden property="recipes_pk"/>
              <html:hidden property="favorite"/>
                <table width="100%">
                  <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="17%">&nbsp;</td>
                    <td width="3%">&nbsp;</td>
                    <td width="78%">&nbsp;</td>
                  </tr>  
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Recipe Name: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><html:text property="name" size="65" maxlength="45"/></td>
                  </tr>  
                  <c:if test="${requestScope.RecipeForm.favorite == 'Yes'}">
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Ingredients: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:textarea property="ingredients" cols="50" rows="7"/></td>
                    </tr>  
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Directions: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:textarea property="directions" cols="50" rows="7"/></td>
                    </tr>  
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Nutrition: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:textarea property="nutrition" cols="50" rows="3"/></td>
                    </tr>  
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Servings: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="servings" size="30" maxlength="30"/></td>
                    </tr>
                  </c:if>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Cookbook: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form">
                      <html:select property="cookbook_pk">
                        <html:options collection="cookbooks" property="value" labelProperty="caption"/>
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Meal: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form">
                      <html:select property="meals_pk">
                        <html:options collection="meals" property="value" labelProperty="caption"/>
                      </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Food Category: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form">
                      <html:select property="meal_categories_pk">
                        <html:options collection="categories" property="value" labelProperty="caption"/>
                      </html:select>
                    </td>
                  </tr>
                  <c:if test="${requestScope.RecipeForm.favorite == 'Yes'}">
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Calories Per Serving: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="calories_per_serving" size="4" maxlength="4"/></td>
                    </tr>  
                    <tr>
                      <td class="form">&nbsp;</td>
                      <td class="form">Serving Size: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="serving_size" size="20" maxlength="20"/></td>
                    </tr>
                  </c:if>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Notes: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><html:textarea property="notes" cols="50" rows="5"/></td>
                  </tr>  
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr>
                    <td class="form" colspan="4">
                      <input type="button" value="Save"  onclick="javascript:document.RecipeForm.submit();" alt="Save" />
                      <c:if test="${requestScope.RecipeForm.favorite != 'Yes'}">
                        &nbsp;&nbsp;&nbsp;
                        <input type="button" value="Make a Favorite"  onclick="makeFavoriteButton()" alt="Make this recipe a favorite" />
                      </c:if>
                    </td>
                  </tr>
                </table>    

                <script type="text/javascript">
                   function makeFavoriteButton()
                   {
                      window.location='/home/MakeRecipeFavorite.do?id=<c:out value="${requestScope.RecipeForm.recipes_pk}"/>';
                   }

                   function save()
                   {
                      document.RecipeForm.submit();
                   }
                </script>


              </html:form>
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




