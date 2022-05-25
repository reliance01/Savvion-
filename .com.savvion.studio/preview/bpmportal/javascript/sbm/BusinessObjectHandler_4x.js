/**
 *   Handler the opearions of collection of business
 *   
 *   -contextPath: the context path of web application
 *   -dataSlotName: the name of collection of business object dataslot
 *   -renderTo: the id of element which grid will be rendered
 *   -fieldDefs: definition of fileds for column names
 *   -extGridColumnModel: the ext column models
 *   -gridData: the data of grid with respect to grid column model, type is Arrayreader
 **/
function BusinessObjectHandler(contextPath,
                               dataSlotName,
			       businessObjectName,
			       renderTo,
			       fieldDefs,
			       extGridColumnModel,
			       gridData) {
	this.contextPath = contextPath;
	this.dataSlotName = dataSlotName;
	this.businessObjectName = businessObjectName;
	this.renderTo = renderTo;  
	this.fieldDefs = fieldDefs;
	this.extGridColumnModel = extGridColumnModel;
	this.gridData = gridData;
	this.init();
	var div = dataSlotName;	
}

BusinessObjectHandler.constructor = BusinessObjectHandler;
BusinessObjectHandler.prototype = {
   init: function(){   	    	                  	
		var fields = this.fieldDefs;		
		var _id = this.dataSlotName.replace(/\./g,'_')+'';
		this.gridStore = Ext.create('Ext.data.JsonStore', {
		        storeId: _id+'_store',
				fields:this.extGridColumnModel
		});		      
		var handler = this;		
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id:  _id + '_Id',
	        store: this.gridStore,
	        columns: this.extGridColumnModel,
	        layout: 'fit',	       
	        autoHeight:true,
			autoWidth:true,			
	        title:this.businessObjectName+' List',            
	        frame:true,
	        viewConfig: {	            
	           // emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGridWithAddList1') + this.businessObjectName + getLocalizedString('NoRecordsInGridWithAddList2') + '</div>'
			   emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGridWithAddList') + '</div>'
	        },
			dockedItems: [{
						  id: _id +'toolbar',
						  xtype: 'toolbar',
						  dock: 'top',
						  items: [{
						   	 id: _id + '_create',
							 name: _id + '_create',
							 iconCls:'addIcon',
							 text: getLocalizedString('AddToList'),
							 xtype: 'button',
							 enableToggle: true,
							 scope: this,
							 toggleHandler: function(){
							  	this.addBusinessObject();
							 }
						   }]
						}
			]		
			// tbar: [{text:'Add '+this.businessObjectName+' to List' , iconCls:'addIcon', handler: this.addBusinessObject,scope: this,pressed: true, enableToggle: true},'-'//,
		        //{text:'Reset List', handler: handler.addBusinessObject, pressed: true, iconCls:'resetIcon' }
		    //]	        
	        
		      
		       
	   });    	
	   this.gridPanel.render(_id);
	   this.gridPanel.doLayout();   	
    },
	
	toggleDetail: function(btn, pressed){
		    var view = grid.getView();
		    view.showPreview = pressed;
		    view.refresh();
	},
	
	addBusinessObject: function(){    
	      $(this.dataSlotName).innerHTML ="Loading...";
	      sbm.utils.updateContent(this.dataSlotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/addBusinessObject.portal?dataslotName="+this.dataSlotName+'&fiid='+Bm.form.Cache.getFiid()); 
    	}
}

/**
 *  Business Object Handler
 *
 */
Ext.namespace("Bm.RepeatSection");
Ext.namespace("Portal.ux");
Bm.RepeatSection.debug = false;
Bm.RepeatSection.pageSize = 5;



function RepeatSectionHandler(businessObjectName,configOptions,fiid){
	  this.fiid=fiid;
	  this.businessObjectName = businessObjectName;
	  this.configOptions = configOptions;
	  this.repeatSectionsCache = {};
	  this.boSectionsCache = {};
	  this.root = eval(businessObjectName);
	  this.rootFields = eval(this.businessObjectName + '.root');	  
	  this.jsCalendarDateFormat = eval(this.businessObjectName + '.jsCalendarDateFormat');
	  this.jsCalendarDateOnlyFormat = eval(this.businessObjectName + '.jsCalendarDateOnlyFormat');
	  this.calendarDateFormat = eval(this.businessObjectName + '.calendarDateFormat');
	  this.calendarDateOnlyFormat = eval(this.businessObjectName + '.calendarDateOnlyFormat');	  
	  this.fields = new Array();
	  // initialize logger..
	  if(Bm.RepeatSection.debug){
        YAHOO.BM.Logger = new BM.Portal.LoggerDialog(Bm.RepeatSection.debug);
      }	  
	  this.init();	  
}



RepeatSectionHandler.constructor = RepeatSectionHandler;
RepeatSectionHandler.prototype = {
	 init: function(){		 
		 /*
		 if(typeof this.rootPanel == 'undefined')
		 this.rootPanel = Ext.create('Ext.panel.Panel',{
					id: this.businessObjectName+'_panel_id',
	 				renderTo: this.businessObjectName,
					autoScroll:true,
					autoShow: true,
					border: true,
					margins: '5 5 5 5',
					bodyStyle:'padding:5px;'+this.bodyStyle,
					title: this.root.title,
					collapsible: this.root.collapsible,
					autoWidth:true, 
					autoHeight:true
		 });*/	 
		 
		for(var i=0; i<this.rootFields.length;i++){		 	
			var field = new Bm.RepeatSection.Field(this.rootFields[i]);
			field.initComponent();
			this.fields[this.fields.length] = field;
		 }
		 this.addValidationRules();
		 this.updateRootFields();         
		 
		 for(var i=0; i< this.root.repeatSections[this.businessObjectName].length; i++){		 	        
			 		var repeatSection = this.root.repeatSections[this.businessObjectName];
			 		var repeatSectionPath = repeatSection[i]['path'];
			 		var repeatSectionType = repeatSection[i]['type'];
			 		var repeatSectionClass = repeatSection[i]['className'];
			 		var repeatSectionTitle = repeatSection[i]['title'];
			 		var repeatSectionCollapsible = repeatSection[i]['collapsible'];
			 		var repeatSectionBodyStyle = repeatSection[i]['bodyStyle'];
					var repeatSectionNumberOfRowsPerPage = repeatSection[i]['numberOfRowsPerPage'];
					var repeatSectionEditable = (typeof repeatSection[i]['editable'] != 'undefined')? repeatSection[i]['editable'] : true;
		
		            
			 		if(Bm.RepeatSection.debug) {
						YAHOO.BM.Logger.info("Initializing Repeat Section for root:");
						YAHOO.BM.Logger.info("-repeatSectionPath:" + repeatSectionPath + ", repeatSectionType:" + repeatSectionType + ", repeatSectionClass:" + repeatSectionClass);
					}
					
			 		var repeatSectionFields = eval(repeatSectionPath);
			 		var fields = new Array();
			 		fields[0] = {name:'edit',dataIndex:'edit'};
			 		fields[1] = {name:'delete',dataIndex:'delete'};
					 
			 		for(var j=0; j<repeatSectionFields.length; j++ ){
						 var path = repeatSectionFields[j]['path'];
	     				 var name = name = path.substring(path.lastIndexOf('.')+1,path.length);
						 var label = repeatSectionFields[j]['label'];
						 var className = repeatSectionFields[j]['className'];
						 var type = repeatSectionFields[j]['type'];
				    	 var args = (typeof repeatSectionFields[j]['args'] == 'undefined') ? {} : repeatSectionFields[j]['args'];				 	
						 if(className == 'java.lang.Boolean')
				       		   fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,renderer:Bm.RepeatSection.renderCheckBox};
						 else if (className == 'java.sql.Timestamp') {						 	
							   if (typeof repeatSectionFields[j]['dateOnly'] != 'undefined' && repeatSectionFields[j]['dateOnly'] == 'true') {
							   	var dateOnly = "true";
								
								try {
									fields[fields.length] = {
										name: name,
										label: label,
										header: label,
										path: path,
										dataIndex: name,
										className: className,
										type: type,
										dateOnly: dateOnly,
										jsCalendarDateFormat: repeatSectionFields[j]['jsCalendarDateFormat'],
										dateOnlyFormat: repeatSectionFields[j]['dateOnlyFormat'],								
										renderer: sbm.util.Format.dateRenderer(repeatSectionFields[j]['dateOnlyFormat'],repeatSectionFields[j]['jsCalendarDateFormat'],true)
										
										
									};
								}catch(e){
									 alert(e);
								}
							   }
							   else 
							   	fields[fields.length] = {
							   		name: name,
							   		label: label,
							   		header: label,
							   		path: path,
							   		dataIndex: name,
							   		args: args,
							   		className: className,
							   		renderer: Bm.RepeatSection.renderDate
							   	};
						 	
						 }
						 else 
						 	fields[fields.length] = {
						 		name: name,
						 		label: label,
						 		header: label,
						 		path: path,
						 		dataIndex: name,
						 		args: args,
						 		className: className,
						 		sortable: true
						 	};
						
			        }
             
			 	    if(typeof this.root.repeatSections[repeatSectionPath] != 'undefined'){
			            var repeatSections = this.root.repeatSections[repeatSectionPath];
				        for(var j=0; j<repeatSections.length;j++)
   				           fields[fields.length] =  {name: 'drilldown',dataIndex:'drilldown',header:repeatSectionTitle,path:repeatSectionPath,renderer:Bm.RepeatSection.renderer};
 						    
		            }

                    var configOptions = {collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable,numberOfRowsPerPage:repeatSectionNumberOfRowsPerPage};
					if(repeatSectionType == 'grid') {
			            repeatSection = new GridRepeatSection(this.businessObjectName,
						                                      repeatSectionPath,
															  repeatSectionClass,
															  repeatSectionTitle,
															  fields,
															  repeatSectionFields,
															  configOptions);
			            this.root.repeatSectionsCache[repeatSectionPath] = repeatSection;
		            }else if(repeatSectionType == 'lineitem') {
			            repeatSection = new LineItemRepeatSection(this.businessObjectName,
						                                          repeatSectionPath,
																  repeatSectionClass,
																  repeatSectionTitle,
																  fields,
																  repeatSectionFields,
																  configOptions);
			            this.root.repeatSectionsCache[repeatSectionPath] = repeatSection;
		            }			 		
		 }
		 
		
		 //Ext.Msg.alert("Debug","<p>root.boSections:"+this.root.boSections[this.businessObjectName]);
		 for(var i=0; i< this.root.boSections[this.businessObjectName].length; i++){
			 		var section = this.root.boSections[this.businessObjectName];			 		
			 		var boSectionType = section[i]['type'];			 		
			 		var boSectionTitle = section[i]['title'];
			 		var boSectionCollapsible = section[i]['collapsible'];
			 		var boSectionBodyStyle = section[i]['bodyStyle'];
					var boSectionPath = section[i]['path'];					
		      		var configOptions = {collapsible:section[i]['collapsible'],bodyStyle:section[i]['bodyStyle']};
			    	var boSection = new BOSection(this.businessObjectName,section[i]['path'],section[i]['className'],section[i]['title']
					                   ,{collapsible:section[i]['collapsible'],bodyStyle:section[i]['bodyStyle']});
			    	//Ext.Msg.alert("Debug","<p>boSectionPath:"+boSectionPath+"\n");
					this.root.boSectionsCache[boSectionPath] = boSection;
		      			 		
		 }
		 YAHOO.util.Event.addListener(document.forms[0], 'reset', this.reset,this,true); 
		
		 
	 },
		 
	 updateRootFields: function(){
	 	    if(Bm.RepeatSection.debug) {
			  YAHOO.BM.Logger.debug("updateRootFields for RepeatSectionHandler");
			  YAHOO.BM.Logger.debug("-rootFields:" + YAHOO.lang.JSON.stringify(this.rootFields));
		    }
            var rootFields = this.rootFields;
			if(typeof rootFields != 'undefined'){
            	var fields = new Array(); 	 
				for(var i=0; i<rootFields.length;i++){
		                var path = rootFields[i]['path'];	
		                var className = rootFields[i]['className'];	
						var type = rootFields[i]['type'];
												
						var widgetId = '';
						//alert(rootFields[i]['widgetId']);
						if(typeof rootFields[i]['widgetId'] != 'undefined')
						    widgetId = rootFields[i]['widgetId']; 
					   
					   	if (typeof path != 'undefined') {								
							    if (typeof rootFields[i]['dateOnly'] != 'undefined' && rootFields[i]['dateOnly'] == 'true') {									
									var dateOnly = "true";									
									fields[fields.length] = {
										name: path,
										className: className,
										type: type,
										dateOnly: dateOnly,
										dateFormat: rootFields[i]['dateOnlyFormat']
									};							
								}
								else 
									fields[fields.length] = {
										name: path,
										className: className,
										type: type,
										widgetId: widgetId
									};
									
						}
				}
                 				 
				if(fields.length > 0){
				        var conn = new Ext.data.Connection();
						var handler = this;
				        conn.request({
                                  url: getContextPath()+'/bpmportal/common/repeatsection/getValues.portal',
					              method: 'POST',
								  scope: this,
					              params: {
											fiid: this.fiid,
                                            fields: YAHOO.lang.JSON.stringify(fields),
	  						                businessObjectName: this.businessObjectName,
		                                    dataslotName: this.businessObjectName		                     
						          },
					              success: function(req,opt){
                                           var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
	        			                   var rowData = retJsonValues['values'];
										   if(Bm.RepeatSection.debug) {			  									
			  									YAHOO.BM.Logger.debug("-rowData:" + YAHOO.lang.JSON.stringify(rowData));
		    							   }
										   
							               for(var i=0; i<fields.length;i++){
                                   				var name = fields[i]['name'];
                                   			    var className = fields[i]['className'];
								                var value = rowData[name];
												var type =  fields[i]['type'];
												if (value != null) {
													try {
														if (className == 'java.lang.Boolean') {
															if(value == 'true')
															  document.getElementById(name).checked = true;
															 else
															  document.getElementById(name).checked = false; 
														}else if(className == 'java.sql.Timestamp'){
															 
															 if (value != '') {
															 	//var pattern = '%b %d, %Y %I:%M %p';
															 	//var pattern1 = 'MMM dd, yyyy';																														
																if (typeof fields[i]['dateOnly'] != 'undefined' && fields[i]['dateOnly'] == 'true') {																	
																		var date = sbm.convertDate(value, this.jsCalendarDateFormat);
																		document.getElementById(name).value = sbm.formatDate(date, this.calendarDateOnlyFormat);
																	
																}else {
																	 document.getElementById(name).value = value;																	
																}
															 }
														    
														
														}  else if (type == 'sbm.textfield'|| type == 'datetime') {
																document.getElementById(name).value = value;
														}  
														
														
														
													} 
													catch (error) {
													}
												}
							                }
								
						          },
                        		  failure: function(req,opt){
										handler.loadMask.hide();
						          }
				      });		
              }		 
	    }
	 },
	 
	 addValidationRules: function(){
		      var rootFields = eval(this.businessObjectName + '.root');
		      if(typeof rootFields != 'undefined'){
			         for(var i=0; i<rootFields.length;i++){ 
                         var path = rootFields[i]['path'];
                         var type = rootFields[i]['className'];
					     var args = rootFields[i]['args'];
				  		 var validationConfig = Bm.RepeatSection.getValidationRules(path, type,args);
						 
					     try
					     {					               
					         if(typeof validationConfig['validationType'] != 'undefined' && 
							       validationConfig['validationType'].length != 0 &&
								       validationConfig['validationType'] != 'none') {								   	  
									  //alert("path:"+path+",type:"+type+"\n"+YAHOO.lang.JSON.stringify(validationConfig));
						 			  //alert("validationConfig:"+validationConfig['validationType']);									  
									  //alert("args:"+YAHOO.lang.JSON.stringify(args));
						              
									  var vOptions = {};
									  var options = validationConfig['vOptions'];
									  var validation = args['validation'];
									  vOptions['validateOn'] = validation['validateOn'];
									  if(typeof options['isRequired'] != 'undefined'){
	      									vOptions['isRequired'] = options['isRequired'];
									  }else{
											vOptions['isRequired'] = false;
									  }
									  
									  if(validationConfig['validationType'] == 'email'){
									  		// applicable parameters
											//isRequired true (the default); false
											//minChars/maxChars Positive integer value									
											if(typeof options['minChars'] != 'undefined' && typeof options['maxChars'] != 'undefined'
											   && typeof options['minChars'] != options['maxChars'] != 'undefined'){
											   	    vOptions['minChars'] = options['minChars'];
													vOptions['maxChars'] = options['maxChars'];
												
											   }
											
									  }else if(validationConfig['validationType'] == 'credit_card'){									  	    
										    if(typeof options['format'] != 'undefined')
			      									vOptions['format'] = options['format'];
									  }else if(validationConfig['validationType'] == 'custom'){									  	    
										    if(typeof options['pattern'] != 'undefined')
													vOptions['pattern'] = options['pattern'];										     											     											
									  }else if(validationConfig['validationType'] == 'phone_number' ||
									           validationConfig['validationType'] == 'social_security_number' ||
											   validationConfig['validationType'] == 'zip_code' ||
											   validationConfig['validationType'] == 'currency' ||
											   validationConfig['validationType'] == 'real' ||
											   validationConfig['validationType'] == 'url'){											   	
											   	vOptions['useCharacterMasking'] = options['useCharacterMasking'];									  	   						     											
									  }	 else if (validationConfig['validationType'] == 'required') {
										  vOptions['isRequired'] = true;
									  }
									  //alert(YAHOO.lang.JSON.stringify(validationConfig['vOptions']));

									  var widgetType = rootFields[i]['type'];
									  if (widgetType == 'sbm.textfield') {
											new Spry.Widget.ValidationTextField(path + "_div", validationConfig['validationType'], vOptions);
									  } else if (widgetType == 'sbm.checkbox') {
											new Spry.Widget.ValidationCheckbox(path + "_div", vOptions);	
									  } else if (widgetType == 'sbm.radio') {
											new Spry.Widget.ValidationRadio(path + "_div", vOptions);	
									  }

								   }
					 
					     }catch(error){
                           alert(error);
					     }
			          }
		      }
	 },
	 
	 reset: function(){   
		var handler = this;
	   	var conn = new Ext.data.Connection();
		conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/resetRows.portal',
		         method: 'POST',
		         params: {    
					 fiid: this.fiid,
	  	             businessObjectName: this.businessObjectName,
					 dataslotName: this.businessObjectName					 
		         },
				 success: function(req,opt){
                    handler.init();								
				 },
                 failure: function(req,opt){                    							            
				 }
		});	
	 }
	 
}

function RepeatSectionObserver(config){
	 Ext.apply(this, config);
     this.addEvents(
        /**
         * @event disable
         * Fires after the component is disabled.
	     * @param {Ext.Component} this
	     */
        'detailrender',
        'masterrender',
        'addrow',
        'editrow',			
        'deleterow',
        'updaterow',
        'loadrowdata',
        'cancel'         
    );
    //this.getId();
    Ext.ComponentMgr.register(this);
    RepeatSectionObserver.superclass.constructor.call(this);
}
Ext.extend(RepeatSectionObserver, Ext.util.Observable,{});

function BOSectionObserver(config){
	 Ext.apply(this, config);
     this.addEvents(
      /**
       * @event disable
       * Fires after the component is disabled.
	     * @param {Ext.Component} this
	     */        
        'loaddata',
        'render',
        'update'         
    );
    //this.getId();
    Ext.ComponentMgr.register(this);
    BOSectionObserver.superclass.constructor.call(this);
}
Ext.extend(BOSectionObserver, Ext.util.Observable,{});


function GridRepeatSection(businessObjectName, path, className, title, fields,repeatSectionFields, configOptions){
		  this.businessObjectName = businessObjectName;
		  this.path = path;
		  this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);
		  this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		  this.className = className;
		  this.configOptions = configOptions;
		  this.fields = fields;		  
		  this.repeatSectionFields = repeatSectionFields;
		  this.fieldsCache = new Array();
		  this.title = (typeof title == 'undefined' || title.length == 0) ? "" : title;	      
		  this.configOptions = configOptions;
		  this.collapsible = (typeof this.configOptions != 'undefined' && typeof this.configOptions['collapsible'] != 'undefined' && this.configOptions['collapsible'] == 'true') ? true : false;      
	      this.bodyStyle = (typeof this.configOptions != 'undefined' && typeof this.configOptions['bodyStyle'] != 'undefined') ? this.configOptions['bodyStyle'] : '';        
          if(typeof this.configOptions != 'undefined' && typeof this.configOptions['parentIndexedNestedPath'] != 'undefined')
		      this.parentIndexedNestedPath = this.configOptions['parentIndexedNestedPath'];
		  this.editable = (typeof this.configOptions != 'undefined' && typeof this.configOptions['editable'] != 'undefined') ? this.configOptions['editable'] : true;
		  this.numberOfRowsPerPage = (typeof this.configOptions != 'undefined' && typeof this.configOptions['numberOfRowsPerPage'] != 'undefined') ? this.configOptions['numberOfRowsPerPage'] : Bm.RepeatSection.pageSize;
		  this.currentPage = 0;
		  this.predicatePath = new NestedPathPredicate(this.parentIndexedNestedPath,this.path,0);
	      this.masterDivId = path+'_master';
	      this.detailDivId = path+'_detail';
	      this.mainDivId = path;
		  this.businessObject = eval(businessObjectName);
		  this.observer = new RepeatSectionObserver({id:this.path});
		  this.fieldValidations = new Array();
		  this.validationCache = {};
		  if(Bm.RepeatSection.debug)
	 	     YAHOO.BM.Logger.debug("GridRepeatSection:\n"+"path:"+this.path+"\nparentIndexedNestedPath:"+this.parentIndexedNestedPath+
			    "\nlastPath:"+this.lastSubPath+
		        "\nconfigOptions"+YAHOO.lang.JSON.stringify(configOptions));
		 this.currentPage = 0;
	     this.init();
}


