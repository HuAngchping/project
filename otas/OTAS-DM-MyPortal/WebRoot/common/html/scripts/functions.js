function createXMLHttpRequest() {
  if (window.XMLHttpRequest) { 
     xmlHttp = new XMLHttpRequest(); 
  } else if (window.ActiveXObject) {
    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
  } 
}

function uncache(url){
  var d = new Date();
  var time = d.getTime();
  return url + '&time='+time;
}

function resizePage() {
  //alert("Page refreshed!");
  if (parent) {
     if (parent.pageReszied) {
        parent.pageReszied();
     }
  }
}
