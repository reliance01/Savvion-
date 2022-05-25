$(document).ready(function(){
    $('#loading').hide();
    $('#itemsTable').hide();
    $('#resumeItemsButton').hide();
});

var getAllSuspendedItems = function () {
    disableAllButtons();
    getDataAndLoadData(onErrorHandler,onSuccessHandler);
};

var resumeAllSuspendedItems = function() {
    disableAllButtons();
};

var getDataAndLoadData = function (errorHandler,successHandler) {   
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState < 4) {
            console.log("waiting for response");
            disableAllButtons();
        }
        else if (this.readyState === 4) {    
            console.log("got a response now it's time to decide whether it's a success response or error") ;
            if (this.status == 200 && this.status < 300) {
                console.log("I decided it's a success response");
                successHandler(this.response);
            } else {
                console.log("I decided it's a error response");
                errorHandler();
            }
        }
    };
    xhttp.open("GET", "suspendedItemService.jsp", true);
    xhttp.send();
};

var disableAllButtons = function() {
    $('#loading').show(); 
    $('#getAllSuspendedItemsButton').attr('disabled','true');
    $('#resumeItemsButton').attr('disabled','true');
};

var enableAllButtons = function() {
    $('#itemsTable').show();
    $('#loading').hide(); 
    $('#getAllSuspendedItemsButton').removeAttr('disabled');
    $('#resumeItemsButton').removeAttr('disabled');
};

var onSuccessHandler = function(data) {
    console.log(data);
    //loadTheTable(data);
    enableAllButtons();
}

var onErrorHandler = function() {
    alert("Error While Resuming or Getting the Items");
}

var loadTheTable = function(data) {
    $('#itemsTable').empty();
};