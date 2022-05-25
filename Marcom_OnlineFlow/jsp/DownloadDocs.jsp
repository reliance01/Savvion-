<%@ page import="java.io.*" %>
<%@ page import="com.savvion.sbm.bizlogic.client.BLClientUtil" %>
<%@ page import="com.savvion.sbm.bizlogic.client.queryservice.QueryService" %>
<%@ page import="com.savvion.sbm.bizlogic.client.queryservice.bizstore.BSProcessInstance" %>
<%@ page import="com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDataSlotData" %>
<%@ page import="com.savvion.sbm.bizlogic.client.queryservice.bizstore.svo.BSDocumentDS" %>
<%@ page import="com.savvion.sbm.bizlogic.server.ejb.BLServer" %>
<%@ page import="com.savvion.sbm.bizlogic.util.Session" %>

<%
  BLServer blserver = null;
  Session blsession = null;
  try{
     String fileName = request.getParameter("name");
     String piID = request.getParameter("pid");
     String processName = request.getParameter("process");
     if(fileName != null && fileName.trim().length()>0 && piID != null && piID.trim().length()>0){
     blserver = BLClientUtil.getBizLogicServer();
	 blsession = blserver.connect("rgicl","rgicl");
	 QueryService qService = new QueryService(blsession);
	 BSDataSlotData bsDataSlotData = null;
	 if(processName != null && processName.trim().length()>0)
	 {
	   if(processName.equalsIgnoreCase("ONL"))
	   {
	       bsDataSlotData = new BSProcessInstance(qService)
					.getProcessInstance(Long.parseLong(piID)).getDataSlotList().get("CreativeDocByOnlineTeam");  
	   }
	   else if(processName.equalsIgnoreCase("MRC"))
	   {
	      bsDataSlotData = new BSProcessInstance(qService)
					.getProcessInstance(Long.parseLong(piID)).getDataSlotList().get("mrcWaterMark");
	   }
	    BSDocumentDS bsDocumentDS = (BSDocumentDS) bsDataSlotData;
	    response.setContentType("application/vnd.ms-excel");   
        response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");   
	    byte[] buf = bsDocumentDS.getContent(fileName);
	    response.getOutputStream().write(buf);
      }
     }
   }catch(Exception e){
	 e.printStackTrace();
   }finally{
     try{
        blserver.disConnect(blsession);
     }catch(Exception ex){
       throw new RuntimeException(ex);
     }
   }
%>