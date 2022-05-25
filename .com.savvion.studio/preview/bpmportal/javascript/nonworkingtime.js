// Object constructor
function nonworkingHrs(name,type,halfday,year,month,date,day,occ) {
   this.name = unescape(name);
   this.type = type;
   this.halfday = halfday;
   this.year = year;
   this.month = month;
   this.date = date;
   this.day = day;
   this.occ = occ;
}

function popnonworkingwindow(nm,srno,type,strOpt)
{
var win_name = 'edtnonworkinghrs';
   var win_size = 'width=642,height=260';
   MM_openBrWindow(encodeURI('pop_nonwrkhrs_det.jsp?name='+unescape(nm)+'&value='+srno+'&type='+type+'&opt='+strOpt,win_name,win_size));
}
function refreshNonWorkingTable(tableName) {
   var table = document.getElementById(tableName);
   var rows = table.rows;
   //to refresh delete all rows from the table
   if(rows.length > 1)
   {
    for(var i=rows.length-1;i > 0;i--)
    {
        table.deleteRow(i);
    }
   }
   //Add new rows from working hrs array
   if(nonworkingHrsArray.length > 0) {
    for(var i=0;i<nonworkingHrsArray.length;i++) {
        var nonworkingHr = nonworkingHrsArray[i];
        var nm = nonworkingHr.name;
        var typ = nonworkingHr.type;
        var hday = nonworkingHr.halfday;
        var yr = nonworkingHr.year;
        var mn = nonworkingHr.month;
        var dt = nonworkingHr.date;
        var dy = nonworkingHr.day;
        var oc = nonworkingHr.occ;
        addRowToNWTable(tableName,nm,typ,hday,yr,mn,dt,dy,oc,i);
    }
   }
   //FIXME: dolayout
   var calendarNonWorkingTimeTab = Ext.getCmp('calendarNonWorkingTimeTabId');
   if (!Ext.isEmpty(calendarNonWorkingTimeTab)) {
        calendarNonWorkingTimeTab.doLayout();
   }
   return true;
}

function escapeSpecialChars (str)
{
	str = this != window? this : str;
	return str.replace("'",'\\\'');
}

function addRowToNWTable(tableName,name,type,halfday,year,month,date,day,occ,srno)
{  
  var table = document.getElementById(tableName);
  var newRow;
  var newCell;
  var newChild;
  var cssClass;
  var cssClass1;
  var dispStrMonth;
  var dispStrDay;

  var nm = escapeSpecialChars(name);
  nm=escape(nm);
  var dispStr = type;
  if(halfday == 1 && type == "Holiday")
  {
  	dispStr = getLocalizedString('HolidayFirstHalf');
  }
  else if(halfday == 2 && type == "Holiday")
  {
  	dispStr = getLocalizedString('HolidaySecondHalf');
  }
  else if(halfday == 0 && type == "Holiday")
  {
  	dispStr = getLocalizedString('HolidayFullDay');
  }
  else if(type == "Weekend") {
    dispStr = getLocalizedString('Weekend');
  }
  else
  {
  	dispStr = type;
  }
  
  if(month != "-") {
    dispStrMonth = getLocalizedString(month+'FN');
  } else {
    dispStrMonth = month;
  }
  
  if(day != "-") {
    dispStrDay = getLocalizedString(day);
  } else {
    dispStrDay = day;
  }
  
  var strOpt = 'day';
  if(date != "-")
    strOpt = 'date';
  newRow = document.createElement('TR');
  cssClass = ((srno%2)==0)?'SegFieldCntr':'SegFieldCntrAlt';
  cssClass1 =((srno%2)==0)?'SegChkBox':'SegChkBoxAlt';
  cssClass2 = ((srno%2)==0)?'SegFieldLft':'SegFieldLftAlt';
  newCell = document.createElement('TD');
  newChild = document.createElement('INPUT');
  
  var rownum = srno+1;
  var onclickjs = "SelcRow(this,document.forms[0],\"NonWorkingTable\","+rownum+")";
  
  if(isNetscape()){
  newChild = document.createElement('INPUT');
  newChild.setAttribute('type','checkbox');
  newChild.setAttribute('value','checkbox');
  newChild.setAttribute('name','nwcheckbox'+srno);
  newChild.setAttribute('onclick',onclickjs);
  }
 
  if(Ext.isIE6 || Ext.isIE7){
	   onclickjs = "SelcRow(this,document.forms[0],\"NonWorkingTable\","+rownum+");";
           var node = "<input type=\"checkbox\" name=\"nwcheckbox"+srno+"\" id=\"check"+srno+"\" value=\"checkbox\" onclick=\""+onclickjs+"\"/>";
	   newChild =document.createElement(node);
	   newChild.onclick = onclickjs;
	  
  } else {  
    newChild = document.createElement('INPUT');
    newChild.setAttribute('type','checkbox');
    newChild.setAttribute('value','checkbox');
    newChild.setAttribute('name','nwcheckbox'+srno);
    newChild.setAttribute('onclick',onclickjs);
  }
  newChild.className = 'InptChkBox';
  newCell.appendChild(newChild);
  newCell.className = cssClass1;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(srno+1);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newCell.innerHTML="<A href=javascript:popnonworkingwindow('"+nm+"',"+srno+",'"+type+"','"+strOpt+"') class=TblLnk>"+name+"</a>";
  newCell.className = cssClass2;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(dispStr);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(year);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(dispStrMonth.substring(0,3));
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(date);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(dispStrDay.substring(0,3));
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(title_case(occ));
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  table.tBodies[0].appendChild(newRow);

  setCheckBoxStyleForIE();
  return true;
}

