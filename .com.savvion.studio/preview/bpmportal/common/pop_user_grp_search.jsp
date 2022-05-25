<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="adminBean" scope="session" class="com.savvion.sbmadmin.bean.SBMAdminBean">
</jsp:useBean>

<jsp:useBean id="umBean" scope="session" class="com.savvion.sbmadmin.bean.UserManagementBean">
</jsp:useBean>

<jsp:useBean id="mpBean3" scope="session" class="com.savvion.sbmadmin.bean.MultiPageBean">
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
<title><sbm:message key="AddUsersGroups" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<SCRIPT LANGUAGE='JavaScript' src="../javascript/utilities.js"></SCRIPT>
<SCRIPT LANGUAGE='JavaScript' src="../javascript/prototype.js"></SCRIPT>
<SCRIPT LANGUAGE='JavaScript' src="../javascript/scriptaculous.js"></SCRIPT>
<script type="text/javascript" language="JavaScript" src="../javascript/engine.js"></script>
<script type="text/javascript" language="JavaScript" src="../javascript/util.js"></script>

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<%! String  queryUserByAlphabet="",queryGroupByAlphabet="", includeAddedResult="", component="";
%>
<script language="JavaScript">
<!--
var user = new Array();
var group = new Array();
var userdet= new Array();
var groupdet = new Array();

function checkAllUserMember() {
    for (var i = 0; i < document.form.elements.length; i++) {
        var e = document.form.elements[i];
        if (e.name != "allUserMember" && e.name != "allGroupMember" && e.name != "groupMemberIndex")
            e.checked = document.form.allUserMember.checked;
    }
}

function checkAllGroupMember() {
    for (var i = 0; i < document.form.elements.length; i++) {
        var e = document.form.elements[i];
        if (e.name != "allUserMember" && e.name != "allGroupMember" && e.name != "userMemberIndex")
            e.checked = document.form.allGroupMember.checked;
    }
}

function checkSubmit()
{
    var checkBoxUser = document.form.userMemberIndex;
    var checkBoxGroup = document.form.groupMemberIndex;

    if (checkBoxUser == null && checkBoxGroup == null) {
        alert('<sbm:message key="userGroupQueryAlert1" />');
        return false;
    }

    var isChecked = false;

    if (checkBoxUser != null) {
        if (checkBoxUser.checked) {
            isChecked = true;
        } else {
            for (var i = 0; i < checkBoxUser.length; i++) {
                if (checkBoxUser[i].checked) {
                    isChecked = true;
                    return true;
                }
            }
        }
    }

    if (checkBoxGroup != null) {
        if (checkBoxGroup.checked) {
            isChecked = true;
        } else {
            for (var i = 0; i < checkBoxGroup.length; i++) {
                if (checkBoxGroup[i].checked) {
                    isChecked = true;
                    return true;
                }
            }
        }
    }

    if (!isChecked) {
        alert ('<sbm:message key="Select_Member" />');
    }
    return isChecked;
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
    window.open(theURL,winName,features);
}

function search() {
    var uName=document.searchform.queryByName.value;
    var uFirstName=document.searchform.queryByFirstName.value;
    var uLastName=document.searchform.queryByLastName.value;
    var uGroup=document.searchform.queryByGroup.value;
    var gName=document.searchform.queryGroupByName.value;
    
    
    if (uName == "" && uFirstName == "" && uLastName == "" && uGroup == "" && gName == "") {
        alert('<sbm:message key="QueryAlert1" />');
        return false;
    }

    return true;
}


