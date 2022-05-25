<%@ page import="java.sql.*"%>
<%@ page import="com.rel.db.*"%>
<%@ page import="oracle.jdbc.OracleTypes"%>


<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
    String menu1 = "0";
    String submenu1 = "3";

    bizManage.setRequest(request);
    bizManage.setResponse(response);
%>

<html>
    <head>
        <sbm:setLocale value="<%= bizManage.getLocale() %>" />
        <sbm:setBundle basename="properties/bpmportal" scope="page" />
        <title>Audit Report</title>
        
        <%@ include file="../../../bpmportal/common/include_css_static.jspf" %>
        <%@ include file="../../../bpmportal/common/include_top.jsp" %>    
        
        <link rel="stylesheet" type="text/css" href="style/commonForAudit.css" />
        <link rel="stylesheet" href="style/jquery.modalbox.css" />
        <link rel="stylesheet" type="text/css" href="style/dataTables.jqueryui.css" />
        <link rel="stylesheet" type="text/css" href="style/jquery-ui.css" />
        
        <link rel="stylesheet" type="text/css" href="../../../bpmportal/javascript/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="../../../bpmportal/javascript/ext/resources/css/xtheme-default.css" />
        <link rel="stylesheet" type="text/css" href="../../../bpmportal/css/theme01/bm-all.css" />
        
        
        <script type="text/javascript" charset="utf8" src="script/jquery-1.11.1.js"></script>
	<script type="text/javascript" charset="utf8" src="script/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="script/jquery.modalbox-1.5.0-min.js"></script>
	<script type="text/javascript" src="script/ext-all.js"></script>
	<script type="text/javascript" charset="utf8" src="script/dataTables.jqueryui.js"></script>
	
	
        <style>
	        
	     .fontStyle
	  	{
	  	  --height:20px;
	  	  color:black;
	  	  font-size : 11px;
	  	  font-weight:bold;
	  	  margin : 5px;
	  	  font:italic;
	        }  
	        
	        td
	        {
	          text-align:center;
	        }
	        
	        .DataTables_sort_wrapper
	        {
	          font-size:11px;
	        }
	        
	        .ui-state-default
	        {
	          text-align:center;
	          background-image:none;
	          background-color:#98b8df;
	        }
	        
	    #annexureDocs
	    {
	        display: none;
	        position: absolute;
	        top: 50%;
	        left: 45%;
	        width: 350px;
	        height:100px;
	        margin-left: -150px;
	        margin-top: -100px;                 
	        padding: 10px;
	        border: 2px solid #98b8df;
	        background-color: #D2E0F1;
	        z-index:1002;
	        overflow:visible;
          }
          
          .ui-widget-header
          {
   			  height:10px;
		  }

		  .dataTables_filter,.dataTables_info,.dataTables_paginate
          {
              margin-top:-6px;
              font-size:11px;
          }
          
          input[type='text']
          {
              font-size:11px;
              font-weight:normal;
          }
	    
	</style>
	
	 
    </head>
    <body>
	
	<div id="northDiv">
	    <%@ include file="../../../bpmportal/common/include_body.jsp" %>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	            <td>
	                <%@ include file="../../../bpmportal/common/include_menu_static.jspf" %>
	            </td>
	        </tr>
	    </table>
	    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="breadcrumbSec">
	        <tr>
	            <td align="left"  valign="middle" width="100%">
	                <table id="breadcrumb"  border="0" cellspacing="0" cellpadding="0" >                   
	                </table>
	            </td>
	        </tr>
	    </table>
</div>

<div id="southDiv">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>
                <%@ include file="../../../bpmportal/common/include_copyright_static.jsp" %>
            </td>
        </tr>
        <tr>
            <td>
                <%@ include file="../../../bpmportal/common/include_bottom.jsp" %>
            </td>
        </tr>
    </table>
