Ext.namespace("Bm.mvc.grid");

Ext.define('Bm.mvc.grid.Panel' ,{
    extend: 'Ext.grid.Panel',
//	extend: 'Bm.grid.GridPanel',
    alias : 'widget.mvcgrid',
    
    title : 'Default Title',
	forceFit: true, //this causes trouble in colmn widths if flex is used
    
    
    //custom props
    showTopPagingToolbar : false,
	showBottomPagingToolbar : false,
    toolBarItems: [],
	
    columns: [],
    
    viewConfig: {
		stripeRows: true,
        emptyText: '<div align="center"><span class="Info infoIcon"><span style="font-size: 9pt; font-weight: bold">'+getLocalizedString("NoFavorites")+'</span></span><div>',
        deferEmptyText : false
	},
            
			
    dockedItems: [
			{
                xtype: 'toolbar',
                dock: 'top',
                items: []
            }],
    
    initComponent: function(config) {
        //First Override user defined config options
        Ext.apply(this, config); 

        //TODO: is there any better of doing it
        this.dockedItems[0].items = this.toolBarItems;
        
        if(this.showTopPagingToolbar == true) {
            this.tbar = new Ext.PagingToolbar({
                store: this.store,
                displayInfo: true,
                pageSize : this.pageSize,
                id: 'Bm.mvc.grid.Panel.tbar'
            });
        }
        
        if(this.showBottomPagingToolbar == true) {
            this.bbar = new Ext.PagingToolbar({
                store: this.store,
                displayInfo: true,
                pageSize : this.pageSize,
                id: 'Bm.mvc.grid.Panel.bbar',
                
                beforePageText: getLocalizedString("beforePageText"),
                nextText : getLocalizedString("nextText"),
				lastText: getLocalizedString("lastText"),
				prevText : getLocalizedString("prevText"),
				firstText: getLocalizedString("firstText"),
				refreshText : getLocalizedString("refreshText"),
                displayMsg: getLocalizedString("displayMsgText")
                
            });
        }
		
        this.callParent(arguments);
    }
    
});


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Ext.namespace("Bm.mvc.data");
var itemsPerPage = 4;
if(pageSize != null && pageSize != undefined){
	itemsPerPage = pageSize;
}
Ext.define('Bm.mvc.data.Store', {
    extend: 'Ext.data.Store',
        
//    autoLoad: {params:{start:0, limit:itemsPerPage}}, //To enable pagination
	autoLoad: true,
    autoSync: false,
    remoteSort: false,
//    pageSize: itemsPerPage, // items per page //To enable pagination
        
    config:{
    	proxyUrl: 'url'	
    },
       
});

