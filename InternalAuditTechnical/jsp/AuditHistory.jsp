<%@ page import="java.sql.*" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="com.tdiinc.userManager.JDBCRealm"%>

<HTML>
    <HEAD>
        <TITLE>Audit History</TITLE>
        <link rel="stylesheet" type="text/css" href="style/commonForAudit.css" />
        <style>
	 table{
	  border:2px solid white;
	  border-collapse:collapse;
	  }
	  table td
	  {
	    border:1px solid white;
	    border-collapse:collapse;
	    text-align : center;
	    background-color:#D2E0F1;
	  }
	  table th
	    {
	      border:1px solid white;
	      border-collapse:collapse;
	      
	  }
	
</style>
    </HEAD>

    <BODY>
        <div class="header">Audit History</div>
        <%!
          SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        %>
        <% 
          Connection connection = null;
          PreparedStatement statement = null;
          ResultSet resultset = null;
          try{
            String query = "select * from INTERNAL_AUDIT_HISTORY WHERE PROCESS_INSTANCE_ID = ? ORDER BY SUBMIT_TIME";
            connection =  DBUtility.getDBConnection();
            statement = connection.prepareStatement(query) ;
            statement.setLong(1,Long.parseLong(request.getParameter("piid")));
            resultset = statement.executeQuery() ; 
        %>

        <TABLE id="scheduledAuditInfo" width="100%">
            <TR>
                <TH>AUDIT ID</TH>
                <TH>PERFORMER</TH>
                <TH>TASK NAME</TH>
                <TH>TIME</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getLong(1) %></td>
                <TD> <%= new JDBCRealm().getUser(resultset.getString(2)).getAttribute("FirstName") %></TD>
                <TD> <%= resultset.getString(3) %></TD>
                <TD> <%= formatter.format(resultset.getTimestamp(4)) %></TD>
            </TR>
            <% } 
            }catch(Exception ex){
              out.println("--------------------------------------------"+ex);
              ex.printStackTrace();
            }finally{
               DBUtility.releaseResources(connection,statement,resultset);
            }
           %>
        </TABLE>
    </BODY>
</HTML>