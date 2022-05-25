var isIEb = ((navigator.appName == "Microsoft Internet Explorer") || (navigator.userAgent.toLowerCase().indexOf('msie') != -1)) ? true : false;
var isNSb = (navigator.appName == "Netscape") ? true : false;

var availWidth = window.screen.availWidth;
var availHeight = window.screen.availHeight; 
var fixedHeight = 125; //height in pixels of header and footer sizes.
var firefoxHeightAdjustment = 18; //pixels of difference between ie and firefox.
var DISABLE_ENTER_KEY = true;
var ENTER_KEY_CODE = 13; 
var DISABLE_CTRL_KEY = true;
/*  
    creating namespace sbm which will be used for calendars.
    This namespace will be created in pwr.js also. 
    If pwr.js is included no need to create the namespace again. 
*/
if (typeof sbm == 'undefined') {
    var sbm = { };
}

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
      param = checkUrl(docattacherurl);
      param += 'bzsid=';
      param += '&pt='+encodedPtName;
      param += '&pi='+encodedPiName;
      param += '&ds=bizsite_dataslot_'+ slotName;
      param += '&docurl=';

       try {
        if(typeof document.dataslot != 'undefined' && typeof document.dataslot.fiid != 'undefined') {
            var fiid = Ext.getDom('fiid').value;
            param += '&fiid='+encodeURIComponent(fiid);
        }
      } catch(e) {
        console.log(e.message);
      }

      openParentFunction(param);
}