function getRestProxy(urlValue){
	return Ext.create('Ext.data.proxy.Rest', 
			{
				url:urlValue,
				reader: {
		            type: 'json',
		            root: 'data',
		            successProperty: 'success',
		            totalProperty: 'count'
		        },
		        writer: {
		        	writeAllFields: true
		        },
		        actionMethods: {create: "PUT", read: "GET", update: "POST", destroy: "DELETE"}
			
			});
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Ext.namespace("Bm.mvc.window");
Ext.define('Bm.mvc.window.Window', {
    extend: 'Ext.window.Window',
    requires: ['Ext.form.Panel'],

    //override
    title : 'Default',
    layout: 'fit',    
    height: 300,
    width: 380,
//    width: 400,
    autoShow: true,
    modal: true,
	resizable: false,
	
	//custom props 
	formFields:[],
	windowButtons:[],
	
    initComponent: function(config) {
    	var me = this;
    	
    	//First Override user defined config options
    	Ext.apply(this, config);
    	var btns = config.windowButtons;
    	var closeBtn = Ext.create('Ext.Button', {
   			text: getLocalizedString('Cancel'),
   			tooltip: getLocalizedString('Cancel'),
    		handler: function(){
    			me.close();
    		}
		});
        btns.push(closeBtn);
    	
        this.items = [
            {
                xtype: 'form',
                padding: '5 5 0 5',
                border: false,
//                border: true,
                style: 'background-color: #fff;',
                items: this.formFields
            }
            /*,
            {
            	xtype: 'button',
//            	padding: '50 250 0 5',
            	border: true,
            	text: 'group',
            	action: 'chooseGroup'
            }*/
        ];
        	
        this.buttons = btns;
        
        this.callParent(arguments);
    }//end of initComp
    
});

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * This method generates the column array based on passed 'headerConfig' and customConfig object.
 * headerConfig - header Information coming from server
 * customConfig - Map <dataIndex, config>, custom Column config object which will override default props
 * headingCols - The columns array to be appended before the generated column array
 * tailingCols - The columns array to be appended after the generated column array
 * 
 */
function prepareGridColumnModel(headerConfig, customConfig, headingCols, tailingCols){
	var columnArr;
	
	//add the heading columns
	if(headingCols != undefined && headingCols != null && headingCols.length > 0){
        columnArr = headingCols;
    } else {
        columnArr = new Array();
    }
	
    //add the columns created based on 'header' data from server
	for(var indx=0; indx < headerConfig.length; indx++){
		var serverData = headerConfig[indx];
		var column, flexVal=1;
		column = Ext.create('Ext.grid.column.Column',{header:serverData.label, dataIndex:serverData.dataIndex, menuDisabled:true, sortable:false, renderer: getToolTipRendererFunction()});

		if(customConfig.containsKey(serverData.dataIndex) == true){
			var customColConfig = customConfig.get(serverData.dataIndex); 
//			alert('key = ' + serverData.dataIndex+ ' & customConfig.getKey() = '+customColConfig);
			Ext.apply(column, customColConfig);
		}
		
		if(serverData.hiddenField == 'false'){
			columnArr.push(column);
		}
		
	}
	
	//add the tailing columns
	if(tailingCols != undefined && tailingCols != null && tailingCols.length > 0){
        for(var indx=0; indx < tailingCols.length; indx++){
        	columnArr.push(tailingCols[indx]);
        }
    }
	return columnArr;
	
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * This method generates the form fields based on headerConfig object and customConfig object
 * headerConfig - header Information coming from server
 * customConfig - Map <name, config>, custom TextField config object which will override default props
 */
function getFormFields(headerConfig, customConfig){
	var fieldArr = [];
    	                           
	for(var indx=0; indx < headerConfig.length; indx++){
		var serverData = headerConfig[indx];
		var field;
		var customFieldConfig;
		if(customConfig.containsKey(serverData.name) == true){
			customFieldConfig = customConfig.get(serverData.name);
			if(customFieldConfig.xtype === 'textarea'){
				field = Ext.create('Ext.form.field.TextArea',{fieldLabel:serverData.label, name:serverData.name, maxLength:serverData.size, enforceMaxLength:true});
			}else if (customFieldConfig.xtype === 'combobox'){
				field = Ext.create('Ext.form.field.ComboBox', {fieldLabel: serverData.label, name:serverData.name, store: customFieldConfig.languageStore, queryMode: 'local', displayField: 'name', valueField: 'value', value:'EN_US'});
			}else{
				field = Ext.create('Ext.form.field.Text',{fieldLabel:serverData.label, name:serverData.name, maxLength:serverData.size, enforceMaxLength:true});
			}
			
//			alert('key = ' + serverData.name+ ' & customConfig.getKey() = '+customFieldConfig);
			Ext.apply(field, customFieldConfig);
		}
		
		if(serverData.hiddenField == 'false'){
			fieldArr.push(field);
		}
		//overriden hiddenField property of server data
		if(customFieldConfig.hiddenField == false){
			fieldArr.push(field);
		}
		
	}
	return fieldArr;
	
};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function getFormButtons(isReset){
	var btnArr = [];
	btnArr.push(Ext.create('Ext.button.Button',{text:getLocalizedString('Save'), action:'save', tooltip:getLocalizedString('Save')}));
	
	if(isReset == true){
		btnArr.push(Ext.create('Ext.button.Button',{text:getLocalizedString('Reset'), action:'reset', tooltip:getLocalizedString('Reset')}));
	}
	return btnArr;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////

function getToolTipRendererFunction(){
	var func = function(value,metaData,record,colIndex,store,view){ 
    			metaData.tdAttr = 'data-qtip="' + value + '"';
    			return value;
    };
	return func;
}

//Returns the data for locale combo box
function getLocaleComboData(){
	var data = new Array();	
	var arr = getSupportedLocales();
	for(var i = 0; i< arr.length; i++){
		var entry = {"value" : arr[i].toUpperCase(), "name" : getLocalizedString(arr[i])};
		data[i] = entry;
	}
	return data;
}