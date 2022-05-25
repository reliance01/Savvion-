<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbmadmin.common.SBMAdminUser" %>
<%@ page import="com.savvion.sbmadmin.common.SBMAdminConstant" %>
<%@ page import="com.savvion.sbm.bpmportal.service.CollaborationService"%>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants"%>


<%@ page errorPage = "../myhome/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %> 

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"/>
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>

 
<%

    String ptName = "";
    String wsName = "";
    boolean isAsc = true;

    String columnName = request.getParameter("columnName");
    String sortOrder = request.getParameter("sortOrder");
    
    String image = "down";
    String title = "ascending";

    if (sortOrder == null && columnName == null){
         sortOrder = "asc";
         columnName = "FirstName";
         isAsc = true;
    }

    if (sortOrder.equals("asc")){
        sortOrder = "desc";
        image = "up";
        title = "Descending Order";
        isAsc = false;

    } else if (sortOrder.equals("desc")){
        sortOrder = "asc";
        image = "down";
        title = "Ascending Order";
        isAsc = true;
    }
    
    Map collaboratorMap = (Map) session.getAttribute("collaboratorMap");
    long taskId = (Long)session.getAttribute("bizsite_taskId");

    if( collaboratorMap == null) {
        collaboratorMap = (Map) CollaborationService.getCollaboratorList(bizManage.getBizLogicSession(), taskId);
        session.setAttribute("collaboratorMap", collaboratorMap);
    }
    
    List emailUserList = new ArrayList(); 
    emailUserList = (List) collaboratorMap.get(PortalConstants.EMAIL);
   
    SBMAdminUser comparator = new SBMAdminUser();
    
    comparator.setCompareAttr(columnName);
    comparator.setCompareOrder(isAsc);
    Collections.sort(emailUserList, comparator);
%>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<html>
<head>
<title><sbm:message key="emailSelectUser" /> -  CX Process Business Manager</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script>
    function sendEmail(){
       var chkSelectionFlag = false;
       var emailString = "";
       var emailChk = document.getElementsByName("userEmail");
       for(var i = 0; i<emailChk.length; i++) {
            if (emailChk[i].checked) {
                emailString += emailChk[i].value +";";
            }
        }   
        window.open("../myhome/mail.jsp?emailString=" + emailString);
    }
</script>

</head>
<body onLoad="setCheckBoxStyleForIE();">
<div align="center"> 
<form name="emailForm" id="emailForm">
<%@ include file="../common/include_form_body.jsp" %>
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr> 
      <td> 
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="emailSelectUser" /> </td>
          </tr>
          <tr> 
            <td class="PopTblEmboss" align="center"> 
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr> 
                  <td> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td class="SegTblInBg"> 
                          <table border=0 cellpadding=6 cellspacing=1 width=100% Class="SegDataTbl">
                            <tr>
                              <td width="80%" class=SegValLft><b><sbm:message key="note" />: </b><sbm:message key="emailNote" />.</td>
                            </tr>
                            <tbody>
                              <tr>
                                <td class=SegValCntr><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                                  <tr>
                                    <td class="TblBgDark"><table border="0" cellspacing="0" cellpadding="2" width="100%" name="list" id="list" class="ListTableCellPad">
                                        <tr>
                                          <td class="ChkBoxSlctAll"><input type="checkbox" class="InptChkBox" name="chkbxall" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="dynamicOnSelectAll(document.forms[0],this,'userEmail','list',1,-1);">                                          </td>
                                          <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
                                          <td class="ColHdrSort"><table border="0" cellspacing="0" cellpadding="0" align="center">
                                              <tr>
                                                <%
                                                    if("FirstName".equals(columnName)) {
                                                %>
                                                <td><a href="pop_task_det_email.jsp?columnName=<%= columnName %>&sortOrder=<%= sortOrder %>"  title="<%= title %>" class="ColHdrLnk"><sbm:message key="FirstName" /> </a>
                                                 
                                                <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a></td>
                                                <%
                                                    } else {
                                                %>
                                                <td><a href="pop_task_det_email.jsp?columnName=FirstName&sortOrder=<%= sortOrder %>"  title="<%= title %>" class="ColHdrLnk"><sbm:message key="FirstName" /></a></td>
                                                 <%
                                                    }
                                                 %>
                                              </tr>
                                          </table></td>
                                          <%
                                                if("LastName".equals(columnName)) {
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_email.jsp?columnName=<%=columnName%>&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="LastName" /></a>
                                          <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a></td>
                                          <%
                                                } else {
                                          
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_email.jsp?columnName=LastName&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="LastName" /></a></td>
                                          <%
                                                }
                                          %>
                                          
                                          <%
                                                if("Email".equals(columnName)) {
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_email.jsp?columnName=<%=columnName%>&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="Email" />
                                              Id </a>
                                          <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a>
                                          <%
                                                } else {
                                          %>
                                            <td class="ColHdr"><a href="pop_task_det_email.jsp?columnName=Email&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="Email" />
                                                Id </a></td>
                                          <%
                                                }
                                          %>

                                          </tr>
                                        <%
                                                for(int i=0; i<emailUserList.size(); i++) {
                                                    SBMAdminUser user = (SBMAdminUser)emailUserList.get(i);    
                                        %>
                                        <tr class="RedTxt">
                                          <td class="<%=(i%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>"><input type="checkbox" class="InptChkBox" id="userEmail" name="userEmail" value="<%= user.getEmail() %>" onclick="dynamicToggleRowSelection(this,document.forms[0].chkbxall,'list','userEmail',document.forms[0],<%=i+1%>,<%=i%>,-1);">                                          </td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= i+1 %></td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= user.getFirstName() %></td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= user.getLastName() %></td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= user.getEmail() %></td>
                                          </tr>
                                        <%
                                                }
                                        %>
                                    </table></td>
                                  </tr>
                                </table></td>
                              </tr>
                              <tr> 
                                <td class=SegValCntr> <table border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                      <td class="BtnSpace"> <input type="submit" name="sendMail" id="sendMail" value="<sbm:message key="sendEmailButton" />"  class="PopButton" onMouseOver="this.className='PopButtonHover';" onMouseOut="this.className='PopButton';" onClick="sendEmail();">                                      </td>
                                      <td class="BtnSpace"> <input type="submit" name="close" id="close" value="<sbm:message key="closeButton" />"  class="PopButton" onMouseOver="this.className='PopButtonHover';" onMouseOut="this.className='PopButton';" onClick=window.close() style="width:130px">                                      </td>
                                    </tr>
                                  </table>                                </td></tr>
                            </tbody>
                          </table>
                        </td></tr>
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
  </form>
</div>
</body>
</html>
