<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/bpmportal/tld/bpmportal.tld" prefix="sbm" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<style type="text/css">
.bcp-header-branding {
   background: white url('../css/<c:out value="${bizManage.theme}"/>/images/pct-header-background.gif') 0 0 repeat-x;
}

.header-center-panel-body {
    background: #efefef url('../css/<c:out value="${bizManage.theme}"/>/images/pct-header-background.gif') 0 0 repeat-x;
	margin-bottom:0px;
	padding-bottom:2px;
	border-bottom-color: #cccccc;
	border-bottom-width: 2px;
	width: '100%';
}

.header-dashboard-title {
	margin-top: 15px;
	margin-left: 15px;
	/*padding-bottom: 10px;*/   
}

.x-panel-body {
   background-color: white;
}

.header-progress-logo{
   margin-right:5px;
   text-align: right;
}


.header-left-logo{
  background: white url('../css/<c:out value="${bizManage.theme}"/>/images/pct-header-tab-center.gif') 0 0 repeat-x;
   height:72px;
}

.header-left-logo-image {  
   position:relative;
   top:21px;
}

.header-progress-logo-img{
  /* padding-top:15px;*/
  margin-top:15px;

}


.x-button-container {
   /*cursor: pointer;
   position:relative;
   top:-2px; */
   /*cursor: pointer;
	position:absolute;
	top:15px;
	left:250px;*/
}

.x-button-left {
    float:left;
	width : 23px;
	height:37px;
	background-image: url('../css/<c:out value="${bizManage.theme}"/>/images/rounded-combo-sprite.png');
}


.x-button-middle {    
    float:left;
    height:37px;
    background-image: url('../css/<c:out value="${bizManage.theme}"/>/images/rounded-combo-sprite.png');
    background-position: 0px -37px ;
}

.x-button-right {
    float:left;
	width : 23px;
	height:37px;
	background-image: url('../css/<c:out value="${bizManage.theme}"/>/images/rounded-combo-sprite.png');
	background-position: 0px -74px;
}


.dashboard-title .x-btn-text {
	float:left;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
	/*margin: 0px 5px 0px 0px;
	line-height:36px;*/	
	color:#fff;
	font-family:arial narrow,arial,helvetica,sans-serif;
	font-size: 14px;
	margin-right:3px;
	border: 0px none;
	font-weight:bold;
	text-align:center;
	font-family:arial narrow,arial,helvetica,sans-serif;
	text-transform:uppercase;
	font-style: normal;
}

.x-header-menu {
	/* position:relative;
	background-position : -100px -20px;*/
}

/*.x-header-menu .x-menu-item-icon{
	display:none;
}*/

.x-header-menu li a {
	padding-left:5px;
	padding-right:5px;
	font-weight:bold;
	font-size: 16px;
	color:#4489b2;
	font-family:arial narrow,arial,helvetica,sans-serif;
	text-transform:uppercase;
	font-style: normal;
}

.x-menu-list {
	background:none repeat scroll 0 0 transparent;
	border:0 none;
	float:left;
	overflow:hidden;
	padding:2px;
	/*margin-right:10px;*/
}

.x-menu {
	background-image: none;
}

.x-menu-item-text {
	background-image: none;
	text-transform:capitalize;
        font-size:14px;
	
}

.x-button-container-over .x-button-left {
	background-position: 0px -111px ;
}

.x-button-container-over .x-button-middle {
	position: relative;
	background-position: 0px -148px ;
}

.x-button-container-over .x-button-right {
	background-position: 0px -185px ;
}

.x-header-hide {
   /*display:: none;*/
}

.x-header-show {
   /*display: block;*/
}

.x-header-menu .x-menu-item-active {
    background-repeat: repeat-x;
    background-position: left top;
    border-style:solid;
    border-width: 1px 0;
    margin:0 1px;
    padding: 0;
}


</style>

