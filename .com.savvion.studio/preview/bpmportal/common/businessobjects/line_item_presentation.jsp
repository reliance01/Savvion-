<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.springframework.beans.BeanWrapper" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.savvion.sbm.bpmportal.service.BusinessObjectService" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.DateTimeUtils" %>
<%@ page import="org.springframework.beans.BeanWrapperImpl" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%
      Object pObject = null;
      String path = null;
      String pType = null;
      String businessObjectName = null;
      int index = ((Integer)request.getAttribute(PortalConstants.INDEX_PARAM)).intValue();
      String commandAction = (String)request.getAttribute(PortalConstants.COMMAND_ACTION_PARAM);
      String dataslotName = (String)request.getAttribute(PortalConstants.DATASLOT_NAME_PARAM);
      Object object = request.getAttribute(PortalConstants.OBJECT_PARAM);
      LinkedHashMap presentationMap = (LinkedHashMap)request.getAttribute(PortalConstants.PRESENTATION_MAP_PARAM);
      Iterator iterator = presentationMap.keySet().iterator(); 
      SimpleDateFormat dateFormatter = (SimpleDateFormat)request.getAttribute(PortalConstants.DATE_FPORMATTER_PARAM);
      if(request.getAttribute(PortalConstants.BUSINESS_OBJECT_NAME_PARAM) != null) 
         businessObjectName = (String)request.getAttribute(PortalConstants.BUSINESS_OBJECT_NAME_PARAM);
      
