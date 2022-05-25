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
        <link rel="stylesheet" type="text/css" href="style/jquery.multiselect.css" />

	

        <link rel="stylesheet" type="text/css" href="../../../bpmportal/javascript/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="../../../bpmportal/javascript/ext/resources/css/xtheme-default.css" />
        <link rel="stylesheet" type="text/css" href="../../../bpmportal/css/theme01/bm-all.css" />
         <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
        
        <script type="text/javascript" charset="utf8" src="script/jquery-1.11.1.js"></script>
        
	<script type="text/javascript" charset="utf8" src="script/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="script/jquery.modalbox-1.5.0-min.js"></script>
	<script type="text/javascript" src="script/ext-all.js"></script>
	<script type="text/javascript" charset="utf8" src="script/dataTables.jqueryui.js"></script>
	<script type="text/javascript" src="//code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
	<script type="text/javascript" src="script/jquery.multiselect.js"></script>
	<script type="text/javascript" src="script/bootstrap.min.js"></script>
        
 

        <style>

		div.ui-datepicker
      {
          font-size:11px;
		  
      }


	  
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
    opacity: 10.3;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
	        
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
		  
		  .form-control {
height:26px;
}
          
          .ui-widget-header
          {
   			  height:24px;
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
                <div id = "newReportDiv" style ="text-align : left; display : none;"><button id="myBtn" class="btn btn-sm btn-info">Open New Report</button></div>
                <%@ include file="../../../bpmportal/common/include_bottom.jsp" %>
            </td>
        </tr>
    </table>
</div>
        
	<div class="header">Audit Report</div>
	<div id='auditDtl'></div>
	<div id='annexureDocs'></div>
	<div id="fade"></div>

<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content container">
  
   
				
 <div class="container" style="width:1000px ;">

  <span class="close">&times;</span>
  <h2>New Audit Reports</h2>
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#home">Consolidate Report</a></li>
    <li><a data-toggle="tab" href="#menu1">TAT Report</a></li>
   
  </ul>

  <div class="tab-content">

    <div id="home" class="tab-pane fade in active">
      <h3>Consolidate Report</h3>
      <p><i>Note : </i>All fields are mandatory. To close report either choose close button or click out side white area. </p>
	  <div> 
       
	   
	    <table cellspacing="10px">
				<tr>
						<td>
								<H4> <span class="label label-primary">Start Date From:</span></H4>
						</td>
						<td>
								<H4><input type="text" class='date data form-control' id="startDate"  placeholder="Select Date" readonly></H4>  
						</td>
				
				
						<td>
								<H4><span class="label label-primary">Start Date To:</span></H4>
						</td>
						<td>
								<H4><input type="text" class='date data form-control' id="endDate" placeholder="Select Date" readonly></H4>
						</td>
				</tr>
	
				<tr> 
						<td>
								<H4><span class="label label-primary">Start Auditor :</span></H4>
						</td>
						<td>
							<H4><select id="auditorDropDown" class = "data form-control">
								<option value = "All">All</option>
								<option value = "70031473">Jagdish Mundra</option>
								<option value = "70005195">Pradyut Mullick</option>
								<option value = "70162009">Saikat Sanbui</option>
								<option value = "70167720">Siddhartha Sett</option>
								<option value = "10010097">Suresh C Ramachandran</option>		
							</select></H4>
						</td>
						<td>
								<H4><span class="label label-primary">Audit Type :</span></H4>
						</td>
						<td>
							<H4><select id="auditDropDown">
									<option>Branch Audit</option>
									<option>OD Claims</option>
									<option>Legal Claims</option>
							</select></H4>
						</td>
						<td  >
									<H4><span class="label label-primary">&nbsp; &nbsp; Status :&nbsp; &nbsp; </span></H4>
						</td>
						<td>
								<H4><select id="statusDropDown">
									<option>ALL</option>
									<option>COMPLETED</option>
									<option>ACTIVATED</option>
								</select>	</H4>
						</td>
				</tr>
				
			
						
				<tr style = "text-align:center">
					<td colspan = "6"><div><input style="font-size: 16px;" type = "button" class="btn btn-sm btn-primary" value = "Download Report" onclick="getConsolidateReport();"/></div> </td>
				</tr>
			
			
				
	</table>
	
</div>	
	  
    </div>
    <div id="menu1" class="tab-pane fade">
      <h3>TAT Report</h3>
      <table cellspacing="20px">
				<tr>
											<td>
								<H4><span class="label label-primary">Start Auditor :</span></H4>
						</td>
                                               <td>
							<H4><select id="auditorDropDownTAT" class = "form-control">
								<option value = "All">All</option>
								<option value = "70031473">Jagdish Mundra</option>
								<option value = "70005195">Pradyut Mullick</option>
								<option value = "70162009">Saikat Sanbui</option>
								<option value = "70167720">Siddhartha Sett</option>
								<option value = "10010097">Suresh C Ramachandran</option>		
							</select></H4>
						</td>
						
				
						<td>
								<H4> <span class="label label-primary">Select Year :</span></H4>
						</td>
						<td>
								<H4><select id="yearDropDown">
								
								<option value = "2015">2015-2016</option>
								<option value = "2016">2016-2017</option>
								<option value = "2017">2017-2018</option>
										
							</select></H4>			
						</td>
				

						
                                           </tr>

					<tr>
						
						<td>
								<H4> <span class="label label-primary">Start Date From:</span></H4>
						</td>
						<td>
								<H4><input type="text" class='date form-control' id="startDateTAT"  placeholder="Select Date" readonly></H4>  
						</td>
				
				
						<td>
								<H4><span class="label label-primary">Start Date To:</span></H4>
						</td>
						<td>
								<H4><input type="text" class='date form-control' id="endDateTAT" placeholder="Select Date" readonly></H4>
						</td>
				
	
				

						
                                           </tr>


				<tr style = "text-align:center">
					<td colspan = "6"><div><input style="font-size: 16px;" type = "button" class="btn btn-sm btn-primary" value = "Download Report" onclick="getTATReport();"/></div> </td>
				</tr>
                         </table>
    </div>
  </div>
</div>		
	
	
  </div>

</div>




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
       	 
            var User = <%= bizSite.getCurrentUser() %>;
             var accessUser = ["70035081","10010097","70167720","70031473","70162009","70005195","10001105"];
             var isUser = accessUser.indexOf(User.toString());
             console.log(isUser);
             if(isUser >= 0)
             {
             $("#newReportDiv").show();
             }
             

	$(".date" ).datepicker({
         minDate: new Date('2015/06/15'),
	 maxDate: 0,
	 dateFormat: 'dd-mm-yy'
       });

   $("#auditorDropDown").multiselect();
   $("#auditDropDown").multiselect();
   $("#statusDropDown").multiselect();
   
   $("select").multiselect({
   header: false,
   hide : ['explode', 500]
	});
	
	$("#auditorDropDown").multiselect({
  
  
	});
     $("#yearDropDown").multiselect({
   multiple: false,
   
	});
	
	$("#auditDropDown").multiselect({
   multiple: false,
  selectedList : false
   
  
	});
	$("#statusDropDown").multiselect({
   multiple: false
   
	});
	
	 $('#auditorDropDown').siblings().children().eq(1).html($("#auditorDropDown option:selected").text());
	 $('#auditDropDown').siblings().children().eq(1).html($("#auditDropDown option:selected").text());
	 $('#statusDropDown').siblings().children().eq(1).html($("#statusDropDown option:selected").text());
  $('#yearDropDown').siblings().children().eq(1).html($("#yearDropDown option:selected").text());   
  $('#yearDropDown').change(function(){setSelectedValue()});
   $('#auditDropDown').change(function(){setSelectedValue()});
   $('#statusDropDown').change(function(){setSelectedValue()});
   $('#auditorDropDown').change(function(){checkValidation()});
    $('#auditorDropDownTAT').change(function(){checkTATValidation()});

   $('#endDate').change(function(){
                    
                        
			
                        var startDateArray = $('#startDate').val().split('-');
                        var modifiedStartDate  = Date.parse(startDateArray[1] + '-' + startDateArray[0] + '-' + startDateArray [2]);
                        var startMili = new Date(modifiedStartDate);
                        
			var endDateArray = $('#endDate').val().split('-');
                        var modifiedEndDate  = Date.parse(endDateArray[1] + '-' + endDateArray[0] + '-' + endDateArray[2]);
                        var endMili = new Date(modifiedEndDate);

                        var result = endMili - startMili;
  			console.log('result' + result );
                        if(result > 8035200000)
			{
				$('#endDate').val('');   
				alert("Please ensure From and To date Interval should not exceed more that 3 months !!!! ");          
                                    
			}
                        
         });


        } );

      $('#yearDropDown').change(function(){
		var selectedYear = $("#yearDropDown option:selected").val();
			
		
		switch(selectedYear)
		{
		case "2015": $('#startDateTAT').val('01-04-2015');
		$('#endDateTAT').val('31-03-2016');
         	break;
		case "2016":  $('#startDateTAT').val('01-04-2016');
		$('#endDateTAT').val('31-03-2017');
         	break;
		case "2017": $('#startDateTAT').val('01-04-2017');
		$('#endDateTAT').val('31-03-2018');
         	break;
		
		default: alert('Please set case in switch');
         	break;
		}
	});
	$('#startDateTAT').val('01-04-2015');
	$('#endDateTAT').val('31-03-2016');
  </script>
  
  <script>
      tableHtml = "<table id='auditInfo' class='display' cellspacing='0' align='center' width='100%'><thead><tr>";
      tableHtml += "<th>Branch</th><th>Auditor</th><th>Auditee</th><th>Audit Start Date</th><th>Pending With</th><th>Status</th><th>Audit Report</th><th>Auditee Annexure</th><th>Auditor Annexure</th><th>Sample Docs</th>";
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
		        // docs = docs.substring(1, docs.length-1);
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


