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

       function addTestRecipeButton()
       {
          window.location="/home/NewTestRecipe.do";
       }

       function getAllRecipesButton()
       {
          window.location="/home/GetRecipeList.do";
       }

       function getRecipesDetailedButton()
       {
          window.location="/home/GetRecipesDetailed.do";
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
                    <input type="button" value="Show Details"  onclick="getRecipesDetailedButton()" alt="Show Detailed Recipe List" />
                    &nbsp;&nbsp;&nbsp;
                    <input type="button" value="New Recipe"  onclick="addButton()" alt="Add New Recipe" />
                    &nbsp;&nbsp;&nbsp;
                    <input type="button" value="New Test Recipe"  onclick="addTestRecipeButton()" alt="Add Test Recipe" />
                  </td>
                </tr>
              </table>
              </br><f:breadcrumbs/></p>
              &nbsp;<p>
              &nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=A">A</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=B">B</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=C">C</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=D">D</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=E">E</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=F">F</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=G">G</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=H">H</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=I">I</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=J">J</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=K">K</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=L">L</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=M">M</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=N">N</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=O">O</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=P">P</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=Q">Q</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=R">R</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=S">S</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=T">T</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=U">U</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=V">V</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=W">W</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=X">X</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=Y">Y</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <a class="letterlinks" href="../GetRecipesByLetter.do?letter=Z">Z</a>

              <p>&nbsp;
              <p>&nbsp;
              <a class="letterheader" href="#"><c:out value="${sessionScope.letter}"/></a>
              <f:showPaginatedList view="list" url="../ViewRecipe.do?id=%s"/>
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




