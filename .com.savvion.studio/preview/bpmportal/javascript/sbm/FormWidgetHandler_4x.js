YAHOO.namespace("BM.WaitDialog");
YAHOO.namespace("BM.Logger");
if(!Bm) Bm = {};
Bm.form = Bm.form || {};
Ext.namespace("BM.widget");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 *  Form Cach Singleton
 */
Bm.form.Cache = {
	singleton: true,
	alias: 'form.cache',
	flag: false,	
	widgetsMap: new Object(),	
	
	configure: function(){		
		for (var i = 0; i < allWidgets.length; i++) {			
			if (typeof allWidgets[i]['widget'] != 'undefined') {
				var key = allWidgets[i]['widget'];
				if(typeof key != 'undefined' && key.length != 0)
				this.widgetsMap[key] = allWidgets[i]['type'];
			}			
		}
		this.flag = true;
	},
	
	getWidgetType: function(widgetName){		
		if (!this.flag) this.configure();
		return this.widgetsMap[widgetName];
	},
	
	getFiid: function(){
		return (document.getElementById('fiid') != null) ? document.getElementById('fiid').value : '';
	},
	
	getDateFormat: function(){
		return _dateFormats.date;
		
	},
	
	getDateOnlyFormat: function(){
		return _dateFormats.dateOnly;
		
	},
	
	getWidgetMap: function(){
		if (!this.flag) this.configure();
		return this.widgetsMap;
	},

    isWidget: function(widgetName) {
		if (!this.flag) this.configure();
		if (YAHOO.lang.isUndefined(this.widgetsMap[widgetName])) {
			return false;
		}
		else {			 
			return true;
		}
	},
	
	focusOnWidget: function(id, isExt){
		if(isExt) {
			Ext.getCmp(id).focus();
		}else {
			var el = document.getElementById(id);
			if(typeof el.fields != 'undefined' ) el = el.fields[0];
            if(typeof el.select != 'undefined' ) el.select();
            if(typeof el.length != 'undefined' ) el = el[0];
			if( el.type != "hidden" && el.style.display != "none"  && !el.disabled ) {  
    	         el.focus();  
    	         return;  
	      	}
		}
	}
}; 
//////////////////////////////////////////////////////////////////////////////////////////

/**
 *  Handles the widgets initialization and interactions
 *  
 *  @param list of FormWidgets
 *
 */

