function dateFormat(date)
{
 var fullTime = date.split(" ")[1].split(".")[0];
 fullTime = fullTime.split(":")
 var h = fullTime[0];
 var hh = h;
 var m = fullTime[1];
 var s = fullTime[2];
 
var dd = "AM";
if (h >= 12) {
 h = hh-12;
 dd = "PM";
}
if (h == 0) {
 h = 12;
}
m = m<10?"0"+m:m;
s = s<10?"0"+s:s;
 
 var dateVal = date.split(" ")[0].split("-");
 var date = dateVal[2];
 var month = dateVal[1];
 var year = dateVal[0];
 return date+"-"+month+"-"+year+" "+h+":"+m+":"+s+" "+dd;
}

function getSubmit() {
    var r = confirm("Confirm Submission!");
    if(r)
    {
      $('#done').click();
    }
}

function getCancel()
{
   $('#cnl').click();
}

function UINValidate(obj)
{
   pwr.isUIN_Exist($(obj).val(),UINValidateCB);
}

function UINValidateCB(val)
{
  if(val == true)
  {
    alert('UIN Number already exist!');
    $('#uinNumber').val('');
    return false;
  }
}

function detectIE() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        return true;
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        return true;
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
       return true;
    }
    //alert('Please Use Internet Explorer (IE) browser for using Marcom or Online Process Flow.');
    //return false;
    return true;
}