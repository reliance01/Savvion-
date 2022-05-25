/*  Copyright Mihai Bazon, 2002, 2003  |  http://dynarch.com/mishoo/
 * ---------------------------------------------------------------------------
 *
 * The DHTML Calendar
 *
 * Details and latest version at:
 * http://dynarch.com/mishoo/calendar.epl
 *
 * This script is distributed under the GNU Lesser General Public License.
 * Read the entire license text here: http://www.gnu.org/licenses/lgpl.html
 *
 * This file defines helper functions for setting up the calendar.  They are
 * intended to help non-programmers get a working calendar on their site
 * quickly.  This script should not be seen as part of the calendar.  It just
 * shows you what one can do with the calendar, while in the same time
 * providing a quick and simple method for setting it up.  If you need
 * exhaustive customization of the calendar creation process feel free to
 * modify this code to suit your needs (this is recommended and much better
 * than modifying calendar.js itself).
 */

// $Id: calendar-setup.js,v 1.25 2005/03/07 09:51:33 mishoo Exp $

/**
 *  This function "patches" an input field (or other element) to use a calendar
 *  widget for date selection.
 *
 *  The "params" is a single object that can have the following properties:
 *
 *    prop. name   | description
 *  -------------------------------------------------------------------------------------------------
 *   inputField    | the ID of an input field to store the date
 *   displayArea   | the ID of a DIV or other element to show the date
 *   button        | ID of a button or other element that will trigger the calendar
 *   eventName     | event that will trigger the calendar, without the "on" prefix (default: "click")
 *   ifFormat      | date format that will be stored in the input field
 *   daFormat      | the date format that will be used to display the date in displayArea
 *   singleClick   | (true/false) wether the calendar is in single click mode or not (default: true)
 *   firstDay      | numeric: 0 to 6.  "0" means display Sunday first, "1" means display Monday first, etc.
 *   align         | alignment (default: "Br"); if you don't know what's this see the calendar documentation
 *   range         | array with 2 elements.  Default: [1900, 2999] -- the range of years available
 *   weekNumbers   | (true/false) if it's true (default) the calendar will display week numbers
 *   flat          | null or element ID; if not null the calendar will be a flat calendar having the parent with the given ID
 *   flatCallback  | function that receives a JS Date object and returns an URL to point the browser to (for flat calendar)
 *   disableFunc   | function that receives a JS Date object and should return true if that date has to be disabled in the calendar
 *   onSelect      | function that gets called when a date is selected.  You don't _have_ to supply this (the default is generally okay)
 *   onClose       | function that gets called when the calendar is closed.  [default]
 *   onUpdate      | function that gets called after the date is updated in the input field.  Receives a reference to the calendar.
 *   date          | the date that the calendar will be initially displayed to
 *   showsTime     | default: false; if true the calendar will include a time selector
 *   timeFormat    | the time format; can be "12" or "24", default is "12"
 *   electric      | if true (default) then given fields/date areas are updated for each move; otherwise they're updated only on close
 *   step          | configures the step of the years in drop-down boxes; default: 2
 *   position      | configures the calendar absolute position; default: null
 *   cache         | if "true" (but default: "false") it will reuse the same calendar object, where possible
 *   showOthers    | if "true" (but default: "false") it will show days from other months too
 *
 *  None of them is required, they all have default values.  However, if you
 *  pass none of "inputField", "displayArea" or "button" you'll get a warning
 *  saying "nothing to setup".
 */
