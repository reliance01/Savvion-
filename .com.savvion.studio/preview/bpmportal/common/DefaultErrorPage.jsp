<%@ page import="com.savvion.sbm.bpmportal.util.*" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<% 	
    String errorCode = null;
	String errorMessage = null;
    
	if (exception instanceof PortalException) {
        	PortalException bex =  (PortalException) exception;
        	errorCode = bex.getMessageID();
        	errorMessage = bex.getMessage();
	} else {
        	errorCode = exception.toString();
        	errorMessage = exception.getMessage();
	}
%>


<div>
  <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
    <tr> 
      <td class="CmnTblEmboss" align="center" valign="top"> 
        <table border="0" width="100%" cellpadding="6">
          <tr> 
            <td class="ValLft" width="92%"><span class="Error"><b><sbm:message key="typeLabel" />: </b> <sbm:message key="homePageError" /> <br>
              <b><sbm:message key="messageLabel" />:</b> <%=errorCode%> <br>
              <b><sbm:message key="descLabel" />:</b> <%=errorMessage%> </span></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
