<%@ page import="java.net.InetAddress"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.CallableStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.sql.DataSource"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="oracle.jdbc.OracleTypes"%>

<%
 
   String systemName = request.getParameter("system");
   Connection connection = null;
   ArrayList<String> systemsList = null;
   if(systemName != null && systemName.trim().length()>0)
   {
      CallableStatement cstmt = null;
      ResultSet rset = null;
      String qry = "{call GET_TECHDESK_L2SYSTEMS(?,?)}";
      	try {
      	    connection = getDBConnection();
	    cstmt = connection.prepareCall(qry);
 	    cstmt.setString(1, systemName.trim());
	    cstmt.registerOutParameter(2, OracleTypes.CURSOR);
	    cstmt.executeUpdate();
	    rset = (ResultSet) cstmt.getObject(2);
	    if(rset != null)
	    {
	        systemsList = new ArrayList<String>();
                while(rset.next())
   		{
                     systemsList.add(rset.getString(1));  
		}
	    }   
	    out.println(systemsList);
         } catch (Exception ex) {
		System.out.println("Error :"
				+ ex.getMessage());
      } finally {
          releaseResouce(rset,cstmt,connection);
     }
   }
%>
 
<%!
   //String jndiName = "jdbc/SBMCommonDB";
   String jndiName = "jdbc/CustomDB";
   public Connection getDBConnection() {
      Connection connection = null;
	try {
		connection = ((DataSource) new InitialContext().lookup(jndiName))
				.getConnection();
	} catch (Exception ex) {
		System.out.println("Error Getting DBConnection : "
				+ ex.getMessage());
	}
       return connection;
    }
    
 
  public void releaseResouce(ResultSet rset, PreparedStatement pstmt,Connection conn) {
  	try {
  	if (rset != null) {
  		rset.close();
  	}
  	if (pstmt != null) {
  		pstmt.close();
  	}
  	if (conn != null) {
	        conn.close();
  	}
        } catch (Exception ex) {
  		System.out.println("Error while ReleaseResouce : "
  				+ ex.getMessage());
      }
  }

%>