Calendar.setup = function (params) {
    function param_default(pname, def) { if (typeof params[pname] == "undefined") { params[pname] = def; } };

    param_default("inputField",     null);
    param_default("displayArea",    null);
    param_default("button",         null);
    param_default("eventName",      "click");
    param_default("ifFormat",       "%Y/%m/%d");
    param_default("daFormat",       "%Y/%m/%d");
    param_default("singleClick",    true);
    param_default("disableFunc",    null);
    param_default("dateStatusFunc", params["disableFunc"]); // takes precedence if both are defined
    param_default("dateText",       null);
    param_default("firstDay",       null);
    param_default("align",          "Br");
    param_default("range",          [1900, 2999]);
    param_default("weekNumbers",    true);
    param_default("flat",           null);
    param_default("flatCallback",   null);
    param_default("onSelect",       null);
    param_default("onClose",        null);
    param_default("onUpdate",       null);
    param_default("date",           null);
    param_default("showsTime",      false);
    param_default("timeFormat",     "24");
    param_default("electric",       true);
    param_default("step",           2);
    param_default("position",       null);
    param_default("cache",          false);
    param_default("showOthers",     false);
    param_default("multiple",       null);

    var tmp = ["inputField", "displayArea", "button"];
    for (var i in tmp) {
        if (typeof params[tmp[i]] == "string") {
            params[tmp[i]] = document.getElementById(params[tmp[i]]);
        }
    }
    if (!(params.flat || params.multiple || params.inputField || params.displayArea || params.button)) {
        alert("Calendar.setup:\n  Nothing to setup (no fields found).  Please check your code");
        return false;
    }

    function onSelect(cal) {
        var p = cal.params;
        var update = (cal.dateClicked || p.electric);
        if (update && p.inputField) {
            p.inputField.value = cal.date.print(p.ifFormat);
            if (typeof p.inputField.onchange == "function")
                p.inputField.onchange();
        }
        if (update && p.displayArea)
            p.displayArea.innerHTML = cal.date.print(p.daFormat);
        if (update && typeof p.onUpdate == "function")
            p.onUpdate(cal);
        if (update && p.flat) {
            if (typeof p.flatCallback == "function")
                p.flatCallback(cal);
        }
        if (update && p.singleClick && cal.dateClicked)
            cal.callCloseHandler();
    };
    
    function onClose(cal) {
        //Show the div for document applets
        var elems = document.getElementsByName('document_div_id');
        if(elems != undefined && elems.length > 0) {
            for(var i=0;i < elems.length;i++) {
                elems[i].style.visibility = 'visible';
            }
        }
        cal.hide();
    };

    var triggerEl = params.button || params.displayArea || params.inputField;
    var dateEl = params.inputField || params.displayArea;
    var dateFmt = params.inputField ? params.ifFormat : params.daFormat;
    var mustCreate = false;
    var cal = window.calendar;
        if (dateEl)
            params.date = Date.parseDate(dateEl.value || dateEl.innerHTML, dateFmt);
        if (!(cal && params.cache)) {
            window.calendar = cal = new Calendar(params.firstDay,
                                 params.date,
                                 params.onSelect || onSelect,
                                 params.onClose || onClose);
            cal.showsTime = params.showsTime;
            cal.time24 = (params.timeFormat == "24");
            cal.weekNumbers = params.weekNumbers;
            mustCreate = true;
        } else {
            if (params.date)
                cal.setDate(params.date);
            cal.hide();
        }
        if (params.multiple) {
            cal.multiple = {};
            for (var i = params.multiple.length; --i >= 0;) {
                var d = params.multiple[i];
                var ds = d.print("%Y%m%d");
                cal.multiple[ds] = d;
            }
        }
        cal.showsOtherMonths = params.showOthers;
        cal.yearStep = params.step;
        cal.setRange(params.range[0], params.range[1]);
        cal.params = params;
        cal.setDateStatusHandler(params.dateStatusFunc);
        cal.getDateText = params.dateText;
        cal.setDateFormat(dateFmt);
        if (mustCreate)
            cal.create();
        cal.refresh();
        if (!params.position)
            cal.showAtElement(params.button || params.displayArea || params.inputField, params.align);
        else
            cal.showAt(params.position[0], params.position[1]);
        

    return cal;
};

sbm.showCalendar = function(field,pattern)
{
    //This condition prevents display of calendar popup if date dataslot is disabled
        if(document.getElementById(field).disabled){
                return false;
    }
    //Hide the div for document applets
    var elems = document.getElementsByName('document_div_id');
    if(elems != undefined && elems.length > 0) {
        for(var i=0;i < elems.length;i++) {
            elems[i].style.visibility = 'hidden';
        }
    } 
    var enableTime = false;
    var timeFormat = 24;
    if((pattern.indexOf("%k") != -1) || (pattern.indexOf("%H") != -1) || (pattern.indexOf("%I") != -1))
        enableTime = true;
    if(pattern.indexOf("%p") != -1)
        timeFormat = 12; 
    //Calendar.setup({inputField:field,ifFormat:"%B, %d %Y %p",showsTime:enableTime});
    Calendar.setup({inputField:field,ifFormat:pattern,showsTime:enableTime,timeFormat:timeFormat}); 
}

sbm.date2Long = function(value,pattern)
{
    /* changed to parseDate1 to avoid parseDate method conflict with Ext-JS*/
    var longDate = Date.parseDate1(value, pattern);
        if(longDate == null)
        return 0;
    return longDate.getTime();
}

