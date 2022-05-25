<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="login_error.jsp" %>

<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalUtil" %>

<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">

<%
bizManage.setRequest(request);
bizManage.setResponse(response);
bizManage.init(config.getServletContext());

String paramName = null;
String paramValue = null;
	String targetURL = request.getParameter(PortalConstants.DOMAIN_TARGET_URL);
if (targetURL == null)
	targetURL = "myhome/redirect";
	
if ((targetURL.indexOf("?") > 0) && (targetURL.indexOf("=") > 0))
{
	paramName = targetURL.substring(targetURL.indexOf("?")+1, targetURL.indexOf("="));
	paramValue = targetURL.substring(targetURL.indexOf("=")+1);
}
	
	String requestFrom =  (String) request.getAttribute(PortalConstants.REQUEST_FROM);
%>
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="Login" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<jsp:include page="common/include_css.jsp">
	<jsp:param name="login" value="true" />
</jsp:include>
	
<script type="text/javascript" src="javascript/top.js"></script>
	
	<!-- ExtJS LIBS -->
	<script type="text/javascript" src="javascript/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="javascript/ext/ext-all.js"></script>
	<script type="text/javascript" src="javascript/bmencryptutils.js"></script>
	<script type="text/javascript" src="javascript/password.js"></script>
	<!-- ENDLIBS -->
	

<script language="javascript">
<!--

function submitTheForm(theForm){
var isPswrdEncrypted = document.getElementById("<%= PortalConstants.IS_PASSWD_ENCRYPTED %>").value;
 if(isPswrdEncrypted == 'true'){
     encryptPasswdFields(theForm);
 }
 validatePassword(true,document.login.BizPassUserID.value,document.login.currentPassword.value,document.login.<%= PortalConstants.BIZPASS_USER_PASSWD%>.value,isPswrdEncrypted); 
 
}

function checkformvals(theForm){    	
       		if(theForm.currentPassword.value == ''){	    
			showMsgBox('','<sbm:message key="currentPasswordAlert"/>','INFO');
			return false;
         	}
       		if(theForm.<%= PortalConstants.BIZPASS_USER_PASSWD%>.value == ''){
              		showMsgBox('','<sbm:message key="newPasswordAlert"/>','INFO');
              		return false;
          	}
         	   	
          	if(theForm.<%= PortalConstants.CONFIRM_NEW_PASSWD%>.value == ''){
	             showMsgBox('','<sbm:message key="confirmPasswordAlert"/>','INFO');
	             return false;
                }
                if(theForm.<%= PortalConstants.BIZPASS_USER_PASSWD%>.value != theForm.<%= PortalConstants.CONFIRM_NEW_PASSWD%>.value){
                     showMsgBox('','<sbm:message key="confirmPasswordMatchAlert"/>','INFO'); 
                     return false;
                }
                
                return true;
}
	
function setTime()
{
	var today = new Date();
	document.login.timeZone.value = today.getTimezoneOffset();
}
	//-->
</script>

