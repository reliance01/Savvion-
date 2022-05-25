<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbmadmin.common.SBMAdminUser" %>
<%@ page import="com.savvion.sbmadmin.common.SBMAdminConstant" %>
<%@ page import="com.savvion.sbm.bpmportal.service.CollaborationService"%>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.PropsUtil" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants"%>

<%@ page errorPage = "../myhome/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %> 

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"/>
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>
 

<%
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

    List imUserList =  new ArrayList();
    imUserList = (List) collaboratorMap.get(PortalConstants.IM);

    SBMAdminUser comparator = new SBMAdminUser();
    comparator.setCompareAttr(columnName);
    comparator.setCompareOrder(isAsc);
    Collections.sort(imUserList, comparator);
%>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<html>
<head>
<title><sbm:message key="chatSelectUser" /> -  <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>

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
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="chatSelectUser" /> </td>
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
                              <td width="80%" class=SegValLft><b><sbm:message key="Note" />: </b><sbm:message key="chatNote" />. </td>
                            </tr>
                            <tbody>
                              <tr>
                                <td class=SegValCntr><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                                  <tr>
                                    <td class="TblBgDark"><table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad">
                                        <tr>
                                          <td class="ColHdrSrNo"><sbm:message key="NoStr" />.</td>
                                          <td class="ColHdrSort"><table border="0" cellspacing="0" cellpadding="0" align="center">
                                              <tr>
                                                <%
                                                    if("FirstName".equals(columnName)) {
                                                %>
                                                <td><a href="pop_task_det_chat.jsp?columnName=<%= columnName %>&sortOrder=<%= sortOrder %>"  title="<%= title %>" class="ColHdrLnk"><sbm:message key="FirstName" /> </a>
                                                 
                                                <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a></td>
                                                <%
                                                    } else {
                                                %>
                                                <td><a href="pop_task_det_chat.jsp?columnName=FirstName&sortOrder=<%= sortOrder %>"  title="<%= title %>" class="ColHdrLnk"><sbm:message key="FirstName" /> </a></td>
                                                 <%
                                                    }
                                                 %>
                                              </tr>
                                          </table></td>
                                          <%
                                                if("LastName".equals(columnName)) {
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_chat.jsp?columnName=<%=columnName%>&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="LastName" /> </a>
                                          <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a></td>
                                          <%
                                                } else {
                                          
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_chat.jsp?columnName=LastName&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="LastName" /></a></td>
                                          <%
                                                }
                                          %>
                                          
                                          <%
                                                if("ChatId".equals(columnName)) {
                                          %>
                                          <td class="ColHdr"><a href="pop_task_det_chat.jsp?columnName=<%=columnName%>&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="chatId" />
                                              </a>
                                          <a href="#" title="Descending Order"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a>
                                          <%
                                                } else {
                                          %>
                                            <td class="ColHdr"><a href="pop_task_det_chat.jsp?columnName=ChatId&sortOrder=<%= sortOrder %>" title="<%= title %>" class="ColHdrLnk"><sbm:message key="chatId" />
                                                </a></td>
                                          <%
                                                }
                                          %>
 
                                          </tr>
                                        <%
                                                for(int i=0; i<imUserList.size(); i++) {
                                                    SBMAdminUser user = (SBMAdminUser)imUserList.get(i);    
                                        %>
                                        <tr class="RedTxt">
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= i+1 %></td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= user.getFirstName() %></td>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><%= user.getLastName() %></td>
                                          <% 
                                            if (!("".equals(user.getChatId()))) { 
                                          %>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>">
                                            <% 
                                                String messengerType = null;
                                                if(user.getMessengerType() == null) {
                                                    messengerType = PropsUtil.getDefaultMessengerType();
                                                } else {
                                                    messengerType = user.getMessengerType();
                                                }
                                            
                            if("yahoo".equalsIgnoreCase(messengerType)) {                                           
                                            %>
                                            <a href="ymsgr:sendIM?<%= user.getChatId() %>" title="Instant Message"><%= user.getChatId() %></a>
                                            <%
                                                } else if ("msn".equalsIgnoreCase(messengerType)) {
                                            %>
                                            <a href="msnim:chat?contact=<%=user.getChatId()%> " title="Instant Message"><%= user.getChatId() %></a>
                                            <%
                                                } else if ("aim".equalsIgnoreCase(messengerType)) {
                                            %>
                                            <a href="aim:goim?screenname=<%= user.getChatId() %>" title="Instant Message"><%= user.getChatId() %></a>
                                            <%
                                                } 
                                            %>
                                          </td>
                                          <% } else {%>
                                          <td class="<%=(i%2 == 1) ? "ValCntrAlt" : "ValCntr"%>">&nbsp;&nbsp</td>
                                          <% } %>
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
