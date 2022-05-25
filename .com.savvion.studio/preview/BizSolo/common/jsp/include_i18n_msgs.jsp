<sbm:setLocale value="<%= request.getLocale() %>"></sbm:setLocale>
<% try{ %><sbm:setBundle scope="page" basename="properties/bizsolo"></sbm:setBundle><% } catch(Exception e){}%>
<script>
var propsI18n =   { "ctxp":"<%=request.getContextPath()%>",
                    "FileUpload_Add":"<sbm:message key="FileUpload_Add" />",
                    "FileUpload_Upload":"<sbm:message key="FileUpload_Upload" />",
                    "FileUpload_Cancel":"<sbm:message key="FileUpload_Cancel" />",
                    "FileUpload_Browse":"<sbm:message key="FileUpload_Browse" />",
                    "FileUpload_Label":"<sbm:message key="FileUpload_Label" />",
                    "FileUpload_Remove":"<sbm:message key="FileUpload_Remove" />",
                    "FileUpload_Delete":"<sbm:message key="FileUpload_Delete" />",
                    "FileUpload_Header":"<sbm:message key="FileUpload_Header" />",
                    "FileUpload_Empty_File":"<sbm:message key="FileUpload_Empty_File" />",
                    "FileUpload_Invalid_File":"<sbm:message key="FileUpload_Invalid_File" />"
                  };
function getLocalizedString(str){
    var localizedStr;
    localizedStr = propsI18n[str];
    if(localizedStr != null && typeof localizedStr != 'undefined'){
        return localizedStr;
    }else{
        return str;
    }
}                  
</script>