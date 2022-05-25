<%@ page import="com.savvion.sbm.bpmportal.mvc2.alerts.util.PortalAlertUtil" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="adminBean" scope="session" class="com.savvion.sbmadmin.bean.SBMAdminBean" />
<jsp:useBean id="umBean" scope="session" class="com.savvion.sbmadmin.bean.UserManagementBean" />
<jsp:useBean id="mpBean3" scope="session" class="com.savvion.sbmadmin.bean.MultiPageBean" />
<jsp:useBean id="mpBean4" scope="session" class="com.savvion.sbmadmin.bean.MultiPageBean" />
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="forward_alerts_title" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>

<%! String  queryUserByAlphabet="",queryGroupByAlphabet="", includeAddedResult=""; %>

<script language="JavaScript">
<!--
var user = new Array();
var group = new Array();
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
function checkSubmit(){
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
function setAlertID(){
    window.opener.setAlertID("<%=request.getParameter("alert_id")%>");
}
function chooseUserGroup() {
    var uName = "";
    var gName = "";
    var userGroup = "";
    var checkBoxUser = document.form.userMemberIndex;
    var checkBoxGroup = document.form.groupMemberIndex;
    if (checkBoxUser != null || checkBoxGroup != null){
        if (checkBoxUser != null){
            if (checkBoxUser.length == undefined){
                if (checkBoxUser.checked) {
                   uName = user[0];
                }
            }else{
                for (var i = 0; i < checkBoxUser.length; i++) {
                    if (checkBoxUser[i].checked) {
                        if("" != uName ){
                            uName = uName + "," + user[i];
                        }else{
                            uName = user[i];
                        }
                    }
                }
            }
        }
        if (checkBoxGroup != null) {
            if(checkBoxGroup.length == undefined){
                if (checkBoxGroup.checked) {
                    gName = group[0];
                }
            }else{
                for (var i = 0; i < checkBoxGroup.length; i++) {
                    if (checkBoxGroup[i].checked) {
                       if("" != gName ){
                           gName = gName + "," + group[i];
                       }else{
                           gName = group[i];
                       }
                    }
                }
            }
        }
    } else {
        alert ('<sbm:message key="Select_Member" />');
        return false;
    }
    if (uName == "" && gName == "") {
        alert ('<sbm:message key="Select_Member" />');
        return false;
    }
    userGroup = uName+"::"+gName
    window.opener.setUserGroup(userGroup);
    window.close();
}
-->
</script>
</head>
<body onload="setAlertID();">
<div id="northDiv">
	<%@ include file="../common/include_body.jsp" %>
</div>

<div id="cmdDiv">
	<table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
    <tr align="center">
    	<td class="ButtonDarkBg">
        	<table border="0" cellspacing="0" cellpadding="0">
          	<tr>
            	<td class="BtnSpace">
              		<input type="button" name="btnForward" value=" <sbm:message key="Forward" /> "  class="PopButton" onmouseover="this.className='PopButtonHover';" onclick="return chooseUserGroup();" onmouseout="this.className='PopButton';" />
            	</td>
            	<td class="BtnSpace">
              		<input type="submit" name="btnCancel" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="window.close();return false" />
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
	<form name="searchform" method="post" action="../administration/UserManagementServlet">
		<%@ include file="../common/include_form_body.jsp" %>
		<input type="hidden" name="action" value="searchForwardUserAndGroup" />
		<input type="hidden" name="includeAddedResult" value="<%= includeAddedResult %>" />
		<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="FilterBarTbl">
    	<tr align="center">
      		<td class="FilterTblEmboss" width="98%" align="left">
          		<table border="0" cellpadding="0" cellspacing="1">
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
              		<td class="DataValue"></td>
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
                		<input type="submit" name="searchUserAndGroup" value=" <sbm:message key="go" /> "  class="ResultBtn" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" onclick="search();" />
               		</td>
            	</tr>
          		</table>
      		</td>
    	</tr>
  		</table>
  	</form>	

	<form name="form" method="post" action="../administration/UserManagementServlet" onsubmit="return checkSubmit()">
		<%@ include file="../common/include_form_body.jsp" %>
		<input type="hidden" name="<%= umBean.getUserGroupSearchAction()%>" value="value" />
	  	<input type="hidden" name="refresh" value="false" />
	  	
	  	<div id="ListTblBgPadId1"></div>
	  	<div id="ListTblBgPadId2" style="padding-top:5px"></div>
	</form>
</div>

<div id="ListDivPadIDUsers">
<%
	String _queryUserByAlphabet=request.getParameter("queryUserByAlphabet");
	String _queryGroupByAlphabet=request.getParameter("queryGroupByAlphabet");
	String _includeAddedResult = request.getParameter("includeAddedResult");
	
	if(_includeAddedResult == null)
    	includeAddedResult = "";
	else
    	includeAddedResult = _includeAddedResult;
	if(_queryUserByAlphabet == null && _queryGroupByAlphabet == null) {
    	queryUserByAlphabet  = "";
    	queryGroupByAlphabet = "";	
	}
	if(_queryUserByAlphabet != null) 
		queryUserByAlphabet=_queryUserByAlphabet;
	
	if(_queryGroupByAlphabet != null) 
		queryGroupByAlphabet=_queryGroupByAlphabet;
%>
	<table border="0" cellspacing="0" cellpadding="0" class="PageBarUp">
	<tr>
		<td align="left" nowrap="nowrap" class="PageBarIn">
			<b><sbm:message	key="QuickSearch" />:</b> &nbsp; 
			<% if (umBean.isSameAlphabetQueryForUser("*")) { %>
				<b><sbm:message key="All" /></b> &nbsp;|&nbsp; 
			<% } else { %> 
				<b><a href="<%=PortalAlertUtil.getUserGroupLink("*", queryGroupByAlphabet, includeAddedResult)%>" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp; 
			<% } %>
			<%
    			String[] alph = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M","N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    			for(int i = 0; i < alph.length; i++){
        			if (umBean.isSameAlphabetQueryForUser(alph[i]+"*")) {
						%> <b><%=alph[i]%></b> &nbsp; <%      
					} else { %> 
						<a href="<%=PortalAlertUtil.getUserGroupLink(alph[i]+"*", queryGroupByAlphabet, includeAddedResult)%>" class="ActionLnk"><%=alph[i]%></a>&nbsp; <%
        			}
    			}
			%>
		</td>
	</tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td class="TblBgDark">
			<table border="0" cellpadding="2" cellspacing="0" width="100%" class="ListTableCellPad" ID="listTable" name="listTable">
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
									<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="User_Name" /></a></td>
									<% if (umBean.isResultUserAscending()) { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
									<% } else { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td> 
									<% } %>
								</tr>
								</table>
							</td>
						<% } else { %>
							<td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="User_Name" /></a></td>
						<% } 
     				} else {
				%>
							<td class="ColHdr"><sbm:message key="User_Name" /></td>
				<%   }  %>
				<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     					if (umBean.isSortedByResultUserFirstName()) { %>
							<td class="ColHdrSort">
								<table border="0" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="First_Name" /></a></td>
									<% if (umBean.isResultUserAscending()) { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserFirstName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
									<% } else { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserFirstName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
									<% } %>
								</tr>
								</table>
							</td>
						<% } else { %>
							<td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="First_Name" /></a></td>
						<% } 
     				} else {
				%>
						<td class="ColHdr"><sbm:message key="First_Name" /></a></td>
				<%   }  %>
				<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     					if (umBean.isSortedByResultUserLastName()) { %>
							<td class="ColHdrSort">
								<table border="0" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="Last_Name" /></a></td>
									<% if (umBean.isResultUserAscending()) { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserLastName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
									<% } else { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserLastName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
									<% } %>
								</tr>
								</table>
							</td>
						<% } else { %>
							<td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="Last_Name" /></a></td>
						<% } 
     				} else  {
				%>
						<td class="ColHdr"><sbm:message key="Last_Name" /></td>
				<%  }  %>
				<% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
     					if (umBean.isSortedByResultUserEmail()) { %>
							<td class="ColHdrSort">
								<table border="0" cellspacing="0" cellpadding="0" align="center">
								<tr>
									<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
									<% if (umBean.isResultUserAscending()) { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserEmail" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
									<% } else { %>
										<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserEmail" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
									<% } %>
								</tr>
								</table>
							</td>
						<% } else { %>
							<td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
						<% } 
     				} else {
				%>
						<td class="ColHdr"><sbm:message key="Email" /></td>
				<%  }  %>
			</tr>

			<%
        		int rowUserCounter = 1;
        		int userCounter = 0;
        		
        		for (int i = mpBean3.getStartIndex(); i < mpBean3.getEndIndex(); i++) { %>
					<tr>
						<td class="<%=(rowUserCounter%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>">
							<input type="checkbox" class="InptChkBox" name="userMemberIndex" value="<%= i %>" onclick="dynamicToggleRowSelection(this,document.form.allUserMember,'listTable','userMemberIndex',document.form,<%=rowUserCounter%>,<%= rowUserCounter %>,-1);" />
						</td>
						<td class="<%=(rowUserCounter%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= i+1 %></td>
						<td class="<%=(rowUserCounter%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getId()%></td>
						<td class="<%=(rowUserCounter%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getFirstName()%>&nbsp;</td>
						<td class="<%=(rowUserCounter%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getLastName()%>&nbsp;</td>
						<td class="<%=(rowUserCounter%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getEmail()%>&nbsp;</td>
					</tr>
					<script>user[<%=userCounter%>]= '<%=umBean.getSearchResultUserList()[i].getId()%>';</script>
			<%
			  userCounter++;
								rowUserCounter++;
							}
			%>

			<%
			  if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) {
			%>
					<tr align="center">
						<td class="ValCntr" colspan="7"><br>
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
			</tbody>
			</table>
		</td>
	</tr>
	</table>
	
	<table border="0" cellspacing="0" cellpadding="3" class="PageBarDown">
	<tr>
		<td class="PageBarIn">
			<sbm:message key="Pages" />: 
			<%
			  if (mpBean3.isFirstPageWindow()) {
			%>
				<span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
			<%
			  } else {
			%> 
				<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPageWindow=<%=mpBean3.getCurrentWindow() - 1%>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
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
        					<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPage=<%=i%>" class="ActionLnk"><%=i%></a> 
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
				<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPageWindow=<%=mpBean3.getCurrentWindow() + 1%>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;</a> 
			<%
 			  }
 			%>
		</td>
		<td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<%=mpBean3.getTotalEntry()%></td>
	</tr>
	</table>
</div>

<div id="ListDivPadIDGroup">
	<table border="0" cellspacing="0" cellpadding="3" class="PageBarUp">
    <tr>
      	<td align="left" nowrap="nowrap" class="PageBarIn">
      		<b><sbm:message key="QuickSearch" />:</b> &nbsp;
			<%
			  if (umBean.isSameAlphabetQueryForGroup("*")) {
			%>
        		<b><sbm:message key="All" /></b> &nbsp;|&nbsp;
			<%
			  } else {
			%>
        		<b><a href="<%=PortalAlertUtil.getUserGroupLink(queryUserByAlphabet, "*", includeAddedResult)%>" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
			<%
			  }
			%>
			<%
			  for(int i = 0; i < alph.length; i++){
			    			if (umBean.isSameAlphabetQueryForGroup(alph[i]+"*")) {
			%>
        				<b><%=alph[i]%></b> &nbsp;
			<%
			  } else {
			%>
        				<a href="<%=PortalAlertUtil.getUserGroupLink(queryUserByAlphabet, alph[i]+"*", includeAddedResult)%>" class="ActionLnk"><%=alph[i]%></a>&nbsp;
			<%
			  }
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
				  if ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) != 0) 
									{
				     					if (umBean.isSortedByResultGroupName()) {
				%>
              				<td class="ColHdrSort">
                				<table border="0" cellspacing="0" cellpadding="0" align="center">
                  				<tr>
                    				<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="Group_Name" /></a></td>
    								<%
    								  if (umBean.isResultGroupAscending()) {
    								%>
                    					<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultGroupName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
    								<%
    								  } else {
    								%>
                    					<td><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultGroupName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
    								<%
    								  }
    								%>
                  				</tr>
                				</table>
              				</td>
						<%
						  } else {
						%>
              				<td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&sortedBy=ResultGroupName" class="ColHdrLnk"><sbm:message key="Group_Name" /></a></td>
						<%
						  } 
						     				} else {
						%>
  						<td class="ColHdr"><sbm:message key="Group_Name" /></td>
				<%
				  }
				%>
          	</tr>

			<%
			  int rowGrpCounter = 1;
			    			int groupCounter = 0;
			    		
			    			for (int i = mpBean4.getStartIndex(); i < mpBean4.getEndIndex(); i++) {
			%>
          			<tr>
            			<td class="<%=(rowGrpCounter%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>">
              				<input type="checkbox" class="InptChkBox" name="groupMemberIndex" value="<%=i%>" onclick="dynamicToggleRowSelection(this,document.form.allGroupMember,'listTable2','groupMemberIndex',document.form,<%=rowGrpCounter%>,<%=rowGrpCounter%>,-1);" />
            			</td>
            			<td class="<%=(rowGrpCounter%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%=i+1%></td>
            			<td class="<%=(rowGrpCounter%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultGroupList()[i].getId()%></td>
          			</tr>
            		<script>group[<%=groupCounter%>]= '<%=umBean.getSearchResultGroupList()[i].getId()%>';</script>
			<%
					groupCounter++;
					rowGrpCounter++;
				}
			%>

 			<% if ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) == 0) {%>
          		<tr align="center">
            		<td class="ValCntr" colspan="7"> <br/>
              			<table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                		<tr>
                  			<td valign="top"><img src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16"></td>
                  			<td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoRecFoundGrp" /></span></span></td>
                		</tr>		
              			</table>
              			<br/>
            		</td>
          		</tr>
			<% } %>

			<%  if (((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) && ((mpBean4.getEndIndex() - mpBean4.getStartIndex()) == 0)) {   %>
  				<script language="JavaScript">
        		<!--
            		//document.form.add.disabled = true;
            		//document.form.add.className = 'ScrnButtonDis';
	        	<%
            		if(!"true".equals(includeAddedResult)){
        		%>
            			//document.form.addMore.disabled = true;
            			//document.form.addMore.className = 'ScrnButtonDis';
        		<%
            		}
        		%>	
        		-->
        		</script>
			<% } %>
			</tbody>
        	</table>
      	</td>
    </tr>
  	</table>
  	
  	<table border="0" cellspacing="0" cellpadding="3" class="PageBarDown">
    <tr>
    	<td class="PageBarIn">
    		<sbm:message key="Pages" />:
			<% if (mpBean4.isFirstPageWindow()) { %>
      			<span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
			<% } else { %>
      			<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPageWindow2=<%= mpBean4.getCurrentWindow() - 1 %>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
			<% } %>
			<% for (int i = mpBean4.getStartPage(); i <= mpBean4.getEndPage(); i++) {
    			if ( i > 0 && i <= mpBean4.getTotalPage()) {
        			if (mpBean4.getCurrentPage() == i) { %>
          				<%= i %>
        			<% } else { %>
          				<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPage2=<%= i %>" class="ActionLnk"><%= i %></a>
        			<% } %>
    			<% } %>
			<% } %>
			<% if (mpBean4.isLastPageWindow()) { %>
      			<span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
			<% } else { %>
      			<a href="../administration/UserManagementServlet?action=searchForwardUserAndGroup&currentPageWindow2=<%= mpBean4.getCurrentWindow() + 1 %>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;</a>
			<% } %>
      	</td>
      	<td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<%= mpBean4.getTotalEntry() %></td>
    </tr>
  	</table>
</div>

<script type="text/javascript" language="JavaScript">
<!--
Ext.onReady(function(){
    var viewport = new Bm.util.BmViewport('<sbm:message key="SearchUserGroups" />', {hasForm: false});
    var userlist = new Ext.Panel({
                    contentEl: 'ListDivPadIDUsers',
                    autoScroll:true,
                    border: true,
                    margins:'2 2 2 2',
                    bodyStyle:'padding:3px',
                    height:200,
                    width:'auto',
                    title: '<sbm:message key="UserList" />'
                });
    userlist.render('ListTblBgPadId1');
    var grouplist = new Ext.Panel({
                    contentEl: 'ListDivPadIDGroup',
                    autoScroll:true,
                    border: true,
                    margins:'2 1 2 2',
                    bodyStyle:'padding:3px',
                    height:200,
                    width:'auto',
                    title: '<sbm:message key="GroupList" />'
                });
    grouplist.render('ListTblBgPadId2');
});
//-->	
</script>
</body>
</html>
