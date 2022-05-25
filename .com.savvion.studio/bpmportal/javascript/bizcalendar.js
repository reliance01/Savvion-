//
// JavaScript Calendar Component
// Author: Vishnu Kumar Trivedi
// Date:   17/10/2003
// History:
// 20/10/2003 Parul:
//   -Added methods to empty drop downs, fill onth drop down.
// 23/10/2003 Rahul:
//   -Added methods required for mark/unmark holiday, show themes.
// 05/11/2003 Parul :
//   -Added methods to hide stack trace and show pop-up relative
//    to current mouse pointer
// 05/11/2003 Anjali :
//     - Updated code to remove flickering when pop-up is displayed

// global x and y screen co-ordinates
var X = 200;
var Y = 300;
var xCord = 0;
var xCord = 0;

initialize();
function initialize() {
    // register for click event to get the screen co-ordinates
    if (navigator.appName.indexOf("Netscape")!=-1) { // netscape
        document.captureEvents(Event.CLICK)
    }
    // required for the tooltip
    if (!document.layers&&!document.all&&!document.getElementById) {
        event="test";
    }
    document.onclick = getcoords;
}

function getcoords(e) {
    if (navigator.appName.indexOf("Netscape")!=-1) {
        X = parseInt(e.screenX);
        Y = parseInt(e.screenY);
    } else if (navigator.appName.indexOf("Microsoft")!=-1) { // IE
        X = parseInt(event.screenX);
        Y = parseInt(event.screenY);
    }
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

///===========================================================================
///===========================================================================
// functions to select / deselect entire row when checkbox is checked =========
function rowSelection(chkbx,tableName,chkpos)
{
    var table=document.getElementById(tableName);

    var id = parseInt(chkpos);
        //id = (id*2)-1;
    var row = table.rows[id];

    if(row == null) return;
    if(chkbx.checked){
        for(var j=0;j < row.cells.length;j++){
        var cell = row.cells[j];
        var c_name = cell.className;
        cell.className = c_name + "Slct";
        }
    }else{
        for(var j=0;j < row.cells.length;j++){
            var cell = row.cells[j];
        var origc_name = cell.className;
        if(origc_name.indexOf("Slct") > -1){
            var c_name = origc_name.substring(0,origc_name.length-4);
                cell.className = c_name;
            }
        }
    }
}

function selectAll(theForm,tableName,chkbxall)
{
    chkposition = 0;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type =='checkbox')
        {
            if(theForm.elements[k] != chkbxall) {
                chkposition++;
                if(chkbxall.checked == true) {
                    if(theForm.elements[k].checked == false) {
                        theForm.elements[k].checked = true;
                        rowSelection(theForm.elements[k],tableName,chkposition);
                    }
                } else {
                    if(theForm.elements[k].checked == true) {
                        theForm.elements[k].checked = false;
                        rowSelection(theForm.elements[k],tableName,chkposition);
                    }
                }
            }
        }
    }
}
// end of functions to select / deselect entire row when checkbox is checked
///===========================================================================
///===========================================================================

function unmarkHoliday(date, month, year, holName, halfDay) {

    xCord = X - 160;
    yCord = Y - 110;
    setCoordinates();
    var newWin = window.open('pop_holidaydet.jsp','holidaywin', "width=500,height=190,left="+xCord+",top="+yCord+",screenX="+xCord+",screenY="+yCord);
    newWin.focus();

    doc = document.forms[0];
    doc.currHolidayName.value = holName;
    doc.selectedDate.value = getMonthString(month) + " "+ date + ", " + year;
    doc.selDateVal.value = date;
    doc.halfDay.value = halfDay;
    X = 200;
    Y = 300;
}

function markHoliday(date, month, year) {
    xCord = X - 160;
    yCord = Y - 110;
    setCoordinates();

    var newWin = window.open('pop_markholiday.jsp','holidaywin', "width=500,height=190,left="+xCord+",top="+yCord+",screenX="+xCord+",screenY="+yCord);
    newWin.focus();
    doc = document.forms[0];
    doc.selectedDate.value = getMonthString(month) + " "+date + ", " + year;
    doc.selDateVal.value = date;
    X = 200;
    Y = 300;
}

function submitForm(action) {
    var doc = document.forms[0];
    doc.action.value = action;
    doc.submit();
}

