<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="AddWorkingTime" /> -  <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript" src="../javascript/utilities.js"></script>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script language="JavaScript" src="../javascript/workinghrs.js"></script>
<script language="JavaScript" src="../javascript/cal.js"></script>
<script language="Javascript">
<!--
function setDefaults()
{
    fillYearDropDown('bizHourYear',2005,2030);
    fillMonthDropDown('bizHourMonth');
    fillDateDropDown('bizHourDate');
    fillHoursDropDown('splBizStartTimeHrs');
    fillHoursDropDown('splBizEndTimeHrs');
    fillMinutesDropDown('splBizStartTimeMins');
    fillMinutesDropDown('splBizEndTimeMins');
    setCheckBoxStyleForIE();
    onSelectOption('day');
}

function fillDate() {


    var drop_down = eval ("document.forms[0]." + 'bizHourDate');

    var imonth = getMonth(document.forms[0].bizHourMonth.value);
    var maxDate = 0;

    if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8
        || imonth == 10 || imonth == 12) {

        maxDate = 31;

    } else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {

        maxDate = 30;
    } else if (imonth == 2){

        if(isLeapYear(document.forms[0].bizHourYear.value) || document.forms[0].bizHourYear.value == "All") {
            maxDate = 29;
        } else {
            maxDate = 28;
        }
    }
    drop_down.options.length = 0;

    var counter = 1;
    for(var i=0;i<maxDate;i++) {
        drop_down.options[i] = new Option(""+counter , ""+counter);
        counter = counter + 1;
    }

}
//-->
</script>
</head>
<body onLoad=setDefaults();>
<form name="addwrkhrs" >
<%@ include file="../common/include_form_body.jsp" %>
<input type="hidden" name="optionselected" value="day" />
<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="AddWorkingTime" /></td>
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
                            <td colspan="2" width="100%" valign="top" class="SmlTxtShortNote">
                              <div style="border-color: #000000; border-style: solid; border-width: 1px; padding-top: 2px; padding-right: 2px; padding-bottom: 2px; padding-left: 2px;"><sbm:message key="calendar.add.working.hr.message.01"/></div>
                            </td>
                            </tr>
                            <tr>
                              <td class="SegValRt"><sbm:message key="Day-Date" />:</td>
                              <td class="SegFieldLft"> <table cellspacing="0" cellpadding="0" border="0">
                                  <tbody>
                                    <tr>
                                      <td><b>
                                        <input class="InptChkBox" type="radio" value="day" name="splBizHourFormat" onclick="onSelectOption('day');">
                                        </b></td>
                                      <td class="SmlTxt"><b><sbm:message key="Day" />:&nbsp;&nbsp;</b></td>
                                      <td class="SmlTxt">
                                         <select class=InptSelect name="bizHourDOW">
                                          <option value=Sunday selected><sbm:message key="calendar.day.sunday"/></option>
                                          <option value=Monday><sbm:message key="calendar.day.monday"/></option>
                                          <option value=Tuesday><sbm:message key="calendar.day.tuesday"/></option>
                                          <option value=Wednesday><sbm:message key="calendar.day.wednesday"/></option>
                                          <option value=Thursday><sbm:message key="calendar.day.thursday"/></option>
                                          <option value=Friday><sbm:message key="calendar.day.friday"/></option>
                                          <option value=Saturday><sbm:message key="calendar.day.saturday"/></option>
                                        </select>
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                                <img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="5">
                                <table cellspacing="0" cellpadding="0" border="0">
                                  <tbody>
                                    <tr>
                                      <td>
                                        <input class="InptChkBox" type="radio" value="date" name="splBizHourFormat" onclick="onSelectOption('date');">
                                      </td>
                                      <td class="SmlTxt"><b><sbm:message key="calendar.add.working.hr.date" />:&nbsp;</b></td>
                                      <td class="SmlTxt"><sbm:message key="calendar.add.working.hr.year.before"/>&nbsp;</td>
                                      <td class="SmlTxt">
                                        <select class="InptSelect" name="bizHourYear" onchange="fillDate();"></select>
                                      </td>
                                      <td class="SmlTxt">&nbsp;<sbm:message key="calendar.add.working.hr.year.after"/></td>
                                      <td class="SmlTxt"><sbm:message key="calendar.add.working.hr.month.before"/>&nbsp;</td>
                                      <td class="SmlTxt">
                                        <select class="InptSelect" name="bizHourMonth" onchange="fillDate();"></select>
                                      </td>
                                      <td class="SmlTxt">&nbsp;<sbm:message key="calendar.add.working.hr.month.after"/></td>
                                      <td class="SmlTxt"><sbm:message key="calendar.add.working.hr.date.before"/>&nbsp;</td>
                                      <td class="SmlTxt">
                                        <select class="InptSelect" name="bizHourDate"></select>
                                      </td>
                                      <td class="SmlTxt">&nbsp;<sbm:message key="calendar.add.working.hr.date.after"/></td>
                                    </tr>
                                  </tbody>
                                </table></td>
                            </tr>
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Time" />:</td>
                              <td class="SegFieldLftAlt"> <table cellspacing="0" cellpadding="0" border="0">
                                  <tbody>
                                    <tr>
                                      <td class="SmlTxt"><sbm:message key="StartTime" />:&nbsp;</td>
                                      <td><select class="InptSelect" name="splBizStartTimeHrs">
                                        </select> </td>
                                      <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                      <td class="SmlTxt"><select class="InptSelect" name="splBizStartTimeMins">
                                        </select></td>
                                      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                      <td class="SmlTxt"><sbm:message key="EndTime" />:&nbsp;</td>
                                      <td><select class="InptSelect" name="splBizEndTimeHrs">
                                        </select> </td>
                                      <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                      <td class="SmlTxt"><select class="InptSelect" name="splBizEndTimeMins">
                                        </select></td>
                                    </tr>
                                  </tbody>
                                </table></td>
                            </tr>
                            <tbody>
                              <tr>
                                <td colspan="2" class="SegFieldCntr"> <table border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td class="BtnSpace"> <input type="button" name="save" value=" <sbm:message key="save" /> "  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="javascript:if(saveWorkingHrs()) {window.close()}" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="saveaddnew" value="<sbm:message key="SaveAndAddNew" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="javascript:saveWorkingHrs();window.location.reload(true);MM_goToURL('parent','#');return document.MM_returnValue;" style="width=140px" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="resetBtn" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="document.forms[0].reset();onSelectOption('day');" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="cancel" value="<sbm:message key="cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="window.close()" />
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
