<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="com.savvion.sbm.bizmanage.aim.AimUtil"%>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.DateTime" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.Decimal" %>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="com.savvion.sbm.bpmportal.service.DashboardService"%>
<%@ page import="com.savvion.sbm.bpmportal.util.DateTimeUtils"%>
<%@ page import="com.savvion.sbm.bpmportal.domain.IWidgetRenderer"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<% String dwId = ((Long)request.getAttribute("dashboardWidgetId")).toString();%>
<c:set var="widgetPosition" value="${dashboardWidget.dashboardWidgetRow}${dashboardWidget.dashboardWidgetColumn}"/>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<form name = "globalDsForm_<%=dwId%>" id = "globalDsForm_<%=dwId%>">
<%@ include file="../common/include_form_body.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="SbmFilterTableComponent">
    <tr>
      <td><b >&nbsp;<sbm:message key="BPM_Portal_Dashboard.ComponentName"/>:</b></td>
      <td nowrap="nowrap"><c:out value="${dashboardWidget.dashboardWidgetName}"/></td>      
      <td><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" /></td>
      <td><b >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="BPM_Portal_Dashboard.Process" />:</b></td>
      <td nowrap="nowrap"><c:out value="${dashboardWidget.widget.applicationName}"/></td>
      <td width="100%">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="_process_" value='<c:out value="${dashboardWidget.widget.applicationName}"/>' />
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="SegTblInBg">
        <table border="0" cellpadding="4" cellspacing="1" class="SegDataTbl" width="100%">
    <%
      String ptid = (String)request.getAttribute(IWidgetRenderer.PROCESS_TEMPLATE_ID);  
      String ptname = bizManage.getPTNameFromPTID(ptid);
      
      boolean dontUpdate = true;
      StringBuffer dateConvert = new StringBuffer("");
      StringBuffer textAreaCheck = new StringBuffer("");
      StringBuffer dateFix = new StringBuffer("");
      ArrayList dataslots = (ArrayList)request.getAttribute("dataslots");
      for (int i=0;i<dataslots.size();i++)
      {
        Dataslot dataslot = (Dataslot) dataslots.get(i);
        dontUpdate = (!dataslot.updatable());
    %>
          <tr>
            <td class="<%= ((i%2 == 0)?"SegValRt":"SegValRtAlt") %>"><%= dataslot.getLabel() %>:
    
    <% if(dataslot.isRequired()){  %> <span class="Required">*</span> <% } %> </td>
    
    <%
        if (dataslot.hasChoices())
        {
          ArrayList allchoices = dataslot.getChoices();
          HashMap choiceLabels = dataslot.getChoiceLabels();
    %>
        <td class="<%= ((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt") %>" width="50%">
        <select class="InptSelect"<%= ((dontUpdate)?" disabled=\"disabled\"":"") %> name="<%=dataslot.getId()%>">
    <%
      for (int c=0;c<allchoices.size();c++)
          {
            String choice = (String) allchoices.get(c);
    %>
        <option value="<%=choice%>"<%=((dataslot.getValue().equals(choice))?" selected":"")%>><%=((choiceLabels.size()>0)?choiceLabels.get(choice):choice)%></option>
    <%
      }
    %>
              </select>
            </td>
    <%
      }  //end dataslot.hasChoices();
        else if (dataslot.isDocument() ||  dataslot.isObject() || dataslot.isSet() || dataslot.isMap() || dataslot.isList())
        {
    %>
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%"><%=dataslot.getValue().toString()%>
      <%
        if(dataslot.isDocument()) {
      %>
      <script type="text/javascript" language="JavaScript">addField( '<%=dataslot.getId()%>', '<%=dataslot.getType().toLowerCase()%>', false);</script>
      <%
        }
      %>
      </td>
      <%=((dataslot.isDocument())?"<input type=\"hidden\" name=\""+dataslot.getId()+"\" value=\"update\">":"")%>
    <%
      }  //end dataslot.isDocument() || dataslot.isObject() || dataslot.isSet() || dataslot.isMap() || dataslot.isList()
        else if (dataslot.isMultiLine())
        {
    %>
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <textarea id="ta<%=String.valueOf(i)%>" name="<%=dataslot.getId()%>"<%=((dontUpdate)?" disabled=\"disabled\"":"")%> class="InptTxtArea" onkeypress="textAreaLimiter(this,<%=dataslot.getSize()%>,'<sbm:message key="textAreaAlert" />');" wrap="virtual" cols="40" rows="4" ><%=AimUtil.getHtmlSafeString(dataslot.getValue().toString())%></textarea>
      </td>
    <%
      } //end dataslot.isMultiLine()
        else if (dataslot.isBoolean())
        {
    %>
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                    <input type="radio"<%=((dontUpdate)?" disabled=\"disabled\"":"")%> <%=((dataslot.getValue().toString().equalsIgnoreCase("true"))?" checked=\"checked\"":"")%> name="<%=dataslot.getId()%>" value="true" />
                  </td>
                  <td class="SmlTxt"><sbm:message key="True" /></td>
                  <td class="SmlTxt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                  <td>
                    <input type="radio"<%=((dontUpdate)?" disabled=\"disabled\"":"")%> <%=((dataslot.getValue().toString().equalsIgnoreCase("false"))?" checked=\"checked\"":"")%> name="<%=dataslot.getId()%>" value="false" />
                  </td>
                  <td class="SmlTxt"><sbm:message key="False" /></td>
                </tr>
              </table>
      </td>
    <%
      }  //end dataslot.isBoolean()
        else if (dataslot.isString() || dataslot.isLong() || dataslot.isDouble() || dataslot.isURL())
        {
            String type  = "text";
            if(dataslot.isPassword()){
                type = "password";
            }
    %>
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <input type="<%=type%>"<%=((dontUpdate)?" disabled=\"disabled\"":"")%> class="InptTxt" name="<%=dataslot.getId()%>" value="<%=AimUtil.getHtmlSafeString(dataslot.getValue().toString())%>" <%=((dataslot.isString())?"maxlength=\""+dataslot.getSize()+"\"":"")%> size="40" />

        <script type="text/javascript" language="JavaScript">addFieldLabel( '<%=dataslot.getId()%>', '<%=dataslot.getType().toLowerCase()%>', false ,'<%=dataslot.getId()%>');</script>

      </td>
    <%
      } //end dataslot.isString() || dataslot.isLong() || dataslot.isDouble() || dataslot.isURL
        else if (dataslot.isDecimal())
        {
          Decimal decimal = (Decimal) dataslot.getValue();
          String overallValue = decimal.getStringValue();
    %>
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right"><input type="text"<%= ((dontUpdate)?" disabled=\"disabled\"":"") %> id='<%= dataslot.getName()+"_" %><c:out value="${widgetPosition}"/>' onclick="editDecimal('<%= dataslot.getName()+"_" %><c:out value="${widgetPosition}"/>','<%= String.valueOf(decimal.getPrecision() - decimal.getScale()) %>','<%= String.valueOf(decimal.getScale()) %>');" class="InptTxt" name="<%= dataslot.getName() %>" size="5" value="<%= overallValue %>" />
                    <script type="text/javascript" language="JavaScript">addField( '<%=dataslot.getId()%>', '<%=dataslot.getType().toLowerCase()%>', false);</script>
                  </td>
                  <td align="right">&nbsp;&nbsp;</td>
                  <td align="right"><a href="#" onclick="editDecimal('<%= dataslot.getName()+"_" %><c:out value="${widgetPosition}"/>','<%= String.valueOf(decimal.getPrecision() - decimal.getScale()) %>','<%= String.valueOf(decimal.getScale()) %>');"><img alt="Edit Decimal" src="../css/<c:out value="${bizManage.theme}" />/images/icon_edit_decimal_dataslot.gif" width="16" height="16" border="0" title='<sbm:message key="editDecimalDataslot" />' /></a></td>
                </tr>
              </table>
      </td>
    <%
      } //end dataslot.isDecimal()
        else if (dataslot.isDateTime())
        {
      String strDateTime = "";
      String dsValue=dataslot.getValue().toString();
      if (dsValue != null && !dsValue.equals("<NULL>")) {
         DateTime datetime = (DateTime) dataslot.getValue();
         
            if (datetime != null) {
            strDateTime =  bizManage.getDate(datetime.getValue().getTime(), ptname, dataslot.getId());                                
        }
      }
      String dateFmt = DateTimeUtils.getJSCalendarDateFormat(ptname, dataslot.getId(), bizManage.getLocale(), bizManage.getName()); 
         dateConvert.append("\tdocument.getElementById('").append(dataslot.getId());
         dateConvert.append("').value = sbm.date2Long(document.getElementById('").append(dataslot.getId());
         dateConvert.append("').value,'").append(dateFmt).append("');\n");

         dateFix.append("\tlong2Date(document.getElementById('").append(dataslot.getId()).append("');\n");
    %>
    
      <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <table border="0" cellspacing="0">
                <tr>
                  <td>
            <input type="text"<%=((dontUpdate)?" disabled=\"disabled\"":"")%> class="InptTxt" id = "<%=dataslot.getId()%>" name="<%=dataslot.getId()%>" value="<%=strDateTime%>" <%=(dataslot.getValidationType()  != null) ? ("alt=\""+dataslot.getValidationType()+"\""):""%>/>
                  </td>
          <td>
            <%
              if (dontUpdate) {
            %>
                <img border="0" height="16" width="16" src="../css/<c:out value="${bizManage.theme}" />/images/calender.gif">
            <%
              } else {
            %>
                <a href="#" onclick="sbm.showCalendar('<%=dataslot.getId()%>', '<%=dateFmt%>' );">
                    <img border="0" height="16" width="16" src="../css/<c:out value="${bizManage.theme}" />/images/calender.gif">
                </a>
            <%
              }
            %>
          </td>
                </tr>
              </table>
      </td>
    <%
      } //end dataslot.isDateTime()
        else if (dataslot.isXML())
        {
    %>
        <%=((dataslot.isXML())?"<input type=\"hidden\" name=\""+dataslot.getId()+"\" value=\"update\">":"")%>
        <td class="<%=((i%2 == 0)?"SegFieldLft":"SegFieldLftAlt")%>" width="50%">
        <input type="hidden" name="<%=dataslot.getId()%>" value="update">
        <%= dataslot.getValue().toString() %>
      </td>
    <%  }  %>
          </tr>
    <%  }  %>
          <tbody> </tbody>
        </table>
      </td>
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
                  <input type="button" name="save" value=" <sbm:message key="save" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="javascript:upload();SaveGlobalDsData(document.getElementById('globalDsForm_<%=dwId%>'),'content_<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>');" />
                </td>
                <td class="BtnSpace">
                  <input type="button" name="resetBtn" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="ResetGlobalDsData(document.globalDsForm_<%=dwId%>,'content_<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${dashboardWidget.dashboardId}"/>','<c:out value="${dashboardWidget.widgetId}"/>','<c:out value="${dashboardWidget.dashboardWidgetId}"/>','<c:out value="${viewmode}"/>')" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
    </tr>
  </table>
   </form>