function setHolDefaults(month) {
    fillMonthDropDown("month");
    selectValueInDropDown(eval ("document.forms[0].month"), month);
}

function submitCalendar(action) {
    if(!isValidCalendar()) {
        return false;
    }

    document.forms[0].specialBizHours.value = specialBizHoursData;
    document.forms[0].weekends.value = weekendsData;
    document.forms[0].fixedHolidays.value = fixedHolidaysData;
    document.forms[0].regularHolidays.value = regHolidaysData;
    document.forms[0].years.value = calYearsData;
    /*alert(specialBizHoursData);
    alert(weekendsData);
    alert(fixedHolidaysData);
    alert(regHolidaysData);
    alert(calYearsData);*/
    submitForm(action);
}



function isValidCalendar() {
	return validateCalendar(true);
}

function validateCalendar(checkCalName) {
    if (checkCalName == true) {
	    if(!checkForWhitespace("document.forms[0]", "Calendar Name", "calName")) {
		return false;
	    }
	    
	    if(checkSpecialChars(document.forms[0].calName.value)) {
		alert('<sbm:message key="bizCalAlert10"/>');
	
		return false;
	    }
    }
    
    if(checkSpecialChars(document.forms[0].calDesc.value)) {
        alert('<sbm:message key="bizCalAlert11"/>');
        return false;
    }
     if(checkDescChars(document.forms[0].calDesc.value) != -1) {
        alert('<sbm:message key="bizCalAlert12"/>');
        return false;
    }
    
    // mid-day break start time
    if(eval(document.forms[0].lunchStartTimeHrs.value) == 0 &&
       eval(document.forms[0].lunchStartTimeMins.value) == 0) {
        //alert("Please enter a value for the Mid-Day Break Start Time field." );
        //document.forms[0].lunchStartTimeHrs.focus();
        //return (false);
    }

    // mid-day break end time
    if(eval(document.forms[0].lunchEndTimeHrs.value) == 0 &&
       eval(document.forms[0].lunchEndTimeMins.value) == 0) {
        //alert("Please enter a value for the Mid-Day Break End Time field." );
        //document.forms[0].lunchEndTimeHrs.focus();
        //return (false);
    }

    // regular business hours start time
    if(eval(document.forms[0].bizHoursStartTimeHrs.value) == 0 &&
       eval(document.forms[0].bizHoursStartTimeMins.value) == 0) {
        //alert("Please enter a value for the Regular Business Hours Start Time field." );
        //document.forms[0].bizHoursStartTimeHrs.focus();
        //return (false);
    }

    // regular business hours end time
    if(eval(document.forms[0].bizHoursEndTimeHrs.value) == 0 &&
       eval(document.forms[0].bizHoursEndTimeMins.value) == 0) {
        //alert("Please enter a value for the Regular Business Hours End Time field." );
        //document.forms[0].bizHoursEndTimeHrs.focus();
        //return (false);
    }

    // regular business hours start time should be less than end time
    startTime = getTime(document.forms[0].bizHoursStartTimeHrs.value,
                        document.forms[0].bizHoursStartTimeMins.value);
    endTime = getTime(document.forms[0].bizHoursEndTimeHrs.value,
                        document.forms[0].bizHoursEndTimeMins.value);

    if(startTime == endTime) {
        alert('<sbm:message key="bizCalAlert14"/>');
        //document.forms[0].bizHoursStartTimeHrs.focus();
        return (false);
    }

    //check if mid-break overlaps with regular business hrs
    var mdb_startTime = getTime(document.forms[0].lunchStartTimeHrs.value,
                        document.forms[0].lunchStartTimeMins.value);
    var mdb_endTime = getTime(document.forms[0].lunchEndTimeHrs.value,
                        document.forms[0].lunchEndTimeMins.value);
    
    var reg_startTime = getTime(document.forms[0].bizHoursStartTimeHrs.value,
                        document.forms[0].bizHoursStartTimeMins.value);
    var reg_endTime = getTime(document.forms[0].bizHoursEndTimeHrs.value,
                        document.forms[0].bizHoursEndTimeMins.value);
    
    if(reg_startTime < reg_endTime) {
	    if(mdb_startTime < reg_startTime || mdb_startTime > reg_endTime)
	    {
                alert('<sbm:message key="bizCalAlert15"/>');
		//document.forms[0].lunchStartTimeHrs.focus();
		return (false);
	    }

	    if(mdb_endTime < reg_startTime || mdb_endTime > reg_endTime)
	    {
                alert('<sbm:message key="bizCalAlert16"/>');
		//document.forms[0].lunchEndTimeHrs.focus();
		return (false);
	    }
	    if(mdb_startTime >= mdb_endTime) {
                alert('<sbm:message key="bizCalAlert17"/>');
		//document.forms[0].lunchStartTimeHrs.focus();
		return (false);
	    }
    } else if (reg_startTime > reg_endTime) {
	    //Check if Mid day break falls on the same day.
	    var temp_reg_endTime = reg_endTime + 2400;
	    var temp_mdb_startTime = mdb_startTime;
	    if(temp_mdb_startTime < reg_endTime)
	    	temp_mdb_startTime = temp_mdb_startTime + 2400;
	    var temp_mdb_endTime = mdb_endTime;
	    if(temp_mdb_endTime < reg_endTime)
	    	temp_mdb_endTime = temp_mdb_endTime + 2400;
	    
	    if(temp_mdb_startTime < reg_startTime || temp_mdb_startTime > temp_reg_endTime)
	    {
                alert('<sbm:message key="bizCalAlert18"/>');
		//document.forms[0].lunchStartTimeHrs.focus();
		return (false);
	    }
	    
	    if(temp_mdb_endTime < reg_startTime || temp_mdb_endTime > temp_reg_endTime)
	    {
                alert('<sbm:message key="bizCalAlert19"/>');
		//document.forms[0].lunchEndTimeHrs.focus();
		return (false);
	    }
	    
	    if(mdb_startTime >= mdb_endTime) {
                alert('<sbm:message key="bizCalAlert20"/>');
		//document.forms[0].lunchStartTimeHrs.focus();
		return (false);
	    }
    }
    
    // weekend nad fixed holiday
    /*if(nonworkingHrsArray.length == 0)

    {
        alert('<sbm:message key="bizCalAlert34"/>');
	return false;
    } else
    {
	var bWeekends = false;
	var bfixedHolidays = false;
	
	for(var count=0; count < nonworkingHrsArray.length;count++)
	{
		var nonwrkHr = nonworkingHrsArray[count];
		if(nonwrkHr.type == 'Weekend' || nonwrkHr.type == 'weekend')
		{
			bWeekends = true;
		}
		if(nonwrkHr.type == 'Holiday' || nonwrkHr.type == 'holiday')
		{
			if(nonwrkHr.year == 'All')
			{
				bfixedHolidays = true;
			}
		}
	}
	
	if(bWeekends == false)
	{
                alert('<sbm:message key="bizCalAlert35"/>');
		return false;
	}

	if(bfixedHolidays == false)
	{
                alert('<sbm:message key="bizCalAlert35"/>');
		return false;
	}

    }*/

    //Years
    if(document.forms[0].yearFrom.value > document.forms[0].yearTo.value)
    {
        alert('<sbm:message key="bizCalAlert21"/>');
    	return false;
    }

    //Timezone
    if(document.forms[0].timezoneID.value == "")
    {
        alert('<sbm:message key="bizCalAlert22"/>');
    	return false;
    }

    return true;
}

