<%@ page import="java.util.*,java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList,com.savvion.sbm.bpmportal.calendar.CalendarAction,
                com.savvion.sbm.calendar.CalDataController,
                com.savvion.sbm.calendar.SBMCalendar,
        com.savvion.sbm.bizlogic.util.BLCalendar,
                com.savvion.sbm.calendar.svo.SBMCalendarInfo,
                com.savvion.sbm.bpmportal.bizsite.util.SBMBizCalendarUtil,
                com.savvion.sbm.util.logger.*,
                com.savvion.sbm.bizmanage.pagegenerator.ServletTools,
                com.savvion.sbm.bpmportal.util.PortalConstants"%>
<%@ page errorPage="bizcal_error.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
  String calenDarAction = request.getParameter("action");
    
    if(calenDarAction != null &&
   calenDarAction.equals("deleteCal")) {
   CalendarAction.self().execute(request, response);
    }
    boolean isSystemCalendar = false;
    String systemCalendarName = "";
    int[] caltype = {BLCalendar.types.OTHER,BLCalendar.types.SYSTEM};
    ArrayList calendars = CalDataController.self().getCalendars(caltype);
    if(calenDarAction != null &&
   (calenDarAction.equals("setDefault") || calenDarAction.equals("unSetDefault") ) ) {
   String defCalName = request.getParameter("defaultCalendar");
   if(calendars != null && calendars.size() > 0)
   {
        for(int i=0;i < calendars.size();i++)
        {
            SBMCalendarInfo defCalInfo = (SBMCalendarInfo) calendars.get(i);
            if(defCalInfo != null && defCalInfo.getId().equals(defCalName))
            {
                SBMCalendar.init(null);
                SBMCalendar sbmCal = new SBMCalendar(defCalName);
                SBMLogger logger = SBMBizCalendarUtil.self().getSBMLogger();
                sbmCal.setLogger(logger);
			 if (calenDarAction.equals("unSetDefault")) {
			 	sbmCal.setRendererType(BLCalendar.types.OTHER);
				response.sendRedirect("bizcal_cnf2.jsp?defCalName=" + ServletTools.getUTFEncodedString(defCalName) + "&msgAction=unsetDefaultCal");
			 } else {
			 	sbmCal.setRendererType(BLCalendar.types.SYSTEM);
				response.sendRedirect("bizcal_cnf2.jsp?defCalName=" +  ServletTools.getUTFEncodedString(defCalName));
			 }
            }
        }
   }


    }
    calendars = CalDataController.self().getCalendars(caltype);
    String sortorder = request.getParameter("sort");
    if (sortorder == null)
    sortorder = "ascending";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<sbm:setLocale value="<%=bizManage.getLocale()%>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="CalendarList" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript" src="../javascript/utilities.js"></script>
<script>
<%@ include file="../javascript/bizcalendar.js" %>
</script>


<script language="JavaScript">
<!--

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function setDefaultCalendarValue(isSystemCalendar, systemCalendarName) {
	if (document.forms[0].radiobutton != null && isSystemCalendar == false) {
		document.forms[0].radiobutton[0].checked=true;
	}
	document.forms[0].defaultCalendar.value = systemCalendarName;
}

function setDefaultCalendar() {
   if (document.forms[0].radiobutton[0].checked) {
   	   if (document.forms[0].defaultCalendar.value == "") {
	   	alert("<sbm:message key="bizCalAlert38" />");
		return false;
	   }
	   
	   if (confirm('<sbm:message key="bizCalAlert39" />')) {
		document.forms[0].action.value='unSetDefault';
		return true;
	   } else {
	   	return false;
	   }
   }


    document.forms[0].action.value='setDefault';
    var found = false;

     for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == 'radiobutton') {

                if(document.forms[0].elements[i].checked) {
                   found = true;
                   if (confirm('<sbm:message key="bizCalAlert5" />')) {
			document.forms[0].defaultCalendar.value=document.forms[0].elements[i].value;
                   } else {
                        return false;
                   }
                   break;
                }
            }
     }
     if (!found){
        alert('<sbm:message key="bizCalAlert6" />');
     }
 }
 //-->
