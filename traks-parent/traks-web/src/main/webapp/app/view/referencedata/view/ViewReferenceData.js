Ext.define('TargetTrak.view.referencedata.view.ViewReferenceData', {
	
	extend : 'Ext.form.Panel',
	
	alias : 'widget.referencedata.view.viewreferencedata',
	
	itemId : 'viewReferenceData',
	
	requires : [ 
		'Ext.form.Panel',
		'Ext.layout.container.Anchor'
    ],
    
    title: '<b>View Reference Data</b>',
	
    bodyStyle : 'padding:25px 350px 0',
    
	width : 1000,
	
    items: [
        {
        	xtype: 'hidden',
        	name: 'id'
        },
        {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Type</b>',
        	labelWidth: 150,
            name: 'type'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Label</b>',
        	labelWidth: 150,
            name: 'label'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Value</b>',
        	labelWidth: 150,
            name: 'value'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Status</b>',
        	labelWidth: 150,
            name: 'status'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Created By</b>',
        	labelWidth: 150,
            name: 'createdBy'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Created Date</b>',
        	labelWidth: 150,
            name: 'createdDateTime'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Last Updated By</b>',
        	labelWidth: 150,
            name: 'lastUpdatedBy'
        }, {
        	xtype: 'displayfield',
        	fieldLabel: '<b>Last Updated Date</b>',
        	labelWidth: 150,
            name: 'lastUpdatedDateTime'
        }
    ],
    
    buttonAlign: 'center',
    
    buttons: [
        {
        	text: 'Back to Results',
        	name: 'backToResultsBtn',
        	itemId : 'backToResultsBtn',
        	handler: function() {
        		 Ext.util.History.back();
        	}
        }
    ]
});