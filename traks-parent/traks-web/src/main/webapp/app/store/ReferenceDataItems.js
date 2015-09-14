Ext.define('TargetTrak.store.ReferenceDataItems', {
	extend : 'Ext.data.Store',
	model : 'TargetTrak.model.ReferenceDataItem',
	pageSize: 20,
	proxy : {
		sortParam: 'sortField',
		type : 'ajax',
		url : '/traks-web/referencedata/getReferenceData.json',
		reader : {
			type : 'json',
			rootProperty : 'data',
			idProperty : 'id',
			successProperty : 'success',
			totalProperty: 'totalSize'
		},
		listeners : {
			exception : function(proxy, response, operation) {
				Ext.MessageBox.show({
					title : 'Target-Trak System Error',
					msg : 'Error getting reference data',
					icon : Ext.MessageBox.ERROR,
					buttons : Ext.Msg.OK
				});
			}
		}
	}
});