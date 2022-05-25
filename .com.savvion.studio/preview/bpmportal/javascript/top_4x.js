var isIE = (navigator.appName == "Microsoft Internet Explorer") ? 1 : 0;
var NOVALUE = '-1';

function setCheckBoxStyleForIE()
{
    var w_Elements = document.getElementsByTagName("input");
    for ( i=0; i < w_Elements.length; ++i)
    {
        if(isIE && (w_Elements.item(i).getAttribute("type") == "checkbox" || w_Elements.item(i).getAttribute("type") == "radio"))
            w_Elements.item(i).className = "ChkBoxNone";
    }
}
window.onload = setCheckBoxStyleForIE;

function goToMigrateInstances()
{
    MM_goToURL('self','../administration/SBMAdminAppServlet?action=migrateInstance');
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location.href=encodeURI('"+args[i+1]+"')");
}

function goToFilterList()
{
    MM_goToURL('self','../myhome/bizsite.filter.do?filterType=1');
}
function goToAdminFilterList()
{
    MM_goToURL('self','../administration/sbmadmin.filter.do?filterType=2');
}
function goToProxyList()
{
    MM_goToURL('self','../myhome/DelegateSettingsServlet?action=bizSiteProxyDetail');
}
function goToTaskList() {
    MM_goToURL('self','../management/tasks_overview.jsp');
}
function goToInstanceList()
{
    MM_goToURL('self','../management/overview.jsp');
}
function goToApplicationList()
{
    MM_goToURL('self','../management/app_overview.jsp');
}
function goToAppLevel()
{
    MM_goToURL('self','../management/instance_search.jsp');
}
function goToTaskLevel()
{
    MM_goToURL('self','../management/user_list.jsp');
}
function goToWsLevel()
{
    MM_goToURL('self','../management/wrkstep_search.jsp');
}
function goToBscView()
{
    MM_goToURL('self','../management/bal_score.jsp');
}
function goToBscDesign()
{
    MM_goToURL('self','../management/bsc_list_wiz.jsp');
}
function goToAimReport()
{
    MM_goToURL('self','../management/reports.jsp');
}
function goStatusReportGenerator()
{
    MM_goToURL('self','../management/report_generator_status.jsp');
}
function goToMyReports()
{
    MM_goToURL('self','../management/report_list.jsp');
}
function goTimeAnalysisReportGenerator()
{
    MM_goToURL('self','../management/report_generator_timeanalysis.jsp');
}
function goWorkloadReportGenerator()
{
    MM_goToURL('self','../management/report_generator_workload.jsp');
}
function goToAimAssigned()
{
    MM_goToURL('self','../management/user_list.jsp');
}
function goToAimAvailable()
{
    MM_goToURL('self','../management/avail_tsk_mod.jsp');
}
function goToSystemStatus()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=systemStatus');
}
function goToSBMConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=SBM');
}
function goToUserAdmin()
{
    MM_goToURL('self','../administration/UserManagementServlet?action=listUser');
}
function goToGroupAdmin()
{
    MM_goToURL('self','../administration/UserManagementServlet?action=listGroup');
}
function goToQueueAdmin()
{
    MM_goToURL('self','../administration/UserManagementServlet?action=listQueue');
}
function goToPermissionAdmin()
{
    MM_goToURL('self','../administration/UserManagementServlet?action=listPermission');
}
function goToDelegateAdmin()
{
    MM_goToURL('self','../administration/UserManagementServlet?action=listDelegate');
}
function goToFavoritesAdmin()
{
    MM_goToURL('self','../favorite/favorites_list.jsp');
}
function goToCalendarAdmin()
{
    MM_goToURL('self','../calendar/bizcal_list.jsp');
}
function goToDashboardAdmin()
{
    MM_goToURL('self','../administration/listdashboard.portal?mode=admin');
}
function goToDashboardHome()
{
    MM_goToURL('self','../myhome/viewdashboard.portal');
}
function goToCalendarHome()
{
    MM_goToURL('self','../calendar/bizcal_info.jsp');
}
function goToBLApps(){
    MM_goToURL('self','../administration/application.jsp');
}
function goToBSApps()
{
    MM_goToURL('self','../administration/SBMAdminAppServlet?action=bizsoloAll');
}
function goToLogViewer()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=viewLog');
}
function goToLogConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=Log');
}
function goToAttributeList()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=attrList');
}
function goToBizSoloConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=BizSolo');
}
function goToPortalConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=Portal');
}
function goToBizLogicConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=BizLogic');
}
function goToBizStoreConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=BizStore');
}
function goToEmailConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=Email');
}
function goToBizPulseConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=BizPulse');
}
function goToArchiverConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=Archive');
}
function goToEventPublisherConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=EventPublisher');
}
function goToAuditEvents()
{
    MM_goToURL('self', '../administration/bizevent.jsp');
}

