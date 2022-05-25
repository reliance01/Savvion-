<html xmlns:bizsolo="http://www.savvion.com/sbm/BizSolo" xmlns:sbm="http://www.savvion.com/sbm" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:xalan="http://xml.apache.org/xalan" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:sfe="http://www.savvion.com/sbm/sfe" xmlns:c="http://java.sun.com/jstl/core">
<head><META http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.savvion.BizSolo.Server.*,com.savvion.BizSolo.beans.*,java.util.Vector" %>
<%@ page errorPage="/BizSolo/common/jsp/error.jsp" %>
<%@ taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sfe" uri="http://jmaki/v1.0/jsp" %>
  <jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"></jsp:useBean>
  <jsp:useBean id="factoryBean" class="com.savvion.BizSolo.beans.EPFactoryBean" scope="session"></jsp:useBean>
  <jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>
<%! String _PageName = "OnlineTeam IRDA Activity"; %>
<%! String __webAppName = "Marcom_OnlineFlow"; %>
<% pageContext.setAttribute( "contextPath", request.getContextPath()+"/"); %>
<bizsolo:if test='<%=_PageName.equals(request.getParameter("_PageName")) %>'>
    <bizsolo:setDS name="IRDADocByOnlineTeam,category,ticketNo,processTime,application,subCategory,onlineIRDARmks"></bizsolo:setDS>
    <bizsolo:choose>
<bizsolo:when test='<%=request.getParameter("bizsite_reassignTask") !=null %>'>
      <bizsolo:initDS name="performer" param="bizsite_assigneeName" hexval="FALSE"></bizsolo:initDS>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKReassignWI" perfMethod="commit" mode="BizSite" dsi="CREATOR" dso="onlineIRDARmks,subCategory,application,processTime,ticketNo,category,IRDADocByOnlineTeam"></bizsolo:executeAction>
</bizsolo:when>
<bizsolo:when test='<%=request.getParameter("bizsite_saveTask") !=null %>'>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKUpdateDS" perfMethod="commit" mode="BizSite" dsi="onlineIRDARmks,subCategory,application,processTime,ticketNo,category,IRDADocByOnlineTeam"></bizsolo:executeAction>
</bizsolo:when>
    <bizsolo:otherwise>
      <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKSetDS" perfMethod="commit" mode="BizSite" dsi="onlineIRDARmks,subCategory,application,processTime,ticketNo,category,IRDADocByOnlineTeam" res=""></bizsolo:executeAction>
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
    <bizsolo:initDS name="IRDADocByOnlineTeam" type="DOCUMENT"></bizsolo:initDS>
    <bizsolo:initDS name="category" type="STRING"></bizsolo:initDS>
    <bizsolo:initDS name="ticketNo" type="STRING"></bizsolo:initDS>
    <bizsolo:initDS name="processTime" type="LONG"></bizsolo:initDS>
    <bizsolo:initDS name="application" type="STRING"></bizsolo:initDS>
    <bizsolo:initDS name="subCategory" type="STRING"></bizsolo:initDS>
    <bizsolo:initDS name="onlineIRDARmks" type="STRING"></bizsolo:initDS>
    <bizsolo:executeAction epClassName="com.savvion.BizSolo.beans.PAKGetDS" perfMethod="commit" mode="BizSite" dso="onlineIRDARmks,subCategory,application,processTime,ticketNo,category,CREATOR,IRDADocByOnlineTeam"></bizsolo:executeAction>
</bizsolo:if>

