Ext.define('TargetTrak.view.referencedata.search.SearchReferenceData', {
	extend : 'Ext.container.Container',
	alias : 'widget.referencedata.search.searchreferencedata',
	requires : [
        'TargetTrak.view.referencedata.search.ReferenceDataGrid',
        'TargetTrak.view.referencedata.search.SearchReferenceDataForm'
	],
	autoScroll: true,
	items: [
		{
			xtype: 'container',
			layout: 'vbox', 
			width: 1000,
	        items: [
				{
				    xtype: 'referencedata.search.searchreferencedataform',
				    flex: 1
				},
				{
				    xtype: 'referencedata.search.referencedatagrid',
				    flex: 1
				}
            ]
		}    
    ]
});