// for add calendar
function setCalDefaults() {
    fillHoursDropDown("lunchStartTimeHrs");
    fillMinutesDropDown("lunchStartTimeMins");
    fillHoursDropDown("lunchEndTimeHrs");
    fillMinutesDropDown("lunchEndTimeMins");
    fillHoursDropDown("bizHoursStartTimeHrs");
    fillMinutesDropDown("bizHoursStartTimeMins");
    fillHoursDropDown("bizHoursEndTimeHrs");
    fillMinutesDropDown("bizHoursEndTimeMins");
    fillSpecialBizHours();
    fillWeekends();
    fillFixedHolidays();
    fillRegHolidays();
    fillCalYears();
    alert(document.forms[0].editTimeZoneID.value);
    //if(document.forms[0].edit.value == "true") {
        selectValueInDropDown(eval("document.forms[0].localeLanguage"),
                              document.forms[0].editLocaleLang.value);
        selectValueInDropDown(eval("document.forms[0].localeCountry"),
                              document.forms[0].editLocaleCountry.value);
        selectValueInDropDown(eval("document.forms[0].timezoneID"),
                              document.forms[0].editTimeZoneID.value);
        lunhcStartTimeHours = getHours(document.forms[0].editLunchStartTime.value);
        selectValueInDropDown(eval("document.forms[0].lunchStartTimeHrs"),
                              getHours(document.forms[0].editLunchStartTime.value));
        selectValueInDropDown(eval("document.forms[0].lunchStartTimeMins"),
                              getMinutes(document.forms[0].editLunchStartTime.value));
        selectValueInDropDown(eval("document.forms[0].lunchEndTimeHrs"),
                              getHours(document.forms[0].editLunchEndTime.value));
        selectValueInDropDown(eval("document.forms[0].lunchEndTimeMins"),
                              getMinutes(document.forms[0].editLunchEndTime.value));
        selectValueInDropDown(eval("document.forms[0].bizHoursStartTimeHrs"),
                              getHours(document.forms[0].editRegBizStartTime.value));
        selectValueInDropDown(eval("document.forms[0].bizHoursStartTimeMins"),
                              getMinutes(document.forms[0].editRegBizStartTime.value));
        selectValueInDropDown(eval("document.forms[0].bizHoursEndTimeHrs"),
                              getHours(document.forms[0].editRegBizEndTime.value));
        selectValueInDropDown(eval("document.forms[0].bizHoursEndTimeMins"),
                              getMinutes(document.forms[0].editRegBizEndTime.value));
    //}
}

