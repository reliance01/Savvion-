<%@tag import="org.json.JSONObject"%>
<jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"/>
<%@ tag import="com.savvion.BizSolo.Server.*,com.savvion.BizSolo.beans.*,java.util.Vector"%> 
<%@ tag import="net.sf.json.*,com.savvion.sbm.bpmportal.service.JSONConverterService,java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo" %>
<%@ attribute type="java.lang.String" name="id" required="true" %>
<%@ attribute type="java.lang.String" name="name" required="false" %>
<%@ attribute type="java.lang.String" name="config" required="true" %>
<%@ attribute type="java.lang.String" name="path" required="true" %>
<%@ attribute type="java.lang.String" name="dataslots" required="false" %>
<%@ attribute type="java.lang.String" name="parentId" required="false" %>
<%
    String _json = (String)jspContext.findAttribute("dataslots");
	if(_json != null ) {
	    try{
		
			List _dataslots = JSONConverterService.getInstance().toList(_json,java.lang.String.class);
			
			String dataslotsJSON = (String)session.getAttribute("dataslotsJSON");
			JSONObject jsonObj = new JSONObject(dataslotsJSON);
			
			for(int i=0;i<_dataslots.size();i++){
				String dataslotName = (String)_dataslots.get(i);
				if (bean.getPropVector(dataslotName) != null) {
					%>
					<script language="JavaScript">
					<!--
					var <%=(String)_dataslots.get(i)%> = <%=bean.getPropVector(dataslotName)%>;
					-->
					</script>
					<%
				} else if (bean.getPropMap(dataslotName) != null) {
					%>
					<script language="JavaScript">
					<!--
					var <%=(String)_dataslots.get(i)%> = <%=bean.getPropMap(dataslotName)%>;
					-->
					</script>
					<%					
				}				
			}
		  
		}catch(Exception e){            		
		}	
	}	
%>

<div id="${id}">
      <div id="${path}_master"></div>
      <div id="${path}_detail"></div>
</div>
<script language="JavaScript">
<!--
Ext.onReady(function(){               
	// initialize the grid
	// create business object view	
	var boView = new Portal.ux.BusinessObjectView(${config},'${parentId}'); 
});
-->
</script>