// Copyright (c) 2015 - 2016. Aurea Software, Inc. All Rights Reserved.

// You are hereby placed on notice that the software, its related technology and
// services may be covered by one or more United States ("US") and non-US patents.
// A listing that associates patented and patent-pending products included in the
// software, software updates, their related technology and services with one or
// more patent numbers is available for you and the general public's access at
// www.aurea.com/legal/ (the "Patent Notice") without charge. The association of
// products-to-patent numbers at the Patent Notice may not be an exclusive listing
// of associations, and other unlisted patents or pending patents may also be
// associated with the products. Likewise, the patents or pending patents may also
// be associated with unlisted products. You agree to regularly review the
// products-to-patent number(s) association at the Patent Notice to check for
// updates.
//  -----------------------------------------------------------------
//  except where attribution is given below
//  -----------------------------------------------------------------

// Validation scripts

// ----------------------------------------------------------------

// VALIDATOR
var fields     = new Array();   // form field registry
var messages   = new Array();   // validation messages
var clickedButton = "none";
var decimalFields = new Array();

// validate form
function validate()
{
   // only validate if trying to complete a task or start a process
   if (clickedButton != "none") {
   if ( clickedButton.toLowerCase() != "bizsite_completetask" &&
        clickedButton.toLowerCase() != "bizsite_savetask" &&
        clickedButton.toLowerCase() != "bizsite_createinstance" &&
	// required by web apps with custom submit buttons
	clickedButton.toLowerCase() != "sb_name" ) {     
     if( window.upload != null ) {
        
        return upload();
     }
     return true;
   }
   }
   
   //Validation for List dataslot
   //List Dataslot related
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

   var isValid   = true;
   var mandatory = new Array();
   var invalid   = new Array();
   var nofocus   = new Array();
   var labels  =new Array();
  
   //resource validation starts here
   var apt_days = 0;
   var apt_hrs = 0;
   var apt_mins = 0;
   var apt = 0;
   //resource validation ends here
   
   for( i = 0; i < fields.length; i++ ) {
  
      var fieldValid = true;
      var name = fields[ i ][ 0 ];
      
      var type = fields[ i ][ 1 ].toLowerCase();
      var required = fields[ i ][ 2 ];
      var label = fields[ i ][ 3 ];
      var size = fields[ i ][ 4 ];
      
      // validation library check..
      
      if(type != "document"){
      if((type != undefined) && (type != '') && (document.forms[ 0 ].elements[ name ].value !='')){	
	      if(type =="string_email"){
                    if(label== undefined) label = name;
		    if(!validateEmail(document.forms[ 0 ].elements[ name ].value,label))
		     return false;
		    
              }else if(type =="string_ssn"){
                    if(label== undefined) label = name;
		    if(!validateSSN(document.forms[ 0 ].elements[ name ].value,label))
		    return false;	      
		      
	      }else if(type.indexOf("string_phonenumber")!= -1){
		    if(label== undefined) label = name;
                    if(!validatePhoneNumber(document.forms[ 0 ].elements[ name ].value,label))
		    return false;
		    
              }else if(type.indexOf("string_postalcode")!= -1){
		    if(label== undefined) label = name;
                    if(!validatePostalCode(document.forms[ 0 ].elements[ name ].value,label))
		    return false;	      
	      
	      }else if(type.indexOf("string_creditcard")!= -1){
		    if(label== undefined) label = name;
		    var cardType ="";
		    var type_array = parseValidationType(type);
		    if(type_array[2] != undefined)
			  cardType = type_array[2];
		    if(!validateCreditCard(document.forms[ 0 ].elements[ name ].value,cardType,label))
		    return false;
		    
              }else if(type =="string_url"){
		    if(label== undefined) label = name;
                    if(!validateURL(document.forms[ 0 ].elements[ name ].value,label))
		    return false;	
		    
	      }else if(type =="number_currency"){
                    if(label== undefined) label = name;		      
		    if(!validateCurrency(document.forms[ 0 ].elements[ name ].value,label))
		    return false;		      
	      
	      }else if(type =="number"){
                    if(label== undefined) label = name;	
		    if(!validateNumber(document.forms[ 0 ].elements[ name ].value,label))
		    return false;
		    
	      }else if(type =="number_decimal"){
                    if(label== undefined) label = name;
		    if(!validateDecimalField(document.forms[ 0 ].elements[ name ],label))
		    return false;
		    
	       }else if(type.indexOf("number_numberrange")!= -1){
                    if(label== undefined) label = name;		       
		    var min = 0;
		    var max = 0;
		    var type_array = parseValidationType(type);
		    if(type_array[2] != undefined)
			  min = type_array[2];
		    if(type_array[3] != undefined)
			  max = type_array[3];
		    
		    if(!validateNumberRange(document.forms[ 0 ].elements[ name ].value,min,max,label))
		    return false;	    
	      } else if(type =="decimal"){
			if(label== undefined) label = name;
			if(!validateDecimalField(getElement( name ),label))
				return false;
	      }
      }
      if((size != undefined) && (size != '')){
      	if(!validateSize(label,document.forms[ 0 ].elements[ name ].value,size))
      	return isSizeValid;
      }
      }
      
      ///validation library ends here..
      // do not show mandatory fields in save, assign or reassign
      if(clickedButton != "none")
      {
	      if(clickedButton.toLowerCase() == "bizsite_savetask" || clickedButton.toLowerCase() == "bizsite_reassigntask")
	         return true;
      }
      
      // check whether this field is mandatory
      if( required ) {

         // ... field is mandatory, check if value exists
         if( type == "document" ) {
            
		 if(label== undefined) label = name;
		 if(document.applets.length !=0 )
		 {
                              fieldValid = document.applets[ name ].existDocuments();
			      if(fieldValid && document.applets[ name ].isXML())
			      {
				       var content = document.applets[ name ].getXmlDocumentContent();
				       if(content != null)
				       {
					       if(!validateXmlContent(name,content,"applet"))
					       {
								getAjaxLocalizedString('InvalidXMLDataSlot',[label]);
								return false;
					       }
				       }
			      }
	         }
		 else if(document.embeds.length !=0 )
		 {
			      fieldValid = document.embeds[ name ].existDocuments();
			      if(fieldValid && document.embeds[ name ].isXML())
			      {
				       var content = document.embeds[ name ].getXmlDocumentContent();
				       if(content != null)
				       {
					       if(!validateXmlContent(name,content,"embeds"))
					       {
								getAjaxLocalizedString('InvalidXMLDataSlot',[label]);
								return false;
					       }
				       }
			      }
		 }
		 else
		 {
			 // it has javascript interface
			 var docDsName = name;
			 docDsName=docDsName.substring(17);
			 var cmDsName='doccheck_mandatory_'+docDsName;
			 var elem = document.getElementById(cmDsName);
			 var value = elem.value;
			 if(value == "false")
			 	 fieldValid = false;
			 
		 }

                if( fieldValid == false )
		{
                             mandatory[ mandatory.length ] = name;
			     labels[labels.length] = new Array(name,label);
			     nofocus[ nofocus.length ] = name;
		}
            
         }
         else if( document.forms[ 0 ].elements[ name ].length > 0 ) {
            
            // ... radio buttons and checkboxes
            var elementChecked = false;
            for( l=0; l < getElement( name ).length; l++ ) {
               
               if( elementChecked == false ) {
                  
                  elementChecked = getElement( name )[ l ].checked;
               }
            }
            
            fieldValid = elementChecked;
            if( fieldValid == false ) {
               
               mandatory[ mandatory.length ] = name;
	       labels[labels.length] = new Array(name,label);
               nofocus[ nofocus.length ] = name;
            }
         }
         else if( document.forms[ 0 ].elements[ name ].value == '' ) {

            // ... standard elements
            mandatory[ mandatory.length ] = name;
            labels[labels.length] = new Array(name,label);
            fieldValid = false;
         }
      }


      // validate field value if it exists
      if( type != "document" ) {
         
         if( document.forms[ 0 ].elements[ name ] != null && document.forms[ 0 ].elements[ name ].value != '' ) {
            
            if( eval( 'window.validate_' + type + ' != null' ) ) {
               
               if ( ! eval( 'validate_' + type + '( "' + name + '" )' ) ) {
                  
                  fieldValid = false;
                  invalid[ invalid.length ] = name;
               }
            }
         }
      }
      
      //resource validation starts here
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
      //resource validation ends here

      if ( ! fieldValid ) {

         isValid = false;
         markIncomplete( name );
      }
      else {

         markComplete( name );
      }
     }
   
   }
   
   //resource validation starts here 
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
   //resource validation ends here 

   // display a message if a field is required or invalid 
   var msg = '';
   var labelmsg='';
   
   //set the isValid flag again here
   //if mandatory[] or invalid[] length is greater than 0 set this flag to false
   if(mandatory.length > 0 || invalid.length > 0) {
	   isValid = false;
   }
   if( ! isValid && mandatory.length > 0 ) {

      msg += "The following fields are mandatory:\n\n";
      for( i = 0; i < mandatory.length; i++ ) {

         if(labels[i][1]==undefined || labels[i][1]=="")
	 msg += "  - " + mandatory[ i ] + "\n";
	 else
	 msg += "  - " +labels[i][1] +"\n";
      }
      msg += "\n\n";
   }

   if( ! isValid && invalid.length > 0 ) {

      msg += "The following fields are invalid:\n\n";
      for( i = 0; i < invalid.length; i++ ) {

         if(invalid[ i ] != null && invalid[ i ].indexOf("resources.") != -1 ) {
         	if(invalid[ i ].substring(10) == "ActualProcessingTime") {
         		msg += "   - " + "Work Time" + "\n";
         	} else {
         		msg += "   - " + invalid[ i ].substring(10) + "\n";
         	}
         } else {
         	msg += "   - " + invalid[ i ] + "\n";
         }
      }
      msg += "\n\n";
   }

   if( ! isValid ) {

      if( mandatory.length + invalid.length > 1 ) {

         msg += "Please complete these fields before continuing.\n";
      }
      else {

         msg += "Please complete this field before continuing.\n";
      }
	for(i = 0; i < fields.length; i++ ) {
		var type = fields[ i ][ 1 ].toLowerCase();
		var name = fields[ i ][ 0 ];
		if((type == "date") && (document.forms[ 0 ].elements[ name ].value !='')){
			var dateDataslot = getElement(name);
			long2Date(dateDataslot);
		 }
	}
      alert( msg );
      var focusElement;
      if( mandatory.length > 0 ) {

         focusElement = mandatory[ 0 ];
      }
      else {
   
         focusElement = invalid[ 0 ];
      }
 
      window.location=( '#' + focusElement + '_locator' );
      
      var doFocus = true;
      for( i = 0; i < nofocus.length; i++ ) {
         
         if( nofocus[ i ] == focusElement ) {
            
            doFocus = false;
         }
      }

      if( doFocus ) {

         document.forms[ 0 ].elements[ focusElement ].focus();
      }
   }
   if (isValid == true)
      
     if( window.upload != null ) {
        
        if (eval(upload())) {
        
           isValid = true;
        }
        else {
        
           isValid = false;
        }
     }
     
   return isValid;
}

