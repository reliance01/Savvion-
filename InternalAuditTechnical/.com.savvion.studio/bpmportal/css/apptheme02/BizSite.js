// (C) Copyright 1998 TDI, Inc.  All Rights Reserved.
// This program is an unpublished copyrighted work which is proprietary
//  to TDI, Inc. and contains confidential information that is not
//  to be reproduced or disclosed to any other person or entity without
//  prior written consent from TDI, Inc. in each and every instance.
//  -----------------------------------------------------------------
var isNav, isIE
if ( parseInt( navigator.appVersion ) >= 4 )
{
    if ( navigator.appName == "Netscape" )
    {
        isNav = true;
    }
    else
    {
        isIE = true;
    }
}

function handleResize()
{
//    top.location.reload();
//    self.location.reload();
    return false;
}

if ( isNav )
{
    top.captureEvents(Event.RESIZE)
    top.onresize = handleResize
    self.captureEvents(Event.RESIZE)
    self.onresize = handleResize
}

function imgN(dir,  name , nr) {
      document.images[name].src= dir + '/' + name + nr + '.jpg';
}

// My Tooltips
function tooltip(message, status)
{
  //if(document.forma.ett.checked) {
  if(status != 0) {
    if(tooltip != null)
      tooltip.focus();
    else {
      var tooltip = open('', 'Tooltip', 'width='+ (message.length * 8) + ',height=10');
      tooltip.document.open();
      tooltip.document.writeln('<html><body text=black bgcolor=#FFFFE1>');
      tooltip.document.writeln('<CENTER><FONT size=2><B>' + message + '</B></FONT></CENTER>');
      tooltip.document.writeln('</body></html>');
      tooltip.document.close();
    }
  } else {
    var tooltip = open('', 'Tooltip', 'width=10,height=10');
    tooltip.close();
  }
  //}
}

if (!document.layers&&!document.all) event="test";

function showtip2(current,e,text){
  if (document.all && document.readyState=="complete") {
    document.all.tooltip2.innerHTML='<marquee style="border:1px solid black">'+text+'</marquee>';
    document.all.tooltip2.style.pixelLeft=event.clientX+document.body.scrollLeft+10;
    document.all.tooltip2.style.pixelTop=event.clientY+document.body.scrollTop+10;
    document.all.tooltip2.style.visibility="visible";
  } else if (document.layers) {
    document.tooltip2.document.nstip.document.write('<b>'+text+'</b>');
    document.tooltip2.document.nstip.document.close();
    document.tooltip2.document.nstip.left=0;
    currentscroll=setInterval("scrolltip()",100);
    document.tooltip2.left=e.pageX+10;
    document.tooltip2.top=e.pageY+10;
    document.tooltip2.visibility="show";
  }
}
function hidetip2(){
  if (document.all)
    document.all.tooltip2.style.visibility="hidden"
      else if (document.layers){
	clearInterval(currentscroll)
	  document.tooltip2.visibility="hidden"
	  }
}

function scrolltip(){
  if (document.tooltip2.document.nstip.left>=-document.tooltip2.document.nstip.document.width)
    document.tooltip2.document.nstip.left-=5;
  else
    document.tooltip2.document.nstip.left=150;
}

//<div id="tooltip2" style="position:absolute;visibility:hidden;clip:rect(0 150 50 0);width:150px;background-color:lightyellow">
//  <layer name="nstip" width=1000px bgColor="lightyellow"></layer>
//</div>
