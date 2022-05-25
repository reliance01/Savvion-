<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList,com.savvion.sbm.bpmportal.calendar.CalendarAction,
                com.savvion.sbm.calendar.CalDataController,
                com.savvion.sbm.calendar.SBMCalendar,
                com.savvion.sbm.bizlogic.util.BLCalendar,
                com.savvion.sbm.calendar.svo.SBMCalendarInfo"%>
<%@page import="com.tdiinc.userManager.User,
                com.tdiinc.userManager.Realm,
                com.savvion.sbmadmin.util.SBMAdminConfig,
                com.savvion.sbm.bpmportal.bizsite.util.SBMBizCalendarUtil,
                com.savvion.sbm.bizmanage.pagegenerator.ServletTools,
                com.savvion.acl.impl.SBMACLUser"%>
<%@ page errorPage="bizcal_error.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
    String isFromMyHome = request.getParameter("myHomeView");
    
    String parentCalendar = null; 
    String aTempCalendarName = ServletTools.getUTFString(request,"calName"); 
    
    if (aTempCalendarName != null && aTempCalendarName.indexOf("|") > 0 ) {
        parentCalendar = aTempCalendarName.substring(0, aTempCalendarName.indexOf("|"));
    } else {
        parentCalendar = aTempCalendarName;
    }       

    Calendar aCalendar = Calendar.getInstance();
    int aYear = aCalendar.get(Calendar.YEAR);

    if(request.getParameter("action") != null &&
       request.getParameter("action").equals("selectCal")) {
            String[] wCalendars = ServletTools.getUTFValues(request,"radiobutton1");

            SBMBizCalendarUtil aUtil = SBMBizCalendarUtil.self();
            String aValidCalendar = aUtil.getUserBizCalender(wCalendars[0], bizManage.getSBMACLUser(request, bizManage.getName()));
	    	
            if(aValidCalendar != null)
                response.sendRedirect("bizcal_myhome_view.jsp?calName="+ ServletTools.getUTFEncodedString(aValidCalendar)+"&year="+aYear);
    }
    int[] caltype = {BLCalendar.types.OTHER,BLCalendar.types.SYSTEM};
    ArrayList calendars = CalDataController.self().getCalendars(caltype);

    String sortorder = request.getParameter("sort");
    if (sortorder == null)
        sortorder = "ascending";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="CalendarList" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript" src="../javascript/utilities.js"></script>
<script language="JavaScript">
<!--

function selectCalendar()
{
    var theForm = document.forms[0];
    var found = false;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='radio'&& theForm.elements[k].name=='radiobutton1')
        {
            if(theForm.elements[k].checked)
            {
                found = true;
                break;
            }
        }
    }
    if(found == false)
    {
        alert('Please select a calendar');
        return false;
    }
    theForm.action.value='selectCal';
    return true;
}

function viewCalendar() {
    window.location="bizcal_myhome_view.jsp?calName=<%= aTempCalendarName %>&year=<%= aYear %>";
}

function toggleRadioSelection(srno,tableName, controlName)
{
    var table=document.getElementById(tableName);
    var theForm = document.forms[0];
    var isChecked = false;
    var selId = 0;
    for(var k=0;k<theForm.elements.length;k++)
    {
        if(theForm.elements[k].type=='radio'&& theForm.elements[k].name==controlName)
        {
            if(theForm.elements[k].checked)
            {
                selId = parseInt(theForm.elements[k].value);
                break;
            }
        }

    }
    for(var c=1;c <table.rows.length;c++)
    {
        var row = table.rows[c];
        if(c == srno+1)
        {
            for(var j=0;j < row.cells.length;j++)
            {
                var cell = row.cells[j];
                var c_name = cell.className;

                if(c_name.indexOf("Slct") == -1)
                {
                    if((c-1)%2 == 1)
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
                    if((c-1)%2 == 1)
                        cell.className = c_name + "Alt";
                    else
                        cell.className = c_name;
                }
            }
        }
    }
}
//-->
</script>
</head>
<body>
<form name="listform" method="post" action="../calendar/bizcal_myhome_list.jsp" >
<div id="northDiv">
    <%@ include file="../common/include_body.jsp" %>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <%
                String menu1 = "0";
                String submenu1 = "7";
            %>
            <%@ include file="../common/include_menu_static.jspf" %>
        </td>
    </tr>
    </table>

    <table border="0" cellspacing="0" cellpadding="0" width="100%" class="breadcrumbSec">
    <tr>
        <td align="left" valign="middle" width="100%">
            <table border="0" cellspacing="0" cellpadding="0" id="breadcrumb">
            <tr>
                <td><a class="ActionLnk" href="javascript:goToTasks()"><sbm:message key="BPM_Portal_Task.Home" /></a></td>
                <td class="bcSepIcon" valign="middle"><sbm:message key="Preferences" /></td>
                <td class="bcSepIcon" valign="middle"><sbm:message key="calendar" /></td>
            </tr>
            </table>
        </td>
    </tr>
    </table>