</div>

	<div class="header">Audit Report</div>
	<div id='auditDtl'></div>
	<div id='annexureDocs'></div>
	<div id="fade"></div>
        <script>
        //Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        var tableHtml = "";
          $(document).ready(function() {
	      $('#auditInfo tfoot th').each( function () {
	          var title = $('#example thead th').eq( $(this).index() ).text();
	          $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
	      } );
	      var dt = $('#auditInfo').DataTable({ "bLengthChange": false});
	      dt.columns().every( function () {
	          var that = this;
	   
	          $( 'input', this.footer() ).on( 'keyup change', function () {
	              that
	                  .search( this.value )
	                  .draw();
	          } );
	      } );	
	      
        } );
  </script>
  
  <script>
      tableHtml = "<table id='auditInfo' class='display' cellspacing='0' align='center' width='100%'><thead><tr>";
      tableHtml += "<th>Branch</th><th>Auditor</th><th>Auditee</th><th>Schedule On</th><th>Pending With</th><th>Status</th><th>Audit Report</th><th>Auditee Annexure</th><th>Auditor Annexure</th><th>Sample Docs</th>";
      tableHtml += "</tr></thead><tfoot><tr><th>Branch</th><th>Auditor</th><th>Auditee</th><th>Schedule On</th><th>Pending With</th><th>Status</th><th>Audit Report</th><th>Auditee Annexure</th><th>Auditor Annexure</th><th>Sample Docs</th>";
      tableHtml += "</tr></tfoot><tbody>";
      
      var auditeeDocName = "uploadAnnexureAuditee";
      var auditorDocName = "uploadAnnexureAuditor";
      var sampleDocName = "sampleDoc";
  </script>
	
	<%
	   Connection connection = null;
	   CallableStatement callableStatement = null;
	   ResultSet rset = null;
	   String query = "{call AUDIT_TASKDETAIL_INFO(?,?)}";
	   
	   try
	   {
	       String currentUser = bizSite.getCurrentUser();
	       connection = DBUtility.getDBConnection();
	       callableStatement = connection.prepareCall(query);
	       callableStatement.setString(1, currentUser);
	       callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
	       callableStatement.executeUpdate();
	       rset = (ResultSet) callableStatement.getObject(2);
	       while (rset.next()) 
	       {
	       
	      %>
	      
	      <script>
	           
	           tableHtml += "<tr>";
	           tableHtml += "<td><span class='fontStyle' style='cursor:pointer;'><%=rset.getString(2)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><%=rset.getString(3)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><%=rset.getString(4)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><%=rset.getString(6)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><%=rset.getString(7)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><%=rset.getString(5)%></style></td>";
	           tableHtml += "<td><span class='fontStyle'><img style='cursor:pointer' onClick='getName(<%=rset.getLong(1)%>);' src='images/Excel.jpg' width='30' height='25' /></style></td>";
	           tableHtml += "<td><span class='fontStyle'><img style='cursor:pointer' onClick='getAnnexure(<%=rset.getLong(1)%>,auditeeDocName);' src='images/Excel.jpg' width='30' height='25' /></style></td>";
	           tableHtml += "<td><span class='fontStyle'><img style='cursor:pointer' onClick='getAnnexure(<%=rset.getLong(1)%>,auditorDocName);' src='images/Excel.jpg' width='30' height='25' /></style></td>";
	           tableHtml += "<td><span class='fontStyle'><img style='cursor:pointer' onClick='getAnnexure(<%=rset.getLong(1)%>,sampleDocName);' src='images/Excel.jpg' width='30' height='25' /></style></td>";
	           tableHtml += "</tr>";
	           
	      </script>
	       	 
	      <% 	 
	       	
	       	 
               }
             %>
              <script>
                tableHtml += "</tbody>";
                tableHtml += "</table>";
                $('#auditDtl').html(tableHtml);
                
                function getName(piid)
                { 
                  $.ajax({
		  type:"POST",    
		  url: "GenerateExcel.jsp",
		  data:"piid="+piid,         
		    success: function(success) {
		       var output = $.trim(success);
                         window.location.href = 'DownloadExcel.jsp?reportname='+output;
		    }
                  });  
                }
                
                function getAnnexure(piid,docDSName)
                {
                 $.ajax({
		  type:"POST",    
		  url: "getAnnexureDocNames.jsp",
		  data:"piid="+piid+"&docName="+docDSName,         
		    success: function(success) {
		       var docs = $.trim(success);
		       if(docs != "Not Found")
		       {
		         docs = docs.substring(1, docs.length-1);
		         docs = docs.split(',');
		                 var tab = "<table style='width: 100%;border-collapse:collapse;'>";
		                 tab += "<tr><td class='header'>Attached Annexure Document List</td>";
		                 tab += "<td class='dynamicTableTH'><a href='#' id='btnClose' onClick='box_close();'>Close</a></td></tr>";
		                 tab += "<tr><td colspan='2' class='dynamicTableTH'><b> Click on link to download file. </b></td></tr>";
		                 for(var i=0;i<docs.length;i++)
		                  {
		                    tab += "<tr><td colspan='2' style='text-align:left;' class='dynamicTableCell'>";
		                    tab += "<span style='font-size : 11px;'>"+(i+1)+" :&nbsp;&nbsp;<a href='DownloadDocs.jsp?piid="+piid+"&docName="+docDSName+"&fileName="+docs[i]+"'>"+docs[i]+"</a></span></td></tr>";
		                  }
		                 tab += "</table>";
		                 $('#annexureDocs').append(tab);		       
		        box_open();
		       }
		       else
		       {
		         alert('No Document Uploaded.');
		       }
		    }
                  });   
                }
              
              function box_open(){
	          window.scrollTo(0,0);
	          document.getElementById('annexureDocs').style.display='block';
	          document.getElementById('fade').style.display='block';
	      }
	      
	      function box_close()
	      {
	        document.getElementById('annexureDocs').innerHTML = '';
	        document.getElementById('annexureDocs').style.display='none';
	        document.getElementById('fade').style.display='none';
	      }
              
                
              </script>
             <%
	     
	   }
	   catch(Exception ex)
	   {
	     out.println(ex.getMessage());
	   }
	   finally
	   {
	     DBUtility.releaseResources(connection,callableStatement,rset);
	   }
	   
	%>
        
     </body>
 </html>