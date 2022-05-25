<%@ page import="java.util.*"%>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.PropsUtil" %>
<%@ page import="com.tdiinc.common.ServletUtilities" %>
<%@ page errorPage = "bizcal_error.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"/>
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
<%
    if (PropsUtil.isConfirmationDisabled()){
        ServletUtilities.forward("bizcal_list.jsp", request, response);
    }
    
    String msgAction = request.getParameter("msgAction");
    if (msgAction == null) msgAction = "";
%>
</head>
<script language="JavaScript">
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
//-->
</script>
</head>
<body>
<%@ include file="../common/include_body.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%
        String menu1 = "2";
          String submenu1 = "1";
      %>
      <%@ include file="../common/include_menu_static.jspf" %>
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
  <tr>
    <td class="ListTitle">&nbsp;</td>
        <td width="100%" valign="bottom" class="LinkTrailTd"><a href="../calendar/bizcal_list.jsp" class="ActionLnk"><sbm:message key="topMenu3" />:&nbsp;<sbm:message key="subMenu13" />&nbsp;(<sbm:message key="CalendarList" />)</a>&nbsp;&gt;&nbsp;<b><sbm:message key="Confirmation" /></b></td>
    <td class="SmlTxt">&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="12">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="ConfirmationTblBg">
        <tr>
          <td>
            <table width="100%" border="0" cellpadding="6">
              <tr>
                <td width="13%">&nbsp;</td>
                <td width="87%"><span class="Confirmation"> 
			<% if (msgAction.equals("unsetDefaultCal")) { %>
				<sbm:message key="bizCalAlert7" /> &quot;<%=request.getParameter("defCalName")%>&quot; <sbm:message key="bizCalAlert37" />
			<% } else { %>
				<sbm:message key="bizCalAlert7" /> &quot;<%=request.getParameter("defCalName")%>&quot; <sbm:message key="bizCalAlert8" />
			<% } %>
		</span></td>
              </tr>
              <tr>
                <td width="13%">&nbsp;</td>
                <td width="87%"> <input type="submit" name="Submit13" value=" <sbm:message key="OK" /> " class="ScrnButton" onMouseOver="this.className='ScrnButtonHover';" onMouseOut="this.className='ScrnButton';" onClick="MM_goToURL('parent','../calendar/bizcal_list.jsp');return document.MM_returnValue">
                  <br> </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>

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
