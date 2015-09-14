/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */
Ext.application({
    name: 'TargetTrak',

    extend: 'TargetTrak.Application',
    
    autoCreateViewport: 'TargetTrak.view.main.Main',
	
    //-------------------------------------------------------------------------
    // Most customizations should be made to TargetTrak.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------
    requires: [
       // 'TargetTrak.controller.App'
        //,'TargetTrak.controller.ReferenceDataController'
       'TargetTrak.config.ScreenToken'
    ],
    
    stores: [
	    
    ],
    
    controllers: [
        'App'
        ,'ReferenceDataController'  
    ],
    
    init: function() {
        // start the mask on the body
        Ext.getBody().mask('Loading TargetTrak, Please wait...');
    },
    
    launch: function() {
    	var me = this;
    	
    	// Application Load Mask 
    	Ext.tip.QuickTipManager.init();
        
       	var task = new Ext.util.DelayedTask(function() {
        	Ext.getBody().unmask();
       	});
       	task.delay(2000);
       	
       	// Initializes History component to track token and page requests (back-button & app state)
        Ext.util.History.init(function(){
            var hash = document.location.hash;
            me.getAppController().fireEvent('tokenchange', hash.replace('#', '' ));
        });
        
        // add change handler for Ext.util.History; when a change in the token
        // occurs, this will fire our controller's event to load the appropriate content
        Ext.util.History.on('change', function(token){
            me.getAppController().fireEvent('tokenchange', token);
        });
    }    
});