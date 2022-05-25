var msg = "workstepMapValidation";

function mapRecord(type, srcname, tgtname) {
	this.type = type;
	this.srcname = srcname;
	this.tgtname = tgtname;
}

function srcRecord(type, name) {
	this.type = type;
	this.name = name;
}

function tgtRecord(type,name) {
	this.type = type;
	this.name = name;
}

function removeTable(tableName) {
	var table = document.getElementById(tableName);
   	var rows = table.rows;

   	//to refresh delete all rows from the table
   	if (rows.length > 1) {
   		for (var i=rows.length-1;i > 0;i--) {
			table.deleteRow(i);
		}
   	}
}

function fillSourceTable(value) {
	removeTable('srcTable');
		
	var count = 0;
	for (var i=0;i < srcWsArray.length;i++) {
		var srcRec = srcWsArray[i];
		var found = false;
		for (var j=0;j<mapWsArray.length;j++) {
			var mapRec = mapWsArray[j];
			if (mapRec.srcname == srcRec.name) {
				found = true;
				break;
			}
		}
		if (found == false) {	
			if (value == srcRec.type) {
				addRow('srcTable',i,count,'srcRadioButton',getLocalizedString(srcRec.type),srcRec.name);
				count++;
			} else if (value == "-1") {
				addRow('srcTable',i,count,'srcRadioButton',getLocalizedString(srcRec.type),srcRec.name);
				count++;
			} 
		}
	}
	return true;
}

function fillTargetTable(value) {
	removeTable('tgtTable');
	var count = 0;

	for (var i=0;i < tgtWsArray.length;i++) {
		var tgtRec = tgtWsArray[i];
		var found = false;

		for (var j=0;j<mapWsArray.length;j++) {
			var mapRec = mapWsArray[j];
			if (mapRec.tgtname == tgtRec.name) {
				found = true;
				break;
			}
		}
		if (found == false) {
			if (value == tgtRec.type) {
				addRow('tgtTable',i,count,'tgtRadioButton',getLocalizedString(tgtRec.type),tgtRec.name);
				count++;
			} else if (value == "-1") {
				addRow('tgtTable',i,count,'tgtRadioButton',getLocalizedString(tgtRec.type),tgtRec.name);
				count++;
			}
		}
	}
	return true;
}

function refreshTable(tableName) {
   var table = document.getElementById(tableName);
   var rows = table.rows;

   //to refresh delete all rows from the table
   	if (rows.length > 1) {
   		for (var i=rows.length-1;i > 0;i--) {
			table.deleteRow(i);
		}
    }
   	
   	if (tableName == 'mappedTable') {
   		fillMappedTable();
   	} else if (tableName == 'tgtTable') {
   		fillTargetTable('-1');
   	} else if (tableName == 'srcTable') {
   		fillSourceTable('-1');
   	}
   return true;
}

function fillMappedTable() {

	for (var i=0;i < mapWsArray.length;i++) {
		var mapRec = mapWsArray[i];
		addMappedRow('mappedTable',i,'chk',getLocalizedString(mapRec.type),mapRec.srcname,mapRec.tgtname);
	}
	return true;
}

function clickedRemove() {
	var theForm = document.forms[0];
	var tmpMapWsArray = new Array();
	var counter = 0;
	var flagChecked = false;
	
	for (var k=0;k<theForm.elements.length;k++) {
		if (theForm.elements[k].type=='checkbox'&& theForm.elements[k].name=='chk') {
			if (theForm.elements[k].checked) {
				flagChecked = true;
				break;
			}
		}
					
	}
	
	if (!flagChecked){
		msg = "workstepMapValidation2"
		return msg
	}
	for (var k=0;k<theForm.elements.length;k++) {
		if (theForm.elements[k].type=='checkbox'&& theForm.elements[k].name=='chk') {
			if (!theForm.elements[k].checked) {
				tmpMapWsArray[counter] = mapWsArray[theForm.elements[k].value];
				counter++;
			} else {
				
				var rec = mapWsArray[theForm.elements[k].value];
				//check if a record already exists in the target record
				var found = false;
				var len = -1;
				for (var count=0;count < tgtWsArray.length;count++) {
					var typ = tgtWsArray[count].type;
					var nm = tgtWsArray[count].name;

					if (rec.type == typ && nm == rec.tgtname) {
						found = true;
						len=count;
						break;
					}
				}
				if (!found) {
					len = tgtWsArray.length;
					
				}
				tgtWsArray[len] = new tgtRecord(rec.type,rec.tgtname);

				//check if a record already exists in the source record
				found = false;
				len = -1;
				for (var count=0;count < srcWsArray.length;count++) {
					var typ = srcWsArray[count].type;
					var nm = srcWsArray[count].name;

					if (rec.type == typ && nm == rec.srcname) {
						found = true;
						len=count;
						break;
					}
				}
				if (!found) {
					len = srcWsArray.length;
				}
				srcWsArray[len] = new srcRecord(rec.type,rec.srcname);
			}
		}
	}

	mapWsArray = tmpMapWsArray;	

	if (mapWsArray.length == 0){
		theForm.chkbxall.checked = false;
	}
	refreshTable('mappedTable');
	refreshTable('tgtTable');
	refreshTable('srcTable');
	return true;
}

