// Copyright (c) 2015 - 2016. Aurea Software, Inc. All Rights Reserved.

// You are hereby placed on notice that the software, its related technology and
// services may be covered by one or more United States ("US") and non-US patents.
// A listing that associates patented and patent-pending products included in the
// software, software updates, their related technology and services with one or
// more patent numbers is available for you and the general public's access at
// www.aurea.com/legal/ (the "Patent Notice") without charge. The association of
// products-to-patent numbers at the Patent Notice may not be an exclusive listing
// of associations, and other unlisted patents or pending patents may also be
// associated with the products. Likewise, the patents or pending patents may also
// be associated with unlisted products. You agree to regularly review the
// products-to-patent number(s) association at the Patent Notice to check for
// updates.

function checkBLAppButton(form, appIndexCtrl, appStatus) {
	checkRefreshBtnStatus(form);
    if (appIndexCtrl.checked) {
        if (appStatus == "Installed") {
            form.installBizLogic.disabled = true;            
            form.resumeBizLogic.disabled = true;
            form.unpublishBizLogic.disabled = true;
            form.installBizLogic.className='ScrnButtonDis';
            form.resumeBizLogic.className='ScrnButtonDis';
            form.unpublishBizLogic.className='ScrnButtonDis';
        } else if (appStatus == "Uninstalled") {
            form.uninstallBizLogic.disabled = true;
            form.suspendBizLogic.disabled = true;
            form.resumeBizLogic.disabled = true;
            form.goToPublishBizLogic.disabled = true;
            form.unpublishBizLogic.disabled = true;
            form.refreshBizLogic.disabled = true;
            form.refreshBizLogic.className='ScrnButtonDis'
            form.force.disabled = true;
            form.uninstallBizLogic.className='ScrnButtonDis';
            form.suspendBizLogic.className='ScrnButtonDis';
            form.resumeBizLogic.className='ScrnButtonDis';
            form.goToPublishBizLogic.className='ScrnButtonDis';
            form.unpublishBizLogic.className='ScrnButtonDis';            
        } else if (appStatus == "Suspended") {
            form.installBizLogic.disabled = true;
            form.uninstallBizLogic.disabled = true;
            form.suspendBizLogic.disabled = true;
            form.goToPublishBizLogic.disabled = true;
            form.unpublishBizLogic.disabled = true;
			form.refreshBizLogic.disabled = true;
			form.refreshBizLogic.className='ScrnButtonDis'
            form.force.disabled = true;
            form.installBizLogic.className='ScrnButtonDis';
            form.uninstallBizLogic.className='ScrnButtonDis';
            form.suspendBizLogic.className='ScrnButtonDis';
            form.goToPublishBizLogic.className='ScrnButtonDis';
            form.unpublishBizLogic.className='ScrnButtonDis';
        } else if (appStatus == "Published") {
            form.installBizLogic.disabled = true;
            form.uninstallBizLogic.disabled = true;
            form.resumeBizLogic.disabled = true;
            form.force.disabled = true;
            form.refreshBizLogic.disabled = true;
            form.refreshBizLogic.className='ScrnButtonDis'
            form.goToPublishBizLogic.disabled = true;
            form.installBizLogic.className='ScrnButtonDis';
            form.uninstallBizLogic.className='ScrnButtonDis';
            form.resumeBizLogic.className='ScrnButtonDis';
            form.goToPublishBizLogic.className='ScrnButtonDis';
        } else if (appStatus == "Deprecated") {
            form.installBizLogic.disabled = true;
            form.suspendBizLogic.disabled = true;
            form.goToPublishBizLogic.disabled = true;
            form.unpublishBizLogic.disabled = true;
            form.refreshBizLogic.disabled = true;
            form.refreshBizLogic.className='ScrnButtonDis'
            form.installBizLogic.className='ScrnButtonDis';
            form.suspendBizLogic.className='ScrnButtonDis';
            form.goToPublishBizLogic.className='ScrnButtonDis';
            form.unpublishBizLogic.className='ScrnButtonDis';
        }
   } else {
       checkAllBLAppButton(form);
   }
  checkBLInstalledbutPublished(form, appIndexCtrl, appStatus);
}

