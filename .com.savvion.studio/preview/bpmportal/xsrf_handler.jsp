<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="login_error.jsp" %>

<%@ page import="com.savvion.sbm.bpmportal.util.*" %>
<%@page import="com.tdiinc.userManager.cache.UMCache"%>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
    String user = bizManage.getName();
    session.invalidate();
    
    // Invalidating user manager caches
    UMCache.removeUser(user, null);
    UMCache.removeFromAllGroupNames(user, null);
    
    request.setAttribute(PortalConstants.REQUEST_FROM, PortalConstants.XSRF);
    
    request.getRequestDispatcher("login.jsp").forward(request,response);
%>
