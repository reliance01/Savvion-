YAHOO.namespace("BM.WaitDialog");
YAHOO.namespace("BM.Logger");


/**
 *  Handles the widgets initialization and interactions
 *  
 *  @param list of FormWidgets
 *
 */

function FormWidgetHandler(allWidgets,options){
   this.allWidgets = allWidgets;
   this.formWidgets = new Array();
   this.sourceDataSlots = new Array();
   this.targetDataSlots = new Array();   
   this.dataslotValues ={};
   this.enableLogger = (YAHOO.lang.isUndefined(options) || 
                         YAHOO.lang.isUndefined(options.enableLogger)) ? false : true;
   this.enableLogger = true;
   // initialize logger..
   if(YAHOO.lang.isUndefined(YAHOO.BM.Logger.enableLogger)){
       YAHOO.BM.Logger = new BM.Portal.LoggerDialog(this.enableLogger);
   }
   if(this.allWidgets.length > 0) 
         this.init();    
};

FormWidgetHandler.constructor = FormWidgetHandler;
FormWidgetHandler.prototype = {   
   init : function(){
      if(this.enableLogger) YAHOO.BM.Logger.info("initializing FormWidgetHandler..."); 
      if(!YAHOO.lang.isUndefined(jmaki))jmaki.debug = false;
      this.waitDialog = new BM.Portal.WaitDialog('/sbm');
      this.waitDialog.show();   
      this.formWidgets = {
          init : function() {
              this.widgetNames = new Array();
              this.sourceDataSlots = {}; 
              this.targetDataSlots = {};        
          },
    
          addFormWidget : function(widgetName, formWidget){
               this[widgetName] = formWidget; 
               this.widgetNames.push(widgetName);               
          },
          
          addSourceDataSlot : function(widgetName,dataSlot){
               this.sourceDataSlots[widgetName] = dataSlot;
          },
          
          addTargetDataSlot : function(widgetName,dataSlot){
               this.targetDataSlots[widgetName] = dataSlot;
          },
          
          hasSourceDataSlot : function(widgetName) {
              if(this[widgetName].getSourceType() == 'DATASLOT' &&
                  !YAHOO.lang.isUndefined(this[widgetName].getSourceDataSlotName()) &&
                  this[widgetName].getSourceDataSlotName().length != 0)return true;
              else return false;
          },
          
          hasTargetDataSlot : function(widgetName) {
              //alert("widgetName:"+widgetName+"\ntargetDs:"+this[widgetName].getTargetDataSlotName());
              if(this[widgetName].getTargetType() == 'DATASLOT' && 
                  !YAHOO.lang.isUndefined(this[widgetName].getTargetDataSlotName()) &&
                  this[widgetName].getTargetDataSlotName().length != 0)return true;
              else return false;
          }
      };
      this.formWidgets.init();
  
      // read all widgets info
      for (var i=0; i<this.allWidgets.length; i++) {           
           var widgetName = this.allWidgets[i]['widget'];
           var editable = this.allWidgets[i]['editable'];
           var widgetType = this.allWidgets[i]['type'];
           var bound = this.allWidgets[i]['bound']; 
           var source = this.allWidgets[i]['source']; 
           var target = this.allWidgets[i]['target'];            
             
           var formWidget = new FormWidget(widgetName,widgetType,editable,source,target);           
           this.formWidgets.addFormWidget(widgetName,formWidget);           
            
	   if(formWidget.getSourceType() == 'DATASLOT' && 
                    (!YAHOO.lang.isUndefined(formWidget.getSourceDataSlotName())) &&
                    (formWidget.getSourceDataSlotName().length !=0)){                    
               var dataSlot = new DataSlot(formWidget.getSourceDataSlotName(), formWidget.getSourceDataSlotType());
               dataSlot.setDataModelType(this.getDataModelTypeForWidget(formWidget.getWidgetType()));
               this.formWidgets.addSourceDataSlot(widgetName,dataSlot);                               
           }
                     
           if(formWidget.getTargetType() == 'DATASLOT' && 
                  (!YAHOO.lang.isUndefined(formWidget.getTargetDataSlotName())) && 
                  (formWidget.getTargetDataSlotName().length !=0)){                                 
               var dataSlot = new DataSlot(formWidget.getTargetDataSlotName(), formWidget.getTargetDataSlotType());
               dataSlot.setDataModelType(this.getDataModelTypeForWidget(formWidget.getWidgetType()));
               this.formWidgets.addTargetDataSlot(widgetName,dataSlot);
	       if(dataSlot.getDataModelType() == 'combobox')
		    this.formWidgets.addSourceDataSlot(widgetName,dataSlot);                     
           }                  
           
           try {              
                 var widget = jmaki.getWidget(widgetName);
                 if(!YAHOO.lang.isUndefined(widget)) formWidget.setWidget(widget);           
                 this.log("Adding to form widgets :",formWidget);      
                 this.formWidgets[this.formWidgets.length] = formWidget;                 
             
           }catch(e){
               if(this.enableLogger) YAHOO.BM.Logger.error("initialization failed at FormWidgetHandler:"+e);               
           }           
      }
      if(this.allWidgets.length > 0) 
         this.initializeWidgets();      
   },
   
   initializeWidgets : function(){
      var param = '';
      for(var j=0; j<this.sourceDataSlots.length;j++){
           param += this.sourceDataSlots[j].getName();
           if(j != (this.sourceDataSlots.length-1)) param += ','
      } 
            
   
      if(this.formWidgets.widgetNames.length > 0) 
           this.getDataSlots(); 
      else 
      {
          this.waitDialog.hide();
          return null;
      }
   }
   
   
};

