Ext.namespace('Bm.component');

/*
 * title - Title for File Dialog window.
 * header -  Header for File Dialog window.
 * key - dsName.
 * props - JSON object contains metadata such as mode,viewURL
 * fileName - Uploaded fileName.
 * fileExtn - need to pass file type for upload validation.
 * appType - may be bizlogic (or) bizsolo
 * docType - type of document for upload
 */

Bm.component.fileView = function(title,header,key,fileName,fileExtn,appType,docType,props,fiid,instanceId) {

  var required = props.isRequired;

    var mainPanel = Ext.getCmp('filePanel_'+key);
    if(!Ext.isEmpty(mainPanel)) {
        mainPanel.destroy();
    }
    
    var fileArray = new Array();
    fileArray.push(getFileNameBoxComp(props.viewURL,fileName, key, props.ptName, props.piName, props.taskName));
    fileArray.push(getDataslotNameHdn(key,required));
    fileArray.push(getMandatoryHdn(key,Ext.isEmpty(fileName)));
    if(props.mode == 'update'){
        fileArray.push(getDeleteBtn(title,header,key,props,fileExtn,appType,docType,fiid,instanceId));
    }
 
    var filePanel;
    if(!Ext.isEmpty(fileName)) { 
        filePanel = new Ext.Container({ 
              id :'filePanel_'+key,
              layout : 'table',
              cls : 'xmlDataslotPanel',
              unstyled : true,
              items : fileArray
        });
    } else {
        var isDisabled;
        if(props.mode == 'update') { 
            isDisabled = false;
        } else {
            isDisabled = true;
        }
        filePanel = new Ext.Container({ 
              id :'filePanel_'+key,
              layout : 'table',
              cls : 'xmlDataslotPanel',
              unstyled : true,
              items : [getAddBtn(title,header,key,props,fileExtn,appType,docType,fiid,instanceId,isDisabled),getDataslotNameHdn(key,required),getMandatoryHdn(key,required)]
        });
    }

    filePanel.render('fileEnclPanel_'+key);
    if(required && typeof document.getElementById('bizsite_dataslot_'+key) != 'undefined' && document.getElementById('bizsite_dataslot_'+key) != null)
           document.getElementById('bizsite_dataslot_'+key).alt = 'xml';
};

function getDataslotNameHdn(key,isRequired) {
  var dsNameHdn;
  if(isRequired) {
    dsNameHdn = ({
          xtype : 'hidden',
          name  : 'bizsite_dataslot_'+key,
          inputId  : 'bizsite_dataslot_'+key,         
          autoCreate: Ext.apply({alt: 'xml'}, Ext.form.TextField.prototype.defaultAutoCreate)
    });
  } else {
    dsNameHdn = ({
          xtype : 'hidden',
          name  : 'bizsite_dataslot_'+key
    });
  }
  return dsNameHdn;
}

function getMandatoryHdn(key,isRequired) {
  if(isRequired) {
    var flag =  false;
  } else {
    var flag = true;
  }
  
  var elHdn = ({
          xtype : 'hidden',
          inputId    : 'xmlcheck_mandatory_'+key,
          name  : 'xmlcheck_mandatory_'+key,
          value : flag
    });
 
  return elHdn;
}

function getFileNameBoxComp(viewURL,fileName, key, ptName,piName, taskName) {
	var fun = "getXmlContent('"+viewURL+"','"+key+"','"+ptName+"','"+piName+"','"+taskName+"');";
    var fileNameBoxComp = new Ext.Component({
        autoShow :true,
        autoEl : {
            tag : 'div',
            html : "<a href='#' class='ActionLnk' onclick=\"alert('Action not supported in preview mode');\">"+fileName+" </a>",
            style : 'padding: 0 5px 0 0;'
        }
     });
    return fileNameBoxComp;
}

function getAddBtn(title,header,key,props,fileExtn,appType,docType,fiid,instanceId,isDisabled) {
    var addBtn = ({
        xtype : 'bmfiledialogaddbtn',
        width: 70,
        height: 22,
        disabled: isDisabled,
        handler : function() {
            alert("Action not supported in preview mode");
        }
        
    }); 
    return addBtn;
}

function getDeleteBtn(title,header,key,props,fileExtn,appType,docType,fiid,instanceId) {        
    var json = prepareJson(appType,docType,key,fiid,instanceId);
    json.action = 'delete';
    var deleteBtn = ({
        xtype : 'bmfiledialogdeletebtn',
        height: 22,
        handler : function() {
	        alert("Action not supported in preview mode");
        }
 }); 
  return deleteBtn;
}

