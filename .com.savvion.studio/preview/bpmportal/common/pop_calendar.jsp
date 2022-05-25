<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
bizManage.setRequest(request);
bizManage.setResponse(response);

%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="calendar" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="../common/include_css.jsp">
    <jsp:param name="login" value="false" />
</jsp:include>
<%@ include file="../common/include_top.jsp" %>
<script type="text/javascript" language="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

var year;
var month;
var monthDate;
var weekDay;
var hour;
var minute;
var days = new Array(42);

function adjustYearUp()
{
    var yearSelect=document.datechooser.year;
    var newvalue;
    for (var i=0;i<7;i++)
    {
        newvalue = yearSelect.options[i].value;
        (newvalue--);
        yearSelect.options[i].value = ''+newvalue;
        yearSelect.options[i].text = ''+newvalue;
        yearSelect.options[i].label = ''+newvalue;
    }
    (year--);
    resetDisplay();
}

function adjustYearDown()
{
    var yearSelect=document.datechooser.year;
    var newvalue;
    for (var i=0;i<7;i++)
    {
        newvalue = yearSelect.options[i].value;
        (newvalue++);
        yearSelect.options[i].value = ''+newvalue;
        yearSelect.options[i].text = ''+newvalue;
        yearSelect.options[i].label = ''+newvalue;
    }
    (year++);
    resetDisplay();
}

function adjustYear()
{
    year = document.datechooser.year.value;
    resetDisplay();
}

function setValues()
{
    year = opener.year;
    month = opener.month;
    monthDate = opener.monthDate;
    weekDay = opener.weekDay;
    hour = opener.hour;
    minute = opener.minute;
    var yearSelect=document.datechooser.year;
    for (var i=0;i<7;i++)
    {
        yearSelect.options[i].value = ''+(year + (i - 2));
        yearSelect.options[i].text = ''+(year + (i - 2));
        yearSelect.options[i].label = ''+(year + (i - 2));
    }
    var adjHour = (hour<10)?'0'+hour:''+hour;
    var adjMinute = (minute<10)?'0'+minute:''+minute;
    document.datechooser.time.value = adjHour+':'+adjMinute;
    document.datechooser.ampm.selectedIndex = ((opener.ampm == 'PM')?1:0);
    //additional settings
    resetDisplay();
}

function setMonth(newmonth)
{
    month = newmonth;
    resetDisplay();
}

function setDay(day)
{
    if (days[day] != 'none')
    {
        monthDate = days[day];
        weekDay = (day%7);
        resetDisplay();
    }
}

function setHourMinute()
{
    var hm = document.datechooser.time.value;
    var colon = hm.indexOf(':');
    if (colon != -1)
    {
        hour = hm.substring(0,colon);
        minute = hm.substring(colon+1,hm.length);
    }
}

