<%@ page import="java.sql.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>

<HTML>
    <HEAD>
        <TITLE>Auditor Status</TITLE>
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
        <div class="header">Auditor Status</div>
        <% 
          String auditorUserName = request.getParameter("auditor_username");
          Connection connection = null;
          CallableStatement callableStatement = null;
          ResultSet resultset = null;
          try{
            String status = "";
            String query = "{call AUDITOR_STATUS(?,?)}";
            connection =  DBUtility.getDBConnection();
            callableStatement = connection.prepareCall(query) ;
            callableStatement.setString(1,auditorUserName);
            callableStatement.registerOutParameter(2,OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            resultset = (ResultSet)callableStatement.getObject(2) ; 
        %>

        <TABLE id="scheduledAuditInfo" width="100%">
            <TR>
                <TH>REGION</TH>
                <TH>BRANCH</TH>
                <TH>LOCATION INCHARGE</TH>
                <TH>START DATE</TH>
                <TH>END DATE</TH>
                <TH>STATUS</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getString("REGION") %></td>
                <TD> <%= resultset.getString("BRANCH") %></TD>
                <TD> <%= resultset.getString("LOCATION_INCHARGE") %></TD>
                <TD> <%= resultset.getString("STARTDATE") %></TD>
                <TD> <%= resultset.getString("ENDDATE") %></TD>
                <%
                  if(resultset.getString("STATUS").equals("PI_ACTIVATED")){
                    status = "Activate";
                  }else if(resultset.getString("STATUS").equals("PI_COMPLETED")){
                    status = "Complete";
                  }
                %>
                <TD> <%= status %></TD>
            </TR>
            <% } 
            }catch(Exception ex){
              out.println("--------------------------------------------"+ex);
              ex.printStackTrace();
            }finally{
               releaseResources(connection,callableStatement,resultset);
            }
           %>
           <%!
      public void releaseResources(Connection conn, CallableStatement cstmt,
			ResultSet rset) {
		try {
			if (rset != null) {
				rset.close();
			}
			if (cstmt != null) {
				cstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	  }
           %>
        </TABLE>
    </BODY>
</HTML>