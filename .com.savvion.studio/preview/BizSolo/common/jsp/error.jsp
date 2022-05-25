<%@ page import="java.io.*" %>
<%@ page isErrorPage="true" import="com.savvion.BizSolo.Server.*, com.savvion.BizSolo.beans.*" %>
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>

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
    String errorMessage = null;
    String stackTrace = null;
    if (exception.getMessage().indexOf("PAKUpdateDS") != -1) {
        errorMessage = "An error occurred while executing the application. The task may have been saved by another user.";
        stackTrace = getStackTrace(exception);
    }
    else if (exception.getMessage().indexOf("PAKSetDS") != -1) {
        errorMessage = "An error occurred while executing the application. The task may have been completed by another user.";
        stackTrace = getStackTrace(exception);
    }
    else if (exception.getMessage().indexOf("PAKReassignWI") != -1) {
		errorMessage = "An error occurred while executing the application. TThe task may have been reassigned to another user or user name specified is invalid.";
        stackTrace = getStackTrace(exception);
    }
    else {
        errorMessage = "An error occurred while executing the application. Some additional information can be found in BizSolo log file." ;
        stackTrace = getStackTrace(exception);
    }

%>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Error Message - CX Process Business Manager</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<script language='JavaScript' src="../javascript/utilities.js"></script>
<script type="text/javascript" language="JavaScript">
<!--
function showDetail() {

    showFirstHideSecond('Detail', 'NoDetail');
}

function hideDetail() {

    showFirstHideSecond('NoDetail', 'Detail');
}

function showFirstHideSecond(first, second) {

    var hiddenCtrl = document.getElementById(second);
    hiddenCtrl.style.visibility = 'hidden';

    var showingCtrl = document.getElementById(first);
    showingCtrl.style.visibility = 'visible';
}

-->
</script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/text.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/bsc_psv.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/calendar_popup.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/common.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/details_view.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/global.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/input_tags.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/list_view.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/menu.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/pop_ups.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bpmportal/css/<%= bizSite.getTheme() %>/reports.css" type="text/css" />
</head>
<body>
<div id="NoDetail" style="position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:1; border: 1px none #000000; visibility: visible; overflow: scroll">
  <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
    <tr>
      <td class="CmnTblEmboss" align="center" valign="top">
        <table border="0" width="100%" cellpadding="6">
          <tr>
            <td class="ValLft" width="92%"><span class="Error"><b>Type: </b> BizSolo error <br>
              <b>Description: </b> <%=errorMessage%> </span></td>
          </tr>
          <tr>
            <td class="ValLft">
              <table border="0" cellspacing="0" cellpadding="2" class="ShowHideDetails" onmouseover="this.className='ShowHideDetailsHover';" onmouseout="this.className='ShowHideDetails';" onclick="showDetail();return document.MM_returnValue" style="width:120px">
                <tr>
                  <td width="100%">&nbsp;Show&nbsp;Details&nbsp;</td>
                  <td><a href="javascript:showDetail()"><img src="/sbm/bpmportal/css/<%=bizSite.getTheme()%>/images/show_details.gif" width="16" height="16" border="0"></a></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
<div id="Detail" style="position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:1; border: 1px none #000000; visibility: hidden; overflow: scroll">
  <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
    <tr>
      <td class="CmnTblEmboss" align="center" valign="top">
        <table border="0" width="100%" cellpadding="6">
          <tr>
            <td class="ValLft" width="92%"><span class="Error"><b>Type: </b> BizSolo error <br>
              <b>Description: </b> <%=errorMessage%> </span></td>
          </tr>
          <tr>
            <td class="ValLft">
              <table border="0" cellspacing="0" cellpadding="2" class="ShowHideDetails" onmouseover="this.className='ShowHideDetailsHover';" onmouseout="this.className='ShowHideDetails';" onclick="hideDetail();return document.MM_returnValue" style="width:120px">
                <tr>
                  <td width="100%">&nbsp;Hide&nbsp;Details&nbsp;</td>
                  <td><a href="javascript:hideDetail()"><img src="/sbm/bpmportal/css/<%=bizSite.getTheme()%>/images/hide_details.gif" width="16" height="16" border="0"></a></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td class="ValLft">
              <pre>
                 <%=stackTrace%>
              </pre>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
