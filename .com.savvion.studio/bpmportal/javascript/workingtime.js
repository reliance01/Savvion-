// Object constructor
function workingHrs(day,date,time) {
   this.day = day
   this.date = date
   this.time = time
}

function popworkingwindow(type,srno)
{
   var win_name = 'edtworkinghrs';
   var win_size = 'width=670,height=230';
   if(type == 'day') {
   	MM_openBrWindow('pop_wrkhrs_det.jsp?type=day&value='+srno,win_name,win_size);
   } else if(type == 'date') {
   	MM_openBrWindow('pop_wrkhrs_det.jsp?type=date&value='+srno,win_name,win_size);
   }
}

function refreshWorkingTable(tableName) {
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
   if(workingHrsArray.length > 0) {
    for(var i=0;i<workingHrsArray.length;i++) {
        var workingHr = workingHrsArray[i];
        var dy = workingHr.day;
        var dt = workingHr.date;
        var tm = workingHr.time;
        addRowToTable(tableName,dy,dt,tm,i);
    }
   }
   return true;
}

function addRowToTable(tableName,day,date,time,srno)
{
  var table = document.getElementById(tableName);
  var newRow;
  var newCell;
  var newChild;
  var cssClass;
  var cssClass1

  newRow = document.createElement('TR');
  cssClass = ((srno%2)==0)?'SegFieldCntr':'SegFieldCntrAlt';
  cssClass1 =((srno%2)==0)?'SegChkBox':'SegChkBoxAlt';
  cssClass2 = ((srno%2)==0)?'SegFieldLft':'SegFieldLftAlt';
  newCell = document.createElement('TD');
  
  var rownum = srno+1;
  var onclickjs = "SelcRow(this,document.forms[0],\"WorkingTable\","+rownum+")";
  
  if(isNetscape()){
  newChild = document.createElement('INPUT');
  newChild.setAttribute('type','checkbox');
  newChild.setAttribute('value','checkbox');
  newChild.setAttribute('name','wkcheckbox'+srno);
  newChild.setAttribute('onclick',onclickjs);
  }
  if(isMicrosoft()){
	   onclickjs = "SelcRow(this,document.forms[0],\"WorkingTable\","+rownum+");";
           var node = "<input type=\"checkbox\" name=\"wkcheckbox"+srno+"\" id=\"check"+srno+"\" value=\"checkbox\" onclick=\""+onclickjs+"\"/>";
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
  if(day == "-") {
    newChild = document.createTextNode(day);
    newCell.appendChild(newChild);
    newCell.className = cssClass;
    newRow.appendChild(newCell);
  } else {
    newCell.innerHTML="<A href=javascript:popworkingwindow('day',"+srno+") class=TblLnk>"+day+'...'+"</a>";
    newCell.className = cssClass2;
    newRow.appendChild(newCell);
  }

  newCell = document.createElement('TD');
  if(date == "-") {
    newChild = document.createTextNode(date);
    newCell.appendChild(newChild);
    newCell.className = cssClass;
    newRow.appendChild(newCell);
  } else {
    newCell.innerHTML="<A href=javascript:popworkingwindow('date',"+srno+") class=TblLnk>"+date+'...'+"</a>";
    newCell.className = cssClass;
    newRow.appendChild(newCell);
  }

  newCell = document.createElement('TD');
  newChild = document.createTextNode(time);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);
  table.tBodies[0].appendChild(newRow);

  setCheckBoxStyleForIE();
  return true;
}

function isWorkingPatternPresent(day,date)
{
    //Check if the pattern already exists if yes then overwrite
    var found = false;
    for(var count=0;count < workingHrsArray.length;count++)
    {
        var wday = workingHrsArray[count].day;
        var wdate = workingHrsArray[count].date;
        if(wday == day && wdate == date)
        {
            found = true;
            break;
        }
    }
    return found;
}

function updateWorkingTable(day,date,time)
{
    //Check if the entry already exists if yes then overwrite
    var found = false;
    var idx = -1;
    for(var count=0;count < workingHrsArray.length;count++)
    {
        var wday = workingHrsArray[count].day;
        var wdate = workingHrsArray[count].date;

        if(wday == day && wdate == date)
        {
            found = true;
            idx=count;
        }
    }
    if(!found) {
        idx = workingHrsArray.length;
    }
    
    workingHrsArray[idx] = new workingHrs(day,date,time);
    refreshWorkingTable("WorkingTable");
}

function deleteWorkingTime()
{
  var theForm = document.forms[0];
  var chkbxname = 'wkcheckbox';
  var tmpArray = new Array();
  var counter = 0;
  var isConfirm = false;
  
  for (var m=0; m<theForm.elements.length;m++){
  	 var checkboxname = theForm.elements[m].name;
	 if(theForm.elements[m].type=='checkbox' && checkboxname.substring(0,10)=='wkcheckbox'){
	  if(theForm.elements[m].checked) 	
          isConfirm = true;
          
      }
  
  }  
  if(isConfirm == false){
	   alert("Please select a working time to delete!");
	   return false;
  }
  if (isConfirm == true){
  if (confirm("Are you sure you want to delete working time? " )) {
  for(var k=0;k<theForm.elements.length;k++)
  {
    if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name.indexOf(chkbxname) != -1)
    {
      if(!theForm.elements[k].checked)
      {
        var str = theForm.elements[k].name.substring(chkbxname.length,theForm.elements[k].name.length);
        tmpArray[counter]=workingHrsArray[parseInt(str)];
        counter++;
      }
    }
  }
  workingHrsArray = tmpArray;
  refreshWorkingTable("WorkingTable");
  theForm.wchkbxAll.checked = false;
  }
  }
  disableBtn();
}

function deleteWorkingTimeFromId(id)
{
  var tmpArray = new Array();
  var counter = 0;
  for(var k=0;k<workingHrsArray.length;k++)
  {
    if(k != id)
    {
      tmpArray[counter]=workingHrsArray[k];
      counter++;
    }
  }
  workingHrsArray = tmpArray;
  refreshWorkingTable("WorkingTable");
}

function setWorkingTimeDetails(id)
{
    //set the day/date and time
    document.forms[0].wrk_dow.value= workingHrsArray[id].day;
    document.forms[0].wrk_date.value= workingHrsArray[id].date;
    document.forms[0].wrk_time.value= workingHrsArray[id].time;

}

function getMonthStr(monthString) {
    if(monthString == "Jan") {
        return "January";
    }
    if(monthString == "Feb") {
        return "February";
    }
    if(monthString == "Mar") {
        return "March";
    }
    if(monthString == "Apr") {
        return "April";
    }
    if(monthString == "May") {
        return "May";
    }
    if(monthString == "Jun") {
        return "June";
    }
    if(monthString == "Jul") {
        return "July";
    }
    if(monthString == "Aug") {
        return "August";
    }
    if(monthString == "Sep") {
        return "September";
    }
    if(monthString == "Oct") {
        return "Oct";
    }
    if(monthString == "Nov") {
        return "November";
    }
    if(monthString == "Dec") {
        return "December";
    }
    return -1;
}

