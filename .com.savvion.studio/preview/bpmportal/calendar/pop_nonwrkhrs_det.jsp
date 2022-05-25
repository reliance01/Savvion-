<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.savvion.sbm.bizmanage.pagegenerator.ServletTools"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<?xml version="1.0" encoding="UTF-8"?>
<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
   String name= ServletTools.getUTFString(request,"name");
   if(name==null)
    name="";
    String val = ServletTools.getUTFString(request,"value");
    if(val == null)
        val="0";
    String opt = ServletTools.getUTFString(request,"opt");
    if(opt == null)
        opt="day";
    String type = ServletTools.getUTFString(request,"type");
    if(type == null)
        type="Weekend";
%>

<head>
<sbm:setLocale value="<%= bizManage.getLocale() %>" />
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="NonWorkingTimeDetails" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%@ include file="../common/include_css_static.jspf" %>
<%@ include file="../common/include_top.jsp" %>
<script language="JavaScript" src="../javascript/utilities.js"></script>
<script>
    <%@ include file="../javascript/bizcalendar.js" %>
</script>
<script language="JavaScript" src="../javascript/nonworkinghrs.js"></script>
<script language="JavaScript" src="../javascript/cal.js"></script>
<script language="Javascript">
<!--
function setDefaults()
{
    fillYearDropDownWithAll('bizNonWrkHourYear',2005,2030);
    fillDateDropDown('bizNonWrkHourDate');
    fillMonthDropDownWithAll('bizNonWrkHourMonth');
    setCheckBoxStyleForIE();
    loadNonWorkingTime('<%=val%>');
}

function onChangeYear()
{
        if(document.forms[0].bizNonWrkHourYear.value=="All" || document.forms[0].bizNonWrkHourYear.value == "all")
        {
            onSelectOption('date');
            disableGroup(document.forms[0],'radiobutton',false);
        }
        else
        {
            onSelectOption('date');
            disableGroup(document.forms[0],'radiobutton',true);
        }
}

function clickedReset()
{
    document.forms[0].reset();
    loadNonWorkingTime('<%=val%>');
}

