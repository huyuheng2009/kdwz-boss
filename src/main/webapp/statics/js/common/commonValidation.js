/*
 * zengtieshu
 * 验证js
 */

/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 指定长度
 * @param {} ctrlId 控件Id
 * @param {} len 长度限定
 * @return {} true: 验证通过, false:验证失败
 */
function commNoNullCheck(ctrlId,minlen,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	}
			});
}
/**
 * 字母，数字，下划线
 * @param {Object} ctrlId
 * @param {Object} minlen
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */

function commNoNullCheckAc(ctrlId,minlen,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vsymbolAndNumb" : {message : errMsg	},
				"vcn" : {	message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	}
			});
}

/**
 * 只能汉字 字母，数字，下划线
 * @param {Object} ctrlId
 * @param {Object} minlen
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function commNoNullCheckUN(ctrlId,minlen,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vsymbolAndNumb" : {message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	}
			});
}

/**
 * 只能中文，字母 非空
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */

function commNullAndFixedMo(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vnull" : {	message : errMsg	},
				"vcnEnglish" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}


/**
 * 中文，字母 
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */

function commIsNullAndFixedMo(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vcnEnglish" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 中文，字母 数字下划线
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function commFixedModeAndLeng(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vsymbolAndNumb" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

function commNumberAndEnclise(ctrlId,minlen,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	},
				"vsyNumbEnglish" : {message : errMsg},
				"allEnglish" : {message : errMsg},
				"allNumber"  : {message : errMsg}
	         });	
}

/**
 * 只能输入字母和数字
 * @param ctrlId
 * @param minlen
 * @param len
 * @param errMsg
 * @return
 */
function commEnclish(ctrlId,minlen,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	},
				"vsyNumbEnglish" : {message : errMsg}				
	         });	
}

/**
 * 只能中文，字母 数字下划线  非空
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function commFixedModIsNotNull(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vnull" : {	message : errMsg	},
				"vsymbolAndNumb" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 检查是固定电话还是手机
 */
function checkPhoneOrMobile(ctrlId,len,errMsg){
		var bool = true;
		var v = $('#'+ctrlId).val();
		var moblieOrTel =$('#'+ctrlId).val().indexOf("1");
		if (v == null || v == "") {//不能为空
			return processFailure(ctrlId, errMsg, 1);
		}
		if(moblieOrTel==0){//手机
			if(checkMobileIsOk(ctrlId,len,errMsg) ==false){
				return false;
			}
		}else{//固定电话
			if(commNulMaxLenCheckAndTele(ctrlId,len,errMsg) == false){
				return false;
			}
		}
		return bool;
}

/**
 * 手机验证
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function checkMobileIsOk(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"vsymMobile" : {message : errMsg	},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 手机验证
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function checkMobileIsNoOk(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
		        "vnull" : {	message : errMsg	},
				"vsymMobile" : {message : errMsg	},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 下拉框必选验证
 * @param {}ctrlId 控件Id
 * @return {} true: 验证通过, false:验证失败
 */
function commNullSelect(ctrlId,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"vselect" : {	message : errMsg	}
			});
}

/**
 * 只验证长度
 * @param {}ctrlId 控件Id
 * @return {} true: 验证通过, false:验证失败
 */
function commOnlyLength(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"maxlen" : {lenmax : len,message : errMsg}
			});
}

/**
 * 验证长度和中文
 * @param {}ctrlId 控件Id
 * @return {} true: 验证通过, false:验证失败
 */
function commChinaLength(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
		        "vcn" : {lenmax : len,message : errMsg},
				"maxlen" : {lenmax : len,message : errMsg}
			});
}

/**
 * 不能为空和中文
 * @param ctrlId
 * @param len
 * @param errMsg
 * @return
 */
function commChinaLengthNull(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
		        "vnull" : {lenmax : len,message : errMsg},
		        "vcn" : {lenmax : len,message : errMsg},
				"maxlen" : {lenmax : len,message : errMsg}
			});
}

/**
 * 常用非空验证组合, 特殊字符, 指定长度
 * @param {} ctrlId 控件Id
 * @param {} len 长度限定
 * @return {} true: 验证通过, false:验证失败
 */
function commNulCheck(ctrlId,minlen,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vsymbol" : {message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	}
			});
}
/**
 * 不能输入特殊字符
 * @param ctrlId
 * @param len
 * @param errMsg
 * @return
 */