function checkUrl(docattacherurl){  
    if(docattacherurl.indexOf("?") == -1){
        docattacherurl += "?";  
    }else if(docattacherurl.lastIndexOf("&") != (docattacherurl.length-1)){     
        docattacherurl += "&";
    }
    return docattacherurl;
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
   var isSystemDashboard = false;
   var sysDash = theForm.systemDashboard.value;  
   if(sysDash !=null && sysDash != ""){
        isSystemDashboard =true;
   }

   var elems = Form.getInputs(theForm,'radio','defaultDashboard' );   
   if(elems != null && elems.length > 0) {
          for(var i=0; i < elems.length;i++) {
             if(elems[i].value == selectedItem.value) {
                 if(selectedItem.checked) {
                    if(isSystemDashboard && elems[i].value == "-1") {
                        elems[i].disabled = true;
                    } else {
                        elems[i].disabled = false;
                    }
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
                if(isSystemDashboard && elems[i].value == "-1") {
                    elems[i].disabled = true;
                }
                break;
             }
          }

         if(selectOOTB) {
              for(var i=0; i < elems.length;i++) {
                if(isSystemDashboard && (elems[i].value == "-1")) {
                    elems[i].disabled = true;
                    elems[i].checked = false;
                } else if (isSystemDashboard && elems[i].value == sysDash) {
                    elems[i].checked = true;
                    elems[i].disabled = false;
                } else if(!isSystemDashboard && elems[i].value == "-1") {
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

function setSelectTooltip(el) {
    if(el != undefined && el.options.length > 0) {
        el.title = el.options[el.options.selectedIndex].text;
    }
}

var docWnd;
function openParentFunction(urlStr) {
     
    Ext.useShims=true;
    docWnd = new Ext.Window({
        id:'documentExtWindow',
        title: getLocalizedString('manageDocuments'),
        autoScroll: false,
        resizable: false,
        modal: true,
        items : [{
            xtype : "container",
            id: 'docIFrame',
            border: false,
            autoEl : {
                tag : "iframe",
                frameBorder: 0,
                width:580,
                height:340,
                src : urlStr
            }
        }],
        buttons: [ {text: getLocalizedString('BUTTON_CLOSE'), handler: function() { docWnd.close(); }}],
        buttonAlign: 'center'
    });

    docWnd.setSize({width:590,height:400});    
    docWnd.setPosition(10,10);
    docWnd.on('beforeclose',closeDocWindow);
    docWnd.show();
}

function closeDocWindow() {    
    var documentWnd = Ext.getDom('docIFrame');
    if(!Ext.isEmpty(documentWnd)){
        documentWnd.contentWindow.checkDocAttached();
    }
}

function isEmptyValue(fieldVal){
    if(null == fieldVal || "" == Ext.String.trim(fieldVal)){
        return true;
    }else {
        return false;
    }
}

// first, declare the two namespaces if they do not already exist
var Bm;
if (Bm == null || typeof(Bm) != "object") {
    Bm = new Object();
}
if (Bm.TaskList == null || typeof(Bm.TaskList) != "object") {
    Bm.TaskList = new Object();
}

// all the functions in the EC.F namespace will go in this block
Bm.TaskList = {
    Applications : function(nm, lbl) {
        this.name = nm;
        this.label = lbl;
        this.templates = new Array();
        this.add = Bm.TaskList.addTemplates;
        this.get = Bm.TaskList.getTemplate;
    },
    Templates : function(nm, lbl) {
        this.name = nm;
        this.label=lbl;
        this.worksteps = new Array();
        this.add = Bm.TaskList.addWorksteps;
        this.setLabel=Bm.TaskList.setTemplateLabel;
    },
    Worksteps : function (nm,lbl) {
        this.name = nm;
        this.label = lbl;
    },
    setTemplateLabel : function(lbl) {
        this.label = lbl;
    },
    addTemplates : function(template) {
        this.templates[this.templates.length] = template;
    },
    getTemplate : function(templateName) {
        var len = this.templates.length;
        for(var i=0; i< len; i++) {
            var tpl = this.templates[i];
            if(tpl.name == templateName) {
                return tpl;
            }
        }
    },
    addWorksteps : function (workstep) {
        this.worksteps[this.worksteps.length] = workstep;
    }
};

/*
    setUserControl(ctrl); setChoosedUserName(userName) searchUser() functions are already defined
    in validate.js
    These functions are duplicated in utilities.js as validate.js is not included in form jsp
    but utilities.js is included in form jsp

*/
var workingCtrl;

function setUserControl(ctrl) {

    workingCtrl = ctrl;
    return ;
}

function setChoosedUserName(userName) {

    if (workingCtrl) {
    
        workingCtrl.value = userName;
    }
    return ;
}

function searchUser() {
	alert("Action not supported in preview mode");
	return ;
}

function setChosenGroupNames(groupNames,prevGrpNames) {
    if(Ext.String.trim(prevGrpNames).length>0){
        var groupNamesArr = groupNames.split(",");
        for(var i=0; i<groupNamesArr.length; i++){
            //It matches: starting with ,[groupName] ending with , and non-word character
            //Comma is appended to the end and start of the expression to ensure that 1st and last group in the matcher is properly matched with the pattern
            var tempGrpNames = ","+prevGrpNames+",";
            var regrexpr = new RegExp("[,]?"+groupNamesArr[i]+"[,]?[\\W]");
            if(!tempGrpNames.match(regrexpr)){
                prevGrpNames = prevGrpNames+","+groupNamesArr[i];
            }
        }
        return prevGrpNames;
    } else {
        return groupNames;
    }
}

function showErrorMsg(title,errorMsg,handler){
    Ext.Msg.show({
          title:'<div align="center">'+title+'</div>'
         ,msg:'<div style="overflow-x: scroll; height:200; border:0 #000000 solid; padding: 2px">'+errorMsg+'</div>'
         ,height:200         
         ,closable:false
         ,buttons:Ext.Msg.OK
         ,fn:handler,
         icon: Ext.MessageBox.ERROR
      });
}

function showWarnMsg(title, warnMsg, handler){
    Ext.Msg.show({
        title:'<div align="center">'+title+'</div>',
        msg: warnMsg,
        height: 200,
        closable:false,
        buttons:Ext.Msg.OK,
        fn:handler,
        icon: Ext.MessageBox.WARNING
    });
}

function isSessionTimeOut(response){
    if(response != undefined && response != null){
        if(response.status === 900) {
            return true;
        }
    }
    return false;
}

function redirectToLogin(ctxPath){            
    var redirect = ctxPath+"/bpmportal/login.jsp";
    window.location = redirect;
}

function AddZero(num) {
    return ((num >= 0)&&(num < 10))?"0"+num:num+"";
} 

function redirectToLgn(errorMsg,errorTitle,ctxPath){
    var handler = Ext.pass(redirectToLogin,[ctxPath]);
    showErrorMsg(errorTitle,errorMsg,handler);
}

function cb(el, success, response, options) {                             
   if(success == false){      
      var errorTitle = getLocalizedString('errorTitle');
        if(isSessionTimeOut(response)) {
            redirectToLgn(getLocalizedString('sesstimeouterror'),errorTitle,getContextPath());
            return true;
        }
  }
  return false;
}

function handleAjaxException(response, options){
    if(!Ext.isEmpty(response)){
        var timeout = cb(null, false, response, options);        
        if(!timeout && !isAbortedReq(response) && !isEmptyResponse(response)){            
            var resp = Ext.decode(response.responseText);
            var errorMessage = Ext.encode(resp.exceptionLocalizedMessage);   // ErrorView sets  exceptionLocalizedMessage in response
            if(errorMessage){
                errorMsg = getLocalizedString('errorMsgLine1')+' '+errorMessage+' '+getLocalizedString('errorMsgLine2');
            }else{
                errorMsg = getLocalizedString('errorMsgLine1')+' '+getLocalizedString('errorMsgLine2');
            }
            errorTitle = getLocalizedString('errorTitle');
            handler = Ext.emptyFn;    
            showErrorMsg(errorTitle,errorMsg,handler);
        }
    }
}

function handleStoreLoadException(store, type, action, options, response, arg){    
    handleAjaxException(response,options);
}

function isEmptyResponse(response){
    if(!Ext.isEmpty(response)){
        var resp = Ext.decode(response.responseText);
        if(!Ext.isEmpty(resp)){
            if(Ext.isArray(resp)){                
                if(resp.length == 0){
                    return true;
                }
            }
        }else{
            return true;
        }
        return false;
    }
    return true;
}

function isAbortedReq(response){
    if(!Ext.isEmpty(response)){
       return response.status == 0 ? true : false;   
    }
    return false;
}

// For pages where include_menu_static.jspf is not included...e.g BizSolo app
if(DISABLE_ENTER_KEY && (document.onkeypress == undefined || document.onkeypress == null)){   
    if (document.captureEvents){
        document.captureEvents(Event.KEYPRESS);
        document.onkeypress = disableSubmit;
    }
    else{
        document.onkeypress = disableSubmit;
    }
}

function disableSubmit(evt,iframeId) {               
    if(Ext.isIE || Ext.isEmpty(evt)){        
        evt = window.event; 
        if(!evt && iframeId){
            var iframeObj = document.getElementById(iframeId);
            if(Ext.util.Format.lowercase(iframeObj.tagName) != 'iframe'){
                var iframeArray = document.getElementsByName(iframeId + "-iframe-name"); 
                if(!Ext.isEmpty(iframeArray)){
                    iframeObj = iframeArray[0];
                }
            }
            evt = iframeObj.contentWindow.event;
        }       
    }
    var node = null;
    if(evt){
        if(evt.target){
            node = evt.target;
        }else if(!node && evt.srcElement) { 
            node = evt.srcElement;
        }    
        if (evt.keyCode == ENTER_KEY_CODE ) {
            // we need to continue the operation if focus is on the following node types and enter key is pressed.
            if(node.type == 'submit' || node.type == 'button' || node.type == 'reset' || node.type == 'textarea') {
                return true;
            } else {
                return false;
            } 
        }
    }
}

function selectColHeaderChkBox (sm) {
    var hd = Ext.fly(sm.grid.getView().innerHd).child('div.x-grid3-hd-checker');
    hd.addClass('x-grid3-hd-checker-on');
}


function deselectColHeaderChkBox (sm) {
    var hd = Ext.fly(sm.grid.getView().innerHd).child('div.x-grid3-hd-checker');
    hd.removeClass('x-grid3-hd-checker-on');
}

//  function validateServerConfigURL
//  valdating url mentioned in the server-config.xml
function validateServerConfigURL(value,alias){
  if(value == "") { 
       getAjaxLocalizedString('EnterServerURL',[alias]);
     return false;
  }
    if(startsWith(value,"http://") || startsWith(value,"https://") || startsWith(value,"ftp://")) {       
    return true;
  } else {
        getAjaxLocalizedString('Invalid_Server_URL',[value,alias]);
        return false;
    }   
}

function startsWith(value, prefix) {
  if (value == null || prefix == null) {
      return false;
  }
  return value.indexOf(prefix) == 0;
} 

function validateAppInfoURL(value){     
    if(value == null || value.length==0) {
        return true;
    } else if((startsWith(value,"http://") || startsWith(value,"https://") || startsWith(value,"ftp://"))) {
        return true;
    } else {
        getAjaxLocalizedString('Invalid_URL',[value]);
        return false;
    }
}

function isValidPiid(piid){
            var numericExpression = /^[0-9]+$/;
            if(piid && piid.match(numericExpression)){
                return true;
            } else {
                alert('"'+piid+'" '+getLocalizedString('BPM_Portal_InvalidPIID'));
                return false;
            }
        }
 

function registerOnLoad(){
    if (this.addEventListener) {
        this.addEventListener('load', registerPortlet, false);
    }
    else if (this.attachEvent) {
        this.attachEvent('onload', registerPortlet);
    }
}

function showDetail() {
    var detail = document.getElementById("exDetail");
    detail.style.visibility = 'visible';

    var showCtrl = document.getElementById("showDetail");
    showCtrl.style.visibility = 'hidden';

    var hideCtrl = document.getElementById("hideDetail");
    hideCtrl.style.visibility = 'visible';
}

function hideDetail() {
    var detail = document.getElementById("exDetail");
    detail.style.visibility = 'hidden';

    var showCtrl = document.getElementById("showDetail");
    showCtrl.style.visibility = 'visible';

    var hideCtrl = document.getElementById("hideDetail");
    hideCtrl.style.visibility = 'hidden';
}

function toggle_visibility(id) {
   var e = document.getElementById(id);
   if(e.style.display == 'block')
      e.style.display = 'none';
   else
      e.style.display = 'block';
}

function endsWith(value, suffix) {
  if (value == null || suffix == null) {
      return false;
  }
  return value.match(suffix+"$") == suffix;
}

function showHideComponent(name){
        if('piidSearch' == name) {
            Ext.getDom('processInstanceId').disabled = false;
            Ext.getDom('eiid').disabled = true;
            Ext.getDom('eiid').value = '';
            Ext.getDom('ptname').disabled = true;
            Ext.getDom('ptname').value = '';
        } else {
            Ext.getDom('processInstanceId').disabled = true;
            Ext.getDom('processInstanceId').value = '';
            Ext.getDom('eiid').disabled = false;
            Ext.getDom('ptname').disabled = false;
        }
    }
  
function validationFilter(){
    var numericExpression = /^[0-9]+$/;
    var radioButtons = document.getElementsByName("searchType");
    for (i=0; i<radioButtons.length; i++){
        if(radioButtons[i].checked == true && radioButtons[i].value == 'eiidSearch'){
            var eiidVal = Ext.getDom('eiid').value;
            var ptname = Ext.getDom('ptname').value;
            if(isEmptyValue(eiidVal)){
                alert(getLocalizedString('ExternalInsId_Error_Msg'));
                return false;
            } else if(isEmptyValue(ptname)) {
                alert(getLocalizedString('ExternalInsId_PT_Error_Msg'));
                return false;
            } else{
                return true;
            }
        } else {
            var piidVal = Ext.getDom('processInstanceId').value;            
            if(isEmptyValue(piidVal)){
                alert(getLocalizedString('ProcessInsId_Error_Msg'));
                return false;
            } else if(piidVal && !piidVal.match(numericExpression)) {
                alert(getLocalizedString('ProcessInsId_Num_Error_Msg'));
                return false;
            } else {
                return true;
            }
        } 
    }
}

function unescapeHtml(value){
    var result = Ext.util.Format.htmlDecode(value);
    // htmlDecode does not handle single quote 
    result = result.replace(/\&#039;/g,"'");  
    return result;
}

// This function will disable new Browser Tab (ctrl+t) and new Browser Window (ctrl+n)
function disableCtrlKeys(evt) {

    var ctrlKeys =["n","t"];
    
    if(Ext.isIE) {
        evt = window.event;
        keyAsciiCode = evt.keyCode;
    } else {
        keyAsciiCode = evt.which;
    }
  
    if(evt.ctrlKey) {
        for(i=0; i<ctrlKeys.length; i++) {
           if(ctrlKeys[i].toLowerCase() == String.fromCharCode(keyAsciiCode).toLowerCase()) {
              return false;
           }
        }
    }
    return true;
}

function showAlertMsg(title,alertMsg){
    Ext.Msg.alert(title,alertMsg);
}

function userMgmtReset() {
    document.form.reset();
    selectCategory();
    return false;
}


if(typeof String.prototype.trim !== 'function') {
   String.prototype.trim = function() {
      return this.replace(/^\s+|\s+$/g, '');
   }
}

function escapeWhiteSpace() {
    if(Ext.isIE9) {
        var expr = new RegExp('>[ \t\r\n\v\f]*<', 'g');
        document.body.innerHTML = document.body.innerHTML.replace(expr, '><');
    }
}

function doLayoutComponent(componentId) {
    var cmp = Ext.getCmp(componentId);
    if (!Ext.isEmpty(cmp)) {
        cmp.doLayout();
    }
}

// to select a value in dropdown
function selectValueInDropDown(path_of_drop_down,value_to_select)
{
    _select = "false";
    for (i=0;i < path_of_drop_down.length;i++)
    {
        if (path_of_drop_down.options[i].value == value_to_select)
        {
            _select = "true";
            path_of_drop_down.options[i].selected = true;
            break;
        }
    }
    if (_select == "false")
    {
        path_of_drop_down.options[0].selected = true;
    }
}

function getProcessInstanceIdForIPC(tokens,values) {
    var piidVal = "";
    if(!Ext.isEmpty(tokens) && tokens.length == 3 && !Ext.isEmpty(values)) {
        if(tokens[0] == "ProcessInstanceId") {
            piidVal = values[0];
        } else if(tokens[1] == "ProcessInstanceId") {
            piidVal = values[1];
        } else {
            piidVal = values[2];
        }
    }
    return piidVal;
}

function isCheckBoxSelected(myForm,selectedType) {
    var el = Form.getInputs(myForm,'checkbox',selectedType);
    var isChecked = false;
    for(var ix=0;ix < el.length;ix++) {
        if(el[ix].checked) {
            isChecked = true;
            break;
        }
    }
    if(isChecked == false && selectedType=='users') {
        alert (getLocalizedString('Select_User'));
        return false;
    } else if(isChecked == false && selectedType=='groups') {
        alert (getLocalizedString('Select_Group'));
        return false;
    } else {
        return isChecked;
    }
}

function clearStateProvider() {
    if (typeof localStorage != 'undefined' && localStorage != null) {
        localStorage.clear();
    }
    
     if (Ext.isIE7) {
        var i, cookieName, cookies = document.cookie.split(";");
        for (i = 0; i < cookies.length; i++) {
            cookieName = cookies[i].substr(0,cookies[i].indexOf("="));
            cookieName = cookieName.trim();
            // Clearing Ext JS cookies - always prefix with "ext-"
            if(cookieName.indexOf("ext-") == 0) {
                try {
                    Ext.util.Cookies.clear(cookieName);
                } catch(e) {}
            }
        }
    }
}

/* IFrame Methods */

function loadIframeSrc(id,src){
    var iframeObj = Ext.getDom(id);
    if(iframeObj) {
        iframeObj.src = src;        
    } else {
        alert("Invalid Iframe Id passed.");
    }
}

/* Grid related Methods */

/* This methos will return true if column exits in the grid else return false */

function isColumnExist(gridId, columnId) {
    if (Ext.getCmp(gridId).getChildByElement(columnId) != null) {
        return true;
    }
    return false;
}

/* This method will return the index of the column, if the columnId is empty return 0 */

function getColumnIndex(columnId) {
    if (!Ext.isEmpty(columnId)) {
        return Ext.getCmp(columnId).getIndex();
    }
    return 0;
}

/* Grid related Methods Ends Here */

/* This function is used to set focus in the first input text-box in view report parameter page */
function setFocus(){
    var paramTextbox = document.getElementById('param0value');
    if(!Ext.isEmpty(paramTextbox)){
        paramTextbox.focus();
    }
}

function validateURLAttribute(urlAttr){
    var isValidURL = true;
    if(!Ext.isEmpty(urlAttr)){      
        isValidURL = validateAppInfoURL(urlAttr.getValue());
    }
    return isValidURL;
}

function addMenuHeaderParam(anchorObj,event){
    if(!Ext.isEmpty(anchorObj) && !Ext.isEmpty(event)){
        var aHref = anchorObj.href;
        var noBreadAndMenuParam = '&NoBreadcrumbs=true&NoMenus=true';
        if(event.button == 1 || event.which == 1){
            if(event.ctrlKey == true){
                if(aHref.indexOf(noBreadAndMenuParam) == -1){
                    anchorObj.href = aHref + noBreadAndMenuParam;
                }
                if(!Ext.isIE){
                   window.open(anchorObj.href,'_blank');
                    return false;
                }
            }else if(aHref.indexOf(noBreadAndMenuParam) != -1){
                anchorObj.href = aHref.replace(noBreadAndMenuParam, '');
            }
        }
        else if((event.button == 2 || event.which == 3) && aHref.indexOf(noBreadAndMenuParam) == -1){
                anchorObj.href = aHref + noBreadAndMenuParam;
        }
    }
}

function setWindowID(docTitle, windowName, windowId) {
    document.title = docTitle;
    window.name = windowName;
    window.id = windowId;
}

function tabClose(){    
    window.parent.close();
    if(!Ext.isIE && !window.parent.closed){
        alert(getLocalizedString('CloseWindowTabWarn'));        
    }
}

function unCheckSelectAll(chkBox) {
    var selectAllChkBox = document.getElementById(chkBox);
    selectAllChkBox.checked = false;
}

// Show Task column document dataslot pop-up
function showDocuments(folderId,instanceName,taskName,dsLabel) {
    Ext.Ajax.request({
       url: getContextPath()+'/bizsite/ajaxutil.portal',
       success: function(response) {
                    showDocumentsOnSuccess(response,instanceName,taskName,dsLabel);
                },
       failure: handleAjaxException,
       params: {folderId:folderId,action:'showTaskColumnDocuments'}
    });
}

function escapeDoubleQuotes(str) {
    var retStr = "";
    if(str != undefined && str.length > 0) {
        retStr = str.replace(/\\\"/g, "&quot;");
        retStr = retStr.replace(/\"/g, "&quot;");
    }
    return retStr;
}

function documentNameRenderer(value, metadata, record, rowIndex, colIndex, store) {
      var docDesc = escapeDoubleQuotes(record.get("docDesc"));
      var keywords = escapeDoubleQuotes(record.get("keywords"));
      var authors = record.get("authors");
      var mime = record.get("mimetype");
      var toolTip = renderToolTip(docDesc, keywords, authors, mime);
      var hrefVal = '<div id="test"><a href="'+record.get('documentopenurl')+'" target="_blank" data-qtip="'+toolTip+'">';
      return hrefVal + value + '</a></div>';
}

function documentLinkRenderer(value, metadata, record, rowIndex, colIndex, store) {
      var description = escapeDoubleQuotes(record.get("docDesc"));
      if(Ext.isEmpty(description)){
        description = '<center><i>'+getLocalizedString('NoDesc')+'</i></center>';
      }
      var hrefVal = "<a href='"+record.get('documentopenurl')+"' target='_blank' data-qtip='"+description+"'>";
      return hrefVal + value + '</a>';
}

function renderToolTip (docDesc, keywords, authors, mime) {
      return "<table cellSpacing=1 cellPadding=0 border=0><tr><td class='SegFieldRt' style='width:20'>"+getLocalizedString('Doc_Description')+":</td><td class='SegValLft' style='width:300' >"+docDesc+"</td></tr><tr><td class='SegFieldRt'  style='width:20'>"+getLocalizedString('Doc_Keywords')+":</td><td class='SegValLft' style='width:300'>"+keywords+"</td></tr><tr><td class='SegFieldRt' style='width:20'>"+getLocalizedString('Doc_Author')+":</td><td class='SegValLft' style='width:300'>"+authors +"</td></tr><tr><td class='SegFieldRt' style='width:20'>"+getLocalizedString('Doc_MIME')+":</td><td class='SegValLft' style='width:300'>"+mime+"</td></tr></table>";
}
      
function showDocumentsOnSuccess(result,instanceName,taskName,dsLabel) {
    var jsonString = Ext.JSON.decode(result.responseText);
    var docStore = new Ext.data.ArrayStore({
                        fields: [
                            {name:'docName', type:'string'},
                            {name:'docSize', type:'int'},
                            {name:'lastModifiedUser', type:'string'},
                            {name:'modifiedDate', type:'string'},
                            {name:'documentopenurl', type:'string'},
                            {name:'docDesc', type:'string'},
                            {name:'keywords', type:'string'},
                            {name:'authors', type:'string'},
                            {name:'mimetype', type:'string'}
                        ]
                    });
    
    var docListColumnModel = [
          new Ext.grid.RowNumberer({header: getLocalizedString('srno'), width:30})
          , {name:'docName',header: getLocalizedString('DOCUMENT'),dataIndex: 'docName',renderer: documentLinkRenderer, width:180, menuDisabled: true}
          , {name:'docSize', header: getLocalizedString('Document_Size'),dataIndex: 'docSize', align:'right',menuDisabled: true,  hideable: false, width: 70}
          , {name:'lastModifiedUser', header:getLocalizedString('Modified_By'),dataIndex: 'lastModifiedUser', align:'left', menuDisabled: true,  hideable: false, width: 100,hidden:true}
          , {name:'modifiedDate', header: getLocalizedString('Modified_Date'),dataIndex: 'modifiedDate', align: 'center', menuDisabled: true,  hideable: false, width: 130}
      ];
    
    var docListGrid = new Ext.grid.GridPanel({
      store: docStore,
      columns: docListColumnModel,
      viewConfig: {                  
          stripeRows: true,
          emptyText:'<div align="center" style="padding: 13px;"><span class="NoRecFound">'+getLocalizedString("No_Docs")+'</span><div>',
          deferEmptyText: false
      },
      forceFit: true,
      loadMask: true
    });
    
	var comp = new Array();
	if(!Ext.isEmpty(taskName)) {
		comp.push(getTaskNameComp(taskName));
	}
	comp.push(getInstanceNameComp(instanceName));
	
   var headerPanel = Ext.create('Ext.panel.Panel', {
            layout: {
                type: 'vbox',
                align:'left'
            },
            frame: true,
            items: comp
    });
    
    docStore.loadData(jsonString);

    var win;
    if(!win){
        win = Ext.create('Ext.Window',{
                    layout : 'anchor',
                    autoScroll: true,
                    resizable : false,
                    modal:true,
                    title :getLocalizedString('Documents_For')+'&nbsp;'+ dsLabel,
                    items :[
                        {xtype: 'container', items: [headerPanel,docListGrid]}
                    ]
                });
    }
    win.setSize({width:450, height:250});
    win.show();
}

function getLocalizedString(str){
    var localizedStr;
    var msgMap;
    if (typeof i18nJSMsgMap != 'undefined' && i18nJSMsgMap != null) {
        msgMap = i18nJSMsgMap;
    } else {
        //This is applicable for ExtJS 3.x forms.
		// Default forms are using EXT JS 3.x with propsI18n as localized message maps
        msgMap = parent.i18nJSMsgMap;
		if(typeof msgMap == 'undefined' && typeof propsI18n != 'undefined') {
 		     localizedStr = propsI18n[str];
			 if(typeof localizedStr != 'undefined')
			    return localizedStr;
			 else
			    return str

			 }
		
    }
    if (Ext.isEmpty(msgMap)) {
        //If message map is empty or undefined, return key.
        return str;
    } else {
        localizedStr = msgMap.get(str);
        //If key doesn't exist, undefined is returned.
        if(typeof localizedStr != 'undefined'){
            return localizedStr;
        }else{
            return str;
        }
    }
}

function getLocalizedStringWithArgs(str, args){
    var localizedStr = getLocalizedString(str);    
    if(localizedStr != 'undefined' && localizedStr != str){
        if(typeof(args) == 'object' && args.length > 0){            
            localizedStr = Ext.String.formatArgArray(localizedStr, args);
        }                    
        return localizedStr;
    }else{
        return str;
    }    
}

//Duplicated method for BusinessExpert localized message retrival
function getLocalizedJSMessage(str){
    var localizedStr;
    var msgMap;
    if (typeof i18nBEJSMsgMap != 'undefined' && i18nBEJSMsgMap != null) {
        msgMap = i18nBEJSMsgMap;
    } else {
        //This is applicable for ExtJS 3.x forms.
        msgMap = parent.i18nBEJSMsgMap;
    }
    if (Ext.isEmpty(msgMap)) {
        //If message map is empty or undefined, return key.
        return str;
    } else {
        localizedStr = msgMap.get(str);
        //If key doesn't exist, undefined is returned.
        if(localizedStr != 'undefined'){
            return localizedStr;
        }else{
            return str;
        }
    }
}
//Duplicated method for BusinessExpert localized message retrival
function getLocalizedJSMessageWithArgs(str, args){   
   var localizedStr = getLocalizedJSMessage(str);  
    if(localizedStr != 'undefined' && localizedStr != str){
        if(typeof(args) == 'object' && args.length > 0){
            localizedStr = Ext.String.formatArgArray(localizedStr, args);
        }                    
        return localizedStr;
    }else{
        return str;
    }
}

function getAjaxLocalizedString(key,args){
    Ext.Ajax.request({
    url: getContextPath() + '/bpmportal/administration/ajaxutil.portal',
    params:{action:'getLocalizedString',key:key, args:args},
    method:'post',
    success:function(result, request){
        var resp = Ext.decode(result.responseText);
        var validLocalMsg = resp.localizedmsg;  
        alert(validLocalMsg);                           
    },
    failure:function(result,request) {
        handleAjaxException(result, request);
    }   
    });
}

function alert(message) {
    // This method is executed by EXT JS 3.x as well as EXT JS 4.x    
	/*Ext.MessageBox = Ext.create('Ext.window.MessageBox', {
        buttonText: {
            ok     : getLocalizedString('ok'),
            yes    : getLocalizedString('Yes'),
            no     : getLocalizedString('No'),
            cancel : getLocalizedString('Cancel')
        }
    });*/
    Ext.MessageBox.alert(getLocalizedString('Alert'), message);
}


var NumericEnum = {
  types: new Array("DECIMAL", "DOUBLE", "LONG"),
  isNumericType: function (type) {
      type = Ext.util.Format.uppercase(type);
      var isNumeric = false;
      for(var i=0; i<this.types.length; i++) {
          if(this.types[i] === type) {
              isNumeric = true;
              break;
          }
      }      
      return isNumeric;
  },
  getTypes: function() {
      return this.types;
  },
  isDecimalType: function(type) {
      type = Ext.util.Format.uppercase(type);
      if (this.types[0] === type) {
          return true;
      } else {
          return false;
      }
  },
  isDoubleType: function(type) {
      type = Ext.util.Format.uppercase(type);
      if (this.types[1] === type) {
          return true;
      } else {
          return false;
      }
  },
  isLongType: function(type) {
      type = Ext.util.Format.uppercase(type);
      if (this.types[2] === type) {
          return true;
      } else {
          return false;
      }
  }
  
};

//To append Task Id in the given URL
function appendTaskId(url) {
    var formName = document.forms[0].name;
    var taskId = null;
    if(formName == Bm.Constants.AutoPresentation_FormName) { 
        //Get task Id from Auto presentation        
        taskId = document.getElementsByName(Bm.Constants.AutoPresentation_FormName)[0].bizsite_taskId.value;
    } else if(formName == Bm.Constants.FormPresentation_FormName) { 
        //Get workitem Id from Form presentation
        taskId = document.getElementsByName(Bm.Constants.FormPresentation_FormName)[0]._WorkitemId.value;
    }
    if(!isEmptyValue(taskId)) {
        if (url.indexOf('?') > 0) {
            url += '&';
         } else {
            url += '?';
         }
        url += 'bizsite_taskId='+taskId;
    }
  return url;
}
function createDecimalField(params) {

    if(Ext.isEmpty(params['isExtOnReadyAvailable']) || params['isExtOnReadyAvailable'] == "false") {
        Ext.onReady(function(){
            createDecimalCmp();
        });
    } else {
        createDecimalCmp();
    }
    function createDecimalCmp() {   
        var isInputParamsValid = Bm.ux.datafield.Decimal.validateInputParams(params);
        if(isInputParamsValid) {
            Ext.create('Bm.ux.datafield.Decimal', {
                fieldName: params['fieldName'],
                precision: params['precision'],
                scale: params['scale'],
                mandatory: params['isMandatory'],
                value: params['value'],
                decimalSeparator: params['separator'],
                messageMap: params['messageMap'],
                spinnerStep: params['spinnerStep'],
                hasSpinner: params['hasSpinner'],
                isReadonly: params['isReadonly'],
                fieldLabel: params['fieldLabel'],
                valueAlign: params['valueAlign']
            });
        } else {
            Ext.getDom('decimalField_' + params['fieldName']).innerHTML = getLocalizedString('invalidDecimalFieldParams');
        }
    }
}

/* Functions used in administration -> System status & System summary Page*/
function checkSubmit() {
    var checkBox = document.getElementsByName('serverName');
    var isChecked = false;
    if (checkBox.checked) {
        isChecked = true;
    } else {
        for (var ix = 0; ix < checkBox.length; ix++) {
            if (checkBox[ix].checked) {
                isChecked = true;
                break;
            }
        }
    }

    if (!isChecked) {
        alert(getLocalizedString("ServerSelectAlert"));
    }
    return isChecked;
}

function refreshServer(openType, URL) {
    if (checkSubmit()) {
        MM_goToURL(openType,URL);
        return document.MM_returnValue;
    }   
}

function getHeaderTextFromDataindex(columnModel, dataIndex) {
    var headerText = dataIndex;
    for (j=0; j< columnModel.length; j++) {
        if (dataIndex == columnModel[j].dataIndex) {
            headerText = columnModel[j].text;
        }
    }
    return headerText;
}

//This method used in the following pages(user_add.jsp,user_det.jsp,group_add.jsp and group_det.jsp)
function updateSelectedCategory() {
    unCheckSelectAll('allPer');
    var table = document.getElementById('segTable');
    var checkbox;
    var i = table.rows.length;
    var row;
    if (numofrows < table.rows.length) numofrows = table.rows.length -1;
    for (; i > 1; i--){
        checkbox = document.getElementById('perIndex'+(i-3));
        row = table.rows[i-1];
        table.tBodies[0].removeChild(row);
        numofrows--;
    }
    var newRow;
    var newCell;
    var newChild;
    var newChild1;
    var cssClass;
    var cntr=0;
    var cntr1=0;
    var index = document.form.category.selectedIndex;
    var cat = document.form.category.options[index].text;
    // since at the time of loading default category is 'All' which is not really a category in perm list, it has to be taken care separately
    // by using non-localized value 'All' for comparison
    var isCategoryAll = document.form.category.options[index].value;
    for(var i=0;i<permArray.length;i++){
      var tmpVal = permArray[i];
      var tmpVal1 = tmpVal.split("|");
      var category = tmpVal1[0];
      var name = tmpVal1[1];
      var desc = tmpVal1[2];
      var permFlag=tmpVal1[3];
      if(cat == category){
        newrow = document.createElement('TR');
        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((cntr1%2)==1)?"SegChkBoxAltSlct" : "SegChkBoxSlct";
        }else{
            cssclass = ((cntr1%2)==1)?"SegChkBoxAlt" : "SegChkBox";
        }
        if(Ext.isIE6 || Ext.isIE7){
          if(permFlag == "true"){
            newchild1 = document.createElement("<input type=\"checkbox\" name=\"perIndex"+cntr+"\" id=\"perIndex"+cntr+"\" value="+cntr+" checked onclick=\"dynamicToggleRowSelection(this,document.form.allPer,'segTable','perIndex',document.form," +(cntr+1)+','+(cntr)+',0);>');
          }else{
            newchild1 = document.createElement("<input type=\"checkbox\" name=\"perIndex"+cntr+"\" id=\"perIndex"+cntr+"\" value="+cntr+" onclick=\"dynamicToggleRowSelection(this,document.form.allPer,'segTable','perIndex',document.form," +(cntr+1)+','+(cntr)+',0);>');
          }
        } else {
          newchild1 = document.createElement('INPUT');
          newchild1.setAttribute('type','checkbox');
          newchild1.setAttribute('name','perIndex' + cntr);
          newchild1.setAttribute('id','perIndex' + cntr);
          newchild1.setAttribute('onclick', 'dynamicToggleRowSelection(this,document.form.allPer,\'segTable\',\'perIndex\',document.form,'+(cntr+1)+','+(cntr)+',0);');
          if(permFlag == "true"){
            newchild1.checked = true;
          } else {
            newchild1.checked = false;
          }
        }
        newchild1.className = 'InptChkbox';
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((cntr1%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((cntr1%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+ (cntr+1));
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((cntr1%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((cntr1%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+category);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((cntr1%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((cntr1%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+name);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((cntr1%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((cntr1%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+desc);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);
        table.tBodies[0].appendChild(newrow);
        numofrows++;
        cntr1++;
        cntr++;
        //setCheckBoxStyleForIE();
      } else if(isCategoryAll == "All"){
        newrow = document.createElement('TR');
        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((i%2)==1)?"SegChkBoxAltSlct" : "SegChkBoxSlct";
        }else{
            cssclass = ((i%2)==1)?"SegChkBoxAlt" : "SegChkBox";
        }
        if(Ext.isIE6 || Ext.isIE7){
          if(permFlag == "true"){
            newchild1 = document.createElement("<input type=\"checkbox\" name=\"perIndex" + i + "\" id=\"perIndex" + i + "\" value="+i+" checked onclick=\"dynamicToggleRowSelection(this,document.form.allPer,'segTable','perIndex',document.form," + (i+1) +"," + (i) + ",0);>");
          } else {
            newchild1 = document.createElement("<input type=\"checkbox\" name=\"perIndex" + i + "\" id=\"perIndex" + i + "\" value="+i+" onclick=\"dynamicToggleRowSelection(this,document.form.allPer,'segTable','perIndex',document.form," + (i+1) +"," + (i) + ",0);>");
          }
        } else{
          newchild1 = document.createElement('INPUT');
          newchild1.setAttribute('type','checkbox');
          newchild1.setAttribute('name','perIndex' + i);
          newchild1.setAttribute('id','perIndex' + i);
          newchild1.setAttribute('onclick', 'dynamicToggleRowSelection(this,document.form.allPer,\'segTable\',\'perIndex\',document.form,'+ (i+1) + ',' + (i) + ',0);');
          if(permFlag == "true"){
            newchild1.checked = true;
          } else {
            newchild1.checked = false;
          }
        }
        newchild1.className = 'InptChkbox';
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((i%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((i%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+ (i+1));
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((i%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((i%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+category);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((i%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((i%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+name);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);

        newcell = document.createElement('TD');
        if(permFlag == "true"){
            cssclass = ((i%2)==1)?"ValLftAltSlct" : "ValLftSlct";
        }else{
            cssclass = ((i%2)==1)?"ValLftAlt" : "ValLft";
        }
        newchild1 = document.createTextNode(''+desc);
        newcell.appendChild(newchild1);
        newcell.className = cssclass;
        newrow.appendChild(newcell);
        table.tBodies[0].appendChild(newrow);
        numofrows++;
        //setCheckBoxStyleForIE();
      }
    } 
    //checkPermissionAndLink();
}

function isvalidNumber(number, separater) {
    var regex = new RegExp("^[\\d]+([\\"+separater+"][\\d]+)?$");
    if(!regex.test(number)) {
        return false;
    }
    return true;
}

// To check special characters for user first name and last name
function checkSpecialCharsForName(nameValue) {
	var regex = new RegExp("[<>|:']");
	if(regex.test(nameValue)) {
		return true;
	}
	return false;
}

function validateNameField(nameLabel, nameValue) {
	if (Ext.isEmpty(nameValue)) {
		alert(Ext.String.format(getLocalizedString('EmptyFieldMsg'), nameLabel));
		return false;
	}
	if(!checkSpecialCharsForName(nameValue)) {
		alert(Ext.String.format(getLocalizedString('AllowedSpecialCharacters'), nameLabel));
		return false;
	}
	return true;
}

function getNextPageUrl(result, defaultUrl) {
	var nextUrl = defaultUrl;
    if(!Ext.isEmpty(result)) {
        var jsonStr = Ext.JSON.decode(result.responseText);
        if(!Ext.isEmpty(jsonStr) && !Ext.isEmpty(jsonStr.nextUrl)) {
            nextUrl = jsonStr.nextUrl; 
        }
    }
    //Formatting the JSP page URL as relative URL
    nextUrl = nextUrl.replace("/bpmportal","..");
    return nextUrl;
}

//Validate a set of comma separated email addresses
function checkAddressFormat(emails, label) {
    var emailarr = emails.split(",");
    for(var i=0;i<emailarr.length;i++) {
        var inStr = emailarr[i];
        if (!Ext.isEmpty(inStr) && (!validateEmail(inStr, label))) {
            return false;
        }
    }
    return true;
}

function getInstanceNameComp(piName) {
		var instanceNameComp = {
			xtype: 'label',
			forId: 'instanceName',
			html: '<b>'+ getLocalizedString('Instance_Name')+'</b>:&nbsp;' + piName,
			padding:'5 5 5 5',
			style:'font-weight:normal;'
		};
		return instanceNameComp;
}

function getTaskNameComp(taskName) {
		var taskNameComp = {
			xtype: 'label',
			forId: 'taskName',
			html: '<b>'+ getLocalizedString('Task_Name')+'</b>:&nbsp;' + taskName,
			padding:'5 5 5 5',
			style:'font-weight:normal;'
		};
		return taskNameComp;
}

function getTemplateNameComp(ptName) {
		var templateNameComp = {
			xtype: 'label',
			forId: 'applicationName',
			html: '<b>'+ getLocalizedString('ApplicationName')+'</b>:&nbsp;' + ptName,
			padding:'5 5 5 5',
			style:'font-weight:normal;'
		};
		return templateNameComp;
}

function showXmlPopup(xmlid, dsName, ptName, piName, taskName) {
	var xmlDocViewUrl = getContextPath()+'/bizsite/ajaxutil.portal';
	xmlDocViewUrl = xmlDocViewUrl+'?xmlid='+xmlid+'&action=showXmlDocument';
	getXmlContent(xmlDocViewUrl, dsName, ptName, piName, taskName);
}


function getXmlContent(xmlDocUrl, dsName, ptName, piName, taskName) {
	Ext.Ajax.request({
       url: xmlDocUrl,
	   method : 'GET',
       success: function(response, request) {
					var res = Ext.JSON.decode(response.responseText);
					var xmlName = res.docName;
					var xmlcontent = res.docContent;
                    openXmlContentViewer(xmlcontent,xmlName, dsName, ptName, piName, taskName);
                },
       failure: handleAjaxException
    });
}

function openXmlContentViewer(xmlcontent,docName, dsName, ptName, piName, taskName) {
	var comp = new Array();
	if(!Ext.isEmpty(taskName) && taskName != "null") {
		comp.push(getTaskNameComp(taskName));
	}
	
	if(Ext.isEmpty(piName) || piName == "null") {
		if(!Ext.isEmpty(ptName) && ptName != "null") {
			comp.push(getTemplateNameComp(ptName));
		}
	} else {
		comp.push(getInstanceNameComp(piName));
	}			
	
	var xmlContentCmp = {
					xtype     : 'textareafield',
					name      : 'xmldoc',
					value:xmlcontent,
					readOnly: true
				};
	var xmlContentContainer = {
		xtype: 'container',
		anchor: '100% 93%',
		layout: 'fit',
		items: [xmlContentCmp]
	};
	
	var labelItemsContainer = {
		xtype: 'container',
		layout: 'vbox',
		anchor:'30',
		items: comp		
	};
	var win;
	if(!win) {
		win = Ext.create('Ext.Window',{
				layout : 'anchor',
				id:'xmlWindow',
				autoScroll: false,
				modal:true,
				title :docName+' ('+dsName+')',
				items: [labelItemsContainer, xmlContentContainer]
		});
	}
	win.setSize({width:550, height:400});
	win.show();
}