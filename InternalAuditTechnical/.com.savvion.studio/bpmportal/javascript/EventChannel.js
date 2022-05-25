// (C) Copyright 2001 Savvion, Inc.  All Rights Reserved.
// This program is an unpublished copyrighted work which is proprietary
//  to Savvion, Inc. and contains confidential information that is not
//  to be reproduced or disclosed to any other person or entity without
//  prior written consent from Savvion, Inc. in each and every instance.
//  -----------------------------------------------------------------
//  except where attribution is given below
//  -----------------------------------------------------------------

// ----------------------------------------------------------------
var agt = navigator.userAgent.toLowerCase();
  	  
var is_nav = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1)
  	                  && (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1)
  	                  && (agt.indexOf('webtv')==-1) && (agt.indexOf('hotjava')==-1));
  
var is_ie = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function setCheckBoxStyleForIE()
{
	var w_Elements = document.getElementsByTagName("input");
	for ( i=0; i < w_Elements.length; ++i)
	{
		if(isIE && (w_Elements.item(i).getAttribute("type") == "checkbox" || w_Elements.item(i).getAttribute("type") == "radio"))
			w_Elements.item(i).className = "ChkBoxNone";
	}
}

var contextData = new Array();

function validate(isAdd)
{
    var str = contextData.join();
    document.forms[0].ctxData.value = str;
    if (isAdd)
    {
        if ( getElementValue( "channelName" ) == '' ||
            getElementValue( "channelName" ) == null ) {
            alert("Please enter channel name");
            return false;
        }
    }
    if (matchPasswords())
    {
        return true;
    }
    else return false;
}

function setDefaultFltClass()
{
    document.forms[0].filterClass.value = "com.savvion.sbm.eventpublisher.filter.ANDFilter" ;
}

function setDefaultFltType()
{
    document.forms[0].filterKey.value = "BizLogic" ;
}


function getElementValue( name )
{
    return document.forms[0].elements[ name ].value;
}

function reset()
{
    document.forms[0].reset();
}

function matchPasswords()
{
    password = document.forms[0].channelPassword.value;
    passwordconf = document.forms[0].channelPasswordConf.value;
    if (password != passwordconf)
    {
        alert("Both channel user passwords should match");
        return false;
    }
    return true;
}

function checkValue(val)
{
    if (val != "BizLogic")
    {
        var elementRef = document.getElementById("filterValueAlt");
        elementRef.style.visibility = "visible";
        document.forms[0].filterValueAlt.focus();
        elementRef = document.getElementById("filterValue");
        elementRef.style.visibility = "hidden";
        document.forms[0].filterValueAlt.focus();

    }
    else
    {
        var elementRef = document.getElementById("filterValueAlt");
        elementRef.style.visibility = "hidden";
        elementRef = document.getElementById("filterValue");
        elementRef.style.visibility = "visible";
        document.forms[0].filterValue.focus();
    }
}

function onSelectAll(theForm, chkbx,controlName, tableName)
{
    if(chkbx.checked)
    {
        selectAllEntries(theForm,controlName,tableName);
        chkbx.checked = true;
    }
    else
    {
        deSelectAllEntries(theForm,controlName,tableName);
    }
    return true;
}

function selectAllEntries(theForm,controlName, tableName)
{
    for(var k=0;k<theForm.elements.length;k++)
    {

    if(theForm.elements[k].type=='checkbox'&&
        (theForm.elements[k].name==controlName|| controlName.indexOf(theForm.elements[k].name.substring(0,4)) != 1 ))
    {

      theForm.elements[k].checked = true;
      toggleRowSelection(theForm.elements[k],tableName,controlName, theForm);
    }
  }
  return true;
}

function deSelectAllEntries(theForm,controlName, tableName)
{
    for(var k=0;k<theForm.elements.length;k++)
    {
    if(theForm.elements[k].type=='checkbox' &&
        (theForm.elements[k].name==controlName||controlName.indexOf(theForm.elements[k].name.substring(0,4)) != 1 ))
    {
      theForm.elements[k].checked = false;
      toggleRowSelection(theForm.elements[k],tableName,controlName, theForm);
    }
    }
    return true;
}


