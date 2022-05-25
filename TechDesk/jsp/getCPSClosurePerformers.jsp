<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.Connection"%>
<jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"></jsp:useBean>
<%!
	String databaseToUse = "jdbc/CustomDB";

	//Getting Database Connection
	Connection getDatabaseConnecton() {
		try {
			return ((DataSource) new InitialContext().lookup(databaseToUse)).getConnection();
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException(e);
		}
	}%>
<%
	Connection databaseConnection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	StringBuilder performers = new StringBuilder("");
	try {
		String sqlQuery = "SELECT * FROM TECHDESK_CPS_TEAMDETAILS WHERE CATEGORY = '"+request.getParameter("category")+"' AND SUBCATEGORY = '"+request.getParameter("subCategory")+"'";
		databaseConnection = getDatabaseConnecton();
		statement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				performers.append(resultSet.getString("L1")+","+resultSet.getString("L2")+","+resultSet.getString("L3"));
				break;
			}
	} catch (Exception e) {
		performers.append("error:"+e.getMessage());
		e.getMessage();
	} finally {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (databaseConnection != null) {
				databaseConnection.close();
			}
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException(e);
		}
	}
	out.print(performers.toString());
%>