// register form field
function addField( name, type, isMandatory )
{
   fields[ fields.length ] = new Array( name, type, isMandatory );
}

function addFieldLabel( name,type, isMandatory,label)
{
   fields[ fields.length ] = new Array( name, type, isMandatory,label );
}

// set validation messages.
function setMandatoryLabels(message1,message2,message3,message4)
{
     messages[0]  = message1;
     messages[1]  = message2;
     messages[2]  = message3;
     messages[3]  = message4;
     
}

// set incomplete image
function markIncomplete( name )
{

   //document.images[ name + '_imgValid' ].src = bizsiteroot + "/images/overdue1.gif"; 
}

// set complete image
function markComplete( name )
{
 
   //document.images[ name + '_imgValid' ].src = bizsiteroot + "/images/noclip.gif"; 

}

// check whether field is currently filled ( for mandatory items ) 
function checkMandatory( name )
{
    
   // check if array -- radio buttons, etc.
   if( getElement( name ).length > 0 ) {

      var elementChecked = false;
      for( l=0; l < getElement( name ).length; l++ ) {

         if( elementChecked == false ) {

            elementChecked = getElement( name )[ l ].checked;
         }
      }
      return elementChecked;
   }

   // ... otherwise check if this element has a value
   if ( getElementValue( name ) == '' || 
        getElementValue( name ) == null ) {
 
      markIncomplete( name );
      return false;
   } 

   markComplete( name );
   return true;
}

function getElement( name )
{

   return document.forms[0].elements[ name ];
}

