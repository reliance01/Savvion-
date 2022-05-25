<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 

<%@ page import=" java.io.*" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

  <c:set var="isError" value="${isError}" />
  <c:set var="errMsg" value="${errorMessage}" />
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
    	<td class="SegFieldCntr">
    	<c:choose>
    		<c:when test="${errorMessage == 'NO_APPLICATION_INSTALLED'}">
    			<sbm:message key="NO_APPLICATION_INSTALLED"/>
    		</c:when>
    		<c:otherwise>
    			<c:out value="${errorMessage}" />
    		</c:otherwise>
    	</c:choose>
    	</td>
    </tr>
    <tr>
    	<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="SegLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2"></td>
        </tr>
      	</table></td>
    </tr>
  </table>
