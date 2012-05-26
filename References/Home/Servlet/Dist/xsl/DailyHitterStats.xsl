<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>
  <xsl:output encoding="UTF-8"/>

  <xsl:template match="/html/body/div/table[1]/tr[1]/td[1]">
    <hitterStats>
      <name><xsl:value-of select="h1[1]"/></name>
      <xsl:apply-templates select="table[5]"/>
    </hitterStats>
  </xsl:template>

  <xsl:template match="table[5]">
    <dailyStats>
    
    </dailyStats>
  </xsl:template>

</xsl:stylesheet>