GridRepeatSection.constructor = GridRepeatSection;
GridRepeatSection.prototype = {	 
   init: function(){		 
         /*this.panel = Ext.create('Ext.panel.Panel',{
			id: this.mainDivId+'panel',
	 		renderTo: this.mainDivId,
			contentEl:this.mainDivId + '_body',
			autoScroll:true,
			autoShow: true,
			border: true,
			margins: '5 5 5 5',
			bodyStyle:'padding:5px;',
			title: this.title,
			collapsible: this.collapsible,
			autoWidth: true, 
			autoHeight: true
		 });*/		
		 this.initEvents();
         var indexedRowNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
		 this.observer.fireEvent('masterrender',indexedRowNestedPath);		 
   },

   initEvents : function(){
        this.observer.on('masterrender', this.onMasterRender, this);
        this.observer.on('detailrender', this.onDetailRender, this);
		this.observer.on('addrow', this.onAddRow, this);
        this.observer.on('editrow', this.onEditRow, this); 
		this.observer.on('deleterow', this.onDeleteRow, this);  
		this.observer.on('loadrowdata', this.onLoadRowData, this);
        this.observer.on('updaterow', this.onUpdateRow, this);
		this.observer.on('cancel', this.onCancel, this);		
   },

   onMasterRender: function(isNewRow){
		   var indexedNestedPath = this.getIndexedNestedPath();
		   if(typeof this.grid == 'undefined' && typeof this.store == 'undefined'){                
				this.createGrid(indexedNestedPath);
		   } else {                				 
				var params = {businessObjectName:this.businessObjectName, indexedNestedPath:indexedNestedPath,
			           		   dataslotName:this.businessObjectName,className:this.repeatSectionClass};				 
				this.lastStoreParams.indexedNestedPath = indexedNestedPath;				
		        YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
				YAHOO.util.Dom.setStyle(this.path+'_detail','display','none');
				//alert(YAHOO.lang.JSON.stringify(this.store.lastOptions)); 
				if(typeof this.lastStoreParams.page != 'undefined'){
					params.page = this.lastStoreParams.page;
					params.start = this.lastStoreParams.start;
					params.limit = this.lastStoreParams.limit;					
				}
				   
				this.store.load({params:params});					 
           }
		   

	},
   
   onDetailRender: function(rowIndex,rowData,isNewRow){	      	  
		  if(Bm.RepeatSection.debug) {
			  YAHOO.BM.Logger.debug("onDtailRender of GridRepeatSection has been fired!");
			  YAHOO.BM.Logger.debug("-rowIndex:" + rowIndex + ", rowData:" +YAHOO.lang.JSON.stringify(rowData) + ", isNewRow:" + isNewRow);
		  }
		  		  
		  if(typeof this.detailPanel == 'undefined' &&  typeof  Ext.getCmp(this.mainDivId + '_panel') == 'undefined'){				  
				  	this.detailPanel = Ext.create('Ext.panel.Panel',{
				  		id: this.mainDivId + '_panel',
				  		renderTo: this.detailDivId,
						contentEl:this.detailDivId +'_body',
				  		autoScroll: true,
				  		autoShow: true,
				  		border: true,
				  		margins: '0 0 0 0',
				  		autoWidth: true,
				  		//height: '100%',
				  		tbar: [{
							scope: this,
				  			text: getLocalizedString('Update'),
				  			iconCls: 'updateIcon',
				  			handler: function(){
				  				//handler.observer.fireEvent('updaterow');
								this.observer.fireEvent('updaterow');
				  			},
				  			scope: this,
				  			pressed: true,
				  			enableToggle: true,
				  			id: 'update'
				  		}, '-', {
				  			text: getLocalizedString('Cancel'),
				  			handler: function(){
				  				//handler.observer.fireEvent('cancel');
								this.observer.fireEvent('cancel');
				  			},
				  			scope: this,
				  			pressed: true,
				  			iconCls: 'cancelIcon'
				  		}]
				  	});
				 			  
		  }
		  
		 // initialize the fields inside line item detail
		 for(var i=0; i<this.repeatSectionFields.length;i++){	
		    if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.debug("Initializing field rowIndex:" + rowIndex + ", rowData:" +YAHOO.lang.JSON.stringify(rowData) + ", isNewRow:" + isNewRow);
		    	 	
			var field = new Bm.RepeatSection.Field(this.repeatSectionFields[i]);
			field.initComponent();
			this.fieldsCache[this.fieldsCache.length] = field;			
		 }
		  
         
		  for(var i =0; i< this.fields.length;i++){
            var name = this.fields[i]['name'];
				  var className = this.fields[i]['className'];
			      if(typeof name != 'undefined' && name != 'edit' && name != 'delete'){
				      var path =  this.fields[i]['path'];
					  if(typeof path != 'undefined'){
					  	  
					       try {
						   	if (typeof rowData != 'undefined' && className == 'java.lang.Boolean') {
								document.getElementById(path).checked = rowData[name]
								
							}else if(typeof rowData != 'undefined' && className == 'java.sql.Timestamp') {									
									var value ='';
									value = rowData[name];								
									if (typeof this.fields[i]['dateOnly'] != 'undefined' && this.fields[i]['dateOnly'] == 'true') {									
										var date = sbm.convertDate(value, this.fields[i]['jsCalendarDateFormat']);																									
										document.getElementById(path).value = sbm.formatDate(date, this.fields[i]['dateOnlyFormat']);
										
									}else {
										document.getElementById(path).value = value;																	
									}
									
									//if (typeof rowData[name] != 'undefined') value = rowData[name];
									//document.getElementById(path).value = value
								
														    
														
						    }else{			
							  document.getElementById(path).value = (typeof rowData == 'undefined' || typeof rowData[name] == 'undefined') ? '' : rowData[name];
							}
				           }catch(error){ alert(error);}
					  }
			      }
		 }
      
         // initialize validations
		 for(var i =0; i< this.fields.length;i++){
			     var path = this.fields[i]['path'];
		         var name = this.fields[i]['name'];
				 var label = this.fields[i]['label'];
				 var args = this.fields[i]['args'];
				 var type = this.fields[i]['className'];
				 var validationConfig = Bm.RepeatSection.getValidationRules(path, type,args);
				 try {
					
					    if(validationConfig['validationType'] != null && validationConfig['validationType'] != 'none'){
                               var validation;					           							   							   
							   
							   if(typeof this.validationCache[path] == 'undefined'){
									var controlType = undefined;	
										
									// get the control type
									for (var rsIndex=0; rsIndex < this.repeatSectionFields.length; rsIndex++) {
										if (path == this.repeatSectionFields[rsIndex].path) {
											controlType = this.repeatSectionFields[rsIndex].type;
											break;
										}
									}

									if (controlType == 'checkbox' || controlType == 'sbm.checkbox') {
										validation = new Spry.Widget.ValidationCheckbox(path+"_div", validationConfig['vOptions']);	
								    } else if (controlType == 'radio' || controlType == 'sbm.radio') {
										validation = new Spry.Widget.ValidationRadio(path+"_div", validationConfig['vOptions']);
								    }  else {
										validation = new Spry.Widget.ValidationTextField(path+"_div", validationConfig['validationType'], validationConfig['vOptions']);									   
									}
									
									this.fieldValidations[this.fieldValidations.length] = validation;
									this.validationCache[path] = validation;
					           }
                        }
					          
				 }catch(error){
                     alert(error);
			     }				   		 
		 }  
         
         var rootHandler = eval(this.businessObjectName);    
		 var boSections = eval(this.businessObjectName + '.boSections');
		 if(typeof boSections != 'undefined' && typeof boSections[this.path] != 'undefined'){
		      var boSections = boSections[this.path];		  
              for(var i=0; i<boSections.length;i++){
                  var boSectionPath = boSections[i]['path'];
                  var boSectionClassName = boSections[i]['className'];
                  var boSectionClassTitle = boSections[i]['title'];
                  var boSectionCollapsible = boSections[i]['collapsible'];
                  var boSectionBodyStyle = boSections[i]['bodyStyle'];
                  var rowIndexedNestedPath = this.getRowIndexedNestedPath(rowIndex);	
                  //alert("boSectionPath:"+boSectionPath+"\nboSectionClassName:"+boSectionClassName);
                  if(typeof rootHandler.boSectionsCache[boSectionPath] == 'undefined'){              	   
                         var bosection = new BOSection(this.businessObjectName, boSectionPath, boSectionClassName, boSectionClassTitle, {parentIndexedNestedPath:rowIndexedNestedPath,isNewRow:(typeof isNewRow != 'undefined' ? isNewRow : false),collapsible:boSectionCollapsible,bodyStyle:boSectionBodyStyle});
						 rootHandler.boSectionsCache[boSectionPath] =  bosection;
				  }else{						  			  
						 var boSection = rootHandler.boSectionsCache[boSectionPath];
                         boSection.setParentIndexedNestedPath(rowIndexedNestedPath,isNewRow);
						 rootHandler.boSectionsCache[boSectionPath] =  boSection;
				  }
			   }
					  
		 }
		 
	 

		 if(typeof this.businessObject.repeatSections[this.path] != 'undefined'){
			     var repeatSections = this.businessObject.repeatSections[this.path];
				   for(var i=0; i< repeatSections.length; i++){
			               var repeatSectionPath =repeatSections[i]['path'];
			               var repeatSectionType = repeatSections[i]['type'];
			               var repeatSectionClass =repeatSections[i]['className'];
						   var repeatSectionTitle =repeatSections[i]['title'];
						   var repeatSectionBodyStyle =repeatSections[i]['bodyStyle'];
						   var repeatSectionCollapsible =repeatSections[i]['collapsible'];
						   var repeatSectionEditable = (typeof repeatSections[i]['editable'] != 'undefined')? repeatSections[i]['editable'] : true;
                           var repeatSectionNumberOfRowsPerPage = repeatSections[i]['numberOfRowsPerPage'];			              

			               var repeatSectionFields = eval(repeatSectionPath);
			               var fields = new Array();
			               fields[0] = {name:'edit',path:repeatSectionPath,dataIndex:'edit'};
			               fields[1] = {name:'delete',path:repeatSectionPath,dataIndex:'delete'};
			 
			               for(var j=0; j<repeatSectionFields.length; j++ ){
				                  var name = "";
				               	  var path = repeatSectionFields[j]['path'];
				               	  var label = repeatSectionFields[j]['label'];
							   	  var className = repeatSectionFields[j]['className'];
							      var args = (typeof repeatSectionFields[j]['args'] == 'undefined') ? {} : repeatSectionFields[j]['args'];
				               	  if(path.lastIndexOf('.') != -1)
					              			name = path.substring(path.lastIndexOf('.')+1,path.length);
                                  if(className == 'java.lang.Boolean')
				                      fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,renderer:Bm.RepeatSection.renderCheckBox};
					              else
				                      fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,sortable: true};
			               } 
						   		                   							   
						   var rowIndexedNestedPath = this.getRowIndexedNestedPath(rowIndex);						   
						   if(typeof rootHandler.repeatSectionsCache[repeatSectionPath] == 'undefined'){
							      var repeanSection = null;
								  if(repeatSectionType == 'grid')
								                      repeatSection = new GridRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable,numberOfRowsPerPage:repeatSectionNumberOfRowsPerPage});
 						          else if(repeatSectionType == 'lineitem')
                                  repeatSection = new LineItemRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable});
								  rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
								                                
						       
						   }else{						  			  
								  var repeatSection = rootHandler.repeatSectionsCache[repeatSectionPath];
                    			  repeatSection.setParentIndexedNestedPath(rowIndexedNestedPath,isNewRow);
								  rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
						   }
						   
		     }
		 }
         this.loadMask.hide();

	},

    onAddRow: function(){
		 if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onAddRow has been fired!");		    	 
		 this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		 this.loadMask.show();
         YAHOO.util.Dom.setStyle(this.path+'_master','display','none');
		 YAHOO.util.Dom.setStyle(this.path+'_detail','display','block'); 
         this.observer.fireEvent('loadrowdata',this.store.getTotalCount(),true);
	},

    onEditRow: function(rowIndex){
		  if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onEditRow has been fired!");		 
		  this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		  this.loadMask.show();
          YAHOO.util.Dom.setStyle(this.path+'_master','display','none');
		  YAHOO.util.Dom.setStyle(this.path+'_detail','display','block'); 
          this.observer.fireEvent('loadrowdata',rowIndex,false);
    },

    onDeleteRow: function(index){
		 if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onDeleteRow has been fired!");	
		 this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		 this.loadMask.show();
                  
		 var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		 var indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+index + ']';
		 var indexedNestedPath = parentNestedPath + this.lastSubPath;	
         var handler = this;
		 var conn = new Ext.data.Connection();
	     conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/deleteRow.portal',
			     method: 'POST',
				 params: {
						  fiid: this.fiid,
                          repeatSectionRoot: this.path,
	  			          businessObjectName: this.businessObjectName,
		                  dataslotName: this.businessObjectName,
		                  indexedNestedPath:indexedRowNestedPath,
				          newRow: ((typeof this.isNewRow == 'undefined' || !this.isNewRow) ? "false" : "true")		  
				 },
				 success: function(req,opt){						  
			                var params = {businessObjectName:handler.businessObjectName,
			                            indexedNestedPath:indexedNestedPath,
			                            dataslotName:handler.businessObjectName,
			                            className:handler.repeatSectionClass,
					                    newRow: ((typeof this.isNewRow == 'undefined' || !this.isNewRow) ? "false" : "true")};
				 		     handler.store.load({params:params});	
						     handler.loadMask.hide();
				},
                failure: function(req,opt){	
				}

		 });
	},

    onLoadRowData: function(rowIndex,isNewRow){	     
	     if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onLoadRowData has been fired!\n rowIndex:"+rowIndex+", isNewRow:"+isNewRow);	
	        
		 this.rowIndex = rowIndex;		  
		 if(isNewRow){
              this.observer.fireEvent('detailrender',rowIndex);	 
		 }else {			  
	          var conn = new Ext.data.Connection();
		      conn.request({
	                 	url: getContextPath()+'/bpmportal/common/repeatsection/getRow.portal',
				        method: 'POST',
					    scope: this,
						params: {
							fiid: this.fiid,
	                        repeatSectionRoot: this.path,
		  			        businessObjectName: this.businessObjectName,
			                dataslotName: this.businessObjectName,
			                indexedNestedPath: this.getRowIndexedNestedPath(rowIndex)						    
					    },
					    success: function(req,opt){
							if(Bm.RepeatSection.debug) 			  
			                   YAHOO.BM.Logger.info("onLoadRowData server response:"+req.responseText);						   
							var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
		        		    var rowData = retJsonValues['row']; 
			                this.observer.fireEvent('detailrender',rowIndex,rowData,isNewRow);	
				        },
                        failure: function(req,opt){
						    this.loadMask.hide();
				        }
			        });
		   }
        
	 },
 
    onUpdateRow: function(){		 
		 var rowIndex = this.rowIndex;
         var isValid = true;
		 var handler = this;
		 for(var i=0; i<this.fieldValidations.length;i++){			     
             if(!this.fieldValidations[i].validate()){
				 isValid = false;
				 break;
			 }
		 }

         if(!isValid) {
		        Bm.RepeatSection.showValidationFailedMessage(this.businessObjectName,this.fieldValidations);
		 } else {
				var fields = new Array(); 		 
				for(var i=0; i<this.fields.length;i++){
		               var name = this.fields[i]['name'];
					   var className = this.fields[i]['className'];	
					   if(name != 'edit' && name != 'delete' && name != 'drilldown'){
						  var path =  this.fields[i]['path'];
						  if(typeof path != 'undefined'){
							      var value = (className == 'java.lang.Boolean') ? document.getElementById(path).checked : document.getElementById(path).value;
					              fields[fields.length] = {name:name,value:value};
						  }
					}
				}
                 
				var handler = this;
				var rowIndexedNestedPath = this.getRowIndexedNestedPath(rowIndex);		 
				var conn = new Ext.data.Connection();
				conn.request({
                        url: getContextPath()+'/bpmportal/common/repeatsection/updateRow.portal',
					    method: 'POST',
						scope: this,
					    params: {
								fiid: this.fiid,
                               fields: YAHOO.lang.JSON.stringify(fields),
	  						   repeatSectionRoot :this.path,
  						       businessObjectName: this.businessObjectName,
		                       dataslotName: this.businessObjectName,
		                       indexedNestedPath:rowIndexedNestedPath,
							   newRow:this.isNewRow	 
						},
					    success: function(req,opt){
							   if(Bm.RepeatSection.debug) 			  
			                     YAHOO.BM.Logger.error("Server Response Successfully updated the row.");	
					           var rootHandler = eval(handler.businessObjectName);
					           var boSections = eval(handler.businessObjectName +'.boSections');	
				               if(typeof boSections != 'undefined' && typeof boSections[handler.path] != 'undefined'){
					                for(var i=0; i<boSections[handler.path].length;i++){
                                           var boSectionPath = boSections[handler.path][i]['path'];
						  				   var boSection =  rootHandler.boSectionsCache[boSectionPath];
 					        		       boSection.observer.fireEvent('update');       
		                            }
				               }				             	
							   handler.observer.fireEvent('masterrender',handler.isNewRow);
					   },
                       failure: function(req,opt){							  
							  
					   }

				});		
		   }
	 },

    onCancel: function(){
		 var rowIndexedNestedPath = this.rowIndexedNestedPath;
         YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
		 YAHOO.util.Dom.setStyle(this.path+'_detail','display','none');
		 var indexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
		 this.observer.fireEvent('masterrender',indexedNestedPath);
	},

    setParentIndexedNestedPath: function(parentIndexedNestedPath,isNewRow){
         this.parentIndexedNestedPath= parentIndexedNestedPath;
		 this.observer.fireEvent('masterrender',isNewRow);
    },

    convertColumns: function(){
		 var _p = this.path.replace(/\./g,'_');
		 var columns = new Array();
		 columns.push(Ext.create('Ext.grid.RowNumberer'));
		 if (this.editable) {
		 	columns.push({
		 		id: _p + '_edit',
		 		text: 'Edit',
		 		header: 'Edit',
		 		width: 50,
		 		sortable: false,
		 		renderer: this.renderer,
		 		dataIndex: 'edit',
		 		name: _p + '_edit'
		 	});
		 	columns.push({
		 		id: _p + '_delete',
		 		text: 'Delete',
		 		header: 'Delete',
		 		width: 50,
		 		sortable: false,
		 		renderer: this.renderer,
		 		dataIndex: 'delete',
		 		name: _p + '_delete'
		 	});
		 }		 
		 for(var i=2; i< this.fields.length;i++){				   
					var id = this.fields[i]['path'].replace(/\./g,'_');	
					if(this.editable)
			       	   columns.push({id: id,text:this.fields[i]['label'],flex:1,dataIndex:this.fields[i]['dataIndex'],
					   name: this.fields[i]['name'],sortable:this.fields[i]['sortable'],align: 'center',header:this.fields[i]['label']});
			        else if(this.fields[i]['dataIndex'] != 'drilldown')				
			    	   columns.push({id: id,text:this.fields[i]['label'],flex:1,dataIndex:this.fields[i]['dataIndex'],
					   name: this.fields[i]['name'],sortable:this.fields[i]['sortable'],align: 'center',header:this.fields[i]['label']});		
					
		 }	
		 return columns;
		 
	},
	
	createGrid: function(indexedNestedPath){
          var url = getContextPath()+"/bpmportal/common/repeatsection/getRows.portal";
		  var params = {		fiid: this.fiid,
								businessObjectName:this.businessObjectName,
			           			  indexedNestedPath:indexedNestedPath,
			           			  dataslotName:this.businessObjectName,
			           				className:this.repeatSectionClass,
			           				page: 0,
									pageSize: this.numberOfRowsPerPage,
			           				start:0,
			           				limit:this.numberOfRowsPerPage
		   };		     	
		  
		   var columns = this.convertColumns();		       
		   var storeId = this.masterDivId.replace(/\./g,'_')+'_storeId';		  
		   this.store = Ext.create('Ext.data.JsonStore', {
		   		storeId: storeId,		   		
		   		proxy: {
		   			type: 'ajax',
		   			url: url,
					reader: {
						 type: 'json',
					 	 root: 'data',
						 totalProperty: 'totalCount'
					}
				},
				remoteSort: false,
				autoDestroy: true,
				fields: columns,
				pageSize: this.numberOfRowsPerPage
			});			
					    	
		    function storeListener(store, options) {				
                options.params['page'] = ((options.params['start'] / options.params['limit']) ) + "" ;
                Ext.apply(params, options.params);						
                params.page = ((options.params['start'] / options.params['limit']) ) + "" ;               
                this.lastStoreParams = params;
				store.lastStoreParams = params;
				return true;
            };
        
            this.store.on('beforeload', storeListener,this);
			this.store.on('beforeload', function(){
				// for debugging
				
			},this);
		    this.store.path = this.path
            this.store.businessObjectName = this.businessObjectName;		    
			document.getElementById(this.masterDivId).innerHTML = '';		
			
			if (this.editable) {				
				this.gridPanel = Ext.create('Ext.grid.Panel', {				
						id:  this.masterDivId+"_id",
						store: this.store,
						columns: columns,
						loadMask: true,
						autoWidth: true,
						autoHeight: true,
						scope: this,
						renderTo: this.masterDivId,						
						title: this.title,
						frame: true,
						viewConfig: {							
							emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGridWithAddList') + '</div>'
						},
						
						dockedItems: [{
						  id: this.masterDivId +'toolbar',
						  xtype: 'toolbar',
						  dock: 'top',
						  items: [{
						   	 id: this.masterDivId + '_create',
							 name: this.masterDivId + '_create',
							 text: getLocalizedString('AddToList'),
							 xtype: 'button',
							 enableToggle: true,
							 scope: this,
							 toggleHandler: function(){
							  	this.observer.fireEvent('addrow');
							 }
						   }]
						}, Ext.create('Ext.toolbar.Paging', {
							dock: 'bottom',
							pageSize: this.numberOfRowsPerPage,
							store: this.store
						})
						]				
						
				
				  });
				  
				  
			} else {
				
				this.gridPanel = Ext.create('Ext.grid.Panel', {		
						store: this.store,
						columns: columns,
						loadMask: true,
						autoHeight: true,
						renderTo: this.masterDivId,
						title: this.title,
						frame: true,
						viewConfig: {
							//autoFill: true,
							//forceFit: true,
							//deferEmptyText: false,
							emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGrid')+'</div>'
						},
						
						
						dockedItems: [Ext.create('Ext.toolbar.Paging', {
							dock: 'bottom',
							store: this.store
						})
						]
					});
					
			}
		    this.store.load({params:params});
		    //this.gridPanel.render();
   },
     
    getIndexedNestedPath: function(){
          var indexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return indexedNestedPath;
    },

    getRowIndexedNestedPath: function(rowIndex){
          var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath+'['+rowIndex+']';
          return rowIndexedNestedPath;
   },
   
   reset: function(){   	    
		/////////////
		/*
		var params = {businessObjectName:this.businessObjectName, indexedNestedPath:this.getIndexedNestedPath(),
			           		   dataslotName:this.businessObjectName,className:this.repeatSectionClass,reset:'true'};				 
				this.store.baseParams.indexedNestedPath = this.getIndexedNestedPath();				
		        YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
				YAHOO.util.Dom.setStyle(this.path+'_detail','display','none'); 
				this.store.reload({params:params});*/
		/*function resetCallback(){		
             var indexedRowNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
		     this.observer.fireEvent('masterrender',indexedRowNestedPath);			
	    }
	   	var conn = new Ext.data.Connection();
		conn.request({
                 url: '/sbm/bpmportal/common/repeatsection/resetRows.portal',
		         method: 'POST',
		         params: {                                            
	  	             businessObjectName: this.businessObjectName,
					 indexedNestedPath:this.getIndexedNestedPath()
		         },
				 success: function(req,opt){
                    resetCallback();								
				 },
                 failure: function(req,opt){
                    Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p>");							            
				 }
		});*/		
		
		
		
	   	
	 	
   }, 
   
   renderer: function(value, metadata, record, rowIndex, colIndex, store){    
	var path = store.path;
	var businessObjectName = store.businessObjectName;
	var handler = eval(businessObjectName + '_handler');
        var repeatSection = handler.repeatSectionsCache[path];
		  
	var page;
	if (typeof store.lastStoreParams.page != 'undefined') {
		this.currentPage= Number(store.lastStoreParams.page);		
	}	
	var index = store.lastStoreParams.page * store.lastStoreParams.pageSize +  rowIndex;
	if(colIndex == 1){
         return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                 "</a></div>";
    }else if(colIndex == 2){
         return "<div style='text-align:center'><a class=\"DeleteImage\" href=\"javascript:Bm.RepeatSection.deleteRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                "</a></div>";
    }else {
         return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                 "</a></span></div>";
	}
   }
     
	 

};
/**
 * This Object encapsulates the operation and controlling 
 *  the states for rendering line item repeat section.
 * 
 * @param {Object} businessObjectName
 * @param {Object} path
 * @param {Object} className
 * @param {Object} title
 * @param {Object} fields
 * @param {Object} configOptions
 */


function LineItemRepeatSection(businessObjectName, path, className, title, fields,repeatSectionFields, configOptions){	    
		this.businessObjectName = businessObjectName;
		this.path = path;
		this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);		  
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		this.className = className;
		this.configOptions = configOptions;		
		this.fields = fields;
		this.repeatSectionFields = repeatSectionFields;
		this.fieldsCache = new Array();
		this.fieldsMap = {};		
		this.title = (typeof title == 'undefined' || title.length == 0) ? "" : title;	      
		this.configOptions = configOptions;
	    this.collapsible = (typeof this.configOptions != 'undefined' && typeof this.configOptions['collapsible'] != 'undefined' && this.configOptions['collapsible'] == 'true') ? true : false;      
	    this.bodyStyle = (typeof this.configOptions != 'undefined' && typeof this.configOptions['bodyStyle'] != 'undefined') ? this.configOptions['bodyStyle'] : '';        
        if(typeof this.configOptions != 'undefined' && typeof this.configOptions['parentIndexedNestedPath'] != 'undefined')
		      this.parentIndexedNestedPath = this.configOptions['parentIndexedNestedPath'];  
	    this.editable = (typeof this.configOptions != 'undefined' && typeof this.configOptions['editable'] != 'undefined') ? this.configOptions['editable'] : true; 
		this.predicatePath = new NestedPathPredicate(this.parentIndexedNestedPath,this.path,0);
	    this.masterDivId = path+'_master';
	    this.detailDivId = path+'_detail';
	    this.mainDivId = path;
		this.businessObject = eval(businessObjectName);
		this.observer = new RepeatSectionObserver({id:this.path});
		this.fieldValidations = new Array();
		this.validationCache = {};
		if(Bm.RepeatSection.debug)
	 	     YAHOO.BM.Logger.debug("LineItemRepeatSection:\n"+"path:"+this.path+"\nparentIndexedNestedPath:"+this.parentIndexedNestedPath+
			    "\nlastPath:"+this.lastSubPath+
		        "\nconfigOptions"+YAHOO.lang.JSON.stringify(configOptions));
	    
	    this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		this.init();
}


