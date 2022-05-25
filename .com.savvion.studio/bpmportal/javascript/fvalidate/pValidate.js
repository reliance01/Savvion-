var clickedButton = "none";
//------------------------------------------------------------------------------------------------------------------//
//  This method is the main method of validation for form fields
//  fValidate has been used to validate form fields only.
//
//------------+-----------------------------------------------------------------------------------------
//  Field     |   Description                                                                           |
//------------+-----------------------------------------------------------------------------------------+
//  formElem  |   This is the form dom element                                                          |
//  vType     |   This argument represents validation type for fValidate configuration                  |
//            |   the values that are supported : 'box' | 'alert' | 'box,alert'                         |
//------------+-----------------------------------------------------------------------------------------+


//------------------------------------------------------------------------------------------------------------------//
sbm.pValidate = function(formElem,vType)
{
	 //------------------------------------------------------------------------------------------------------//
	 //    fValidate validation for forms
	 //------------------------------------------------------------------------------------------------------//
	  var errorMode = 4;
	 if((vType == undefined) || (vType == ''))
		 errorMode = 14;//default setting is box,alert
	 if(vType == 'box') errorMode = 4;
	 else if(vType == 'label') errorMode = 2;
	 else if(vType == 'box+label') errorMode = 10;
	 else if(vType == 'alert') errorMode = 0;
	 else if(vType == 'alert+label') errorMode = 11;
	 else if(vType == 'alert+box+label') errorMode = 28;
	 else if(vType == 'alert+box') errorMode = 14;
	 
	 // do not validate in save, assign or reassign
	 if(clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
	 {
		 // fValidate validation	
		 if(!sbm.validatePortalForm(formElem,false,false,false,true,errorMode))
		 {
			 //if(errorMode == 4)
			 //Effect.Grow("errors",{direction:'top-left',duration:3});
			 //Effect.Appear("errors",{duration:2});
			 return false;
		 }
	 }
	 
	 //-------------------------------------------------------------------------------------------------------//
	 //   validation for list dataslot
	 //   list dataslot related
	 //--------------------------------------------------------------------------------------------------------//
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		if(document.forms[ 0 ].elements[k].name != undefined){  
			if(document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_") != -1) {
				var index = document.forms[ 0 ].elements[k].name.indexOf("_dyn_val_");
				var dsName = document.forms[ 0 ].elements[k].name.substring(9); //9 is length of _dyn_val_
				var dsCtrl = eval("document.forms[ 0 ]."+dsName);
				for(var j=0; j < dsCtrl.length;j++) {
					dsCtrl.options[j].selected = true;
				}
			}
		}
	  }
	 
	 
	 //------------------------------------------------------------------------------------------------------///
	 //   resource validation keep this separate from form validation
	 //   resource validation happens after form validation	//
	 ///------------------------------------------------------------------------------------------------------///
	 if(clickedButton.toLowerCase() != "bizsite_savetask" && clickedButton.toLowerCase() != "bizsite_reassigntask" && clickedButton.toLowerCase() != "bizsite_skip")
	 {
		 if(!sbm.validateResources(formElem))              
			  return false;
	 }
	 //-------------------------------------------------------------------------------------------------------///
	 //   upload document and xml dataslots...
	 //-------------------------------------------------------------------------------------------------------///
	 if(!eval(upload()))
	 {
	         alert("document upload has been failed...");
		 return false;
	 }
	 
	 //--------------------------------------------------------------------------------------------------------///
	 //  validation must end here...
	 //--------------------------------------------------------------------------------------------------------///
	 return true;
}
//----------------------------------------------------------------------------------------------------//
sbm.validatePortalForm = function( f, bConfirm, bDisable, bDisableR, groupError, errorMode )
{	
	//	Set defaults
	bConfirm	= Boolean( bConfirm );
	bDisable	= Boolean( bDisable );
	bDisableR	= Boolean( bDisableR );
	groupError	= Boolean( groupError );
	errorMode	= ( typeof errorMode != 'undefined' ) ? parseInt( errorMode, 10 ) : 0;
	
	//	Init vars and fValidate object
	var params, fvCode, type;
	if ( typeof f.fv == 'undefined' )
	{
		f.fv = new fValidate( f, errorMode, groupError );		
	} else {		
		f.fv._reset();
		f.fv.errorMode = errorMode;
	}
	
	//	Loop through all form elements	
	var elem, i = 0, attr = f.fv.config.code;
	
	while ( elem = f.elements[i++] )
	{
			//	Skip fieldsets
			if ( elem.nodeName == "FIELDSET" ) continue;
		
			//	Does element have validator attribute? (short-circuit check)
			fvCode			= ( elem[attr] ) ? elem[attr] : elem.getAttribute( attr );
			
			if ( !( typeof fvCode == 'undefined' || fvCode == null || fvCode == "" ) )
			{
				//	Set params, validation type, and validation state
				params			= fvCode.split( "|" );
				type			= params[0];
				elem.validated	= true;
				
				//	Valid validator type?
				if ( typeof f.fv[type] == 'undefined' )
				{				
					f.fv.devError( [type, elem.name], 'notFound' );
					return false;
				}
				
				//	Check for modifiers
				switch( params.last() )
				{				
					case 'bok'	:	//	bok requested
						params = params.reduce( 1, 1 );
						elem.bok = true;
						break;
					case 'if'	:	//	Conditional validation requested
						params = params.reduce( 1, 1 );
						elem._if_ = true;
						break;
					case 'then'	:	//	Conditional validation requested
						params = params.reduce( 1, 1 );
						elem._then_ = true;
						break;
					default		:	//	No modifiers
						params = params.reduce( 1, 0 );
						
				}
				
				//	Is element an array?
				if ( /radio|checkbox/.test( elem.type ) )
				{
					//	Set group property
					elem.group = f.elements[elem.name];
				}
				
				//	Add events if not already added
				if ( typeof elem.fName == 'undefined' )
				{
					//	If element is an array			
					if ( typeof elem.group != 'undefined' )
					{
						for ( var j = 0; j < elem.group.length; j++ )				
						{
							//	Apply event-function to each child
							if ( f.fv.config.clearEvent != null )
							{
								//	fvalidate.addEvent( elem.group.item( j ), fv.config.clearEvent, fv.revertError, false );
								addEvent( elem.group.item( j ), f.fv.config.clearEvent, f.fv, 'revertError', false );
							}
						}
					}
					else
					{
						//	Apply event-function to element
						//	fvalidate.addEvent( elem, fv.config.clearEvent, fv.revertError, false );
						addEvent( elem, f.fv.config.clearEvent, f.fv, 'revertError', false );
					}
				}
				
				//	Set formatted name, current element
				elem.fName	= elem.name.format();
				f.fv.elem		= elem;
				f.fv.type		= type;
				
				//alert(" elem.name :"+elem.name+			
				//"\nfvCode :"+fvCode);
				//	Create function to call the proper validator method of the fValidate class
				// validate selectively for save..
				if(clickedButton.toLowerCase() == "bizsite_savetask")
				{
				      if( (type == "datetime") || (type == "number") || (type == "url") || (type == "decimal"))
				      {
					      if(elem.value != undefined && elem.value.length != 0)
					      {
						      var func = new Function( "obj", "method", "obj[method]( " + params.toArgString() + " );" );
						      func( f.fv, type );
					      }
				      }
				}
				else
				{
					var func = new Function( "obj", "method", "obj[method]( " + params.toArgString() + " );" );
					func( f.fv, type );
				}
				
				//	If element test failed AND group error is off, return false
				if ( elem.validated == false && groupError == false ) return false;
				
				//	Clear error if field okay
				if ( elem.validated == true ) f.fv.revertError();
			}
	 } //	end of element loop
		
	 //	If group error, show it
	 if ( groupError ) f.fv.showGroupError();
	 
	 //	Return false if errors found
	 if ( f.fv.errors.length > 0 )
	 {
		return false;
	 }
	 
	 //	Show pre-submission confirmation
	 if ( bConfirm && !confirm( f.fv.config.confirmMsg ) )
	 {
		if ( f.fv.config.confirmAbortMsg != '' ) alert( f.fv.config.confirmAbortMsg );
			return false;
	  }
	  
	  //	Disable reset and/or submit buttons if requested
	  if ( bDisable ) 
	  {
			if ( typeof f.fv.config.submitButton == 'object' )
			{
				var sb, j = 0;
				while( sb = f.fv.config.submitButton[j++] )
				{
					if ( f.fv.elementExists( sb ) )
					{
						f.elements[sb].disabled = true;
					}
				}
			}
			else if ( f.fv.elementExists( f.fv.config.submitButton ) )
			{
				f.elements[f.fv.config.submitButton].disabled = true;
			}
	  }
	  if ( bDisableR && f.fv.elementExists( f.fv.config.resetButton ) )
	  {
		f.elements[f.fv.config.resetButton].disabled = true;
	  }
		
	  //	Successful Validation.  Submit form
	  return true;
		
	 function addEvent( elem, evt, obj, method, capture )
	 {
		        var self = elem;
			if ( typeof elem.attachEvent != 'undefined' )
			{
				elem.attachEvent( "on" + evt, function() { obj[method]( self ) } );
			}
			else if ( typeof elem.addEventListener != 'undefined' )
			{
				elem.addEventListener( evt, function() { obj[method]( self ) }, capture );
			}
			else if ( f.fv.config.eventOverride )
			{
				elem['on' + evt] = function() { obj[method]( self ) };
			}
	 }
}
//---------------------------------------------------------------------------------------------------------//
//   resources validation function
//---------------------------------------------------------------------------------------------------------//
sbm.validateResources = function(formElem)
{
	var apt_days = 0;
	 var apt_hrs = 0;
	 var apt_mins = 0;
	 var apt = 0;
	 var msg= "";
	 var invalid   = new Array();
	 var nofocus   = new Array();
	 var isValid = true;
         
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		 if(document.forms[ 0 ].elements[k].name != undefined){    	
			 if(document.forms[ 0 ].elements[k].name.indexOf("resources.") != -1 && document.forms[ 0 ].elements[ k ].name == name) {
				 if(document.forms[ 0 ].elements[ k ].value == '') {
					 fieldValid = false;
					 invalid[ invalid.length ] = name;
				 }
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "APT.days" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_days") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
				 apt_days=parseInt(document.forms[ 0 ].elements[ k ].value)*24;
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "APT.hours" || document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_hours") && parseInt(document.forms[ 0 ].elements[ k ].value) > 0) {
				 apt_hrs=parseInt(document.forms[ 0 ].elements[ k ].value);
			 }
			 if((document.forms[ 0 ].elements[ k ].name == "bizsite_ActualProcessingTime_minutes" || document.forms[ 0 ].elements[ k ].name == "APT.mins") && parseInt(document.forms[ 0 ].elements[ k ].value) >= 0) {
				 apt_mins=parseInt(document.forms[ 0 ].elements[ k ].value)/60;
			 }
			 apt=apt_days+apt_hrs+apt_mins;
		 }
	 }
	 
	 for(var k=0;k<document.forms[ 0 ].elements.length;k++) {
		 if ((document.forms[ 0 ].elements[k] != undefined) && (document.forms[ 0 ].elements[k].name != undefined)){
			 if(document.forms[ 0 ].elements[k].name.indexOf("resources.ActualProcessingTime") != -1) {
				 document.forms[ 0 ].elements[k].value = apt;
				 if(apt <= 0) {
					 fieldValid = false;
					 invalid[ invalid.length ] = 'resources.ActualProcessingTime';
					 nofocus[ nofocus.length ] = 'resources.ActualProcessingTime';
					 isValid = false;
					 break;
				 }
			 }
		 }
	 }
	 
         if(!isValid && invalid.length > 0 ) 
	 {
		 msg += "The following fields are invalid:\n\n";
		 for( i = 0; i < invalid.length; i++ )
		 {
		        if(invalid[ i ] != null && invalid[ i ].indexOf("resources.") != -1 ) 
			{
				 if(invalid[ i ].substring(10) == "ActualProcessingTime") 
					 msg += "   - " + "Work Time" + "\n";
				 else
					 msg += "   - " + invalid[ i ].substring(10) + "\n";
			}
			else 
				 msg += "   - " + invalid[ i ] + "\n";			
		 }
		 msg += "\n\n";
         }
	 
	 if(!isValid)
	 {
	     alert(msg);
	     return false;
	 }
	 else
	     return true;	
}
//-------------------------------------------------------------------------------------//
//    This function is for document dataslot validation
//-------------------------------------------------------------------------------------//
fValidate.prototype.document = function()
{
	//if (this.typeMismatch('document')) return;
	if((document.applets != undefined) && (document.applets.length !=0 ))
	{
		if(document.applets[ this.elem.name ] != undefined)
		{
        		      if(!document.applets[ this.elem.name ].existDocuments())
			      {
				       this.elem.validated == false;
				       //this.throwError( [this.elem.fName] );
				       if(typeof this.elem.label == 'undefined')
				       {
					       var fieldName = this.elem.fName;
					       if(fieldName.indexOf("bizsite dataslot ") != -1)
					       {
						       this.throwError( [fieldName.substr(17,fieldName.length)] );
					       }
					       else 
						       this.throwError( [fieldName]);	 
				       }
				       else
				       {
					       var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
					       this.throwError([fieldLabel]);
				       }
				       return;
			      }
	        }
	}
	else if((document.embeds != undefined) && (document.embeds.length !=0 ))
	{
	        if(document.embeds[ this.elem.name ] != undefined)
		{
                              if(!document.embeds[ this.elem.name ].existDocuments())
			      {
			               this.elem.validated == false;
				       //this.throwError( [this.elem.fName] );
				       if(typeof this.elem.label == 'undefined')
				       {
					       var fieldName = this.elem.fName;
					       if(fieldName.indexOf("bizsite dataslot ") != -1)
					       {
						       this.throwError( [fieldName.substr(17,fieldName.length)] );
					       }
					       else 
						       this.throwError( [fieldName]);	 
				       }
				       else
				       {
					       var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
					       this.throwError([fieldLabel]);
				       }
				       return;
			      }
	        }
	}
	else
	{
		// check if it has mandatory javascript interface
	        if(this.elem.name.substr(0,17) == 'bizsite_dataslot_')
		{
		         var dsName = this.elem.name.substring(17);
			 var mandatoryCheck = "doccheck_mandatory_"+dsName;
			 if(document.getElementById(mandatoryCheck) != undefined)
			 {
				 var mElem = document.getElementById(mandatoryCheck);
				 if(mElem.value == "false")
				 {
					  //this.throwError( [this.elem.fName] );
					  if(typeof this.elem.label == 'undefined')
					  {
						  var fieldName = this.elem.fName;
						  if(fieldName.indexOf("bizsite dataslot ") != -1)
						  {
							  this.throwError( [fieldName.substr(17,fieldName.length)] );
						  }
						  else 
							  this.throwError( [fieldName]);	 
					  }
					  else
					  {
						  var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
						  this.throwError([fieldLabel]);
					  }
					  return;
				 }
			 }
		}
	}
	this.elem.validated == true;
	return;
}
//--------------------------------------------------------------------------------------//
//     This function is date validation function
//--------------------------------------------------------------------------------------//
fValidate.prototype.datetime = function(format)
{
     if(!Date.isValid(this.elem.value,format))
     {
	     //this.throwError( [this.elem.fName,format] );
	     if(typeof this.elem.label == 'undefined')
	     {
			  var fieldName = this.elem.fName;
			  if(fieldName.indexOf("bizsite dataslot ") != -1)
			  {
				  this.throwError( [fieldName.substr(17,fieldName.length),format] );
			  }
			  else 
				  this.throwError( [fieldName,format]);	 
	      }
	      else
	      {
			  var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
			  this.throwError([fieldLabel,format]);
	      }
	     return;
     }
     this.elem.validated == true;
     return;
}

