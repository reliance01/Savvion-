<%@ taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo" %>
<script>
   $('.registerDiv').css('width','600px');
   $('.registerDiv').css('height','300px');
   $('.registerDiv').css('margin','10px 0px 0px 375px');
   $('#loadDoc').html($('#docs').html());
</script>
<table id='registerTab' align='center'>
    <tr>
      <td height='35'>Application Type<span class='mandetory'>*</span></td>      
      <td style='padding-left:2px;'>
        <select id='appType' onChange='getCategoryList();'>
          <option value='Select'>Select Application</option>
          <option value='Marcom'>Marcom</option>
          <option value='Online Sales Management'>Online Sales Management</option>
        </select>
      </td>
   </tr>
   <tr>
      <td height='35'>Select Category<span class='mandetory'>*</span></td>      
      <td style='padding-left:2px;'>
        <select id='categryList' onChange='getSubCategories();' disabled>
          <option value='Select'>Select Category</option>
        </select>
      </td>
   </tr>
   <tr>
      <td height='35'>Select Sub Category<span class='mandetory'>*</span></td>      
      <td style='padding-left:2px;'>
       <select id='subCategryList' disabled>
         <option value='Select'>Select SubCategory</option>
       </select>
     </td>
   </tr>
   <tr>
      <td height='35'>Remarks<span class='mandetory'>*</span></td>      
      <td style='padding-left:2px;'><textarea rows='5' cols='30' id='rmks'></textarea></td>
   </tr>
   <tr>
      <td height='35'>Upload Screenshot<span class='mandetory'>*</span></td>      
      <td style='padding-left:2px;'>
          <div id='loadDoc'></div>
      </td>                       
   </tr>
   <tr>
     <td  colspan='2' height='35'>
       <img src='images/Register-Now-Button.png' id='registerID' style='cursor:pointer;margin:10px 0px 5px 150px;' height='45' width='150' onclick='setDSValues();' />
     </td>     
   </tr>
</table>