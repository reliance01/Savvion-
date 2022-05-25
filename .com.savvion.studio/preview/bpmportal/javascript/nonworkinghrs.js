function loadNonWorkingTime(id)
{
    var theForm = document.forms[0];
    
    opener.setNonWorkingTimeDetails(id);
    
    var name = opener.document.forms[0].nonwrk_name.value;
    var type = opener.document.forms[0].nonwrk_type.value;
    var halfday = opener.document.forms[0].nonwrk_halfday.value;
    var year = opener.document.forms[0].nonwrk_year.value;
    var month = opener.document.forms[0].nonwrk_month.value;
    var date = opener.document.forms[0].nonwrk_date.value;
    var day = opener.document.forms[0].nonwrk_day.value;
    var occ = opener.document.forms[0].nonwrk_occ.value;

    if(type != "-")
    {
        for(var i=0;i<theForm.nonwrktype.options.length;i++)
        {
            if(halfday == theForm.nonwrktype.options[i].value)
            {
                theForm.nonwrktype.options[i].selected = true;
                break;
            }
        }
        for(var k=0;k<theForm.elements.length;k++)
        {
            if(theForm.elements[k].type=='radio' && theForm.elements[k].name == 'radiobuttontype')
            {
              if(type == theForm.elements[k].value)
              {
                    theForm.elements[k].checked = true;
              }
            }
        }
    }
    if(year != "-")
    {
        for(var i=0;i<theForm.bizNonWrkHourYear.options.length;i++)
        {
            if(year == theForm.bizNonWrkHourYear.options[i].text)
            {
                theForm.bizNonWrkHourYear.options[i].selected = true;
                break;
            }
        }
    }
    if(month != "-")
    {
        for(var i=0;i<theForm.bizNonWrkHourMonth.options.length;i++)
        {
            if(theForm.bizNonWrkHourMonth.options[i].text.indexOf(month) != -1)
            {
                theForm.bizNonWrkHourMonth.options[i].selected = true;
                break;
            }
        }
    }
    if(date != "-")
    {
        for(var i=0;i<theForm.bizNonWrkHourDate.options.length;i++)
        {
            if(date == theForm.bizNonWrkHourDate.options[i].text)
            {
                theForm.bizNonWrkHourDate.options[i].selected = true;
                break;
            }
        }
    }

    if(day != "-")
    {
        for(var i=0;i<theForm.bizNonWrkHourDay.options.length;i++)
        {
            if(theForm.bizNonWrkHourDay.options[i].text.indexOf(day) != -1)
            {
                theForm.bizNonWrkHourDay.options[i].selected = true;
                break;
            }
        }
    }

    if(occ != "-")
    {
        for(var i=0;i<theForm.bizNonWrkHourOcc.options.length;i++)
        {
            if(occ == theForm.bizNonWrkHourOcc.options[i].value)
            {
                theForm.bizNonWrkHourOcc.options[i].selected = true;
                break;
            }
        }
    }
    
    onSelectType(type);
    if(date!="-")
    {
	onSelectOption('date');
    }
    if(day!="-")
    {
	onSelectOption('day');
    }
}

function validateForm()
{
    //Put the validation of form here
    if(document.forms[0].nonwrkname.value.length == 0 || trimString(document.forms[0].nonwrkname.value).length == 0)
    {
    	alert(calenderi18["bizCalAlert42"]);
    	return false;
    }
    
    if(document.forms[0].nonwrkname.value.indexOf(",") != -1)
    {
    	alert(calenderi18["bizCalAlert25"]);
    	return false;
    }
    
    if(document.forms[0].nonwrkname.value.indexOf("$") != -1) {
    	alert(calenderi18["bizCalAlert63"]);
    	return false;
    }

    var isWeekend = true;
    for (var i=0; i<document.forms[0].elements.length; i++) {
    	if (document.forms[0].elements[i].name == 'radiobuttontype' && document.forms[0].elements[i].checked == true) {
    		if(document.forms[0].elements[i].value == 'Holiday' || document.forms[0].elements[i].value == 'holiday')
    		{
    			isWeekend = false;
    		}
	}
    }
    
    var isDate = true;
    for (var i=0; i<document.forms[0].elements.length; i++) {
    	if (document.forms[0].elements[i].name == 'radiobutton' && document.forms[0].elements[i].checked == true) {
    		if(document.forms[0].elements[i].value == 'day' || document.forms[0].elements[i].value == 'Day')
    		{
    			isDate = false;
    		}
	}
    }

    if(isWeekend == true)
    {
    	if(document.forms[0].bizNonWrkHourDay.value == -1)
    	{
    		alert(calenderi18["bizCalAlert43"]);
    		return false;
    	}
    	if(document.forms[0].bizNonWrkHourOcc.value == -1)
    	{
    		alert(calenderi18["bizCalAlert44"]);
    		return false;
    	}
    	if (document.forms[0].bizNonWrkHourOcc.value == 'last')
    	{
    		alert(calenderi18["bizCalAlert45"]);
    		return false;
    	}    	
    }
    else
    {
    	if(document.forms[0].nonwrktype.value == -1)
    	{
    		alert(calenderi18["bizCalAlert46"]);
    		return false;
    	}
    	if(document.forms[0].bizNonWrkHourYear.value == -1)
    	{
    		alert(calenderi18["bizCalAlert47"]);
    		return false;
    	}
    	if(document.forms[0].bizNonWrkHourMonth.value == -1)
    	{
    		alert(calenderi18["bizCalAlert48"]);
    		return false;
    	}
    	if(document.forms[0].bizNonWrkHourYear.value != "All" || document.forms[0].bizNonWrkHourYear.value != "all")
    	{
		if(isDate == false && document.forms[0].bizNonWrkHourDay.value == -1)
		{
			alert(calenderi18["bizCalAlert49"]);
			return false;
		}
		if(isDate == false && document.forms[0].bizNonWrkHourOcc.value == -1)
		{
			alert(calenderi18["bizCalAlert50"]);
			return false;
		}
		if(isDate == false && document.forms[0].bizNonWrkHourOcc.value == "all")
		{
			alert(calenderi18["bizCalAlert51"]);
			return false;
		}
		
		if(isDate == true && document.forms[0].bizNonWrkHourDate.value == -1)
		{
			alert(calenderi18["bizCalAlert52"]);
			return false;		
		}
    	}
    	if(document.forms[0].bizNonWrkHourYear.value == "All" || document.forms[0].bizNonWrkHourYear.value == "all")
	{
		if(isDate == true && document.forms[0].bizNonWrkHourDate.value == -1)
		{
			alert(calenderi18["bizCalAlert53"]);
			return false;		
		}
	}

    }
    return true;
}


