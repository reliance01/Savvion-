


// Date format  MM : Month
//              DD : Day
//              YY : Year
//              HH : Hour
//              NN : Minute
//              AP : AM / PM
myFormat    = "MM/DD/YY HH:NNAP";


// CALENDAR COLORS
background       = "#CCCCFF";       // BG COLOR OF THE CALENDAR
tableBGColor     = "black";         // BG COLOR OF THE BOTTOM FRAME'S TABLE
cellColor        = "lightgrey";     // TABLE CELL BG COLOR OF THE DATE CELLS IN THE BOTTOM FRAME
headingCellColor = "white";         // TABLE CELL BG COLOR OF THE WEEKDAY ABBREVIATIONS
headingTextColor = "black";         // TEXT COLOR OF THE WEEKDAY ABBREVIATIONS
dateColor        = "blue";          // TEXT COLOR OF THE LISTED DATES (1-28+)
focusColor       = "#ff0000";       // TEXT COLOR OF THE SELECTED DATE (OR CURRENT DATE)
hoverColor       = "darkred";       // TEXT COLOR OF A LINK WHEN YOU HOVER OVER IT
fontStyle        = "9pt arial, helvetica";           // TEXT STYLE FOR DATES
headingFontStyle = "bold 10pt arial, helvetica";      // TEXT STYLE FOR WEEKDAY ABBREVIATIONS

// FORMATTING PREFERENCES
bottomBorder  = false;        // TRUE/FALSE (WHETHER TO DISPLAY BOTTOM CALENDAR BORDER)
tableBorder   = 0;            // SIZE OF CALENDAR TABLE BORDER (BOTTOM FRAME) 0=none


year      = 0;
month     = 0;
monthDate = 1;
weekDay   = 0;
hour      = 0;
minute    = 0;
ampm      = 'AM';


var debug = false;

// DETERMINE BROWSER BRAND
var isNav = false;
var isIE  = false;

// ASSUME IT'S EITHER NETSCAPE OR MSIE
if (navigator.appName == "Netscape") {

    isNav = true;
}
else {

    isIE = true;
}

function rebuildCalendar() {

    if (eval(debug)) {

        alert("rebuildCalendar(): " + month + "/" + monthDate + "/" + year + " " + hour + ":" + minute + ampm);
    } 

    top.newWin.document.open();
    top.newWin.document.write("<HTML>\r\n");
    top.newWin.document.write(buildHeaderHTML() + '\r\n');
    top.newWin.document.write(buildCalendar() + '\r\n');
    top.newWin.document.write("</HTML>" + '\r\n');
    top.newWin.document.close();
}

function setDateTimeControl(datetime) {

    if (eval(debug)) {

       alert("setDateTimeControl(): datetime: " + datetime.value );
    }
    datetimeControl = datetime;
    init(datetime.value);
}

function init(datetimeString) {

    if (eval(debug)) {

       alert("init(): datetimeString: " + datetimeString);
    }
    var dateInLong = date2Long(datetimeString);
    if (eval(debug)) {

       alert("init(): date in long: " + dateInLong);
    }
    var myDate = new Date();
    ampm = 'AM';
    myDate.setTime(dateInLong);
    month     = myDate.getMonth();
    year      = myDate.getFullYear();
    monthDate = myDate.getDate();
    weekDay   = myDate.getDay();
    hour      = myDate.getHours();
    minute    = myDate.getMinutes();
    if (eval(debug)) {

       alert("init(): date locale string: " + myDate.toLocaleString());
    }

    if (hour > 12) {

        hour -= 12;
        ampm = 'PM';
    }
}

