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
     long piID = Long.parseLong(request.getParameter("piid").toString());
     String docName = request.getParameter("docName");
     if(piID != 0L && docName != null && docName.trim().length() > 0){
     blserver = BLClientUtil.getBizLogicServer();
     blsession = blserver.connect("rgicl","rgicl");
     QueryService qService = new QueryService(blsession);
     BSDataSlotData bsDataSlotData = null;
     bsDataSlotData = new BSProcessInstance(qService)
					.getProcessInstance(piID).getDataSlotList().get(docName);
     if(bsDataSlotData != null)
     {
       BSDocumentDS bsDocumentDS = (BSDocumentDS) bsDataSlotData;
       if(bsDocumentDS != null && bsDocumentDS.getDocumentNames().size() > 0)
       {
         out.println(bsDocumentDS.getDocumentNames());
       }
       else
       {
         out.println("Not Found");
       }
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