function openFileDialog(title,header,key,props,fileExtn,appType,docType,fiid,instanceId) {
    
    var fileUploadField = new Bm.component.FileUploadField({
                fieldLabel : header,
                labelAlign : 'right'
    });
    
    var truncatedTitle;
    if(!Ext.isEmpty(title)){
        truncatedTitle = title;
    } else {
        truncatedTitle = key;
    }
    
     var mainFormPanel = new Ext.form.FormPanel({
        title: truncatedTitle,
        fileUpload : true,      
        url: getContextPath() + '/bizsite/FileServlet?fiid='+fiid,
        method:'post',
        buttonAlign : 'center',     
        frame : true,
        bodyStyle : 'padding: 10px 10px 0 10px;',
      defaults: {
          allowBlank : false,
          msgTarget : 'side'
      },
        items:[ fileUploadField ],
        buttons:[
                 new Ext.Button({
                     text : getLocalizedString('FileUpload_Upload'), 
                     handler : function() {
                         var json = prepareJson(appType,docType,key,fiid,instanceId);
                         json.action = 'upload';
                         var fileName = fileUploadField.getValue();
                         if(isFileValid(fileName,fileExtn)) {
                            mainFormPanel.getForm().submit({
                                params: json,
                                success: function(fp, response){
                                        //var uploadedFName = response.result.fileName;
                                        var uploadedFName = fileName;
                                         refreshComponents(title,header,key,props,uploadedFName,fileExtn,appType,docType,fiid,instanceId);
                                         closeFileDialogWindow();
                                         if(props.isRequired) {
                                            setMandatoryHdnValue(key,true);
                               }
                                },
                                failure: function(fp, response) {
                                    if(Ext.isEmpty(fileName)) {
                                        Bm.component.showMsg(getLocalizedString('error'),
                                                               getLocalizedString('FileUpload_Empty_File'),
                                                               'bulkOpPanel',
                                                               Ext.MessageBox.ERROR);
                                    } else {
                                        closeFileDialogWindow();
                                        handleAjaxException(response.response, null);
                                    }
                              }
                            }); 
                         } else {
                                Bm.component.showMsg(getLocalizedString('error'),
                                                               getLocalizedString('FileUpload_Invalid_File') +' '+ fileExtn ,
                                                     'bulkOpPanel',
                                                     Ext.MessageBox.ERROR);
                         }
                    }   
                }),
                new Ext.Button({
                    text : getLocalizedString('FileUpload_Cancel'),
                    handler : function() {
                        closeFileDialog();
                    }
            })
        ]
    });
    
    fileDialogWnd = new Ext.Window({
                id : 'fileDialogWindow',
                headerAsTest : true,
                modal : true,
                resizable : false,
                title : getLocalizedString('FileUpload_Label') + ' '+ header,
                items : [mainFormPanel]
                //autoLoad : {url:urlStr, scripts:true}
    });

    fileDialogWnd.setSize({width:430,height:135}); 

    fileDialogWnd.doLayout();
    fileDialogWnd.show();
    fileDialogWnd.center();
}

function prepareJson(appType,docType,key,fiid,instanceId) {
    var json = {};
    json.appType = appType;
    json.docType = docType;
    json.referenceKey = key;
    json.metadata = '{}';
    json.fiid = fiid;
    json.instanceId = instanceId;
    return json;
}

function isFileValid(fileName,fileExtn) {
    var fileType = fileName.substring(fileName.length-3,fileName.length);
    if(Ext.isEmpty(fileExtn) || Ext.isEmpty(fileType) 
            || (Ext.util.Format.uppercase(fileType) == Ext.util.Format.uppercase(fileExtn))) {
        return true;
    } else {
        return false;
    }
}


function refreshComponents(title,header,key,props,uploadedFName,fileExtn,appType,docType,fiid,instanceId) {  
    new Bm.component.fileView(title,header,key,uploadedFName,fileExtn,appType,docType,props,fiid,instanceId);   
}


function closeFileDialogWindow() {
    if(fileDialogWnd != null) {
        fileDialogWnd.close();
    }
}

function closeFileDialog() {
    var wndHandle = Ext.WindowMgr.get('fileDialogWindow');
    if(wndHandle != null || wndHandle != undefined) {
        wndHandle.close();
    }
}

function setMandatoryHdnValue(key,flag) {
  var el = getMandatoryHdnTxtBox(key);
  if(!Ext.isEmpty(el)) {
      el.setValue(flag);
  }
}

function getMandatoryHdnTxtBox(key){
    return Ext.getCmp('xmlcheck_mandatory_'+key);
}