FormWidgetHandler.prototype.getDataSlots = function(){
      var messages = new Array();
      var handler = this;
      function getDataSlotsCallback(_req){         
          var retJsonValues =YAHOO.lang.JSON.parse(_req.responseText);          
          var dataSlotValues = retJsonValues['dataSlots'];  
          for(var j=0; j<handler.formWidgets.widgetNames.length;j++) 
          {
            try 
            {
                   var widgetName = handler.formWidgets.widgetNames[j];
                   if(handler.formWidgets.hasSourceDataSlot(widgetName) || handler.formWidgets.hasTargetDataSlot(widgetName)) {                                
                         var formWidget = handler.formWidgets[widgetName];
                         var widget = formWidget.getWidget();
                         if(handler.formWidgets.hasSourceDataSlot(widgetName)) {
                              var dataSlot = handler.formWidgets.sourceDataSlots[widgetName];    
                              if(!YAHOO.lang.isUndefined(dataSlot))
                                formWidget.getWidget().setValue(dataSlotValues[dataSlot.getName()]);
                         }
                         
                         if(handler.formWidgets.hasTargetDataSlot(widgetName)) {
                              var dataSlot = handler.formWidgets.targetDataSlots[widgetName];    
                              if(!YAHOO.lang.isUndefined(dataSlot))  {
                                  if(formWidget.getWidgetType() == 'sbm.list' || 
                                     formWidget.getWidgetType() == 'sbm.radio' || 
                                     formWidget.getWidgetType() == 'sbm.checkbox' ||
		        	     formWidget.getWidgetType() == 'sbm.radio' ||
                   		     formWidget.getWidgetType() == 'sbm.combobox') 
					       formWidget.getWidget().setSelectedValues(dataSlotValues[dataSlot.getName()]);                              
			      }
                         }
                   }
             }
             catch(e)
             {
                  alert(e);
                  messages[messages.length] = "-error while initializing '"+widgetName+"' widget:\n"+"<span style='color:red'>"+e
                    +"</span>";
              }
          }
          handler.hideWaitDialog();
          if(messages.length > 0) handler.showError(messages);          
      }      
	 
      // prepare the dataslots
      var sourceDataSlots = new Array();
      for(var i=0; i<this.formWidgets.widgetNames.length; i++){
          var widgetName = this.formWidgets.widgetNames[i];
          var formWidget = this.formWidgets[widgetName];
          //alert("widgetName:"+widgetName+" ,formWidget:"+formWidget);
          if(this.formWidgets.hasSourceDataSlot(widgetName))
              if(this.formWidgets.sourceDataSlots[widgetName] != null) 
                sourceDataSlots[sourceDataSlots.length] = this.formWidgets.sourceDataSlots[widgetName];
          if(this.formWidgets.hasTargetDataSlot(widgetName) && !this.formWidgets.hasSourceDataSlot(widgetName))
              if(this.formWidgets.targetDataSlots[widgetName] != null)
                sourceDataSlots[sourceDataSlots.length] = this.formWidgets.targetDataSlots[widgetName]; 
      }    
      
      // perpare post data for save dataslots ajax call...
      var postData = {
          commandAction: 'getdataslots',
          dataSlots: YAHOO.lang.JSON.stringify(sourceDataSlots)};
      if(this.enableLogger) YAHOO.BM.Logger.debug("retrieving the values for :"+YAHOO.lang.JSON.stringify(sourceDataSlots)); 
      // do ajax call..      
      this.doAjax("/sbm/bpmportal/common/forms/getdataslots.form",postData,getDataSlotsCallback);    
}  
  
