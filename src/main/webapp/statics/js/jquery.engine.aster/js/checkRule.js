function mobilePhone(field,rules,i,options){	
	if($.trim(field.val()).length > 0){
		 var r = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{7,16}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^1[0-9]\d{9}$)/;
		 var t = /\d|-/;
		 if(!t.test(field.val())){
			  return "* 只能输入数字和横杠";
		 }
		 else if(!r.test(field.val())){
			 return "* 请输入正确的联系电话";
		 }
	 }
}

function numberzm(field, rules, i, options) {
	if ($.trim(field.val()).length > 0) {
		var y = /^[a-zA-Z\d]+$/;
		var vlen = field.val().replace(/[\u4E00-\u9FA5]/g, '**').length;
		var minlen = field.attr("minlen");
		var maxlen = field.attr("maxlen");
		var msg = field.attr("msg");
		
		if (msg === undefined || msg === null) {
			msg = "";
		}
		if (!y.test(field.val())) {
			return "只能输入字母和数字";
		} else if ((minlen !== "undefined" && minlen !== '' && minlen !== null)
				&& (maxlen !== "undefined" && maxlen !== '' && maxlen !== null)) {
			if (vlen < parseInt(minlen) || vlen > parseInt(maxlen)) {
				return "只能输入" + parseInt(minlen) + "-" + parseInt(maxlen) + "位字符";
			}
		} else if ((minlen === "undefined" || minlen === '' || minlen === null) && (maxlen !== "undefined" && maxlen !== '' && maxlen !== null)) {
			if (vlen > parseInt(maxlen)) {
				return "最多只能输入" + parseInt(maxlen) + "位字符";
			}
		}
	}
}


