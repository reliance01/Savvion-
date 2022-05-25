var bm;
var clickedButton = 'none';

if(!sbm) sbm = {};
sbm.utils = sbm.utils || {};
sbm.widgets = sbm.widgets || {};
/**
 *     This function execute the fValidate validation with some other resource validation.
 *     vType specifies the fValidation mode box, label or alert
 */
sbm.utils.beforeFormSubmit = function(vType){
	 //------------------------------------------------------------------------------------------------------//
	 //    fValidate validation for forms
	 //------------------------------------------------------------------------------------------------------//
	 
	 var errorMode = 4;
	 if((vType == undefined) || (vType == ''))
		 errorMode = 14;//default setting is box,alert
	 if(vType == 'box') errorMode = 4;
	 else if(vType == 'label') errorMode = 2;
	 else if(vType == 'box+label') errorMode = 10;
	 else if(vType == 'alert') errorMode = 0;
	 else if(vType == 'alert+label') errorMode = 11;
	 else if(vType == 'alert+box+label') errorMode = 28;
	 else if(vType == 'alert+box') errorMode = 14;
	 
	 // do not validate in save, assign or reassign
	 if(clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
	 {
		 // fValidate validation	
		 if(!sbm.pValidate(document.forms[0],false,false,false,true,errorMode))
		 {
			 //if(errorMode == 4)
			 //Effect.Grow("errors",{direction:'top-left',duration:3});
			 //Effect.Appear("errors",{duration:2});
			 return false;
		}
	 } 
	 
	 //-------------------------------------------------------------------------------------------------------//
	 //   validation for list dataslot
	 //   list dataslot related
	 //--------------------------------------------------------------------------------------------------------//
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		if(document.forms[ 0 ].elements[k].name != undefined){  
			if(document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_") != -1) {
				var index = document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_");
				var dsName = document.forms[ 0 ].elements[k].name.substring(9); //9 is length of _dyn_val_
				var dsCtrl = eval("document.forms[ 0 ]."+dsName);
				for(var j=0; j < dsCtrl.length;j++) {
					dsCtrl.options[j].selected = true;
				}
			}
		}
	  }
	 
	 
	 //------------------------------------------------------------------------------------------------------///
	 //   resource validation keep this separate from form validation
	 //   resource validation happens after form validation	//
	 ///------------------------------------------------------------------------------------------------------///
	 if(clickedButton.toLowerCase() != "bizsite_savetask" && clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
	 {
		 if(!sbm.utils.validateResources(document.forms[0]))              
			  return false;
	 }
	 
	 //-------------------------------------------------------------------------------------------------------///
	 //   upload document and xml dataslots...
	 //-------------------------------------------------------------------------------------------------------///
	 if(!eval(upload()))
	 {
	         alert("document upload has been failed...");
		 return false;
	 }
	 
	 //--------------------------------------------------------------------------------------------------------///
	 //  validation must end here...
	 //--------------------------------------------------------------------------------------------------------///
	 return true;
	
}
//----------------------------------------------------------------------------------------------------------//
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function goToCreateCollaboration()
{
	MM_goToURL('parent','/sbm/bpmportal/myhome/create_collaboration.jsp');
}

function goToViewCollaboration()
{
	MM_goToURL('parent','/sbm/bpmportal/myhome/view_collaboration.jsp');
}

function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

function goToEmail()
{
	MM_openBrWindow('/sbm/bpmportal/common/pop_task_det_email.jsp','emailuser','scrollbars=yes,resizable=yes,width=600,height=350');
}

function goToChat()
{
	MM_openBrWindow('/sbm/bpmportal/common/pop_task_det_chat.jsp','chatuser','scrollbars=yes,resizable=yes,width=600,height=350');
}


function goToNotes()
{
	MM_openBrWindow('/sbm/bpmportal/myhome/processnotes.jsp','Notes','scrollbars=yes,resizable=yes,width=900,height=600');
}


//---------------------------------------------------------------------------------------------------------//
//   resources validation function
//---------------------------------------------------------------------------------------------------------//
sbm.utils.validateResources = function(formElem)
{
	var apt_days = 0;
	 var apt_hrs = 0;
	 var apt_mins = 0;
	 var apt = 0;
	 var msg= "";
	 var invalid   = new Array();
	 var nofocus   = new Array();
	 var isValid = true;
         
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		 if(document.forms[ 0 ].elements[k].name != undefined){    	
			 if(document.forms[ 0 ].elements[k].name.indexOf("resources.") != -1 && document.forms[ 0 ].elements[ k ].name == name) {
				 if(document.forms[ 0 ].elements[ k ].value == '') {
					 fieldValid = false;
					 invalid[ invalid.length ] = name;
				 }
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "APT.days" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_days") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
				 apt_days=parseInt(document.forms[ 0 ].elements[ k ].value)*24;
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "APT.hours" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_hours") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
				 apt_hrs=parseInt(document.forms[ 0 ].elements[ k ].value);
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_minutes" || document.forms[ 0 ].elements[ k ].name == "APT.mins") && parseInt(document.forms[ 0 ].elements[ k ].value) >= 0) {
				 apt_mins=parseInt(document.forms[ 0 ].elements[ k ].value)/60;
			 }
			 apt=apt_days+apt_hrs+apt_mins;
		 }
	 }
	 
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		 if ((document.forms[ 0 ].elements[k] != undefined) && (document.forms[ 0 ].elements[k].name != undefined)){
			 if(document.forms[ 0 ].elements[k].name.indexOf("resources.ActualProcessingTime") != -1) {
				 document.forms[ 0 ].elements[k].value = apt;
				 if(apt <= 0) {
					 fieldValid = false;
					 invalid[ invalid.length ] = 'resources.ActualProcessingTime';
					 nofocus[ nofocus.length ] = 'resources.ActualProcessingTime';
					 isValid = false;
					 break;
				 }
			 }
		 }
	 }
	 
         if(!isValid && invalid.length > 0 ) 
	 {
		 msg += "The following fields are invalid:\n\n";
		 for( i = 0; i < invalid.length; i++ )
		 {
		        if(invalid[ i ] != null && invalid[ i ].indexOf("resources.") != -1 ) 
			{
				 if(invalid[ i ].substring(10) == "ActualProcessingTime") 
					 msg += "   - " + "Work Time" + "\n";
				 else
					 msg += "   - " + invalid[ i ].substring(10) + "\n";
			}
			else 
				 msg += "   - " + invalid[ i ] + "\n";			
		 }
		 msg += "\n\n";
         }
	 
	 if(!isValid)
	 {
	     alert(msg);
	     return false;
	 }
	 else
	     return true;	
}