function goToSupport()
{
    MM_goToURL('self','../general/support.jsp');
}

function goToBRMSPortal(language)
{
    MM_goToURL('self','../../brmsportal/index.jsp?locale=' + language);
}

function goToHelp()
{
    MM_openBrWindow('../../onlinehelp/BPM_Portal/index.html','help','');
}
function goToAbout()
{
    MM_openBrWindow('../general/about.jsp','AboutSBM','width=320,height=282');
}
function goToSupportInfo()
{
    MM_openBrWindow('../general/support.jsp','Support','width=600,height=362');
}
function goToSupportFromLogin()
{
    MM_openBrWindow('general/support.jsp','Support','width=600,height=362');
}
function goToLogout()
{
    clearStateProvider();
    MM_goToURL('self','../logout.jsp');
}
function goToTasks()
{
    MM_goToURL('self','../myhome/bizsite.task');
}
function goToMyCollaborativeTasks()
{
    MM_goToURL('self','../myhome/bizsite.ctask.list');
}
function goToStatus()
{
    MM_goToURL('self','../myhome/bizsite.status.list');
}
function goToApps()
{
    MM_goToURL('self','../myhome/bizsite.app.list');
}
function goToAlerts()
{
    MM_goToURL('self','../myhome/bizsite.alerts.list');
}
function goToAlertPreferences()
{
    MM_goToURL('self','../myhome/alerts_preferences.jsp');
}
function goToModels()
{
    MM_goToURL('self','../myhome/models.jsp?orderBy=MODEL_NAME&asc=true&sorting=true');
}
function goToLinks()
{
    MM_goToURL('self','../myhome/mylinks.jsp');
}
function goToProfile()
{
    MM_goToURL('self','../myhome/bizsite.profile.show');
}
function goToInfopadManager()
{
    MM_goToURL('self','../management/infopad_mgr.jsp');
}
function goToSystem()
{
    MM_goToURL('self','../integration/int_system_status.jsp');
}
function goToMessageList()
{
    MM_goToURL('self','../integration/message_list.jsp');
}
function goToAdapterList()
{
    MM_goToURL('self','../integration/adapter_list.jsp');
}
function goToChannelList()
{
    MM_goToURL('self','../integration/channel_list.jsp');
}
function goToTransformList()
{
    MM_goToURL('self','../integration/transform_list.jsp');
}
function goToConfig()
{
    MM_goToURL('self','../integration/int_config.jsp');
}
function goToViewReports(reportname,ptid,paramcheck)
{
    if (reportname != null) {
        MM_goToURL('self','../management/view_report.jsp?reportName=' + reportname + '&PTID=' + ptid + '&paramcheck=' + paramcheck);
    } else {
        parent.close();
    }
}

var activeMenu = null;
var activeSubMenu = null;
var timer_id = null;
var subtimer_id = null;

function menuOver(MenuID)
{
    // If DOM1 supported and element exists ...
    if((document.getElementById)&&(document.getElementById(MenuID)!=null))
    {
        // If this menu is already open ...
        if(activeMenu==document.getElementById(MenuID))
        {
            // Do not close it
            window.clearTimeout(timer_id);
        }
        // Another might still be open ...
        else
        {
            // If another menu is open ...
            if(activeMenu!=null)
            {
                // Do not wait, shut it now
                window.clearTimeout(timer_id);
                hideNow();
            }
        }
        // This is the new 'live' menu, make it visible
        activeMenu = document.getElementById(MenuID);
        // activeMenu.style.visibility is
        // initially empty in IE5 until
        // it is assigned by these
        // functions, so must check that
        // it's not null before proceeding...
        if(activeMenu && (activeMenu.style)&&(activeMenu.style.visibility!=null))
        {
            activeMenu.style.visibility = 'visible';
        }
    }
}