function deleteCalendars(count) {
    document.forms[0].calName.value = "";
    if(count == 1) {
        if(document.forms[0].delcal.checked) {
            document.forms[0].calName.value += document.forms[0].delcal.value ;
        }
    } else {
        first = "true";
        for(i = 0; i < count; ++i) {
            if(document.forms[0].delcal[i].checked) {
                if(first == "true") {
                    document.forms[0].calName.value +=
                        document.forms[0].delcal[i].value ;
                    first = "false";
                } else {
                    document.forms[0].calName.value +=
                        ","+document.forms[0].delcal[i].value ;
                }
            }
        }
    }
    // no checked records
    if ( document.forms[0].calName.value == "") {
        alert('<sbm:message key="bizCalAlert23"/>');
        return false;
    }
    // confirm for delete
    if (confirm("Are you sure you want to delete these records!! " )) {
        submitForm("deleteCal");
        return true;
    } else {    // not confirmed
        return false;
    }
}

// remove all entries from the drop downs
function emptyDropDown(field_path)
{
    for (m=field_path .options.length-1;m>=0;m--)
    {
        field_path .options[m]=null;
    }
}

// fill the month drop down with all the twelve months
function fillMonthDropDown(eleName)
{
    drop_down = eval ("document.forms[0]." + eleName);
    drop_down.options[0] = new Option("Jan" , "January");
    drop_down.options[1] = new Option("Feb" , "February");
    drop_down.options[2] = new Option("Mar" , "March");
    drop_down.options[3] = new Option("Apr" , "April");
    drop_down.options[4] = new Option("May" , "May");
    drop_down.options[5] = new Option("Jun" , "June");
    drop_down.options[6] = new Option("Jul" , "July");
    drop_down.options[7] = new Option("Aug" , "August");
    drop_down.options[8] = new Option("Sep" , "September");
    drop_down.options[9] = new Option("Oct" , "October");
    drop_down.options[10] = new Option("Nov" , "November");
    drop_down.options[11] = new Option("Dec" , "December");
}

// fill the month drop down with all the twelve months and all option
function fillMonthDropDownWithAll(eleName)
{
    drop_down = eval ("document.forms[0]." + eleName);

    drop_down.options[0] = new Option("Select One" , "-1");
    drop_down.options[1] = new Option("January" , "January");
    drop_down.options[2] = new Option("February" , "February");
    drop_down.options[3] = new Option("March" , "March");
    drop_down.options[4] = new Option("April" , "April");
    drop_down.options[5] = new Option("May" , "May");
    drop_down.options[6] = new Option("June" , "June");
    drop_down.options[7] = new Option("July" , "July");
    drop_down.options[8] = new Option("August" , "August");
    drop_down.options[9] = new Option("September" , "September");
    drop_down.options[10] = new Option("October" , "October");
    drop_down.options[11] = new Option("November" , "November");
    drop_down.options[12] = new Option("December" , "December");
}