sbm.getLabelFor = function(form,name)
{
    var label = name;
    if(typeof form.getElementsByTagName == 'undefined' ) return label;
    var labels = form.getElementsByTagName("label");
    
    for(var i=0;i<labels.length;i++)
    {
        if(labels[i].htmlFor == name)
        {
            label = labels[i].innerHTML;
            break;
        }
    }
    return label;
}


//--------------------------------------------------------------------------------------------------------------------//
//       Start of javascript Toolbox
//       This functionality by Matt Kruse is used for custom date validation date
//--------------------------------------------------------------------------------------------------------------------//
/*===================================================================
 Author: Matt Kruse
 
 View documentation, examples, and source code at:
     http://www.JavascriptToolbox.com/

 NOTICE: You may use this code for any purpose, commercial or
 private, without any further permission from the author. You may
 remove this notice from your final code if you wish, however it is
 appreciated by the author if at least the web site address is kept.

 This code may NOT be distributed for download from script sites, 
 open source CDs or sites, or any other distribution method. If you
 wish you share this code with others, please direct them to the 
 web site above.
 
 Pleae do not link directly to the .js files on the server above. Copy
 the files to your own server for use with your site or webapp.
 ===================================================================*/
/*
Date functions

These functions are used to parse, format, and manipulate Date objects.
See documentation and examples at http://www.JavascriptToolbox.com/lib/date/

*/
Date.$VERSION = 1.02;

// Utility function to append a 0 to single-digit numbers
Date.LZ = function(x) {return(x<0||x>9?"":"0")+x};
// Full month names. Change this for local month names
Date.monthNames = new Array('January','February','March','April','May','June','July','August','September','October','November','December');
// Month abbreviations. Change this for local month names
Date.monthAbbreviations = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
// Full day names. Change this for local month names
Date.dayNames = new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday');
// Day abbreviations. Change this for local month names
Date.dayAbbreviations = new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
// Used for parsing ambiguous dates like 1/2/2000 - default to preferring 'American' format meaning Jan 2.
// Set to false to prefer 'European' format meaning Feb 1
Date.preferAmericanFormat = true;

// If the getFullYear() method is not defined, create it
if (!Date.prototype.getFullYear) { 
    Date.prototype.getFullYear = function() { var yy=this.getYear(); return (yy<1900?yy+1900:yy); } 
} 

