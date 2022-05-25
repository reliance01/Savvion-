<%@ page import="java.util.*" %>
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
<title><sbm:message key="HolidayDetails" /> -  <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script type="text/javascript" language="JavaScript">
<!--
    function initRadio() {
        doc = window.opener.document.forms[0];
        holType = doc.halfDay.value;
        document.forms[0].halfDay[holType].checked = true;
    }


    function addHoliday() {
        parentdoc = window.opener.document.forms[0];
        parentdoc.date.value = parentdoc.selDateVal.value;
        parentdoc.holName.value = parentdoc.currHolidayName.value;
        //parentdoc.currHolidayName.value='';
        for (i = 0; i < 3; ++i) {
            if(document.forms[0].halfDay[i].checked) {
                parentdoc.halfDay.value = document.forms[0].halfDay[i].value;
            }
        }
        parentdoc.action.value = "markHoliday";
        parentdoc.submit();
        closeWindow();
    }



//-->
</script>
</head>
<body onLoad="setCheckBoxStyleForIE();javascript:initRadio();" >
<form onSubmit="javascript:return addHoliday();">
<%@ include file="../common/include_form_body.jsp" %>
<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp; <sbm:message key="HolidayDetails" /></td>
          </tr>
          <tr>
            <td class="PopTblEmboss" align="center">
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="SegTblInBg">
                          <table border=0 cellpadding=6 cellspacing=1 width=100% Class="SegDataTbl">
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Date" />:</td>
                              <td width="60%" class="SegFieldLftAlt">
                                <script>getDate();</script>
                              </td>
                            </tr>
                            <tr>
                              <td class="SegValRt"><sbm:message key="HolidayName" />:</td>
                              <td width="60" class="SegFieldLft"> <script>getHolidayName();</script></td>
                            </tr>
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Type" />:</td>
                              <td width="60%" class="SegFieldLftAlt"><table>
                                  <tr>
                                    <td> <b>
                                      <input disabled="disabled" name="halfDay" type="radio" class="InptChkBox" value="0" >
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="FullDay" /></td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input disabled="disabled" name="halfDay" type="radio" class="InptChkBox" value="1">
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="FirstHalf" /></td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input disabled="disabled" name="halfDay" type="radio" class="InptChkBox" value="2">
                                      </b> </td>
                                    <td class="SmlTxt"><sbm:message key="SecondHalf" /></td>
                                  </tr>
                                </table></td>
                            </tr>
                            <tbody>
                              <tr>
                                <td class="SegFieldCntr" colspan="2"> <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td class="BtnSpace"><input type="button" name="Cancel" value="<sbm:message key="Cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="window.close()" />
                                      </td>
                                      <td width="100%" class="BtnSpace">&nbsp; </td>
                                      <td class="BtnSpace"><input type="button" name="Unmark Holiday" value="<sbm:message key="UnmarkHoliday" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="javascript:deleteHoliday();" style="width:130px" />
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
