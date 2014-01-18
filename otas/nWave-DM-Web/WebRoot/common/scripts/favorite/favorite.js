// Tracking target deviceID
var bundle4Favorite = new Bundle({messages: {
'zh':{
          fieldLabel:'收藏至',
          emptyText:'请选择...',
          submit: '确定',
          cancel: '取消',
          success: '收藏成功！',
          failure: '失败,该收藏夹下已存在此设备!',
          error: '您的登录状态已经超时, 请重新登录!',
          close:'关闭'
        },
   'en':{
          fieldLabel:'Favorite',
          emptyText:'Select a state...',
          submit: 'Submit',
          cancel: 'Cancel',
          success: 'Success!',
          failure: 'Failure, The favorites of this equipment has been in existence under!',
          error: 'Your login state has taken more time, please re-sign in!',
          close:'Close'
        }
}});

var __device_id_for_add_favorite = '';

function loadFavoriteOptions() {
	ds = new Ext.data.Store({
	    proxy: new Ext.data.HttpProxy({
	        url: './postAddFavorite.do'
	    }),
	    reader: new Ext.data.JsonReader({
	        root: 'root',
	        totalProperty: 'totalProperty',
	        id: 'id'
	    }, [
	        {name: 'id', mapping: 'id'},
	        {name: 'name', mapping: 'name'}
	    ]),
	    baseParams: {
		  method: 'getFavorites'
	    }
	});
	ds.load();
	return ds;
}
function initDevFavoritePanel(){
    var win;
    var button4AddDeviceFavorite = Ext.get('add_device_favorite_button');
    var firstPanel4DeviceFavorite = new Ext.FormPanel({
                    id: 'panel4DeviceFavorite',
                    labelWidth: 75, // label settings here cascade unless overridden
                    frame:true,
                    disabled: false,
                    hidden: false,
                    bodyStyle:'padding:20px 20px 0',
                    width: 350,
                    defaults: {width: 160},
                    items: [
                        new Ext.form.ComboBox({
                            id: 'combo4DeviceFavorite',
                            fieldLabel: bundle4Favorite.getValue('fieldLabel'),
                            hiddenName:'state',
                            valueField:'id',
                            displayField:'name',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            emptyText:bundle4Favorite.getValue('emptyText'),
                            selectOnFocus:true,
                            width:190,
                            editable:true
                        })
                    ],
                    
                    buttons: [{
                        text: bundle4Favorite.getValue('submit'),
                        handler  : function(){
                            var form = firstPanel4DeviceFavorite.getForm();
                            var combobox = firstPanel4DeviceFavorite.getComponent('combo4DeviceFavorite');
                            var favoriteName = combobox.getRawValue();
                            var favoriteId = combobox.getValue();
                            // 调用创建Device Favorite Server Action
                            Ext.Ajax.request({
														  url: './postAddFavorite.do',
														  success: function(response, options) { 
														    // alert(response.responseText);
														    showDevFavoritePanel4Success(response.responseText);
														  },
														  // failure: otherFn,
														  params: { 
                                method: 'saveDeviceToFavorite',
                                favoriteName: favoriteName,
                                deviceId: __device_id_for_add_favorite
														  }
													  });
                            
                        }
                    },{
                        text: bundle4Favorite.getValue('cancel'),
                        handler  : function(){
                            win.hide();
                        }
                    }]
                    
                });
    var secondPanel4DeviceFavorite = new Ext.FormPanel({
                    id: 'panel4AddDeviceFavoriteSuccess',
                    labelWidth: 75, 
                    frame:true,
                    disabled: false,
                    hidden: true,
                    bodyStyle:'padding:20px 5px 0',
                    autoWidth:true,
                    height:128,
                    defaults: {width: 290},
                    items: [{
                        id: 'label4AddDevFavoriteStatus',
												xtype:'label',
												text:bundle4Favorite.getValue('success'),
												style:'text-align:center;font-size: 12pt;'
												}],
                    
                    buttons: [{
                        text: bundle4Favorite.getValue('close'),
                        handler  : function(){
                            win.hide();
                        }
                    }]
                });

    // create the window on the first click and reuse on subsequent clicks
        win = new Ext.Window({
            id          : 'popWin4AddDeviceFavorite',
            applyTo     : 'add-device-favorite-win',
            layout      : 'fit',
            width       : 320,
            height      : 160,
            closeAction :'hide',
            plain       : true,
            pageX       : 550,
            pageY       : 300,
            items       : [firstPanel4DeviceFavorite, secondPanel4DeviceFavorite]
     
        });
}

function showDevFavoritePanel4Input() {
    firstPanel4DeviceFavorite = Ext.getCmp('panel4DeviceFavorite');
    combobox = firstPanel4DeviceFavorite.getComponent('combo4DeviceFavorite');
    firstPanel4DeviceFavorite.getComponent('combo4DeviceFavorite').store = loadFavoriteOptions();
    firstPanel4DeviceFavorite.show();
    secondPanel4DeviceFavorite = Ext.getCmp('panel4AddDeviceFavoriteSuccess');
    secondPanel4DeviceFavorite.hide();	
}

function showDevFavoritePanel4Success(flag) {
    firstPanel4DeviceFavorite = Ext.getCmp('panel4DeviceFavorite');
    firstPanel4DeviceFavorite.hide();
    secondPanel4DeviceFavorite = Ext.getCmp('panel4AddDeviceFavoriteSuccess');
    labelCmp = Ext.getCmp('label4AddDevFavoriteStatus');
    // alert(flag);
    // alert(labelCmp.text);
    if (flag && flag == "success") {
       msg = bundle4Favorite.getValue('success');
       // alert(msg);
       labelCmp.setText(msg);
    } 
    if (flag && flag == "failure") {
      msg = bundle4Favorite.getValue('failure');
      labelCmp.setText(msg);
    } 
    if (flag && flag == "error") {
       msg = bundle4Favorite.getValue('error');
       labelCmp.setText(msg);
    }
    secondPanel4DeviceFavorite.show();	
}

function popAddDeviceFavoriteWin(deviceID){
	//alert("deviceID: " + deviceID);
	__device_id_for_add_favorite = deviceID;
	
	win = Ext.getCmp('popWin4AddDeviceFavorite');
	if (!win) {
		initDevFavoritePanel();
		win = Ext.getCmp('popWin4AddDeviceFavorite');	
	}
	//alert("win: " + win);
    win.show(Ext.get('add_device_favorite_button'));
    showDevFavoritePanel4Input();
}

