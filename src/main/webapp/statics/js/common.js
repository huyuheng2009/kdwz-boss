jQuery.extend({
    /** * @see 将javascript数据类型转换为json字符串 * @param 待转换对象,支持object,array,string,function,number,boolean,regexp * @return 返回json字符串 */
    toJSONString: function(object) {
        var type = typeof object;
        if ('object' == type) {
            if (Array == object.constructor)
                type = 'array';
            else if (RegExp == object.constructor)
                type = 'regexp';
            else
                type = 'object';
        }
        switch (type) {
            case 'undefined':
            case 'unknown':
                return;
                break;
            case 'function':
            case 'boolean':
            case 'regexp':
                return object.toString();
                break;
            case 'number':
                return isFinite(object) ? object.toString() : 'null';
                break;
            case 'string':
                return '"' + object.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function() {
                    var a = arguments[0];
                    return (a == '\n') ? '\\n' : (a == '\r') ? '\\r' : (a == '\t') ? '\\t' : ""
                }) + '"';
                break;
            case 'object':
                if (object === null)
                    return 'null';
                var results = [];
                for (var property in object) {
                    var value = jQuery.toJSONString(object[property]);
                    if (value !== undefined)
                        results.push(jQuery.toJSONString(property) + ':' + value);
                }
                return '{' + results.join(',') + '}';
                break;
            case 'array':
                var results = [];
                for (var i = 0; i < object.length; i++) {
                    var value = jQuery.toJSONString(object[i]);
                    if (value !== undefined)
                        results.push(value);
                }
                return '[' + results.join(',') + ']';
                break;
        }
    }
});

/*if(jQuery.validator){
 jQuery.validator.messages = {
 required: "请输入值.",
 remote: "请修正该字段.",
 email: "请输入有效的邮件地址.",
 url: "请输入有效的网址.",
 date: "请输入有效的日期.",
 dateISO: "请输入合法的日期 (ISO).",
 number: "请输入有效的数字.",
 digits: "只能输入整数.",
 creditcard: "请输入合法的信用卡号.",
 equalTo: "请再次输入相同的值.",
 accept: "请输入拥有合法后缀名的字符串.",
 maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串."),
 minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串."),
 rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串."),
 range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值."),
 max: $.validator.format("请输入一个最大为 {0} 的值."),
 min: $.validator.format("请输入一个最小为 {0} 的值."),
 decimal: $.validator.format("请输入正确金额。如:45.80")
 };
 }*/


function selected($this) {
    var ids = document.getElementsByName('id');
    for (var i = 0; i < ids.length; i++) {
        ids[i].checked = $this.checked;
    }
}

function getSelected() {
    var ids = document.getElementsByName('id');
    var s_id = [];
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked)
            s_id.push(ids[i].value);
    }
    return s_id;
}

/** ********** Date 日期扩展 *************** */
/**
 * 是否在该日期前
 * @param {} date  
 * @return {Boolean}
 */
Date.prototype.before = function(date) {
    return this.getTime() < date.getTime();
}

/**
 * 是否在该日期后
 * @param {} date
 * @return {}
 */
Date.prototype.after = function(date) {
    return this.getTime() > date.getTime();
}

Date.prototype.between = function(startDate, endDate) {
    var t = this.getTime();
    return startDate.getTime() <= t && t <= endDate.getTime();
}

Date.prototype.toStr = function() {
    return this.getFullYear() + "-" + ((this.getMonth() + 1) < 10 ? ("0" + (this.getMonth() + 1)) : (this.getMonth() + 1))
            + "-" + (this.getDate() < 10 ? ("0" + this.getDate()) : this.getDate());
}

Date.prototype.format = function(format) {
    var o =
            {
                "M+": this.getMonth() + 1, //month
                "d+": this.getDate(), //day
                "h+": this.getHours(), //hour
                "m+": this.getMinutes(), //minute
                "s+": this.getSeconds(), //second
                "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
                "S": this.getMilliseconds() //millisecond
            }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

function setCookie(name, value, time) {
    var exp = new Date();
    exp.setTime(exp.getTime() + (typeof(time) == 'undefined' ? (360 * 24 * 60 * 60 * 1000) : time));
    document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString();
}

function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) {
        var tmp = unescape(arr[2]);
        if (tmp.indexOf('"') == 0) {
            tmp = tmp.substr(1, tmp.length - 2);
        }
        return tmp;
    }
    return null;
}

function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";path=/;expires=" + exp.toGMTString();
}

String.prototype.toDate = function() {
    var _tmp = this.split("-");
    return new Date(parseInt(_tmp[0], 10), parseInt(_tmp[1], 10) - 1, parseInt(
            _tmp[2], 10))
}

String.prototype.replaceAll = function(s1, s2) {
    var r, re;
    eval("re = /" + s1 + "/g;")
    r = this.replace(re, s2);
    return (r);
}
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
    return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
    return this.replace(/(\s*$)/g, "");
}
function lhgdialog_clear(w) {
    var list = (w ? w : this).lhgdialog.list;
    for (var i in list) {
        list[i].close();
        delete list[i];
    }
}

/**
 * 清空所有查询条件
 * @return
 */
function clearValue() {
    alert(1);
    $("input[type=text],select,input[type=hidden]").each(function() {
        $(this).val('');
    });
}