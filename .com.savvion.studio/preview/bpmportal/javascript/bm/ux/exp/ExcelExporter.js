Ext.namespace("Bm.ux.exp"); 
Ext.define("Bm.ux.exp.ExcelExporter", {

    statics: {
        postRequest: function(url, paramsMap, method) {
            var method = method || "post"; // Set method to post by default, if not specified.
        
            var form = document.createElement("form");
            form.setAttribute("method", method);
            form.setAttribute("action", url);
        
            for(var key in paramsMap) {
                if(paramsMap.hasOwnProperty(key)) {
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", key);
                    hiddenField.setAttribute("value", paramsMap[key]);
                    form.appendChild(hiddenField);
                }
            }
        
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);            
        },

        exportToExcel: function(grid) {
            var allColumns = grid.columns;
            var columns = [];
            var data = [];
            var headerNames = [];
            var params = {};
            
            Ext.each(allColumns,function(col) {
               if (col.hidden != true && col.dataIndex != '' && col.dataIndex != undefined) {
                   columns.push(col);
                   headerNames.push(col.text);
               }
            }, this);
            
           data.push(headerNames);
            
            var ix = 0;
            grid.store.data.each(function(item) {
                var convertedData = [];
                convertedData.push((grid.store.data.items[ix++].index)+1);

                for (var key in item.data) {
                    var value = item.data[key];

                    Ext.each(columns, function(column) {
                                                
                        if (column.dataIndex == key) {            
                            if (column.dataIndex == 'task' && item.data['bm_taskId'] != undefined) {
                                 convertedData.push(value + "#" + item.data['bm_taskId']);
                            // This check will be replaced with column.type == 'NUMBER' after Amaladeepan's changes.
                            } else if (column.type == 'number' || column.type =='DECIMAL'){
                                 convertedData.push(Number(value));
                            } else {
                                 convertedData.push(value);
                            }
                            return false;
                        }
                         
                    }, this);                    
                }
                
                data.push(convertedData);
                
            });
            
            // Prepare Params map
            params["type"] = "json";
            if(grid.title != undefined && grid.title != '') {
                params["fileName"] = grid.title;
            }            
            params["data"] = Ext.JSON.encode(data);            
            this.postRequest("../export/viewer/excel", params, "POST");
            
        }
        
    }

});