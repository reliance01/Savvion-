var isIE = (navigator.appName == "Microsoft Internet Explorer") ? 1 : 0;

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
    MM_goToURL('parent','../administration/mig_instance_list.jsp');
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function goToFilterList()
{
    MM_goToURL('parent','../myhome/bizsite.filter.do?filterType=1');
}
function goToAdminFilterList()
{
    MM_goToURL('parent','../administration/sbmadmin.filter.do?filterType=2');
}
function goToProxyList()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=bizSiteProxyDetail');
}
function goToInstanceList()
{
    MM_goToURL('parent','../management/overview.jsp');
}
function goToApplicationList()
{
    MM_goToURL('parent','../management/app_overview.jsp');
}
function goToAppLevel()
{
    MM_goToURL('parent','../management/instance_search.jsp');
}
function goToTaskLevel()
{
    MM_goToURL('parent','../management/user_list.jsp');
}
function goToWsLevel()
{
    MM_goToURL('parent','../management/wrkstep_search.jsp');
}
function goToBscView()
{
    MM_goToURL('parent','../management/bal_score.jsp');
}
function goToBscDesign()
{
    MM_goToURL('parent','../management/bsc_list_wiz.jsp');
}
function goToAimReport()
{
    MM_goToURL('parent','../management/reports.jsp');
}
function goStatusReportGenerator()
{
    MM_goToURL('parent','../management/report_generator_status.jsp');
}
function goToMyReports()
{
    MM_goToURL('parent','../management/report_list.jsp');
}
function goTimeAnalysisReportGenerator()
{
    MM_goToURL('parent','../management/report_generator_timeanalysis.jsp');
}
function goWorkloadReportGenerator()
{
    MM_goToURL('parent','../management/report_generator_workload.jsp');
}
function goToAimAssigned()
{
    MM_goToURL('parent','../management/user_list.jsp');
}
function goToAimAvailable()
{
    MM_goToURL('parent','../management/avail_tsk_mod.jsp');
}
function goToSystemStatus()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=systemStatus');
}
function goToSBMConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=SBM');
}
function goToUserAdmin()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=listUser');
}
function goToGroupAdmin()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=listGroup');
}
function goToQueueAdmin()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=listQueue');
}
function goToPermissionAdmin()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=listPermission');
}
function goToDelegateAdmin()
{
    MM_goToURL('parent','../administration/UserManagementServlet?action=listDelegate');
}
function goToFavoritesAdmin()
{
    MM_goToURL('parent','../administration/favorites_list.jsp');
}
function goToCalendarAdmin()
{
    MM_goToURL('parent','../calendar/bizcal_list.jsp');
}
function goToDashboardAdmin()
{
    MM_goToURL('parent','../administration/listdashboard.portal?mode=admin');
}
function goToDashboardHome()
{
    MM_goToURL('parent','../myhome/viewdashboard.portal');
}
function goToCalendarHome()
{
    MM_goToURL('parent','../calendar/bizcal_info.jsp');
}
function goToBLApps()
{
    MM_goToURL('parent','../administration/SBMAdminAppServlet?action=bizlogicAll');
}
function goToBSApps()
{
    MM_goToURL('parent','../administration/SBMAdminAppServlet?action=bizsoloAll');
}
function goToLogViewer()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=viewLog');
}
function goToLogConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=Log');
}
function goToAttributeList()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=attrList');
}
function goToBizSoloConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=BizSolo');
}
function goToPortalConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=Portal');
}
function goToBizLogicConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=BizLogic');
}
function goToBizStoreConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=BizStore');
}
function goToEmailConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=Email');
}
function goToBizPulseConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=BizPulse');
}
function goToArchiverConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=Archive');
}
function goToEventPublisherConfig()
{
    MM_goToURL('parent','../administration/SBMAdminServlet?action=confPara&confName=EventPublisher');
}
function goToAuditEvents()
{
    MM_goToURL('parent', '../administration/bizevent.jsp');
}

