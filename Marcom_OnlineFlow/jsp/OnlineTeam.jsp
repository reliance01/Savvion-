<%--  This JSP has been generated by using from xsl version 9.0 at Wed Mar 09 17:54:24 IST 2022 --%>
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
<%! String _PageName = "OnlineTeam"; %>
<%! String __webAppName = "Marcom_OnlineFlow"; %>
<% pageContext.setAttribute( "contextPath", request.getContextPath()+"/"); %>
<% pageContext.setAttribute( "maxMultiLineLength", DatabaseMapping.self().getSQLSize(java.lang.String.class));  %>
<%java.util.List<String> priorities = com.savvion.sbm.bpmportal.util.PortalUtil.self().getPriorities();%>
<% pageContext.setAttribute( "_priorities",priorities); %>
<bizsolo:if test='<%=_PageName.equals(request.getParameter("_PageName")) %>'>
    <bizsolo:setDS name="userUpload,uwDocs,legalDocs,onlineTeamUpload,application,category,subCategory,ticketNo,userRemarks,underwriters,processTime,onlineAssignTo,priorty,legalApprovalStatus,onlineTeamRmks,uwGrpName,uwRemarks,uwApprovalStatus,legalRemarks,uinNo"></bizsolo:setDS>
    <bizsolo:choose>
<bizsolo:when test='<%=request.getParameter("bizsite_reassignTask") !=null %>'>
      <bizsolo:initDS name="performer" param="bizsite_assigneeName" hexval="FALSE"></bizsolo:initDS>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKReassignWI" perfMethod="commit" mode="BizSite" dsi="CREATOR,legalDocs,uwDocs,userUpload" dso="uinNo,legalRemarks,uwApprovalStatus,uwRemarks,uwGrpName,onlineTeamRmks,legalApprovalStatus,priorty,onlineAssignTo,processTime,underwriters,userRemarks,ticketNo,subCategory,category,application,onlineTeamUpload"></bizsolo:executeAction>
</bizsolo:when>
<bizsolo:when test='<%=request.getParameter("bizsite_saveTask") !=null %>'>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKUpdateDS" perfMethod="commit" mode="BizSite" dsi="uinNo,legalRemarks,uwApprovalStatus,uwRemarks,uwGrpName,onlineTeamRmks,legalApprovalStatus,priorty,onlineAssignTo,processTime,underwriters,userRemarks,ticketNo,subCategory,category,application,onlineTeamUpload"></bizsolo:executeAction>
</bizsolo:when>
    <bizsolo:otherwise>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKSetDS" perfMethod="commit" mode="BizSite" dsi="uinNo,legalRemarks,uwApprovalStatus,uwRemarks,uwGrpName,onlineTeamRmks,legalApprovalStatus,priorty,onlineAssignTo,processTime,underwriters,userRemarks,ticketNo,subCategory,category,application,onlineTeamUpload" res=""></bizsolo:executeAction>
      </bizsolo:otherwise>
    </bizsolo:choose>
<% /* Workaround, retAddr will disappear in the future */ %>
<% String retAddr = bean.getPropString("returnPage"); %>
<% if(retAddr != null) { %>
<bizsolo:redirectURL page="<%= retAddr %>"/>
<% } %>
</bizsolo:if>
<bizsolo:if test='<%= ! _PageName.equals(request.getParameter("_PageName")) %>'>
    <bizsolo:initApp mode="BizSite" name="Marcom_OnlineFlow"></bizsolo:initApp>
    <bizsolo:initDS name="CREATOR" label="CREATOR" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="userUpload" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="uwDocs" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="legalDocs" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="onlineTeamUpload" type="DOCUMENT"></bizsolo:initDS>
<bizsolo:initDS name="application" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="category" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="subCategory" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="ticketNo" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="userRemarks" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="underwriters" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="processTime" type="LONG"></bizsolo:initDS>
<bizsolo:initDS name="onlineAssignTo" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="priorty" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="legalApprovalStatus" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="onlineTeamRmks" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="uwGrpName" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="uwRemarks" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="uwApprovalStatus" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="legalRemarks" type="STRING"></bizsolo:initDS>
<bizsolo:initDS name="uinNo" type="STRING"></bizsolo:initDS>
    <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKGetDS" perfMethod="commit" mode="BizSite" dso="uinNo,legalRemarks,uwApprovalStatus,uwRemarks,uwGrpName,CREATOR,onlineTeamRmks,legalApprovalStatus,priorty,onlineAssignTo,processTime,underwriters,userRemarks,ticketNo,subCategory,category,application,onlineTeamUpload,legalDocs,uwDocs,userUpload"></bizsolo:executeAction>
