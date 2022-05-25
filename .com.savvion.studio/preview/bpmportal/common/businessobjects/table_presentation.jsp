<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.springframework.beans.BeanWrapper" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.savvion.sbm.bpmportal.service.BusinessObjectService" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="org.springframework.beans.BeanWrapperImpl" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%
      int index = 0;
      String sortBy = null;
      String businessObjectName = null;
      Boolean orderBy = Boolean.FALSE;
      
      String dataslotName = (String)request.getAttribute(PortalConstants.DATASLOT_NAME_PARAM);
      // contains property path anf column Name
      LinkedHashMap presentationMap = (LinkedHashMap)request.getAttribute(PortalConstants.PRESENTATION_MAP_PARAM);
      System.out.println(presentationMap);
      List columnNames = (List)request.getAttribute(PortalConstants.COLUMN_NAMES_PARAM);
      String editable = (request.getAttribute(PortalConstants.IS_EDITABLE_PARAM) == null)? Boolean.FALSE.toString() : (String)request.getAttribute(PortalConstants.IS_EDITABLE_PARAM);
      SimpleDateFormat dateFormatter = (SimpleDateFormat)request.getAttribute(PortalConstants.DATE_FPORMATTER_PARAM);
      List list = (List)request.getAttribute(PortalConstants.BO_LIST_PARAM);
      
      if(request.getAttribute(PortalConstants.SORT_BY_PARAM) != null) 
          sortBy = (String)request.getAttribute(PortalConstants.SORT_BY_PARAM);
      if(request.getAttribute(PortalConstants.ORDER_BY_PARAM) != null) 
          orderBy = (Boolean)request.getAttribute(PortalConstants.ORDER_BY_PARAM);
      if(request.getAttribute(PortalConstants.BUSINESS_OBJECT_NAME_PARAM) != null)
          businessObjectName = (String)request.getAttribute(PortalConstants.BUSINESS_OBJECT_NAME_PARAM); 
     
%>
<div id="businessObjectGrid"></div>
<script type="text/javascript" src="../javascript/sbm/BusinessObjectHandler.js"></script>
<input type="hidden" name="<%=dataslotName%>" value="update"/>
<script type="text/javascript" defer="defer">
//<![CDATA[ 
YAHOO.util.Event.onDOMReady(function(){
var <%=businessObjectName%>_businessObjectHandler; 
Ext.namespace("Bm.BusinessObjects");
Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
var fieldDefs = [{name:'edit'},{name:'delete'},<% for(int i=0;i<columnNames.size();i++) { %>{name: '<%=columnNames.get(i)%>' }<% if( i != (columnNames.size()-1)) { %> , <% } } %>];
var data =[<% for(int i=0; i < list.size(); i++) {  
           Object row = list.get(i);
	   Object pObject = null;
	   %>['edit','delete',<%
           Iterator pIterator = presentationMap.keySet().iterator();
            while(pIterator.hasNext()){
	         String pValue = "";
		 try
                 {
                              String nPath = (String)pIterator.next();
			      String pPath = StringUtils.substringAfter(nPath,".");
			      pValue = BusinessObjectService.getPropertyValue(row,pPath,dateFormatter);
			      
                 }
		 catch(Throwable th)
		 {
		 }finally{
		    %>'<%=pValue%>'<%=pIterator.hasNext() ? "," : ""%><%
		 }
		 
	     }
	     %>]<%=((list.size()-1) != i) ? "," : ""%><%
	  }%>
];

Bm.BusinessObjects.renderCheckBox = function(value, metadata, record, rowIndex, colIndex, store){ 
  return '<input type="checkbox" '+((value == 'true') ? 'checked=\'true\' ' : '')+'disabled/>';
}

Bm.BusinessObjects.renderer = function(value, metadata, record, rowIndex, colIndex, store){ 
 if(colIndex == 1){
     return "<a href=\"javascript:sbm.editBusinessObject('<c:out value='${dataslotName}'/>',"+rowIndex+");\">"+
      "<img src=\"/sbm/bpmportal/javascript/images/cog_edit.png\"/></a>";
 }else if(colIndex == 2){
      return "<a href=\"javascript:sbm.deleteBusinessObject('<c:out value='${dataslotName}'/>',"+rowIndex+");\">"+
      "<img src=\"/sbm/bpmportal/javascript/images/delete.gif\"/></a>";
  }
};

var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer({header: "No.",width: 32, sortable: false}),
	{header: 'Edit',width:50,sortable:false,renderer:Bm.BusinessObjects.renderer},
	{header: 'Delete',width:50,sortable:false,renderer:Bm.BusinessObjects.renderer},
        <% for(int i=0;i<columnNames.size();i++) { %>
	 {header: "<%=columnNames.get(i)%>", width: 100, sortable: true,dataIndex: "<%=columnNames.get(i)%>"
	   <% if(list !=null && list.size() > 0){  
	       String clolumnName = (String)columnNames.get(i);
	       Iterator pIterator = presentationMap.keySet().iterator();
               while(pIterator.hasNext()){
	            String key = (String)pIterator.next();
		    String value = (String)presentationMap.get(key);
		    if(value.equals(clolumnName) && BusinessObjectService.getPropertyType(list.get(0),StringUtils.substringAfter(key,".")).equals(java.lang.Boolean.class.getName())) { %>
		    ,renderer:Bm.BusinessObjects.renderCheckBox
	   <% } } } %>
	     
	  
	 }
	 <% if( i != (columnNames.size()-1)) { %> ,<% } %>
	<% } %>
]);

Ext.onReady(function() { 
      <%=businessObjectName%>_businessObjectHandler = new BusinessObjectHandler("<%=request.getContextPath()%>",
                           "<%=dataslotName%>","<%=businessObjectName%>","businessObjectGrid",fieldDefs,cm,data);
      <%=businessObjectName%>_businessObjectHandler.gridPanel.render();
      <%=businessObjectName%>_businessObjectHandler.gridPanel.getView().refresh();
      <%=businessObjectName%>_businessObjectHandler.gridStore.loadData(data);
      //<c:out value="${dataslotName}"/>_businessObjectHandler.refresh();
});
});
//]]>
</script>

