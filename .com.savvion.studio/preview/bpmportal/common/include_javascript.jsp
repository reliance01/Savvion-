<%@ include file="../common/include_i18n_msgs.jsp" %>
<script type="text/javascript" src="../javascript/prototype.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/scriptaculous.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/engine.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/util.js" charset="UTF-8"></script>

<script type="text/javascript" src="../javascript/pwr.js" charset="UTF-8"></script>

<!-- ExtJS LIBS -->
<script type="text/javascript" src="../javascript/ext/adapter/ext/ext-base.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/ext/ext-all.js" charset="UTF-8"></script>

<script language='JavaScript' src="../javascript/ext/PagingRowNumberer.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/ux/superboxselect/SuperBoxSelect.js" charset="UTF-8"></script>
<script type="text/javascript">
<!--
Ext.BLANK_IMAGE_URL = '../javascript/ext/resources/images/default/s.gif'
-->
</script>
<!-- ENDLIBS -->
<script type='text/javascript' src="../javascript/utilities.js" charset="UTF-8"></script>
<script type='text/javascript' src="../javascript/cal.js" charset="UTF-8"></script>
<script type='text/javascript' src="../javascript/validate.js" charset="UTF-8"></script>
<script type='text/javascript' src="../javascript/top.js" charset="UTF-8"></script> 

<script type='text/javascript' src="../javascript/BmViewport.js" charset="UTF-8"></script>
<script type='text/javascript' src="../javascript/bmutil.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/bm/common/bmplugin.js" charset="UTF-8"></script>

<% Boolean isExtUxDtTime = (Boolean)pageContext.getAttribute("isExtUxDateTime"); 
   if(null != isExtUxDtTime && isExtUxDtTime) {
%>
        <script type="text/javascript" src="../javascript/bm/common/datetime.js" charset="UTF-8"></script>
 
<%
   } 
%>  
<script type="text/javascript" src="../javascript/ux/fileuploadfield/FileUploadField.js"></script>
<script type="text/javascript" src="../javascript/bm/common/bmfield.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/bm/common/bmfilter.js" charset="UTF-8"></script>
<script type="text/javascript" src="../javascript/bm/common/bmdsfilter.js" charset="UTF-8"></script>

<script type="text/javascript" src="../javascript/fileupload.js"></script>


<%@ include file="../common/include_localized_javascript.jspf" %>   
