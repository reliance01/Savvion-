<jsp:useBean id="umBean" scope="session" class="com.savvion.sbmadmin.bean.UserManagementBean">
</jsp:useBean>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" language="JavaScript">
<!--

    opener.location.reload();
<% if (umBean.isAddMoreMember()) { %>
    this.location = "../administration/UserManagementServlet?action=searchUserAndGroup&refresh=true";
<% } else { %>
    window.close();
<% } %>

-->
</script>
</head>
<body>
</body>
</html>