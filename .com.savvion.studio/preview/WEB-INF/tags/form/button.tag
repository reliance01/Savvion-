<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo" %>
<%@ attribute type="java.lang.String" name="id" required="false" %>
<%@ attribute type="java.lang.String" name="name" required="false" %>
<%@ attribute type="java.lang.String" name="link" required="false" %>
<%@ attribute type="java.lang.String" name="onclick" required="false" %>
<%@ attribute type="java.lang.String" name="resourceType" required="true" %>
<div id="${id}_${link}_btn"></div>
<script language="JavaScript">
<!--
Ext.onReady(function(){
	Ext.create('Ext.Button', {
	                id: '${id}_${link}_id',			
			text: '<bizsolo:getLabel name="${link}" type="${resourceType}"/>',
			renderTo: Ext.get('${id}_${link}_btn'),
			type: ('${onclick}'.length != 0) ? 'button': 'submit',			
			handler: function() {
				if ('${id}' === 'bizsite_completeTask') {
					document.forms[0].onsubmit();
				} 
				else {
					alert('Action not supported in preview mode');
				}
			}
	});	  
});
-->
</script>