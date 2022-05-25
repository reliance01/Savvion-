<%@ page import="java.sql.*" %>
<%@ page import="java.net.*" %>
<%@ page import="oracle.jdbc.OracleTypes" %>
<%@ page import="com.rel.db.*" %>
<%@ page import="java.util.ArrayList" %>

<%!

  public Connection getSqlDBConnection()
  {
     Connection connection = null;
     String dbip = null;
     String dbuser = null;
     String dbpass = null;
     try
     {
	   String ip = InetAddress.getLocalHost().getHostAddress();
           if (ip.contains("10.65.15.")) 
           {
               dbip = "10.65.15.208";
               dbuser = "savvion";
               dbpass = "pass@123";
           }
           else
           {
               dbip = "RGIRMSCDDB";
               dbuser = "savvionapp";
               dbpass = "sav$123";
           }
          connection = DriverManager.getConnection("jdbc:sqlserver://" + dbip
                         + ":7359;databaseName=SavvionDB", dbuser, dbpass);
        } 
        catch (Exception ex) 
        {
			System.out.println("Error Getting getSqlDBConnection : "
					+ ex.getMessage()); 
        }
        return connection;
  }


%>



<%
  String value = request.getParameter("AuditType");
  String regionName = request.getParameter("regionName");
  String branchName1 = request.getParameter("branchName");
  
  java.util.HashMap<String,String> objRetailHUB = new java.util.HashMap<String,String>();
  objRetailHUB.put("Delhi","70011970-Abhishek Bansal");
  objRetailHUB.put("Madhya Pradesh","70012125-Shirish Sitoke");
    
  java.util.HashMap<String,String> objCommHUB = new java.util.HashMap<String,String>();
  objCommHUB.put("Delhi","70167305-Manish Dara");
  objCommHUB.put("Madhya Pradesh","70171765-Tauheed Akhtar");
  
  String branchAudit_RgnlInchges = "Select,70008439-Anupam Kumar,70008468-Sanjeev Chatrath,70172879-Amit Jain,70030233-Gaurav Srivastava,70026943-Rahul Velankar,70258556-Puneet Sukla,70005091-Hema Chandra Reddy,70250071-Geosh Roshan,70017695-Amit Kumar,70008805-Chidambaram L";
  if(value.trim().equalsIgnoreCase("Branch Office"))
  {
      //out.println(branchAudit_RgnlInchges);     
      ArrayList<String> regionInchargeList1 = new ArrayList<String>();
      Connection connection1 = null;
      CallableStatement callableStatement1 = null;
      ResultSet rset1 = null;
      try
      {
	  String query = "{call usp_GetRHDetails(?)}";
	  connection1 =  getSqlDBConnection();
	  callableStatement1 = connection1.prepareCall(query) ;
	  callableStatement1.setString(1,branchName1.trim());
	  rset1 = callableStatement1.executeQuery();
	  while(rset1.next())
	  {
	      regionInchargeList1.add(rset1.getString(1));
	  }
       }
       catch(Exception e)
       {
            throw new RuntimeException("Error occered while getting Region Incharge List..."+e.getMessage());
       }
       finally
       {
           DBUtility.releaseResources(connection1,callableStatement1,rset1);
       }
       out.println(regionInchargeList1);     
  }
  else if(value.trim().equalsIgnoreCase("OD Claims") || value.trim().equalsIgnoreCase("Legal Claims"))
  {
      ArrayList<String> regionInchargeList = new ArrayList<String>();
      Connection connection = null;
      CallableStatement callableStatement = null;
      ResultSet rset = null;
      try
      {
	  String query = "{call AUDIT_OD_LEGAL_REGIONINCHARGE(?,?,?)}";
	  connection =  DBUtility.getDBConnection();
	  callableStatement = connection.prepareCall(query) ;
	  callableStatement.setString(1,value.trim());
	  callableStatement.setString(2,regionName.trim());
	  callableStatement.registerOutParameter(3,OracleTypes.CURSOR);
	  callableStatement.executeUpdate();
	  rset = (ResultSet)callableStatement.getObject(3);
	  while(rset.next())
	  {
	      regionInchargeList.add(rset.getString(1));
	  }
      }
      catch(Exception e)
      {
	  throw new RuntimeException("Error occered while getting Region Incharge List...");
      }
      finally
      {
	  DBUtility.releaseResources(connection,callableStatement,rset);
      }
      out.println(regionInchargeList);
  }
  else if(value.trim().equalsIgnoreCase("Retail HUB Operation") && regionName != null && regionName.trim().length() > 0)
  {
      out.println(objRetailHUB.get(regionName.trim()));
  }
  else if(value.trim().equalsIgnoreCase("Commercial HUB Operation") && regionName != null && regionName.trim().length() > 0)
  {
      out.println(objCommHUB.get(regionName.trim()));
  }
  else
  {
      //out.println("-");
  }
%>