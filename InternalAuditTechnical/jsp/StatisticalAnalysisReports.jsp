<%@ page import="java.sql.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.rel.db.*" %>
<%@ page import="com.rel.techaudit.utilities.TechnicalAuditUtility"%>
<HTML>
    <HEAD>
        <TITLE>Statistical Analysis & Reports</TITLE>
        <link rel="stylesheet" type="text/css" href="style/commonForAudit.css" />
        <script type="text/javascript" src="script/commonJS.js"></script>
        <style>
	 table{
	  border:2px solid white;
	  border-collapse:collapse;
	  }
	  table td
	  {
	    border:1px solid white;
	    border-collapse:collapse;
	    text-align : center;
	    background-color:#D2E0F1;
	  }
	  table th
	    {
	      border:1px solid white;
	      border-collapse:collapse;
	  }
	
</style>

    </HEAD>

    <BODY>
        <div class="header">Statistical Analysis & Reports</div>
        <%!
           public String getRiskRate(String value){
             if(value.equals("Select")){
                return "1";
             }else{
               return value;
             }
           }
           
           public String ellipseStr(String str){
             if(str.length() > 15) str = str.substring(0,15)+"...";
             return str;
           }
           
           public String riskRateColorMapp(String val){
	       String value = "";
	            if(val.equals("1") || val.equals("2")){
	               value = "green-white";
	            }
	            if(val.equals("3")){
	               value = "yellow-black";
	            }
	            if(val.equals("4") || val.equals("5")){
	               value = "red-white";
	            }
	         return value;
           }
           
           TechnicalAuditUtility techUtil = new TechnicalAuditUtility();
           String oldParameterName = "";
           Double totalContibution = 0.0;
           Double totalAuditScore = 0.0;
           Integer totalWeightage = 0;
           String query = null;
           Long piid;
           String reportName;
        %>
        <% 
          Connection connection = null;
          PreparedStatement statement = null;
          ResultSet resultset = null;
          try{
                           
           query = "SELECT tab1.PARAMETER_VALUE PARAMETER,tab1.SUBPARAMETER_VALUE SUBPARAMETER,tab1.RISK_RATE RISKRATE "+
                   ",tab2.WEIGHTAGE WEIGHTAGE,(SELECT count(a.PARAMETER_VALUE) FROM INTERNAL_TECH_AUDIT a INNER JOIN TBL_AUDIT_PARAMETER b "+
                   "ON a.PARAMETER_VALUE = b.PARAMETER WHERE a.PIID = ? AND tab1.PARAMETER_VALUE = b.PARAMETER) CT "+
                   "FROM INTERNAL_TECH_AUDIT tab1 INNER JOIN TBL_AUDIT_PARAMETER tab2 ON tab1.PARAMETER = tab2.ID "+                  
                   "WHERE tab1.PIID = ? ORDER BY tab1.PARAMETER_VALUE,tab1.SUBPARAMETER_VALUE";
 
              
	    piid = Long.parseLong(request.getParameter("piid"));
            connection =  DBUtility.getDBConnection();
            statement = connection.prepareStatement(query) ;
            statement.setLong(1,piid);
            statement.setLong(2,piid);
            resultset = statement.executeQuery() ; 
            com.rel.internalaudit.ReportCreation report = new com.rel.internalaudit.ReportCreation();
            reportName = report.statisticalReportGenerator(query,piid);
            
        %>

        <TABLE id="scheduledAuditInfo" width="100%">
            <TR>
                <TH>PARAMETER</TH>
                <TH>SUB PARAMETER</TH>
                <TH>(A) RISK RATING (1 TO 5)</TH>
                <TH>(B) WEIGHTAGE ALLOCATED</TH>
                <TH>(C) CONTRIBUTION OF EACH SUB PARAMETER</TH>
                <TH>(D) AUDIT SCORE = (A)*(C)</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <%
                  String parameter = resultset.getString(1);
                  String subParameter = resultset.getString(2);
                  String riskRate = getRiskRate(resultset.getString(3));
                  String riskRateTDColor = riskRateColorMapp(riskRate).split("-")[0];
                  String riskRateTxtColor = riskRateColorMapp(riskRate).split("-")[1];
                  Double contri = resultset.getDouble(4)/resultset.getFloat(5);
                  Double auditScore = (Integer.parseInt(getRiskRate(resultset.getString(3)))*contri)/100;
                  totalContibution += contri;
                  totalAuditScore += auditScore;
                  Integer weightageAlloc = resultset.getInt(4);
                
                if(! parameter.equals(oldParameterName)){
                %>
                <TD title="<%=parameter%>" rowspan="<%=resultset.getInt(5)%>"> <%= ellipseStr(parameter) %></td>
               <% }  %>
                <TD title="<%=subParameter%>"> <%= ellipseStr(subParameter) %></TD>
                <TD style="background-color:<%=riskRateTDColor%>;color:<%=riskRateTxtColor%>;"> <%= riskRate %></TD>                
                <%
                 if(! parameter.equals(oldParameterName)){
                %>
                <TD rowspan="<%=resultset.getInt(5)%>"> <%= weightageAlloc+"%" %></TD>   
                <% 
                  totalWeightage += weightageAlloc;
                } %>                
                <TD> <%= new DecimalFormat("##.00").format(contri)+"%" %></TD>
                <TD> <%= new DecimalFormat("##.##").format(auditScore) %></TD>
            </TR>
                <% oldParameterName = parameter;
                %>
                
            <% } %>
              <TR>
                <TD colspan="3"><b>Total</b></TD>
                <TD><b><%=totalWeightage+"%"%></b></TD>
                <TD><b><%=new DecimalFormat("##.00").format(totalContibution)+"%"%></b></TD>
                <TD><b><%=new DecimalFormat("##.00").format(totalAuditScore)%></b></TD>
              </TR>
            <%   
               totalContibution = 0.0;
               totalAuditScore = 0.0;
               totalWeightage = 0;
            }catch(Exception ex){
              out.println("--------------------------------------------"+ex);
              ex.printStackTrace();
            }finally{
               DBUtility.releaseResources(connection,statement,resultset);
            }
           %>
        </TABLE>
         
        <div style="text-align:center;margin:20px;">
	     <image style="cursor:pointer;" height="50" width="200" src="images/download.gif" onClick="downloadReport()" />           
        </div>
        <script>
	    function downloadReport(){
	       window.location.href = "DownloadExcel.jsp?reportname=<%=reportName%>";
	    }
	    
	    function callBackReport(){}
        </script>
      
    </BODY>
</HTML>
