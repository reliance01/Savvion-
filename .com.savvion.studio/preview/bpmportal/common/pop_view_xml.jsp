<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.XML"%>
<%@ page import="com.savvion.sbm.bizlogic.util.Session"%>
<%@ page import="com.savvion.sbm.bpmportal.fileUpload.service.*"%>
<%@ page import="com.savvion.sbm.bpmportal.fileUpload.handlers.*"%>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<jsp:useBean id="bizSite" class="com.savvion.sbm.bpmportal.bizsite.api.BizSiteBean" scope="session"/>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session"/>
<%
String xmlid = "";
String xmlContent = "";
String encodedTaskName = (String)request.getParameter("task");
String encodedDsName = (String)request.getParameter("ds");
String appType = request.getParameter("appType");
String docType = request.getParameter("docType");
String refKey = request.getParameter("refKey");
String sessionId = request.getSession(false).getId();
xmlid = (String)request.getParameter("xmlid");
if(xmlid == null){
	xmlid = (String)session.getValue(encodedTaskName+"_"+encodedDsName);
}
if(xmlid != null){
Session blSess = bizSite.getBizlogicSession();
long lVal = -1;
try {
    lVal = Long.valueOf(xmlid);
} catch(Exception e) { } 
XML xmlDoc = new XML(lVal);
xmlContent = xmlDoc.getContent(blSess);
}else if(appType != null && docType != null){
    AbstractFileHandler handler = (AbstractFileHandler)FileManager.getHandler(appType,docType);
    if(handler != null){
	    xmlContent = ((AbstractXMLFileHandler)handler).getContents(sessionId,refKey);
    }
}
%>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<html>
<head>
<title><sbm:message key="ViewXML" /> -  <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript">
</script>
</head>
<body>
<div align="center"> 
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr> 
      <td> 
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="PopTitle"><sbm:message key="ViewXML" /></td>
          </tr>
          <tr> 
            <td class="PopTblEmboss" align="center"> <table width="100%" cellpadding="6" cellspacing="2">
                <tr> 
                  <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td class="SegTblInBg"> <table border=0 cellpadding=4 cellspacing=1 width=100% Class="SegDataTbl">
                            <tbody>
                              <tr> 
                                <td valign="top" class=SegValCntr> 
                                  <table border="0">
                                    <tr> 
                                      <td class="DataLabel" valign="top" width="20%"><sbm:message key="XMLFile" />:</td>
                                      <td class="DataValue"> <textarea name="textfield" cols="100" rows="30" wrap="VIRTUAL"  class="InptSelect" readonly><%=(xmlContent != null) ? xmlContent : ""%></textarea> 
                                      </td>
                                    </tr>
                                  </table></td></tr>
                              <tr> 
                                <td class=ButtonBarBg> <table border="0" cellspacing="0" cellpadding="0" align="center">
                                    <tr>
                                      <td class="ButtonBar"> <input type="submit" name="Submit2223322" value="<sbm:message key="close" />"   class="PopButton" onMouseOver="this.className='PopButtonHover';" onMouseOut="this.className='PopButton';" onClick=window.close()> 
                                      </td>
                                    </tr>
                                  </table>
                                  
                                </td>
                              </tr>
                            </tbody>
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table>
              
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <%@ include file="../common/include_bottom.jsp" %>
</div>
</body>
</html>
