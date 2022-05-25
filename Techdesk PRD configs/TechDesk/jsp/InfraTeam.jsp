<%--  This JSP has been generated by using from xsl version 9.0 at Thu Sep 09 17:06:17 IST 2021 --%>
<!DOCTYPE html>
<html xmlns:sbm="http://www.savvion.com/sbm" xmlns:bizsolo="http://www.savvion.com/sbm/BizSolo" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:xalan="http://xml.apache.org/xalan" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:sfe="http://www.savvion.com/sbm/sfe" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head><META http-equiv="Content-Type" content="text/html; charset=utf-8"><meta http-equiv="X-UA-Compatible" content="IE=Edge">

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
  <jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"></jsp:useBean>
  <jsp:useBean id="factoryBean" class="com.savvion.BizSolo.beans.EPFactoryBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>
<%! String _PageName = "InfraTeam"; %>
<%! String __webAppName = "TechDesk"; %>
<% pageContext.setAttribute( "contextPath", request.getContextPath()+"/"); %>
<% pageContext.setAttribute( "maxMultiLineLength", DatabaseMapping.self().getSQLSize(java.lang.String.class));  %>
<%java.util.List<String> priorities = com.savvion.sbm.bpmportal.util.PortalUtil.self().getPriorities();%>
<% pageContext.setAttribute( "_priorities",priorities); %>
<bizsolo:if test='<%=_PageName.equals(request.getParameter("_PageName")) %>'>
    <bizsolo:setDS name="AppSupportPerformer,ReopenId,TicketNo,CallType,ApplicationType,IssueRequestId,CallCreatedBy,IssueReqSubTypeID,CallLogDate,Branch,UserContactNo,UserEmailID,ApproverName,ApproverDesignation,ApproverDocument,VerifierDocument,AppSupportDocument,UserRemark,ApproverFinalRemark,AppSupportRemark"></bizsolo:setDS>
    <bizsolo:choose>
<bizsolo:when test='<%=request.getParameter("bizsite_reassignTask") !=null %>'>
      <bizsolo:initDS name="performer" param="bizsite_assigneeName" hexval="FALSE"></bizsolo:initDS>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKReassignWI" perfMethod="commit" mode="BizSite" dsi="VerifierDocument,ApproverDocument,ApproverDesignation,ApproverName,UserEmailID,UserContactNo,Branch,CallLogDate,IssueReqSubTypeID,CallCreatedBy,IssueRequestId,ApplicationType,CallType,TicketNo,ReopenId" dso="AppSupportRemark,ApproverFinalRemark,UserRemark,AppSupportDocument,AppSupportPerformer"></bizsolo:executeAction>
</bizsolo:when>
<bizsolo:when test='<%=request.getParameter("bizsite_saveTask") !=null %>'>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKUpdateDS" perfMethod="commit" mode="BizSite" dsi="AppSupportRemark,ApproverFinalRemark,UserRemark,AppSupportDocument,AppSupportPerformer"></bizsolo:executeAction>
</bizsolo:when>
    <bizsolo:otherwise>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKSetDS" perfMethod="commit" mode="BizSite" dsi="AppSupportRemark,ApproverFinalRemark,UserRemark,AppSupportDocument,AppSupportPerformer" res=""></bizsolo:executeAction>
      </bizsolo:otherwise>
    </bizsolo:choose>
<% /* Workaround, retAddr will disappear in the future */ %>
<% String retAddr = bean.getPropString("returnPage"); %>
<% if(retAddr != null) { %>
<bizsolo:redirectURL page="<%= retAddr %>"/>
<% } %>
</bizsolo:if>
<bizsolo:if test='<%= ! _PageName.equals(request.getParameter("_PageName")) %>'>
    <bizsolo:initApp mode="BizSite" name="TechDesk"></bizsolo:initApp>
<bizsolo:initDS name="AppSupportPerformer" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ReopenId" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="TicketNo" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="CallType" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ApplicationType" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="IssueRequestId" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="CallCreatedBy" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="IssueReqSubTypeID" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="CallLogDate" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="Branch" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="UserContactNo" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="UserEmailID" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ApproverName" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ApproverDesignation" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ApproverDocument" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="VerifierDocument" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="AppSupportDocument" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="UserRemark" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ApproverFinalRemark" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="AppSupportRemark" type="STRING"></bizsolo:initDS>
    <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKGetDS" perfMethod="commit" mode="BizSite" dso="AppSupportRemark,ApproverFinalRemark,UserRemark,AppSupportDocument,VerifierDocument,ApproverDocument,ApproverDesignation,ApproverName,UserEmailID,UserContactNo,Branch,CallLogDate,IssueReqSubTypeID,CallCreatedBy,IssueRequestId,ApplicationType,CallType,TicketNo,ReopenId,AppSupportPerformer"></bizsolo:executeAction>