// fill the date drop down with all the 31 days
function fillDateDropDown(eleName)
{
    drop_down = eval ("document.forms[0]." + eleName);

    var counter = 1;
    for(var i=0;i<31;i++) {
        drop_down.options[i] = new Option(""+counter , ""+counter);
        counter=counter+1;
    }
}

// fill the year drop down with the specified range
function fillYearDropDown(eleName,startYear,endYear)
{
    drop_down = eval ("document.forms[0]." + eleName);

    var START_YEAR = 2005;
    var END_YEAR = 2030;

    var stYear = startYear;
    var edYear= endYear;

    if(stYear < START_YEAR) {
        stYear = START_YEAR;
    }

    if(edYear > END_YEAR) {
        edYear = END_YEAR;
    }

    var diff = edYear - stYear +1;
    var counter = stYear;
    for(var i=0;i<diff;i++) {
        drop_down.options[i] = new Option(""+counter , ""+counter);
        counter=counter+1;
    }
}

// fill the year drop down with the specified range and all option
function fillYearDropDownWithAll(eleName,startYear,endYear)
{
    drop_down = eval ("document.forms[0]." + eleName);

    var START_YEAR = 2005;
    var END_YEAR = 2030;

    var stYear = startYear;
    var edYear= endYear;

    if(stYear < START_YEAR) {
        stYear = START_YEAR;
    }

    if(edYear > END_YEAR) {
        edYear = END_YEAR;
    }

    var diff = edYear - stYear +1;
    var counter = stYear;
    drop_down.options[0] = new Option("Select One" , "-1");
    drop_down.options[1] = new Option("All" , "All");
    for(var i=0;i<diff;i++) {
        drop_down.options[i+2] = new Option(""+counter , ""+counter);
        counter=counter+1;
    }
}

// fill the hours drop down with 24 hours
function fillHoursDropDown(eleName) {
    drop_down = eval ("document.forms[0]." + eleName);
    for (i = 0; i <= 9; i++) {
        drop_down.options[i] = new Option ("0" + i, "0" + i);
    }
    for (i = 10; i < 24; i++) {
        drop_down.options[i] = new Option ("" + i, "" + i);
    }
}

// fill the minutes drop down with 60 minutes
function fillMinutesDropDown(eleName) {
    drop_down = eval ("document.forms[0]." + eleName);
    count = 0;
    drop_down.options[count++] = new Option("00" , "00");
    for(i = 10; i < 60; i=i+10) {
        drop_down.options[count++] = new Option(i , i);
    }
}

// to select a value in dropdown
function selectValueInDropDown(path_of_drop_down,value_to_select)
{
    _select = "false";
    for (i=0;i < path_of_drop_down.length;i++)
    {
        if (path_of_drop_down.options[i].value == value_to_select)
        {
            _select = "true";
            path_of_drop_down.options[i].selected = true;
            break;
        }
    }
    if (_select == "false")
    {
        path_of_drop_down.options[0].selected = true;
    }
}

// for tooltip
function showtip(current,e,text){
    if (document.all||document.getElementById){
        thetitle=text.split("<br>");
        if (thetitle.length>1){
            thetitles="";
            for (i=0;i<thetitle.length;i++) {
                thetitles+=thetitle[i];
            }
            current.title=thetitles;
        }
        else {
            current.title=text;
        }
    }
    else if (document.layers){
        document.tooltip.document.write("<layer bgColor='white' style='border:1px solid  black;font-size:12px;'>"+text+"</layer>");
        document.tooltip.document.close();
        document.tooltip.left=e.pageX+5;
        document.tooltip.top=e.pageY+5;
        document.tooltip.visibility="show";
    }
}

function hidetip(){
    if (document.layers)
        document.tooltip.visibility="hidden";
}

function closeWindow() {
    window.close();
}

function getHolidayName() {
    doc = window.opener.document.forms[0];
    hol = doc.currHolidayName.value;
    document.write(hol);
}

function getDate() {
    doc = window.opener.document.forms[0];
    var selDate = doc.selectedDate.value;
    document.write(selDate);
}

function getHolidayType() {
    doc = window.opener.document.forms[0];
    holType = doc.halfDay.value;
    if(holType == 0) {
        document.write("Full Day");
    } else if(holType == 1) {
        document.write("First Half");
    } else {
        document.write("Second Half");
    }
}

