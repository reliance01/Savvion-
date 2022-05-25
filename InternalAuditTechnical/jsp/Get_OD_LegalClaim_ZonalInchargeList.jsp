<%@ page import="java.sql.*" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  String auditVal = request.getParameter("auditTypeName");
  String regionName = request.getParameter("regionName");

  String zonalInchrgName = "";
  Connection connection = null;
  CallableStatement callableStatement = null;
  ResultSet rset = null;
  if(auditVal != null && auditVal.trim().length() > 0 && regionName != null && regionName.trim().length() > 0)
  {
      try
      {
          String query = "{call AUDIT_OD_LEGAL_ZONALINCHARGE(?,?,?)}";
          connection =  DBUtility.getDBConnection();
          callableStatement = connection.prepareCall(query) ;
          callableStatement.setString(1,auditVal.trim());
          callableStatement.setString(2,regionName.trim());
          callableStatement.registerOutParameter(3,java.sql.Types.VARCHAR);
          callableStatement.executeUpdate();
          zonalInchrgName = callableStatement.getString(3);
      }
      catch(Exception e)
      {
          throw new RuntimeException("Error occered while getting Zonal Incharge List...");
      }
      finally
      {
          DBUtility.releaseResources(connection,callableStatement,rset);
      }
      out.println(zonalInchrgName);   
  }   
%>