function getElementValue( name )
{

   return document.forms[0].elements[ name ].value;
}

// ----------------------------------------------------------------

// STRING VALIDATION

function validate_string( stringVal )
{
   stringVal = getElementValue( stringVal );
   return true;
}

// ----------------------------------------------------------------

// BOOLEAN VALIDATION

function validate_boolean( booleanVal )
{

   booleanVal = getElementValue( booleanVal );

   if ( booleanVal.toLowerCase() == 'true' || 
        booleanVal.toLowerCase() == 'false' ) { 

      return true;
   }
   return false;
}

// ----------------------------------------------------------------

// LONG VALIDATION

function validate_long( longVal )
{

   longVal = getElementValue( longVal );
   var index = longVal.lastIndexOf('.');
   if ( index >= 0 ) {
      return false;
   }
   else {
      index = longVal.lastIndexOf('e');
      if ( index >= 0 ) {
          return false;
      }

      index = longVal.lastIndexOf('E');

      if ( index >= 0 ) {
          return false;
      }
   }
	if ( isInteger(longVal)){
		return true;

   }else {
        return false;
   }
}

// ----------------------------------------------------------------

// DOUBLE VALIDATION

function validate_double( doubleVal )
{

   doubleVal = getElementValue( doubleVal );
   var num = new Number( doubleVal );
   var strVal = num.toString();
   if ( strVal != 'NaN' )
      return true;
   else
      return false;
}

// ----------------------------------------------------------------
// DOUBLE VALIDATION
function validate_number_double( doubleVal )
{

   doubleVal = getElementValue( doubleVal );
   var num = new Number( doubleVal );
   var strVal = num.toString();
   if ( strVal != 'NaN' )
      return true;
   else
      return false;
}


// ----------------------------------------------------------------
// Integer VALIDATION

function validate_integer( intVal )
{

   intVal = getElementValue( intVal );
   if(isInteger(intVal)){
     return true;
   }else {
     return false;
   }
}

// ----------------------------------------------------------------
// Integer VALIDATION

function validate_number_integer( intVal )
{

   intVal = getElementValue( intVal );
   if(isInteger(intVal)){
     return true;
   }else {
     return false;
   }
}



//----------------------------------------------------------------

// DATE VALIDATION

function validate_date( date )
{

   dateDataslot = getElement(date);
   if (!isNaN(dateDataslot.value)) {
       //alert("Long number date");
       return true;
   }
   else {

       dateDataslot.value = date2Long(dateDataslot.value);
       if (!isNaN(dateDataslot.value)) {
           //alert("Successfully converted to long date");
           return true;
       }
       else {
           //alert("Failed to convert to long date");
           return false;
       }
   }
   //if (!isNumber(date)) {
   //if ( dateCheck( date, "dmy" ) ||
   //     dateCheck( date, "mdy" ) ) {

   //   return true;
   //}
   //return false;
}

// OWNERSHIP FOR ITEMS BELOW IS AS ATTRIBUTED
//----------------------------------------------------------------

// <!-- Original:  Sandeep Tamhankar (stamhankar@hotmail.com) -->
// <!-- This script and many more are available free online at -->
// <!-- The JavaScript Source!! http://javascript.internet.com -->

// <!-- Begin
/* Here's the list of tokens we support:
   m (or M) : month number, one or two digits.
   mm (or MM) : month number, strictly two digits (i.e. April is 04).
   d (or D) : day number, one or two digits.
   dd (or DD) : day number, strictly two digits.
   y (or Y) : year, two or four digits.
   yy (or YY) : year, strictly two digits.
   yyyy (or YYYY) : year, strictly four digits.
   mon : abbreviated month name (April is apr, Apr, APR, etc.)
   Mon : abbreviated month name, mixed-case (i.e. April is Apr only).
   MON : abbreviated month name, all upper-case (i.e. April is APR only).
   mon_strict : abbreviated month name, all lower-case (i.e. April is apr 
         only).
   month : full month name (April is april, April, APRIL, etc.)
   Month : full month name, mixed-case (i.e. April only).
   MONTH: full month name, all upper-case (i.e. APRIL only).
   month_strict : full month name, all lower-case (i.e. april only).
   h (or H) : hour, one or two digits.
   hh (or HH) : hour, strictly two digits.
   min (or MIN): minutes, one or two digits.
   mins (or MINS) : minutes, strictly two digits.
   s (or S) : seconds, one or two digits.
   ss (or SS) : seconds, strictly two digits.
   ampm (or AMPM) : am/pm setting.  Valid values to match this token are
         am, pm, AM, PM, a.m., p.m., A.M., P.M.
*/

// Be careful with this pattern.  Longer tokens should be placed before shorter
// tokens to disambiguate them.  For example, parsing "mon_strict" should 
// result in one token "mon_strict" and not two tokens "mon" and a literal
// "_strict".

var tokPat=new RegExp("^month_strict|month|Month|MONTH|yyyy|YYYY|mins|MINS|mon_strict|ampm|AMPM|mon|Mon|MON|min|MIN|dd|DD|mm|MM|yy|YY|hh|HH|ss|SS|m|M|d|D|y|Y|h|H|s|S");
 
// lowerMonArr is used to map months to their numeric values.

var lowerMonArr={jan:1, feb:2, mar:3, apr:4, may:5, jun:6, jul:7, aug:8, sep:9, oct:10, nov:11, dec:12}

// monPatArr contains regular expressions used for matching abbreviated months
// in a date string.

var monPatArr=new Array();
monPatArr['mon_strict']=new RegExp(/jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec/);
monPatArr['Mon']=new RegExp(/Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec/);
monPatArr['MON']=new RegExp(/JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC/);
monPatArr['mon']=new RegExp("jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec",'i');

// monthPatArr contains regular expressions used for matching full months
// in a date string.

var monthPatArr=new Array();
monthPatArr['month']=new RegExp(/^january|february|march|april|may|june|july|august|september|october|november|december/i);
monthPatArr['Month']=new RegExp(/^January|February|March|April|May|June|July|August|September|October|November|December/);
monthPatArr['MONTH']=new RegExp(/^JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER/);
monthPatArr['month_strict']=new RegExp(/^january|february|march|april|may|june|july|august|september|october|november|december/);

// cutoffYear is the cut-off for assigning "19" or "20" as century.  Any
// two-digit year >= cutoffYear will get a century of "19", and everything
// else gets a century of "20".