<script>


function checkValidation()
{

var auditorArray = $("#auditorDropDown").multiselect("getChecked");
console.log(auditorArray.length);
for(i=0 ; auditorArray.length > i ; i++)
{

var check = auditorArray[i].value;
if(check === "All")
{
if(auditorArray.length != 1)
{
$("#auditorDropDown").multiselect("uncheckAll");
}

}

}
}

function checkTATValidation()
{

var auditorArray = $("#auditorDropDownTAT").multiselect("getChecked");
console.log(auditorArray.length);
for(i=0 ; auditorArray.length > i ; i++)
{

var check = auditorArray[i].value;
if(check === "All")
{
if(auditorArray.length != 1)
{
$("#auditorDropDownTAT").multiselect("uncheckAll");
}

}

}
}

function getConsolidateReport()
{
var isValidate =false;
$(".data").each(function()
{
if($(this).val() === "")
{
alert("Please select required fields");
isValidate = false;
$(this).focus();
return false;
}
isValidate = true;
});

var auditorArray = $("#auditorDropDown").multiselect("getChecked");
if(auditorArray.length == 0)
{
alert("Please select Auditor !!!");
return false;

}
var startDateArray = $('#startDate').val().split('-');
       var modifiedStartDate  = Date.parse(startDateArray[1] + '-' + startDateArray[0] + '-' + startDateArray [2]);
                  var startMili = new Date(modifiedStartDate);
       	var endDateArray = $('#endDate').val().split('-');
             var modifiedEndDate  = Date.parse(endDateArray[1] + '-' + endDateArray[0] + '-' + endDateArray[2]);
                var endMili = new Date(modifiedEndDate);


if(startMili  > endMili)
{
alert("Start Date should be lower than End Date !!!")
return false;
}

if(isValidate == true)
{

var startDate = $("#startDate").val();
var endDate = $("#endDate").val();
var auditor = "";
var auditType = $("#auditDropDown option:selected").text();
var status = $("#statusDropDown option:selected").text();
var auditorArray = $("#auditorDropDown").multiselect("getChecked");
for(i=0 ; auditorArray.length > i ; i++)
{
console.log(auditorArray[i].value + "length "+ i);
auditor = auditor + auditorArray[i].value +",";
if(auditorArray.length - 1 === i)
{
auditor = auditor.slice(0, -1);
}
}
if(auditor === "All")
{
auditor = '70031473,70005195,70162009,70167720,10010097';
}


 $.ajax({
		 type:"POST",    
		  url: "GenerateExcel2.jsp",
		  data:"startDate="+startDate+"&endDate="+endDate+"&auditor="+auditor+"&auditType="+auditType+"&status="+status,         
		    success: function(success) {
		       var output = $.trim(success);
                         window.location.href = 'DownloadExcel.jsp?reportname='+output;
		    }
                });  
            

}

}