sbm.utils.cancel = function(){
     if(document.forms[0].elements['bizsite_assigneeName'] != undefined) {
          self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.task");
     } else if(document.forms[0].elements['bizsite_pagetype'] != undefined) {
    	  var pagetype = document.forms[0].elements['bizsite_pagetype'];
    	  if(pagetype.value == "start") 
    	      self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.app.list");
    	  else 
    	      self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.task");    	
     } else {
     	  self.parent.location.replace("/sbm/bpmportal/myhome/bizsite.app.list");     
     }
     return false;
}       

sbm.utils.reset = function(){
     document.forms[0].reset();     
}

sbm.utils.applyEffects = function(uuid, effects) {
  var effect = null;
  try {
    var widgetEffects = new  WidgetEffects(effects);	
    if(widgetEffects.type == "fade")
        effect = new Spry.Effect.DoFade(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "slide"){
	effect = new Spry.Effect.DoSlide(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    }else if(effects.type == "blind"){
        effect = new Spry.Effect.DoBlind(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    }else if(effects.type == "grow")
        effect = new Spry.Effect.DoGrow(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "shake")
        effect = new Spry.Effect.DoShake(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    else if(effects.type == "highlight")
        effect = new Spry.Effect.Highlight(uuid, {duration: widgetEffects.duration, from: widgetEffects.from, to: widgetEffects.to,toggle:true});
    if(effect.start)effect.start();
  }catch(e){
    alert(e);	  
  }
}

sbm.renderBusinessObject = function(dataslotName,commandAction){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction="+commandAction;
    sbm.utils.updateContent(dataslotName,'GET',"/sbm/bpmportal/common/businessobjects/"+commandAction+".portal?"+params);    
}

sbm.editBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=editBusinessObject&index="+index;
    sbm.utils.updateContent(dataslotName,'GET',"/sbm/bpmportal/common/businessobjects/editBusinessObject.portal?"+ params);
}


sbm.deleteBusinessObject = function(dataslotName,index){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=deleteBusinessObject&index="+index;
    sbm.utils.updateContent(dataslotName,'GET',"/sbm/bpmportal/common/businessobjects/deleteBusinessObject.portal?"+ params);
}

sbm.addBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=addBusinessObject";
    sbm.utils.updateContent(dataslotName,'GET',"/sbm/bpmportal/common/businessobjects/addBusinessObject.portal?"+ params);
}

sbm.resetBusinessObject = function(dataslotName){
    $(dataslotName).innerHTML ="Loading...";
    var params = "dataslotName="+dataslotName+"&commandAction=resetBusinessObject";
    sbm.utils.updateContent(dataslotName,'GET',"/sbm/bpmportal/common/businessobjects/resetBusinessObject.portal?"+ params);
}


/**
 *   returns the jmaki widget object for a given widget id
 */ 
sbm.widgets.get = function(widgetName) {
        return FormWidgetHandler.getInstance().findWidgetByName(widgetName);
}
 
 
sbm.widgets.setValue =function(widgetName, value) {
      var widget = FormWidgetHandler.getInstance().findWidgetByName(widgetName);
      if(YAHOO.lang.isUndefined(widget)) {
	    sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!"); 
	    return;
      }
      try
      {
          widget.setValue(value);
      }catch(e){
	    sbm.widgets.showMsg('Error',e);  
      }
}


sbm.widgets.getValue =function(widgetName) {
      var widget = FormWidgetHandler.getInstance().findWidgetByName(widgetName);
      if(YAHOO.lang.isUndefined(widget)){ 
	      sbm.widgets.showMsg('Alert',"'"+widgetName + "' widget not found!"); 
	      return;
      }
      try
      {
          return  widget.getValue();
      }catch(e){
	    sbm.widgets.showMsg('Error',e);  
      }
}

sbm.widgets.show =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName+'_div','display','block');    
}

