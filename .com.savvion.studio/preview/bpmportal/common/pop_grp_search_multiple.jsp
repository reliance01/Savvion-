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

<%
    bizManage.setRequest(request);
    bizManage.setResponse(response);
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
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
    var gName=document.form.queryGroupByName.value;

    if (gName == "") {
        alert('<sbm:message key="QueryAlert1" />');
        return false;
    }

    document.form.action = "../administration/UserManagementServlet";
    return true;
}

function chooseGroup() {
    var gName = "";

    if (document.form.groupName != null) {
        if (document.form.groupName.value != null) {
            gName = document.form.groupName.value;
        } else {
            for (var i=0; i < document.form.groupName.length; i++) {
                if (document.form.groupName[i].checked) {
                    if(gName == '') {
                        gName = document.form.groupName[i].value;
                    } else {
                        gName += ","+document.form.groupName[i].value;
                    }
                }
            }
        }
    } else {
        alert('<sbm:message key="groupQueryAlert1" />');
        return false;
    }

    if (gName == null || trimString(gName)=='') {
        alert('<sbm:message key="groupQueryAlert2" />');
        return false;
    }

    window.opener.setChoosedGroupName(gName);
    window.close();
    return false;
}
-->
</script>
<title><sbm:message key="SearchGrp" /> - <sbm:message key="SBM" /></title>
</head>
<body>
<form name="form" method="post">
<div id="northDiv">
	<%@ include file="../common/include_body.jsp" %>
	<%@ include file="../common/include_form_body.jsp" %>
<input type="hidden" name="actionToPerform" value="searchGroup" />
<input type="hidden" name="selectionType" value="multiple" />
</div>
<div id="filterDiv">
<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="FilterBarTbl">
  <tr align="center">
    <td class="FilterTblEmboss" width="100%" align="left">
      <table border="0" cellpadding="2" cellspacing="1">
        <tr>
          <td class="SmlTxt" align="right"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_search.gif" width="16" height="16"></td>
          <td nowrap class="DataLabelLft"><sbm:message key="SearchGrp" />&nbsp;-</td>
          <td class="DataLabel"><sbm:message key="GrpName" />:</td>
          <td class="DataValue">
            <input type="text" class="InptTxt" name="queryGroupByName" size="20" maxlength="32" />
          </td>
          <td class="DataValue" width="100%">
            <input type="submit" name="Submit222322" value=" <sbm:message key="go" /> "  class="ResultBtn" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" onclick="return search()">
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id="cmdDiv">
<table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
  <tr align="center">
    <td class="ButtonDarkBg">
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="BtnSpace">
            <input type="submit" name="Submit222323" value="<sbm:message key="choose" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="return chooseGroup()" />
          </td>
          <td class="BtnSpace">
            <input type="button" name="Submit222332" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="window.close()" />
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<div id="southDiv">
  <%@ include file="../common/include_bottom.jsp" %>
</div>
<div id="resultDiv">
    <table border="0" cellspacing="0" cellpadding="3" class="PageBarUp">
      <tr>
        <td class="PageBarIn" nowrap="nowrap"><b><sbm:message key="QuickSearch" />:</b> &nbsp;