<script type="text/javascript" charset="UTF-8">
    //Ext.namespace('Bcp.Button');
    var northDivConfig =
    {
        id: 'bcp-header',		
        border:false,
        region:'north',
        height:72,
        layout:'border',
        bodyStyle:'background-color:transparent',
        cls: 'bcp-header-branding',
        defaults: { border:false },
        items:
        [{
            id: 'pct_header_leftcmp',
	    region:'west',
            bodyStyle:'background-color:transparent;margin-left:10px',
            xtype:'panel',
	    cls: 'bcp-header-branding',			
            contentEl:'pct_header_left'
            
        },{
            id: 'pct_header_centercmp',
	    region:'center',           
            baseCls: 'header-center-panel',
	    xtype:'panel',
            contentEl: 'pct_header_center'			
        },{            
	    region:'east',
            xtype:'panel',
            id:'pct_header_rightcmp',
	    baseCls: 'header-right',
            contentEl: "pct_header_right"
        }]
    };

    var dashboardArray = new Array();
    var dashboardTitleBtn = null;

    var currentDashboardId;
    var currentDashboardTitle;

    Ext.onReady(function(){
        //create dashboards array
           <c:forEach var="d" items="${mydashboards}" varStatus="status">
            var item = new Object();
                      <c:choose>
                          <c:when test="${d.key == customdashboardid}">
                    item.id = '<c:out value="${d.key}"/>';
                    item.text = '<sbm:message key="BPM_Portal_Dashboard.CustomDashboard" />';
                    item.listeners = {
                        'click': function(btn) {
                            getSelectedDashboardByValue('<c:out value="${d.key}"/>', '<sbm:message key="BPM_Portal_Dashboard.CustomDashboard" />');
                        }
                    }
                                  </c:when>
                                  <c:otherwise>
                    item.id = '<c:out value="${d.key}"/>';
                    item.text = '<c:out value="${d.value}"/>';
                    item.listeners = {
                        'click': function(btn) {
                            getSelectedDashboardByValue('<c:out value="${d.key}"/>', '<c:out value="${d.value}"/>');
                        }
                    }
                                  </c:otherwise>
                              </c:choose>

            dashboardArray.push(item);
        </c:forEach>

        //create the tile of the dashboard...
        <c:choose>
            <c:when test="${dashboard.id == customdashboardid}">
                currentDashboardTitle = '<sbm:message key="BPM_Portal_Dashboard.CustomDashboard" />';
                currentDashboardId = '<c:out value="${dashboard.id}"/>';
            </c:when>
            <c:otherwise>
                currentDashboardTitle = '<c:out value="${dashboard.name}"/>';
                currentDashboardId = '<c:out value="${dashboard.id}"/>';
            </c:otherwise>
        </c:choose>

        //create menu for dashboard
        var dashboardList = new Array();
        for(var i=0; i < dashboardArray.length; i++){
               /*dashboardList[i]={text:dashboardArray[i].text,
                                 id:'__'+dashboardArray[i].id,
                           listeners:{
                              'click': function(btn) {
                                               getSelectedDashboardByValue(this.id, this.text)
                                            }
                                          }
                          };*/
                          
               dashboardList[i]=new Bcp.MenuItem({text:dashboardArray[i].text,
                                 id:'__'+dashboardArray[i].id,
                           listeners:{
                              'click': function(btn) {
                                               getSelectedDashboardByValue(this.id, this.text)
                                            }
                                          }
                          });
        }
        var delay = 250;
        dashboardTitleBtn = new Bcp.Button({
            id:'header-button',		
            text: currentDashboardTitle,
            height: 37,
            renderTo: 'dashboard-title-btn',
            minWidth:150,
            //menuAlign: 'tr-br',
            //ctCls: 'dashboard-title-div',
            cls: 'dashboard-title',
            overCls: 'dashboard-title-over',			
			template:  new Ext.Template(
                    '<div id="x-button-container" class="x-button-container"><div class="x-button-left" id="x-button-left"></div>',
                    '<div class="x-button-middle" id="x-button-middle"><em><button id="x-header-button" type="{0}"></button></em></div>',
                    '<div class="x-button-right" id="x-button-right"></div>',
                    '</div>'),
            menu: new Ext.menu.Menu({
                items: dashboardList ,
		cls : 'x-header-menu',
		id: 'x-header-menu',
                listeners: {
                   mouseover: function(){			 
                   //hideTask.cancel();
                },
                mouseout: function(){
                   //hideTask.delay(delay);
                }}
           }),
    
	   listeners: {
		mouseover: function(){
		  //hideTask.cancel();		       
                },
                mouseout: function(){
                  //hideTask.delay(delay);
                },
		click: function(){   
		   if(!this.hasVisibleMenu()){
		          this.showMenu();
                   }
                   
		   var xyBtn = Ext.get('x-header-button').getXY();
		   var xyMenu = Ext.get('x-header-menu').getXY();
		   xyMenu[0] = xyBtn[0];
		   xyMenu[1] = xyBtn[1] +  Ext.get('x-header-button').getHeight();			      
		   Ext.getCmp('x-header-menu').showAt(xyMenu);			  
		},
		
		resize: function(){
			
		}

     }
 });
  
  var hideTask = new Ext.util.DelayedTask(dashboardTitleBtn.hideMenu, dashboardTitleBtn);

        <c:choose>
            <c:when test="${dashboard.id == customdashboardid}">
                rethink_restoreLink("false");
            </c:when>
            <c:otherwise>
                rethink_restoreLink("true");
            </c:otherwise>
        </c:choose>

        // forces a layout computation (needed for IE7)
        setTimeout("pct_rethinklayout()",10);
    });

 
    function getSelectedDashboardByValue(menuId, dashboardTitle) {    	    
      /*
       *  As the menu id (dashboard id) could conflict with the dashboard widget id,
       *  menu id is prefixed with 2 underscore characters. To get the dashboard id,
       *  we strip off the underscore characters.
       * 
       */
      var underScores = '__';
        var prefix = menuId.substring(0,underScores.length);
        var dashboardId = menuId;
            if(prefix == underScores && menuId.length > underScores.length){
                dashboardId = menuId.substring(underScores.length);
       }else{
                dashboardId = menuId;                 
       }
      
       Ext.Ajax.request({
               url : 'viewdashboard.portal<c:if test="${isRequestFromBcp}">?requestFromBcp=true</c:if>' ,
               params : { defaultSelection : dashboardId, formAction : 'select', responseString : 'plaintext' },
               method: 'POST',
               success: function ( result, request) {
               	      
		   rethink_restoreLink(result.responseText);
                   changeDashboardLayout(dashboardId);
                   currentDashboardId = dashboardId;
                   currentDashboardTitle = dashboardTitle;
                   dashboardTitleBtn.setText(currentDashboardTitle);
                   pct_rethinklayout();
                   

               },
               failure: function ( result, request) {
                   Ext.MessageBox.alert('<sbm:message key="FailedDashboardGet" />');
               }
           });
    }

    function rethink_restoreLink(hideLink) {
        var restoreLinkElement = Ext.get("dashboardRestoreLink");
        if(restoreLinkElement == null)
            return;
        restoreLinkElement.setVisibilityMode(Ext.Element.DISPLAY);
        if(hideLink == "true") {
            restoreLinkElement.hide();
        } else {
            restoreLinkElement.show();
        }
    }

    function restoreDefaultDashboardPCT() {
        var selectedDashboardId = currentDashboardId;
        Ext.Ajax.request({
                url : 'viewdashboard.portal<c:if test="${isRequestFromBcp}">?requestFromBcp=true</c:if>' ,
                params : { defaultSelection : ''+selectedDashboardId, formAction : 'restoreDefaults', responseString : 'plaintext' },
                method: 'POST',
                success: function ( result, request) {
                    if(result.responseText == "true") {
                        window.location.reload(true);
                    } else {
                        Ext.MessageBox.alert('<sbm:message key="FailedDashboardRestore" />');
                    }
                },
                failure: function ( result, request) {
                    Ext.MessageBox.alert('<sbm:message key="FailedDashboardRestore" />');
                }
            });
    }

    function editDashboardPCT() {
        var dashId = currentDashboardId;
        var dashName = currentDashboardTitle;
        document.location.href = '../myhome/createdashboard.portal?mode=edit&name='+ dashName + '&dashboardId='+dashId+'&user=<%=bizManage.getId()%>&requestFromBcp=<c:out value="${isRequestFromBcp}"/>';
    }
 
   	function hideMenu(){
        Ext.getCmp("x-header-menu").hide();
	}
 
    function pct_rethinklayout()
    {		
		Ext.get('pct_header_center').setStyle('display','block');
		Ext.get('pct_header_right').setStyle('display','block');		
		Ext.get('x-button-container').addClassOnOver('x-button-container-over');
		var w = Ext.get("pct_header_table").getWidth();
		Ext.getCmp("pct_header_rightcmp").setWidth(w);
		var l = Ext.get("pct_header_left_table").getWidth();
		Ext.getCmp("pct_header_leftcmp").setWidth(l+70);
				
		//Ext.getCmp("pct_header_centercmp").setWidth(200);
		//Ext.getCmp("pct_header_centercmp").doLayout();  		
                Ext.getCmp("bcp-header").doLayout();  				
    }
	

    
