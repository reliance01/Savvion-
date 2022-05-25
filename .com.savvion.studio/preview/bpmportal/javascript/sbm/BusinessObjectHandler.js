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
   	    Ext.QuickTips.init();	                  	
		var fields = this.fieldDefs;
		this.gridStore = new Ext.data.SimpleStore({
		        fields:fields
                });
		
        this.gridStore.loadData(this.gridData);		
	        
		var handler = this;
		this.gridPanel = new Ext.grid.GridPanel({
			id:  this.dataSlotName+'_id',
	        store: this.gridStore,
	        cm: this.extGridColumnModel,
	        layout: 'fit',
	        loadMask: true,
	        autoShow: true,
	        autoHeight:true,
	        renderTo: this.dataSlotName,
	        title:this.businessObjectName+' List',
            layout: 'fit',
	        frame:true,
	        viewConfig: {
	            autoFill: true,
	            forceFit:true,
	            deferEmptyText: false,
	            emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGridWithAddList1') + this.businessObjectName + getLocalizedString('NoRecordsInGridWithAddList2') + '</div>'
	        },
                tbar: [{text:'Add '+this.businessObjectName+' to List' , iconCls:'addIcon', handler: this.addBusinessObject,scope: this,pressed: true, enableToggle: true},'-'//,
		            //{text:'Reset List', handler: handler.addBusinessObject, pressed: true, iconCls:'resetIcon' }
		            ]	        
	        /*
		       bbar: new Ext.PagingToolbar({
		       store: this.gridStore,
		       pageSize: 5})
		       */
	   });
    
       setGridPanelWidth(this.gridPanel);
	   	
    },
	
	toggleDetail: function(btn, pressed){
		    var view = grid.getView();
		    view.showPreview = pressed;
		    view.refresh();
	},
	
	addBusinessObject: function(){    
	      $(this.dataSlotName).innerHTML ="Loading...";
	      sbm.utils.updateContent(this.dataSlotName,'GET',getContextPath()+"/bpmportal/common/businessobjects/addBusinessObject.portal?dataslotName="+this.dataSlotName); 
    	}
}

/**
 *  Business Object Handler
 *
 */
Ext.namespace("Bm.RepeatSection");
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
	   // listen to reset event of the form
	  //var handler = this;
	  
}



