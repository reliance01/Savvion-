Ext.QuickTips.init();
Ext.namespace("Bm.util");

/*
Ext.define('Ext.FormViewport', {
    extend:'Ext.Container',
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
        this.autoScroll = true;
        Ext.EventManager.onWindowResize(this.fireResize, this);
        this.renderTo = this.el;
    },

    fireResize : function(w, h){
        this.fireEvent('resize', this, w, h, w, h);
        this.doLayout();
		Ext.ComponentManager.each(function(key,value,length){
          	    if(Ext.getCmp(key).getXType && (Ext.getCmp(key).getXType() == 'panel' ||
				   Ext.getCmp(key).getXType() == 'treepanel' || Ext.getCmp(key).getXType() == 'tabpanel' ||
  				    Ext.getCmp(key).getXType() == 'gridpanel' )){
          	          Ext.getCmp(key).doLayout();
          	    }
          	 },this);

    }
});
*/
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
/*
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
            this.filterPanel = Ext.create('Ext.Panel',{
                region:'north',
                contentEl: this.filterDivId,
                autoScroll:this.filterDivScroll,
                bodyBorder:false,
                margin:'2 1 2 1'
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

            this.resultPanel = Ext.create('Ext.Panel',{
                region:'center',
                contentEl: this.resultDivId,
                autoScroll:true,
                border: true,
                //margin: resultPanelMargins,
                bodyStyle:'padding:3px',
                title: this.title
            });



        this.centerPanelItems.push(this.resultPanel);

        if(document.getElementById(this.cmdDivId) != null) {
            this.cmdPanel = Ext.create('Ext.Panel',{
                region:'south',
                contentEl: this.cmdDivId,
                border: false,
                margin:'0 1 1 1'
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

            this.viewportItems.push(new Ext.Component(northDivOptions));}

        this.viewportItems.push(Ext.create('Ext.Panel',{
                                region:'center',
                                border:false,
                                layout: 'border',
                                items: this.centerPanelItems
                            }));

    this.viewportItems.push(Ext.create('Ext.Component',{
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

	//FIXME: doLayout
	//To fix the layout of sub-menu panel which was not proper across a large number of pages in IE7
	if (typeof menuTabs != 'undefined' && menuTabs != null && Ext.isIE7) {
	    setHeightForMenuTab();
		menuTabs.doLayout();
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
*/
///////////////////////////////////////////////////////////////
//    Temporary Viewport
///////////////////////////////////////////////////////////////
Ext.define('Bm.FormViewport', {
    extend:'Ext.Viewport',
    initComponent : function() {
        Bm.FormViewport.superclass.initComponent.call(this);
        document.getElementsByTagName('html')[0].className += ' x-viewport';
        this.el = Ext.get(document.forms[0]);
        this.el.setHeight = Ext.emptyFn;
        this.el.setWidth = Ext.emptyFn;
        this.el.setSize = Ext.emptyFn;
        this.el.dom.scroll = 'no';
        this.el.applyStyles({width: '100%', height: '100%'});
        this.allowDomMove = false;
        //this.autoScroll = true;
        this.renderTo = this.el;
    }
});

