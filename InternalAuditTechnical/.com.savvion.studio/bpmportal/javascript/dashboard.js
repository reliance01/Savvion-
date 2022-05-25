function SaveGlobalDsData(theForm, divId,dId,wId,dwId,viewmode) {

	var url = 'widgetupdate.portal';
	var ser = Form.serialize(theForm);

	var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=updateglobalds';
	params += '&'+ser;
	
	var elem = $(divId);
	elem.innerHTML = "Saving...";
	pwr.loadPage(url,params,divId,'post', Prototype.emptyFunction, Prototype.emptyFunction, true);
}

function ResetGlobalDsData(theForm, divId,dId,wId,dwId,viewmode) {

	var elem = $(divId);
	elem.innerHTML = "Reseting...";

	var url = 'widgetupdate.portal';
	var ser = Form.serialize(theForm);

	var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=resetglobalds';
	params += '&'+ser;
	
	pwr.loadPage(url,params,divId,'post', Prototype.emptyFunction, Prototype.emptyFunction, true);
}

function SaveInfopadData(theForm, divId,dId,wId,dwId,viewmode) {

	var url = 'widgetupdate.portal';
	var ser = Form.serialize(theForm);

	var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=updateinfopad';
	params += '&'+ser;
	
	var elem = $(divId);
	elem.innerHTML = "Saving...";
	pwr.loadPage(url,params,divId,'post');


}

function ResetInfopadData(theForm, divId,dId,wId,dwId,viewmode) {

	var elem = $(divId);
	elem.innerHTML = "Reseting...";

	var url = 'widgetupdate.portal';
	var ser = Form.serialize(theForm);

	var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=resetinfopad';
	params += '&'+ser;
	
	pwr.loadPage(url,params,divId,'post');
}

function RefreshData(theForm, divId, dId, wId, dwId,viewmode) {
	var elem = $(divId);
	elem.innerHTML = "Refreshing data...";

	var url = 'widgetupdate.portal';
	var ser = Form.serialize(theForm);

	var params = 'viewmode='+viewmode+'&did='+dId+'&wid='+wId+'&dwid='+dwId+'&action=refreshdata';
	params += '&'+ser;
	
	pwr.loadPage(url,params,divId,'post');
}