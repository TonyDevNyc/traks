Ext.define('TargetTrak.store.Statuses', {
	extend : 'Ext.data.Store',
	model : 'TargetTrak.model.Status',
	proxy : {
		type : 'ajax',
		url : '/traks-web/referencedata/getReferenceDataByType.json',
		reader : {
			type : 'json',
			rootProperty : 'data'
		},
		fields: [ 
          	{name: 'value'}, 
          	{name: 'label'}
	    ],
		listeners : {
			exception : function(proxy, response, operation) {
				Ext.MessageBox.show({
					title : 'Target-Trak System Error',
					msg : response.message,
					icon : Ext.MessageBox.ERROR,
					buttons : Ext.Msg.OK
				});
			}
		}
	}
});