Bm.util.BmViewport = function(title, options)
{
		this.hasForm = true;
		this.minWidth = Bm.Constants.MinIpadWidth;
        this.resultComponentXtype = 'panel';
        this.isFilterComponentRequired = false;
        this.centerContainerLayout = 'anchor';
        this.isCenterContainerScrollable = true;
		Ext.apply(this, options);

		var northDivId = "northDiv";
		var filterDivId = "filterDiv";
		var resultDivId = "resultDiv";
		var cmdDivId = "cmdDiv";
        var southDivId = "southDiv";

		var viewportClass = 'Bm.FormViewport';

        var northContainer = {
            region: 'north',
            xtype: 'container',
            id: 'northContainer',
            contentEl: northDivId,
            listeners: {
                resize:function() {
                    var northCmp = Ext.getCmp('mainTab');
                    if (!Ext.isEmpty(northCmp)) {
                        northCmp.setWidth(getCenterContainer().getWidth());
                        northCmp.doLayout();
						//Resize command links menu if it is visible.
						var dropdownBtn = Ext.getCmp('dropdownBtn');
						if(!Ext.isEmpty(dropdownBtn) && dropdownBtn.hasVisibleMenu()) {
							dropdownBtn.showMenu();
						}
                    }
                }
            }
        };

		var filterComponent = {
			xtype: 'container',
			id: 'filterContainer',
			layout: 'fit',
			minWidth: this.minWidth
		};
		var resultComponent = {
			xtype: this.resultComponentXtype,
			id: 'resultPanel',
            border: false,
			title: title,
			layout: 'fit',
			minWidth: this.minWidth
		};

        var centerContainer = {
            region: 'center',
            xtype: 'panel',
            id: 'centerContainer',
            autoScroll: this.isCenterContainerScrollable,
            layout:this.centerContainerLayout,
            defaults: {
                padding: '-1 1 1 1'
            }
        };

		var cmdComponent = {
			xtype: 'container',
			id: 'cmdContainer',
			layout:'fit'
		};

		var southComponent = {
			xtype: 'container',
			id: 'southDivContainer',
			layout:'fit'
		};

		var southContainer = {
			region: 'south',
			xtype: 'container',
			id: 'southContainer',
			layoutConfig: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [cmdComponent, southComponent]
		};

		if(document.getElementById(filterDivId) != null) {
			filterComponent.contentEl = filterDivId;
            this.isFilterComponentRequired = true;
		}
		if(document.getElementById(resultDivId) != null) {
			resultComponent.contentEl = resultDivId;
            if (!Ext.isEmpty(this.resultComponentAnchor)){
                resultComponent.anchor = this.resultComponentAnchor;
            }
		}
		if(document.getElementById(cmdDivId) != null) {
			cmdComponent.contentEl = cmdDivId;
		}
		if(document.getElementById(southDivId) != null) {
			southComponent.contentEl = southDivId;
		}

        var centerContainerItems = new Array();
        if(this.isFilterComponentRequired){
            centerContainerItems.push(filterComponent);
        }
        centerContainerItems.push(resultComponent);
        centerContainer.items = centerContainerItems;

		if(!this.hasForm) {
			viewportClass = 'Ext.Viewport';
		}

        var viewport = Ext.create(viewportClass,{
            layout: 'border',
            items:[
                northContainer,
                centerContainer,
                southContainer
            ]
        });


		//FIXME: doLayout
		//To fix the layout of sub-menu panel which was not proper across a large number of pages in IE7
		if (typeof menuTabs != 'undefined' && menuTabs != null && Ext.isIE7) {
			setHeightForMenuTab();
			menuTabs.doLayout();
		}
		return viewport;
}


function getResultPanel(){
    return Ext.getCmp('resultPanel');
}

function getFilterContainer(){
    return Ext.getCmp('filterContainer');
}

function getSouthContainer(){
    return Ext.getCmp('southContainer');
}

function getCenterContainer(){
    return Ext.getCmp('centerContainer');
}

function getCmdContainer(){
    return Ext.getCmp('cmdContainer');
}

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
                     if(elem.dom.getElementsByTagName('input').length <= 2)
                            status = true;
                    return status;

                }

                var northDiv = Ext.get('northDiv');
                if (northDiv && hasInputChild(northDiv)) {
                    var elem = northDiv.dom.getElementsByTagName('input')[0];
                    if(!Ext.isEmpty(elem)) {
                        var tokenName = elem.getAttribute('name');
                        var tokenValue = elem.getAttribute('value');
                        if(typeof tokenName != 'undefined' && typeof tokenValue != 'undefined') {
                            p = this.addToken(url,p,tokenName,tokenValue);
                        }
                    }
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
                         //return me.doFormUpload.call(me, o, p, url);
                         return me.callOverridden(arguments);
                     }
                    serForm = Ext.Element.serializeForm(form);
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
                return (me.transId = this.callOverridden(arguments));
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

//Override Ext.data.proxy.Server to fire handleAjaxException method, if the Ajax server call fails
Ext.override(Ext.data.proxy.Server, {
        constructor: function(config) {
            this.callOverridden([config]);
            this.addListener("exception",  function (proxy, response, operation) {
                if (!Ext.isEmpty(response.responseText)) {
                    handleAjaxException(response, operation);
                }
            });
        }
});