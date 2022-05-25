<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.sql.DataSource"%>

<%

     String userId = request.getParameter("userID");
     boolean isMRCONL_User = false;
     if(userId != null && userId.trim().length() > 0)    
     {
	     Connection connection = null;
	     PreparedStatement pstmt = null;
	     ResultSet rset = null;
	     try
	     {
	         connection = ((DataSource) new InitialContext().lookup("jdbc/SBMCommonDB")).getConnection();
	         pstmt = connection.prepareStatement("SELECT ID FROM MRCONL_USERS");
	         rset = pstmt.executeQuery();
	         while(rset.next())
	         {   
	             if(userId.equalsIgnoreCase(rset.getString(1).trim()))
	             {
	                 isMRCONL_User = true;
	             }
	         }
	     }
	     catch (Exception ex) 
	     {
	     	System.out.println("Error in CheckMarcomUser.jsp : "+ex.getMessage());
	     }
	     finally
	     {
	         try
	         {
	             if(rset != null)
	             {
	                 rset.close();
	             }
	             if(pstmt != null)
	             {
	                 pstmt.close();
	             }
	             if(connection != null)
	             {
	                 connection.close();
	             }
	         }
	         catch(Exception ex)
	         {
	             System.out.println("Error in CheckMarcomUser.jsp in finally block : "+ex.getMessage());
	         }
             }
     }
     out.println(isMRCONL_User);
        
%>