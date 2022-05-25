<%@ page import="java.util.*"%>
<%@ page import="com.savvion.sbm.bpmportal.domain.PCWorkItem"%>
<%@ page import="com.savvion.common.appinfo.XPathAppInfo" %>
<%@ page import="com.savvion.common.appinfo.AppInfoKeeper"%>
<%@ page import="com.savvion.common.appinfo.AppInfo"%>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants"%>
<%@ page import="com.savvion.sbm.bpmportal.service.CollaborationService"%>

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

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" >
</jsp:useBean>

<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>


<%!
    public static final String alphabets[] = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"}; 
%>

<%
bizManage.setRequest(request);
bizManage.setResponse(response);

Object task = session.getAttribute("bizsite_taskNameE");
List userList = new ArrayList();
List reassignCollaboratorList = new ArrayList();
boolean isReassign = false;

Map collaboratorMap = (Map) session.getAttribute("collaboratorMap");
Long taskId = (Long)session.getAttribute("bizsite_taskId");

if(taskId!= null) {
    if( collaboratorMap == null) {
        collaboratorMap = (Map) CollaborationService.getCollaboratorList(bizManage.getBizLogicSession(), taskId.longValue());
        session.setAttribute("collaboratorMap", collaboratorMap);
    }

    isReassign = Boolean.parseBoolean(request.getParameter("isReassign"));

    if(isReassign) {
        reassignCollaboratorList = (List) collaboratorMap.get(PortalConstants.REASSIGN);

    }
}
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="SearchUser" />  - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>

<script type="text/javascript">
//<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function search() {
    var uName=document.form.queryByName.value;
    var uFirstName=document.form.queryByFirstName.value;
    var uLastName=document.form.queryByLastName.value;
    var uGroup=document.form.queryByGroup.value;

    if (uName == "" && uFirstName == "" && uLastName == "" && uGroup == "") {
        alert('<sbm:message key="QueryAlert1" />');
        return false;
    }

    return true;
}

function chooseUser() {
    var uName;

    if (document.form.userName != null) {
        if (document.form.userName.value != null) {
            uName = document.form.userName.value;
        } else {
            for (var i=0; i < document.form.userName.length; i++) {
                if (document.form.userName[i].checked) {
                    uName = document.form.userName[i].value;
                }
            }
        }
    } else {
        alert('<sbm:message key="userQueryAlert1" />');
        return false;
    }

    if (uName == null) {
        alert('<sbm:message key="userQueryAlert2" />');
        return false;
    }

    window.opener.setChoosedUserName(uName);
    window.close();
    return false;
}
//-->
</script>
</head>
<body>
<form name="form" method="post" action="../administration/UserManagementServlet">
<%@ include file="../common/include_form_body.jsp" %>
<div id="northDiv">
    <input type="hidden" name="action" value="searchUser" />
    <%@ include file="../common/include_body.jsp" %>
</div>