function isNonWorkingPatternPresent(name,type,halfday,year,month,date,day,occ)
{
    //Check if the pattern already exists if yes then overwrite
    var found = false;
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nm = nonworkingHrsArray[count].name;
        var typ = nonworkingHrsArray[count].type;
        var yr = nonworkingHrsArray[count].year;
        var mn = nonworkingHrsArray[count].month;
        var dt = nonworkingHrsArray[count].date;
        var dy = nonworkingHrsArray[count].day;
        var oc = nonworkingHrsArray[count].occ;
        if(name != nm && type == typ && (yr == year || 'all' == yr.toLowerCase()) && mn == month && dt == date && (day.indexOf(dy) != -1) && (oc == occ || oc.toLowerCase() == 'all'))
        {
            found = true;
            break;
        }
    }
    return found;
}

function isRecordPresent(name, year)
{
    //Check if the entry already exists if yes then overwrite
    var found = false;
    var idx = -1;
    
    if(typeof(year) == 'undefined' || year == null){
      year = '';
    } 
    var nm;
    var yr;
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        nm = nonworkingHrsArray[count].name;
        yr = nonworkingHrsArray[count].year;
        if(nm == name && yr == year)
        {
            found = true;
            break;
        }
    }
    if(found == true)
    	return true;
    else
    	return false;
}

function updateNonWorkingTable(name,type,halfday, year,month,date,day,occ)
{
    //Check if the entry already exists if yes then overwrite
    var found = false;
    var idx = -1;
    var nm;
    var yr;
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        nm = nonworkingHrsArray[count].name;
        yr = nonworkingHrsArray[count].year;
        if(nm == name && yr == year)
        {
            found = true;
            idx=count;
        }
    }
    if(!found) {
        idx = nonworkingHrsArray.length;
    }
    nonworkingHrsArray[idx] = new nonworkingHrs(name,type,halfday, year,month,date,day,occ);
    refreshNonWorkingTable("NonWorkingTable");
}

