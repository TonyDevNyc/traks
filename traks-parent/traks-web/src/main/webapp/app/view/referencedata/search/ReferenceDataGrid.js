Ext.define('TargetTrak.view.referencedata.search.ReferenceDataGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.referencedata.search.referencedatagrid',
	store : 'ReferenceDataItems',
	itemId : 'referenceDataGrid',
	loadMask : true,
	autoScroll : true,
	width : 1000,
	viewConfig : {
		enableTextSelection : true
	},
	listeners: {
		render : function(grid) {
        	grid.getStore().load({ params : { text : '' }});
        }
    },
	columns : [ 
	     {
           text: 'View',
           align: 'center',
           stopSelection: true,
           xtype: 'widgetcolumn',
           width: 65,
           widget: {
                  xtype: 'button',
                  _btnText: "View",
                  _name: 'viewReferenceDataBtn',
                  defaultBindProperty: null, //important
                  handler: function(widgetColumn) {
                        var record = widgetColumn.getWidgetRecord();
                        var screenToken = Ext.create('TargetTrak.config.ScreenToken');
                        screenToken.setItemId('viewReferenceData');
                        screenToken.setRecordId(record.get('id'));
                        TargetTrak.app.fireEvent('viewReferenceData', screenToken);
                  },
                  listeners: {
                        beforerender: function(widgetColumn) {
                            widgetColumn.setText( widgetColumn._btnText ); //can be mixed with the row data if needed
                        }
                  }
            }
	    },
	    {
	    	text: 'Edit',
	    	align: 'center',
	    	stopSelection: true,
	    	xtype: 'widgetcolumn',
	    	width: 65,
	    	widget: {
	    		xtype: 'button',
	    		_btnText: "Edit",
	    		_name: 'viewReferenceDataBtn',
	    		defaultBindProperty: null, //important
	    		handler: function(widgetColumn) {
	    			var record = widgetColumn.getWidgetRecord();
	    			var screenToken = Ext.create('TargetTrak.config.ScreenToken');
	    			screenToken.setItemId('editReferenceData');
	    			screenToken.setRecordId(record.get('id'));
	    			TargetTrak.app.fireEvent('editReferenceData', screenToken);
	    		},
                listeners: {
                    beforerender: function(widgetColumn) {
                        widgetColumn.setText( widgetColumn._btnText ); //can be mixed with the row data if needed
                    }
                }
        	}
	    },
		{
			xtype : 'gridcolumn',
			dataIndex : 'type',
			text : 'Type',
			width : 175
		},
		{
			xtype : 'gridcolumn',
			dataIndex : 'label',
			text : 'Label',
			width : 225
		},
		{
			xtype : 'gridcolumn',
			dataIndex : 'value',
			text : 'Value',
			width : 225
		},
		{
			xtype : 'gridcolumn',
			dataIndex : 'status',
			text : 'Status',
			width : 200
		}
	]
    ,
	bbar : [ 
        {
        	xtype : 'pagingtoolbar',
        	store : 'ReferenceDataItems',
        	displayInfo : true,
        	displayMsg : 'Displaying {0} - {1} of {2}',
        	width : 1000
        } 
    ]
});