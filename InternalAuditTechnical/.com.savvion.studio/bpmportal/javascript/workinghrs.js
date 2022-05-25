function onSelectOption(sel)
{
	document.forms[0].optionselected.value = sel;
    if(sel == 'date') {
        document.forms[0].bizHourYear.disabled=false;
        document.forms[0].bizHourMonth.disabled=false;
        document.forms[0].bizHourDOW.disabled=true;
        document.forms[0].bizHourDate.disabled=false;
    } else {
        document.forms[0].bizHourYear.disabled=true;
        document.forms[0].bizHourMonth.disabled=true;
        document.forms[0].bizHourDOW.disabled=false;
        document.forms[0].bizHourDate.disabled=true;
    }
    selectRadioButton(document.forms[0],'splBizHourFormat',sel);
    return true;
}

function validateForm()
{
    //Put the validation of form here
	// special business hours start time
	if(eval(document.forms[0].splBizStartTimeHrs.value) == 0 &&
	   eval(document.forms[0].splBizStartTimeMins.value) == 0) {
		//alert("Please enter a value for the Special Business Hours Start Time field." );
		document.forms[0].splBizStartTimeHrs.focus();
		//return (false);
	}

	// special  business hours end time
	if(eval(document.forms[0].splBizEndTimeHrs.value) == 0 &&
	   eval(document.forms[0].splBizEndTimeMins.value) == 0) {
		//alert("Please enter a value for the Special Business Hours End Time field." );
		document.forms[0].splBizEndTimeHrs.focus();
		//return (false);
	}

	startTime = getTime(document.forms[0].splBizStartTimeHrs.value,
						document.forms[0].splBizStartTimeMins.value);
	endTime = getTime(document.forms[0].splBizEndTimeHrs.value,
						document.forms[0].splBizEndTimeMins.value);

	if(startTime == endTime) {
		alert("The Start Time cannot be equal to the End Time for the Special Business Hours." );
		document.forms[0].splBizStartTimeHrs.focus();
		return (false);
	}
    return true;
}

function saveWorkingHrs()
{
    var opt=document.forms[0].optionselected.value;
    var day = "";
    var date = "";
    var time = "";

    if(!validateForm())
    {
    	return false;
    }
    if(opt == 'date') {
      day="-";
      var mon = document.forms[0].bizHourMonth.value;
      mon = mon.substring(0,3);
      date= mon +' '+ document.forms[0].bizHourDate.value +', '+ document.forms[0].bizHourYear.value;
    } else {
      day=document.forms[0].bizHourDOW.value;
      date="-";
    }

    var tmslot1 = document.forms[0].splBizStartTimeHrs.value +":"+document.forms[0].splBizStartTimeMins.value +"-"+
        document.forms[0].splBizEndTimeHrs.value +":"+document.forms[0].splBizEndTimeMins.value;

    if(tmslot1 != "00:00-00:00")
    {
        time+=tmslot1;
        if(opener.isWorkingPatternPresent(day,date))
        {
		if(!confirm("Working time for this day/date already exists. Do you want to overwrite?"))
			return false;
        }
        opener.updateWorkingTable(day,date,time);
        opener.disableBtn();
    }
    return true;
}

function loadWorkingTime(id)
{
    var theForm = document.forms[0];

    //set the day/date and time
    opener.setWorkingTimeDetails(id);
    var day = opener.document.forms[0].wrk_dow.value;
    var date = opener.document.forms[0].wrk_date.value;
    var time = opener.document.forms[0].wrk_time.value;

   if(day != "-")
    {
        for(var i=0;i<theForm.bizHourDOW.options.length;i++)
        {
            if(day == theForm.bizHourDOW.options[i].text)
            {
                theForm.bizHourDOW.options[i].selected = true;
                break;
            }
        }
    }
    if(date != "-")
    {
        var opts = date.split(' ');
        var mn = opts[0];
        mn = trimString(mn);
        var dt = opts[1];
        dt = dt.substring(0,dt.length-1);
        var yr = opts[2];
        yr = trimString(yr);
        for(var i=0;i<theForm.bizHourYear.options.length;i++)
        {
            if(yr == theForm.bizHourYear.options[i].text)
            {
                theForm.bizHourYear.options[i].selected = true;
                break;
            }
        }
        for(var i=0;i<theForm.bizHourMonth.options.length;i++)
        {
            if(theForm.bizHourMonth.options[i].text.indexOf(mn) != -1)
            {
                theForm.bizHourMonth.options[i].selected = true;
                break;
            }
        }
        for(var i=0;i<theForm.bizHourDate.options.length;i++)
        {
            if(dt == theForm.bizHourDate.options[i].text)
            {
                theForm.bizHourDate.options[i].selected = true;
                break;
            }
        }
    }


    var tms = time.split("-");
    var sttm = tms[0];
    var edtm = tms[1];

    var tmopts = sttm.split(":");
    var sthr = tmopts[0];
    var stmn = tmopts[1];

    tmopts = edtm.split(":");
    var edhr = tmopts[0];
    var edmn = tmopts[1];

    for(var i=0;i<theForm.splBizStartTimeHrs.options.length;i++)
    {
        if(sthr == theForm.splBizStartTimeHrs.options[i].text)
        {
            theForm.splBizStartTimeHrs.options[i].selected = true;
            break;
        }
    }

    for(var i=0;i<theForm.splBizStartTimeMins.options.length;i++)
    {
        if(stmn == theForm.splBizStartTimeMins.options[i].text)
        {
            theForm.splBizStartTimeMins.options[i].selected = true;
            break;
        }
    }

    for(var i=0;i<theForm.splBizEndTimeHrs.options.length;i++)
    {
        if(edhr == theForm.splBizEndTimeHrs.options[i].text)
        {
            theForm.splBizEndTimeHrs.options[i].selected = true;
            break;
        }
    }

    for(var i=0;i<theForm.splBizEndTimeMins.options.length;i++)
    {
        if(edmn == theForm.splBizEndTimeMins.options[i].text)
        {
            theForm.splBizEndTimeMins.options[i].selected = true;
            break;
        }
    }
    if(date!="-")
    {
	onSelectOption('date');
    }
    if(day!="-")
    {
	onSelectOption('day');
    }
}