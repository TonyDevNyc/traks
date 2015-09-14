Ext.define('TargetTrak.controller.App', {
    
	extend: 'Ext.app.Controller',
    
	id: 'App',
	
	requires: [
	    'TargetTrak.view.referencedata.search.SearchReferenceData',
	    'TargetTrak.view.referencedata.view.ViewReferenceData',
	    'TargetTrak.view.referencedata.create.CreateReferenceDataForm',
	    'TargetTrak.view.referencedata.import.ImportReferenceDataForm'
	],
	
    views: [ 
        'TargetTrak.view.referencedata.search.SearchReferenceData',
        'TargetTrak.view.referencedata.view.ViewReferenceData'
    ],
    
    refs: [
       { ref: 'Menu', selector: '[xtype=view.menu.targettrakmenu]' },
       { ref: 'CenterRegion', selector: '[xtype=view.layout.centerregion]' }
    ],
    
    init: function() {
    	this.application.on('viewReferenceData', this.handleScreenChange);
    	this.application.on('editReferenceData', this.handleScreenChange);
    	
        this.listen({
            controller: {
                '#App': {
                    tokenchange: this.dispatch
                }
            },
            component: {
				'menuitem#searchReferenceData' : { click : this.addHistory },
            	'menuitem#createReferenceData' : { click : this.addHistory },
            	'menuitem#importReferenceData' : { click : this.addHistory }
            },
            global: {},
            store: {}  
        });
    },
    
    handleScreenChange : function(screenToken) {
    	var me = this;
    	console.log('handleScreenChange -> ScreenToken -> itemId:' + screenToken.getItemId() + ' recordId: ' + screenToken.getRecordId());
    	me.getAppController().getController('App').addHistory(screenToken.getItemId(), null, null, screenToken);
    },
    
	addHistory: function(item, e, opts, screenToken) {
	    var me = this,
	    token = (item.itemId == null) ? item : item.itemId;
	    Ext.util.History.add(token);
	    me.fireEvent('tokenchange', token, screenToken);
	},
	
	dispatch: function(token, screenToken) {
		Ext.getBody().mask('Loading Requested Page, Please wait...');
        var me = this, config;
        
        switch(token) {
            
        	case 'createReferenceData':
        		config = { xtype: 'referencedata.create.createreferencedataform' }; 
        		break;
        		
            case 'searchReferenceData' : 
            	config = { xtype: 'referencedata.search.searchreferencedata' }; 
            	break;
            
            case 'viewReferenceData':	
            	config = Ext.create('TargetTrak.view.referencedata.view.ViewReferenceData');
            	config.getForm().load({ 	
            		url:'/traks-web/referencedata/getReferenceDataItem.json',
            		method: 'GET',
    				params:{ id: screenToken.getRecordId() }
    			});
            	break;
            	
            case 'editReferenceData':	
            	config = Ext.create('TargetTrak.view.referencedata.edit.EditReferenceDataForm');
            	config.getForm().load({ 	
            		url:'/traks-web/referencedata/getReferenceDataItem.json',
            		method: 'GET',
    				params:{ id: screenToken.getRecordId() }
    			});
            	break;
            
            case 'importReferenceData':	
            	config = { xtype: 'referencedata.import.importreferencedataform' };
            	break;

            default : 
            	config = 
            	{ 
            		xtype: 'panel', 
            		title: 'Under Construction',
            		html: '<strong>Page under construction, please try again later</strong>'
            	}; 
        		break;
        }
        me.updateCenterRegion(config);
        
        var task = new Ext.util.DelayedTask(function() {
        	Ext.getBody().unmask();
       	});
       	task.delay(500);
    },
    
    updateCenterRegion: function(config) {
        var me = this;
        center = me.getCenterRegion();

        // remove all existing content
        center.removeAll(true);
        // add new content
        center.add(config);    
    }
});