function resetDisplay()
{
    //for month
    setMonthClass(document.getElementById('janTD'),document.getElementById('janA'),0); //jan
    setMonthClass(document.getElementById('febTD'),document.getElementById('febA'),1); //feb
    setMonthClass(document.getElementById('marTD'),document.getElementById('marA'),2); //mar
    setMonthClass(document.getElementById('aprTD'),document.getElementById('aprA'),3); //apr
    setMonthClass(document.getElementById('mayTD'),document.getElementById('mayA'),4); //may
    setMonthClass(document.getElementById('junTD'),document.getElementById('junA'),5); //jun
    setMonthClass(document.getElementById('julTD'),document.getElementById('julA'),6); //jul
    setMonthClass(document.getElementById('augTD'),document.getElementById('augA'),7); //aug
    setMonthClass(document.getElementById('sepTD'),document.getElementById('sepA'),8); //sep
    setMonthClass(document.getElementById('octTD'),document.getElementById('octA'),9); //oct
    setMonthClass(document.getElementById('novTD'),document.getElementById('novA'),10); //nov
    setMonthClass(document.getElementById('decTD'),document.getElementById('decA'),11); //dec

    //for days
    var daysInMonth = getNumberOfDaysInMonth();
    if (monthDate > daysInMonth)
        monthDate = daysInMonth;

    var firstOfMonth = new Date(year, month, 1);

    //first row is special, need to pad.
    for (var i=0;i<firstOfMonth.getDay();i++)
    {
        var day = document.getElementById('day'+i);
        day.firstChild.nodeValue = ' ';
        day.className = 'CalDayLnk';
        var cell = document.getElementById('cell'+i);
        cell.className = 'CalDay';
        days[i] = 'none';
    }
    for (var i=1;i<=daysInMonth;i++)
    {
        if (i==monthDate)
        {
            days[(i+firstOfMonth.getDay()-1)] = i;
            var day = document.getElementById('day'+(i+firstOfMonth.getDay()-1));
            day.firstChild.nodeValue = ''+i;
            day.className = 'CalDayLnkcurrentDate';
            var cell = document.getElementById('cell'+(i+firstOfMonth.getDay()-1));
            cell.className = 'CalDayCurrentDate';
        }
        else
        {
            days[(i+firstOfMonth.getDay()-1)] = i;
            var day = document.getElementById('day'+(i+firstOfMonth.getDay()-1));
            day.firstChild.nodeValue = ''+i;
            day.className = 'CalDayLnk';
            var cell = document.getElementById('cell'+(i+firstOfMonth.getDay()-1));
            cell.className = 'CalDay';
        }
    }
    var lastday = firstOfMonth.getDay()+daysInMonth;
    for (var i=(lastday);i<42;i++)
    {
        var day = document.getElementById('day'+i);
        if(day.firstChild.nodeValue > 0)
            day.firstChild.nodeValue = ' ';
        days[i] = 'none';
        var cell = document.getElementById('cell'+i);
        cell.className = 'CalDay';
    }
}

function setMonthClass(tdele, aele, monthid)
{
    tdele.className = (month == monthid)?'CalMnthBgSlct':'CalMnthBg';
    aele.className = (month == monthid)?'CalMnthLnkSlct':'CalMnthLnk';
}

function getNumberOfDaysInMonth()
{
    var days;

    // Return 31 days
    if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month==11)
        days = 31;

    // Return 30 days
    else if (month == 3 || month == 5 || month == 8 || month ==10)
        days = 30;

    // Return 29 days
    else if (month == 1)
    {
        if (isLeapYear(year))
            days = 29;

        // Return 28 days
        else
            days = 28;
    }
    return (days);
}

function isLeapYear(lyear)
{
    if (((lyear % 4) == 0) && ((lyear % 100) != 0) || ((lyear % 400) == 0))
    {
        return (true);
    }
    else
    {
        return (false);
    }
}

