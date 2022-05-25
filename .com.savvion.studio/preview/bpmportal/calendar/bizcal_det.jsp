<%@ page import="java.util.*,java.net.URLEncoder"%>  
<%@ page import="java.util.ArrayList,com.savvion.sbm.bpmportal.calendar.CalendarAction,
                com.savvion.sbm.bpmportal.calendar.SBMCalendarBean,
                com.savvion.sbm.calendar.CalDataController,
                com.savvion.sbm.calendar.svo.CalendarVO,
                com.savvion.sbm.calendar.svo.SBMCalendarInfo,
                com.savvion.sbm.calendar.util.CalendarUtil,
                com.savvion.sbm.bizmanage.pagegenerator.ServletTools,
                com.savvion.sbm.calendar.util.CalendarConstants,
                com.savvion.sbm.bpmportal.util.PortalConstants"%>
<%@ page errorPage="bizcal_error.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%!
    String convJS(Object s) {
    // Convert problem characters to JavaScript Escaped values
      if (s == null) {
        return "";
      }

      String t = (String)s;
      t = replace(t,"\\","\\\\"); // replace backslash with \\
      t = replace(t,"'","\\\'");  // replace an single quote with \'
      t = replace(t,"\"","\\\""); // replace a double quote with \"
      t = replace(t,"\r","\\r"); // replace CR with \r;
      t = replace(t,"\n","\\n"); // replace LF with \n;

      return t;
    }
    String replace(String s, String one, String another) {
    // In a string replace one substring with another
      if (s.equals("")) return "";
      String res = "";
      int i = s.indexOf(one,0);
      int lastpos = 0;
      while (i != -1) {
        res += s.substring(lastpos,i) + another;
        lastpos = i + one.length();
        i = s.indexOf(one,lastpos);
      }
      res += s.substring(lastpos);  // the rest
      return res;
    }
%>
<%
    CalendarVO calvo = null;
    String actn = request.getParameter("action");
    String calName = ServletTools.getUTFString(request,"calName");
    if(actn != null && (actn.trim().equals("deleteCal")))
    {
         CalendarAction.self().execute(request, response);
         response.sendRedirect("bizcal_cnf1.jsp?calName="+calName);
    }
    if(actn != null && (actn.trim().equals("createCal") ||
       actn.trim().equals("editCal"))) {
           CalendarAction.self().execute(request, response);
    } else {
        calName = ServletTools.getUTFString(request,"calName");
        calvo = calName != null ?
                           CalDataController.self().getCalendarMetaData(calName) : null;
    }
    boolean edit = false;
    if(calvo != null) {
        edit = true;
    }

    HashMap calYears = calvo.getYears();
    Set yrSet = calYears.keySet();
    String yrFrm = Collections.min(yrSet).toString();
    String yrTo = Collections.max(yrSet).toString();

    String lunchStTime = calvo.getLunchStartTime();
    String lunchStTimeHrs = lunchStTime.substring(0,2);
    String lunchStTimeMins = lunchStTime.substring(2);

    String lunchEdTime = calvo.getLunchEndTime();
    String lunchEdTimeHrs = lunchEdTime.substring(0,2);
    String lunchEdTimeMins = lunchEdTime.substring(2);

    String regBusinessStTime = calvo.getRegularBusinessStartTime();
    String regBusinessEdTime = calvo.getRegularBusinessEndTime();

    //Correction for Night Shift
    try {
        int sttm = Integer.parseInt(regBusinessStTime);
        int edtm = Integer.parseInt(regBusinessEdTime);
        if(edtm >= 2400)
            edtm = edtm - 2400;
        regBusinessEdTime = "0000"+String.valueOf(edtm);
        regBusinessEdTime = regBusinessEdTime.substring(regBusinessEdTime.length()-4);
    } catch(Exception e) { }

    String regBusinessStTimeHrs = regBusinessStTime.substring(0,2);
    String regBusinessStTimeMins = regBusinessStTime.substring(2);

    String regBusinessEdTimeHrs = regBusinessEdTime.substring(0,2);
    String regBusinessEdTimeMins = regBusinessEdTime.substring(2);

    String tmzId = calvo.getTimeZoneID();
    boolean isSystem = false;
    SBMCalendarInfo sci = CalDataController.self().getCalendar(calName);
        isSystem = sci.isSystemCalendar();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="CalendarDetails" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="../css/app<%=bizManage.getTheme()%>/sbm_app01.css" type="text/css"/>