LineItemRepeatSection.constructor = LineItemRepeatSection;
LineItemRepeatSection.prototype = {
   init: function(){			 
		 /*this.panel = Ext.create('Ext.panel.Panel',{
			id: this.mainDivId+'_panel',
	 		renderTo: this.mainDivId,
			contentEl: this.mainDivId + '_body',			
			autoScroll:true,
			autoShow: true,
			border: true,
			margins: '5 5 5 5',
			bodyStyle:'padding:3px;',
			title: this.title,
			collapsible:this.collapsible,
			autoWidth: true, 
			autoHeight:true
		 });*/		 
		 // initialize the fields inside line item detail
		 for(var i=0; i<this.repeatSectionFields.length;i++){		 	
			var field = new Bm.RepeatSection.Field(this.repeatSectionFields[i]);
			field.initComponent();
			this.fieldsCache[this.fieldsCache.length] = field;			
			this.fieldsMap[field.getPath()] = field;
		 }
		 this.initEvents();		 
         var indexedRowNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath; 
		 
		 try {
		 	    if (typeof this.navToolbar == 'undefined' && typeof this.detailpanel == 'undefined') 
		 		  this.createToolbars();
			 	this.observer.fireEvent('loadnavtoolbardata', indexedRowNestedPath);
		 }catch(error){
		 	alert(error);
		 }
		 
   },

   initEvents : function(){
        this.observer.on('loadnavtoolbardata', this.onLoadNavToolBarData, this);
        this.observer.on('detailrender', this.onDetailRender, this);
		this.observer.on('addrow', this.onAddRow, this);
		this.observer.on('newrow', this.onNewRow, this);
		this.observer.on('nextrow', this.onNextRow, this);
		this.observer.on('previousrow', this.onPreviousRow, this);
	    this.observer.on('editrow', this.onEditRow, this); 
		this.observer.on('deleterow', this.onDeleteRow, this);  
		this.observer.on('loadrowdata', this.onLoadRowData, this);
        this.observer.on('updaterow', this.onUpdateRow, this);	
    },
     
   createToolbars: function(){
		   //this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		   //this.loadMask.show();		   
		   //this.navToolbar = new Bm.RepeatSection.NavigationToolbar({repeatSection: this});		  
		   if (this.editable) {		   	
		   	this.detailpanel = Ext.create('Ext.panel.Panel',{
		   		id: this.detailDivId + '_panel',
		   		renderTo: this.detailDivId,
				contentEl: this.detailDivId + '_body',
		   		title: this.title,
				collapsible:this.collapsible,
				autoScroll: true,				
		   		autoShow: true,
		   		autoWidth: true,
				autoHeight: true,
				border: true,
		   		margins: '5 5 5 5',
		   		bodyStyle: 'padding:3px',
		   		//width: '100%',
		   		//height: '100%',
		   		dockedItems: [{
					id: this.masterDivId +'toolbar',
						  xtype: 'toolbar',
						  dock: 'top',
						  items: [{
						  	id:  this.detailDivId + '_panel_add',
						  	text: getLocalizedString('Add'),
		   					iconCls: 'addIcon',
		   					handler: function(){
		   						this.observer.fireEvent('addrow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					enableToggle: true
						}, {
							id:  this.detailDivId + '_panel_update',
		   					text: getLocalizedString('Update'),
		   					iconCls: 'updateIcon',
		   					handler: function(){								
		   						this.observer.fireEvent('updaterow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					enableToggle: true
		   					
		   				},  {
							id:  this.detailDivId + '_panel_delete',
		   					text: getLocalizedString('Delete'),
		   					handler: function(){
		   						this.observer.fireEvent('deleterow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					iconCls: 'removeIcon'
		   				}]
					
				},{
					id: this.detailDivId + '_navtoolbar',
					xtype: 'navtoolbar',
					repeatSection: this,
					dock: 'bottom'
				}
					
					
				]
				
				/*tbar: [{
		   			text: getLocalizedString('Add'),
		   			iconCls: 'addIcon',
		   			handler: function(){
		   				this.observer.fireEvent('addrow');
		   			},
		   			scope: this,
		   			pressed: true,
		   			enableToggle: true
		   		}, '-', {
		   			text: getLocalizedString('Update'),
		   			iconCls: 'updateIcon',
		   			handler: function(){
		   				this.observer.fireEvent('updaterow');
		   			},
		   			scope: this,
		   			pressed: true,
		   			enableToggle: true,
		   			id: 'update'
		   		}, '-', {
		   			text: getLocalizedString('Delete'),
		   			handler: function(){
		   				this.observer.fireEvent('deleterow');
		   			},
		   			scope: this,
		   			pressed: true,
		   			iconCls: 'removeIcon'
		   		}],
		   		bbar: this.navToolbar*/
		   	});
		   }else {
		   	 this.detailpanel = Ext.create('Ext.panel.Panel',{
		   		id: this.detailDivId + '_panel',
				contentEl: this.detailDivId + '_body',
		   		renderTo: this.detailDivId,
				title: this.title,
				collapsible:this.collapsible,
		   		autoScroll: true,
		   		autoShow: true,
		   		border: true,
		   		margins: '5 5 5 5',
		   		bodyStyle: 'padding:3px',
		   		autoWdth:true,
				autoHeight: true,		   		
		   		dockedItems: [{
					id: this.masterDivId +'toolbar',
						  xtype: 'toolbar',
						  dock: 'top',
						  items: [{
						  	text: getLocalizedString('Add'),
		   					iconCls: 'addIcon',
		   					handler: function(){
		   						this.observer.fireEvent('addrow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					enableToggle: true
						}, '-', {
		   					text: getLocalizedString('Update'),
		   					iconCls: 'updateIcon',
		   					handler: function(){
		   						this.observer.fireEvent('updaterow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					enableToggle: true,
		   					id: 'update'
		   				}, '-', {
		   					text: getLocalizedString('Delete'),
		   					handler: function(){
		   						this.observer.fireEvent('deleterow');
		   					},
		   					scope: this,
		   					pressed: true,
		   					iconCls: 'removeIcon'
		   				}]
						}]
		   	});
			
		   }
		   this.navToolbar = Ext.getCmp(this.detailDivId + '_navtoolbar');					 
		  
	 },

   onLoadNavToolBarData: function(indexedRowNestedPath){          
          var conn = new Ext.data.Connection();
	      conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/getRows.portal',
			     method: 'POST',
				 scope: this,
				 params: {
				 	businessObjectName: this.businessObjectName,
				 	indexedNestedPath: indexedRowNestedPath,
				 	dataslotName: this.businessObjectName,
				 	className: this.repeatSectionClass
				 },
				 success: function(req,opt){
				     var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
	        	     var rowData = retJsonValues['data']; 
					 var totalCount = parseInt(retJsonValues['totalCount']);			              
                     this.rowData = rowData;			  
								  
					 var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
			 		 if(typeof this.activeItem == 'undefined') this.activeItem = 0;
					 this.indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+this.activeItem + ']';	
		             this.navToolbar.setItemData(((totalCount == 0) ? 1  : totalCount),((totalCount == 0) ? 1 : totalCount));					 
		             //if(this.totalCount != 0)
			             this.observer.fireEvent('loadrowdata',indexedRowNestedPath,false);
						 this.loadMask.hide();
				     },
                     failure: function(req,opt){                          
						  this.loadMask.hide();
				     }

				 });        

	 },

   onAddRow: function(){   	    
         function showResult(btn){
                if(btn == 'yes'){
				      this.loadMask.hide();
                      this.observer.fireEvent('updaterow',true);
 			     }else if(btn == 'no'){
                      var parentNestedPath = (typeof handler.parentIndexedNestedPath != 'undefined') ? (handler.parentIndexedNestedPath +'.') : '';		 
		              indexedRowNestedPath = parentNestedPath + handler.lastSubPath + '['+(handler.navToolbar.totalCount) + ']';		
                      this.observer.fireEvent('detailrender',indexedRowNestedPath);
					  this.navToolbar.setItemData(handler.navToolbar.totalCount+1, handler.navToolbar.totalCount+1);
			     }      
         };
		          
         if(this.isDirty()) {
		 	this.observer.fireEvent('updaterow',true);
   		    /*Ext.MessageBox.show({
   				title: 'Save Changes?',
   				msg: 'You are leaving the current line item that has unsaved changes. <br />Would you like to save your changes?',
   				buttons: Ext.MessageBox.YESNOCANCEL,
   				fn: showResult,
   				//animEl: 'mb4',
				icon: Ext.MessageBox.QUESTION
			});*/
		}else{
		    var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		    indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+(this.navToolbar.totalCount) + ']';		
            this.observer.fireEvent('detailrender',indexedRowNestedPath);
			this.navToolbar.setItemData(this.navToolbar.totalCount+1, this.navToolbar.totalCount+1);
		}		
   },

   onEditRow: function(index){
		 this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		 this.loadMask.show();
         YAHOO.util.Dom.setStyle(this.path+'_master','display','none');
		 YAHOO.util.Dom.setStyle(this.path+'_detail','display','block'); 
         var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		 indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+index + ']';		
         this.observer.fireEvent('loadrowdata',indexedRowNestedPath,false);
   },

   onDeleteRow: function(){
		this.loadMask.show();
        var index = this.navToolbar.pageActiveItem-1;             
		var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		var indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+index + ']';
		var indexedNestedPath = parentNestedPath + this.lastSubPath;	
        var handler = this;
		var conn = new Ext.data.Connection();
	    conn.request({
                     url: getContextPath()+'/bpmportal/common/repeatsection/deleteRow.portal',
			         method: 'POST',
				     params: {
						 fiid: this.fiid,
                        repeatSectionRoot: this.path,
	  			        businessObjectName: this.businessObjectName,
		                dataslotName: this.businessObjectName,
		                indexedNestedPath:indexedRowNestedPath				              
				     },
				     success: function(req,opt){						  
			            var parentNestedPath = (typeof handler.parentIndexedNestedPath != 'undefined') ? (handler.parentIndexedNestedPath +'.') : '';		 
		                var newIndex = (index == 0)? 0 : index-1;
		                indexedRowNestedPath = parentNestedPath + handler.lastSubPath + '['+newIndex+ ']';		
                        handler.navToolbar.setItemData(handler.navToolbar.totalCount-1, newIndex+1);
                        handler.observer.fireEvent('loadrowdata',indexedRowNestedPath,false);
					 },
                     failure: function(req,opt){                          
						  handler.loadMask.hide();
				     }

				 });
	},

   onUpdateRow: function(forwardToAdd){		                                   
	   var repeatSectionPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
       var indexedRowNestedPath = repeatSectionPath+'['+(this.navToolbar.pageActiveItem-1)+']';		 
       var isValid = true;
	   
	   for(var i=0; i<this.fieldValidations.length;i++){
                 if(!this.fieldValidations[i].validate()){
					   isValid = false;
					   break;
				 }
		}
        
		if(!isValid) {
		        Bm.RepeatSection.showValidationFailedMessage(this.businessObjectName,this.fieldValidations);
        } else {
				var fields = new Array(); 		 
				for(var i=0; i<this.fields.length;i++){
		               var name = this.fields[i]['name'];	
					   var className = this.fields[i]['className'];
					   if(name != 'edit' && name != 'delete' && name != 'drilldown'){
						     var path =  this.fields[i]['path'];
							 if(typeof path != 'undefined'){
							        var value = (className == 'java.lang.Boolean') ? document.getElementById(path).checked : document.getElementById(path).value;
					                fields[fields.length] = {name:name,value:value};
							 }
					   }
				 }

       				  
				 
				 var masterNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
				 var conn = new Ext.data.Connection();
				 conn.request({
                        url: getContextPath()+'/bpmportal/common/repeatsection/updateRow.portal',
					    method: 'POST',
						scope:this,
					    params: {
							fiid: this.fiid,
                                 fields: YAHOO.lang.JSON.stringify(fields),
	  						     repeatSectionRoot :this.path,
  						         businessObjectName: this.businessObjectName,
		                         dataslotName: this.businessObjectName,
		                         indexedNestedPath:indexedRowNestedPath,
							     newRow:this.isNewRow	 
						},
					    success: function(req,opt){								
								 var rootHandler = eval(this.businessObjectName);
								 var boSections = eval(this.businessObjectName +'.boSections');	
								 
				                 if(typeof boSections != 'undefined' && typeof boSections[this.path] != 'undefined'){
					                   for(var i=0; i<boSections[this.path].length;i++){
                                            var boSectionPath = boSections[this.path][i]['path'];
						  					var boSection =  rootHandler.boSectionsCache[boSectionPath];
 					        		        boSection.observer.fireEvent('update');       
		                               }
				                 }   
								       
								 if(typeof forwardToAdd == 'undefined' || !forwardToAdd){								 	        
								           Ext.Msg.alert("Message","<p>Line Item has been updated successfully.</p>");
									       this.observer.fireEvent('loadnavtoolbardata',repeatSectionPath); 
							     } else {
								 		  var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		                                  indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+(this.navToolbar.totalCount) + ']';		
                                          //this.observer.fireEvent('detailrender',indexedRowNestedPath);
										  this.observer.fireEvent('newrow');
					                      this.navToolbar.setItemData(this.navToolbar.totalCount+1, this.navToolbar.totalCount+1);

								 }
                        },
                        failure: function(req,opt){                               
							   this.loadMask.hide();
						}

				 });		
		}
	 },
	 
   onNewRow: function(){
   	    var repeatSectionPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
        var indexedRowNestedPath = repeatSectionPath+'['+(this.navToolbar.pageActiveItem)+']';		 
		 var conn = new Ext.data.Connection();
	     conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/getRow.portal',
			     method: 'POST',
				 scope: this,
				 params: {
					 fiid: this.fiid,
                          repeatSectionRoot: this.path,
	  			          businessObjectName: this.businessObjectName,
		                  dataslotName: this.businessObjectName,
		                  indexedNestedPath: indexedRowNestedPath,
					      newRow: 'true'	  
				 },
				 success: function(req,opt){
						  var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
	        			  var rowData = retJsonValues['row']; 
			              this.observer.fireEvent('detailrender',indexedRowNestedPath,rowData);	
				        },
                 failure: function(req,opt){                         
						 this.loadMask.hide();
				}

	      });		
	
   },
	
   onLoadRowData: function(indexedRowNestedPath,isNewRow){          
		 var repeatSectionPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
         var indexedRowNestedPath = repeatSectionPath+'['+(this.navToolbar.pageActiveItem-1)+']';
		 var conn = new Ext.data.Connection();
	     conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/getRow.portal',
			     method: 'POST',
				 scope: this,
				 params: {
					 fiid: this.fiid,
                          repeatSectionRoot: this.path,
	  			          businessObjectName: this.businessObjectName,
		                  dataslotName: this.businessObjectName,
		                  indexedNestedPath: indexedRowNestedPath,
					      newRow: (typeof isNewRow != 'undefined' && isNewRow) ? 'true' : 'false' 	  
				 },
				 success: function(req,opt){
						  var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
	        			  var rowData = retJsonValues['row']; 
			              this.observer.fireEvent('detailrender',indexedRowNestedPath,rowData,isNewRow);	
				        },
                failure: function(req,opt){                         
						 this.loadMask.hide();
				}

	      });		
        
	 },

   onDetailRender: function(rowIndexedNestedPath,rowData,isNewRow){              
		    this.rowData = rowData;
			this.rowIndexedNestedPath = rowIndexedNestedPath;
		    this.isNewRow = isNewRow;			
         
		    for(var i =0; i< this.fields.length;i++){
                var name = this.fields[i]['name'];
				var className = this.fields[i]['className'];
			    if(typeof name != 'undefined' && name != 'edit' && name != 'delete'){
				        var path =  this.fields[i]['path'];											
					    if(typeof path != 'undefined'){
							   try{ 								      
									  if (typeof rowData != 'undefined' && className == 'java.lang.Boolean') {
									  	document.getElementById(path).checked = (name in rowData)? rowData[name] : false;
									  }
									  else {
									  		document.getElementById(path).value = (name in rowData) ? rowData[name] : '';
									  }
							 
							    }catch(error){ alert(error);}
						}
			        }
		    }
		    // initialize validations
		    for(var i =0; i< this.fields.length;i++){
			       var path =  this.fields[i]['path'];
		           var name = this.fields[i]['name'];
				   var args = this.fields[i]['args'];
				   var type = this.fields[i]['className'];
				   var validationConfig = Bm.RepeatSection.getValidationRules(path, type,args);
				   try
				   {					
					           if(validationConfig['validationType'] != null && validationConfig['validationType'] != 'none'){
					                var validation;
									if(typeof this.validationCache[path] == 'undefined'){

						                var controlType = undefined;
										// get the control type
										for (var rsIndex=0; rsIndex < this.repeatSectionFields.length; rsIndex++) {
											if (path == this.repeatSectionFields[rsIndex].path) {
												controlType = this.repeatSectionFields[rsIndex].type;
												break;
											}
										}

										if (controlType == 'checkbox' || controlType == 'sbm.checkbox') {
											validation = new Spry.Widget.ValidationCheckbox(path+"_div", validationConfig['vOptions']);	
										} else if (controlType == 'radio' || controlType == 'sbm.radio') {
											validation = new Spry.Widget.ValidationRadio(path+"_div", validationConfig['vOptions']);	
										} else {
											validation = new Spry.Widget.ValidationTextField(path+"_div", validationConfig['validationType'], validationConfig['vOptions']);									   
										}
										
										this.fieldValidations[this.fieldValidations.length] = validation;
										this.validationCache[path] = validation;
					                }			              
					           }
					
				    }catch(error){
                        alert(error);
				    }				  			 
		    }
		    
			var rootHandler = eval(this.businessObjectName);  
		    var boSections = eval(this.businessObjectName + '.boSections');
		    if(typeof boSections != 'undefined' && typeof boSections[this.path] != 'undefined'){
				 var boSections = boSections[this.path];		  
                 for(var i=0; i<boSections.length;i++){
                    var boSectionPath = boSections[i]['path'];
                    var boSectionClassName = boSections[i]['className'];
              		var boSectionClassTitle = boSections[i]['title'];
              		var boSectionCollapsible = boSections[i]['collapsible'];
              		var boSectionBodyStyle = boSections[i]['bodyStyle'];
					var boSectionEditable = (typeof boSections[i]['editable'] != 'undefined')? boSections[i]['editable'] : true;
		            //alert("boSectionPath:"+boSectionPath+"\nboSectionClassName:"+boSectionClassName);		
                    if(typeof rootHandler.boSectionsCache[boSectionPath] == 'undefined'){              	   
                         var bosection = new BOSection(this.businessObjectName, boSectionPath, boSectionClassName, boSectionClassTitle, {parentIndexedNestedPath:rowIndexedNestedPath,isNewRow:(typeof isNewRow != 'undefined' ? isNewRow : false),collapsible:boSectionCollapsible,bodyStyle:boSectionBodyStyle,editable:boSectionEditable});
						 rootHandler.boSectionsCache[boSectionPath] =  bosection;
			        }else{						  			  
						 var boSection = rootHandler.boSectionsCache[boSectionPath];
                   		 boSection.setParentIndexedNestedPath(rowIndexedNestedPath,isNewRow);
						 rootHandler.boSectionsCache[boSectionPath] =  boSection;
				    }
		         }
					  
		   }
		  
		   if(typeof this.businessObject.repeatSections[this.path] != 'undefined'){
			       var repeatSections = this.businessObject.repeatSections[this.path];
				     for(var i=0; i< repeatSections.length; i++){
			               var repeatSectionPath =repeatSections[i]['path'];
			               var repeatSectionType = repeatSections[i]['type'];
			               var repeatSectionClass =repeatSections[i]['className'];
						   var repeatSectionTitle =repeatSections[i]['title'];
						   var repeatSectionBodyStyle =repeatSections[i]['bodyStyle'];
						   var repeatSectionCollapsible =repeatSections[i]['collapsible'];
						   var repeatSectionEditable = (typeof repeatSections[i]['editable'] != 'undefined')? repeatSections[i]['editable'] : true;
			               //alert("repeatSectionPath:"+repeatSectionPath+"\nrepeatSectionType:"+repeatSectionType+"\nrepeatSectionClass:"+repeatSectionClass);
			               var repeatSectionFields = eval(repeatSectionPath);
			               var fields = new Array();
			               fields[0] = {name:'edit',path:repeatSectionPath,dataIndex:'edit'};
			               fields[1] = {name:'delete',path:repeatSectionPath,dataIndex:'delete'};
			 
			               for(var j=0; j<repeatSectionFields.length; j++ ){
				               		var name = "";
				               		var path = repeatSectionFields[j]['path'];
				               		var label = repeatSectionFields[j]['label'];
							   		var className = repeatSectionFields[j]['className'];
							   		var args = (typeof repeatSectionFields[j]['args'] == 'undefined') ? {} : repeatSectionFields[j]['args'];
				                    if(path.lastIndexOf('.') != -1)
					                    name = path.substring(path.lastIndexOf('.')+1,path.length);
				                        fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,sortable: true};
			                        } 
						   		                   							   
						   			var rootHandler = eval(this.businessObjectName);
						            if(typeof rootHandler.repeatSectionsCache[repeatSectionPath] == 'undefined'){
							           var repeanSection = null;
									   //work to do repeatSectionFields are missing
									   var repeatSectionFields = eval(repeatSectionPath);
								       if(repeatSectionType == 'grid')
								          repeatSection = new GridRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable});
 						              else if(repeatSectionType == 'lineitem')
                                          repeatSection = new LineItemRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable});
								     rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
								                                
						       
						   }else{						  			  
								     var repeatSection = rootHandler.repeatSectionsCache[repeatSectionPath];
                     				 repeatSection.setParentIndexedNestedPath(rowIndexedNestedPath,isNewRow);
								     rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
						   }
						   
		      }
		 }		 
		 this.loadMask.hide();

	},  
	
   validate: function(){
	   var isValid = true;
	   for(var i=0; i<this.fieldValidations.length;i++){
                 if(!this.fieldValidations[i].validate()){
					   isValid = false;
					   break;
				 }
		}
		return isValid; 
		
	},
	
   isDirty: function(){
   	    var dirty = false;
		if(typeof this.rowData == 'undefined')return true;		
		for(var i=0; i < this.fieldsCache.length; i++){			  
			  var field = this.fieldsCache[i];
			  lastSubPath = field.getLastSubPath();	
			  if(lastSubPath in this.rowData && this.rowData[lastSubPath] != field.getValue()){
			  	 dirty = true;				
				 break;	
			  } 
		}
		return dirty;	    	
	}, 
		
   setParentIndexedNestedPath: function(parentIndexedNestedPath,isNewRow){
          this.parentIndexedNestedPath= parentIndexedNestedPath;
		  var indexedRowNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
		  this.observer.fireEvent('loadnavtoolbardata',indexedRowNestedPath,isNewRow); 		      
    },
   
   getIndexedNestedPath: function(){
          var indexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return indexedNestedPath;
    },
	
   setRowData: function(rowData){
	    this.rowData = rowData;
			
   },
	
   getRowData: function(){
		return this.rowData;
   }
}


//////////////////////////////////////////////
function BOSection(businessObjectName, path, className, title, configOptions){
		this.businessObjectName = businessObjectName;
		this.businessObject = eval(businessObjectName);
		this.path = path;
		this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		this.className = className;
		this.title = (typeof title == 'undefined' || title.length == 0) ? "" : title;	    
		this.configOptions = configOptions;
	    this.isNewRow = (typeof this.configOptions['isNewRow'] != 'undefined') ? this.configOptions['isNewRow'] : false;
	    this.collapsible = ((typeof this.configOptions['collapsible'] != 'undefined' && this.configOptions['collapsible'] == 'true') ? true : false);      
	    this.bodyStyle = ((typeof this.configOptions['bodyStyle'] != 'undefined') ? this.configOptions['bodyStyle'] : '');        
        if(typeof this.configOptions != 'undefined' && typeof this.configOptions['parentIndexedNestedPath'] != 'undefined')
		      this.parentIndexedNestedPath = this.configOptions['parentIndexedNestedPath'];  
	    if(typeof this.configOptions != 'undefined' && typeof this.configOptions['isNewRow'] != 'undefined')
		        this.isNewRow = this.configOptions['isNewRow'];  	    
	    this.predicatePath = new NestedPathPredicate(this.parentIndexedNestedPath,this.path,0);
	    this.mainDivId = path;		  
		this.fieldValidations = new Array();
		this.observer = new BOSectionObserver({id:this.path});
		this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		this.init();
		  
}


BOSection.constructor = BOSection;
BOSection.prototype = {
	init: function(){	  	  
	  	  this.panel = Ext.create('Ext.panel.Panel',{
	                   id: this.path+'_panel',
			           renderTo:this.path,
					   contentEl: this.path + '_body',
			           autoScroll:true,
			           autoShow: true,
			           border: true,
			           margins: '5 5 5 5',
			           bodyStyle:'padding:3px',
	  		           title: this.title,
	  		           collapsible:this.collapsible,
					   autoWidth:true, 
			           autoHeight:true
		    });
		    this.fields = eval(this.path);		    
		    this.initEvents();
		    this.observer.fireEvent('load',this.isNewRow);   	 
	  	  
	  },
  
    initEvents: function(){
    	this.observer.on('load', this.onLoad, this);
        this.observer.on('render', this.onRender, this);
        this.observer.on('update', this.onUpdate, this);
		    
    },
    
    onLoad: function(isNewRow){   	  
		  if(typeof this.fields != 'undefined' && this.fields.length > 0 && !isNewRow) {		  	  
    	      var conn = new Ext.data.Connection();
		      conn.request({
	                 url: getContextPath()+'/bpmportal/common/bosection/getSection.portal',
				     method: 'POST',
					 scope:this,
					 params: {
						 fiid: this.fiid,
	                       repeatSectionRoot: this.path,
		  			       businessObjectName: this.businessObjectName,
			               dataslotName: this.businessObjectName,
			               indexedNestedPath: this.getIndexedNestedPath(),
			               className:this.className						    
					 },
					 success: function(req,opt){					 	   
						   var retJsonValues =YAHOO.lang.JSON.parse(req.responseText); 
		        		   var rowData = retJsonValues['section']; 
			               this.observer.fireEvent('render',rowData,isNewRow);	
				     },
                     failure: function(req,opt){                           
						   this.loadMask.hide();
				     }
			  });
        }
        else
        {
        	  // is new row
        	  this.observer.fireEvent('render',undefined,isNewRow);
        }
        		
    
    },
    
    onRender: function(rowData,isNewRow){
		   for(var i=0; i< this.fields.length;i++){
    	    	var path =  this.fields[i]['path'];
    	    	var className = this.fields[i]['className'];
    	    	var name = Bm.RepeatSection.getLastSubPath(path); 
				try { 
				         if (typeof rowData != 'undefined' && className == 'java.lang.Boolean') {
						  	document.getElementById(path).checked = rowData[name]
						 }else if(typeof rowData != 'undefined' && typeof rowData[name] != 'undefined') {
							document.getElementById(path).value = rowData[name];
						 } else if(typeof rowData == 'undefined')
						    document.getElementById(path).value = '';
				}catch(error){ alert(error);}
			} 	    		
    	    	
    	    	
    	    var rootHandler = eval(this.businessObjectName);    
		 	var boSections = eval(this.businessObjectName + '.boSections');
		 	if(typeof boSections != 'undefined' && typeof boSections[this.path] != 'undefined'){
			  var boSections = boSections[this.path];		  
              for(var i=0; i<boSections.length;i++){
                 var boSectionPath = boSections[i]['path'];
                 var boSectionClassName = boSections[i]['className'];
                 var boSectionClassTitle = boSections[i]['title'];
                 var boSectionCollapsible = boSections[i]['collapsible'];
                 var boSectionBodyStyle = boSections[i]['bodyStyle'];
                 var rowIndexedNestedPath = this.getRowIndexedNestedPath(rowIndex);			
                 if(typeof rootHandler.boSectionsCache[boSectionPath] == 'undefined'){              	   
                       var bosection = new BOSection(this.businessObjectName, boSectionPath, boSectionClassName, boSectionClassTitle, {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:boSectionCollapsible,bodyStyle:boSectionBodyStyle});
					   rootHandler.boSectionsCache[repeatSectionPath] =  repeatSection;
				 }else{						  			  
					   var boSection = rootHandler.boSectionsCache[repeatSectionPath];
                       boSection.setParentIndexedNestedPath(rowIndexedNestedPath);
					   rootHandler.boSectionsCache[repeatSectionPath] =  boSection;
				 }
			   }
					  
		 }
		 
	   

		 if(typeof this.businessObject.repeatSections[this.path] != 'undefined'){
			     var repeatSections = this.businessObject.repeatSections[this.path];
				   for(var i=0; i< repeatSections.length; i++){
			               var repeatSectionPath =repeatSections[i]['path'];
			               var repeatSectionType = repeatSections[i]['type'];
			               var repeatSectionClass =repeatSections[i]['className'];
						   			 var repeatSectionTitle =repeatSections[i]['title'];
						   			 var repeatSectionCollapsible =repeatSections[i]['collapsible'];
						   			 var repeatSectionBodyStyle =repeatSections[i]['bodyStyle'];
			               //alert("repeatSectionPath:"+repeatSectionPath+"\nrepeatSectionType:"+repeatSectionType+"\nrepeatSectionClass:"+repeatSectionClass);
			               var repeatSectionFields = eval(repeatSectionPath);
			               var fields = new Array();
			               fields[0] = {name:'edit',path:repeatSectionPath,dataIndex:'edit'};
			               fields[1] = {name:'delete',path:repeatSectionPath,dataIndex:'delete'};
			 
			               for(var j=0; j<repeatSectionFields.length; j++ ){
				                   var name = "";
				               		 var path = repeatSectionFields[j]['path'];
				               		 var label = repeatSectionFields[j]['label'];
							   			 		 var className = repeatSectionFields[j]['className'];
							         		 var args = (typeof repeatSectionFields[j]['args'] == 'undefined') ? {} : repeatSectionFields[j]['args'];
				               		 if(path.lastIndexOf('.') != -1)
					              			name = path.substring(path.lastIndexOf('.')+1,path.length);
                           if(className == 'java.lang.Boolean')
				                      fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,renderer:Bm.RepeatSection.renderCheckBox};
					                 else
				                      fields[fields.length] = {name:name,label:label,header:label,path:path,dataIndex:name,args:args,className:className,sortable: true};
			               } 
						   		                   							   
						         var rowIndexedNestedPath = this.getIndexedNestedPath();						   
						         if(typeof rootHandler.repeatSectionsCache[repeatSectionPath] == 'undefined'){
							            var repeanSection = null;
								          if(repeatSectionType == 'grid')
								                      repeatSection = new GridRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle});
 						             else if(repeatSectionType == 'lineitem')
                                      repeatSection = new LineItemRepeatSection(this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle});
								         rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
								                                
						       
						   }else{						  			  
								    var repeatSection = rootHandler.repeatSectionsCache[repeatSectionPath];
                    repeatSection.setParentIndexedNestedPath(rowIndexedNestedPath,isNewRow);
								    rootHandler.repeatSectionsCache[repeatSectionPath] =  repeatSection;
						   }
						   
		     }
		 }
     this.loadMask.hide();
     
    },
    
    onUpdate: function(){    	
    	if(this.fields.length >0){
    	      var fields = new Array(); 		 
			      for(var i=0; i<this.fields.length;i++){
		               var path =  this.fields[i]['path'];
		               var name = Bm.RepeatSection.getLastSubPath(path); 
					   var className = this.fields[i]['className'];						                 
					   if(typeof path != 'undefined'){
						      var value = (className == 'java.lang.Boolean') ? document.getElementById(path).checked : document.getElementById(path).value;
					          fields[fields.length] = {name:name,value:value};
						}
				    }
				    var conn = new Ext.data.Connection();
				    conn.request({
                        url: getContextPath()+'/bpmportal/common/bosection/updateSection.portal',
					    method: 'POST',
					    scope: this,
						params: {
							fiid: this.fiid,
                                fields: YAHOO.lang.JSON.stringify(fields),
	  						    repeatSectionRoot :this.path,
  						        businessObjectName: this.businessObjectName,
		                        dataslotName: this.businessObjectName,
		                        indexedNestedPath:this.getIndexedNestedPath(),
		                        className:this.className							               
						},
					    success: function(req,opt){								
								           // do something for nested level....
                        },
                        failure: function(req,opt){                              
							                handler.loadMask.hide();
						            }

				 });		
		  }
    	
    	
    	
    },
    
    
    setParentIndexedNestedPath: function(parentIndexedNestedPath,isNewRow){
          this.parentIndexedNestedPath= parentIndexedNestedPath;
          this.isNewRow = isNewRow;
          this.observer.fireEvent('load',this.isNewRow);		      
	  },
    
    getIndexedNestedPath: function(){
          var indexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return indexedNestedPath;
	 }
	
	
}





