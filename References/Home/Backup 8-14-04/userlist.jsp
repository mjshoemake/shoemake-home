<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/app.tld"    prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
  <head>
    <html:base/>
    <link href="css/shoemake.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
       function setCheckboxes()
       {
          var form = document.forms[0];
          var val = form.selectAll.checked;
          for (var i=0; i < form.elements.length; i++)
          {
             if (form.elements[i].type == "checkbox")
             {
                form.elements[i].checked = val;
             }  
          }
       }
    </script>
  </head>
  <body>

    <html:errors/>
 
    <table align="center" width="80%">
       <!--  BREADCRUMBS  -->
       <tr>
          <td>
             <table cellpadding="0" cellspacing="0">
                <tr>
                   <td class="breadcrumb"><a href="Shoemake/UserAdd.xhtml">User List</a></td>
                   <td class="breadcrumb" width="20">&nbsp;&nbsp;&gt;&nbsp;&nbsp;</td>
                   <td class="breadcrumb">Add a User</td>
                </tr>
             </table>
          </td>
          <td>&nbsp;</td>
       </tr>
       <!--  BLANK LINE  -->
       <tr><td>&nbsp;</td></tr>
       <!--  TOOLBAR  -->
       <tr>
          <td>
             <table cellpadding="0" cellspacing="0" align="center">
                <tr>
                   <td><a href="AddUserPage.do"><img border="0" src="images/buttonAddUser.gif" alt="Add User" height="26"></a></td>
                   <td width="30">&nbsp;</td>
                   <td><a href="Shoemake/UserAdd.xhtml"><img border="0" src="images/buttonDeleteUser.gif" alt="Delete Users" height="26"></a></td>
                   <td width="30">&nbsp;</td>
                   <td><a href="Shoemake/UserAdd.xhtml"><img border="0" src="images/buttonDeleteAll.gif" alt="Delete All" height="26"></a></td>
                </tr>
             </table>
          </td>
       </tr>
       <!--  TITLE  -->
       <tr><td>&nbsp;</td></tr>
       <tr>
          <td class="title" align="center"><bean:message key="userlist.heading"/></td>
       </tr>
       <tr>
          <td>
             <!--   GRID  --> 
             <form name="userListForm" method="post" action="/cgi/junk.cgi">
                <table align="center" width="100%">
                   <tr>
                      <!--   Headers  --> 
                      <th><input name="selectAll" type="checkbox" onclick="setCheckboxes()"/></th>
                      <th><a class="header" href="GetUserList.do?method=sortUserName"><bean:message key="userlist.login.title"/></a></th>
                      <th><a class="header" href="GetUserList.do?method=sortFirstName"><bean:message key="userlist.firstname.title"/></a></th>
                      <th><a class="header" href="GetUserList.do?method=sortLastName"><bean:message key="userlist.lastname.title"/></a></th>
                   </tr>
                   <%--  GET THE USER LIST DATA  --%>
                   <% // Retrieve the user list generated by the action.
                      java.util.Vector list = (java.util.Vector)(request.getAttribute("mjs.home.ReturnValue"));
                      String tableRowClass = "plain";
                      for (int C=0; C <= list.size()-1; C++)
                      { 
                         mjs.home.model.User user = (mjs.home.model.User)(list.get(C)); 
             
                         // Odd or even row?
                         if (C % 2 > 0)
                            tableRowClass = "highlight";
                         else
                            tableRowClass = "plain";
                    %>
            
                         <tr class="<%= tableRowClass%>">
                            <td align="center"><input name="rowSelect" value="<%= user.getUserID() %>" type="checkbox"/></td>
                            <td><a href="UpdateUserPage.do?id=<%= user.getUserID() %>"><%= user.getUserName()%></a></TD>
                            <td><%= user.getFirstName()%></td>
                            <td><%= user.getLastName()%></td>
                         </tr>

                   <% } %> 
                </table>
             </form>
          </td>
       </tr>
    </table>&nbsp;
  </body>
</html:html>
