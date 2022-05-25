var fromValues = new Array(12);
var toValues   = new Array(12);

fromValues[0] = "";
toValues[0] = "";
fromValues[1] = "05/24/2001";
toValues[1] = "05/24/2001";
fromValues[2] = "05/23/2001";
toValues[2] = "05/23/2001";
fromValues[3] = "05/20/2001";
toValues[3] = "05/24/2001";
fromValues[4] = "05/13/2001";
toValues[4] = "05/19/2001";
fromValues[5] = "05/01/2001";
toValues[5] = "05/24/2001";
fromValues[6] = "04/01/2001";
toValues[6] = "04/30/2001";
fromValues[7] = "04/01/2001";
toValues[7] = "05/24/2001";
fromValues[8] = "01/01/2001";
toValues[8] = "03/31/2001";
fromValues[9] = "01/01/2001";
toValues[9] = "05/24/2001";
fromValues[10] = "01/01/2000";
toValues[10] = "12/31/2000";
fromValues[11] = "__/__/__";
toValues[11] = "__/__/__";

function setFromAndToDates(dateRangeComboBox, fromDateTextField, toDateTextField)
{
  var dateRangeIndex = dateRangeComboBox.selectedIndex;
  if (dateRangeIndex < (dateRangeComboBox.length - 1))
  {
    fromDateTextField.value = fromValues[dateRangeIndex];
    toDateTextField.value = toValues[dateRangeIndex];
  }
}

function findDayMax(month,year)
{
  var modmonth = (month.substring(0,1) == 0)?month.substring(1,2):month;
  modmonth -= 1;
  nonlyDayMax = [31,28,31,30,31,30,31,31,30,31,30,31];
  lyDayMax = [31,29,31,30,31,30,31,31,30,31,30,31];
  if ((year % 4) == 0)
  {
     if ((year % 100) == 0 && (year % 400) != 0)
       return nonlyDayMax[modmonth];
     
     return lyDayMax[modmonth];
  }
  return nonlyDayMax[modmonth];
}

function checkValidDateParameters()
{
  var fromDate = document.queryForm.fromDate.value;
  var toDate = document.queryForm.toDate.value;

  if ((fromDate != "") || (toDate != ""))
  {
    var dateTemplate = /^[01]\d\/[0-3]\d\/\d\d\d\d$/;
    if (!dateTemplate.test(fromDate))
    {
      window.alert("From Date does not have the correct format please use mm/dd/yyyy.");
      return false;
    }
    else if (!dateTemplate.test(toDate))
    {
      window.alert("To Date does not have the correct format please use mm/dd/yyyy.");
      return false;
    }

    var fromDateMonth = fromDate.substr(0, 2);
    if ((fromDateMonth < "01") || (fromDateMonth > "12"))
    {
      window.alert("From Date month value must be between 1 and 12.");
      return false;
    }

    var toDateMonth = toDate.substr(0, 2);
    if ((toDateMonth < "01") || (toDateMonth > "12"))
    {
      window.alert("To Date month value must be between 1 and 12.");
      return false;
    }

    var fromDateDay = fromDate.substr(3, 2);
    var toDateDay = toDate.substr(3, 2);
    var fromDateYear = fromDate.substr(6, 4);
    var toDateYear = toDate.substr(6, 4);
    var dayMax = findDayMax(fromDateMonth,fromDateYear);
    if ((fromDateDay < "01") || (fromDateDay > dayMax))
    {
      window.alert("From Date day value must be between 1 and "+dayMax);
      return false;
    }

    dayMax = findDayMax(toDateMonth,toDateYear);
    if ((toDateDay < "01") || (toDateDay > dayMax))
    {
      window.alert("To Date day value must be between 1 and "+dayMax);
      return false;
    }

    if ((fromDateYear > toDateYear) ||
        ((fromDateYear == toDateYear) && ((fromDateMonth > toDateMonth) ||
                                          ((fromDateMonth == toDateMonth) && (fromDateDay > toDateDay)))))
    {
      window.alert("To Date must be greater than From Date.");
      return false;
    }
  }

  return true;
}

function getDateFilteringParameters()
{
  var dateParameters = "startTimeDateRange=" + getSelectedOption(document.queryForm.dateRange) + "&";
  if (document.queryForm.fromDate.value != "")
  {
    dateParameters += "startTimeFromDate=" + document.queryForm.fromDate.value + "&";
  }
  if (document.queryForm.toDate.value != "")
  {
    dateParameters += "startTimeToDate=" + document.queryForm.toDate.value;
  }

  return dateParameters;
}