<title>OnlineTeam IRDA Activity</title>
<!-- Javascript -->
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/initControls.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/customValidation.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/prototype.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/effects.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/scriptaculous.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/pwr.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/engine.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/cal.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/util.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/utilities.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/fvalidate/fValidate.config.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/fvalidate/fValidate.core.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/fvalidate/fValidate.lang-enUS.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/fvalidate/fValidate.validators.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/fvalidate/pValidate.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/document.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/jscalendar/calendar.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/jscalendar/calendar-en.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/jscalendar/calendar-setup.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>dwr/interface/adapterDWR.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/utilities/utilities.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/container/container-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/connection/connection-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/resize/resize-beta-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/animation/animation-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/json/json-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/logger/logger-min.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/checkboxvalidation/SpryValidationCheckbox.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/confirmvalidation/SpryValidationConfirm.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/passwordvalidation/SpryValidationPassword.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/radiovalidation/SpryValidationRadio.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/selectvalidation/SpryValidationSelect.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/textareavalidation/SpryValidationTextarea.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry/textfieldvalidation/SpryValidationTextField.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/spry//SpryEffects.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/ext/adapter/ext/ext-base.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/ext/ext-all.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/ext/PagingRowNumberer.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/BmViewport.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/WaitDialog.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/LoggerDialog.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/ResizableDialog.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/FormWidget.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/FormPanel.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/FormWidgetHandler.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/TransactionAjaxObject.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/BusinessObjectHandler.js"></script>
<script language="JavaScript" src="<c:out value='${contextPath}'/>bpmportal/javascript/sbm/sbm.utils.js"></script>
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/fonts/fonts.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/resize/assets/skins/sam/resize.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/container/assets/skins/sam/container.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/yahoo/logger/assets/skins/sam/logger.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/checkboxvalidation/SpryValidationCheckbox.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/confirmvalidation/SpryValidationConfirm.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/textareavalidation/SpryValidationTextarea.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/passwordvalidation/SpryValidationPassword.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/radiovalidation/SpryValidationRadio.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/selectvalidation/SpryValidationSelect.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/spry/textfieldvalidation/SpryValidationTextField.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/ext/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/javascript/ext/resources/css/xtheme-default.css">
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>bpmportal/css/theme01/bm-all.css">
<script language="JavaScript">
 Ext.BLANK_IMAGE_URL = '<c:out value='${contextPath}'/>bpmportal/javascript/ext/resources/images/default/s.gif';
	 
	  var isIFrame = <%= (PublicResources.INTERACTIVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE)) || PublicResources.SLAVE_MODE.equalsIgnoreCase(bean.getPropString(PublicResources.MODE))) ? true : false %>;
	  
