/**
 * jQuery Validation Plugin @VERSION
 *
 * http://bassistance.de/jquery-plugins/jquery-plugin-validation/
 * http://docs.jquery.com/Plugins/Validation
 *
 * Copyright (c) 2006 - 2011 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

(function() {
	
	// 身份证号码验证
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	return this.optional(element) || isIdCardNo(value);
	}, "请正确输入您的身份证号码");

	// 字符验证
	jQuery.validator.addMethod("userName", function(value, element) {
	return this.optional(element)
	|| /^[\Α-\￥\w]+$/.test(value);
	}, "用户名只能包括中文字、英文字母、数字和下划线");
	
	jQuery.validator.addMethod("decimal", function(value, element) {
		return this.optional(element) || (/^\d+(\.\d{1,2})?$/.test(value));
	}, "请正确输入数字。如:45.80");
	
	// 字母
	jQuery.validator.addMethod("azname", function(value, element) {		
		return this.optional(element)
		|| (/^[a-z|A-Z]+$/.test(value));
		}, "只能输入英文字母");
	
	//不能输入中文 
	jQuery.validator.addMethod("repassword", function(value, element) {		
		return this.optional(element)
		|| (!/[\u4E00-\u9FA5]+/.test(value));
		}, "不能输入中文");
	// 字母和下划线
	jQuery.validator.addMethod("abname", function(value, element) {		
		return this.optional(element)
		|| (/^[a-zA-Z_]+$/.test(value));
		}, "只能输入英文字母和下划线");
	
	// 字母和数字
	jQuery.validator.addMethod("numaz", function(value, element) {		
		return this.optional(element)
		|| (/^[a-zA-Z|\d]+$/.test(value));
		}, "只能输入英文字和数字");
	
	// 字母和小数点
	jQuery.validator.addMethod("akname", function(value, element) {		
		return this.optional(element)
		|| (/^[a-zA-Z|.]+$/.test(value));
		}, "只能输入英文字母和小数点");	
		
		// 字母和中文
	jQuery.validator.addMethod("chinesename", function(value, element) {		
		return this.optional(element)
		|| (/^[a-zA-z\u4E00-\u9FA5]+$/.test(value));
		}, "只能输入中文和字母");

	// 手机号码验证
	jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	return this.optional(element)
	|| (length == 11 && /^(1[0-9])\d{9}$/.test(value));
	}, "请正确填写您的手机号码");

	// 电话号码验证
	jQuery.validator.addMethod("isPhone", function(value, element) {
	var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
	return this.optional(element) || (tel.test(value));
	}, "请正确填写您的电话号码");
	
	// 手机号码验证       
	jQuery.validator.addMethod("isMobilePhone", function(value, element) {       
	     var length = value.length;   
	     //var mobile = /^((\\+86)|(86))?(1[358])\d{9}$/; 
	     var mobile = /(^(\d{3,4}-)?\d{7,8})$|^(1[0-9])\d{9}$/; 				   
	   return this.optional(element) || mobile.test(value);       
	}, "请正确填写您的手机号码或者电话号码(如13530654056,0755-7221243)");

	// 邮政编码验证
	jQuery.validator.addMethod("isZipCode", function(value, element) {
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
	}, "请正确填写您的邮政编码");

})();
