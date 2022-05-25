<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="adminBean" scope="session" class="com.savvion.sbmadmin.bean.SBMAdminBean">
</jsp:useBean>

<jsp:useBean id="umBean" scope="session" class="com.savvion.sbmadmin.bean.UserManagementBean">
</jsp:useBean>

<jsp:useBean id="mpBean4" scope="session" class="com.savvion.sbmadmin.bean.MultiPageBean">
</jsp:useBean>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" >
</jsp:useBean>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="SearchGrp" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<SCRIPT LANGUAGE='JavaScript' src="../javascript/utilities.js"></SCRIPT>

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>

<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function search() {
    var qName=document.form.queryQueueByName.value;

    if (qName == "") {
		alert('<sbm:message key="QueryAlert1" />');
        return false;
    }

    document.form.action = "../administration/UserManagementServlet";
	return true;
}

function chooseQueue() {
    var qName;

    if (document.form.queueName != null) {
        if (document.form.queueName.value != null) {
            qName = document.form.queueName.value;
        } else {
            for (var i=0; i < document.form.queueName.length; i++) {
                if (document.form.queueName[i].checked) {
                    qName = document.form.queueName[i].value;
                }
            }
        }
    } else {
		alert('<sbm:message key="QueryAlert2" />');
        return false;
    }

    if (qName == null) {
		alert('<sbm:message key="queueQueryAlert1" />');
        return false;
    }

    window.opener.setChosenQueueName(qName);
    window.close();
    return false;
}
-->
</script>
</head>
<body onload="positionDiv('ListTblBgPadId','ListDivPadID');undoAdjustmentForResolution('ListTblBgPadId','ListDivPadID');">
<%@ include file="../common/include_body.jsp" %>
<form name="form" method="post">
<%@ include file="../common/include_form_body.jsp" %>
<input type="hidden" name="action" value="searchQueue" />
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="SearchQueue" /></td>
          </tr>
          <tr>
            <td class="PopTblEmboss" align="center">
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr>
                  <td>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="FilterBarTbl">
                      <tr align="center">
                        <td class="FilterTblEmboss" width="98%" align="left">
                          <table border="0" cellpadding="2" cellspacing="1">
                            <tr>
                              <td class="SmlTxt" align="right"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_search.gif" width="16" height="16" /></td>
                              <td align="right" nowrap class="SmlTxt"><b><sbm:message key="SearchQueue" />&nbsp;-</b></td>
                              <td class="SmlTxt" align="right">&nbsp;</td>
                              <td class="SmlTxt" align="right"><sbm:message key="QueueName" />:</td>
                              <td>
                                <input type="text" class="InptTxt" name="queryQueueByName" size="12" maxlength="32" />
                              </td>
                              <td align="left">
                                <input type="submit" name="Submit222322" value=" <sbm:message key="go" /> "  class="ResultBtn" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" onclick="return search()" />
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="SepBarTbl">
                      <tr>
                        <td><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2" /></td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ListLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2" /></td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ListTblBgPad" id="ListTblBgPadId" height="454" valign="top" width="100%">&nbsp;
                        </td>
                      </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ListLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2" /></td>
                      </tr>
                    </table>
                    <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
                      <tr align="center">
                        <td class="ButtonDarkBg">
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td class="BtnSpace">
                                <input type="submit" name="Submit222323" value="<sbm:message key="choose" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="return chooseQueue()" />
                              </td>
                              <td class="BtnSpace">
                                <input type="submit" name="Submit222332" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="window.close()" />
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <%@ include file="../common/include_bottom.jsp" %>
  <div id="ListDivPadID" style="position:absolute; left:16px; top:74px; width:96.4%; height:454px; z-index:1; visibility: visible; overflow: auto" class="ListDivPad">
    <table border="0" cellspacing="0" cellpadding="3" class="PageBar">
      <tr>
        <td class="PageBarIn" nowrap="nowrap"><b><sbm:message key="SearchQueue" />:</b> &nbsp;

