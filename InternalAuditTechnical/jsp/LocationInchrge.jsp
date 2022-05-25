<%@ page import="java.sql.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%
  String auditVal = request.getParameter("AuditType");
  String regionName = request.getParameter("regionName");
  String locationVal = request.getParameter("Location");
  
  java.util.HashMap<String,String> objRetailHUB = new java.util.HashMap<String,String>();
  objRetailHUB.put("Delhi","70011970-Abhishek Bansal");
  objRetailHUB.put("Indore","70012125-Shirish Sitoke");
  
  java.util.HashMap<String,String> objCommHUB = new java.util.HashMap<String,String>();
  objCommHUB.put("Delhi","70167305-Manish Dara");
  objCommHUB.put("Indore","70171765-Tauheed Akhtar");
  if(auditVal != null && regionName != null && locationVal != null)
  {
      if(auditVal.trim().equalsIgnoreCase("Retail HUB Operation") && locationVal != null && locationVal.trim().length() > 0)
      {
          out.println(objRetailHUB.get(locationVal.trim()));
      }
      else if(auditVal.trim().equalsIgnoreCase("Commercial HUB Operation") && locationVal != null && locationVal.trim().length() > 0)
      {
          out.println(objCommHUB.get(locationVal.trim()));
      }
      else if((auditVal.trim().equalsIgnoreCase("OD Claims") || auditVal.trim().equalsIgnoreCase("Legal Claims")) && regionName.trim().length() > 0)
      {
          ArrayList<String> locationInchargeList = new ArrayList<String>();
          Connection connection = null;
          CallableStatement callableStatement = null;
          ResultSet rset = null;
          try
          {
	      String query = "{call AUDIT_ODLEGAL_LOCATIONINCHARGE(?,?,?)}";
	      connection =  DBUtility.getDBConnection();
	      callableStatement = connection.prepareCall(query) ;
	      callableStatement.setString(1,auditVal.trim());
	      callableStatement.setString(2,regionName.trim());
	      callableStatement.registerOutParameter(3,OracleTypes.CURSOR);
	      callableStatement.executeUpdate();
	      rset = (ResultSet)callableStatement.getObject(3);
	      while(rset.next())
	      {
	          locationInchargeList.add(rset.getString(1));
	      }
          }
          catch(Exception e)
          {
	      throw new RuntimeException("Error occered while getting Location Incharge List...");
          }
          finally
          {
	      DBUtility.releaseResources(connection,callableStatement,rset);
          }
          out.println(locationInchargeList);   
      }    
      else
      {
           //out.println("Select");
      }
  }
%>