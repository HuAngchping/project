/*
    此段javascript是解决ext本地化程序
    通过页面传递语言参数，判断使用什么语言，如果页面传递的参数存在，则使用页面传递的参数作为语言标识
    如果页面传递的参数不存在，则通过navigator.language、navigator.browserLanguage、navigator.userLanguage来获得本地语言
*/
function Bundle(config) {
  defaultLanguage = 'en';
  this.messages = config.messages;
  Bundle.prototype.guessLanguage = function(){
    lang = '';
    if (Default_Language) {
      lang = Default_Language;
      // alert("Default_Language: " + Default_Language);
      return lang;
    }
    if (navigator.language) {
      lang = navigator.language;
      // alert('navigator.language: ' + navigator.language);
      return lang;
    }
    if (navigator.browserLanguage) {
      lang = navigator.browserLanguage;
      // alert('navigator.browserLanguage: ' + navigator.browserLanguage);
      return lang;
    }
    if (navigator.userLanguage) {
      lang = navigator.userLanguage;
      // alert('navigator.userLanguage: ' + navigator.userLanguage);
      return lang;
    }
    if (this.defaultLanguage) {
      lang = this.defaultLanguage;
      // alert('this.defaultLanguage: ' + this.defaultLanguage);
      return lang;
    }
    // return (Default_Language || navigator.language || navigator.browserLanguage || navigator.userLanguage || this.defaultLanguage);
  };
  language = this.guessLanguage();
  // alert('Language: ' + language);
  if (language == "zh_CN" || language == "zh-cn") {
    language = "zh";
  }
  if (!language){
    language = defaultLanguage;
    // alert('this.guessLanguage: ' + language);
  }
  if (!this.messages[language]) {
    language = defaultLanguage;
    // alert('messages[language]: ' + language);
  }
  Bundle.prototype.getValue = function(key) {
    // alert('messages[language][key]: ' + messages[language][key]);
    return this.messages[language][key];
  };
}

