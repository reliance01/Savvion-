var delegatesArray = new Array();

function delegateRecord(comboid, selectedValue) {
    this.comboid = comboid;
    this.value = selectedValue;
}

function updateValueRecords(applnDataArray) {
    if(delegatesArray != null && delegatesArray.length > 0) {
        for(var ix = 0; ix < delegatesArray.length; ix++) {
            var record = delegatesArray[ix];
            var cmb = document.getElementById(record.comboid);
            if(applnDataArray != null && applnDataArray.length > 0){
                for(var jx=0; jx < applnDataArray.length;jx++) {
                    cmb.options[cmb.options.length] = new Option(applnDataArray[jx][1], applnDataArray[jx][0]);
                    if(applnDataArray[jx][0] == record.value) {
                        cmb.options[cmb.options.length-1].selected=true;
                    }
                }
           }
        }
    }
}