function commNulMaxLenCheck(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"vsymbol" : {message : errMsg	},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 验证年龄
 * @param ctrlId
 * @param len
 * @param errMsg
 * @returns
 */
function commAgeCheck(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {				
				"vnull" : {message : errMsg	},
				"vint" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 验证整数
 * @param ctrlId
 * @param len
 * @param errMsg
 * @returns
 */
function commNumberCheck(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {				
				"vnull" : {message : errMsg	},
				"vint0" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 验证整数可以为空
 * @param ctrlId
 * @param len
 * @param errMsg
 * @returns
 */
function commNullNumberCheck(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {			
				"vint0" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}


/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 指定长度
 * @param {} ctrlId 控件Id
 * @param {} len 长度限定
 * @return {} true: 验证通过, false:验证失败
 */
function commNullCheckes(ctrlId,minlen,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vlen" : {lenlimit : len,lenmin : minlen,message : errMsg	}
			});
}
/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 指定长度
 * @param {} ctrlId 控件Id
 * @param {} len 长度限定
 * @return {} true: 验证通过, false:验证失败
 */
function commCheckIsNull(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vcn" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},				
				"maxlen": {lenmax : len,message : errMsg},
				"vnumber" : {message : errMsg}
			});
}


function commCheckNotNull(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
		        "vnull" : {	message : errMsg	},
				"vcn" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},				
				"maxlen": {lenmax : len,message : errMsg},
				"vnumber" : {message : errMsg}
			});
}

function commCheckNotNullVcn(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
		        "vnull" : {	message : errMsg	},
				"vcn" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},				
				"maxlen": {lenmax : len,message : errMsg}				
			});
}
//
function commCheckNumber(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vcn" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},				
				"vnumber" : {message : errMsg}
			});
}

function checkNumberNotNull(ctrlId,len,errMsg) {
    return doCombiValidation(ctrlId,
    		{
    	        "vnull" : {message : errMsg},
    	        "vnumber" : {message : errMsg},
    	        "maxlen": {lenmax : len,message : errMsg}
    		});
}

/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 指定长度
 * @param {} ctrlId 控件Id
 * @param {} len 长度限定
 * @return {} true: 验证通过, false:验证失败
 */
function checkIsNull(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {	
				"vnull" : {	message : errMsg	},
				"vcn" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},				
				"maxlen": {lenmax : len,message : errMsg},
				"vnumber" : {message : errMsg}
			});
}

/**
 * 常用验证组合, 包括特殊字符, 长度上限
 */
function commNullCheckt(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vsymbol" : {message : errMsg	},
				"maxlen" : {lenmax : len,message : errMsg	}
			});
}

/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 长度上限(没有下限)
 */
function commNullChecks(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vsymbol" : {message : errMsg	},
				"maxlen" : {lenmax : len,message : errMsg	}
			});
}

function commNullCheckAndNumber(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vsymbolAndNumb" : {message : errMsg	},
				"maxlen" : {lenmax : len,message : errMsg	}
			});
}
/**
 * 常用非空验证组合, 包括验证非空, 特殊字符, 长度上限(没有下限)
 */
function commNullCheckos(ctrlId,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	}
			});
}

/**
 * 常用非空验证组合, 包括验证非空, 长度上限(没有下限)
 */
function commNullCheckss(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"maxlen" : {lenmax : len,message : errMsg	}
			});
}
/**
 * 身份证号码验证 包括：非空、特殊字符、长度15/18、格式
 * @param ctrlId 控件Id
 * @return true: 验证通过, false:验证失败
 */
function idCardCheck(ctrlId, errMsg) {
	if(!doCombiValidation(ctrlId, {				
		        "vnull" : {message : errMsg},
				"vsymbol" : {message : errMsg},
				"vcn" : {message : errMsg}
			})){
		return false;
	}	
	// 验证身份证格式输入是否正确
	 var cardId = $.trim(jQuery("#"+ctrlId).val());
	 if(cardId != "" && cardId != null && typeof cardId != "undefined"){
		 if(cardId.length!=15 && cardId.length!=18){
			 return processFailure(ctrlId,errMsg,14);
		 }
	     if(isNaN(cardId)){
	       var firstCard=cardId.substring(0,cardId.toString().length-1);
	       var endCard=cardId.charAt(cardId.toString().length-1);
	       if((isNaN(firstCard)) || endCard!="X"){
	           return processFailure(ctrlId,errMsg,13);
	       }
	     }
	 }
    return successMsg(ctrlId);
}

