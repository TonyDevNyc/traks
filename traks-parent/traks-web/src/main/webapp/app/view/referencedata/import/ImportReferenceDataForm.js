Ext.define('TargetTrak.view.referencedata.import.ImportReferenceDataForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.referencedata.import.importreferencedataform',
	requires : [ 
		'Ext.form.Panel'
    ],
    title: '<b>Import Reference Data</b>',
	bodyStyle : 'padding:10px 10px 0',
	width : 1000,
	height: 100,
	items: [
        {
        	 xtype: 'filefield',
             name: 'file',
             fieldLabel: 'Reference Data File',
             labelWidth: 150,
             msgTarget: 'side',
             allowBlank: false,
             anchor: '70%',
             buttonText: 'Browse'
		}
	],
	
	buttonAlign: 'center',
    
    buttons: [
        {
        	text: 'Upload',
        	name: 'uploadReferenceDataBtn',
        	itemId : 'uploadReferenceDataBtn'
        }
    ]
});