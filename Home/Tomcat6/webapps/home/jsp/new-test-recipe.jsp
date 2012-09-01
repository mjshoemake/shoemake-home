<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
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
      <!-- end #menu -->
      <div id="page">
        <div id="page-bgtop">
          <div id="page-bgbtm">
            <div id="content">
              <html:form action="/AddTestRecipe" focus="name">
              <table width="900px">
                 <tr>
                   <td width="40%"><h2>New Recipe To Try</h2></td>
                   <td width="60%">&nbsp;</td>
                </tr>
              </table>
              </br><f:breadcrumbs/></p>
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
                    <td class="form"><html:text property="name" size="30" maxlength="45"/></td>
                  </tr>
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
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Notes: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><html:textarea property="notes" cols="50" rows="5"/></td>
                  </tr>
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">&nbsp;</td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><input class="buttons" type="submit" value="Save"/></td>
                  </tr>  
                </table>    
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




