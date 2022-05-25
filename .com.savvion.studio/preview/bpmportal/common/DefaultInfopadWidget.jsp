<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import=" java.io.*" %>
<%@ page import="java.util.*"%>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="com.savvion.sbm.bpmportal.service.DashboardService"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="SbmFilterTableComponent">
    <tr>
      <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.ComponentName"/>:</b></td>
      <td nowrap="nowrap"><c:out value="${dashboardWidget.dashboardWidgetName}"/></td>
      <td><div class="SbmSummaryHelp" onmouseover="this.className='SbmSummaryHelpHover';" onmouseout="this.className='SbmSummaryHelp';"><a href="#"></a><div>
      <c:out value="${dashboardWidget.dashboardWidgetDesc}"/>
      </div>
      </div><!--close div.SbmSummaryHelp-->
      </td>
      <td><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
      <td><b >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="BPM_Portal_Dashboard.Process" />:</b></td>
      <td nowrap="nowrap"><c:out value="${dashboardWidget.dashboardWidgetAppName}"/></td>
      <td width="100%">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="infopadAppName" value="<c:out value="${infopadAppName}"/>" />
  <input type="hidden" name="moduleName" value="<c:out value="${moduleName}"/>" />
  <input type="hidden" name="infopadName" value="<c:out value="${infopadName}"/>" />
  <input type="hidden" name="rowcol" value="<c:out value="${rowcol}"/>" />
  <c:choose>
  	<c:when test="${isRowSliceOn == true}">
  		<input type="hidden" name="sliceon" value="row" />
  	</c:when>
  	<c:otherwise>
  		<input type="hidden" name="sliceon" value="column" />
  	</c:otherwise>
  </c:choose>
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="SegTblBgDark"> <table border=0 cellpadding=4 cellspacing=0 width=100% class="SegTableCellPad" name="infopadtable" id="infopadtable">
          <tbody>
            <tr>
              <td rowspan="2" class="SegColHdr"><sbm:message key="slotnames" /></td>
              <c:choose>
              	<c:when test="${isRowSliceOn}" >
              		<td colspan="<c:out value="${colsize}"/>" class="SegColHdr"><sbm:message key="Column" /></td>
              	</c:when>
              	<c:otherwise>
              		<td colspan="<c:out value="${rowsize}"/>" class="SegColHdr"><sbm:message key="Row" /></td>
              	</c:otherwise>
              </c:choose>
            </tr>
            <tr>
            	<c:choose>
            		<c:when test="${isRowSliceOn}">
            			<c:if test="${not empty columns}" >
            				<c:forEach var="column" items="${columns}">
            					<td class="SegColHdr"><c:out value="${column}"/></td>
            				</c:forEach>
            			
            			</c:if>
            		</c:when>
            		<c:otherwise>
            			<c:if test="${not empty rows}" >
            				<c:forEach var="row" items="${rows}">
            					<td class="SegColHdr"><c:out value="${row}"/></td>
            				</c:forEach>
            			
            			</c:if>
            		</c:otherwise>
            	</c:choose>
            </tr>
            <c:if test="${not empty slotMap}">
            	<c:forEach var="slot" items="${slotMap}" varStatus="status" >
            	<tr>
            		<c:choose>
            			<c:when test="${status.index % 2 == 0}">
            				<c:set var="SegFieldCntrClass" value="SegFieldCntr" />
            				<td class="SegFieldLft"><c:out value="${slot.key}"/></td>
            			</c:when>
            			<c:otherwise>
            				<c:set var="SegFieldCntrClass" value="SegFieldCntrAlt" />
            				<td class="SegFieldLftAlt"><c:out value="${slot.key}"/></td>
            			</c:otherwise>
            		</c:choose>
			<c:choose>
				<c:when test="${isRowSliceOn}">
					<c:if test="${not empty columns}" >
						<c:forEach var="column" items="${columns}">
							<td class="<c:out value="${SegFieldCntrClass}"/>"><input type="text" class="InptTxt" name="slot_<c:out value="${rowcol}"/>_<c:out value="${column}"/>_<c:out value="${slot.key}"/>" size="10" value="<c:out value="${slot.value[column]}"/>" /></td>
						</c:forEach>

					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty rows}" >
						<c:forEach var="row" items="${rows}">
							<td class="<c:out value="${SegFieldCntrClass}"/>"><input type="text" class="InptTxt" name="slot_<c:out value="${row}"/>_<c:out value="${rowcol}"/>_<c:out value="${slot.key}"/>" size="10" value="<c:out value="${slot.value[row]}"/>" /></td>
						</c:forEach>

					</c:if>
				</c:otherwise>
			</c:choose>
            	</tr>
            	</c:forEach>
            </c:if>
          </tbody>
        </table></td>
    </tr>
    <tr>
    	<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="SegLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2"></td>
        </tr>
      	</table></td>
    </tr>
    <tr>
    	<td><table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
        <tr align="center">
          <td class="ButtonDarkBg">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="BtnSpace">
                  <input type="button" name="save" value=" <sbm:message key="save" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="SaveInfopadData(document.forms[0],'p_p_content_<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>');" />
                </td>
                <td class="BtnSpace">
                  <input type="button" name="resetBtn" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="ResetInfopadData(document.forms[0],'p_p_content_<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>');" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
    </tr>
  </table>
