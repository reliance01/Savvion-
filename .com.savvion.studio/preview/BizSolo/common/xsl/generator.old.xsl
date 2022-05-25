<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:bizsolo="http://www.savvion.com/sbm/BizSolo"> 
	<xsl:output method="html" version="4.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="pageName"/>
	<xsl:param name="pageFileName"/>
	<xsl:param name="formAction"/>
	<xsl:param name="BizSoloRoot"/>
	<xsl:param name="themeURL"/>
	<xsl:variable name="page" select="/Process/AtomicWS[@name=$pageName]"/>
	<xsl:variable name="appName" select="/Process/@name"/>

	<xsl:template name="HtmlSection" match="/">
		<html>
			<head>
				<bizsolo:link rel="stylesheet" />
				<title>
					<xsl:value-of select="concat($appName,'/',$pageName)"/>
				</title>
			</head>
			<body class="ApBody" bgcolor="#FFFFFF">
				<xsl:text>&#010;</xsl:text>
				<xsl:call-template name="JavaScript" />
				<xsl:text>&#010;</xsl:text>
				<xsl:call-template name="WorkitemHeader"/>
				<xsl:text>&#010;</xsl:text>
				<xsl:call-template name="FormInfo"/>
				<xsl:call-template name="Footer"/>
			</body>
		</html>
	</xsl:template>


	<xsl:template name="WorkitemHeader">
		<xsl:variable name="useInstruction" select="$page/Option[@name='Instructions']"/>
		<xsl:variable name="usePriority" select="$page/Option[@name='Priority']"/>
		<xsl:variable name="useStartDate" select="$page/Option[@name='StartDate']"/>
		<xsl:variable name="useDueDate" select="$page/Option[@name='DueDate']"/>
		
		<xsl:text disable-output-escaping="yes"><![CDATA[<bizsolo:if test="<%=bean.getPropString(\"workitemName\") != null %>" >]]></xsl:text>
		<xsl:text>&#010;</xsl:text>

		<table width="90%" cellpadding="3" cellspacing="3" border="0">
			<tr>
				<td class="ApSegTitle" align="center" nowrap="nowrap">
					<xsl:text>Task: </xsl:text>
					<bizsolo:getDS name="workitemName"/>
				</td>
			</tr>
		</table>
		<table width="90%" cellpadding="3" cellspacing="3" border="0">
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
		<xsl:text>&#010;</xsl:text>
		<xsl:text disable-output-escaping="yes"><![CDATA[</bizsolo:if>]]></xsl:text>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>
	
	<xsl:template name="ShowOption">
		<xsl:param name="dsname"/>
		<xsl:param name="colspan"/>
		<xsl:variable name="toUpperDS" select="translate($dsname,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
		<td class="ApSegValRt" width="2%" align="left" nowrap="nowrap">
			<bizsolo:getLabel>
				<xsl:attribute name="name"><xsl:value-of select="concat('BIZSITE_',$toUpperDS,'_LABEL')"/></xsl:attribute>
				<xsl:attribute name="type">RESOURCE</xsl:attribute>
			</bizsolo:getLabel>
		</td>
		<td class="ApSegFieldLft" width="5%" align="left" nowrap="nowrap">
			<xsl:if test="not($colspan='')">
				<xsl:attribute name="colspan"><xsl:value-of select="$colspan"/></xsl:attribute>
			</xsl:if>
			<bizsolo:getDS>
				<xsl:attribute name="name"><xsl:value-of select="concat('bizsite_',$dsname)"/></xsl:attribute>
			</bizsolo:getDS>
		</td>
	</xsl:template>
	
	<xsl:template name="FormInfo">
		<xsl:variable name="editableDs">
			<xsl:for-each select="$page/Format[@editable='true']">
				<xsl:variable name="dsName" select="@input"/>
				<xsl:if test="/Process/Dataslot[@name=$dsName][@type='DOCUMENT']">
					<xsl:text>TRUE</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:text disable-output-escaping="yes"><![CDATA[<form method="post" name="form" action='<%=response.encodeURL("]]></xsl:text>
		<xsl:value-of select="$formAction" />
		<xsl:text disable-output-escaping="yes"><![CDATA[") %>']]></xsl:text>
		<xsl:if test="$page/Format[@required='true'] or starts-with($editableDs,'TRUE')">
			<xsl:text disable-output-escaping="yes"><![CDATA[ onsubmit='return validate()']]></xsl:text>
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
			<xsl:text>&#010;</xsl:text>
			<xsl:call-template name="DsTable"/>
			<xsl:call-template name="FlowInfo"/>
			<xsl:call-template name="ExtraInfo"/>
		<xsl:text disable-output-escaping="yes">	<![CDATA[</form>]]>	</xsl:text>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>

	<xsl:template name="DsTable">
		<xsl:if test="$page/Format[@input]">
			<br>
				<table border="0" class="ApSegDataTbl">
					<xsl:call-template name="AllDs"/>
				</table>
			</br>
		</xsl:if>
	</xsl:template>

	<xsl:template name="AllDs">
		<xsl:for-each select="$page/Format">
			<xsl:call-template name="DS"/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="DS">
		<xsl:variable name="dsname" select="@input"/>
		<tr>
			<td class="ApSegValRt">
				<bizsolo:getLabel type="DATASLOT">
					<xsl:if test="/Process[@type='BIZLOGIC']">
						<xsl:attribute name="mode">BizSite</xsl:attribute>
					</xsl:if>
					<xsl:attribute name="name"><xsl:value-of select="$dsname"/></xsl:attribute>
					<xsl:attribute name="wsName"><xsl:value-of select="$pageName"/></xsl:attribute>
				</bizsolo:getLabel>
			</td>
		<td class="ApSegFieldLft">
		<bizsolo:getDS >
			<xsl:attribute name="name"><xsl:value-of select="$dsname"/></xsl:attribute>
			<xsl:if test="@required">
				<xsl:attribute name="required"><xsl:value-of select="@required"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@editable">
				<xsl:attribute name="writePerm"><xsl:value-of select="@editable"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="/Process/Dataslot[@name=$dsname][@type='DOCUMENT']">
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
			<xsl:if test="@size">
				<xsl:attribute name="size"><xsl:value-of select="@size"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@length">
				<xsl:attribute name="maxlength"><xsl:value-of select="@length"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@width">
				<xsl:attribute name="cols"><xsl:value-of select="@width"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@height">
				<xsl:attribute name="rows"><xsl:value-of select="@height"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="@password">
				<xsl:attribute name="password"><xsl:value-of select="@password"/></xsl:attribute>
			</xsl:if>
			<xsl:variable name="precision" select="/Process/Dataslot[@name=$dsname][@precision]"/>
			<xsl:if test="$precision">
				<xsl:attribute name="precision"><xsl:value-of select="$precision"/></xsl:attribute>
			</xsl:if>
			<xsl:variable name="scale" select="/Process/Dataslot[@name=$dsname][@scale]"/>
			<xsl:if test="$scale">
				<xsl:attribute name="scale"><xsl:value-of select="$scale"/></xsl:attribute>
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
		<td>
			<input type="submit" onclick="clickedButton=this.name">
				<xsl:attribute name="name">
					<xsl:value-of select="$btnName"/>
				</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:text>&lt;bizsolo:getLabel name='</xsl:text>
					<xsl:value-of select="$btnLabel"/>
					<xsl:text>' type='</xsl:text>
					<xsl:value-of select="$labelType"/>
					<xsl:text>'</xsl:text>
					<xsl:if test="$mode">
						 <xsl:text> mode='</xsl:text>
						 <xsl:value-of select="$mode"/>
						 <xsl:text>'</xsl:text>
					</xsl:if>
					<xsl:text>/&gt;</xsl:text>
				</xsl:attribute>
			</input>
		</td>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>

	<xsl:template name="FlowInfo">
		<!-- by flow info we mean any clickable entities which can move us to another page within the flow 
			typical elements include submit buttons, hyperlinks and clickable images -->
		<table border="0" class="ApSegDataTbl">
		<xsl:text>&#010;</xsl:text>
		<xsl:choose>
			<xsl:when test="/Process[@type='BIZLOGIC']">
				<xsl:call-template name="BizSiteButtons"/>
			</xsl:when>
			<xsl:when test="/Process[@type='BIZSOLO']">
				<xsl:call-template name="BizSoloFlowInfo"/>
			</xsl:when>
		</xsl:choose>
		</table>
	</xsl:template>

	<xsl:template name="BizSoloFlowInfo">
		<!-- we choose to group connections by their type, otherwise the HTML will look bad in the browser-->
		<!-- if attribute type is not defined for a link, then we have a SUBMIT button-->
		<xsl:for-each select="/Process/Link[Source=$pageName][not(@type)]">
			<xsl:variable name="btnLabel">
				<xsl:choose>
					<xsl:when test="not(Label)">SUBMIT_LABEL</xsl:when>
					<xsl:otherwise><xsl:value-of select="@Name"/></xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="labelType">
				<xsl:choose>
					<xsl:when test="not(Label)">RESOURCE</xsl:when>
					<xsl:otherwise>LINK</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:call-template name="ConnectionInfo">
				<xsl:with-param name="btnName">SB_Name</xsl:with-param>
				<xsl:with-param name="btnLabel"><xsl:value-of select="$btnLabel"/></xsl:with-param>
				<xsl:with-param name="labelType"><xsl:value-of select="$labelType"/></xsl:with-param>
			</xsl:call-template>
			<xsl:text>&#010;</xsl:text>
		</xsl:for-each>
		<xsl:if test="$page/Dataoutput">
			<td>
				<input type="reset" name="SB_Name"/>
			 	<xsl:text>&#010;</xsl:text>
			</td>
		</xsl:if>
		<xsl:for-each select="/Process/Link[Source=$pageName][@type='TEXT']">
			<xsl:call-template name="TextLinks"/>
			<xsl:text>&#010;</xsl:text>
		</xsl:for-each>
		<xsl:for-each select="/Process/Link[Source=$pageName][@type='IMAGE']">
			<xsl:call-template name="ImageLinks"/>
			<xsl:text>&#010;</xsl:text>
		</xsl:for-each>
	</xsl:template>	
	

	<xsl:template name="TextLinks">
		<xsl:variable name="linkName" select="@Name"/>
		<xsl:call-template name="anchor">
			<xsl:with-param name="linkName"><xsl:value-of select="$linkName"/></xsl:with-param>
		</xsl:call-template>
		<bizsolo:getLabel type="LINK">
			<xsl:attribute name="name"><xsl:value-of select="$linkName"/></xsl:attribute>
		</bizsolo:getLabel>
		<xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>
	</xsl:template>

	<xsl:template name="ImageLinks">
		<xsl:variable name="linkName" select="@Name"/>
		<xsl:call-template name="anchor">
			<xsl:with-param name="linkName"><xsl:value-of select="$linkName"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="image">
			<xsl:with-param name="linkName"><xsl:value-of select="$linkName"/></xsl:with-param>
		</xsl:call-template>		
		<xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>		
	</xsl:template>
	
	<xsl:template name="anchor">
			<!-- this template will have to be rewritten in order to avoid this excessive use of CDATA
		      However, the only way to do it will be to define a new custom JSP tag <bizsolo:a/> -->
		<xsl:param name="linkName"/>
		<xsl:text disable-output-escaping="yes"><![CDATA[<a class="ApSegTblLnk"]]></xsl:text>
		<xsl:if test="@submit='TRUE'">
			<xsl:text disable-output-escaping="yes"><![CDATA[ onclick="document.form.submit();"]]></xsl:text>
		</xsl:if>
		<xsl:text disable-output-escaping="yes"><![CDATA[ href="<bizsolo:getDS name=']]></xsl:text>
		<xsl:value-of select="$linkName"/>
		<xsl:text disable-output-escaping="yes"><![CDATA[_target' />" >]]></xsl:text>
	</xsl:template>

	<xsl:template name="image">
			<!-- this template will have to be rewritten in order to avoid this excessive use of CDATA
		      However, the only way to do it will be to define a new custom JSP tag <bizsolo:image/> -->
		<xsl:param name="linkName"/>
		<xsl:text disable-output-escaping="yes"><![CDATA[<img src="<bizsolo:getDS name=']]></xsl:text>
		<xsl:value-of select="$linkName"/>
		<xsl:text disable-output-escaping="yes"><![CDATA[_imgSrc' />" alt="<bizsolo:getLabel type="LINK" name="]]></xsl:text>
		<xsl:value-of select="$linkName"/>
		<xsl:text disable-output-escaping="yes"><![CDATA["/> ">]]></xsl:text>
	</xsl:template>

	<xsl:template name="BizSiteButtons">
		<table border="0">
			<tr>
			<xsl:choose>
				<xsl:when test="$page/AtomicType[@template='START']">
					<td>
					<xsl:call-template name="ConnectionInfo">
						<xsl:with-param name="btnName">bizsite_createInstance</xsl:with-param>
						<xsl:with-param name="btnLabel">CREATE_LABEL</xsl:with-param>
						<xsl:with-param name="labelType">RESOURCE</xsl:with-param>
						<xsl:with-param name="mode">BizSite</xsl:with-param>
					</xsl:call-template>
					</td>
					<td>
						<input type="reset" name="SB_Name"/> 
					</td>
				</xsl:when>
				<xsl:otherwise>
					<td>
					<xsl:call-template name="ConnectionInfo">
						<xsl:with-param name="btnName">bizsite_completeTask</xsl:with-param>
						<xsl:with-param name="btnLabel">COMPLETE_LABEL</xsl:with-param>
						<xsl:with-param name="labelType">RESOURCE</xsl:with-param>
						<xsl:with-param name="mode">BizSite</xsl:with-param>
					</xsl:call-template>
					</td><td>
					<xsl:call-template name="ConnectionInfo">
						<xsl:with-param name="btnName">bizsite_saveTask</xsl:with-param>
						<xsl:with-param name="btnLabel">SAVE_LABEL</xsl:with-param>
						<xsl:with-param name="labelType">RESOURCE</xsl:with-param>
						<xsl:with-param name="mode">BizSite</xsl:with-param>
					</xsl:call-template>
					</td>
				</xsl:otherwise>
			</xsl:choose>
			</tr>
			<xsl:variable name="dontUseReassign" select="$page/Option[@name='Reassign'][@visible='false']"/>
			<xsl:if test="not($dontUseReassign)">
				<tr>
					<td class="ApSegFieldLft" width="2%" align="left" nowrap="nowrap">
						<input type="text" name="bizsite_assigneeName" size="20"/>
					</td>
					<td>
						<input type="submit" name="bizsite_reassignTask" onclick="clickedButton=this.name; return AlertReassign()">
							<xsl:attribute name="value">
								<xsl:text>&lt;bizsolo:getLabel name='REASSIGN_LABEL' type='RESOURCE' mode='BizSite'/&gt;</xsl:text>
							</xsl:attribute>
						</input>
					</td>
				</tr>
			</xsl:if>
		</table>
	</xsl:template>
	
	<xsl:template name="ExtraInfo">
		<xsl:choose>
			<xsl:when test="/Process[@type='BIZLOGIC']">
				<input type="hidden" name="_PageName">
					<xsl:attribute name="value"><xsl:value-of select="$pageFileName"/></xsl:attribute>
				</input>
			</xsl:when>
			<xsl:when test="/Process[@type='BIZSOLO']">
				<input type="hidden" name="crtPage">
					<xsl:attribute name="value"><xsl:value-of select="$pageFileName"/></xsl:attribute>
				</input>
				<xsl:text>&#010;</xsl:text>
				<input type="hidden" name="crtApp">
					<xsl:attribute name="value"><xsl:value-of select="$appName"/></xsl:attribute>
				</input>
				<xsl:text>&#010;</xsl:text>
				<input type="hidden" name="activityMode">
					<xsl:attribute name="value">procReq</xsl:attribute>
				</input>
				<xsl:text>&#010;</xsl:text>
				<xsl:variable name="controllerName" select="/Process/AtomicWS[AtomicType='START']/@name"/>
				<xsl:text disable-output-escaping="yes"><![CDATA[<input type='hidden' name='nextPage' value='<%=response.encodeUrl("]]></xsl:text>
				<xsl:value-of select="concat($controllerName,'.jsp')"/>
				<xsl:text disable-output-escaping="yes"><![CDATA[") %>' >]]></xsl:text>
			</xsl:when>
		</xsl:choose>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>
	
	<xsl:template name="Footer">
		<xsl:comment>Add your footer here</xsl:comment>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>

	<xsl:template name="JavaScript">
		<xsl:variable name="BizSoloJS">/sbm/BizSolo/common/js/</xsl:variable>
		<xsl:variable name="jsLibURL">/sbm/bpmportal/javascript</xsl:variable>

		<script language="JavaScript">
			<xsl:attribute name="src">
				<xsl:value-of select="concat($jsLibURL,'/validate.js')"/>
			</xsl:attribute>
		</script>
		<xsl:text>&#010;</xsl:text>
		<script language="JavaScript">
			<xsl:attribute name="src">
				<xsl:value-of select="concat($jsLibURL,'/helpers.js')"/>
			</xsl:attribute>
		</script>
		<xsl:text>&#010;</xsl:text>
		<script language="JavaScript">
			<xsl:text disable-output-escaping="yes">var bizsiteroot='</xsl:text>
			<xsl:value-of select="$themeURL"/>
			<xsl:text disable-output-escaping="yes">';</xsl:text>
		</script>
		<xsl:text>&#010;</xsl:text>
		<xsl:variable name="dontUseReassign" select="$page/Option[@name='Reassign'][@visible='false']"/>
		<xsl:if test="/Process[@type='BIZLOGIC'] and not($dontUseReassign)">
			<script language="JavaScript">
				<xsl:attribute name="src">
					<xsl:value-of select="concat($BizSoloJS,'alertReassign.js')"/>
				</xsl:attribute>
			</script>
			<xsl:text>&#010;</xsl:text>
		</xsl:if>
		<xsl:variable name="editableDs">
			<xsl:for-each select="$page/Format[@editable='true']">
				<xsl:variable name="dsName" select="@input"/>
				<xsl:if test="/Process/Dataslot[@name=$dsName][@type='DOCUMENT']">
					<xsl:text>TRUE</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:if test="starts-with($editableDs,'TRUE')">
			<script language="JavaScript">
				<xsl:attribute name="src">
					<xsl:value-of select="concat($BizSoloJS,'uploadApplet.js')"/>
				</xsl:attribute>
			</script>
		</xsl:if>
		<xsl:text>&#010;</xsl:text>
	</xsl:template>

</xsl:transform>