function chooseUser() {

	if('<%=includeAddedResult%>' != 'true') {
		return true;
	}
    var uName = "";
    var udet = "";
    var gdet = "";

    var checkBoxUser = document.getElementsByName('userMemberIndex');
    var checkBoxGroup = document.getElementsByName('groupMemberIndex');

    if (checkBoxUser != null || checkBoxGroup != null){
		if (checkBoxUser != null){
			for (var i = 0; i < checkBoxUser.length; i++) {
				if (checkBoxUser[i].checked) {
					if("" != uName )
						uName = uName + "," + user[i];
					else
						uName = user[i];
					if("" != udet )
						udet = udet + "," + userdet[i];
					else
						udet = userdet[i];
				}
			}
		}
		if (checkBoxGroup != null) {
			for (var i = 0; i < checkBoxGroup.length; i++) {
				if (checkBoxGroup[i].checked) {
					
					if("" != uName )
						uName = uName + "," + group[i];
					else
						uName = group[i];
					if("" != gdet )
						gdet = gdet + "," + groupdet[i];
					else
						gdet = groupdet[i];
				}
			}
		}

    } else {
		alert ('<sbm:message key="Select_Member" />');
        return false;
    }

    if (uName == null) {
		alert ('<sbm:message key="Select_Member" />');
        return false;
    }

	if('<%=component%>' == 'dashboard') {
		window.opener.setChoosedUserDetails(udet);
		window.opener.setChoosedGroupDetails(gdet);
	} else {
    		window.opener.setChoosedUserName(uName);
    	}
    window.close();
    return false;
}
-->
</script>
</head>
<body>
<div id="northDiv">
<%@ include file="../common/include_body.jsp" %>
</div>
<div id="filterDiv">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="FilterBarTbl">
    <tr align="center">
      <td class="FilterTblEmboss" width="98%" align="left">
      <form name="searchform" method="post" action="../administration/UserManagementServlet">
      	<%@ include file="../common/include_form_body.jsp" %>
		<input type="hidden" name="action" value="searchUserAndGroup" />
		<input type="hidden" name="includeAddedResult" value="<%= includeAddedResult%>" />
		<input type="hidden" name="component" value="<%= component%>" />
          <table border="0" cellpadding="2" cellspacing="1">
            <tr>
              <td align="right" valign="middle" class="SmlTxt"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_search.gif" width="16" height="16" /></td>
              <td nowrap="nowrap"  class="DataLabelLft" align="left"><sbm:message key="SearchUser" />&nbsp;</td>
              <td nowrap="nowrap"  class="DataLabel"><sbm:message key="username" />:</td>
              <td class="DataValue">
                <input type="text" class="InptTxt" name="queryByName" size="12" maxlength="32" />
              </td>
              <td nowrap="nowrap"  class="DataLabel"><sbm:message key="FirstName" />:</td>
              <td class="DataValue">
                <input type="text" class="InptTxt" name="queryByFirstName" size="12" maxlength="32" />
              </td>
              <td nowrap="nowrap"  class="DataLabel"><sbm:message key="LastName" />:</td>
              <td class="DataValue">
                <input type="text" class="InptTxt" name="queryByLastName" size="12" maxlength="32" />
              </td>
              <td nowrap="nowrap"  class="DataLabel"><sbm:message key="Group" />:</td>
              <td class="DataValue">
                <input type="text" class="InptTxt" name="queryByGroup" size="12" maxlength="32" />
              </td>
              <td class="DataValue">
              </td>
            </tr>
            <tr>
              <td class="SmlTxt" align="right"></td>
              <td  class="DataLabelLft"><sbm:message key="SearchGrp" />&nbsp;</td>
              <td  class="DataLabel"><sbm:message key="GrpName" />:</td>
              <td class="DataValue">
                <input type="text" class="InptTxt" name="queryGroupByName" size="12" />
              </td>
              <td colspan="6"></td>
              <td width="100%" align="center" class="DataValue">
                <input type="submit" name="searchUserAndGroup" value=" <sbm:message key="go" /> "  class="ResultBtn" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" onclick="return search();" />
              </td>
            </tr>
          </table>
        </form>
      </td>
    </tr>
  </table>
</div>
<div id="resultDiv">
<form name="form" method="post" action="../administration/UserManagementServlet" onsubmit="return checkSubmit()">
<%@ include file="../common/include_form_body.jsp" %>
<input type="hidden" name="<%= umBean.getUserGroupSearchAction()%>" value="value" />
<input type="hidden" name="refresh" value="false" />
  <div id="ListTblBgPadId1"></div>
  <div id="ListTblBgPadId2" style="padding-top:5px"></div>
  <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
    <tr align="center">
      <td class="ButtonDarkBg">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="BtnSpace">
              <input type="submit" name="add" value=" <sbm:message key="Add" /> "  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="return chooseUser();"/>
                   </td>
            <%
      if (!"true".equals(includeAddedResult)) {
            %>
            <td class="BtnSpace">
              <input type="submit" name="addMore" value="<sbm:message key="AddnContinue" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" />
                   </td>
            <%}
            %>
            <td class="BtnSpace">
              <input type="submit" name="Submit222332" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="window.close();return false" />
                   </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </form>
