<%@ page import="com.savvion.rcf.*" %>
<%@ page import="java.util.*" %>

<%
        String systemName = request.getParameter("system");
        if(systemName != null && systemName.trim().length() > 0)
        {
            Vector<String> userList = new ReassignTaskAppSupport().getPerformers(systemName);
            if(userList != null && !userList.isEmpty())
            {
                out.println(userList);
            }
            else
            {
                out.println("-");
            }
        }
        else
        {
            out.println("-");
        }
%>
