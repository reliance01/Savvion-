function disableEsc(){if((document.all)&&(event.keyCode==27)){event.returnValue=false;}}
function disableFields(fields){for(var i=0;i<fields.length;i++){fields[i].disabled=true;}}
function enableFields(fields){for(var i=0;i<fields.length;i++){fields[i].disabled=false;}}
function getElementByClassName(obj,className,nodeName){if(nodeName==null){nodeName="div";}
var list=obj.getElementsByTagName(nodeName);for(var i=0;i<list.length;i++){if(list[i].className&&list[i].className.match(className)){return list[i];}}
return null;}
function blink(){if(document.all){var blinkArray=document.all.tags("blink");for(var i=0;i<blinkArray.length;i++){blinkArray[i].style.visibility=blinkArray[i].style.visibility==""?"hidden":"";}}}
function changeOpacity(object,opacity){opacity=(opacity>=100)?99.999:opacity;opacity=(opacity<0)?0:opacity;object.style.opacity=(opacity/100);object.style.MozOpacity=(opacity/100);object.style.KhtmlOpacity=(opacity/100);object.style.filter="alpha(opacity="+opacity+")";}
function check(form,name,checked){for(var i=0;i<form.elements.length;i++){var e=form.elements[i];if((e.name==name)&&(e.type=="checkbox")){e.checked=checked;}}}
function checkAll(form,name,allBox){if(isArray(name)){for(var i=0;i<form.elements.length;i++){var e=form.elements[i];if(e.type=="checkbox"){for(var j=0;j<name.length;j++){if(e.name==name[j]){e.checked=allBox.checked;}}}}}
else{for(var i=0;i<form.elements.length;i++){var e=form.elements[i];if((e.name==name)&&(e.type=="checkbox")){e.checked=allBox.checked;}}}}
var PortletHeaderBar={fadeIn:function(id){var bar=document.getElementById(id);if(bar==null)
return;if(bar.startOut){clearTimeout(bar.timerOut);bar.timerOut=0;}
bar.startOut=false;bar.startIn=true;bar.opac+=20;
bar.iconBar.style.display="block";if(bar.opac<100){bar.timerIn=setTimeout("PortletHeaderBar.fadeIn(\""+id+"\")",50);}
else{bar.timerIn=0;bar.startIn=false;}},fadeOut:function(id){var bar=document.getElementById(id);if(bar==null)
return;if(bar.startIn){clearTimeout(bar.timerIn);bar.timerIn=0;}
bar.startIn=false;bar.startOut=true;bar.opac-=20;
bar.iconBar.style.display="block";if(bar.opac>0){bar.timerOut=setTimeout("PortletHeaderBar.fadeOut(\""+id+"\")",50);}
else{bar.iconBar.style.display="none";bar.timerOut=0;bar.startOut=false;}},init:function(bar){if(!bar.iconBar){bar.iconBar=getElementByClassName(bar,"portlet-small-icon-bar");}
if(!bar.iconList){bar.iconList=bar.iconBar.getElementsByTagName("img");}},hide:function(id){var bar=document.getElementById(id);if(bar.timerIn&&!bar.startIn){clearTimeout(bar.timerIn);bar.timerIn=0;}
if(!bar.startOut&&bar.opac>0){if(bar.timerOut){clearTimeout(bar.timerOut);bar.timerOut=0;}
this.init(bar);bar.timerOut=setTimeout("PortletHeaderBar.fadeOut(\""+id+"\")",150);}},show:function(id){var bar=document.getElementById(id);if(bar.timerOut&&!bar.startOut){clearTimeout(bar.timerOut);bar.timerOut=0;}
if(!bar.startIn&&(!bar.opac||bar.opac<100)){if(!bar.opac){bar.opac=0;}
if(bar.timerIn){clearTimeout(bar.timerIn);bar.timerIn=0;}
this.init(bar);bar.timerIn=setTimeout("PortletHeaderBar.fadeIn(\""+id+"\")",150);}}}