<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript" src="../javascript/utilities.js"></script>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script type="text/javascript" src="../javascript/workingtime.js"></script>
<script type="text/javascript" src="../javascript/nonworkingtime.js"></script>
<script language="JavaScript">
<!--
// Initialization of global JavaScript variables
var DELIMITER = "<%= com.savvion.sbm.calendar.util.CalendarUtil.getDelimiter() %>";
var FORMAT_DOW = "<%= com.savvion.sbm.calendar.util.CalendarConstants.FORMAT_DOW %>";
var FORMAT_DAY = "<%= com.savvion.sbm.calendar.util.CalendarConstants.FORMAT_DAY %>";
var workingHrsArray = new Array();
var nonworkingHrsArray = new Array();

function validateCalDetails()
{
    if(!validateCalendar(false))
    {
        return false;
    }

    if(document.forms[0].calDesc.value.length == 0 || trimString(document.forms[0].calDesc.value).length == 0)
    {
        alert('Please enter calendar Description');
        return false;
    }

    return true;
}

function deleteCalendar()
{
    if (confirm("Are you sure you want to delete "+document.forms[0].calName.value +"  calendar? ")) {
        document.forms[0].action.value='deleteCal';
    document.forms[0].submit();
    return true;
    }
}

function editCalendar()
{
    if(!validateCalDetails()) {
        return false;
    }

    document.forms[0].action.value='editCal';

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
        sttm =  parseInt(sttm,10)
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
    //alert('Regular Holidays set to - '+document.forms[0].regularHolidays.value);

    //alert("Business Hours Start Time(hrs) - "+document.forms[0].bizHoursStartTimeHrs.value);

    //alert("Business Hours Start Time(mins) - "+document.forms[0].bizHoursStartTimeMins.value);
    //alert("Business Hours End Time(hrs) - "+document.forms[0].bizHoursEndTimeHrs.value);
    //alert("Business Hours End Time(mins) - "+document.forms[0].bizHoursEndTimeMins.value);
    document.forms[0].submit();
    return true;
}

