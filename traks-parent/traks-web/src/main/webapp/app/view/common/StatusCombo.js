Ext.define('TargetTrak.view.common.StatusCombo', {
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.common.statuscombo',
	requires: [ 
        'Ext.form.field.ComboBox'
    ],
    store: 'Statuses',
    fieldLabel: 'Status',
    displayField: 'label',
    valueField: 'value',
    typeAhead: true,
    emptyText: '',
    queryMode: 'local',
    width: 250   
});