<!--
    function AlertReassign()
    {
      if (document.form.elements['bizsite_assigneeName'].value == '' )
      {
        alert('Please provide assignee name!')
        document.form.elements['bizsite_assigneeName'].focus();
        return false;
      }
      else
      {
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
      uploadWnd = openDocumentPresentation(docServer + '/BizSite.DocAttacher?' + param, isIFrame);
    }

    function setCheckBoxStyleForIE()
    {
      var isIE = (navigator.appName == "Microsoft Internet Explorer") ? 1 : 0;
      var w_Elements = document.getElementsByTagName("input");
      for ( i=0; i < w_Elements.length; ++i)
      {
          if(isIE && (w_Elements.item(i).getAttribute("type") == "checkbox" || w_Elements.item(i).getAttribute("type") == "radio"))
            w_Elements.item(i).className = "ChkBoxNone";
      }
    }
    
    function onSuccess() {
    }
    
    
   function editDecimal(element,pms,scale)
    {
    if(typeof element == 'string') element = document.getElementById(element);
	var id = element.getAttribute('id');
	if (element != null)
	{
	      var newurl = '<c:out value='${contextPath}'/>bpmportal/common/pop_decimal_dataslot.jsp?elementID=' + id + '&pms=' + pms + '&scale=' + scale + '&value=' + element.value;
	      
        MM_openBrWindow(newurl,'editdecimal','scrollbars=yes,resizable=yes,width=690,height=174');
	}
}
//-->
</script>

<bizsolo:link rel="stylesheet"></bizsolo:link>

<sbm:setLocale value="<%= bizManage.getLocale() %>"></sbm:setLocale>
<% try{ %><sbm:setBundle scope="page" basename="Marcom_OnlineFlow/properties/Marcom_OnlineFlow"></sbm:setBundle><% } catch(Exception e){}%>
</head>
<body class="apbody yui-skin-sam" onUnload="pwr.removePakBizSoloBeanFromCache('<%=session.getId()%>', onSuccess);" onLoad="setCheckBoxStyleForIE();hideControls();beforeInitControls();initControls();initTabs();sbm.utils.onDOMReady();">
<form method="post" name="form" onsubmit="return sbm.utils.onFormSubmit();">
<div id="northDiv"></div><% /* Workaround, activityName will disappear in the future */ %>
<% String activityName = bean.getPropString("workitemName"); %>
<div id="resultDiv">
<div style='visibility:hidden;display:none' class='vBoxClass' name='errors' id='errors'></div>
<input name="_PageName" type="hidden" value="OnlineTeam IRDA Activity">
<%if(bean.getPropString("workitemName") != null) {%><input name="_WorkitemName" type="hidden" value="<%= URLHexCoder.encode(bean.getPropString("workitemName")) %>"/><input name="_WorkitemId" type="hidden" value="<%= bean.getPropString("workitemId") %>"/><%}%>
<input name="bizsite_pagetype" type="hidden" value="activity">
<input name="_ProcessTemplateName" type="hidden" value='<%=bean.getPropString("ptName")%>'>
<input name="_yahoo_flow_button" type="hidden" value=''>
<!-- Content --> 

    <font color="#000000">
      <script src='scripts/jquery-1.11.2.js'></script>
<link rel='stylesheet' href='style/custom.css' />

<style>         

table
{
 width : 100%;
  border : 1px solid #3375B7;
  background-color : #D3E1F1;
}

.ApSegDataVal
{
  border : 1px solid white;
  background-color : #D3E1F1;
}

select, option 
{
width: 200px; 
}             
  
.Header
{
  background-color : #3B4868;
  border : 1px solid white;
}

.Header b
{
   color : white;
}
</style>
    </font>
    <br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table5" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" width="100%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <center>
            </font>
            <font color="#000000">
              <b>Online Team IRDA Activity</b>
            </font>
            <font color="#000000">
              </center>
            </font>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table1" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b> Ticket Number :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= bean.getPropString("ticketNo") %>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b>Call Log Date :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= com.rgicl.marcom.BasicUtility.getFormatedDate(bean.getPropLong("processTime")) %>
            </font>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b>Application Name :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= bean.getPropString("application") %>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b>Category :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= bean.getPropString("category") %>
            </font>
          </td>
        </tr>
        <tr>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b>Sub Category :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= bean.getPropString("subCategory") %>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000" face="Times New Roman" size="2">
              <b>Call Log By :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000">
              <%= com.rgicl.marcom.BasicUtility.getNameOfUser(bean.getPropString("CREATOR")) %>
            </font>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table2" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000"> </font>
            <font color="#000000" face="Times New Roman" size="2">
              <b>Attach File :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000"> </font>
            <div style="display: inline;" id="document1"><bizsolo:getDS type="field" name="IRDADocByOnlineTeam" multiline="true" wsName="OnlineTeam IRDA Activity" appendWith="false"></bizsolo:getDS></div>


          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000"> </font>
            <font color="#000000" face="Times New Roman" size="2">
              <b>Remarks :</b>
            </font>
          </td>
          <td class="ApSegDataVal" width="25%" rowspan="1" colspan="1" valign="top">
            <font color="#000000"> </font>
            <font color="#000000">
              <textarea id='rmks' cols='30' rows='5'></textarea>
            </font>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<br clear="all">
<table align="left" cellpadding="0" cellspacing="0" class="ApSegDataTbl" id="table3" width="100%">
      <tbody>
        <tr>
          <td class="ApSegDataVal" width="100%" rowspan="1" colspan="1" valign="top">
            <font color="#000000"> </font>
            <font color="#000000">
              <center>
            </font>
            <input class="ApScrnButton" name="bizsite_completeTask" id="button1" type="submit" value="<bizsolo:getLabel name='COMPLETE_LABEL' type='RESOURCE'/>" onClick="return button1_onClick();clickedButton=this.name;this.onsubmit = new Function('return false');" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';">

            <input class="ApScrnButton" name="Submit1323" id="button2" type="button" value="<bizsolo:getLabel name='CANCEL_LABEL' type='RESOURCE'/>" onMouseOut="this.className='ApScrnButton';" onMouseOver="this.className='ApScrnButtonHover';" onClick="sbm.utils.cancel();">

            <font color="#000000">
              </center>
            </font>
          </td>
        </tr>
      </tbody>
    </table>
    <br clear="all">
<font color="#000000">
      <div style='display:none;'>
    </font>
    <br clear="all">
<input class="ApInptTxt" type="text" id="textField1" name="CREATOR" size="30" maxlength="256" disabled value="<bizsolo:value name='CREATOR'/>">
    <div style="display:none" id="textField1Error"><div><font color="red"><span class="error" id="textField1ErrorMsg"></span><a href="#" onclick="textField1ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <input class="ApInptTxt" type="text" id="textField2" name="category" size="20" maxlength="256" value="<bizsolo:value name='category'/>">
    <div style="display:none" id="textField2Error"><div><font color="red"><span class="error" id="textField2ErrorMsg"></span><a href="#" onclick="textField2ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <input class="ApInptTxt" type="text" id="textField4" name="ticketNo" size="20" maxlength="256" value="<bizsolo:value name='ticketNo'/>">
    <div style="display:none" id="textField4Error"><div><font color="red"><span class="error" id="textField4ErrorMsg"></span><a href="#" onclick="textField4ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <input class="ApInptTxt" type="text" id="textField5" name="processTime" size="30" maxlength="256" value="<bizsolo:value name='processTime'/>" alt="number|0|bok">
    <div style="display:none" id="textField5Error"><div><font color="red"><span class="error" id="textField5ErrorMsg"></span><a href="#" onclick="textField5ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <br clear="all">
<input class="ApInptTxt" type="text" id="textField3" name="application" size="20" maxlength="256" value="<bizsolo:value name='application'/>">
    <div style="display:none" id="textField3Error"><div><font color="red"><span class="error" id="textField3ErrorMsg"></span><a href="#" onclick="textField3ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <input class="ApInptTxt" type="text" id="textField6" name="subCategory" size="20" maxlength="256" value="<bizsolo:value name='subCategory'/>">
    <div style="display:none" id="textField6Error"><div><font color="red"><span class="error" id="textField6ErrorMsg"></span><a href="#" onclick="textField6ErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <input class="ApInptTxt" type="text" id="onlineTeamRmks" name="onlineIRDARmks" size="20" maxlength="2000" value="<bizsolo:value name='onlineIRDARmks'/>">
    <div style="display:none" id="onlineTeamRmksError"><div><font color="red"><span class="error" id="onlineTeamRmksErrorMsg"></span><a href="#" onclick="onlineTeamRmksErrorMsgClose();return false;"><img border="0" src="<c:out value='${contextPath}'/>bpmportal/css/apptheme01/images/close.gif"></a></font></div></div>
    <br clear="all">
<font color="#000000">
      </div>
    </font>
  
                    <div id="resizablepanel" style="display:none">
                        <div class="hd">Alert Dialog</div>
                        <div class="bd"></div>
                        <div class="ft"></div>
                    </div> 
</div> 
<div id="southDiv"></div></form>
</body>

<script language="JavaScript">
<!--
function beforeInitControls() {
}
-->
</script>
<script language="JavaScript">
<!--
function userValidationJavascipt() {
  return true;
}
-->
</script>
<sbm:dataSources appName="Marcom_OnlineFlow" appType="bizlogic">
</sbm:dataSources>
<script language="JavaScript">
<!--
    
function button1_onClick(eventContext) {
{
$('#onlineTeamRmks').val($('#rmks').val());;
}
}


  -->
</script>
<!--Initialize extensible widgets.-->
<script language="JavaScript">
<!--
var allWidgets = [];
var businessObjects = [];
var formWidgetHandler;
sbm.utils.onDOMReady = function() {
YAHOO.util.Event.onDOMReady(function(){formWidgetHandler = new FormWidgetHandler(allWidgets);});
}
Ext.onReady(function(){

});
         var viewport = new Bm.util.BmViewport('');
sbm.utils.onFormSubmit = function() {
         if(allWidgets.length > 0 && !formWidgetHandler.validateWidgets()) return false;
        try{
             if(!userValidationJavascipt()) return false;
             if(!sbm.utils.beforeFormSubmit('box+label')) return false;
         }catch(e){}
         document.form.action='<%=response.encodeURL("OnlineTeamIRDAActivity.jsp")%>';
         if(allWidgets.length > 0)formWidgetHandler.saveDataSlots();
         return true;
}
-->
</script>

</html>