Ext.define('FormWidgetHandler',{
    extend:'Ext.util.Observable',
	constructor: function(allWidgets, onload_function, options, fiid){
		this.xtype = 'formwidget_handler';
		this.onload_function= onload_function;
		this.fiid = fiid;
		this.allWidgets = allWidgets;	          
		if(!YAHOO.lang.isUndefined(options)) {
			this.adapletCache = options['adapletCache'];
			this.processName = options['processName'];
			this.processType = options['processType'];
			this.maxMultiLineLength = options['maxMultiLineLength'];
    	}
   		this.formWidgets = new Array();
   		this.sourceDataSlots = new Array();
   		this.targetDataSlots = new Array();
   		//this.observer = new FormWidgetObserver({id:this.path});   
   		this.dataslotValues ={};
   		this.enableLogger = (YAHOO.lang.isUndefined(options) || 
                         YAHOO.lang.isUndefined(options.enableLogger)) ? false : true;
   		this.enableLogger = false;
   		// initialize logger..
   		if(this.enableLogger){   	
       		YAHOO.BM.Logger = new BM.Portal.LoggerDialog(this.enableLogger);
   		}
		FormWidgetHandler.superclass.constructor.call(this);
		this.initEvents();
   		if(this.allWidgets.length > 0)this.init();
   		else this.fireEvent('onLoadBody');
		this.saveAnyWay = false;
   		var dataSlotValuesCache;
		
	},

    init : function(){
      if(this.enableLogger) YAHOO.BM.Logger.info("initializing FormWidgetHandler...");          
      this.formWidgets = {
          init : function() {
              this.widgetNames = new Array();
              this.sourceDataSlots = {}; 
              this.targetDataSlots = {};   
			  this.adaplets ={};     
          },
    
          addFormWidget : function(widgetName, formWidget){
               this[widgetName] = formWidget; 
               this.widgetNames.push(widgetName);               
          },
          
          addSourceDataSlot : function(widgetName,dataSlot){
               this.sourceDataSlots[widgetName] = dataSlot;
          },
		  
		  addAdaplet : function(widgetName,adapletConfig){
               this.adaplets[widgetName] = adapletConfig;
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
               if(widgetType == 'sbm.combobox' && formWidget.getTargetDataSlotType() == 'STRING')
			       dataSlot.setDataModelType('none');
			   else	   
			       dataSlot.setDataModelType(this.getDataModelTypeForWidget(formWidget.getWidgetType()),formWidget.getTargetDataSlotType(),(formWidget.getTargetDataSlotName()!= formWidget.getSourceDataSlotName()));
               this.formWidgets.addTargetDataSlot(widgetName,dataSlot);
	           //if(dataSlot.getDataModelType() == 'combobox' && (formWidget.getSourceDataSlotName() == formWidget.getTargetDataSlotName()))
		         //this.formWidgets.addSourceDataSlot(widgetName,dataSlot);                     
           }
		   
		   if(formWidget.getSourceType() == 'ADAPLET') {
			   this.formWidgets.addAdaplet(widgetName,source);		   	
		   }                      
           
           try {              
                 var widget = jmaki.getWidget(widgetName);
                 if(!YAHOO.lang.isUndefined(widget)) formWidget.setWidget(widget); 
				
				 if(formWidget.getSourceDataSlotType() == 'BOOLEAN' || formWidget.getTargetDataSlotType() == 'BOOLEAN'){				 	  
				 	  widget.setBindingType('BOOLEAN');
				 }
				           
                 this.log("Adding to form widgets :",formWidget);      
                 this.formWidgets[this.formWidgets.length] = formWidget;                 
             
           }catch(e){
               if(this.enableLogger) YAHOO.BM.Logger.error("initialization failed at FormWidgetHandler:"+e);               
           }           
      }
	  
	  
      if(this.allWidgets.length > 0) 
         this.initializeWidgets();      
   },
   
   initEvents : function(){
      this.on('onLoadData', this.onLoadData, this);      
	  this.on('setValues', this.setValues, this);	
	  this.on('resetValues', this.resetValues, this);	
	  this.on('onLoadBody', this.invokeOnLoadFunctions);	
   },
   
   initializeWidgets : function(){
      var param = '';
      for(var j=0; j<this.sourceDataSlots.length;j++){
           param += this.sourceDataSlots[j].getName();
           if(j != (this.sourceDataSlots.length-1)) param += ','
      } 
	  
	  //alert(YAHOO.lang.JSON.stringify(this.formWidgets.adaplets));
	  // initialize widgets
	  for (var key in this.formWidgets.adaplets){
	  	  var temp =this.formWidgets.adaplets[key];		  
		  if(typeof this.formWidgets[key] == 'undefined')
	  	     alert("Can not find widget type for '"+key+"' widget.");
		  if(this.formWidgets[key].getWidgetType() != 'sbm.textfield' && this.formWidgets[key].getWidgetType() != 'sbm.textarea' &&
		     this.formWidgets[key].getWidgetType() != 'sbm.combobox')
		  this.invokeAdaplet(key,this.formWidgets[key].getWidgetType(),temp['path'],temp['name']);		 
	  }            
      
      if (this.formWidgets.widgetNames.length > 0) {
	  	  this.fireEvent('onLoadData'); 
		  //var task = new Ext.util.DelayedTask({fn: this.getDataSlots(), scope:this});
          //task.delay(1000);         
		  //this.getDataSlots();
	  }
	  else {	  	
	  	return null;
	  }
   },  
   
   invokeAdaplet: function(elementId,type,adapletPath,outputName){
   	   //alert("elementId:"+elementId+"\nadapletPath:"+adapletPath+"\noutputName:"+outputName);
	   var adapletClassName = this.adapletCache[adapletPath];	   
	   var stringUrl = "'"+self.location+"'";
	   var processType = (stringUrl.indexOf('/BizSolo/') >= 0) ? "BizSolo" : "";	       	
	   var processContextMap = {'_P_TYPE':processType,'_PT_NAME':this.processName,'_WS_NAME':adapletPath,'_CLASS_NAME':adapletClassName};
	   //alert(YAHOO.lang.JSON.stringify(processContextMap));
       var inputDataslotsMap = {};	
	   var adapletOutput = this.adapletOutputName;   
	   var handler = this;
	   adapterDWR.executeAdapter(processContextMap, inputDataslotsMap, function(map){
      		  if(map['exception'] != undefined){
        			alert("Exception in adapter execution..."+map['exception']);
      		  } else {
        			var values = map[outputName];
					if(this.enableLogger) YAHOO.BM.Logger.debug("Adaplet output '"+outputName+"' is:"+YAHOO.lang.JSON.stringify(map[outputName])); 
					if (type == 'sbm.checkbox' || type == 'sbm.list' || type == 'sbm.combobox') {
						handler.convertAdapletListToDataModel(elementId,map[outputName]);							
						
					} else if (type == 'sbm.radio') {						
						var content = '';
						var parent = document.getElementById(elementId + '_div');
						for (var i = 0; i < values.length; i++) {
								var radio = '<input type="radio" class="ApInptRadio" name="' + elementId + '" value="' + values[i] + '" id="' + values[i] + '">' + values[i] + '<br>\n';
								content += radio;
						}
						parent.innerHTML = content;
						//sbm.utils.addData(elementId,'radio',map[adapletOutput]);*/     
					}
			  }
    	});
   },
   
   invokeOnLoadFunctions: function(){
   	   // call body onload function
	   try{
	   	  if(this.onload_function != null)
		  this.onload_function.call();
	   }catch(e){}
   },
   
   
   isUndefined: function(_v){
   	    return YAHOO.lang.isUndefined(_v);
   },
   
   convertAdapletListToDataModel: function(widgetName,data){
   	     if(this.enableLogger) YAHOO.BM.Logger.debug("converting to adaplet data model for '"+widgetName+"' widget:"+data); 
		 var widget = this.formWidgets[widgetName].getWidget();
		 convertedData = new Array();
	     if(typeof data != 'object') 
		 		data = eval(data);
		 for(var i = 0; i < data.length; i++) {
		     convertedData[convertedData.length] = {label: data[i],value: data[i]};
		 }		
		  	
		 widget.setValue(YAHOO.lang.JSON.stringify(convertedData)); 
		 /*var formWidget = this.formWidgets[widgetName]; 
		 if(this.enableLogger) YAHOO.BM.Logger.debug("dataSlotValuesCache:"+dataSlotValuesCache); 
		 if (this.formWidgets.hasTargetDataSlot(widgetName) && typeof dataSlotValuesCache != 'undefined') {
		 	var dataSlot = this.formWidgets.targetDataSlots[widgetName];
			selectedValues = new Array();
			
			if(dataSlot.getType() == 'STRING')
			      selectedValues[selectedValues.length] = dataSlotValuesCache[dataSlot.getName()];
		 	else if(dataSlot.getType() == 'LIST') {				  
				  selectedValues = YAHOO.lang.JSON.parse(dataSlotValuesCache[dataSlot.getName()]);
			}			
			widget.setSelectedValues(YAHOO.lang.JSON.stringify(selectedValues));
			
		 }*/	
   },   
   
   convertDataModelIfNecessary: function(type,formWidget,data){   	   
		var convertedData = data;
		if(type == 'source'){
			 if ((formWidget.getWidgetType() == 'sbm.combobox' || formWidget.getWidgetType() == 'sbm.radio' ||
			      formWidget.getWidgetType() == 'sbm.checkbox' || formWidget.getWidgetType() == 'sbm.list') &&
			 	  formWidget.getSourceDataSlotType() == 'LIST') {
			 	
				   convertedData = new Array();
			 	   if(typeof _v != 'object') 
			 		data = eval(data);
			 	   for(var i = 0; i < data.length; i++) {
			 		   convertedData[convertedData.length] = {label: data[i],value: data[i]};
			 		
			 	   }			 	
				   return YAHOO.lang.JSON.stringify(convertedData);
					
			 }else if((formWidget.getWidgetType() == 'sbm.combobox' ||
					formWidget.getWidgetType() == 'sbm.radio' ||
					formWidget.getWidgetType() == 'sbm.checkbox' ||
					formWidget.getWidgetType() == 'sbm.list') &&
					formWidget.getSourceDataSlotType() == 'MAP') {
					  	convertedData = new Array();
			 			if (typeof _v != 'object') 
			 			map = eval(data);	
						for(var key in map){
							//convertedData[convertedData.length] = {label: map[key],value: key};
							convertedData[convertedData.length] = {label: key,value: map[key]};
							
						}		 			
					    return YAHOO.lang.JSON.stringify(convertedData);
					
			  }
			  
			
		}
   	    return convertedData;
		
   },
   
   getBindingDataSlots: function(){
   	  // prepare the dataslots
      var dataSlots = new Array();
      for(var i=0; i<this.formWidgets.widgetNames.length; i++){
          var widgetName = this.formWidgets.widgetNames[i];
          var formWidget = this.formWidgets[widgetName];
          // find source dataslots 
		  if(this.formWidgets.hasSourceDataSlot(widgetName) &&
		      this.formWidgets.sourceDataSlots[widgetName] != null) {
		  	    dataSlots[dataSlots.length] = this.formWidgets.sourceDataSlots[widgetName];		  	
		  }		  
		  // find target dataslots
		  if(this.formWidgets.hasTargetDataSlot(widgetName) && 
		       (this.formWidgets.targetDataSlots[widgetName] != null) &&
			   (formWidget.getSourceDataSlotName() != formWidget.getTargetDataSlotName())) {
		  		  		dataSlots[dataSlots.length] = this.formWidgets.targetDataSlots[widgetName];		  
		  } 
      }
	  return dataSlots;
   },
   
   
   setProcessName: function(processName){
   	    this.processName = processName;
   },
   
   setDataSlotValues: function(dataSlotValues){
   	   this.dataSlotValues = dataSlotValues;
   },
   
   getDataSlotValues: function(){
   	   return this.dataSlotValues;
   },
   
   onLoadData: function(handler){ 
       var dataSlots = this.getBindingDataSlots();
	   if(this.enableLogger) YAHOO.BM.Logger.debug("retrieving the values for :"+YAHOO.lang.JSON.stringify(dataSlots)); 
	   var conn = new Ext.data.Connection();
	   conn.request({
             url: getContextPath()+'/bpmportal/common/forms/getdataslots.form',
			 method: 'POST',
			 scope: this,
			 params: {
				 fiid: this.fiid,
                 commandAction: 'getdataslots',
	  		     dataSlots: YAHOO.lang.JSON.stringify(dataSlots)
		     },
			 success: function(req,opt){			
			     if(this.enableLogger) YAHOO.BM.Logger.debug(" Response from server:"+req.responseText);     			  
			     var retJsonValues =YAHOO.lang.JSON.parse(req.responseText);		 
				 this.retJsonValuesCache = retJsonValues;
				 this.setValues(retJsonValues);		 
			 },
             failure: function(req,opt){
                 Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");				 
			 }

	   });
   },  
   
   resetValues: function(){
   	  this.fireEvent('setValues',this.retJsonValuesCache);	
   }, 
   
   setValues: function(retJsonValues){   	   
   	   var dataSlotValues = retJsonValues['dataSlots'];
	   var messages = new Array();
   	   this.setDataSlotValues(dataSlotValues);
   	   
	   for(var j=0; j < this.formWidgets.widgetNames.length; j++) {
   		    try
			{	   
				   var widgetName = this.formWidgets.widgetNames[j];				   
				   if(this.formWidgets.hasSourceDataSlot(widgetName) || this.formWidgets.hasTargetDataSlot(widgetName)) {                                
                         var formWidget = this.formWidgets[widgetName];
						 var widget = formWidget.getWidget();
                          // add default validation rule for specific data bindings						 
				   		  if(formWidget.getTargetDataSlotType() == 'URL' && formWidget.getWidget().addUrlValidation){				   	 			
					 			formWidget.getWidget().addUrlValidation();
				   		  }	
						  // add default validation rule for specific data bindings						 
				   		  if((formWidget.getTargetDataSlotType() == 'NUMBER' || formWidget.getTargetDataSlotType() == 'LONG') && typeof formWidget.getWidget().getValidationType != 'undefined' && formWidget.getWidget().getValidationType() == 'none'){						  				   	 			
					 			formWidget.getWidget().addNumberValidation();
				   		  }
						  if((formWidget.getTargetDataSlotType() == 'DOUBLE' || formWidget.getTargetDataSlotType() == 'BIGDECIMAL' || 
						       formWidget.getTargetDataSlotType() == 'DECIMAL')
							    && formWidget.getWidget().getValidationType() == 'none'){				   	 			
					 			formWidget.getWidget().addDecimalValidation();
				   		  }	
						  if(formWidget.getSourceDataSlotName() == 'STARTTIME' ){						  	  
							  try {
							  	var dataSlot = this.formWidgets.sourceDataSlots[widgetName];
							  	var _v = dataSlotValues[dataSlot.getName()];
							  	var _date = new Date(_v);
							  	dataSlotValues[dataSlot.getName()] = Ext.util.Format.date(_date, _dateFormats.date);
							  }catch(e){}							
						  }
						  if(formWidget.getWidgetType() == 'sbm.textarea' && this.processType == 'bizlogic' && formWidget.getWidget().getValidationType() == 'none'){
						  	  formWidget.getWidget().addMaxLengthValidation(this.maxMultiLineLength);							
						  }							 
						 
						 //has source
						 if(this.formWidgets.hasSourceDataSlot(widgetName)) {
                               var dataSlot = this.formWidgets.sourceDataSlots[widgetName];							     
                               if(this.enableLogger) YAHOO.BM.Logger.debug(" Setting the source of '"+widgetName+"' dataslot:"+YAHOO.lang.JSON.stringify(dataSlot));     
							   if(this.enableLogger) YAHOO.BM.Logger.debug(" Setting the value for '"+widgetName+"' dataslot:"+dataSlotValues[dataSlot.getName()]);     
							   if(!YAHOO.lang.isUndefined(dataSlot) && !YAHOO.lang.isUndefined(dataSlotValues[dataSlot.getName()])) {										
										/**		
	 									*  If setValue is defined for widget then
	 									*/									
										if (formWidget.getWidget().setValue) {
											if(this.enableLogger) YAHOO.BM.Logger.debug(" Setting the value for '"+widgetName+"' dataslot:"+dataSlotValues[dataSlot.getName()]);     
											/*
											 *  if (formWidget.getSourceDataSlotType() == 'MAP') {
										       var convertedData = new Array();
											   if (typeof dataSlotValues[dataSlot.getName()] != undefined) {
													map = eval(dataSlotValues[dataSlot.getName()]);
													for (var key in map) {
														convertedData.push({label:key,value:map[key]});
													}
													
													formWidget.getWidget().setValue(YAHOO.lang.JSON.stringify(convertedData));
												}
										}
										else 
											 * */
											
											
											if(formWidget.getSourceDataSlotType() == 'MAP'){
												 var convertedData = new Array();
											   if (typeof dataSlotValues[dataSlot.getName()] != undefined) {
													map = eval(dataSlotValues[dataSlot.getName()]);
													for (var key in map) {
														convertedData.push({label:key,value:map[key]});
													}
													
													formWidget.getWidget().setValue(YAHOO.lang.JSON.stringify(convertedData));
												}
											}else
											formWidget.getWidget().setValue(this.convertDataModelIfNecessary('source', formWidget, dataSlotValues[dataSlot.getName()]));
										}
								}		
                          }						  
                          //has target
                          if(this.formWidgets.hasTargetDataSlot(widgetName)) {
                              var dataSlot = this.formWidgets.targetDataSlots[widgetName];    
                              if(this.enableLogger) YAHOO.BM.Logger.debug(" Setting the target of '"+widgetName+"' dataslot:"+YAHOO.lang.JSON.stringify(dataSlot));
							  if(this.enableLogger) YAHOO.BM.Logger.debug(" Setting the value for '"+widgetName+"' dataslot:"+dataSlotValues[dataSlot.getName()]);     
							  if(!YAHOO.lang.isUndefined(dataSlot) && formWidget.getTargetDataSlotType() != 'STRING' && formWidget.getTargetDataSlotType() != 'BOOLEAN')  {
                                  if(formWidget.getWidgetType() == 'sbm.list' || 
                                     formWidget.getWidgetType() == 'sbm.radio' || 
                                     formWidget.getWidgetType() == 'sbm.checkbox' ||
		        	                 formWidget.getWidgetType() == 'sbm.radio' ||
                   		             formWidget.getWidgetType() == 'sbm.combobox'){		
									    
									    if (formWidget.getTargetDataSlotType() == 'MAP' && typeof dataSlotValues[dataSlot.getName()] != 'undefined') {
										     
											var map;
											var convertedData = new Array();
											if (typeof dataSlotValues[dataSlot.getName()] != undefined) {
												map = eval(dataSlotValues[dataSlot.getName()]);
												for (var key in map) {
													convertedData[convertedData.length] = key;
												}
												
												if (convertedData.length > 0) 
													formWidget.getWidget().setSelectedValues(YAHOO.lang.JSON.stringify(convertedData));
											}
											
										}
										else {
											
											formWidget.getWidget().setSelectedValues(dataSlotValues[dataSlot.getName()]);
										}
				     			   }
			                  }
			      				// for business objects property mapped to combobox
			      			  if(!YAHOO.lang.isUndefined(dataSlot) && dataSlot.getType() == 'STRING' && 
				            		formWidget.getWidgetType() == 'sbm.combobox' &&
			                		formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()){
				      				// set selected values 
									formWidget.getWidget().setSelectedValue(dataSlotValues[dataSlot.getName()]);
			      			
							}else if(!YAHOO.lang.isUndefined(dataSlot) && dataSlot.getType() == 'STRING' && 
				            		formWidget.getWidgetType() == 'sbm.radio' && 
									formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()){
				      				// set selected values
									formWidget.getWidget().setSelectedValue(dataSlotValues[dataSlot.getName()]);
			                }else if(!YAHOO.lang.isUndefined(dataSlot) && dataSlot.getType() == 'BOOLEAN' && 
				            		// ABL logical data binding
									formWidget.getWidgetType() == 'sbm.checkbox'){
				      				// set selected values									
									formWidget.getWidget().setValue(dataSlotValues[dataSlot.getName()]);
			                }    
				      
                         }
                   }
						
			}catch (e) {
				alert(e);
				messages[messages.length] = "-error while initializing '" + widgetName + "' widget:\n" + "<span style='color:red'>" + e +"</span>";
			}
		}
		this.fireEvent('onLoadBody');
	
	}
   
   
});

