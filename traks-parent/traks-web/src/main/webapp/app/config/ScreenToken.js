Ext.define('TargetTrak.config.ScreenToken', {
	
	singleton : false,
	
	config : {
		itemId : '',
		
		recordId : ''
    },
    
    constructor : function(config) {
    	this.initConfig(config);
    },
    
    getItemId : function() {
    	return this.itemId;
    },
    
    setItemId : function(newItemId) {
    	this.itemId = newItemId;
    },
    
    getRecordId : function() {
    	return this.recordId;
    },
    
    setRecordId : function(newRecordId) {
    	this.recordId = newRecordId;
    }
});