Bm.RepeatSection.renderer = function(value, metadata, record, rowIndex, colIndex, store){ 
    var path = store.path;
	  var businessObjectName = store.businessObjectName;
	  var handler = eval(businessObjectName + '_handler');
      var repeatSection = handler.repeatSectionsCache[path];
	  //var index = (typeof store.currentPage != 'undefined') ?  (store.currentPage * Bm.RepeatSection.pageSize +  rowIndex) : rowIndex;
	  var index = (typeof store.currentPage != 'undefined') ?  (store.currentPage * store.pageSize +  rowIndex) : rowIndex;
      if(colIndex == 1){
               return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                 "</a></div>";
      }else if(colIndex == 2){
               return "<div style='text-align:center'><a class=\"DeleteImage\" href=\"javascript:Bm.RepeatSection.deleteRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                "</a></div>";
      }else {
               return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+index+");\">"+
                 "</a></span></div>";
	  }
}

Bm.RepeatSection.editRow = function(businessObjectName, path, index){	
     var repeatSection = Bm.RepeatSection.Cache.get(path);	
	 //repeatSection.fireEvent('detailrender',path,index);
}

Bm.RepeatSection.deleteRow = function(businessObjectName, path, index){ 
    var rootHandler = eval(businessObjectName);  
    var repeatSection = rootHandler.repeatSectionsCache[path];       
	  repeatSection.observer.fireEvent('deleterow',index);	  
};


Bm.RepeatSection.doAjax = function(url,params,callback){
    function handleError(msg,_req){//responseText
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

Bm.RepeatSection.asyncRequest = function(url,postData,handleSuccess,loadMask){
    function handleFailure(_req){//responseText
        var resizableDialog = new ResizableDialog();
		var exception = YAHOO.lang.JSON.parse(_req.responseText);  
        var body = "";
		var msg = "";
        body += "<table><tr><td>"+msg+"</td></tr><tr><td>";
        body += "<tr><td><span style='color:red'>"+exception['exceptionLocalizedMessage']+"</td></tr>";
	    //body += "<tr><td><span style='color:blue'>"+exception['exceptionStackTrace']+"</td></tr>";      
        body += "</td></tr></table>";
        resizableDialog.setBody(body);
	    resizableDialog.show();
   	    YAHOO.util.Dom.setStyle('resizablepanel','display','block');
	    //loadMask.hide();
    }   

	var callback ={
		  success:handleSuccess,
		  failure:handleFailure		  
	};
    YAHOO.util.Connect.asyncRequest('POST', url, callback, postData);
             
}

Bm.RepeatSection.renderCheckBox = function(value, metadata, record, rowIndex, colIndex, store){ 
  return '<input type="checkbox" '+((value) ? 'checked=\'true\' ' : '')+'disabled/>';
}

Bm.RepeatSection.renderRadio = function(value, metadata, record, rowIndex, colIndex, store){ 
  return '<input type="radio" '+((value) ? 'checked=\'true\' ' : '')+'disabled/>';
}

Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName = function(path,businessObjectName){		 
    var tmpPath = path;
		var index = path.indexOf(businessObjectName);
		return tmpPath.substring(index+businessObjectName.length+1,tmpPath.length);		
}

Bm.RepeatSection.appendIndexedNestedPath = function(path,businessObjectName,index){		         
}

Bm.RepeatSection.getJsonValue = function(jsonValues,fieldPath){        
		 var tmpJsonValue = jsonValues;
		 var fielSubPath = fieldPath.split('.');
		 for(var i=0; i< fielSubPath.length-1; i++){			  
              if(typeof tmpJsonValue == 'undefined') break;
			  tmpJsonValue = tmpJsonValue[fielSubPath[i]];		     	  
		 }
		 var name = Bm.RepeatSection.getLastSubPath(fieldPath)
		 return (typeof tmpJsonValue == 'undefined' || tmpJsonValue== null || typeof tmpJsonValue[name] == 'undefined')? '' :tmpJsonValue[name];
}




Bm.RepeatSection.getLastSubPath = function(path){
        return path.substring(path.lastIndexOf('.')+1,path.length);
}

Bm.RepeatSection.showValidationFailedMessage = function(businessObjectName, fieldValidations){
      var invalidWidgets = new Array();
     	//var widgetQueue = Spry.Widget.Form.onSubmitWidgetQueue;		      
		  for (var i = 0; i < fieldValidations.length; i++) { 
		        if(!fieldValidations[i].validate()){
			             var validationLabel = fieldValidations[i].element.getAttribute('id');
			             invalidWidgets[invalidWidgets.length] = fieldValidations[i].element.getAttribute('id').substring(0,fieldValidations[i].element.getAttribute('id').indexOf('_div'));			  
			      }
		  }

			var msg = "<table><tr><td><span style='color:black'>Following fields are not valid:</span></td></tr>";
      for(var i = 0; i < invalidWidgets.length; i++) {
				    var validationLabels = eval(businessObjectName +'.validationLabels');
            var label = (typeof validationLabels[invalidWidgets[i]] == 'undefined') ? invalidWidgets[i] : validationLabels[invalidWidgets[i]];
              //.substring(0,invalidWidgets[i].indexOf('_div'));
						  //var label = (typeof validationLabels[path] == 'undefined') ? invalidWidgets[i].substring(0,invalidWidgets[i].length-4) : validationLabels[path];
						msg += ("<tr><td><span style='color:red'>-"+label +"</span></td></tr>");
 	                    
      }
      msg += "</table>";
     	sbm.widgets.showMsg('Validation Failed!',msg);  
}

Bm.RepeatSection.getValidationRules = function(path, type,args){
      //alert("path:"+path+"\ntype:"+type+"\nargs:"+args);
	  var vOptions = {};    
	  var validationType = 'none';

	  if(typeof args != 'undefined'){
			    var validationType = args['validationType'];
				var validation = args['validation'];
	                     
				if(typeof validation != 'undefined' && validationType != 'none'){
                         var vOptions = {};
	                     if(!YAHOO.lang.isUndefined(validation)){
	 						      var validationOn = validation['validationOn'];
								  var useCharacterMasking = validation['useCharacterMasking'];
								  var isRequired = validation['isRequired'];
								  var minValue = validation['minValue'];
								  var maxValue = validation['maxValue'];
								  var format = validation['format'];
								  var pattern = validation['pattern'];
								  var regExp = validation['regExp'];
								  var pattern = validation['pattern'];
								  var minChars = validation['minChars'];
								  var maxChars = validation['maxChars'];
								  if(!YAHOO.lang.isUndefined(validationOn)) 
								       vOptions['validateOn'] = validationOn;
					        	  if(!YAHOO.lang.isUndefined(useCharacterMasking)) 
	 							 	  vOptions['useCharacterMasking'] = true;
				    	          if(!YAHOO.lang.isUndefined(isRequired) && isRequired)
								      vOptions['isRequired'] = true;
								  else vOptions['isRequired'] = false;				
						          if(!YAHOO.lang.isUndefined(minValue))
								  	  vOptions['minValue'] = minValue;
	   							  if(!YAHOO.lang.isUndefined(maxValue))
								      vOptions['maxValue'] = maxValue;
								  if(!YAHOO.lang.isUndefined(format))
								      vOptions['format'] = format;
								  if(!YAHOO.lang.isUndefined(pattern)) {
					 			               if(validationType == "custom") vOptions['format'] = 'custom';
 		                                       if(YAHOO.lang.isUndefined(vOptions['validateOn']))vOptions['validateOn'] = ['blur','change'];
					                           if(!YAHOO.lang.isUndefined(regExp) && regExp.length != 0) 
	            							         vOptions['regExp'] = regExp;				       
							                   if(!YAHOO.lang.isUndefined(pattern) && pattern.length != 0)
			                                         vOptions['pattern'] = pattern;
 			                      }
                                  if(!YAHOO.lang.isUndefined(minChars))
								       vOptions['minChars'] = minChars;
 			                      if(!YAHOO.lang.isUndefined(maxChars)) 
								       vOptions['maxChars'] = maxChars;
								                                 
						}						
					// Set required validation if no validation is found and the 'required' attribute is set
				 } else if (typeof validation != 'undefined' && validationType == 'none' && args['required'] == true) {
					validationType = 'required';
					vOptions['validateOn'] = ['blur'];
				 }
      }
	     
	  
	  if(typeof vOptions == 'undefined') vOptions = {};
	  if((typeof validationType == 'undefined' || validationType == 'none') && type == 'java.lang.Long'){
		   validationType = 'integer';
		   vOptions['validateOn'] = ['blur','change'];
               
	  }

      
	  return {validationType:validationType,vOptions:vOptions};

}

/**
 * This is a utility method for setting the values into corresponding fields 
 * @param {Object} fields
 * @param {Object} rowData
 */

Bm.RepeatSection.setFieldValues = function(fields, rowData){	
	for(var i =0; i< fields.length;i++){
          var name = fields[i]['name'];
		  var path =  fields[i]['path'];	
		  var className = fields[i]['className'];
		  if(typeof name != 'undefined' && name != 'edit' && name != 'delete'){				 			 
				 if(typeof path != 'undefined'){
				 	  try{ 
						      if(typeof rowData != 'undefined' && className == 'java.lang.Boolean'){
                               		document.getElementById(path).checked = rowData[name]										
							  } else
								    document.getElementById(path).value = (typeof rowData == 'undefined')? '' :rowData[name];
							 
					  }catch(error){ alert(error);}
				 }
		   }
	 }

}

Bm.RepeatSection.isFieldValuesChanged = function(fields, rowData){
	var hasChanged = false;
	if(typeof rowData == 'undefined' || typeof fields == 'undefined'){
		 Bm.RepeatSection.showMessage('Warning','rowData can not be undefined!');
	}
	
    for(var i =0; i< fields.length;i++){
          var name = fields[i]['name'];
		  var path =  fields[i]['path'];
		  var className =  fields[i]['className'];		  
		  if(typeof name != 'undefined' && name != 'edit' && name != 'delete'){
		  	     if(className == 'java.lang.Boolean'){
				 	   var checked = document.getElementById(path).checked;				  
					   if((checked && !rowData[name]) || (!checked && rowData[name])){					   	 
						  	hasChanged = true;
						    break;
					   }
					   				    
				 }else {
				 	if(document.getElementById(path).value != rowData[name]){
						hasChanged = true;
						break;
					}
				 }			 
		   }
	 }
}

Bm.RepeatSection.showMessage = function(title,message){
	Ext.MessageBox.show({
             title:title,
             msg: message,
             buttons: Ext.MessageBox.OK,
             //fn: showResult,
             //animEl: 'mb4',
             icon: Ext.MessageBox.WARNING
     });	
}

/***
 *   Path defines the nested path without index
 *    for repeat section
 *   Index shows the row index in collection
 */
function NestedPathPredicate(parentPath,path,index){
	this.parentPath = YAHOO.lang.isUndefined(parentPath) ? "" : parentPath;
    this.path = YAHOO.lang.isUndefined(path) ? "" : path;
    this.index = YAHOO.lang.isUndefined(index) ? 0 : index;    
}

NestedPathPredicate.constructor = NestedPathPredicate;
NestedPathPredicate.prototype = {	
    getParentPath: function() {
        return this.parentPath;
    },

	getPath: function() {
        return this.path;
    },
    
    getIndex: function() {
        return this.index;
    } 
}     


/***
 *   Field defines the meta data of a field in detail section
 */
Bm.RepeatSection.Field = function(configOptions){
	this.path = configOptions['path'];
	this.name = configOptions['path'];
    this.label =  configOptions['label'];
	this.type = configOptions['type'];
	this.args = configOptions['args'];
	this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
	this.className = configOptions['className'];
	this.widgetSource = configOptions['widgetSource'];
	this.widgetTarget = configOptions['widgetTarget'];
	this.dataSlotName = configOptions['dataSlotName'];
	this.dataSlotType = configOptions['dataSlotType'];
	if(typeof this.widgetSource != 'undefined' && this.widgetSource['type'] == 'ADAPLET')
	this.adapletOutputName = configOptions['widgetSource']['name'];	
	//alert("path: "+this.path+"\nlabel: "+this.label+"\ntype: "+this.type+"\nwidgetSource: "+this.widgetSource+"\nwidgetTarget: "+this.widgetTarget);	
	
};

Bm.RepeatSection.Field.prototype = {		
	initComponent: function(){		
	    //alert("Field.initComponent::\n"+"widgetSource:"+this.widgetSource['type']+"\nthis.name:"+this.name);
		if (typeof this.widgetSource != 'undefined' && this.widgetSource['type'] == 'ADAPLET') {
			var processContextMap = {
				'_P_TYPE': '',
				'_PT_NAME': 'AdvancedWigets',
				'_WS_NAME': this.widgetSource['path'] + '',
				'_CLASS_NAME': 'com.savvion.sbm.adapters.db.DBAdapter'
			};
			var inputDataslotsMap = {};
			var adapletOutput = this.adapletOutputName;
			var elementId = this.path;
			var type = this.type;
			
			adapterDWR.executeAdapter(processContextMap, inputDataslotsMap, function(map){
				if (map['exception'] != undefined) {
					alert("Exception in adapter execution..." + map['exception']);
				}else {
						var values = map[adapletOutput];
						if (type == 'sbm.combobox') {
							var element = document.getElementById(elementId);
							pwr.util.addOptions(element, map[adapletOutput]);
						}else if(type == 'sbm.radio') {
							var content = '';
							var parent = document.getElementById(elementId + '_div');
							for (var i = 0; i < values.length; i++) {
								var radio = '<input type="radio" class="ApInptRadio" name="' + elementId + '" value="' + values[i] + '" id="' + values[i] + '">' + values[i] + '<br>\n';
								content += radio;
							}
							parent.innerHTML = content;
						   
						}
				}
			});
			
			
		}else if(typeof this.widgetSource != 'undefined' && this.widgetSource['type'] == 'STATIC') {			
			if (this.type == 'sbm.combobox'){
				
			}else if (this.type == 'sbm.radio'){				
				var parent = document.getElementById(this.path + '_div');
				var content = '';
				values = this.widgetSource['value']; 
				for (var i = 0; i < values.length; i++) {
					var label = values[i].label;
					var value = values[i].value
					var selected = values[i].selected;
					if (typeof label != 'undefined' && typeof value != 'undefined') {
						var radio = '<input type="radio" class="ApInptRadio" name="' + this.path + '" value="' + value + '" id="' + value + '"' +
						(selected ? ' checked="true"' : '') +'>' +label +'<br>\n';
						content += radio;
					}
				}
				parent.innerHTML = content;				
			} 
	    }
		
		
	},
	
	getName: function() {
        return this.name;
    },
	
	getType: function() {
        return this.type;
    },

    getLabel: function() {
        return this.label;
    },

	getPath: function() {
        return this.path;
    },
	
	getLastSubPath: function() {
        return this.lastSubPath;
    },
    
    getArgs: function() {
        return this.args;
    },
	
	getValue: function() {
        var value ="";		
		if(this.type == 'sbm.textfield' || this.type == 'datetime' || this.type == 'sbm.combobox'){			 
			 value = document.getElementById(this.name).value;		
		}else if(this.type == 'sbm.checkbox'){
			 value = document.getElementById(this.name).checked;
		}else if(this.type == 'sbm.radio'){
			var elem = document.getElementById(this.name);
			//alert(elem);	
		}
		return value;
		
    },
	
	setValue: function(value){		
		if(typeof value == 'undefined') return;		
		if(this.type == 'sbm.checkbox'){
			document.getElementById(this.path).checked = value;		
		
		}else if(this.type == 'sbm.textfield' || this.type == 'datetime' || this.type == 'sbm.combobox'){
			document.getElementById(this.path).value = value;			
		}	
	}
};    

Ext.define('Bm.RepeatSection.NavigationToolbar', {
    extend: 'Ext.toolbar.Toolbar', 
    displayMsg : 'Displaying {0} - {1} of {2}',
    emptyMsg : 'No data to display',
    beforePageText : "Item",
    afterPageText : "of {0}",
    firstText : "First",
    prevText : "Previous",
    nextText : "Next",
    lastText : "Last",
	isRendered: false,
	xtype: 'navtoolbar',
	//dock: 'bottom',
    refreshText : "Refresh",
    
    paramNames : {start: 'start', limit: 'limit'},
    pageActiveItem : 1,

    initComponent : function(){		
		this.observer = new RepeatSectionObserver();
		this.observer.on('activeitemchnaged', this.onActiveItemChanged, this);
		this.observer.on('firstrow', this.onFirstRow, this);
		this.observer.on('prevrow', this.onPrevRow, this);
		this.observer.on('nextrow', this.onNextRow, this);
		this.observer.on('lastrow', this.onLastRow, this);
        
        Bm.RepeatSection.NavigationToolbar.superclass.initComponent.call(this);
        this.cursor = 0;
        this.bind(this.repeatSection);
		this.on('afterlayout', this.onFirstLayout, this, {single: true});		
    },
	
	 // private
    onRender : function(ct, position){		
		Ext.PagingToolbar.superclass.onRender.call(this, ct, position);		
		var handler = this;
        this.first = this.add({
            tooltip: this.firstText,
            iconCls: "x-tbar-page-first",
            disabled: true,
			scope: this,
            handler: function(){
				this.onClick("first")
			}            
				
        });		
        this.prev = this.add({
            tooltip: this.prevText,
            iconCls: "x-tbar-page-prev",
            disabled: true,
			scope: this,            
			handler: function(){				
				this.onClick("prev")
			}			
        });
        //this.add('-');
        this.add(this.beforePageText);
		this.field = new Ext.form.NumberField({
           tag: "input",
           type: "text",
           size: "3",
           value: "1",
		   width:50,
           cls: "x-tbar-page-number"
		});		
		
		
        //////////this.field.on("keydown", this.onPagingKeydown, this);
        //this.field.on("focus", function(){this.field.select();});      
		this.add(this.field);
		//this.afterTextEl = this.addText(String.format(this.afterPageText, 1));        
		var _id = this.id + '_text_item';		
		this.afterTextItem =   { id: _id, xtype: 'tbtext', text:Ext.String.format(this.afterPageText, 1) }; 
		this.add({ id: _id, xtype: 'tbtext', text: this.afterPageText });
		this.afterTextItem = Ext.getCmp(_id);
					   
        this.next = this.add({
            tooltip: this.nextText,
            iconCls: "x-tbar-page-next",
            disabled: true,
			scope: this,
            handler: function(){
				this.onClick("next");
			}            
        });
		
        this.last = this.add({
            tooltip: this.lastText,
            iconCls: "x-tbar-page-last",
            disabled: true,
			scope: this,
            handler: function(){
				this.onClick("last");
			}           
        });
        
	
        /*
		this.loading = this.addButton({
            tooltip: this.refreshText,
            iconCls: "x-tbar-loading",
            handler: this.onClick.createDelegate(this, ["refresh"])
        });*/

        if(this.displayInfo){
            this.displayEl = Ext.fly(this.el.dom).createChild({cls:'x-paging-info'});
        }
		this.isRendered = true;
		
       
    },

    onActiveItemChanged: function(totalCount,activeItem){		
		if (typeof this.afterTextItem != 'undefined') {
			this.afterTextItem.update(Ext.String.format(this.afterPageText, totalCount));
			this.field.setValue(activeItem);
			this.first.setDisabled(activeItem == 1);
			this.prev.setDisabled(activeItem == 1);
			this.next.setDisabled(activeItem == totalCount);
			this.last.setDisabled(activeItem == totalCount);
		}

	},

    onFirstRow: function(){
       this.pageActiveItem = 0;
       this.observer.fireEvent('loadrowdata',0);
	},

    onPrevRow: function(){
        this.pageActiveItem = this.pageActiveItem-1;
		this.observer.fireEvent('loadrowdata',this.pageActiveItem-1);
	},

    onNextRow: function(){
		this.pageActiveItem = this.pageActiveItem+1;
        this.observer.fireEvent('loadrowdata',this.pageActiveItem+1);
	},

    onLastRow: function(){
        this.pageActiveItem = this.totalCount;
		    this.observer.fireEvent('loadrowdata',this.totalCount);
	},
	
	 onFirstLayout : function(){
        if(this.dsLoaded){
            this.onLoad.apply(this, this.dsLoaded);
        }
    },

    setItemData: function(totalCount, activeItem){				 
		 this.totalCount = totalCount;		
         this.pageActiveItem = activeItem;		
         //this.observer.fireEvent('activeitemchnaged',this, totalCount,activeItem);
		 this.onActiveItemChanged(totalCount,activeItem);		
	},

    onClick : function(which){	      
		if (this.isRendered) {
			switch (which) {
				case "first":					
					this.setItemData(this.totalCount, 1);					
					this.repeatSection.observer.fireEvent('loadrowdata', 0);
					break;
				case "prev":
					this.setItemData(this.totalCount, this.pageActiveItem - 1);
					this.repeatSection.observer.fireEvent('loadrowdata', this.pageActiveItem - 1);
					break;
				case "next":
					this.setItemData(this.totalCount, this.pageActiveItem + 1);
					this.repeatSection.observer.fireEvent('loadrowdata', this.pageActiveItem + 1);
					break;
				case "last":
					this.setItemData(this.totalCount, this.totalCount);
					this.repeatSection.observer.fireEvent('loadrowdata', this.totalCount);
					break;
			}
		}
    },

    
    unbind : function(repeatSection){
        this.repeatSection = undefined;
    },

    
    bind : function(repeatSection){     
        this.repeatSection = repeatSection;
    },
    
    getActiveItem: function(){
    	return this.pageActiveItem;
    }
});
/////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
sbm.convertDateByFormat = function(sourceFormat,targetFormat,value){
     
	 var date = sbm.convertDate(value, sourceFormat);
	 var value = date.p
	
}



sbm.convertDate = function(str, fmt) {
	var today = new Date();
	var y = 0;
	var m = -1;
	var d = 0;
	var a = str.split(/[,\s:;]+/);
	var b = fmt.match(/%./g);
	var i = 0, j = 0;
	var hr = 0;
	var min = 0;
	var sec = 0;
	for (i = 0; i < a.length; ++i) {
		if (!a[i])
			continue;
		switch (b[i]) {
		    case "%d":
		    case "%e":
			d = parseInt(a[i], 10);
			break;

		    case "%m":
			m = parseInt(a[i], 10) - 1;
			break;

		    case "%Y":
		    case "%y":
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
			break;

		    case "%b":
		    case "%B":
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
				if (Calendar._SMN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
			}
			break;

		    case "%H":
		    case "%I":
		    case "%k":
		    case "%l":
			hr = parseInt(a[i], 10);
			break;

		    case "%P":
		    case "%p":
			if (/pm/i.test(a[i]) && hr < 12)
				hr += 12;
			else if (/am/i.test(a[i]) && hr >= 12)
				hr -= 12;
			break;

		    case "%M":
			min = parseInt(a[i], 10);
			break;
		}
	}
	if (isNaN(y)) y = today.getFullYear();
	if (isNaN(m)) m = today.getMonth();
	if (isNaN(d)) d = today.getDate();
	if (isNaN(hr)) hr = today.getHours();
	if (isNaN(min)) min = today.getMinutes();
	if (y != 0 && m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	y = 0; m = -1; d = 0;
	for (i = 0; i < a.length; ++i) {
		if (a[i].search(/[a-zA-Z]+/) != -1) {
			var t = -1;
			for (j = 0; j < 12; ++j) {
				if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
			}
			if (t != -1) {
				if (m != -1) {
					d = m+1;
				}
				m = t;
			}
		} else if (parseInt(a[i], 10) <= 12 && m == -1) {
			m = a[i]-1;
		} else if (parseInt(a[i], 10) > 31 && y == 0) {
			y = parseInt(a[i], 10);
			(y < 100) && (y += (y > 29) ? 1900 : 2000);
		} else if (d == 0) {
			d = a[i];
		}
	}
	if (y == 0)
		y = today.getFullYear();
	if (m != -1 && d != 0)
		return new Date(y, m, d, hr, min, 0);
	return today;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
sbm.formatDate = function(date,format) {
   format=format+"";
    var result="";
    var i_format=0;
    var c="";
    var token="";
    var y=date.getYear()+"";
    var M=date.getMonth()+1;
    var d=date.getDate();
    var E=date.getDay();
    var H=date.getHours();
    var m=date.getMinutes();
    var s=date.getSeconds();
    var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
    // Convert real date parts into formatted versions
    var value=new Object();
    if (y.length < 4) {
        y=""+(+y+1900);
    }
    value["y"]=""+y;
    value["yyyy"]=y;
    value["yy"]=y.substring(2,4);
    value["M"]=M;
    value["MM"]=Date.LZ(M);
    value["MMM"]=Date.monthNames[M-1];
    value["NNN"]=Date.monthAbbreviations[M-1];
    value["d"]=d;
    value["dd"]=Date.LZ(d);
    value["E"]=Date.dayAbbreviations[E];
    value["EE"]=Date.dayNames[E];
    value["H"]=H;
    value["HH"]=Date.LZ(H);
    if (H==0){
        value["h"]=12;
    }
    else if (H>12){
        value["h"]=H-12;
    }
    else {
        value["h"]=H;
    }
    value["hh"]=Date.LZ(value["h"]);
    value["K"]=value["h"]-1;
    value["k"]=value["H"]+1;
    value["KK"]=Date.LZ(value["K"]);
    value["kk"]=Date.LZ(value["k"]);
    if (H > 11) { 
        value["a"]="PM"; 
    }
    else { 
        value["a"]="AM"; 
    }
    value["m"]=m;
    value["mm"]=Date.LZ(m);
    value["s"]=s;
    value["ss"]=Date.LZ(s);
    while (i_format < format.length) {
        c=format.charAt(i_format);
        token="";
        while ((format.charAt(i_format)==c) && (i_format < format.length)) {
            token += format.charAt(i_format++);
        }
        if (typeof(value[token])!="undefined") { 
            result=result + value[token]; 
        }
        else { 
            result=result + token; 
        }
    }
    return result;	
}



sbm.util.Format = function() {
    var trimRe         = /^\s+|\s+$/g,
        stripTagsRE    = /<\/?[^>]+>/gi,
        stripScriptsRe = /(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig,
        nl2brRe        = /\r?\n/g;

    return {
        
        ellipsis : function(value, len, word) {
            if (value && value.length > len) {
                if (word) {
                    var vs    = value.substr(0, len - 2),
                        index = Math.max(vs.lastIndexOf(' '), vs.lastIndexOf('.'), vs.lastIndexOf('!'), vs.lastIndexOf('?'));
                    if (index == -1 || index < (len - 15)) {
                        return value.substr(0, len - 3) + "...";
                    } else {
                        return vs.substr(0, index) + "...";
                    }
                } else {
                    return value.substr(0, len - 3) + "...";
                }
            }
            return value;
        },

        
        undef : function(value) {
            return value !== undefined ? value : "";
        },

        
        defaultValue : function(value, defaultValue) {
            return value !== undefined && value !== '' ? value : defaultValue;
        },

        
        
        date : function(v, format) {
            if (!v) {
                return "";
            }
            if (!Ext.isDate(v)) {
                v = new Date(Date.parse(v));
            }
            return v.dateFormat(format || "m/d/Y");
        },

        
        dateRenderer : function(dateOnlyFormat,format,dateOnly) {			
            return function(v) {
				///////////// Convert				
				var output = '';				
				if (v != '') {
					if (dateOnly) {						
						var date = sbm.convertDate(v, format);
						output = sbm.formatDate(date, dateOnlyFormat);
					}
					else {
						output = value;
					}
				}
				
                return output;
            };
        }
    };
}();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    This method overrides the onResize event Ext.Panel
//    If grid panel is located inside tab panel then the bottom toolbar
//    and top toolbar gets 'width:0px'
//    TR#RPM00033289
//    RPM00029588 - 8.0.0: Pagination and Add record button doesn't appear on Portal.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Ext.override(Ext.Panel, {
   
    onResize : function(adjWidth, adjHeight, rawWidth, rawHeight){
        var w = adjWidth,
            h = adjHeight;
		if(typeof adjWidth == 'undefined') adjWidth = 'auto';
        if(Ext.isDefined(w) || Ext.isDefined(h)){
            if(!this.collapsed){
                // First, set the the Panel's body width.
                // If we have auto-widthed it, get the resulting full offset width so we can size the Toolbars to match
                // The Toolbars must not buffer this resize operation because we need to know their heights.

                if(Ext.isNumber(w)){
                    this.body.setWidth(w = this.adjustBodyWidth(w - this.getFrameWidth()));
                } else if (w == 'auto') {
                    w = this.body.setWidth('auto').dom.offsetWidth;
                } else {
                    w = this.body.dom.offsetWidth;
                }
               
                if(this.tbar){
		    // the fix added
		    if(Ext.isIE && w == 0) w = 'auto';
                     this.tbar.setWidth(w);
                    if(this.topToolbar){
                        this.topToolbar.setSize(w);
                    }
                }
                if(this.bbar){				    
		    // the fix added
		    if(Ext.isIE && w == 0) w = 'auto';
                    this.bbar.setWidth(w);
                    if(this.bottomToolbar){
                        this.bottomToolbar.setSize(w);
                        // The bbar does not move on resize without this.
                        if (Ext.isIE) {
                            this.bbar.setStyle('position', 'static');
                            this.bbar.setStyle('position', '');
                        }
                    }
                }
                if(this.footer){
                    this.footer.setWidth(w);
                    if(this.fbar){
                        this.fbar.setSize(Ext.isIE ? (w - this.footer.getFrameWidth('lr')) : 'auto');
                    }
                }

                // At this point, the Toolbars must be layed out for getFrameHeight to find a result.
                if(Ext.isNumber(h)){
                    h = Math.max(0, h - this.getFrameHeight());
                    //h = Math.max(0, h - (this.getHeight() - this.body.getHeight()));
                    this.body.setHeight(h);
                }else if(h == 'auto'){
                    this.body.setHeight(h);
                }
            }
        }
    }
});
/////////////////////////////////////////////////////////////////////////////////////////////
//     New Implementation for Business Object 8.1
//
/////////////////////////////////////////////////////////////////////////////////////////////

//------------------------------------------------------------------------------------------//
//   BusinessObjectView
//
//------------------------------------------------------------------------------------------//

Ext.define('Portal.ux.BusinessObjectView',{
    extend:'Ext.util.Observable',
    statics: {
		allowMode:'allow',
		customMode:'custom',
		disabledMode:'disabled'
	},
    width: null,
    height: null,
    
    constructor: function(config,parentId){     
	    if(Bm.RepeatSection.debug){
           YAHOO.BM.Logger = new BM.Portal.LoggerDialog(Bm.RepeatSection.debug);
		   YAHOO.BM.Logger.debug("BusinessObjectView.constructor:" + YAHOO.lang.JSON.stringify(config));
        }	   
		
		this.config = {}; 
		Ext.apply(this.config, config);	
		this.presentationType = this.config['presentation']['default'];		
		this.fields = this.config.fields;
		this.path = this.config.path;
		this.businessObjectName = this.getBusinessObjectName(this.path);
		if(typeof parentId != 'undefined') this.parentId = parentId;			
		this.init();	
    },
	
    initEvents: function(){
		// init events
		this.on('init', this.init, this);				
    },
	
	init: function(){
	    //alert('this.columns:'+YAHOO.lang.JSON.stringify(this.columns));
		this.columns = Bm.RepeatSection.convertFields(this.fields,true,this.businessObjectName,this.path);        
		var boColumns = Bm.RepeatSection.convertBoColumns(this.fields,this.businessObjectName,this.path);		
		this.boColumns = boColumns['boColumns'];
		this.boSectionsMap = boColumns['boSectionsMap'];		
		this.columns = Ext.Array.merge(this.columns, this.boColumns);
		var attr_map = Bm.RepeatSection.getAttributeMap(this.config); 
		
		var repeatSection = null;
		if(this.presentationType == 'grid')			
		  repeatSection = new Portal.ux.BusinessObjectGridView({
							           businessObjectName: this.businessObjectName,
						               repeatSectionPath: this.path,
									   repeatSectionClass: this.config.className,
									   //repeatSectionTitle: this.config.label,									   
									   repeatSectionTitle: attr_map.get('title'),	
									   
									   boSectionsMap: this.boSectionsMap,
									   //repeatSectionsMap: this.repeatSectionsMap,
									   columns: this.columns,	
									   boColumns: this.boColumns,								   
									   fields: this.fields,
									   configOptions: {showCreate:true,
									                  showDelete:this.getAttribute('deleteMode'),
													  customCreate:true,
													  createMode:this.getAttribute('createMode'),
													  editMode:this.getAttribute('editMode'),
													  deleteMode:this.getAttribute('deleteMode'),
													  resetMode:true,
													  numberOfRowsPerPage: this.getAttribute('numrows'),
													  parentId: this.parentId}
								    });
									
		else if(this.presentationType == 'collection_combobox') {
			
			var combo_attrs = null;			
			var values =  this.config['presentation']['values'];			
			for(var i=0; i<values.length;i++){				
				if (values[i]['key'] == 'collection_combobox') {
					combo_attrs = values[i]['attributes'];				
				}
			}
			
			
			repeatSection = new Portal.ux.BusinessObjectComboView({
							           businessObjectName: this.businessObjectName,
						               repeatSectionPath: this.path,
									   repeatSectionClass: this.config.className,
									   repeatSectionTitle: this.config.label,									   
									   boSectionsMap: this.boSectionsMap,
									   //repeatSectionsMap: this.repeatSectionsMap,
									   columns: this.columns,									   
									   comboAttributes: combo_attrs,	
									   boColumns: this.boColumns,								   
									   fields: this.fields,
									   configOptions: {showCreate:true,
									                  showDelete:this.getAttribute('deleteMode'),
													  customCreate:true,
													  createMode:this.getAttribute('createMode'),
													  editMode:this.getAttribute('editMode'),
													  deleteMode:this.getAttribute('deleteMode'),
													  numberOfRowsPerPage: this.getAttribute('numrows'),
													  parentId: this.parentId}
								    });
			
		}
									   
		//var botest = eval(this.businessObjectName);
		//alert('Adding to the Cache:'+this.config.repeatSectionPath);
		//botest.repeatSectionsCache[this.config.repeatSectionPath] = repeatSection;
	
   },
   
    getLastPath: function(str){
   	  return str.substring(str.lastIndexOf('.')+1,str.length);	
   },
   
   getBusinessObjectName: function(path){
   	  return path.substring(0,path.lastIndexOf('.'));	
   },
   
   getAttribute: function(configAttrName){   	    
		var value = '';		
		var attributes = this.config['presentation']['values'];	
		gridloop: for (var i = 0; i < attributes.length; i++) {			
			if(attributes[i]['key'] == 'grid'){
				var attrs = attributes[i]['attributes'];
		    attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];
				   var attrMode = attrs[j]['value']['mode'];
				   
				   if(attrName == 'create' && configAttrName == 'createMode'){
				         value =  attrs[j]['value']['mode'];
						 break gridloop;
				   }else if(attrName == 'edit' && configAttrName == 'editMode'){
				         value =  attrs[j]['value']['mode'];
						 break gridloop;
				   }else if(attrName == 'delete' && configAttrName == 'deleteMode'){				   	   
					     value =  attrs[j]['value'];
						 break gridloop;
				   }else if(attrName == 'numrows' && configAttrName == 'numrows'){				   	   
					     value =  attrs[j]['value'];
						 break gridloop;
				   }
				}			
			}			
		}		
	    return value;	
   }
	
});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    Gets the field type based on property type
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Bm.RepeatSection.getFieldType = function(className){		
		var xtype = 'textfield';
		if(className == 'java.lang.String')
		     xtype = 'textfield';
	    else if(className == 'java.sql.Timestamp' || className == 'java.util.GregorianCalendar')
		     xtype = 'datefield';
		else if(className == 'java.math.BigDecimal' || className == 'java.lang.Long' || 
		        className == 'java.lang.Double' || className == 'java.math.BigDecimal')
		     xtype = 'numberfield';
	    else if(className == 'java.lang.Boolean')
		     xtype = 'checkboxfield';			 		
		return xtype;
}

Bm.RepeatSection.isFieldRequired = function(field){		
	var required = true;
	var values = field['presentation']['values'];
	var presentationType = field['presentation']['default'];
	parentloop: for (var i = 0; i < values.length; i++) {		
		if (typeof values[i]['validation'] != 'undefined' && values[i]['key'] == presentationType) {
			var validation = values[i]['validation'];		
			validationloop: for(var j=0; j<validation.length;j++){				
			    if (validation[j].key == 'required') {				
					required = validation[j].value;
					break parentloop;
				}	
			}	
		}
	}	
	return required;
		
		
}


Bm.RepeatSection.getFieldValidation = function(field){		
	var validation_data = {};
	var values = field['presentation']['values'];
	for (var i = 0; i < values.length; i++) {		
		if (typeof values[i]['validation'] != 'undefined') {
			var validation = values[i]['validation'];	
			for(var j=0; j<validation.length;j++){				
			    if (validation[j].key == 'min') {				
					validation_data.min = validation[j].value;					
				}
				if (validation[j].key == 'max') {				
					validation_data.max = validation[j].value;					
				}	
				
				if (validation[j].key == 'minMsg') {				
					validation_data.minMsg = validation[j].value;					
				}	
				
				if (validation[j].key == 'maxMsg') {				
					validation_data.maxMsg = validation[j].value;					
				}	
					
			}	
		}
	}	
	return validation_data;
		
		
}

Bm.RepeatSection.getSelectedAttribute =function(field){
	var selectedValue = null;
	var values = field['presentation']['values'];
	for (var i = 0; i < values.length; i++) {		
		if (values[i]['key'] == 'combobox') {
			var choices = values[i]['choices'];
			for(var j=0;j<choices.length;j++){
			    if (choices[j].selected) {				
					selectedValue = choices[i].value;
				}	
			}	
		}
	}
	return selectedValue;	
}

Bm.RepeatSection.getChoices =function(field){
	var _choices = new Object();
	var values = field['presentation']['values'];
	for (var i = 0; i < values.length; i++) {		
		if (values[i]['key'] == 'combobox') {
			var choices = values[i]['choices'];
			for(var j=0;j<choices.length;j++){
			    if (choices[j].selected) {				
					
					_choices.type = choices[i].type
					_choices.value = choices[i].value
				}	
			}	
		}
	}
	return _choices;	
}

Bm.RepeatSection.getChoicesType =function(field){
	var map = {};
	var presentation = field['presentation']['default'];
	if(presentation == 'combobox'){
		var values = field['presentation']['values'];
	    valuesloop: for (var i = 0; i < values.length; i++) {		
		    var key = values[i]['key'];
			if(key == 'combobox') {
				 var choices = values[i]['choices'];
				 choicesloop: for(var k=0; k<choices.length;k++){				     	
					 if(choices[k]['selected']){					 	
					 	 map.type =  choices[k]['type'];
						 map.value = choices[k]['value'];
						 break valuesloop;
						 
					 }					
				 }				 
			}
			
		}		
	}	
	return map;
}


Bm.RepeatSection.convertBoColumns = function(repeatSectionFields,boName,repeatSectionPath){	
	var fields = new Array();
	var boSectionsMap = new Ext.util.HashMap();
	for (var j = 0; j < repeatSectionFields.length; j++) {
	     if(repeatSectionFields[j]['bo']){
		 	 var _className = repeatSectionFields[j]['className']
			 var bo_fields = repeatSectionFields[j]['fields']		 	 
			 boSectionsMap.add(repeatSectionFields[j]['path'],repeatSectionFields[j]['className']);
			 for(k=0; k<bo_fields.length; k++) {
			 	   /////////////////////////////////////////////
				   //alert('bo_fields[j]:'+YAHOO.lang.JSON.stringify(bo_fields[k]));		   
				   if (bo_fields[k]['checked']) {				   	  
					  if(_className.indexOf('com.progress.lang') != -1) {
					  	fields.push(Bm.RepeatSection.convertABLBoColumn(repeatSectionFields[j],bo_fields[k], boName, repeatSectionPath));
					  }else					
					   fields.push(Bm.RepeatSection.convertBoColumn(bo_fields[k], boName, repeatSectionPath));
				   }				    
			 }			
		 }	
	}
	
	//////////////////////////
	//alert('convertBoColumns::fields:'+YAHOO.lang.JSON.stringify(fields));
	return {boColumns:fields,boSectionsMap:boSectionsMap};
	
	
}

Bm.RepeatSection.convertBoColumn = function(repeatSectionField,boName,repeatSectionPath){	
	var path = repeatSectionField['path'];
    var label = Bm.RepeatSection.getColumnTitle(repeatSectionField);
	var className = repeatSectionField['className'];
	var xtype = Bm.RepeatSection.getFieldType(className);
	var presentationType = repeatSectionField['presentation']['default'];
	xtype = (presentationType == 'combobox') ? 'combo' : xtype;
	xtype = (presentationType == 'radio') ? 'radiofield' : xtype;	
	path = path.substring(repeatSectionPath.length+1,path.length);
	var required = Bm.RepeatSection.isFieldRequired(repeatSectionField);	
	var id =  boName+'_'+repeatSectionPath.replace('.','_')+path.replace('.','_');
	id = id.replace('.','_');
	
	var field = {
				id: id,
				name: Bm.RepeatSection.getLastSubPath(path),
				text: label,
				header: label,
				path: path,
				type: 'string',
				dataIndex: Bm.RepeatSection.getLastSubPath(path),
				sortable: true,
				renderer: Bm.RepeatSection.renderer,
				flex: 1,
				editor: {
					xtype: xtype,
					allowBlank: !required
				}
			};
	
	if(xtype == 'combo'){
		 var store;
		 var cType = Bm.RepeatSection.getChoicesType(repeatSectionField);			
		 var _choices = Bm.RepeatSection.getChoices(repeatSectionField);
		 var type = _choices['type'];			
		 if(cType.type == 'static') {
				store = Bm.RepeatSection.Cache.createDataStore(id,'static',_choices['value']);				
		 }else if(cType.type == 'dataslot') {
				store = Bm.RepeatSection.Cache.createDataStore(id,'dataslot',_choices,cType)
		 }
		 field.field.store = store;
		 field.field.displayField = 'name';        
		 field.field.valueField = 'name';
	}
	
	if (className == 'java.sql.Timestamp')
	    field.type = 'date';
	else if (className == 'java.lang.String')
	    field.type = 'string';	
	return field;
}

Bm.RepeatSection.convertABLBoColumn = function(parent,repeatSectionField,boName,repeatSectionPath){	
	var path = repeatSectionField['path'];	
    var label = Bm.RepeatSection.getABLColumnTitle(parent);
	var className = repeatSectionField['className'];
	var xtype = Bm.RepeatSection.getFieldType(className);
	var presentationType = repeatSectionField['presentation']['default'];
	var required = Bm.RepeatSection.isFieldRequired(repeatSectionField);
	xtype = (presentationType == 'combobox') ? 'combo' : xtype;
	xtype = (presentationType == 'radio') ? 'radiofield' : xtype;	
	path = path.substring(repeatSectionPath.length+1,path.length);	
	var absolutePath = path.substring(0,path.indexOf('.'));
	
	var field = {
				id: boName+'_'+path.replace('.','_'),
				name: absolutePath,
				text: label,
				header: label,
				path: path,
				isAbl: true,
				dataIndex: absolutePath,
				sortable: true,
				renderer: Bm.RepeatSection.renderer,
				flex: 1,
				field: {
					xtype: xtype,
					allowBlank: !required
				}
			};
	
	if(xtype == 'datefield'){
		   var dateOnly =  Bm.RepeatSection.isDateOnly(repeatSectionField);
		   var dateFormat = (dateOnly) ? Bm.RepeatSection.Cache.getDateOnlyFormat() :  Bm.RepeatSection.Cache.getDateFormat();		
		   field.dateOnly = dateOnly;
		   field.renderer = function(value, metadata, record, rowIndex, colIndex, store){	   
				   var _v =''
				   var grid = Ext.getCmp(store.gridCompId);
				   var column = grid.columns[colIndex];				  
				   if(typeof value == 'undefined' || (typeof value == 'string' && value.length ==0)) return '';
				   if(grid.columns[colIndex]['dateOnly'])
				        _v = Ext.util.Format.date(value,Bm.RepeatSection.Cache.getDateOnlyFormat());
				   else 
				        _v = Ext.util.Format.date(value,Bm.RepeatSection.Cache.getDateFormat());				   
				   				   
				   return _v;
				};
		field.dateFormat = Bm.RepeatSection.Cache.getDateFormat();	
		field.field.format = Bm.RepeatSection.Cache.getDateFormat();	
	}
	
	return field;
}


Bm.RepeatSection.renderer = function(value, metadata, record, rowIndex, colIndex, store){	
	return (value == null)? '': value;
}

Bm.RepeatSection.renderCollectionColumn  = function(value, metadata, record, rowIndex, colIndex, store){	
	var grid = Ext.getCmp(store.gridCompId)
	var col = Ext.getCmp(store.gridCompId).columns[colIndex];	
	//return "<div style='text-align:center;color:black;'><a href=\"javascript:Bm.RepeatSection.editRow('"+store.businessObjectName +"','"+store.path+"',"+rowIndex+",'"+col.path+"');\">"+
      //           col.text+"</a></div></div>";
	return "<div style='text-align:center;color:blue;'>"+
                col.text+"</a></div></div>";
}

Bm.RepeatSection.getAttributeMap = function(config){
		var map = new Ext.util.HashMap();			
		var attributes = config['presentation']['values'];	
		gridloop: for (var i = 0; i < attributes.length; i++) {			
			if(attributes[i]['key'] == 'grid'){
				var attrs = attributes[i]['attributes'];
		    attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];
				   var attrMode = attrs[j]['value']['mode'];				  
				   
				   if(attrName == 'create' && typeof attrs[j]['value']['mode'] != 'undefined'){
				         map.add('createMode', attrs[j]['value']['mode']);					 
				   }else if(attrName == 'edit' && typeof attrs[j]['value']['mode'] != 'undefined'){				         
						 map.add('editMode', attrs[j]['value']['mode']);							 
				   }else if(attrName == 'delete'){				     
						 map.add('deleteMode', attrs[j]['value']);						 
				   }else if(attrName == 'numrows'){	
				         map.add('numrows',attrValue);						 
				   }else if(attrName == 'title'){	
				         map.add('title', attrs[j]['value']);					     
						 
				   }
				}			
			}			
		}		
	    return map;	
}