</div>

<div id="resultDiv">
    <input type="hidden" name="action" value = "" />
    <input type="hidden" name="calName" value = "<%= aTempCalendarName %>" />
    <input type="hidden" name="year" value = "<%= aYear %>" />
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td class="TblBgDark">
            <table border="0" cellspacing="0" cellpadding="2" width="100%" class="ListTableCellPad" id="listTable" name="listTable">
            <tr>
                <td class="ChkBoxSlctAll">&nbsp; </td>
                <td class="ColHdrSrNo"><sbm:message key="NoStr" /></td>
                <td class="ColHdrSort">
                    <table border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr>
                        <%
                            String title = "Ascending Order";
                            String image = "up";
                            if ("ascending".equals(sortorder))
                            {
                                sortorder = "descending";
                                Collections.sort(calendars, CalDataController.ascendingComparator);
                            } else {
                                sortorder = "ascending";
                                title = "Descending Order";
                                image = "down";
                                Collections.sort(calendars, CalDataController.descendingComparator);
                            }
                 
                            String href = "bizcal_myhome_list.jsp?sort="+sortorder;
                            if (aTempCalendarName != null) {
                                href = href + "&calName="+aTempCalendarName; 
                            }
                        %>
                        <td>
                            <a href="<%= href %>" class="ColHdrLnk"><sbm:message key="Name" /> </a>
                        </td>
                        <td>
                            <a href="<%= href %>" title="<%= title %>"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%=image %>.gif" width="16" height="16" border="0"></a>
                        </td>
                    </tr>
                    </table>
                </td>
                <td class="ColHdr"><sbm:message key="Description" /> </td>
            </tr>
            <%
              if(calendars != null && calendars.size() > 0) {
                            if(parentCalendar != null) {
                                for(int nCount=0;nCount < calendars.size();nCount++)
                                {
                                    SBMCalendarInfo calInfo = (SBMCalendarInfo) calendars.get(nCount);
                                    String calName = calInfo.getId();

                                    // Checking for row selection condition
                                    String classStateChk = null;
                                    String classState = null;
                                    if (calName.equals(parentCalendar)){
                                        classStateChk = "ChkBoxSlct";
                                        classState = "ValLftSlct";
                                    } else {
                                        classStateChk = nCount%2 == 1 ? "ChkBoxAlt" : "ChkBox";
                                        classState = nCount%2 == 1 ? "ValLftAlt" : "ValLft";
                                    }
            %>
                            <tr class="RedTxt">
                                <td class="<%=classStateChk%>" > <input name="radiobutton1" type="radio" class="InptRadio" value="<%=calName%>" <%=calName.equals(parentCalendar) ? "checked" : ""%> onclick="toggleRadioSelection(<%=nCount%>,'listTable','radiobutton1');"/></td>
                                <td class="<%=classState%>"><%=nCount+1%></td>
                                <td class="<%=classState%>"><%=calName%></td>
                                <td class="<%=classState%>"><%=calInfo.getDescription() == null ? "" : calInfo.getDescription()%></td>
                            </tr>
            <%
              }
                            } else {
                                boolean isSysCalExists = false;
                                SBMCalendarInfo sysCalInfo = (SBMCalendarInfo) SBMCalendar.getSystemCalendar();
                            
                                if (sysCalInfo != null){
                                    isSysCalExists = true;
                                }
                            
                                if (isSysCalExists == false){
                                    String strSelected = "checked";
                                    for(int nCount=0;nCount < calendars.size();nCount++) {
                                        SBMCalendarInfo calInfo = (SBMCalendarInfo) calendars.get(nCount);
                                        String calName = calInfo.getId();
                                    
                                        // Checking for row selection condition
                                        String classStateChk = null;
                                        String classState = null;
                                    
                                        if (nCount == 0){
                                            classStateChk = "ChkBoxSlct";
                                            classState = "ValLftSlct";
                                        } else {
                                            classStateChk = nCount%2 == 1 ? "ChkBoxAlt" : "ChkBox";
                                            classState = nCount%2 == 1 ? "ValLftAlt" : "ValLft";
                                        }
            %>
                                <tr class="RedTxt">
                                    <td class="<%=classStateChk%>"> <input name="radiobutton1" type="radio" class="InptRadio" value="<%=calName%>" <%=strSelected%> onclick="toggleRadioSelection(<%=nCount%>,'listTable','radiobutton1');"/></td>
                                    <td class="<%=classState%>"><%=nCount+1%></td>
                                    <td class="<%=classState%>"><%=calName%></td>
                                    <td class="<%=classState%>"><%=calInfo.getDescription() == null ? "" : calInfo.getDescription()%></td>
                                </tr>
            <%
              strSelected ="";
                                    }// end for
                                } else if(isSysCalExists) {
                                    for(int nCount=0;nCount < calendars.size();nCount++)
                                    {
                                        SBMCalendarInfo calInfo = (SBMCalendarInfo) calendars.get(nCount);
                                        String calName = calInfo.getId();
                                    
                                        // Checking for row selection condition
                                        String classStateChk = null;
                                        String classState = null;
                                    
                                        if (calInfo.isSystemCalendar()){
                                            classStateChk = "ChkBoxSlct";
                                            classState = "ValLftSlct";
                                        } else{
                                            classStateChk = nCount%2 == 1 ? "ChkBoxAlt" : "ChkBox";
                                            classState = nCount%2 == 1 ? "ValLftAlt" : "ValLft";
                                        }
            %>
                                <tr class="RedTxt">
                                    <td class="<%=classStateChk%>" > <input name="radiobutton1" type="radio" class="InptRadio" value="<%=calName%>" <%=(calInfo.isSystemCalendar()) ? "checked" : "" %> onclick="toggleRadioSelection(<%=nCount%>,'listTable','radiobutton1');"/></td>
                                    <td class="<%=classState%>"><%= nCount+1 %></td>
                                    <td class="<%=classState%>"><%=calName%></td>
                                    <td class="<%=classState%>"><%= calInfo.getDescription() == null ? "" : calInfo.getDescription() %></td>
                                </tr>
            <%
                            }// end for
                        }
                    }
                } else
                {
            %>
                    <tr align="center">
                        <td class="ValCntr" colspan="3">
                            <table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                            <tr>
                                <td valign="top"><img alt="" src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16" /></td>
                                <td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoRecFound" /></span></span></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
            <%
                }
            %>
            </table>
        </td>
    </tr>
    </table>