function onSelectType(sel)
{
    document.forms[0].typeselected.value=sel;
    if(sel.indexOf('Weekend') != -1 || sel.indexOf('weekend') != -1)
    {
    	document.forms[0].bizNonWrkHourYear.disabled=true;
    	document.forms[0].bizNonWrkHourMonth.disabled=true;
    	document.forms[0].bizNonWrkHourDate.disabled=true;
    	document.forms[0].nonwrktype.disabled=true;
    	onSelectOption('day');
    	disableGroup(document.forms[0],'radiobutton',true);
    }
    else
    {
    	document.forms[0].bizNonWrkHourYear.disabled=false;
    	document.forms[0].bizNonWrkHourMonth.disabled=false;
    	document.forms[0].bizNonWrkHourDate.disabled=false;
    	document.forms[0].nonwrktype.disabled=false;
    	onSelectOption('date');
    	if(document.forms[0].bizNonWrkHourYear.value=="All" || document.forms[0].bizNonWrkHourYear.value == "all")
    	{
    		disableGroup(document.forms[0],'radiobutton',false);
    	}
    	else
    	{
    		disableGroup(document.forms[0],'radiobutton',true);
    	}
    }
}


function onSelectOption(sel)
{
    document.forms[0].optionselected.value=sel;
    if(sel == 'date') {
        document.forms[0].bizNonWrkHourOcc.disabled=true;
        document.forms[0].bizNonWrkHourDay.disabled=true;
        document.forms[0].bizNonWrkHourDate.disabled=false;
    } else {
        document.forms[0].bizNonWrkHourOcc.disabled=false;
        document.forms[0].bizNonWrkHourDay.disabled=false;
        document.forms[0].bizNonWrkHourDate.disabled=true;
    }
    selectRadioButton(document.forms[0],'radiobutton',sel);
    return true;
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

function saveNonWorkingHrs(mode)
{
  
   var name = document.forms[0].nonwrkname.value;
   var year = document.forms[0].bizNonWrkHourYear.value;
   if(mode == 'add') {
   	//check if record is present
   	if(opener.isRecordPresent(name, year))
   	{
   		alert(calenderi18["bizCalAlert61"]);
   		return false;
   	}
   }   
   var type = document.forms[0].typeselected.value;
   var halfday = document.forms[0].nonwrktype.value   
   var month = document.forms[0].bizNonWrkHourMonth.value;
   var date = document.forms[0].bizNonWrkHourDate.value;
   var day = document.forms[0].bizNonWrkHourDay.value;
   var occ = document.forms[0].bizNonWrkHourOcc.value;
   
   //Check user selected date is exists in working date
    if(type == 'Holiday') {
        if(opener.isWorkingTimeExist(date, month, year)) {
            alert(calenderi18["bizCalAlert65"]);
            return false;
        }
    }
    
   if(!validateForm())
   {
   	return false;
   }
   
   if(document.forms[0].optionselected.value == 'date') {
    day = "-";
    occ = "-";
   }
   if(document.forms[0].optionselected.value == 'day') {
    date = "-";
   }
   
   if(document.forms[0].typeselected.value == 'Weekend' || document.forms[0].typeselected.value == 'weekend')
   {
   	type=document.forms[0].typeselected.value;
   	year="-";
   	month="-";
   	
   }
   if(opener.isNonWorkingPatternPresent(name,type,halfday,year,month,date,day,occ))
   {
	alert(calenderi18["bizCalAlert62"]);
	return false;
   }
   opener.updateNonWorkingTable(name,type,halfday,year,month,date,day,occ);
   opener.disableBtn();
   return true;
}
