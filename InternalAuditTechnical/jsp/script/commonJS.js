var prmtr_sbprmtrMap = {};
function createMap(auditData)
{
    var prmtr = [];
    var duplictPrmtr = [];
    for(var i = 0; i < auditData.length; i++)
    {
        duplictPrmtr.push(auditData[i].PARAMETER_VALUE);
    }
    
    prmtr = duplictPrmtr.unique();
    for(var i = 0; i < prmtr.length; i++)
    {
        var sbPrmArr = [];
        for(var j = 0; j < auditData.length; j++)
        {
             if(auditData[j].PARAMETER_VALUE == prmtr[i])
             {
                 sbPrmArr.push(auditData[j].SUBPARAMETER_VALUE);
             }
        }
        prmtr_sbprmtrMap[prmtr[i]] = sbPrmArr;
    }   
}


function columnNames(pageName,auditType)
{
    /*
     *    DataBase column name mapping.
     *
     *      DB Column Name                        View Column Name
     *
     *        PARAMETER_VALUE                PARAMETER
     *        SUBPARAMETER_VALUE             SUB PARAMETER
     *        RISK_RATE                      RISK RATE
     *        DOCUMENTNO                     DOCUMENT NO / ANNEXURE REF NO
     *        MIDDLE_PARA                    AUDIT OBSERVATION
     *        LAST_PARA                      RISK IMPACT
     *                                       POM NO    // This is only for view
     *
     */
    
    var clmArr;
    if(pageName.trim() != '' && pageName != null && auditType.trim() != '' && auditType != null)
    {
        if(pageName.trim() == 'StartAuditPage')
        {
            if(auditType.trim() == 'OD Claims')
            {
                clmArr = ['POM NO','PARAMETER','SUB PARAMETER','CLAIM CATEGORY','CLAIM STATUS','SETTLEMENT BASIS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION'];
            }
            else if(auditType.trim() == 'Legal Claims')
            {
                clmArr = ['POM NO','PARAMETER','SUB PARAMETER','COURT TYPE','LOSS NATURE','CLAIM STATUS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION'];
            }
            else
            {
                clmArr = ['POM NO','PARAMETER','SUB PARAMETER','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION'];
            }
        }
        else if(pageName.trim() == 'AuditeeReplyPage')
        {
            if(auditType.trim() == 'OD Claims')
	    {
	        clmArr = ['POM NO','PARAMETER','SUB PARAMETER','CLAIM CATEGORY','CLAIM STATUS','SETTLEMENT BASIS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR'];
	    }
	    else if(auditType.trim() == 'Legal Claims')
	    {
	        clmArr = ['POM NO','PARAMETER','SUB PARAMETER','COURT TYPE','LOSS NATURE','CLAIM STATUS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR'];
	    }
	    else
	    {
	        clmArr = ['POM NO','PARAMETER','SUB PARAMETER','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR'];
            }
        }
        else if(pageName.trim() == 'AuditClosurePage')
	{
	    if(auditType.trim() == 'OD Claims')
	    {
		clmArr = ['POM NO','PARAMETER','SUB PARAMETER','CLAIM CATEGORY','CLAIM STATUS','SETTLEMENT BASIS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR','AUDITOR STATUS'];
	    }
	    else if(auditType.trim() == 'Legal Claims')
	    {
		clmArr = ['POM NO','PARAMETER','SUB PARAMETER','COURT TYPE','LOSS NATURE','CLAIM STATUS','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR','AUDITOR STATUS'];
	    }
	    else
	    {
		clmArr = ['POM NO','PARAMETER','SUB PARAMETER','DEPARTMENT','CLASS','RISK RATE','DOCUMENT NO / ANNEXURE REF NO','AUDIT OBSERVATION','RISK IMPACT','RECOMMENDATION','REASON FOR DEVIATION','CORRECTIVE PREVENTIVE ACTION','TIMELINE RESOLUTION','AUDITEE STATUS','REMARKS BY AUDITOR','AUDITOR STATUS'];
	    }
        }
    }
    return clmArr;
}

Array.prototype.unique = function() {
     var u = {}, a = [];
     for(var i = 0, l = this.length; i < l; ++i)
     {
	if(u.hasOwnProperty(this[i])) 
	{
	     continue;
	}
	a.push(this[i]);
	u[this[i]] = 1;
     }
     return a;
}