</div>
<div id="cmdDiv">
</div>
<div id="southDiv">
<%@ include file="../common/include_bottom.jsp" %>
</div>
<div id="ListDivPadIDUsers">
<%
String _queryUserByAlphabet=request.getParameter("queryUserByAlphabet");
String _queryGroupByAlphabet=request.getParameter("queryGroupByAlphabet");
String _includeAddedResult = request.getParameter("includeAddedResult");
String _component = request.getParameter("component");
if(_includeAddedResult == null)
	includeAddedResult = "";
else
	includeAddedResult = _includeAddedResult;
if(_component == null)
	component = "";
else
	component = _component;
if(_queryUserByAlphabet == null && _queryGroupByAlphabet == null) {
    queryUserByAlphabet  = "";
    queryGroupByAlphabet = "";
}
if(_queryUserByAlphabet != null) queryUserByAlphabet=_queryUserByAlphabet;
if(_queryGroupByAlphabet != null) queryGroupByAlphabet=_queryGroupByAlphabet;
%>
  <table border="0" cellspacing="0" cellpadding="3" class="PageBarUp">
    <tr>
      <td align="left" nowrap="nowrap" class="PageBarIn"><b><sbm:message key="QuickSearch" />:</b> &nbsp;

<% if (umBean.isSameAlphabetQueryForUser("*")) { %>
        <b><sbm:message key="All" /></b> &nbsp;|&nbsp;
<% } else { %>
        <b><a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("A*")) { %>
        <b>A</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=A*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">A</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("B*")) { %>
        <b>B</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=B*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">B</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("C*")) { %>
        <b>C</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=C*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">C</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("D*")) { %>
        <b>D</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=D*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">D</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("E*")) { %>
        <b>E</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=E*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">E</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("F*")) { %>
        <b>F</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=F*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">F</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("G*")) { %>
        <b>G</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=G*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">G</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("H*")) { %>
        <b>H</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=H*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">H</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("I*")) { %>
        <b>I</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=I*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">I</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("J*")) { %>
        <b>J</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=J*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">J</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("K*")) { %>
        <b>K</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=K*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">K</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("L*")) { %>
        <b>L</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=L*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">L</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("M*")) { %>
        <b>M</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=M*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">M</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("N*")) { %>
        <b>N</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=N*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">N</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("O*")) { %>
        <b>O</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=O*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">O</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("P*")) { %>
        <b>P</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=P*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">P</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("Q*")) { %>
        <b>Q</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=Q*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Q</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("R*")) { %>
        <b>R</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=R*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">R</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("S*")) { %>
        <b>S</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=S*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">S</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("T*")) { %>
        <b>T</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=T*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">T</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("U*")) { %>
        <b>U</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=U*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">U</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("V*")) { %>
        <b>V</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=V*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">V</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("W*")) { %>
        <b>W</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=W*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">W</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("X*")) { %>
        <b>X</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=X*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">X</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("Y*")) { %>
        <b>Y</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=Y*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Y</a>&nbsp;
<% } %>
<% if (umBean.isSameAlphabetQueryForUser("Z*")) { %>
        <b>Z</b> &nbsp;
<% } else { %>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryUserByAlphabet=Z*&queryGroupByAlphabet=<%=queryGroupByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Z</a>&nbsp;
<% } %>
      </td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td class="TblBgDark">
        <table border=0 cellpadding=2 cellspacing=0 width=100% class="ListTableCellPad" ID="listTable" name="listTable">
          <tbody>
          <tr>
            <td class="ChkBoxSlctAll">
              <input type="checkbox" class="InptChkBox" name="allUserMember" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="onSelectAll(document.form,this,'userMemberIndex','listTable');" />
            </td>
            <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     if (umBean.isSortedByResultUserName()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="User_Name" /></a></td>
    <% if (umBean.isResultUserAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="User_Name" /></a></td>
<% } }
     else
     {
%>
<td class="ColHdr"><sbm:message key="User_Name" /></td>
<%   }  %>
<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     if (umBean.isSortedByResultUserFirstName()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="First_Name" /></a></td>
    <% if (umBean.isResultUserAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserFirstName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserFirstName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="First_Name" /></a></td>
<% } }
     else
     {
%>
<td class="ColHdr"><sbm:message key="First_Name" /></a></td>
<%   }  %>
<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     if (umBean.isSortedByResultUserLastName()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="Last_Name" /></a></td>
    <% if (umBean.isResultUserAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserLastName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserLastName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="Last_Name" /></a></td>
<% } }
     else
     {
%>
<td class="ColHdr"><sbm:message key="Last_Name" /></td>
<%   }  %>
<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     if (umBean.isSortedByResultUserEmail()) { %>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
    <% if (umBean.isResultUserAscending()) { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserEmail" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <% } else { %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserEmail" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <% } %>
                  </tr>
                </table>
              </td>
<% } else { %>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
<% } }
     else
     {
%>
<td class="ColHdr"><sbm:message key="Email" /></td>
<%   }  %>
          </tr>
