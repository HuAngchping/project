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

function invokeAbout()
{
		if( document.all )
	{
		left = (screen.width/2)-200;
		atop = (screen.height/2)-200;

		var ft='width=400,height=375'+',left='+left+',top='+atop+',resizable=no';

		win=window.open("./common/help/about.jsp","_blank",ft);

		win.focus();
	}
	else if( document.getElementById )
	{
		win = window.open('./common/help/about.jsp','_blank','width=400,height=375,resizable=no')
		win.focus();
		win.moveTo((document.width/2)-235,(document.height/2)-130);
	}
}
