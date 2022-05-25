<%@ page import="java.util.*,
                com.savvion.sbm.calendar.svo.SBMHolidayInfo,
                com.savvion.sbm.calendar.SBMCalendar,
                org.apache.commons.lang.StringEscapeUtils" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="MarkHoliday" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script type="text/javascript" language="JavaScript">
<!--
    var holListArray = new Array();

//-->
</script>
</head>

<%
    SBMCalendar.init(null);
    SBMCalendar cal = new SBMCalendar((String)session.getAttribute("calname"));
    List holdayList = cal.getHolidays();
    HashMap uniqueHdMap = new HashMap();
    if (holdayList != null && holdayList.size() > 0) {
         for (int i=0;i <holdayList.size();i++) {
            SBMHolidayInfo holidayInfo = (SBMHolidayInfo) holdayList.get(i);
            String holidayName = holidayInfo.getHolidayName();
            uniqueHdMap.put(holidayName, "");

         }
         Iterator it = uniqueHdMap.keySet().iterator();
         int i = 0;
         while (it.hasNext()) {
             i++;
             String key = (String)it.next();
             key= StringEscapeUtils.escapeJavaScript(key);
%>
 <script> holListArray[<%=i%>]= '<%= key %>';</script>

<%
        }
    }

%>

<script type="text/javascript" language="JavaScript">
<!--
   
    function isHolNameExists(){
        var holidyName = document.forms[0].holName.value;
        if((holidyName.indexOf("'")!=-1)||(holidyName.indexOf("\"")!=-1)) {
            alert('<sbm:message key="bizCalAlert41" />');
            return false;
        }
        for (i=0; i < holListArray.length; i++) {
            var holName = holListArray[i];
           if (document.forms[0].holName.value == holName){
               alert('<sbm:message key="bizCalAlert9" />');
               return false;
            }
        } 
        return addHoliday();
    }

//-->
</script>
<body onLoad="setCheckBoxStyleForIE();">
<form>

<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="MarkHoliday" /></td>
          </tr>
          <tr>
            <td class="PopTblEmboss" align="center">
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="SegTblInBg">
                          <table border="0" cellpadding="6" cellspacing="1" width="100%" class="SegDataTbl">
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Date" />:</td>
                              <td width="60%" class="SegFieldLftAlt">
                                <script>getDate();</script>
                              </td>
                            </tr>
                            <tr>
                              <td class="SegValRt"><sbm:message key="HolidayName" />:</td>
                              <td width="60%" class="SegFieldLft"> <input type="text" class="InptTxt" name="holName" size="40" maxLength="32" />
                              </td>
                            </tr>
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Type" />:</td>
                              <td width="60%"class="SegFieldLftAlt"><table>
                                  <tr>
                                    <td> <b>
                                      <input name="halfDay" type="radio" class="InptChkBox" value="0" checked="true" />
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="FullDay" /></td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input name="halfDay" type="radio" class="InptChkBox" value="1" />
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="FirstHalf" /></td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input name="halfDay" type="radio" class="InptChkBox" value="2" />
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="SecondHalf" /></td>
                                  </tr>
                                </table></td>
                            </tr>
                            <tbody>
                              <tr>
                                <td class=SegFieldCntr colspan="2"> <table border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td class="BtnSpace"> <input type="submit" name="Save" value="<sbm:message key="Save" />"  class="PopButton" onMouseOver="this.className='PopButtonHover';" onMouseOut="this.className='PopButton';" onClick="return isHolNameExists();">
                                      </td>
                                      <td class="BtnSpace"> <input type="reset" name="Reset" value="<sbm:message key="Reset" />" class="ScrnButton" onMouseOver="this.className='ScrnButtonHover';" onMouseOut="this.className='ScrnButton';">
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="Cancel" value="<sbm:message key="Cancel" />"  class="PopButton" onMouseOver="this.className='PopButtonHover';" onMouseOut="this.className='PopButton';" onClick=window.close()>
                                      </td>
                                    </tr>
                                  </table></td>
                              </tr>
                            </tbody>
                          </table>
                        </td></tr>
                    </table>
                  </td>
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
</form>
</body>
</html>
