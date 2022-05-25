<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList,com.savvion.sbm.bpmportal.calendar.CalendarAction,
                com.savvion.sbm.bpmportal.calendar.SBMCalendarBean,
                com.savvion.sbm.calendar.CalDataController,
                com.savvion.sbm.calendar.svo.CalendarVO,
                com.savvion.sbm.calendar.svo.SBMCalendarInfo,
                com.savvion.sbm.bizmanage.pagegenerator.ServletTools,
                com.savvion.sbm.calendar.util.CalendarUtil"%>
<%@ page errorPage="bizcal_error.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<%
    CalendarVO calvo = null;
    String actn = request.getParameter("action");
    if(actn != null && (actn.trim().equals("createCal") ||
       actn.trim().equals("editCal") || actn.trim().equals("deleteCal"))) {
           CalendarAction.self().execute(request, response);
    } else {
        String calName = ServletTools.getUTFString(request,"calName");
        calvo = calName != null ?
                           CalDataController.self().getCalendarMetaData(calName) : null;
    }
    boolean edit = false;
    if(calvo != null) {
        edit = true;
    }

    String tmzId = TimeZone.getDefault().getID();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="AddCalendar" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="../css/app<%=bizManage.getTheme()%>/sbm_app01.css" type="text/css"/>
<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script type="text/javascript" src="../javascript/workingtime.js"></script>
<script type="text/javascript" src="../javascript/nonworkingtime.js"></script>
<script language="JavaScript1.1">
<!--
// Initialization of global JavaScript variables
var DELIMITER = "<%= com.savvion.sbm.calendar.util.CalendarUtil.getDelimiter() %>";
var FORMAT_DOW = "<%= com.savvion.sbm.calendar.util.CalendarConstants.FORMAT_DOW %>";
var FORMAT_DAY = "<%= com.savvion.sbm.calendar.util.CalendarConstants.FORMAT_DAY %>";
var workingHrsArray = new Array();
var nonworkingHrsArray = new Array();
var calNames = new Array();
var win_name = 0;
function validateCalDetails()
{
    if(document.forms[0].calName.value.length == 0 || trimString(document.forms[0].calName.value).length == 0)
    {
        alert("<sbm:message key='bizCalAlert1' />");
        return false;
    }

    if(document.forms[0].calName.value.indexOf("|") != -1)
    {
        alert('<sbm:message key="bizCalAlert2" />');
        return false;
    }
    
    if(document.forms[0].calName.value.indexOf("#") != -1)
    {
        alert('<sbm:message key="bizCalAlert40" />');
        return false;
    }

    if(!isValidCalendar())
    {
        return false;
    }

    if(document.forms[0].calDesc.value.length == 0 || trimString(document.forms[0].calDesc.value).length == 0)
    {
        alert('<sbm:message key="bizCalAlert3" />');
        return false;
    }

    var calendarName = document.forms[0].calName.value;
    for(var count=0; count < calNames.length;count++)
    {
        var tempName = calNames[count];
        if (trimString(calendarName) == trimString(tempName)) {

            if (confirm('<sbm:message key="bizCalAlert4" />')) {
                return true;
            } else {    // not confirmed
                return false;
            }
        }
    }
    return true;
}