Bm.RepeatSection.getColumnTitle = function(field){ 
  var title = '';
  var values = field['presentation']['values'];
  var attributes = field['presentation']['values'];	
		gridloop: for (var i = 0; i < attributes.length; i++) {	
				var attrs = attributes[i]['attributes'];
		    attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];				  
				   if(attrName == 'title'){	
				         title = attrValue;
						 break gridloop;
						 				     
						 
				   }							
			}			
		}		
	
  return title;
}

Bm.RepeatSection.isDateOnly = function(field){ 
  var dateOnly = false;
  var values = field['presentation']['values'];
  var attributes = field['presentation']['values'];	
		gridloop: for (var i = 0; i < attributes.length; i++) {	
				var attrs = attributes[i]['attributes'];
		    attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];				  
				   if(attrName == 'dateonly'){	
				         dateOnly = attrValue;
						 break gridloop;
						 				     
						 
				   }							
			}			
		}		
	
  return dateOnly;
}



Bm.RepeatSection.getABLColumnTitle = function(field){ 
  var title = '';
  var attributes = field['presentation']['values'];	
  gridloop: for (var i = 0; i < attributes.length; i++) {		
			  var attrs = attributes[i]['attributes'];
		      attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];	   					 
				   if(attrName == 'title'){	
				         title = attrs[j]['value'];	
						 break gridloop;						 
				   }
			 }						
  }		
  return title;  
}


Bm.RepeatSection.convertFields = function(repeatSectionFields,showRowNumberer,boName){
	var fields = new Array();
	//if(showRowNumberer)
	//	fields.push(Ext.create('Bm.grid.RowNumberer'));
	for(var j=0; j<repeatSectionFields.length; j++ ){
	    var name = "";
	    var path = repeatSectionFields[j]['path'];
		var label = repeatSectionFields[j]['label'];
		
		label = Bm.RepeatSection.getColumnTitle(repeatSectionFields[j]);
		
		
		var className = repeatSectionFields[j]['className'];
		var presentationType = repeatSectionFields[j]['presentation']['default'];		
		var xtype = Bm.RepeatSection.getFieldType(className);		
		xtype = (presentationType == 'combobox') ? 'combo' : xtype;
		xtype = (presentationType == 'radio') ? 'radiofield' : xtype;		
		var required = Bm.RepeatSection.isFieldRequired(repeatSectionFields[j]);
		var validation = Bm.RepeatSection.getFieldValidation(repeatSectionFields[j]);				
		var args = (typeof repeatSectionFields[j]['args'] == 'undefined') ? {} : repeatSectionFields[j]['args'];
		if(path.lastIndexOf('.') != -1)
		    name = path.substring(path.lastIndexOf('.')+1,path.length);        
		var id =  boName.replace(/\./g,'_')+ name.replace(/\./g,'_');	
		if(repeatSectionFields[j]['bo']) continue;	
		if(!repeatSectionFields[j]['checked']) continue;							
		if(repeatSectionFields[j]['collection']) {						
			fields[fields.length] = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				dataIndex: name,
				align: 'center',
				renderer: Bm.RepeatSection.renderCollectionColumn			
			};
			continue;
			
		}
		
		// if field type is combo
		if (xtype == 'combo') {		    
			var store;
			var cType = Bm.RepeatSection.getChoicesType(repeatSectionFields[j]);			
			var _choices = Bm.RepeatSection.getChoices(repeatSectionFields[j]);
			var type = _choices['type'];			
			if(cType.type == 'static') {
				store = Bm.RepeatSection.Cache.createDataStore(id,'static',_choices['value']);				
			}else {
				store = Bm.RepeatSection.Cache.createDataStore(id,'dataslot',_choices,cType)
			}
						  
			fields[fields.length] = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				dataIndex: name,
				align: 'center',
				flex: 1,				
				editor: {
					id: id+'_combo',
					autoLoad: false,
					xtype: 'combo',
					store: store,
                    displayField:'name',        
					valueField:'name',
					listeners: {
						 expand: {
						 	fn: function(){                   					
									Ext.getCmp(id+'_combo').setLoading(false,false);				
							}
							
							
						 }
						
					},					
					allowBlank: !required
				}
			
			};
		// if field type is checkbox
		}else if (xtype == 'checkboxfield') {			
			fields[fields.length] = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				dataIndex: name,
				align: 'center',
				renderer: Bm.RepeatSection.renderCheckBox,
				//renderer: Bm.RepeatSection.renderCheckBox,
				editor: {
					xtype: 'checkbox'//,
					//allowBlank: false
				}
			
			};
		}else if (xtype == 'radiofield') {			
			fields[fields.length] = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				dataIndex: name,
				align: 'center',
				renderer: Bm.RepeatSection.renderRadio,
				//renderer: Bm.RepeatSection.renderCheckBox,
				editor: {
					xtype: 'radio'//,
					//allowBlank: false
				}
			
			};
		// if field type is date
		}else if (className == 'java.sql.Timestamp') {	 
			  var dateOnly =  Bm.RepeatSection.isDateOnly(repeatSectionFields[j]);
			  var dateFormat = (dateOnly) ? Bm.RepeatSection.Cache.getDateOnlyFormat() :  Bm.RepeatSection.Cache.getDateFormat();
			  
			  fields[fields.length] = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				type: 'date',
				dateOnly:dateOnly,
				flex:1,
				dataIndex: name,
				align: 'center',		
				renderer: function(value, metadata, record, rowIndex, colIndex, store){  
				   var _v =''
				   var grid = Ext.getCmp(store.gridCompId);
				   var column = grid.columns[colIndex];
				   if(value == null) return '';
				   if(grid.columns[colIndex]['dateOnly'])
				        _v = Ext.util.Format.date(value,Bm.RepeatSection.Cache.getDateOnlyFormat());
				   else 
				        _v = Ext.util.Format.date(value,Bm.RepeatSection.Cache.getDateFormat());				   
				   return _v;
				},	
				dateFormat: Bm.RepeatSection.Cache.getDateFormat(),				
				editor: {
					xtype: 'datefield',					
					//format: 'M d, Y h:i A',
					format: dateFormat,
					allowBlank: !required
				}
			
			};
		
		
		}else if(className == 'java.lang.Long' || className == 'java.lang.Double' || className == 'java.math.BigDecimal'){
						
			var _field = {
				id: id,
				name: name,
				text: label,
				header: label,
				path: path,
				dataIndex: name,				
				sortable: true,
				flex: 1,
				editor: {
					xtype: xtype,
					allowBlank: !required
				}
			};	
					
			if (typeof validation.min != 'undefined' && validation.min.length != 0) {
				_field.editor.minValue = parseInt(validation.min);
				_field.editor.minText = (typeof validation.minMsg != 'undefined') ? validation.minMsg : '';				 
			}
			if (typeof validation.max != 'undefined' && validation.max.length != 0) {
				_field.editor.maxValue = parseInt(validation.max);
				_field.editor.maxText = (typeof validation.maxMsg != 'undefined') ? validation.maxMsg : '';
			}
			
			fields[fields.length] = _field;			
		}else 
			fields[fields.length] = {
				id: id,
				name: name,
				type:'string',
				text: label,
				header: label,
				path: path,
				dataIndex: name,
				align: 'center',
				sortable: true,
				flex: 1,
				editor: {
					xtype: xtype,
					allowBlank: !required
				}
			};
			
			
	  
	} 
	
	return fields;
}


