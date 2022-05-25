if(!sbm) sbm = {};
sbm.utils = sbm.utils || {};

var transactionAjaxObject = { 
	    start:function(type, args){ 
		// start   
	        // transaction event: 'args[0].tId' type: type has been fired.
		if(!YAHOO.lang.isUndefined(args[0].argument[1]) && YAHOO.lang.isBoolean(args[0].argument[1]) && (args[0].argument[1] == true))
			YAHOO.BM.WaitDialog.show();
	    }, 
	 
	    complete:function(type, args){ 
	        // complete
		// transaction event: 'args[0].tId' type: type has been fired.
		//alert("complete");
	    }, 
	 
	    success:function(type, args){ 
	       	// success
		// transaction event: 'args[0].tId' type: type has been fired.
                var element;
                if(typeof args[0].argument[0] == 'string')element = document.getElementById(args[0].argument[0]);     
                else element = args[0].argument[0];
                
                if(args[0].responseText != undefined){ 
                   // Transaction id: args[0].tId
                   // HTTP status: args[0].status 
                   // Status code message: args[0].statusText
                   // HTTP headers: args[0].getAllResponseHeader
                   // Server response: args[0].responseText
                   // Argument object: args[0].argument[1]
                   sbm.utils.setContent(element,args[0].responseText);                       
	         }		 
	    }, 
	 
	    failure:function(type, args){ 
	        // failure
		// transaction event: 'args[0].tId' type: type has been fired.
                if(args[0].responseText != undefined){ 
                    // Transaction id: " + args[0].tId 
                    // HTTP status: " + args[0].status
                    // Status code message: " + args[0].statusText                 
                   var data = YAHOO.lang.JSON.parse(args[0].responseText);
                   YAHOO.portal.container.alertDialog = new YAHOO.widget.SimpleDialog("alertPanel", { width: "400px",fixedcenter: true, visible: true,draggable: true,
                          modal:false, close: true,underlay: "shadow", xy:[200,200],constraintoviewport: true});
	           YAHOO.portal.container.alertDialog.setHeader("Exception Occured!");  
                   YAHOO.portal.container.alertDialog.setBody(YAHOO.lang.JSON.stringify(data["exception"]));  
                   YAHOO.portal.container.alertDialog.render(document.body);
                   YAHOO.portal.container.alertDialog.show();               
                   YAHOO.util.Dom.setStyle('alertPanel','display','block');                     
	        } 
		YAHOO.BM.WaitDialog.hide();
           }, 
	 
	   abort:function(type, args){ 
	        // abort
		// transaction event: 'args[0].tId' type: type has been fired.		
	   } 
}; 

sbm.utils.updateContent = function(div,method,url,showWaitingDialog) {
   this.showWaitingDialog = showWaitingDialog || true;	
   var callback = { 
	    timeout: 10000,
	    customevents:{ 
	        onStart:transactionAjaxObject.start, 
	        onComplete:transactionAjaxObject.complete, 
	        onSuccess:transactionAjaxObject.success, 
	        onFailure:transactionAjaxObject.failure, 
	        onAbort:transactionAjaxObject.abort 
	    },
	    argument:[div,this.showWaitingDialog] 
    }; 
    var request = YAHOO.util.Connect.asyncRequest(method, url, callback); 	
}

sbm.utils.handleError = function(req){
     var data = YAHOO.lang.JSON.parse(req.xhRequest.responseText);
     YAHOO.BM.WaitDialog.hide();   
     YAHOO.portal.container.alertDialog = new YAHOO.widget.ResizePanel("exceptionPanel", { width: "700px", constraintoviewport: true,modal:true,
	                     close: true,underlay: "shadow", visible: true,effect:[{effect:YAHOO.widget.ContainerEffect.FADE,duration:0.7}]} ); 
     // report exception JSON
     // exceptionLocalizedMessage
     // exceptionStackTrace
     // exceptionCause
     YAHOO.portal.container.alertDialog.render();
     YAHOO.portal.container.alertDialog.show();
     YAHOO.portal.container.alertDialog.center();  
     YAHOO.util.Dom.setStyle('exceptionPanel','display','block');
     if(!YAHOO.lang.isUndefined(data["exceptionLocalizedMessage"]))
       document.getElementById("exceptionLocalizedMessage").innerHTML = data["exceptionLocalizedMessage"];
     if(!YAHOO.lang.isUndefined(data["exceptionCause"]))
       document.getElementById("exceptionCause").innerHTML = data["exceptionCause"];
     if(!YAHOO.lang.isUndefined(data["exceptionStackTrace"])){
          var stackTrace =  data["exceptionStackTrace"];
	  //alert(stackTrace);
	  document.getElementById("exceptionStackTrace").innerHTML = ""+sbm.utils.escapeIllegalCharacters(stackTrace)+"";
     }
     var localizedMsgEffect = new Spry.Effect.Highlight('MsgDiv', {duration: 3000, from:'#cccccc', to:'#efefef', toggle: true});
     localizedMsgEffect.start();
     function startEffect(){localizedMsgEffect.start();};
     YAHOO.util.Event.addListener("MsgDiv", "click", startEffect,true);  
     
      
}

sbm.utils.setContent = function(div,content){
        if(typeof div == 'string')div = document.getElementById(div);
	if (!div) return;
        
	var scriptExpr = "<script[^>]*>(.|\s|\n|\r)*?</script>";
	div.innerHTML = content.replace(new RegExp(scriptExpr, "img"), "");
	var matches = content.match(new RegExp(scriptExpr, "img"));
	
        if (matches){
		var numMatches = matches.length;
		for (var i = 0; i < numMatches; i++)
		{
			var s = matches[i].replace(/<script[^>]*>[\s\r\n]*(<\!--)?|(-->)?[\s\r\n]*<\/script>/img, "");
			var oScript = document.createElement("script");
			oScript.text = s;
			div.appendChild(oScript);
		}
	}   
}

sbm.utils.escapeIllegalCharacters = function(str){
	str = str.replace("&","&amp;");
	str = str.replace("<","&lt;");
	str = str.replace(">","&gt;");
	str = str.replace('"',"&quote");
        str = str.replace("'","\''");
	str = str.replace("&nbsp;"," ");
	return str;
}