</div>

<div id="cmdDiv">   
    <table width="100%" cellpadding="0" align="center" cellspacing="0" border="0">
    <tr align="center">
        <td class="ButtonDarkBg">
            <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td class="BtnSpace">
                    <input type="submit" name="selectcalendar" value="<sbm:message key="calendar.pref.list.button.01"/>" class="ScrnButton" onMouseOver="this.className='ScrnButtonHover';" onMouseOut="this.className='ScrnButton';" onClick="return selectCalendar();"/>
                </td>
                <% if (aTempCalendarName != null) { %>
                    <td class="BtnSpace">
                        <input type="button" name="viewcalendar" value="<sbm:message key="Cancel"/>" class="ScrnButton" onMouseOver="this.className='ScrnButtonHover';" onMouseOut="this.className='ScrnButton';" onClick="viewCalendar();" />
                    </td>
                <% } %>
            </tr>
            </table>
        </td> 
    </tr>
    </table>
</div>
    
<div id="southDiv">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <%@ include file="../common/include_copyright_static.jsp" %>
        </td>
    </tr>
    <tr>
        <td>
            <%@ include file="../common/include_bottom.jsp"%>
        </td>
    </tr>
    </table>
</div>

<script type="text/javascript" language="JavaScript">
//<!--
    Ext.onReady(function(){
        var viewport = new Bm.util.BmViewport('<sbm:message key="calendar" />');
    });
//-->   
</script>
</body>
</html>