function addHoliday() {

    if(!checkForWhitespace("document.forms[0]", "Holiday Name", "holName")) {
        return false;
    }

    if(document.forms[0].holName.value.indexOf(",") != -1)
    {
        alert('<sbm:message key="bizCalAlert25"/>');
    	return false;
    }

    parentdoc = window.opener.document.forms[0];
    parentdoc.date.value = parentdoc.selDateVal.value;
    parentdoc.holName.value = document.forms[0].holName.value;

    for (i = 0; i < 3; ++i) {
        if(document.forms[0].halfDay[i].checked) {
            parentdoc.halfDay.value = document.forms[0].halfDay[i].value;
        }
    }
    parentdoc.action.value = "markHoliday";
    parentdoc.submit();
    closeWindow();
}

function deleteHoliday() {
        parentdoc = window.opener.document.forms[0];
	parentdoc.date.value = parentdoc.selDateVal.value;
	parentdoc.holName.value = parentdoc.currHolidayName.value;
	for (i = 0; i < 3; ++i) {
		if(document.forms[0].halfDay[i].checked) {
				parentdoc.halfDay.value = document.forms[0].halfDay[i].value;
			}
		}
	
        parentdoc.action.value = "unmarkHoliday";
        parentdoc.submit();
        closeWindow();
}

// hide stakc trace on error page
function hideTrace(id, flag) {
    if (document.layers) { // Netscape before 6.0
        if(flag == "true") {
            document.layers[id].visibility = "hide"
        } else {
            document.layers[id].visibility = "show"
        }
    } else {
        if(navigator.appName.indexOf("Netscape")!=-1) { // Netscape 6.0 and higher
            // Obtain an element reference
            var elementRef = document.getElementById("trace");

            // Check the style object and visibility
            // property are available.
            if((elementRef.style) &&
               (elementRef.style.visibility != null)) {
                if(flag == "true") {
                    elementRef.style.visibility = "hidden";
                } else {
                    elementRef.style.visibility = "visible";
                }
            }
        } else { // others
            if(flag == "true") {
                document.all[id].style.display = "none"
            } else {
                document.all[id].style.display = "block"
            }
        }
    }
}
function isValid(name) {
        if(name == "" || name.length == 0 || name.charAt(0) == " "){
            return false;
        }
        return true;
}
function setCoordinates() {
    if(xCord < 0 ) {
        xCord = 0;
    }
    if(xCord > 480) {
        xCord = 480;
    }
     if(yCord < 0) {
        yCord = 0;
    }
    if(yCord > 480) {
        yCord = 480;
    }
}

function getTime(hours, mins) {
    return eval(hours) * 100 + eval(mins);
}

function getHours( time) {
    hrs = parseInt(eval(time)/100);
    if(hrs < 10) {
        return "0" + hrs;
    } else {
        return hrs;
    }
}

function getMinutes( time) {
    return eval(time) % 100;
}

//==============================================================================
//================ Validation functions ========================================

// Function to check for string comprising entirely of whitespaces
function checkForWhitespace(the_form_path, display_field_name, actual_field_name)
{
    // Check for EMPTY string values.
    field_tobe_accessed = eval( the_form_path + "." + actual_field_name );
    if (field_tobe_accessed.value == "") {
        alert("Please enter a value for the \" " +
               display_field_name + "\" field." );
        field_tobe_accessed.focus();
        return (false);
    } else {
        length_of_value = field_tobe_accessed.value.length ;
        check_value = field_tobe_accessed.value;
        no_of_space_chars = 0;
        for ( var i = 0 ; i<length_of_value ; i++) {
            if (check_value.charAt(i) == " "){
                no_of_space_chars++;
            }
        }
        if (no_of_space_chars == length_of_value){
            alert("Please enter a value for the \" " + display_field_name +
                  "\" field. \n \r ONLY space characters are NOT allowed." );
            field_tobe_accessed.select();
            field_tobe_accessed.focus();
            return (false);
        }
        return (true);
    }
}

// Function to check for string comprising entirely of whitespaces
function checkForNumeric(the_form_path, display_field_name, actual_field_name){
    // Check for EMPTY string values.
    field_tobe_accessed = eval( the_form_path + "." + actual_field_name );
    if (isNaN(field_tobe_accessed.value)) {
        alert("Please enter a numeric value for the \" " +
               display_field_name + "\" field." );
        field_tobe_accessed.select();
        field_tobe_accessed.focus();
        return false;
    }
    return true;
}