var linkNumber = 1;
function createDynamicHyperlink(id,clickMethodOutput,innerHTMLValue,parentElementId)
{
    var parameterLinks = document.createElement("a");
    parameterLinks.setAttribute("href","#");
    parameterLinks.setAttribute("id",id);
    parameterLinks.style.textDecoration = "none";
    parameterLinks.onclick = new Function(clickMethodOutput);
    if(linkNumber==1)
    {
        parameterLinks.innerHTML = "<div onClick=divActivate(this);  class='parameterLink parameterLinkActivated' >"+innerHTMLValue+"&nbsp;<img style='width:20px;height:10px;' src='images/fast_forward.png' /></div>";
    }
    else
    {
        parameterLinks.innerHTML = "<div onClick=divActivate(this);  class='parameterLink' >"+innerHTMLValue+"&nbsp;<img style='width:20px;height:10px;' src='images/fast_forward.png' /></div>";
    }
    document.getElementById(parentElementId).appendChild(parameterLinks);
    linkNumber++;
}

function getNVL(val)
{
    if(val == null || val == "null" || val == "")
    {
        val = "";
    }
    return val;
}

function divActivate(Obj)
{
    var parentObj = Obj.parentNode.parentNode;
    var childrenObj = parentObj.childNodes;
    for(var i=0;i<childrenObj.length;i++)
    {
        if(jQuery(childrenObj[i].childNodes[0]).hasClass('parameterLinkActivated'))
        {
            jQuery(childrenObj[i].childNodes[0]).removeClass( "parameterLinkActivated" );
        }
    }
    Obj.className += " parameterLinkActivated";
}



function createDymamicTableHead(tableId,TH_List,divId,cellStyle)
{
    var tableObj = document.createElement('table');
    tableObj.id = tableId;
    tableObj.className = "dynamicTableStyle";
    var tableHeadObj = tableObj.createTHead();
    var tableHeadRowObj = tableHeadObj.insertRow(0);
    for(var i=0;i<TH_List.length;i++)
    {
        var tableHCellObj = tableHeadRowObj.insertCell(i);
        tableHCellObj.className = cellStyle;
        tableHCellObj.innerHTML="<b>"+TH_List[i]+"</b>";
        //setValueInTitle(tableHCellObj,TH_List[i]);
    }
    document.getElementById(divId).appendChild(tableObj);
}


var currentshowtbl;
function showClickedParameterDataTable(tableId)
{
    currentshowtbl = tableId;
    for(var i=0;i<prmtrs.length;i++) 
    {
        document.getElementById(prmtrs[i]).style.display = "none";
    }
    document.getElementById(tableId).style.display = "block";
}

       
function divDeactivate(Obj)
{
    var newClassName = "";
    var i;
    var classes = Obj.className.split(" ");
    var removeClassName = classes[(classes.length-1)];
    for(i = 0; i < classes.length; i++) 
    {
        if(classes[i] !== removeClassName) 
        {
            newClassName += classes[i] + " ";
        } 
    }
    Obj.className = newClassName;
 }
 
function isEven(n) 
{
     n = Number(n);
     return n === 0 || !!(n && !(n%2));
}