sbm.widgets.hide =function(widgetName) {
     YAHOO.util.Dom.setStyle(widgetName+'_div','display','none');
}

sbm.widgets.enable =function(widgetName) {
      var wElement = document.getElementById(widgetName+"_div");
      var childs = wElement.getElementsByTagName("input");
      for (var i = 0; i < childs.length; i++)                     
	   childs[i].disabled = false;
      var children = wElement.getElementsByTagName("select");
      for (var i = 0; i < children.length; i++)                     
	   children[i].disabled = false;
}

sbm.widgets.disable =function(widgetName) {
      var wElement = document.getElementById(widgetName+"_div");
      
      var childs = wElement.getElementsByTagName("input");
      
      if(!YAHOO.lang.isUndefined(childs)){
         for (var i = 0; i < childs.length; i++)                     
	   childs[i].disabled = true;
      }
      
      var children = wElement.getElementsByTagName("select");
      if(!YAHOO.lang.isUndefined(children)){
      for (var i = 0; i < children.length; i++)                     
	   children[i].disabled = true;
      }
}

sbm.widgets.addOption = function(widgetName,label,value) {
      var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
      var widget = formWidget.getWidget();
      if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
	     sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
	     return; 
      }
      if(YAHOO.lang.isUndefined(label)) {
	      sbm.widgets.showMsg('Alert','sbm.widgets.addOption failed, because label is undefined!'); 
      }
      
      if(YAHOO.lang.isUndefined(value)) value = label;
      var select = document.getElementById(widgetName);
      select.options[select.options.length] = new Option(label,value);
}

sbm.widgets.removeOptions = function(widgetName,indexes) {
     var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
     var widget = formWidget.getWidget();
     if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
	     sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
	     return; 
      }
      var select = document.getElementById(widgetName);
      if(indexes != null && indexes.length > 0) {
	      for(var i = indexes.length-1; i >= 0; i--){
                 select.remove(indexes[i]);
	      }
      }
}

sbm.widgets.removeAllOptions = function(widgetName) {
     var formWidget = FormWidgetHandler.getInstance().findFormWidgetByName(widgetName);
     var widget = formWidget.getWidget();
     if(!(formWidget.getWidgetType() == 'sbm.list' || formWidget.getWidgetType() == 'sbm.combobox')){
	     sbm.widgets.showMsg('Error',"Can not add option for '"+widgetName + "' widget, supported types are list and combobox."); 
	     return; 
     }
     sbm.util.removeAllOptions(widgetName);
}

sbm.widgets.showTooltip = function(widgetName,title) {
	var tooltip = new Ext.ToolTip({
                           target: widgetName,
                           title: title,
                           plain: true,
                           showDelay: 0,
                           hideDelay: 0.5,
                           trackMouse: true
        });
}