<div id="filterDiv">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-bottom:3px">
            <tr align="center">
                <td class="FilterTblEmboss" width="98%" align="left"> 
                    <table border="0" cellpadding="0" cellspacing="0" class="FilterBarTbl">
                    <tr>
                        <td class="DataLabel" align="right"><img alt="filter" src="../css/<c:out value="${bizManage.theme}" />/images/filter_icon.gif" width="16" height="16" /></td>
                        <td align="right" nowrap class="DataLabel"><sbm:message key="SearchUser" /> - </td>
                        <td class="DataLabel" align="right"><sbm:message key="username" />:</td>
                        <td class="DataValue">
                            <input type="text" class="InptTxt" name="queryByName" size="12" maxlength="32" />
                        </td>
                        <td class="DataLabel" align="right"><sbm:message key="FirstName" />:</td>
                        <td class="DataValue">
                            <input type="text" class="InptTxt" name="queryByFirstName" size="12" maxlength="32" />
                        </td>
                        <td class="DataLabel" align="right"><sbm:message key="LastName" />:</td>
                        <td class="DataValue">
                            <input type="text" class="InptTxt" name="queryByLastName" size="12" maxlength="32" />
                        </td>
                        <td class="DataLabel" align="right"><sbm:message key="Group" />:</td>
                        <td class="DataValue"> 
                            <input type="text" class="InptTxt" name="queryByGroup" size="12" maxlength="32" />
                        </td>
                        <td align="left" class="DataValue">
                            <input type="submit" name="Submit222322" value=" <sbm:message key="go" /> "  class="ResultBtn" onmouseover="this.className='ResultBtnHover';" onmouseout="this.className='ResultBtn';" onclick="return search()" />
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            </table>
</div>
<div id="resultDiv">            
            <table border="0" cellspacing="0" cellpadding="0" class="PageBarUp">
            <tr>
                <td nowrap class="PageBarIn">
                    <b><sbm:message key="QuickSearch" />:</b> &nbsp;
                    <% if (umBean.isSameAlphabetQueryForUser("*")) { %>
                        <b><sbm:message key="All" /></b> &nbsp;|&nbsp;
                    <% } else { %>
                        <b><a href="../administration/UserManagementServlet?action=searchUser&queryUserByAlphabet=*" class="ActionLnk"><sbm:message key="All" /></a></b> &nbsp;|&nbsp;
                    <% } %>
                    
                    <%
                        for(int i=0;i<alphabets.length;i++) {
                            if(umBean.isSameAlphabetQueryForUser(alphabets[i] + "*")) {
                    %>
                                <%= alphabets[i] %>&nbsp;
                    <%
                            } else { 
                    %>
                                <a href="../administration/UserManagementServlet?action=searchUser&queryUserByAlphabet=<%= alphabets[i] %>*" class="ActionLnk"><%= alphabets[i] %></a>&nbsp;
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
                    <table border="0" cellpadding="2" cellspacing="0" width="100%" class="ListTableCellPad">
                    <tbody>
                    <tr>
                        <td class="ChkBoxSlctAll">&nbsp; </td>
                        <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
                        <%  if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
                            if (umBean.isSortedByResultUserName()) { %>
                                      <td class="ColHdrSort">
                                        <table border="0" cellspacing="0" cellpadding="0" align="center">
                                          <tr>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="username" /></a></td>
                            <% if (umBean.isResultUserAscending()) { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
                            <% } else { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
                            <% } %>
                                          </tr>
                                        </table>
                                      </td>
                        <% } else { %>
                                      <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserName" class="ColHdrLnk"><sbm:message key="username" /></a></td>
                        <% } }
                            else
                            {
                        %>
                            <td class="ColHdr"><sbm:message key="username" /></td>
                        <%   }
                        %>
                        <% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
                             if (umBean.isSortedByResultUserFirstName()) { %>
                                      <td class="ColHdrSort">
                                        <table border="0" cellspacing="0" cellpadding="0" align="center">
                                          <tr>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="FirstName" /></a></td>
                            <% if (umBean.isResultUserAscending()) { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserFirstName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
                            <% } else { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserFirstName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
                            <% } %>
                                          </tr>
                                        </table>
                                      </td>
                        <% } else { %>
                                      <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserFirstName" class="ColHdrLnk"><sbm:message key="FirstName" /></a></td>
                        <% } }
                            else
                            {
                        %>
                        <td class="ColHdr"><sbm:message key="FirstName" /></td>
                        <%   }
                        %>
                        <% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
                            if (umBean.isSortedByResultUserLastName()) { %>
                                      <td class="ColHdrSort">
                                        <table border="0" cellspacing="0" cellpadding="0" align="center">
                                          <tr>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="LastName" /></a></td>
                            <% if (umBean.isResultUserAscending()) { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserLastName" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
                            <% } else { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserLastName" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
                            <% } %>
                                          </tr>
                                        </table>
                                      </td>
                        <% } else { %>
                                      <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserLastName" class="ColHdrLnk"><sbm:message key="LastName" /></a></td>
                        <% } }
                            else
                            {
                        %>
                        <td class="ColHdr"><sbm:message key="LastName" /></td>
                        <%   }
                        %>
                        <% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) != 0) {
                            if (umBean.isSortedByResultUserEmail()) { %>
                                      <td class="ColHdrSort">
                                        <table border="0" cellspacing="0" cellpadding="0" align="center">
                                          <tr>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
                            <% if (umBean.isResultUserAscending()) { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserEmail" title="ascending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_up.gif" width="16" height="16" border="0" /></a></td>
                            <% } else { %>
                                            <td><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserEmail" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_down.gif" width="16" height="16" border="0" /></a></td>
                            <% } %>
                                          </tr>
                                        </table>
                                      </td>
                        <% } else { %>
                                      <td class="ColHdr"><a href="../administration/UserManagementServlet?action=searchUser&sortedBy=ResultUserEmail" class="ColHdrLnk"><sbm:message key="Email" /></a></td>
                        <% } }
                            else
                            {
                        %>
                        <td class="ColHdr"><sbm:message key="Email" /></td>
                        <%   }
                        %>
                    </tr>

                    <%
                      for (int i = mpBean3.getStartIndex(); i < mpBean3.getEndIndex(); i++) {
                                            if(isReassign && reassignCollaboratorList.size()>0) {
                                                if(reassignCollaboratorList.contains(umBean.getSearchResultUserList()[i].getId())) {
                    %>
                              <tr>
                                <td class="<%=(i%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>">
                                  <input type="radio" class="InptChkBox" name="userName" value="<%=umBean.getSearchResultUserList()[i].getId()%>" />
                                </td>
                                <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%=i+1%></td>
                                <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getId()%></td>
                                <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getFirstName()%>&nbsp;</td>
                                <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getLastName()%>&nbsp;</td>
                                <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getEmail()%>&nbsp;</td>
                              </tr>
                    <%
                      }
                                            } else {
                    %>
                          <tr>
                            <td class="<%=(i%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>">
                              <input type="radio" class="InptChkBox" name="userName" value="<%=umBean.getSearchResultUserList()[i].getId()%>" />
                            </td>
                            <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%=i+1%></td>
                            <td class="<%=(i%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%=umBean.getSearchResultUserList()[i].getId()%></td>
                            <td class="<%= (i%2 == 1) ? "ValLftAlt" : "ValLft" %>"><%= umBean.getSearchResultUserList()[i].getFirstName() %>&nbsp;</td>
                            <td class="<%= (i%2 == 1) ? "ValLftAlt" : "ValLft" %>"><%= umBean.getSearchResultUserList()[i].getLastName() %>&nbsp;</td>
                            <td class="<%= (i%2 == 1) ? "ValLftAlt" : "ValLft" %>"><%= umBean.getSearchResultUserList()[i].getEmail() %>&nbsp;</td>
                          </tr>
                    
                    <%
                            }
                        }
                    
                    %>

                    <% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) {%>
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
                    <% } %>
                    </tbody>
                    </table>
                </td>
            </tr>
            </table>
            
            <table border="0" cellspacing="0" cellpadding="3" class="PageBarDown">
            <tr>
                <td>
                    <sbm:message key="Pages" />:
                    <% if (mpBean3.isFirstPageWindow()) { %>
                          <span class="DisabledTxt">&lt;<sbm:message key="PREVIOUS_PAGE" /></span>
                    <% } else { %>
                          <a href="../administration/UserManagementServlet?action=searchUser&currentPageWindow=<%= mpBean3.getCurrentWindow() - 1 %>" class="ActionLnk">&lt;<sbm:message key="PREVIOUS_PAGE" />&nbsp;<a>
                    <% } %>
    
                    <% for (int i = mpBean3.getStartPage(); i <= mpBean3.getEndPage(); i++) {
                        if ( i > 0 && i <= mpBean3.getTotalPage()) {
                            if (mpBean3.getCurrentPage() == i) { %>
                              <%= i %>
                            <% } else { %>
                              <a href="../administration/UserManagementServlet?action=searchUser&currentPage=<%= i %>" class="ActionLnk"><%= i %></a>
                            <% } %>
                        <% } %>
                    <% } %>
    
                    <% if (mpBean3.isLastPageWindow()) { %>
                          <span class="DisabledTxt"><sbm:message key="NEXT_PAGE" />&gt;</span>
                    <% } else { %>
                          <a href="../administration/UserManagementServlet?action=searchUser&currentPageWindow=<%= mpBean3.getCurrentWindow() + 1 %>" class="ActionLnk"><sbm:message key="NEXT_PAGE" />&gt;</a>
                    <% } %>
                </td>
                <td nowrap="nowrap" align="right"><sbm:message key="TOTAL" />:&nbsp;<b><%= mpBean3.getTotalEntry() %></b></td>
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
                    <input type="button" name="Submit222323" value="<sbm:message key="Add" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="return chooseUser()" />
                </td>
                <td class="BtnSpace">
                    <input type="submit" name="Submit222332" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';"  onclick="window.close()" />
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
  
    <script type="text/javascript">
    //<!--
        Ext.onReady(function(){
            var viewport = new Bm.util.BmViewport('<sbm:message key="SearchUser" />');
            
            <% if ((mpBean3.getEndIndex() - mpBean3.getStartIndex()) == 0) {%>
                document.form.Submit222323.disabled = true;
                document.form.Submit222323.className = 'ScrnButtonDis';
            <% } %>
        });
    //-->   
    </script>
</form>
</body>
</html>
