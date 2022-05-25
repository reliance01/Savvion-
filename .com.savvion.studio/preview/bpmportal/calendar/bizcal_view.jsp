<%@ taglib uri="/bpmportal/tld/calendar-tags.tld" prefix="calendar" %>
<%@ page import="java.util.*,java.text.*,java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList,com.savvion.sbm.bpmportal.calendar.*,
                com.savvion.sbm.calendar.CalDataController,
                com.savvion.sbm.bizlogic.util.BLCalendar,
                com.savvion.sbm.calendar.svo.SBMCalendarInfo,
                com.savvion.sbm.calendar.svo.SBMHolidayInfo,
                com.savvion.sbm.calendar.util.CalendarUtil,
                com.savvion.sbm.bizmanage.pagegenerator.ServletTools,
                com.savvion.sbm.bizmanage.util.BizManageConfiguration,
                com.savvion.sbm.calendar.util.CalendarConstants,
                com.savvion.sbm.bpmportal.util.PortalConstants"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="bizcal_error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="adminBean" scope="session" class="com.savvion.sbmadmin.bean.SBMAdminBean" />
<jsp:useBean id="umBean" scope="session" class="com.savvion.sbmadmin.bean.UserManagementBean" />
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<%
    bizManage.setRequest(request);
    bizManage.setResponse(response);

    String months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
    Calendar aCalendar = Calendar.getInstance();
    int aYear = aCalendar.get(Calendar.YEAR);

    if(request.getParameter("action") != null) {
        CalendarAction.self().execute(request, response);
    }
    SBMCalendarBean.self().refresh();
    CalendarAction.self().populateData(request);

    String selectedYear = (String)request.getAttribute("year");
    String month = (String)request.getAttribute("month");

    int aMonth = aCalendar.get(Calendar.MONTH);
    String tempMonth = months[aMonth];


    String calname = ServletTools.getUTFString(request,"calName");
    session.setAttribute("calname", calname);

    int[] caltype = {BLCalendar.types.OTHER,BLCalendar.types.SYSTEM};
    ArrayList calendars = CalDataController.self().getCalendars(caltype);

    // Calculate startTime
    Calendar rightNow = Calendar.getInstance();
    rightNow.set(Integer.parseInt(selectedYear), Calendar.JANUARY, 1, 0, 0, 0);
    long startTime = rightNow.getTimeInMillis();

    // EndTime
    rightNow.set(Integer.parseInt(selectedYear), Calendar.DECEMBER, 31, 23, 59, 59);
    long endTime = rightNow.getTimeInMillis();

    ArrayList holidays = CalDataController.self().getHolidays(calname,startTime, endTime);

    ArrayList calNames = (ArrayList)request.getAttribute("calnames");
    ArrayList calyrs = (ArrayList)request.getAttribute("calyears");

    String dayOfWeek = (String)request.getAttribute("dayOfWeek");
    String[][] calInfo = (String[][])request.getAttribute("calInfo");

    boolean isSystem = false;
    SBMCalendarInfo sci = CalDataController.self().getCalendar(calname);
        isSystem = sci.isSystemCalendar();

    pageContext.setAttribute("calendars", calendars);
    pageContext.setAttribute("calname", calname);
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="CalendarView" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script type="text/javascript" language="JavaScript">
<!--

var dateArray = new Array();
var monthArray = new Array();
function initMonth() {
    //monthArray
}
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function setDefaults() {
    fillMonthDropDown("month");
    selectValueInDropDown(eval ("document.forms[0].month"), '<%=month%>');
    fillYearDropDown("year", 2005, 2030);
    selectValueInDropDown(eval ("document.forms[0].year"), '<%=selectedYear%>');
    return true;
}

debug = false;

isIE = Ext.isIE;

function getCtrl(name)
{
  var ctrl;
  if (isIE)
  {
    ctrl = document.bulk.elements(name);
    //return document.bulk.elements(name);
  }
  else
  {
    ctrl = document.forms['bulk'].elements[name];
    //return document.forms['bulk'].elements[name];
  }
    return ctrl;
}

function setValue(name, value)
{

  var ctrl = getCtrl(name);
  ctrl.value = value;
  //alert("The control " + ctrl.name + " is set to " + ctrl.value);
}

function getValue(name)
{

  var ctrl = getCtrl(name);
  //alert("ctrl : " + ctrl.name + " value : " + ctrl.value);
  return ctrl.value;
}

