<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConfig"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="SbmFilterTableComponent" align="center">
    <tr>
      <c:choose>
        <c:when test='${scorecardType =="kpi"}'>
          <td nowrap><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.KpiName"/>:</b></td>
          <td nowrap><c:out value="${kpiName}"/></td>         
          <td><img src="<%=request.getContextPath()%>/bpmportal/css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
          <td><b >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="Score"/>&nbsp;:&nbsp;<c:out value="${kpiScore}"/></b></td>
          <td nowrap>|</td>
          <td align="right" nowrap><a href="<%=request.getContextPath()%>/bpmportal/management/bal_history.jsp?bscname=<c:out value="${encodedbsc}"/>&perspname=<c:out value="${encodedpersp}"/>&kpiname=<c:out value="${encodedkpi}"/>"><sbm:message key="BPM_Portal_Dashboard.ScoreHistory"/></a></td>
          <td width="100%">&nbsp;</td>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test='${scorecardType =="perspective"}'>
                  <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.PerspectiveName"/>:</b></td>
                  <td><c:out value="${perspectiveName}"/></td>
                   <td><img src="<%=request.getContextPath()%>/bpmportal/css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
                  <td><b >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="Score"/>&nbsp;:&nbsp;<c:out value="${perspectiveScore}"/></b></td>
                  <td nowrap>&nbsp;</td>
                  <td nowrap>&nbsp;</td>
                  <td width="100%">&nbsp;</td>
                </c:when>
                <c:otherwise>
                  <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.ScorecardName"/>:</b></td>
                  <td><c:out value="${scorecardName}"/></td>
                  <td><img src="<%=request.getContextPath()%>/bpmportal/css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
                  <td><b >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="Score"/>&nbsp;:&nbsp;<c:out value="${scorecardScore}"/></b></td>
                  <td nowrap>|</td>
                  <td nowrap><a href="<%=request.getContextPath()%>/bpmportal/management/bal_history.jsp?bscname=<c:out value="${encodedbsc}"/>"><sbm:message key="BPM_Portal_Dashboard.ScoreHistory"/></a></td>
                  <td width="100%">&nbsp;</td>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
      </c:choose>
    </tr>
  </table>
  <%StringBuilder chartXml = new StringBuilder("<Chart upperLimit='10' lowerLimit='0' majorTMNumber='11' majorTMHeight='9' minorTMNumber='5' autoScale='1' origW='270' origH='200'");
    chartXml.append("   minorTMColor='000000' minorTMHeight='3' pivotRadius='6' majorTMThickness='2' showBorder='0' gaugeInnerRadius='30' gaugeOuterRadius='90' bgColor='FFFFFF'");
    chartXml.append("   gaugeOriginX='135' gaugeOriginY='150' gaugeScaleAngle='180' gaugeAlpha='60' decimalPrecision='0'");
    chartXml.append("   displayValueDistance='15' decimalPrecision='1'  tickMarkDecimalPrecision='0' hoverCapBgColor='F2F2FF' hoverCapBorderColor='6A6FA6'> ");
    String cXml = chartXml.toString();
  %>  
  <div class="SbmKpiSmallTableDiv SbmKpiContentCell">
    <table border="0" cellpadding="2" cellspacing="2" align="center">
      <tr>
      <c:choose>
    <c:when test='${scorecardType == "kpi"}'>
        <td align="center" valign="middle">     
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="<%=PortalConfig.self().getConfig().getFlashCodebaseURL()%>" name="Angular" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.height}"/>' id="Angular">
        <param name="movie" value='<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=<c:out value="${dashboardWidget.widget.width}"/>&amp;chartHeight=<c:out value="${dashboardWidget.widget.height}"/>'>
        <param name="FlashVars" value="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='<c:out value="${targetScore}"/>' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='<c:out value="${targetScore}"/>' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${kpiScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='105'/></dials></Chart>">
        <param name="quality" value="high">
        <param name="WMode" value="Transparent">
        <embed src='<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=<c:out value="${dashboardWidget.widget.width}"/>&amp;chartHeight=<c:out value="${dashboardWidget.widget.height}"/>' FlashVars="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='<c:out value="${targetScore}"/>' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='<c:out value="${targetScore}"/>' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${kpiScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='80'/></dials></Chart>" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.width}"/>' quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" name="Angular" swLiveConnect="true" allowScriptAccess="true" wmode="transparent">
        </embed>
                </object>
        </td>
        <td align="left" valign="middle"><table border="0" cellpadding="0" cellspacing="0">
            <tbody >
                <c:if test="${weightDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="Weight"/>: </b></td>
                <td valign="middle" ><c:out value="${weight}"/></td>
                  </tr>
                </c:if>
                <c:if test="${currentValueDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="CurrentValue"/>: </b></td>
                <td valign="middle" ><c:out value="${currentValue}"/>&nbsp;<c:out value="${unit}"/></td>
                  </tr>
                </c:if>
                <c:if test="${bestValueDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="BestValue"/>: </b></td>
                <td valign="middle" ><c:out value="${bestValue}"/>&nbsp;<c:out value="${unit}"/></td>
                  </tr>
                </c:if>
                <c:if test="${worstValueDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="WorstValue"/>: </b></td>
                <td valign="middle" ><c:out value="${worstValue}"/>&nbsp;<c:out value="${unit}"/></td>
                  </tr>
                </c:if>
                <c:if test="${scoreDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="Score"/>&nbsp;:&nbsp; </b></td>
                <td valign="middle" ><c:out value="${kpiScore}"/></td>
                  </tr>
                </c:if>
                <c:if test="${targetScoreDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="TargetScore"/>: </b></td>
                <td valign="middle" ><c:out value="${targetScore}"/></td>
                  </tr>
                </c:if>
                <c:if test="${descriptionDisplay == true}">
                  <tr  >
                <td align="right" valign="middle" nowrap><b><sbm:message key="description"/>: </b></td>
                <td valign="middle" ><c:out value="${description}"/></td>
                  </tr>
                </c:if>
            </tbody>
        </table></td>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test='${scorecardType =="perspective"}'>
                <td align="center" valign="middle">
                 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="<%=PortalConfig.self().getConfig().getFlashCodebaseURL()%>" name="Angular" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.height}"/>' id="Angular">
                 <param name="movie" value='<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=<c:out value="${dashboardWidget.widget.width}"/>&amp;chartHeight=<c:out value="${dashboardWidget.widget.height}"/>'>
                 <param name="FlashVars" value="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='8' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='8' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${perspectiveScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='105'/></dials></Chart>">
                 <param name="quality" value="high">
                 <param name="WMode" value="Transparent">
                 <embed src='<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=<c:out value="${dashboardWidget.widget.width}"/>&chartHeight=<c:out value="${dashboardWidget.widget.height}"/>' FlashVars="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='8' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='8' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${perspectiveScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='80'/></dials></Chart>" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.height}"/>' quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" name="Angular" swLiveConnect="true" allowScriptAccess="true" wmode="transparent">
                 </embed>
                         </object>
                </td>
            </c:when>
            <c:otherwise>
                <td align="center" valign="middle">
                 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="<%=PortalConfig.self().getConfig().getFlashCodebaseURL()%>" name="Angular" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.height}"/>' id="Angular">
                 <param name="movie" value="<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=270&amp;chartHeight=150">
                 <param name="FlashVars" value="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='8' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='8' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${scorecardScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='105'/></dials></Chart>">
                 <param name="quality" value="high">
                 <param name="WMode" value="Transparent">
                 <embed src='<%=request.getContextPath()%>/bpmportal/FusionCharts/AngularGauge.swf?chartWidth=<c:out value="${dashboardWidget.widget.width}"/>&chartHeight=<c:out value="${dashboardWidget.widget.height}"/>' FlashVars="&dataXML=<%=cXml%><colorRange><color minValue='0' maxValue='2' code='E95D0F' alpha='60' borderColor='EFEFEF'/> <color minValue='2' maxValue='8' code='FDC12E' alpha='60' borderColor='EFEFEF'/><color minValue='8' maxValue='10' code='00B900' alpha='60' borderColor='EFEFEF'/> </colorRange><dials><dial value='<c:out value="${scorecardScore}"/>' bgColor='444444' borderAlpha='0' baseWidth='6' topWidth='1' radius='80'/></dials></Chart>" width='<c:out value="${dashboardWidget.widget.width}"/>' height='<c:out value="${dashboardWidget.widget.height}"/>' quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" name="Angular" swLiveConnect="true" allowScriptAccess="true" wmode="transparent">
                 </embed>
                         </object>
                </td>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
      </c:choose>
      </tr>
    </table>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="SbmFilterTableComponent" align="center">
    <tr>
      <c:choose>
        <c:when test='${scorecardType =="kpi"}'>
          <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.ScorecardName"/>:</b></td>
          <td nowrap><a href="<%=request.getContextPath()%>/bpmportal/management/bal_score_det.jsp?bscname=<c:out value="${scorecardName}"/>"><c:out value="${scorecardName}"/></a></td>
          </td>
          <td><img src="<%=request.getContextPath()%>/bpmportal/css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
          <td><b >&nbsp;&nbsp;&nbsp;<sbm:message key="BPM_Portal_Dashboard.PerspectiveName"/> </b></td>
          <td nowrap><a href="<%=request.getContextPath()%>/bpmportal/management/perspective_det.jsp?bscname=<c:out value="${scorecardName}"/>&perspname=<c:out value="${perspectiveName}"/>"><c:out value="${perspectiveName}"/></a></td>
          <td width="100%">&nbsp;</td>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test='${scorecardType =="perspective"}'>
                  <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.ScorecardName"/>:</b></td>
                  <td nowrap><a href="<%=request.getContextPath()%>/bpmportal/management/bal_score_det.jsp?bscname=<c:out value="${scorecardName}"/>"><c:out value="${scorecardName}"/></a></td>
                  <td width="100%">&nbsp;</td>
                </c:when>
                <c:otherwise>
                  <td width="100%">&nbsp;</td>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
      </c:choose>
    </tr>
  </table>