var currentFieldObj;
function setValuesInFields(branchTyp)
{
    var documentValue = document.getElementById('documentNo').value;
    var middleParaValue = document.getElementById('middlePara').value;
    var lastParaValue = document.getElementById('lastPara').value;
    var recommendationValue = document.getElementById('reccommendation').value;
    if(documentValue.trim() == "")
    {
        $('#documentNo').css({'border' : '1px solid red'});
        $('#documentNo').focus();
        return false; 
    }
    else
    {
        $('#documentNo').css({'border' : ''});
    }
    if(middleParaValue.trim() == "")
    {
        $('#middlePara').css({'border' : '1px solid red'});
        $('#middlePara').focus();
        return false;
    }
    else
    {
        $('#middlePara').css({'border' : ''});
    }
    if(lastParaValue.trim() == "")
    {
        $('#lastPara').css({'border' : '1px solid red'});
        $('#lastPara').focus();
        return false;
    }
    else
    {
        $('#lastPara').css({'border' : ''});
    }
    if(recommendationValue.trim() == "")
    {
        $('#reccommendation').css({'border' : '1px solid red'});
        $('#reccommendation').focus();
        return false;
    }
    else
    {
        $('#reccommendation').css({'border' : ''});
    }
    var currentTRObj = currentFieldObj.parentNode.parentNode;
    if(branchTyp == 'OD Claims' || branchTyp == 'Legal Claims')
    {
        currentTRObj.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value = documentValue;
        currentTRObj.getElementsByTagName('td')[10].getElementsByTagName('input')[0].value = middleParaValue;
        currentTRObj.getElementsByTagName('td')[11].getElementsByTagName('input')[0].value = lastParaValue;
        currentTRObj.getElementsByTagName('td')[12].getElementsByTagName('input')[0].value = recommendationValue;
    }
    else
    {
        currentTRObj.getElementsByTagName('td')[6].getElementsByTagName('input')[0].value = documentValue;
        currentTRObj.getElementsByTagName('td')[7].getElementsByTagName('input')[0].value = middleParaValue;
        currentTRObj.getElementsByTagName('td')[8].getElementsByTagName('input')[0].value = lastParaValue;
        currentTRObj.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value = recommendationValue;
    }
  
   document.getElementById('documentNo').value = "";
   document.getElementById('middlePara').value = "";
   document.getElementById('lastPara').value = "";
   document.getElementById('reccommendation').value = "";
   lightbox_close();
}


function setValuesInPopupAuditor()
{
    var documentObj = document.getElementById('documentNo');
    var middleParaObj = document.getElementById('middlePara');
    var recommendationObj = document.getElementById('reccommendation');
    var lastParaObj = document.getElementById('lastPara');
    var currentTRObj = currentFieldObj.parentNode.parentNode;
    if($(currentTRObj).children('td').length == 13)
    {
        documentObj.value = currentTRObj.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value;
	middleParaObj.value = currentTRObj.getElementsByTagName('td')[10].getElementsByTagName('input')[0].value;
	recommendationObj.value = currentTRObj.getElementsByTagName('td')[12].getElementsByTagName('input')[0].value;
        lastParaObj.value = currentTRObj.getElementsByTagName('td')[11].getElementsByTagName('input')[0].value;
    }
    else
    {  
        documentObj.value = currentTRObj.getElementsByTagName('td')[6].getElementsByTagName('input')[0].value;
        middleParaObj.value = currentTRObj.getElementsByTagName('td')[7].getElementsByTagName('input')[0].value;
        recommendationObj.value = currentTRObj.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value;
        lastParaObj.value = currentTRObj.getElementsByTagName('td')[8].getElementsByTagName('input')[0].value;  
    }
}


function setValuesInFieldsInAuditee()
{   
    var deviationReason = document.getElementById('deviationReason').value;
    var correctiveAction = document.getElementById('correctiveAction').value;
    if(deviationReason.trim() == "")
    {
        document.getElementById('deviationReason').style.border = "1px solid red";
        document.getElementById('deviationReason').focus();
        return false;
    }
    else if(correctiveAction.trim() == "")
    {
        document.getElementById('deviationReason').style.border = "";
        document.getElementById('correctiveAction').style.border = "1px solid red";
        document.getElementById('correctiveAction').focus();
        return false;
    }
    else
    {
        document.getElementById('correctiveAction').style.border = "";
        var currentTRObj = currentFieldObj.parentNode.parentNode;
        if(currentTRObj.cells.length >= 17)
        {
            currentTRObj.getElementsByTagName('td')[13].getElementsByTagName('input')[0].value = deviationReason;
  	    currentTRObj.getElementsByTagName('td')[14].getElementsByTagName('input')[0].value = correctiveAction;
        }
        else
        {
            currentTRObj.getElementsByTagName('td')[10].getElementsByTagName('input')[0].value = deviationReason;
  	    currentTRObj.getElementsByTagName('td')[11].getElementsByTagName('input')[0].value = correctiveAction;
        }
  	document.getElementById('deviationReason').value = "";
  	document.getElementById('correctiveAction').value = "";
  	lightboxAuditee_close();
    }
}


