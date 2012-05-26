<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored ="false" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/femto" prefix="f" %>
<%@ taglib uri="/tags/jstl-core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
  <head>
    <html:base/>
    <f:defaultHeader/>

    <script type="text/javascript">
       function addButton()
       {
          window.location="/home/NewRecipe.do";
       }

       function getAllRecipesButton()
       {
          window.location="/home/GetRecipeList.do";
       }

       function getByLetterButton()
       {
          window.location="/home/GetRecipesByLetter.do";
       }
    </script>

  </head>
  <body>
    <div id="wrapper">

      <!-- Menu -->
      <f:topNav/>

      <div id="page">
        <div id="page-bgtop">
          <div id="page-bgbtm">
            <div id="content">
              <table width="100%">
                <tr>
                  <td width="40%"><h2>Recipes</h2></td>
                  <td width="60%">
                    <input type="button" value="All Recipes"  onclick="getAllRecipesButton()" alt="View All Recipes" />
                    &nbsp;&nbsp;&nbsp;
                    <input type="button" value="By Letter"  onclick="getByLetterButton()" alt="View By Letter" />
                    &nbsp;&nbsp;&nbsp;
                    <input type="button" value="New Recipe"  onclick="addButton()" alt="Add New Recipe" />
                  </td>
                </tr>
              </table>
              </br><f:breadcrumbs/></p>
              <f:showPaginatedList view="details" url="../ViewRecipe.do?id=%s" formName="RecipeSearchForm"/>
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