function goToSupport()
{
    MM_goToURL('parent','../general/support.jsp');
}
function goToHelp(menu)
{
    if (menu == '0')
        MM_openBrWindow('../../onlinehelp/BPM_Portal/bpm_portal.htm#common/home_module.htm','help','');
    else if (menu == '1')
        MM_openBrWindow('../../onlinehelp/BPM_Portal/bpm_portal.htm#common/management_module.htm','help','');
    else if (menu == '2')
        MM_openBrWindow('../../onlinehelp/BPM_Portal/bpm_portal.htm#common/administration_module.htm','help','');
    else if (menu == '3')
        MM_openBrWindow('../../onlinehelp/BPM_Portal/bpm_portal.htm#common/integration_module.htm','help','');
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
    MM_goToURL('parent','../logout.jsp');
}
function goToTasks()
{
    MM_goToURL('parent','../myhome/bizsite.task');
}
function goToMyCollaborativeTasks()
{
    MM_goToURL('parent','../myhome/bizsite.ctask.list');
}
function goToStatus()
{
    MM_goToURL('parent','../myhome/bizsite.status.list');
}
function goToApps()
{
    MM_goToURL('parent','../myhome/bizsite.app.list');
}
function goToAlerts()
{
    MM_goToURL('parent','../myhome/bizsite.alerts.list');
}
function goToAlertPreferences()
{
    MM_goToURL('parent','../myhome/alerts_preferences.jsp');
}
function goToModels()
{
    MM_goToURL('parent','../myhome/models.jsp?orderBy=MODEL_NAME&asc=true&sorting=true');
}
function goToLinks()
{
    MM_goToURL('parent','../myhome/mylinks.jsp');
}
function goToProfile()
{
    MM_goToURL('parent','../myhome/bizsite.profile.show');
}
function goToCreateCollaboration()
{
	MM_goToURL('parent','../myhome/create_collaboration.jsp');
}
function goToInfopadManager()
{
    MM_goToURL('parent','../management/infopad_mgr.jsp');
}
function goToSystem()
{
    MM_goToURL('parent','../integration/int_system_status.jsp');
}
function goToMessageList()
{
    MM_goToURL('parent','../integration/message_list.jsp');
}
function goToAdapterList()
{
    MM_goToURL('parent','../integration/adapter_list.jsp');
}
function goToChannelList()
{
    MM_goToURL('parent','../integration/channel_list.jsp');
}
function goToTransformList()
{
    MM_goToURL('parent','../integration/transform_list.jsp');
}
function goToConfig()
{
    MM_goToURL('parent','../integration/int_config.jsp');
}
function goToViewReports(reportname,ptid,paramcheck)
{
    if (reportname != null) {
        MM_goToURL('parent','../management/view_report.jsp?reportName=' + reportname + '&PTID=' + ptid + '&paramcheck=' + paramcheck);
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

function toggleRowSelection(chkbx,chkbxall,tableName, controlName, theForm)
{
    var table=document.getElementById(tableName);
    var id = parseInt(chkbx.value) + 1;
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
                if((id-1)%2 == 1)
                {
                    var temp_name = c_name.substring(0,c_name.length-3);
                    cell.className = temp_name + "Slct";
                }
                else
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
                if((id-1)%2 == 1)
                    cell.className = c_name + "Alt";
                else
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
        chkbx.title = "Deselect All";
        selectAllEntries(theForm,chkbx,controlName,tableName);
    }
    else
    {
        chkbx.title = "Select All";
        deSelectAllEntries(theForm,chkbx,controlName,tableName);
    }
    return true;
}

function selectAllEntries(theForm,chkbxall,controlName, tableName)
{
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName)
        {
            theForm.elements[k].checked = true;
            toggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm);
        }
    }
    return true;
}

function deSelectAllEntries(theForm,chkbxall,controlName, tableName)
{
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
        {
            theForm.elements[k].checked = false;
            toggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm);
        }
    }
    return true;
}

function changeTip(chkbxall)
{
    if(chkbxall.checked)
        chkbxall.title = "Deselect All";
    else
        chkbxall.title = "Select All";
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
                if((parseInt(chkIndex))%2 == 1)
                {
                    var temp_name = c_name.substring(0,c_name.length-3);
                    cell.className = temp_name + "Slct";
                }
                else
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
                if((chkIndex)%2 == 1)
                    cell.className = c_name + "Alt";
                else
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
    if(bFlag)
        chkbxall.checked = true;
    else
        chkbxall.checked = false;
}

