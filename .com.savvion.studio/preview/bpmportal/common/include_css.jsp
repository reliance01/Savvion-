<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
bizManage.setRequest(request);
bizManage.setResponse(response);

%>
<%@ include file="include_css_static.jspf" %>