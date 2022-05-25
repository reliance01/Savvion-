var isIEb = ((navigator.appName == "Microsoft Internet Explorer") || (navigator.userAgent.toLowerCase().indexOf('msie') != -1)) ? true : false;
var isNSb = (navigator.appName == "Netscape") ? true : false;

var availWidth = window.screen.availWidth;
var availHeight = window.screen.availHeight;
var fixedHeight = 125; //height in pixels of header and footer sizes.
var firefoxHeightAdjustment = 18; //pixels of difference between ie and firefox.

function findSize()
{
	if( typeof( window.innerWidth ) == 'number' ) {
		//Non-IE
		availWidth = window.innerWidth;
		availHeight = window.innerHeight;
	} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		//IE 6+ in 'standards compliant mode'
		availWidth = document.documentElement.clientWidth;
		availHeight = document.documentElement.clientHeight;
	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		//IE 4 compatible
		availWidth = document.body.clientWidth;
		availHeight = document.body.clientHeight;
	}
}

function isMicrosoft()
{
	return isIEb;
}

function isNetscape()
{
	return isNSb;
}

function getElementPos(el,sProp)
{
	var iPos = 0;
	while (el!=null) {
	iPos+=el["offset" + sProp];
	el = el.offsetParent;
	}
	return iPos;
}

function positionDiv(a_tdId, a_divId)
{
  var divElem = document.getElementById(a_divId);
  var tdElem = document.getElementById(a_tdId);
  
  findSize();
  var heightAdjustment = fixedHeight + ((arguments[2] != null)?arguments[2]:0);
  if (isNetscape())
	  heightAdjustment += firefoxHeightAdjustment;
  tdElem.height = availHeight - heightAdjustment;
  
  positionDivNoVisible(a_tdId, a_divId);
  divElem.style.visibility="visible";
}

function adjustIframeForResolution(frameId)
{
  var frmElem1 = document.getElementById(frameId);
  findSize();
  var heightAdjustment = fixedHeight + ((arguments[1] != null)?arguments[1]:0);
  if (isNetscape())
	  heightAdjustment += firefoxHeightAdjustment;
  frmElem1.height = availHeight - heightAdjustment;
}

function positionDivNoVisible(a_tdId, a_divId)
{
  var divElem = document.getElementById(a_divId);
  var tdElem = document.getElementById(a_tdId);

  findSize();
  divElem.style.position = "absolute";
  divElem.style.overflow = "auto";
  divElem.style.zIndex= "1";

  divElem.style.left = getElementPos(tdElem,"Left");
  divElem.style.top = getElementPos(tdElem,"Top");

  if(isMicrosoft())
  {
  	divElem.style.width=tdElem.offsetWidth;
  	divElem.style.height=tdElem.height;
  }
  else
  {
  	divElem.style.width=tdElem.offsetWidth-firefoxHeightAdjustment;
  	divElem.style.height=tdElem.height-firefoxHeightAdjustment;
  }
  divElem.style.display="block";
}

function adjustDivForResolution(tdId1,divId1,tdId2,divId2)
{
	findSize();
  var tdElem1 = document.getElementById(tdId1);
  var tdElem2 = document.getElementById(tdId2);

  var divElem1 = document.getElementById(divId1);
  var divElem2 = document.getElementById(divId2);

  tdElem1.height = parseInt(tdElem1.height)/2;
  tdElem2.height = parseInt(tdElem2.height)/2;
  
  divElem1.style.position = "absolute";
  divElem1.style.overflow = "auto";

  divElem1.style.left = getElementPos(tdElem1,"Left");
  divElem1.style.top = getElementPos(tdElem1,"Top");
  
  divElem2.style.position = "absolute";
  divElem2.style.overflow = "auto";

  divElem2.style.left = getElementPos(tdElem2,"Left");
  divElem2.style.top = getElementPos(tdElem2,"Top");

  if(isMicrosoft())
  {
  	divElem1.style.width=tdElem1.offsetWidth;
  	divElem1.style.height=tdElem1.height;
  	divElem2.style.width=tdElem2.offsetWidth;
  	divElem2.style.height=tdElem2.height;
  }
  else
  {
  	divElem1.style.width=tdElem1.offsetWidth-firefoxHeightAdjustment;
  	divElem1.style.height=tdElem1.height-firefoxHeightAdjustment;
  	divElem2.style.width=tdElem2.offsetWidth-firefoxHeightAdjustment;
	divElem2.style.height=tdElem2.height-firefoxHeightAdjustment;
  }
}