function addCalendar()
{
    if(!validateCalDetails()) {
        return false;
    }

    document.forms[0].action.value='createCal';

    //Set Years
    document.forms[0].years.value='';
    var stYear = document.forms[0].yearFrom.value;
    var edYear = document.forms[0].yearTo.value;
    for(var count=stYear;count <= edYear;count++)
    {
        document.forms[0].years.value+=count;
        document.forms[0].years.value+=',true';
        if(count <edYear) {
            document.forms[0].years.value+=','+DELIMITER+',';
        }
    }

    //Set Special Business Hours

    document.forms[0].specialBizHours.value='';
    for(var count=0;count < workingHrsArray.length;count++)
    {
        var wHr = workingHrsArray[count];
        var wday = wHr.day;
        var wdate = wHr.date;
        var wtime = wHr.time;
        var strwHr = "";

        var tmptm = wtime.split("-");
        var sttm = tmptm[0];
        sttm=sttm.replace(":","");
        var edtm = tmptm[1];
        edtm=edtm.replace(":","");
        edtm = parseInt(edtm,10);
        sttm =  parseInt(sttm,10);
        
        //Check for Night Shift
       if(edtm < sttm)
        {
            edtm = parseInt(edtm,10) + 2400;
        }

        if(wday != "-")
        {
            strwHr=FORMAT_DOW+','+sttm+','+edtm+','+wday;
        }
        if(wdate != "-")
        {
            var opt = wdate.split(" ");
            var dm = getMonthStr(opt[0]);
            var dd = opt[1].substring(0,opt[1].length-1);
            var dy = opt[2];
            strwHr=FORMAT_DAY+','+sttm+','+edtm+','+dy+','+dm+','+dd;
        }
        document.forms[0].specialBizHours.value+=strwHr;
        document.forms[0].specialBizHours.value+=','+DELIMITER+',';
    }
    //trim the last delimiter
    document.forms[0].specialBizHours.value=document.forms[0].specialBizHours.value.substring(0,document.forms[0].specialBizHours.value.length -1);
    //alert('Special business hrs set to - '+document.forms[0].specialBizHours.value);

    //Set Weekends
    document.forms[0].weekends.value='';

    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nwHr = nonworkingHrsArray[count];
        if(nwHr.type.indexOf('Weekend') != -1 || nwHr.type.indexOf('weekend') != -1)
        {
            var strnwHr = nwHr.name+","+nwHr.day.substring(0,3)+","+nwHr.occ;
            document.forms[0].weekends.value+=strnwHr;
            document.forms[0].weekends.value+=','+DELIMITER+',';
        }
    }
    //trim the last delimiter
    document.forms[0].weekends.value=document.forms[0].weekends.value.substring(0,document.forms[0].weekends.value.length -1);
    //alert('Weekends set to - '+document.forms[0].weekends.value);

    //Set Fixed Holidays
    document.forms[0].fixedHolidays.value='';
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nwHr = nonworkingHrsArray[count];
        if(nwHr.year == 'All' && (nwHr.type == "Holiday"|| nwHr.type == "holiday"))
        {
            var dt = nwHr.date;
            var dy = nwHr.day;
            var strnwHr = "";

            if(dt != "-")
            {
                strnwHr =FORMAT_DAY+','+nwHr.name+","+nwHr.month+','+nwHr.halfday+','+nwHr.date;
            }

            if(dy != "-")
            {
                strnwHr =FORMAT_DOW+','+nwHr.name+","+nwHr.month+','+nwHr.halfday+','+nwHr.day+','+nwHr.occ;
            }
            document.forms[0].fixedHolidays.value+=strnwHr;
            document.forms[0].fixedHolidays.value+=','+DELIMITER+',';
        }
    }
    //trim the last delimiter
    document.forms[0].fixedHolidays.value=document.forms[0].fixedHolidays.value.substring(0,document.forms[0].fixedHolidays.value.length -1);
    //alert('Fixed holidays set to - '+document.forms[0].fixedHolidays.value);

    //Set Regular Holidays
    document.forms[0].regularHolidays.value='';
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nwHr = nonworkingHrsArray[count];
        if(nwHr.year != 'All' && (nwHr.type == "Holiday"|| nwHr.type == "holiday"))
        {
            var dt = nwHr.date;
            var dy = nwHr.day;
            var strnwHr = "";

            if(dt != "-")
            {
                strnwHr =nwHr.name+","+nwHr.year+','+nwHr.month+','+nwHr.date+','+nwHr.halfday;
                document.forms[0].regularHolidays.value+=strnwHr;
                document.forms[0].regularHolidays.value+=','+DELIMITER+',';
            }
        }
    }
    //trim the last delimiter
    document.forms[0].regularHolidays.value=document.forms[0].regularHolidays.value.substring(0,document.forms[0].regularHolidays.value.length -1);
    document.forms[0].submit();
}

