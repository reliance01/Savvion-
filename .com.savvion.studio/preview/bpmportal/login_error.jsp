<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isErrorPage="true" %>

<%@ page import="com.savvion.sbm.bpmportal.util.*" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
    //Most of the times we land here because of the invalid credentials. so its not good to log it as error msg.
    //but some times we may land here because of some other reason, in such cases we need to log the exception.
    //so logging as warning... but did not find isWarningEnabled..so using debug enabled.
    if(PortalUtil.self().getLogger().isDebugEnabled())
        PortalUtil.self().getLogger().warnKey("BizManage_ERROR_6124", "login_error.jsp", exception, request.getParameter(PortalConstants.BIZPASS_USER_ID));

    request.setAttribute(PortalConstants.REQUEST_FROM, PortalConstants.LOGIN_ERROR);
     
    if(request.getParameter("BizPassErrorMsg") != null && request.getParameter("BizPassErrorMsg").contains("PW16096")){
    	request.getRequestDispatcher("login_expired.jsp").forward(request,response);
    }else{
    	request.getRequestDispatcher("login.jsp").forward(request,response);
    }
    
%>