function getElementPos(el,sProp)
{
    var iPos = 0;
    while (el!=null)
    {
        iPos+=el["offset" + sProp];
        el = el.offsetParent;
    }
    return iPos;
}

function menuOverforACL(MenuID, TabId)
{
    var tab = document.getElementById(TabId);
    var layer = document.getElementById(MenuID);
    layer.style.left = getElementPos(tab,"Left");

    // If DOM1 supported and element exists ...
    if((document.getElementById)&&(document.getElementById(MenuID)!=null))
    {
        // If this menu is already open ...
        if(activeMenu==document.getElementById(MenuID))
        {
            // Do not close it
            window.clearTimeout(timer_id);
        }
        // Another might still be open ...
        else
        {
            // If another menu is open ...
            if(activeMenu!=null)
            {
                // Do not wait, shut it now
                window.clearTimeout(timer_id);
                hideNow();
            }
        }
        // This is the new 'live' menu, make it visible
        activeMenu = document.getElementById(MenuID);
        // activeMenu.style.visibility is
        // initially empty in IE5 until
        // it is assigned by these
        // functions, so must check that
        // it's not null before proceeding...
        if(activeMenu && (activeMenu.style)&&(activeMenu.style.visibility!=null))
        {
            activeMenu.style.visibility = 'visible';
        }
    }
}

function menuOut(MenuID)
{
    // If DOM1 supported and a menu is open ...
    if((document.getElementById)&&(document.getElementById(MenuID)!=null))
    {
        // Get the current live menu
        activeMenu = document.getElementById(MenuID);
        // Prepare to shut it in 450 milliseconds
        timer_id = window.setTimeout('hideNow();',450);
    }
}

function submenuOver(MenuID,pID)
{
    // If DOM1 supported and element exists ...
    menuOver(pID);
    if((document.getElementById)&&(document.getElementById(MenuID)!=null))
    {
        // If this menu is already open ...
        if(activeSubMenu==document.getElementById(MenuID))
        {
            // Do not close it
            window.clearTimeout(subtimer_id);
        }
        // Another might still be open ...
        else
        {
            // If another menu is open ...
            if(activeSubMenu!=null)
            {
                // Do not wait, shut it now
                window.clearTimeout(subtimer_id);
                hideSubMenuNow();
            }
        }
        // This is the new 'live' menu, make it visible
        activeSubMenu = document.getElementById(MenuID);
        // activeSubMenu.style.visibility is
        // initially empty in IE5 until
        // it is assigned by these
        // functions, so must check that
        // it's not null before proceeding...
        if(activeSubMenu && (activeSubMenu.style)&&(activeSubMenu.style.visibility!=null))
        {
            activeSubMenu.style.visibility = 'visible';
        }
    }
}

function submenuOut(MenuID,pID)
{
    menuOut(pID);
    // If DOM1 supported and a menu is open ...
    if((document.getElementById)&&(document.getElementById(MenuID)!=null))
    {
        // Get the current live menu
        activeSubMenu = document.getElementById(MenuID);
        // Prepare to shut it in 450 milliseconds
        subtimer_id = window.setTimeout('hideSubMenuNow();',450);
    }
}

function hideSubMenuNow()
{
    if(activeSubMenu && (activeSubMenu.style)&&(activeSubMenu.style.visibility))
        activeSubMenu.style.visibility = 'hidden';
}

function hideNow()
{
    if(activeMenu && (activeMenu.style)&&(activeMenu.style.visibility))
        activeMenu.style.visibility = 'hidden';
}

function toggleRowSelection(chkbx,chkbxall,tableName, controlName, theForm, chkPos)
{
    var table=document.getElementById(tableName);
    var id = chkPos;
    if(Ext.isEmpty(id)){
        id = parseInt(chkbx.value) + 1;
        if(isNaN(id)){
            id = parseInt(chkbx.id) + 1;
        }
    }
        
    var row = table.rows[id];
    if(row == null) return;
    if(chkbx.checked)
    {
        for(var j=0;j < row.cells.length;j++)
        {
            var cell = row.cells[j];
            var c_name = cell.className;            
            if(c_name.indexOf("Slct") == -1)
            {              
                cell.className = c_name + "Slct";
            }
        }        
    }
    else
    {
        for(var j=0;j < row.cells.length;j++)
        {
            var cell = row.cells[j];
            var origc_name = cell.className;            
            if(origc_name.indexOf("Slct") > -1)
            {               
                var c_name = origc_name.substring(0,origc_name.length-4);
                cell.className = c_name;
            }
        }
    }

    //if all the entries are selected select
    //select all option
    var bFlag = true;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
        {
            if(!theForm.elements[k].checked)
                bFlag = false;
        }
    }
    if(bFlag)
        chkbxall.checked = true;
    else
        chkbxall.checked = false;
}

