var debug = false;

function setValue(name, value)
{
    var ctrl = getCtrl(name);
    ctrl.value = value;
}

function getValue(name)
{
    var ctrl = getCtrl(name);
    return ctrl.value;
}

function checkSpecialCharsForFilter(value) {    
    var spArray = new Array("<",">","\"","'",";","(",")","&","+","\\",",",":","|");
    var retVal = false;    

    for(var i=0;i < spArray.length;i++) {        
        if(value.indexOf(spArray[i]) != -1) {            
            retVal = true;            
            break;        
        }    
    }    
    return retVal;
}

function changeApplicationName()
{
    setValue('action', 'ChangeApplication');
}

function addMoreConditions()
{
    setValue('action', 'AddMoreConditions');
    setValue('RowsToBeAdded','5');
}

function deleteConditions()
{
    var list = document.query.elements['BulkIndexes'];

    var hasSelection = false;
    if (list!=undefined) {
        if(list.length != undefined) {
            for (var i = 0; i < list.length; i++)
            {
                if (list[i].checked == true)
                {
                    hasSelection = true;
                    break;
                }
            } // following else if is added for deleting row if its the only row present in table
        } else if(list.type == 'checkbox' && list.checked) {
            hasSelection = true;
        }
    }

    if (!hasSelection)
    {
        displayDeleteErrorMsg();
        return false;
    }
    setValue('action', 'DeleteConditions');
    prepareSubmit();
    submit();
}

function changeConditionType(index, conditionCtrl)
{
    setValue('action', 'ChangeConditionType');
    setValue('ConditionIndexChanged', index);
    setValue('NewConditionName', conditionCtrl.value);
    prepareSubmit();
    submit();
}

function changeOrderbySeq(index, conditionCtrl)
{
    setValue('action', 'ChangeSortSeq');
    setValue('OrderbyIndexChanged', index);
    setValue('NewOrderbyName', conditionCtrl.value);
    prepareSubmit();
    submit();
}

function cancel()
{
   setValue('action', 'cancel');
}

function preview(jsp)
{
    window.open(jsp,'filtercopy','scrollbars=yes,resizable=yes,width=436,height=150');
}

function validateField(aDataColDesc) {

    var temp = aDataColDesc.name.split('c');
    var index = temp[1];
    var dataValueName = "DataValue" + index;
    var dataColDesc = aDataColDesc.value;
    var nameTabCtrl = document.getElementById(dataValueName);
    var calImgCtrlName = "cal" + index;
    var calImgCtrl = document.getElementById(calImgCtrlName);
    var anchorName = "calanch" + index;
    var anchor = document.getElementById(anchorName);

    nameTabCtrl.disabled = false;
    nameTabCtrl.style.visibility = 'visible';

    //calImgCtrl.disabled = false;
    //calImgCtrl.style.visibility = 'visible';


    anchor.disabled = false;
    anchor.style.visibility = 'visible';
    if(dataColDesc != 'Custom'){
        nameTabCtrl.disabled = true;
        nameTabCtrl.style.visibility = 'hidden';
        //calImgCtrl.style.visibility = 'hidden';
        //calImgCtrl.disabled = true;
        anchor.style.visibility = 'hidden';
        anchor.disabled = true;
    }
}

function doDebug() {

    var component = getCtrl('comp');
    var action = getCtrl('action');
    var app = getCtrl('app');
    var col = getCtrl('col');
    var dir = getCtrl('dir');
    var rdir = getCtrl('rdir');
    alert(
        "comp: " + component.value + "\r\n" +
        "action : " + action.value + "\r\n" +
        "app : " + app.value + "\r\n" +
        "sort : " + col.value + "\r\n" +
        "dir : " + dir.value + "\r\n" +
        "rdir : " + rdir.value
     );
}

function selectApplication() {
    if (debug) {
        doDebug();
    }
    setValue('action', 'filterapp');
    setValue('app', getValue('SelectedApplication'));
    setValue('version', getValue('fltrTemplateVersions'));
    if (debug) {
        doDebug();
    }
}

function selectApplicationForQuery() {
    if (debug) {
        doDebug();
    }
    setValue('app', getValue('FilterApplicationName'));
    setValue('version', getValue('templateVersions'));
    if (debug) {
        doDebug();
    }
}

function newQuery() {
    if (eval(debug)) {
        doDebug();
    }
    setValue('newFilter', 'Add Filter');
    setValue('action', 'create');
    setValue('query', '');
    setValue('col', '');
    setValue('dir', '');
    if (eval(debug)) {
        doDebug();
    }
}

function editQuery(qname) {
    setValue('editqId', qname);
    if (eval(debug)) {
        doDebug();
    }
    setValue('action', 'show');
    setValue('query', qname);
    if (eval(debug)) {
        doDebug();
    }
    submit();
}

function checkAllUncheckAll(bulkController) {
    var table = document.getElementById('listTable');
    var chks = new Array(table.rows.length);
    var k = 0;
    for (var j = 0; j < document.bulk.elements.length; j++) {
        var e = document.bulk.elements[j];
        if (e.name == "QueryNames") {
            k = e.id;
            if((e.checked) && !bulkController.checked) {
                e.checked = document.bulk.elements[j - 1].checked;
            } else {
                e.checked = true;
            }
            dynamicToggleRowSelection(e,bulkController,'listTable','QueryNames',document.bulk,k,parseInt(k)+1,-1);
            k++;
        }
    }
}

function sort(column) {
    if (eval(debug)) {
        doDebug();
    }
    setValue('action', 'sort');
    //In case of admin filter SelectedApplication column is not present.
    if(getCtrl('SelectedApplication') != undefined && getCtrl('SelectedApplication') != null) {
        setValue('app', getValue('SelectedApplication'));
    }
    sortCol = getValue('col');
    if (sortCol == column) {
        var dir = getValue('dir');
        var rdir = getValue('rdir');
        if (rdir == '') {
            setValue('dir', 'desc');
        } else {
            setValue('dir', rdir);
        }
    } else {
        setValue('col', column);
        setValue('dir', 'desc');
    }
    if (eval(debug)) {
        doDebug();
    }
    submit();
}

function page(page) {
    if (debug) doDebug();
    setValue('action', 'sort');
    setValue('page', page);
    sortCol = getValue('col');
    dir = getValue('dir');
    setValue('col', sortCol);
    setValue('dir', dir);
    if (debug)doDebug();
    submit();
}

function refreshPage() {
//document.forms[0].action.value='bizsite.filter.do';
//alert(document.forms[0].action.value);
    submit();
}