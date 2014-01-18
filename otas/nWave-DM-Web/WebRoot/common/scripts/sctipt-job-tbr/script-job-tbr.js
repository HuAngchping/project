/*
 * Ext JS Library 2.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

var bundle4Toolbar = new Bundle({messages: {
'zh':{
          yzText:'叶子节点',
          zjText:'中间节点',
          revocationText: '撤销',
          formatText: '整理格式'
        },
   'en':{
          yzText:'Leaf Node',
          zjText:'Interior Node',
          revocationText: 'Undo',
          formatText: 'Format'
        }
}});

Ext.onReady(function(){
    var addyz = new Ext.Action({
        text: bundle4Toolbar.getValue('yzText'),
        handler: function(){
            doAddLeafNodeTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var addzj = new Ext.Action({
        text: bundle4Toolbar.getValue('zjText'),
        handler: function(){
            doAddInteriorNodeTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var delTargetNode = new Ext.Action({
        text: 'Target Node',
        handler: function(){
            doDeleteTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var altDisplay = new Ext.Action({
        text: 'Display',
        handler: function(){
            doAlertDisplayTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var altConfirm = new Ext.Action({
        text: 'Confirm',
        handler: function(){
            doAlertConfirmTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var altSingleChoice = new Ext.Action({
        text: 'Single Choice',
        handler: function(){
            doAlertSingleChoiceTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var altMultipleChoice = new Ext.Action({
        text: 'Multiple Choice',
        handler: function(){
            doAlertMultipleChoiceTemplate(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var repTarget = new Ext.Action({
        text: 'Target',
        handler: function(){
            doReplace(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var repTargetData = new Ext.Action({
        text: 'TargetDate Noda',
        handler: function(){
            doReplace1(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var excTargetNode = new Ext.Action({
        text: 'Target Node',
        handler: function(){
            doExec(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var excTargetData = new Ext.Action({
        text: 'TargetDate Noda',
        handler: function(){
            doExec1(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var getTargetNode = new Ext.Action({
        text: 'Target Noda',
        handler: function(){
            doGet(document.getElementById("commandScriptEditor"));
        },
        iconCls: 'blist'
    });

    var addMenu = new Ext.menu.Menu({
        id: 'addMenu',
        items: [addyz,addzj]
    });

    var delMenu = new Ext.menu.Menu({
        id: 'delMenu',
        items: [delTargetNode]
    });

    var altMenu = new Ext.menu.Menu({
        id: 'altMenu',
        items: [altDisplay,altConfirm,altSingleChoice,altMultipleChoice]
    });

    var repMenu = new Ext.menu.Menu({
        id: 'repMenu',
        items: [repTarget,repTargetData]
    });

    var excMenu = new Ext.menu.Menu({
        id: 'repMenu',
        items: [excTargetNode,excTargetData]
    });

    var getMenu = new Ext.menu.Menu({
        id: 'repMenu',
        items: [getTargetNode]
    });

    var tb = new Ext.Toolbar();
    tb.render('script-job-tbr');
    tb.add({
            text:'Add',
            menu: addMenu  // assign menu by instance
        },{
            text:'Del',
            menu: delMenu  // assign menu by instance
        },{
            text:'Alt',
            menu: altMenu  // assign menu by instance
        },{
            text:'Rep',
            menu: repMenu  // assign menu by instance
        },{
            text:'Exc',
            menu: excMenu  // assign menu by instance
        },{
            text:'Get',
            menu: getMenu  // assign menu by instance
        }, '-', {
            text: bundle4Toolbar.getValue('revocationText'),
            type: 'button',
            handler: function() {
                doUndo(document.getElementById("commandScriptEditor"));
            }
        }, '-', {
            text: bundle4Toolbar.getValue('formatText'),
            type: 'button',
            handler: function() {
                doFormat(document.getElementById("commandScriptEditor"));
            }
        }, '-', {
            text: 'Seq',
            type: 'button',
            handler: function() {
                doSequenceTemplate(document.getElementById("commandScriptEditor"));
            }
        }, '-', {
            text: 'Ato',
            type: 'button',
            handler: function() {
                doAtomicTemplate(document.getElementById("commandScriptEditor"));
            }
        })

});