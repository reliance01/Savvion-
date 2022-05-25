<!-- bizsolo generate="false" -->
<%--  This JSP has been generated by using from xsl version 9.0 at Fri Mar 30 11:21:51 IST 2018 --%>
<!DOCTYPE html>
<html xmlns:bizsolo="http://www.savvion.com/sbm/BizSolo" xmlns:sbm="http://www.savvion.com/sbm" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:xalan="http://xml.apache.org/xalan" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:sfe="http://www.savvion.com/sbm/sfe" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head><META http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- Internet Explorer Compatibility -->
<meta http-equiv="X-UA-Compatible" content="IE=11" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />  
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.savvion.BizSolo.Server.*,com.savvion.BizSolo.beans.*,com.savvion.sbm.util.DatabaseMapping,java.util.Vector,java.util.Locale" %>
<%@ page import="com.savvion.sbm.bizsolo.util.session.*,java.net.*" %>
<%@ page errorPage="/BizSolo/common/jsp/expcontroller.jsp" %>
<%@ taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form"%>
<%@ taglib prefix="sfe" uri="http://jmaki/v1.0/jsp" %>
<%@ include file="/BizSolo/common/jsp/include_i18n_msgs.jsp" %>
  <jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"></jsp:useBean>
  <jsp:useBean id="factoryBean" class="com.savvion.BizSolo.beans.EPFactoryBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>
<%! String _PageName = "ResumeSuspendedItems"; %>
<%! String __webAppName = "ResumeSuspendedItems"; %>
<% pageContext.setAttribute( "contextPath", request.getContextPath()+"/"); %>
<% pageContext.setAttribute( "maxMultiLineLength", DatabaseMapping.self().getSQLSize(java.lang.String.class));  %>
<%java.util.List<String> priorities = com.savvion.sbm.bpmportal.util.PortalUtil.self().getPriorities();%>
<% pageContext.setAttribute( "_priorities",priorities); %>

<title>Resume Suspended Items</title>
<%boolean isStandaloneBS = (bizManage == null || bizManage.getName() == null || "".equals(bizManage.getName()) || bizManage.getLocale() == null);Locale myLocale = (!isStandaloneBS) ? bizManage.getLocale() : request.getLocale();%>
<!-- Javascript -->
<script language="JavaScript"> var getLocalizedString = parent.getLocalizedString; </script>
<form:includes contextPath="${contextPath}"/>
<script language="JavaScript">
 Ext.BLANK_IMAGE_URL = '<c:out value='${contextPath}'/>bpmportal/javascript/ext/resources/images/default/s.gif';
	 
	  var isIFrame = <%= (PublicResources.INTERACTIVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE)) || PublicResources.SLAVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE))) ? true : false %>;
	  

	function getContextPath(){
        return "<%=request.getContextPath()%>";
    }
</script>


<sbm:setLocale value="<%= bizManage.getLocale() %>"></sbm:setLocale>
<% try{ %><sbm:setBundle scope="page" basename="ResumeSuspendedItems/properties/ResumeSuspendedItems"></sbm:setBundle><% } catch(Exception e){}%>
<bizsolo:getApplicationResources baseName="ResumeSuspendedItems/properties/ResumeSuspendedItems"/></head>
<body class="apbody yui-skin-sam" onLoad="setCheckBoxStyleForIE();hideControls();beforeInitControls();initControls();initTabs();sbm.utils.onDOMReady();">
<form method="post" name="form" onsubmit="return sbm.utils.onFormSubmit();">
<div id="northDiv" style="height:0px;display:none;"><bizsolo:xsrf/></div><% /* Workaround, activityName will disappear in the future */ %>
<% String activityName = bean.getPropString("workitemName"); %>
<div id="resultDiv">
<div style='visibility:hidden;display:none' class='vBoxClass' name='errors' id='errors'></div>
<input name="crtPage" type="hidden" value="ResumeSuspendedItems"><input name="crtApp" type="hidden" value="ResumeSuspendedItems"><input name="activityMode" type="hidden" value="procReq"><input type="hidden" name="nextPage" value="Start.jsp">
<input name="_yahoo_flow_button" type="hidden" value=''>
<!-- Content --> 

    
<!-- Header -->
<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td class="ApSegTblInBg">
<table width="100%" cellpadding="4" align="center" cellspacing="0" border="0">
<tr>