<% int counter = 0;%>
<% for (int i = mpBean3.getStartIndex(); i < mpBean3.getEndIndex(); i++) { %>
          <tr>
            <td class=<% if (i%2 == 1) { %>"ChkBoxAlt"<% }  else { %>"ChkBox"<% } %>>
              <input type="checkbox" class="InptChkBox" name="userMemberIndex" value="<%= i %>" onclick="toggleRowSelection(this,document.form.allUserMember,'listTable','userMemberIndex',document.form);" />
            </td>
            <td class=<% if (i%2 == 1) { %>"ValCntrAlt"<% } else { %>"ValCntr"<% } %>><%= i+1 %></td>
            <td class=<% if (i%2 == 1) { %>"ValLftAlt"<% } else { %>"ValLft"<% } %>><%=umBean.getSearchResultUserList()[i].getId()%></td>
            <td class=<%if (i%2 == 1) {%>"ValLftAlt"<%} else {%>"ValLft"<%}%>><%=umBean.getSearchResultUserList()[i].getFirstName()%>&nbsp;</td>
            <td class=<%if (i%2 == 1) {%>"ValLftAlt"<%} else {%>"ValLft"<%}%>><%=umBean.getSearchResultUserList()[i].getLastName()%>&nbsp;</td>
            <td class=<%if (i%2 == 1) {%>"ValLftAlt"<%} else {%>"ValLft"<%}%>><%=umBean.getSearchResultUserList()[i].getEmail()%>&nbsp;</td>
          </tr>
			<script>user[<%=counter%>]= "<%=umBean.getSearchResultUserList()[i].getId()%>";</script>
			<script>userdet[<%=counter%>]= "<%=umBean.getSearchResultUserList()[i].getId()%>|<%=umBean.getSearchResultUserList()[i].getFirstName()%>|<%=umBean.getSearchResultUserList()[i].getLastName()%>|<%=umBean.getSearchResultUserList()[i].getEmail()%>";</script>
	<%
	  counter++;
	%>
<%
  }
%>

 <%
   if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) {
 %>
          <tr align="center">
            <td class="ValCntr" colspan="7"> <br>
              <table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                <tr>
                  <td valign="top"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16" /></td>
                  <td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoRecFoundUser" /></span></span></td>
                </tr>
              </table>
              <br>
            </td>
          </tr>

<%
  }
%>

       <p></p>
          </tbody>
        </table>
      </td>
    </tr>
  </table>
  <table border="0" cellspacing="0" cellpadding="3" class="PageBarDown">
    <tr>
      <td class="PageBarIn"><sbm:message key="Pages" />:
<%
        if (mpBean3.isFirstPageWindow()) {
      %>
      <span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
<%
  } else {
%>
      <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPageWindow=<%=mpBean3.getCurrentWindow() - 1%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
<%
  }
%>

<%
  for (int i = mpBean3.getStartPage(); i <= mpBean3.getEndPage(); i++) {
    if ( i > 0 && i <= mpBean3.getTotalPage()) {
    if (mpBean3.getCurrentPage() == i) {
%>
          <%=i%>
        <%
          } else {
        %>
          <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPage=<%=i%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><%=i%></a>
        <%
          }
        %>
    <%
      }
    %>
<%
  }
%>

