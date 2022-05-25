<%@ page import="com.savvion.sbm.bizmanage.api.*" %>
<%@ page import="com.savvion.sbm.bpmportal.util.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<jsp:useBean id="bizManage" class="com.savvion.sbm.bizmanage.api.BizManageBean" scope="session" />

<%
//bizManage.setRequest(request);
//bizManage.setResponse(response);

String id = request.getParameter("elementID");
String value = request.getParameter("value");
String pms = request.getParameter("pms"); //stands for precision - scale, that is the length of the first input area.
String scale = request.getParameter("scale"); //the length of the second input area.
String pmsValue = "";
String scaleValue = "";

char decimalSeparator = PortalNumberFormatUtil.getDecimalSeparator(bizManage.getLocale());

if (value == null)
    value = "";
else
{
    if (value.indexOf(decimalSeparator) > -1)
    {
        pmsValue = value.substring(0,value.indexOf(decimalSeparator));
        scaleValue = value.substring(value.indexOf(decimalSeparator)+1,value.length());
    }
    else
        pmsValue = value;
}
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <sbm:setLocale value="<%= bizManage.getLocale() %>" /> -->
<sbm:setBundle basename="properties/bpmportal" scope="page" />
<title><sbm:message key="DECIMAL" /> <sbm:message key="Dataslot" /> - <sbm:message key="SBM" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<jsp:include page="../common/include_css.jsp">
    <jsp:param name="login" value="false" />
</jsp:include>
<%@ include file="../common/include_top.jsp" %>
<script type="text/javascript" language="JavaScript">
<!--
function cleanup()
{
    //var element = window.opener.document.getElementById('<%= id %>');
    var element = window.opener.document.forms[0].<%= id %>;
    if(element == undefined || element == null ){
             element = window.opener.document.getElementById('<%= id %>');
    }
    
    if (element != null) {
        var pms = document.decimal.pms.value;
        var scale = document.decimal.scale.value;
        var point="<%= decimalSeparator %>";
        
        if (pms == null)
            pms = "";
        else
            pms = trimString(pms);
            
        if(pms != '' && pms != 0 && !Number(pms)){
            alert('<sbm:message key="decimalDataslotMsg5"/>');
            return false;
        }

        if (scale == null)
            scale = "";
        else
            scale = trimString(scale);
        
        if(scale != '' && scale != 0 && !Number(scale)){
            alert('<sbm:message key="decimalDataslotMsg6"/>');
            return false;
        }

        if (pms.length == 0 && scale.length == 0)
            element.value = "0.0";
        else {          
            if ( pms.length == 0 && scale.length > 0 )
                pms = "0";
            if (scale.length == 0 )
                point = "";
                
            element.value = pms + point + scale;
        }
            
    }
    window.close();
    return true;
}
//-->
</script>
</head>
<body>
<form name="decimal" action="" method="post">
<%@ include file="../common/include_form_body.jsp" %>
<div align="center">
  <%@ include file="../common/include_body.jsp" %>
  <table width="100%" cellspacing="0" border="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
          <tr>
            <td class="PopTitle">&nbsp;&nbsp;<sbm:message key="DECIMAL" /> <sbm:message key="Dataslot" /></td>
          </tr>
          <tr>
            <td class="PopTblEmboss" align="center">
              <table width="100%" cellpadding="6" cellspacing="2">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="SegTblInBg">
                          <table border="0" cellpadding="4" cellspacing="1" class="SegDataTbl" width="100%">
                            <tbody>
                              <tr>
                                <td width="40%" nowrap="nowrap" class="SegValRt"> <sbm:message key="decimalDataslotMsg1" />:</td>
                                <td class="SegFieldLft" width="60%">
                                  <table border="0" cellspacing="0">
                                    <tr>
                                      <td><input name="pms" type="text" class="InptTxt" size="10" maxlength="<%= pms %>" value="<%= pmsValue %>" /></td>
                                      <td nowrap="nowrap" class="SmlTxt"> &nbsp;(<sbm:message key="decimalDataslotMsg2" /> <%= pms %> <sbm:message key="decimalDataslotMsg3" />)</td>
                                    </tr>
                                  </table>
                                </td>
                              </tr>
                              <tr>
                                <td nowrap="nowrap" class="SegValRtAlt"> <sbm:message key="decimalDataslotMsg4" />:</td>
                                <td class="SegFieldLftAlt">
                                  <table border="0" cellspacing="0">
                                    <tr>
                                      <td><input name="scale" type="text" class="InptTxt" size="10" maxlength="<%= scale %>" value="<%= scaleValue %>" /></td>
                                      <td nowrap="nowrap" class="SmlTxt"> &nbsp;(<sbm:message key="decimalDataslotMsg2" /> <%= scale %> <sbm:message key="decimalDataslotMsg3" />)</td>
                                    </tr>
                                  </table>
                                </td>
                              </tr>
                              <tr>
                                <td class="SegFieldCntr" colspan="2"> <table border="0" cellspacing="0" cellpadding="0" align="center">
                                    <tr>
                                      <td class="PopBtnSpace"> <input type="submit" name="save" value="<sbm:message key="save" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="return cleanup();" />
                                      </td>
                                      <td class="PopBtnSpace"> <input type="reset" name="reset" value="<sbm:message key="reset" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" />
                                      </td>
                                      <td class="PopBtnSpace"> <input type="submit" name="cancel" value="<sbm:message key="cancel" />"  class="PopButton" onmouseover="this.className='PopButtonHover';" onmouseout="this.className='PopButton';" onclick="window.close()" />
                                      </td>
                                    </tr>
                                  </table></td>
                              </tr>
                            </tbody>
                          </table></td></tr>
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
</form>
</body>
</html>
