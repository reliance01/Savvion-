    function loadData() {
        fillMonthDropDown("splBizHourMonth");
        fillHoursDropDown("splBizStartTimeHrs");
        fillMinutesDropDown("splBizStartTimeMins");
        fillHoursDropDown("splBizEndTimeHrs");
        fillMinutesDropDown("splBizEndTimeMins");
        // get data from parent
        selectValueInDropDown(eval(document.forms[0].splBizStartTimeHrs), opener.document.forms[0].splBizStartTimeHrs.value);
        selectValueInDropDown(eval(document.forms[0].splBizStartTimeMins), opener.document.forms[0].splBizStartTimeMins.value);
        selectValueInDropDown(eval(document.forms[0].splBizEndTimeHrs), opener.document.forms[0].splBizEndTimeHrs.value);
        selectValueInDropDown(eval(document.forms[0].splBizEndTimeMins), opener.document.forms[0].splBizEndTimeMins.value);
        if(opener.document.forms[0].splBizHourFormat.value == opener.FORMAT_DAY) {
            document.forms[0].splBizHourFormat[1].checked = true;
        } else {
            document.forms[0].splBizHourFormat[0].checked = true;
        }

        if(opener.document.forms[0].splBizHourFormat.value == opener.FORMAT_DOW) {
            selectValueInDropDown(eval(document.forms[0].splBizHourDOW), opener.document.forms[0].splBizHourDOW.value);
        } else {
            document.forms[0].splBizHourYear.value = opener.document.forms[0].splBizHourYear.value;
            selectValueInDropDown(eval(document.forms[0].splBizHourMonth), opener.document.forms[0].splBizHourMonth.value);
            document.forms[0].splBizHourDate.value = opener.document.forms[0].splBizHourDate.value;
        }
    }

    function submitSpecialBizHour(openAgain) {
        if(!isValidBusinessHours()){
            return false;
        }
        // send data to parent
        opener.document.forms[0].splBizStartTimeHrs.value = document.forms[0].splBizStartTimeHrs.value;
        opener.document.forms[0].splBizStartTimeMins.value = document.forms[0].splBizStartTimeMins.value;
        opener.document.forms[0].splBizEndTimeHrs.value = document.forms[0].splBizEndTimeHrs.value;
        opener.document.forms[0].splBizEndTimeMins.value = document.forms[0].splBizEndTimeMins.value;
        if(document.forms[0].splBizHourFormat[0].checked == true) {
            opener.document.forms[0].splBizHourFormat.value = opener.FORMAT_DOW;
            opener.document.forms[0].splBizHourDOW.value = document.forms[0].splBizHourDOW.value;
        } else {
            opener.document.forms[0].splBizHourFormat.value = opener.FORMAT_DAY;
            opener.document.forms[0].splBizHourYear.value = document.forms[0].splBizHourYear.value;
            opener.document.forms[0].splBizHourMonth.value = document.forms[0].splBizHourMonth.value;
            opener.document.forms[0].splBizHourDate.value = document.forms[0].splBizHourDate.value;
        }
        // call to update holidays
        opener.updateSpecialBizHour();

        if(openAgain == 'true') {
            document.forms[0].reset();
        } else {
            window.close();
        }
    }

    function fillSpecialBizHours() {
        bizHoursDropDown = document.forms[0].specialBizHoursList;
        // Empty the special business hours Drop Down
        for (m = bizHoursDropDown.options.length - 1; m >= 0; m--) {
            bizHoursDropDown.options[m] = null;
        }

        if(specialBizHoursData.length == 0) {
            bizHoursDropDown.options[0] = new Option("None", "-1");
        } else {
            // Fill the special business hours Drop Down
            for (i = 0; i < specialBizHoursData.length; i++) {
                if(specialBizHoursData[i][0] == FORMAT_DOW) {
                    bizHoursName = specialBizHoursData[i][3];
                } else {
                    bizHoursName = specialBizHoursData[i][5] + " " +
                                   specialBizHoursData[i][4] + " " +
                                   specialBizHoursData[i][3];
                }
                bizHoursDropDown.options[i] = new Option(bizHoursName, i);
            }
        }
    }

    function newSpecialBizHour() {
        document.forms[0].splBizStartTimeHrs.value = "";
        document.forms[0].splBizStartTimeMins.value = "";
        document.forms[0].splBizEndTimeHrs.value = "";
        document.forms[0].splBizEndTimeMins.value = "";
        document.forms[0].splBizHourDOW.value = "";
        document.forms[0].splBizHourYear.value = "";
        document.forms[0].splBizHourMonth.value = "";
        document.forms[0].splBizHourDate.value = "";
        document.forms[0].splBizHourFormat.value = "";
        document.forms[0].addSpecialBizHour.value = "true";
        var newWin = window.open('pop_bushrs_add.jsp','addbushours','width=700,height=190');
        newWin.focus();
    }

    function editSpecialBizHour() {
        selected = document.forms[0].specialBizHoursList.value;
        col = 0;
        document.forms[0].splBizHourFormat.value = specialBizHoursData[selected][col++];
        startTime = specialBizHoursData[selected][col++];
        document.forms[0].splBizStartTimeHrs.value = getHours(startTime);
        document.forms[0].splBizStartTimeMins.value = getMinutes(startTime);
        endTime = specialBizHoursData[selected][col++];
        document.forms[0].splBizEndTimeHrs.value = getHours(endTime);
        document.forms[0].splBizEndTimeMins.value = getMinutes(endTime);
        if(document.forms[0].splBizHourFormat.value == FORMAT_DOW) {
            document.forms[0].splBizHourDOW.value = specialBizHoursData[selected][col++];
        } else {
            document.forms[0].splBizHourYear.value = specialBizHoursData[selected][col++];
            document.forms[0].splBizHourMonth.value = specialBizHoursData[selected][col++];
            document.forms[0].splBizHourDate.value = specialBizHoursData[selected][col++];
        }
        document.forms[0].addSpecialBizHour.value = "";
        window.open('pop_bushrs_det.jsp','editbushours','width=700,height=190');
    }

    function deleteSpecialBizHour() {
        window.close();
        opener.deleteSpecialBizHourData();
    }

    function deleteSpecialBizHourData() {
        selected = document.forms[0].specialBizHoursList.value;
        for(ix = selected; ix < (specialBizHoursData.length - 1); ix++) {
            next = parseInt(ix) + 1;
            specialBizHoursData[ix] = specialBizHoursData[next];
        }
        specialBizHoursData[ix] = null;
        // reduce the length of the array (very imp).
        specialBizHoursData.length = specialBizHoursData.length - 1;
        fillSpecialBizHours();
    }

    function updateSpecialBizHour() {
        add = document.forms[0].addSpecialBizHour.value;
        index = 0;
        if(add  == "true") {
            index = specialBizHoursData.length;
            // increase the length of the array by one
            specialBizHoursData.length = index + 1;
            specialBizHoursData[index] = new Array();
        } else {
            index = document.forms[0].specialBizHoursList.value;
        }
        startTime = getTime(document.forms[0].splBizStartTimeHrs.value,
                            document.forms[0].splBizStartTimeMins.value);
        endTime = getTime(document.forms[0].splBizEndTimeHrs.value,
                            document.forms[0].splBizEndTimeMins.value);

        col = 0;
        specialBizHoursData[index][col++] = document.forms[0].splBizHourFormat.value;
        specialBizHoursData[index][col++] = startTime;
        specialBizHoursData[index][col++] = endTime;
        if(document.forms[0].splBizHourFormat.value == FORMAT_DOW) {
            specialBizHoursData[index][col++] = document.forms[0].splBizHourDOW.value;
            specialBizHoursData[index][col++] = DELIMITER;
        } else {
            specialBizHoursData[index][col++] = document.forms[0].splBizHourYear.value;
            specialBizHoursData[index][col++] = document.forms[0].splBizHourMonth.value;
            specialBizHoursData[index][col++] = document.forms[0].splBizHourDate.value;
            specialBizHoursData[index][col++] = DELIMITER;
        }
        fillSpecialBizHours();
    }

    function isValidBusinessHours() {
        // special business hours start time
        if(eval(document.forms[0].splBizStartTimeHrs.value) == 0 &&
           eval(document.forms[0].splBizStartTimeMins.value) == 0) {
            alert("Please enter a value for the Special Business Hours Start Time field." );
            document.forms[0].splBizStartTimeHrs.focus();
            return (false);
        }

        // special  business hours end time
        if(eval(document.forms[0].splBizEndTimeHrs.value) == 0 &&
           eval(document.forms[0].splBizEndTimeMins.value) == 0) {
            alert("Please enter a value for the Special Business Hours End Time field." );
            document.forms[0].splBizEndTimeHrs.focus();
            return (false);
        }

        startTime = getTime(document.forms[0].splBizStartTimeHrs.value,
                            document.forms[0].splBizStartTimeMins.value);
        endTime = getTime(document.forms[0].splBizEndTimeHrs.value,
                            document.forms[0].splBizEndTimeMins.value);

        if(startTime >= endTime) {
            alert("The Start Time cannot be greater than or equal to the End Time for the Special Business Hours." );
            document.forms[0].splBizStartTimeHrs.focus();
            return (false);
        }

        if(document.forms[0].splBizHourFormat[1].checked == true) {
            // year field
            if(!checkForWhitespace("document.forms[0]", "Year", "splBizHourYear")) {
                return false;
            }
            if(!checkForNumeric("document.forms[0]", "Year", "splBizHourYear")) {
                return false;
            }
            if(!checkForFixedLength("document.forms[0]", "Year", "splBizHourYear", 4, 4)) {
                return false;
            }

            // date field
            if(!checkForWhitespace("document.forms[0]", "Date", "splBizHourDate")) {
                return false;
            }
            if(!checkForNumeric("document.forms[0]", "Date", "splBizHourDate")) {
                return false;
            }
            if(!checkForFixedLength("document.forms[0]", "Date", "splBizHourDate", 2, 1)) {
                return false;
            }

            if(!checkForDate("document.forms[0]", "splBizHourYear", "splBizHourMonth", "splBizHourDate")) {
                return false;
            }
        }
        return true;
    }