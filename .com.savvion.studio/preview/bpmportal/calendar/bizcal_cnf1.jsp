<%@ page import="java.util.*,java.net.URLEncoder"%>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.PropsUtil" %>
<%@ page import="com.tdiinc.common.ServletUtilities" %>
<%@ page import="com.savvion.sbm.bizmanage.pagegenerator.ServletTools" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page errorPage = "bizcal_error.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"/>
<%
    String link = "MM_goToURL('parent','bizcal_list.jsp');return document.MM_returnValue";;
        String calname = ServletTools.getUTFString(request,"calName");
    if(calname == null)
        calname="";
    String usercalendar = request.getParameter("usercalendar");
    Calendar aCalendar = Calendar.getInstance();
    int aYear = aCalendar.get(Calendar.YEAR);
    if(usercalendar != null)
       link ="MM_goToURL('parent','../calendar/bizcal_myhome_view.jsp?calName="+ calname +"&year="+aYear+"');return document.MM_returnValue";
       
    if (PropsUtil.isConfirmationDisabled()){
        ServletUtilities.forward("bizcal_myhome_view.jsp?calName="+ ServletTools.getUTFEncodedString(calname) +"&year="+aYear, request, response);
    }
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="Confirmation" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
</head>

<body>
<%@ include file="../common/include_body.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%
        String menu1 = "2";
        String submenu1 = "0";
        if (usercalendar != null) {
            menu1 = "0";
            submenu1 = "5";
        }

      %>
      <%@ include file="../common/include_menu_static.jspf" %>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="breadcrumbSec">
  <tr>
  <%
    if (usercalendar != null) {
  %>
    <td class="SmlTxt" height="20">&nbsp;&nbsp;<a href=javascript:encodeHrefUrl("../calendar/bizcal_myhome_view.jsp?calName=<%=URLEncoder.encode(calname,PortalConstants.UTF8)%>&year=<%=aYear%>") class="ActionLnk"><sbm:message key="Home" />:&nbsp;<sbm:message key="Preferences" />&nbsp;(<sbm:message key="calendar" />)</a>&nbsp;&gt;&nbsp;<a href=javascript:encodeHrefUrl("bizcal_myhome_det.jsp?calName=<%=URLEncoder.encode(calname,PortalConstants.UTF8)%>") class="ActionLnk"><sbm:message key="CalendarDetails" /></a>&nbsp;&gt;&nbsp;<b><sbm:message key="Confirmation" /></b></td>
  <%
    } else {
  %>
    <td class="SmlTxt" height="20">&nbsp;&nbsp;<a href="../calendar/bizcal_list.jsp" class="ActionLnk"><sbm:message key="Administration" />:&nbsp;<sbm:message key="System" />&nbsp;(<sbm:message key="CalendarList" />)</a>&nbsp;&gt;&nbsp;<a href=javascript:encodeHrefUrl("bizcal_det.jsp?calName=<%=URLEncoder.encode(calname,PortalConstants.UTF8)%>") class="ActionLnk"><sbm:message key="CalendarDetails" /></a>&nbsp;&gt;&nbsp;<b><sbm:message key="Confirmation" /></b></td>
  <%
    }
  %>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" align="center" class="ConfirmationTblBg">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="ConfirmationMsgBox">
        <tr>
          <td class="ConfirmationMsg">
          <%
              if(usercalendar != null) {
           %>
              <td width="87%"><span class="ConfirmationMsg"> <sbm:message key="ModifyUserCalConfirmation"/></span></td>
              <%  } else { %>
               <td width="87%"><span class="ConfirmationMsg"> <sbm:message key="ModifyCalConfirmation" params="<%=calname%>" /></span></td>
          <% } %>  
          </td>
        </tr>
      </table></td>
  </tr>
  <tr class="ButtonDarkBg">
    <td align="center" class="ButtonBar">
    <input type="submit" name="OkButton" value=" <sbm:message key="OK" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="<%=link%>" />
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%@ include file="../common/include_copyright_static.jsp" %>
    </td>
  </tr>
  <tr>
    <td>
      <%@ include file="../common/include_bottom.jsp" %>
    </td>
  </tr>
</table>
</body>
</html>