function toggleRowSelection(chkbx, tableName, controlName, theForm)
{
  var table = document.getElementById(tableName);
  var id = parseInt(chkbx.id);
  var row = table.rows[id];
  if(row == null) return;
  if(chkbx.checked)
  {
    for(var j=0;j < row.cells.length;j++)
    {
      if(j == 0)
      {
        row.cells[j].className = "SegChkBoxSlct";
      }
      else 
      {
        row.cells[j].className = "SegFieldLftSlct";
      }
    }
  }
  else
  {
    for(var j=0;j < row.cells.length;j++)
    {
      if(j == 0)
      {
        if((id-1)%2 == 1)
        {
          row.cells[j].className = "SegChkBoxAlt";
        }
        else
        {
          row.cells[j].className = "SegChkBox";
        }
      }
        else
        {
        if((id-1)%2 == 1)
        {
          row.cells[j].className = "SegFieldLftAlt";
        }
        else
        {
          row.cells[j].className = "SegFieldLft";
        }
      }
    }
  }
  //if all the entries are selected select
  //select all option
  var bFlag = true;
  for(var k=0;k<theForm.elements.length;k++)
  {
    if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
    {
      if(!theForm.elements[k].checked)
      {
        bFlag = false;
      }
    } else if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name!='CTX.chkbxAll') {
      if(!theForm.elements[k].checked)
      {
        bFlag = false;
      }
    }
  }
  var allCheck = document.getElementById('CTX.chkbxAll');
  if(bFlag)
  {
    allCheck.checked = true;
  }
  else
  {
    allCheck.checked = false;
  }

}


// hide stakc trace on error page
function hideTrace(id, flag) {
    if (document.layers) { // Netscape before 6.0
        if(flag == "true") {
            document.layers[id].visibility = "hide"
        } else {
            document.layers[id].visibility = "show"
        }
    } else {
        if(navigator.appName.indexOf("Netscape")!=-1) { // Netscape 6.0 and higher
            // Obtain an element reference
            var elementRef = document.getElementById("trace");

            // Check the style object and visibility
            // property are available.
            if((elementRef.style) &&
               (elementRef.style.visibility != null)) {
                if(flag == "true") {
                    elementRef.style.visibility = "hidden";
                } else {
                    elementRef.style.visibility = "visible";
                }
            }
        } else { // others
            if(flag == "true") {
                document.all[id].style.display = "none"
            } else {
                document.all[id].style.display = "block"
            }
        }
    }
}
function isValid(name) {
        if(name == "" || name.length == 0 || name.charAt(0) == " "){
            return false;
        }
        return true;
}

function loadData() {
    document.forms[0].ctxConditionName.value = opener.document.forms[0].ctxConditionName.value;
    document.forms[0].ctxConditionValue.value = opener.document.forms[0].ctxConditionValue.value;
}

function submitContextData(isAdd) {
   // send data to parent
    var ctxName = getElementValue( "ctxConditionName" );
    var ctxValue = getElementValue( "ctxConditionValue" ) ;
    if (isAdd)
    {
        if ( ctxName == '' || ctxName == null )
        {
            alert("Please enter context condition name");
            return false;
        }
        if (ctxName.substring(0,4) != "CTX.")
        {
              alert("Context condition name should start with: CTX.");
              return false;
        }
        if (ctxName.indexOf(" ") != -1)
        {
             alert("Context condition name cannot contain spaces");
             return false;
        }
    }
    else
    {
        //edit
        if ( ctxName != opener.document.forms[0].ctxConditionName.value )
        {
            alert("Context condition name cannot be changed!");
            return false;
        }
    }

    if ( ctxValue == '' || ctxValue == null ) {
        alert("Please enter context condition value");
        return false;
    }

    opener.document.forms[0].ctxConditionName.value = ctxName;
    opener.document.forms[0].ctxConditionValue.value = ctxValue;
    // call to update the context data
    if (isAdd)
    {
        opener.addContextData();
    }
    else
    {
        opener.editContextData();
    }
    window.close();
}

function loadFilterData() {
    var selectedValues = opener.document.forms[0].filterValue.value;
    
    
}

function submitFilterData(isAdd) {
   // send data to parent
    var selectedValues = "";
    var firstSelect = true;
	   
    for (var i=0;i<document.forms[0].filterValues.length;i++) {
	if (document.forms[0].filterValues.options[i].selected==true) {
		if (firstSelect)
		{
			selectedValues = document.forms[0].filterValues.options[i].text;
			firstSelect = false;
		}
		else
		{
			selectedValues = selectedValues + "," + document.forms[0].filterValues.options[i].text;
		}
		
	}
	}
	//alert(selectedValues);
	
    opener.document.forms[0].filterValue.value = selectedValues;
    
    window.close();
}