function getTATReport()
{


var auditorArray = $("#auditorDropDownTAT").multiselect("getChecked");
if(auditorArray.length == 0)
{
alert("Please select Auditor !!!");
return false;

}

if( $("#endDateTAT").val() == "")
{
alert("Please select Start Date to");
return false;
}
if( $("#startDateTAT").val() == "")
{
alert("Please select Start to From");
return false;
}

var startDateArray = $('#startDateTAT').val().split('-');
       var modifiedStartDate  = Date.parse(startDateArray[1] + '-' + startDateArray[0] + '-' + startDateArray [2]);
                  var startMili = new Date(modifiedStartDate);
       	var endDateArray = $('#endDateTAT').val().split('-');
             var modifiedEndDate  = Date.parse(endDateArray[1] + '-' + endDateArray[0] + '-' + endDateArray[2]);
                var endMili = new Date(modifiedEndDate);


if(startMili  > endMili)
{
alert("Start Date should be lower than End Date !!!")
return false;
}


var startDate = $("#startDateTAT").val();
var endDate = $("#endDateTAT").val();
var auditor = "";
var auditorArray = $("#auditorDropDownTAT").multiselect("getChecked");
for(i=0 ; auditorArray.length > i ; i++)
{
console.log(auditorArray[i].value + "length "+ i);
auditor = auditor + auditorArray[i].value +",";
if(auditorArray.length - 1 === i)
{
auditor = auditor.slice(0, -1);
}
}
if(auditor === "All")
{
auditor = "70031473,70005195,70162009,70167720,10010097";
}


var methodName = "method1";

 $.ajax({
		 type:"POST",    
		  url: "TATReport.jsp",
		  data:"startDate="+startDate+"&endDate="+endDate+"&auditor="+auditor,         
		    success: function(success) {
		     var output = $.trim(success);
                         window.location.href = 'DownloadExcel.jsp?reportname='+output;	
	    }
                });  


}


function setSelectedValue()
{
startTimer();
}

function startTimer() {
    setTimeout(stopTimer,25);
}

function stopTimer() {
$('#auditDropDown').siblings().children().eq(1).html($("#auditDropDown option:selected").text());
 $('#statusDropDown').siblings().children().eq(1).html($("#statusDropDown option:selected").text());   
 $('#yearDropDown').siblings().children().eq(1).html($("#yearDropDown option:selected").text());  
}

// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = $(".close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
    modal.style.display = "block";
	
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

        
     </body>
 </html>