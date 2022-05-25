<%@ page import="com.rgicl.marcom.BasicUtility" %>
<%@ page import="com.tdiinc.userManager.UserManager" %>
<%@ page import="java.util.ArrayList" %>

<%
    try
    {
        String grpName = request.getParameter("grpName");
        ArrayList<String> memberList = new ArrayList<String>();
        if(grpName != null && grpName.trim().length() > 0)
        {
	    String[] memberArr = UserManager.getGroup(grpName.trim()).getMemberNames();
	    for(int i = 0; i < memberArr.length; i++)
    	    {
	        String name = BasicUtility.getNameOfUser(memberArr[i]);
	        if(name != null && name.trim().length() > 0)
	        {
		    memberList.add(name);
	        }
	    }
        }
        out.print(memberList);
    }
    catch(Exception e)
    {
        throw new RuntimeException(e);
    }
  
%>