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
<%! String __webAppName = "ResumeSuspendedItems"; %>
<%! int res=-10; %>
<bizsolo:ifCrtWS name="Start" isDefault="true" >
<bizsolo:initApp name="ResumeSuspendedItems" />
<bizsolo:getParentData />
<bizsolo:if test="<%=request.getParameter(\"workitemName\")!=null %>" >
<bizsolo:executeAction wsName="" epClassName="com.savvion.BizSolo.beans.PAKGetDS" perfMethod="commit" />
</bizsolo:if>
<bizsolo:checkSecure />
<bizsolo:redirectURL page="Start.jsp?crtApp=ResumeSuspendedItems&crtPage=Get_All_Suspended_Items" />
</bizsolo:ifCrtWS>



<%long processTemplateID=bean.getPropLong("processTemplateID");
Map suspendedItems=bean.getPropMap("suspendedItems");
long suspendedItemsCount=bean.getPropLong("suspendedItemsCount");
long Count=bean.getPropLong("Count");
String WSName=bean.getPropString("WSName");
%>




<bizsolo:ifCrtWS name="ResumeSuspendedItems" >
<bizsolo:choose >
<bizsolo:when test="<%=\"procReq\".equals(request.getParameter(\"activityMode\")) %>" >

<bizsolo:choose >
<bizsolo:when test="<%=\"Resume All\".equals(request.getParameter(\"SB_Name\")) || \"1434800942\".equals(request.getParameter(\"SB_Name\")) %>" >
<bizsolo:forwardURL page="Start.jsp?crtApp=ResumeSuspendedItems&crtPage=Resume_All_Suspended_Items" />
</bizsolo:when>
</bizsolo:choose>
</bizsolo:when>
<bizsolo:otherwise >
<bizsolo:redirectURL page="ResumeSuspendedItems.jsp" />
</bizsolo:otherwise>
</bizsolo:choose>
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="End" >
<bizsolo:transferDS />
<bizsolo:setParentData />
<% /* Workaround, retAddr will disappear in the future */ %>
<% String retAddr = bean.getPropString("returnPage"); %>
<% if (bean.getPropString(PublicResources.MODE) == null && bean.getPropString(PublicResources.IS_BIZSOLO_SUBPROCESS)==null)
        session.setAttribute(VirtualSessionManager.ATTR_NEED_CLEANUP, "true");%><% if(retAddr != null) { %>
  <bizsolo:redirectURL page="<%= retAddr %>" />
<% } %>
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="Get_All_Suspended_Items" >
<bizsolo:executeAction wsName="Get All Suspended Items" epClassName="com.rgi.savvion.techdesk.GetAllSuspendedItems" perfMethod="commit" dsi="processTemplateID@processTemplateID,suspendedItems@suspendedItems,suspendedItemsCount@suspendedItemsCount,Count@Count,WSName@WSName" dso="processTemplateID@processTemplateID,suspendedItems@suspendedItems,suspendedItemsCount@suspendedItemsCount,Count@Count,WSName@WSName" />
<bizsolo:forwardURL page="Start.jsp?crtApp=ResumeSuspendedItems&crtPage=ResumeSuspendedItems" />
</bizsolo:ifCrtWS>



<bizsolo:ifCrtWS name="Resume_All_Suspended_Items" >
<bizsolo:executeAction wsName="Resume All Suspended Items" epClassName="com.rgi.savvion.techdesk.ResumeAllSuspendedItems" perfMethod="commit" dsi="processTemplateID@processTemplateID" dso="processTemplateID@processTemplateID" />
<bizsolo:forwardURL page="Start.jsp?crtApp=ResumeSuspendedItems&crtPage=End" />
</bizsolo:ifCrtWS>