/**
 * 验证邮箱
 * @param ctrlId
 * @param len
 * @param errMsg
 * @returns
 */
function commonCheckEmail(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {
				"vnull" : {	message : errMsg	},
				"vemail" : {message : errMsg   },
				"vsymbol" : {message : errMsg	},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 验证邮箱
 * @param ctrlId
 * @param len
 * @param errMsg
 * @returns
 */
function commNullCheckEmail(ctrlId,len,errMsg){
	return doCombiValidation(ctrlId,
	         {				
				"vemail" : {message : errMsg   },
				"vsymbol" : {message : errMsg	},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 固定电话号码验证
 * @param {Object} ctrlId
 * @param {Object} len
 * @param {Object} errMsg
 * @return {TypeName} 
 */
function commNulMaxLenCheckAndTele(ctrlId,len,errMsg) {
	return doCombiValidation(ctrlId,
	         {				
				"fixedLengAndRe" : {message : errMsg},
				"vsymNumberAndT" : {message : errMsg},
				"maxlen": {lenmax : len,message : errMsg}
			});
}

/**
 * 特殊字符验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @return true:空、字符串中不包括:$?+\/[]<>*"'%#&=
 */
function doSymbolValidation(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=v.match(/[\$\?\+\\\/\[\]<>*"'%|#&=]/);
	if(result!=null){
		return processFailure(id, message, 7);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 特殊字符验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @return true:空、字符串中不包括:$?+\/[]<>*"'%#&=
 */
function doSymbolValidation1(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=v.match(/[\$\?\+\\\/\[\]<>*"'%|#&=\(\)\{\}\:\~\@\^;`,.-_]/);
	if(result!=null){
		return processFailure(id, message, 7);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 特殊字符验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @return true:只能包含字母，数字以及下划线  
 */
function doSymbolValidationAndNumber(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id);
	}
	var result=/^\w+$/;
	if(!result.test(v)){ 
		return processFailure(id, message, 24);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 验证字母和数字
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @return true:只能包含字母，数字 
 */
function doSymbolValidNotUnderAndNumber(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id);
	}
	var result=/^[A-Za-z0-9]+$/;
	if(!result.test(v)){ 
		return processFailure(id, message, 27);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 身份证验证
 */
function doSymbolCardAndNumber(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id);
	}
	var result=/(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	if(!result.test(v)){ 
		return processFailure(id, message, 25);
	}
	//return processSuccess(id);
	return successMsg(id);
}


/**
 * 固定电话
 * @param {Object} id
 * @param {Object} message
 * @return {TypeName} 
 */
function doSymbolValidaNumber(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id);
	}
	var result=/\d{3}-\d{7,8}|\d{4}-\d{7,8}/;
	var zeroIsStart = v.indexOf("0");
	if(zeroIsStart!=0){
		return processFailure(id, message, 20);
	}
	if(!result.test(v)){
			return processFailure(id, message, 18);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 暂时不用
 * @param {Object} id
 * @param {Object} message
 * @return {TypeName} 
 */
function doSymbolValidationAndTele(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=/(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}/;
	if(!result.test(v)){
			return processFailure(id, message, 17);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * 验证手机号码
 * @param {Object} id
 * @param {Object} message
 * @return {TypeName} 
 */

function doSymbolValidationMobile(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=/^1[3|5|8][0-9]\d{8}$/;
	if(!result.test(v)){
		return processFailure(id, message, 19);
	}
	//return processSuccess(id);
	return successMsg(id);
}

/**
 * @param errMsg 验证失败时错误提示信息
 * @return true:中文,字母
 */
function doCNValidatEnglish(id, errMsg){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=/^[a-zA-z\u4E00-\u9FA5]+$/gi;
	if(!result.test(v)){
		return processFailure(id, errMsg, 21);
	}
	return successMsg(id);
}

/**
 * @return true:只能包含下划线数字字母汉字  
 */
function doSymbolValidNumber(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=/^[a-zA-Z0-9_\u4E00-\u9FA5]+$/;
	if(!result.test(v)){
		return processFailure(id, message, 23);
	}
	//return processSuccess(id);
	return successMsg(id); 
}

/**
 * 电话不能有2个连接符
 * @param {Object} id
 * @param {Object} message
 * @return {TypeName} 
 */

function doSymbolValidAndRela(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	var result=/^(?!\-)[0-9\-]+\d$/;
	var vLeng = v.split("-").length;
	if(!result.test(v)){
		if(vLeng >=3 ){
			return processFailure(id, message, 8);
		}else{
			return processFailure(id, message, 7);
		}
	}
	//return processSuccess(id);
	return successMsg(id); 
}


function doNumberValidation(id,message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}	
	var teat =/^[0-9]*$/;  ///^[1-9]+[0-9]$/;
	//var result=v.match(g);
	if(!teat.test(v)){
		return processFailure(id, message, 2);
	}	
	return successMsg(id);
}

/**
 * 全为数字则错误
 * @param id
 * @param message
 * @returns
 */
function doValidationNumber(id,message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}	
	var teat =/^[0-9]*$/;  ///^[1-9]+[0-9]$/;
	//var result=v.match(g);
	if(teat.test(v)){
		return processFailure(id, message, 27);
	}	
	return successMsg(id);
}

/**
 * 全为英文则错误
 * @param id
 * @param message
 * @returns
 */
function doValidationEnishe(id,message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}	
	var teat =/^[a-zA-Z]*$/;  ///^[1-9]+[0-9]$/;
	//var result=v.match(g);
	if(teat.test(v)){
		return processFailure(id, message, 27);
	}	
	return successMsg(id);
}

/**
 * 年龄不能0开头
 * @param {Object} id
 * @param {Object} message
 * @return {TypeName} 
 */
function doNumberValidationIsNotZero(id,message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}	
	var teat =/^[0-9]*$/;  ///^[1-9]+[0-9]$/;
	//var result=v.match(g);
	var zeroIs = v.indexOf("0");
	if(zeroIs == 0){
		return processFailure(id, message, 22);
	}
	if(!teat.test(v)){
		if(zeroIs == 0){
			return processFailure(id, message, 2);
		}else{
			return processFailure(id, message, 22);
		}
	}	
	return successMsg(id);
}
/**
 * 长度验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @param lenmax 最大长度
 * @param lenmin 最小长度
 * @return true:长度满足要求
 */
function doLengthValidation(id, lenmin, lenmax, message) {
	if(lenmax==null&&lenmin==null){
		return successMsg(id);
	}
	var v = jQuery('#' + id).val();
	var len = v.replace(/[\u4E00-\u9FA5]/g, "aa").length;
	if(lenmax!=null){
		if(v.length>lenmax){		
			return processFailure(id, message+"长度范围为"+lenmin+"-"+lenmax+"位", 200);//
		}
	}
	if(lenmin!=null){
		if(v.length<lenmin){
			return processFailure(id, message+"长度范围为"+lenmin+"-"+lenmax+"位", 200);//+"输入值长度不能小于"+ lenmin +"位"
		}
	}
	return successMsg(id);
}

/**
 * 长度验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @param lenmax 最大长度
 * @param lenmin 最小长度
 * @return true:长度满足要求
 */
function doMaxLengthValidation(id, lenmax, message) {
	if(lenmax==null){
	   return successMsg(id);
	}
	var v = jQuery('#' + id).val();	
	//var len = v.replace(/[\u4E00-\u9FA5]/g, "aa").length;	
	if(lenmax!=null){		
		if(v.length>lenmax){		
			return processFailure(id, message+"长度不能大于"+ lenmax +"位", 200);
		}
	}
	return successMsg(id);
}

/**
 * 长度验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @param lenmax 最大长度
 * @param lenmin 最小长度
 * @return true:长度满足要求
 */
function doMaxLengthValidationss(id, lenmax, message) {
	if(lenmax==null){
		return successMsg(id);
	}
	var v = jQuery('#' + id).val();
	var len = v.replace(/[\u4E00-\u9FA5]/g, "aa").length;
	if(lenmax!=null){
		if(v.length!=lenmax){		
			return processFailure(id, message+"长度为"+ lenmax +"位", 200);//
		}
	}	
	return successMsg(id);
}

/**
 * 长度验证
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @param lenmax 固定长度
 * @return true:长度满足要求
 */
function doMaxLengthValidaFixedLeng(id, lenmax, message) {
	if(lenmax==null){
		return successMsg(id);
	}
	var v = jQuery('#' + id).val();
	var len = v.replace(/[\u4E00-\u9FA5]/g, "aa").length;
	if(lenmax!=null){
		if(v.length!=lenmax && v.length!= 0){		
			return processFailure(id, message+"长度为"+ lenmax +"位", 200);
		}
	}	
	return successMsg(id);
}
/**
 * 下拉框必选验证
 * @param id 控件Id
 * @param errMsg 验证失败时错误提示信息
 * @return true:下拉框已选
 */
function doNullSelect(id,errMsg){
	var v = jQuery('#' + id).val();		
	if (v == null || v == -1 || v == "") {
		return processFailure(id, errMsg, 12);
	}
	return successMsg(id);
}

/**
 * 非空验证
 * @param id 控件Id
 * @param errMsg 验证失败时错误提示信息
 * @return true: 非空
 */
function doNullValidation(id, errMsg) {
	var v = $('#'+id).val();
	v = v.replace(/(^[\s|\u3000]*)/g,"");
	if (v == null || v == "") {
		return processFailure(id, errMsg, 1);
	}
    return successMsg(id);
}

/**
 * 验证输入值为正整数
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @param type 验证类型标识 type为0表示可为0
 * @return true:空、正整数(、0)
 */
function doIntegerValidation(id, message, type){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id); 
	}
	if(type==0){
		var result=v.match(/^(([1-9]\d*)|0)$/i);
	} else {
		var result=v.match(/^[1-9]\d*$/i);
	}
	if(result==null){
		return processFailure(id, message, 5);
	}
	return successMsg(id);
}

/**
 * 验证输入值为email
 * @param id 控件Id
 * @param message 验证失败时错误提示信息
 * @return true:空、email格式
 */
function doEmailValidation(id, message){
	var v = jQuery('#' + id).val();
	if(v == null|| v == ''){
		return successMsg(id);
	}
	var result=v.match(/^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i);
	if(result==null){
		return processFailure(id, message, 4);
	}
	return successMsg(id);
}

/**
 * 验证输入值是否有中文字符,不能为空
 * @param id 控件Id
 * @param errMsg 验证失败时错误提示信息
 * @return true:非空、无中文
 */
function doCNValidation(id, errMsg){
	var v = jQuery('#' + id).val();
	//if(!v){
	//	return processFailure(id,errMsg,1); 
	//}
	var result=/[\u4E00-\u9FA5]/g;
	if(result.test(v)){
		return processFailure(id, errMsg, 11);
	}
	return successMsg(id);
}

/**
 * 验证长度是否超过指定值 (不显示错误提示)
 * @param value 输入的值
 * @param lenmax 最大长度
 * @return true:长度满足要求
 */
function doLinxinLengthValidation(value,lenmax) {
	if(lenmax==null){
		return successMsg(id);
	}
	var len = value.replace(/[\u4E00-\u9FA5]/g, "aa").length;// 多少字节,一个汉字两字节,一个英文字符,数字一个字节
	if(len>lenmax){
		return false;
	}
	return successMsg(id);
}

/**
 * 验证输入值长度是否为指定长度
 * @param {} id 控件Id
 * @param {} slen 指定长度(可为数组表示多个长度，满足其中一个长度即通过)
 * @param message 验证失败时错误提示信息
 * @return {Boolean} true:长度满足slen或slen数组中的一个
 */
function doSpecifiedLenValidation(id, slen, message) {
	if (slen == null) {
		return successMsg(id);
	}
	var v = jQuery('#' + id).val();
	var len = v.replace(/[\u4E00-\u9FA5]/g, "aa").length;
	if(typeof(slen)=='number'){
		if (len != slen) {
			return processFailure(id, message, 9);
		}
	} else {
		var arraylen = slen.length;
		for(var i in slen){
			if(len==slen[i]){
				return successMsg(id);
			}
		}
		return processFailure(id, message, 9);
	}
	return successMsg(id);
}


/**
 * 组合验证器,根据传入的配置参数,定制组合验证方法
 * @param {} id 验证控件id
 * @param {} obj 验证器配置参数<br>
 * <br>
 * vnull 非空验证, 参数[message]<br>
 * vselect 驗證選擇框請選擇 vsymbol 特殊符号验证, 参数[message]<br>
 * vint 整数验证, 参数[message]<br>
 * vlen 字符长度验证, 参数[lenlimit, message]<br>
 * vslen 字符指定长度验证, 参数[slen, message]<br>
 * vemail 字符指定长度验证, 参数[message]<br>
 * integerThan 端口值验证, 参数[message]<br>
 * FloatString 浮点数字验证, 参数[message]<br>
 * vcn 中文验证(非空), 参数[message]<br>
 * @return {Boolean} true: 验证通过, false:验证失败
 */
function doCombiValidation(id, obj) {
	// 下拉列表为空
	if(obj.vselect != null){
		if(!doNullSelect(id,obj.vselect.message)){
		return false;
		}
	}
	
	// 非空
	if (obj.vnull != null) {
		if (!doNullValidation(id, obj.vnull.message)) {
			return false;
		}
	}
	// 特殊符号
	if (obj.vsymbol != null) {
		if (!doSymbolValidation(id, obj.vsymbol.message)) {
			return false;
		}
	}
	
	// 特殊符号
	if (obj.vsymboll != null) {
		if (!doSymbolValidation1(id, obj.vsymboll.message)) {
			return false;
		}
	}
	// 只能输入下划线数字字母
	if (obj.vsymbolNumber != null) {
		if (!doSymbolValidationAndNumber(id, obj.vsymbolNumber.message)) {
			return false;
		}
	}
	// 只能输入数字字母
	if (obj.vsyNumbEnglish != null) {
		if (!doSymbolValidNotUnderAndNumber(id, obj.vsyNumbEnglish.message)) {
			return false;
		}
	}
	// 只能输入数字  连接符
	if (obj.vsymNumberAndT != null) {
		if (!doSymbolValidaNumber(id, obj.vsymNumberAndT.message)) {
			return false;
		}
	}
	
	// 身份证验证
	if (obj.vsymCardIsOk != null) {
		if (!doSymbolCardAndNumber(id, obj.vsymCardIsOk.message)) {
			return false;
		}
	}
	// 电话号码格式
	if (obj.vsymTele != null) {
		if (!doSymbolValidationAndTele(id, obj.vsymTele.message)) {
			return false;
		}
	}
	// 手机号码格式
	if (obj.vsymMobile != null) {
		if (!doSymbolValidationMobile(id, obj.vsymMobile.message)) {
			return false;
		}
	}
	// 只能输入下划线数字字母汉字 
	if (obj.vsymbolAndNumb != null) {
		if (!doSymbolValidNumber(id, obj.vsymbolAndNumb.message)) {
			return false;
		}
	}
	//只有一个连接符
	if (obj.fixedLengAndRe != null) {
		if (!doSymbolValidAndRela(id, obj.fixedLengAndRe.message)) {
			return false;
		}
	}
	//数字
	if(obj.vnumber != null){
		if(!doNumberValidation(id,obj.vnumber.message)){
			return false;
		}
	}
	//不能0开头
	if(obj.vnumberIsNotZe != null){
		if(!doNumberValidationIsNotZero(id,obj.vnumberIsNotZe.message)){
			return false;
		}
	}
	// 整数
	if (obj.vint != null) {
		if (!doIntegerValidation(id, obj.vint.message)) {
			return false;
		}
	}
	// 整数带0
	if (obj.vint0 != null) {
		if (!doIntegerValidation(id, obj.vint0.message, 0)) {
			return false;
		}
	}
	// 长度区间
	if (obj.vlen != null) {
		if (!doLengthValidation(id, obj.vlen.lenmin, obj.vlen.lenlimit, obj.vlen.message)) {
			return false;
		}
	}
	
	if (obj.maxlen != null) {
		if (!doMaxLengthValidation(id, obj.maxlen.lenmax, obj.maxlen.message)) {
			return false;
		}
	}
	//固定长度
	if (obj.fixedLeng != null) {
		if (!doMaxLengthValidaFixedLeng(id, obj.fixedLeng.lenmax, obj.fixedLeng.message)) {
			return false;
		}
	}
	
	// 指定长度(或指定长度数组中的一个)
	if (obj.vslen != null){
		if (!doSpecifiedLenValidation(id, obj.vslen.slen, obj.vslen.message)) {
			return false;
		}
	}
	// Email格式
	if (obj.vemail != null) {
		if (!doEmailValidation(id, obj.vemail.message)) {
			return false;
		}
	}	
	// 验证中文
	if(obj.vcn != null){
		if(!doCNValidation(id,obj.vcn.message)){
			return false;
		}
	}
	// 验证中文和字母
	if(obj.vcnEnglish != null){
		if(!doCNValidatEnglish(id,obj.vcnEnglish.message)){
			return false;
		}
	}
	//全为数字错误
	if(obj.allEnglish != null){
		 if(!doValidationEnishe(id,obj.allEnglish.message)){
			 return false;
		 }
	}
	
	//全为数字错误
	if(obj.allNumber != null){
		 if(!doValidationNumber(id,obj.allNumber.message)){
			 return false;
		 }
	}
	
	return this;
}

/**
 * 验证失败处理,弹出消息提示框
 * @param id 验证控件
 * @param message 验证失败时错误提示信息
 * @return false
 */
function processFailure(id, message, fn) {
	errorMsg(id,message,fn);
	return false;
}

/**
 * 边框变绿,验证通过
 * @param id 控件Id
 */
function successMsg(id) {
	jQuery('#' + id + "x").css("visibility","hidden");
	//jQuery('#' + id + "x").hide();	
	jQuery('#' + id + "xx").html('');
	//jQuery('#' + id + "t" ).css("display","true");
	//jQuery('#' + id + "x").css("display","none");
	return true;
}

/**
 * 验证失败时错误信息提示
 * @param id 获取焦点的控件ID
 * @param message 验证失败时错误提示信息
 * @param fn 功能号,根据该标识拼接错误提示信息
 * @return
 */
function errorMsg(id, message, fn) {
	if(message=='#'){
		return;
	}
	var name =$("#"+id+"x");
	var namet = $("#"+id + "xx");
	var msg = null;
	switch (fn) {
		case 1 :
			msg = message+"不能为空";
			break;
		case 2 : 
			msg = message + "只能输入数字";
			break;
		case 4 :
			msg = message + "格式不对";
			break;
		case 5 :
			msg = message + "必须为正整数";
			break;
		case 7 :
			msg = message + "不能含有特殊字符";//($?\/[]<>*\"'%|#&=)
			break;
		case 8 :
			msg = message + "只能输入一个连接字符";//($?\/[]<>*\"'%|#&=)
			break;
		case 9 :
			msg = message + "输入值长度有误";
			break;
		case 11 :
			msg = message + "不能含有中文";
			break;
		case 12 :
			msg = "请选择" + message;
			break;
		case 13 :
			msg = message + "不能包含除大写X外的字母";
			break;
		case 14 :
			msg = message + "的长度应为15位或18位";
			break;
		case 15 :
			msg = message + "两次新密码不一致，请重试！";
			break;
		case 16 :
			msg = message + "亲情电话不能相同，请重试！";
			break;
		case 17 :
			msg = message + "电话号码格式不正确，请重试！";
			break;
		case 18 :
			msg = message + "正确格式如:010-12345678";
			break;
		case 19 :
			msg = message + "手机号输入不正确";
			break;
		case 20 :
			msg = message + "固定电话必须以0开头";
			break;
		case 21 :
			msg = message + "只能包含中文以及字母";
			break;
		case 22 :
			msg = message + "年龄只能输入数字且不能以0开头";
			break;
		case 23 :
			msg = message + "只能输入中文数字字母下划线";
			break;
		case 24 :
			msg = message + "只能输入数字字母下划线";
			break;
		case 25 :
			msg = message + "身份证只能是15/18位(只能包含X字母)";
			break;
		case 26 :
			msg = message + "只能输入字母以及数字";
			break;
		case 27 :
			msg = message + "只能输入字母和数字的组合";
			break;
		case 200 :
			msg = message + "";
			break;
		default :
			throw new Error(top.error);
	}
	try{      
		//alert(1);
		//jQuery('#' + id + "m").html(msg);
		//jQuery('#' + id + "t" ).hide();		
	    //jQuery('#' + id + "x").css("display","none");		
		name.show();
		name.css("visibility","visible");
	    namet.html(msg);	
	}catch (e) {
	}
}


function jumpUrl(url){
	location.href = url;
}