function date2Long(calendarString) {

    if (eval(debug)) {

       alert("date2Long(): calendarString: " + calendarString);
    }

    if (calendarString == "") {

        var nd = new Date();
        return nd.getTime();
    }

    var datetime = calendarString.split(" ", 2);
    var d = new Date(datetime[0]);
    var ap = datetime[1].substr(datetime[1].length - 2, 2);
    var hourminute = datetime[1].substr(0, datetime[1].length -2);
    var hhmm = hourminute.split(":", 2);

    if (eval(debug)) {

       alert("date2Long(): datetime: " + datetime + "hourminute: " + hhmm);
    }
    if (ap == 'PM' && hhmm[0] < 12) {

        hhmm[0] =  new Number(hhmm[0]) + new Number(12);
    }

    d.setHours(hhmm[0]);
    d.setMinutes(hhmm[1]);
    if (eval(debug)) {

       alert("date2Long(): time in long: " + d.getTime());
    }
    return d.getTime();
}

function long2Date(datetime) {

    if (eval(debug)) {

        alert("long2Date() : control value before converting: " + datetime.value);
    }
    datetime.value = convertLong2Date(datetime.value);
    if (eval(debug)) {

        alert("long2Date() : control value after converting: " + datetime.value);
    }
}

function convertLong2Date(dlong) {

    if (eval(debug)) {

        alert("convertLong2Date() : dlong: " + dlong);
    }

    var d = new Date();
    
    if (!isNaN(dlong)) {

        dlong = Number(dlong);
    }

    if (!isNaN(dlong) && dlong != 0) {

        d.setTime(dlong);
    }
 
    if (eval(debug)) {

        alert("convertLong2Date() : locale string: " + d.toLocaleString());
    }

    var result = "";

    if (d.getMonth() < 9) {

        result += '0';
    }

    result += ((d.getMonth() + 1) + '/');

    if (d.getDate() < 10) {

        result += '0';
    }

    result += (d.getDate() + '/' + d.getFullYear() + ' ');
    var hh = d.getHours();
    var ap = 'AM';
    if (hh >= 12) {

        ap = 'PM';
        hh -= 12;
    }

    if (hh < 10) {

        result += '0';
    }

    result += (hh + ':');
    if (d.getMinutes() < 10) {

        result += '0';
    }

    result += (d.getMinutes() + ap);

    if (eval(debug)) {

        alert("convertLong2Date() : date formated: " + result);
    }
    //result += (hh + ':' + d.getMinutes() + ap);
    return result;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
// build calendar and its segement

function buildCalendar() {

    if (eval(debug)) {

        alert("buildCalendar(): " + month + "/" + monthDate + "/" + year + " " + hour + ":" + minute + ampm);
    } 

    calendarHTML =
        //buildHeaderHTML() +
        "<BODY BGCOLOR=" + background + ">\r\n" +
        "<FORM NAME=calControl onSubmit='return false;'>\r\n" +
        buildMonthYearHTML() + "\r\n" +
        "<HR>\r\n" +
        buildDateTableHTML() + "\r\n" +
        "</FORM>\r\n" +
        "</BODY>\r\n";
    return calendarHTML;
}

// CREATE HEADER
function buildHeaderHTML() {

    var headerHTML = 
        "<HEAD>\r\n" +
        "<STYLE type='text/css'>\r\n" +
        "<!--\r\n" +
        "TD.heading { text-decoration: none; color:" + headingTextColor + "; font: " + headingFontStyle + "; }\r\n" +
        "A.focusDay:link { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }\r\n" +
        "A.focusDay:visited { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }\r\n" +
        "A.focusDay:hover { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }\r\n" +
        "A.weekday:link { color: " + dateColor + "; text-decoration: none; font: " + fontStyle + "; }\r\n" +
        "A.weekday:visited { color: " + dateColor + "; text-decoration: none; font: " + fontStyle + "; }\r\n" +
        "A.weekday:hover { color: " + hoverColor + "; font: " + fontStyle + "; }\r\n" +
        "DIV.monthyear { position: static; visibility: show }\r\n" +
        "DIV.datetable { position: static; visibility: show }\r\n" +
        "DIV.hourminute { position: static; visibility: show }\r\n" +
        "-->\r\n" +
        "</STYLE>\r\n" +
        "</HEAD>\r\n";
    return headerHTML;
}

// CREATE THE TOP CALENDAR FRAME
function buildMonthYearHTML() {

    // CREATE THE TOP FRAME OF THE CALENDAR
    var monthYearHTML =
        "<DIV ID='monthyear' BGCOLOR='" + background + "' ALIGN='CENTER' VALIGN='MIDDLE'>\r\n" +
        //"<CENTER>\r\n" +
        "<TABLE CELLPADDING=0 CELLSPACING=1 BORDER=0>\r\n" +
        "<TR><TD COLSPAN=7 ALIGN='CENTER' VALIGN='MIDDLE'>\r\n" +
        buildMonthHTML() + 
        "<INPUT NAME='year' VALUE='" + year + "'TYPE=TEXT SIZE=4 MAXLENGTH=4 onClick='blur()'>\r\n" +
        "</TD>\r\n" +
        "</TR>\r\n" +
        "<TR>\r\n" +
        "<TD COLSPAN=7>\r\n" +
        "<INPUT " +
        "TYPE=BUTTON NAME='previousYear' VALUE='<<' onClick='parent.opener.setPreviousYear()'><INPUT " +
        "TYPE=BUTTON NAME='previousMonth' VALUE=' < ' onClick='parent.opener.setPreviousMonth()'><INPUT " +
        "TYPE=BUTTON NAME='today' VALUE='Today' onClick='parent.opener.setToday()'><INPUT " +
        "TYPE=BUTTON NAME='nextMonth' VALUE=' > ' onClick='parent.opener.setNextMonth()'><INPUT " +
        "TYPE=BUTTON NAME='nextYear' VALUE='>>' onClick='parent.opener.setNextYear()'>" +
        "</TD>\r\n" +
        "</TR>\r\n" +
        "</TABLE>\r\n" +
        //"</CENTER>\r\n";
        "</DIV>\r\n";

    return monthYearHTML;
}

function buildMonthHTML() {

    var monthArray = new Array('January', 'February', 'March', 'April', 'May', 'June',
                               'July', 'August', 'September', 'October', 'November', 'December');

    monthHTML = "<SELECT NAME='month' onChange='parent.opener.setCurrentMonth()'>\r\n";

    for (i in monthArray) {
        
        // SHOW THE CORRECT MONTH IN THE SELECT LIST
        if (i == month) {

            monthHTML += "<OPTION SELECTED>" + monthArray[i] + "\r\n";
        }
        else {

            monthHTML += "<OPTION>" + monthArray[i] + "\r\n";
        }
    }
    monthHTML += "</SELECT>";

    return monthHTML;
}


function buildDateTableHTML() {       

    // START CALENDAR DOCUMENT
    var dateTableHTML =
        //"<CENTER>\r\n";
        "<DIV ID='datetable' BGCOLOR='" + background + "' ALIGN='CENTER' VALIGN='MIDDLE'>\r\n";


    dateTableHTML +=
        buildDateTableContentHTML()+
        //"</CENTER>\r\n" +
        "</DIV>\r\n" +
        "<HR>\r\n" +
        "<DIV ID='hourminute' BGCOLOR='" + background + "' ALIGN='CENTER' VALIGN='MIDDLE'>\r\n" +
        //"<CENTER>\r\n" +
        buildHourMinuteHTML(hour, minute) + "\r\n" +
        //"</CENTER>\r\n";
        "</DIV>\r\n";

    // RETURN THE COMPLETED CALENDAR PAGE
    return dateTableHTML;
}

function buildDateTableContentHTML() {
      
    var blankCell = "<TD align=center bgcolor='" + cellColor + "'>&nbsp;&nbsp;&nbsp;</TD>\r\n";
    var dateTableHTML = "";

    // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE TABLE OUTLINES PROPERLY
    if (isNav) {

        dateTableHTML += 
            "<TABLE CELLPADDING=0 CELLSPACING=1 BORDER=" + tableBorder + 
            " ALIGN=CENTER BGCOLOR='" + tableBGColor + "'>\r\n<TR>\r\n<TD>";
    }

    // BUILD WEEKDAY HEADINGS
    dateTableHTML +=
        "<TABLE CELLPADDING=0 CELLSPACING=1 BORDER=" + tableBorder + 
        " ALIGN=CENTER BGCOLOR='" + tableBGColor + "'>\r\n" +
        buildWeekTitleHTML() + "\r\n" +
        "<TR>\r\n";

    var i   = 0;

    // DETERMINE THE NUMBER OF DAYS IN THE CURRENT MONTH
    var days = getNumberOfDaysInMonth();

    // IF GLOBAL DAY VALUE IS > THAN DAYS IN MONTH, HIGHLIGHT LAST DAY IN MONTH
    if (monthDate > days) {

        monthDate = days;
    }

    // DETERMINE WHAT DAY OF THE WEEK THE CALENDAR STARTS ON
    var firstOfMonth = new Date (year, month, 1);

    // GET THE DAY OF THE WEEK THE FIRST DAY OF THE MONTH FALLS ON
    var startingPos  = firstOfMonth.getDay();
    days += startingPos;

    // KEEP TRACK OF THE COLUMNS, START A NEW ROW AFTER EVERY 7 COLUMNS
    var columnCount = 0;

    // MAKE BEGINNING NON-DATE CELLS BLANK
    for (i = 0; i < startingPos; i++) {

        dateTableHTML += blankCell;
	columnCount++;
    }

    // SET VALUES FOR DAYS OF THE MONTH
    var currentDay = 0;
    var dayType    = "weekday";

    // DATE CELLS CONTAIN A NUMBER
    for (i = startingPos; i < days; i++) {

	var paddingChar = "&nbsp;";

        // ADJUST SPACING SO THAT ALL LINKS HAVE RELATIVELY EQUAL WIDTHS
        if ((i - startingPos + 1) < 10) {
            padding = "&nbsp;&nbsp;";
        }
        else {
            padding = "&nbsp;";
        }

        // GET THE DAY CURRENTLY BEING WRITTEN
        currentDay = i-startingPos+1;

        // SET THE TYPE OF DAY, THE focusDay GENERALLY APPEARS AS A DIFFERENT COLOR
        if (currentDay == monthDate) {

            dayType = "focusDay";
        }
        else {

            dayType = "weekDay";
        }

        // ADD THE DAY TO THE CALENDAR STRING
        dateTableHTML +=
                  "<TD align=center bgcolor='" + cellColor + "'>" +
                  "<a class='" + dayType + "' href='javascript:parent.opener.getDateAndTime(" + 
                  currentDay + ")'>" + padding + currentDay + paddingChar + "</a></TD>\r\n";

        columnCount++;

        // START A NEW ROW WHEN NECESSARY
        if (columnCount % 7 == 0) {

            dateTableHTML += "\r\n</TR>\r\n<TR>\r\n";
        }
    }

    // MAKE REMAINING NON-DATE CELLS BLANK
    for (i = days; i < 42; i++)  {

        dateTableHTML += blankCell;
	columnCount++;

        // START A NEW ROW WHEN NECESSARY
        if (columnCount % 7 == 0) {

            dateTableHTML += "</TR>\r\n";
            if (i < 41) {

                dateTableHTML += "<TR>\r\n";
            }
        }
    }

    // WHETHER OR NOT TO DISPLAY A THICK LINE BELOW THE CALENDAR
    if (bottomBorder) {

        dateTableHTML += "<TR>\r\n</TR>\r\n";
    }

    // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE BORDERS PROPERLY
    if (isNav) {

        dateTableHTML += "\r\n</TABLE>\r\n</TD>\r\n</TR>\r\n";
    }

    dateTableHTML += "</TABLE>";
    return dateTableHTML;
}


function buildWeekTitleHTML() {

    var weekdayList  = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
    var weekdayArray = new Array('Su','Mo','Tu','We','Th','Fr','Sa');

    // START HTML TO HOLD WEEKDAY NAMES IN TABLE FORMAT
    var weekTitleHTML = "<TR BGCOLOR='" + headingCellColor + "'>\r\n";

    // LOOP THROUGH WEEKDAY ARRAY
    for (i in weekdayArray) {

        weekTitleHTML += "<TD class='heading' align=center>" + weekdayArray[i] + "</TD>\r\n";
    }
    weekTitleHTML += "</TR>\r\n";

    // RETURN TABLE ROW OF WEEKDAY ABBREVIATIONS TO DISPLAY ABOVE THE CALENDAR
    return weekTitleHTML;
}

function buildHourMinuteHTML(hour, minute) {

        hourMinuteHTML = "<TABLE CELLPADDING=0 CELLSPACING=1 BORDER=0 WIDTH=20%>\r\n<TR>\r\n" +
                         "<TD ALIGN='CENTER' VALIGN='MIDDLE' NOWRAP>" +
                         "<INPUT NAME='hour' VALUE='";

        if (hour >= 12) {

            ampm = 'PM';
            hour -= 12;
        }

        if (hour < 10) {

            hourMinuteHTML += '0';
        }

        hourMinuteHTML +=
            hour +
            "' SIZE='2' MAXLENGTH='2' onChange='parent.opener.checkHH(this)'><B>:</B><INPUT NAME='minute' VALUE='";

        if (minute < 10) {

            hourMinuteHTML += '0';
        }

        hourMinuteHTML +=
            minute +
            "' SIZE='2' MAXLENGTH='2' onChange='parent.opener.checkMM(this);focus();'>" +
            "<SELECT NAME='ampm' onChange='parent.opener.setAMPM(this)'>\r\n<OPTION VALUE='AM'";

        if (ampm == 'AM') {

             hourMinuteHTML += " SELECTED";
        }

        hourMinuteHTML += ">AM</OPTION>\r\n<OPTION VALUE='PM'";


        if (ampm == 'PM') {

              hourMinuteHTML += " SELECTED";

        }

        hourMinuteHTML +=
            ">PM</OPTION>\r\n</SELECT>" +
            "</TD>\r\n</TR>\r\n" +
            "</TABLE>\r\n";
       return hourMinuteHTML;
}


/////////////////////////////////////////////////////////////////////////////////////////////////////
// Helper methods

function getNumberOfDaysInMonth()  {

    var days;
    if (eval(debug)) {

        alert(month);
    }

    // RETURN 31 DAYS
    if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month==11) {

        days = 31;
    }
    // RETURN 30 DAYS
    else if (month == 3 || month == 5 || month == 8 || month ==10) {

        days = 30;
    }
    // RETURN 29 DAYS
    else if (month == 1)  {

        if (isLeapYear(year)) {

            days = 29;
        }
        // RETURN 28 DAYS
        else {

            days = 28;
        }
    }
    return (days);
}