Portal.ux.BusinessObjectGridView = Ext.extend(Ext.util.Observable, {
    
    constructor: function(config){	    
		if(Bm.RepeatSection.debug) {			  
			YAHOO.BM.Logger.debug("-config:" + YAHOO.lang.JSON.stringify(config));
		}
		this.config = {}; 
		Ext.apply(this.config, config);	
		Portal.ux.BusinessObjectGridView.superclass.constructor.call(this);
		this.type = 'Portal.ux.BusinessObjectGridView';
        this.initEvents();
		this.init();        
    },
    
    init: function(){
        //alert('Repeat Section Path:'+this.config.repeatSectionPath+'\nrepeatSectionTitle:'+this.config.repeatSectionPath);
		this.path = this.config.repeatSectionPath;
		this.businessObjectName = this.config.businessObjectName;		
		this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		this.lastIndex = this.config.lastIndex;		
		this.callerView = (typeof this.config.callerView != 'undefined') ? this.config.callerView : null;
		this.callerViewPath = (typeof this.config.callerViewPath != 'undefined') ? this.config.callerViewPath : null;		
		this.showCreate = (typeof this.config.configOptions.showCreate != 'undefined' && 
		   this.config.configOptions.showCreate) ? true: false;
		this.showDelete = (typeof this.config.configOptions.showDelete != 'undefined' && 
		   this.config.configOptions.showDelete) ? true: false;
		this.createCustom = (typeof this.config.configOptions.createCustom != 'undefined' && 
		   this.config.configOptions.createCustom) ? true: false;
		// create, edit and delete modes
		this.createMode = (typeof this.config.configOptions.createMode != 'undefined') ? this.config.configOptions.createMode: 'allow';
		this.deleteMode = (typeof this.config.configOptions.deleteMode != 'undefined' && this.config.configOptions.deleteMode) ? this.config.configOptions.deleteMode :false;
		this.editMode = (typeof this.config.configOptions.editMode != 'undefined' &&  this.config.configOptions.editMode == 'allow') ? true: false;
		this.resetMode = (typeof this.config.configOptions.resetMode != 'undefined' &&  this.config.configOptions.resetMode ) ? true: false;		
		this.numberOfRowsPerPage = (typeof this.config.configOptions.numberOfRowsPerPage != 'undefined'
		    && this.config.configOptions.numberOfRowsPerPage != '') ? parseInt(this.config.configOptions.numberOfRowsPerPage): 5;
				
		this.repeatSectionClass = this.config.repeatSectionClass;		
		this.title = this.getLastPath(this.repeatSectionClass); 
	    this.isRoot = (typeof this.config.configOptions.isRoot != 'undefined' && this.config.configOptions.isRoot)
		                 ? true : false;
		this.isChild = (typeof this.config.isChild != 'undefined') ? this.config.isChild : false;
		if(this.isChild)
		this.parentTitle = (typeof this.config.parentTitle != 'undefined') ? this.config.parentTitle : null;		
		this.className = this.config.className
		this.masterDivId = (typeof this.config.masterDivId != 'undefined') ? this.config.masterDivId: this.config.repeatSectionPath+'_master';
	    
		if(typeof this.config.parentDetailDivId != 'undefined'){			 
			  var el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_detail'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_detail')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);   
			  el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_master'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_master')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);
			   
		}
		// add detail div because it does not exist if there is master div id		  
		this.detailDivId = this.config.repeatSectionPath+'_detail';  		
		this.mainDivId = this.config.repeatSectionPath;
		this.renderTo = this.config.renderTo
		this.columns = this.config.columns;
		this.fieldValidations = new Array();
		this.validationCache = {};
		//alert(YAHOO.lang.JSON.stringify(this.config.fields)); 
		this.fields = this.config.fields;		
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.config.repeatSectionPath);
		
		
		this.collectionNestedPathPredicate = new Bm.RepeatSection.CollectionNestedPathPredicate(
		                              {parentPath: (this.config.configOptions.parentPathWithoutIndex != 'undefined') ? this.config.configOptions.parentPathWithoutIndex : null,									                                                                     
		                               parentLastIndex: this.lastIndex,lastSubPath:this.lastSubPath});
				
		var indexedRowNestedPath = ((typeof this.config.configOptions.parentIndexedNestedPath != 'undefined') ? (this.config.configOptions.parentIndexedNestedPath +'.') : '') + this.lastSubPath;	 
				
		this.parentIndexedNestedPath = indexedRowNestedPath;		
		this.editable = (typeof this.config.configOptions != 'undefined' && typeof this.config.configOptions['editable'] != 'undefined') ? this.config.configOptions['editable'] : true;
		this.initConfig();		
		this.fireEvent('masterrender',indexedRowNestedPath);
    },
	
	initEvents : function(){
        this.on('masterrender', this.onMasterRender, this);
		this.on('masterreconfigure', this.onMasterReconfigure, this);
        this.on('detailrender', this.onDetailRender, this);
		this.on('addrow', this.onAddRow, this);
        this.on('editrow', this.onEditRow , this); 
		this.on('deleterow', this.onDeleteRow, this);  
		this.on('loadrowdata', this.onLoadRowData, this);
        this.on('updaterow', this.onUpdateRow, this);
		this.on('cancel', this.onCancel, this);	
		this.on('reset', this.onReset, this);	
		this.on('customcreate', this.onCustomCreate, this);	
		
    },
	
	
	initConfig: function(){
		// TO_DO define config
	},
	
	createRepeatSection: function(repeatSection,index){		 
		var rp = repeatSection['path'];
		var repeatSectionFields = repeatSection.fields;
		//alert('repeatSectionFields:'+YAHOO.lang.JSON.stringify(repeatSectionFields));
		var columns = Bm.RepeatSection.convertFields(repeatSectionFields,true,this.path);	
		var boColumnsObj = Bm.RepeatSection.convertBoColumns(repeatSectionFields,this.businessObjectName,rp);				
		var boColumns = boColumnsObj['boColumns'];
	    var boSectionsMap = boColumnsObj['boSectionsMap'];		
		columns = Ext.Array.merge(columns, boColumns);		
		// create the proper repeat section		
		var repeatSection = new Portal.ux.BusinessObjectGridView({
							           businessObjectName: this.businessObjectName,
						               repeatSectionPath: repeatSection['path'],
									   repeatSectionClass: repeatSection['className'],
									   repeatSectionTitle: repeatSection['label'],	
									   //parentIndexedNestedPath: ,								   
									   parentDetailDivId: this.detailDivId,
									   columns: columns,
									   callerPath: this.path,
									   callerView: this,
									   isChild:true,
									   callerViewPath:this.path,
									   fields: repeatSectionFields,
									   boSectionsMap: this.boSectionsMap,
									   columns: columns,	
									   lastIndex: index,
									   boColumns: boColumns,									   
									   configOptions: {parentIndexedNestedPath:this.path+'['+index+']',									                   
													   parentPathWithIndex: this.collectionNestedPathPredicate.getPath() ,
													   parentPathWithoutIndex: this.collectionNestedPathPredicate.getPathWithoutIndex() ,
									                   //parentLastIndex: index,
													   //showCreate:true, showDelete:false,customCreate:true,
													   showDelete:this.getAttribute('deleteMode',repeatSection),
													   createMode:this.getAttribute('createMode',repeatSection),
													   editMode:this.getAttribute('editMode',repeatSection),
													   deleteMode:this.getAttribute('deleteMode',repeatSection),
													   numberOfRowsPerPage: this.getAttribute('numrows',repeatSection)											   
													   }});
									   
									  
									   
		
		Bm.RepeatSection.Cache.add(repeatSection['path'],repeatSection);		
		//YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
		//Ext.get(repeatSection['path']+'_master').hide();
		//Ext.get(repeatSection['path']+'_detail').show();			
		
	},
	
	getGridPanel: function(){		
		return Ext.getCmp(this.masterDivId + '_id');		
	},
	
	
	onMasterRender: function(indexedRowNestedPath){	  
		   this.createGrid(indexedRowNestedPath);
		   this.indexedRowNestedPath = indexedRowNestedPath;
		   Bm.RepeatSection.Cache.add(this.path,this);
	},
	
	onMasterReconfigure: function(parentLastIndex){	  		  
		   this.collectionNestedPathPredicate.setParentLastIndex(parentLastIndex);
		   var params = {businessObjectName:this.config.businessObjectName,
			            //indexedNestedPath:indexedNestedPath,						
						indexedNestedPath:this.collectionNestedPathPredicate.getPath(),
			           	dataslotName:this.config.businessObjectName,
			           	className:this.config.repeatSectionClass,
						fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
			           	page: 0,
			           	start:0,
			           	limit:Bm.RepeatSection.pageSize}; 
		   this.store.load({params:params});
		   
	},
	
	onReset: function(){
		//this.firstInitialPath
		var params = {businessObjectName:this.config.businessObjectName,
			            //indexedNestedPath:indexedNestedPath,						
						indexedNestedPath:this.firstInitialPath,
			           	dataslotName:this.config.businessObjectName,
			           	className:this.config.repeatSectionClass,
						fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
			           	page: 0,
			           	start:0,
			           	limit:Bm.RepeatSection.pageSize}; 
		   this.store.load({params:params});	
	},
	
	onDetailRender: function(path,index){	
	     //alert('onDetailRender:\n'+'path:'+path+'\nindex:'+index);	 
		 var indexedRowNestedPath = path+'['+index+']';		 
		 var repeatSections = this.getRepeatSections();		 
		 for(var i=0; i< repeatSections.length; i++){			
				var _rp = Bm.RepeatSection.Cache.get(repeatSections[i]['path']);				
				if(typeof _rp != 'undefined') {
					  var lastSubPath = this.getLastPath(repeatSections[i]['path']);				  
					  _rp.fireEvent('masterreconfigure',index);
					 //_rp.lastIndex = index;
				}else				
				this.createRepeatSection(repeatSections[i],index);						 		
		 }		 
		 this.showDetail();
	},
	
	onCustomCreate: function(){		
		this.form = Ext.widget('form', {
                id: 'create_form_'+this.businessObjectName+'_id',
				layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                border: false,
                bodyPadding: 10,

                fieldDefaults: {
                    labelAlign: 'top',
                    labelWidth: 100,
                    labelStyle: 'font-weight:bold'
                },
                defaults: {
                    margins: '0 0 10 0'
                },
                items: [{
            		xtype: 'fieldset',
            		flex: 1,
            		title: this.title,
            		layout: 'anchor',
            		defaults: {
                		anchor: '100%',
                		hideEmptyLabel: false
            		},
            		items: this.getFormFields('create_')
				}],
                buttons: [{
                    text: 'Cancel',
					scope: this,
                    handler: function() {
                        this.createWindow.close();
						this.createWindow.destroy();
                    }
                }, {
                    text: 'Create',
					scope: this,
                    handler: function() {
                         var formFields = this.getFormFields();
							var values = new Array();
							for(var i=0;i<formFields.length;i++){								
								var _v = Ext.getCmp(formFields[i]['id']).getValue();								
								values.push({name:formFields[i]['name'],value:_v});
							}
							////////////////////////////////////////////////////////
							////////////////////////////////////////////////////////////////////		
							var rowIndex = this.store.getTotalCount();														
							var rowIndexedNestedPath = this.getRowIndexedNestedPath3(this.lastIndex);		
						    rowIndexedNestedPath = rowIndexedNestedPath +'['+(rowIndex)+']';			
		                    if (rowIndexedNestedPath.indexOf(this.businessObjectName) != -1) {			
								rowIndexedNestedPath = rowIndexedNestedPath.substring(this.businessObjectName.length+1,rowIndexedNestedPath.length);		
		                    }
							////////////////////////////////////////////////////////
							var conn = new Ext.data.Connection();
							conn.request({
                        		url: getContextPath()+'/bpmportal/common/form/repeatsection/updateRow.portal',
					    		method: 'POST',
								scope: this,
					    		params: {
                               		fields: YAHOO.lang.JSON.stringify(values),
	  						   		repeatSectionRoot :this.path,
  						       		businessObjectName: this.businessObjectName,
		                       		dataslotName: this.businessObjectName,
		                       		indexedNestedPath:rowIndexedNestedPath,
							   		//newRow:this.isNewRow
							   		newRow:true,
									fiid: Bm.RepeatSection.Cache.getFiid()	 
								},
					    		success: function(req,opt){						   
							   		this.createWindow.close();
									this.createWindow.destroy();
									var store = Ext.getStore(this.masterDivId + '_stroreId');
									var columns = Ext.getCmp(this.masterDivId + '_id').columns;
									var data = {};
									for(var i=0;i<columns.size();i++){									
										var name = columns[i].dataIndex;
										for (var j = 0; j < values.length; j++) {
											if(name == values[j].name)																					
												data[name] = values[j]['value'];
										}									
									}									
									this.fireEvent('masterrender',this.indexedRowNestedPath);					   
					   			},
                       			failure: function(req,opt){					  
							  		var response =YAHOO.lang.JSON.parse(req.responseText);
							  		if(Bm.RepeatSection.debug) 			  
			                   			YAHOO.BM.Logger.error("Error onUpdateRow server response:"+opt['exceptionLocalizedMessage']);                              									 
					           }

				            });		
                    }
                }]
         });
		 ////////////////////////////////////////////////////////////
		 this.createWindow = Ext.widget('window', {
                title: 'Create',
                closeAction: 'hide',
                width: 400,
                height: 400,
                minHeight: 400,
                layout: 'fit',
                resizable: true,
                modal: true,
                items: this.form
            }).show();		
	},
	
	getRepeatSections: function(){
		var repeatSections = new Array();
		for(var i=0; i<this.fields.length;i++){
			if(this.fields[i].collection){
				//alert('Processing repeatSection:'+this.fields[i]['path']);
				repeatSections.push(this.fields[i]);
			}
			
		}
		return repeatSections;
	},
	
	getFieldProperties: function(){
		if(typeof this.fieldProperties != 'undefined')
		    return this.fieldProperties;		
		this.fieldProperties = new Array();	
		for(var i=0; i<this.fields.size();i++){			
			if (this.fields[i]['primative']) {
				var path = this.fields[i]['path'];
				var className = this.fields[i]['className'];
				var p = '';
				p = this.getPropertyPath(path);
				this.fieldProperties.push({
					name: p,
					value: ''
				});
			}			
		}
		
		if((typeof this.config.boColumns != 'undefined') && this.config.boColumns.size() > 0){
			//alert('this.config.boColumns:'+YAHOO.lang.JSON.stringify(this.config.boColumns));
			var columns = this.config.boColumns;
			for (var i = 0; i < columns.length; i++) {
				var path = columns[i]['path']+'';				
				var isAbl = (typeof columns[i]['isAbl'] != 'undefined')? columns[i]['isAbl']: false;
				var className = columns[i]['className'];
				var p = '';					
				p = this.getLastPath(path);				
				
				if (isAbl) {
					// strip the value
					p = this.getFirstPath(path)					
					this.fieldProperties.push({
						name: p,
						value: '',
						path: path,
						primitive: false,
						abl: isAbl
				 });
						
					
				}
				else 
					this.fieldProperties.push({
						name: p,
						value: '',
						path: path,
						primitive: false
					});				
			}
			//alert(YAHOO.lang.JSON.stringify(this.config.boSectionsMap));			
		}
		//alert(YAHOO.lang.JSON.stringify(this.fieldProperties));
		return this.fieldProperties;
		
	},
	
	getFormFields: function(idPrefix){
		if(typeof this.formFields != 'undefined')
		    return this.formFields;		
		this.formFields = new Array();	
		for(var i=0; i<this.fields.size();i++){			
			if (this.fields[i]['primative'] && this.fields[i]['checked']) {
				//alert(YAHOO.lang.JSON.stringify(this.fields[i]));	
				var path = this.fields[i]['path'];
				var className = this.fields[i]['className'];
				var presentation = this.fields[i]['presentation']['deault'];				
				var xtype = Bm.RepeatSection.getFieldType(className);
				xtype = (presentation == 'combobox') ? 'combo' : xtype;
				xtype = (presentation == 'radio') ? 'radiofield' : xtype;
				
								
				var p = '';
				p = this.getPropertyPath(path);
				
				var form_field = {
					id: ((typeof idPrefix != 'undefined') ? idPrefix: '') + p+'_id',
					xtype: xtype,
					fieldLabel: this.fields[i]['label'],
					name: p
				}; 
				if (className == 'java.sql.Timestamp') {
				   form_field['format'] = 'M d, Y h:i A';
				   form_field['dateFormat'] = 'M d, Y h:i A';
				   form_field['xtype'] = 'datefield';				   
				}		      
			    this.formFields.push(form_field);
			
			}			
		}	
		return this.formFields;		
	},
	
	
	createGrid: function(indexedNestedPath){	 	
		  if(indexedNestedPath.indexOf(this.businessObjectName) != -1) {			
			indexedNestedPath = indexedNestedPath.substring(this.businessObjectName.length+1,indexedNestedPath.length);			
		  }		  
		  
		  var url = getContextPath()+"/bpmportal/bo_preview_data.jsp";
		  var params = {businessObjectName:this.config.businessObjectName,
			            //indexedNestedPath:indexedNestedPath,						
						indexedNestedPath:(typeof this.collectionNestedPathPredicate == 'undefined') ? indexedNestedPath : this.collectionNestedPathPredicate.getPath(),
			           	dataslotName:this.config.businessObjectName,
			           	className:this.config.repeatSectionClass,
						fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
						pageSize:this.numberOfRowsPerPage,
			           	page: 0,
			           	start:0,
			           	limit:this.numberOfRowsPerPage};	 
            
            // This code is causing layout issue for columns 
			if (this.columns.length > 9) {
				var gridWidth = 70;//Default width for two columns "No and Delete"
				var forceFit = true;
				for (var i = 1; i < this.columns.length; i++) {
					this.columns[i].flex = 0;
					this.columns[i].width = this.columns[i].text.length * Bm.Constants.GridHeaderCharSize;
					gridWidth = gridWidth + this.columns[i].width;
				}
				if (gridWidth > Ext.getBody().getWidth()) {
					forceFit = false;
				}
			}
		   if(this.deleteMode)
		   this.columns.push({name:'delete',dataIndex:'delete',text:'<img src=\"/sbm/bpmportal/css/dashboard/bo_delete.png\"/>',renderer: this.renderer,
		             width:40,align:'center',sortable:false});		   
		   		  
		   if (typeof Ext.getStore(this.masterDivId + '_stroreId') == 'undefined') {		   	 
			 this.store = Ext.create('Ext.data.JsonStore', {
		   		storeId: this.masterDivId + '_stroreId',
				pageSize: this.numberOfRowsPerPage,
		   		proxy: {
		   			type: 'ajax',
		   			actionMethods : 'GET',
		   			//url: (local ? url.local : url.remote),
								url: url,
								reader: {
									type: 'json',
									root: 'data',
									successProperty: 'success',
									totalProperty: 'totalCount',
									idProperty: '_id'
								}
							},
							remoteSort: false,
							autoDestroy: true,
							fields: this.columns,
							sorters: [],
							pageSize: this.numberOfRowsPerPage
						});
					}
			 function storeListener(store, options) {			   
                options.params = {businessObjectName:this.config.businessObjectName,			            					
						indexedNestedPath:(typeof this.collectionNestedPathPredicate == 'undefined') ? indexedNestedPath : this.collectionNestedPathPredicate.getPath(),
			           	dataslotName:this.config.businessObjectName,
			           	className:this.config.repeatSectionClass,
						fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
						pageSize:this.numberOfRowsPerPage,
			           	page: parseInt(options.page)-1,
			           	start:options.start,
			           	limit:options.limit,
						fiid: Bm.RepeatSection.Cache.getFiid()	 			           
			           	};		
				this.lastStoreParams = options.params;	
				if(typeof this.firstInitialPath == 'undefined')
				 this.firstInitialPath = this.lastStoreParams.indexedNestedPath;	
				return true;
            };
        
            this.store.on('beforeload', storeListener,this);		
					
			this.store.businessObjectName = this.businessObjectName;
			this.store.path = this.path;
			this.store.gridCompId = this.masterDivId + '_id';			
			var id = this.masterDivId.substring(this.masterDivId.indexOf(this.businessObjectName),this.masterDivId.length);			
			if(typeof this.getGridPanel() == 'undefined')
			 this.rowEditingPlugin =  Ext.create('Ext.grid.plugin.RowEditing', {
						clicksToEdit: 1,
						errorSummary: false		
					});
			
			var parentId = this.parentId;	
			this.container_height = (this.numberOfRowsPerPage < 4) ? 200 : parseInt(this.numberOfRowsPerPage)* 45		
			if (typeof this.getGridPanel() == 'undefined') {				
	        	var _columns = new Array();
	        	_columns.push(Ext.create('Bm.grid.RowNumberer'));
	        	_columns = Ext.Array.merge(_columns, this.columns);
	        	//this.columns.push(Ext.create('Bm.grid.RowNumberer'));				
				
				this.gridPanel = Ext.create('Ext.grid.Panel', {
					id: this.masterDivId + '_id',
					store: this.store,
					columns: _columns,
					//title: this.config.repeatSectionTitle,
					//loadMask: true,
					height: this.container_height,
					autoWidth: true,
					autoScroll: true,					
					scope: this,
					padding:4,
                    //forceFit: forceFit,
					renderTo: this.masterDivId,				
					/*listeners: {
						click: {
							//element: 'el', //bind to the underlying el property on the panel
							scope: this,
							fn: function(){
							    alert('click');
							}
						},
						itemclick: {
							//element: 'el', //bind to the underlying el property on the panel
							scope: this,
							fn: function(){
							    //this.rowEditingPlugin.completeEdit();
							}
						},
						
						scrollerhide: {
							scope: this,
							fn: function(){
								//alert('scrollerhide');
							}
							
						},
						
						scrollershow: {
							scope: this,
							fn: function(){
								//alert('scrollershow');
							}
							
						}
					},*/
					frame: true,
					viewConfig: {
						//autoFill: true,
						//forceFit: true,
						//deferEmptyText: false,
						emptyText: '<div style="{text-align: center}"><span style="font-size: 9pt; font-weight: normal">' + getLocalizedString('NoRecordsInGrid') + '</span></span></div>'
					},
					selType: 'rowmodel',
					plugins: ((this.editMode) ? [this.rowEditingPlugin] : [] ),
					listeners: {
						afterrender: function(comp, opt){							
							try {
								if(typeof parentId  != 'undefined' && typeof Ext.getCmp(parentId + '_panel') != 'undefined' &&
								parentId != '')
								Ext.getCmp(parentId + '_panel').doLayout();
							}catch(e){}
							
						}
						
					},					
					dockedItems: [{
						id: this.masterDivId +'toolbar',
						xtype: 'toolbar',
						dock: 'top',
						items: [{							
							id: this.masterDivId + '_create',
							name: this.masterDivId + '_create',
							text: 'Create',
							xtype: 'button',
							enableToggle: true,
							scope: this,
							toggleHandler: function(){						
								
								//handle custom create event								
								if(this.createMode == 'custom') 
								    this.fireEvent('customcreate');
								else{
									// handle create event
									var store = Ext.getStore(this.masterDivId + '_stroreId');
									var columns = Ext.getCmp(this.masterDivId + '_id').columns;
									var data = {};		
									var callback = false;						
									if(store.getCount() % this.numberOfRowsPerPage == 0 && store.getCount() != 0){										
										callback = true;									
									} 
									
									for(var i=0;i<columns.size();i++){									
										var name = columns[i].dataIndex;									
										if(name != null && name.length > 0)
										data[name] = '';									
									}								
									store.add(data);
									var _index = this.lastStoreParams.page * this.numberOfRowsPerPage  + store.getCount()-1
									this.onUpdateRow(store.getAt(_index),_index,callback);									
									//this.gridPanel.setHeight(this.gridPanel.getView().getHeight());
									this.gridPanel.doLayout();		
								}												
							}
						}, '-'/*, {
							//itemId: this.masterDivId + '_cancel',
							id: this.masterDivId + '_cancel',
							name: this.masterDivId + '_cancel',
							text: 'Cancel',
							enableToggle: true,
							scope: this,
							toggleHandler: function(){								
								if (typeof this.callerView != 'undefined') 
									this.callerView.showMaster();
							}
						}*/]
					}, Ext.create('Ext.toolbar.Paging', {
						dock: 'bottom',
						id: this.masterDivId + '_pagingtoolbar_id',
						pageSize: this.numberOfRowsPerPage,
						store: this.store
						
					})]
				
				
				
				});
				if(this.createMode == 'disable')
				    Ext.getCmp(this.masterDivId + '_create').disable(true);
				if(this.resetMode)
				    Ext.getCmp(this.masterDivId +'toolbar').add({						
						id: this.masterDivId + '_reset',
						name: this.masterDivId + '_reset',
						text: 'Reset',
						enableToggle: true,
						scope: this,
						toggleHandler: function(){							
							var conn = new Ext.data.Connection();
							conn.request({
                 				url: '/sbm/bpmportal/common/form/repeatsection/resetRows.portal',
		         				method: 'POST',
								scope:this,
		         				params: {                                            
	  	             				businessObjectName: this.businessObjectName,
									dataslotName: this.businessObjectName,
					 				indexedNestedPath:this.lastStoreParams.indexedNestedPath
		         				},
				 				success: function(req,opt){									
                    				this.fireEvent('reset',this.path);						
				 				},
                 				failure: function(req,opt){                    											            
				 				}
						});
						///////////////						
							
								
						}
					});
				if (this.isChild && !this.isRoot) {					
					this.gridPanel.setTitle(this.callerView.config.repeatSectionTitle +' -> '+this.config.repeatSectionTitle);
					Ext.getCmp(this.masterDivId +'toolbar').add({
						//itemId: this.masterDivId + '_cancel',
						id: this.masterDivId + '_cancel',
						name: this.masterDivId + '_cancel',
						text: '<img src=\"/sbm/bpmportal/javascript/images/arrow_left_blue.png\"/>',
						tooltip: 'Go to parent',
						enableToggle: true,
						scope: this,
						toggleHandler: function(){							
							if(this.isChild)
							   //this.callerView.fireEvent('masterrender',this.getPathWithoutBOName(this.callerViewPath));
							   this.callerView.showMaster();
							else
							   this.fireEvent('masterrender',this.path);
							
							
							
							
								
						}
					});
					
				}else
				   this.gridPanel.setTitle(this.config.repeatSectionTitle);
				
				
				if (this.editMode) {
					this.gridPanel.on('edit', function(editor, e){		
						this.fireEvent('updaterow', e.record, this.lastStoreParams.page * this.numberOfRowsPerPage + e.rowIdx);
					}, this);
					
					this.gridPanel.on('beforeedit', function(editor, e){						
						if(typeof this.store.lastOptions.page != 'undefined') {
		   							this.currentPage= Number(this.store.lastOptions.page) - 1;		
						}
	  					var index = (typeof this.currentPage != 'undefined') ?  (this.currentPage * this.numberOfRowsPerPage +  e.rowIdx) : e.rowIdx;
						
						if (this.isColumnCollection(e.colIdx)) {
							this.rowEditingPlugin.cancelEdit();
							var path = this.getColumnPath(e.colIdx);
							
							// fire the child view						 
							this.fireEvent('detailrender', path, index);
						}
						else 
							if (this.isColumnDelete(e.colIdx)) {
								
									  	
      							this.fireEvent('deleterow', index);								
								//this.rowEditingPlugin.cancelEdit();								
							    
							}else {
								var buttons = Ext.query(".x-grid-row-editor-buttons .x-box-inner");
								// soemtimes buttos gets width zero
								if (buttons.length > 0 && Ext.get(buttons[0]).select('x-box-inner')) {
									var btn = Ext.get(buttons[0]).select('.x-box-inner');
									Ext.get(btn).setWidth(192);
								//this.rowEditingPlugin.completeEdit();			
								}
								
							}
						
					}, this);
				}
			
			
			}	
			
			this.store.load({params:params});
			this.getGridPanel().getView().refresh(); 
			this.showMaster();		
    },
	
	showDetail: function(){		
		Ext.get(this.detailDivId).show(true);
		Ext.get(this.masterDivId).hide(true);
		Ext.get(this.masterDivId).setHeight(0);
		
	},
	
	showMaster: function(){			
		Ext.get(this.detailDivId).hide(true);
		Ext.get(this.masterDivId).show(true);
		Ext.get(this.masterDivId).setHeight(this.container_height);
		
	},
   
   
    isColumnCollection: function(colIdx){
		var isCollection = false;
		var col = this.gridPanel.columns[colIdx];		
		
		if(typeof col != 'undefined')
		for(var i=0; i<this.fields.length;i++){						
			if (typeof this.fields[i] != 'undefined' && this.fields[i]['label'] == col.name && this.fields[i]['collection']) {
				isCollection = true;
				break;
			}			
		}		
		return isCollection;
	},
	
	isColumnDelete: function(colIdx){
		var isDelete = false;
		var col = this.gridPanel.columns[colIdx];
		if(typeof col != 'undefined')		
		if(col.dataIndex == 'delete')
		  isDelete = true;	
		return isDelete;
	},
	
	 getColumnPath:function(colIdx){
	 	var path =null;		
		var col = this.gridPanel.columns[colIdx];		
		for(var i=0; i<this.fields.length;i++){
			//alert(YAHOO.lang.JSON.stringify(this.fields[i]));			
			if (typeof this.fields[i] != 'undefined' && this.fields[i]['label'] == col.dataIndex && this.fields[i]['collection']) {
				path = this.fields[i]['path']
				break;
			}			
		}
		return path;
	},
	
   
    onEditRow:function(path,index){		
		this.fireEvent('detailrender',path,index);
	},
	
	onDeleteRow: function(index){
		var _rowIndexedNestedPath = this.collectionNestedPathPredicate.getPath();		
		var _updatepath = _rowIndexedNestedPath;		
		_rowIndexedNestedPath = _rowIndexedNestedPath +'['+(index)+']';		
		
		var callback = false;		
		if((this.store.getCount() -1) % this.numberOfRowsPerPage == 1 && this.store.getCount() != 0){										
			callback = true;									
		} 
		var index_mod = this.store.getTotalCount() % this.numberOfRowsPerPage;		
		var conn = new Ext.data.Connection();		
	    conn.request({
                 url: getContextPath()+'/bpmportal/common/form/repeatsection/deleteRow.portal',
			     method: 'POST',
				 scope: this,
				 params: {
                          repeatSectionRoot: this.path,
	  			          businessObjectName: this.businessObjectName,
		                  dataslotName: this.businessObjectName,
		                  //indexedNestedPath:indexedNestedPath,
						  indexedNestedPath:_rowIndexedNestedPath,
				          newRow: ((typeof this.isNewRow == 'undefined' || !this.isNewRow) ? "false" : "true"),
						  fiid: Bm.RepeatSection.Cache.getFiid()			  
				 },
				 success: function(req,opt){						  
			                /*var params = {businessObjectName:this.businessObjectName,			                            indexedNestedPath:_updatepath,
			                            dataslotName:this.businessObjectName,
										//fields: this.getPropretyFields(),
										fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
			                            className:this.repeatSectionClass,
					                    newRow: ((typeof this.isNewRow == 'undefined' || !this.isNewRow) ? "false" : "true")};
				 		     this.store.load({params:params});*/							
							
							if(index_mod == 1 && this.lastStoreParams.start != 0 &&
				 		                   this.lastStoreParams.start -this.lastStoreParams.pageSize >= 0) {								 
								 this.lastStoreParams.page = this.lastStoreParams.page;
								 this.lastStoreParams.start = this.lastStoreParams.start -this.lastStoreParams.pageSize;
							     this.store.currentPage = this.lastStoreParams.page;								 
								 
							 }							 
							 this.store.load({params:this.lastStoreParams});
						     
				},
                failure: function(req,opt){                           
				}

		 });
		
	},
   
    onUpdateRow: function(record,rowIndex,callback){    
		var bo_fields = new Array();		
		for (var i = 0; i < this.fields.size(); i++) {
			var path = this.fields[i]['path'];
			var className = this.fields[i]['className'];
			var primative = this.fields[i]['primative'];
			var presentationType = this.fields[i]['presentation']['default'];
						
			if (primative) {
				var p = '';
				p = this.getPropertyPath(path);
				if (className == 'java.sql.Timestamp') {						
					var dateOnly = Bm.RepeatSection.isDateOnly(this.fields[i]);
					var dateFormat = (dateOnly) ? Bm.RepeatSection.Cache.getDateOnlyFormat() :  Bm.RepeatSection.Cache.getDateFormat();
			        var _v = Ext.util.Format.date(record.get(p), dateFormat)
					
					
					bo_fields.push({
						name: p,
						value: Ext.util.Format.date(_v,  Bm.RepeatSection.Cache.getDateFormat())
					});
				}else if (className == 'java.lang.Boolean' && presentationType == 'radio') {
				     bo_fields.push({
						name: p,
						value: (record.get(p) == 'on') ? 'true' : 'false'
					});
				}
				else 
					bo_fields.push({
						name: p,
						value: (typeof record != 'undefined') ? record.get(p) : ''
					});
			}
		}
		
		/////////////////////////////////////////////////////////////////
		///alert(YAHOO.lang.JSON.stringify(this.config.boColumns));	
		if(this.config.boColumns.size() > 0){
			//alert('this.config.boColumns:'+YAHOO.lang.JSON.stringify(this.config.boColumns));
			var columns = this.config.boColumns;
			for (var i = 0; i < columns.length; i++) {
				var path = columns[i]['path'];				
				var className = columns[i]['className'];
				var name = columns[i]['name'];
				var isAbl = (typeof columns[i]['isAbl'] != 'undefined' && columns[i]['isAbl']) ? columns[i]['isAbl'] : false;
				
				var p = '';
				//p = this.getPropertyPath(path);
				p = path;
				var _value = (isAbl) ? record.get(name) : record.get(Bm.RepeatSection.getLastSubPath(p));
				if (className == 'java.sql.Timestamp') {						
						bo_fields.push({
							name: path,
							value: Ext.util.Format.date(_value, Bm.RepeatSection.Cache.getDateFormat()),
							abl: isAbl
						});
					}
				else 
						bo_fields.push({
							name:path,
							value: _value,
							abl: isAbl
						});
					
				//}
			//alert(YAHOO.lang.JSON.stringify(this.config.boSectionsMap));
			}
			
		}
		//alert(YAHOO.lang.JSON.stringify(bo_fields));		
		////////////////////////////////////////////////////////////////////		
		var rowIndexedNestedPath = this.getRowIndexedNestedPath3(this.lastIndex);		
		rowIndexedNestedPath = rowIndexedNestedPath +'['+(rowIndex)+']';			
			
		if (rowIndexedNestedPath.indexOf(this.businessObjectName) != -1) {			
			rowIndexedNestedPath = rowIndexedNestedPath.substring(this.businessObjectName.length+1,rowIndexedNestedPath.length);		
		}
		
		var _rowIndexedNestedPath = this.collectionNestedPathPredicate.getPath()+'['+(rowIndex)+']';		
		var conn = new Ext.data.Connection();
				conn.request({
                        url: getContextPath()+'/bpmportal/common/form/repeatsection/updateRow.portal',
					    method: 'POST',
						scope: this,
					    params: {
                               fields: YAHOO.lang.JSON.stringify(bo_fields),
	  						   repeatSectionRoot :this.path,
  						       businessObjectName: this.businessObjectName,
		                       dataslotName: this.businessObjectName,
		                       indexedNestedPath:_rowIndexedNestedPath,
							   //newRow:this.isNewRow
							   newRow:true,
							   fiid:Bm.RepeatSection.Cache.getFiid()	 
						},
					    success: function(req,opt){	
						       
							   if (callback) {
							   	  this.lastStoreParams.page = this.lastStoreParams.page + 1;
							   }	
							   this.store.load({
							   		  params: this.lastStoreParams
							   });						   	   
							   //this.getGridPanel().doLayout();						   
					   },
                       failure: function(req,opt){					  
							  var response =YAHOO.lang.JSON.parse(req.responseText);
							  if(Bm.RepeatSection.debug) 			  
			                   YAHOO.BM.Logger.error("Error onUpdateRow server response:"+opt['exceptionLocalizedMessage']);	
                              Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p><p>"+response['exceptionLocalizedMessage']+"</p>");							  
					   }

				});				
    },
	
	
	 onCustomUpdateRow: function(record,rowIndex){   	    
		var bo_fields = new Array();		
		//alert(YAHOO.lang.JSON.stringify(this.fields));
		for (var i = 0; i < this.fields.size(); i++) {
			var path = this.fields[i]['path'];
			var className = this.fields[i]['className'];
			var primative = this.fields[i]['primative'];
			
			if (primative) {
				var p = '';
				p = this.getPropertyPath(path);
				if (className == 'java.sql.Timestamp') {						
					bo_fields.push({
						name: p,
						value: Ext.util.Format.date(record.get(p), 'M d, Y h:i A')
					});
				}
				else 
					bo_fields.push({
						name: p,
						value: record.get(p)
					});
			}
		}
		
		/////////////////////////////////////////////////////////////////
		///alert(YAHOO.lang.JSON.stringify(this.config.boColumns));	
		if(this.config.boColumns.size() > 0){
			//alert('this.config.boColumns:'+YAHOO.lang.JSON.stringify(this.config.boColumns));
			var columns = this.config.boColumns;
			for (var i = 0; i < columns.length; i++) {
				var path = columns[i]['path'];				
				var className = this.columns[i]['className'];
				var p = '';
				//p = this.getPropertyPath(path);
				p = path;				
				
				if(className == 'java.sql.Timestamp'){			  
					//alert(Ext.util.Format.date(record.get(p),'M d, Y'));	
					bo_fields.push({name:p,value:Ext.util.Format.date(record.get(Bm.RepeatSection.getLastSubPath(p)),'M d, Y')});
				}else			
					bo_fields.push({name:p,value:record.get(Bm.RepeatSection.getLastSubPath(p))});
				}
				//alert(YAHOO.lang.JSON.stringify(this.config.boSectionsMap));
			
		}
		//alert(YAHOO.lang.JSON.stringify(bo_fields));		
		////////////////////////////////////////////////////////////////////		
		var rowIndexedNestedPath = this.getRowIndexedNestedPath3(this.lastIndex);		
		//if(typeof this.lastIndex != 'undefined'){
			rowIndexedNestedPath = rowIndexedNestedPath +'['+(rowIndex)+']';			
		//}	
		if (rowIndexedNestedPath.indexOf(this.businessObjectName) != -1) {			
			rowIndexedNestedPath = rowIndexedNestedPath.substring(this.businessObjectName.length+1,rowIndexedNestedPath.length);		
		}
		if(typeof rowIndex != 'undefined'){
			//rowIndexedNestedPath = rowIndexedNestedPath +'[' +rowIndex+']';			
		}
		var conn = new Ext.data.Connection();
				conn.request({
                        url: getContextPath()+'/bpmportal/common/form/repeatsection/updateRow.portal',
					    method: 'POST',
						scope: this,
					    params: {
                               fields: YAHOO.lang.JSON.stringify(bo_fields),
	  						   repeatSectionRoot :this.path,
  						       businessObjectName: this.businessObjectName,
		                       dataslotName: this.businessObjectName,
		                       indexedNestedPath:rowIndexedNestedPath,
							   //newRow:this.isNewRow
							   newRow:true,
							   fiid: Bm.RepeatSection.Cache.getFiid()		 
						},
					    success: function(req,opt){						   
							   this.getGridPanel().doLayout();						   
					   },
                       failure: function(req,opt){							  
					   }

				});				
    },
	
	
	getIndexedNestedPath: function(){
          var indexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return indexedNestedPath;
    },

    getRowIndexedNestedPath: function(rowIndex){
          //alert('parentIndexedNestedPath:'+this.parentIndexedNestedPath+'\nlastSubPath:'+this.lastSubPath);
		  if(this.parentIndexedNestedPath.indexOf(this.lastSubPath) > 0) return this.parentIndexedNestedPath;
		  var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath+'['+rowIndex+']';
          return rowIndexedNestedPath;
    },
	
	getRowIndexedNestedPath3: function(rowIndex){
          //alert('parentIndexedNestedPath:'+this.parentIndexedNestedPath+'\nlastSubPath:'+this.lastSubPath);	  
		  if(this.parentIndexedNestedPath == this.lastSubPath){
		  	      if(typeof rowIndex != 'undefined')			   
			      	return this.parentIndexedNestedPath + '['+rowIndex+']';
				  else 
				    return this.parentIndexedNestedPath;			
		  }		      
		  
		  if(this.parentIndexedNestedPath.indexOf(this.lastSubPath) > 0) return this.parentIndexedNestedPath;
		  var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath+'['+rowIndex+']';
          return rowIndexedNestedPath;
    },
	
	getRowIndexedNestedPath4: function(rowIndex){
          //alert('parentIndexedNestedPath:'+this.parentIndexedNestedPath+'\nlastSubPath:'+this.lastSubPath);
		  if(this.parentIndexedNestedPath == this.lastSubPath){
		  	return this.parentIndexedNestedPath;
	      }		  
		  if(this.parentIndexedNestedPath.indexOf(this.lastSubPath) > 0) return this.parentIndexedNestedPath;
		  var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return rowIndexedNestedPath;
    },
	
	getRowIndexedNestedPath2: function(){
          var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath;
          return rowIndexedNestedPath;
    },
	
	getPropertyPath: function(propertyPath){		  
		 propertyPath = propertyPath.substring(this.path.length+1,propertyPath.length);		 
		 return propertyPath;
    },
	
	getFirstPath: function(propertyPath){		  
		 propertyPath = propertyPath.substring(0,propertyPath.indexOf('.'));		 
		 return propertyPath;
    },
	
		
	renderer: function(value, metadata, record, rowIndex, colIndex, store){ 
    	var path = store.path;
	    var businessObjectName = store.businessObjectName;	    
	    var page;		
		if(typeof store.lastOptions.page != 'undefined') {
		   this.currentPage= Number(store.lastOptions.page) - 1;		
		}		
	  	var index = (typeof this.currentPage != 'undefined') ?  (this.currentPage * Bm.RepeatSection.pageSize +  rowIndex) : rowIndex;	  	
      	if(colIndex == 1){
               return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+rowIndex+");\">"+
                 "</a></div>";
      	}else if((this.columns.size()-1) == colIndex){
               	//return "<div style='text-align:center'><a class=\"DeleteImage\" href=\"javascript:Bm.RepeatSection.deleteRow('"+businessObjectName +"','"+path+"',"+rowIndex+");\">"+
	                //"</a></div>";
					//return "<div style='text-align:center;color:red;'><a href=\"#\"><img src=\"/sbm/bpmportal/css/dashboard/bo_delete.png\"/></a></div>";
					metadata.tdAttr = 'data-qtip="Delete row"';
					return "<div style='text-align:center;'><a href=\"#\"><img src=\"/sbm/bpmportal/css/dashboard/bo_delete.png\"/></a></div>";
					
      	}else {
               return "<div style='text-align:center'><a class=\"RowEditImage\" href=\"javascript:Bm.RepeatSection.editRow('"+businessObjectName +"','"+path+"',"+rowIndex+");\">"+
                 "</a></span></div>";
	  	}	

	
   },
   
   getType: function(){
   	  return this.type;
	
   },
   
   getLastPath: function(str){
   	  return str.substring(str.lastIndexOf('.')+1,str.length);	
    },
	
	getPathWithoutBOName: function(str){
   	  return str.substring(str.indexOf('.')+1,str.length);	
    },
	
	getLastPathWithoutIndex: function(indexedPath){		
		if(typeof indexedPath != 'undefined'){			
			if(indexedPath.indexOf(this.businessObjectName) == 0){
				indexedPath = indexedPath.substr(this.businessObjectName.length +1,indexedPath.length);
			}			
			if(indexedPath.lastIndexOf(']') == indexedPath.length-1){
			    indexedPath = indexedPath.substring(0,indexedPath.lastIndexOf('['));
		    }			
		}	
		return indexedPath;
	},
	
	setState: function(path){
		// TO-DO		
	},	
	
	createState: function(options){		
		// TO-DO	
		
	},
	
	 getAttribute: function(configAttrName,config){   	    
		var value = '';			
		var attributes = config['presentation']['values'];		
		gridloop: for (var i = 0; i < attributes.length; i++) {	
		    		
			if(attributes[i]['key'] == 'grid'){				
				var attrs = attributes[i]['attributes'];
		    attributesloop:	for (var j = 0; j < attrs.length; j++) {
				   var attrName = attrs[j]['key'];
				   var attrValue = attrs[j]['value'];
				   var attrMode = attrs[j]['value']['mode'];
				   //alert('attrName:'+attrName+'\nattrValue:'+attrValue+'\nattrMode:'+attrMode);
				   
				   if(attrName == 'create' && configAttrName == 'createMode'){
				         value =  attrs[j]['value']['mode'];
						 break gridloop;
				   }else if(attrName == 'edit' && configAttrName == 'editMode'){
				         value =  attrs[j]['value']['mode'];
						 break gridloop;
				   }else if(attrName == 'delete' && configAttrName == 'deleteMode'){				   	   
					     value =  attrs[j]['value'];
						 break gridloop;
				   }else if(attrName == 'numrows' && configAttrName == 'numrows'){				   	   
					     value =  attrs[j]['value'];
						 break gridloop;
				   }
				}			
			}			
		}
	    return value;	
   }
	
	
	
	
	
	
	
} ); 
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
/*
 * Wrapper extension for Ext.grid.RowNumberer
 */
