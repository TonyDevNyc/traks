Ext.define('TargetTrak.view.referencedata.create.CreateReferenceDataForm', {
	
	extend : 'Ext.form.Panel',
	
	alias : 'widget.referencedata.create.createreferencedataform',
	
	itemId : 'createReferenceData',
	
	requires : [ 
		'Ext.form.Panel',
		'Ext.layout.container.Anchor',
		'TargetTrak.view.common.StatusCombo'
    ],
    
    title: '<b>Create Reference Data</b>',
	
    bodyStyle : 'padding:40px 325px 0',
    
	width : 1000,
    
    items: [
        {
        	xtype: 'displayfield',
        	fieldLabel: '<strong>To create a Reference Data Item, enter all of the fields below.</strong>',
        	labelStyle: 'padding: 0 100 0 0',
        	labelWidth: 700,
        	labelSeparator: ''
        },
        {
        	xtype: 'textfield',
        	fieldLabel: '<b>Type</b>',
        	labelWidth: 150,
            name: 'type',
            msgTarget: 'side'
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
        }
    ],
    
    buttonAlign: 'center',
    
    buttons: [
        {
        	text: 'Create',
        	name: 'createReferenceDataBtn',
        	itemId : 'createReferenceDataBtn'
        }, 
        {
        	text: 'Cancel',
        	name: 'backToResultsBtn',
        	itemId : 'backToResultsBtn',
        	handler : function() {
        		Ext.Msg.show({
        		    title:'Cancel Create?',
        		    message: 'You are leaving the Create Reference Data page that may have unsaved changes. Are you sure you want to cancel?',
        		    buttons: Ext.Msg.YESNOCANCEL,
        		    icon: Ext.Msg.QUESTION,
        		    fn: function(btn) {
        		        if (btn === 'yes') {
        		        	alert('fire event to searchReferenceDataView');
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