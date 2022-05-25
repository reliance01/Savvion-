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
        this.doLayout();
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
	this.filterDivScroll = true;

	
	
    Ext.apply(this, options);   
    
    this.centerPanelItems = new Array();
    
    if(document.getElementById(this.filterDivId) != null) {
            this.filterPanel = new Ext.Panel({
                region:'north',
                contentEl: this.filterDivId,
                autoScroll:this.filterDivScroll,
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
				bodyStyle: (typeof this.resultBodyStyle != 'undefined') ? this.resultBodyStyle : 'padding:3px',
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
        
        if(window.northDivConfig)
	{
		this.viewportItems.push(window.northDivConfig);
	}
	else if(document.getElementById(this.northDivId) != null)
	{
        	var northDivOptions = { 
                                region:'north',
                                el: this.northDivId,
                                border: true
								  };
        
        	this.viewportItems.push(new Ext.BoxComponent(northDivOptions));
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

var getContextPath = parent.getContextPath;
var getAjaxLocalizedString = parent.getAjaxLocalizedString;

///////////////////////////////////////////////////////////////
//    Override for Ext.data.Connection
///////////////////////////////////////////////////////////////
Ext.override(Ext.data.Connection, {

 request : function(o){
            
            var me = this;
            if(me.fireEvent("beforerequest", me, o)){
                if (o.el) {
                    if(!Ext.isEmpty(o.indicatorText)){
                        me.indicatorText = '<div class="loading-indicator">'+o.indicatorText+"</div>";
                    }
                    if(me.indicatorText) {
                        Ext.getDom(o.el).innerHTML = me.indicatorText;
                    }
                    o.success = (Ext.isFunction(o.success) ? o.success : function(){}).createInterceptor(function(response) {
                        Ext.getDom(o.el).innerHTML = response.responseText;
                    });
                }

                var p = o.params,
                    url = o.url || me.url,
                    method,
                    cb = {success: me.handleResponse,
                          failure: me.handleFailure,
                          scope: me,
                          argument: {options: o},
                          timeout : Ext.num(o.timeout, me.timeout)
                    },
                    form,
                    serForm;


                if (Ext.isFunction(p)) {
                    p = p.call(o.scope||WINDOW, o);
                }
				
				function hasInputChild(elem){
				     var status = false;
					 if(elem.dom.getElementsByTagName('input').length == 1)
					        status = true;
					return status;
					 
				}
                
				var northDiv = Ext.get('northDiv');									
				if (northDiv && hasInputChild(northDiv)) {
                    var elem = northDiv.dom.getElementsByTagName('input')[0]				
					var tokenName = elem.getAttribute('name')
					var tokenValue = elem.getAttribute('value');
					if(typeof tokenName != 'undefined' && typeof tokenValue != 'undefined')
						p = this.addToken(url,p,tokenName,tokenValue);
					
					
				}else {				
				    this.readCookies();
					if(typeof this.tokenName != 'undefined' && typeof this.tokenValue != 'undefined')
						p = this.addToken(url,p,this.tokenName,this.tokenValue);											
				}
				
				p = Ext.urlEncode(me.extraParams, Ext.isObject(p) ? Ext.urlEncode(p) : p);

                if (Ext.isFunction(url)) {
                    url = url.call(o.scope || WINDOW, o);
                }
				
                if((form = Ext.getDom(o.form))){
                    url = url || form.action;
                     if(o.isUpload || (/multipart\/form-data/i.test(form.getAttribute("enctype")))) {
                         return me.doFormUpload.call(me, o, p, url);
                     }
                    serForm = Ext.lib.Ajax.serializeForm(form);
                    p = p ? (p + '&' + serForm) : serForm;
                }

                method = o.method || me.method || ((p || o.xmlData || o.jsonData) ? 'POST' : 'GET');

                if(method === 'GET' && (me.disableCaching && o.disableCaching !== false) || o.disableCaching === true){
                    var dcp = o.disableCachingParam || me.disableCachingParam;
                    url = Ext.urlAppend(url, dcp + '=' + (new Date().getTime()));
                }

                o.headers = Ext.applyIf(o.headers || {}, me.defaultHeaders || {});

                if(o.autoAbort === true || me.autoAbort) {
                    me.abort();
                }

                if((method == 'GET' || o.xmlData || o.jsonData) && p){
                    url = Ext.urlAppend(url, p);
                    p = '';
                }
                return (me.transId = Ext.lib.Ajax.request(method, url, cb, p, o));
            }else{
                return o.callback ? o.callback.apply(o.scope, [o,UNDEFINED,UNDEFINED]) : null;
            }
        },
		
		readCookies : function(){
			var cookies = {},
            c = document.cookie + ";",
            re = /\s?(.*?)=(.*?);/g,matches, name,value;
            while((matches = re.exec(c)) != null){
            name = matches[1];
            value = matches[2];
            
			if(name == 'mmnnuull-00'){			  
			   var tokens = value.split('=');
			   this.tokenName = decodeURIComponent(tokens[0]);
			   this.tokenValue = decodeURIComponent(tokens[1]);			   			   
			}
			
        }		
        return cookies;
    },
	
	addToken: function(url,p,tokenName,tokenValue){			 
		 if(typeof p == 'string') {
			  p = p +"&"+ encodeURIComponent(tokenName) + "=" + encodeURIComponent(tokenValue);
		 }else if(typeof p == 'object'){
		      p[tokenName] = tokenValue;
		 } else if(typeof p == 'undefined'){					   
		      if(url.indexOf('=') != -1) {
			    url = url + "&"+ encodeURIComponent(tokenName) + "=" + encodeURIComponent(tokenValue);
			  }else{
				p = new Object();
				p[tokenName] = tokenValue;
			  }
		 }
		 return p;	
	}

});