</script>
<div id='pct_header_left' class="bcp-header-branding">
    <table id="pct_header_left_table" cellpadding="0" cellspacing="0"> 
	<tbody>
          <tr>
	     <td valign='top'><img class="tab-left" src="../css/<c:out value="${bizManage.theme}"/>/images/pct-header-tab-left.gif"></td>
	     <td valign='top'>		   
		  <div class="header-left-logo">
		      <img class="header-left-logo-image" src="../css/<c:out value="${bizManage.theme}"/>/images/progresslogo.gif">		           
		  </div>
	     </td> 
	     <td valign='top'>
		     <img  src="../css/<c:out value="${bizManage.theme}"/>/images/pct-header-tab-right.gif">
	     </td>
	     <td valign='top'><div id='dashboard-title-btn' class="header-dashboard-title"></div></td>
         </tr>
       </tbody>
    </table>	 
</div>

<div id='pct_header_center'>
   <div class="header-progress-logo" id="progress-logo">
      <img class="header-progress-logo-img" id="progress-logo-image" src="../css/<c:out value="${bizManage.theme}"/>/images/ct-logo.png">&nbsp;
   </div>
</div>

<div id='pct_header_right' style='position:relative;top:0;left:0' class="bcp-header-branding">
    <table id="pct_header_table" class="bcp-header-branding"><tr>
     <tbody>
        <tr>
         <td valign='top' class='dashboard-header-info-section'>
           <div style="white-space:nowrap;color:#ffffff;">
              <%=bizManage.getFirstName()%> - [<a href="javascript:goToControlTowerLogout()" style="color:#ffffff;text-decoration:underline;"><sbm:message key="logout" /></a>]
           </div>
           <c:choose>
             <c:when test="${bcpMode != 'edit'}">
                <div style="padding-top:2px;white-space:nowrap;color:#ffffff;font-size:9px;">
                    <div class='dashboard-header-date'>
                        <%=new java.util.Date()%>
                    </div>
		    <a href="javascript:editDashboardPCT()" style="color:#ffffff;text-decoration:underline;" title="<sbm:message key="BPM_Portal_Dashboard.EditDashboard"/>"><sbm:message key="BPM_Portal_Dashboard.Customize"/></a>&nbsp;
                    <a id="dashboardRestoreLink" href="javascript:restoreDefaultDashboardPCT()"
                        title="<sbm:message key="BPM_Portal_Dashboard.RestoreDefaults" />"><sbm:message key="BPM_Portal_Dashboard.Restore" /></a>
                </div>
             </c:when>
           </c:choose>
         </td>
       </tr>
      </tbody>
    </table>
</div>
