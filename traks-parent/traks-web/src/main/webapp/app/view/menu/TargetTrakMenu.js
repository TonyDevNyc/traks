Ext.define('TargetTrak.view.menu.TargetTrakMenu', {
	extend : 'Ext.menu.Menu',
	alias : 'widget.view.menu.targettrakmenu',
	floating : false,
	items : [ 
	    {
	    	xtype : 'menuitem',
	    	text : 'Welcome username',
	    	iconCls : 'icon-home',
	    	iconAlign : 'left',
	    	displayOrder : 1,
	    	menu : {
				xtype : 'menu',
				width : 150,
				items : [ 
				    {
				    	displayOrder : 1,
				    	text : 'Home',
				    	iconCls : 'icon-home',
				    	itemId : 'homePageItem'
				    }, 
				    {    xtype: 'menuseparator' },
				    {
						displayOrder : 2,
						text : 'User Profile',
						iconCls : 'icon-user-profile',
						itemId : 'userProfileItem'
				    }, 
				    {    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'User Settings',
						iconCls : 'icon-user-settings',
						itemId : 'userSettingsItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 4,
						text : 'Logout',
						iconCls : 'icon-logout',
						itemId : 'logoutItem'
					},
					{    xtype: 'menuseparator' }
				]
	    	}, 
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Matters',
			iconCls : 'icon-matter',
			iconAlign : 'left',
			displayOrder : 2,
			menu : {
				xtype : 'menu',
				items : [ 
		            {
						displayOrder : 1,
						text : 'Search Matter',
						iconCls : 'icon-search',
						itemId : 'searchMatterItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 2,
						text : 'Create Process Service',
						iconCls : 'icon-create-matter',
						itemId : 'createMatterItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'Generate Affidavit',
						iconCls : 'icon-gen-matter-aff',
						itemId : 'generateAffidavitItem'
					} 
				]
			}
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Tasks',
			iconCls : 'icon-tasks',
			iconAlign : 'left',
			displayOrder : 3,
			menu : {
				xtype : 'menu',
				items : [ 
		            {
		            	displayOrder : 1,
		            	text : 'Search Task',
		            	iconCls : 'icon-search',
		            	itemId : 'searchTaskItem'
		            }, 
		            {    xtype: 'menuseparator' },
		            {
						displayOrder : 2,
						text : 'Create Task',
						iconCls : 'edit-icon',
						itemId : 'createTaskItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'Delete Task',
						iconCls : 'edit-icon',
						itemId : 'deleteTaskItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 4,
						text : 'View Task Audit Log',
						iconCls : 'edit-icon',
						itemId : 'viewTaskAuditLogItem'
					} 
				]
			}
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Reports',
			iconCls : 'icon-reports',
			iconAlign : 'left',
			displayOrder : 4,
			menu : {
				xtype : 'menu',
				items : [ 
		            {
						displayOrder : 1,
						text : 'Generate Matter Report',
						iconCls : 'edit-icon',
						itemId : 'searchTaskItem'
					} 
	            ]
			}
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Billing',
			iconCls : 'icon-billing',
			iconAlign : 'left',
			displayOrder : 5,
			menu : {
				xtype : 'menu',
				items : [ 
		            {
						displayOrder : 1,
						text : 'Create Billing Ledger',
						iconCls : 'edit-icon',
						itemId : 'createBillingItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 2,
						text : 'View Billing Ledger',
						iconCls : 'edit-icon',
						itemId : 'viewBillingTaskItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'Import Billing Data',
						iconCls : 'edit-icon',
						itemId : 'importBillingItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 4,
						text : 'Generate Billing Report',
						iconCls : 'edit-icon',
						itemId : 'generateBillingItem'
					},
					{    xtype: 'menuseparator' }
				]
			}
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Reference Data',
			iconCls : 'icon-ref-data',
			iconAlign : 'left',
			displayOrder : 6,
			menu : {
				xtype : 'menu',
				items : [ 
				    {
						displayOrder : 1,
						text : 'Search Reference Data',
						iconCls : 'icon-search',
						itemId : 'searchReferenceData'
					},
					{    xtype: 'menuseparator' },
					{
						displayOrder : 2,
						text : 'Create Reference Data',
						iconCls : 'icon-css',
						itemId : 'createReferenceData'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'Import Reference Data',
						iconCls : 'icon-css',
						itemId : 'importReferenceData'
					},
					{    xtype: 'menuseparator' }
				]
			}
	    }, 
	    {    xtype: 'menuseparator' },
	    {
			xtype : 'menuitem',
			text : 'Contact Group',
			iconCls : 'icon-contacts',
			iconAlign : 'left',
			displayOrder : 7,
			menu : {
				xtype : 'menu',
				items : [ 
				    {
						displayOrder : 1,
						text : 'Search Company',
						iconCls : 'icon-company',
						itemId : 'companyAdminItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 2,
						text : 'Create Company',
						iconCls : 'icon-css',
						itemId : 'createCompany'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'Search Contact',
						iconCls : 'icon-contact',
						itemId : 'contactAdminItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 4,
						text : 'Create Contact',
						iconCls : 'icon-css',
						itemId : 'createContact'
					},
					{    xtype: 'menuseparator' }
				]
			}
		}, 
		{    xtype: 'menuseparator' },
		{
			xtype : 'menuitem',
			text : 'Administration',
			iconCls : 'icon-sec-admin',
			iconAlign : 'left',
			displayOrder : 8,
			menu : {
				xtype : 'menu',
				items : [ 
				    {
						displayOrder : 1,
						text : 'View Audit Log',
						iconCls : 'edit-icon',
						itemId : 'viewAuditLogItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 2,
						text : 'View Roles',
						iconCls : 'edit-icon',
						itemId : 'viewRolesItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 3,
						text : 'View User Roles',
						iconCls : 'edit-icon',
						itemId : 'viewUserRolesItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 4,
						text : 'Create Role',
						iconCls : 'edit-icon',
						itemId : 'creteRoleItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 5,
						text : 'Assign Role Privileges',
						iconCls : 'edit-icon',
						itemId : 'assignRolePrivilegesItem'
					}, 
					{    xtype: 'menuseparator' },
					{
						displayOrder : 6,
						text : 'Assign User Roles',
						iconCls : 'edit-icon',
						itemId : 'assignUserRolesItem'
					},
					{    xtype: 'menuseparator' }
				]
			}
	} ]
});