<%
  if (mpBean3.isLastPageWindow()) {
%>
      <span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
<%
  } else {
%>
      <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPageWindow=<%=mpBean3.getCurrentWindow() + 1%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;<a>
<%
  }
%>
<!--
        &nbsp;&nbsp;&nbsp;| &nbsp;&nbsp;&nbsp;
        <a href="#" class="ActionLnk" title="Set Paging" onclick="MM_openBrWindow('../common/pop_set_paging.jsp','SetPagingPopup','resizable=yes,width=380,height=142')">
        Set Paging..</a>
-->
      </td>
      <td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<%=mpBean3.getTotalEntry()%></td>
    </tr>
  </table>
</div>
<div id="ListDivPadIDGroup">
  <table border="0" cellspacing="0" cellpadding="3" class="PageBarUp">
    <tr>
      <td align="left" nowrap="nowrap" class="PageBarIn"><b><sbm:message key="QuickSearch" />:</b> &nbsp;
<%
  if (umBean.isSameAlphabetQueryForGroup("*")) {
%>
        <b><sbm:message key="All" /></b> &nbsp;|&nbsp;
<%
  } else {
%>
        <b><a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("A*")) {
%>
        <b>A</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=A*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">A</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("B*")) {
%>
        <b>B</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=B*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">B</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("C*")) {
%>
        <b>C</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=C*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">C</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("D*")) {
%>
        <b>D</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=D*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">D</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("E*")) {
%>
        <b>E</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=E*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">E</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("F*")) {
%>
        <b>F</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=F*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">F</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("G*")) {
%>
        <b>G</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=G*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">G</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("H*")) {
%>
        <b>H</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=H*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">H</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("I*")) {
%>
        <b>I</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=I*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">I</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("J*")) {
%>
        <b>J</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=J*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">J</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("K*")) {
%>
        <b>K</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=K*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">K</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("L*")) {
%>
        <b>L</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=L*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">L</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("M*")) {
%>
        <b>M</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=M*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">M</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("N*")) {
%>
        <b>N</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=N*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">N</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("O*")) {
%>
        <b>O</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=O*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">O</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("P*")) {
%>
        <b>P</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=P*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">P</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("Q*")) {
%>
        <b>Q</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=Q*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Q</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("R*")) {
%>
        <b>R</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=R*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">R</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("S*")) {
%>
        <b>S</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=S*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">S</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("T*")) {
%>
        <b>T</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=T*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">T</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("U*")) {
%>
        <b>U</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=U*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">U</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("V*")) {
%>
        <b>V</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=V*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">V</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("W*")) {
%>
        <b>W</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=W*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">W</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("X*")) {
%>
        <b>X</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=X*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">X</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("Y*")) {
%>
        <b>Y</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=Y*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Y</a>&nbsp;
<%
  }
%>
<%
  if (umBean.isSameAlphabetQueryForGroup("Z*")) {
%>
        <b>Z</b> &nbsp;
<%
  } else {
%>
        <a href="../administration/UserManagementServlet?action=searchUserAndGroup&queryGroupByAlphabet=Z*&queryUserByAlphabet=<%=queryUserByAlphabet%>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">Z</a>&nbsp;
<%
  }
%>
      </td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td class="TblBgDark">
        <table border="0" cellpadding="2" cellspacing="0" width="100%" class="ListTableCellPad" ID="listTable2" name="listTable2">
          <tbody>
          <tr>
            <td class="ChkBoxSlctAll">
              <input type="checkbox" class="InptChkBox" name="allGroupMember" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="onSelectAll(document.form,this,'groupMemberIndex','listTable2');" />
            </td>
            <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
<%
  if ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) != 0) {
     if (umBean.isSortedByResultGroupName()) {
%>
              <td class="ColHdrSort">
                <table border="0" cellspacing="0" cellpadding="0" align="center">
                  <tr>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="Group_Name" /></a></td>
    <%
      if (umBean.isResultGroupAscending()) {
    %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultGroupName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    <%
      } else {
    %>
                    <td><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultGroupName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    <%
      }
    %>
                  </tr>
                </table>
              </td>
<%
  } else {
%>
              <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUserAndGroup&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="Group_Name" /></a></td>
<%
  } }
     else
     {
%>
  <td class="ColHdr"><sbm:message key="Group_Name" /></td>
<%
  }
%>
          </tr>

<%
  counter = 0;
