<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes"/>
  <xsl:output encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="UserList">
    <xsl:processing-instruction name="cocoon-format">type="text/html"</xsl:processing-instruction>
    <html>
      <head>
        <title>User List</title>
      </head>
      <body>
        <h1>User List</h1>
        <table align="left">
          <tr>
            <td>Login</td>      
            <td>First Name</td>      
            <td>Last Name</td>      
          </tr>
          <xsl:apply-templates select="User"/>
        </table>  
      </body>
    </html>
  </xsl:template>

  <xsl:template match="User">
    <tr>
      <td><xsl:value-of select="UserName"/></td>      
      <td><xsl:value-of select="FirstName"/></td>      
      <td><xsl:value-of select="LastName"/></td>      
    </tr>
  </xsl:template>

</xsl:stylesheet>
