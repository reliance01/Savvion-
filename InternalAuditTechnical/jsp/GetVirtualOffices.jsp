<%@ page import="java.sql.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  String location = request.getParameter("location");
  if(location != null && location.trim().length() > 0)
  {    
      ArrayList<String> virtualOfficesList = null;
      Connection connection = null;
      CallableStatement callableStatement = null;
      ResultSet rset = null;
      try
      {
	  String query = "{call AUDIT_GET_VIRTULOFFICES(?,?)}";
	  connection =  DBUtility.getDBConnection();
	  callableStatement = connection.prepareCall(query) ;
	  callableStatement.setString(1,location.trim());
	  callableStatement.registerOutParameter(2,OracleTypes.CURSOR);
	  callableStatement.executeUpdate();
	  rset = (ResultSet)callableStatement.getObject(2);
	  virtualOfficesList = new ArrayList<String>();
	  while(rset.next())
	  {
	      virtualOfficesList.add(rset.getString(1));
	  }
      }
      catch(Exception e)
      {
      }
      finally
      {
	  DBUtility.releaseResources(connection,callableStatement,rset);
      }
      out.println(virtualOfficesList);
  }
  else
  {
      //out.println("Select");
  }
%>