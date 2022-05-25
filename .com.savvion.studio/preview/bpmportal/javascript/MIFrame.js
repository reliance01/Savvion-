Ext.namespace("Bm.util");
Bm.util.MIFrame = function(title, url, options) 
{
    this.title = title;
    this.url = url;
    
    this.options = {};
    this.options.layout = 'fit';
    this.options.minimizable = false;
    this.options.maximizable = true;
    this.options.height = 550;
    this.options.width = 700;
    this.options.constrainHeader = true;
    this.options.collapsible = true;
    this.options.animCollapse = Ext.isIE;
    this.options.plain = false;
    this.options.modal = true;
    
    Ext.apply(this.options, options);
}

Bm.util.MIFrame.prototype = {
    show: function() {
        this.options.title = this.title;
        this.options.items = {
            xtype: 'iframepanel',
                    defaultSrc : this.url,
                    loadMask :true 
        };
        
        this.win = new Ext.Window(this.options);
            this.win.show();
    }
}

/* This can be used to create iframes in a panel.
Ext.ux.IFrameComponent = Ext.extend('Ext.Component', {
    onRender : function(ct, position){
     this.el = ct.createChild({tag: 'iframe', id: 'framepanel'+this.id, frameBorder: 0, width:'100%', height:'100%', src: this.url});
    }
});   */

// This can be used to create iframes in Application and Task details page.
Bm.util.Iframe = function(config) {
    var iframe = Ext.extend(Ext.BoxComponent, {
        onRender : function(targetElement, position){
           config = setIframeDefaultValues(config);
           this.el = targetElement.createChild(config);
        },
        afterRender : function(thisFrame) {
            thisFrame.focus(); 
        }
    });
    new iframe().render(Ext.getDom(config.targetId));    
}

function setIframeDefaultValues(config){
  config.tag = 'iframe';
  if(Ext.isEmpty(config.width)){
    config.width ='100%';
  }
  if(Ext.isEmpty(config.height)){
    config.height ='100%';
  }
  if(Ext.isEmpty(config.frameBorder)){
    config.frameBorder = 0;
  }
  if(Ext.isEmpty(config.scrolling)){
    config.scrolling = 'auto';
  }
  if(Ext.isEmpty(config.align)){
    config.align = 'center';
  }
  if(Ext.isEmpty(config.style)){
    config.style = 'border:0px none;';
  }
  return config;
}


//function loadIframe(id,src){
function loadIframe(config){
    
    var mif = new Ext.ux.ManagedIframe.Component({
                    	id : config.iframeId,
                    	frameName : config.iframeId + "-iframe-name",                      
                        renderTo : config.renderTo,
                        width: config.width,
                        height: config.height,
                        align: config.align,
                        autoMask: true
                    }
                );

    /*var mif = new Ext.ux.ManagedIFrame.Element(id);
    if(Ext.LoadMask){
        mif.mask(Ext.LoadMask.prototype.msg,'ext-el-mask-msg');
    } else {
        mif.mask("Loading...",'ext-el-mask-msg');
    }*/

    //mif.setSrc(src);
    mif.setSrc(config.iframeSrc);
    
    //Commented the line below and Added ManagedIframe to avoid displaying blank page in FF
    //document.getElementById(id).src = src;
    
    //For FireFox
    var iframeObj = Ext.getDom(config.iframeId);
    if(Ext.util.Format.lowercase(iframeObj.tagName) != 'iframe'){
    	iframeObj = document.getElementsByName(config.iframeId + "-iframe-name")[0];
    }
    if(iframeObj && DISABLE_ENTER_KEY){
        if(!Ext.isIE){
            //iframeObj.observe('load', setIFrameKeyPressHandler.createDelegate(this,[id]));
            iframeObj.observe('load', setIFrameKeyPressHandler(config.iframeId));
        } else {               
            //Event.observe(iframeObj,'load', setIFrameKeyPressHandler.createDelegate(this,[id]))
        	//This was causing javascript error 'Not Implemented' in IE8 in the status analysis report. 
        	//It happens as IFrame tag does not have onkeypress event.
            //Event.observe(iframeObj, 'load', setIFrameKeyPressHandler(config.iframeId));
        }
    }
}

function loadIframeSrc(id,src){

    var iframeObj = Ext.getDom(id);
    /*if(Ext.LoadMask){
        mif.mask(Ext.LoadMask.prototype.msg,'ext-el-mask-msg');
    } else {
        mif.mask("Loading...",'ext-el-mask-msg');
    } */

    
    iframeObj.src = src;    
    //Commented the line below and Added ManagedIframe to avoid displaying blank page in FF
    //document.getElementById(id).src = src;
    
    //For FireFox
    if(iframeObj && DISABLE_ENTER_KEY){
        if(!Ext.isIE){
            iframeObj.observe('load', setIFrameKeyPressHandler(id));
        } else {
            //FIXME: MIFrame.OnKeyPress
            //This was causing javascript error 'Not Implemented' in IE8 in the status analysis report. It happens as IFrame tag does not have onkeypress event.
            //Event.observe('load', setIFrameKeyPressHandler(id));
        }
    }
}

function createIFrame(config){
    /*new Bm.util.Iframe({
        id          : config.iframeId,
        src         : Ext.isIE ? config.iframeSrc: Ext.SSL_SECURE_URL,
        targetId    : config.renderTo
    });
    if(!Ext.isIE){
        Ext.EventManager.addListener(config.bodyId, 
                                    'load', 
                                    loadIframe(config.iframeId, config.iframeSrc));
    }else if(DISABLE_ENTER_KEY){               
        setIFrameKeyPressHandler(config.iframeId);        
    }*/
    setIframeDefaultValues(config);
    loadIframe(config);
}

function setIFrameKeyPressHandler(iframeId){   
    var iframeObj = Ext.getDom(iframeId);
    if(Ext.util.Format.lowercase(iframeObj.tagName) != 'iframe'){
    	var iframeArray = document.getElementsByName(iframeId + "-iframe-name"); 
    	if(!Ext.isEmpty(iframeArray)){
    		iframeObj = iframeArray[0];
    	}
    }
    if(iframeObj){
        if(!Ext.isIE){    
            iframeObj.onkeypress = disableSubmit(this,iframeId); 
        }else{
            iframeObj.onkeypress = disableSubmit(this,iframeId); 
        }        
    }
}

/*
    In the original unmask method, the following code 
    causes textfield to loose focus after unmask:
    a.removeClass(["x-masked-relative","x-masked"])
    This code can be removed since the component representing it -
    "_mask" itself will be destroyed.
    The below override is exactly the same as their parent method 
    but without the above line.
*/
if(!Ext.isIE && Ext.ux.ManagedIFrame) {
    Ext.override(Ext.ux.ManagedIFrame.Element, {
        unmask : function() {
            if(this._mask){
                if(this._maskMsg){
                    this._maskMsg.remove();
                    delete this._maskMsg;
                }
                this._mask.remove();
                delete this._mask;
            }
        }
    });
}