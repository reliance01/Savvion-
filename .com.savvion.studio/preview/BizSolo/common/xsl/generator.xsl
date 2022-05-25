<!--
  author Liviu Casapu
  author modified by Shigehiro Soejima
  version 1.6, 09/08/2003
-->
<xsl:transform version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:bizsolo="http://www.savvion.com/sbm/BizSolo">
  <xsl:output method="html" encoding="utf-8" indent="yes" omit-xml-declaration="yes"/>
  <xsl:param name="pageName"/><!-- programatically set, required -->
  <xsl:param name="pageFileName"/><!-- programatically set, required -->
  <xsl:param name="formAction"/><!-- programatically set, required -->
  <!--<xsl:param name="BizSoloRoot"/>--><!-- no longer required -->
  <xsl:param name="themeURL"/><!-- programaticamly set, required -->
  <xsl:param name="docInterface"/><!-- programatically set, required -->
  <xsl:variable name="atomicType" select="//Process/AtomicWS[@name=$pageName]/AtomicType[text()]"/>
  <xsl:variable name="page" select="//Process/AtomicWS[@name=$pageName]"/>
  <xsl:variable name="isSkippable" select="//Process/AtomicWS[@name=$pageName]/@skippable='true'"/>
  <xsl:variable name="isTaskEnabled" select="//Process/AtomicWS[@name=$pageName]/Collaboration/CollabFunction[@type='Task']/@enabled='true'"/>
  <xsl:variable name="isReassignEnabled" select="//Process/AtomicWS[@name=$pageName]/Collaboration/CollabFunction[@type='Reassign']/@enabled='true'"/>
  <xsl:variable name="isEmailEnabled" select="//Process/AtomicWS[@name=$pageName]/Collaboration/CollabFunction[@type='Email']/@enabled='true'"/>
  <xsl:variable name="isImEnabled" select="//Process/AtomicWS[@name=$pageName]/Collaboration/CollabFunction[@type='IM']/@enabled='true'"/>
  <xsl:variable name="isNotesEnabed" select="//Process/AtomicWS[@name=$pageName]/Collaboration/CollabFunction[@type='Notes']/@enabled='true'"/>
  <xsl:variable name="isWorkTimeEnabled" select="//Process/AtomicWS[@name=$pageName]/@worktime='true'"/>
  
  <xsl:variable name="appName" select="/Process/@name"/>

  <xsl:template name="HtmlSection" match="/">
    <html>
      <head>
        <bizsolo:link rel="stylesheet"/>
        <title>
          <xsl:value-of select="concat($appName,'/',$pageName)"/>
        </title>
      </head>

      <xsl:text disable-output-escaping="yes"><![CDATA[<body class="ApBody" onUnload="pwr.removePakBizSoloBeanFromCache('<%=session.getId()%>', onSucess);">]]></xsl:text>

        <xsl:call-template name="JavaScript"/>
        <xsl:call-template name="WorkitemHeader"/>
        <xsl:call-template name="FormInfo"/>
        <xsl:call-template name="Footer"/>
      <xsl:text disable-output-escaping="yes"><![CDATA[</body>]]></xsl:text>
    </html>
  </xsl:template>


  <xsl:template name="WorkitemHeader">
    <xsl:variable name="useInstruction" select="$page/Option[@name='Instructions']"/>
    <xsl:variable name="usePriority" select="$page/Option[@name='Priority']"/>
    <xsl:variable name="useStartDate" select="$page/Option[@name='StartDate']"/>
    <xsl:variable name="useDueDate" select="$page/Option[@name='DueDate']"/>
    <xsl:variable name="editableDs">
      <xsl:for-each select="$page/Format[@editable='true']">
        <xsl:variable name="dsName" select="./@input"/>
        <xsl:if test="/Process/Dataslot[@name=$dsName][@type='DOCUMENT' or @type='XML']">
          <xsl:text>TRUE</xsl:text>
        </xsl:if>
      </xsl:for-each>
    </xsl:variable>
    <xsl:variable name="processValidationType" select="/Process/Forms/ValidationAlert/@type"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[<form method="post" name="form" onsubmit="document.form.action='<%=response.encodeURL("]]></xsl:text>
    <xsl:value-of select="$formAction" />
    <xsl:text disable-output-escaping="yes"><![CDATA[")%>']]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[;return sbm.pValidate(this,']]></xsl:text><xsl:value-of select="$processValidationType"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[');this.onsubmit = new Function('return false');"]]></xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <xsl:choose>
          <xsl:when test="$atomicType='START'">
            <td class="ApSegTblInBg">
              <table width="100%" cellpadding="4" align="center" cellspacing="0" border="0">
                <tr align="center">
                  <td class="ApSegTitle" nowrap="">
                    <bizsolo:getLabel type="RESOURCE">
                      <xsl:attribute name="name">
                        <xsl:value-of select="'processTemplateLabel_'"/>
                      </xsl:attribute>
                    </bizsolo:getLabel>
                  </td>
                </tr>
              </table>
              <table class="ApSegDataTbl" width="100%" cellpadding="4" align="center" cellspacing="1" border="0">
                <tr align="center" border="1">
                  <td class="ApSegGenLabel" width="15%" border="1" nowrap="">
                    <bizsolo:getLabel type="RESOURCE">
                      <xsl:attribute name="name">
                        <xsl:value-of select="'BIZSITE_INSTANCE_NAME_LABEL'"/>
                      </xsl:attribute>
                    </bizsolo:getLabel>
                  </td>
                  <td class="ApSegGenData" width="55%" border="1">
                    <input class="ApInptTxt" type="text" size="30" name="bizsite_instanceName" value="{$appName}"/>
                  </td>
                  <xsl:if test="not($usePriority[@visible='false'])">
                    <td class="ApSegGenLabel" width="15%" border="1" nowrap="">
                      <bizsolo:getLabel type="RESOURCE">
                        <xsl:attribute name="name">
                          <xsl:value-of select="'BIZSITE_PRIORITY_LABEL'"/>
                        </xsl:attribute>
                      </bizsolo:getLabel>
                    </td>
                    <td class="ApSegGenData" width="15%" border="1"> 
                      <select class="ApInptSelect" name="bizsite_priority">
                        <option value="critical">
                          <bizsolo:getLabel type="RESOURCE">
                            <xsl:attribute name="name">
                              <xsl:value-of select="'PRIORITY_CRITICAL'"/>
                            </xsl:attribute>
                          </bizsolo:getLabel>
                        </option>
                        <option value="high">
                          <bizsolo:getLabel type="RESOURCE">
                            <xsl:attribute name="name">
                              <xsl:value-of select="'PRIORITY_HIGH'"/>
                            </xsl:attribute>
                          </bizsolo:getLabel>
                        </option>
                        <option value="medium">
                          <bizsolo:getLabel type="RESOURCE">
                            <xsl:attribute name="name">
                              <xsl:value-of select="'PRIORITY_MEDIUM'"/>
                            </xsl:attribute>
                          </bizsolo:getLabel>
                        </option>
                        <option value="low">
                          <bizsolo:getLabel type="RESOURCE">
                            <xsl:attribute name="name">
                              <xsl:value-of select="'PRIORITY_LOW'"/>
                            </xsl:attribute>
                          </bizsolo:getLabel>
                        </option>
                      </select>
                    </td>
                  </xsl:if>
                </tr>
                <xsl:if test="not($useInstruction[@visible='false'])">
                  <tr>
                    <td class="ApSegGenLabel" width="15%" border="1" nowrap=""> 
                      <bizsolo:getLabel type="RESOURCE">
                        <xsl:attribute name="name">
                          <xsl:value-of select="'BIZSITE_INSTRUCTION_LABEL'"/>
                        </xsl:attribute>
                      </bizsolo:getLabel>
                    </td>
                    <td class="ApSegGenData" colspan="3" border="1"> 
                      <xsl:value-of select="//AtomicWS[@name=$pageName]/Instruction"/>
                    </td>
                  </tr>
                </xsl:if>
              </table>
            </td>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text disable-output-escaping="yes"><![CDATA[<bizsolo:if test="<%=bean.getPropString(\"workitemName\") != null %>">]]></xsl:text>
            <td class="ApSegTblInBg">
              <table class="ApButtonDarkBg" width="100%" cellpadding="0" align="center" cellspacing="1">
                <tr>
                  <td class="ApSegTitle" align="center">
                    <bizsolo:getDS name="workitemName"/>
                  </td>
                </tr>
              </table>
              <table class="ApSegDataTbl" width="100%" cellspacing="1" cellpadding="4">
                <xsl:if test="not($useInstruction[@visible='false'])">
                  <tr>
                    <xsl:call-template name="ShowOption">
                      <xsl:with-param name="dsname">instruction</xsl:with-param>
                      <xsl:with-param name="colspan">5</xsl:with-param>
                    </xsl:call-template>
                  </tr>
                </xsl:if>
                <tr>
                  <xsl:if test="not($usePriority[@visible='false'])">
                    <xsl:call-template name="ShowOption">
                      <xsl:with-param name="dsname">priority</xsl:with-param>
                    </xsl:call-template>
                  </xsl:if>
                  <xsl:if test="not($useStartDate[@visible='false'])">
                    <xsl:call-template name="ShowOption">
                      <xsl:with-param name="dsname">startDate</xsl:with-param>
                    </xsl:call-template>
                  </xsl:if>
                  <xsl:if test="not($useDueDate[@visible='false'])">
                    <xsl:call-template name="ShowOption">
                      <xsl:with-param name="dsname">dueDate</xsl:with-param>
                    </xsl:call-template>
                  </xsl:if>
                </tr>
              </table>
            </td>
            <xsl:text disable-output-escaping="yes"><![CDATA[</bizsolo:if>]]></xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </tr>
    </table>
    <!-- <br/> -->
  </xsl:template>


  <xsl:template name="ShowOption">
    <xsl:param name="dsname"/>
    <xsl:param name="colspan"/>
    <xsl:variable name="toUpperDS" select="translate($dsname,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
   
    <td class="ApSegGenLabel" width="15%" nowrap="">
      <bizsolo:getLabel type='RESOURCE'>
        <xsl:attribute name="name">
          <xsl:value-of select="concat('BIZSITE_',$toUpperDS,'_LABEL')"/>
        </xsl:attribute>
      </bizsolo:getLabel>
    </td>
    <td class="ApSegGenData" width="15%" nowrap="">
      <xsl:if test="not($colspan='')">
        <xsl:attribute name="colspan">
          <xsl:value-of select="$colspan"/>
        </xsl:attribute>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="$dsname='instruction'">
          <bizsolo:getDS>
            <xsl:attribute name="name">
              <xsl:value-of select="'bizsite_instruction'"/>
            </xsl:attribute>    
	    <xsl:attribute name="wsName">
              <xsl:value-of select="$pageName"/>
            </xsl:attribute>
          </bizsolo:getDS> 
        </xsl:when>
        <xsl:otherwise>
          <bizsolo:getDS>
            <xsl:attribute name="name">
              <xsl:value-of select="concat('bizsite_',$dsname)"/>
            </xsl:attribute>
          </bizsolo:getDS>
        </xsl:otherwise>
      </xsl:choose>
    </td>
  </xsl:template>
  
  
  <xsl:template name="FormInfo">
    <xsl:variable name="editableDs">
      <xsl:for-each select="$page/Format[@editable='true']">
        <xsl:variable name="dsName" select="./@input"/>
        <xsl:if test="/Process/Dataslot[@name=$dsName][@type='DOCUMENT' or @type='XML']">
          <xsl:text>TRUE</xsl:text>
        </xsl:if>
      </xsl:for-each>
    </xsl:variable>
    
    <div id="errors" name="errors" class="vBoxClass" style="visibility:hidden;display:none"></div>

      <xsl:call-template name="DsTable"/>
      <xsl:call-template name="FlowInfo"/>
      <xsl:call-template name="ExtraInfo"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[</form>]]></xsl:text>
  </xsl:template>
  
  
  <xsl:template name="DsTable">
    <xsl:if test="$page/Format[@input]">
        <table cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="ApSegTblInBg">
              <table class="ApSegDataTbl" cellpadding="4" cellspacing="1" width="100%">
                <xsl:call-template name="AllDs"/>
              </table>
            </td>
          </tr>
        </table>
    </xsl:if>
  </xsl:template>
  <xsl:template name="AllDs">
    <xsl:for-each select="$page/Format">
      <xsl:call-template name="DS"/>
    </xsl:for-each>
  </xsl:template>
  
  
  <xsl:template name="DS">
    <xsl:variable name="dsname" select="./@input"/>
    <xsl:variable name="mixed">
      <xsl:value-of select="'false'"/>
      <xsl:for-each select="$page/Format">
        <xsl:if test="not(./@label='') or ./@required='true'">
          <xsl:value-of select="'t'"/>
        </xsl:if>
      </xsl:for-each>
    </xsl:variable>
    <xsl:variable name="dsValidationType" select="/Process/Dataslot[@name = $dsname]/Format/Validation/ValidationType[text()]"/>
    <xsl:if test="string-length($dsValidationType) != 0 and starts-with($dsValidationType,'cc')" >
               <xsl:text disable-output-escaping="yes"><![CDATA[
                <tr>
                  <td class="ApSegDataLabel"><label for="Creadit_Card_Type">Credit Card Type</label></td>
                  <td class="ApSegDataVal">  <select name="Credit_Card_Type" id="Credit_Card_Type" class="ApInptSelect"></select></td>
              </tr>
              <script>sbm.loadCreditCardTypes("Credit_Card_Type");</script>
	      ]]></xsl:text>
    </xsl:if>
    
    <tr>
      <xsl:if test="not($mixed='false')">
        <td class="ApSegDataLabel" nowrap="">
          <bizsolo:getLabel name="{$dsname}" wsName="{$pageName}">
            <xsl:if test="/Process[@type='BIZLOGIC']">
              <xsl:attribute name="mode">BizSite</xsl:attribute>
            </xsl:if>
          </bizsolo:getLabel>
          <xsl:if test="./@required='true'">
            <span class="ApRequired">*</span>
          </xsl:if>
        </td>
      </xsl:if>
      <td class="ApSegDataVal">
        <bizsolo:getDS name="{$dsname}">
          <xsl:if test="./@type"> 
            <xsl:attribute name="type">
              <xsl:value-of select="./@type"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:attribute name="wsName">
            <xsl:value-of select="$pageName"/>
           </xsl:attribute>
          <xsl:if test="./@required='true'">
            <xsl:attribute name="required">
              <xsl:value-of select="'true'"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="./@editable='false'">
            <xsl:attribute name="writePerm">
              <xsl:value-of select="'false'"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="/Process/Dataslot[@name=$dsname][@type='DOCUMENT' or @type='XML']">
            <xsl:if test="/Process/Dataslot[@name=$dsname]/DataValue!=''">
              <xsl:attribute name="file">
                <xsl:variable name="files">
                  <xsl:for-each select="/Process/Dataslot[@name=$dsname]/DataValue">
                    <xsl:value-of select="concat(., ',')"/>
                  </xsl:for-each>
                </xsl:variable>
                <xsl:value-of select="substring($files, 0, string-length($files))"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:attribute name="multiline">
              <xsl:value-of select="/Process/Dataslot[@name=$dsname]/MultiLine"/>
            </xsl:attribute>
            <xsl:attribute name="appendWith">
              <xsl:value-of select="/Process/Dataslot[@name=$dsname]/AppendWith"/>
            </xsl:attribute>
            <xsl:if test="/Process[@type='BIZLOGIC']">
              <xsl:attribute name="mode">SLAVE</xsl:attribute>
            </xsl:if>
          </xsl:if>
          <xsl:if test="/Process/Dataslot[@name=$dsname][@type='XSL'] and /Process[@type='BIZLOGIC']">
            <xsl:attribute name="mode">SLAVE</xsl:attribute>
          </xsl:if>
          <xsl:if test="./@size and not(./@size=20)">
            <xsl:attribute name="size">
              <xsl:value-of select="./@size"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="./@length and not(./@length=20)">
            <xsl:attribute name="maxlength">
              <xsl:value-of select="./@length"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="./@width and not(./@width=4)">
            <xsl:attribute name="cols">
              <xsl:value-of select="./@width"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="./@height and not(./@height=20)">
            <xsl:attribute name="rows">
              <xsl:value-of select="./@height"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:if test="./@password='true'">
            <xsl:attribute name="password">
              <xsl:value-of select="'true'"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:variable name="precision" select="/Process/Dataslot[@name=$dsname][@precision]"/>
          <xsl:if test="$precision">
            <xsl:attribute name="precision">
              <xsl:value-of select="$precision"/>
            </xsl:attribute>
          </xsl:if>
          <xsl:variable name="scale" select="/Process/Dataslot[@name=$dsname][@scale]"/>
          <xsl:if test="$scale">
            <xsl:attribute name="scale">
              <xsl:value-of select="$scale"/>
            </xsl:attribute>
          </xsl:if>
	  <xsl:if test="string($dsValidationType)">
            <xsl:attribute name="validationType">
              <xsl:value-of select="$dsValidationType"/>
            </xsl:attribute>
          </xsl:if>
	   <xsl:if test="string($atomicType) = 'START' and string(/Process/Dataslot[@name=$dsname]/@type) ='DOCUMENT' and string(/Process[@type = 'BIZLOGIC'])">
            <xsl:attribute name="isStart">
              <xsl:value-of select="'true'"/>
            </xsl:attribute>
          </xsl:if>
        </bizsolo:getDS>
      </td>
    </tr>
  </xsl:template>


  <xsl:template name="ConnectionInfo">
  <xsl:param name="btnName"/>
  <xsl:param name="btnLabel"/>
  <xsl:param name="labelType"/>
  <xsl:param name="mode"/>
  <xsl:param name="linkName"/>
  
     <td class="ApBtnSpace">
      <input type="submit" name="{$btnName}" value="Complete" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">
        <xsl:attribute name="value">
          <xsl:text><![CDATA[<bizsolo:getLabel name=']]></xsl:text><xsl:value-of select="$linkName"/><xsl:text><![CDATA[' type=']]></xsl:text><xsl:value-of select="$labelType"/>
	  <xsl:if test="$mode"><xsl:text><![CDATA[' mode=']]></xsl:text><xsl:value-of select="$mode"/></xsl:if>   
	  <xsl:text><![CDATA['/>]]></xsl:text>
        </xsl:attribute>
      </input>
    </td>
  </xsl:template>


  <xsl:template name="FlowInfo">
    <!-- by flow info we mean any clickable entities which can move us to another page within the flow -->
    <!-- typical elements include submit buttons, hyperlinks and clickable images                      -->
    <br/>
    <xsl:choose>
      <xsl:when test="/Process[@type='BIZLOGIC']">
        <xsl:call-template name="BizSiteButtons"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="BizSoloFlowInfo"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


  <xsl:template name="BizSoloFlowInfo">
    <table class="ApSegDataTbl" width="100%">
    <!-- we choose to group connections by their type, otherwise the HTML will look bad in the browser	-->
    <!-- if attribute type is not defined for a link, then we have a SUBMIT button			-->
    <xsl:if test="/Process/Link[Source=$pageName][not(@type)] or $page/Dataoutput">   
        <tr>
    		<td class="ApButtonDarkBg">
    			<table cellspacing="0" cellpadding="0" align="center">
    				<tr>
    				<xsl:for-each select="/Process/Link[Source=$pageName][not(@type)]">
      				       
				       <xsl:variable name="btnLabel">
        					<xsl:choose>
         				 		<xsl:when test="not(Label)">
         				 			<xsl:value-of select="./@Name"/>
         				 		</xsl:when>
          						<xsl:otherwise>
            							<xsl:value-of select="Label"/>
          						</xsl:otherwise>
        					</xsl:choose>
      					</xsl:variable>
					<xsl:variable name="linkName" select="./@Name"/>
      					<xsl:call-template name="ConnectionInfo">
        					<xsl:with-param name="btnName">SB_Name</xsl:with-param>
        					<xsl:with-param name="btnLabel">
          						<xsl:value-of select="$btnLabel"/>
        					</xsl:with-param>
        					<xsl:with-param name="labelType">LINK</xsl:with-param>
						<xsl:with-param name="linkName"><xsl:value-of select="$linkName"/></xsl:with-param>
      					</xsl:call-template>
    				</xsl:for-each>
				
    				<xsl:if test="$page/Dataoutput">
      					<td class="ApBtnSpace">
        					<input type="button" name="SB_Name" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;resetForm();">
                                                  <xsl:attribute name="value">
                                                    <xsl:text disable-output-escaping="yes"><![CDATA[<bizsolo:getLabel name='RESET_LABEL' type='RESOURCE'/>]]></xsl:text>
                                                  </xsl:attribute>
                                                </input>
      					</td>
    				</xsl:if>
    				</tr>
    			</table>
    		</td>
    	</tr>
	</xsl:if>
	
	<xsl:choose>
	 <xsl:when test="/Process/Link[@type='text']/Source[text() = $pageName]">
	     <tr>
    		<td class="ApButtonDarkBg">
    			<table cellspacing="0" cellpadding="0" align="center">
    				<tr>
    				<xsl:for-each select="/Process/Link[@type='text']/Source[text() = $pageName]">
				<xsl:variable name="linkName" select="parent::*/@Name"/>  
				        <xsl:call-template name="TextLinks">
					    <xsl:with-param name="linkName" select="$linkName"/>
					</xsl:call-template>
    				</xsl:for-each>
    				</tr>
    			</table>
    		</td>
    	      </tr>
	 </xsl:when>
       </xsl:choose>
       
        <xsl:choose>
 	  <xsl:when test="/Process/Link[@type='image']/Source[text() = $pageName]">
	            <td class="ApButtonDarkBg">
                      <xsl:for-each select="/Process/Link[@type='image']/Source[text() = $pageName]">
		          <xsl:variable name="linkName" select="parent::*/@Name"/>
			  <xsl:variable name="imageSrc" select="parent::*/@value"/>
			  <xsl:variable name="toolTip" select="parent::*/Label[text()]"/>
			        <xsl:call-template name="ImageLinks">
			            <xsl:with-param name="linkName" select="$linkName"/>
				    <xsl:with-param name="imageSrc" select="$imageSrc"/>
				    <xsl:with-param name="toolTip" select="$toolTip"/>
			        </xsl:call-template>
                          </xsl:for-each>
                     </td>
	     </xsl:when>
	</xsl:choose> 
       
    </table>
  </xsl:template> 
  
  
 <xsl:template name="TextLinks">
   <xsl:param name="linkName"/>
   <td>
    <xsl:call-template name="anchor">
      <xsl:with-param name="linkName">
        <xsl:value-of select="$linkName"/>
      </xsl:with-param>
    </xsl:call-template>
    <bizsolo:getLabel type="LINK">
      <xsl:attribute name="name">
        <xsl:value-of select="$linkName"/>
      </xsl:attribute>
    </bizsolo:getLabel>
    <xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>
  </td>
  <td><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;&nbsp;]]></xsl:text></td>
  </xsl:template>


  <xsl:template name="ImageLinks">
  <xsl:param name="linkName"/>
  <xsl:param name="imageSrc"/>
  <xsl:param name="toolTip"/>
  <td>
    <xsl:call-template name="anchor">
      <xsl:with-param name="linkName" select="$linkName"/>
    </xsl:call-template>
    <xsl:call-template name="image">
       <xsl:with-param name="imageSrc" select="$imageSrc"/>
       <xsl:with-param name="toolTip" select="$toolTip"/>
    </xsl:call-template>            
    <xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>             
  </td>
  <td><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;&nbsp;&nbsp;]]></xsl:text></td>
  </xsl:template>


  <xsl:template name="anchor">
    <!-- this template will have to be rewritten in order to avoid this excessive use of CDATA
          However, the only way to do it will be to define a new custom JSP tag <bizsolo:a/> -->
    <xsl:param name="linkName"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[<a class="ApSegTblLnk"]]></xsl:text>
    <xsl:choose>
      <xsl:when test="../@submit='TRUE'">
        <xsl:text disable-output-escaping="yes"><![CDATA[ href="javascript:document.form.action='<bizsolo:getDS name=']]></xsl:text>
        <xsl:value-of select="$linkName"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[_target' wsName=']]></xsl:text>
        <xsl:value-of select="$pageFileName"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[' />';javascript:submit();this.onsubmit = new Function('return false');" >]]></xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text disable-output-escaping="yes"><![CDATA[ href="<bizsolo:getDS name=']]></xsl:text>
        <xsl:value-of select="$linkName"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[_target' wsName=']]></xsl:text>
        <xsl:value-of select="$pageFileName"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[' />" >]]></xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <xsl:template name="image">
    <!-- this template will have to be rewritten in order to avoid this excessive use of CDATA
         However, the only way to do it will be to define a new custom JSP tag <bizsolo:image/> -->
    <xsl:param name="imageSrc"/>   
    <xsl:param name="toolTip"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[<img src="]]></xsl:text><xsl:value-of select="$imageSrc"/><xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>
    <xsl:if test="string-length($toolTip) != 0">
       <xsl:text disable-output-escaping="yes"><![CDATA[ alt="]]></xsl:text><xsl:value-of select="$imageSrc"/><xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>
       <xsl:text disable-output-escaping="yes"><![CDATA[ title="]]></xsl:text><xsl:value-of select="$toolTip"/><xsl:text disable-output-escaping="yes"><![CDATA["]]></xsl:text>
    </xsl:if>
     <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
    </xsl:template>


  <xsl:template name="BizSiteButtons">
    <script language="JavaScript">
      <![CDATA[
      <!--
           function AlertReassign()
           {
             if (document.form.elements['bizsite_assigneeName'].value == '' )
             {
               alert('Please provide assignee name!');
               document.form.elements['bizsite_assigneeName'].focus();
               return false;
             } else {
               return true;
             }
           }

           var uploadWnd;
           var param;
           function openDocAttWin( slotName,sesID, ptname, piname, docurl, docServer, readonly, ismultiline, appendwith, isStart )
           {
             param = 'bzsid=' + sesID;
             param += '&pt=' + ptname;
             param += '&pi=' + piname;
             param += '&ds=' + slotName;
             param += '&docurl=' + docurl;
             param += '&readonly=' + readonly;
             param += '&ismultiline=' + ismultiline;
             param += '&appendwith=' + appendwith;
             param += '&isPICreation=' + isStart;
             uploadWnd = window.open(docServer + '/BizSite.DocAttacher?' + param,
             'uploadWnd',
             'width=400,height=480,scrollbars=yes,resizable=yes,status=0' );
             uploadWnd.focus();
           }
      //-->
      ]]>
    </script>
    <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
      <tbody>
	     <xsl:if test="$isWorkTimeEnabled='true'">
	       <xsl:call-template name="WorkTimeFooter"/>
	     </xsl:if>
      <tr align="center"> 
        <td class="ApButtonDarkBg" width="63%">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <xsl:choose>
                <xsl:when test="$atomicType='START'">
                  <td class="ApBtnSpace"> 
                    <input type="submit" name="bizsite_completeTask" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='CREATE_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  <td class="ApBtnSpace"> 
                   <input type="button" name="bizsite_reset" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="resetForm()">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='RESET_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                   </input>
                  </td>
                  <td class="ApBtnSpace"> 
                    <input type="button" name="Submit1323" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="cancel()">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='CANCEL_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                </xsl:when>
                <xsl:otherwise>
                  <td class="ApBtnSpace"> 
                    <input type="submit" name="bizsite_completeTask" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='COMPLETE_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  <td class="ApBtnSpace"> 
                    <input type="submit" name="bizsite_saveTask" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='SAVE_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  <bizsolo:isAssigned>
                  <xsl:if test="$isSkippable='true'">
                  <td class="ApBtnSpace"> 
                    <input type="submit" name="bizsite_skip" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='SKIP_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  </xsl:if>
                  </bizsolo:isAssigned>
                  <td class="ApBtnSpace"> 
                    <input type="button" name="bizsite_reset" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="resetForm()">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='RESET_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  <td class="ApBtnSpace"> 
                    <input type="button" name="Submit1323" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="cancel()">
                      <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='CANCEL_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                    </input>
                  </td>
                  <bizsolo:isAssigned>
                  <xsl:if test="not($page/Option) or $page/Option[@name='Reassign']/@visible='true'">
                    <!-- $page/Option is a temporary workaround -->
                    <td class="ApBtnSpace">&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</td>
                    <td class="ApBtnSpace"> 
                      <input  class="ApInptSelect" type="text" name="bizsite_assigneeName" size="20"/>
                    </td>
                    <td class="ApBtnSpace"> 
                      <a href="javascript://" onClick="setUserControl(document.form.bizsite_assigneeName);searchUser()">
                        <img src="{concat($themeURL,'/images/icon_edit_user_search_single.gif')}" width="16" height="16" border="0" title="Search Creator"/>
                      </a>
                    </td>
                    <td class="ApBtnSpace"> 
                      <input type="submit" name="bizsite_reassignTask" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover'" onMouseOut="this.className='ApScrnButton'" onClick="clickedButton=this.name;return AlertReassign();this.onsubmit = new Function('return false');">
                        <xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='REASSIGN_LABEL' type='RESOURCE'/>]]></xsl:attribute>
                      </input>
                    </td>
                  </xsl:if>
		    <xsl:if test="$isTaskEnabled='true'">
		    <td class="ApBtnSpace"> 
		      <input type="button" style="width:150px" name="bizsite_createCollaboration" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="goToCreateCollaboration();">
			<xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='CREATECOLLABORATION_LABEL' type='RESOURCE'/>]]></xsl:attribute>
		      </input>
		    </td>
		    <td class="ApBtnSpace"> 
		      <input type="button" style="width:150px" name="bizsite_viewCollaboration" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="goToViewCollaboration();">
			<xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='VIEWCOLLABORATION_LABEL' type='RESOURCE'/>]]></xsl:attribute>
		      </input>
		    </td>
		    </xsl:if>
		    <xsl:if test="$isEmailEnabled='true'">
		    <td class="ApBtnSpace"> 
		      <input type="button" name="bizsite_emailButton" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="goToEmail();">
			<xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='EMAIL_LABEL' type='RESOURCE'/>]]></xsl:attribute>
		      </input>
		    </td>
		    </xsl:if>
		    <xsl:if test="$isImEnabled='true'">
		    <td class="ApBtnSpace"> 
		      <input type="button" name="bizsite_chatButton" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="goToChat();">
			<xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='CHAT_LABEL' type='RESOURCE'/>]]></xsl:attribute>
		      </input>
		    </td>
		    </xsl:if>
		    <xsl:if test="$isNotesEnabed='true'">
		    <td class="ApBtnSpace"> 
		      <input type="button" name="bizsite_notes" class="ApScrnButton" onMouseOver="this.className='ApScrnButtonHover';" onMouseOut="this.className='ApScrnButton';" onClick="goToNotes();">
			<xsl:attribute name="value"><![CDATA[<bizsolo:getLabel name='NOTES_LABEL' type='RESOURCE'/>]]></xsl:attribute>
		      </input>
		    </td>
		    </xsl:if>
		  </bizsolo:isAssigned>  
                </xsl:otherwise>
              </xsl:choose>
            </tr>
          </table>
        </td>
      </tr>
      </tbody>
    </table>
  </xsl:template>

 <xsl:template name="WorkTimeFooter">
   <input type="hidden" name="resources.ActualProcessingTime" value="" />
   <tr>
     <td class="ApSegDataLabel" width="64%">
     	<div align="center">
     	<b>Work Time:    </b> Day(s): <input class="ApInptTxt" type="text" id="bizsite_ActualProcessingTime_days" name="bizsite_ActualProcessingTime_days" size="9" /> 
	<script>addField("bizsite_ActualProcessingTime_days", "number",false);</script>
	          <div style="display:none" id="bizsite_ActualProcessingTime_daysError"><div><font color="red"><span class="error" id="bizsite_ActualProcessingTime_daysErrorMsg"></span><a href="#" onclick="bizsite_ActualProcessingTime_daysErrorMsgClose();return false;"><img border="0" src="{concat($themeURL,'/images/close.gif')}" /></a></font></div></div> Hour(s): <input class="ApInptTxt" type="text" id="bizsite_ActualProcessingTime_hours" name="bizsite_ActualProcessingTime_hours" size="9" />
	<script>addField("bizsite_ActualProcessingTime_hours", "number",false);</script>
	          <div style="display:none" id="bizsite_ActualProcessingTime_hoursError"><div><font color="red"><span class="error" id="bizsite_ActualProcessingTime_hoursErrorMsg"></span><a href="#" onclick="bizsite_ActualProcessingTime_hoursErrorMsgClose();return false;"><img border="0" src="{concat($themeURL,'/images/close.gif')}"/></a></font></div></div> Minute(s): <input class="ApInptTxt" type="text" id="bizsite_ActualProcessingTime_minutes" name="bizsite_ActualProcessingTime_minutes" size="9" />
	<script>addField("bizsite_ActualProcessingTime_minutes", "number",false);</script>
	          <div style="display:none" id="bizsite_ActualProcessingTime_minutesError"><div><font color="red"><span class="error" id="bizsite_ActualProcessingTime_minutesErrorMsg"></span><a href="#" onclick="bizsite_ActualProcessingTime_minutesErrorMsgClose();return false;"><img border="0" src="{concat($themeURL,'/images/close.gif')}"/></a></font></div></div>
              <br clear="all" />
     	</div>
     </td>
   </tr>
 </xsl:template>

  <xsl:template name="ExtraInfo">
    <xsl:choose>
      <xsl:when test="/Process[@type='BIZLOGIC']">
        <input type="hidden" name="_PageName">
          <xsl:attribute name="value">
            <xsl:value-of select="$pageFileName"/>
          </xsl:attribute>
        </input>
        <xsl:text disable-output-escaping="yes">
          <![CDATA[<%if(bean.getPropString("workitemName") != null) {%><input name="_WorkitemName" type="hidden" value="<%= URLHexCoder.encode(bean.getPropString("workitemName")) %>"/><input name="_WorkitemId" type="hidden" value="<%= bean.getPropString("workitemId") %>"/><%}%>]]>
        </xsl:text>
        <xsl:text disable-output-escaping="yes">
          <![CDATA[<input name="_ProcessTemplateName" type="hidden" value="<%=bean.getPropString("ptName") %>"/><%}%>]]>
        </xsl:text>
     </xsl:when>
      <xsl:otherwise>
        <input type="hidden" name="crtPage">
          <xsl:attribute name="value">
            <xsl:value-of select="$pageFileName"/>
          </xsl:attribute>
        </input>
        <input type="hidden" name="crtApp">
          <xsl:attribute name="value">
            <xsl:value-of select="$appName"/>
          </xsl:attribute>
        </input>
        <input type="hidden" name="activityMode">
          <xsl:attribute name="value">procReq</xsl:attribute>
        </input>
        <xsl:variable name="controllerName" select="/Process/AtomicWS[AtomicType='START']/@name"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[<input type='hidden' name='nextPage' value='<%=response.encodeUrl("]]></xsl:text>
        <xsl:value-of select="concat($controllerName,'.jsp')"/>
        <xsl:text disable-output-escaping="yes"><![CDATA[") %>' >]]></xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  
  <xsl:template name="Footer">
    <xsl:comment>Add your footer here</xsl:comment>
  </xsl:template>
  
  
  <xsl:template name="JavaScript">
    <xsl:text disable-output-escaping="yes"><![CDATA[<% pageContext.setAttribute( "commonJavaScriptUrl", request.getContextPath()+"/bpmportal/javascript/"); %>]]></xsl:text>
    <xsl:text>&#10;</xsl:text>
    <xsl:variable name="commonJavaScriptUrl">
        <xsl:text disable-output-escaping="yes"><![CDATA[<c:out value='${commonJavaScriptUrl}'/>]]></xsl:text>
    </xsl:variable>

    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'validate.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'validate2.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'customValidation.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'prototype.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'effects.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'scriptaculous.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'controls.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'dragdrop.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'engine.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'util.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'pwr.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'jscalendar/calendar.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'jscalendar/calendar-en.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'jscalendar/calendar-setup.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'fvalidate/fValidate.config.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'fvalidate/fValidate.core.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'fvalidate/fValidate.lang-enUS.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
     <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'fvalidate/fValidate.validators.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
      <script language="JavaScript">
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'fvalidate/pValidate.js')"/>
      </xsl:attribute>
    </script><xsl:text>&#10;</xsl:text>
    <script language="JavaScript"> 
      <xsl:attribute name="src">
        <xsl:value-of select="concat($commonJavaScriptUrl, 'document.js')"/>
      </xsl:attribute> 
    </script><xsl:text>&#10;</xsl:text>
    
    <xsl:variable name="editableDs">
      <xsl:for-each select="$page/Format[@editable='true']">
        <xsl:variable name="dsName" select="./@input"/>
        <xsl:if test="/Process/Dataslot[@name=$dsName][@type='DOCUMENT' or @type='XML']">
          <xsl:text>TRUE</xsl:text>
        </xsl:if>
      </xsl:for-each>
    </xsl:variable>
    <script language="JavaScript">
      <![CDATA[
      <!--
           var uploadWnd;
           var param;
           function openDocAttWin( slotName,sesID, ptname, piname, docurl, docServer, readonly, ismultiline, appendwith, isStart  )
           {
             param = 'bzsid=' + sesID;
             param += '&pt=' + ptname;
             param += '&pi=' + piname;
             param += '&ds=' + slotName;
             param += '&docurl=' + docurl;
             param += '&readonly=' + readonly;
         param += '&ismultiline=' + ismultiline;
         param += '&appendwith=' + appendwith;
         param += '&isPICreation=' + isStart;
             uploadWnd = window.open(docServer + '/BizSite.DocAttacher?' + param,
             'uploadWnd',
             'width=400,height=480,scrollbars=yes,resizable=yes,status=0' );
             uploadWnd.focus();
           }

           function submit() {
             if (validate()) {
               document.form.submit();
             }

           }
           
           function onSuccess() {
           }
      //-->
      ]]>
    </script><xsl:text>&#10;</xsl:text>
    <xsl:if test="$docInterface='JavaApplet'">
      <xsl:if test="starts-with($editableDs,'TRUE')">
         <script language="JavaScript">
           <xsl:attribute name="src">
             <xsl:value-of select="concat($commonJavaScriptUrl, 'document.js')"/>
           </xsl:attribute>
         </script><xsl:text>&#10;</xsl:text>
      </xsl:if>
    </xsl:if>
  </xsl:template>
</xsl:transform>