function setDefaults()
{
    fillYearDropDown('yearFrom',2005,2030);
    fillYearDropDown('yearTo',2005,2030);
    selectValueInDropDown(eval ("document.forms[0].yearTo"), '2030');
    fillHoursDropDown("lunchStartTimeHrs");
    fillMinutesDropDown("lunchStartTimeMins");
    fillHoursDropDown("lunchEndTimeHrs");
    fillMinutesDropDown("lunchEndTimeMins");
    fillHoursDropDown("bizHoursStartTimeHrs");
    fillMinutesDropDown("bizHoursStartTimeMins");
    fillHoursDropDown("bizHoursEndTimeHrs");
    fillMinutesDropDown("bizHoursEndTimeMins");
    refreshWorkingTable('WorkingTable');
    refreshNonWorkingTable('NonWorkingTable');
    selectValueInDropDown(eval ("document.forms[0].timezoneID"), '<%=tmzId%>');
    return true;
}

function resetAddBizCalendarForm(){
    document.forms[0].reset();
    setDefaults();
}
// Disables delNonWrkHrs and DelWorkingTimeBtn when there are no non-/working time added to save.
function disableBtn(){
    document.forms[0].delNonWrkHrs.disabled = false;
    document.forms[0].delNonWrkHrs.className = 'ScrnButton';

    document.forms[0].DelWorkingTimeBtn.disabled = false;
    document.forms[0].DelWorkingTimeBtn.className = 'ScrnButton';

    if (nonworkingHrsArray.length <= 0) {
        document.forms[0].delNonWrkHrs.disabled = true;
        document.forms[0].delNonWrkHrs.className = 'ScrnButton';
    }

    if (workingHrsArray.length <= 0) {
        document.forms[0].DelWorkingTimeBtn.disabled = true;
        document.forms[0].DelWorkingTimeBtn.className = 'ScrnButton';
    }
}
//-->
</script>
<script type="text/javascript" src="../javascript/special_bizhours.js">
</script>
<script type="text/javascript" src="../javascript/weekend.js">
</script>
<script type="text/javascript" src="../javascript/fixed_holiday.js">
</script>
<script type="text/javascript" src="../javascript/reg_holiday.js">
</script>
<script type="text/javascript" src="../javascript/cal_years.js">
</script>
<script>
<!--
<%
    ArrayList calendars = CalDataController.self().getCalendars();
    pageContext.setAttribute("calendars", calendars);
%>
<c:if test="${not empty calendars}">
  <c:forEach var="item" items="${calendars}" varStatus="i">
    <c:if test="${item != null}">
    calNames[<c:out value="${i.index}" />] = "<c:out value="${item.name}" />";
    </c:if>
  </c:forEach>
</c:if>
//-->
</script>
</head>
<body onLoad="setDefaults();disableBtn();">
<form name="AddBizCalendar" method="post" action="../calendar/bizcal_add.jsp" onsubmit="return validateCalDetails()">
<div id="northDiv">
    <%@ include file="../common/include_body.jsp" %>
    <input type="hidden" name="action" value="">
    <input type="hidden" name="years" value="">
    <input type="hidden" name="weekends" value="">
    <input type="hidden" name="specialBizHours" value="">
    <input type="hidden" name="fixedHolidays" value="">
    <input type="hidden" name="regularHolidays" value="">
    
    <input type="hidden" name="wrk_dow" value="">
    <input type="hidden" name="wrk_date" value="">
    <input type="hidden" name="wrk_time" value="">
    
    <input type="hidden" name="nonwrk_name" value="">
    <input type="hidden" name="nonwrk_type" value="">
    <input type="hidden" name="nonwrk_halfday" value="">
    <input type="hidden" name="nonwrk_year" value="">
    <input type="hidden" name="nonwrk_month" value="">
    <input type="hidden" name="nonwrk_date" value="">
    <input type="hidden" name="nonwrk_day" value="">
    <input type="hidden" name="nonwrk_occ" value="">

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
    
    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="breadcrumbSec">
    <tr>
        <td align="left" valign="middle" width="100%">
            <table border="0" cellspacing="0" cellpadding="0" id="breadcrumb">
                <tr>
                    <td><sbm:message key="administration" /></td>
                    <td class="bcSepIcon" valign="middle"><sbm:message key="system" /></td>
                    <td class="bcSepIcon" valign="middle"><a href="javascript:goToCalendarAdmin()" class="ActionLnk"><sbm:message key="calendars" /></a></td>
                    <td class="bcSepIcon" valign="middle"><sbm:message key="AddCalendar" /></td>
                </tr>
            </table>
        </td>
    </tr>
    </table>
</div>
    
