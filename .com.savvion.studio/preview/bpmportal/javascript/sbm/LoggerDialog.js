BM.Portal = BM.Portal || {};
BM.Portal.LoggerDialog = function(enableLogger,div){
	this.enableLogger = false;
        this.visible = false;
	if(!YAHOO.lang.isUndefined(enableLogger) && enableLogger == true)
	this.enableLogger = true;
	this.div = div || "loggerReader";	
	this.init();
}

BM.Portal.LoggerDialog.constructor = YAHOO.LoggerDialog;
BM.Portal.LoggerDialog.prototype = {
	init: function(){
		  
	      if(this.enableLogger){	
		  this.visible = false;
                  this.loggerContainer = document.body.appendChild(document.createElement(this.div));
		  //this.loggerContainer = document.getElementById('BmFooter').appendChild(document.createElement(this.div));
		  this.loggerReader = new YAHOO.widget.LogReader(this.loggerContainer,{draggable:true,
		          outputBuffer:3000,width:"600px",verboseOutput:false,xy:['400','400']});
		  this.loggerWriter = new YAHOO.widget.LogWriter("Forms Logger");
                  this.loggerReader.show();
		  //var pos = YAHOO.util.Dom.getXY('BmFooter');
		  //YAHOO.util.Dom.setXY(this.loggerContainer,pos);
                  
                  /*var handler = this;
		  var keyPressedHandler = function(type, args, obj){                 
                       if(handler.visible) handler.hide();
                       else if(!handler.visible)handler.show();                                               
                  }*/
                  
                  //document.documentElement.focus();
                  document.body.focus();
                  /*this.loggerCloseEvent = new YAHOO.util.KeyListener(document,{alt:true,keys:[38]},{fn:keyPressedHandler});
                  this.loggerCloseEvent.enable();               */
              }
	},
	       
	show: function(){
	        if(this.enableLogger){
		    //YAHOO.util.Dom.setStyle(this.div,'display','block');
                    this.loggerReader.show();
                    this.visible = true;
		}
	},
	
	hide: function(){
		if(this.enableLogger){
		    //YAHOO.util.Dom.setStyle(this.div,'display','none');
                    this.loggerReader.hide();
                    this.visible = false;
		}
	},
	
	log: function(message,level){
		if(this.enableLogger){
		     if(YAHOO.lang.isUndefined(level)) this.loggerWriter.log(message,"debug");
		     else this.loggerWriter.log(message,level);
		}
	},
	
	debug: function(message){
	       if(this.enableLogger)
		this.loggerWriter.log(message,"debug");
	},
	
	error: function(message){
	       if(this.enableLogger)	
		this.loggerWriter.log(message,"error");
	},
	
	info: function(message){
	       if(this.enableLogger)	
		this.loggerWriter.log(message,"info");
	}
}