//*****************************************************************************
// Function to check for length between minimum and maximum
function checkForFixedLength(the_form_path, display_field_name,
                               actual_field_name, max_length, min_length)
{
    field_tobe_accessed = eval( the_form_path + "." + actual_field_name );
    field_length = field_tobe_accessed.value.length;

    if ( (field_length > max_length) || (field_length < min_length) ) {
        if ( min_length == max_length ) {
            alert("Please enter exactly " + max_length +
                  " characters \n\r in the \" " + display_field_name +
                  "\" field." );
        } else  {
            alert("Please enter " + min_length +
                  " to " + max_length +
                  "  characters in the \" " + display_field_name +
                  "\" field." );
        }
        field_tobe_accessed.select();
        field_tobe_accessed.focus();
        return (false);
    }
    else {
        return (true);
    }
}

function checkForDate(the_form_path, year_field_name, month_field_name, date_field_name) {
    date_field = eval(the_form_path + "." + date_field_name)
    mnth_field = eval(the_form_path + "." + month_field_name);
    year_field = eval(the_form_path + "." + year_field_name);

    date_val = date_field.value;
    month_val = getMonth(mnth_field.options[mnth_field.selectedIndex].value);
    year_val = year_field.value;

    // year is not valid
    if ( (eval(year_val) < 1900 )||(eval(year_val) > 9999 )) {
        alert("Please enter a value between \"1900\" and \"9999\" " +
              "for the \"Year field");
        year_field.select();
        year_field.focus();
        return (false);
    }

    if ((( eval(year_val) % 4 == 0 ) && ( eval(year_val) % 100 != 0 ) ) ||
        ( eval(year_val) % 400 == 0 )) {
        leap_year = true;
    } else {
        leap_year = false;
    }

    if ( eval(month_val) == 2) {
        if (leap_year == true) {
            number_of_days = 29;
        } else {
            number_of_days = 28;
        }
    }

    if ( (eval(month_val) == 1) || (eval(month_val) == 3) ||
         (eval(month_val) == 5) || (eval(month_val) == 7) ||
         (eval(month_val) == 8 )|| (eval(month_val) == 10) ||
         (eval(month_val) == 12) ) {
            number_of_days = 31;
    } else if ( (eval(month_val) == 4) || (eval(month_val) == 6) ||
                (eval(month_val) == 9) || (eval(month_val) == 11) ) {
            number_of_days = 30;
    }

    if ( (eval(date_val) < 1) || (eval(date_val) > number_of_days ) ) {
        alert("Please enter a value between \"01\" and \"" +
               number_of_days + "\" " +
               "for the \"Date field.");
        date_field.select();
        date_field.focus();
        return (false);
    }
    // IF every thing is correct then return true
    return (true);
}

function getMonth(monthString) {
    if(monthString == "January") {
        return 1;
    }
    if(monthString == "February") {
        return 2;
    }
    if(monthString == "March") {
        return 3;
    }
    if(monthString == "April") {
        return 4;
    }
    if(monthString == "May") {
        return 5;
    }
    if(monthString == "June") {
        return 6;
    }
    if(monthString == "July") {
        return 7;
    }
    if(monthString == "August") {
        return 8;
    }
    if(monthString == "September") {
        return 9;
    }
    if(monthString == "October") {
        return 10;
    }
    if(monthString == "November") {
        return 11;
    }
    if(monthString == "December") {
        return 12;
    }
    return -1;
}

function getMonthString(monthId) {
    if(monthId == 1) {
        return "Jan";
    }
    if(monthId == 2) {
        return "Feb";
    }
    if(monthId == 3) {
        return "Mar";
    }
    if(monthId == 4) {
        return "Apr";
    }
    if(monthId == 5) {
        return "May";
    }
    if(monthId == 6) {
        return "Jun";
    }
    if(monthId == 7) {
        return "Jul";
    }
    if(monthId == 8) {
        return "Aug";
    }
    if(monthId == 9) {
        return "Sep";
    }
    if(monthId == 10) {
        return "Oct";
    }
    if(monthId == 11) {
        return "Nov";
    }
    if(monthId == 12) {
        return "Dec";
    }
    return -1;
}