</bizsolo:if>

<title>OnlineTeam</title>
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


<sbm:setLocale value="<%= bizManage.getLocale() %>"></sbm:setLocale>
<% try{ %><sbm:setBundle scope="page" basename="Marcom_OnlineFlow/properties/Marcom_OnlineFlow"></sbm:setBundle><% } catch(Exception e){}%>
<bizsolo:getApplicationResources baseName="Marcom_OnlineFlow/properties/Marcom_OnlineFlow"/></head>
<body class="apbody yui-skin-sam" onLoad="setCheckBoxStyleForIE();hideControls();beforeInitControls();initControls();initTabs();sbm.utils.onDOMReady();">
<form method="post" name="form" onsubmit="return sbm.utils.onFormSubmit();">
<div id="northDiv" style="height:0px;display:none;"><bizsolo:xsrf/></div><% /* Workaround, activityName will disappear in the future */ %>
<% String activityName = bean.getPropString("workitemName"); %>
<div id="resultDiv">
<div style='visibility:hidden;display:none' class='vBoxClass' name='errors' id='errors'></div>
<input name="_PageName" type="hidden" value="OnlineTeam">
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
<td width="15%" class="ApSegGenLabel"><bizsolo:getLabel type="RESOURCE" name="BIZSITE_INSTRUCTION_LABEL"></bizsolo:getLabel></td><td width="85%" class="ApSegGenData" colspan="5"><sbm:message key="workstep.OnlineTeam.instruction" escapeLine="true"></sbm:message></td>
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

    <script src='scripts/jquery-1.11.2.js'></script>
<script src='scripts/commonJS.js'></script>
<link rel='stylesheet' href='style/custom.css' />
<div class='header'>Online Team Activity</div>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataVal" id="table1" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Ticket Number : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= bean.getPropString("ticketNo") %>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Call Log Date : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= com.rgicl.marcom.BasicUtility.getFormatedDate(bean.getPropLong("processTime")) %>
</span>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Application Name : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= bean.getPropString("application") %>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Category : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= bean.getPropString("category") %>
</span>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Sub Category : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= bean.getPropString("subCategory") %>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table2" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Call Log By : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <%= com.rgicl.marcom.BasicUtility.getNameOfUser(bean.getPropString("CREATOR")) %>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>User Attached Docs : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
            <div style="display: inline;" id="document1"><bizsolo:getDS type="field" name="userUpload" multiline="true" wsName="OnlineTeam" appendWith="false" writePerm="false"></bizsolo:getDS></div>


            </span>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>User Remark : </span>
          </td>
          <td class="ApSegDataVal" colspan="3" id="" rowspan="1">
            <span class='fieldValueBig'>
  <%= bean.getPropString("userRemarks") %>
</span>
          </td>
        </tr>
      </tbody>
    </table>
        <%



   String uwApprovalStatusVal = bean.getPropString("uwApprovalStatus");



   String legalApprovalStatusVal = bean.getPropString("legalApprovalStatus");



   if(uwApprovalStatusVal != null && uwApprovalStatusVal.equals("Rework")



     && (legalApprovalStatusVal == null || legalApprovalStatusVal.trim() == "")



   )



   {



%>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table7" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>U/W Remark : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValueBig'>
<%

  String uwRmks = bean.getPropString("uwRemarks");

  if(uwRmks != null && uwRmks.trim().length()>0)

  {

     out.println(uwRmks);

  }

%>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>U/W attached Docs : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValueBig'>
            <div style="display: inline;" id="document3"><bizsolo:getDS type="field" name="uwDocs" multiline="true" wsName="OnlineTeam" appendWith="false" writePerm="false"></bizsolo:getDS></div>


            </span>
          </td>
        </tr>
      </tbody>
    </table>
    <%
  }