function setValuesInPopupAuditee()
{
    var deviationReasonObj = document.getElementById('deviationReason');
    var correctiveActionObj = document.getElementById('correctiveAction');
    var currentTRObj = currentFieldObj.parentNode.parentNode;
    if(currentTRObj.cells.length >= 17)
    {
        deviationReasonObj.value = currentTRObj.getElementsByTagName('td')[13].getElementsByTagName('input')[0].value;
        correctiveActionObj.value = currentTRObj.getElementsByTagName('td')[14].getElementsByTagName('input')[0].value;    
    }
    else
    {
        deviationReasonObj.value = currentTRObj.getElementsByTagName('td')[10].getElementsByTagName('input')[0].value;
        correctiveActionObj.value = currentTRObj.getElementsByTagName('td')[11].getElementsByTagName('input')[0].value;
    }
}

function setValuesInFieldsClosure()
{
    var remarks = document.getElementById('remarksTextArea').value;
    if(remarks == "" || remarks == null)
    {
        document.getElementById('remarksTextArea').style.border = "1px solid red";
        document.getElementById('remarksTextArea').focus();
        return false;
    }
    var currentTRObj = currentFieldObj.parentNode.parentNode;
    if(currentTRObj.cells.length == 19)
    {
        currentTRObj.getElementsByTagName('td')[17].getElementsByTagName('input')[0].value = remarks;    
    }
    else
    {
        currentTRObj.getElementsByTagName('td')[14].getElementsByTagName('input')[0].value = remarks;    
    }
    document.getElementById('remarksTextArea').value = "";
    remarkbox_close();
}


function setValueInPopupClosure()
{
    var remarksObj = document.getElementById('remarksTextArea');
    var currentTRObj = currentFieldObj.parentNode.parentNode;
    if(currentTRObj.cells.length == 19)
    {
	remarksObj.value = currentTRObj.getElementsByTagName('td')[17].getElementsByTagName('input')[0].value;    
    }
    else
    {
        remarksObj.value = currentTRObj.getElementsByTagName('td')[14].getElementsByTagName('input')[0].value;    
    }
}


function lightbox_open(Obj)
{
    currentFieldObj = Obj;
    window.scrollTo(0,0);
    document.getElementById('light').style.display='block';
    document.getElementById('fade').style.display='block';
    document.getElementById("documentNo").focus();  
    setValuesInPopupAuditor();
}



function getValInPopup(obj)
{
    window.scrollTo(0,0);
    var i = 0;
    var tabHtml = "<table>";
    $(obj).find('tr').each(function() 
    {
  	tabHtml += "<tr>";
  	if($(this).children('td').length >= 17)
	{
	    if(i != 0)
	    {
		$(this).find('td').each(function()
		{
		    if(($(this).index() > 1 && $(this).index() < 13) || $(this).index() == 17)
		    {
			if($(this).index() != 8)
			{
			    tabHtml += "<td><span class='dynamicTableTH1' style='width:150px;border:0px;height:100px;overflow:auto;'>"+this.title+"</td>";
			}
		    }
		});  
	    }
	    else
	    {
		$(this).find('td').each(function()
		{
		    if(($(this).index() > 1 && $(this).index() < 13) || $(this).index() == 17)
		    {
			if($(this).index() != 8)
			{
			    tabHtml += "<td style='background-color:#98b8df;'><span style='border:0px;font-weight:bold;' class='dynamicTableTH1'>"+$(this).children().first().html()+"</td>";
			}
		    }
		});
	    }
	    i++;
	    tabHtml += "</tr>";
        }
  	else if($(this).children('td').length == 15)
  	{
  	    if(i != 0)
  	    {
  		$(this).find('td').each(function()
  		{
  		    if(($(this).index() > 1 && $(this).index() < 10) || $(this).index() == 14)
    		    {
                        if($(this).index() != 5)
          	        {
          	     	    tabHtml += "<td><span class='dynamicTableTH1' style='width:150px;border:0px;height:100px;overflow:auto;'>"+this.title+"</td>";
                        }
       		    }
                });  
            }
            else
  	    {
  		$(this).find('td').each(function()
  		{
  	            if(($(this).index() > 1 && $(this).index() < 10) || $(this).index() == 14)
  	            {
  	                if($(this).index() != 5)
                        {
    	                    tabHtml += "<td style='background-color:#98b8df;'><span style='border:0px;font-weight:bold;' class='dynamicTableTH1'>"+$(this).children().first().html()+"</td>";
    	    	        }
         	    }
                });
            }
            i++;
            tabHtml += "</tr>";
      }
      else
      {  
        if(i != 0)
        {
            $(this).find('td').each(function()
            {
     	         if(($(this).index() > 1 && $(this).index() < 10))
                 {
     	             if($(this).index() != 5)
          	     { 	
                          tabHtml += "<td><span class='dynamicTableTH1' style='width:150px;border:0px;height:100px;overflow:auto;'>"+this.title+"</td>";
          	     }
       		 }
     	    });
    	}
 	else
  	{
  	    $(this).find('td').each(function()
  	    {
                if($(this).index() > 1 && $(this).index() < 10)
                {
                    if($(this).index() != 5)
                    {
    	                tabHtml += "<td style='background-color:#98b8df;'><span style='border:0px;font-weight:bold;' class='dynamicTableTH1'>"+$(this).children().first().html()+"</td>";
    	            }
                }
            });
        }
        i++;
        tabHtml += "</tr>";
      }
   });
   tabHtml += "</table>";
   tabHtml += "<a href='#' class='cancel' onClick='Pp_close();'></a>";
   document.getElementById('valPp').innerHTML = tabHtml;
   document.getElementById('valPp').style.display='block';
   document.getElementById('fade').style.display='block';
}