</head>
<body>
<%@ include file="common/include_body.jsp" %>
	<div id="loginDiv" align="center" style="margin-top:125px"></div>
	<div align="center" id="loginContent">
		<form name="login" action="<%= targetURL %>" method="post">
			<input type="hidden" id="<%= PortalConstants.IS_PASSWD_ENCRYPTED %>" name="<%= PortalConstants.IS_PASSWD_ENCRYPTED %>" value="true"/> 
			<input type="hidden" name="timeZone" value=""/>
				<%= ((paramName != null && paramValue != null)?"<input type=\"hidden\" name=\""+paramName+"\" value=\""+paramValue+"\" />":"") %>
   		<table width="640" height="272" border="0" cellpadding="0" cellspacing="0" class="SbmLoginMainDiv" >
        <tr>
        	<td width="320">
        		 <table border="0" cellspacing="0" cellpadding="20">
               <tr>
               <td> &nbsp;</td>
               <td> &nbsp;</td>
               <td class="bpm-portal-logo-image_prog_savv"></td>
        			</tr>
        			<tr>
        			<td> &nbsp;</td>
        			<td> &nbsp;</td>
          		  <td height="35">&nbsp;</td>
              </tr>
               <tr>
               <td> &nbsp;</td>
               <td> &nbsp;</td>
                 <td class="bpm-portal-logo-image_busimgr"></td>
        			</tr>
        			<tr>
        			<td> &nbsp;</td>
        			<td> &nbsp;</td>
          			  <td height="35">&nbsp;</td>
              </tr>
               <tr>
               <td> &nbsp;</td>
               <td> &nbsp;</td>
                 <td class="bpm-portal-logo-image_progsw"></td>
        			</tr>
      			 </table>      			 
  			 </td>
             <td width="360" align="right">
             	 <table width="91%" height="256" border="0" cellpadding="0" cellspacing="0">
        	      
                  <tr>  <td>&nbsp;</td> </tr>
                  <tr>
                     <td>
                     	 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                     	 	<tr>
                     	 
                     	 	 <td height="30" colspan="2" class="SbmInfoMessage">
	            							<%if(PortalConstants.LOGIN_ERROR.equals(requestFrom)) {
			       	  					    if(request.getParameter("BizPassErrorMsg").contains("PW16096")){ %>
			       	  					        <sbm:message key="passwordExpired"/>
			       	  					    <%}
			       	  						
			       	  					} else {
			       	  					%>	
  										      <sbm:message key="EnterLogin" />
		       	  						<%
			       	  					}
		       	  						%>
		       	  						</td>
                        
                        </tr> 
			              	  
			              	  <tr>
                        <td height="30" nowrap="nowrap">
	                            <div align="right"><span class="bpm-portal-login-text"><label for="txtBizPassUserID"><sbm:message key="username" />&nbsp;&nbsp;</label></span>
	                                <input type = "hidden" name = "BizPassUserID" value = "<%=request.getParameter(PortalConstants.BIZPASS_USER_ID)%>">
                              </div>
	                       </td>
	                       <td> <div id="txtBizPassUserID" class="SbmInfoMessage"> <%=request.getParameter(PortalConstants.BIZPASS_USER_ID)%></div> </td>
                         </tr>
	                      <tr>
                          <td height="30" nowrap="nowrap">
	                           <div align="right"><span class="bpm-portal-login-text"><label for="txtBizPassUserPassword"><sbm:message key="password" />&nbsp;&nbsp;</label></span>
                             </div>
	                        </td>
	                       <td> <input type="password"  name="<%=PortalConstants.CURRENT_PASSWD%>" id="txtCurrUserPassword" onFocus="setTime()" class="InptTxt" size="32" maxlength="255" tabindex="2"> </td>     
	                      </tr>
	                       <tr>
                           <td height="30" nowrap="nowrap">
	                             <div align="right"><span class="bpm-portal-login-text"><label for="txtBizPassUserPassword"><sbm:message key="newPassword" />&nbsp;&nbsp;</label></span>
                               </div>
	                          </td>
	                          <td> <input type="password"  name="<%= PortalConstants.BIZPASS_USER_PASSWD %>" id="txtBizPassUserPassword" onFocus="setTime()" class="InptTxt" size="32" maxlength="255" tabindex="2"> </td>
	                       </tr>
	                        <tr>
                           <td height="30" nowrap="nowrap">
	                            <div align="right"><span class="bpm-portal-login-text"><label for="txtBizPassUserPassword"><sbm:message key="confNewPassword" />&nbsp;&nbsp;</label></span>
                              </div>
	                        </td>
	                        <td> <input type="password"  name="<%=PortalConstants.CONFIRM_NEW_PASSWD%>" id="txtConfNewUserPassword" onFocus="setTime()" class="InptTxt" size="32" maxlength="255" tabindex="2"> </td>   
	                       </tr>
	                       <input type = "hidden" name = "<%=PortalConstants.PASSWD_EXPIRED%>" value = "true">
	                      <tr>
	                       <td> &nbsp; </td>
	                        <td valign="right" class="SBMButton" nowrap="nowrap" height="30">
	                            <div align="right"><input type="button" name="login" class="ResultBtn" tabindex="3" onMouseOver="this.className='ResultBtnHover';" onMouseOut="this.className='ResultBtn';" onClick="if(checkformvals(document.login))submitTheForm(document.login);" value="<sbm:message key="Login" />" />  </div>
	                       </td>
	                        
	                      </tr>
	                      
	                   </table>
                  </td>
               </tr>
               <tr>
                 <td>
        	       <table>
                       <tr>
                       <td align="left" width="85%" valign="bottom">
			                   <div class="bpm-portal-login-footer"><sbm:message key="copyrightMessage" /></div>
                      </td>
                      <td> &nbsp; </td>
                      <td align="right"valign="bottom" style="padding-right:10px">                   
                          <div><a href="javascript:goToSupportFromLogin()" tabindex="3"><span class="bpm-portal-login-footer-support"><sbm:message key="support" /></span></a></div>
                      </td>
                       </tr>
                  </table>
                </td>
              </tr>
       </table>
		</form>
	</div>

	
</body>
</html>
<% session.invalidate(); %>
