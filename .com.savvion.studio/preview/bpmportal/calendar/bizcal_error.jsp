<%@ page import="java.io.*" %>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<%!
    private String getStackTrace( Throwable t ) {

        if ( t != null ) {

             StringWriter os = new StringWriter();
             PrintWriter pw = new PrintWriter( os );
             t.printStackTrace( pw );
             return os.toString();
        }
        else {

             return "";
        }
    }
%>

<%
	bizManage.setRequest(request);
	bizManage.setResponse(response);
    String errorCode = null;
    String errorMessage = null;
    String stackTrace = null;

	errorCode = exception.toString();
	errorMessage = exception.getMessage();
	Throwable error = null;
	stackTrace = getStackTrace(exception);

%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="errorMessage" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script language='JavaScript' src="../javascript/utilities.js"></script>

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script type="text/javascript" language="JavaScript">
<!--
function showDetail() {
    var detail = document.getElementById("exDetail");
    detail.style.visibility = 'visible';

    var showCtrl = document.getElementById("showDetail");
    showCtrl.style.visibility = 'hidden';

    var hideCtrl = document.getElementById("hideDetail");
    hideCtrl.style.visibility = 'visible';
}

function hideDetail() {
    var detail = document.getElementById("exDetail");
    detail.style.visibility = 'hidden';

    var showCtrl = document.getElementById("showDetail");
    showCtrl.style.visibility = 'visible';

    var hideCtrl = document.getElementById("hideDetail");
    hideCtrl.style.visibility = 'hidden';
}
-->
</script>
</head>
<body>

<div id="northDiv">
<%@ include file="../common/include_body.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	  <%
	  	String menu1 = "2";
		  String submenu1 = "0";
	  %>
	  <%@ include file="../common/include_menu_static.jspf" %>
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
  <tr>
    <td class="ListTitle">&nbsp;</td>
    <td width="100%" valign="bottom" class="LinkTrailTd"><b><sbm:message key="errorMessage" /></b></td>
    <td class="SmlTxt">&nbsp;</td>
  </tr>
</table>
</div>

<div id="southDiv">
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
</div>

<div id="resultDiv">
  <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
    <tr>
      <td class="CmnTblEmboss" align="center" valign="top">
        <table border="0" width="100%" cellpadding="6">
          <tr>
            <td class="ValLft" width="92%"><span class="Error"><b><sbm:message key="ApplicationTitle" />: </b><sbm:message key="CalendarPageError" /> <br>
              <b><sbm:message key="AimMessage" />:</b> <%=errorCode%> <br>
              <b><sbm:message key="Description" />: </b> <%=errorMessage%> </span></td>
          </tr>
          <tr id="showDetail">
            <td class="ValLft">
              <table border="0" cellspacing="0" cellpadding="2" class="ShowHideDetails" onmouseover="this.className='ShowHideDetailsHover';" onmouseout="this.className='ShowHideDetails';" onclick="showDetail();return document.MM_returnValue" style="width:120px">
                <tr>
                  <td width="100%">&nbsp;<sbm:message key="showDetails" />&nbsp;</td>
                  <td><a href="javascript:showDetail()"><img alt="" src="../css/<c:out value="${bizManage.theme}" />/images/show_details.gif" width="16" height="16" border="0" /></a></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr id="hideDetail" style="visibility: hidden;">
            <td class="ValLft">
              <table border="0" cellspacing="0" cellpadding="2" class="ShowHideDetails" onmouseover="this.className='ShowHideDetailsHover';" onmouseout="this.className='ShowHideDetails';" onclick="hideDetail();return document.MM_returnValue" style="width:120px">
                <tr>
                  <td width="100%">&nbsp;<sbm:message key="hideDetails" />&nbsp;</td>
                  <td><a href="javascript:hideDetail()"><img alt="" src="../css/<c:out value="${bizManage.theme}" />/images/hide_details.gif" width="16" height="16" border="0" /></a></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr id="exDetail" style="visibility: hidden;">
            <td class="ValLft">
              <pre class="Error">
                  <%= stackTrace %>
              </pre>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

    <script type="text/javascript">
    //<!--
        Ext.onReady(function(){
            var viewport = new Bm.util.BmViewport('', {hasForm: false});
        });
    //-->   
    </script>
</body>
</html>
