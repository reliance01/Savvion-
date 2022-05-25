<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>

<jsp:useBean id="calendarBizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
    String[] zoneIDs = TimeZone.getAvailableIDs();
    TreeMap timezoneIDs = new TreeMap();
    for(int ix = 0; ix < zoneIDs.length; ix++) {
        timezoneIDs.put(zoneIDs[ix],zoneIDs[ix]);
    }
%>
        <option value=""><sbm:message key="SelectOne" /></option>
<%
    for(Iterator iter=timezoneIDs.keySet().iterator(); iter.hasNext();) {
        String zoneID = (String)iter.next();
%>
        <option value="<%=zoneID%>">
        	<%= TimeZone.getTimeZone(zoneID).getDisplayName(calendarBizManage.getLocale()) %>
        </option>
<%
    }
%>