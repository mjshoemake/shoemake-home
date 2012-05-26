<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes"/>
  <xsl:output encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="UserList">
    <xsl:processing-instruction name="cocoon-format">type="text/html"</xsl:processing-instruction>
    <html>
      <head>
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Users</title>
        <base href="http://localhost:8080/Shoemake/"/>
        <link type="text/css" rel="stylesheet" href="http://localhost:8080/Shoemake/css/shoemake.css">
        <style type="text/css">
          td.desc {font-weight: bold} 
        </style>
      </head>
      <body>
      <table align="center" width="80%">
        <tr>
          <td class="title" align="center">The List of Users</td>
        </tr>
      </table><!--   &nbsp;  -->
      <p/>
      <table align="center" width="80%">
        <tr>
          <th>Login</th>
          <th>First Name</th>
          <th>Last Name</th>
        </tr>
        <xsl:apply-templates select="User"/>
      </table><!--   &nbsp;  -->
      <p/>
      <table align="center" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><input type="button" value="Add User" name="add"/></td>
        </tr>
      </table>
    </body>
  </html>
</xsl:template>

  <xsl:template match="User">
    <xsl:variable name="UserID">
       <xsl:value-of select="UserID"/>       
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="position() mod 2 > 0"> 
        <TR class="plain">
          <TD><a href="http://localhost:8080/Shoemake/Home?state=5&#38;id={$UserID}" target="body"><xsl:value-of select="UserName"/></a></TD>
          <TD><xsl:value-of select="FirstName"/></TD>
          <TD><xsl:value-of select="LastName"/></TD>
        </TR>
      </xsl:when> 
      <xsl:otherwise>
        <TR class="highlight">
          <TD><a href="http://localhost:8080/Shoemake/Home?state=5&#38;id={$UserID}" target="body"><xsl:value-of select="UserName"/></a></TD>
          <TD><xsl:value-of select="FirstName"/></TD>
          <TD><xsl:value-of select="LastName"/></TD>
        </TR>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>