FormWidgetHandler.prototype.saveDataSlots = function(){
    if(this.enableLogger) YAHOO.BM.Logger.info("saveDataSlots hs been invoked...");     
    if(this.formWidgets.widgetNames.length == 0) {
        this.waitDialog.hide();
        return null;
    }
    
    var param = '';	
    var dataSlots = new Array();
    var errorMessages = new Array();
    for(var j=0; j<this.formWidgets.widgetNames.length;j++) 
    {
            try 
            {
                var widgetName = this.formWidgets.widgetNames[j];
                param += widgetName;
                if(j != (this.formWidgets.widgetNames.length-1)) param += ','
                
                if(this.formWidgets.hasTargetDataSlot(widgetName)) {
                    var formWidget = this.formWidgets[widgetName];
                    if(!YAHOO.lang.isUndefined(formWidget)) {
                         if(this.enableLogger) YAHOO.BM.Logger.info("-widgetName: "+formWidget.getWidgetName()+
                          ", targetDataSlot:"+formWidget.getTargetDataSlotName());
                         var dataslot = {};
                         if(formWidget.getTargetDataSlotName().length != 0) {
                              dataslot['name'] = formWidget.getTargetDataSlotName();
                              if(formWidget.getWidgetType() == 'sbm.list' || 
                                 formWidget.getWidgetType() == 'sbm.radio' || 
                                 formWidget.getWidgetType() == 'sbm.checkbox' ||
				 formWidget.getWidgetType() == 'sbm.radio' ||
				 formWidget.getWidgetType() == 'sbm.combobox') {
                                 try{
                                     if(formWidget.getWidget().getSelectedValues) 
                                         dataslot['selections'] = formWidget.getWidget().getSelectedValues(); 
                                 }catch(e){
                                     alert(e);
                                 }
                              }
                              else
                              {
                                 try
                                 {
                                      if(!YAHOO.lang.isUndefined(formWidget.getWidget().getValue()))
                                         dataslot['jsonValue'] = formWidget.getWidget().getValue();
                                      dataslot['dataModelType'] = this.getDataModelTypeForWidget(formWidget.getWidgetType());
                                      
                                 }catch(e){
                                     alert(e)
                                 }
                                 
                              }
                              dataSlots[dataSlots.length] = dataslot;
                              
                         }
                    }
                    
                } 
                
            }
            catch(e)
            { 
                 errorMessages.push(widgetName + " -error:"+e);                              
            }
    }
    
    function saveDataSlotsCallback(){
        document.forms[0].submit();        
    }
        
    var handleNo = function(){
       this.hide();
    }
    
    var handleYes = function(){
         if(handler.enableLogger) YAHOO.BM.Logger.debug("DoAjax for saving following dataslots :\n"+YAHOO.lang.JSON.stringify(dataSlots)); 
         // perpare post data for save dataslots ajax call...
         var postData = {
           commandAction: 'savedataslots',
           dataSlots :YAHOO.lang.JSON.stringify(dataSlots)};         
    
        if(dataSlots.length > 0)     
           handler.doAjax("/sbm/bpmportal/common/forms/savedataslots.form",postData,saveDataSlotsCallback); 
    }
    var handler = this;        
    if(errorMessages.length > 0) {
       var message = "<table><tr><td><span style='font-weight:bold;'>Error occured while getting value of following widgets,\n Do you want to continue?\n</span></td></tr>";    
       for(var i=0; i< errorMessages.length;i++){
          message += ("<tr><td>"+errorMessages[i]+"\n</td></tr>");
       } 
       message += "</table>";
       var alertDialog = new YAHOO.widget.SimpleDialog("AlertDialog", 
        { width: "450px", fixedcenter: false, visible: true, draggable: true, modal:true,
          close: true, text: message,
          icon: YAHOO.widget.SimpleDialog.ICON_HELP, constraintoviewport: true,
          buttons: [ { text:"Yes", handler:handleYes,obj: [handler],override:true, isDefault:true },
                     { text:"No",  handler:handleNo } ]});
            alertDialog.setHeader("Error Ocurred!");
            alertDialog.setBody(message);
            alertDialog.render('container');
            alertDialog.show();
            alertDialog.center();
            YAHOO.util.Dom.setStyle('container','display','block');         
    } 
    else
    {
         //if(this.enableLogger) YAHOO.BM.Logger.debug("DoAjax for saving following dataslots :\n"+YAHOO.lang.JSON.stringify(dataSlots)); 
         // perpare post data for save dataslots ajax call...
         var postData = {
           commandAction: 'savedataslots',
           dataSlots :YAHOO.lang.JSON.stringify(dataSlots)};         
         if(dataSlots.length > 0)     
           this.doAjax("/sbm/bpmportal/common/forms/savedataslots.form",postData,saveDataSlotsCallback);
   }
}