<% if (umBean.isSameAlphabetQueryForGroup("*")) { %>
        <b><sbm:message key="All" /></b> &nbsp;|&nbsp;
<% } else { %>
        <b><a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=*" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("A*")) { %>
        <b>A</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=A*" class="ActionLnk">A</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("B*")) { %>
        <b>B</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=B*" class="ActionLnk">B</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("C*")) { %>
        <b>C</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=C*" class="ActionLnk">C</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("D*")) { %>
        <b>D</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=D*" class="ActionLnk">D</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("E*")) { %>
        <b>E</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=E*" class="ActionLnk">E</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("F*")) { %>
        <b>F</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=F*" class="ActionLnk">F</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("G*")) { %>
        <b>G</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=G*" class="ActionLnk">G</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("H*")) { %>
        <b>H</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=H*" class="ActionLnk">H</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("I*")) { %>
        <b>I</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=I*" class="ActionLnk">I</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("J*")) { %>
        <b>J</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=J*" class="ActionLnk">J</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("K*")) { %>
        <b>K</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=K*" class="ActionLnk">K</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("L*")) { %>
        <b>L</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=L*" class="ActionLnk">L</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("M*")) { %>
        <b>M</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=M*" class="ActionLnk">M</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("N*")) { %>
        <b>N</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=N*" class="ActionLnk">N</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("O*")) { %>
        <b>O</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=O*" class="ActionLnk">O</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("P*")) { %>
        <b>P</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=P*" class="ActionLnk">P</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("Q*")) { %>
        <b>Q</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=Q*" class="ActionLnk">Q</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("R*")) { %>
        <b>R</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=R*" class="ActionLnk">R</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("S*")) { %>
        <b>S</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=S*" class="ActionLnk">S</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("T*")) { %>
        <b>T</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=T*" class="ActionLnk">T</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("U*")) { %>
        <b>U</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=U*" class="ActionLnk">U</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("V*")) { %>
        <b>V</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=V*" class="ActionLnk">V</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("W*")) { %>
        <b>W</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=W*" class="ActionLnk">W</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("X*")) { %>
        <b>X</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=X*" class="ActionLnk">X</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("Y*")) { %>
        <b>Y</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=Y*" class="ActionLnk">Y</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForGroup("Z*")) { %>
        <b>Z</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&queryGroupByAlphabet=Z*" class="ActionLnk">Z</a>&nbsp;
<% } %>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td class="TblBgDark">
          <table border=0 cellpadding=2 cellspacing=0 width=100% class="ListTableCellPad"  id="listTable" name="listTable">
            <tbody>
            <tr>
              <td class="ChkBoxSlctAll"><input type="checkbox" class="InptChkBox" name="allGroupMember" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="onSelectAll(document.form,this,'groupName','listTable');" /></td>             
              <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
              <% if (umBean.isSortedByResultGroupName()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="GrpName" /></a></td>
    <% if (umBean.isResultGroupAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&sortedBy=ResultGroupName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&sortedBy=ResultGroupName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="GrpName" /></a></td>
<% } %>
            </tr>

<% for (int i = mpBean4.getStartIndex(); i < mpBean4.getEndIndex(); i++) { %>
          <tr>
            <td class="<%= (i%2 == 1) ? "ChkBoxAlt" : "ChkBox" %>">
              <input type="checkbox" class="InptChkBox" id="<%= i %>" name="groupName" value="<%=umBean.getSearchResultGroupList()[i].getId()%>" onclick="toggleRowSelection(this,document.form.allGroupMember,'listTable','groupName',document.form);" />
            </td>
            <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%=i+1%></td>
            <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultGroupList()[i].getId()%></td>
          </tr>
<% } %>
            <p></p>
            </tbody>
          </table>
        </td>
      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="3" class="PageBarDown">
      <tr>
        <td class="PageBarIn"><sbm:message key="Pages" />:

<% if (mpBean4.isFirstPageWindow()) { %>
      <span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
<% } else { %>
      <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&currentPageWindow=<%= mpBean4.getCurrentWindow() - 1 %>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
<% } %>

<% for (int i = mpBean4.getStartPage(); i <= mpBean4.getEndPage(); i++) {
    if ( i > 0 && i <= mpBean4.getTotalPage()) {
        if (mpBean4.getCurrentPage() == i) { %>
          <%= i %>
        <% } else { %>
          <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&currentPage=<%= i %>" class="ActionLnk"><%= i %></a>
        <% } %>
    <% } %>
<% } %>

<% if (mpBean4.isLastPageWindow()) { %>
      <span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
<% } else { %>
      <a href="../administration/UserManagementServlet?actionToPerform=searchGroup&selectionType=multiple&currentPageWindow=<%= mpBean4.getCurrentWindow() + 1 %>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;<a>
<% } %>
<!--
        &nbsp;&nbsp;&nbsp;| &nbsp;&nbsp;&nbsp;
        <a href="#" class="ActionLnk" title="Set Paging" onclick="MM_openBrWindow('../common/pop_set_paging.jsp','SetPagingPopup','resizable=yes,width=380,height=142')">
        Set Paging..</a>
-->
      </td>
      <td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<%= mpBean4.getTotalEntry() %></td>
    </tr>
    </table>
</div>
<script type="text/javascript">
//<!--
    Ext.onReady(function(){
        var viewport = new Bm.util.BmViewport('<sbm:message key="SearchGrp" />');
    });
//-->   
</script>
</form>
</body>
</html>