function checkAllBLAppButton(form) {
    var appStatus;
    var isInstallDisabled = false;
    var isUninstallDisabled = false;
    var isSuspendDisabled = false;
    var isResumeDisabled = false;
    var isPublishDisabled = false;
    var isUnpublishDisabled = false;
    var isRefreshDisabled = false;

    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            appStatus = form.appStatus[i].value;

            if (appStatus == "Installed") {
                isInstallDisabled = true;
                isResumeDisabled = true;
                isUnpublishDisabled = true;
            } else if (appStatus == "Uninstalled") {
                isUninstallDisabled = true;
                isSuspendDisabled = true;
                isResumeDisabled = true;
                isPublishDisabled = true;
                isUnpublishDisabled = true;
            } else if (appStatus == "Suspended") {
                isInstallDisabled = true;
                isUninstallDisabled = true;
                isSuspendDisabled = true;
                isPublishDisabled = true;
                isUnpublishDisabled = true;
            } else if (appStatus == "Published") {
                isInstallDisabled = true;
                isUninstallDisabled = true;
                isResumeDisabled = true;
                isPublishDisabled = true;
            } else if (appStatus == "Deprecated") {
                isInstallDisabled = true;
                isSuspendDisabled = true;
                isPublishDisabled = true;
                isUnpublishDisabled = true;
            }

        }
    }
    if (form.installBizLogic != null) {
        form.installBizLogic.disabled = isInstallDisabled;        
        if(!isInstallDisabled)
            form.installBizLogic.className='ScrnButton';
        else
            form.installBizLogic.className='ScrnButtonDis';
    }

    if (form.uninstallBizLogic != null) {
        form.uninstallBizLogic.disabled = isUninstallDisabled;
        form.force.disabled = isUninstallDisabled;
        if(!isUninstallDisabled)
            form.uninstallBizLogic.className='ScrnButton';
        else
            form.uninstallBizLogic.className='ScrnButtonDis';
    }

	if (form.refreshBizLogic != null) {
		form.refreshBizLogic.disabled = isRefreshDisabled;
		form.force.disabled = isUninstallDisabled;
		if(!isUninstallDisabled)
			form.refreshBizLogic.className='ScrnButton';
		else
			form.refreshBizLogic.className='ScrnButtonDis';
    }

    if (form.suspendBizLogic != null) {
        form.suspendBizLogic.disabled = isSuspendDisabled;
        if(!isSuspendDisabled)
            form.suspendBizLogic.className='ScrnButton';
        else
            form.suspendBizLogic.className='ScrnButtonDis';
    }

    if (form.resumeBizLogic != null) {
        form.resumeBizLogic.disabled = isResumeDisabled;
        if(!isResumeDisabled)
            form.resumeBizLogic.className='ScrnButton';
        else
            form.resumeBizLogic.className='ScrnButtonDis';
    }

    if (form.goToPublishBizLogic != null) {
        form.goToPublishBizLogic.disabled = isPublishDisabled;
        if(!isPublishDisabled)
            form.goToPublishBizLogic.className='ScrnButton';
        else
            form.goToPublishBizLogic.className='ScrnButtonDis';
    }

    if (form.unpublishBizLogic != null) {
        form.unpublishBizLogic.disabled = isUnpublishDisabled;
        if(!isUnpublishDisabled)
            form.unpublishBizLogic.className='ScrnButton';
        else
            form.unpublishBizLogic.className='ScrnButtonDis';
    }
}


function checkBSAppButton(form, appIndexCtrl, appStatus) {
    if (appIndexCtrl.checked) {
        if (appStatus == "Installed") {
            form.installBizSolo.disabled = true;
            form.installBizSolo.className='ScrnButtonDis';
        } else if (appStatus == "Uninstalled") {
            form.uninstallBizSolo.disabled = true;
			form.uninstallBizSolo.className='ScrnButtonDis';
        } else if (appStatus == "Published") {
            form.installBizSolo.disabled = true;
            form.uninstallBizSolo.disabled = true;
            form.installBizSolo.className='ScrnButtonDis';
            form.uninstallBizSolo.className='ScrnButtonDis';
        }
   } else {
       checkAllBSAppButton(form);
   }
   
}