function isLeapYear(year) {

    if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {

        return (true);
    }
    else {

        return (false);
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// event handling
// SET THE CALENDAR TO TODAY'S DATE AND DISPLAY THE NEW CALENDAR

function setToday() {

    var d = new Date();

    month = d.getMonth();
    year  = d.getFullYear();
    monthDate = d.getDate();

    if (eval(debug)) {

        alert("setToday(): " + month + "/" + monthDate + "/" + year + " " + hour + ":" + minute + ampm);
    } 
    //top.newWin.document.calControl.month.selectedIndex = month;

    // SET YEAR VALUE
    //top.newWin.document.calControl.year.value = year;

    // DISPLAY THE NEW CALENDAR
    rebuildCalendar();
}


function setYear() {

    var y = top.newWin.document.calControl.year.value;
    if (isNaN(y) || (y > 3000 || y < 1970)) {

        alert("Year should be in range of 1970 - 3000.");
        top.newWin.document.calControl.year.focus();
        return ;
    }

    year  = y;

    rebuildCalendar();
}


function setCurrentMonth() {

    month = top.newWin.document.calControl.month.selectedIndex;

    rebuildCalendar();
}


function setPreviousYear() {

    year  = top.newWin.document.calControl.year.value - 1;

    rebuildCalendar();
}


function setPreviousMonth() {

    year  = top.newWin.document.calControl.year.value;
    month = top.newWin.document.calControl.month.selectedIndex;

    // IF MONTH IS JANUARY, SET MONTH TO DECEMBER AND DECREMENT THE YEAR
    if (month == 0) {

        month = 11;

        year--;
        top.newWin.document.calControl.year.value = year;

    }
    else {

        month --;
    }

    top.newWin.document.calControl.month.selectedIndex = month;

    rebuildCalendar();
}


function setNextMonth() {

    year = top.newWin.document.calControl.year.value;

    month = top.newWin.document.calControl.month.selectedIndex;

    if (month == 11) {

        month = 0;
        year++;
        top.newWin.document.calControl.year.value = year;
    }
    else {

        month++;
    }

    top.newWin.document.calControl.month.selectedIndex = month;
    rebuildCalendar();
}


function setNextYear() {

    year  = top.newWin.document.calControl.year.value;
    year++;
    top.newWin.document.calControl.year.value = year;
    rebuildCalendar();
}

function checkHH(h) {

    var num = new Number(h.value);
    if (eval(debug)) {

        alert('checkHH(): num (hour) : ' + num);
    }

    if (isNaN(num) || (num < 0 || num > 13)) {

        alert("Hour: 0 - 12");
        h.focus();
        return ;
    }
    hour = num;
    //rebuildCalendar();
}

function checkMM(m) {

    var num = new Number(m.value);
    if (eval(debug)) {

        alert('checkMM(): mum (minute) : ' + num);
    }

    if (isNaN(num) || (num < 0 || num > 61)) {

        alert("Minute: 0 - 60");
        m.focus();
        return ;
    }

    minute = num;
    //rebuildCalendar();
}

function setAMPM(m) {

    if (eval(debug)) {

        alert("setAMPM(): ampm: " + ampm + " control value: " + m.selectedIndex);
    }

    if (m.selectedIndex == 0) {

        ampm = 'AM';
    }
    else {

        ampm = 'PM';
    }
    //rebuildCalendar();
}

function getDateAndTime(date) {
    
    if (eval(debug)) {

        alert("getDateAndTime(): date picked: " + date + '\r\n' + 
              month + "/" + monthDate + "/" + year + " " +
              hour + ":" + minute + "(" + ampm + ")");
    }
    monthDate = date;
    var d = new Date();
    d.setTime(0);
    d.setYear(year);
    d.setDate(monthDate);

    if (ampm == 'AM') {

        d.setHours(hour);
    }
    else {

        d.setHours(new Number(12) + new Number(hour));
    }

    d.setMinutes(minute);
    if (eval(debug)) {

        alert("getDateAndTime(): " + hour + ";" + minute);
        alert("getDateAndTime(): datetimeControl value: " + datetimeControl.value);
    }

    d.setMonth(month);
    datetimeControl.value = convertLong2Date(d.getTime());
    top.newWin.close();
    datetimeControl.focus();
}


//////////////////////////////////////////////////////////////////////////////////////////////
// layer

function rewriteLayer(layer,content) {

    if (top.newWin.document.layers) {

        var l = eval('top.newWin.document.layers.' +layer);
        l.visibility = 'hidden';
        l.document.write(content);
        l.document.close();
        l.visibility = 'show';
    }
    else if (top.newWin.document.all) {

        top.newWin.document.calControl.all[layer].innerHTML= content;
    }
    else {

        top.newWin.document.getElementById(layer).innerHTML = content;
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////
// write readonly date
function writeLong2Date(date) {

    if (isNaN(date)) { 

        document.writeln(date);
    }
    else {

        dlong = Number(date);
        document.writeln(convertLong2Date(dlong));
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
// for compatibility

function doNothing() {
}
