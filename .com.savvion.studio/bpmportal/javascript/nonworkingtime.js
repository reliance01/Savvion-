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
   MM_openBrWindow('pop_nonwrkhrs_det.jsp?name='+nm+'&value='+srno+'&type='+type+'&opt='+strOpt,win_name,win_size);
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
  var cssClass1

  var nm = escapeSpecialChars(name);
  nm=escape(nm);
  var dispStr = type;
  if(halfday == 1 && type == "Holiday")
  {
  	dispStr+='(First Half)';
  }
  else if(halfday == 2 && type == "Holiday")
  {
  	dispStr+='(Second Half)';
  }
  else if(halfday == 0 && type == "Holiday")
  {
  	dispStr+='(Full Day)';
  }
  else
  {
  	dispStr = type;
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
  if(isMicrosoft()){
	   onclickjs = "SelcRow(this,document.forms[0],\"NonWorkingTable\","+rownum+");";
           var node = "<input type=\"checkbox\" name=\"nwcheckbox"+srno+"\" id=\"check"+srno+"\" value=\"checkbox\" onclick=\""+onclickjs+"\"/>";
	   newChild =document.createElement(node);
	   newChild.onclick = onclickjs;
	  
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
  newChild = document.createTextNode(month.substring(0,3));
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(date);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(day.substring(0,3));
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
        if(name != nm && type == typ && (yr == year || 'all' == yr.toLowerCase()) && mn == month && dt == date && (day.indexOf(dy) != -1) && (oc.toLowerCase() == 'all'))
        {
            found = true;
            break;
        }
    }
    return found;
}

function isRecordPresent(name)
{
    //Check if the entry already exists if yes then overwrite
    var found = false;
    var idx = -1;
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nm = nonworkingHrsArray[count].name;
        if(nm == name)
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
    for(var count=0;count < nonworkingHrsArray.length;count++)
    {
        var nm = nonworkingHrsArray[count].name;
        if(nm == name)
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
	   alert("Please select an holiday to delete!");
	   return false;
  }	   
  
  if (isConfirm == true){
     if (confirm("Are you sure you want to delete non-working time? " )) {
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
	return nhtext;

}