// Parse a string and convert it to a Date object.
// If no format is passed, try a list of common formats.
// If string cannot be parsed, return null.
// Avoids regular expressions to be more portable.
Date.parseString = function(val, format) {
    // If no format is specified, try a few common formats
    if (typeof(format)=="undefined" || format==null || format=="") {
        var generalFormats=new Array('y-M-d','MMM d, y','MMM d,y','y-MMM-d','d-MMM-y','MMM d','MMM-d','d-MMM');
        var monthFirst=new Array('M/d/y','M-d-y','M.d.y','M/d','M-d');
        var dateFirst =new Array('d/M/y','d-M-y','d.M.y','d/M','d-M');
        var checkList=new Array(generalFormats,Date.preferAmericanFormat?monthFirst:dateFirst,Date.preferAmericanFormat?dateFirst:monthFirst);
        for (var i=0; i<checkList.length; i++) {
            var l=checkList[i];
            for (var j=0; j<l.length; j++) {
                var d=Date.parseString(val,l[j]);
                if (d!=null) { 
                    return d; 
                }
            }
        }
        return null;
    }

    this.isInteger = function(val) {
        for (var i=0; i < val.length; i++) {
            if ("1234567890".indexOf(val.charAt(i))==-1) { 
                return false; 
            }
        }
        return true;
    };
    this.getInt = function(str,i,minlength,maxlength) {
        for (var x=maxlength; x>=minlength; x--) {
            var token=str.substring(i,i+x);
            if (token.length < minlength) { 
                return null; 
            }
            if (this.isInteger(token)) { 
                return token; 
            }
        }
    return null;
    };
    val=val+"";
    format=format+"";
    var i_val=0;
    var i_format=0;
    var c="";
    var token="";
    var token2="";
    var x,y;
    var year=new Date().getFullYear();
    var month=1;
    var date=1;
    var hh=0;
    var mm=0;
    var ss=0;
    var ampm="";
    while (i_format < format.length) {
        // Get next token from format string
        c=format.charAt(i_format);
        token="";
        while ((format.charAt(i_format)==c) && (i_format < format.length)) {
            token += format.charAt(i_format++);
        }
        // Extract contents of value based on format token
        if (token=="yyyy" || token=="yy" || token=="y") {
            if (token=="yyyy") { 
                x=4;y=4; 
            }
            if (token=="yy") { 
                x=2;y=2; 
            }
            if (token=="y") { 
                x=2;y=4; 
            }
            year=this.getInt(val,i_val,x,y);
            if (year==null) { 
                return null; 
            }
            i_val += year.length;
            if (year.length==2) {
                if (year > 70) { 
                    year=1900+(year-0); 
                }
                else { 
                    year=2000+(year-0); 
                }
            }
        }
        else if (token=="MMM" || token=="NNN"){
            month=0;
            var names = (token=="MMM"?(Date.monthNames.concat(Date.monthAbbreviations)):Date.monthAbbreviations);
            for (var i=0; i<names.length; i++) {
                var month_name=names[i];
                if (val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()) {
                    month=(i%12)+1;
                    i_val += month_name.length;
                    break;
                }
            }
            if ((month < 1)||(month>12)){
                return null;
            }
        }
        else if (token=="EE"||token=="E"){
            var names = (token=="EE"?Date.dayNames:Date.dayAbbreviations);
            for (var i=0; i<names.length; i++) {
                var day_name=names[i];
                if (val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()) {
                    i_val += day_name.length;
                    break;
                }
            }
        }
        else if (token=="MM"||token=="M") {
            month=this.getInt(val,i_val,token.length,2);
            if(month==null||(month<1)||(month>12)){
                return null;
            }
            i_val+=month.length;
        }
        else if (token=="dd"||token=="d") {
            date=this.getInt(val,i_val,token.length,2);
            if(date==null||(date<1)||(date>31)){
                return null;
            }
            i_val+=date.length;
        }
        else if (token=="hh"||token=="h") {
            hh=this.getInt(val,i_val,token.length,2);
            if(hh==null||(hh<1)||(hh>12)){
                return null;
            }
            i_val+=hh.length;
        }
        else if (token=="HH"||token=="H") {
            hh=this.getInt(val,i_val,token.length,2);
            if(hh==null||(hh<0)||(hh>23)){
                return null;
            }
            i_val+=hh.length;
        }
        else if (token=="KK"||token=="K") {
            hh=this.getInt(val,i_val,token.length,2);
            if(hh==null||(hh<0)||(hh>11)){
                return null;
            }
            i_val+=hh.length;
            hh++;
        }
        else if (token=="kk"||token=="k") {
            hh=this.getInt(val,i_val,token.length,2);
            if(hh==null||(hh<1)||(hh>24)){
                return null;
            }
            i_val+=hh.length;
            hh--;
        }
        else if (token=="mm"||token=="m") {
            mm=this.getInt(val,i_val,token.length,2);
            if(mm==null||(mm<0)||(mm>59)){
                return null;
            }
            i_val+=mm.length;
        }
        else if (token=="ss"||token=="s") {
            ss=this.getInt(val,i_val,token.length,2);
            if(ss==null||(ss<0)||(ss>59)){
                return null;
            }
            i_val+=ss.length;
        }
        else if (token=="a") {
            if (val.substring(i_val,i_val+2).toLowerCase()=="am") {
                ampm="AM";
            }
            else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {
                ampm="PM";
            }
            else {
                return null;
            }
            i_val+=2;
        }
        else {
            if (val.substring(i_val,i_val+token.length)!=token) {
                return null;
            }
            else {
                i_val+=token.length;
            }
        }
    }
    // If there are any trailing characters left in the value, it doesn't match
    if (i_val != val.length) { 
        return null; 
    }
    // Is date valid for month?
    if (month==2) {
        // Check for leap year
        if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { // leap year
            if (date > 29){ 
                return null; 
            }
        }
        else { 
            if (date > 28) { 
                return null; 
            } 
        }
    }
    if ((month==4)||(month==6)||(month==9)||(month==11)) {
        if (date > 30) { 
            return null; 
        }
    }
    // Correct hours value
    if (hh<12 && ampm=="PM") {
        hh=hh-0+12; 
    }
    else if (hh>11 && ampm=="AM") { 
        hh-=12; 
    }
    return new Date(year,month-1,date,hh,mm,ss);
}

// Check if a date string is valid
Date.isSBMDateValid = function(val,format) {
    return (Date.parseString(val,format) != null);
}

// Check if a date object is before another date object
Date.prototype.isBefore = function(date2) {
    if (date2==null) { 
        return false; 
    }
    return (this.getTime()<date2.getTime());
}

// Check if a date object is after another date object
Date.prototype.isAfter = function(date2) {
    if (date2==null) { 
        return false; 
    }
    return (this.getTime()>date2.getTime());
}

// Check if two date objects have equal dates and times
Date.prototype.equals = function(date2) {
    if (date2==null) { 
        return false; 
    }
    return (this.getTime()==date2.getTime());
}