function clickedMap() {
	var theForm = document.forms[0];
	//Get the row selected from src table
	var srcId = -1;
	var tgtId = -1;
	srcId = srcSelection;
	tgtId = tgtSelection;
		
	if (tgtId == -1 || srcId == -1) {
		msg = "workstepMapValidation1";
		refreshAllTables();	
		return msg;
	}
	if (!validateWsMapping(srcId,tgtId)) {
		msg = "workstepMapValidation";
		refreshAllTables();	
		return msg;
	}
	
	var srcVal = srcWsArray[srcId];
	var tgtVal = tgtWsArray[tgtId];
	
	//check if a record already exists in the map record
	var found = false;
	var idx = -1;
	for (var count=0;count < mapWsArray.length;count++) {
		var sName = mapWsArray[count].srcname;
		var tName = mapWsArray[count].tgtname;
		
		if (sName == srcVal.name && tName == tgtVal.name) {
			found = true;
			idx=count;
			break;
		}
	}
	
	if(!found) {
		idx = mapWsArray.length;
	}
	mapWsArray[idx]=new mapRecord(tgtVal.type,srcVal.name,tgtVal.name);
	
	refreshAllTables();	
	return true;
}

function refreshAllTables(){
	srcSelection = -1;
	tgtSelection = -1;
	refreshTable('mappedTable');
	refreshTable('tgtTable');
	refreshTable('srcTable');
}

function validateWsMapping(srcId,tgtId) {
	var srcVal = srcWsArray[srcId];
	var tgtVal = tgtWsArray[tgtId];

	if (srcVal.type != tgtVal.type) {
		return false;
	}
	return true;
}

function addRow(tableName,value,srno, radiobutton_name,type,name) {
    
  var table = document.getElementById(tableName);
  var newRow;
  var newCell;
  var newChild;
  var cssClass;
  var cssClass1;
  
  newRow = document.createElement('TR');
  cssClass = ((srno%2)==0)?'ValLft':'ValLftAlt';
  cssClass1 =((srno%2)==0)?'ChkBox':'ChkBoxAlt';
  newCell = document.createElement('TD');
  
  if (Ext.isIE7 || Ext.isIE8) {
  	newChild = document.createElement("<INPUT type=\'radio\' name=\'"+radiobutton_name+"\' value=\'"+value+"\' onClick=\"toggleRadioSelection(\'"+srno+"\',\'"+tableName+"\',\'"+radiobutton_name+"\');\">");
  } else {
  	newChild = document.createElement('INPUT');
	newChild.setAttribute('type','radio');
	newChild.setAttribute('value',''+value);
	newChild.setAttribute('name',radiobutton_name);	
        newChild.setAttribute('onClick','toggleRadioSelection('+srno+',\''+tableName+'\',\''+radiobutton_name+'\')');
  }
  newChild.className = 'InptRadio';
  newCell.appendChild(newChild);
  newCell.className = cssClass1;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(type);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(name);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  table.tBodies[0].appendChild(newRow);

  setCheckBoxStyleForIE();
  return true;
} 