function saveAndAddContextData(isAdd)
{
    // send data to parent
        var ctxName = getElementValue( "ctxConditionName" );
        var ctxValue = getElementValue( "ctxConditionValue" ) ;
        if (isAdd)
        {
            if ( ctxName == '' || ctxName == null )
            {
                alert("Please enter context condition name");
                return false;
            }
            if (ctxName.substring(0,4) != "CTX.")
            {
                  alert("Context condition name should start with: CTX.");
                  return false;
            }

        }
        else
        {
            //edit
            if ( ctxName != opener.document.forms[0].ctxConditionName.value )
            {
                alert("Context condition name cannot be changed!");
                return false;
            }
        }

        if ( ctxValue == '' || ctxValue == null ) {
            alert("Please enter context condition value");
            return false;
        }

        opener.document.forms[0].ctxConditionName.value = ctxName;
        opener.document.forms[0].ctxConditionValue.value = ctxValue;
        // call to update the context data
        if (isAdd)
        {
            opener.addContextData();
        }
        else
        {
            opener.editContextData();
        }
        if (!isAdd)
        {
            window.close();
        }

}


function addContextData() {
    var index = contextData.length;
    addRow("ctxConditions");
    contextData[index] =  new Array( document.forms[0].ctxConditionName.value,
            document.forms[0].ctxConditionValue.value) ;
    document.forms[0].ctxConditionName.value = "";
    document.forms[0].ctxConditionValue.value = "";
}

//context conditions script
function addRow(tableName)
{
    var table = document.getElementById(tableName);
    var newRow;
    var newCell;
    var newChild;
    var cssClass = "SegFieldLft";
    var inputFieldClass = "InptTxt";
    var strConditionName;
    var name = document.forms[0].ctxConditionName.value;
    var value = document.forms[0].ctxConditionValue.value;
    var index = contextData.length + 1;
    newRow = document.createElement('TR');
    //add checkbox TD
    newCell = document.createElement('TD');
    
  if (is_ie) {
  	newChild = document.createElement("<INPUT id=\'"+ index +"\' type=\'checkbox\' name=\'"+ name +"\' value=\'"+ name +"\' onClick=\"toggleRowSelection(this,\'"+tableName+"\',\''+name+'\',document.forms[0]);\">");
  } else if (is_nav) {
    newChild = document.createElement('INPUT');
    newChild.setAttribute('type','checkbox');
    newChild.setAttribute('value',name);
    newChild.setAttribute('id',index);    
    newChild.setAttribute('name',name);
    newChild.setAttribute('onClick','toggleRowSelection(this,\''+tableName+'\',\''+name+'\',document.forms[0])');         
  }    


    newChild.className = 'InptChkBox';
    newCell.appendChild(newChild);
    if(index%2 == 0) {
    	newCell.className = 'SegChkBoxAlt';
    } else {
    	newCell.className = 'SegChkBox';
    }
    newRow.appendChild(newCell);

    //add condition name TD
    newCell = document.createElement('TD');
    newCell.setAttribute('id',name);
    newCell.setAttribute('name',name);
    editLink = getEditLink(name,value );
    newCell.appendChild(editLink);
    //newCell.className = cssClass;
    if(index%2 == 0) {
    	newCell.className = 'SegFieldLftAlt';
    } else {
    	newCell.className = 'SegFieldLft';
    }    
    newRow.appendChild(newCell);

    //add condition value TD
    newCell = document.createElement('TD');
    newChild = document.createTextNode(value);
    //newChild.setAttribute('class','InptTxt');
    newCell.appendChild(newChild);
    //newCell.className = cssClass;
    if(index%2 == 0) {
    	newCell.className = 'SegFieldLftAlt';
    } else {
    	newCell.className = 'SegFieldLft';
    }      
    newRow.appendChild(newCell);

    table.tBodies[0].appendChild(newRow);
    setCheckBoxStyleForIE();
    return true;
}

function getEditLink(ctxName,ctxValue){
    editLink = document.createElement("a");
    linkText = document.createTextNode(ctxName);
    //linkText.setAttribute('class','InptTxt');
    str = "javascript:popeditContextData('"+ ctxName +"','" +ctxValue + "')" ;
    editLink.setAttribute("href", str);

    editLink.appendChild(linkText);
    return editLink;
}

