    function loadData() {
        // get data from parent
        fillMonthDropDown("month");
        document.forms[0].holidayName.value = opener.document.forms[0].fixedHolidayName.value;
        selectValueInDropDown(eval(document.forms[0].month), opener.document.forms[0].fixedHolidayMonth.value);
        document.forms[0].holidayType[opener.document.forms[0].fixedHolidayType.value].checked = true;

        if(opener.document.forms[0].fixedHolidayFormat.value == opener.FORMAT_DOW) {
            document.forms[0].holFormat[1].checked = true;
        } else {
            document.forms[0].holFormat[0].checked = true;
        }

        if(opener.document.forms[0].fixedHolidayFormat.value == opener.FORMAT_DAY) {
            document.forms[0].date.value = opener.document.forms[0].fixedHolidayDate.value;
        } else {
            selectValueInDropDown(eval(document.forms[0].dow), opener.document.forms[0].fixedHolidayDOW.value);
            selectValueInDropDown(eval(document.forms[0].occurence), opener.document.forms[0].fixedHolidayOccur.value);
        }
    }

    function submitFixedHoliday(openAgain) {
        if(!isValidFixedHoliday()) {
            return false;
        }
        // send data to parent
        opener.document.forms[0].fixedHolidayName.value = document.forms[0].holidayName.value;
        opener.document.forms[0].fixedHolidayMonth.value = document.forms[0].month.value;
        for (radIndex = 0; radIndex < document.forms[0].holidayType.length; radIndex++) {
            if (document.forms[0].holidayType[radIndex].checked == true) {
                opener.document.forms[0].fixedHolidayType.value =
                document.forms[0].holidayType[radIndex].value;
                break;
            }
        }
        if(document.forms[0].holFormat[0].checked == true) {
            opener.document.forms[0].fixedHolidayFormat.value = opener.FORMAT_DAY;
            opener.document.forms[0].fixedHolidayDate.value = document.forms[0].date.value;
        } else {
            opener.document.forms[0].fixedHolidayFormat.value = opener.FORMAT_DOW;
            opener.document.forms[0].fixedHolidayDOW.value = document.forms[0].dow.value;
            opener.document.forms[0].fixedHolidayOccur.value = document.forms[0].occurence.value;
        }
        // call to update holidays
        opener.updateFixedHoliday();

        if(openAgain == 'true') {
            document.forms[0].reset();
        } else {
            window.close();
        }
    }

    function fillFixedHolidays() {
        holidaysDropDown = document.forms[0].fixedHolidaysList;
        // Empty the holiday Drop Down
        for (m = holidaysDropDown.options.length - 1; m >= 0; m--) {
            holidaysDropDown.options[m] = null;
        }

        if(fixedHolidaysData.length == 0) {
            holidaysDropDown.options[0] = new Option("None", "-1");
        } else {
            // Fill the holidays Drop Down
            for (i = 0; i < fixedHolidaysData.length; i++) {
                holidaysDropDown.options[i] = new Option(fixedHolidaysData[i][1], i);
            }
        }
    }

    function newFixedHoliday() {
        document.forms[0].fixedHolidayName.value = "";
        document.forms[0].fixedHolidayMonth.value = "";
        document.forms[0].fixedHolidayType.value = "0";
        document.forms[0].fixedHolidayFormat.value = "";
        document.forms[0].fixedHolidayDate.value = "";
        document.forms[0].fixedHolidayDOW.value = "";
        document.forms[0].fixedHolidayOccur.value = "";
        document.forms[0].addFixedHoliday.value = "true";
        var newWin = window.open('pop_fixholiday_add.jsp','addfixedholiday','width=658,height=230');
        newWin.focus();
    }

    function editFixedHoliday() {
        selected = document.forms[0].fixedHolidaysList.value;
        col = 0
        document.forms[0].fixedHolidayFormat.value = fixedHolidaysData[selected][col++];
        document.forms[0].fixedHolidayName.value = fixedHolidaysData[selected][col++];
        document.forms[0].fixedHolidayMonth.value = fixedHolidaysData[selected][col++];
        document.forms[0].fixedHolidayType.value = fixedHolidaysData[selected][col++];
        if(document.forms[0].fixedHolidayFormat.value == FORMAT_DAY) {
            document.forms[0].fixedHolidayDate.value = fixedHolidaysData[selected][col++];
        } else {
            document.forms[0].fixedHolidayDOW.value = fixedHolidaysData[selected][col++];
            document.forms[0].fixedHolidayOccur.value = fixedHolidaysData[selected][col++];
        }
        document.forms[0].addFixedHoliday.value = "";
        var editWin = window.open('pop_fixholiday_det.jsp','editfixedholiday','width=658,height=230');
        editWin.focus();
    }

    function deleteFixedHoliday() {
        window.close();
        opener.deleteFixedHolidayData();
    }

    function deleteFixedHolidayData() {
        selected = document.forms[0].fixedHolidaysList.value;
        for(ix = selected; ix < (fixedHolidaysData.length - 1); ix++) {
            next = parseInt(ix) + 1;
            fixedHolidaysData[ix] = fixedHolidaysData[next];
        }
        fixedHolidaysData[ix] = null;
        // reduce the length of the array (very imp).
        fixedHolidaysData.length = fixedHolidaysData.length - 1;
        fillFixedHolidays();
    }

    function updateFixedHoliday() {
        add = document.forms[0].addFixedHoliday.value;
        index = 0;
        if(add  == "true") {
            index = fixedHolidaysData.length;
            // increase the length of the array by one
            fixedHolidaysData.length = index + 1;
            fixedHolidaysData[index] = new Array();
        } else {
            index = document.forms[0].fixedHolidaysList.value;
        }
        col = 0;
        fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayFormat.value;
        fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayName.value;
        fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayMonth.value;
        fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayType.value;
        if(document.forms[0].fixedHolidayFormat.value == FORMAT_DAY) {
            fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayDate.value;
            fixedHolidaysData[index][col++] = DELIMITER;
        } else {
            fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayDOW.value;
            fixedHolidaysData[index][col++] = document.forms[0].fixedHolidayOccur.value;
            fixedHolidaysData[index][col++] = DELIMITER;
        }
        fillFixedHolidays();
    }

    function isValidFixedHoliday() {
        if(!checkForWhitespace("document.forms[0]", "Holiday Name", "holidayName")) {
            return false;
        }
        if(document.forms[0].holFormat[0].checked == true) {
            // date field
            if(!checkForWhitespace("document.forms[0]", "Date", "date")) {
                return false;
            }
            if(!checkForNumeric("document.forms[0]", "Date", "date")) {
                return false;
            }
            if(!checkForFixedLength("document.forms[0]", "Date", "date", 2, 1)) {
                return false;
            }
        }
        return true;
    }
