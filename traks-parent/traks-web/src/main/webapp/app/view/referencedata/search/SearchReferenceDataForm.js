Ext.define('TargetTrak.view.referencedata.search.SearchReferenceDataForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.referencedata.search.searchreferencedataform',
	requires : [ 
		'Ext.form.Panel',
		'Ext.layout.container.Anchor'
    ],
    title: '<b>Search Reference Data</b>',
	bodyStyle : 'padding:10px 10px 0',
	width : 1000,
	height: 100,
	layout: 'column',
	items: [
        {
        	xtype: 'textfield',
        	name: 'text',
        	columnWidth: 0.90,
        	margin: '0 10 0 0',
        	emptyText: 'Please enter search text ...'
		},
		{
			xtype: 'button',
			text: 'Search',
        	name: 'searchReferenceData',
        	action: 'searchReferenceData',
        	columnWidth: 0.08
        		
		}
	]
});