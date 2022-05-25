<%@ page import="java.sql.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  
    String auditType = request.getParameter("auditType");
    ArrayList<String> regionList = new ArrayList<String>();
    Connection connection = null;
    CallableStatement callableStatement = null;
    ResultSet rset = null;
    if(auditType != null && auditType.trim().length() > 0)
    {
        try
        {
            String query = "{call AUDIT_GET_OD_LEGAL_REGION(?,?)}";
            connection =  DBUtility.getDBConnection();
            callableStatement = connection.prepareCall(query) ;
            callableStatement.setString(1,auditType.trim());
            callableStatement.registerOutParameter(2,OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rset = (ResultSet)callableStatement.getObject(2);
            while(rset.next())
            {
                regionList.add(rset.getString(1));
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error occered while getting Region List...");
        }
        finally
        {
            DBUtility.releaseResources(connection,callableStatement,rset);
        }
        out.println(regionList);
    }
  
%>