function setDefaults()
{
    fillYearDropDown('yearFrom',2005,2030);
    fillYearDropDown('yearTo',2005,2030);
    fillHoursDropDown("lunchStartTimeHrs");
    fillMinutesDropDown("lunchStartTimeMins");
    fillHoursDropDown("lunchEndTimeHrs");
    fillMinutesDropDown("lunchEndTimeMins");
    fillHoursDropDown("bizHoursStartTimeHrs");
    fillMinutesDropDown("bizHoursStartTimeMins");
    fillHoursDropDown("bizHoursEndTimeHrs");
    fillMinutesDropDown("bizHoursEndTimeMins");


    //show the calendar data
    selectValueInDropDown(eval ("document.forms[0].yearFrom"), '<%=yrFrm%>');
    selectValueInDropDown(eval ("document.forms[0].yearTo"), '<%=yrTo%>');
    selectValueInDropDown(eval ("document.forms[0].lunchStartTimeHrs"), '<%=lunchStTimeHrs%>');
    selectValueInDropDown(eval ("document.forms[0].lunchStartTimeMins"), '<%=lunchStTimeMins%>');
    selectValueInDropDown(eval ("document.forms[0].lunchEndTimeHrs"), '<%=lunchEdTimeHrs%>');
    selectValueInDropDown(eval ("document.forms[0].lunchEndTimeMins"), '<%=lunchEdTimeMins%>');
    selectValueInDropDown(eval ("document.forms[0].bizHoursStartTimeHrs"), '<%=regBusinessStTimeHrs%>');
    selectValueInDropDown(eval ("document.forms[0].bizHoursStartTimeMins"), '<%=regBusinessStTimeMins%>');
    selectValueInDropDown(eval ("document.forms[0].bizHoursEndTimeHrs"), '<%=regBusinessEdTimeHrs%>');
    selectValueInDropDown(eval ("document.forms[0].bizHoursEndTimeMins"), '<%=regBusinessEdTimeMins%>');
    selectValueInDropDown(eval ("document.forms[0].timezoneID"), '<%=tmzId%>');
    document.forms[0].calDesc.value = '<%= convJS(calvo.getDescription()) %>';

    refreshWorkingTable('WorkingTable');
    refreshNonWorkingTable('NonWorkingTable');
    return true;
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
</head>
<%
    ArrayList spBusinessHours = calvo.getSpecialBusinessHours();
    if(spBusinessHours != null && spBusinessHours.size() > 0)
    {
        int counter=0;
        for(int i=0;i<spBusinessHours.size();i++)
        {
            HashMap sbhMap = (HashMap)spBusinessHours.get(i);

            if(sbhMap != null) {
                String sttm = (String)sbhMap.get(CalendarConstants.START_TIME);
                String edtm = (String)sbhMap.get(CalendarConstants.END_TIME);
                //Correction for Night Shift
                try {
                    int sttmVal = Integer.parseInt(sttm);
                    int edtmVal = Integer.parseInt(edtm);
                    if(edtmVal >= 2400)
                        edtmVal = edtmVal-2400;
                    sttm = "0000"+String.valueOf(sttmVal);
                    sttm = sttm.substring(sttm.length()-4);
                    edtm = "0000"+String.valueOf(edtmVal);
                    edtm = edtm.substring(edtm.length()-4);
                } catch(Exception e) { }
                String dy = (String)sbhMap.get(CalendarConstants.DAY_OF_WEEK);
                sttm=sttm.substring(0,2)+":"+sttm.substring(2);
                edtm=edtm.substring(0,2)+":"+edtm.substring(2);
                String formatDate = "";
                String formatTime = sttm+"-"+edtm;

                if(dy == null || "".equals(dy.trim())) dy = "-";
                String yr = (String)sbhMap.get(CalendarConstants.YEAR);
                if(yr == null) yr = "-";
                String mn = (String)sbhMap.get(CalendarConstants.MONTH);
                if(mn == null) mn = "-";
                String dt = (String)sbhMap.get(CalendarConstants.DAY);
                if(dt == null || "".equals(dt.trim())) dt = "-";
                if("-".equals(dy))
                    formatDate=mn.substring(0,3)+" "+dt+", "+yr;
                else
                    formatDate=dt;
%>
                <script>
                    workingHrsArray[<%=counter%>]=new workingHrs('<%=dy%>','<%=formatDate%>','<%=formatTime%>');
                </script>
<%
                counter++;
            }
        }
    }

    ArrayList weekends = calvo.getWeekends();
    int counter=0;
    if(weekends != null && weekends.size() > 0)
    {
        for(int i=0;i<weekends.size();i++)
        {
            HashMap wndMap = (HashMap)weekends.get(i);

            if(wndMap != null) {
                String nm = (String)wndMap.get(CalendarConstants.NAME);
                String dow = (String)wndMap.get(CalendarConstants.DAY_OF_WEEK);
                String occ = (String)wndMap.get(CalendarConstants.OCCURRENCE);
                if(occ == null) occ="all";

                String typ = "Weekend";
                String hd = "";
                String yr = "-";
                String mn = "-";
                String dt = "-";
%>
                <script>
                    var escnm = '<%=convJS(nm)%>';
                    nonworkingHrsArray[<%=counter%>]=new nonworkingHrs(escnm,'<%=typ%>','<%=hd%>','<%=yr%>','<%=mn%>','<%=dt%>','<%=dow%>','<%=occ%>');
                </script>
<%
                counter++;
            }
        }
    }
    ArrayList fixedHolidays = calvo.getFixedHolidays();
    if(fixedHolidays != null && fixedHolidays.size() > 0)
    {
        for(int i=0;i<fixedHolidays.size();i++)
        {
            HashMap fhMap = (HashMap)fixedHolidays.get(i);
            if(fhMap != null) {
                String nm = (String)fhMap.get(CalendarConstants.NAME);
                String typ = "Holiday";
                String hd = (String)fhMap.get(CalendarConstants.HALF_DAY);
                String yr = "All";
                String mn = (String)fhMap.get(CalendarConstants.MONTH);
                String dt = (String)fhMap.get(CalendarConstants.DAY);
                if(dt == null) dt = "-";
                String dow = (String)fhMap.get(CalendarConstants.DAY_OF_WEEK);
                if(dow == null) dow = "-";
                String occ = (String)fhMap.get(CalendarConstants.OCCURRENCE);
                if(occ == null) occ = "-";
%>
                <script>
                    var escnm = '<%=convJS(nm)%>';
                    nonworkingHrsArray[<%=counter%>]=new nonworkingHrs(escnm,'<%=typ%>','<%=hd%>','<%=yr%>','<%=mn%>','<%=dt%>','<%=dow%>','<%=occ%>');
                </script>
<%
                counter++;
            }
        }
    }
    ArrayList regularHolidays = calvo.getRegularHolidays();
    if(regularHolidays != null && regularHolidays.size() > 0)
    {
        for(int i=0;i<regularHolidays.size();i++)
        {
            HashMap rhMap = (HashMap)regularHolidays.get(i);
            if(rhMap != null) {
                String nm = (String)rhMap.get(CalendarConstants.NAME);
                String typ = "Holiday";
                String hd = (String)rhMap.get(CalendarConstants.HALF_DAY);
                String yr = (String)rhMap.get(CalendarConstants.YEAR);
                String mn = (String)rhMap.get(CalendarConstants.MONTH);
                String dt = (String)rhMap.get(CalendarConstants.DAY);
                if(dt == null) dt = "-";
                String dow = (String)rhMap.get(CalendarConstants.DAY_OF_WEEK);
                if(dow == null) dow = "-";
                String occ = (String)rhMap.get(CalendarConstants.OCCURRENCE);
                if(occ == null) occ = "-";
%>
                <script>
                    var escnm = '<%=convJS(nm)%>';
                    nonworkingHrsArray[<%=counter%>]=new nonworkingHrs(escnm,'<%=typ%>','<%=hd%>','<%=yr%>','<%=mn%>','<%=dt%>','<%=dow%>','<%=occ%>');
                </script>
<%
                counter++;
            }
        }
    }
%>
<body>
 <%@ include file="../common/include_body.jsp" %>
<form name="BizCalendarDetails" method="post" action="../calendar/bizcal_det.jsp">
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
<input type="hidden" name="isSystem" value="<%= String.valueOf(isSystem) %>" />

<div id="northDiv">
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
        <td align="left" valign="middle" width="80%">
            <table border="0" cellspacing="0" cellpadding="0" id="breadcrumb">
                <tr>
                    <td class="SmlTxt" height="20">&nbsp;&nbsp;<a href="../calendar/bizcal_list.jsp" class="ActionLnk"><sbm:message key="Administration" />:&nbsp;<sbm:message key="System" />&nbsp;(<sbm:message key="CalendarList" />)</a>&nbsp;&gt;&nbsp;<b><sbm:message key="CalendarDetails" /></b></td>
                </tr>
            </table>
        </td>
        <td valign="middle" align="right" width="20%">
            <%
                Calendar aCalendar = Calendar.getInstance();
                int aYear = aCalendar.get(Calendar.YEAR);
            %>
          <a href=javascript:encodeHrefUrl("../calendar/bizcal_view.jsp?calName=<%=URLEncoder.encode(calName,PortalConstants.UTF8)%>&year=<%=aYear%>") class="PsvTitleLnk"><sbm:message key="CalendarView" /></a>
        </td>
    </tr>
    </table>
</div>
<div id="cmdDiv">
    <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
    <tr align="center">
        <td align="center" class="ButtonDarkBg">
            <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td class="BtnSpace"> <input type="button" name="save" value=" <sbm:message key="save" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="return editCalendar();" />
                </td>
                <td class="BtnSpace"> <input type="button" name="reset" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="setDefaults();dynamicOnSelectAll(document.BizCalendarDetails,this,'wchkbxAll','WorkingTable',1,-1);dynamicOnSelectAll(document.BizCalendarDetails,this,'nwchkbxAll','NonWorkingTable',1,-1);this.title='';" />
                </td>
                <td class="BtnSpace"><input type="button" name="delete" value="<sbm:message key="Delete" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="return deleteCalendar();" /></td>
                <td class="BtnSpace"> <input type="button" name="cancel" value="<sbm:message key="cancel" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="MM_goToURL('parent','../calendar/bizcal_list.jsp');return document.MM_returnValue" />
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
            <%@ include file="../common/include_bottom.jsp" %>
        </td>
    </tr>
    </table>
</div>
<div id="resultDiv">
    <div id="addCalendarTabsDiv">
    </div>
</div>
<div id="GeneralId" class="x-hide-display">
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="SegTblEmboss" align="center">
        <table width="100%" class="SegInbetweenTbl">
          <tr>
            <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="SegTblInBg">
                    <table width="100%" class="SegDataTbl" cellpadding="4" cellspacing="1" border="0">
                     <tbody>
                      <tr>
                        <td class="SegFieldRt" width="20%"><sbm:message key="Name" />:</td>
                        <td class="SegValLft"><%=calvo.getCalendarName()%></td>
                        <input type="hidden" name="calName" value="<%=calvo.getCalendarName()%>"  />
                      </tr>
                      <tr>
                        <td class="SegFieldRtAlt"><sbm:message key="Description" />:<span class="ApRequired">*</span></td>
                        <td class="SegValLftAlt">
                          <textarea name="calDesc" class="InptTxtArea" wrap="VIRTUAL" cols="40" rows="4"><%=calvo.getDescription()%></textarea>
                        </td>
                      </tr>
                      <tr>
                        <td class="SegFieldRt" valign="top"><sbm:message key="TimeZone" />:<span class="ApRequired">*</span></td>
                        <td class="SegValLft"><select class="InptSelect" name="timezoneID">
                        <%@ include file="timezone_options.jsp" %>
                        </select>
                        </td>
                      </tr>
                      <tr>
                        <td class="SegFieldRtAlt" valign="top"> <sbm:message key="YearRange" />:</td>
                        <td class="SegValLftAlt">
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="SmlTxt"><sbm:message key="From" />:&nbsp;</td>
                              <td class="SmlTxt"><select class="InptSelect" name="yearFrom">
                                </select></td>
                              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                              <td class="SmlTxt"><sbm:message key="To" />:&nbsp;</td>
                              <td class="SmlTxt"><select class="InptSelect" name="yearTo" >
                               </select></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td class="SegFieldRt" valign="top"><sbm:message key="RegularBusinessHrs" />:</td>
                        <td class="SegValLft"> 
                          <table cellspacing="0" cellPadding="0" border="0">
                            <tbody>
                              <tr>
                                <td class="SmlTxt"><sbm:message key="StartTime" />:&nbsp;</td>
                                <td><select class="InptSelect" name="bizHoursStartTimeHrs">
                                  </select> </td>
                                <td class="SmlTxt">&nbsp;:&nbsp;</td>
                                <td class="SmlTxt"><select class="InptSelect" name="bizHoursStartTimeMins">
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
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td class="SegFieldRtAlt"><sbm:message key="MidDayBreak" />:</td>
                        <td class="SegValLftAlt"> 
                          <table cellspacing="0" cellpadding="0" border="0">
                            <tbody>
                              <tr>
                                <td class="SmlTxt"><sbm:message key="StartTime" />:&nbsp;</td>
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
                                <td class="SmlTxt"><select class="InptSelect" name="lunchEndTimeMins">
                                  </select></td>
                              </tr>
                            </tbody>
                          </table></td>
                      </tr>
                      </tbody>
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
</div>
<div id="WorkingId" class="x-hide-display">
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="SegTitle">&nbsp;&nbsp;<sbm:message key="WorkingTime" /></td>
    </tr>
    <tr>
      <td class="SegTblEmboss" align="center"><table width="100%" class="SegInbetweenTbl">
          <tr>
            <td> <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="SegTblBgDark"> <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="WorkingTable" name="WorkingTable">
                            <tr>
                              <td class="SegChkBoxSlctAll"><input type="checkbox" class="InptChkBox" name="wchkbxAll" value="wcheckbox" title="Select All" onmouseover="changeTip(this);" onclick="OnSelectAll(document.BizCalendarDetails,this,'WorkingTable');">
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
                  <td height="36"><input name="AddWorkingTimeBtn" type="button" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="MM_openBrWindow('../calendar/pop_wrkhrs_add.jsp','addworkinghours','width=642,height=260')"  value='<sbm:message key="AddWorkingTime" />...' />
                    <input type="button" name="DelWorkingTimeBtn" value='<sbm:message key="DelWorkingTime" />' class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="deleteWorkingTime();" />
                  </td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>
<div id="NonWorkingId" class="x-hide-display">
  <table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
      <td class="SegTitle">&nbsp;&nbsp;<sbm:message key="Non-WorkingTime" /></td>
    </tr>
    <tr>
      <td class="SegTblEmboss" align="center"><table width="100%" class="SegInbetweenTbl">
          <tr>
            <td> <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                      <tr>
                        <td class="SegTblBgDark"> <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="NonWorkingTable" name="NonWorkingTable">
                            <tr>
                              <td class="SegChkBoxSlctAll"> <input type="checkbox" class="InptChkBox" name="nwchkbxAll" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="OnSelectAll(document.BizCalendarDetails,this,'NonWorkingTable');">
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
                  <td height="36"><input name="addNonWrkHrs" type="button" onclick="MM_openBrWindow('../calendar/pop_nonwrkhrs_add.jsp','addnonwrkhrs','width=642,height=260')" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" value='<sbm:message key="AddNonWorkingTime" />' />
                    <input type="button" name="delNonWrkHrs" value='<sbm:message key="DelNonWorkingTime" />' class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="deleteNonWorkingTime();" />
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
        tabItems.push({id: 'GeneralTabId', contentEl:'GeneralId', title: '<sbm:message key="General" />'});
        tabItems.push({id: 'WorkingTabId', contentEl:'WorkingId', title: '<sbm:message key="WorkingTime" />'});
        tabItems.push({id: 'NonWorkingTabId', contentEl:'NonWorkingId', title: '<sbm:message key="Non-WorkingTime" />'});
        var tabs = new Ext.TabPanel({
            renderTo: 'addCalendarTabsDiv',
            plain:true,
            activeTab: 0,
            border : true,
            frame:true,
            defaults: {autoHeight: true,autoWidth: true},
            items: tabItems
        });
        var viewport = new Bm.util.BmViewport('<sbm:message key="CalendarDetails" />');
        setCheckBoxStyleForIE();setDefaults();disableBtn();
    });
//-->   
</script>
</form>
</body>
</html>