<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>
<html> 
  <head>
    <script src='scripts/jquery-1.11.2.js'></script>
    <script src='scripts/commonJS.js'></script>
    <link rel='stylesheet' href='style/custom.css' />    
   
    <script>	 
      $(document).ready(function() {
          pwr.getTicketInitialInfo('<%= bizSite.getCurrentUser() %>',callbackInfo);      
      });
      
      var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                           "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                       ];
      var flag = 0;
      var srch;
      function callbackInfo(data)
          { 
            $('.registerDiv').html('');
            if(data != ''){
             $('.registerDiv').css('width','1250px');
             $('.registerDiv').css('margin-left','30px');
             $('.registerDiv').css('height','300px');             
             
             var myTable= "<table align='center' id='registerTab'>";
             var statusVal = ""; 
              myTable += "<thead>";
              myTable += "<tr class='tabHeadRow'>";
              myTable += "<th class='tabHeadTd'>TICKET NUMBER</th>";
              myTable += "<th class='tabHeadTd'>CATEGORY</th>";
              myTable += "<th class='tabHeadTd'>SUB CATEGORY</th>";
              myTable += "<th class='tabHeadTd'>PRODUCT</th>";
              myTable += "<th class='tabHeadTd'>BUSINESS USER</th>";
              myTable += "<th class='tabHeadTd'>APPROVAL DATE</th>";
              myTable += "<th class='tabHeadTd'>IRDA DATE</th>";
              myTable += "<th class='tabHeadTd'>UIN NUMBER</th>";
              myTable += "<th class='tabHeadTd'>CALL REGISTER TIME</th>";
              myTable += "<th class='tabHeadTd'>FINAL CREATIVE</th>";
              myTable += "<th class='tabHeadTd'>STATUS</th>";
              myTable += "</tr>";
              myTable += "</thead>";
              myTable += "<tbody>";
	      for (var i=0; i<data.length; i++) {
	         myTable+="<tr>";
	         myTable+="<td><a style='color:white;' href='#' onClick='getTicketHistory(this)'>"+data[i].ticketNo+"</a></td>";
	         myTable+="<td>"+data[i].category+"</td>";
	         myTable+="<td>"+data[i].subCategory+"</td>";
	         myTable+="<td>"+data[i].product+"</td>";
	         myTable+="<td>"+data[i].businessUser+"</td>";
	         myTable+="<td>"+formatDate(new Date(data[i].legalApprovalTime))+"</td>";
	         myTable+="<td>"+_formatDate(new Date(data[i].irdaDate))+"</td>";
	         myTable+="<td>"+data[i].uinNo+"</td>";
	         myTable+="<td>"+formatDate(new Date(data[i].processTime))+"</td>";
	         if(data[i].status == "COMPLETED"){
	           statusVal = "Completed";
	         }else if(data[i].status == "ACTIVATED"){
	           statusVal = "In Progress";
	         }else if(data[i].status == "SUSPENDED"){
	           statusVal = "Interruped";
	         }else if(data[i].status == "REMOVED"){
	           statusVal = "Deleted";
	         }
	         myTable+="<td><a href='#' style='cursor:pointer;' onClick='getAttachment(this);'>Attachment</a></td>";
	         myTable+="<td>"+statusVal+"</td></tr>";
	         }  
	    myTable += "</tbody></table><div class='paginationDiv'></div>";
	    srch = "<div style='text-align:center;'><span class='tabHeadRow' style='font-weight:bold;text-align:center;width:200px;display:inline;'>Ticket Number : </span>";
	    srch += "&nbsp;&nbsp;<input type='text' class='inp' id='ticketNoInp' />";
	    srch += "&nbsp;&nbsp;<span class='tabHeadRow' style='font-weight:bold;width:200px;text-align:center;display:inline;'>UIN Number :</span>";
	    srch += "&nbsp;&nbsp;<input type='text' class='inp' id='uinNoInp' /><br />";
	    srch += "<span><input type='button' class='button' id='searchUinBtn' value='Search' onClick='getTicketInfo();' /></span></div><br />";	    
	    var backBtn = "<span id='bckBtn'><img src='images/backbutton.png' height='30' width='100' style='margin:20px;cursor:pointer;' onClick='getBack();' /></span>";
	    var excelDownload = "<span id='excel'><img src='images/download-excel.gif' height='30' width='150' style='margin-top:10px;cursor:pointer;' onClick='getExcel();' /></span>";
	    $('.registerDiv').append(srch);
	    $('.registerDiv').append(myTable);
	    $('.registerDiv').append(excelDownload);
	    if(flag == 1)
	    { 
	      $('.registerDiv').append("<br/>"+backBtn);
	    }
	    
            var rowsShown = 5;
            var rowsTotal = $('#registerTab tbody tr').length;
            var numPages = rowsTotal/rowsShown;
            for(i = 0;i < numPages;i++) {
                var pageNum = i + 1;
                $('.paginationDiv').append('<a class="pagination" href="#" rel="'+i+'">'+pageNum+'</a> ');
            }
            $('#registerTab tbody tr').hide();
            $('#registerTab tbody tr').slice(0, rowsShown).show();
            $('.paginationDiv a:first').addClass('paginationActive');
            $('.paginationDiv a').bind('click', function(){
            $('.paginationDiv a').removeClass('paginationActive');
            $('.paginationDiv a').addClass('pagination');
            $(this).addClass('paginationActive');
            var currPage = $(this).attr('rel');
            var startItem = currPage * rowsShown;
            var endItem = startItem + rowsShown;
            $('#registerTab tbody tr').css('opacity','0.0').hide().slice(startItem, endItem).
            css('display','table-row').animate({opacity:1}, 300);
          });
       }else{
        $('.registerDiv').html('');
        $('.registerDiv').append(srch);
        $('.registerDiv').append("<img src='images/download.jpg' style='width:200px;height:200px;'>");
      }
     }
          
          function getTicketInfo()
          {
            var ticketNo = $('#ticketNoInp').val().trim();
            var uinNo = $('#uinNoInp').val().trim();
            if((ticketNo != null && ticketNo.trim() != '') || (uinNo != null && uinNo.trim() != ''))
            {
              flag = 1;
              pwr.getTicketInfo('<%= bizSite.getCurrentUser() %>',ticketNo.trim(),uinNo.trim(),callbackInfo);  
            }
            else
            {
              alert('Please enter Ticket Number Or UIN Number.');            
              $('#ticketNoInp').focus();
            }
          }
          
          function getBack()
          {
            flag = 0;
            pwr.getTicketInitialInfo('<%= bizSite.getCurrentUser() %>',callbackInfo);
          }
          
          function getTicketHistory(obj)
          {
            var ticketNo = $(obj).text();
            pwr.getFlowStatus(ticketNo.trim(),callbackHistry);
          }
          
         function callbackHistry(data)
         {
         if(data != ''){
          var myTable= "<table align='center' id='dataHistory'>";
		  var statusVal = ""; 
		  myTable += "<thead>";
		  myTable += "<tr class='tabHeadRow'>";
		  myTable += "<th class='tabHeadTd'>Performer</th>";
		  myTable += "<th class='tabHeadTd'>Remark</th>";
		  myTable += "<th class='tabHeadTd'>Status</th>";
		  myTable += "<th class='tabHeadTd'>Process Time</th>";
		  myTable += "</tr>";
		  myTable += "</thead>";
		  myTable += "<tbody>";
	      for (var i=0; i<data.length; i++) {
		 myTable+="<tr class=''>";
		 myTable+="<td class=''>"+data[i].prfmr+"</td>";
		 myTable+="<td class=''><div class='shwRMK'>"+data[i].rmks+"</div></td>";
		 myTable+="<td class=''>"+data[i].status+"</td>";
		 myTable+="<td class=''>"+dateFormat(data[i].processTime)+"</td>";
		 }  
	     myTable += "</tbody>";
	     myTable+="</table>";
	     $('.registerDiv').html('');
	     $('.registerDiv').css('width','1100px');
             $('.registerDiv').css('margin-left','90px');
	     var backBtn = "<span id='bckBtn'><img src='images/backbutton.png' height='30' width='100' style='margin:20px;cursor:pointer;' onClick='getBack();' /></span>";
	     $('.registerDiv').append(myTable);
	     $('.registerDiv').append(backBtn);
      }else{
        $('#registerDiv').html('');
        $('#registerDiv').append("<img src='images/download.jpg' style='margin-left:300px;width:200px;height:200px;'>");
      }
     }
         
      
      function formatDate(d){
       if(d.getFullYear() > 2000){
	    function addZero(n){
	       return n < 10 ? '0' + n : '' + n;
	    }
	  
	      return addZero(d.getDate()) + "-" + addZero(monthNames[d.getMonth()])+ "-" + d.getFullYear() + " " + 
	             addZero(d.getHours()) + ":" + addZero(d.getMinutes()) + ":" + addZero(d.getMinutes());
	     }else{
	       return "-";
	     }
      }
      
      function _formatDate(d){
       if(d.getFullYear() > 2000){
	    function addZero(n){
	       return n < 10 ? '0' + n : '' + n;
	    }
	  
	      return addZero(d.getDate()) + "-" + addZero(monthNames[d.getMonth()])+ "-" + d.getFullYear();
	     }else{
	       return "-";
	     }
      }
      
      var piID;
      var process;
      function getAttachment(obj)
      {
        var val = $(obj).parent().siblings(":first").text();
        process = val.split('#')[0];
        piID = val.split('#')[1];
        pwr.getWaterMarkDocs(piID,process,callBackAttachment);
      }
      
      var divObj;
      function callBackAttachment(docs)
      {
        if(docs != null)
        {
         if(docs.length>0)
         {
          if(divObj != null)
          {
            $(divObj).remove();
          }
          divObj = document.createElement('div');
	  divObj.className = 'web_dialog';
          divObj.id = 'dialog';
          var tab = "<table style='width: 100%; border: 0px;' cellpadding='3' cellspacing='0'>";
          tab += "<tr><td class='web_dialog_title'>Attached File List</td>";
          tab += "<td class='web_dialog_title align_right'><a href='#' id='btnClose' onClick='HideDialog();'>Close</a></td></tr>";
          tab += "<tr><td colspan='2' style='padding-left: 15px;color:#2250cd;'><b> Click on link to download file. </b></td></tr>";
          for(var i=0;i<docs.length;i++)
           {
             tab += "<tr><td colspan='2' style='padding-left: 15px;'>";
             tab += "<span style='font-size : 11px;'>"+(i+1)+" :<a href='DownloadDocs.jsp?name="+docs[i]+"&pid="+piID+"&process="+process+"'>"+docs[i]+"</a></span></td></tr>";
           }
          tab += "</table>";
          $(divObj).append(tab);
          $('.registerDiv').append(divObj);
          ShowDialog();
         }
        }
        else
        {
          alert('No Document Available.');
        }
      }   
      
      function ShowDialog()
         {
            $("#dialog").fadeIn(300);
         }
      
	 function HideDialog()
	 {
	    $("#dialog").fadeOut(300);
	 }  
         
      function getExcel()
      {
        var jspcall = "ExcelDownloadTrackHistory.jsp?user=<%= bizSite.getCurrentUser() %>";
        window.location.href = jspcall;
      }
     
    </script>	 
    
  </head>
  <body></body>
</html>