function onSelectAll(theForm, chkbx,controlName, tableName)
{
    if(chkbx.checked)
    {
        chkbx.title = getLocalizedString('DeSelectAll');
        selectAllEntries(theForm,chkbx,controlName,tableName);
    }
    else
    {
        chkbx.title = getLocalizedString('SelectAll');
        deSelectAllEntries(theForm,chkbx,controlName,tableName);
    }
    return true;
}

function selectAllEntries(theForm,chkbxall,controlName, tableName)
{
    var chkPos = 1;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName)
        {
            theForm.elements[k].checked = true;
            toggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm, chkPos++);
        }
    }
    return true;
}

function deSelectAllEntries(theForm,chkbxall,controlName, tableName)
{
    var chkPos = 1;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
        {
            theForm.elements[k].checked = false;
            toggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm, chkPos++);
        }
    }
    return true;
}

function changeTip(chkbxall)
{
    if(chkbxall.checked)
        chkbxall.title = getLocalizedString('DeSelectAll');
    else
        chkbxall.title = getLocalizedString('SelectAll');
}

function dynamicToggleRowSelection(chkbx,chkbxall,tableName, controlName, theForm,chkpos,chkIndex,controlNameIndex)
{
    var table=document.getElementById(tableName);
    var id = parseInt(chkpos);
    var row = table.rows[id];
    if(row == null) return;
    if(chkbx.checked)
    {
        for(var j=0;j < row.cells.length;j++)
        {
            var cell = row.cells[j];
            var c_name = cell.className;            
            if(c_name.indexOf("Slct") == -1)
            {              
                cell.className = c_name + "Slct";
            }
        }
    }
    else
    {
        for(var j=0;j < row.cells.length;j++)
        {
            var cell = row.cells[j];
            var origc_name = cell.className;            
            if(origc_name.indexOf("Slct") > -1)
            {
                var c_name = origc_name.substring(0,origc_name.length-4);                
                cell.className = c_name;
            }
        }
    }

    //if all the entries are selected select
    //select all option
    var bFlag = true;
    var ind = parseInt(controlNameIndex);
    var cbIndex = 0;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if( ind != -1)
        {
            if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName+cbIndex)
            {
                if(!theForm.elements[k].checked)
                    bFlag = false;
                cbIndex++;
            }
        }
        else
        {
            if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
            {
                if(!theForm.elements[k].checked)
                    bFlag = false;
            }
        }
    }
    if(chkbxall != undefined) {
        if(bFlag)
            chkbxall.checked = true;
        else
            chkbxall.checked = false;
    }
}

function dynamicOnSelectAll(theForm, chkbx,controlName,tableName,chkpos,controlNameIndex)
{
    if(chkbx.checked)
    {
        chkbx.title = getLocalizedString('DeSelectAll');
        dynamicSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,0,controlNameIndex);
    }
    else
    {
        chkbx.title = getLocalizedString('SelectAll');
        dynamicDeSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,0,controlNameIndex);
    }
    return true;
}

function dynamicSelectAllEntries(theForm,chkbxall,controlName, tableName,chkpos,chindex,controlNameIndex)
{
    var ind = parseInt(controlNameIndex);
    var chkindex = parseInt(chindex);
    var chkposition = parseInt(chkpos);
    for(var k=0;k<theForm.elements.length;k++)
    {
        if( ind != -1)
        {
            if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName+ind)
            {
                if(theForm.elements[k].disabled != true) {
					theForm.elements[k].checked = true;
				}
                dynamicToggleSelection(theForm.elements[k],tableName,chkposition);
                ind++;
                chkindex++;
                chkposition++;
            }
        }
        else
        {
            if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName)
            {
                if(theForm.elements[k].disabled != true) {
					theForm.elements[k].checked = true;
				}
                dynamicToggleSelection(theForm.elements[k],tableName,chkposition);
                chkindex++;
                chkposition++;
            }
        }
    }
    return true;
}

