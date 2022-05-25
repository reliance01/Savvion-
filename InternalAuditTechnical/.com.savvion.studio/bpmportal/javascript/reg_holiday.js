    function loadData() {
        // get data from parent
        document.forms[0].holidayName.value = opener.document.forms[0].regHolidayName.value;
        document.forms[0].year.value = opener.document.forms[0].regHolidayYear.value;
        selectValueInDropDown(eval(document.forms[0].month), opener.document.forms[0].regHolidayMonth.value);
        document.forms[0].date.value = opener.document.forms[0].regHolidayDate.value;
        document.forms[0].holidayType[opener.document.forms[0].regHolidayType.value].checked = true;
    }

    function submitRegHoliday(openAgain) {
        if(!isValidRegularHoliday()) {
            return false;
        }

        // send data to parent
        opener.document.forms[0].regHolidayName.value = document.forms[0].holidayName.value;
        opener.document.forms[0].regHolidayYear.value = document.forms[0].year.value;
        opener.document.forms[0].regHolidayMonth.value = document.forms[0].month.value;
        opener.document.forms[0].regHolidayDate.value = document.forms[0].date.value;

        for (radIndex = 0; radIndex < document.forms[0].holidayType.length; radIndex++) {
            if (document.forms[0].holidayType[radIndex].checked == true) {
                opener.document.forms[0].regHolidayType.value =
                document.forms[0].holidayType[radIndex].value;
                break;
            }
        }

        // call to update holidays
        opener.updateRegHoliday();

        if(openAgain == 'true') {
            document.forms[0].reset();
        } else {
            window.close();
        }
    }

    function fillRegHolidays() {
        holidaysDropDown = document.forms[0].regularHolidaysList;
        // Empty the holiday Drop Down
        for (m = holidaysDropDown.options.length - 1; m >= 0; m--) {
            holidaysDropDown.options[m] = null;
        }

        if(regHolidaysData.length == 0) {
            holidaysDropDown.options[0] = new Option("None", "-1");
        } else {
            // Fill the holidays Drop Down
            for (i = 0; i < regHolidaysData.length; i++) {
                holidayName = regHolidaysData[i][0] + " (" + regHolidaysData[i][1] + ")";
                holidaysDropDown.options[i] = new Option(holidayName, i);
            }
        }
    }

    function newRegHoliday() {
        document.forms[0].regHolidayName.value = "";
        document.forms[0].regHolidayYear.value = "";
        document.forms[0].regHolidayMonth.value = "";
        document.forms[0].regHolidayDate.value = "";
        document.forms[0].regHolidayType.value = "0";
        document.forms[0].addRegHoliday.value = "true";
        var newWin = window.open('pop_regholiday_add.jsp','addregholiday','width=462,height=258');
        newWin.focus();
    }

    function editRegHoliday() {
        selected = document.forms[0].regularHolidaysList.value;
        col = 0;
        document.forms[0].regHolidayName.value = regHolidaysData[selected][col++];
        document.forms[0].regHolidayYear.value = regHolidaysData[selected][col++];
        document.forms[0].regHolidayMonth.value = regHolidaysData[selected][col++];
        document.forms[0].regHolidayDate.value = regHolidaysData[selected][col++];
        document.forms[0].regHolidayType.value = regHolidaysData[selected][col++];
        document.forms[0].addRegHoliday.value = "";
        var editWin = window.open('pop_regholiday_det.jsp','editregholiday','width=462,height=258');
        editWin.focus();
    }

    function deleteRegHoliday() {
        window.close();
        opener.deleteRegHolidayData();
    }

    function deleteRegHolidayData() {
        selected = document.forms[0].regularHolidaysList.value;
        for(ix = selected; ix < (regHolidaysData.length - 1); ix++) {
            next = parseInt(ix) + 1;
            regHolidaysData[ix] = regHolidaysData[next];
        }
        regHolidaysData[ix] = null;
        // reduce the length of the array (very imp).
        regHolidaysData.length = regHolidaysData.length - 1;
        fillRegHolidays();
    }

    function updateRegHoliday() {
        add = document.forms[0].addRegHoliday.value;
        index = 0;
        if(add  == "true") {
            index = regHolidaysData.length;
            // increase the length of the array by one
            regHolidaysData.length = index + 1;
            regHolidaysData[index] = new Array();
        } else {
            index = document.forms[0].regularHolidaysList.value;
        }
        col = 0;
        regHolidaysData[index][col++] = document.forms[0].regHolidayName.value;
        regHolidaysData[index][col++] = document.forms[0].regHolidayYear.value;
        regHolidaysData[index][col++] = document.forms[0].regHolidayMonth.value;
        regHolidaysData[index][col++] = document.forms[0].regHolidayDate.value;
        regHolidaysData[index][col++] = document.forms[0].regHolidayType.value;
        regHolidaysData[index][col++] = DELIMITER;
        fillRegHolidays();
    }

    function isValidRegularHoliday() {
        if(!checkForWhitespace("document.forms[0]", "Holiday Name", "holidayName")) {
            return false;
        }

        // year field
        if(!checkForWhitespace("document.forms[0]", "Year", "year")) {
            return false;
        }
        if(!checkForNumeric("document.forms[0]", "Year", "year")) {
            return false;
        }
        if(!checkForFixedLength("document.forms[0]", "Year", "year", 4, 4)) {
            return false;
        }

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

        if(!checkForDate("document.forms[0]", "year", "month", "date")) {
            return false;
        }

        return true;
    }