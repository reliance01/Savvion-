    function loadData() {
        // get data from parent
        document.forms[0].calYear.value = opener.document.forms[0].calYear.value;
        if(opener.document.forms[0].override.value == "true") {
            document.forms[0].override.checked = true;
        }
    }

    function submitCalYear(openAgain) {
        if(!isValidYear()) {
            return false;
        }

        // send data to parent
        opener.document.forms[0].calYear.value = document.forms[0].calYear.value;
        if(document.forms[0].override.checked == true) {
            opener.document.forms[0].override.value = "true";
        } else {
            opener.document.forms[0].override.value = "false";
        }
        // call to update calYears
        opener.updateCalYear();

        if(openAgain == 'true') {
            document.forms[0].reset();
        } else {
            window.close();
        }
    }

    function fillCalYears() {
        calYearsDropDown = document.forms[0].yearsList;
        // Empty the calYear Drop Down
        for (m = calYearsDropDown.options.length - 1; m >= 0; m--) {
            calYearsDropDown.options[m] = null;
        }

        if(calYearsData.length == 0) {
            calYearsDropDown.options[0] = new Option("None", "-1");
        } else {
            // Fill the calYears Drop Down
            for (i = 0; i < calYearsData.length; i++) {
                calYearsDropDown.options[i] = new Option(calYearsData[i][0], i);
            }
        }
    }

    function newCalYear() {
        document.forms[0].calYear.value = "";
        document.forms[0].override.value = "false";
        document.forms[0].addCalYear.value = "true";
        var newCalWin = window.open('pop_calyear_add.jsp','addcalyear','width=484,height=155');
        newCalWin.focus();
    }

    function editCalYear() {
        selected = document.forms[0].yearsList.value;
        document.forms[0].calYear.value = calYearsData[selected][0];
        document.forms[0].override.value = calYearsData[selected][1];
        document.forms[0].addCalYear.value = "";
        var editCalWin = window.open('pop_calyear_det.jsp','editcalyear','width=484,height=155');
        editCalWin.focus();
    }

    function deleteCalYear() {
        window.close();
        opener.deleteCalYearData();
    }

    function deleteCalYearData() {
        selected = document.forms[0].yearsList.value;
        for(ix = selected; ix < (calYearsData.length - 1); ix++) {
            next = parseInt(ix) + 1;
            calYearsData[ix] = calYearsData[next];
        }
        calYearsData[ix] = null;
        // reduce the length of the array (very imp).
        calYearsData.length = calYearsData.length - 1;
        fillCalYears();
    }

    function updateCalYear() {
        add = document.forms[0].addCalYear.value;
        if(add  == "true") {
            index = calYearsData.length;
            // increase the length of the array by one
            calYearsData.length = index + 1;
            calYearsData[index] = new Array();
        } else {
            index = document.forms[0].yearsList.value;
        }
        calYearsData[index][0] = document.forms[0].calYear.value;
        calYearsData[index][1] = document.forms[0].override.value;
        calYearsData[index][2] = DELIMITER;
        fillCalYears();
    }

    function isValidYear() {
        // year field
        if(!checkForWhitespace("document.forms[0]", "Year", "calYear")) {
            return false;
        }
        if(!checkForNumeric("document.forms[0]", "Year", "calYear")) {
            return false;
        }
        if(!checkForFixedLength("document.forms[0]", "Year", "calYear", 4, 4)) {
            return false;
        }
        return true;
    }