function dynamicDeSelectAllEntries(theForm,chkbxall,controlName, tableName,chkpos,chindex,controlNameIndex)
{
    var ind = parseInt(controlNameIndex);
    var chkindex = parseInt(chindex);
    var chkposition = parseInt(chkpos);
    for(var k=0;k<theForm.elements.length;k++)
    {
        if( ind != -1)
        {
            if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName+ind)
            {
                theForm.elements[k].checked = false;
                dynamicToggleSelection(theForm.elements[k],tableName,chkposition);
                ind++;
                chkindex++;
                chkposition++;
            }
        }
        else
        {
            if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName)
            {
                theForm.elements[k].checked = false;
                dynamicToggleSelection(theForm.elements[k],tableName,chkposition);
                chkindex++;
                chkposition++;
            }
        }
    }
    return true;
}

function MM_preloadImages()
{
    var d=document;
    if(d.images)
    {
        if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments;
        for(i=0; i<a.length; i++)
        {
            if (a[i].indexOf("#")!=0)
            {
                d.MM_p[j]=new Image;
                d.MM_p[j++].src=a[i];
            }
        }
    }
}

function changeMenuClass(tableName,rowcnt,cellcnt,setreset)
{
    var mainTable = window.document.getElementById(tableName);
    var row = mainTable.rows[rowcnt];
    var cell = row.cells[cellcnt];
    if(setreset == 1)
        cell.className='SubMenuSlctHoverNonClick';
    else
        cell.className='SubMenu';
    return;
}

function dynamicOnSelectAllWithIndex(theForm, chkbx,controlName,tableName,chkpos,chkindex,controlNameIndex)
{
    if(chkbx.checked)
    {
        chkbx.title = getLocalizedString('DeSelectAll');
        dynamicSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,chkindex,controlNameIndex);
    }
    else
    {
        chkbx.title = getLocalizedString('SelectAll');
        dynamicDeSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,chkindex,controlNameIndex);
    }
    return true;
}
function trimString (str)
{
    str = this != window? this : str;
    return str.replace(/^\s+/g, '').replace(/\s+$/g, '');
}