</script>
</head>
<body>
<form name="BizCalendarList" method="post" action="../calendar/bizcal_list.jsp">
<div id="northDiv">
	<input type="hidden" name="defaultCalendar" value="">
	<%@ include file="../common/include_body.jsp" %>
  	<input type="hidden" name="action" value=""/>
  	<input type="hidden" name="calName" value=""/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>
      		<%
      		  String menu1 = "2";
      		  			String submenu1 = "0";
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
				 	<td><sbm:message key="administration" /></td>
		            <td class="bcSepIcon" valign="middle"><sbm:message key="system" /></td>
		            <td class="bcSepIcon" valign="middle"><sbm:message key="calendars" /></td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</div>

<div id="filterDiv">	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>
    		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="TblBgLight">
        	<tr>
          		<td> 
          			<table width="100%" align="center" class="ButtonLightBg">
              		<tr>
                		<td class="SmlTxt"> 
                			<input type="submit" name="addCal" value="<sbm:message key="AddCalendar" />"  class="OthrButton" onmouseover="this.className='OthrButtonHover';" onmouseout="this.className='OthrButton';" onclick="MM_goToURL('parent','../calendar/bizcal_add.jsp');return document.MM_returnValue" />
                		</td>
              		</tr>
            		</table>
            	</td>
        	</tr>
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
            		<input type="submit" name="save" value="<sbm:message key="save" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="return setDefaultCalendar();" />
                </td>
                <td class="BtnSpace"> 
                	<input type="reset" name="reset" value="<sbm:message key="reset" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="dynamicOnSelectAll(document.BizCalendarList,this,'delcal','CalTable',1,-1);" />
                </td>
                <td class="BtnSpace">
                	<input type="button" name="deletecal" value="<sbm:message key="delete" /> " class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="return deleteCalendars('<%=calendars.size()%>');"/>
                </td>
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