FormWidgetHandler.prototype.doAjax = function(url,params,callback){
    var handler = this;
    function handleError(msg,_req){//responseText
        handler.hideWaitDialog();
        var resizableDialog = new ResizableDialog();
		var exception = YAHOO.lang.JSON.parse(_req.responseText);  
        var body = ""
        body += "<table><tr><td>"+msg+"</td></tr><tr><td>";
        body +=     " +";        
        body += "</td></tr></table>";
        resizableDialog.setBody(body);
	resizableDialog.show();
	YAHOO.util.Dom.setStyle('resizablepanel','display','block');       
    }   
    jmaki.doAjax({ url : url,method : 'POST',asynchronous: false, content : params, callback: callback, onerror: handleError });      
}

FormWidgetHandler.prototype.showError = function(messages){     
     this.waitDialog.hide();
     var body = ""
     var rows = "";
     for(var i=0; i<messages.length; i++) rows += "<tr><td>"+messages[i]+"</td></tr>";
     body += "<table><tr><td>Initialization of following widgets has been failed:</td></tr>" + rows + "</table>" 
     var resizableDialog = new ResizableDialog();
     resizableDialog.setBody(body);
     resizableDialog.show();
     YAHOO.util.Dom.setStyle('resizablepanel','display','block'); 
	 
}


FormWidgetHandler.prototype.getDataModelTypeForWidget = function(widgetType){
    if(widgetType == 'sbm.textfield' || sbm.checkbox == 'sbm.checkbox' || widgetType == "sbm.textarea")
          return 'none';
    else if(widgetType == "sbm.list" || widgetType == "sbm.combobox" || widgetType == "sbm.checkbox")
          return 'combobox';
     else if(widgetType == "dojo.table")
          return 'table';  
    
}
    
FormWidgetHandler.prototype.findWidgetByDataSlotName = function(dataSlotName,type){
    for(var i=0; i<this.formWidgets.length;i++){
        if(type == 'source' && this.formWidgets[i].getSourceDataSlotName() == dataSlotName){
            return this.formWidgets[i].getWidget();
        } 
		if(type == 'target' && this.formWidgets[i].getTargetDataSlotName() == dataSlotName){
            return this.formWidgets[i].getWidget();s
        } 
		
    }    
} 