var cutoffYear=50;

// FormatToken is a datatype we use for storing extracted tokens from the
// format string.

function FormatToken (token, type) {
this.token=token;
this.type=type;
}

function parseFormatString (formatStr) {
var tokArr=new Array;
var tokInd=0;
var strInd=0;
var foundTok=0;
    
while (strInd < formatStr.length) {
if (formatStr.charAt(strInd)=="%" &&
(matchArray=formatStr.substr(strInd+1).match(tokPat)) != null) {
strInd+=matchArray[0].length+1;
tokArr[tokInd++]=new FormatToken(matchArray[0],"symbolic");
} else {

// No token matched current position, so current character should 
// be saved as a required literal.

if (tokInd>0 && tokArr[tokInd-1].type=="literal") {

// Literal tokens can be combined.Just add to the last token.

tokArr[tokInd-1].token+=formatStr.charAt(strInd++);
}
else {
tokArr[tokInd++]=new FormatToken(formatStr.charAt(strInd++), "literal");
      }
   }
}
return tokArr;
}

function buildDate(dateStr,formatStr) {
// parse the format string first.
var tokArr=parseFormatString(formatStr);
var strInd=0;
var tokInd=0;
var intMonth;
var intDay;
var intYear;
var intHour;
var intMin;
var intSec;
var ampm="";
var strOffset;

// Create a date oject with the current date so that if the user only
// gives a month or day string, we can still return a valid date.

var curdate=new Date();
intMonth=curdate.getMonth()+1;
intDay=curdate.getDate();
intYear=curdate.getFullYear();

// Default time to midnight, so that if given just date info, we return
// a Date object for that date at midnight.

intHour=0;
intMin=0;
intSec=0;

// Walk across dateStr, matching the parsed formatStr until we find a 
// mismatch or succeed.

while (strInd < dateStr.length && tokInd < tokArr.length) {

// Start with the easy case of matching a literal.

if (tokArr[tokInd].type=="literal") {
if (dateStr.indexOf(tokArr[tokInd].token,strInd)==strInd) {

// The current position in the string does match the format 
// pattern.

strInd+=tokArr[tokInd++].token.length;
continue;
}
else {

// ACK! There was a mismatch; return error.

return "\"" + dateStr + "\" does not conform to the expected format: " + formatStr;
   }
}

// If we get here, we're matching to a symbolic token.
switch (tokArr[tokInd].token) {
case 'm':
case 'M':
case 'd':
case 'D':
case 'h':
case 'H':
case 'min':
case 'MIN':
case 's':
case 'S':

// Extract one or two characters from the date-time string and if 
// it's a number, save it as the month, day, hour, or minute, as
// appropriate.

curChar=dateStr.charAt(strInd);
nextChar=dateStr.charAt(strInd+1);
matchArr=dateStr.substr(strInd).match(/^\d{1,2}/);
if (matchArr==null) {

// First character isn't a number; there's a mismatch between
// the pattern and date string, so return error.

switch (tokArr[tokInd].token.toLowerCase()) {
case 'd': var unit="day"; break;
case 'm': var unit="month"; break;
case 'h': var unit="hour"; break;
case 'min': var unit="minute"; break;
case 's': var unit="second"; break;
}
return "Bad " + unit + " \"" + curChar + "\" or \"" + curChar +
nextChar + "\".";
}
strOffset=matchArr[0].length;
switch (tokArr[tokInd].token.toLowerCase()) {
case 'd': intDay=parseInt(matchArr[0],10); break;
case 'm': intMonth=parseInt(matchArr[0],10); break;
case 'h': intHour=parseInt(matchArr[0],10); break;
case 'min': intMin=parseInt(matchArr[0],10); break;
case 's': intSec=parseInt(matchArr[0],10); break;
}
break;
case 'mm':
case 'MM':
case 'dd':
case 'DD':
case 'hh':
case 'HH':
case 'mins':
case 'MINS':
case 'ss':
case 'SS':

// Extract two characters from the date string and if it's a 
// number, save it as the month, day, or hour, as appropriate.

strOffset=2;
matchArr=dateStr.substr(strInd).match(/^\d{2}/);
if (matchArr==null) {

// The two characters aren't a number; there's a mismatch 
// between the pattern and date string, so return an error
// message.

switch (tokArr[tokInd].token.toLowerCase()) {
case 'dd': var unit="day"; break;
case 'mm': var unit="month"; break;
case 'hh': var unit="hour"; break;
case 'mins': var unit="minute"; break;
case 'ss': var unit="second"; break;
}
return "Bad " + unit + " \"" + dateStr.substr(strInd,2) + 
"\".";
}
switch (tokArr[tokInd].token.toLowerCase()) {
case 'dd': intDay=parseInt(matchArr[0],10); break;
case 'mm': intMonth=parseInt(matchArr[0],10); break;
case 'hh': intHour=parseInt(matchArr[0],10); break;
case 'mins': intMin=parseInt(matchArr[0],10); break;
case 'ss': intSec=parseInt(matchArr[0],10); break;
}
break;
case 'y':
case 'Y':

// Extract two or four characters from the date string and if it's
// a number, save it as the year.Convert two-digit years to four
// digit years by assigning a century of '19' if the year is >= 
// cutoffYear, and '20' otherwise.

if (dateStr.substr(strInd,4).search(/\d{4}/) != -1) {

// Four digit year.

intYear=parseInt(dateStr.substr(strInd,4),10);
strOffset=4;
}
else {
if (dateStr.substr(strInd,2).search(/\d{2}/) != -1) {

// Two digit year.

intYear=parseInt(dateStr.substr(strInd,2),10);
if (intYear>=cutoffYear) {
intYear+=1900;
}
else {
intYear+=2000;
}
strOffset=2;
}
else {

// Bad year; return error.

return "Bad year \"" + dateStr.substr(strInd,2) + 
"\". Must be two or four digits.";
   }
}
break;
case 'yy':
case 'YY':

// Extract two characters from the date string and if it's a 
// number, save it as the year.Convert two-digit years to four 
// digit years by assigning a century of '19' if the year is >= 
// cutoffYear, and '20' otherwise.

if (dateStr.substr(strInd,2).search(/\d{2}/) != -1) {

// Two digit year.

intYear=parseInt(dateStr.substr(strInd,2),10);
if (intYear>=cutoffYear) {
intYear+=1900;
}
else {
intYear+=2000;
}
strOffset=2;
} else {
// Bad year; return error
return "Bad year \"" + dateStr.substr(strInd,2) + 
"\". Must be two digits.";
}
break;
case 'yyyy':
case 'YYYY':

// Extract four characters from the date string and if it's a 
// number, save it as the year.

if (dateStr.substr(strInd,4).search(/\d{4}/) != -1) {

// Four digit year.

intYear=parseInt(dateStr.substr(strInd,4),10);
strOffset=4;
}
else {

// Bad year; return error.

return "Bad year \"" + dateStr.substr(strInd,4) + 
"\". Must be four digits.";
}
break;
case 'mon':
case 'Mon':
case 'MON':
case 'mon_strict':

// Extract three characters from dateStr and parse them as 
// lower-case, mixed-case, or upper-case abbreviated months,
// as appropriate.

monPat=monPatArr[tokArr[tokInd].token];
if (dateStr.substr(strInd,3).search(monPat) != -1) {
intMonth=lowerMonArr[dateStr.substr(strInd,3).toLowerCase()];
}
else {

// Bad month, return error.

switch (tokArr[tokInd].token) {
case 'mon_strict': caseStat="lower-case"; break;
case 'Mon': caseStat="mixed-case"; break;
case 'MON': caseStat="upper-case"; break;
case 'mon': caseStat="between Jan and Dec"; break;
}
return "Bad month \"" + dateStr.substr(strInd,3) + 
"\". Must be " + caseStat + ".";
}
strOffset=3;
break;
case 'month':
case 'Month':
case 'MONTH':
case 'month_strict':

// Extract a full month name at strInd from dateStr if possible.

monPat=monthPatArr[tokArr[tokInd].token];
matchArray=dateStr.substr(strInd).match(monPat);
if (matchArray==null) {

// Bad month, return error.

return "Can't find a month beginning at \"" +
dateStr.substr(strInd) + "\".";
}

// It's a good month.

intMonth=lowerMonArr[matchArray[0].substr(0,3).toLowerCase()];
strOffset=matchArray[0].length;
break;
case 'ampm':
case 'AMPM':
matchArr=dateStr.substr(strInd).match(/^(am|pm|AM|PM|a\.m\.|p\.m\.|A\.M\.|P\.M\.)/);
if (matchArr==null) {

// There's no am/pm in the string.Return error msg.

return "Missing am/pm designation.";
}

// Store am/pm value for later (as just am or pm, to make things
// easier later).

if (matchArr[0].substr(0,1).toLowerCase() == "a") {

// This is am.

ampm = "am";
}
else {
ampm = "pm";
}
strOffset = matchArr[0].length;
break;
}
strInd += strOffset;
tokInd++;
}
if (tokInd != tokArr.length || strInd != dateStr.length) {

/* We got through the whole date string or format string, but there's 
 more data in the other, so there's a mismatch. */

return "\"" + dateStr + "\" is either missing desired information or has more information than the expected format: " + formatStr;
}

// Make sure all components are in the right ranges.

if (intMonth < 1 || intMonth > 12) {
return "Month must be between 1 and 12.";
}
if (intDay < 1 || intDay > 31) {
return "Day must be between 1 and 31.";
}

// Make sure user doesn't put 31 for a month that only has 30 days

if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && intDay == 31) {
return "Month "+intMonth+" doesn't have 31 days!";
}