function dynamicOnSelectAll(theForm, chkbx,controlName,tableName,chkpos,controlNameIndex)
{
    if(chkbx.checked)
    {
        chkbx.title = "Deselect All";
        dynamicSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,0,controlNameIndex);
    }
    else
    {
        chkbx.title = "Select All";
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
                theForm.elements[k].checked = true;
                dynamicToggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm,chkposition,chkindex,ind);
                ind++;
                chkindex++;
                chkposition++;
            }
        }
        else
        {
            if(theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName)
            {
                theForm.elements[k].checked = true;
                dynamicToggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm,chkposition,chkindex,controlNameIndex);
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
                dynamicToggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm,chkposition,chkindex,ind);
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
                dynamicToggleRowSelection(theForm.elements[k],chkbxall,tableName,controlName, theForm,chkposition,chkindex,controlNameIndex);
                chkindex++;
                chkposition++;
            }
        }
    }
    return true;
}
// For hot keys handling
var SHIFT_CNTR_T = 20;
var SHIFT_CNTR_M = 13;
var SHIFT_CNTR_S = 19;
var SHIFT_CNTR_L = 12;
var SHIFT_CNTR_O = 15;
var SHIFT_CNTR_F = 6;
var SHIFT_CNTR_I = 9;
var SHIFT_CNTR_A = 1;
var SHIFT_CNTR_N = 14;
var SHIFT_CNTR_K = 11;
var SHIFT_CNTR_H = 27;
var SHIFT_CNTR_W = 23;
var SHIFT_CNTR_D = 4;
var SHIFT_CNTR_G = 7;
var SHIFT_CNTR_Q = 17;
var SHIFT_CNTR_P = 16;
var SHIFT_CNTR_E = 5;
var SHIFT_CNTR_R = 18;
var SHIFT_CNTR_C = 3;
var SHIFT_CNTR_V = 22;
var SHIFT_CNTR_U = 21;
var SHIFT_CNTR_Z = 26;

function handleShortKeys(evt)
{
    var keyAsciiCode = 0;
    if(isIE)
        evt = event;
    if((evt.shiftKey) && (evt.ctrlKey))
    {
        if(isIE)
        {
            keyAsciiCode = evt.keyCode;
        }
        else
        {
            keyAsciiCode = evt.which;
            keyAsciiCode = keyAsciiCode - 64;
        }
        switch(keyAsciiCode)
        {
            case SHIFT_CNTR_T:
                goToTasks();
                break;
            case SHIFT_CNTR_M:
                goToStatus();
                break;
            case SHIFT_CNTR_S:
                goToApps();
                break;
            case SHIFT_CNTR_H:
                goToAlerts();
                break;
            case SHIFT_CNTR_O:
                goToProfile();
                break;
            case SHIFT_CNTR_F:
                goToFilterList();
                break;
            case SHIFT_CNTR_I:
                goToInstanceList();
                break;
            case SHIFT_CNTR_A:
                goToApplicationList();
                break;
            case SHIFT_CNTR_N:
                goToAppLevel();
                break;
            case SHIFT_CNTR_K:
                goToTaskLevel();
                break;
            case SHIFT_CNTR_W:
                goToWsLevel()
                break;
            case SHIFT_CNTR_D:
                goToDashboardHome();
                break;
            case SHIFT_CNTR_R:
                goToMyReports();
                break;
            case SHIFT_CNTR_G:
                goToGroupAdmin();
                break;
            case SHIFT_CNTR_Q:
                goToQueueAdmin();
                break;
            case SHIFT_CNTR_P:
                goToPermissionAdmin();
                break;
            case SHIFT_CNTR_E:
                goToDelegateAdmin();
                break;
            case SHIFT_CNTR_C:
                goToBscView();
                break;
            case SHIFT_CNTR_V:
                goToLogViewer();
                break;
            case SHIFT_CNTR_U:
                goToUserAdmin();
                break;
            case SHIFT_CNTR_L:
                goToBLApps();
                break;
            case SHIFT_CNTR_Z:
                goToBSApps();
                break;
        }
    }
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
        chkbx.title = "Deselect All";
        dynamicSelectAllEntries(theForm,chkbx,controlName,tableName,chkpos,chkindex,controlNameIndex);
    }
    else
    {
        chkbx.title = "Select All";
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


function isEmpty(textFieldName) {
    var textField = document.getElementById(textFieldName).value;
    var re = /^\s{1,}$/g; //match any white space including space, tab, form-feed, etc.
    if ((textField.length==0) || (textField==null) || ((textField.search(re)) > -1)) {
        return true;
    } else {
        return false;
    }
}
