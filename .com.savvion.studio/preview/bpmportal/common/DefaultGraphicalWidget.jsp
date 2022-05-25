<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import=" java.io.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.savvion.sbm.bpmportal.util.DashboardConstants"%>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConfig"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<%
    PrintWriter pw = new PrintWriter((Writer) out);
    
    StringBuilder strBuf = (StringBuilder)request.getAttribute("chart");
    
    String chartWidth = (String)request.getAttribute("chartWidth");
    
    String chartHeight = (String)request.getAttribute("chartHeight");
    
    Properties jspProps = (Properties)request.getAttribute("properties");
    
    String type = (String)request.getAttribute("chartType");
    
    long dashboardWidgetId = (Long)request.getAttribute("dashboardWidgetId");
    
    String swfFileName = "";
    
    if(DashboardConstants.PIE2D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/Pie2D.swf";
    } else if(DashboardConstants.PIE3D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/Pie3D.swf";
    } else if(DashboardConstants.AREA_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/MSArea.swf";
    } else if(DashboardConstants.HORIZONTALBAR2D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/MSBar2D.swf";
    } else if(DashboardConstants.HORIZONTALBAR3D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/MSBar2D.swf";
    } else if(DashboardConstants.VERTICALBAR2D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/MSColumn2D.swf";
    } else if(DashboardConstants.VERTICALBAR3D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/MSColumn3D.swf";
    } else if(DashboardConstants.LINE2D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/Line.swf";
    } else if(DashboardConstants.LINE3D_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/Line.swf";
    }  else if(DashboardConstants.METER_CHART_TYPE.equals(type)) {
        swfFileName = "../FusionCharts/AngularGauge.swf";
    } else if(DashboardConstants.FUNNEL_CHART_TYPE.equals(type)) {
            swfFileName = "../FusionCharts/Funnel.swf";
    } else if(DashboardConstants.PYRAMID_CHART_TYPE.equals(type)) {
            swfFileName = "../FusionCharts/Pyramid.swf";
    } else if(DashboardConstants.DOUGHNUT2D_CHART_TYPE.equals(type)) {
            swfFileName = "../FusionCharts/Doughnut2D.swf";
    } else if(DashboardConstants.DOUGHNUT3D_CHART_TYPE.equals(type)) {
            swfFileName = "../FusionCharts/Doughnut3D.swf";
    } else if(DashboardConstants.BAR3DLINE_CHART_TYPE.equals(type)) {
            swfFileName = "../FusionCharts/MSColumnLine3D.swf";
    }     
%>
<div align="center">
  <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
    codebase="<%=PortalConfig.self().getConfig().getFlashCodebaseURL()%>" 
        width="<%=chartWidth%>" height="<%=chartHeight%>" id="<%=dashboardWidgetId%>" type="application/x-shockwave-flash">
        <param name="movie" value="<%=swfFileName%>?chartWidth=<%=chartWidth%>&chartHeight=<%=chartHeight%>" />
        <param name="FlashVars" value="&dataXML=<%=strBuf.toString()%>" >
        <param name="quality" value="high" />
        <param name="wmode" value="transparent"> 
      <embed src="<%=swfFileName%>?chartWidth=<%=chartWidth%>&chartHeight=<%=chartHeight%>" 
        flashVars="&dataXML=<%=strBuf.toString()%>" quality="high" 
        width="<%=chartWidth%>" height="<%=chartHeight%>" name="<%=dashboardWidgetId%>" type="application/x-shockwave-flash" wmode="transparent" 
        pluginspage="http://www.macromedia.com/go/getflashplayer" />
  </object>
<div>
