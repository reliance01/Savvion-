
<script type="text/javascript" language="JavaScript">
<!--
//set checkbox styles for IE

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

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function goToFilterList()
{
    MM_goToURL('parent','../../../bpmportal/myhome/filter_list.jsp');
}
function goToInstanceList()
{
    MM_goToURL('parent','../../../bpmportal/management/overview.jsp');
}
function goToApplicationList()
{
    MM_goToURL('parent','../../../bpmportal/management/infopush_chart_view.jsp');
}
function goToAppLevel()
{
    MM_goToURL('parent','../../../bpmportal/management/instance_search.jsp');
}
function goToTaskLevel()
{
    MM_goToURL('parent','../../../bpmportal/management/user_list.jsp');
}
function goToWsLevel()
{
    MM_goToURL('parent','../../../bpmportal/management/wrkstep_search.jsp');
}
function goToBscView()
{
    MM_goToURL('parent','../../../bpmportal/management/bal_score.jsp');
}
function goToBscDesign()
{
    MM_goToURL('parent','../../../bpmportal/management/bsc_list.jsp');
}
function goToAimReport()
{
    MM_goToURL('parent','../../../bpmportal/management/reports.jsp');
}
function goStatusReportGenerator()
{
    MM_goToURL('parent','../../../bpmportal/management/report_generator_status.jsp');
}
function goTimeAnalysisReportGenerator()
{
    MM_goToURL('parent','../../../bpmportal/management/report_generator_timeanalysis.jsp');
}
function goWorkloadReportGenerator()
{
    MM_goToURL('parent','../../../bpmportal/management/report_generator_workload.jsp');
}
function goToAimAssigned()
{
    MM_goToURL('parent','../../../bpmportal/management/user_list.jsp');
}
function goToAimAvailable()
{
    MM_goToURL('parent','../../../bpmportal/management/avail_tsk_mod.jsp');
}
function goToSystemStatus()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=systemStatus');
}
function goToSBMConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=SBM');
}
function goToUserAdmin()
{
    MM_goToURL('parent','../../../bpmportal/administration/UserManagementServlet?action=listUser');
}
function goToGroupAdmin()
{
    MM_goToURL('parent','../../../bpmportal/administration/UserManagementServlet?action=listGroup');
}
function goToPermissionAdmin()
{
    MM_goToURL('parent','../../../bpmportal/administration/UserManagementServlet?action=listPermission');
}
function goToBLApps()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminAppServlet?action=bizlogicAll');
}
function goToBSApps()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminAppServlet?action=bizsoloAll');
}
function goToLogViewer()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=viewLog');
}
function goToBizSoloConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=BizSolo');
}
function goToPortalConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=Portal');
}
function goToBizLogicConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=BizLogic');
}
function goToBizPulseConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=BizPulse');
}
function goToArchiverConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=Archiver');
}
function goToArchiverConfig()
{
    MM_goToURL('parent','../../../bpmportal/administration/SBMAdminServlet?action=confPara&confName=Archiver');
}
function goToSupport()
{
    MM_goToURL('parent','../../../bpmportal/general/support.jsp');
}
function goToHelp()
{
    MM_goToURL('parent','../../../bpmportal/general/support.jsp');
}
function goToLogout()
{
    MM_goToURL('parent','../../../bpmportal/logout.jsp');
}
function goToTasks()
{
    MM_goToURL('parent','../../../bpmportal/myhome/mytasks.jsp');
}
function goToStatus()
{
    MM_goToURL('parent','../../../bpmportal/myhome/status.jsp');
}
function goToAlerts()
{
    MM_goToURL('parent','../../../bpmportal/myhome/my_alerts.jsp');
}
function goToApps()
{
    MM_goToURL('parent','../../../bpmportal/myhome/apps.jsp');
}
function goToLinks()
{
    MM_goToURL('parent','../../../bpmportal/myhome/links_list.jsp');
}
function goToProfile()
{
    MM_goToURL('parent','../../../bpmportal/myhome/profile.jsp');
}
function goToManagementConsole()
{
    MM_goToURL('parent','../../../bpmportal/management/console_all.jsp');
}
function goToInfopadManager()
{
    MM_goToURL('parent','../../../bpmportal/management/infopad_mgr.jsp');
}

var activeMenu = null;
var timer_id = null;

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
    if((activeMenu.style)&&(activeMenu.style.visibility!=null)){
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
    // Prepare to shut it in 250 milliseconds
    timer_id = window.setTimeout('hideNow();',250);
  }
}

function hideNow()
{
  if((activeMenu.style)&&(activeMenu.style.visibility))
  {
    activeMenu.style.visibility = 'hidden';
  }
}

//-->
</script>