function fillDate() {


    var drop_down = eval ("document.forms[0]." + 'bizNonWrkHourDate');

    var imonth = getMonth(document.forms[0].bizNonWrkHourMonth.value);
    var maxDate = 0;

    if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8
        || imonth == 10 || imonth == 12) {

        maxDate = 31;

    } else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {

        maxDate = 30;
    } else if (imonth == 2){

        if(isLeapYear(document.forms[0].bizNonWrkHourYear.value) || document.forms[0].bizNonWrkHourYear.value == "All") {
            maxDate = 29;
        } else {
            maxDate = 28;
        }
    }
    drop_down.options.length = 0;

    var counter = 1;
    for(var i=0;i<maxDate;i++) {
        drop_down.options[i] = new Option(""+counter , ""+counter);
        counter = counter + 1;
    }

}
//-->
</script>
</head>
<body onLoad=setDefaults();>
<form name="addnonwrkhrs" >
<%@ include file="../common/include_form_body.jsp" %>
<input type="hidden" name="nonwrkname" value='<%=name%>' />
<input type="hidden" name="typeselected" value="" />
<input type="hidden" name="optionselected" value="" />
<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp; <sbm:message key="NonWorkingTimeDetails" /></td>
          </tr>
          <tr>
            <td class="PopTblEmboss" align="center">
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="SegTblInBg">
                          <table border="0" cellpadding="6" cellspacing="1" width="100%" class="SegDataTbl">
                            <tr>
                              <td class="SegValRt"><sbm:message key="Name" />:</td>
                              <td width="100%" class="SegFieldLft"><%=name%></td>
                            </tr>
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Type" />:</td>
                              <td class="SegFieldLftAlt"><table border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td><select class="InptSelect" name="nonwrktype">
                                        <option value="-1" selected="selected"><sbm:message key="SelectOne" /></option>
                                        <option value="0"><sbm:message key="FullDay" /></option>
                                        <option value="1"><sbm:message key="FirstHalf" /></option>
                                        <option value="2"><sbm:message key="SecondHalf" /></option>
                                      </select> </td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input name="radiobuttontype" type="radio" class="InptChkBox" value="Weekend" onclick="onSelectType('Weekend');" />
                                      </b> </td>
                                    <td class="SmlTxt"><b><sbm:message key="Weekend" />&nbsp;</b></td>
                                    <td>&nbsp;</td>
                                    <td> <b>
                                      <input name="radiobuttontype" type="radio" class="InptChkBox" value="Holiday" onclick="onSelectType('Holiday')" />
                                      </b> </td>
                                    <td class="SmlTxt" ><b><sbm:message key="Holiday" />&nbsp;</b></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                            <tr>
                              <td class="SegValRt"><sbm:message key="Year" />:</td>
                              <td class="SegFieldLft"><select class="InptSelect" name="bizNonWrkHourYear" onchange="onChangeYear();fillDate();">
                                </select></td>
                            </tr>
                            <tr>
                              <td class="SegValRtAlt"><sbm:message key="Month" />:</td>
                              <td class="SegFieldLftAlt"><select class="InptSelect" name="bizNonWrkHourMonth" onchange="fillDate();">
                                </select></td>
                            </tr>
                            <tr>
                              <td nowrap="nowrap" class="SegValRt"><sbm:message key="SelectOne" />:</td>
                              <td class="SegFieldLft"><table border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td> <b>
                                      <input name="radiobutton" type="radio" class="InptChkBox" value="date" onclick="onSelectOption('date');" />
                                      </b> </td>
                                    <td class="SmlTxt"><b><sbm:message key="Date" />:&nbsp;</b></td>
                                    <td class="SmlTxt"><select class="InptSelect" name="bizNonWrkHourDate">
                                      </select></td>
                                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                    <td> <b>
                                      <input name="radiobutton" type="radio" class="InptChkBox" value="day" onclick="onSelectOption('day');" />
                                      </b> </td>
                                    <td class="SmlTxt" ><b><sbm:message key="Day" />:&nbsp;</b></td>
                                    <td class="SmlTxt"><select class="InptSelect" name="bizNonWrkHourDay">
                                        <option value="-1" selected="selected"><sbm:message key="SelectOne" /></option>
                                        <option value="Sunday"><sbm:message key="calendar.day.sunday" /></option>
                                        <option value="Monday"><sbm:message key="calendar.day.monday" /></option>
                                        <option value="Tuesday"><sbm:message key="calendar.day.tuesday" /></option>
                                        <option value="Wednesday"><sbm:message key="calendar.day.wednesday" /></option>
                                        <option value="Thursday"><sbm:message key="calendar.day.thursday" /></option>
                                        <option value="Friday"><sbm:message key="calendar.day.friday" /></option>
                                        <option value="Saturday"><sbm:message key="calendar.day.saturday" /></option>
                                      </select></td>
                                    <td class="SmlTxt">&nbsp;&nbsp;<sbm:message key="Occurrence" />:&nbsp;</td>
                                    <td class="SmlTxt"><select class="InptSelect" name="bizNonWrkHourOcc" >
                                        <option value="-1" selected="selected"><sbm:message key="SelectOne" /></option>
                                        <option value="all"><sbm:message key="All" /></option>
                                        <option value="first"><sbm:message key="First" /></option>
                                        <option value="second"><sbm:message key="Second" /></option>
                                        <option value="third"><sbm:message key="Third" /></option>
                                        <option value="fourth"><sbm:message key="Fourth" /></option>
                                        <option value="fifth"><sbm:message key="Fifth" /></option>
                                        <option value="last"><sbm:message key="Last" /></option>
                                      </select></td>
                                  </tr>
                                </table></td>
                            </tr>
                            <tbody>
                              <tr>
                                <td class="SegFieldCntr" colspan="2"> <table border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td class="BtnSpace"> <input type="button" name="save" value=" <sbm:message key="save" /> "  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="javascript:if(saveNonWorkingHrs('edit')) { window.close() }" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="resetBtn" value="<sbm:message key="reset" />" class="ScrnButton" onmouseover="this.className='ScrnButtonHover';" onmouseout="this.className='ScrnButton';" onclick="clickedReset();" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="delete" value="<sbm:message key="Delete" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="javascript:opener.deleteNonWorkingTimeFromId(<%=val%>);window.close()" />
                                      </td>
                                      <td class="BtnSpace"> <input type="button" name="cancel" value="<sbm:message key="cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="window.close()" />
                                      </td>
                                    </tr>
                                  </table>

                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </td></tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <%@ include file="../common/include_bottom.jsp" %>
</div>
</body>
</html>
