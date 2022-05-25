<%@ page import="java.sql.*"%>
<%@ page import="com.rel.db.*"%>
<%@ page import="oracle.jdbc.OracleTypes"%>
<%@ page import="java.io.*" %>

<%

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rset = null;
  
  try
  {
    
      String piID = request.getParameter("piid");
      String docName = request.getParameter("docName");
      String fileName = request.getParameter("fileName").trim();
        
      if(piID != null && piID.trim().length() > 0 && fileName != null && fileName.trim().length() > 0 && docName != null && docName.trim().length() > 0)
      {
          String query = "SELECT CONTENT FROM DOCUMENT WHERE PATH LIKE \'/sbm/9020/"+piID+"/"+docName+"%\' AND DOC_NAME = ?";
          Blob content = null;
        
          conn = DBUtility.getDBConnection();
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1,fileName);
          rset = pstmt.executeQuery();
          while(rset.next())
          {
              content = rset.getBlob(1);
          }
          if(content != null)
          {
               response.setContentType("application/vnd.ms-excel");   
  	           response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");   
  	           int cntntLength = (int)content.length();
	           byte[] buf = content.getBytes(1,cntntLength);
               OutputStream os = response.getOutputStream();
               os.write(buf);                      
               os.flush();
               os.close();
          }
     }
     
   }
   catch(Exception e)
   {
         System.out.println("get Document  : "+e.getMessage());
   }
   finally
   {
       DBUtility.releaseResources(conn,pstmt,rset);
   }
   
%>