// Check for February date validity (including leap years) 

if (intMonth == 2) {

// figure out if "year" is a leap year; don't forget that
// century years are only leap years if divisible by 400

var isleap=(intYear%4==0 && (intYear%100!=0 || intYear%400==0));
if (intDay > 29 || (intDay == 29 && !isleap)) {
return "February " + intYear + " doesn't have " + intDay + 
" days!";
   }
}

// Check that if am/pm is not provided, hours are between 0 and 23.

if (ampm == "") {
if (intHour < 0 || intHour > 23) {
return "Hour must be between 0 and 23 for military time.";
   }
}
else {

// non-military time, so make sure it's between 1 and 12.

if (intHour < 1|| intHour > 12) {
return "Hour must be between 1 and 12 for standard time.";
   }
}

// If user specified amor pm, convert intHour to military.

if (ampm=="am" && intHour==12) {
intHour=0;
}
if (ampm=="pm" && intHour < 12) {
intHour += 12;
}
if (intMin < 0 || intMin > 59) {
return "Minute must be between 0 and 59.";
}
if (intSec < 0 || intSec > 59) {
return "Second must be between 0 and 59.";
}
return new Date(intYear,intMonth-1,intDay,intHour,intMin,intSec);
}
function dateCheck(dateStr,formatStr) {
var myObj = buildDate(dateStr,formatStr);
if (typeof myObj == "object") {

// We got a Date object, so good.

return true;
}
else {

// We got an error string.

alert(myObj);
return false;
   }
}

var dataslotObj;
var l1;
var l2;

function setDataslot(obj, precision, scale) {

     dataslotObj = obj;

     if (scale > 0)
          l1 = precision - scale - 1;
     else
          l1 = precision;

     l2 = scale;
     decimalFields[obj.name] = [obj, (precision + scale), scale];
}

function validateDecimal(obj, wl, dl) {

     var fmt = /^(\d{1,7}.\d{3})$/;
     //alert(obj.value + '\r\n' + fmt);
     if (fmt.test(obj.value))
		 alert(getLocalizedString('ok'));
     else
		  alert(getLocalizedString('BadFormat'));

}

function validateDecimalField(obj, label) {

	if (obj.value == "" || !isFloat(obj.value) || !isNumber(obj.value)){ 
		getAjaxLocalizedString('EnterValidDecimal',[label]);  
			return false;
	}	

	if(isNaN(parseFloat(obj.value))){
	
		getAjaxLocalizedString('NotAValidDecimal',[String(obj.value)]);  
		return false;
	} else {
    	if(obj.name in decimalFields) {
    		var field = decimalFields[obj.name];
    		var expectedPrecision = field[1];
    		var expectedScale = field[2];
   			var fieldValue = new String(field[0].value);
		

   			// Digits before the decimal point
   			if(fieldValue.indexOf(".") != -1) {
				if(fieldValue.substring(0, fieldValue.indexOf(".")).length > (expectedPrecision - expectedScale)) {
					getAjaxLocalizedString('MaximumDigitsAllowedForDecimalPoint',[String((expectedPrecision - expectedScale)),label]);  
					return false;

				}       			
   			} else {
				if(fieldValue.length > (expectedPrecision - expectedScale)) {
					getAjaxLocalizedString('MaximumDigitsAllowedForDecimalPoint',[String((expectedPrecision - expectedScale)),label]);    
					return false;

				}        			

   			}
			
   			// Digits after the decimal point
   			if(fieldValue.substring((fieldValue.indexOf(".") + 1), fieldValue.length).length > expectedScale) {
   				getAjaxLocalizedString('MaximumDigitsAllowedForDecimalPoint',[String(expectedScale),label]); 
				return false;
   			
   			}
    	}
		return true;
    }

}