FormWidgetHandler.prototype.findWidgetByName = function(widgetName){
    if(YAHOO.lang.isUndefined(this.formWidgets[widgetName])) return;
    return this.formWidgets[widgetName].getWidget();
}

FormWidgetHandler.prototype.findFormWidgetByName = function(widgetName){
    if(YAHOO.lang.isUndefined(this.formWidgets[widgetName])) return;
    return this.formWidgets[widgetName];
}

FormWidgetHandler.prototype.validateWidgets = function(){
  if(typeof Spry != 'undefined') {
	  if(this.enableLogger) {
		 var widgetQueue = Spry.Widget.Form.onSubmitWidgetQueue;
		 for (var i = 0; i < widgetQueue.length; i++) { 
			 var spanId = widgetQueue[i].element.getAttribute('id');
			 var widgetName = spanId.substring(0,spanId.length-4);
			 if(!widgetQueue[i].isDisabled())
				 YAHOO.BM.Logger.info("validating '"+widgetName+"' is :"+widgetQueue[i].validate());
		 }
	  }
	  return Spry.Widget.Form.validate(document.forms[0]);  
  
  } else return true;
}

FormWidgetHandler.prototype.hideWaitDialog = function(){
    this.waitDialog.hide();    
}

FormWidgetHandler.getInstance = function() {
	return formWidgetHandler;
}

FormWidgetHandler.prototype.checkForSpecialCharacters = function(value) {    
    var specialChars =["<",">","\"","'","%",";","(",")","&","+","\\",
           "<",">","\"","'","%",";","&","+","\\"];
}
 
FormWidgetHandler.prototype.escapeIllegalCharacters = function(str){
	str = str.replace("&","&amp;");
	str = str.replace("<","&lt;");
	str = str.replace(">","&gt;");
	str = str.replace('"',"&quote");
        str = str.replace("'","\''");
	str = str.replace("&nbsp;"," ");
	return str;
}

FormWidgetHandler.prototype.log = function(message,formWidget){
    if(this.enableLogger){
       message += "widgetName="+formWidget.getWidgetName();
       message += ", widgetType="+formWidget.getWidgetType();
       if(formWidget.getSourceType() == 'DATASLOT')
         message += ", source: dataSlotName="+formWidget.getSourceDataSlotName()+", dataSlotType:"+formWidget.getSourceDataSlotType();
       if(formWidget.getTargetType() == 'DATASLOT')
         message += ", target: dataSlotName="+formWidget.getTargetDataSlotName()+", dataSlotType:"+formWidget.getTargetDataSlotType();
       YAHOO.BM.Logger.debug(message);   
    }
}

  
function DataSlot(name, type, dataModelType){
    this.name = YAHOO.lang.isUndefined(name) ? "" : name;
    this.type = YAHOO.lang.isUndefined(type) ? "" : type;
    this.dataModelType = YAHOO.lang.isUndefined(dataModelType) ? "none" : dataModelType;
}

DataSlot.constructor = DataSlot;
DataSlot.prototype = {	
    getName: function() {
        return this.name;
    },
    
    getType: function() {
        return this.type;
    },

    getDataModelType: function() {
        return this.dataModelType;
    },

    setDataModelType: function(dataModelType) {
        this.dataModelType = dataModelType;
    }  
    
}     


function Choice(label,value,selected){
    this.label = YAHOO.lang.isUndefined(label) ? "" : label;
    this.value = YAHOO.lang.isUndefined(value) ? "" : value;
    this.selected = YAHOO.lang.isUndefined(selected) ? false : selected;
}

Choice.constructor = Choice;
Choice.prototype = {	
    getLabel: function() {
        return this.name;
    },
    
    getValue: function() {
        return this.value;
    },

    isSelected: function() {
        return this.selected;
    }    
}     