<div id="cmdDiv">
    <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
    <tr align="center">
        <td class="ButtonDarkBg"> 
            <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="BtnSpace"> 
                    <input type="button" name="save" value=" <sbm:message key="save" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="javascript:return addCalendar();" />
                </td>
                <td class="BtnSpace"> 
                    <input type="button" name="resetbtn" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="resetAddBizCalendarForm();"/>
                </td>
                <td class="BtnSpace"> 
                    <input type="button" name="cancel" value="<sbm:message key="cancel" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="MM_goToURL('parent','bizcal_list.jsp');return document.MM_returnValue" />
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </table>
</div>

<div id="southDiv">
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
</div>

<div id="resultDiv">
    <div id="addCalendarTabsDiv">
    </div>
</div>
    
<div id="calendarGeneralTabContainer" class="x-hide-display">
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="SegTblEmboss" align="center"> <table width="100%" class="SegInbetweenTbl">
          <tr>
            <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="SegTblInBg"> <table class="SegDataTbl" cellpadding="0" cellspacing="1" border="0" width="100%">
                      <tr>
                        <td width="20%" class="SegFieldRt"><sbm:message key="Name" />:<span class="ApRequired">*</span></td>
                        <td width="80%" class="SegValLft"><input type="text" class="InptTxt" name="calName" size="40" maxlength="22"></td>
                      </tr>
                      <tr>
                        <td valign="top" class="SegFieldRtAlt"> <sbm:message key="Description" />:<span class="ApRequired">*</span></td>
                        <td class="SegValLftAlt"> <textarea name="calDesc" class="InptTxtArea" wrap="VIRTUAL" cols="40" rows="4"></textarea>
                        </td>
                      </tr>
                      <TR>
                        <td class="SegFieldRt"><sbm:message key="TimeZone" />:<span class="ApRequired">*</span></td>
                        <td class="SegValLft"> <select class="InptSelect" name="timezoneID">
                        <%@ include file="timezone_options.jsp" %>
                        </select></td>
                      </TR>
                      <tr>
                        <td class="SegFieldRtAlt"> <sbm:message key="YearRange" />:</td>
                        <td class="SegValLftAlt"><table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="SmlTxt"><sbm:message key="From" />:&nbsp;</td>
                              <td class="SmlTxt"><select class="InptSelect" name="yearFrom">
                                </select></td>
                              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                              <td class="SmlTxt"><sbm:message key="To" />:&nbsp;</td>
                              <td class="SmlTxt"><select class="InptSelect" name="yearTo" >
                               </select></td>
                            </tr>
                          </table></td>
                      </tr>
                      <tr>
                        <td class="SegFieldRt"><sbm:message key="RegularBusinessHrs" />:</td>
                        <td class="SegValLft"> <table cellSpacing="0" cellPadding="0" border="0">
                            <tbody>
                              <tr>
                                <td class="SmlTxt"><sbm:message key="StartTime" />:&nbsp;</td>
                                <td><select class="InptSelect" name="bizHoursStartTimeHrs">
                                  </select> </td>
                                <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                <td class="smltxt"><select class="inptselect" name="bizHoursStartTimeMins">
                                  </select></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td class="SmlTxt"><sbm:message key="EndTime" />:&nbsp;</td>
                                <td><select class="InptSelect" name="bizHoursEndTimeHrs">
                                  </select> </td>
                                <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                <td class="SmlTxt"><select class="InptSelect" name="bizHoursEndTimeMins">
                                  </select></td>
                              </tr>
                            </tbody>
                          </table></td>
                      </tr>
                      <tr>
                        <td class="SegFieldRtAlt"><sbm:message key="MidDayBreak" />:</td>
                        <td class="SegValLftAlt"> <table cellSpacing="0" cellPadding="0" border="0">
                            <tbody>
                              <tr>
                                <td class="SmlTxt"><sbm:message key="StartTime" />:&nbsp;</TD>
                                <td><select class="InptSelect" name="lunchStartTimeHrs">
                                  </select> </td>
                                <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                <td class="SmlTxt"><select class="InptSelect" name="lunchStartTimeMins">
                                  </select></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td class="SmlTxt"><sbm:message key="EndTime" />:&nbsp;</td>
                                <td><select class="InptSelect" name="lunchEndTimeHrs">
                                  </select> </td>
                                <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                <td class="SmlTxt"><select class=InptSelect name="lunchEndTimeMins">
                                  </select></td>
                              </tr>
                            </tbody>
                          </table></td>
                      </tr>
                      <tbody>
                      </tbody>
                    </table></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
  </div>