//-------------------------------------------------------------------------------------//
//    This function is for xml dataslot validation
//-------------------------------------------------------------------------------------//
fValidate.prototype.xml = function()
{
	//if (this.typeMismatch('xml')) return;
	if((document.applets != undefined) && (document.applets.length !=0 ))
	{
		if(document.applets[ this.elem.name ] != undefined)
		{
                              if(!(document.applets[ this.elem.name ].isXML() && !document.applets[ this.elem.name ].existDocuments()))
			      {
			               this.elem.validated == false;
				       //this.throwError( [this.elem.fName] );
				       if(typeof this.elem.label == 'undefined')
				       {
						  var fieldName = this.elem.fName;
						  if(fieldName.indexOf("bizsite dataslot") != -1)
						  {
							  this.throwError( [fieldName.substr(16,fieldName.length)] );
						  }
						  else 
							  this.throwError( [fieldName]);	 
					}
					else
					{
						  var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
						  this.throwError([fieldLabel]);
					}
					return;
			      }
	        }
	}
	else if((document.embeds != undefined) && (document.embeds.length !=0 ))
	{
	        if(document.embeds[ this.elem.name ] != undefined)
		{
                              if(!document.embeds[ this.elem.name ].existDocuments())
			      {
			               this.elem.validated == false;
				       //this.throwError( [this.elem.fName] );
				       if(typeof this.elem.label == 'undefined')
				       {
						  var fieldName = this.elem.fName;
						  if(fieldName.indexOf("bizsite dataslot") != -1)
						  {
							  this.throwError( [fieldName.substr(16,fieldName.length)] );
						  }
						  else 
							  this.throwError( [fieldName]);	 
					}
					else
					{
						  var fieldLabel = sbm.filterLabel(this.elem.label.innerHTML);
						  this.throwError([fieldLabel]);
					}
					return;
			      }
	        }
	}
	this.elem.validated == true;
	return;
}

sbm.loadCreditCardTypes = function(id)
{
	DWRUtil.addOptions($(id), fvalidate.i18n.creditCardType);	
}

sbm.addValidation = function(form,fieldId,validationRule)
{
	var fieldElement = $(fieldId);
	fieldElement.setAttribute("alt",validationRule);
	return false;
}
sbm.isNumber = function(value)
{
	var valid = true;
	if(isNaN(parseInt(value, 10 )) || isNaN(parseFloat(value)))
		valid = false;
	return valid;
}

sbm.filterLabel = function(label)
{
	// this filter is used to strip off the colon
	if((label.length > 1) && (label.substring(label.length-1,label.length) == ":"))
		   label = label.substring(0,label.length-1)
	return label;	
}
	