</bizsolo:if>

<title>InfraTeam</title>
<%boolean isStandaloneBS = (bizManage == null || bizManage.getName() == null || "".equals(bizManage.getName()) || bizManage.getLocale() == null);Locale myLocale = (!isStandaloneBS) ? bizManage.getLocale() : request.getLocale();%>
<!-- Javascript -->
<script language="JavaScript"> var getLocalizedString = parent.getLocalizedString; </script>
<form:includes contextPath="${contextPath}"/>
<script language="JavaScript">
     
      var isIFrame = <%= (PublicResources.INTERACTIVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE)) || PublicResources.SLAVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE))) ? true : false %>;
      

    function getContextPath(){
        return "<%=request.getContextPath()%>";
    }
</script>

<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>ebmsapps/common/css/rgic01.css">

<sbm:setLocale value="<%= bizManage.getLocale() %>"></sbm:setLocale>
<% try{ %><sbm:setBundle scope="page" basename="TechDesk/properties/TechDesk"></sbm:setBundle><% } catch(Exception e){}%>
<bizsolo:getApplicationResources baseName="TechDesk/properties/TechDesk"/></head>
<body class="apbody yui-skin-sam" onLoad="setCheckBoxStyleForIE();hideControls();beforeInitControls();initControls();initTabs();sbm.utils.onDOMReady();">
<form method="post" name="form" onsubmit="return sbm.utils.onFormSubmit();">
<div id="northDiv" style="height:0px;display:none;"><bizsolo:xsrf/></div><% /* Workaround, activityName will disappear in the future */ %>
<% String activityName = bean.getPropString("workitemName"); %>
<div id="resultDiv">
<div style='visibility:hidden;display:none' class='vBoxClass' name='errors' id='errors'></div>
<input name="_PageName" type="hidden" value="InfraTeam">
<%if(bean.getPropString("workitemName") != null) {%><input name="_WorkitemName" type="hidden" value="<%= URLHexCoder.encode(bean.getPropString("workitemName")) %>"/><input name="_WorkitemId" type="hidden" value="<%= bean.getPropLong("workitemId") %>"/><%}%>
<input name="bizsite_pagetype" type="hidden" value="activity">
<input name="_ProcessTemplateName" type="hidden" value='<%=bean.getPropString("ptName")%>'>
<input name="_yahoo_flow_button" type="hidden" value=''>
<!-- Content --> 

    
<!-- Header -->

                    <bizsolo:choose><bizsolo:when test='<%=bean.getPropString(\"workitemName\") != null %>'><table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td class="ApSegTblInBg">
<table width="100%" cellpadding="4" align="center" cellspacing="0" border="0">
<tr>

