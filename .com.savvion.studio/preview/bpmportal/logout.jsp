<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="login_error.jsp" %>

<%@ page import="com.savvion.sbm.bpmportal.common.*" %>
<%@ page import="com.savvion.sbm.bpmportal.util.*" %>
<%@page import="com.tdiinc.userManager.cache.UMCache"%>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
	String user = bizManage.getName(); 

	if(user != null && !user.equals("")) {
	
		// Invalidating user manager caches
		UMCache.removeUser(user, null);
		UMCache.removeFromAllGroupNames(user, null);

		if(PortalUtil.self().getLogger().isDebugEnabled())
			PortalUtil.self().getLogger().debugKey("BizManage_INFO_6121", "logout.jsp", user);

		//the following if check is required....when the user enters logout.jsp for the first time instead of login.jsp
		request.setAttribute(PortalConstants.REQUEST_FROM, PortalConstants.LOGOUT);
		
        LogoutEvent logoutEvent = new LogoutEvent(request, response, user);
        PortalUserSessionListenerRegistry.self().notifyListeners(logoutEvent);
	}
  	session.invalidate();
    request.getRequestDispatcher("login.jsp").forward(request,response);
%>
