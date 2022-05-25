/**
 *   Defines the properties of widgets used in Form Editor
 *
 *   widgetName   the name of the widget 
 *   widgetType   type of the widget 
 *   editable      
 *   source
 *   target 
 */

function FormWidget(widgetName, widgetType, editable, source, target){                
	// initialize the property
        this.widgetName = widgetName;
	    this.widgetType = widgetType;
        this.editable = editable;	
        this.source = source;
        this.target = target;
        this.sourceType = source['type'];
        this.targetType = target['type'];
        // get source information
        
	if(this.sourceType == 'DATASLOT') {            
            if(!YAHOO.lang.isUndefined(source['dataSlotName']))
                 this.sourceDataSlotName = source['dataSlotName'];
            if(!YAHOO.lang.isUndefined(source['dataSlotType']))
                 this.sourceDataSlotType = source['dataSlotType']; 
        }
        
        if(this.sourceType == 'SERVICE') {     
            if(!YAHOO.lang.isUndefined(source['serviceUrl']))
                 this.sourceServiceUrl = source['serviceUrl'];  
        }
        // get target information
        if(this.targetType == 'DATASLOT') {    
            if(!YAHOO.lang.isUndefined(target['dataSlotName']))
                 this.targetDataSlotName = target['dataSlotName'];
            if(!YAHOO.lang.isUndefined(target['dataSlotType']))
                 this.targetDataSlotType = target['dataSlotType']; 
        }
}

FormWidget.constructor = FormWidget;
FormWidget.prototype = {	
	getWidgetName : function(){
	    return this.widgetName;
	},
	
	getWidgetType : function(){
	    return this.widgetType;
	},
	
	getSource : function(){
            return this.source;
        },
        
        getSourceType : function(){
            return this.sourceType;
        },
        
        getTarget : function(){
            return this.target;
        },
        
        getTargetType : function(){
            return this.targetType;
        },

	getSourceDataSlotName : function(){
	    return this.sourceDataSlotName;
	},
	
	getSourceDataSlotType : function(){
	    return this.sourceDataSlotType;
	},  

        getSourceServiceUrl : function(){
	    return this.sourceServiceUrl;
	},
        
        getTargetDataSlotName : function(){
	    return this.targetDataSlotName;
	},
	
	getTargetDataSlotType : function(){
	    return this.targetDataSlotType;
	},              
        
        isEditable : function(){
	    return this.editable;
	}        
}
 
FormWidget.prototype.setWidget = function(widget){
       this.widget = widget;    /* holds jmaki object */
}  

FormWidget.prototype.getWidget = function(){
       return this.widget;    
} 



        
    