<div id="resultDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
    	<td class="TblBgDark">
        	<table border="0" cellspacing="0" cellpadding="0" width="100%" class="ListTableCellPad" id="CalTable" name="CalTable">
          	<tr>
            	<td class="ChkBoxSlctAll"> 
            		<input type="checkbox" class="InptChkBox" name="chkbxAll" value="checkbox" title="Select All" onmouseover="changeTip(this);" onclick="dynamicOnSelectAll(document.BizCalendarList,this,'delcal','CalTable',2,-1);" <%=(calendars.size() == 0 ?"disabled=\"disabled\"":"")%> />
            	</td>
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
		                  		                    }
		                  		                    else
		                  		                    {
		                  		                        sortorder = "ascending";
		                  		                        title = "Descending Order";
		                  		                        image = "down";
		                  		                        Collections.sort(calendars, CalDataController.descendingComparator);
		                  		                    }
		                  		                    String href = "../calendar/bizcal_list.jsp?sort="+sortorder;
		                  %>
                  		<td><a href="<%=href%>" class="ColHdrLnk"><sbm:message key="Name" /> </a></td>
                  		<td><a href="<%=href%>" title="<%=title%>"><img src="../css/<c:out value="${bizManage.theme}" />/images/sort_<%= image %>.gif" width="16" height="16" border="0"></a></td>
                	</tr>
              		</table>
              	</td>
            	<td class="ColHdr"><sbm:message key="Description" /> </td>
            	<td class="ColHdr"> <sbm:message key="AimView" /> </td>
            	<td class="ColHdr"> <sbm:message key="DefaultSelection" /> </td>
          	</tr>
          	<%
          	  if(calendars != null && calendars.size() > 0)
          	        	{
          	%>
		  			<tr>
						<td class="ChkBoxAlt" >&nbsp;</td>
						<td class="ValCntr">1</td>
						<td class="ValLftAlt" ><sbm:message key="Calendar.NoCalendar" /></td>
						<td class="ValLftAlt" ><sbm:message key="Calendar.UnsetDefaultCalendar" /></td>
						<td class="ValCntrAlt" >&nbsp;</td>
						<td class="ValCntrAlt" ><input name="radiobutton" type="radio" class="InptRadio" value="-1" /></td>
		  			</tr>
         	<%
         	  Calendar aCalendar = Calendar.getInstance();
         	            	int aYear = aCalendar.get(Calendar.YEAR);
         	            	for(int nCount=0;nCount < calendars.size();nCount++)
         	            	{
         	                	SBMCalendarInfo calInfo = (SBMCalendarInfo) calendars.get(nCount);
         	                	String calName = calInfo.getId();
         			    
         			    			if (calInfo.isSystemCalendar() == true){
         			    				isSystemCalendar = true;
         								systemCalendarName = calName;
         			    			}
         	%>
                  		<tr>
                  		<td class="<%=(nCount%2 == 1) ? "ChkBoxAlt" : "ChkBox"%>"> 
                    			<input type="checkbox" class="InptChkBox" name="delcal" value="<%=calName%>" onclick="dynamicToggleRowSelection(this,document.BizCalendarList.chkbxAll,'CalTable','delcal',document.BizCalendarList,<%=nCount+2%>,<%=nCount%>,-1);" />
                    		</td>
                    		<td class=<% if (nCount%2 == 1) { %>"ValCntrAlt"<% } else { %>"ValCntr"<% } %>><%= nCount+2 %></td>
                    		<td class="<%=(nCount%2 == 1) ? "ValLftAlt" : "ValLft"%>"><a href=javascript:encodeHrefUrl("../calendar/bizcal_det.jsp?calName=<%=URLEncoder.encode(calName,PortalConstants.UTF8)%>") class="TblLnk"><%=calName%></a></td>
                    		<td class="<%=(nCount%2 == 1) ? "ValLftAlt" : "ValLft"%>"><%= calInfo.getDescription() == null ? "" : calInfo.getDescription() %></td>
                    	  <td class="<%=(nCount%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><a href=javascript:encodeHrefUrl("../calendar/bizcal_view.jsp?calName=<%=URLEncoder.encode(calName,PortalConstants.UTF8)%>&year=<%=aYear%>") class="TblLnk"><img src="../css/<c:out value="${bizManage.theme}" />/images/bizcal_calendar_view.gif" title="<sbm:message key="CalendarView" />" width="34" height="16" border="0"></a></td>
                    		<td class="<%=(nCount%2 == 1) ? "ValCntrAlt" : "ValCntr"%>"><input name="radiobutton" type="radio" class="InptRadio" value="<%=calName%>" <%=(calInfo.isSystemCalendar()) ? "checked=true" : ""%> /></td>
                  		</tr>
          	<%
                	}
            	} else {
          	%>
          			<tr align="center">
              			<td class="ValCntr" colspan="7"> <br/>
                			<table border="0" cellspacing="2" cellpadding="4" align="center" width="50%">
                  			<tr>
                    			<td valign="top"><img alt="" src="../css/<c:out value="${bizManage.theme}" />/images/icon_info_small.gif" width="16" height="16" /></td>
                    			<td width="100%"><span class="Info"><span style="font-size: 9pt; font-weight: bold"><sbm:message key="NoRecFound" /></span></span></td>
                  			</tr>
                			</table>
                			<br/>
              			</td>
            		</tr>
       		<%
            	}
          	%>
        	</table>
      	</td>
    </tr>
  	</table>
  
  	<% if(calendars.size() == 0) { %>
    	<script type="text/javascript">
    	//<!--
			document.BizCalendarList.save.disabled = true;
          	document.BizCalendarList.save.className = 'ScrnButtonDis';
          	document.BizCalendarList.reset.disabled = true;
          	document.BizCalendarList.reset.className = 'ScrnButtonDis';
          	document.BizCalendarList.deletecal.disabled = true;
          	document.BizCalendarList.deletecal.className = 'ScrnButtonDis';
      	//-->
    	</script>
   	<% } %>

   <script>
   	<% if(calendars != null && calendars.size() > 0) { %>
	   	setDefaultCalendarValue(<%= isSystemCalendar %>, "<%= systemCalendarName %>");
	<% } %>
   </script>
   
</div>
	<script type="text/javascript">
	//<!--
		Ext.onReady(function(){
			var viewport = new Bm.util.BmViewport('<sbm:message key="calendars" />');
		});
	//-->	
	</script>

</form>
</body>
</html>
