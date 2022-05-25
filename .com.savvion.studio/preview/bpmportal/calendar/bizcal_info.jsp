<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbmadmin.bean.*" %>
<%@ page import="com.savvion.sbmadmin.util.*" %>
<%@ page import="com.savvion.sbm.bizlogic.util.*" %>
<%@page import="com.tdiinc.userManager.User,
                com.tdiinc.userManager.Realm,
                com.savvion.sbmadmin.util.SBMAdminConfig,
                com.savvion.sbm.bpmportal.bizsite.util.SBMBizCalendarUtil,
                com.savvion.acl.impl.SBMACLUser,
		com.savvion.sbm.bizmanage.pagegenerator.ServletTools" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage = "mgmnt_error.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<jsp:useBean id="adminBean" class="com.savvion.sbmadmin.bean.SBMAdminBean" scope="session"/>
<%
  bizManage.setRequest(request);
	bizManage.setResponse(response);

	UserImpl aUser = SBMAdminConfig.getRealm().getUser(bizManage.getCurrentUser().getId());
	String tempcalname = aUser.getAttribute("calendarname");

	Calendar aCalendar = Calendar.getInstance();
	int aYear = aCalendar.get(Calendar.YEAR);

	
	if (tempcalname == null || "-1".equals(tempcalname) || !SBMBizCalendarUtil.self().isCalendarExist(tempcalname))
	{
		String systemCal = SBMBizCalendarUtil.self().getSystemCalendar();
    
    if(systemCal == null || systemCal.length() == 0 || !SBMBizCalendarUtil.self().isCalendarExist(systemCal)) {
			response.sendRedirect("bizcal_myhome_list.jsp");
			return;
		} else {
			tempcalname = systemCal;
		}
	}

	// Here validate the calendar
		SBMBizCalendarUtil aUtil = SBMBizCalendarUtil.self();
		String aValidCalendar = aUtil.getUserBizCalender(tempcalname,bizManage.getSBMACLUser());

	if (aValidCalendar != null) {
	
			response.sendRedirect("bizcal_myhome_view.jsp?calName="+ ServletTools.getUTFEncodedString(aValidCalendar)+"&year="+aYear);
			return;
	} else {
		response.sendRedirect("bizcal_myhome_list.jsp");
	}
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="Information" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
</head>
<body>
<%@ include file="../common/include_body.jsp" %>
<form name="instmgrform">
<input type="hidden" name="actionToPerform" value="" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%
	  	String menu1 = "0";
		  String submenu1 = "5";
	  %>
	  <%@ include file="../common/include_menu_static.jspf" %>
	</td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
  <tr>
    <td class="ListTitle">&nbsp;</td>
    <td width="100%" valign="bottom" class="LinkTrailTd"><b><sbm:message key="Home" />:&nbsp;<sbm:message key="Calendar" />&nbsp;</b></td>
    <td class="SmlTxt">&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="12">
  <tr>
    <td>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="InfoTblBg">
        <tr>
          <td>
            <table width="100%" border="0" cellpadding="6">
              <tr>
                <td width="13%">&nbsp;</td>
                <td width="87%"> <p><span class="Info">There is no default calendar set in the system.</span></p></td>
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
</form>
</body>
</html>