function checkSpecialChars( fieldValue )
{
    var spArray = new Array("<",">","\"","'","%",";","(",")","&","+","\\",",",":","|");
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < spArray.length;i++)
    {
        if(val.indexOf(spArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function checkSpecialChars4URL( fieldValue )
{
    var spArray = new Array("<",">","\"","'","%",";","(",")","&","+","\\",",","|");
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < spArray.length;i++)
    {
        if(val.indexOf(spArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function checkSpecialChars4Date( fieldValue )
{
    var spArray = new Array("<",">","\"","'","%",";","(",")","&","+","\\","|");
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < spArray.length;i++)
    {
        if(val.indexOf(spArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function isValidName( fieldValue ) {
    var spArray = new Array("<",">","\"","'","%",";","(",")","&","+","\\",",",":","|","!","@","#","$","^","*","-","=","`","~","{","}","[","]",".","?","/");
    var val = fieldValue;
    for(var i=0;i < spArray.length;i++) {
        if(val.indexOf(spArray[i]) != -1) {
            return false;
        }
    }

    var startsWith = val.charAt(0);
    var noExp = new RegExp("[0-9_]");
    var isMatch = noExp.exec(startsWith);
    if (isMatch != null) {
    return false;
    }

    return true;
}


function checkSpecialCharsWoBrackets( fieldValue )
{
    var spArray = new Array("<",">","\"","'","%",";","&","+","\\");
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < spArray.length;i++)
    {
        if(val.indexOf(spArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function checkSpecialCharsInArray(fieldValue, charArray)
{
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < charArray.length;i++)
    {
        if(val.indexOf(charArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function isEmpty(textFieldName) {
    var textField = document.getElementById(textFieldName).value;
    var re = /^\s{1,}$/g; //match any white space including space, tab, form-feed, etc.
    if ((textField.length==0) || (textField==null) || ((textField.search(re)) > -1)) {
        return true;
    } else {
        return false;
    }
}

function encodeHrefUrl(url){
    return document.location=encodeURI(url);
}

/**** BUSINESS EXPERT CODE INTEGRATION START ****/
function goToBEConfig()
{
    MM_goToURL('self','../administration/SBMAdminServlet?action=confPara&confName=BEServer');
}
function goToAnalytics()
{
    MM_goToURL('self','../management/businessmetric_list.jsp');
    }
function goToAnalysis()
{
    MM_goToURL('self','../be/list.jsp');
}
/**** BUSINESS EXPERT CODE INTEGRATION END ****/

/** Table Row slection functions **/
BmSelectAllHandler = function(checkBoxCtrl, tableRowPrefix, checkBoxPrefix, rowCount)
{
    if(checkBoxCtrl.checked)
    {
        checkBoxCtrl.title = getLocalizedString('DeSelectAll');
        BmSelectAllEntries(tableRowPrefix,checkBoxPrefix,rowCount);
    }
    else
    {
        checkBoxCtrl.title = getLocalizedString('SelectAll');
        BmDeSelectAllEntries(tableRowPrefix,checkBoxPrefix,rowCount);
    }
    return true;
}

BmSelectAllEntries = function(tableRowPrefix, checkBoxPrefix, rowCount)
{
    for(var i=0;i <rowCount;i++) {
        var rowid = tableRowPrefix+i;
        var chkElem = Ext.get(checkBoxPrefix+i);
        chkElem.checked = true;
        BmToggleRowSelection(rowid,chkElem);    
    }
    return true;
}

BmDeSelectAllEntries = function(tableRowPrefix, checkBoxPrefix, rowCount)
{
    for(var i=0;i <rowCount;i++) {
        var rowid = tableRowPrefix+i;
        var chkElem = Ext.get(checkBoxPrefix+i);
        chkElem.checked = false;
        BmToggleRowSelection(rowid,chkElem);    
    }
    return true;
}

BmToggleRowSelection = function(rowid,chkbx) {
    var row = Ext.get(rowid);
    if(row == null || row == undefined) return;
    if(chkbx.checked)
    {
        row.addClass('SegTableRowSlct');
    }
    else
    {
        row.removeClass('SegTableRowSlct');
    }    
}

/** End Table row selection functions ****/

function dynamicToggleSelection(chkbx,tableName,chkpos){
    var table=document.getElementById(tableName);
    var id = parseInt(chkpos);
    var row = table.rows[id];
    if(row == null){
      return;
    } 
    if(chkbx.checked){
        for(var j=0;j < row.cells.length;j++){
            var cell = row.cells[j];
            var c_name = cell.className;            
            if(c_name.indexOf("Slct") == -1){              
                cell.className = c_name + "Slct";
            }
        }
    }else{
        for(var j=0;j < row.cells.length;j++){
            var cell = row.cells[j];
            var origc_name = cell.className;            
            if(origc_name.indexOf("Slct") > -1){
                var c_name = origc_name.substring(0,origc_name.length-4);                
                cell.className = c_name;
            }
        }
    }
}

function checkSpecialCharsForCalendar( fieldValue )
{
    var spArray = new Array("<",">","\"","\\");
    var val = fieldValue;
    var retVal = false;
    for(var i=0;i < spArray.length;i++)
    {
        if(val.indexOf(spArray[i]) != -1)
        {
            retVal = true;
            break;
        }
    }
    return retVal;
}

function goToPsvTableView() {
   MM_goToURL('self','../myhome/psv_table_portlet_view.jsp');
}

function goToWidgetAdmin()
{
    MM_goToURL('self','../administration/widgetUtil.portal');
}
function deployAppPack() {
    var appPackPopupWnd = Ext.create('Ext.Window',{	
        title:getLocalizedString('ExportAppPackToPCT'),
        modal:true,        
        bodyStyle: 'padding :10px',
        closable:true,
        resizable:false,
        buttonAlign: 'center',
        labelWidth : 30,        
        items:[{
                  xtype: 'textfield',
                  fieldLabel:getLocalizedString('ExportWidgetURL'),
                  id: 'pctApplnPackUrl',
                  name: 'pctApplnPackUrl', 
                  value: '', 
                  width:340,
                  readOnly: false,
                  labelStyle: 'white-space: nowrap; width:120',
                  emptyText:'<protocol>://<host>:<port>'
                  
              },{
                  xtype: 'textfield',
                  fieldLabel:getLocalizedString('AppPackId'), 
                  name: 'appPackId',
                  id: 'appPackId',
                  value: getSUId(),
                  disabled : true,
                  editable : false,
                  width:250,
                  labelStyle: 'white-space: nowrap; width:120'
        }],
        buttons:[{
            text : getLocalizedString('ok'), 
            id:'okBtn',
            name:'okBtn',
            handler : function() {
                var okCmp = Ext.getCmp('okBtn');
                okCmp.disable();
                var pctServerUrl = Ext.getCmp('pctApplnPackUrl').getValue();
                var appPackId = Ext.getCmp('appPackId').getValue();
                if(isEmptyValue(pctServerUrl)) {
                    alert(getLocalizedString('PCT_URL_Error_Msg'));
                    okCmp.enable();
                    return false;
                } 
                if(!startsWith(pctServerUrl,"http://") && !startsWith(pctServerUrl,"https://")){
                    alert(getLocalizedString('PCT_URL_Invalid_Msg'));
                    okCmp.enable();
                    return false;
                } 
                if(!endsWith(pctServerUrl,"/")){
                    pctServerUrl = pctServerUrl + "/";
                }

                if(isEmptyValue(appPackId)) {
                    alert(getLocalizedString('PCT_AppPackId_Error_Msg'));
                    okCmp.enable();
                    return false;
                } 
                pctServerUrl =  pctServerUrl + "pct.application.pack/";
                Ext.Ajax.request({
                    url: getContextPath() + '/bpmportal/ajaxutil.portal?action=exportAppPack',
                    method : 'post',
                    params : {pctServerUrl : pctServerUrl,  appPackId : appPackId},
                    success: function(response, opts) {
						appPackPopupWnd.destroy();
                        Ext.Msg.show({
                            title : getLocalizedString('success'),
                            msg : getLocalizedString('AppPackExportSuccessMsg'), 
                            width : 300, 
                            height : 100,
                            buttons : Ext.Msg.OK,
                            icon: Ext.MessageBox.INFO
                        });
                    },
                    failure: function(response, opts) {
                        //appPackPopupWnd.destroy();
                        handleAjaxException(response, opts);
                        okCmp.enable();
                    }
                });
            } 
            },
            { 
            text : getLocalizedString('FileUpload_Cancel'), 
            handler : function() {
                appPackPopupWnd.destroy();
            }
        }]
    });
   
    appPackPopupWnd.setSize({width:380,height:140});
    appPackPopupWnd.setPosition(600,300);
    appPackPopupWnd.doLayout();
    appPackPopupWnd.show();
}

function showSubprocessHeatMap(url, args) {
    MM_goToURL('self', url+args);     
}

function createMenuToolBar(notFirstLogin) {
    var profile = {text: getLocalizedString('profile'), iconCls: 'profileIcon', handler: function() { goToProfile(); }};
    var support = {text: getLocalizedString('support'), iconCls: 'supportIcon', handler: function() { goToSupportInfo(); }};
    var help = {text: getLocalizedString('help'), iconCls: 'helpIcon', handler: function() { goToHelp(); }};
    var about = {text: getLocalizedString('about'), iconCls: 'aboutIcon', handler: function() { goToAbout(); }};
    var linkMenu = [];
         
    if(notFirstLogin) {
        linkMenu.push(profile);
        linkMenu.push('-');
    }
    linkMenu.push(support);    
    if(notFirstLogin) {
        linkMenu.push(help);
    }    
    linkMenu.push(about);
    
    var menu = Ext.create("Ext.menu.Menu", {
		id: 'cmdLinksMenu',
		//menuAlign: 'tr-br',
		items: linkMenu
	});

	Ext.create('Ext.toolbar.Toolbar', {
		id: 'dropdowntoolbar',
		baseCls: 'bm-dropdown-toolbar-default',
		renderTo: 'dropdownDiv',
		width:27,
		items: [
			{
				id: 'dropdownBtn',
				width: 18,
				//iconCls: 'bmDropdownArrow',
				pressedCls: 'bm-dropdown-button-pressed',
				arrowCls : 'widearrow',
				menu: menu
			}	
		]
	});
}