function getValInPopupClosure(obj)
{
     window.scrollTo(0,0);
     var i = 0;
     var tabHtml = "<table>";
     $(obj).find('tr').each(function() {
     tabHtml += "<tr>";
     if($(this).children('td').length == 19)
     {
         if(i != 0)
	 {
	      $(this).find('td').each(function()
	      {
	          if($(this).index() > 1 && $(this).index() < 17)
	          {
	              if($(this).index() != 8)
	              {
	                  tabHtml += "<td><span class='dynamicTableTH1' style='width:108px;border:0px;height:85px;overflow:auto;'>"+this.title+"</td>";
	              }
	          }
	      });
	 }
	 else
	 {
	     $(this).find('td').each(function()
	     {
	         if($(this).index() > 1 && $(this).index() < 17)
	         {
	             if($(this).index() != 8)
	             {
	                tabHtml += "<td style='background-color:#98b8df;'><span style='border:0px;font-weight:bold;' class='dynamicTableTH1'>"+$(this).children().first().html()+"</td>";
	    	     }
	         }
	     });
         }
     }
     else
     {
         if(i != 0)
         {
             $(this).find('td').each(function()
             {
                 if($(this).index() > 1 && $(this).index() < 14)
                 {
                     if($(this).index() != 5)
                     {
                         tabHtml += "<td><span class='dynamicTableTH1' style='width:108px;border:0px;height:85px;overflow:auto;'>"+this.title+"</td>";
                     }
                 }
             });
         }
         else
         {
             $(this).find('td').each(function()
             {
                 if($(this).index() > 1 && $(this).index() < 14)
                 {
                     if($(this).index() != 5)
                     {
        	         tabHtml += "<td style='background-color:#98b8df;'><span style='border:0px;font-weight:bold;' class='dynamicTableTH1'>"+$(this).children().first().html()+"</td>";
         	     }
                 }
             });
         }
     }
     i++;
     tabHtml += "</tr>";
   });
   
      tabHtml += "</table>";
      tabHtml += "<a href='#' class='cancel' onClick='Pp_close();'></a>";
      document.getElementById('valPp').innerHTML = tabHtml;
      document.getElementById('valPp').style.display='block';
      document.getElementById('fade').style.display='block';
}


function Pp_close()
{
    document.getElementById('valPp').style.display='none';
    document.getElementById('fade').style.display='none';
}

function lightbox_open_auditee(Obj)
{
    currentFieldObj = Obj;
    window.scrollTo(0,0);
    document.getElementById('lightAuditee').style.display='block';
    document.getElementById('fade').style.display='block';  
    document.getElementById("deviationReason").focus();
    setValuesInPopupAuditee();
}

function lightbox_close()
{
    document.getElementById('light').style.display='none';
    document.getElementById('fade').style.display='none';
}