function displaySelCalendar(count) {
    // extract the month,year and date from the string

    var month;
    var year;

    if (dateArray[count].indexOf('|') >= 0) {
        var temp = new Array();
        temp = dateArray[count].split('|');
        year = temp[0];
        month  = temp[1];
    }
    //alert(month + year);
    setValue('month', month);
    setValue('year', year);
    setValue('selectedDate', 1);
    setValue('selDateVal', 1);
    var doc = document.forms[0];
    //doc.action.value = action;
    doc.submit();
}

function submitForm(action) {
    setValue('month', getValue('month'));
    setValue('year', getValue('year'));
    var doc = document.forms[0];
    doc.action.value = action;
    doc.submit();
}




//-->
</script>
<link href="../css/<c:out value="${bizManage.theme}" />/bizcal_view.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="setDefaults();">
<form name="bulk" method="post" action="../calendar/bizcal_view.jsp">
<%@ include file="../common/include_body.jsp" %>
<input type="hidden" name="action" value="" />
<input type="hidden" name="date" value="">
<input type="hidden" name="holName" value="">
<input type="hidden" name="halfDay" value="">
<input type="hidden" name="currHolidayName" value="">
<input type="hidden" name="selectedDate" value="">
<input type="hidden" name="selDateVal" value="">
<input type="hidden" name="temp" value="">
<input type="hidden" name="isSystem" value="<%= String.valueOf(isSystem) %>" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <%
      String menu1 = "2";
      String submenu1 = "0";
    %>
      <%@ include file="../common/include_menu_static.jspf" %>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td class="SmlTxt" height="20">&nbsp;&nbsp;<a href="../calendar/bizcal_list.jsp" class="ActionLnk"><sbm:message key="Administration" />:&nbsp;<sbm:message key="System" />&nbsp;(<sbm:message key="CalendarList" />)</a>&nbsp;&gt;&nbsp;<b><sbm:message key="CalendarView" /></b></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="6" cellpadding="0">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="FilterBarTbl">
        <tr align="center">
          <td class="FilterTblEmboss" align="center"><table width="100%" border="0" cellpadding="2" cellspacing="1">
              <tr>
                <td width="55%" align="right" class="SmlTxt"><b><sbm:message key="Calendar" />:</b></td>
                <td>
                    <select class="InptSelect" name="calName" onchange='JavaScript:submitForm("mthCal")' >
                    <c:if test="${not empty calendars}">
                      <c:forEach var="calendar" items="${calendars}">
                        <c:choose>
                          <c:when test="${calendar.name == calname}">
                        <option selected="selected" value="<c:out value="${calendar.name}" />"><c:out value="${calendar.name}" /></option>
                          </c:when>
                          <c:otherwise>
                        <option value="<c:out value="${calendar.name}" />"><c:out value="${calendar.name}" /></option>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                    </c:if>
                  </select>
                </td>
                <td width="45%" align="right"><a href=javascript:encodeHrefUrl("../calendar/bizcal_det.jsp?calName=<%=URLEncoder.encode((String)pageContext.findAttribute("calName"),PortalConstants.UTF8)%>") class="PsvTitleLnk"><sbm:message key="CalendarDetails" /></a></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="SepBarTbl">
        <tr>
          <td><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="2"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="SegLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="1" ></td>
        </tr>
        <tr>
          <td height="457" align="center" valign="top" class="CalSegTblBgPad" id="CalSegTblBgPadId">
            <table border="0" cellspacing="4" cellpadding="4">
              <tr>
                <td align="right"> <table border="0" cellpadding="0" cellspacing="1" class="BizCalTblLightBg">
                    <tr>
                      <td> <table width="100%" border="0" cellpadding=4 cellspacing="1">
                          <tbody>
                            <tr>
                              <td colspan="7" CLASS="BizCalDateBgGrp"> <table border="0" align="center">
                                  <tbody>
                                    <tr>
                                      <td class="BizCalTimeLabel"><sbm:message key="Month" />:&nbsp;</td>
                                      <td class="" align="center"> <select name="month" class="BizCalInptSelectAmPm" onchange='JavaScript:submitForm("mthCal")' >
                                        </select> </td>
                                      <td class="BizCalTimeLabel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<sbm:message key="Year" />:&nbsp;</td>
                                      <td><select name="year" class="BizCalInptSelectAmPm" onchange='JavaScript:submitForm("yearCal")' >
                                        </select></td>
                                    </tr>
                                  </tbody>
                                </table></td>
                            </tr>
                                <calendar:formGenerator
                                  month="<%=CalendarUtil.getMonth(month)%>"
                                  year="<%=Integer.parseInt(selectedYear)%>"
                                  dayOfWeek="<%=Integer.parseInt(dayOfWeek)%>"
                                  locale="<%= request.getLocale()%>"
                                  calendarInfo="<%=calInfo%>"/>
                          </tbody>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad">
                          <tr>
                            <td class="LegendBar"> <table border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td class="SmlTxtShortNote"><img src="../css/<c:out value="${bizManage.theme}" />/images/legend_weekend.gif" width="19" height="17" border="0"></td>
                                  <td class="SmlTxt">&nbsp;<sbm:message key="Weekend" /></td>
                                  <td class="SmlTxtShortNote">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                  <td class="SmlTxtShortNote"><img src="../css/<c:out value="${bizManage.theme}" />/images/legend_holiday.gif" width="19" height="17" border="0"></td>
                                  <td class="SmlTxt">&nbsp;<sbm:message key="Holiday" /></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td width=30%> <div  style="height:339px; z-index:1; visibility: visible; overflow: auto" class="CalListDivPad">
                    <table border="0" cellspacing="0" cellpadding="0" align="center">
                      <tr>
                        <td class="CalTblBgDark"> <table border=0 cellpadding=4 cellspacing=1 class="ListTableCellPad">
                            <tbody>
                              <tr>
                  <%
                        boolean isHdAvailForSelcYr = false;
                        if (holidays != null && holidays.size() > 0) {
                            for(int nCount=0;nCount < holidays.size();nCount++) {
                                SBMHolidayInfo holidayInfo = (SBMHolidayInfo) holidays.get(nCount);
                                if (!holidayInfo.getType().equals(CalendarConstants.WEEKEND_TYPE)) {
                                    isHdAvailForSelcYr = true;
                                    break;
                                }
                            }
                        }

                  if(isHdAvailForSelcYr) { %>
                     <td colspan="2" nowrap="nowrap" class="CalValLft"><b><sbm:message key="Holidaysforselectedyear" /></b></td>
                  <% } else { %>
                       <td colspan="2" nowrap="nowrap" class="CalValLft"><b><sbm:message key="NoHolidaysforselectedyear" /></b></td>
                  <% } %>
                  </tr>
                    <%

                        if (holidays != null && holidays.size() > 0) {
                            for(int nCount=0;nCount < holidays.size();nCount++) {
                                SBMHolidayInfo holidayInfo = (SBMHolidayInfo) holidays.get(nCount);
                                if (!holidayInfo.getType().equals(CalendarConstants.WEEKEND_TYPE)) {
                                String holidayName = holidayInfo.getHolidayName();
                                SimpleDateFormat formatter = BizManageConfiguration.BizManageResources.getDateFormat(bizManage.getLocale());
                                String fromDateString = formatter.format(new java.util.Date(holidayInfo.getValue())).toString();
                                aCalendar.setTime(new java.util.Date(holidayInfo.getValue()));
                                aYear = aCalendar.get(Calendar.YEAR);

                                aMonth = aCalendar.get(Calendar.MONTH);
                                tempMonth = months[aMonth];

                    %>
                              <tr>
                                <td nowrap class="CalValLftAlt"><a href="Javascript:displaySelCalendar('<%=nCount%>')" class="TblLnk" > <%=fromDateString%> </a></td>

                                <td class="CalValLftAlt"><%=holidayName%></td>

                              </tr>
                                <script language="JavaScript">
                                    <!--
                                    dateArray[<%=nCount%>] = '<%=aYear%>' + '|' + '<%=tempMonth%>' ;
                                    //-->
                                </script>
                    <%
                                }
                            }
                        }
                        else
                        {
                    %>

                          <tr align="center">
                            <td class="ValCntr" colspan="9"> <br>
                              <table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                                <tr>
                                  <td valign="top"><img alt="" src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16" /></td>
                                  <td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoSearchResults" /></span></span></td>
                                </tr>
                              </table>
                              <br>
                            </td>
                          </tr>
                    <%
                        }
                    %>
                            <p></p>
                          </table></td>
                      </tr>
                    </table>
                  </div></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="SegLineBg"><img src="../css/<c:out value="${bizManage.theme}" />/images/blank.gif" width="1" height="1"></td>
        </tr>
      </table>
      <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
        <tr align="center">
          <td align="center" class="ButtonDarkBg"> <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="BtnSpace"> <input type="submit" name="Cancel" value="Cancel" class="ScrnButton" onMouseOver="this.className='ScrnButtonHover';" onMouseOut="this.className='ScrnButton';" onClick="MM_goToURL('parent','../calendar/bizcal_list.jsp');return document.MM_returnValue">
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%@ include file="../common/include_copyright_static.jsp" %>
    </td>
  </tr>
  <tr>
    <td>
      <%@ include file="../common/include_bottom.jsp"%>
    </td>
  </tr>
</table>
</form>
</body>
</html>
