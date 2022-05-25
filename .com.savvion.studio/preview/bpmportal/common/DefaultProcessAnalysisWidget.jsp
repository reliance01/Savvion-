<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<c:set var="targetUrlMap" value="${targetUrlMap}"/> 
<c:set var="targetUrl" value="${targetUrl}"/> 
<c:set var="properties" value="${properties}"/>
<c:set var="dashboardWidget" value="${dashboardWidget}"/>
<c:set var="widget" value="${dashboardWidget.widget}"/>

<%

	StringBuilder params = new StringBuilder();   
	StringBuilder targetUrl = new StringBuilder((String)pageContext.getAttribute("targetUrl")); 
 	Map targetMap = (HashMap)pageContext.getAttribute("targetUrlMap");  
	if(targetMap != null && targetMap.size() > 0){
    	params.append("?");  
   		for (Iterator iter = targetMap.entrySet().iterator();iter.hasNext();)
   		{
       		Map.Entry<String,String> entry = (Map.Entry<String,String>)iter.next();
       		params.append(entry.getKey());
       		params.append("=");
       		params.append(URLEncoder.encode(entry.getValue(),PortalConstants.UTF8)); 
       		if(iter.hasNext()){
           		params.append("&"); 
       		}       
    	}   
    	targetUrl.append(params);
 	}
%>

<iframe src = "/sbm/<%=targetUrl.toString()%>" height = '<c:out value = "${requestScope.height}"/>px'  width = '100%' frameborder="0" align = "center"> 
</iframe>