<div id="calendarWorkingTimeTabContainer" class="x-hide-display">
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="SegTblEmboss" align="center"><table width="100%" class="SegInbetweenTbl">
          <tr>
            <td> <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="SegTblBgDark"> <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="WorkingTable" name="WorkingTable">
                            <tr>
                              <td class="SegChkBoxSlctAll"> <input type="checkbox" class="InptChkBox" name="wchkbxAll" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="OnSelectAll(document.AddBizCalendar,this,'WorkingTable');">
                              </td>
                              <td class="SegColHdrSrNo"><sbm:message key="NoStr" /></td>
                              <td class="SegColHdr"> <sbm:message key="SingleDay" /></td>
                              <td class="SegColHdr"><sbm:message key="Date" /></td>
                              <td class="SegColHdr"> <sbm:message key="time" /></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td height="36"><input name="AddWorkingTimeBtn" type="button" class="ScrnButton" onclick="MM_openBrWindow('../calendar/pop_wrkhrs_add.jsp',win_name++,'width=670,height=230')" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" value="<sbm:message key="AddWorkingTime" />" />
                    <input type="button" name="DelWorkingTimeBtn" value="<sbm:message key="DelWorkingTime" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="deleteWorkingTime();" />
                  </td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
  </div>
  
<div id="calendarNonWorkingTimeTabContainer" class="x-hide-display">
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="SegTblEmboss" align="center"><table width="100%" class="SegInbetweenTbl">
          <tr>
            <td> <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                      <tr>
                        <td class="SegTblBgDark"> <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="NonWorkingTable" name="NonWorkingTable">
                            <tr>
                              <td class="SegChkBoxSlctAll"> <input type="checkbox" class="InptChkBox" name="nwchkbxAll" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="OnSelectAll(document.AddBizCalendar,this,'NonWorkingTable');">
                              </td>
                              <td class="SegColHdrSrNo"><sbm:message key="NoStr" /></td>
                              <td class="SegColHdr"><sbm:message key="Name" /></td>
                              <td class="SegColHdr"><sbm:message key="Type" /></td>
                              <td class="SegColHdr"><sbm:message key="Year" /></td>
                              <td class="SegColHdr"><sbm:message key="Month" /></td>
                              <td class="SegColHdr"><sbm:message key="Date" /></td>
                              <td class="SegColHdr"><sbm:message key="Day" /></td>
                              <td class="SegColHdr"><sbm:message key="Occurrence" /></td>
                            </tr>
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td height="36"><input name="addNonWrkHrs" type="button" class="ScrnButton" onclick="MM_openBrWindow('../calendar/pop_nonwrkhrs_add.jsp','addnonwrkhrs','width=642,height=260')" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" value="<sbm:message key="AddNonWorkingTime" />" />
                    <input type="button" name="delNonWrkHrs" value="<sbm:message key="DelNonWorkingTime" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="deleteNonWorkingTime();" />
                  </td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
    <script type="text/javascript">
    //<!--
        Ext.onReady(function(){
            var tabItems = new Array();
            tabItems.push({id: 'calendarGeneralTabId', contentEl:'calendarGeneralTabContainer', title: '<sbm:message key="General" />'});
            tabItems.push({id: 'calendarWorkingTimeTabId', contentEl:'calendarWorkingTimeTabContainer', title: '<sbm:message key="WorkingTime" />'});
            tabItems.push({id: 'calendarNonWorkingTimeTabId', contentEl:'calendarNonWorkingTimeTabContainer', title: '<sbm:message key="Non-WorkingTime" />'});

            var tabs = new Ext.TabPanel({
                renderTo: 'addCalendarTabsDiv',
                plain:true,
                activeTab: 0,
                border : true,
                frame:true,
                defaults: {autoHeight: true,autoWidth: true},
                items: tabItems
            });
            var viewport = new Bm.util.BmViewport('<sbm:message key="AddCalendar" />');
        });
    //-->   
    </script>
</form>
</body>
</html>