function editDecimal(obj, precision, scale) {

     if(obj.name == undefined)
           obj = document.getElementById(obj);
     setDataslot(obj, precision, scale);
     url = getRelativePath()+'bpmportal/common/pop_decimal_dataslot.jsp?elementID=' + obj.name + '&pms=' + precision + '&scale=' + scale + '&value=' + obj.value;
     window.open(url,'EditDecimalData','dependent=yes,scrollbars=yes,resizable=yes,width=690,height=174,screenX=200,screenY=300,titlebar=no');
}

function getRelativePath() {
	var stringUrl = "'"+self.location+"'";
	if (stringUrl.indexOf('/webapp/') >= 0)
		return "../../../../";
	else if (stringUrl.indexOf('/ebmsapps/') >= 0)
	    return "../../../";
	else if (stringUrl.indexOf('/bpmportal/') >= 0 || stringUrl.indexOf('/BizSolo/') >= 0)
		return "../../";
}

function setDecimalValue(v1, v2) {

     if (isNaN(v1) || isNaN(v2)) {

         //alert('Number required!');
         return false;
     }
     else {
         if (v1 != '' || v2 != '') {

         dataslotObj.value = v1 + '.' + v2;
         }
         else {

              dataslotObj.value = '0.0';
         }
         return true;
     }
}

function xmlviewer (xmlid, sessionid) {

     if (xmlid == '-1') {
		   alert(getLocalizedString('NoXMLAttach'));
           return ;
     }

     url = getContextPath()+'/xml/xmlviewer';
     if (sessionid != '') {
         url += ';jsessionid=';
         url += sessionid;
     }
     url += '?xmlid=';
     url += xmlid;
     top.newWin = window.open(url,'view_xml','dependent=yes,width=640,height=480,screenX=200,screenY=300,titlebar=no,scrollbars=yes');
}

function cancel() { 
    // Here it is assumed that the task detail page contains 'bizsite_assigneeName' field   
    if(getElement('bizsite_assigneeName') != undefined) {
     self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.task");
    } else if(getElement('bizsite_pagetype') != undefined) {
    	var pagetype = getElement('bizsite_pagetype');
    	if(pagetype.value == "start") {
    	  self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.app.list");
    	} else {
    	  self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.task");
    	}
    } else {
     	self.parent.location.replace(getContextPath()+"/bpmportal/myhome/bizsite.app.list");     
     }

     return false;
}


function setUserControl(ctrl) {

    //alert(ctrl);
    workingCtrl = ctrl;
    return ;
}

function setChoosedUserName(userName) {

    if (workingCtrl) {
    
        workingCtrl.value = userName;
    }
    return ;
}

function searchUser() {

    window.open(getContextPath()+'/bpmportal/administration/UserManagementServlet?action=searchUser','SearchUser','scrollbars=yes,resizable=yes,width=900,height=600');
    return ;
}

function searchReassignUser() {

    window.open(getContextPath()+'/bpmportal/administration/UserManagementServlet?action=searchUser&queryUserByAlphabet=*&isReassign=true','SearchUser','scrollbars=yes,resizable=yes,width=900,height=600');
    return ;
}


function resetForm() {

   document.forms[0].reset();

   if (!fields) {
   
       return ;
   }
    
   for( i = 0; i < fields.length; i++ ) {
  
      var name = fields[ i ][ 0 ];
      var type = fields[ i ][ 1 ].toLowerCase();
      
      // check whether this field is mandatory
      if(type == 'date' ) {
           
         var dateCtrl = getElement(name);
         if (!isNaN(dateCtrl.value)) {
           
            long2Date(dateCtrl);
         }
      }
   }
}

function parseValidationType(type){
	var val_array = type.split("_");
	return val_array;
}

// function validateEmail
// validating Email address format 
function validateEmail(value,label) {
  // {0} is the field label:
  var NO_SPACE_ERROR = getLocalizedString('EmailShouldNotHaveSpace');
  var EMAIL_ALERT = getLocalizedString('InvalidEmailAddress');
	
  if (value == ""){ 
          alert(getLocalizedStringWithArgs('EnterValidEmail',[label]));
          return false;
  }

  value += ""; // Force conversion to string
  var indexOfAtSign = value.indexOf("@");
  var indexOfPeriod = value.lastIndexOf('.');
  var indexOfFirst_last = value.indexOf(".");
  
  if (value.indexOf(" ") >= 0) {
       // Ensure there are no spaces:
	   alert(getLocalizedString('InvalidEmailFormat'));
       return false;
  } else if (indexOfAtSign < 1) {
	   alert(getLocalizedString('InvalidEmailFormat'));
       return false;
  } else if (value.indexOf("@", indexOfAtSign+1) >= 0) {
       alert(EMAIL_ALERT.replace("{0}", label));
       return false;
  } else if ((value.length - indexOfPeriod) < 3) {
           alert(EMAIL_ALERT.replace("{0}", value));
	   return false;
  } else {
    return true;
  }
}

// function validateSSN
// validating Social Security Number
function validateSSN(value,label) {
        
	if (value == ""){ 
	   getAjaxLocalizedString('EnterValidSecurityNumber',[label]); 	  
	  return false;
	}
	  
	var matchArr = value.match(/^(\d{3})-?\d{2}-?\d{4}$/);
        var numDashes = value.split('-').length - 1;
        if (matchArr == null || numDashes == 1) {
          getAjaxLocalizedString('InvalidSecurityNumber',[String(value)]); 	  
	  return false;
        }else if (parseInt(matchArr[1],10)==0) {
		  getAjaxLocalizedString('SecurityNumberNotValid',[String(value)]); 	  
          return false;    
       }else {
            return true;
       }
}

