<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/femto" prefix="f" %>
<%@ taglib uri="/tags/jstl-core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
  <head>
    <html:base/>
    <f:defaultHeader/>
    <script type="text/javascript" src="../js/crypto.js"></script>

    <script type="text/javascript" src="http://crypto-js.googlecode.com/files/2.5.3-crypto-sha1-hmac-pbkdf2-blockmodes-des.js"></script>

    <script type="text/javascript">
       function loginButton()
       {
          var pass = document.getElementById('password').value;
          var key = 'MichaelShoemake';
          var crypted = Aes.Ctr.encrypt(pass, key, 256);
          document.getElementById('password').value = crypted;

          // submit form.
          document.LoginForm.submit();
       }
    </script>
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <div id="logo">
          <h1>Shoemake Management and Chronicles</h1>
        </div>
      </div>
      <div id="page">
        <div id="page-bgtop">
          <div id="page-bgbtm">
            <div id="content">
              <html:form action="/Login" focus="username">
              <table width="900px">
                 <tr>
                   <td width="40%"><h2>Login</h2></td>
                   <td width="60%">&nbsp;</td>
                </tr>
              </table>
                <table width="100%">
                  <tr>
                    <td width="2%">&nbsp;</td>
                    <td width="17%">&nbsp;</td>
                    <td width="3%">&nbsp;</td>
                    <td width="78%">&nbsp;</td>
                  </tr>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Username:</td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><html:text property="username" size="30" maxlength="30"/></td>
                  </tr>
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">Password: </td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><input <input type="password" id="password" name="password" size="30" maxlength="30" value=""/></td>
                  </tr>  
                  <tr><td class="form" colspan="4">&nbsp;</td></tr>  
                  <tr>
                    <td class="form">&nbsp;</td>
                    <td class="form">&nbsp;</td>
                    <td class="form">&nbsp;</td>
                    <td class="form"><input class="buttons" type="button" value="Login" onclick="loginButton()"/></td>
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