%>
<%
  for (int i = mpBean4.getStartIndex(); i < mpBean4.getEndIndex(); i++) {
%>
          <tr>
            <td class=<%if (i%2 == 1) {%>"ChkBoxAlt"<%}  else {%>"ChkBox"<%}%>>
              <input type="checkbox" class="InptChkBox" name="groupMemberIndex" value="<%=i%>" onclick="toggleRowSelection(this,document.form.allGroupMember,'listTable2','groupMemberIndex',document.form);" />
            </td>
            <td class=<%if (i%2 == 1) {%>"ValCntrAlt"<%} else {%>"ValCntr"<%}%>><%=i+1%></td>
            <td class=<%if (i%2 == 1) {%>"ValLftAlt"<%} else {%>"ValLft"<%}%>><%=umBean.getSearchResultGroupList()[i].getId()%></td>
          </tr>
			<script>group[<%=counter%>]= '<%=umBean.getSearchResultGroupList()[i].getId()%>';</script>
			<script>groupdet[<%=counter%>]= '<%=umBean.getSearchResultGroupList()[i].getId()%>|<%= umBean.getSearchResultGroupList()[i].getDesc() %>';</script>
	<%counter++;%>
<% } %>

 <% if ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) == 0) {%>
          <tr align="center">
            <td class="ValCntr" colspan="7"> <br>
              <table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                <tr>
                  <td valign="top"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16"></td>
                  <td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoRecFoundGrp" /></span></span></td>
                </tr>
              </table>
              <br>
            </td>
          </tr>

<% } %>

<%  if (((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) && ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) == 0)) {   %>

  <script language="JavaScript">
        <!--
            document.form.add.disabled = true;
            document.form.add.className = 'ScrnButtonDis';
		<%
			if(!"true".equals(includeAddedResult)){
		%>
		        var addMoreEltArr = document.getElementsByName('addMore');
		        if(addMoreEltArr != 'undefined' && addMoreEltArr !=null & addMoreEltArr.length > 0){
                addMoreEltArr[0].disabled = true;
                addMoreEltArr[0].className = 'ScrnButtonDis';
            }        
		<%
			}
		%>
        -->
        </script>

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
      <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPageWindow2=<%= mpBean4.getCurrentWindow() - 1 %>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
<% } %>

<% for (int i = mpBean4.getStartPage(); i <= mpBean4.getEndPage(); i++) {
    if ( i > 0 && i <= mpBean4.getTotalPage()) {
        if (mpBean4.getCurrentPage() == i) { %>
          <%= i %>
        <% } else { %>
          <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPage2=<%= i %>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><%= i %></a>
        <% } %>
    <% } %>
<% } %>

<% if (mpBean4.isLastPageWindow()) { %>
      <span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
<% } else { %>
      <a href="../administration/UserManagementServlet?action=searchUserAndGroup&currentPageWindow2=<%= mpBean4.getCurrentWindow() + 1 %>&includeAddedResult=<%=includeAddedResult%>&component=<%=component%>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;<a>
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
<script type="text/javascript" language="JavaScript">
<!--
    Ext.onReady(function(){
	     var userlist = new Ext.Panel({
                        contentEl: 'ListDivPadIDUsers',
                        autoScroll:true,
                        border: false,
                        margins:'2 2 2 2',
                        //bodyStyle:'padding:3px',
                        height:'auto',
                        collapsible:true,
                        width:'auto',
                        frame:false,
                        unstyled: true,
                        title: '<font class="PsvTitle">'+'<sbm:message key="UserList" />'+'</font>'
                    });
        userlist.render('ListTblBgPadId1');
        var grouplist = new Ext.Panel({
                        contentEl: 'ListDivPadIDGroup',
                        autoScroll:true,
                        border: false,
                        margins:'2 1 2 2',
                        //bodyStyle:'padding:3px',
                        height:'auto',
                        collapsible:true,
                        width:'auto',
                        frame:false,
                        unstyled: true,
                        title: '<font class="PsvTitle">'+'<sbm:message key="GroupList" />'+'</font>'
                    });
        grouplist.render('ListTblBgPadId2');
	    var viewport = new Bm.util.BmViewport('<sbm:message key="SearchUserGroups" />', {hasForm:false});
	});
//-->	
</script>
</body>
</html>
