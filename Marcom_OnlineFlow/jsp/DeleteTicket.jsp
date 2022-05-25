<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"></jsp:useBean>

<table id='registerTab' align='center'>

  <tr>  
     <td height='35'>Enter Ticket Number : <span class='mandetory'>*</span></td>      
     <td style='padding-left:2px;'>
        <input type='text' id='tktNo' /	>       
     </td>
  </tr>
  <tr>
     <td height='35'>Reason for Deletion : <span class='mandetory'>*</span></td>      
     <td style='padding-left:2px;'><textarea rows='5' cols='30' id='dltReason'></textarea></td>
  </tr>
  <tr>
     <td  colspan='2' height='35'>
         <img src='images/deleteTkt.jpg' style='cursor:pointer;margin:30px 0px 5px 150px;' height='35' width='120' onclick='deleteTicket();' />
     </td>     
  </tr>

</table>


<script>
   $('.registerDiv').css('height','200px');
   $('.registerDiv').css('width','600px');
   $('.registerDiv').css('margin','10px 0px 0px 375px');
    var tktNo;
    var dltReason;
    function validate()
    {
       tktNo = $('#tktNo').val();
       dltReason = $('#dltReason').val();
       if(tktNo == null || tktNo.trim() == '')
       {
         $('#tktNo').val('');
         alert('Please Enter Ticket Number to be deleted.');
         $('#tktNo').focus();
         return false;
       }
       else if(tktNo.indexOf('#') === -1)
       {
         alert('Please Enter valid Ticket Number.');
         $('#tktNo').focus();
         return false;
       }
       else if(tktNo.indexOf('#') != -1 && tktNo.split('#')[1].length == 0)
       {
           alert('Please Enter valid Ticket Number.');
           $('#tktNo').focus();
           return false;
       }
       else if(dltReason == null || dltReason.trim() == '')
       {
         $('#dltReason').val('');
	 alert('Please Enter Reason for Delete Ticket.');
	 $('#dltReason').focus();
         return false;
       }
       else
       {
         return true;
       }
    }
    
 function deleteTicket()
 {
   if(validate())
   {
     pwr.deleteTicket(tktNo,'<%= bizSite.getCurrentUser() %>',dltReason,CB_deleteTicket);
   }
   
 }
 
 function CB_deleteTicket(val)
 {
   if(val == true)
   {
     $('#tktNo').val('');
     $('#dltReason').val('');
     alert('Ticket has been Deleted.');
   }
   else
   {
     $('#tktNo').val('');
     $('#dltReason').val('');
     alert('Ticket not Deleted.');
   }
 }
 
</script>