sbm.widgets.showMsg = function(title,format) {
	function msg(title,format) {
	     var msgCt = Ext.DomHelper.insertFirst(document.getElementById('resizablepanel'), {id:'msg-div'}, true);
             msgCt.alignTo(document, 't-t');
             var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
             //var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
	     //var m = Ext.DomHelper.append(msgCt, {}, true);
             //m.slideIn('t').pause(1).ghost("t", {remove:true});
	}
	Ext.fly(document.body).dom.value = Ext.MessageBox.WARNING;
        Ext.MessageBox.show({ title: title,
	    	                  msg: format,
                                  buttons: Ext.MessageBox.OK,
                                  animEl: document.body,
                                  fn: msg });
}


function WidgetEffects(options){
	this.type = (YAHOO.lang.isUndefined(options.type)) ? 'highlight': options.type;
	this.duration = (YAHOO.lang.isUndefined(options.duration)) ? 1000 : options.duration;
	this.toggle = (YAHOO.lang.isUndefined(options.toggle)) ? true : options.toggle;
	if(!YAHOO.lang.isUndefined(options.from)) this.from = options.from;
	if(!YAHOO.lang.isUndefined(options.to)) this.to = options.to;
	this.restoreColor = (YAHOO.lang.isUndefined(options.restoreColor)) ? '#FFCC33' : options.restoreColor;
	//this.from = (YAHOO.lang.isUndefined(options.from)) ? '#CCCCCC' : options.from;
	//this.to = (YAHOO.lang.isUndefined(options.to)) ? '#FFCC33' : options.to;
}

WidgetEffects.constructor = WidgetEffects;
WidgetEffects.prototype.toString = function() {
	var message = "type: "+this.type+", duration:"+this.duration+", toggle:"+this.toggle+
	      ", from:"+this.from+", to: "+this.to+", restoreColor:"+this.restoreColor;
	sbm.widgets.showMsg('Effects',message);
	
}

sbm.adaplet.renderChart = function(p_type ,pt_name, adapletName, chart_type, div_name, caption, Xlabel, Ylabel, width, height)
{
	
    processContextMap = {'_P_TYPE':p_type,'_PT_NAME':pt_name,'_WS_NAME':adapletName,'_CLASS_NAME':'com.savvion.sbm.adapters.db.DBAdapter'};
    inputDataslotsMap = {};
    
    chartMap = {'chartType':chart_type,'width':width,'height':height,'caption':caption,'subcaption':'Powered by CX Process Business Manager','xAxisName':Xlabel,'yAxisName':Ylabel,'numberPrefix':''};
    adapterDWR.renderChart(processContextMap, inputDataslotsMap, chartMap, function(map){
      if(map['exception'] != undefined)
        alert("Exception in adapter execution..."+map['exception']);
      else
      {
	 document.getElementById(div_name).innerHTML = map[div_name];
      }
    });
}

sbm.adaplet.renderTable = function (id,list) {
    document.getElementById(id).innerHTML = 'Please wait while loading...';  
    var fieldsDef = new Array();
	var columns = new Array();
	var data = new Array();
    
    var width = Ext.get(id).getWidth()
	for(var i=0;i < list[0].length;i++) {
           fieldsDef[fieldsDef.length] = {name:list[0][i], type:'string'};
	       columns[columns.length] = {header: list[0][i], sortable: true, dataIndex: list[0][i]};
	}
    
	for(var i=1; i < list.length;i++){
          var row = new Array();
          for(var j=0; j < list[1].length;j++) {
			    row[row.length] = (list[i][j] != undefined)  ? list[i][j] : ' ';	            	
	      }
	      data[data.length] = row;      
     }  


	// create the data store
    var store = new Ext.data.SimpleStore({
        fields: fieldsDef
    });
    store.loadData(data);

    // create the Grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns:columns,
		stripeRows: true,
	    border: true,
        autoScroll: true,
        containerScroll: true,
        autoHeight:true,
		autoWidth:true,
	    layout:'fit',
 	    title:'Adaplet Grid'
    });
    document.getElementById(id).innerHTML = '';  
    grid.render(id);
	grid.getView().fitColumns();
	grid.getSelectionModel().selectFirstRow(); 
}

/***
 *  Dummy meothds for backward compatibility
 */
function addField( name, type, isMandatory ){}
function addFieldLabel( name,type, isMandatory,label){}