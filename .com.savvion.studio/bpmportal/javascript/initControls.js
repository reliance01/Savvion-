   var values     = new Array();

  // Add a new control name with its value.
  function addValue( name, value) 
  {
    values[ values.length ] = new Array( name, Array(value));
  }
  // Add a new value for this multi valued control
  function addValues( name, value) 
  {
    for(var j=0; j < values.length; j++) 
    {
      if(name == values[j][0])
      {
        values[j][1].push(value);
        return;
      }
    }
    addValue(name, Array(value));
  }
  // Executed onload of page to initialise values of all controls on page
  function initControls() 
  {
    for(var i=0; i < document.forms[ 0 ].elements.length; i++) 
    {
      var control = document.forms[ 0 ].elements[i];
      for(var j=0; j < values.length; j++) 
      {
        if(control.name == values[j][0])
        {
          switch(control.type)
          {
            case "submit":
            case "button":
            case "text":
            case "textarea":
              settextValue(control, values[j][1][0])
              break;
            case "select-one":
            {
              clearcomboboxValue(control);
              setcomboboxValue(control, values[j][1][0]);
              break;
            }
            case "select-multiple":
            {
              clearcomboboxValue(control);
              for(var selctr=0; selctr<values[j][1].length; selctr++)
              {
                setcomboboxValue(control, values[j][1][selctr]);
              }
              break;
            }
            case "checkbox":
            {
              if(values[j][1].length==1 && values[j][1][0]=="true")
              {
                setcheckboxValue(control, "true")
              }
              else 
              {
                for(var selctr=0; selctr<values[j][1].length; selctr++)
                {

                  if(control.value == values[j][1][selctr] )
                  {
                    setcheckboxValue(control, "true")
                  }
                }
              }
              break;
            }
            case "radio":
            {
              setcheckboxValue(control, control.value == values[j][1][0]);
              break;
            }
          }
        }
      }
    }
  }
  function settextValue(textObject, value)
  {
    textObject.defaultValue = value;
    textObject.value = value;
  }
  function clearcomboboxValue(dropDownObject, valueToSelect)
  {
    var length = dropDownObject.length;
    for(var ctr=0; ctr<length; ctr++)
    {
      dropDownObject.options[ctr].defaultSelected = false;
      dropDownObject.options[ctr].selected = false;
    }
  }
  function setcomboboxValue(dropDownObject, valueToSelect)
  {
    var length = dropDownObject.length;
    for(var ctr=0; ctr<length; ctr++)
    {
      if(dropDownObject.options[ctr].value == valueToSelect)
      {
        dropDownObject.options[ctr].defaultSelected = true;
        dropDownObject.options[ctr].selected = true;
      }
    }
  }
  function setcheckboxValue(checkBoxObject, value)
  {
      checkBoxObject.defaultChecked = value;
      checkBoxObject.checked = value;
  }

  var hiddenControls = new Array(); 

  // Add a hidden control name.
  function addHiddenControls( name) 
  {
    hiddenControls[ hiddenControls.length ] = name;
  }

  // Executed onload of page to initialise values of all controls on page
  function hideControls() 
  {
    for(var j=0; j < hiddenControls.length; j++) 
    {
      sbm.util.hide(hiddenControls[j]);
    }
  }

  var dependants = new Array();

  // Add a new dependant for this dependantOn control
  function addDependants( dependant, dependantOn, dependencyType) 
  {
    var newDependancy = Array(dependant, dependencyType);
    for(var j=0; j < dependants.length; j++) 
    {
      if(dependantOn == dependants[j][0])
      {
        dependants[j][1].push(newDependancy);
        return;
      }
    }
    dependants[ dependants.length ] = new Array( dependantOn, new Array(newDependancy));
  }
  // Executed onload of page to initialise dependency of all controls on page
  function initDependancy() 
  {
    for(var i=0; i < dependants.length; i++) 
    {
      setDependants(i)
    }
  }
  function setDependants(i)
  {
    var dependantOn = document.getElementsByName(dependants[i][0]);
    for(var j=0; j < dependants[i][1].length; j++) // Iterate through dependants list
    {
      var elementList = document.getElementsByName(dependants[i][1][j][0]); 
      for(var k=0; k < elementList.length; k++) // Enable each if dependant on is checked
      {
        switch (dependants[i][1][j][1])
        {
          case "IF_CHECKED_EDITABLE":
            elementList[k].disabled = !dependantOn[0].checked;
            break;
          case "IF_CHECKED_DISPLAY":
            elementList[k].style.visibility = (dependantOn[0].checked?"visible":"hidden");
            break;
          case "IF_CHECKED_":
            elementList[k].style.visibility = (dependantOn[0].checked?"visible":"hidden");
            break;
          default:
            alert("Zol Hai");
        }
      }
    }
  }
  function setDependantsOn(dependantName)
  {
    for(var j=0; j < dependants.length; j++) 
    {
      if(dependantName == dependants[j][0])
      {
        setDependants(j);
      }
    }

  }


  var tabbedPanes     = new Array();

  // Add a new tabbed pane.
  function addTabbedPane( name, value)
  {
    tabbedPanes[ tabbedPanes.length ] = new Array( name, Array(value));
  }
  // Add a tab
  function addTabs( name, value)
  {
    for(var j=0; j < tabbedPanes.length; j++)
    {
      if(name == tabbedPanes[j][0])
      {
        tabbedPanes[j][1].push(value);
        return;
      }
    }
    addTabbedPane(name, Array(value));
  }
  function selectTab(tabPane, tab)
  {
    for(var j=0; j < tabbedPanes.length; j++)
    {
      if(tabbedPanes[j][0] == tabPane)
      {
        var currTabPane = tabbedPanes[j][1];
        for(var k=0; k < currTabPane.length; k++)
        {
          if(currTabPane[k] != tab)
          {
            document.getElementById(currTabPane[k]).style.display = 'none';
            document.getElementById(currTabPane[k]+"_cell").className="";
          }
        }
        document.getElementById(tab).style.display = 'block';
        document.getElementById(tab+"_cell").className="SbmSelectedTab";
      }
    }
  }
  function initTabs()
  {
    for(var j=0; j < tabbedPanes.length; j++)
    {
      var currTabPane = tabbedPanes[j][1];
      for(var k=0; k < currTabPane.length; k++)
      {
        document.getElementById(currTabPane[k]).style.display = ((k != 0)?'none':'block');
        document.getElementById(currTabPane[k]+"_cell").className = ((k != 0)?"":"SbmSelectedTab");
      }
    }
  }
  function doNothing()
  {
  }
