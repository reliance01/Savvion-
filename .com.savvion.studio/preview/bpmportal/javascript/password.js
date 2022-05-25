
function validateUserProfilePassword(securityEnabled,user,currPasswd,encrypted){    
	var parameters = Form.serialize("profile", true);
	parameters['action'] = 'updateUserProfile';
	parameters['userName'] = user;
	parameters['currentPassword'] = currPasswd;
	parameters['isPasswordEncrypted'] = encrypted;

	Ext.Ajax.request({
            url:'ajaxutil.portal',
            params:parameters,
            method:'post',
            success: function handleValidPwd (result,request) {
        				window.location="../myhome/confirmupdateprofile.jsp";
                     },
            failure: function handleInvalidPwd(result,request) {
                   if(isSessionTimeOut(result)){
                        redirectToLgn('<sbm:message key="sesstimeouterror"/>','<sbm:message key="errorTitle"/>','<%=request.getContextPath()%>');
                    } else {
                        showInvPwdErrorMsg(result,request);
                        return false;
                    }
            }
    });
}

function validatePassword(securityEnabled,user,currPasswd,passwd,encrypted){    
            Ext.Ajax.request({
            url:'ajaxutil.portal',
            params:{action:'validatePwd',userName:user, currentPassword:currPasswd,password:passwd,isPasswordEncrypted:encrypted},
            method:'post',
            success: passwdValid,
            failure: showInvPwdErrorMsg
        });
   }
    
    function passwdValid(result,request){
        var pwdValidationResult = result.responseText;
        submitForm();
    }
    
    
    function showInvPwdErrorMsg(result,request){
	    var msg = result.responseText;
	    //response can be of format: "(MSG_ID):<msg>;<context>;" - or - 
	    //{"exceptionCause":<exceptionCause>,"exceptionLocalizedMessage":"(MSG_ID):<msg>;<stack trace>
	    var tmp = msg.split(';');
	    if(!Ext.isEmpty(tmp) && tmp.length > 0){
	    	var idx = tmp[0].indexOf("exceptionLocalizedMessage"); 
	    	if(idx > -1){
	    		tmp = tmp[0].split(":");
	    		if(!Ext.isEmpty(tmp) && tmp.length >= 3){
	    			tmp[0] = tmp[2].substring(1) + ":" + (tmp[3] ?  tmp[3] : '');  
	    		}	
	    	}
	    	tmp = tmp[0].split(':');
			if(!Ext.isEmpty(tmp) && tmp.length > 0){
				msg = tmp[0];
				//var msgid = msg.substring(1, msg.length -1);
				var msgid = msg.substring(msg.indexOf('(') + 1, msg.indexOf(')'));
				msg = getLocalizedString(msgid);
			}
		}
        showMsgBox('',msg,'ERROR');
        resetPasswordFields(document.forms[0]);
    }
    
    function submitForm(){      
        document.forms[0].submit(); 
    }
    
    function showMsgBox(msgTitle,message,typeOfMsg){
           if(typeOfMsg == 'ERROR'){
            iconType = Ext.MessageBox.ERROR;
           }
           else{
            iconType = Ext.MessageBox.INFO;
           }
            
           var mBox = Ext.Msg.show({
           title: msgTitle,
           msg: message,
           width: 300,
           buttons: Ext.MessageBox.OK,
           icon:iconType
        });
   }
   
   function encryptPasswdFields(theForm){
    var userName;
    var bizUserId;
    var uname;
    if(theForm.userName != undefined){
        userName = theForm.userName.value;
        uname = userName;
    }
    if(theForm.BizPassUserID != undefined){
        bizUserId = theForm.BizPassUserID.value;
        uname = bizUserId;
    }
        
    try{
        for(i=0;i<theForm.elements.length;i++){
            if(theForm.elements[i].name == 'currentPassword'){
            var currPwdEl = theForm.currentPassword;
            currPwdEl.value = Bm.BmEncryptUtils.encrypt(uname,currPwdEl.value,'-');
        }
        if(theForm.elements[i].name == 'newPassword'){
            var newPwdEl = theForm.newPassword;
            newPwdEl.value = Bm.BmEncryptUtils.encrypt(uname,newPwdEl.value,'-');               
        }
        if(theForm.elements[i].name == 'password'){
            var pwdEl = theForm.password;
            pwdEl.value = Bm.BmEncryptUtils.encrypt(uname,pwdEl.value,'-');         
        }
        if(theForm.elements[i].name == 'BizPassUserPassword'){
        var bizPwdEl = theForm.BizPassUserPassword;
        bizPwdEl.value = Bm.BmEncryptUtils.encrypt(uname,bizPwdEl.value,'-');       
        }
        if(theForm.elements[i].name == 'confNewPassword'){
        var confNewPwdEl = theForm.confNewPassword;
        confNewPwdEl.value = Bm.BmEncryptUtils.encrypt(uname,confNewPwdEl.value,'-');       
        }
        if(theForm.elements[i].name == 'confPassword'){
        var confPwdEl = theForm.confPassword;
        confPwdEl.value = Bm.BmEncryptUtils.encrypt(uname,confPwdEl.value,'-');     
        }
       
    }       
    return true;
    }catch(e){
        alert(e);  
        return false;
    }
   }
   
   
   function resetPasswordFields(theForm){
       
       for(i=0;i<theForm.elements.length;i++){
          if(theForm.elements[i].name == 'currentPassword'){
          var currPwdEl = theForm.currentPassword;
          currPwdEl.value = '';
      }
      if(theForm.elements[i].name == 'newPassword'){
          var newPwdEl = theForm.newPassword;
          newPwdEl.value = '';
      }
      if(theForm.elements[i].name == 'password'){
          var pwdEl = theForm.password;
          pwdEl.value = '';
      }
      if(theForm.elements[i].name == 'Password'){
          var pwdElement = theForm.Password;
          pwdElement.value = '';
      }
      if(theForm.elements[i].name == 'BizPassUserPassword'){
          var bizPwdEl = theForm.BizPassUserPassword;
          bizPwdEl.value = '';
      }
      if(theForm.elements[i].name == 'confNewPassword'){
          var confNewPwdEl = theForm.confNewPassword;
          confNewPwdEl.value = '';
          }
          if(theForm.elements[i].name == 'confPassword'){
          var confPwdEl = theForm.confPassword;
          confPwdEl.value = '';
          }
       
       }
   
   
   }

    