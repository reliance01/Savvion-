<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<div id="layout-outer-side-decoration">
<div id="layout-inner-side-decoration">
<div id="layout-box">
<div id="layout-content-outer-decoration">
<div id="layout-content-inner-decoration">
<div id="layout-content-container">
<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:#FFFFFF solid 10px; background:#FFFFFF;">
<tbody><tr>
<td valign="top" colspan="3" id="td_layout-column_column-1">
<div class="" id="layout-column_column-1">
<c:if test="${not empty columnOne}">
	<c:forEach var="widget" items="${columnOne}" varStatus="status">
		<%@ include file="../common/widget.jsp" %>
	</c:forEach>
</c:if>
<div class="layout-blank-portlet"></div></div>
</td>
</tr>
<tr>
<td valign="top" width="50%" id="td_layout-column_column-2">
<div class="" id="layout-column_column-2">
<c:if test="${not empty columnTwo}">
	<c:forEach var="widget" items="${columnTwo}" varStatus="status">
		<%@ include file="../common/widget.jsp" %>
	</c:forEach>
</c:if>
<div class="layout-blank-portlet"></div></div>
</td>
<td class="layout-column-spacer" width="1%">
<div>&nbsp;</div>
</td>
<td valign="top" width="49%" id="td_layout-column_column-3">
<div class="" id="layout-column_column-3">
<c:if test="${not empty columnThree}">
	<c:forEach var="widget" items="${columnThree}" varStatus="status">
		<%@ include file="../common/widget.jsp" %>
	</c:forEach>
</c:if>
<div class="layout-blank-portlet"></div></div>
</td>
<td class="layout-column-spacer" width="1%">
<div>&nbsp;</div>
</td>
</tr>
<tr>
<td valign="top" colspan="3" id="td_layout-column_column-4">
<div class="" id="layout-column_column-4">
<c:if test="${not empty columnFour}">
	<c:forEach var="widget" items="${columnFour}" varStatus="status">
		<%@ include file="../common/widget.jsp" %>
	</c:forEach>
</c:if>
<div class="layout-blank-portlet"></div></div>
</td>
</tr>
</tbody></table>
</div>
</div>
</div>
<div id="layout-bottom-container"></div>
</div><!-- End layout-box -->
</div>
</div><!-- End layout-outer-side-decoration -->
 <c:if test='${viewmode != "true"}'>
 <script type="text/javascript">
 // <![CDATA[
   function reorderAfterDelete(id) {
   	var params = 'formAction=deletecomponent&widgetId='+id;
   	pwr.sendRequest('changelayout.portal','post', params);
   	
   	reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-1",{name:'columnOne'}));
   	reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-2",{name:'columnTwo'}));
   	reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-3",{name:'columnThree'})); 
   	reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-4",{name:'columnFour'}));

   }
   
   function reorderLayout(url,layout,serparam) {

     var params='viewmode=false&layout='+layout.value+'&formAction=reorder&'+serparam;
     pwr.sendRequest(url,'post', params);
   }
   Sortable.create("layout-column_column-1",
     {dropOnEmpty:true,tag:'div',handle:'portlet-header-bar',hoverclass:'portlet-dragging-placeholder',containment:["layout-column_column-1","layout-column_column-2","layout-column_column-3","layout-column_column-4"],constraint:false,
     	onUpdate:function(){
     	   reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-1",{name:'columnOne'}));
      	}
     });
   Sortable.create("layout-column_column-2",
     {dropOnEmpty:true,tag:'div',handle:'portlet-header-bar',hoverclass:'portlet-dragging-placeholder',containment:["layout-column_column-1","layout-column_column-2","layout-column_column-3","layout-column_column-4"],constraint:false,
     	onUpdate:function(){
     	   reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-2",{name:'columnTwo'}));
      	}
     });
   Sortable.create("layout-column_column-3",
     {dropOnEmpty:true,tag:'div',handle:'portlet-header-bar',hoverclass:'portlet-dragging-placeholder',containment:["layout-column_column-1","layout-column_column-2","layout-column_column-3","layout-column_column-4"],constraint:false,
     	onUpdate:function(){
     	   reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-3",{name:'columnThree'}));
      	}
     });
   Sortable.create("layout-column_column-4",
     {dropOnEmpty:true,tag:'div',handle:'portlet-header-bar',hoverclass:'portlet-dragging-placeholder',containment:["layout-column_column-1","layout-column_column-2","layout-column_column-3","layout-column_column-4"],constraint:false,
     	onUpdate:function(){
     	   reorderLayout('changelayout.portal',document.DashboardCreate.layoutSelection,Sortable.serialize("layout-column_column-4",{name:'columnFour'}));
      	}
     });
   
 // ]]>
 </script>
</c:if>