function deleteNonWorkingTime()
{
  var theForm = document.forms[0];
  var chkbxname = 'nwcheckbox';
  var tmpArray = new Array();
  var counter = 0;
  var isConfirm = false;
  var checkedCount = 0;
  
 for (var m=0; m<theForm.elements.length;m++){
  	 var checkboxname = theForm.elements[m].name;
	 if(theForm.elements[m].type=='checkbox' && checkboxname.substring(0,10)=='nwcheckbox'){
	  if(theForm.elements[m].checked) 	
          isConfirm = true;
          
      }
  
  }
  
  if(isConfirm == false){
	   alert(calenderi18["bizCalAlert59"]);
	   return false;
  }	   
  
  if (isConfirm == true){
     if (confirm(calenderi18["bizCalAlert60"])) {
  for(var k=0;k<theForm.elements.length;k++)
  {
    if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name.indexOf(chkbxname) != -1)
    {
      if(!theForm.elements[k].checked)
      {
        var str = theForm.elements[k].name.substring(chkbxname.length,theForm.elements[k].name.length);
        tmpArray[counter]=nonworkingHrsArray[parseInt(str)];
        counter++;
      }
    }
  }
  nonworkingHrsArray = tmpArray;
  refreshNonWorkingTable("NonWorkingTable");
  theForm.nwchkbxAll.checked = false;  
  }
  }
  disableBtn();
}

function deleteNonWorkingTimeFromId(id)
{
  var tmpArray = new Array();
  var counter = 0;
  for(var k=0;k<nonworkingHrsArray.length;k++)
  {
    if(k != id)
    {
      tmpArray[counter]=nonworkingHrsArray[k];
      counter++;
    }
  }
  nonworkingHrsArray = tmpArray;
  refreshNonWorkingTable("NonWorkingTable");
}

function setNonWorkingTimeDetails(id)
{
    //set the day/date and time
    document.forms[0].nonwrk_name.value= nonworkingHrsArray[id].name;
    document.forms[0].nonwrk_type.value= nonworkingHrsArray[id].type;
    document.forms[0].nonwrk_halfday.value= nonworkingHrsArray[id].halfday;
    document.forms[0].nonwrk_year.value= nonworkingHrsArray[id].year;
    document.forms[0].nonwrk_month.value= nonworkingHrsArray[id].month;
    document.forms[0].nonwrk_date.value= nonworkingHrsArray[id].date;
    document.forms[0].nonwrk_day.value= nonworkingHrsArray[id].day;
    document.forms[0].nonwrk_occ.value= nonworkingHrsArray[id].occ;
}

function title_case(instr)
{
	var htext, nhtext;
	var htext= instr;
	htext=instr.toLowerCase();
	// Just in case they're all caps.
	j=instr.length;
	nhtext="";
	for(i=0;i<j;i++)
	{
		if(i==0)
		// To capitalize the first character.
		{
			nhtext=nhtext+htext.substr(i,1).toUpperCase();
		}
		else if(htext.charAt(i)==" ")
		{
			// Checks for the appearance of the space character.
			nhtext=nhtext+htext.substr(i,1);
			// Adds that space character to the string.
			nhtext=nhtext+htext.substr(++i,1).toUpperCase();
			// Capitalizes and adds the next character to the
			// string.
		}
		else if(htext.charAt(i)=="")
		{
			// Checks for the appearance of the newline
			character.
			nhtext=nhtext+htext.substr(i,1);
			// Adds the newline character to the string.
			nhtext=nhtext+htext.substr(++i,1).toUpperCase();
			// Capitalizes and adds the next character to the
			// string.
		}
		else
		{
			nhtext=nhtext+htext.substr(i,1);
			// Adds the character in a normal way.
		}
	}
	if(nhtext != "-") {
	   nhtext = getLocalizedString(nhtext);
  }
	return nhtext;

}

function isWorkingTimeExist(date, month, year) {   
    //Check user selected date is exists in working date
    var isDateExist = false;
    var tempDate = month.substring(0,3) + " "+ date +", "+year;    
    var workingHrDate;
    for(var ix=0;ix < workingHrsArray.length;ix++) {        
        workingHrDate = workingHrsArray[ix].date;
        if(workingHrDate == tempDate) {
            isDateExist = true;
            break;
        }
    }   
    return isDateExist;    
}