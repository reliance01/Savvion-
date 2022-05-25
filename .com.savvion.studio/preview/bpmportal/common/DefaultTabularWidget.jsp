<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import=" java.io.*" %>
<%@ page import="java.util.*"%>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="com.savvion.sbm.bpmportal.service.DashboardService"%>
<%@ page import="com.savvion.sbm.bpmportal.bizsite.util.StringUtil"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td class="TblBgDark">
        <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="table1" name="table1">
          <tr>
            <c:forEach var="column" items="${columns}">
                <td class="ColHdr"><c:out value="${column.key}"/></td>
            </c:forEach>
          </tr>
          <c:if test="${not empty rows}" >
            <c:forEach var="rowMap" items="${rows}" varStatus="status">
                <tr>
            <c:forEach var="column" items="${columns}">
                <c:set var="columnurl" value="${urlmap[column.key]}"/>
                <c:set var="columnvalue" value="${rowMap[column.key]}"/>
                <c:set var="drilldowntarget" value="${DRILLDOWN_MULTIPLE_URL['__TARGET__']}"/>
                <c:set var="inline" value="inline"/>
                <%
                    String columnValue = String.valueOf(pageContext.getAttribute("columnvalue"));
                    String strUrl = (String)pageContext.getAttribute("columnurl");
                    if(strUrl != null && strUrl.indexOf("#PARAM#") != -1) {
                        strUrl = com.savvion.sbm.bpmportal.bizsite.util.StringUtil.replaceString(strUrl,"#PARAM#",columnValue);
                        pageContext.setAttribute("columnurl",strUrl);
                        if(strUrl.indexOf("/") == 0) {
                            pageContext.setAttribute("columnurl",request.getContextPath()+strUrl);
                        } 
                    }
                %>
                <c:choose>
                    <c:when test="${status.index %2 == 0}">
                        <td class="ValCntr">
                        <c:choose>
                            <c:when test="${not empty columnurl}">
                                <c:choose>
                                    <c:when test="${inline eq drilldowntarget}">
                                        <a href="<c:out value="${columnurl}"/>" class="TblLnk"><c:out value="${rowMap[column.key]}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:manageDrillDown('<c:out value='${columnurl}'/>','<c:out value='${drilldowntarget}'/>')" class="TblLnk"><c:out value="${rowMap[column.key]}"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${rowMap[column.key]}"/>
                            </c:otherwise>
                        </c:choose>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class="ValCntrAlt">
                        <c:choose>
                            <c:when test="${not empty columnurl}">
                                <c:choose>
                                    <c:when test="${inline eq drilldowntarget}">
                                        <a href="<c:out value="${columnurl}"/>" class="TblLnk"><c:out value="${rowMap[column.key]}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:manageDrillDown('<c:out value='${columnurl}'/>','<c:out value='${drilldowntarget}'/>')" class="TblLnk"><c:out value="${rowMap[column.key]}"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${rowMap[column.key]}"/>
                            </c:otherwise>
                        </c:choose>
                        </td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            </tr>
            </c:forEach>
          </c:if>
        </table>
      </td>
    </tr>
  </table>