%>
<script>
//<![CDATA[  
Ext.onReady(function(){
    Ext.QuickTips.init();
    var fieldsDef = [<% while(iterator.hasNext()) {
                String key = (String)iterator.next();
                try
		{
	        	path = StringUtils.substringAfter(key,".");
	                pType = BusinessObjectService.getPropertyType(object,path);
	                pObject = BusinessObjectService.getPropertyValue(object,path,dateFormatter); 
	        }
	        catch(Exception e)
	        { }
       %>
               <% if(java.lang.Long.class.getName().equals(pType) || java.lang.Integer.class.getName().equals(pType) || java.math.BigDecimal.class.getName().equals(pType)) { %>
		{
	              name: '<%=key%>',
		      id: '<%=key%>',
	              xtype: 'numberfield', 
		      allowDecimals: <%=(java.math.BigDecimal.class.getName().equals(pType)) ? true : false %>,
		      fieldLabel: '<%=presentationMap.get(key)%>',
		      validationEvent: true,
		      validateOnBlur: true,
		      invalidText: '<sbm:message key="DefineDialogPromptError4" />',
		      msgTarget: 'side',
                      allowBlank:false,
		      blankText: '<sbm:message key="DefineDialogPromptError6" />',
		      value: '<%=(!PortalConstants.ADD_BO_ACTION.equals(request.getAttribute(PortalConstants.COMMAND_ACTION_PARAM))) ? pObject : ""%>'
		 }
		 <% } else if(java.sql.Timestamp.class.getName().equals(pType)) {%>
		 {
		      name: '<%=key%>',
		      id: '<%=key%>',
		      xtype:'datefield',
		      fieldLabel: '<%=presentationMap.get(key)%>',
		      format:'F j, Y g:i A',
		      blankText: '<sbm:message key="DefineDialogPromptError" />',
		      value: '<%=(!PortalConstants.ADD_BO_ACTION.equals(request.getAttribute(PortalConstants.COMMAND_ACTION_PARAM))) ? pObject : ""%>'
		 }
		 <% } else if(java.lang.Boolean.class.getName().equals(pType)) {%>
		      new Ext.form.Checkbox({
		      name: '<%=key%>',
		      id: '<%=key%>',
		      //style:'background-position:-13px 0;',
		      autoShow: true,
                      fieldLabel: '<%=presentationMap.get(key)%>',
		      checked: '<%=(!PortalConstants.ADD_BO_ACTION.equals(request.getAttribute(PortalConstants.COMMAND_ACTION_PARAM)) && pObject != null) ? pObject.toString() : false%>'
		    })
		 <% } else { %>
		 {
		      name: '<%=key%>',
		      id: '<%=key%>',
	              fieldLabel: '<%=presentationMap.get(key)%>',
		      validationEvent: true,
		      validateOnBlur: true,
		      invalidText: '<sbm:message key="DefineDialogPromptError4" />',
		      msgTarget: 'side',
                      allowBlank:false,
		      blankText: '<sbm:message key="DefineDialogPromptError" />',
		      value: '<%=(!PortalConstants.ADD_BO_ACTION.equals(request.getAttribute(PortalConstants.COMMAND_ACTION_PARAM))) ? pObject : ""%>'
		 }
		 <% } %>
		 <%= (iterator.hasNext()) ? "," : ""%> 
       <% } %>];    
    
     var fieldSet = new Ext.form.FieldSet({
       defaultType: 'textfield',
       title: '<%=businessObjectName%>',
       autoHeight:true,
       defaults: {width: 210},
       items: fieldsDef,
       bodyStyle: 'padding:0 10px 0;',
       labelWidth: 110
    });
    
    function cancel(){
        <c:choose>
            <c:when test="${commandAction eq 'addBusinessObject'}">sbm.deleteBusinessObject('<c:out value='${dataslotName}'/>',<%=index%>);</c:when>
            <c:otherwise>sbm.renderBusinessObject('<c:out value='${dataslotName}'/>','renderBusinessObject');</c:otherwise>
        </c:choose>
    }
    
    function updateBusinessObject(){
       var msg ="";
       var dialog;
       for(var i=0;i<fieldsDef.length;i++){
            var id = fieldsDef[i]["id"];
	    var field = fieldSet.findById(id);
	    if(!field.isValid(true))
	        msg = msg + fieldsDef[i]["fieldLabel"] +"\n ";
       }
       
        function msg(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.getElementById('<c:out value='${dataslotName}'/>'), {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('t').pause(1).ghost("t", {remove:true});
        }
       
       
       if(msg.length > 0) 
       {
            Ext.fly('update').dom.value = Ext.MessageBox.WARNING;
            Ext.MessageBox.show({ title: 'Alert',
	    	                  msg: 'following fields are not valid:\n'+msg,
                                  buttons: Ext.MessageBox.OK,
                                  animEl: '<c:out value='${dataslotName}'/>',
                                  fn: msg });
       } else {
            var params = "dataslotName=<%=dataslotName%>&index=<%=index%>&";
            for(var i=0; i<fieldsDef.length;i++){
                var el = document.getElementById(fieldsDef[i]['name']);
		if(el.type == 'checkbox')
		    params += (fieldsDef[i]['name'] +"="+ el.checked+"&"); 
                else
 		    params += (fieldsDef[i]['name'] +"="+ el.value+"&"); 
            }
            sbm.utils.updateContent('<%=dataslotName%>','GET',"/sbm/bpmportal/common/businessobjects/updateBusinessObject.portal?"+params);   
       }
    }
    
    var panel = new Ext.Panel({	        
	        loadMask: true,
	        autoHeight:true,
	        renderTo: '<c:out value="${dataslotName}"/>_businessObjectDetail',
	        //width:600,
                //height:300,
	        title:'<%=businessObjectName%>',
	        frame:true,
                bodyStyle: 'padding-right:5px;',
		tbar: [{text : <c:choose>
                                 <c:when test="${commandAction eq 'addBusinessObject'}">'Add <c:out value='${businessObjectName}'/> to List'</c:when>
                                 <c:otherwise>' Update <c:out value='${businessObjectName}'/>'</c:otherwise>
                               </c:choose>, 
		        iconCls: 'updateIcon',
			handler: updateBusinessObject,
			pressed: true, 
			enableToggle: true,
			id:'update'
			},'-',
		       {text:'Cancel', handler: cancel, pressed: true, iconCls: 'cancelIcon' }],
		items: [fieldSet]
    });
    panel.render("<c:out value='${dataslotName}'/>_businessObjectDetail");    
});
//]]>
</script>
<div id="<c:out value='${dataslotName}'/>_businessObjectDetail"></div>
<br/>