FormWidgetHandler.prototype.getDataSlots = function(){
      var messages = new Array();
      var handler = this;
      function getDataSlotsCallback(_req){         
          var retJsonValues =YAHOO.lang.JSON.parse(_req.responseText);          
          if(handler.enableLogger) YAHOO.BM.Logger.debug(" Response from server:"+YAHOO.lang.JSON.stringify(retJsonValues));     
		  var dataSlotValues = retJsonValues['dataSlots'];  
		  dataSlotValuesCache = dataSlotValues;
          for(var j=0; j<handler.formWidgets.widgetNames.length;j++) 
          {
            try 
            {
                   /*
                    * The logic of dataslot mapping
                    * source:
                    * target:
                    */
				   var widgetName = handler.formWidgets.widgetNames[j];
				   if(handler.formWidgets.hasSourceDataSlot(widgetName) || handler.formWidgets.hasTargetDataSlot(widgetName)) {                                
                         var formWidget = handler.formWidgets[widgetName];
						 var widget = formWidget.getWidget();
                         //has source
						 if(handler.formWidgets.hasSourceDataSlot(widgetName)) {
                               var dataSlot = handler.formWidgets.sourceDataSlots[widgetName];							     
                               if(handler.enableLogger) YAHOO.BM.Logger.debug(" Setting the source of '"+widgetName+"' dataslot:"+YAHOO.lang.JSON.stringify(dataSlot));     
							   if(!YAHOO.lang.isUndefined(dataSlot) && !YAHOO.lang.isUndefined(dataSlotValues[dataSlot.getName()])) {										
										/**		
	 									*  If setValue is defined for widget then
	 									*/									
										if (formWidget.getWidget().setValue) {
											if(handler.enableLogger) YAHOO.BM.Logger.debug(" Setting the value for '"+widgetName+"' dataslot:"+dataSlotValues[dataSlot.getName()]);     
											formWidget.getWidget().setValue(handler.convertDataModelIfNecessary('source', formWidget, dataSlotValues[dataSlot.getName()]));
										}
								}		
                          }						  
                          //has target
                          if(handler.formWidgets.hasTargetDataSlot(widgetName)) {
                              var dataSlot = handler.formWidgets.targetDataSlots[widgetName];    
                              if(handler.enableLogger) YAHOO.BM.Logger.debug(" Setting the target of '"+widgetName+"' dataslot:"+YAHOO.lang.JSON.stringify(dataSlot));
							  if(!YAHOO.lang.isUndefined(dataSlot) && formWidget.getTargetDataSlotType() != 'STRING' &&
							  formWidget.getTargetDataSlotType() != 'BOOLEAN')  {
                                  if(formWidget.getWidgetType() == 'sbm.list' || 
                                     formWidget.getWidgetType() == 'sbm.radio' || 
                                     formWidget.getWidgetType() == 'sbm.checkbox' ||
		        	                 formWidget.getWidgetType() == 'sbm.radio' ||
                   		             formWidget.getWidgetType() == 'sbm.combobox'){		
									    
									   
											if (formWidget.getTargetDataSlotType() == 'MAP' && typeof dataSlotValues[dataSlot.getName()] != 'undefined') {
											
												var map;
												var convertedData = new Array();
												if (typeof dataSlotValues[dataSlot.getName()] != undefined) {
													map = eval(dataSlotValues[dataSlot.getName()]);
													for (var key in map) {
														convertedData[convertedData.length] = key;
													}
													
													if (convertedData.length > 0) 
														formWidget.getWidget().setSelectedValues(YAHOO.lang.JSON.stringify(convertedData));
												}
												
											}
											else {
											
												formWidget.getWidget().setSelectedValues(dataSlotValues[dataSlot.getName()]);
											}
				     			   }
			                  }
			      				// for business objects property mapped to combobox
			      			  if(!YAHOO.lang.isUndefined(dataSlot) && dataSlot.getType() == 'STRING' && 
				            		formWidget.getWidgetType() == 'sbm.combobox' &&
			                		formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()){
				      				// set selected values 
									formWidget.getWidget().setSelectedValue(dataSlotValues[dataSlot.getName()]);
			      			
							}else if(!YAHOO.lang.isUndefined(dataSlot) && dataSlot.getType() == 'STRING' && 
				            		formWidget.getWidgetType() == 'sbm.radio' && 
									formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()){
				      				// set selected values
									formWidget.getWidget().setSelectedValue(dataSlotValues[dataSlot.getName()]);
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
          if(messages.length > 0) handler.showError(messages);          
      }      
	 
      // prepare the dataslots
      var sourceDataSlots = new Array();
      for(var i=0; i<this.formWidgets.widgetNames.length; i++){
          var widgetName = this.formWidgets.widgetNames[i];
          var formWidget = this.formWidgets[widgetName];
          // find source dataslots 
		  if(this.formWidgets.hasSourceDataSlot(widgetName) &&
		      this.formWidgets.sourceDataSlots[widgetName] != null) {
		  	    sourceDataSlots[sourceDataSlots.length] = this.formWidgets.sourceDataSlots[widgetName];		  	
		  }		  
		  // find target dataslots
		  if(this.formWidgets.hasTargetDataSlot(widgetName) && 
		       (this.formWidgets.targetDataSlots[widgetName] != null) &&
			   (formWidget.getSourceDataSlotName() != formWidget.getTargetDataSlotName())) {
		  		  		sourceDataSlots[sourceDataSlots.length] = this.formWidgets.targetDataSlots[widgetName];		  
		  } 
      }    
      //alert(YAHOO.lang.JSON.stringify(sourceDataSlots));
      // perpare post data for save dataslots ajax call...
      var postData = {
          fiid: this.fiid,
          commandAction: 'getdataslots',
		  scope: this,
          dataSlots: YAHOO.lang.JSON.stringify(sourceDataSlots)};
      if(this.enableLogger) YAHOO.BM.Logger.debug("retrieving the values for :"+YAHOO.lang.JSON.stringify(sourceDataSlots)); 
      // do ajax call..      
      if(sourceDataSlots.length > 0)
	      this.doAjax(getContextPath()+"/bpmportal/common/forms/getdataslots.form",postData,getDataSlotsCallback); 
	  
}  
  
FormWidgetHandler.prototype.saveDataSlots = function(){
    if(this.enableLogger) YAHOO.BM.Logger.info("saveDataSlots hs been invoked...");     
    if(YAHOO.lang.isUndefined(this.formWidgets.widgetNames) || this.formWidgets.widgetNames.length == 0)        
        return null;
        
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
                                     		
											
										if(typeof formWidget.getTargetDataSlotType() != 'undefined' && typeof formWidget.getTargetDataSlotType().length != 0)												
											if (formWidget.getWidget().getSelectedValues && formWidget.getTargetDataSlotType() != 'STRING') {										
												
												if (formWidget.getTargetDataSlotType() == 'MAP' &&
												(formWidget.getWidgetType() == 'sbm.combobox' ||
												formWidget.getWidgetType() == 'sbm.list')) {													
														var mapValues = {};														
														var _map = formWidget.getWidget().getValue();																																										
														if(typeof _map == 'string') _map = YAHOO.lang.JSON.parse(_map);														
														for(var k=0; k<_map.length;k++) {														     
															 if(typeof _map[k].selected != 'undefined' && _map[k].selected)	
															    mapValues[_map[k]['label']] = _map[k]['value'];			
														
														}																												
														dataslot['dataModelType'] = 'combobox';
														dataslot['jsonValue'] = YAHOO.lang.JSON.stringify(mapValues);
																											
												
												}else if (formWidget.getTargetDataSlotType() == 'BOOLEAN' && formWidget.getSourceDataSlotType() == 'BOOLEAN' &&
												!YAHOO.lang.isUndefined(formWidget.getWidget().getValue())) {
													dataslot['jsonValue'] = formWidget.getWidget().getValue();
												}else if((formWidget.getTargetDataSlotType() == 'MAP' || formWidget.getTargetDataSlotType() == 'STRING') && formWidget.getWidgetType() == 'sbm.radio') {
												    var value = YAHOO.lang.JSON.parse(formWidget.getWidget().getValue());
													var map = {}; 
													for (var k = 0; k < value.length; k++) {
													    if(value[k].selected)
														   map[value[k].value] = value[k].label;  
														    
													}
													dataslot['dataModelType'] = 'combobox';
													dataslot['jsonValue'] = YAHOO.lang.JSON.stringify(map); 
													
												}else{ 	
													dataslot['selections'] = formWidget.getWidget().getSelectedValues();
												// for business objects property mapped to combobox
												}
											}
											else if (formWidget.getWidgetType() == 'sbm.combobox' && formWidget.getTargetDataSlotType() == 'STRING' &&
												formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()) {
													dataslot['dataModelType'] = 'none';
													dataslot['jsonValue'] = formWidget.getWidget().getSelectedValue();
											}else if (formWidget.getWidgetType() == 'sbm.radio' && formWidget.getTargetDataSlotType() == 'STRING' &&
													formWidget.getTargetDataSlotName() != formWidget.getSourceDataSlotName()) {
														dataslot['dataModelType'] = 'none';
														dataslot['jsonValue'] = formWidget.getWidget().getSelectedValue();
											}else if ((formWidget.getTargetDataSlotType() == 'STRING' || formWidget.getTargetDataSlotType() == 'URL') &&
														!YAHOO.lang.isUndefined(formWidget.getWidget().getValue())) {
															dataslot['jsonValue'] = formWidget.getWidget().getValue();
											}
											
											
											
														
					 
                                 
								 
								 }catch(e){
                                     alert(e);
                                 }
                              }
                              else
                              {
                                 try
                                 {
                                      if(formWidget.getWidget().getValue)
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
        //document.forms[0].submit();        
    }
        
    var handleNo = function(){
       this.hide();
    }
    
    var handleYes = function(){
         if(handler.enableLogger) YAHOO.BM.Logger.debug("DoAjax for saving following dataslots :\n"+YAHOO.lang.JSON.stringify(dataSlots)); 
         // perpare post data for save dataslots ajax call...
         var postData = {
           fiid: this.fiid,
           commandAction: 'savedataslots',
           dataSlots :YAHOO.lang.JSON.stringify(dataSlots)};         
    
        if(dataSlots.length > 0)     
           handler.doAjax(getContextPath() + "/bpmportal/common/forms/savedataslots.form",postData,saveDataSlotsCallback); 
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
           fiid: Bm.form.Cache.getFiid(),
           commandAction: 'savedataslots',
           dataSlots :YAHOO.lang.JSON.stringify(dataSlots)};
	 if(dataSlots.length > 0)     
           this.doAjax(getContextPath()+"/bpmportal/common/forms/savedataslots.form",postData,saveDataSlotsCallback);
   }
}

FormWidgetHandler.prototype.doAjax = function(url,params,callback){
    var handler = this;
    function handleError(msg,_req){
	    //responseText        
        var resizableDialog = new ResizableDialog();
		var exception = YAHOO.lang.JSON.parse(_req.responseText);  
        var body = ""
        body += "<table><tr><td>"+msg+"</td></tr><tr><td>";
        body += "<tr><td><span style='color:red'>"+exception['exceptionLocalizedMessage']+"</td></tr>";
	body +=     " +";        
        body += "</td></tr></table>";
        resizableDialog.setBody(body);
	resizableDialog.show();
	YAHOO.util.Dom.setStyle('resizablepanel','display','block');
    }   
    jmaki.doAjax({ url : url,method : 'POST',asynchronous: false, content : params, callback: callback, onerror: handleError });      
}

FormWidgetHandler.prototype.showError = function(messages){          
     var body = ""
     var rows = "";
     for(var i=0; i<messages.length; i++) rows += "<tr><td>"+messages[i]+"</td></tr>";
     body += "<table><tr><td>Initialization of following widgets has been failed:</td></tr>" + rows + "</table>" 
     var resizableDialog = new ResizableDialog();
     resizableDialog.setBody(body);
     resizableDialog.show();
     YAHOO.util.Dom.setStyle('resizablepanel','display','block'); 
	 
}


FormWidgetHandler.prototype.getDataModelTypeForWidget = function(widgetType,targetDataSlotType,isSourceTargetNotEqual){
    if(widgetType == 'sbm.textfield' || widgetType == 'sbm.checkbox' || widgetType == "sbm.textarea")
        return 'none';
    else if(widgetType == "sbm.list" || widgetType == "sbm.combobox" || widgetType == "sbm.checkbox"){
        return (widgetType == 'sbm.checkbox' && 
	         !YAHOO.lang.isUndefined(targetDataSlotType) && 
		 !YAHOO.lang.isUndefined(isSourceTargetNotEqual) && 
		 isSourceTargetNotEqual && 
		 targetDataSlotType == 'STRING') ? 'none' : 'combobox';
    }else if(widgetType == "dojo.table")
        return 'table';  
    
}
    
FormWidgetHandler.prototype.findWidgetByDataSlotName = function(dataSlotName,type){
    for(var i=0; i<this.formWidgets.length;i++){
        if(type == 'source' && this.formWidgets[i].getSourceDataSlotName() == dataSlotName){
            return this.formWidgets[i].getWidget();
        } 
		if(type == 'target' && this.formWidgets[i].getTargetDataSlotName() == dataSlotName){
            return this.formWidgets[i].getWidget();
        } 
		
    }    
} 

FormWidgetHandler.prototype.findWidgetByName = function(widgetName){
    if(YAHOO.lang.isUndefined(this.formWidgets[widgetName])) return;
    return this.formWidgets[widgetName].getWidget();
}

FormWidgetHandler.doLayout = function(widgetName){
     Ext.ComponentManager.each(function(key,value,length){      
                if(Ext.getCmp(key).getXType && (Ext.getCmp(key).getXType() == 'panel' || Ext.getCmp(key).getXType() == 'treepanel' || Ext.getCmp(key).getXType() == 'toolbar' || Ext.getCmp(key).getXType() == 'gridpanel' || Ext.getCmp(key).getXType() == 'tabpanel')){        
                      Ext.getCmp(key).doLayout();       
                }       
     },this); 
}

FormWidgetHandler.prototype.findFormWidgetByName = function(widgetName){
    if(YAHOO.lang.isUndefined(this.formWidgets[widgetName])) return;
    return this.formWidgets[widgetName];
}

FormWidgetHandler.prototype.showInLineValidationDialog = function(invalidWidgets){
    var msg = "<ul>";
    for(var i = 0; i < invalidWidgets.length; i++) {
		var _label = invalidWidgets[i]['label'];
		var _validation_message = (typeof invalidWidgets[i]['validationMessage'] == 'undefined') ? '' : invalidWidgets[i]['validationMessage'];
		if(typeof _label == 'undefined' || _label.length == 0){
			_label = invalidWidgets[i]['id'];
		}	 
		msg += ("<li title=\"Click to target field\" onclick=\"Bm.form.Cache.focusOnWidget(\'" + invalidWidgets[i]['id'] + "\');\">'" + _label + "' field is not valid. "+_validation_message + "</li>");
	}
    msg += "</ul>";    
	var error = document.getElementById('errors');	
	error.innerHTML = msg;
	// modify the CSS of error box
	Ext.get('errors').setStyle('background-color',"#efefef");
	Ext.get('errors').setStyle('margin-bottom',"7px");	
	Ext.get('errors').setStyle('display','block');
	Ext.get('errors').setStyle('visibility','visible');
	Ext.get('errors').setStyle('border-radius','5px');	
}

FormWidgetHandler.prototype.validateWidgets = function(){ 
	  if(this.enableLogger) {
		 var widgetQueue = Spry.Widget.Form.onSubmitWidgetQueue;
		 for (var i = 0; i < widgetQueue.length; i++) { 
			 var spanId = widgetQueue[i].element.getAttribute('id');
			 var widgetName = spanId.substring(0,spanId.length-4);
			 if(!widgetQueue[i].isDisabled())
				 YAHOO.BM.Logger.info("validating '"+widgetName+"' is :"+widgetQueue[i].validate());
		 }
	  }
	  // provides label element id map for showing validation messages
	  var fieldsMap = new Ext.util.HashMap();
	  var invalidWidgets = new Array();
	  var isValid = Spry.Widget.Form.validate(document.forms[0]);	  
	  function isNotValidDateTimeField(key) {
		if (key.indexOf('_id') > 0
				&& (Ext.getCmp(key).getXType() === 'datefield' || Ext.getCmp(
						key).getXType() === 'datetimefield')) {
			if (Ext.getCmp(key).validate && !Ext.getCmp(key).validate()) {
				return true;
			}
		}
		return false;
	  }
	  Ext.ComponentManager.each(function(key,value,length){ 
         if (isNotValidDateTimeField(key)) {         
            isValid = false;				
            return false;		
         }
	  },this);	
	  var docXmlValidation = Bm.util.validate(document.forms[0]);
	  if(!docXmlValidation.isValid) {	  	 
		 isValid = false;		 
		 invalidWidgets = Ext.Array.merge(invalidWidgets,docXmlValidation.invalidWidgets)
	  }
	  
	  Ext.ComponentManager.each(function(key,value,length){		     
            if (isNotValidDateTimeField(key)) {                
				var label = this.findLabel(key.substr(0,key.length -3));								
				invalidWidgets[invalidWidgets.length] = {label: (label.length != 0) ? label : key.substr(0,key.length -3),
				                                           id:key, validationMessage:(Ext.getCmp(key).getActiveErrors().join('.')) };	
				isValid = false;		
		 	}
			if(Ext.getCmp(key).getXType() == 'decimaldatafield'){
			  	   var comp = Ext.getCmp(key);
				   if (comp.hasActiveError()) {
				        var msg = comp.getActiveError();        				
						msg = msg.replace(/\<br>/gi, '');
						msg = msg.replace(/\<ul>/gi, '');
						msg = msg.replace(/\<li>/gi, '');
						msg = msg.replace('\n', '');
						msg = msg.replace(/\<b>/gi, '');
						invalidWidgets[invalidWidgets.length] = { label: msg, id: key};
        				isValid = false;
				   }
			  }
	      },this);
	  	  
	  if(!isValid) {		   
		   var widgetQueue = Spry.Widget.Form.onSubmitWidgetQueue;
		   for (var i = 0; i < widgetQueue.length; i++) { 
		          if (!widgetQueue[i].validate()) {
				  	var invalidElemId = widgetQueue[i].element.getAttribute('id');	
					var label = this.findLabel(widgetQueue[i].element.getAttribute('id'));					
					var _el = Ext.get(invalidElemId);
					var _parent = _el.parent();	
					var children = _parent.select('span');
					var _validation_message = '';
						children.each(function(el,c,idx){
							if(el.dom.getStyle('display') == 'inline')
						       _validation_message = el.dom.innerHTML;
							
					});
					
					if(invalidElemId.indexOf('_div') != -1)
						    invalidElemId = invalidElemId.replace('_div','');
					if(label.length != 0){
				  	   invalidWidgets[invalidWidgets.length] = {label: label, id: invalidElemId, validationMessage: _validation_message};
					}else {						
						invalidWidgets[invalidWidgets.length] = {label: label, id: invalidElemId, validationMessage: _validation_message}; 
					}
				  }			  
		   }
		   
		   	
		   
		   // show validation failed message....
		   //Ext.Msg.alert('Validation', 'Validation has been failed!');
           if(invalidWidgets.length > 0) {
			   if ((clickedButton.toLowerCase() == "bizsite_reassigntask" ||
			   clickedButton.toLowerCase() == "bizsite_skip" ||
			   clickedButton.toLowerCase() == "bizsite_savetask") &&
			   this.saveAnyWay) 
			   	return true;
			   else 
			   {			      
				   // removed basic controls hook
				   this.showInLineValidationDialog(invalidWidgets);   
			   	   return false;		  
			   }
		   }
	  }
	  return isValid;
  
}

FormWidgetHandler.prototype.findLabel = function(elemId){
   var label = "";
   if(elemId.indexOf('_div')!= -1){
         var id = elemId.substring(0,elemId.length-4);
		 var labels = document.getElementsByTagName("label");
		 for (var i = 0; i < labels.length; i++) {
		 	if(labels[i].htmlFor == id)
			     return labels[i].innerHTML;
		 	
		 }    
   }
   return label;
}

FormWidgetHandler.prototype.showValidationDialog = function(invalidWidgets){
    var msg = "<table><tr><td><span style='color:black'>Following fields are not valid:</span></td></tr>";
    for(var i = 0; i < invalidWidgets.length; i++) {	 
		  msg += ("<tr><td><span style='color:red'>-"+invalidWidgets[i] +"</span></td></tr>");	  
    }
    msg += "</table>";
    
    if(clickedButton.toLowerCase() == "bizsite_reassigntask" || 
	     clickedButton.toLowerCase() == "bizsite_skip" ||
             clickedButton.toLowerCase() == "bizsite_savetask"){
	     var handler = this;
             function showOptions(btn,text) {	
		          if(btn == 'ok') 
			  {
			     handler.saveAnyWay = true;
			     var elements = document.forms[0].elements;
			     for(var i=0; i< elements.length;i++){
				     if(elements[i].name == clickedButton && elements[i].type == 'submit'){
					      elements[i].click();
					      break;
				     }
			     }
			  }else saveAnyWay = false;
	     }
	     Ext.fly(document.body).dom.value = Ext.MessageBox.WARNING;
	     Ext.MessageBox.show({ title: 'Save Changes?',
	    	                  msg: msg,
                                  buttons: Ext.MessageBox.OKCANCEL,
                                  animEl: document.body,
                                  fn: showOptions });
		     
		     
		     
		     
    } else {
	    
	sbm.widgets.showMsg('Validation Failed!',msg);
        return false;
    }
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

//-----------------------------------------------------------------//
/**
 * @class Bm.widget.Panel
 * @extends Ext.panel.Panel
 * Extention of Ext.panel.Panel for Form Designer Generated JSPs
 */
Ext.define('Bm.widget.Panel', {
    extend: 'Ext.panel.Panel',
    alias: 'bm.panel',
    layout: 'fit',
    anchor: '100%',
    frame: true,
    //bodyPadding: 10,
	autoScroll: true,	
    //collapsible: true,	
	listeners: {
	   afterrender: {
          fn: function(comp,eOpts){          	     
				 //comp.doLayout();		
          }		
       },		
	   resize: {
			fn: function(comp, adjWidth, adjHeight, eOpts){				
				var el = Ext.get(comp.contentEl);				
				el.setHeight(adjHeight);
				//el.setWidth(adjWidth);
				comp.doLayout();			
			}			
		}		
	},
	
	constructor: function(config){
		config = config || {};
		Ext.apply(this,config);		
		this.callParent(arguments);
	}/*,
	
	 afterRender : function() {        
		var me = this,
            pos,
            xy;

        me.getComponentLayout();

        // Set the size if a size is configured, or if this is the outermost Container.
        // Also, if this is a collapsed Panel, it needs an initial component layout
        // to lay out its header so that it can have a height determined.
        if (me.collapsed || (!me.ownerCt || (me.height || me.width))) {
            me.setSize(me.width, me.height);
        } else {
            // It is expected that child items be rendered before this method returns and
            // the afterrender event fires. Since we aren't going to do the layout now, we
            // must render the child items. This is handled implicitly above in the layout
            // caused by setSize.
            me.renderChildren();
        }

        // For floaters, calculate x and y if they aren't defined by aligning
        // the sized element to the center of either the container or the ownerCt
        if (me.floating && (me.x === undefined || me.y === undefined)) {
            if (me.floatParent) {
                xy = me.el.getAlignToXY(me.floatParent.getTargetEl(), 'c-c');
                pos = me.floatParent.getTargetEl().translatePoints(xy[0], xy[1]);
            } else {
                xy = me.el.getAlignToXY(me.container, 'c-c');
                pos = me.container.translatePoints(xy[0], xy[1]);
            }
            me.x = me.x === undefined ? pos.left: me.x;
            me.y = me.y === undefined ? pos.top: me.y;
        }

        if (Ext.isDefined(me.x) || Ext.isDefined(me.y)) {
            me.setPosition(me.x, me.y);
        }

        if (me.styleHtmlContent) {
            me.getTargetEl().addCls(me.styleHtmlCls);
        }
    }*/
   
});