<td class="ApSegTitle" align="center"><bizsolo:choose><bizsolo:when test='<%=bean.getPropString(\"workitemName\") != null %>'><bizsolo:getDS name="workitemName"></bizsolo:getDS></bizsolo:when><bizsolo:otherwise><%=_PageName%></bizsolo:otherwise></bizsolo:choose></td>
</tr>
</table>
<table class="ApSegDataTbl" width="100%" cellspacing="1" cellpadding="4" border="0">
<tr>
<td width="15%" class="ApSegGenLabel"><bizsolo:getLabel type="RESOURCE" name="BIZSITE_INSTRUCTION_LABEL"></bizsolo:getLabel></td><td width="85%" class="ApSegGenData" colspan="5"><sbm:message key="workstep.InfraTeam.instruction" escapeLine="true"></sbm:message></td>
</tr>
<tr>
<td width="15%" class="ApSegGenLabel"><bizsolo:getLabel type="RESOURCE" name="BIZSITE_PRIORITY_LABEL"></bizsolo:getLabel></td><td width="15%" class="ApSegGenData"><bizsolo:getDS name="bizsite_priority"></bizsolo:getDS></td>
<td width="15%" class="ApSegGenLabel"><bizsolo:getLabel type="RESOURCE" name="BIZSITE_STARTDATE_LABEL"></bizsolo:getLabel></td><td width="15%" class="ApSegGenData"><bizsolo:getDS name="bizsite_startDate"></bizsolo:getDS></td>
<td width="15%" class="ApSegGenLabel"><bizsolo:getLabel type="RESOURCE" name="BIZSITE_DUEDATE_LABEL"></bizsolo:getLabel></td><td width="15%" class="ApSegGenData"><bizsolo:getDS name="bizsite_dueDate"></bizsolo:getDS></td>
</tr>
</table>
</td>
</tr>
</table></bizsolo:when></bizsolo:choose>

    
      
    
    <table align="left" cellpadding="0" cellspacing="0" class="blue_shade_reliance_red tr td" id="table1" width="100%">
      <tbody>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div align="center" id="">
              <b>Infra Team Activity</b>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <sfe:widget name="sbm.textfield" id="textField8" args="{'type':'text', 'size':140, 'maxlength':100, 'readonly':false, 'visible':false, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" /><script>addHiddenControls("textField8");</script>

    <sfe:widget name="sbm.textfield" id="textField6" args="{'type':'text', 'size':245, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="blue_shade_reliance_blue tr td" id="table3" width="100%">
      <tbody>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Ticket No:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField1" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Call Type:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField3" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Application Name:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField16" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Category:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField2" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Call Logged By:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField5" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Sub Category:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField17" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Call Log Date:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField4" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Branch:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField9" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':true, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Contact No:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField10" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':true, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Email ID:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField11" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':true, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Approver Name:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField12" args="{'type':'text', 'size':190, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Approver Designation:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <sfe:widget name="sbm.textfield" id="textField13" args="{'type':'text', 'size':145, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>1st Approval Document:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div style="display: inline;" id="document2"><bizsolo:getDS type="field" name="ApproverDocument" multiline="false" wsName="InfraTeam" appendWith="false" writePerm="false"></bizsolo:getDS></div>


          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Verifier Document:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div style="display: inline;" id="document3"><bizsolo:getDS type="field" name="VerifierDocument" multiline="false" wsName="InfraTeam" appendWith="false" writePerm="false"></bizsolo:getDS></div>


          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Reopen ID:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <%
String contextPath = request.getContextPath();
if(bean.getPropString("ReopenId") == null || bean.getPropString("ReopenId").length() <  1 ){
%>
&nbsp;Not a Reopen Case


<%
 }else{
%>
<a href="#" class="textfield_reliance_blink" onclick="showDocsWindow();" title="Click on the link to view previous call details">
<img border='0' title='Previous Call Details' src="<%=contextPath%>/bpmportal/css/theme01/images/Alert.gif"></a>
<%
 }
%>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Appsupport Document:</b>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div style="display: inline;" id="document1"><bizsolo:getDS type="field" name="AppSupportDocument" multiline="false" wsName="InfraTeam" appendWith="false"></bizsolo:getDS></div>


          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>User Remark:</b>
          </td>
          <td class="(default)" colspan="3" id="" rowspan="1">
            <sfe:widget name="sbm.textarea" id="textField14" args="{'wrap':'Soft', 'rows':56, 'cols':405, 'visible':true, 'readonly':false, 'disabled':false, 'validationType':'none', 'validation':{ 'counterId':'textField14_counter_span'}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>Approver Remark:</b>
          </td>
          <td class="(default)" colspan="3" id="" rowspan="1">
            <sfe:widget name="sbm.textarea" id="textField15" args="{'wrap':'Soft', 'rows':56, 'cols':405, 'visible':true, 'readonly':false, 'disabled':false, 'validationType':'none', 'validation':{ 'counterId':'textField15_counter_span'}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
        <tr>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <b>AppSupport Remark:</b>
          </td>
          <td class="(default)" colspan="3" id="" rowspan="1">
            <sfe:widget name="sbm.textarea" id="textField7" args="{'wrap':'Soft', 'rows':70, 'cols':405, 'visible':true, 'readonly':false, 'disabled':false, 'validationType':'none', 'validation':{ 'counterId':'textField7_counter_span'}, 'toolTip':'', 'tabOrder':'0'}" />

          </td>
        </tr>
      </tbody>
    </table>
    <% 



	



	String perfm = bizSite.getCurrentUser();



System.out.println (" ########################  <b>Current User : id "+perfm+"</b>");



	bean.setPropString("AppSupportPerformer",perfm);



	System.out.println (" ************  <b>Current User : id "+bean.getPropString("AppSupportPerformer")+"</b>");



	  %>  











<input type="Hidden" name="CurrentPerformer" value="<%=bizSite.getCurrentUser()%>">
    <br clear="all">
<br clear="all">
<table align="center" cellpadding="0" cellspacing="0" id="table1" width="100%">
      <tbody>
        <tr>
          <td class="apbuttondarkbg" id="" rowspan="1" colspan="1">
            <div align="center" id="">
              <input class="ApScrnButton" name="bizsite_completeTask" id="button-complete" type="submit" tabindex="0" value="<bizsolo:getLabel name='COMPLETE_LABEL' type='RESOURCE'/>" onClick="return onComplete_Infra();clickedButton=this.name;this.onsubmit = new Function('return false');" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';">

              <input class="ApScrnButton" name="bizsite_saveTask" id="button-save" type="submit" tabindex="0" value="<bizsolo:getLabel name='SAVE_LABEL' type='RESOURCE'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="clickedButton=this.name;this.onsubmit = new Function('return false');">

              <input class="ApScrnButton" name="bizsite_reset" id="button-reset" type="button" tabindex="0" value="<bizsolo:getLabel name='RESET_LABEL' type='RESOURCE'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="sbm.utils.reset();">

              <input class="ApScrnButton" name="Submit1323" id="button-cancel" type="button" tabindex="0" value="<bizsolo:getLabel name='CANCEL_LABEL' type='RESOURCE'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="sbm.utils.cancel();">

              <sfe:widget name="sbm.textfield" id="bizsite_assigneeName" args="{'type':'text', 'size':106, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

              <a href="javascript://" onclick="setUserControl(document.form.bizsite_assigneeName);searchUser()"><img border="0" id="image1" width="32" height="32" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/icon_edit_user_search_single.gif" tabindex="0"></a>

              <input class="ApScrnButton" name="bizsite_reassignTask" id="button-reassign" type="submit" tabindex="0" value="<bizsolo:getLabel name='REASSIGN_LABEL' type='RESOURCE'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="clickedButton=this.name;return AlertReassign();this.onsubmit = new Function('return false');">

            </div>
          </td>
        </tr>
      </tbody>
    </table>
    

</div>
<!-- Footer -->

<div id="cmdDiv" style="height:46px;">
<table width="100%" height="100%" cellpadding="0" align="center" cellspacing="0" border="0">
<tr align="center">
<td class="ApButtonDarkBg" width="63%">
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="ApBtnSpace">
<bizsolo:isNotSuspended><form:button id="bizsite_completeTask" name="bizsite_completeTask" link="COMPLETE_LABEL" resourceType="RESOURCE"/> </bizsolo:isNotSuspended></td>
<td class="ApBtnSpace">
<form:button id="bizsite_saveTask" name="bizsite_saveTask" link="SAVE_LABEL" resourceType="RESOURCE"/> </td>
<bizsolo:isAssigned></bizsolo:isAssigned><td class="ApBtnSpace">
<form:button id="bizsite_reset" name="bizsite_reset" link="RESET_LABEL" onclick="sbm.utils.reset" resourceType="RESOURCE"/> </td>
<td class="ApBtnSpace">
<form:button id="btn-cancel" name="btn-cancel" link="CANCEL_LABEL" onclick="sbm.utils.cancel" resourceType="RESOURCE"/> </td>
<bizsolo:isAssigned><td class="ApBtnSpace"><input class="ApInptTxt" type="text" name="bizsite_assigneeName" size="20"></td><td class="ApBtnSpace"><a href="javascript://" onClick="setUserControl(document.form.bizsite_assigneeName);searchUser()"><img width="16" height="16" border="0" title="Search" src="<c:out value='${contextPath}'/>bpmportal/css/app<%= bizManage.getTheme() %>/images/icon_edit_user_search_single.gif"></img></a></td><td class="ApBtnSpace"><form:button id="bizsite_reassignTask" name="bizsite_reassignTask" link="REASSIGN_LABEL" resourceType="RESOURCE"/> </td>
</bizsolo:isAssigned></tr>
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
<sbm:dataSources appName="TechDesk" appType="bizlogic">
</sbm:dataSources>
<script language="JavaScript">
<!--
function onComplete_Infra() {
	var currUser =  document.forms[0].CurrentPerformer.value;
	sbm.util.setValue('textField8',currUser);
}

function showDocsWindow() {
	var ticketNo = sbm.util.getValue('textField1');
	var reopenId = sbm.util.getValue('textField6');
	if(reopenId != null || reopenId != ""){
	//window.open('<%=contextPath%>/bpmportal/common/OperationsAudit/OperationsDocList.jsp?PIID= ' + piid ,'Findings Documents','resizable=yes,width=420,height=550');
	//window.open('<%=contextPath%>/bpmportal/CallDeskReports/ReopenTicketDetails.jsp?ticket=' + ticketNo+'&reopenid='+reopenId,'mywindow','scrollbars=1,width=400,height=320');
	window.open('<%=contextPath%>/bpmportal/CallDeskReports/ReopenTicketDetails.jsp?ticket=' + ticketNo,'mywindow','scrollbars=1,width=400,height=320');
	}
}
function makeROonLoad() {
	document.forms[0].textField14.readOnly=true;
  	document.forms[0].textField15.readOnly=true;
}
-->
</script>
<!--Initialize extensible widgets.-->
<script language="JavaScript">
<!--
var _dateFormats = {date:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_FORMAT)%>', dateOnly:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_ONLY_FORMAT)%>'};

