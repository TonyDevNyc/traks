Ext.define('TargetTrak.view.layout.CenterRegion', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.view.layout.centerregion',
	layout: 'card',
    items: [
        {
        	xtype: 'panel',
        	html: 'Welcome to the Target Trak',
        	activeItem: 0
        }
    ]
});