function selectRadioButton(formName, groupName, val) {
  for (var i=0; i<formName.elements.length; i++) {
    if (formName.elements[i].name == groupName && formName.elements[i].value == val) {
      formName.elements[i].checked = true;
    }
  }
}

function disableGroup(formName, groupName, booleanDisabled) {
  for (var i=0; i<formName.elements.length; i++) {
    if (formName.elements[i].name == groupName) {
      formName.elements[i].disabled = booleanDisabled;
    }
  }
}
function OnSelectAll(theForm,chkbx,tablename)
{
	
	if(chkbx.checked)
	{
		
		chkbx.title = "Deselect All";
		SelectAll(theForm,tablename);
	}
	else
	{		
		chkbx.title = "Select All";
		DeSelectAll(theForm,tablename);
	}
}



function SelectAll(theForm,tablename){
       
	var count =1;
	for(var k=0;k<theForm.elements.length;k++)
	{
		var chekbxname = theForm.elements[k].name;
		if(tablename == 'WorkingTable'){
		
			if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='wkcheckbox')
		{
			theForm.elements[k].checked = true;
			SelcRow(theForm.elements[k],theForm,tablename,count);
			count++;
			
		}
		}
		if(tablename == 'NonWorkingTable'){
		if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='nwcheckbox')
		{
			theForm.elements[k].checked = true;
			SelcRow(theForm.elements[k],theForm,tablename,count);
			count++;			
		}
		}
		
	}
}	
function DeSelectAll(theForm,tablename){
        var count =1;
	for(var k=0;k<theForm.elements.length;k++)
	{
		var chekbxname = theForm.elements[k].name;
		if(tablename == 'WorkingTable'){
			
		if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='wkcheckbox')
		{
			theForm.elements[k].checked = false;
			SelcRow(theForm.elements[k],theForm,tablename,count);
			count++;	
		}
		}
		if(tablename == 'NonWorkingTable'){
		if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='nwcheckbox')
		{
			theForm.elements[k].checked = false;
			SelcRow(theForm.elements[k],theForm,tablename,count);
			count++;	
		}
		}
	}
}	

function SelcRow(checkbox,theForm,tablename,count){
	var table=document.getElementById(tablename);
	var id = parseInt(count);
	var row = table.rows[id];
	if(row == null) return;
	if(checkbox.checked){
		for(var j=0;j < row.cells.length;j++){
			
			var cell = row.cells[j];
			var c_name = cell.className;
			
			if(c_name.indexOf("Slct") == -1)
			{
				if((parseInt(count))%2 == 0)
				{
					var temp_name = c_name.substring(0,c_name.length-3);
					cell.className = temp_name + "Slct";
				}
				else
					cell.className = c_name + "Slct";
			}
		}
	}else{
		for(var j=0;j < row.cells.length;j++)
		{
			var cell = row.cells[j];
			var origc_name = cell.className;
						
			if(origc_name.indexOf("Slct") > -1)
			{
				var c_name = origc_name.substring(0,origc_name.length-4);
				if((parseInt(count))%2 == 0)
					cell.className = c_name + "Alt";
				else
					cell.className = c_name;
			}
		}
	}
	
	// Check for all ..
	var bFlag = true;
	for(var k=0;k<theForm.elements.length;k++)
	{
		var chekbxname = theForm.elements[k].name;
		if(tablename=='NonWorkingTable'){
		if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='nwcheckbox'){	
				if(!theForm.elements[k].checked)
					bFlag = false;
			}		
		}
		if(tablename=='WorkingTable'){
		if(theForm.elements[k].type=='checkbox' && chekbxname.substring(0,10) =='wkcheckbox'){	
				if(!theForm.elements[k].checked)
					bFlag = false;
			}		
		}
	}	
	
	if(bFlag){
		if(tablename=='NonWorkingTable')
		theForm.nwchkbxAll.checked = true;
	        else if(tablename=='WorkingTable')
		theForm.wchkbxAll.checked = true;	
	} else{
		if(tablename=='NonWorkingTable')
		theForm.nwchkbxAll.checked = false;
	        else if(tablename=='WorkingTable')
		theForm.wchkbxAll.checked = false;
	}
}	

function checkDescChars(fieldValue)
{	
	var val = fieldValue;
	var retVal = -1;
	var length = val.length;
	if(length > 255){
		retVal =length-255;
	}
	return retVal;
}