<td class="ApSegTitle" align="center"><bizsolo:choose><bizsolo:when test='<%=bean.getPropString(\"workitemName\") != null %>'><bizsolo:getDS name="workitemName"></bizsolo:getDS></bizsolo:when><bizsolo:otherwise><%=_PageName%></bizsolo:otherwise></bizsolo:choose></td>
</tr>
</table>
<table class="ApSegDataTbl" width="100%" cellspacing="1" cellpadding="4" border="0">
<tr>
</tr>
</table>
</td>
</tr>
</table>

    
  <link rel="stylesheet" href="css/bootstrap.css">

  <link rel="stylesheet" href="css/jquery-ui.min.css">

  <script src="js/jquery-3.3.1.min.js"></script>

  <script src="js/popper.min.js"></script>

  <script src="js/bootstrap.min.js"></script> 

  <script src="js/jquery-ui.min.js"></script> 

  <script src="js/calldeskResumeWorkStep.js"></script>

<%

long totalCount = bean.getPropLong("suspendedItemsCount");

java.util.Map wsDataMap = bean.getPropMap("suspendedItems");

System.out.println("wsDataMap on UI::::: "+wsDataMap.toString());

%>



<!-- Content Start -->

<div class="container">

    <div class="row" style="margin-top:40px">

        <div class="col-md-4"></div>

        <div class="col-md-4 align-self-center">

            <div style="width:100%;text-align:left;background-color:rgb(200, 216, 234)">       

	<h3>Total Suspended Items: <span style="color:red"><%= totalCount %></scpan></h3>                 

            <table id="itemsTable" class="table" style="margin-top:20px">

		<thead>

			<tr><th style="text-align:center;color:rgb(0, 0, 255)"><h4>Suspended Work Steps</h4>

			</th></tr>

		</thead>

		<tbody style="text-align: left">


		<% if( wsDataMap.get("ExpectedClosureDateWS") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("ExpectedClosureDateWS") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("UpdateApprover2Status") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("UpdateApprover2Status") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("UpdateCallDeskData") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("UpdateCallDeskData") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("AppSupportFTPUpload") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("AppSupportFTPUpload") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("UpdateApproverStatus") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("UpdateApproverStatus") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("and") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("and") %></h6></td>

	       </tr>

	<% } %>

		<% if( wsDataMap.get("Approver") != null) { %>

                 <tr>

		<td><h6><%= wsDataMap.get("Approver") %></h6></td>

	       </tr>

	<% } %>

		</tbody>
	  </table>

            </div>  

        </div>

        <div class="col-md-4"></div>

    </div>

          

</div>

<!-- Content end -->
    <br clear="all">


</div>
<!-- Footer -->

<div id="cmdDiv" style="height:46px;">
<table width="100%" height="100%" cellpadding="0" align="center" cellspacing="0" border="0">
<tr align="center">
<td class="ApButtonDarkBg" width="63%">
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="ApBtnSpace">
<form:button id="SB_Name" name="SB_Name" link="Resume All" resourceType="LINK"/> </td>
<td class="ApBtnSpace">
<form:button id="bizsite_reset" name="bizsite_reset" link="RESET_LABEL" onclick="sbm.utils.reset" resourceType="RESOURCE"/> </td>
</tr>
</table>
</td>
</tr>
</table>
</div>

  
                    <div id="resizablepanel" style="display:none">
                        <div class="hd">Alert Dialog</div>
                        <div class="bd"></div>
                        <div class="ft"></div>
                    </div> 
<div id="southDiv" style="height:0px;display:none;"></div></form>
</body>

<script language="JavaScript">
<!--
function beforeInitControls() {
}
-->
</script>
<script language="JavaScript">
<!--
function userValidationJavascript() {
return true;
}
-->
</script>
<sbm:dataSources appName="ResumeSuspendedItems" appType="bizsolo">
</sbm:dataSources>
<script language="JavaScript">
<!---->
</script>
<!--Initialize extensible widgets.-->
<script language="JavaScript">
<!--
var _dateFormats = {date:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_FORMAT)%>', dateOnly:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_ONLY_FORMAT)%>'};

var allWidgets = [];
var businessObjects = [];
var formWidgetHandler;

sbm.utils.onDOMReady = function() {
YAHOO.util.Event.onDOMReady(function(){
formWidgetHandler = new FormWidgetHandler(allWidgets,null,{processName:'ResumeSuspendedItems',processType:'bizsolo',maxMultiLineLength:${maxMultiLineLength},adapletCache:{}},'<%=request.getParameter(BizSoloRequest.BSS_FIID)%>');
 });
 }
Ext.onReady(function(){
 var task = new Ext.util.DelayedTask(function(){
});
  task.delay(500);
  var viewport = new Bm.util.BmViewport('',{resultComponentXtype:'container'});

});
sbm.utils.onFormSubmit = function() {
         if(!formWidgetHandler.validateWidgets()) return false;
        try{
             if(!userValidationJavascript()) return false;
             sbm.utils.handleFormSubmit();
         }catch(e){return false;}
         document.form.action='<%=response.encodeURL("Start.jsp")%>';
         if(allWidgets.length > 0)formWidgetHandler.saveDataSlots();
         return true;
}
-->
</script>

</html>