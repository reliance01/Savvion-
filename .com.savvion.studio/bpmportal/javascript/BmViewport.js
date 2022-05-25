Ext.FormViewport = Ext.extend(Ext.Container, {
    initComponent : function() {
        Ext.FormViewport.superclass.initComponent.call(this);
        document.getElementsByTagName('html')[0].className += ' x-viewport';
        this.el = Ext.get(document.forms[0]);
        this.el.setHeight = Ext.emptyFn;
        this.el.setWidth = Ext.emptyFn;
        this.el.setSize = Ext.emptyFn;
        this.el.dom.scroll = 'no';
        this.el.applyStyles({width: '100%', height: '100%'});
        this.allowDomMove = false;
        this.autoWidth = true;
        this.autoHeight = true;
        Ext.EventManager.onWindowResize(this.fireResize, this);
        this.renderTo = this.el;
    },

    fireResize : function(w, h){
        this.fireEvent('resize', this, w, h, w, h);
    }
});

/**
* It creates a view port area with the border layout having north, center and south panels.
* North panel contains the Menu Information along with the breadcrumb details
* Center Panel is again having a border layout with filter, result and cmd panels.
*     Filter Panel contains the search requirements and once the input to the search is submitted,
*     the results panel gets updated. The cmd panel contains the command buttons that should be operated 
*     on the elements in the results panel.
* South panel contains the copyright or other information.
* 
* Declare the div with the following ids:
*   North Div id --> northDiv (Optional)
*   Filter Panel id --> filterDiv (Optional)
*   Results Panel id --> resultDiv
*   Commands panel id --> cmdDiv (Optional)
*   South panel div --> southDiv
*
* This will fail if the mandatory panels do not exist in the DOM.
* 
*/
Ext.namespace("Bm.util");
Bm.util.BmViewport = function(title, options) 
{
    this.title = title;
    this.northDivId = "northDiv";
    this.southDivId = "southDiv";
    
    this.filterDivId = "filterDiv";
    this.resultDivId = "resultDiv";
    this.cmdDivId = "cmdDiv";
	this.hasForm = true;

    Ext.apply(this, options);   
    
    this.centerPanelItems = new Array();
    
    if(document.getElementById(this.filterDivId) != null) {
            this.filterPanel = new Ext.Panel({
                region:'north',
                contentEl: this.filterDivId,
                autoScroll:true,
                border:false,
                margins:'5 3 -2 3'
            });
            this.centerPanelItems.push(this.filterPanel);
        }
        
        var resultPanelMargins = '5 1 0 1';
        if(document.getElementById(this.filterDivId) == null && document.getElementById(this.cmdDivId) == null){
            resultPanelMargins = '5 1 1 1';
        } else if(document.getElementById(this.cmdDivId) == null) {
            resultPanelMargins = '5 1 1 1';
        } else if(document.getElementById(this.filterDivId) == null) {
            resultPanelMargins = '5 1 0 1';
        }
        
        this.resultPanel = new Ext.Panel({
            region:'center',
            contentEl: this.resultDivId,
            autoScroll:true,
            border: true,
            margins: resultPanelMargins,
            bodyStyle:'padding:3px',
            title: this.title
        });
        this.centerPanelItems.push(this.resultPanel);
        
        if(document.getElementById(this.cmdDivId) != null) {
            this.cmdPanel = new Ext.Panel({
                region:'south',
                contentEl: this.cmdDivId,
                autoScroll:true,
                border: false,
                margins:'0 1 1 1'
            });
            this.centerPanelItems.push(this.cmdPanel);
        }
        
        this.viewportItems = new Array();
        
        if(document.getElementById(this.northDivId) != null) {
        	this.viewportItems.push(new Ext.BoxComponent({ // raw
                                region:'north',
                                el: this.northDivId,
                                border: true
                            }));
        }
        
        this.viewportItems.push(new Ext.Panel({
                                region:'center',
                                border:false,
                                layout: 'border',
                                items: this.centerPanelItems
                            }));
	
	this.viewportItems.push(new Ext.BoxComponent({
                                    region:'south',
                                    el: this.southDivId
                            }));        	
    
        var optionsToPass = {
                    		layout: 'border',
                    		items: this.viewportItems
        		};
        if(this.hasForm) {
            this.viewport = new Ext.FormViewport(optionsToPass);
            this.viewport.doLayout();
        } else {
            this.viewport = new Ext.Viewport(optionsToPass);
        }
};

Bm.util.BmViewport.prototype = {
    getFilterPanel: function(){
        return this.filterPanel;
    },
    
    getResultPanel: function(){
        return this.resultPanel;
    },
    
    getCmdPanel: function() {
        return this.cmdPanel;
    }
};

// This can be used to create iframes in a panel.
Ext.ux.IFrameComponent = Ext.extend(Ext.BoxComponent, {
	onRender : function(ct, position){
	 this.el = ct.createChild({tag: 'iframe', id: 'framepanel'+this.id, frameBorder: 0, width:'100%', height:'100%', src: this.url});
 	}
});
