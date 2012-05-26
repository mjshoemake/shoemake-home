<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/app.tld"    prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
  <head>
    <html:base/>
    <link href="css/shoemake.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <table align="center" width="80%">
       <!--  BREADCRUMBS  -->
       <tr>
          <td>
             <table cellpadding="0" cellspacing="0">
                <tr>
                   <td class="breadcrumb"><a href="GetUserList.do?method=sortMostRecent">User List</a></td>
                   <td class="breadcrumb" width="20">&nbsp;&nbsp;&gt;&nbsp;&nbsp;</td>
                   <td class="breadcrumb">Add a User</td>
                </tr>
             </table>
          </td>
          <td>&nbsp;</td>
       </tr>
       <!--  BLANK LINE  -->
       <tr><td>&nbsp;</td></tr>
       <tr><td><html:errors/></td></tr>    
       <tr>
          <td>
             <html:form action="/AddUser" focus="username">
                <table width="412" align="center" border="0" cellpadding="0" cellspacing="0">
                   <tr>
                      <td class="title" colspan="5" align="center">Add a User</td>
                   </tr>
                </table>&nbsp;
                <table width="440" align="center" border="0" cellpadding="0" cellspacing="0">
                   <tr>
                      <td width="20"><img src="images/formbdrTL.gif" align="center" height="20" width="20"/></td>
                      <td width="140"><img src="images/formbdrT.gif" align="center" height="20" width="140"/></td>
                      <td width="20"><img src="images/formbdrT.gif" align="center" height="20" width="20"/></td>
                      <td width="240"><img src="images/formbdrT.gif" align="center" height="20" width="240"/></td>
                      <td width="20"><img src="images/formbdrTR.gif" align="center" height="20" width="20"/></td>
                   </tr>
                   <tr>
                      <td height="30"><img src="images/formbdrL.gif" align="center" height="30" width="20"/></td>
                      <td class="form">User Name: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="userName" size="30" maxlength="30"/></td>
                      <td><img src="images/formbdrR.gif" align="center" height="30" width="20"/></td>
                   </tr>
                   <tr>
                      <td height="30"><img src="images/formbdrL.gif" align="center" height="30" width="20"/></td>
                      <td class="form">First Name: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="firstName" size="30" maxlength="30"/></td>
                      <td><img src="images/formbdrR.gif" align="center" height="30" width="20"/></td>
                   </tr>
                   <tr>
                      <td height="30"><img src="images/formbdrL.gif" align="center" height="30" width="20"/></td>
                      <td class="form">Last Name: </td>
                      <td class="form">&nbsp;</td>
                      <td class="form"><html:text property="lastName" size="30" maxlength="30"/></td></td>
                      <td><img src="images/formbdrR.gif" align="center" height="30" width="20"/></td>
                   </tr>
                   <tr>
                      <td><img src="images/formbdrBL.gif" align="center" height="20" width="20"/></td>
                      <td><img src="images/formbdrB.gif" align="center" height="20" width="140"/></td>
                      <td><img src="images/formbdrB.gif" align="center" height="20" width="20"/></td>
                      <td><img src="images/formbdrB.gif" align="center" height="20" width="240"/></td>
                      <td><img src="images/formbdrBR.gif" align="center" height="20" width="20"/></td>
                   </tr>
                </table>
                <p/>
                <table align="center" border="0" cellpadding="0" cellspacing="0">
                   <td><html:submit/><td/>        
                   <td width="20"/>
                   <td><html:reset/><td/>        
                   <td width="20"/>
                   <td><html:cancel/><td/>        
                </table>
             </html:form>
          </td>
       <tr>
    </table>
  </body>
</html:html>
