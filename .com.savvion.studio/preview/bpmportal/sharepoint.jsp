<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="login_error.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.Base64Decoder" %>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.Base64FormatException" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${empty param.timeZone}">
		<script type="text/javascript">
		function setTime()
		{
			var today = new Date();
			document.SharepointRedirectForm.timeZone.value = today.getTimezoneOffset();
		}
		</script>

		<body onload="setTime();document.SharepointRedirectForm.submit()">
			<form name="SharepointRedirectForm" method="Post" action="sharepoint.jsp">
				<input type="hidden" name="PageCode" value="<%= request.getParameter("PageCode") %>" />
				<input type="hidden" name="BizPassSSOToken" value="<%= request.getParameter("BizPassSSOToken") %>" />
				<input type="hidden" name="timeZone" value="" />
			</form>
		</body>
	</c:when>
	<c:otherwise>
		<%
			Map<String, String> pageCodes = new HashMap<String, String>();
			pageCodes.put("SharePointHome", "/bpmportal/myhome/moss07.jsp");
			pageCodes.put("MyTasks", "/bpmportal/myhome/bizsite.task");
			pageCodes.put("MyInstances", "/bpmportal/myhome/bizsite.status.list");
			pageCodes.put("MyAlerts", "/bpmportal/myhome/bizsite.alerts.list");
			pageCodes.put("MyApplications", "/bpmportal/myhome/bizsite.app.list");
			pageCodes.put("MyDashboard", "/bpmportal/myhome/viewdashboard.portal");
	
			String ssoToken = request.getParameter("BizPassSSOToken");
	
			Base64Decoder base64decoder = new Base64Decoder(ssoToken);
			String userId = base64decoder.processString();
	
			String pageCode = request.getParameter("PageCode");
			if(pageCode == null || pageCode.trim().length() == 0 || !pageCodes.containsKey(pageCode))
				pageCode = "SharePointHome";
		
			String pageURL = pageCodes.get(pageCode);
			String timeZoneValue = request.getParameter("timeZone");
		%>
		<jsp:forward page="/bpmportal/myhome/redirect" >
			<jsp:param name="BizPassUserID" value="<%= userId %>"/>
			<jsp:param name="BizPassUserPassword" value="<%= userId %>"/>
			<jsp:param name="isPasswordEncrypted" value="false"/>
			<jsp:param name="NoMenus" value="true"/>
			<jsp:param name="timeZone" value="<%= timeZoneValue %>"/>
			<jsp:param name="domainTargetURL" value="<%= request.getContextPath() + pageURL %>" />
		</jsp:forward>
	</c:otherwise>
</c:choose>