Ext.namespace('Bm.plugin');

Bm.plugin.GridViewForceFit = function() {
  var layout = function () {
      if(!this.mainBody){
        return; // not rendered
      }
      var g = this.grid;
      var c = g.getGridEl(), cm = this.cm,
      expandCol = g.autoExpandColumn,
      gv = this;
      
      var csize = c.getSize(true);
      var vw = csize.width;
      
      if(vw < 20 || csize.height < 20){ // display: none?
       return;
      }
      
      if(g.autoHeight){
       // sufficient to get scrolling back
       this.scroller.dom.style.overflowX = 'auto';
    
       // without this, it scrolls but doesn't keep headers synched
       this.el.setWidth(csize.width);
       this.scroller.setWidth(vw);
       if(this.innerHd) {
        this.innerHd.style.width = (vw) + 'px';
       }
      }
      else {
       this.el.setSize(csize.width, csize.height);
       
       var hdHeight = this.mainHd.getHeight();
       var vh = csize.height - (hdHeight);
       
       this.scroller.setSize(vw, vh);
       if(this.innerHd){
        this.innerHd.style.width = (vw)+'px';
       }
      }
      if(this.forceFit){
       if(this.lastViewWidth != vw){
        this.fitColumns(false, false);
        this.lastViewWidth = vw;
       }
      }
      else {
       this.autoExpand();
       this.syncHeaderScroll();
      }
      this.onLayout(vw, vh);
  };
  
  return {    
    init: function(grid) {
        if(Ext.isIE){
            grid.getView().layout = layout;
        }        
    }
  };
};