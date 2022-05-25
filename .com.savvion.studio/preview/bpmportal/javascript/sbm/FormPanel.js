
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


Ext.override(Ext.form.Checkbox, {
	onRender: function(ct, position){
		Ext.form.Checkbox.superclass.onRender.call(this, ct, position);
		if(this.inputValue !== undefined){
			this.el.dom.value = this.inputValue;
		}
		//this.el.addClass('x-hidden');
		this.innerWrap = this.el.wrap({
			//tabIndex: this.tabIndex,
			cls: this.baseCls+'-wrap-inner'
		});
		this.wrap = this.innerWrap.wrap({cls: this.baseCls+'-wrap'});
		this.imageEl = this.innerWrap.createChild({
			tag: 'img',
			src: Ext.BLANK_IMAGE_URL,
			cls: this.baseCls
		});
		if(this.boxLabel){
			this.labelEl = this.innerWrap.createChild({
				tag: 'label',
				htmlFor: this.el.id,
				cls: 'x-form-cb-label',
				html: this.boxLabel
			});
		}
		//this.imageEl = this.innerWrap.createChild({
			//tag: 'img',
			//src: Ext.BLANK_IMAGE_URL,
			//cls: this.baseCls
		//}, this.el);
		if(this.checked){
			this.setValue(true);
		}else{
			this.checked = this.el.dom.checked;
		}
		this.originalValue = this.checked;
	},
	afterRender: function(){
		Ext.form.Checkbox.superclass.afterRender.call(this);
		//this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
		this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
	},
	initCheckEvents: function(){
		//this.innerWrap.removeAllListeners();
		this.innerWrap.addClassOnOver(this.overCls);
		this.innerWrap.addClassOnClick(this.mouseDownCls);
		this.innerWrap.on('click', this.onClick, this);
		//this.innerWrap.on('keyup', this.onKeyUp, this);
	},
	onFocus: function(e) {
		Ext.form.Checkbox.superclass.onFocus.call(this, e);
		//this.el.addClass(this.focusCls);
		this.innerWrap.addClass(this.focusCls);
	},
	onBlur: function(e) {
		Ext.form.Checkbox.superclass.onBlur.call(this, e);
		//this.el.removeClass(this.focusCls);
		this.innerWrap.removeClass(this.focusCls);
	},
	onClick: function(e){
		if (e.getTarget().htmlFor != this.el.dom.id) {
			if (e.getTarget() !== this.el.dom) {
				this.el.focus();
			}
			if (!this.disabled && !this.readOnly) {
				this.toggleValue();
			}
		}
		//e.stopEvent();
	},
	onEnable: Ext.form.Checkbox.superclass.onEnable,
	onDisable: Ext.form.Checkbox.superclass.onDisable,
	onKeyUp: undefined,
	setValue: function(v) {
		var checked = this.checked;
		this.checked = (v === true || v === 'true' || v == '1' || String(v).toLowerCase() == 'on');
		if(this.rendered){
			this.el.dom.checked = this.checked;
			this.el.dom.defaultChecked = this.checked;
			//this.wrap[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
			this.imageEl[this.checked ? 'addClass' : 'removeClass'](this.checkedCls);
		}
		if(checked != this.checked){
			this.fireEvent("check", this, this.checked);
			if(this.handler){
				this.handler.call(this.scope || this, this, this.checked);
			}
		}
	},
	getResizeEl: function() {
		//if(!this.resizeEl){
			//this.resizeEl = Ext.isSafari ? this.wrap : (this.wrap.up('.x-form-element', 5) || this.wrap);
		//}
		//return this.resizeEl;
		return this.wrap;
	}
});
Ext.override(Ext.form.Radio, {
	checkedCls: 'x-form-radio-checked'
});
