/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('TargetTrak.view.main.Main', {
    extend: 'Ext.container.Container',
    requires: [
        'TargetTrak.view.main.MainController',
        'TargetTrak.view.main.MainModel',
        'TargetTrak.view.menu.TargetTrakMenu',
        'TargetTrak.view.layout.CenterRegion'
    ],

    xtype: 'app-main',
    
    controller: 'main',
    viewModel: {
        type: 'main'
    },

    layout: {
        type: 'border'
    },

    items: [{
        xtype: 'panel',
        bind: {
            title: '<b>{name} Navigation</b>'
        },
        region: 'west',
        width: 250,
        collapsible: true,
        split: true,
        items: [
            {
                xtype: 'view.menu.targettrakmenu'
            }
        ]
    },{
        region: 'center',
        xtype: 'view.layout.centerregion'
    }]
});
