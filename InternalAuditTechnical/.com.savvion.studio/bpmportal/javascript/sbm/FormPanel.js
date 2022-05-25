
FormPanel = Ext.extend(Ext.Panel,{
        initComponent : function(){
        Ext.Panel.superclass.initComponent.call(this);
        this.bodyContentEl = document.getElementById(this.renderTo+ "_body");

        this.addEvents(            
            'bodyresize',            
            'titlechange',            
            'collapse',            
            'expand',            
            'beforecollapse',            
            'beforeexpand',            
            'beforeclose',            
            'close',            
            'activate',            
            'deactivate'
        );
        
        if(this.tbar){
            this.elements += ',tbar';
            if(typeof this.tbar == 'object'){
                this.topToolbar = this.tbar;
            }
            delete this.tbar;
        }
        if(this.bbar){
            this.elements += ',bbar';
            if(typeof this.bbar == 'object'){
                this.bottomToolbar = this.bbar;
            }
            delete this.bbar;
        }

        if(this.header === true){
            this.elements += ',header';
            delete this.header;
        }else if(this.title && this.header !== false){
            this.elements += ',header';
        }

        if(this.footer === true){
            this.elements += ',footer';
            delete this.footer;
        }

        if(this.buttons){
            var btns = this.buttons;
            
            this.buttons = [];
            for(var i = 0, len = btns.length; i < len; i++) {
                if(btns[i].render){                     btns[i].ownerCt = this;
                    this.buttons.push(btns[i]);
                }else{
                    this.addButton(btns[i]);
                }
            }
        }
        if(this.autoLoad){
            this.on('render', this.doAutoLoad, this, {delay:10});
        }
    },
	   
    createElement : function(name, pnode){
	if(this[name]){
	    pnode.appendChild(this[name].dom);
            return;
        }
         
        if(name === 'bwrap' || this.elements.indexOf(name) != -1){
	    if(this[name+'Cfg']){
                this[name] = Ext.fly(pnode).createChild(this[name+'Cfg']);
            }else{
                
		var el = (name == 'body') ?   this.bodyContentEl : document.createElement('div');
                el.className = this[name+'Cls'];		
                this[name] = Ext.get(pnode.appendChild(el));
				
            }
        }
    }
});