function undoAdjustmentForResolution(tdId1,divId1)
{
	findSize();
  var tdElem1 = document.getElementById(tdId1);
  var divElem1 = document.getElementById(divId1);

  tdElem1.height = parseInt(tdElem1.height)/2;
  
  divElem1.style.position = "absolute";
  divElem1.style.overflow = "auto";

  divElem1.style.left = getElementPos(tdElem1,"Left");
  divElem1.style.top = getElementPos(tdElem1,"Top");
  
  if(isMicrosoft())
  {
  	divElem1.style.width=tdElem1.offsetWidth;
  	divElem1.style.height=tdElem1.height;
  }
  else
  {
  	divElem1.style.width=tdElem1.offsetWidth-firefoxHeightAdjustment;
  	divElem1.style.height=tdElem1.height-firefoxHeightAdjustment;
  }
}

function updateDateFields(theField, fromField, toField)
{
	var daterange=theField.options[theField.selectedIndex].value;
	var date=new Date();
	if(daterange=="-1" || daterange=="All")
	{
		fromField.value="mm-dd-yyyy";
		toField.value="mm-dd-yyyy";
	}
	if(daterange=="<%=BizManageBean.TODAY%>")
	{
		CalculateDate(0,fromField, toField);
	}
	if(daterange=="<%=BizManageBean.YESTERDAY%>")
	{
		CalculateDate(1,fromField, toField);
	}
	if(daterange=="<%=BizManageBean.THISWEEK%>")
	{
		var dayofweek=date.getDay();
		CalculateDate(dayofweek-1, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.LASTWEEK%>")
	{
		var dayofweek=date.getDay();
		CalculateDate(dayofweek-1+7, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.THISMONTH%>")
	{
		CalculateDateForMonths(theField, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.LASTMONTH%>")
	{
		CalculateDateForMonths(theField, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.THISQUARTER%>")
	{
		CalculateQuarter(daterange, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.LASTQUARTER%>")
	{
		CalculateQuarter(daterange, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.THISYEAR%>")
	{
		CalculateThisYear(daterange, fromField, toField);
	}
	if(daterange=="<%=BizManageBean.LASTYEAR%>")
	{
		CalculateThisYear(daterange,fromField, toField);
	}
}

function CalculateQuarter(daterange,fromField, toField)
{
	var date=new Date();
	var sday=date.getDate();
	var smonth=date.getMonth()+1;
	var syear=date.getYear();
	var Str=new String("");
	var quarter=smonth/4;
	if(daterange=="<%=BizManageBean.THISQUARTER%>")
	{
		if((quarter<1) &(quarter>=0))
		{
			CalculateDateFieldsForQuarter(0,daterange,fromField, toField);
		}
		else if((quarter>=1) &(quarter<2))
		{
			CalculateDateFieldsForQuarter(1,daterange,fromField, toField);
		}
		else if((quarter<=2) &(quarter<3))
		{
			CalculateDateFieldsForQuarter(2,daterange,fromField, toField);
		}
		else if((quarter<=3) &(quarter<4))
		{
			CalculateDateFieldsForQuarter(3,daterange,fromField, toField);
		}
		else if(quarter==4)
		{
			CalculateDateFieldsForQuarter(3,daterange,fromField, toField);
		}
	}
	else if(daterange=="<%=BizManageBean.LASTQUARTER%>")
	{
      if((quarter<1) &(quarter>=0))
      {
		CalculateDateFieldsForQuarter(0,daterange,fromField, toField);
      }
      else if((quarter>=1) &(quarter<2))
      {
		CalculateDateFieldsForQuarter(0,daterange,fromField, toField);
      }
      else if((quarter<=2) &(quarter<3))
      {
		CalculateDateFieldsForQuarter(1,daterange,fromField, toField);
      }
      else if((quarter<=3) &(quarter<4))
      {
		CalculateDateFieldsForQuarter(2,daterange,fromField, toField);
      }
      else if(quarter==4)
      {
		CalculateDateFieldsForQuarter(3,daterange,fromField, toField);
      }
   }
}

function CalculateDateFieldsForQuarter(quarter,daterange,fromField,toField)
{
	var date=new Date();
	var sday=date.getDate();
	var smonth=date.getMonth()+1;
	var syear=date.getYear();
	var fyear=date.getYear();
	var Str=new String("");
	if(quarter=="0")
	{
		fromField.value=ToDate(syear,1,1);
		if(daterange=="<%=BizManageBean.THISQUARTER%>")
		{
			toField.value=ToDate(syear,smonth,sday);
		}
		else if(daterange=="<%=BizManageBean.LASTQUARTER%>")
		{
			toField.value=ToDate(syear,3,31);
			toField.disabled=false;
		}
	}
	else if(quarter=="1")
	{
		fromField.value=ToDate(syear,4,1);
		if(daterange=="<%=BizManageBean.THISQUARTER%>")
		{
			toField.value=ToDate(syear,smonth,sday);
		}
		else if(daterange=="<%=BizManageBean.LASTQUARTER%>")
		{
			toField.value=ToDate(syear,6,30);
			toField.disabled=false;
		}
	}
	else if(quarter=="2")
	{
		fromField.value=ToDate(syear,7,1);
		if(daterange=="<%=BizManageBean.THISQUARTER%>")
		{
			toField.value=ToDate(syear,smonth,sday);
		}
		else if(daterange=="<%=BizManageBean.LASTQUARTER%>")
		{
			toField.value=ToDate(syear,9,30);
			toField.disabled=false;
		}
	}
	else if(lmonth=="3")
	{
		fromField.value=ToDate(syear,10,1);
		if(daterange=="<%=BizManageBean.THISQUARTER%>")
		{
			toField.value=ToDate(syear,smonth,sday);
		}else if(daterange=="<%=BizManageBean.LASTQUARTER%>")
		{
			toField.value=ToDate(syear,9,30);
			toField.disabled=false;
		}
	}
	return Str;
 }

function CalculateThisYear(daterange, fromField, toField)
{
	var date=new Date();

	var sday=date.getDate();
	var smonth=date.getMonth()+1;
	var  syear=date.getYear();
	var Str=new String("");
	Str=new String("1/1/2003");
	if(daterange=="<%=BizManageBean.THISYEAR%>")
	{
		fromField.value=Str;
		toField.value=ToDate(syear,smonth,sday);
   		toField.disabled=true;
   }
   else if(daterange=="<%=BizManageBean.LASTYEAR%>")
   {
   		fromField.value=ToDate(syear-1,1,1);
		toField.value=ToDate(syear-1,12,31);
   }
}

function ToDate(year,month,day)
{
	var todate=month+"/"+day+"/"+year;
	return todate;
}

function CalculateDate(mdays,fromField, toField)
{
	var minusdays=7;
	var date=new Date();
	var sday=date.getDate();
	var smonth=date.getMonth()+1;
	var syear=date.getYear();
	var Str=new String("");
	var duedatedays=sday-mdays;
	if((smonth==1)&&(duedatedays<0))
	{
    	Str=ToDate(syear-1,12,duedatedays+30);
	}
	else if((smonth==2)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==3)&&(duedatedays<=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+28);
	}
	else if((smonth==4)&&(duedatedays<=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==5)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+30);
	}
	else if((smonth==6)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==7)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+30);
	}
	else if((smonth==8)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==9)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==10)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays-30);
	}
	else if((smonth==11)&&(duedatedays <=0))
	{
		Str=ToDate(syear,smonth-1,duedatedays+31);
	}
	else if((smonth==12)&&(duedatedays <=0))
	{
		Str=ToDate(syear+1,smonth-1,duedatedays+30);
	}
	else
	{
		Str=ToDate(syear,smonth,duedatedays);
    }
	toField.value=ToDate(syear,smonth,sday);
	fromField.value=Str;
}

function CalculateDateForMonths(theField, fromField, toField)
{
	var daterange=theField.options[theField.selectedIndex].value;
	var date=new Date();
	var sday=date.getDate();
	var smonth=date.getMonth()+1;
	var  syear=date.getYear();
	var  fyear=date.getYear();
	var Str=new String("");
	var day;
	var month;
	var year;
	if(daterange=="<%=BizManageBean.THISMONTH%>")
	{
		Str=ToDate(syear,smonth,1);
		toField.value=ToDate(syear,smonth,sday);
	}
	if(daterange=="<%=BizManageBean.LASTMONTH%>")
	{
		Str=ToDate(syear,smonth-1,1);
		toField.value=ToDate(syear,smonth,sday);
	}
	fromField.value=Str;
}

function checkFieldForSpaces(fieldValue)
{
	if(fieldValue != ""){  
		for(var a = 0; a < fieldValue.length; a++){ 
			//look for ' ' char
			if(fieldValue.charAt(a) == " "){ 
				return true;
			}
		}
	}
	return false;
}

function checkFieldForChar(fieldValue,charValue)
{
	if(fieldValue != ""){  
		for(var a = 0; a < fieldValue.length; a++){ 
			//look for char
			if(fieldValue.charAt(a) == charValue){ 
				return true;
			}
		}
	}
	return false;
}

function getCellValue (cellOrId) {
  var cell =
    typeof cellOrId == 'string' ?
      (document.all ? document.all[cellOrId] : document.getElementById
(cellOrId)) :
      cellOrId;
  if (document.all)
    return cell.innerText;
  else {
    cell.normalize();
    return cell.firstChild.nodeValue;
  }
}

function textAreaLimiter(field, maxLength, message)
{
	if (field.value.length > maxLength)
	{
		alert(message+' '+maxLength);
		return false;
	}
	return true;
}

function openDocAttWin( slotName,encodedPtName,encodedPiName,docurl,docattacherurl )
{
      param = docattacherurl+'?';
      param += 'bzsid=';
      param += '&pt='+encodedPtName;
      param += '&pi='+encodedPiName;
      param += '&ds=bizsite_dataslot_'+ slotName;
      param += '&docurl=';
      uploadWnd = window.open( param,
	'uploadWnd','width=410,height=480,scrollbars=yes,resizable=yes,status=0' );
      uploadWnd.focus();
}

function setDashboardCheckbox() {
   var theForm = document.forms[0];
   var elems = Form.getInputs(theForm,'checkbox','dashboards' );   
   if(elems != null && elems.length > 0) {
      for(var i=0; i < elems.length;i++) {
      	 onClickAssignDashboard(theForm,elems[i]);
      }
   }  
}

function onClickAssignDashboard(theForm, selectedItem) {

   var elems = Form.getInputs(theForm,'radio','defaultDashboard' );   
   if(elems != null && elems.length > 0) {
      for(var i=0; i < elems.length;i++) {
         if(elems[i].value == selectedItem.value) {
            if(selectedItem.checked) {
            	elems[i].disabled = false;
            } else {
            	elems[i].checked = false;
            	elems[i].disabled = true;
            }
         }
      }
      
      //If all the elems are not checked check the ootb dashboard
      var selectOOTB = true;
      for(var i=0; i < elems.length;i++) {
         if(elems[i].checked == true) {
         	selectOOTB = false;
         	break;
         }
      }
      if(selectOOTB) {
	 for(var i=0; i < elems.length;i++) {
		if(elems[i].value == "-1") {
			elems[i].disabled = false;
			elems[i].checked = true;
	 	}
	
	 }
      }
      
   }

}

function onClickDefaultDashboard(theForm, defaultSelection) {

   var elems = Form.getInputs(theForm,'radio','defaultDashboard' );   
   if(elems != null && elems.length > 0) {
      for(var i=0; i < elems.length;i++) {
         if(elems[i].value != defaultSelection.value) {
            elems[i].checked = false;
         }
      }
   }
   
}