Ext.define('Bm.grid.RowNumberer', {
    extend : 'Ext.grid.RowNumberer',
    alias: 'widget.bmrownumberer',
    header: getLocalizedString('srno'),
    menuDisabled: false,
    hideable: false,
    minWidth: 30,
    maxWidth: 30
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Business Object Repeat Section Cache

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Ext.define('Portal.ux.BusinessObjectComboView', {
	extend: 'Ext.util.Observable',
	alias: 'repeatsection.combo',
	
	
	constructor: function(config){		
		if(Bm.RepeatSection.debug) {			  
			YAHOO.BM.Logger.debug("-config:" + YAHOO.lang.JSON.stringify(config));
		}
		this.config = {}; 
		Ext.apply(this.config, config);	
		this.type = 'Portal.ux.BusinessObjectComboView';
        this.init(); 
		this.callParent(arguments);
		this.initEvents();
		if (typeof this.config.lazyRendering != 'undefined' && this.config.lazyRendering) {
		// do nothing
		}
		else {
			this.fireEvent('render');
		}
		       	
	},
	
	 init: function(){
        //alert('Repeat Section Path:'+this.config.repeatSectionPath+'\nrepeatSectionTitle:'+this.config.repeatSectionPath);
		this.path = this.config.repeatSectionPath;
		this.businessObjectName = this.config.businessObjectName;
		this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		this.lastIndex = this.config.lastIndex;		
		this.callerView = (typeof this.config.callerView != 'undefined') ? this.config.callerView : null;
		this.callerViewPath = (typeof this.config.callerViewPath != 'undefined') ? this.config.callerViewPath : null;		
		this.showCreate = (typeof this.config.configOptions.showCreate != 'undefined' && 
		   this.config.configOptions.showCreate) ? true: false;
		this.showDelete = (typeof this.config.configOptions.showDelete != 'undefined' && 
		   this.config.configOptions.showDelete) ? true: false;
		this.createCustom = (typeof this.config.configOptions.createCustom != 'undefined' && 
		   this.config.configOptions.createCustom) ? true: false;
		// create, edit and delete modes
		this.createMode = (typeof this.config.configOptions.createMode != 'undefined') ? this.config.configOptions.createMode: 'allow';
		this.deleteMode = (typeof this.config.configOptions.deleteMode != 'undefined' && this.config.configOptions.deleteMode) ? this.config.configOptions.deleteMode :false;
		//this.editMode = (typeof this.config.configOptions.editMode != 'undefined') this.config.configOptions.editMode) ? true: false;
		this.repeatSectionClass = this.config.repeatSectionClass;		
		this.title = this.getLastPath(this.repeatSectionClass); 
	
		this.isChild = (typeof this.config.isChild != 'undefined') ? this.config.isChild : false;
		if(this.isChild)
		this.parentTitle = (typeof this.config.parentTitle != 'undefined') ? this.config.parentTitle : null;		
		this.className = this.config.className
		this.masterDivId = (typeof this.config.masterDivId != 'undefined') ? this.config.masterDivId: this.config.repeatSectionPath+'_master';
	    
		if(typeof this.config.parentDetailDivId != 'undefined'){			 
			  var el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_master'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_master')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);
			  el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_detail'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_detail')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);   
			  
			   
		}
		var indexedRowNestedPath = ((typeof this.config.configOptions.parentIndexedNestedPath != 'undefined') ? (this.config.configOptions.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
		this.parentIndexedNestedPath = indexedRowNestedPath;
		this.collectionNestedPathPredicate = new Bm.RepeatSection.CollectionNestedPathPredicate(
		                              {parentPath: (this.config.configOptions.parentPathWithoutIndex != 'undefined') ? this.config.configOptions.parentPathWithoutIndex : null,									                                                                     
		                               parentLastIndex: this.lastIndex,
									   lastSubPath:this.lastSubPath});
		
		// add detail div because it does not exist if there is master div id		  
		this.detailDivId = this.config.repeatSectionPath+'_detail';  		
		this.mainDivId = this.config.repeatSectionPath;
		this.renderTo = this.config.renderTo
		this.columns = this.config.columns;
		this.fieldValidations = new Array();
		this.comboAttributes = this.config.comboAttributes;		
		this.fields = this.config.fields;
		this.childRepeatSectionPaths = new Array();	
		//this.editable = (typeof this.config.configOptions != 'undefined' && typeof this.config.configOptions['editable'] != 'undefined') ? this.config.configOptions['editable'] : true;			
		
		
    },
	
	initEvents : function(){		
        this.on('render', this.onRender, this);        
		
    },
	
	onRender: function(){		
		// create container
		// add combo
		// add the only sub collection 
		/////////////////////////////////////////////
		var indexedNestedPath = '';
		if(this.path.indexOf(this.businessObjectName) != -1) {			
			indexedNestedPath = this.path.substring(this.businessObjectName.length+1,this.path.length);			
		}		
		
		var url = getContextPath()+"/bpmportal/bo_preview_data.jsp";
		var params = {businessObjectName:this.businessObjectName,
			            //indexedNestedPath:indexedNestedPath,
			           	indexedNestedPath:(typeof this.collectionNestedPathPredicate == 'undefined') ? indexedNestedPath : this.collectionNestedPathPredicate.getPath(),
						dataslotName:this.businessObjectName,
			           	className:this.config.repeatSectionClass,
						fields: YAHOO.lang.JSON.stringify(this.getFieldProperties()),
			           	page: 1,
			           	start:0,
						fiid: Bm.RepeatSection.Cache.getFiid(),
			           	limit:Bm.RepeatSection.pageSize};	
		
		var primitiveType = '';
		var collectionType = '';
		var collectionLabel = '';
		
		for(var i=0; i<this.comboAttributes.length;i++){			
			if(this.comboAttributes[i]['key'] == 'primative')
				primitiveType = this.comboAttributes[i]['value'];				
			
			if (this.comboAttributes[i]['key'] == 'sub_collection') {
				collectionType = this.comboAttributes[i]['value'];
				collectionLabel = this.comboAttributes[i]['title'];
			}
		}
		
		primitiveType = this.getLastPath(primitiveType);
		collectionType = this.getLastPath(collectionType);
		
		//////////////////////////////		
		 var storeId = this.masterDivId.replace(/\./g,'_')+'_StoreId';
		this.comboStore = Ext.create('Ext.data.JsonStore', {           
            autoDestroy: true,            
            storeId: storeId,
			fields: this.columns,
            proxy: {
                type: 'ajax',
				actionMethods : 'GET',
				url: url,                
                reader: {
                    type: 'json',
                    root: 'data',
                    //idProperty: 'id',
                    totalProperty: 'totalCount',					
					successProperty: 'success'//,
					//totalProperty: 'totalCount'
                }
            },
            remoteSort: false,            
            pageSize: 50
        });
		
		function storeListener(store, options) {               
			   // options.params['page'] = ((options.params['start'] / options.params['limit']) ) + "" ;
                //Ext.apply(params, options.params);
				Ext.apply(options.params,params);						
                //params.page = ((options.params['start'] / options.params['limit']) ) + "" ;               
                //this.lastStoreParams = params;
				//store.lastStoreParams = params;
				return true;
            };
        
         this.comboStore.on('beforeload', storeListener,this);
		
		
		
		// get collection node
		var collection_path = '';
		for(var i=0; i< this.config.fields.length;i++){			 
			if (typeof this.config.fields[i]['collection'] != 'undefined' && this.config.fields[i]['collection']) {				
				this.createRepeatSection(this.config.fields[i]);
			}
			
		}
		
		
		
		this.comboStore.load({params:params});		
		Ext.create('Ext.panel.Panel', {
            id: primitiveType+'_container',
			title: this.title,			
            renderTo: this.masterDivId,
            height: 60,
            layout: {
                type: 'vbox',
                padding: 5
            },          
            items: [{
					id: primitiveType,
					xtype: 'combo',
					store: this.comboStore,
					displayField: primitiveType,
					dateFormat: Bm.RepeatSection.Cache.getDateFormat(),
					format: Bm.RepeatSection.Cache.getDateFormat(),
					valueField: primitiveType,
					fieldLabel: collectionLabel,
					listeners: {
						scope:this,
						change: {
							//element: 'el', //bind to the underlying el property on the panel
							scope: this,
							fn: function(){	
							    for(var i=0; i<this.childRepeatSectionPaths.length;i++){
									var rp = Bm.RepeatSection.Cache.get(this.childRepeatSectionPaths[i]);
									if (typeof rp != 'undefined') {
										var _v = Ext.getCmp(primitiveType).getValue();
										var index = this.comboStore.find(primitiveType, _v);
										if(rp.type == 'Portal.ux.BusinessObjectComboView'){
											rp.setLastIndex(index);
											//rp.collectionNestedPathPredicate.rowIndex = index;
											rp.fireEvent('render');
										}else {
										    rp.setLastIndex(index);
										    rp.fireEvent('masterrender', this.path + '[' + index + ']' + '.' + collectionType);
										}
										
										
									}
								}
								
								
								
							}
						}
						
					}
					
				}]
		});
		
		
		
	},
	
	getLastPath: function(str){
   	  return str.substring(str.lastIndexOf('.')+1,str.length);	
    },
	
	getFieldProperties: function(){
		if(typeof this.fieldProperties != 'undefined')
		    return this.fieldProperties;		
		this.fieldProperties = new Array();	
		for(var i=0; i<this.fields.size();i++){			
			if (this.fields[i]['primative']) {
				var path = this.fields[i]['path'];
				var className = this.fields[i]['className'];
				var p = '';
				p = this.getPropertyPath(path);
				this.fieldProperties.push({
					name: p,
					value: ''
				});
			}			
		}
		
		if((typeof this.config.boColumns != 'undefined') && this.config.boColumns.size() > 0){
			//alert('this.config.boColumns:'+YAHOO.lang.JSON.stringify(this.config.boColumns));
			var columns = this.config.boColumns;
			for (var i = 0; i < columns.length; i++) {
				var path = columns[i]['path'];
				var isAbl = (typeof columns[i]['isAbl'] != 'undefined')? columns[i]['isAbl']: false;
				var className = this.columns[i]['className'];
				var p = '';							
				p = this.getLastPath(path);
				
				if(isAbl)
				 this.fieldProperties.push({name:p,value:'',path:path,primitive:false,abl:isAbl});			
				else
				this.fieldProperties.push({name:p,value:'',path:path,primitive:false});				
			}
			//alert(YAHOO.lang.JSON.stringify(this.config.boSectionsMap));			
		}
		//alert(YAHOO.lang.JSON.stringify(this.fieldProperties));
		return this.fieldProperties;
		
	},
	
	getPropertyPath: function(propertyPath){		  
		 propertyPath = propertyPath.substring(this.path.length+1,propertyPath.length);		 
		 return propertyPath;
    },
	
	
	createRepeatSection: function(repeatSection,index){		 
		var rp = repeatSection['path'];
		var repeatSectionFields = repeatSection.fields;		
		var columns = Bm.RepeatSection.convertFields(repeatSectionFields,true,this.path);		
		var boColumns = new Array();		
		var boSectionsMap = new Ext.util.HashMap();
		//var boColumns = new Array();
		var boColumns = Bm.RepeatSection.convertBoColumns(repeatSectionFields,this.businessObjectName,this.path);		
		var _boColumns = boColumns['boColumns'];
		if(typeof _boColumns != 'undefined' && _boColumns.length > 0){
			var _lastSubPath = this.getLastPath(repeatSection['path']);	
			for(var k=0; k<_boColumns.length; k++){
				if(typeof _boColumns[k]['path'] != 'undefined')
				_boColumns[k]['path'] = _boColumns[k]['path'].replace(_lastSubPath +'.','');
			}
			
			
		}
		
		var boSectionsMap = new Ext.util.HashMap();  
	    this.childRepeatSectionPaths.push(rp);	
		columns = Ext.Array.merge(columns,_boColumns);		
		var presentationType = repeatSection['presentation']['default'];
		var repeatSection;
		if (presentationType == 'grid') {
			repeatSection = new Portal.ux.BusinessObjectComboGridView({
				businessObjectName: this.businessObjectName,
				repeatSectionPath: repeatSection['path'],
				repeatSectionClass: repeatSection['className'],
				repeatSectionTitle: repeatSection['label'],
				//parentIndexedNestedPath: ,								   
				parentDetailDivId: this.detailDivId,
				columns: columns,
				callerPath: this.path,
				callerView: this,
				isChild: true,
				callerViewPath: this.path,
				fields: repeatSectionFields,
				boSectionsMap: boColumns['boSectionsMap'],
				lastIndex: index,
				boColumns: _boColumns,
				configOptions: {
					parentIndexedNestedPath: this.path + '[%lastIndex]',
					collectionNestedPathPredicate: this.collectionNestedPathPredicate,
					//parentPathWithIndex: this.collectionNestedPathPredicate.getPath() ,
					parentPathWithoutIndex: this.collectionNestedPathPredicate.getPathWithoutIndex(),
					isRoot: true,
					//parentIndexedNestedPath:this.path+'['+index+']',									                   
					//parentPathWithIndex: this.collectionNestedPathPredicate.getPath() ,
					//parentPathWithoutIndex: this.collectionNestedPathPredicate.getPathWithoutIndex() ,
					numberOfRowsPerPage: this.getAttribute('numrows', repeatSection),
					createMode: this.getAttribute('createMode', repeatSection),
					editMode: this.getAttribute('editMode', repeatSection),
					deleteMode: this.getAttribute('deleteMode', repeatSection),
					parentId: (typeof this.config.configOptions.parentId != 'undfined') ? this.config.configOptions.parentId : ''
				}
			});
		}else if(presentationType = 'collection_combobox'){
			var combo_attrs = null;			
			var values =  repeatSection['presentation']['values'];			
			for(var i=0; i<values.length;i++){				
				if (values[i]['key'] == 'collection_combobox') {
					combo_attrs = values[i]['attributes'];				
				}
			}
			
			repeatSection = new Portal.ux.BusinessObjectComboView({
							           businessObjectName: this.businessObjectName,
						               repeatSectionPath: repeatSection['path'],
									   repeatSectionClass: repeatSection['className'],
									   repeatSectionTitle: repeatSection['label'],									   
									   boSectionsMap: boColumns['boSectionsMap'],  
									   parentDetailDivId: this.detailDivId,
									   columns: columns,									   
									   comboAttributes: combo_attrs,	
									   boColumns: _boColumns,
									   lastIndex: index,								   
									   fields: repeatSectionFields,
									   lazyRendering: true,
									   configOptions: {showCreate:true,
									                  
													  parentIndexedNestedPath: this.path + '[%lastIndex]',
													  collectionNestedPathPredicate: this.collectionNestedPathPredicate,													
													  parentPathWithoutIndex: this.collectionNestedPathPredicate.getPathWithoutIndex(),
													  showDelete:this.getAttribute('deleteMode',repeatSection),
													  customCreate:true,
													  createMode:this.getAttribute('createMode',repeatSection),
													  editMode:this.getAttribute('editMode',repeatSection),
													  deleteMode:this.getAttribute('deleteMode',repeatSection),
													  numberOfRowsPerPage: this.getAttribute('numrows',repeatSection),
													  parentId: this.parentId}
								    });
			
			
		}							   
									  
									   
		
		Bm.RepeatSection.Cache.add(repeatSection['path'],repeatSection);		
		//YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
		//YAHOO.util.Dom.setStyle(repeatSection['path']+'_master','display','block');
		//YAHOO.util.Dom.setStyle(repeatSection['path']+'_detail','display','block');	
				
	},
	
	
	 getAttribute: function(configAttrName, config){
	 	var value = '';
	 	var attributes = config['presentation']['values'];
	 	gridloop: for (var i = 0; i < attributes.length; i++) {
	 	
	 		if (attributes[i]['key'] == 'grid') {
	 			var attrs = attributes[i]['attributes'];
	 			attributesloop: for (var j = 0; j < attrs.length; j++) {
	 				var attrName = attrs[j]['key'];
	 				var attrValue = attrs[j]['value'];
	 				var attrMode = attrs[j]['value']['mode'];
	 				//alert('attrName:'+attrName+'\nattrValue:'+attrValue+'\nattrMode:'+attrMode);
						
						if (attrName == 'create' && configAttrName == 'createMode') {
							value = attrs[j]['value']['mode'];
							break gridloop;
						}
						else 
							if (attrName == 'edit' && configAttrName == 'editMode') {
								value = attrs[j]['value']['mode'];
								break gridloop;
							}
							else 
								if (attrName == 'delete' && configAttrName == 'deleteMode') {
									value = attrs[j]['value'];
									break gridloop;
								}
								else 
									if (attrName == 'numrows' && configAttrName == 'numrows') {
										value = attrs[j]['value'];
										break gridloop;
									}
					}
				}
			}
			return value;
	},
	
	getLastPath: function(str){
   	  return str.substring(str.lastIndexOf('.')+1,str.length);	
    },
	
	setLastIndex: function(index){
		//this.parentIndexedNestedPath = this.parentIndexedNestedPath.replace('%lastIndex',index);
		this.collectionNestedPathPredicate.parentLastIndex = index;		
	}
	
	
});


