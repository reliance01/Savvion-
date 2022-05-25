// JavaScript for JavaApplet document handling
// 
// Author: Zhili Xu
// This is move from HTML page which is formerly inside page


function upload() {
    var status = true;
    var containsApplet = false;
    var lastDocApplet;    
    for ( var i = 0; i < document.applets.length; i++ ) {                
    if(!(isThisFlashObject(document.applets[i].type))){   
          if ( document.applets[i].getName() == 'DocTransfer' ) {               
            containsApplet = true;
            lastDocApplet = document.applets[i];
            status = lastDocApplet.updateDocuments();
            if (status == false)
                   break;
        }
    }
    }
    for ( var i = 0; i < document.embeds.length; i++ ) {
    	if(document.embeds[i].isXML){
    		if ( document.embeds[i].getName() == 'DocTransfer' ) {
         		containsApplet = true;
        		lastDocApplet = document.embeds[i];
        		status = lastDocApplet.updateDocuments();
        		if (status == false)
        		    break;
        		 }
      		}
     	}

    if ( containsApplet == true && status == true ) {
	
    	lastDocApplet.clientGarbageCollection();
    }

    return (status == true)?true:false;
}

var wnd;

function openExtWnd(urlInfo, title, ht, wd) {
var wid = 450;
var hit = 150;
    wnd = new Ext.Window({
              layout : 'fit',
              headerAsTest:true,
              resizable : false,
              modal:true,
              title:title,
              width: wid,
              height: hit,
              autoLoad : {url:encodeURI(urlInfo),
                          scripts:true}
			
    });

	wnd.setPosition(50,30);
    wnd.show();
}
    
function closeWindow() {
  wnd.close();
}



var isDocumentInIframe = false;
function openDocumentPresentation(urlStr,isIframe) {    


      try {
        var  fiid = document.myTaskForm?document.myTaskForm.fiid.value:document.form.fiid.value;
        if (urlStr.indexOf('?') > 0)
          urlStr += '&fiid=';
        else
           urlStr += '?fiid=';
        urlStr += encodeURIComponent(fiid);  
      } catch(e) {
      }

    isDocumentInIframe = isIframe;
    if(isIframe) {
        window.parent.openParentFunction(urlStr);
    } else {
        openParentFunction(urlStr);
    }
	
}


function isThisFlashObject(objectType){
	var isFlash = false;
    if(objectType != undefined && objectType == 'application/x-shockwave-flash'){
		isFlash = true;	
	}	
	return isFlash;
}

///////*******  File upload part integration starts *****/////

function fileUploadUtil(ptName,piName,dsName,fileName,readonly,docId,appType,docType,required,fiid, instanceId) {
	// Preparing title for file upload popup
	var truncatedTitle;

	if(appType == 'BIZLOGIC') {
		truncatedTitle = ptName + " (" + dsName + ")";
	} else {
		truncatedTitle = dsName;
	}
	
	if(readonly){
    mode = 'view';
  } else {
    mode = 'update';
  }
          
	var fileExtn = 'xml';
    if (fiid == '' || fiid == 'null')
      try {
         fiid = document.myTaskForm.fiid.value;
         //alert('fiid:'+fiid);
      } catch(e) {
      }
  var header   = getLocalizedString('FileUpload_Header');
  var props = {
                "mode"       : mode,
                "viewURL"    : "",
                "isRequired" : required,
				"ptName":ptName,
				"piName":piName,
				"taskName":''
              };
    
	new Bm.component.fileView(truncatedTitle,header,dsName,fileName,fileExtn,appType,docType,props,fiid,instanceId);
	
}

function prepareViewURL(docId,appType,docType,refKey,fiid,instanceId) {
  
  var viewURL = getContextPath() + '/bizsite/ajaxutil.portal';
  
  if(!Ext.isEmpty(docId) && docId > 0) {
    viewURL = viewURL + '?xmlid='+ docId+ '&fiid=' + fiid;
  } else {
        if(appType == 'BIZLOGIC') {
            viewURL = viewURL + '?appType='+ appType + '&docType=' +docType + '&refKey=' + refKey+ '&fiid=' + fiid + '&instanceId=' + instanceId;
        } else {
            viewURL = viewURL + '?appType='+ appType + '&docType=' +docType + '&refKey=' + refKey+ '&fiid=' + fiid;
        }
  }
  viewURL = viewURL+'&action=showXmlDocument';
  return viewURL;
}

///////*******  File upload part integration starts *****/////