<% if (umBean.isSameAlphabetQueryForQueue("*")) { %>
        <b><span class="DisabledTxt" disabled="disabled"><sbm:message key="All" /></span></b> &nbsp;|&nbsp;
<% } else { %>
        <b><a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=*" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("A*")) { %>
        <span class="DisabledTxt" disabled="disabled">A</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=A*" class="ActionLnk">A</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("B*")) { %>
        <span class="DisabledTxt" disabled="disabled">B</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=B*" class="ActionLnk">B</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("C*")) { %>
        <span class="DisabledTxt" disabled="disabled">C</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=C*" class="ActionLnk">C</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("D*")) { %>
        <span class="DisabledTxt" disabled="disabled">D</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=D*" class="ActionLnk">D</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("E*")) { %>
        <span class="DisabledTxt" disabled="disabled">E</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=E*" class="ActionLnk">E</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("F*")) { %>
        <span class="DisabledTxt" disabled="disabled">F</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=F*" class="ActionLnk">F</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("G*")) { %>
        <span class="DisabledTxt" disabled="disabled">G</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=G*" class="ActionLnk">G</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("H*")) { %>
        <span class="DisabledTxt" disabled="disabled">H</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=H*" class="ActionLnk">H</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("I*")) { %>
        <span class="DisabledTxt" disabled="disabled">I</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=I*" class="ActionLnk">I</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("J*")) { %>
        <span class="DisabledTxt" disabled="disabled">J</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=J*" class="ActionLnk">J</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("K*")) { %>
        <span class="DisabledTxt" disabled="disabled">K</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=K*" class="ActionLnk">K</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("L*")) { %>
        <span class="DisabledTxt" disabled="disabled">L</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=L*" class="ActionLnk">L</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("M*")) { %>
        <span class="DisabledTxt" disabled="disabled">M</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=M*" class="ActionLnk">M</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("N*")) { %>
        <span class="DisabledTxt" disabled="disabled">N</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=N*" class="ActionLnk">N</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("O*")) { %>
        <span class="DisabledTxt" disabled="disabled">O</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=O*" class="ActionLnk">O</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("P*")) { %>
        <span class="DisabledTxt" disabled="disabled">P</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=P*" class="ActionLnk">P</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("Q*")) { %>
        <span class="DisabledTxt" disabled="disabled">Q</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=Q*" class="ActionLnk">Q</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("R*")) { %>
        <span class="DisabledTxt" disabled="disabled">R</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=R*" class="ActionLnk">R</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("S*")) { %>
        <span class="DisabledTxt" disabled="disabled">S</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=S*" class="ActionLnk">S</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("T*")) { %>
        <span class="DisabledTxt" disabled="disabled">T</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=T*" class="ActionLnk">T</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("U*")) { %>
        <span class="DisabledTxt" disabled="disabled">U</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=U*" class="ActionLnk">U</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("V*")) { %>
        <span class="DisabledTxt" disabled="disabled">V</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=V*" class="ActionLnk">V</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("W*")) { %>
        <span class="DisabledTxt" disabled="disabled">W</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=W*" class="ActionLnk">W</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("X*")) { %>
        <span class="DisabledTxt" disabled="disabled">X</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=X*" class="ActionLnk">X</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("Y*")) { %>
        <span class="DisabledTxt" disabled="disabled">Y</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=Y*" class="ActionLnk">Y</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForQueue("Z*")) { %>
        <span class="DisabledTxt" disabled="disabled">Z</span> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchQueue&queryQueueByAlphabet=Z*" class="ActionLnk">Z</a>&nbsp;
<% } %>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="SepBarTbl">
      <tr>
        <td><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2" /></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td class="TblBgDark">
          <table border="0" cellpadding="2" cellspacing="0" width="100%" class="ListTableCellPad">
            <tbody>
            <tr>
              <td class="ChkBoxSlctAll">&nbsp; </td>
              <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
<% if (umBean.isSortedByResultQueueName()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchQueue&sortedBy=ResultQueueName" class="ColHdrLnk"><sbm:message key="QueueName" /></a></td>
    <% if (umBean.isResultQueueAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?action=searchQueue&sortedBy=ResultQueueName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?action=searchQueue&sortedBy=ResultQueueName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchQueue&sortedBy=ResultQueueName" class="ColHdrLnk"><sbm:message key="QueueName" /></a></td>
<% } %>
            </tr>

<% for (int i = mpBean4.getStartIndex(); i < mpBean4.getEndIndex(); i++) { %>
          <tr>
            <td class="<%= (i%2 == 1) ? "ChkBoxAlt" : "ChkBox" %>">
              <input type="radio" class="InptChkBox" name="queueName" value="<%=umBean.getSearchResultQueueList()[i].getId()%>">
            </td>
            <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%=i+1%></td>
            <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultQueueList()[i].getId()%></td>
          </tr>
<% } %>
            <p></p>
            </tbody>
          </table>
        </td>
      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="3" class="PageBar">
      <tr>
        <td class="PageBarIn"><b><sbm:message key="Pages" />:</b>

<% if (mpBean4.isFirstPageWindow()) { %>
      <span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
<% } else { %>
      <a href="../administration/UserManagementServlet?action=searchQueue&currentPageWindow=<%= mpBean4.getCurrentWindow() - 1 %>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
<% } %>


<% for (int i = mpBean4.getStartPage(); i <= mpBean4.getEndPage(); i++) {
    if ( i > 0 && i <= mpBean4.getTotalPage()) {
        if (mpBean4.getCurrentPage() == i) { %>
          <%= i %>
        <% } else { %>
          <a href="../administration/UserManagementServlet?action=searchQueue&currentPage=<%= i %>" class="ActionLnk"><%= i %></a>
        <% } %>
    <% } %>
<% } %>

<% if (mpBean4.isLastPageWindow()) { %>
      <span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
<% } else { %>
      <a href="../administration/UserManagementServlet?action=searchQueue&currentPageWindow=<%= mpBean4.getCurrentWindow() + 1 %>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;<a>
<% } %>
<!--
        &nbsp;&nbsp;&nbsp;| &nbsp;&nbsp;&nbsp;
        <a href="#" class="ActionLnk" title="Set Paging" onclick="MM_openBrWindow('../common/pop_set_paging.jsp','SetPagingPopup','resizable=yes,width=380,height=142')">
        Set Paging..</a>
-->
      </td>
      <td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<b><%= mpBean4.getTotalEntry() %></b></td>
    </tr>
    </table>
  </div>
</form>
</body>
</html>
