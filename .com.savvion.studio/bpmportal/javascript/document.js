// JavaScript for JavaApplet document handling
// 
// Author: Zhili Xu
// This is move from HTML page which is formerly inside page

function upload() {

    var status = true;
    var containsApplet = false;
    var lastDocApplet;
    for ( var i = 0; i < document.applets.length; i++ ) {
	    if ( document.applets[i].getName() == 'DocTransfer' ) {
		    containsApplet = true;
		    lastDocApplet = document.applets[i];
		    status = lastDocApplet.updateDocuments();
		    if (status == false)
			       break;
	    }
    }
    for ( var i = 0; i < document.embeds.length; i++ ) {
             if ( document.embeds[i].getName() == 'DocTransfer' ) {
	        containsApplet = true;
		lastDocApplet = document.embeds[i];
		status = lastDocApplet.updateDocuments();
		if (status == false)
			break;
	     }
     }

    if ( containsApplet == true && status == true ) {

        lastDocApplet.clientGarbageCollection();
    }

    return (status == true)?true:false;
}