Ext.define('Portal.ux.BusinessObjectComboGridView', {
	extend: 'Portal.ux.BusinessObjectGridView',
	alias: 'repeatsection.gridcombo',
	
	
	constructor: function(config){
		if(Bm.RepeatSection.debug) {			  
			YAHOO.BM.Logger.debug("-config:" + YAHOO.lang.JSON.stringify(config));
		}
		this.config = {};		
		Ext.apply(this.config, config);		
		this.init(); 
		this.callParent(arguments);
		this.type = 'Portal.ux.BusinessObjectComboGridView';
        this.initEvents();
		
	},
	
	init: function(){
	 //alert('Repeat Section Path:'+this.config.repeatSectionPath+'\nrepeatSectionTitle:'+this.config.repeatSectionPath);
		this.path = this.config.repeatSectionPath;
		this.businessObjectName = this.config.businessObjectName;		
		this.absolutePathWithoutBOName = Bm.RepeatSection.getAbsoluteNestedPathWithoutBOName(this.path,this.businessObjectName);
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.path);
		this.lastIndex = this.config.lastIndex;		
		this.callerView = (typeof this.config.callerView != 'undefined') ? this.config.callerView : null;
		this.callerViewPath = (typeof this.config.callerViewPath != 'undefined') ? this.config.callerViewPath : null;		
		this.showCreate = (typeof this.config.configOptions.showCreate != 'undefined' && 
		   this.config.configOptions.showCreate) ? true: false;
		this.showDelete = (typeof this.config.configOptions.showDelete != 'undefined' && 
		   this.config.configOptions.showDelete) ? true: false;
		this.createCustom = (typeof this.config.configOptions.createCustom != 'undefined' && 
		   this.config.configOptions.createCustom) ? true: false;
		// create, edit and delete modes
		this.createMode = (typeof this.config.configOptions.createMode != 'undefined') ? this.config.configOptions.createMode: 'allow';
		this.deleteMode = (typeof this.config.configOptions.deleteMode != 'undefined' && this.config.configOptions.deleteMode) ? this.config.configOptions.deleteMode :false;
		this.editMode = (typeof this.config.configOptions.editMode != 'undefined' &&  this.config.configOptions.editMode == 'allow') ? true: false;
		this.resetMode = (typeof this.config.configOptions.resetMode != 'undefined' &&  this.config.configOptions.resetMode ) ? true: false;
		this.numberOfRowsPerPage = (typeof this.config.configOptions.numberOfRowsPerPage != 'undefined') ? parseInt(this.config.configOptions.numberOfRowsPerPage): 5;
		
		if(typeof this.config.configOptions.collectionNestedPathPredicate != 'undefined')
		this.collectionNestedPathPredicate = this.config.configOptions.collectionNestedPathPredicate;
				
		this.repeatSectionClass = this.config.repeatSectionClass;		
		this.title = this.getLastPath(this.repeatSectionClass); 
	
	    this.isRoot = (typeof this.config.configOptions.isRoot != 'undefined' && this.config.configOptions.isRoot)
		                 ? true : false;
		this.isChild = (typeof this.config.isChild != 'undefined') ? this.config.isChild : false;
		if(this.isChild)
		this.parentTitle = (typeof this.config.parentTitle != 'undefined') ? this.config.parentTitle : null;		
		this.className = this.config.className
		this.masterDivId = (typeof this.config.masterDivId != 'undefined') ? this.config.masterDivId: this.config.repeatSectionPath+'_master';
	    
		if(typeof this.config.parentDetailDivId != 'undefined'){			 
			  var el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_detail'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_detail')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);   
			  el = {value: '', tag: 'div',id:this.config.repeatSectionPath+'_master'};			  
			  if(Ext.get(this.config.repeatSectionPath+'_master')== null)
			   Ext.DomHelper.append(Ext.get(this.config.parentDetailDivId), el);
			   
		}
		// add detail div because it does not exist if there is master div id		  
		this.detailDivId = this.config.repeatSectionPath+'_detail';  		
		this.mainDivId = this.config.repeatSectionPath;
		this.parentId = this.config.configOptions.parentId;
		this.renderTo = this.config.renderTo
		this.columns = this.config.columns;
		this.fieldValidations = new Array();
		this.validationCache = {};
		
		this.fields = this.config.fields;		
		this.lastSubPath = Bm.RepeatSection.getLastSubPath(this.config.repeatSectionPath);
		this.collectionNestedPathPredicate = new Bm.RepeatSection.CollectionNestedPathPredicate(
		                              {parentPath: (this.config.configOptions.parentPathWithoutIndex != 'undefined') ? this.config.configOptions.parentPathWithoutIndex : null,									                                                                     
		                               parentLastIndex: this.lastIndex,lastSubPath:this.lastSubPath});
				
		var indexedRowNestedPath = ((typeof this.config.configOptions.parentIndexedNestedPath != 'undefined') ? (this.config.configOptions.parentIndexedNestedPath +'.') : '') + this.lastSubPath;		 
		this.parentIndexedNestedPath = indexedRowNestedPath;
		
		this.editable = (typeof this.config.configOptions != 'undefined' && typeof this.config.configOptions['editable'] != 'undefined') ? this.config.configOptions['editable'] : true;
		this.initConfig();		
	},
	
	
	setLastIndex: function(index){
		this.parentIndexedNestedPath = this.parentIndexedNestedPath.replace('%lastIndex',index);
		this.collectionNestedPathPredicate.parentLastIndex = index;		
	},
	
	  onUpdateRow: function(record,rowIndex){   	    
		var bo_fields = new Array();		
		//alert(YAHOO.lang.JSON.stringify(this.fields));
		for (var i = 0; i < this.fields.size(); i++) {
			var path = this.fields[i]['path'];
			var className = this.fields[i]['className'];
			var primative = this.fields[i]['primative'];
			
			if (primative) {
				var p = '';
				p = this.getPropertyPath(path);
				if (className == 'java.sql.Timestamp') {						
					bo_fields.push({
						name: p,
						value: Ext.util.Format.date(record.get(p), 'M d, Y h:i A')
					});
				}
				else 
					bo_fields.push({
						name: p,
						value: record.get(p)
					});
			}
		}
		
		/////////////////////////////////////////////////////////////////
		///alert(YAHOO.lang.JSON.stringify(this.config.boColumns));	
		if(this.config.boColumns.size() > 0){
			//alert('this.config.boColumns:'+YAHOO.lang.JSON.stringify(this.config.boColumns));
			var columns = this.config.boColumns;
			for (var i = 0; i < columns.length; i++) {
				var path = columns[i]['path'];				
				var className = this.columns[i]['className'];
				var p = '';
				//p = this.getPropertyPath(path);
				p = path;				
				
				if(className == 'java.sql.Timestamp'){			  
					//alert(Ext.util.Format.date(record.get(p),'M d, Y'));	
					bo_fields.push({name:p,value:Ext.util.Format.date(record.get(Bm.RepeatSection.getLastSubPath(p)),'M d, Y')});
				}else			
					bo_fields.push({name:p,value:record.get(Bm.RepeatSection.getLastSubPath(p))});
				}
				//alert(YAHOO.lang.JSON.stringify(this.config.boSectionsMap));
			
		}
		//alert(YAHOO.lang.JSON.stringify(bo_fields));		
		////////////////////////////////////////////////////////////////////		
		var rowIndexedNestedPath = this.getRowIndexedNestedPath3(this.lastIndex);		
		rowIndexedNestedPath = rowIndexedNestedPath +'['+(rowIndex)+']';			
			
		if (rowIndexedNestedPath.indexOf(this.businessObjectName) != -1) {			
			rowIndexedNestedPath = rowIndexedNestedPath.substring(this.businessObjectName.length+1,rowIndexedNestedPath.length);		
		}
		
		var conn = new Ext.data.Connection();
				conn.request({
                        url: getContextPath()+'/bpmportal/common/form/repeatsection/updateRow.portal',
					    method: 'POST',
						scope: this,
					    params: {
                               fields: YAHOO.lang.JSON.stringify(bo_fields),
	  						   repeatSectionRoot :this.path,
  						       businessObjectName: this.businessObjectName,
		                       dataslotName: this.businessObjectName,
		                       indexedNestedPath:rowIndexedNestedPath,
							   //newRow:this.isNewRow
							   newRow:true,
							   fiid: Bm.RepeatSection.Cache.getFiid()		 
						},
					    success: function(req,opt){						   
							   this.getGridPanel().doLayout();						   
					   },
                       failure: function(req,opt){							  
					   }

				});				
    },
	
	getRowIndexedNestedPath3: function(rowIndex){
          //alert('parentIndexedNestedPath:'+this.parentIndexedNestedPath+'\nlastSubPath:'+this.lastSubPath);	  
		  if(this.parentIndexedNestedPath == this.lastSubPath){
		  	      if(typeof rowIndex != 'undefined')			   
			      	return this.parentIndexedNestedPath + '['+rowIndex+']';
				  else 
				    return this.parentIndexedNestedPath;			
		  }		      
		  
		  if(this.parentIndexedNestedPath.indexOf(this.lastSubPath) > 0) return this.parentIndexedNestedPath;
		  var rowIndexedNestedPath = ((typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '') + this.lastSubPath+'['+rowIndex+']';
          return rowIndexedNestedPath;
    }
	
});



////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Converts the map dataslot to proper combobox data
////////////////////////////////////////////////////////////////////////////////////////////////////////////
Bm.RepeatSection.convertData = function(data){	
	var values = new Array();	
	for(var key in data){		
		values.push({name:key,value:data[key]});		
	}
	return values;	
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Business Object Repeat Section Cache

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Ext.define('Bm.RepeatSection.Cache', {
    singleton: true,
	alias: 'repeatsection.cache',
	map: new Ext.util.HashMap(),
	storeMap: new Ext.util.HashMap(),
	
    add: function(path, repeatSectionView) {	    
		this.map.add(path,repeatSectionView);
		//console.log(this.map);	
	},
	
	get: function(path) {
   		return this.map.get(path);	
	},
	/**
	 * 
	 */
	addDataStore: function(dsName,dataStoreType){
		
	},
	
	getDataStore: function(dsName,dataStoreType){
		
	},
	
	createDataStore: function(storeName,type,choices,cType){
		var store = null;		
		if(!this.storeMap.contains(storeName)) {		      
			  if(type == 'static'){
			  	     var _choices = new Array();
					 
					 for(var i=0;i<choices.length;i++){
					 	var _label = choices[i].label;
						_choices[_choices.length] = choices[i];						
						_choices[i].name = _label;						 
					 }
					
					 store = Ext.create('Ext.data.Store', {		        		
    					storeId: storeName + '_StoreId',
						fields: ['name', 'value'],						
						data:_choices
						});		
				
			  } else if(type == 'dataslot'){				   
				   var dsType = cType.value.dstype;
				   var dsName = cType.value.name;				   
				   
				   if (dsType == 'LIST') {
				   	var values = new Array();
				   	try {
				   		var _v = eval(dsName);
				   		for (var i = 0; i < _v.length; i++) {
				   			values.push({
				   				name: _v[i],
				   				value: _v[i]
				   			});
				   			
				   		}
				   	} 
				   	catch (e) {
				   	}
				   	store = Ext.create('Ext.data.JsonStore', {
				   		autoDestroy: true,
				   		storeId: storeName + '_StoreId',
				   		proxy: {
				   			type: 'memory',
				   			reader: {
				   				type: 'json',
				   				root: 'data'
				   			}
				   		},
				   		fields: ['name', 'value'],
				   		data: values
				   	
				   	});
				   }else if(dsType == 'MAP'){
				   	
				         store = Ext.create('Ext.data.JsonStore', {
		        			autoDestroy: true,
    						storeId: storeName + '_StoreId',
							proxy: {
	        					type: 'memory',
        						reader: {
	            					type: 'json',
            						root: 'data'
        						}
    						},
							fields: ['name', 'value']
						
				   		});		  
				   		
				   	   var dataslots = new Array();				   
				   	   dataslots.push({name:cType.value.name,type:cType.value.dstype,dataModelType:'combobox'});
				   	   var conn = new Ext.data.Connection();
	   			   	   conn.request({
             				url: getContextPath()+'/bpmportal/common/forms/getdataslots.form',
			 				method: 'POST',
			 				scope: this,
			 				params: {				 			
                 				commandAction: 'getdataslots',
	  		     				dataSlots: YAHOO.lang.JSON.stringify(dataslots)
		     				},
			 				success: function(req,opt){			
			     				if(this.enableLogger) YAHOO.BM.Logger.debug(" Response from server:"+req.responseText); 			     			
								var retJsonValues =YAHOO.lang.JSON.parse(req.responseText);					
								var data = Bm.RepeatSection.convertData(retJsonValues['dataSlots'][cType.value.name],cType.value.dstype)
								Ext.getStore(storeName + '_StoreId').loadData(data);												           
			 				}
				
	   				   });			   
				   }	   
			  }		}
		else 
			store = this.storeMap.get(dsName);
		
		
		if(store == null)
		 store = Ext.create('Ext.data.Store', {
		        		// store configs
    					autoDestroy: true,
    					storeId: storeName + '_StoreId',
						fields: ['name', 'value'],
						data:[]
						});
		
		return store;
	},
	
	getData: function(dsName, dsType){		
		// TO-DO
	},
	
	getFiid: function(){		
		return (document.getElementById('fiid') != null) ? document.getElementById('fiid').value : '';
	},
	
	getDateFormat: function(){
		return _dateFormats.date;
		
	},
	
	getDateOnlyFormat: function(){
		return _dateFormats.dateOnly;
		
	}
    
});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  This overirde is because of an EXT JS BUG the errorSummary attribute does not work
//  It removes the annoying validation messages which is shown in aggregation for invalid forms
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*Ext.override( Ext.grid.plugin.RowEditing, {
   initEditor: function() {      
	    var me = this,
            grid = me.grid,
            view = me.view,
            headerCt = grid.headerCt;	
		
        return  Ext.create('Ext.grid.RowEditor', {
            autoCancel: me.autoCancel,
            errorSummary: false,
            fields: headerCt.getGridColumns(),
            hidden: true,

            // keep a reference..
            editingPlugin: me,
            renderTo: view.el
        });
    } 
});

Ext.override( Ext.grid.RowEditor, {
	loadRecord: function(record) {        
		var me = this,
            form = me.getForm();
        form.loadRecord(record);        
        me.hideToolTip();
        // render display fields so they honor the column renderer/template
        Ext.Array.forEach(me.query('>displayfield'), function(field) {
            me.renderColumnData(field, record);
        }, me);
    },
    repositionIfVisible: function(c){
        var me = this,
            view = me.view;
        
        
        
        if (c && (c == me || (typeof view.isDescendantOf != 'undefined' && !view.isDescendantOf(c)))) {
            return;
        }
        
        if (me.isVisible() && view.isVisible(true)) {
            me.reposition();    
        }
    }
});*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bm.RepeatSection.CollectionNestedPathPredicate(parentPath,parentLastIndex,lastSubPath,rowIndex)

Ext.define('Bm.RepeatSection.CollectionNestedPathPredicate', {    
	alias: 'repeatsection.nestedpathpredicate',
	map: new Ext.util.HashMap(),
	
	/**
	 * 
	 * @param {Object} parentPath
	 * @param {Object} parentLastIndex
	 * @param {Object} lastSubPath
	 * @param {Object} rowIndex
	 */
	//constructor: function(parentPath,parentLastIndex,lastSubPath,rowIndex){
	constructor: function(config){
		Ext.apply(this.config, config);	
		this.parentPath = (typeof this.config.parentPath != 'undefined') ? this.config.parentPath : null;
		this.parentLastIndex = (typeof this.config.parentLastIndex != 'undefined') ? this.config.parentLastIndex : null;
		if(typeof this.config.lastSubPath == 'undefined')  Ext.Msg.alert("Error","<p>Last Sub Path cannot be 'undefined'.</p>"); 
		this.lastSubPath = this.config.lastSubPath;
		this.rowIndex = (typeof this.config.rowIndex != 'undefined') ? this.config.rowIndex : null;
	},
	
    getPath: function() {	
	    var path = '';		
		if(this.parentPath != null) path = this.parentPath;
		if(this.parentLastIndex != null) path = path +'['+this.parentLastIndex+'].';
		if(this.lastSubPath != null) path = path +this.lastSubPath;
		if(this.rowIndex != null) path = path +'['+this.rowIndex+']';		
	    return path;
	},
	
	setParentPath: function(parentPath){
		this.parentPath = parentPath;
	},
	
	getParentPath: function(){
		return this.parentPath;
	},
	
	getPathWithoutIndex: function(){		
		var path = '';
		if(this.parentPath != null) path = this.parentPath;
		if(this.parentLastIndex != null) path = path +'['+this.parentLastIndex+'].';
		if(this.lastSubPath != null) path = path +this.lastSubPath;		
	    return path;
	},
	
	setParentLastIndex: function(parentLastIndex){
		this.parentLastIndex = parentLastIndex;
	},
	
	setLastSubPath: function(lastSubPath){
		this.lastSubPath = lastSubPath;
	},
	
	setRowIndex: function(rowIndex){
		this.rowIndex = rowIndex;
	}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