if(legalApprovalStatusVal != null && legalApprovalStatusVal.equals("Rework"))
   {
%>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table7_V1" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>Legal Team Remark : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValueBig'>
<%

  String legalRmks = bean.getPropString("legalRemarks");

  if(legalRmks != null && legalRmks.trim().length()>0)

  {

     out.println(legalRmks);

  }

%>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>Legal Team Attached Docs : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValueBig'>
            <div style="display: inline;" id="document4"><bizsolo:getDS type="field" name="legalDocs" multiline="true" wsName="OnlineTeam" appendWith="false" writePerm="false"></bizsolo:getDS></div>


            </span>
          </td>
        </tr>
      </tbody>
    </table>
    <%

  }

%>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table3" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Priority : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
<select id='priory' onChange='setPriority();'>
  <option value='Select'>Select</option>
  <option value='Critical'>Critical</option>
  <option value='High'>High</option>
  <option value='Medium'>Medium</option>
  <option value='Low'>Low</option>
</select>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Attach Your Document : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
            <div style="display: inline;" id="document2"><bizsolo:getDS type="field" name="onlineTeamUpload" multiline="true" wsName="OnlineTeam" appendWith="false"></bizsolo:getDS></div>


            </span>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <%
  if(legalApprovalStatusVal == null || !legalApprovalStatusVal.equals("Rework"))
   {
%>
<span class='fieldNameBig'>Assign To : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>

<select id='assignTo' onChange='setAssignee();'>

  <option value='Select'>Select</option>

  <option value='UnderWriter'>UnderWriter</option>

  <option value='Legal Team'>Legal Team</option>

</select>

</span>

<%

  }

%>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldNameBig'>Remark : </span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldValueBig'>
  <textarea cols='30' rows='2' id='rmks'></textarea>
</span>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table8" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>UIN Number : </span>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
  <input type='text' id='uinNumber' onblur='UINValidate(this)' />
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
        </tr>
      </tbody>
    </table>
    <div id='prductList' style='display:none;'>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table4" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Select Product : </span>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
 <select id='prduct' onChange='setUWGrpName();'>
  <option value='Select'>Select</option>
  <option value='MRCONL_AGENCY'>AGENCY</option>
  <option value='MRCONL_BANKINGOPERATION'>BANKING OPERATION</option>
  <option value='MRCONL_COMMERCIAL'>COMMERCIAL</option>
  <option value='MRCONL_HEALTH'>HEALTH</option>
  <option value='MRCONL_HEALTHCLAIM'>HEALTH CLAIM</option>
  <option value='MRCONL_HOME'>HOME</option>
  <option value='MRCONL_MOTOR'>MOTOR</option>
  <option value='MRCONL_MOTORCLAIM'>MOTOR CLAIM</option>
  <option value='MRCONL_NBD'>NBD</option>
  <option value='MRCONL_OPERATION'>OPERATION</option>
  <option value='MRCONL_PERSONALACCIDENT'>PERSONAL ACCIDENT</option>
  <option value='MRCONL_RURAL'>RURAL</option>
  <option value='MRCONL_STRATEGY'>STRATEGY</option>
  <option value='MRCONL_TRAVEL'>TRAVEL</option>
  <option value='MRCONL_TRAVELCLAIM'>TRAVEL CLAIM</option>
  <option value='MRCONL_WELLNESS'>WELLNESS</option>
</select>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
        </tr>
      </tbody>
    </table>
    </div>

<div id='uwListDiv' style='display:none;'>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table5" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <span class='fieldName'>Select UnderWriter : </span>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <span class='fieldValue'>
   <select id='uwListDD' multiple></select>
</span>
          </td>
          <td class="ApSegDataVal" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
          <td class="(default)" id="" rowspan="1" colspan="1">
            <div class='fieldValue'></div>
          </td>
        </tr>
      </tbody>
    </table>
    </div>
<center>
   <span class='submit'>
      <img src='images/Submit.png' onClick='getSubmit();' height='35' width='150' />
   </span>
   <span class='cancel'>
      <img src='images/cancelButton.png' onClick='getCancel();' height='30' width='130' />
   </span>
</center>
<div id='statusData' class='footerDiv'>
 <span class='header'>Previous Status</span>
 <div id='data'></div>