function addMappedRow(tableName,srno,chkbox_name,type,srcname,tgtname) {
   var table = document.getElementById(tableName);
  var newRow;
  var newCell;
  var newChild;
  var cssClass;
  var cssClass1

  newRow = document.createElement('TR');
  cssClass = ((srno%2)==0)?'ValLft':'ValLftAlt';
  cssClass1 =((srno%2)==0)?'ChkBox':'ChkBoxAlt';
  newCell = document.createElement('TD');
	if (Ext.isIE7 || Ext.isIE8) {
		newChild = document.createElement("<INPUT type=\'checkbox\' name=\'"+chkbox_name+"\' value=\'"+srno+"\' onClick=\"toggleCheckboxSelection(\'"+srno+"\',\'"+tableName+"\',\'"+chkbox_name+"\')\">");
	} else {
	  newChild = document.createElement('INPUT');
	  newChild.setAttribute('type','checkbox');
	  newChild.setAttribute('value',''+srno);
	  newChild.setAttribute('name',chkbox_name);
	  newChild.setAttribute('onClick','toggleCheckboxSelection('+srno+',\''+tableName+'\',\''+chkbox_name+'\')');
	}
  newChild.className = 'InptRadio';
  newCell.appendChild(newChild);
  newCell.className = cssClass1;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(type);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(srcname);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  newCell = document.createElement('TD');
  newChild = document.createTextNode(tgtname);
  newCell.appendChild(newChild);
  newCell.className = cssClass;
  newRow.appendChild(newCell);

  table.tBodies[0].appendChild(newRow);

  setCheckBoxStyleForIE();
  return true;
}

function toggleRadioSelection(srno,tableName, controlName) {
	var table=document.getElementById(tableName);
	var theForm = document.forms[0];
	var isChecked = false;
	var selId = -1;
	
	for (var k=0;k<theForm.elements.length;k++) {
		if (theForm.elements[k].type=='radio' && theForm.elements[k].name==controlName) {
			if (theForm.elements[k].checked) {
				selId = parseInt(theForm.elements[k].value);
				break;
			}
		}
	}
	
	if (tableName == 'srcTable') {
		srcSelection = selId;
	} else if (tableName == 'tgtTable') {
		tgtSelection = selId;
	}	
	for (var c=1;c <table.rows.length;c++) {
		var row = table.rows[c];
		
		if (c == eval(parseInt(srno) + parseInt(1))) {
			for (var j=0;j < row.cells.length;j++) {
				var cell = row.cells[j];
				var c_name = cell.className;

				if (c_name.indexOf("Slct") == -1) {
					if ((c-1)%2 == 1) {
						var temp_name = c_name.substring(0,c_name.length-3);
						cell.className = temp_name + "Slct";
					}
					else
						cell.className = c_name + "Slct";
				}
			}
		} else {
			for (var j=0;j < row.cells.length;j++) {
				var cell = row.cells[j];
				var origc_name = cell.className;
				if (origc_name.indexOf("Slct") > -1) {
					var c_name = origc_name.substring(0,origc_name.length-4);
					if ((c-1)%2 == 1)
						cell.className = c_name + "Alt";
					else
						cell.className = c_name;
				}
			}
		}
	}
}

function toggleCheckboxSelection(srno,tableName, controlName) {
	var table=document.getElementById(tableName);
	var theForm = document.forms[0];
	var id = parseInt(srno) + 1;
	var row = table.rows[id];
	if (row == null) return;
	var isChecked = false;
	for (var k=0;k<theForm.elements.length;k++) {
		if (theForm.elements[k].type=='checkbox'&& theForm.elements[k].name==controlName && theForm.elements[k].value == srno) {
			if (theForm.elements[k].checked) {
				isChecked = true;
			} else {
				isChecked = false;
			}
		}
	}
	
	if (isChecked) {
		for (var j=0;j < row.cells.length;j++) {
			var cell = row.cells[j];
			var c_name = cell.className;
			if (c_name.indexOf("Slct") == -1) {
				if ((id-1)%2 == 1) {
					var temp_name = c_name.substring(0,c_name.length-3);
					cell.className = temp_name + "Slct";
				} else
					cell.className = c_name + "Slct";
			}
		}
	} else {
		for (var j=0;j < row.cells.length;j++) {
			var cell = row.cells[j];
			var origc_name = cell.className;
			if (origc_name.indexOf("Slct") > -1) {
				var c_name = origc_name.substring(0,origc_name.length-4);
				if ((id-1)%2 == 1)
					cell.className = c_name + "Alt";
				else
					cell.className = c_name;
			}
		}
	}
	
	
		//if all the entries are selected select
		//select all option
		var bFlag = true;
		var ind = parseInt(srno) + 1;
		var cbIndex = 0;
		for(var k=0;k<theForm.elements.length;k++)
		{
			if( ind != -1)
			{
				if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
				{
					if(!theForm.elements[k].checked)
						bFlag = false;
					cbIndex++;
				}
			}
			else
			{
				if(theForm.elements[k].type=='checkbox' && theForm.elements[k].name==controlName)
				{
					if(!theForm.elements[k].checked)
						bFlag = false;
				}
			}
		}
		if(bFlag)
			theForm.chkbxall.checked = true;
		else
			theForm.chkbxall.checked = false;

}