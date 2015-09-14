Ext.define('TargetTrak.view.referencedata.edit.EditReferenceDataForm', {
	
	extend : 'Ext.form.Panel',
	
	alias : 'widget.referencedata.edit.editreferencedataform',
	
	itemId : 'editReferenceData',
	
	requires : [ 
		'Ext.form.Panel',
		'Ext.layout.container.Anchor',
		'TargetTrak.view.common.StatusCombo'
    ],
    
    title: '<b>Edit Reference Data</b>',
	
    bodyStyle : 'padding:25px 350px 0',
    
	width : 1000,
    
    items: [
        {
        	xtype: 'hidden',
        	name: 'id'
        },
        {
        	xtype: 'hidden',
        	name: 'version'
        },
        {
        	xtype: 'textfield',
        	fieldLabel: '<b>Type</b>',
        	labelWidth: 150,
            name: 'type',
            readOnly: true
        }, {
        	xtype: 'textfield',
        	fieldLabel: '<b>Label</b>',
        	labelWidth: 150,
            name: 'label',
            msgTarget: 'side'
        }, {
        	xtype: 'textfield',
        	fieldLabel: '<b>Value</b>',
        	labelWidth: 150,
            name: 'value',
            msgTarget: 'side'
        }, {
        	xtype: 'common.statuscombo',
        	fieldLabel: '<b>Status</b>',
        	labelWidth: 150,
            name: 'status',
            msgTarget: 'side'
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
        	text: 'Save',
        	name: 'saveReferenceDataBtn',
        	itemId : 'saveReferenceDataBtn'
        }, 
        {
        	text: 'Cancel',
        	name: 'backToResultsBtn',
        	itemId : 'backToResultsBtn',
        	handler : function() {
        		Ext.Msg.show({
        		    title:'Cancel Changes?',
        		    message: 'You are leaving the Edit Reference Data page that may have unsaved changes. Are you sure you want to cancel?',
        		    buttons: Ext.Msg.YESNOCANCEL,
        		    icon: Ext.Msg.QUESTION,
        		    fn: function(btn) {
        		        if (btn === 'yes') {
        		        	Ext.util.History.back();
        		        } else if (btn === 'no') {
        		            console.log('No pressed');
        		        } else {
        		            console.log('Cancel pressed');
        		        } 
        		    }
        		});
        	}
        }
    ]
});