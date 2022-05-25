<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
bizManage.setRequest(request);
bizManage.setResponse(response);
%>
<%@ include file="include_copyright_static.jsp" %>
