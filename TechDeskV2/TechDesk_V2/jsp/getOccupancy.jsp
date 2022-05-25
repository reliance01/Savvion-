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
	StringBuilder occupancyOptionsJson = new StringBuilder("[");
	try {
		String sqlQuery = "SELECT OCCUPANCY FROM TECHDESK_OCCUPANCY_MASTER ORDER BY OCCUPANCY ASC";
		databaseConnection = getDatabaseConnecton();
		statement = databaseConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(sqlQuery);
		String alreadySelectedOccupancyOption = bean.getPropString("Occupancy");
		if(alreadySelectedOccupancyOption != null && alreadySelectedOccupancyOption.trim().length() > 0) {
            	// This line (Below this comment) of code is placed here because the same was in PRD but not in UAT. By Sunil Jangir	
				occupancyOptionsJson.append("{\"selected\": true,\"value\":\"NA\",\"label\":\"Select\"},");
			while (resultSet.next()) {
				occupancyOptionsJson.append("{");
				if(alreadySelectedOccupancyOption != null && alreadySelectedOccupancyOption.equals(resultSet.getString(1))){
					// This line (Below this comment) of code is placed here because the same was in PRD but not in UAT. By Sunil Jangir
					occupancyOptionsJson.replace(13,18," false");
					occupancyOptionsJson.append("\"selected\":true,");
				} else {
					occupancyOptionsJson.append("\"selected\":false,");
				}
				occupancyOptionsJson.append("\"value\":\""+resultSet.getString(1)+"\",");
				occupancyOptionsJson.append("\"label\":\""+resultSet.getString(1)+"\"}");
				occupancyOptionsJson.append(",");
			}
		} else {
			occupancyOptionsJson.append("{");
			occupancyOptionsJson.append("\"selected\":true,");
			occupancyOptionsJson.append("\"value\":\"NA\",");
			occupancyOptionsJson.append("\"label\":\"Select\"},");
		}
		occupancyOptionsJson.append("{\"sdeWidgetKey\":\"\"}]");
	} catch (Exception e) {
		occupancyOptionsJson.append("{\"sdeWidgetKey\":\"\"}]");
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
	out.print(occupancyOptionsJson.toString());
%>
