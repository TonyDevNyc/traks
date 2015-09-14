Ext.define('TargetTrak.controller.ReferenceDataController', {
	
	extend : 'Ext.app.Controller',
	
	id: 'referenceDataController',
	
	requires : [
        'TargetTrak.view.referencedata.search.SearchReferenceData'
    ],

	stores : [ 'ReferenceDataItems', 'Statuses'],

	models : [ 'ReferenceDataItem', 'Status'],

	views : [ 
        'referencedata.search.SearchReferenceData',
        'referencedata.view.ViewReferenceData',
        'referencedata.edit.EditReferenceDataForm',
        'referencedata.create.CreateReferenceDataForm',
        'common.StatusCombo'
    ],

	refs : [ 
		{ ref : 'searchReferenceDataForm', 		selector : '[xtype=referencedata.search.searchreferencedataform]' },
		{ ref : 'searchReferenceDataButton', 	selector : 'button[name="searchReferenceData"]' },
		{ ref : 'editReferenceDataForm', 		selector : '[xtype=referencedata.edit.editreferencedataform]'},
		{ ref : 'createReferenceDataForm', 		selector : '[xtype=referencedata.create.createreferencedataform]'},
		{ ref : 'importReferenceDataForm', 		selector : '[xtype=referencedata.import.importreferencedataform]'}	
    ],
	
	onLaunch : function() {
	},

	init : function() {
		this.getStatusesStore().load({ params : { type : 'status' }});
		
		this.control({
            'button[name="searchReferenceData"]': { click : this.searchReferenceData },
            
            'button[name="saveReferenceDataBtn"]': { click : this.updateReferenceData },
            
            'button[name="createReferenceDataBtn"]': { click : this.createReferenceData },
            
            'button[name="uploadReferenceDataBtn"]': {click : this.importReferenceData }
        });
		
	}, 
	
	importReferenceData : function() {
		var me = this;
		var form = me.getImportReferenceDataForm().getForm();
		
		form.submit({
			url: '/traks-web/referencedata/importReferenceData.json',
			
			
			waitMsg: 'Importing Reference Data. Please wait...',
			success: function(form, action) {
				Ext.Msg.alert('Import Reference Data Success', action.result.message);
			},
			failure: function(form, action) {
				
				Ext.Msg.alert('Import Reference Data Error', action.result.message);
			}
		});
	},
	
	createReferenceData : function() {
		var me = this;
		var form = me.getCreateReferenceDataForm().getForm();
		
		form.submit({
			submitEmptyText: true,
			url: '/traks-web/referencedata/createReferenceData.json',
			method: 'POST',
			waitMsg: 'Creating Reference Data. Please wait...',
			success: function(form, action) {
				Ext.example.msg('Create Reference Data Success', 'Reference Data Item has been created successfully.');
				var screenToken = Ext.create('TargetTrak.config.ScreenToken');
                screenToken.setItemId('viewReferenceData');
                screenToken.setRecordId(action.result.data.id);
                TargetTrak.app.fireEvent('viewReferenceData', screenToken);
			},
			failure: function(form, action) {
				form.markInvalid(action.result.errors);
				Ext.Msg.alert('Create Reference Data Error', action.result.message);
			}
		});
	},
	
	updateReferenceData : function() {
		var me = this;
		var form = me.getEditReferenceDataForm().getForm();
		form.submit({
			submitEmptyText: true,
			url : '/traks-web/referencedata/updateReferenceData.json',
			method: 'POST',
			waitMsg: 'Updating Reference Data. Please wait...',
			success: function(form, action) {
				Ext.example.msg('Update Reference Data', 'Reference Data Item has been updated successfully.');
				form.load({ 	
            		url:'/traks-web/referencedata/getReferenceDataItem.json',
            		method: 'GET',
    				params:{ id: form.findField('id').getValue() }
    			});
			},
			failure: function(form, action) {
				form.markInvalid(action.result.errors);
				Ext.Msg.alert('Edit Reference Data Error', action.result.message);
			}
		});
	},
	
	searchReferenceData : function() {
		var me = this;
		var form = me.getSearchReferenceDataForm().getForm();
		form.submit({
			submitEmptyText: true,
			url : '/traks-web/referencedata/searchReferenceData.json',
//    			headers : {
//					'X-CSRF-TOKEN': TGT.security.CsrfToken.getCsrfToken()
//				},
			params : {
				page: 1,
				start : 0,
				limit : 20
			},
			method : 'POST',
            waitMsg : 'Searching Please Wait...',
            success : function(form, action) {
            	var referenceDataStore = me.getReferenceDataItemsStore();
            	referenceDataStore.loadData(action.result.data);
            	referenceDataStore.totalCount = action.result.totalSize;
            	Ext.ComponentQuery.query('pagingtoolbar')[0].onLoad();
            },
            failure : function(form, action) {
            	Ext.Msg.alert('Reference Data Search Error', 'Please contact your system administrator');
            }
		});
	}
});