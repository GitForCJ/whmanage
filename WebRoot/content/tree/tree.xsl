<?xml version="1.0" encoding="GB2312"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" language="JavaScript">

<xsl:template match="tree">
  <xsl:apply-templates select="entity"/>
</xsl:template>

<xsl:template match="entity">
  <div onclick="window.event.cancelBubble = true;clickOnEntity(this);" onselectstart="return false" ondragstart="return false">
  <xsl:attribute name="image"><xsl:value-of select="image"/></xsl:attribute>
  <xsl:attribute name="imageOpen"><xsl:value-of select="imageOpen"/></xsl:attribute>
  <xsl:attribute name="open">false</xsl:attribute>
  <xsl:attribute name="id">f<xsl:value-of select="@id"/></xsl:attribute>
  <xsl:attribute name="open">false</xsl:attribute>
  <xsl:attribute name="STYLE">
  	padding-left:30px;
    cursor: hand;
    <xsl:if expr="depth(this) > 2">
      display: none;
    </xsl:if>
  </xsl:attribute>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="middle" >
          <img border="0" id="image">
            <xsl:attribute name="SRC">
              <xsl:value-of select="image"/>
            </xsl:attribute>
          </img>
        </td>
        <td valign="middle" nowrap="true" style="font-family:'宋体'; font-size:15px;">
        <xsl:choose>
         <xsl:when match=".[dirurl='']">
          <xsl:value-of select="description"/>
         </xsl:when>
         <xsl:otherwise>
           <xsl:choose>
            <xsl:when match=".[description='用户登录']">
             <a  target="_parent"><xsl:attribute  name="href">
             <xsl:value-of  select="dirurl"/>
             </xsl:attribute>
            <xsl:value-of select="description"/>
            </a>
            </xsl:when>
            <xsl:otherwise>
              <a target="right" style="color:#000; text-decoration: none;"> 
			    <xsl:attribute  name="href">
               <xsl:value-of  select="dirurl"/>
               </xsl:attribute>
              <font onClick="if  (document.all.temp.value=='')  {}  else  { eval(document.all.temp.value+'.style.backgroundColor=\'#EFF7FF\'');eval(document.all.temp.value+'.style.color=\'#000\'');}
                 document.all.temp.value=this.id;this.style.backgroundColor='#FFFFFF';this.style.color='#FF6600';">
                 <xsl:attribute name="id">fina<xsl:value-of select="eid"/>
                 </xsl:attribute>
              <xsl:value-of select="description"/>
			  </font> 
             </a>
           </xsl:otherwise>
          </xsl:choose>    
         </xsl:otherwise>
        </xsl:choose>    
       </td>
      </tr>
    </table>
  <xsl:apply-templates select="contents/entity"/>
  </div>
</xsl:template>

</xsl:stylesheet>
