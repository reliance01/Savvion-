<%@page import="com.savvion.BizSolo.Server.*,com.savvion.BizSolo.beans.*,java.util.*" contentType="text/html;charset=UTF-8"%>
<%@page import="com.savvion.sbm.bizsolo.util.session.*,java.net.*"%>
<%! String fid = null; %>
<% 
		if (VirtualSessionManager.isEnabled()) {
		   fid = request.getParameter(BizSoloRequest.BSS_FIID);
			if (fid == null) {
				response.sendRedirect(VirtualSessionManager.makeItselfSessionURL(request));
				return;
		}
		 }
%>
<%@page errorPage="/BizSolo/common/jsp/expcontroller.jsp" %>
<%@taglib uri="/BizSolo/common/tlds/bizsolo.tld" prefix="bizsolo"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<jsp:useBean id="bean" class="com.savvion.BizSolo.beans.Bean" scope="session"/>
<jsp:useBean id="factoryBean" class="com.savvion.BizSolo.beans.EPFactoryBean" scope="session"/>
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>

<%! String _PageName = "Start"; %>
<%! String __webAppName = "TechDeskSuspendedItems"; %>
<%! int res=-10; %>
<bizsolo:ifCrtWS name="Start" isDefault="true" >
<bizsolo:initApp name="TechDeskSuspendedItems" />
<bizsolo:getParentData />
<bizsolo:if test="<%=request.getParameter(\"workitemName\")!=null %>" >
<bizsolo:executeAction wsName="" epClassName="com.savvion.BizSolo.beans.PAKGetDS" perfMethod="commit" />
</bizsolo:if>
<bizsolo:checkSecure />
<bizsolo:redirectURL page="Start.jsp?crtApp=TechDeskSuspendedItems&crtPage=GetSuspendedWS" />
</bizsolo:ifCrtWS>



<%Map suspendedItems=bean.getPropMap("suspendedItems");
long Count=bean.getPropLong("Count");
long processTemplateID=bean.getPropLong("processTemplateID");
long suspendedItemsCount=bean.getPropLong("suspendedItemsCount");
String WSName=bean.getPropString("WSName");
%>




<bizsolo:ifCrtWS name="ViewSuspendedItems" >
<bizsolo:choose >
<bizsolo:when test="<%=\"procReq\".equals(request.getParameter(\"activityMode\")) %>" >

<bizsolo:choose >
<bizsolo:when test="<%=\"Connection 3\".equals(request.getParameter(\"SB_Name\")) || \"2087425937\".equals(request.getParameter(\"SB_Name\")) %>" >
<bizsolo:forwardURL page="Start.jsp?crtApp=TechDeskSuspendedItems&crtPage=ResumeSuspendedWS" />
</bizsolo:when>
</bizsolo:choose>
</bizsolo:when>
<bizsolo:otherwise >
<bizsolo:redirectURL page="ViewSuspendedItems.jsp" />
</bizsolo:otherwise>
</bizsolo:choose>
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="End_1" >
<bizsolo:transferDS />
<bizsolo:setParentData />
<% /* Workaround, retAddr will disappear in the future */ %>
<% String retAddr = bean.getPropString("returnPage"); %>
<% if (bean.getPropString(PublicResources.MODE) == null && bean.getPropString(PublicResources.IS_BIZSOLO_SUBPROCESS)==null)
        session.setAttribute(VirtualSessionManager.ATTR_NEED_CLEANUP, "true");%><% if(retAddr != null) { %>
  <bizsolo:redirectURL page="<%= retAddr %>" />
<% } %>
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="GetSuspendedWS" >
<bizsolo:executeAction wsName="GetSuspendedWS" epClassName="com.ap.techdesk.suspendedinstances.getSuspendedWS" perfMethod="commit" dsi="Count@Count,processTemplateID@processTemplateID,suspendedItems@suspendedItems,suspendedItemsCount@suspendedItemsCount,WSName@WSName" dso="Count@Count,processTemplateID@processTemplateID,suspendedItems@suspendedItems,suspendedItemsCount@suspendedItemsCount,WSName@WSName" />
<bizsolo:forwardURL page="Start.jsp?crtApp=TechDeskSuspendedItems&crtPage=ViewSuspendedItems" />
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="ResumeSuspendedWS" >
<bizsolo:executeAction wsName="ResumeSuspendedWS" epClassName="com.ap.techdesk.suspendedinstances.resumeSuspendedWS" perfMethod="commit" dsi="processTemplateID@processTemplateID" dso="processTemplateID@processTemplateID" />
<bizsolo:forwardURL page="Start.jsp?crtApp=TechDeskSuspendedItems&crtPage=End_1" />
</bizsolo:ifCrtWS>