RepeatSectionHandler.constructor = RepeatSectionHandler;
RepeatSectionHandler.prototype = {
	 init: function(){	 	
		 if(typeof this.rootPanel == 'undefined')
		 this.rootPanel = new FormPanel({
					id: this.businessObjectName+'_panel',
	 				renderTo: this.businessObjectName,
					autoScroll:true,
					autoShow: true,
					border: true,
					margins: '5 5 5 5',
					bodyStyle:'padding:5px;'+this.bodyStyle,
					title: this.root.title,
					collapsible: this.root.collapsible,
					width:'100%', 
					height:'100%'
		 });	 
		 
		for(var i=0; i<this.rootFields.length;i++){		 	
			var field = new Bm.RepeatSection.Field(this.rootFields[i]);
			field.initComponent();
			this.fields[this.fields.length] = field;
		 }
		 this.addValidationRules(this.fiid);
		 this.updateRootFields(this.fiid);         
		 
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
							   var required = (typeof repeatSectionFields[j]['required'] != 'undefined' &&
							          repeatSectionFields[j]['required'] == 'true') ? true : false;								
							   if(typeof repeatSectionFields[j]['dateOnly'] != 'undefined' && repeatSectionFields[j]['dateOnly'] == 'true') {
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
										required: required,
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
									required: required,
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
			            repeatSection = new GridRepeatSection(this.fiid,
															  this.businessObjectName,
						                                      repeatSectionPath,
															  repeatSectionClass,
															  repeatSectionTitle,
															  fields,
															  repeatSectionFields,
															  configOptions);
			            this.root.repeatSectionsCache[repeatSectionPath] = repeatSection;
		            }else if(repeatSectionType == 'lineitem') {
			            repeatSection = new LineItemRepeatSection(this.fiid,
																  this.businessObjectName,
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
			    	var boSection = new BOSection(this.fiid,this.businessObjectName,section[i]['path'],section[i]['className'],section[i]['title']
					                   ,{collapsible:section[i]['collapsible'],bodyStyle:section[i]['bodyStyle']});
			    	//Ext.Msg.alert("Debug","<p>boSectionPath:"+boSectionPath+"\n");
					this.root.boSectionsCache[boSectionPath] = boSection;
		      			 		
		 }
		 YAHOO.util.Event.addListener(document.forms[0], 'reset', this.reset,this,true); 
		
		 
	 },
		 
	 updateRootFields: function(fiid){
		    this.fiid = fiid;
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
								  	    //alert(YAHOO.lang.JSON.stringify(messages));
										var messages = YAHOO.lang.JSON.parse(req.responseText);										
										//alert(messages['exceptionCause']['localizedMessage']);										
                                        var box = Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p><br>\n<p>"+messages['exceptionCause']['localizedMessage']+"</p>");
							            box.getDialog().setWidth(400);
										handler.loadMask.hide();
						          }
				      });		
              }		 
	    }
	 },
	 
	 addValidationRules: function(fiid){
			  this.fiid = fiid;
		      var rootFields = eval(this.businessObjectName + '.root');
		      if(typeof rootFields != 'undefined'){
			         for(var i=0; i<rootFields.length;i++){ 
                         var path = rootFields[i]['path'];
                         var type = rootFields[i]['className'];
					     var args = rootFields[i]['args'];
						 var required = rootFields[i]['required'];
						 if(required == 'true') args.required = required;						 
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
									  if (typeof validation != 'undefined') {
									  	vOptions['validateOn'] = validation['validateOn'];
									  	if (typeof options['isRequired'] != 'undefined') {
									  		vOptions['isRequired'] = options['isRequired'];
									  	}
									  	else {
									  		vOptions['isRequired'] = false;
									  	}
									  	
									  	if (validationConfig['validationType'] == 'email') {
									  		// applicable parameters
													//isRequired true (the default); false
													//minChars/maxChars Positive integer value									
													if (typeof options['minChars'] != 'undefined' && typeof options['maxChars'] != 'undefined' &&
													typeof options['minChars'] != options['maxChars'] != 'undefined') {
														vOptions['minChars'] = options['minChars'];
														vOptions['maxChars'] = options['maxChars'];
														
													}
													
												}
												else 
													if (validationConfig['validationType'] == 'credit_card') {
														if (typeof options['format'] != 'undefined') 
															vOptions['format'] = options['format'];
													}
													else 
														if (validationConfig['validationType'] == 'custom') {
															if (typeof options['pattern'] != 'undefined') 
																vOptions['pattern'] = options['pattern'];
														}
														else 
															if (validationConfig['validationType'] == 'phone_number' ||
															validationConfig['validationType'] == 'social_security_number' ||
															validationConfig['validationType'] == 'zip_code' ||
															validationConfig['validationType'] == 'currency' ||
															validationConfig['validationType'] == 'real' ||
															validationConfig['validationType'] == 'url') {
																vOptions['useCharacterMasking'] = options['useCharacterMasking'];
															}
															else 
																if (validationConfig['validationType'] == 'required') {
																	vOptions['isRequired'] = true;
																}
											}
									  //alert(YAHOO.lang.JSON.stringify(validationConfig['vOptions']));

									  var widgetType = rootFields[i]['type'];
									  if (widgetType == 'sbm.textfield') {
											new Spry.Widget.ValidationTextField(path + "_div", validationConfig['validationType'], vOptions);
									  } else if (widgetType == 'sbm.checkbox') {
											new Spry.Widget.ValidationCheckbox(path + "_div", vOptions);	
									  } else if (widgetType == 'sbm.radio') {
											new Spry.Widget.ValidationRadio(path + "_div", vOptions);	
									  } else if (widgetType == 'datetime') {										
											var dateOnly = (typeof rootFields[i]['dateOnly'] == 'undefined' || rootFields[i]['dateOnly'] == 'false') ? false : true;
											var calendarDateFormat = rootFields[i]['calendarDateFormat'] ;
											var calendarDateOnlyFormat = rootFields[i]['calendarDateOnlyFormat'] ;
											document.getElementById(path).setAttribute('alt','datetime|'+((dateOnly) ? calendarDateOnlyFormat : calendarDateFormat)+(args.required ? '' : '|bok'));
											
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
                    Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p>");							            
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


function GridRepeatSection(fiid, businessObjectName, path, className, title, fields,repeatSectionFields, configOptions){
		  this.fiid = fiid;
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
         this.panel = new FormPanel({
			id: this.mainDivId+'panel',
	 		renderTo: this.mainDivId,
			autoScroll:true,
			autoShow: true,
			border: true,
			margins: '5 5 5 5',
			bodyStyle:'padding:5px;',
			title: this.title,
			collapsible: this.collapsible,
			width:'100%', 
			height:'100%'
		 }); 
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
				this.createGrid(indexedNestedPath,this.fiid);
		   } else {                				 
				var params = {businessObjectName:this.businessObjectName, indexedNestedPath:indexedNestedPath,
			           		   dataslotName:this.businessObjectName,className:this.repeatSectionClass};				 
				this.store.baseParams.indexedNestedPath = indexedNestedPath;
				//alert(YAHOO.lang.JSON.stringify(this.store.baseParams));
		        YAHOO.util.Dom.setStyle(this.path+'_master','display','block');
				YAHOO.util.Dom.setStyle(this.path+'_detail','display','none');
				//alert(YAHOO.lang.JSON.stringify(this.store.lastOptions)); 
				if(typeof this.store.lastOptions.params.page != 'undefined'){
					params.page = this.store.lastOptions.params.page;
					params.start = this.store.lastOptions.params.start;
					params.limit = this.store.lastOptions.params.limit;
					
				}
				   
				this.store.reload({params:params});					 
           }

	},
   
   onDetailRender: function(rowIndex,rowData,isNewRow){	      	  
		  if(Bm.RepeatSection.debug) {
			  YAHOO.BM.Logger.debug("onDtailRender of GridRepeatSection has been fired!");
			  YAHOO.BM.Logger.debug("-rowIndex:" + rowIndex + ", rowData:" +YAHOO.lang.JSON.stringify(rowData) + ", isNewRow:" + isNewRow);
		  }
		  		  
		  if(typeof this.detailPanel == 'undefined' &&  typeof  Ext.getCmp(this.mainDivId + '_panel') == 'undefined'){				  
				  	this.detailPanel = new FormPanel({
				  		id: this.mainDivId + '_panel',
				  		renderTo: this.detailDivId,
				  		autoScroll: true,
				  		autoShow: true,
				  		border: true,
				  		margins: '0 0 0 0',
				  		width: '100%',
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
				 var required = this.fields[i]['required'];
				 if(required) args.required = required;				 
				 var validationConfig = Bm.RepeatSection.getValidationRules(path, type,args);
				 try {
					
					    if(validationConfig['validationType'] != null && typeof validationConfig['validationType'] != 'undefined' 
						               && validationConfig['validationType'] != 'none'){
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
								    } else if (controlType == 'datetime') {	
										var dateOnly = (typeof this.fields[i]['dateOnly'] != 'undefined' &&  this.fields[i]['dateOnly']) ? true : false;																	
										validation = new Bm.RepeatSection.DateFieldValidation(path,'date',{required:required,dateOnly: dateOnly,
										                               calendarDateFormat: this.businessObject.calendarDateFormat,
																	   calendarDateOnlyFormat: this.businessObject.calendarDateOnlyFormat,
																	   jsCalendarDateFormat: this.businessObject.jsCalendarDateFormat,
																	   jsCalendarDateOnlyFormat: this.businessObject.jsCalendarDateOnlyFormat																	   
																	   });									
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
                         var bosection = new BOSection(this.fiid,this.businessObjectName, boSectionPath, boSectionClassName, boSectionClassTitle, {parentIndexedNestedPath:rowIndexedNestedPath,isNewRow:(typeof isNewRow != 'undefined' ? isNewRow : false),collapsible:boSectionCollapsible,bodyStyle:boSectionBodyStyle});
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
								                      repeatSection = new GridRepeatSection(this.fiid,
																			this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,																		
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable,numberOfRowsPerPage:repeatSectionNumberOfRowsPerPage});
 						          else if(repeatSectionType == 'lineitem')
                                  repeatSection = new LineItemRepeatSection(this.fiid,
																			this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,																			
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable}
																			);
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
         this.observer.fireEvent('loadrowdata',this.store.getTotalCount(),this.fiid,true);
	},

    onEditRow: function(rowIndex){
		  if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onEditRow has been fired!");		 
		  this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		  this.loadMask.show();
          YAHOO.util.Dom.setStyle(this.path+'_master','display','none');
		  YAHOO.util.Dom.setStyle(this.path+'_detail','display','block'); 
          this.observer.fireEvent('loadrowdata',rowIndex,this.fiid,false);
    },

    onDeleteRow: function(index){
		 if(Bm.RepeatSection.debug) 			  
			  YAHOO.BM.Logger.info("onDeleteRow has been fired!");	
		 this.loadMask = new Ext.LoadMask(Ext.get(this.path), {msg:"Please wait..."});
		 this.loadMask.show();
                  
		 var parentNestedPath = (typeof this.parentIndexedNestedPath != 'undefined') ? (this.parentIndexedNestedPath +'.') : '';		 
		 var indexedRowNestedPath = parentNestedPath + this.lastSubPath + '['+index + ']';
		 var indexedNestedPath = parentNestedPath + this.lastSubPath;
		 var index_mod = this.store.getTotalCount() % this.numberOfRowsPerPage;	
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
				 		     
							 if(index_mod == 1 && handler.store.lastStoreParams.start != 0 &&
				 		                   handler.store.lastStoreParams.start -handler.store.lastStoreParams.pageSize >= 0) {
								 handler.store.lastStoreParams.page = handler.store.lastStoreParams.page -1;
								 handler.store.lastStoreParams.start = handler.store.lastStoreParams.start -handler.store.lastStoreParams.pageSize;
							 }							 
							 handler.store.load({params:handler.store.lastStoreParams});	
						     handler.loadMask.hide();
				},
                failure: function(req,opt){
                           Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
						   handler.loadMask.hide();
				}

		 });
	},

    onLoadRowData: function(rowIndex,fiid,isNewRow){	
		 this.fiid = fiid;
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
							if(Bm.RepeatSection.debug) 			  
			                   YAHOO.BM.Logger.error("Error onLoadRowData server response:"+req.responseText);	
                            Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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
					   	      if(Bm.RepeatSection.debug) 			  
			                   YAHOO.BM.Logger.error("Error onUpdateRow server response:"+req.responseText);	
                              Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p><p>"+opt+"</p>");
							  handler.loadMask.hide();
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

    createGrid: function(indexedNestedPath,fiid){
		  this.fiid = fiid;	
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
		   //alert(YAHOO.lang.JSON.stringify(this.fields));
		   this.store = new Ext.data.JsonStore({
                    url: url,
                    root: 'data',
                    totalProperty: 'totalCount',
                    fields: this.fields,
					remoteSort: true,
					scope: this,
                    baseParams: params
            });
		     
		    function storeListener(store, options) {
				
                options.params['page'] = ((options.params['start'] / options.params['limit']) ) + "" ;
                Ext.apply(params, options.params);
				
							
                params.page = ((options.params['start'] / options.params['limit']) ) + "" ;
                store.lastStoreParams = params;
                return true;
            };
        
            this.store.on('beforeload', storeListener,this);
		    this.store.path = this.path
            this.store.businessObjectName = this.businessObjectName;
		 
		    var columnsDef = new Array();
		    columnsDef[0] = new Ext.ux.grid.PagingRowNumberer({header: "No",width: 30, sortable: false});
			if (this.editable) {
				columnsDef[1] = {
					header: 'Edit',
					width: 50,
					sortable: false,
					renderer: this.renderer,
					dataIndex: 'edit',
					name: 'edit'
				};
				columnsDef[2] = {
					header: 'Delete',
					width: 50,
					sortable: false,
					renderer: this.renderer,
					dataIndex: 'delete',
					name: 'delete'
				};
			}
		    for(var i=2; i< this.fields.length;i++){
               var colHeaderWidth = (typeof this.fields[i]['label'] != 'undefined')? this.fields[i]['label'].length * 8 : 120; // Convert to pixel based on column label
                if(colHeaderWidth > 100) {
                    this.fields[i]['width'] = colHeaderWidth;
                }
			    
				if(this.editable)
			       	columnsDef[columnsDef.length] = this.fields[i];
			    else if(this.fields[i]['dataIndex'] != 'drilldown')				
			    	columnsDef[columnsDef.length] = this.fields[i];
		    }		 	
		 
		    var cm = new Ext.grid.ColumnModel(columnsDef);
		    var handler = this;
			document.getElementById(this.masterDivId).innerHTML = '';
			if (this.editable) {				
				this.gridPanel = new Ext.grid.GridPanel({
						id:  this.masterDivId+"_id",
						store: this.store,
						cm: cm,
						loadMask: true,
						autoHeight: true,
						scope: this,
						renderTo: this.masterDivId,
						//title: this.path,
						frame: true,
						viewConfig: {
							deferEmptyText: false,
							emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGridWithAddList') + '</div>'
						},
						
						tbar: [{
							text: getLocalizedString('AddToList'),
							iconCls: 'addIcon',
							handler: function(){
								handler.observer.fireEvent('addrow')
							},
							scope: handler,
							pressed: true,
							enableToggle: true
						}, '-'//,//{text:'Reset List', handler: handler.reset, pressed: true, iconCls:'resetIcon' }
						],
						bbar: new Ext.PagingToolbar({
							store: this.store,
							pageSize:this.numberOfRowsPerPage
						})
				
				  });
				  
			} else {
				this.gridPanel = new Ext.grid.GridPanel({
						store: this.store,
						cm: cm,
						loadMask: true,
						autoHeight: true,
						renderTo: this.masterDivId,
						//title: this.path,
						frame: true,
						viewConfig: {
							deferEmptyText: false,
							emptyText: '<div class="emptyTextConfig">' + getLocalizedString('NoRecordsInGrid')+'</div>'
						},
						
						
						bbar: new Ext.PagingToolbar({
							store: this.store,
							pageSize:this.numberOfRowsPerPage
						})
					});
					
			}
		    this.store.load({params:params});
		    setGridPanelWidth(this.gridPanel);
			this.gridPanel.render();
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
	if (typeof store.lastOptions.params.page != 'undefined') {
		this.currentPage= Number(store.lastOptions.params.page);		
	}	
	var index = store.lastOptions.params.page * store.lastOptions.params.pageSize +  rowIndex;
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


function LineItemRepeatSection(fiid, businessObjectName, path, className, title, fields,repeatSectionFields, configOptions){	   
	    this.fiid = fiid;
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
		 this.panel = new FormPanel({
			id: this.mainDivId+'_panel',
	 		renderTo: this.mainDivId,
			autoScroll:true,
			autoShow: true,
			border: true,
			margins: '5 5 5 5',
			bodyStyle:'padding:3px;',
			title: this.title,
			collapsible:this.collapsible,
			width:'100%', 
			height:'100%'
		 });		 
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
		   this.navToolbar = new Bm.RepeatSection.NavigationToolbar({repeatSection: this});
		   if (this.editable) {		   	
		   	this.detailpanel = new FormPanel({
		   		id: this.detailDivId + '_panel',
		   		renderTo: this.detailDivId,
		   		autoScroll: true,
		   		autoShow: true,
		   		border: true,
		   		margins: '5 5 5 5',
		   		bodyStyle: 'padding:3px',
		   		width: '100%',
		   		//height: '100%',
		   		tbar: [{
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
		   		bbar: this.navToolbar
		   	});
		   }else {
		   	 this.detailpanel = new FormPanel({
		   		id: this.detailDivId + '_panel',
		   		renderTo: this.detailDivId,
		   		autoScroll: true,
		   		autoShow: true,
		   		border: true,
		   		margins: '5 5 5 5',
		   		bodyStyle: 'padding:3px',
		   		width: '100%',
		   		height: '100%',		   		
		   		bbar: this.navToolbar
		   	});
			
		   }
									 	
								 
		  
	 },

   onLoadNavToolBarData: function(indexedRowNestedPath){  
          var conn = new Ext.data.Connection();
	      conn.request({
                 url: getContextPath()+'/bpmportal/common/repeatsection/getRows.portal',
			     method: 'POST',
				 scope: this,
				 params: {
					fiid:this.fiid,
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
                          Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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
                          Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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
                               Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p>");
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
                         Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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
                         Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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

						                var controlType = null;
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
										} else if(controlType != 'datetime'){
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
                         var bosection = new BOSection(this.fiid,this.businessObjectName, boSectionPath, boSectionClassName, boSectionClassTitle, {parentIndexedNestedPath:rowIndexedNestedPath,isNewRow:(typeof isNewRow != 'undefined' ? isNewRow : false),collapsible:boSectionCollapsible,bodyStyle:boSectionBodyStyle,editable:boSectionEditable},this.fiid);
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
								          repeatSection = new GridRepeatSection(this.fiid,
										                                    this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,																		
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle,editable:repeatSectionEditable});
 						              else if(repeatSectionType == 'lineitem')
                                          repeatSection = new LineItemRepeatSection(this.fiid,
										                                    this.businessObjectName,
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
function BOSection(fiid,businessObjectName, path, className, title, configOptions){
		this.fiid = fiid;
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
	  	  this.panel = new FormPanel({
	                   id: this.path+'_panel',
			           renderTo:this.path,
			           autoScroll:true,
			           autoShow: true,
			           border: true,
			           margins: '5 5 5 5',
			           bodyStyle:'padding:3px',
	  		           title: this.title,
	  		           collapsible:this.collapsible,
										 width:'100%', 
			               height:'100%'
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
                           Ext.Msg.alert("Error","<p>Failed to get row detail.</p><p>Please see the bizsolo log file.</p>");
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
						 }else if(rowData[name] != null) {
							document.getElementById(path).value = (typeof rowData == 'undefined') ? '' : rowData[name];
						 }
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
								                      repeatSection = new GridRepeatSection(this.fiid,
																			this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,
								  	                                        {parentIndexedNestedPath:rowIndexedNestedPath,collapsible:repeatSectionCollapsible,bodyStyle:repeatSectionBodyStyle});
 						             else if(repeatSectionType == 'lineitem')
                                      repeatSection = new LineItemRepeatSection(this.fiid,
										                                    this.businessObjectName,
									                                        repeatSectionPath,
									                                        repeatSectionClass,
									                                        repeatSectionTitle,
									                                        fields,
																			repeatSectionFields,																	
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
                              Ext.Msg.alert("Error","<p>Failed to update row.</p><p>Please see the bizsolo log file.</p>");
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
	 var rootHandler = eval(businessObjectName);  
   var repeatSection = rootHandler.repeatSectionsCache[path]; 
	 repeatSection.observer.fireEvent('editrow',index);
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
			             invalidWidgets[invalidWidgets.length] = (fieldValidations[i].element.getAttribute('id').indexOf('_div') != -1) ? 
						    fieldValidations[i].element.getAttribute('id').substring(0,fieldValidations[i].element.getAttribute('id').indexOf('_div'))
							: fieldValidations[i].element.getAttribute('id');			  
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
	  if(type == 'java.sql.Timestamp'){
	  	  validationType = 'datetime';
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

Bm.RepeatSection.DateFieldValidation = function(id,validationType, options){
   this.id = id;   
   this.dateOnly = options.dateOnly;
   this.required = (typeof options != 'undefined' && typeof options.required != 'undefined'
		               && options.required) ? true : false;     
   this.element = document.getElementById(id);
   this.calendarDateOnlyFormat = options.calendarDateOnlyFormat;
   this.calendarDateFormat = options.calendarDateFormat;     
   this.dateOnly = options.dateOnly;    
}

Bm.RepeatSection.DateFieldValidation.prototype = {
	validate: function(){
		var isValid = true;		
		var value = document.getElementById(this.id).value;		
		if(this.required && value.length == 0){
		     isValid = false;
		}else if(value.length == 0){
			// do nothing
		}else if(value.length != 0){
			// validate date time
			try {
				
				if(!Date.isSBMDateValid(value,this.calendarDateFormat)) isValid = false;
				
			}catch(e){				
				isValid = false;				
			}
		}		
		return isValid;
	}	
};



Bm.RepeatSection.NavigationToolbar = Ext.extend(Ext.Toolbar, {   
    displayMsg : 'Displaying {0} - {1} of {2}',
    emptyMsg : 'No data to display',
    beforePageText : "Item",
    afterPageText : "of {0}",
    firstText : "First",
    prevText : "Previous",
    nextText : "Next",
    lastText : "Last",
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
        this.first = this.addButton({
            tooltip: this.firstText,
            iconCls: "x-tbar-page-first",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["first"])            
				
        });
        this.prev = this.addButton({
            tooltip: this.prevText,
            iconCls: "x-tbar-page-prev",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["prev"])			
        });
        this.addSeparator();
        this.add(this.beforePageText);
        
		
		/*this.field = Ext.get(this.addDom({
           tag: "input",
           type: "text",
           size: "3",
           value: "1",
           cls: "x-tbar-page-number"
        }).el);*/
		
		this.field = new Ext.form.NumberField({
           tag: "input",
           type: "text",
           size: "3",
           value: "1",
           cls: "x-tbar-page-number"
		});		
		
        this.field.on("keydown", this.onPagingKeydown, this);
        //this.field.on("focus", function(){this.field.select();});
        this.add(this.field);
		//this.afterTextEl = this.addText(String.format(this.afterPageText, 1));
        
		this.afterTextItem = new Ext.Toolbar.TextItem({
            text: String.format(this.afterPageText, 1)
        });
		this.add(this.afterTextItem);
		
		this.field.setHeight(18);
		
		
        this.addSeparator();
        this.next = this.addButton({
            tooltip: this.nextText,
            iconCls: "x-tbar-page-next",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["next"])            
        });
        this.last = this.addButton({
            tooltip: this.lastText,
            iconCls: "x-tbar-page-last",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["last"])           
        });
        this.addSeparator();
	
        /*
		this.loading = this.addButton({
            tooltip: this.refreshText,
            iconCls: "x-tbar-loading",
            handler: this.onClick.createDelegate(this, ["refresh"])
        });*/

        if(this.displayInfo){
            this.displayEl = Ext.fly(this.el.dom).createChild({cls:'x-paging-info'});
        }
		
       
    },

    onActiveItemChanged: function(totalCount,activeItem){		
		this.afterTextItem.update(String.format(this.afterPageText, totalCount));		
        this.field.setValue(activeItem);		
        this.first.setDisabled(activeItem == 1);
        this.prev.setDisabled(activeItem == 1);
        this.next.setDisabled(activeItem == totalCount);
        this.last.setDisabled(activeItem == totalCount);

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
         this.observer.fireEvent('activeitemchnaged', totalCount,activeItem);
	},

    onClick : function(which){             
		switch(which){
            case "first":
                  	this.setItemData(this.totalCount,1);
                  	this.repeatSection.observer.fireEvent('loadrowdata',0);
                  	break;
            case "prev":
				  	this.setItemData(this.totalCount,this.pageActiveItem-1);
		          	this.repeatSection.observer.fireEvent('loadrowdata',this.pageActiveItem-1);
                  	break;
            case "next":
                  	this.setItemData(this.totalCount,this.pageActiveItem+1);
                  	this.repeatSection.observer.fireEvent('loadrowdata',this.pageActiveItem+1);
                  	break;
            case "last":
                  	this.setItemData(this.totalCount,this.totalCount);
				  	this.repeatSection.observer.fireEvent('loadrowdata',this.totalCount);
                    break;           
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
                if(this.disabled && this.el._mask){
                    this.el._mask.setSize(this.el.dom.clientWidth, this.el.getHeight());
                }
            }else{
                // Adds an event to set the correct height afterExpand.  This accounts for the deferHeight flag in panel
                this.queuedBodySize = {width: w, height: h};
                if(!this.queuedExpand && this.allowQueuedExpand !== false){
                    this.queuedExpand = true;
                    this.on('expand', function(){
                        delete this.queuedExpand;
                        this.onResize(this.queuedBodySize.width, this.queuedBodySize.height);
                    }, this, {single:true});
                }
            }
            this.onBodyResize(w, h);
        }
        this.syncShadow();
        Ext.Panel.superclass.onResize.call(this, adjWidth, adjHeight, rawWidth, rawHeight);

    }
});

// Fix for CR#RPM00035914: Columnheaders appear shrunk within BO having >30 attributes
// For the fix, scroll bar is added to the grid to view all the attributes without shrinking
function setGridPanelWidth(gridPanel){
    var el = gridPanel.getGridEl();
    if(!Ext.isEmpty(el)) {
        var width = gridPanel.getView().getOffsetWidth();	   	
        gridPanel.setWidth(parseInt(width.substr(0,width.length-2)));
    }	   		    
}