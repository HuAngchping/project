if (!document.getElementById) {
    document.getElementById = function() { return null; }
}

function initMenu() {
    //alert("initMenu()");
    var uls = document.getElementsByTagName("table");
    for (i = 0; i < uls.length; i++) {
        //alert("uls[i]:" + uls[i]);
        if (uls[i].className == "MainMenu") {
            decorateMenu(uls[i]);
        }
    }
}

// menu: <table class=MainMenu>
function decorateMenu(menu) {
    //alert("decorateMenu(menu)");
    var items = menu.getElementsByTagName("th");
    for (var i=0; i < items.length; i++) {
        items[i].firstChild.myIndex = i;
        // retain any existing onclick handlers from menu-config.xml
        if (items[i].firstChild.onclick) {
           alert("Add onclick handler");
           items[i].firstChild.onclick=function() { 
                eval(items[this.myIndex].firstChild.getAttribute("onclick"));
                setCookie("DMMenuSelected", this.myIndex); 
                };
        } else {
          items[i].firstChild.onclick=function() { 
                setCookie("DMMenuSelected", this.myIndex); 
            };
        }
    }
    activateMenu(items);
}

function activateMenu(items) {
    var activeMenu;
    var found = 0;
    // Find current menu item and highlisht it
    for (var i=0; i < items.length; i++) {
        var url = items[i].firstChild.getAttribute("href");
        var current = document.location.toString();
        if (current.indexOf(url) != -1) {
            found++;
        }
    }
    //alert(found);
    if (found == 0) {  
        var menuSelected = getCookie("DMMenuSelected"); 
        //alert(menuSelected);
        hilightMenu(items[menuSelected], menuSelected, items.length);
        //items[menuSelected].className+="selected";
    } else {
	    // only one found, match on URL
	    for (var i=0; i < items.length; i++) {
	        var url = items[i].firstChild.getAttribute("href");
	        var current = document.location.toString();
	        if (current.indexOf(url) != -1) {
	           //alert("activeMenu value=" + i);
	           hilightMenu(items[i], i, items.length);
	        }
	    }
    }
}

function realPreviousSibling(node){
  var tempNode=node.previousSibling;
  while(tempNode.nodeType!=1){
    tempNode=tempNode.previousSibling;
  }
  return tempNode;
}

function realNextSibling(node){
  var tempNode=node.nextSibling;
  while(tempNode.nodeType!=1){
    tempNode=tempNode.nextSibling;
  }
  return tempNode;
}


// high light menu item
// index: menu index number
// total: total of menu item
function hilightMenu(menu, index, total) {
  //alert("Highlight menu:" + menu);
  menu.className = "selected";
  menu.previousSibling.className = "selected";
  
  if (index == 0) { 
     // IE Browser
     //menu.previousSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlight_begin.gif";
     // Firefox Browser
     //menu.previousSibling.previousSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlight_begin.gif";
     
     realPreviousSibling(menu).firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlight_begin.gif";
  } else {
    // IE Browser
    //menu.previousSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_normalend_hightlightbeg.gif";
    // Firefox Browser
    //menu.previousSibling.previousSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_normalend_hightlightbeg.gif";
    
    realPreviousSibling(menu).firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_normalend_hightlightbeg.gif";
  }
  if (index == total - 1) {
     // IE Browser
     //menu.nextSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_highlight_end.gif";
     // Firefox Browser
     //menu.nextSibling.nextSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_highlight_end.gif";
     
     realNextSibling(menu).firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_highlight_end.gif";
  } else {
    // IE Browser
    //menu.nextSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlightend_normalbeg.gif";
    // Firefox Browser
    //menu.nextSibling.nextSibling.firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlightend_normalbeg.gif";
     
     realNextSibling(menu).firstChild.src = "./common/css/templates/" + CSS_TEMPLATE_NAME + "/images/mnu_hightlightend_normalbeg.gif";
  }
  setCookie("DMMenuSelected", index); 
  
}

// Highlight a menuitem by it's name
function hilightTopMenu(menuName) {
  //alert("Highlight menu ID:" + menuName);
  var mainMenu = document.getElementById("MainMenu");
  var items = mainMenu.getElementsByTagName("th");
  
  for (var i=0; i < items.length; i++) {
      var id = items[i].getAttribute("id");
      if (id == menuName) {
         hilightMenu(items[i], i, items.length);
         return;
      }
  }
}

// Select the menu that matches the URL when the page loads
window.onload=initMenu;

// =========================================================================
//                          Cookie functions 
// =========================================================================
/* This function is used to set cookies */
function setCookie(name,value,expires,path,domain,secure) {
  deleteCookie(name, path, domain);
  
  cookieString = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
  
  document.cookie = cookieString;
  //alert("setCookie:" + cookieString);
}

/* This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 

	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length) 
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end);
	//alert("getCookie:" + value);
	return unescape(value);
}

/* This function is used to delete cookies */
function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}