var allWidgets = [{widget:'textField8', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'AppSupportPerformer', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'AppSupportPerformer', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField6', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'ReopenId', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ReopenId', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField1', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'TicketNo', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'TicketNo', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField3', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'CallType', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'CallType', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField16', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'ApplicationType', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ApplicationType', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField2', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'IssueRequestId', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'IssueRequestId', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField5', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'CallCreatedBy', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'CallCreatedBy', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField17', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'IssueReqSubTypeID', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'IssueReqSubTypeID', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField4', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'CallLogDate', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'CallLogDate', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField9', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'Branch', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'STRING', service:'false'},
{widget:'textField10', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'UserContactNo', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'STRING', service:'false'},
{widget:'textField11', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'UserEmailID', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'STRING', service:'false'},
{widget:'textField12', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'ApproverName', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ApproverName', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField13', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'ApproverDesignation', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ApproverDesignation', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField14', bound:'true', editable:'true', type:'sbm.textarea', source: {type:'DATASLOT', dataSlotName:'UserRemark', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'UserRemark', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField15', bound:'true', editable:'true', type:'sbm.textarea', source: {type:'DATASLOT', dataSlotName:'ApproverFinalRemark', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ApproverFinalRemark', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField7', bound:'true', editable:'true', type:'sbm.textarea', source: {type:'DATASLOT', dataSlotName:'AppSupportRemark', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'AppSupportRemark', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'bizsite_assigneeName', bound:'false', editable:'true', type:'sbm.textfield', source: {type:'STATIC'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'', service:'false'}
];
var businessObjects = [];
var formWidgetHandler;

sbm.utils.onDOMReady = function() {
YAHOO.util.Event.onDOMReady(function(){
formWidgetHandler = new FormWidgetHandler(allWidgets,makeROonLoad,{processName:'TechDesk',processType:'bizlogic',maxMultiLineLength:${maxMultiLineLength},adapletCache:{'user':''}},'<%=request.getParameter(BizSoloRequest.BSS_FIID)%>');
 });
 }
Ext.onReady(function(){
 var task = new Ext.util.DelayedTask(function(){
});
  task.delay(500);
  var viewport = new Bm.util.BmViewport('',{resultComponentXtype:'container'});

});

   
    scrollToErrors = function() {
        var errorsDiv = document.getElementById('errors');
        if(errorsDiv !== null) {
            errorsDiv.scrollIntoView();
        }
    }
    sbm.utils.onFormSubmit = function() {
        if(!formWidgetHandler.validateWidgets()) {
            scrollToErrors();
            return false;
        }
        try{
            if(!userValidationJavascript()) {
                scrollToErrors();
                return false;
            }              
            sbm.utils.handleFormSubmit();
        }catch(e){
            scrollToErrors();
            return false;
        }

                            document.form.action='<%=response.encodeURL("InfraTeam.jsp")%>';
         if(allWidgets.length > 0)formWidgetHandler.saveDataSlots();
         return true;
}
-->
</script>

</html>