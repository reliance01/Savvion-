<%@ tag import="com.savvion.sbm.bpmportal.util.PortalConstants.DocTypeEnum"  body-content="empty" %> 
<%@ attribute type="java.lang.String" name="version" required="false" %>
<%@ attribute type="java.lang.String" name="dtdType" required="false" %>
<%=DocTypeEnum.getDocType(version,dtdType)%>
