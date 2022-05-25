/*
 * File: DateTimePicker.js
 *
 * This file requires use of the Ext JS library, under independent license.
 * This is part of the UX for DateTimeField developed by Guilherme Portela
 *
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Guilherme Lopes Portela
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
 
Ext.define('Ext.ux.DateTimePicker', {
    extend: 'Ext.picker.Date',
    alias: 'widget.datetimepicker',
    requires: [
        'Ext.picker.Date',
        'Ext.slider.Single',
        'Ext.form.field.Time',
        'Ext.form.Label'
    ],
    
    // <locale>
    // </locale>
    // <locale>
    /**
     * @cfg {String} hourText
     * The default text displayed above the hour slider
     */
    hourText: 'Hour',
    // </locale>
    // <locale>
    /**
     * @cfg {String} minuteText
     * The default text displayed above the minute slider
     */
    minuteText : 'Minutes',
    // </locale>
    
    amText: 'AM',
    
    pmText: 'PM',

    initEvents: function() {
        var me = this,
            eDate = Ext.Date,
            day = eDate.DAY;

        Ext.apply(me.keyNavConfig,{
            up: function(e) {
                if (e.ctrlKey) {
                    if (e.shiftKey) {
                        me.minuteSlider.setValue(me.minuteSlider.getValue() + 1);
                    } else {
                        me.showNextYear();
                    }
                } else {
                    if (e.shiftKey) {
                        me.hourSlider.setValue(me.hourSlider.getValue() + 1);
                    } else {
                        me.update(eDate.add(me.activeDate, day, - 7));
                    }
                }
            },

            down: function(e) {
                if (e.ctrlKey) {
                    if (e.shiftKey) {
                        me.minuteSlider.setValue(me.minuteSlider.getValue() - 1);
                    } else {
                        me.showPrevYear();
                    }
                } else {
                    if (e.shiftKey) {
                        me.hourSlider.setValue(me.hourSlider.getValue() - 1);
                    } else {
                        me.update(eDate.add(me.activeDate, day, 7));
                    }
                }
            }
        });
        me.callParent();
    },

    initComponent: function() {
        var me = this,
            dtAux = me.value ? new Date(me.value) : new Date();

        dtAux.setSeconds(0);
        
        me.timeFormat = me.format.indexOf('h') !== -1 ? 'h' : 'H';
        me.hourSlider = new Ext.slider.Single({
            fieldLabel: me.hourText,
            labelAlign: 'top',
            labelSeparator: ' ',
            padding: '0 0 10 17',
            focusable : false,
            value: 0,
            minValue: 0,
            maxValue: 23,
            vertical: true,
            listeners: {
                change: me.changeTimeValue,
                scope: me
            }
        });

        me.minuteSlider = new Ext.slider.Single({
            fieldLabel: me.minuteText,
            labelAlign: 'top',
            labelSeparator: ' ',
            padding: '0 10 10 0',
            focusable : false,
            value: 0,
            increment: 1,
            minValue: 0,
            maxValue: 59,
            vertical: true,
            listeners: {
                change: me.changeTimeValue,
                scope: me
            }
        });
        
        me.callParent();
        me.setValue(new Date(dtAux));
    },

    afterRender: function() {
        var me = this;

        me.timePicker = Ext.create('Ext.panel.Panel', {
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            border: false,
            defaults: {
                flex: 1
            },
            width: 130,
            floating: true,
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui: 'header',
                cls: 'x-datepicker-header',
                items: [
                    '->', {
                        xtype: 'label',
                        ui: 'footer',
                        text: me.timeFormat === 'h' ? '12:00 ' + me.amText : '00:00',
                        cls: 'x-menu-item-text',
                        padding: '5px 0 5px 0',
						style: {
							'color':'white'				
						}
                    },
                    '->'
                ]
            }],
            items: [me.hourSlider, me.minuteSlider],
            onMouseDown: function(e) {
                e.preventDefault();
            },
        });

        me.callParent();
    },

    handleTabClick: function (e) {
        this.handleDateClick(e, this.activeCell.firstChild, true);
    },

    getSelectedDate: function (date) {
        var me = this,
            t = Ext.Date.clearTime(date,true).getTime(),
            cells = me.cells,
            cls = me.selectedCls,
            cellItems = cells.elements,
            cLen = cellItems.length,
            cell, c;

        cells.removeCls(cls);

        for (c = 0; c < cLen; c++) {
            cell = cellItems[c].firstChild;
            if (cell.dateValue === t) {
                return cell;
            }
        }
        return null;
    },

    changeTimeValue: function(slider, e, eOpts) {
        var me = this,
            label = me.timePicker.down('label'),
            minutePrefix = me.minuteSlider.getValue() < 10 ? '0' : '',
            hourDisplay = me.hourSlider.getValue(),
            pickerValue, hourValue, minuteValue, hourPrefix, timeSufix, auxValue;

        if (me.timeFormat == 'h') {
            timeSufix = me.hourSlider.getValue() < 12 ? ' ' + me.amText : ' ' + me.pmText;
            hourDisplay = me.hourSlider.getValue() < 13 ? hourDisplay : hourDisplay - 12;
            hourDisplay = hourDisplay || '12';
        }

        hourPrefix = hourDisplay < 10 ? '0' : '';

        label.setText(hourPrefix + hourDisplay + ':' + minutePrefix + me.minuteSlider.getValue() + (timeSufix || ''));

        if (me.pickerField && (pickerValue = me.pickerField.getValue())) {
            hourValue = me.hourSlider.getValue();
            minuteValue = me.minuteSlider.getValue();
            auxValue = new Date(pickerValue.setHours(hourValue, minuteValue));
            me.pickerField.setValue(auxValue);
        }
    },

    onShow: function() {
        var me = this;
        me.showTimePicker();
        me.callParent();
    },

    showTimePicker: function() {
        var me = this,
            el = me.el;

        Ext.defer(function() {
            var body = Ext.getBody(),
                bodyWidth = body.getViewSize().width,
                alignTo = (bodyWidth < (el.getX() + el.getWidth() + 140)) ? 'tl' : 'tr',
                xPos = alignTo == 'tl' ? -135 : 5,
                backgroundColor, toolbar;

            me.timePicker.setHeight(el.getHeight());
            me.timePicker.showBy(me, alignTo, [xPos, 0]);

            toolbar = me.timePicker.down('toolbar').getEl();
            backgroundColor = toolbar.getStyle('background-color');
            if (backgroundColor == 'transparent') {
                toolbar.setStyle('background-color', toolbar.getStyle('border-color'));
            }
        }, 1);
    },

    onHide: function() {
        var me = this;
        me.timePicker.hide();
        me.callParent();
    },

    beforeDestroy: function() {
        var me = this;

        if (me.rendered) {
            Ext.destroy(
                me.timePicker,
                me.minuteSlider,
                me.hourSlider
            );
        }
        me.callParent();
    },

    setValue: function(value) {
        value = value || new Date();
        value.setSeconds(0);
        this.value = new Date(value);
        return this.update(this.value);
    },

    selectToday: function() {
        var me = this,
            btn = me.todayBtn,
            handler = me.handler,
            auxDate = new Date();

        if (btn && !btn.disabled) {
            me.setValue(new Date(auxDate.setSeconds(0)));
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
        return me;
    },

    handleDateClick: function(e, t, /*private*/ blockStopEvent) {
        var me = this,
            handler = me.handler,
            hourSet = me.timePicker.items.items[0].getValue(),
            minuteSet = me.timePicker.items.items[1].getValue(),
            auxDate = new Date(t.dateValue);

        if(blockStopEvent !== true) {
            e.stopEvent();
        }

        if (!me.disabled && t.dateValue && !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)) {
            me.doCancelFocus = me.focusOnSelect === false;
            auxDate.setHours(hourSet, minuteSet, 0);
            me.setValue(new Date(auxDate));
            delete me.doCancelFocus;
            me.fireEvent('select', me, me.value);
            if (handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
    },

    selectedUpdate: function(date) {
        var me = this,
            dateOnly = Ext.Date.clearTime(date, true),
            currentDate = (me.pickerField && me.pickerField.getValue()) || new Date();

        this.callParent([dateOnly]);

        if (currentDate) {
            Ext.defer(function() {
                me.hourSlider.setValue(currentDate.getHours());
                me.minuteSlider.setValue(currentDate.getMinutes());
            }, 10);

        }
    },

    fullUpdate: function(date) {
        var me = this,
            dateOnly = Ext.Date.clearTime(date, true),
            currentDate = (me.pickerField && me.pickerField.getValue()) || new Date();

        this.callParent([dateOnly]);

        if (currentDate) {
            Ext.defer(function() {
                me.hourSlider.setValue(currentDate.getHours());
                me.minuteSlider.setValue(currentDate.getMinutes());
            }, 10);
        }
    }
});