function checkAllBSAppButton(form) {
    var appStatus;
    var isInstallDisabled = false;
    var isUninstallDisabled = false;
    var isPublishDisabled = false;
    var isUnpublishDisabled = false;

    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            appStatus = form.pubStatus[i].value;
            if (appStatus == "Installed") {
                isInstallDisabled = true;
                isResumeDisabled = true;
                isUnpublishDisabled = true;
            } else if (appStatus == "Uninstalled") {
                isUninstallDisabled = true;
                isSuspendDisabled = true;
                isResumeDisabled = true;
                isPublishDisabled = true;
                isUnpublishDisabled = true;
                
            } else if (appStatus == "Published") {
                isInstallDisabled = true;
                isUninstallDisabled = true;
                isResumeDisabled = true;
                isPublishDisabled = true;
            }

        }
    }

    if (form.installBizSolo != null) {
        form.installBizSolo.disabled = isInstallDisabled;
        if(!isInstallDisabled) {
            form.installBizSolo.className='ScrnButton';
        } else {
            form.installBizSolo.className='ScrnButtonDis';
        }
    }

    if (form.uninstallBizSolo != null) {
        form.uninstallBizSolo.disabled = isUninstallDisabled;
        if(!isUninstallDisabled)
            form.uninstallBizSolo.className='ScrnButton';
        else
            form.uninstallBizSolo.className='ScrnButtonDis';
    }

    if (form.publishBizSolo != null) {
        form.publishBizSolo.disabled = isPublishDisabled;
        if(!isPublishDisabled)
            form.publishBizSolo.className='ScrnButton';
        else
            form.publishBizSolo.className='ScrnButtonDis';
    }

    if (form.unpublishBizSolo != null) {
        form.unpublishBizSolo.disabled = isUnpublishDisabled;
        if(!isUnpublishDisabled)
            form.unpublishBizSolo.className='ScrnButton';
        else
            form.unpublishBizSolo.className='ScrnButtonDis';
    }
}

function publishBizLogicButCheck(form){
    var appStatus;
    var publishCnt = 0;
    var checkedFlag = false; 
    var checkedFlagCount = 0;

    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            checkedFlag = true;
            checkedFlagCount++;
            appStatus = form.appStatus[i].value;
            if (appStatus == "Installed") {
                publishCnt++;

            }
            else{
                publishCnt--;
            }
        }
    }
     
    if(publishCnt == 1 && checkedFlagCount == 1){
        form.goToPublishBizLogic.disabled = false;
        form.goToPublishBizLogic.className='ScrnButton';        
    } else if(publishCnt != 1){
        form.goToPublishBizLogic.disabled = true;
        form.goToPublishBizLogic.className='ScrnButtonDis';        
    }
    
}

function publishBizSoloButCheck(form){

    var appStatus;
    var publishCnt = 0;
    var checkedFlag = false;
    var checkedFlagCount = 0;

    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            checkedFlag = true;
            appStatus = form.pubStatus[i].value;
            if (appStatus == "Installed") {
                publishCnt++;
            }
            else{
                publishCnt--;
            }            
        }
    }
    
    if(publishCnt == 1 && checkedFlagCount == 1){
        form.publishBizSolo.disabled = false;
        form.publishBizSolo.className='ScrnButton';        
    }
    else if(publishCnt != 1){
        form.publishBizSolo.disabled = true;
        form.publishBizSolo.className='ScrnButtonDis';        
    }
}

function checkBLInstalledbutPublished(theForm,chkbox,appStatus){
    if(chkbox.checked) {
        if (appStatus == "Published") {
	    theForm.goToPublishBizLogic.disabled = true;
	    theForm.goToPublishBizLogic.className='ScrnButtonDis';
        } else if (appStatus == "Suspended") {
	    theForm.suspendBizLogic.disabled = true;
	    theForm.suspendBizLogic.className='ScrnButtonDis';
	    theForm.goToPublishBizLogic.disabled = true;
	    theForm.goToPublishBizLogic.className='ScrnButtonDis';
        }
    } else {
	checkAllBLInstalledbutPublished(form);
    }
   checkRefreshBtnStatus(form); 
   publishBizLogicButCheck(form);
}