</div>
<script>
pwr.getFlowStatus('<%= bean.getPropString("ticketNo") %>',callbackStatus);
function callbackStatus(data)
{ 
$('#data').html('');
if(data != ''){
 var myTable= "<table width='100%' align='center' id='statusTab'>";
 myTable += "<thead>";
 myTable += "<tr>";
 myTable += "<th class='fieldName'>PERFORMER</th>";
 myTable += "<th class='fieldName'>REMARK</th>";
 myTable += "<th class='fieldName'>STATUS</th>";
 myTable += "<th class='fieldName'>TIME</th>";
 myTable += "</tr>";
 myTable += "</thead>";
 myTable += "<tbody>";
   for (var i=0; i<data.length; i++) {
           myTable+="<tr>";
           myTable+="<td><span class='fieldValueBig'>"+data[i].prfmr+"</span></td>";
	 myTable+="<td><span class='fieldValueBig'>"+data[i].rmks+"</span></td>";
	 myTable+="<td><span class='fieldValueBig'>"+data[i].status+"</span></td>";
	 myTable+="<td><span class='fieldValueBig'>"+dateFormat(data[i].processTime)+"</span></td>";
           myTable+="</tr>";
          }  
  myTable += "</tbody></table>";
  $('#data').html(myTable);
 }
}
</script>

<div style='display:none;'>
    <br clear="all">