function lightboxPp_close()
{
    document.getElementById('valPp').style.display='none';
    document.getElementById('fade').style.display='none';
}

function lightboxAuditee_close()
{
    document.getElementById('deviationReason').style.border = "";
    document.getElementById('correctiveAction').style.border = "";
    document.getElementById('lightAuditee').style.display='none';
    document.getElementById('fade').style.display='none';
}

function remarkbox_open(Obj)
{
    currentFieldObj = Obj;
    window.scrollTo(0,0);
    document.getElementById('remarkPopup').style.display='block';
    document.getElementById('fade').style.display='block';
    document.getElementById("remarksTextArea").focus();
    setValueInPopupClosure();  
}

function remarkbox_close()
{
    document.getElementById('remarksTextArea').style.border = "";
    document.getElementById('remarkPopup').style.display='none';
    document.getElementById('fade').style.display='none';
}

function savingInfoStart()
{
    savingMsg = new Ext.LoadMask(Ext.getBody(), {msg:"Saving Report..."});
    savingMsg.show();    
}

function savingInfoEnd()
{
    savingMsg.hide();
    if(btnObj.value == "Submit")
    {
        document.getElementById('completeWS').click();
    }  
}

function validateEmail(email) 
{ 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
} 


function riskRateValMapping(val)
{
    var value;
     if(val == 1)
     {
        value = '1 - Recommendation Risk';
     }
     else if(val == 2)
     {
        value = '2 - Low Risk';
     }
     else if(val == 3)
     {
        value = '3 - Medium Risk';   
     }
     else if(val == 4)
     {
        value = '4 - High Risk';
     }
     else if(val == 5)
     {
        value = '5 - No Audit Observation';
     }
     else
     {
        value = 'Select';
     }
    return value;
}

function riskRateColorMapp(val)
{
    var value;
       if(val == 1 || val == 5){
          value = '#5e5e5e-#000000';
       }
       if(val == 2){
          value = '#5e5e5e-#00FF00';
       }
       if(val == 3){
          value = '#5e5e5e-#FFC200';
       }
       if(val == 4){
          value = '#5e5e5e-#ff0000';
       }
    return value;
}


function ellipsify (str) 
{
   if (str.length > 15) 
   {
        return (str.substring(0, 15) + "...");
   }
   else 
   {
       return str;
   }
}

function shwFullTxtInPopup(value)
{
    document.getElementById('shwFullTxt').style.display = 'block';
    document.getElementById('shwFullTxt').innerHTML = value; 
}

function hideFullTxtInPopup()
{
    document.getElementById('shwFullTxt').style.display = 'none';
}

function setValueInTitle(obj,value)
{
    obj.title = value.trim();
}

function openAuditHistory(url)
{
    window.open(url,'_blank');
}

function showLoading()
{
    loadingInfo = new Ext.LoadMask(Ext.getBody());
    loadingInfo.show();
}

function loadingComplete()
{
    loadingInfo.hide();
}


var count = "3000";
function limiter(obj)
{
    if($('#documentNo').val().trim() == "")
    {
        $('#middlePara').val(null);
  	$('#lastPara').val(null);
  	$('#reccommendation').val(null);
    }
    if($('#middlePara').val().trim() == "")
    {
  	$('#lastPara').val(null);
  	$('#reccommendation').val(null);
    }
    if($('#lastPara').val().trim() == "")
    {
        $('#reccommendation').val(null);
    }
    var tex = obj.value;
    var len = tex.length;
    if(len > count)
    {
        tex = tex.substring(0,count);
	obj.value =tex;
	return false;
    }
}


function wordLimiter(obj)
{
    var tex = obj.value;
    var len = tex.length;
    if(len > count)
    {
	tex = tex.substring(0,count);
	obj.value =tex;
	return false;
    }
}

function wordLimiter(obj,limit)
{
    var tex = obj.value;
    var len = tex.length;
    if(len > limit)
    {
	tex = tex.substring(0,limit);
	obj.value =tex;
	return false;
    }
}


function getValueOfField(Obj)
{
  // if(!(Obj.value).trim() == ''){
     //setValueInTitle(Obj.parentNode,Obj.value);
  //  }
}
