<%@ page import="java.sql.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  String auditVal = request.getParameter("AuditType");
  String regionVal = request.getParameter("Region");
  
  java.util.HashMap<String,String> objRetailHUB = new java.util.HashMap<String,String>();
  objRetailHUB.put("Delhi","Delhi");
  objRetailHUB.put("Madhya Pradesh","Indore");
  
  java.util.HashMap<String,String> objCommHUB = new java.util.HashMap<String,String>();
  objCommHUB.put("Delhi","Delhi");
  objCommHUB.put("Madhya Pradesh","Indore");
  
  if(auditVal.trim().equalsIgnoreCase("Retail HUB Operation") && regionVal != null && regionVal.trim().length() > 0)
  {
      out.println(objRetailHUB.get(regionVal.trim()));
  }
  else if(auditVal.trim().equalsIgnoreCase("Commercial HUB Operation") && regionVal != null && regionVal.trim().length() > 0)
  {
      out.println(objCommHUB.get(regionVal.trim()));
  }
  else if((auditVal.trim().equalsIgnoreCase("OD Claims") || auditVal.trim().equalsIgnoreCase("Legal Claims")) && regionVal != null && regionVal.trim().length() > 0)
  {
      ArrayList<String> locationList = new ArrayList<String>();
      Connection connection = null;
      CallableStatement callableStatement = null;
      ResultSet rset = null;
      try
      {
	  String query = "{call AUDIT_GET_OD_LEGAL_LOCATION(?,?,?)}";
	  connection =  DBUtility.getDBConnection();
	  callableStatement = connection.prepareCall(query) ;
	  callableStatement.setString(1,auditVal.trim());
	  callableStatement.setString(2,regionVal.trim());
	  callableStatement.registerOutParameter(3,OracleTypes.CURSOR);
	  callableStatement.executeUpdate();
	  rset = (ResultSet)callableStatement.getObject(3);
	  while(rset.next())
	  {
	      locationList.add(rset.getString(1));
	  }
      }
      catch(Exception e)
      {
	  throw new RuntimeException("Error occered while getting Location List...");
      }
      finally
      {
	  DBUtility.releaseResources(connection,callableStatement,rset);
      }
      out.println(locationList);
  }
  else
  {
      //out.println("Select");
  }
%>