function setDate()
{   if(monthDate == undefined)
    {   alert("Please select a valid date");
        return false;
    }
    else {
    var adjMonth = ((month < 9)?'0'+(month+1):(month+1));
    var adjDay = ((monthDate < 10)?'0'+monthDate:monthDate);
    var adjHour = (((hour < 10)&&((new String(hour)).length == 1))?'0'+hour:hour);
    var adjMinute = (((minute < 10)&&((new String(minute)).length == 1))?'0'+minute:minute);
    opener.datetimeControl.value = adjMonth+'/'+adjDay+'/'+year+' '+adjHour+':'+adjMinute+((document.datechooser.ampm.selectedIndex==1)?"PM":"AM");

    window.close();
    }
}
//-->
</script>
</head>
<body onload="setValues();MM_preloadImages('../css/<c:out value="${bizManage.theme}" />/images/calleft_on.gif','../css/<c:out value="${bizManage.theme}" />/images/calright_on.gif');">
<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <form name="datechooser" action="" method="post">
  <%@ include file="../common/include_form_body.jsp" %>
  <table width="100%" height="100%" border="0" cellpadding="2" cellspacing="4">
    <tr>
      <td align="center" valign="middle">
        <table border="0" cellspacing="1" cellpadding="0" class="CalTblLightBg">
          <tr>
            <td class="CalYearBg">
              <table border="0" align="center" width="100%">
                <tbody>
                <tr>
                  <td><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image4','','../css/<c:out value="${bizManage.theme}" />/images/calleft_on.gif',1)" onclick="adjustYearUp();"><img alt="Left" name="Image4" border="0" src="../css/<c:out value="${bizManage.theme}" />/images/calleft_off.gif" width="16" height="16" /></a></td>
                  <td class="CalYear" width="100%" align="center">
                    <select id="year" name="year" class="CalYearCombo" onchange="adjustYear();">
                      <option>2000</option>
                      <option>2001</option>
                      <option selected="selected">2002</option>
                      <option>2003</option>
                      <option>2004</option>
                      <option>2005</option>
                      <option>2006</option>
                    </select>
                  </td>
                  <td><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','../css/<c:out value="${bizManage.theme}" />/images/calright_on.gif',1)" onclick="adjustYearDown();"><img alt="Right" name="Image3" border="0" src="../css/<c:out value="${bizManage.theme}" />/images/calright_off.gif" width="16" height="16" /></a></td>
                </tr>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td align="center">
              <table cellspacing="1" width="100%">
                <tbody>
                <tr>
                  <td id="janTD" class="CalMnthBgSlct"><a id="janA" href="#" class="CalMnthLnkSlct" onclick="setMonth(0);"><sbm:message key="januaryAbbr" /></a></td>
                  <td nowrap id="febTD" class="CalMnthBg"><a id="febA" href="#" class="CalMnthLnk" onclick="setMonth(1);"><sbm:message key="februaryAbbr" /></a></td>
                  <td nowrap id="marTD" class="CalMnthBg"><a id="marA" href="#" class="CalMnthLnk" onclick="setMonth(2);"><sbm:message key="marchAbbr" /></a></td>
                  <td nowrap id="aprTD" class="CalMnthBg"><a id="aprA" href="#" class="CalMnthLnk" onclick="setMonth(3);"><sbm:message key="aprilAbbr" /></a></td>
                  <td nowrap id="mayTD" class="CalMnthBg"><a id="mayA" href="#" class="CalMnthLnk" onclick="setMonth(4);"><sbm:message key="mayAbbr" /></a></td>
                  <td nowrap id="junTD" class="CalMnthBg"><a id="junA" href="#" class="CalMnthLnk" onclick="setMonth(5);"><sbm:message key="juneAbbr" /></a></td>
                </tr>
                <tr>
                  <td nowrap id="julTD" class="CalMnthBg"><a id="julA" href="#" class="CalMnthLnk" onclick="setMonth(6);"><sbm:message key="julyAbbr" /></a></td>
                  <td nowrap id="augTD" class="CalMnthBg"><a id="augA" href="#" class="CalMnthLnk" onclick="setMonth(7);"><sbm:message key="augustAbbr" /></a></td>
                  <td nowrap id="sepTD" class="CalMnthBg"><a id="sepA" href="#" class="CalMnthLnk" onclick="setMonth(8);"><sbm:message key="septemberAbbr" /></a></td>
                  <td nowrap id="octTD" class="CalMnthBg"><a id="octA" href="#" class="CalMnthLnk" onclick="setMonth(9);"><sbm:message key="octoberAbbr" /></a></td>
                  <td nowrap id="novTD" class="CalMnthBg"><a id="novA" href="#" class="CalMnthLnk" onclick="setMonth(10);"><sbm:message key="novemberAbbr" /></a></td>
                  <td nowrap id="decTD" class="CalMnthBg"><a id="decA" href="#" class="CalMnthLnk" onclick="setMonth(11);"><sbm:message key="decemberAbbr" /></a></td>
                </tr>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table id="daytable" border="0" cellpadding="3" cellspacing="1" width="100%">
                <tbody>
                  <tr>
                    <td class="CalWeek"><sbm:message key="sundayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="mondayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="tuesdayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="wednesdayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="thursdayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="fridayAbbr" /></td>
                    <td class="CalWeek"><sbm:message key="saturdayAbbr" /></td>
                  </tr>
                  <tr>
                    <td id="cell0" class="CalDay" onclick="setDay(0);"><a id="day0" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell1" class="CalDay" onclick="setDay(1);"><a id="day1" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell2" class="CalDay" onclick="setDay(2);"><a id="day2" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell3" class="CalDay" onclick="setDay(3);"><a id="day3" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell4" class="CalDay" onclick="setDay(4);"><a id="day4" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell5" class="CalDay" onclick="setDay(5);"><a id="day5" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell6" class="CalDay" onclick="setDay(6);"><a id="day6" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td id="cell7" class="CalDay" onclick="setDay(7);"><a id="day7" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell8" class="CalDay" onclick="setDay(8);"><a id="day8" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell9" class="CalDay" onclick="setDay(9);"><a id="day9" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell10" class="CalDay" onclick="setDay(10);"><a id="day10" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell11" class="CalDay" onclick="setDay(11);"><a id="day11" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell12" class="CalDay" onclick="setDay(12);"><a id="day12" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell13" class="CalDay" onclick="setDay(13);"><a id="day13" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td id="cell14" class="CalDay" onclick="setDay(14);"><a id="day14" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell15" class="CalDay" onclick="setDay(15);"><a id="day15" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell16" class="CalDay" onclick="setDay(16);"><a id="day16" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell17" class="CalDay" onclick="setDay(17);"><a id="day17" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell18" class="CalDay" onclick="setDay(18);"><a id="day18" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell19" class="CalDay" onclick="setDay(19);"><a id="day19" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell20" class="CalDay" onclick="setDay(20);"><a id="day20" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td id="cell21" class="CalDay" onclick="setDay(21);"><a id="day21" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell22" class="CalDay" onclick="setDay(22);"><a id="day22" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell23" class="CalDay" onclick="setDay(23);"><a id="day23" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell24" class="CalDay" onclick="setDay(24);"><a id="day24" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell25" class="CalDay" onclick="setDay(25);"><a id="day25" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell26" class="CalDay" onclick="setDay(26);"><a id="day26" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell27" class="CalDay" onclick="setDay(27);"><a id="day27" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td id="cell28" class="CalDay" onclick="setDay(28);"><a id="day28" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell29" class="CalDay" onclick="setDay(29);"><a id="day29" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell30" class="CalDay" onclick="setDay(30);"><a id="day30" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell31" class="CalDay" onclick="setDay(31);"><a id="day31" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell32" class="CalDay" onclick="setDay(32);"><a id="day32" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell33" class="CalDay" onclick="setDay(33);"><a id="day33" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell34" class="CalDay" onclick="setDay(34);"><a id="day34" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td id="cell35" class="CalDay" onclick="setDay(35);"><a id="day35" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell36" class="CalDay" onclick="setDay(36);"><a id="day36" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell37" class="CalDay" onclick="setDay(37);"><a id="day37" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell38" class="CalDay" onclick="setDay(38);"><a id="day38" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell39" class="CalDay" onclick="setDay(39);"><a id="day39" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell40" class="CalDay" onclick="setDay(40);"><a id="day40" href="#" class="CalDayLnk">&nbsp;</a></td>
                      <td id="cell41" class="CalDay" onclick="setDay(41);"><a id="day41" href="#" class="CalDayLnk">&nbsp;</a></td>
                  </tr>
                  <tr>
                    <td colspan="7" bgcolor="#CCF1FF" CLASS="CalDateBgGrp">
                      <table border="0" align="center">
                        <tbody>
                          <tr>
                            <td class="CalTimeLabel"><sbm:message key="time" />:&nbsp;</td>
                            <td class="" align="center"> <input name="time"  onblur="setHourMinute();" class="CalInptTxtHhMm" value="" size="6" > </td>
                            <td><select name="ampm" class="CalInptSelectAmPm">
                                <option selected="selected"><sbm:message key="am" /></option>
                                <option><sbm:message key="pm" /></option>
                              </select></td>
                          </tr>
                        </tbody>
                      </table></td>
                  </tr>
                  <tr>
                    <td colspan="7" class="CalMainBtnBg">
                      <table border="0" align="center" cellpadding="0">
                        <tbody>
                          <tr>
                            <td class="" align="center"> <input type="submit" name="go" value=" <sbm:message key="ok" /> "   class="CalInptMainBtn"  onmouseover="this.className='CalInptMainBtnHover';" onmouseout="this.className='CalInptMainBtn';"  onclick="return setDate();">
                            </td>
                            <td>&nbsp;</td>
                            <td><input type="submit" name="cancel" value="<sbm:message key="cancel" />"  class="CalInptMainBtn"  onmouseover="this.className='CalInptMainBtnHover';" onmouseout="this.className='CalInptMainBtn';"  onclick="window.close()"></td>
                          </tr>
                        </tbody>
                      </table></td></tr>
                </tbody>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </form>
  <%@ include file="../common/include_bottom.jsp" %>
</div>
</body>
</html>