function checkAllBLInstalledbutPublished(form) {
    var appStatus;
    var isPublishDisabled = false;
    var isSuspendedDisabled = false;
    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            appStatus = form.appStatus[i].value;
	    if (appStatus == "Published") {
                isPublishDisabled = true;
            } else if (appStatus == "Suspended") {
                isSuspendedDisabled = true;
                isPublishDisabled = true;
            } else if (appStatus == "Uninstalled"){
				isSuspendedDisabled = true;
				isPublishDisabled = true;
            } else if (appStatus == "Deprecated"){
				isSuspendedDisabled = true;
				isPublishDisabled = true;
            }
       	}
    }

    if (form.goToPublishBizLogic != null) {
        form.goToPublishBizLogic.disabled = isPublishDisabled;
        if(!isPublishDisabled)
            form.goToPublishBizLogic.className='ScrnButton';
        else
            form.goToPublishBizLogic.className='ScrnButtonDis';
    }

    if (form.suspendBizLogic != null) {
        form.suspendBizLogic.disabled = isSuspendedDisabled;
        if(!isSuspendedDisabled)
            form.suspendBizLogic.className='ScrnButton';
        else
            form.suspendBizLogic.className='ScrnButtonDis';
    }
    
    publishBizLogicButCheck(form);
}

function checkBSInstalledbutPublished(theForm,chkbox,pubstatus){
    if(chkbox.checked && pubstatus){	
	theForm.publishBizSolo.disabled = true;
	theForm.publishBizSolo.className='ScrnButtonDis';
    }else{
	checkAllBSInstalledbutPublished(form);
    }
     
}

function checkAllBSInstalledbutPublished(form) {
    var appStatus;
    var isPublishDisabled = false;
	if (form.appIndex == null)
		return;
    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            appStatus = form.pubStatus[i].value;
	    if (appStatus == "Published") {
                isPublishDisabled = true;
                break;
            }
       	}
    }
    if (isPublishDisabled) {
    	form.publishBizSolo.className='ScrnButtonDis';
    	form.publishBizSolo.disabled = true;
    } else {
            form.publishBizSolo.className='ScrnButton';
            form.publishBizSolo.disabled = false;
          }
    
}

function checkRefreshBtnByStatus(form) {
    var appStatus;
    var disableFlag = false;
    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            appStatus = form.appStatus[i].value;

            if (appStatus == 'Uninstalled' || appStatus == 'Suspended' || appStatus == 'Deprecated') {
                disableFlag = true;
                break;
            }
        }
    }
    
    if (disableFlag){
        document.form.refreshBizLogic.disabled = true;
        document.form.refreshBizLogic.className = 'ScrnButtonDis';
    } else {
        document.form.refreshBizLogic.disabled = false;
        document.form.refreshBizLogic.className = 'ScrnButton';
    }
    
    return disableFlag;
}

function checkRefreshBtnByRules(form) {
    var hasPT;
    var disableFlag = false;
    for (var i=0; i < form.appIndex.length; i++) {
        if (form.appIndex[i].checked) {
            hasPT = form.hasPT[i].value;

            if (hasPT == "false"){
                disableFlag = true;
                break;
            }
        }
    }
    
    if (disableFlag){
        document.form.refreshBizLogic.disabled = true;
        document.form.refreshBizLogic.className = 'ScrnButtonDis';
    } else {
        document.form.refreshBizLogic.disabled = false;
        document.form.refreshBizLogic.className = 'ScrnButton';
    }
    
}

function checkRefreshBtnStatus(form) {
	checkRefreshBtnByStatus(form);
	
	if (checkRefreshBtnByStatus(form) == false) {
		checkRefreshBtnByRules(form);
	}
}