//  function valdatePhoneNumber 
//  validating Phone Number 
function validatePhoneNumber(value,label){
	var i;
	var ch;
	
	if (value == ""){ 
	  getAjaxLocalizedString('EnterPhoneNo',[label]); 	  	  
          return false;
        }
	
	if ((value.length != 0) && (value.length < 10)){
		getAjaxLocalizedString('EnterValidPhoneNoFormat',[label]); 	  
		return false;
	}
	if (value.length > 12){
		getAjaxLocalizedString('EnterValidPhoneNoFormat',[label]); 
		return false;
	}
	
	for (i = 0; i < value.length; i++){
	     var ch = value.charAt(i);
	        if(((i == 3) || (i == 7)) && ((ch != '-') && (ch !=' '))){ 
				getAjaxLocalizedString('EnterDigitsOnlyIn',[ch]); 
			return false;
			
	        }else if(((i != 3) && (i != 7)) &&  !isDigit(ch)){
			getAjaxLocalizedString('EnterDigitsOnlyIn',[ch]); 
			return false;
		}
	}
       
	if (value.length == 12){
		
		if(value.charAt(0) < '2' ){
			getAjaxLocalizedString('CheckFirstDigit',[label]);
          	        return false;
		}else if(value.substring(0,3) == "900" ){
			getAjaxLocalizedString('AreaCodeCanNotBe',[label,"900"]);
			return false;
		}else if(value.substring(0,3) == "911" ){
			getAjaxLocalizedString('AreaCodeCanNotBe',[label,"911"]);
			return false;
		}else{
		
        	return true;
		}
        }
	return false;
	
}

function validatePostalCode(value,label){
  
  if (value == ""){ 
	  getAjaxLocalizedString('EnterPostalCode',[label]);
          return false;
   }
  
  if ((value.length >= 5) || (value.length == 5)) {
	  
	  var zipcode = parseInt(value.substring(0,5));
	  var zipcodestr = ''+zipcode;
	 
	  if( isNaN(parseInt(value.substring(0,5))) || (zipcodestr.length != 5)){ 
		alert(getLocalizedString('InvalidPostalCode'));
		return false; 
	  }else if(value.length > 5){  
		  if(isNaN(parseInt(value.indexOf("-"))) || (value.indexOf("-") != 5)){
			alert(getLocalizedString('PostalCodeShouldBeIn'));
		    return false;
		  }else if(!isNaN(parseInt(value.indexOf("-"))) && (value.indexOf("-") == 5)){
		    var postalcode = value.substring(value.indexOf("-")+1,value.length);
		    var lastfour = parseInt(postalcode);
		    lastfour = ''+lastfour;
		    
		    if(lastfour.length != 4){
			   alert(getLocalizedString('PostalCodeShouldBeIn'));
		       return false;
		    }else if(isNaN(postalcode)){
			  alert(getLocalizedString('PostalCodeLastDigitShouldBeNumber'));
		      return false; 
		    }else{
			 return true;     
	            }     
		  }
	  }
  }

  if ((value.length != 5) && (value.length != 10)) {
	getAjaxLocalizedString('InvalidZipCode',[String(value)]);
    return false;
  } else {
    return true;
  }
}

//  function validateCreditCard
//  valdating credit card number
//  types are 'Visa', 'MasterCard', 'Amx'
function validateCreditCard(value,type,label){
   
    if((type =='Visa') || (type =='MasterCard'))
	  return validateCreditCardDefault(value,label);
    else if (type=='Amx')
	  return validateCreditCardAmx(value,label);
    else
	  return validateCreditCardDefault(value,label);

}
//  function validateCreditCard
//  valdating credit card number
function validateCreditCardDefault(value,label){
	if (value == ""){ 
	  getAjaxLocalizedString('EnterCreditCardNo',[label]);
          return false;
        }
	
	if(value.length != 19){
		alert(getLocalizedString('CreditCardNoFormat'));
		return false; 
	}
	if( !isInteger(value.substring(0,4)) || !isInteger(value.substring(5,9)) || !isInteger(value.substring(10,14)) || !isInteger(value.substring(15,19))){
			alert(getLocalizedString('InvalidCreditCardNo'));
		return false;
	}	
	if( isInteger(value.substring(0,4)) && isInteger(value.substring(5,9)) && isInteger(value.substring(10,14)) && isInteger(value.substring(15,19))){
	       return true;	
	}
	return false;
	
}	

//  function validateCreditCard
//  valdating credit card number for American Express type
function validateCreditCardAmx(value,label){
	if (value == ""){ 
	  getAjaxLocalizedString('EnterCreditCardNo',[label]);
          return false;
        }
	
	if(value.length != 18){
		alert(getLocalizedString('CreditCardNoFormat'));
		return false; 
	}
	if( !isInteger(value.substring(0,4)) || !isInteger(value.substring(5,9)) || !isInteger(value.substring(10,14)) || !isInteger(value.substring(15,18))){
			alert(getLocalizedString('InvalidCreditCardNo'));
		return false;
	}	
	if( isInteger(value.substring(0,4)) && isInteger(value.substring(5,9)) && isInteger(value.substring(10,14)) && isInteger(value.substring(15,18))){
	       return true;	
	}
	return false;
	
}	

//  function validateURL
//  valdating url
function validateURL(value,label){
	
	if (value == ""){ 
	  getAjaxLocalizedString('EnterURL',[label]);
          return false;
        }
	
      	var http = value.substring(0,7);
	var https = value.substring(0,8);
	var ftp = value.substring(0,6);
	
	if((http == "http://") || (ftp == "ftp://") || (https == "https://"))
		return true;
	else{
		getAjaxLocalizedString('Invalid_URL',[value]);
		return false;
	}	
}



//  function validateCurrency
//  valdating currency
function validateCurrency(value,label) {
     if (value == ""){ 
	  getAjaxLocalizedString('EnterValidCurrency',[label]);
          return false;
      }
	
     var valuedec = parseDecimal(value);
     if (isNaN(valuedec)) {
			 getAjaxLocalizedString('NotValidCurrency',[String(value)]);
             return false;
     }else{
     	var decimalPlace = value.indexOf(".");
	if( (decimalPlace == -1) ){
		 alert(getLocalizedString('InvalidCurrencyFormat'));
	     return false;
        }
	else	
	return true;
	}
    }
    
