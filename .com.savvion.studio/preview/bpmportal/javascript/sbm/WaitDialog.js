var BM = BM || {};
BM.Portal = BM.Portal || {};
BM.Portal.WaitDialog = function(requestContextPath,args){
	this.requestContextPath = requestContextPath;
        this.args = args || {};	
	this.init();
}

BM.Portal.WaitDialog.constructor = BM.Portal.WaitDialog;

BM.Portal.WaitDialog.prototype = {
	init: function(args){
		this.visible = false;
		this.width = this.args.width || 240;
		this.fixedcenter = this.args.fixedcenter || true;
		this.close = this.args.close || true;
		this.modal = this.args.modal || true;
		this.zindex = this.args.zindex || 9;
		this.visible = this.args.visible || true;
		this.headerMessage = this.args.headerMessage || "Loading, please wait..."; 
		this.waitDialog =  new YAHOO.widget.Panel("wait",  { width: "240px", fixedcenter: true, close: false, draggable: false, modal: true, zindex:9, visible: true});
	        this.waitDialog.setHeader(this.headerMessage);
		this.waitDialog.setBody("<img src=\""+this.requestContextPath+"/bpmportal/javascript/images/rel_interstitial_loading.gif\"/>");
		this.waitDialog.render(document.body);
		this.waitDialog.center();
	},
	
	show: function(){
	        if(!this.visible){
		   this.waitDialog.show();
		   this.visible = true;
		}
	},
	
	hide: function(){
		if(this.visible){
		    this.waitDialog.hide();
		    this.visible = false;
		}
	}
}