<sfe:widget name="sbm.textfield" id="textField1" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField2" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField3" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField4" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField5" args="{'type':'text', 'size':20, 'maxlength':2000, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="SelectedUW" args="{'type':'text', 'size':20, 'maxlength':2000, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <br clear="all">
<sfe:widget name="form.datetime" id="dateTime1" args="{'dateonly':false, 'visible':true, 'readonly':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="onlineAssignTo" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="priority" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField8" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="onlineRmks" args="{'type':'text', 'size':20, 'maxlength':2000, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField6" args="{'type':'text', 'size':30, 'maxlength':256, 'readonly':true, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="uwGrpName" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField7" args="{'type':'text', 'size':20, 'maxlength':2000, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField9" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="textField10" args="{'type':'text', 'size':20, 'maxlength':2000, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <sfe:widget name="sbm.textfield" id="uinNumberInp" args="{'type':'text', 'size':20, 'maxlength':256, 'readonly':false, 'visible':true, 'disabled':false, 'validationType':'none', 'validation':{}, 'toolTip':'', 'tabOrder':'0'}" />

    <input class="ApScrnButton" name="bizsite_completeTask" id="done" type="submit" tabindex="0" value="<sbm:message key='OnlineTeam.done.label'/>" onClick="return button1_onClick();clickedButton=this.name;this.onsubmit = new Function('return false');" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';">

    <input class="ApScrnButton" name="Submit1323" id="cnl" type="button" tabindex="0" value="<sbm:message key='OnlineTeam.cnl.label'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="sbm.utils.cancel();">

    <br clear="all">
</div>
    

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
<sbm:dataSources appName="Marcom_OnlineFlow" appType="bizlogic">
</sbm:dataSources>
<script language="JavaScript">
<!--
function setAssignee()
{
  var assignTo = $('#assignTo').val();
  if(assignTo == 'UnderWriter')
  {
    $('#prductList').show();
    $('#onlineAssignTo').val(assignTo);
  }
  else
  {
    $('#prductList').hide();
    $('#uwListDiv').hide();
    $('#onlineAssignTo').val(assignTo);
  }
}

function setPriority()
{
  $('#priority').val($('#priory').val());
}

function setUWGrpName()
{
$('#uwGrpName').val($('#prduct option:selected').text().trim()+"*"+$('#prduct').val());
        $.ajax({
            url : 'GroupMember.jsp',
            data : {
                grpName : $('#prduct').val()
            },
            success : function(grpMembers) {
                grpMembers = grpMembers.trim();
                $('#uwListDD').html('');
                var allUsers = grpMembers.substring(1, grpMembers.length-1);
                var userArr = allUsers.split(',');
                for(var i = 0;i < userArr.length; i++)
                {
                    var newOption = $('<option value="'+userArr[i].split('-')[0].trim()+'">'+userArr[i].split('-')[1].trim()+'</option>');
                    $('#uwListDD').append(newOption);
                }
            }
        });
   $('#uwListDiv').css('display','block');
}

$(document).ready(function(){
  var uinNo = $('#uinNumberInp').val();
  if(uinNo != null && uinNo.trim() != '')
  {
    $('#uinNumber').val(uinNo);
    $('#uinNumber').prop('disabled',true);
  }
  $('select').css('width','150');
});

 function button1_onClick(eventContext){
{
var priory = $('#priory').val();
var assignTo = $('#assignTo').val();
var prduct = $('#prduct').val();
var uw = $('#uwListDD').val();
var rmks = $('#rmks').val();
var uinNo = $('#uinNumber').val();
if(priory == null || priory.trim() == '' || priory == 'Select')
{
  alert('Please Select Priority.');
  $('#priory').focus();
  return false;
}
if(assignTo == null || assignTo.trim() == '' || assignTo == 'Select')
{
  if('<%=legalApprovalStatusVal%>' != 'Rework')
   {
    alert('Please Select Assignee.');
    $('#assignTo').focus();
    return false;
   } 
}
if(assignTo == 'UnderWriter')
{
  if(prduct == null || prduct.trim() == '' || prduct == 'Select')
  {
    alert('Please Select Product.');
    $('#prduct').focus();
    return false;
  }
  if(uw == null || uw == '')
  {
    alert('Please Select UnderWriter.');
    $('#uwListDD').focus();
    return false;
  }
}
if(rmks == null || rmks.trim() == '')
{
  alert('Please insert Remark.');
  $('#rmks').focus();
  return false;
}
if(uinNo == null || uinNo.trim() == '')
{
  alert('Please insert UIN Number.');
  $('#uinNumber').focus();
  return false;
}
$('#onlineRmks').val(rmks);
$('#uinNumberInp').val(uinNo);
$('#SelectedUW').val(uw);;
}}

-->
</script>
<!--Initialize extensible widgets.-->
<script language="JavaScript">
<!--
var _dateFormats = {date:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_FORMAT)%>', dateOnly:'<%=bean.getPropString(PublicResources.JS_CALENDAR_DATE_ONLY_FORMAT)%>'};

var allWidgets = [{widget:'textField1', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'application', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'application', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField2', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'category', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'category', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField3', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'subCategory', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'subCategory', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField4', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'ticketNo', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'ticketNo', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField5', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'userRemarks', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'userRemarks', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'SelectedUW', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'underwriters', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'underwriters', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'dateTime1', bound:'true', editable:'true', type:'form.datetime', source: {type:'DATASLOT', dataSlotName:'processTime', dataSlotType:'LONG'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'LONG', service:'false'},
{widget:'onlineAssignTo', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'onlineAssignTo', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'onlineAssignTo', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'priority', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'priorty', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'priorty', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField8', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'legalApprovalStatus', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'legalApprovalStatus', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'onlineRmks', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'onlineTeamRmks', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'onlineTeamRmks', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField6', bound:'true', editable:'false', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'CREATOR', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'', dataSlotType:''}, dsType:'STRING', service:'false'},
{widget:'uwGrpName', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'uwGrpName', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'uwGrpName', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField7', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'uwRemarks', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'uwRemarks', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField9', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'uwApprovalStatus', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'uwApprovalStatus', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'textField10', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'legalRemarks', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'legalRemarks', dataSlotType:'STRING'}, dsType:'STRING', service:'false'},
{widget:'uinNumberInp', bound:'true', editable:'true', type:'sbm.textfield', source: {type:'DATASLOT', dataSlotName:'uinNo', dataSlotType:'STRING'}, target:{type:'DATASLOT', dataSlotName:'uinNo', dataSlotType:'STRING'}, dsType:'STRING', service:'false'}
];
var businessObjects = [];
var formWidgetHandler;

sbm.utils.onDOMReady = function() {
YAHOO.util.Event.onDOMReady(function(){
formWidgetHandler = new FormWidgetHandler(allWidgets,null,{processName:'Marcom_OnlineFlow',processType:'bizlogic',maxMultiLineLength:${maxMultiLineLength},adapletCache:{'system':'','user':''}},'<%=request.getParameter(BizSoloRequest.BSS_FIID)%>');
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

                            document.form.action='<%=response.encodeURL("OnlineTeam.jsp")%>';
         if(allWidgets.length > 0)formWidgetHandler.saveDataSlots();
         return true;
}
-->
</script>

</html>