//  function validateDecimal
//  valdating decimal    
function validateDecimal(value,label){
	if (value == ""){ 
	  getAjaxLocalizedString('EnterValidDecimalValue',[label]);
          return false;
        }	
	
	var decimal = parseFloat(value);
	decimal = ''+decimal;
		
	if(decimal.length != value.length){
		 getAjaxLocalizedString('NotAValidDecimal',[String(value)]);
	         return false;
        }else if(isNaN(parseFloat(value))){
		getAjaxLocalizedString('NotAValidDecimal',[String(value)]);
		return false;
        }else{
		return true;
        }

}

//  function validateNumber
//  valdating Number 
function validateNumber(value,label){
	if (value == ""){ 
	  getAjaxLocalizedString('EnterValidNumber',[label]);
          return false;
        }
	if(isNumber(value)){
		 return true;
	}else {
	 getAjaxLocalizedString('InvalidNumberEnterValidNumber',[label]);
	 return false;
        }	 
}


//  function validateNumberRange
//  valdating Number in the range min < number < max    
function validateNumberRange(value,min,max,label){
	if (value == ""){ 
	  getAjaxLocalizedString('EnterValidDecimal',[label]);
          return false;
        }
	if(isInteger(value)){
		var number = parseInt(value);
		if( min <= number && number <= max){
			return true;
		}else{
			getAjaxLocalizedString('ValueShouldBeInRange',[String(value),String(min),String(max),label]);
			
			return false;
		}
	}else {
		getAjaxLocalizedString('InvalidNumberEnterValidNumber',[label]);
		return false;	
	}
}

//  function validateXmlField
//  valdating the content of xml dataslot    
function validateXmlContent(name,content,type){
	var valid = true;
	if(type == "applet")
	         valid = validateXmlIE(name,content)
	else if(type == "embeds") 
	        valid = validateXmlMozilla(name,content)
	return valid;
}

function validateXmlIE(name,content)
{
	   var valid = true;
	   var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
           xmlDoc.async = "false";
	   xmlDoc.validateOnParse = false;
	   xmlDoc.resolveExternals = false;
	   xmlDoc.loadXML(content);
	   if(xmlDoc.parseError.errorCode != 0)  
			 valid = false;
	   return valid;
}

function validateXmlMozilla(name,content)
{
	   var valid = true;
	   var parser = new DOMParser();
	   var xmlDoc = parser.parseFromString(content,"text/xml");
	   var roottag = xmlDoc.documentElement;
	   if (roottag.tagName == "parsererror")
	            valid = false;
	   return valid;
}

function parseDecimal(value) {
  return parseFloat(stripNonDecimal(value));
}

function stripNonDecimal(value) {
  var result = "";
  var c;              // The current character.

  value += ""; // Force conversion to string

  for (var i=0; i<value.length; i++) {
    c = value.charAt(i);
    if (((c >= "0") && (c <= "9")) || (c == ".")) {
      result += c;
    }
  }
  return result;
}

function isNumber(number){
  	var ch;
	var isNumber = true;
	var valid_chars = "-0123456789."; 
	
	for(var i=0; i<number.length && isNumber == true ;i++) 
	{
		ch = number.charAt(i);
		if(valid_chars.indexOf(ch) == -1)
			 isNumber = false;
	}
	if(number.indexOf(".") != number.lastIndexOf("."))
		 isNumber = false;
	
	if(number.indexOf("-") != -1)
        {
            if((number.indexOf("-") != 0) || (number.indexOf("-") != number.lastIndexOf("-")))
                 isNumber = false;
        }
	return isNumber;
}

function isInteger(integer){
	var i;
	for(var i = 0; i <  integer.length; i++){
		var ch = integer.charAt(i);
	        if(!isDigit(ch) && !(ch == "-")) return false;
        }
        return true;
}

function isDigit(c){
	return ((c >= "0") && (c <= "9"));	
}

function isFloat(aValue){
	  var c;              // The current character.
	  var decimalPointCount = 0;	

	  for (var i=0; i<aValue.length; i++) {
	    c = aValue.charAt(i);
	    if (!(((c >= "0") && (c <= "9")) || (c == ".") || (c == "-"))) {
	      return false ;
	    } else if(c == ".") {
	    	decimalPointCount = decimalPointCount + 1;
	    	if(decimalPointCount > 1) {
	    		return false;
	    	}
	    }
	  }
	  return true;
}

function validateSize(nm,val, sz) {
	if(val.length > parseInt(sz)) {
		getAjaxLocalizedString('CharacterLimit',[nm]);
		return false;
		
	}
	return true;
}

function addFieldLabelSize( name,type, isMandatory,label,size)
{
   fields[ fields.length ] = new Array( name, type, isMandatory,label,size );

   /*var imgName = "noclip.gif";
   if( isMandatory ) {

      imgName = "icon_overdue.gif";
   }
   document.write( "<img name='" + name + "_imgValid' src='" +  bizsiteroot + "/images/" + imgName + "'><a name='" + name + "_locator'>" );
   */

   // added by Hui
   document.write("<a name='" + name + "_locator'>" );
   // call input helper if available
   if( eval( 'window.fieldHelper_' + type + ' != null' ) ) {
      
      eval( 'fieldHelper_' + type + '( name );' );
   }
}


function addFiid(arg) {
   try {
     var fiidv = document.myTaskForm.fiid.value;
     if (arg.indexOf('?') > 0)
        arg += '&';
     else
       arg += '?';
     arg += 'fiid='+encodeURIComponent(fiidv);
  } catch(e) {
  }

  return arg;
}

function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+addFiid(args[i+1])+"'");
}

function goToCreateCollaboration()
{
    var url = appendTaskId('/bpmportal/myhome/create_collaboration.jsp');
    MM_goToURL('parent',getContextPath()+url);

}

function goToViewCollaboration()
{
    var url = appendTaskId('/bpmportal/myhome/view_collaboration.jsp');
    MM_goToURL('parent',getContextPath()+url);
}

function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

function goToEmail()
{
	MM_openBrWindow(getContextPath()+'/bpmportal/common/pop_task_det_email.jsp','emailuser','scrollbars=yes,resizable=yes,width=600,height=350');
}

function goToChat()
{
	MM_openBrWindow(getContextPath()+'/bpmportal/common/pop_task_det_chat.jsp','chatuser','scrollbars=yes,resizable=yes,width=600,height=350');
}


function goToNotes()
{
	MM_openBrWindow(getContextPath()+'/bpmportal/myhome/processnotes.jsp','Notes','scrollbars=yes,resizable=yes,width=900,height=600');
}

