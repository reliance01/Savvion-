<%@ page import="java.io.*,com.savvion.sbm.bizsolo.util.SBMConf" %>
<%

try {
String reportName = request.getParameter("reportname");
   String filename = SBMConf.SBM_WEB_URL + "/sbm/bpmportal/reports/AuditReport/" + reportName+".xls";
   response.sendRedirect(filename);
  }catch(Exception e){
	e.printStackTrace();
}

%>