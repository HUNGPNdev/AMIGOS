(function(n){"use strict";typeof define=="function"&&define.amd?define(["jquery"],n):typeof exports=="object"?n(require("jquery")):n(window.jQuery)})(function(n){"use strict";var t=0;n.ajaxTransport("iframe",function(i){if(i.async){var e=i.initialIframeSrc||"javascript:false;",r,u,f;return{send:function(o,s){r=n('<form style="display:none;"><\/form>');r.attr("accept-charset",i.formAcceptCharset);f=/\?/.test(i.url)?"&":"?";i.type==="DELETE"?(i.url=i.url+f+"_method=DELETE",i.type="POST"):i.type==="PUT"?(i.url=i.url+f+"_method=PUT",i.type="POST"):i.type==="PATCH"&&(i.url=i.url+f+"_method=PATCH",i.type="POST");t+=1;u=n('<iframe src="'+e+'" name="iframe-transport-'+t+'"><\/iframe>').bind("load",function(){var t,f=n.isArray(i.paramName)?i.paramName:[i.paramName];u.unbind("load").bind("load",function(){var t;try{if(t=u.contents(),!t.length||!t[0].firstChild)throw new Error;}catch(i){t=undefined}s(200,"success",{iframe:t});n('<iframe src="'+e+'"><\/iframe>').appendTo(r);window.setTimeout(function(){r.remove()},0)});r.prop("target",u.prop("name")).prop("action",i.url).prop("method",i.type);i.formData&&n.each(i.formData,function(t,i){n('<input type="hidden"/>').prop("name",i.name).val(i.value).appendTo(r)});i.fileInput&&i.fileInput.length&&i.type==="POST"&&(t=i.fileInput.clone(),i.fileInput.after(function(n){return t[n]}),i.paramName&&i.fileInput.each(function(t){n(this).prop("name",f[t]||i.paramName)}),r.append(i.fileInput).prop("enctype","multipart/form-data").prop("encoding","multipart/form-data"),i.fileInput.removeAttr("form"));r.submit();t&&t.length&&i.fileInput.each(function(i,r){var u=n(t[i]);n(r).prop("name",u.prop("name")).attr("form",u.attr("form"));u.replaceWith(r)})});r.append(u).appendTo(document.body)},abort:function(){u&&u.unbind("load").prop("src",e);r&&r.remove()}}}});n.ajaxSetup({converters:{"iframe text":function(t){return t&&n(t[0].body).text()},"iframe json":function(t){return t&&n.parseJSON(n(t[0].body).text())},"iframe html":function(t){return t&&n(t[0].body).html()},"iframe xml":function(t){var i=t&&t[0];return i&&n.isXMLDoc(i)?i:n.parseXML(i.XMLDocument&&i.XMLDocument.xml||n(i.body).html())},"iframe script":function(t){return t&&n.globalEval(n(t[0].body).text())}}})})