function editContextData(){
    editRow("ctxConditions");
}

function editRow(tableName) {
    var table = document.getElementById(tableName);
    var row = "";
    var cell = "";
    var str = "";
    var updateValue = false;

    var name = document.getElementById(document.forms[0].ctxConditionName.value);
    //alert(name.firstChild.firstChild.name);
    //alert(name.firstChild.firstChild.value);

    //directly start from row 2 which is data row
    for(var i=1;i<table.tBodies[0].rows.length;i++)
    {
        var row = table.tBodies[0].rows[i];
        //directly start from column "name"
        for(var j=1;j < row.cells.length;j++)
        {
            var cell = row.cells[j];

            //alert(cell.innerHTML);
            str = cell.innerHTML;
            if (str.match(document.forms[0].ctxConditionName.value))
            {
                updateValue = true;
                contextData[i-1][1] =  document.forms[0].ctxConditionValue.value ;

                if (j==1) //for context condition name TD
                {
                    cell.innerHTML = "<a href=\"javascript:popeditContextData('"
                    + document.forms[0].ctxConditionName.value
                    + "','"
                    + document.forms[0].ctxConditionValue.value
                    + "')\">"
                    + document.forms[0].ctxConditionName.value
                    + "</a>" ;

                }
            }

            if (j==2) //for context condition value TD
            {
                //alert(updateValue);
                if (updateValue == true)
                {
                    cell.innerHTML = document.forms[0].ctxConditionValue.value;
                }
            }

            //alert("one cell in a row completed");
        }

        updateValue = false;
        //alert("one row completed");
    }

    document.forms[0].ctxConditionName.value = ""
    document.forms[0].ctxConditionValue.value = ""
    return true;

}

function popeditContextData(conditionName,conditionValue) {

    document.forms[0].ctxConditionName.value = conditionName;
    document.forms[0].ctxConditionValue.value = conditionValue;
    window.open("ContextDet.jsp","editcontext", "width=400,height=160")
}

function deleteRows(tableName)
{
    var table = document.getElementById(tableName);

    var tempArray = new Array();
    var rowlength = table.tBodies[0].rows.length;
    var arrayCounter = 0;
    for(var i=1;i<rowlength;)
    {
        var row = table.tBodies[0].rows[i];
        for(var j=0;j < row.cells.length;j++)
        {
            var cell = row.cells[j];
            for(var k=0;k<cell.childNodes.length;k++)
            {
                var child = cell.childNodes[k];
                if(child.name != undefined && child.type=='checkbox' )
                {
                    arrayCounter ++;
                    if(child.checked)
                    {
                        table.tBodies[0].removeChild(table.tBodies[0].rows[i]);
                        break;
                    } else { 
                    	tempArray[tempArray.length] =  contextData[arrayCounter -1];
                    	i++;
                    }
                 }
            } //for k
        }//for j
        rowlength = table.tBodies[0].rows.length;
    } //for i
    contextData = tempArray;
    
    
    for(var i=1;i<rowlength;i++)
    {
          var row = table.tBodies[0].rows[i];
	  for(var j=0;j < row.cells.length;j++)
	    {
	      if(j == 0)
	      {  var cell = row.cells[j];
	         for(var k=0;k<cell.childNodes.length;k++)
		 {
			var child = cell.childNodes[k];
	
			if(child.name != undefined && child.type=='checkbox' )
			{	
				child.id = i; 
	
			}
		 }
		
		if((i-1)%2 == 1)
		{
		  row.cells[j].className = "SegChkBoxAlt";
		}
		else
		{
		  row.cells[j].className = "SegChkBox";
		}
	      }
	      else 
	      {
		if((i-1)%2 == 1)
		{
		  row.cells[j].className = "SegFieldLftAlt";
		}
		else
		{
		  row.cells[j].className = "SegFieldLft";
		}
	      }
	    }    
    
    }
    return true;
}

function deleteContextData() {
    deleteRows("ctxConditions");
}

// If date field exists, this function validate date. Blank value is accepted.
function validate_DateField(date){
	if (date == null || date == "") {
		return true;
	} else {
	
		if (isNaN(date2Long(date))) {
			alert("Please enter valid date.");
			return false;
		}
		return true;
	}
     
	        
}	        
