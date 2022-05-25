    function loadData() {
        // get data from parent
        document.forms[0].weekendName.value = opener.document.forms[0].weekendName.value;
        selectValueInDropDown(eval(document.forms[0].dow), opener.document.forms[0].weekendDOW.value);
        selectValueInDropDown(eval(document.forms[0].occurence), opener.document.forms[0].weekendOccur.value);
    }

    function submitWeekend(openAgain) {
        if(!isValidWeekend()) {
            return false;
        }
        // send data to parent
        opener.document.forms[0].weekendName.value = document.forms[0].weekendName.value;
        opener.document.forms[0].weekendDOW.value = document.forms[0].dow.value;
        opener.document.forms[0].weekendOccur.value = document.forms[0].occurence.value;
        // call to update weekends
        opener.updateWeekend();

        if(openAgain == 'true') {
            document.forms[0].reset();
        } else {
            window.close();
        }
    }

    function fillWeekends() {
        weekendsDropDown = document.forms[0].weekendsList;
        // Empty the weekend Drop Down
        for (m = weekendsDropDown.options.length - 1; m >= 0; m--) {
            weekendsDropDown.options[m] = null;
        }

        if(weekendsData.length == 0) {
            weekendsDropDown.options[0] = new Option("None", "-1");
        } else {
            // Fill the weekends Drop Down
            for (i = 0; i < weekendsData.length; i++) {
                weekendsDropDown.options[i] = new Option(weekendsData[i][0], i);
            }
        }
    }

    function newWeekend() {
        document.forms[0].weekendName.value = "";
        document.forms[0].weekendDOW.value = "";
        document.forms[0].weekendOccur.value = "";
        document.forms[0].addWeekend.value = "true";
        var newWeekendWin = window.open('pop_weekend_add.jsp','addweekend','width=484,height=190');
        newWeekendWin.focus();
    }

    function editWeekend() {
        selected = document.forms[0].weekendsList.value;
        col = 0
        document.forms[0].weekendName.value = weekendsData[selected][col++];
        document.forms[0].weekendDOW.value = weekendsData[selected][col++];
        document.forms[0].weekendOccur.value = weekendsData[selected][col++];
        document.forms[0].addWeekend.value = "";
        var editWeekendWin = window.open('pop_weekend_det.jsp','editweekend','width=484,height=190');
        editWeekendWin.focus();
    }

    function deleteWeekend() {
        window.close();
        opener.deleteWeekendData();
    }

    function deleteWeekendData() {
        selected = document.forms[0].weekendsList.value;
        for(ix = selected; ix < (weekendsData.length - 1); ix++) {
            next = parseInt(ix) + 1;
            weekendsData[ix] = weekendsData[next];
        }
        weekendsData[ix] = null;
        // reduce the length of the array (very imp).
        weekendsData.length = weekendsData.length - 1;
        fillWeekends();
    }

    function updateWeekend() {
        add = document.forms[0].addWeekend.value;
        if(add  == "true") {
            index = weekendsData.length;
            // increase the length of the array by one
            weekendsData.length = index + 1;
            weekendsData[index] = new Array();
        } else {
            index = document.forms[0].weekendsList.value;
        }
        col = 0;
        weekendsData[index][col++] = document.forms[0].weekendName.value;
        weekendsData[index][col++] = document.forms[0].weekendDOW.value;
        weekendsData[index][col++] = document.forms[0].weekendOccur.value;
        weekendsData[index][col++] = DELIMITER;
        fillWeekends();
    }

    function isValidWeekend() {
        if(!checkForWhitespace("document.forms[0]", "Weekend Name", "weekendName")) {
            return false;
        }
        return true;
    }
