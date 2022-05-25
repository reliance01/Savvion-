<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="com.savvion.sbm.bpmportal.util.PortalConstants" %>
<%@ include file="cxui/incl/pgs/login_tag.jspf" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>

<%
    String  productId           = System.getProperty(PortalConstants.SAVVION_PRODUCT_ID);
    boolean isPAM               = false;

    if (productId != null && productId.trim().equalsIgnoreCase(PortalConstants.PAM)) {
        isPAM = true;
    } else {
        isPAM = false;
    }
%>

<sbmtags:doctype /><html><%@ include file="cxui/incl/pgs/login_hd.jspf" %>   
<body onload="BmLoginUtil.load();">
  <header class=hdr>
    <div class=full-contr>
      <div class=logo></div>
    </div>
  </header>
  <center>
    <section class=cnt>
      <div class=panel>
        <% if(!isPAM) { %>
        <h1 class="ttl"><sbm:message key="BPM_Portal_Product.PortalTitle" /><span> <sbm:message key="Login" /></span></h1>
        <% } else { %>
        <h1 class="ttl"><sbm:message key="PAM_Portal_Product.PortalTitle" /><span> <sbm:message key="Login" /></span></h1>
        <% } %>
        <form name="loginForm" onsubmit="return BmLoginUtil.submit(this);">
          <p id="infoMsg" name="infoMsg"></p>
          <input type="text"  name="<%= PortalConstants.BIZPASS_USER_ID %>" value="<%= bizManage.getName() %>" id="txtBizPassUserID"
            placeholder="<sbm:message key="username" />" />
          <input type="password" autocomplete="off"  name="<%= PortalConstants.BIZPASS_USER_PASSWD %>" id="txtBizPassUserPassword"
            placeholder="<sbm:message key="password" />" />
          <input type="submit" name="login" class="btn btn-green" id="login"/>
        </form>
      </div>
    </section>
  </center>
  <footer class="ftr">
    <center>
      <p><span id="copyrightMsg"></span> - <a id="supportLink"></a></p>
      <div class="logo"><sbm:message key="BPM_Portal_Product.Title" /></div>
    </center>
  </footer>
</body>
</html>