<%@ page import="java.sql.*"%>
<%@ page import="com.rel.db.*"%>
<%@ page import="oracle.jdbc.OracleTypes"%>


<%
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rset = null;
  
  try
  {
     
      String piID = request.getParameter("piid");
      String docName = request.getParameter("docName");
    
    
      if(piID != null && piID.trim().length() > 0 && docName != null && docName.trim().length() > 0)
      {
          String query = "SELECT DOC_NAME FROM DOCUMENT WHERE PATH LIKE \'/sbm/9020/"+piID+"/"+docName+"%\'";
          String docNames = "";
     
          conn = DBUtility.getDBConnection();
          pstmt = conn.prepareStatement(query);
          rset = pstmt.executeQuery();
          while(rset.next())
          {
              docNames += rset.getString(1)+",";
          }
          if(docNames != null && docNames.trim().length() > 0)
          {
              docNames = docNames.substring(0,docNames.length()-1);
          }
          else
          {   
              docNames = "Not Found";
          }
          out.println(docNames);
      }	   
    
  }
  catch(Exception e)
  {
       System.out.println("Error getting doc Name : "+e.getMessage());
       e.printStackTrace();
  }
  finally
  {
       DBUtility.releaseResources(conn,pstmt,rset);
  }
   
%>