// Check if two date objects have equal dates, disregarding times
Date.prototype.equalsIgnoreTime = function(date2) {
    if (date2==null) { 
        return false; 
    }
    var d1 = new Date(this.getTime()).clearTime();
    var d2 = new Date(date2.getTime()).clearTime();
    return (d1.getTime()==d2.getTime());
}

// Format a date into a string using a given format string
Date.prototype.format = function(format) {
    format=format+"";
    var result="";
    var i_format=0;
    var c="";
    var token="";
    var y=this.getYear()+"";
    var M=this.getMonth()+1;
    var d=this.getDate();
    var E=this.getDay();
    var H=this.getHours();
    var m=this.getMinutes();
    var s=this.getSeconds();
    var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
    // Convert real date parts into formatted versions
    var value=new Object();
    if (y.length < 4) {
        y=""+(+y+1900);
    }
    value["y"]=""+y;
    value["yyyy"]=y;
    value["yy"]=y.substring(2,4);
    value["M"]=M;
    value["MM"]=Date.LZ(M);
    value["MMM"]=Date.monthNames[M-1];
    value["NNN"]=Date.monthAbbreviations[M-1];
    value["d"]=d;
    value["dd"]=Date.LZ(d);
    value["E"]=Date.dayAbbreviations[E];
    value["EE"]=Date.dayNames[E];
    value["H"]=H;
    value["HH"]=Date.LZ(H);
    if (H==0){
        value["h"]=12;
    }
    else if (H>12){
        value["h"]=H-12;
    }
    else {
        value["h"]=H;
    }
    value["hh"]=Date.LZ(value["h"]);
    value["K"]=value["h"]-1;
    value["k"]=value["H"]+1;
    value["KK"]=Date.LZ(value["K"]);
    value["kk"]=Date.LZ(value["k"]);
    if (H > 11) { 
        value["a"]="PM"; 
    }
    else { 
        value["a"]="AM"; 
    }
    value["m"]=m;
    value["mm"]=Date.LZ(m);
    value["s"]=s;
    value["ss"]=Date.LZ(s);
    while (i_format < format.length) {
        c=format.charAt(i_format);
        token="";
        while ((format.charAt(i_format)==c) && (i_format < format.length)) {
            token += format.charAt(i_format++);
        }
        if (typeof(value[token])!="undefined") { 
            result=result + value[token]; 
        }
        else { 
            result=result + token; 
        }
    }
    return result;
}

// Get the full name of the day for a date
Date.prototype.getDayName = function() { 
    return Date.dayNames[this.getDay()];
}

// Get the abbreviation of the day for a date
Date.prototype.getDayAbbreviation = function() { 
    return Date.dayAbbreviations[this.getDay()];
}

// Get the full name of the month for a date
Date.prototype.getMonthName = function() {
    return Date.monthNames[this.getMonth()];
}

// Get the abbreviation of the month for a date
Date.prototype.getMonthAbbreviation = function() { 
    return Date.monthAbbreviations[this.getMonth()];
}

// Clear all time information in a date object
Date.prototype.clearTime = function() {
  this.setHours(0); 
  this.setMinutes(0);
  this.setSeconds(0); 
  this.setMilliseconds(0);
  return this;
}

// Add an amount of time to a date. Negative numbers can be passed to subtract time.
Date.prototype.add = function(interval, number) {
    if (typeof(interval)=="undefined" || interval==null || typeof(number)=="undefined" || number==null) { 
        return this; 
    }
    number = +number;
    if (interval=='y') { // year
        this.setFullYear(this.getFullYear()+number);
    }
    else if (interval=='M') { // Month
        this.setMonth(this.getMonth()+number);
    }
    else if (interval=='d') { // Day
        this.setDate(this.getDate()+number);
    }
    else if (interval=='w') { // Weekday
        var step = (number>0)?1:-1;
        while (number!=0) {
            this.add('d',step);
            while(this.getDay()==0 || this.getDay()==6) { 
                this.add('d',step);
            }
            number -= step;
        }
    }
    else if (interval=='h') { // Hour
        this.setHours(this.getHours() + number);
    }
    else if (interval=='m') { // Minute
        this.setMinutes(this.getMinutes() + number);
    }
    else if (interval=='s') { // Second
        this.setSeconds(this.getSeconds() + number);
    }
    return this;
}
//------------------------------------------------------------------------------------------------------------------------------//
//        End of javascript Toolbox
//-------------------------------------------------------------------------------------------------------------------------------//

