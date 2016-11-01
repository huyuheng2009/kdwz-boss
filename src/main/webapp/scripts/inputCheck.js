function phoneCall(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		  if(!checkPhone(field.val(),'Y')){
			  return "不合法，手机号或者座机号" ; 
		   }	
	  }
}

function phones(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		  if(!checkPhone(field.val(),'N')){
			  return "不合法，不是正确的手机号" ; 
		   }	
	  }
}

function lgcOrderNo(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		var r = /^[a-zA-Z0-9]{5,18}$/ ;
		  if(!r.test(field.val())){
			  return "不合法，5-18数字或英文" ; 
		   }	
	  }
}

function fanhuan(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='现金'&&$.trim(field.val())!='转账'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2){
			return "请输入正确的返款方式，1.现金 2.转账 " ;
			field.focus() ;
		}else{
			if($.trim(field.val())==1||$.trim(field.val())=='现金'){
				field.val("现金") ;
			}else{
				field.val("转账") ;
			}
		}
	  }
}

function payStatu(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='已支付'&&$.trim(field.val())!='未支付'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2){
			return "请输入正确的支付状态，1.已支付 2.未支付 " ;
			$('input[name=payStatu]').focus() ;
		}else{
			if($.trim(field.val())==1||$.trim(field.val())=='已支付'){
				$('input[name=payStatu]').val("已支付") ;
			}else{
				$('input[name=payStatu]').val("未支付") ;
			}
		}
	  }
}

function monthSettle(field, rules, i, options){
	if($.trim(field.val()).length>0){
		$('input[name=mcount]').removeClass("validate[required]") ;
		$('input[name=mcount]').addClass("validate[required]") ;
		$('input[name=msettleDate]').removeClass("validate[required]") ;
		$('input[name=msettleDate]').addClass("validate[required]") ;
	}
}



function paytype(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='现金'&&$.trim(field.val())!='月结'&&$.trim(field.val())!='会员卡'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2&&$.trim(field.val())!=3){
			return "请输入付款方式，1.现金  2.月结  " ;
			$('input[name=freight_type]').focus() ;
		}else{
			if($.trim(field.val())==1||$.trim(field.val())=='现金'){
				$('input[name=payType]').val("现金") ;
				$('input[name=monthSettleNo]').removeClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;*/
			}else if($.trim(field.val())==2||$.trim(field.val())=='月结'){
				$('input[name=payType]').val("月结") ;
				$('input[name=monthSettleNo]').removeClass("validate[required]") ;
				$('input[name=monthSettleNo]').addClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;*/
			}else{
				$('input[name=payType]').val("会员卡") ;
				$('input[name=monthSettleNo]').removeClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=disUserNo]').addClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;
				$('input[name=pwd]').addClass("validate[required]") ;*/
				
				
			}
		}
	
	  }
}

function paytype1(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='现金'&&$.trim(field.val())!='月结'&&$.trim(field.val())!='会员卡'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2&&$.trim(field.val())!=3){
			return "请输入付款方式，1.现金  2.月结 " ;
			$('input[name=si_freight_type]').focus() ;
		}else{
			if($.trim(field.val())==1||$.trim(field.val())=='现金'){
				$('input[name=si_payType]').val("现金") ;
				$('input[name=si_monthSettleNo]').removeClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;*/
			}else if($.trim(field.val())==2||$.trim(field.val())=='月结'){
				$('input[name=si_payType]').val("月结") ;
				$('input[name=si_monthSettleNo]').removeClass("validate[required]") ;
				$('input[name=si_monthSettleNo]').addClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;*/
			}else{
				$('input[name=si_payType]').val("会员卡") ;
				$('input[name=si_monthSettleNo]').removeClass("validate[required]") ;
				/*$('input[name=disUserNo]').removeClass("validate[required]") ;
				$('input[name=disUserNo]').addClass("validate[required]") ;
				$('input[name=pwd]').removeClass("validate[required]") ;
				$('input[name=pwd]').addClass("validate[required]") ;*/
				
				
			}
		}
	
	  }
}

function freight_type(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='寄方付'&&$.trim(field.val())!='到方付'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2){
			return "请输入付款人，1.寄方付  2.到方付 " ;
			$('input[name=freight_type]').focus() ;
		}else{
			if($.trim(field.val())==2||$.trim(field.val())=='到方付'){
				$('input[name=freight_type]').val("到方付") ;
				$('input[name=payType]').removeClass("validate[required]") ;
				$('input[name=payType]').removeClass("validate[required]") ;
				//$('input[name=payType]').val("");
				//$('input[name=payType]').attr("disabled","disabled");
				//$('input[name=monthSettleNo]').focus() ;
			}else{
				$('input[name=freight_type]').val("寄方付") ;
				//$('input[name=payType]').removeAttr("disabled");
				$('input[name=payType]').removeClass("validate[required]") ;
				$('input[name=payType]').addClass("validate[required]") ;
			}
		}
	
	  }
}

function freight_type1(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='寄方付'&&$.trim(field.val())!='到方付'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2){
			return "请输入付款人，1.寄方付  2.到方付 " ;
			$('input[name=freight_type]').focus() ;
		}else{
			if($.trim(field.val())==2||$.trim(field.val())=='到方付'){
				$('input[name=freight_type]').val("到方付") ;
				$('input[name=payType]').removeClass("validate[required]") ;
				$('input[name=payType]').removeClass("validate[required]") ;
			}else{
				$('input[name=freight_type]').val("寄方付") ;
				$('input[name=payType]').removeClass("validate[required]") ;
				$('input[name=payType]').addClass("validate[required]") ;
			}
		}
	
	  }
}

function freight_type2(field, rules, i, options){
	if ($.trim(field.val()).length>0) {
		if($.trim(field.val())!='寄方付'&&$.trim(field.val())!='到方付'&&
				$.trim(field.val())!=1&&$.trim(field.val())!=2){
			return "请输入付款人，1.寄方付  2.到方付 " ;
			$('input[name=si_freight_type]').focus() ;
		}else{
			if($.trim(field.val())==2||$.trim(field.val())=='到方付'){
				$('input[name=si_freight_type]').val("到方付") ;
			}else{
				$('input[name=si_freight_type]').val("寄方付") ;
				
			}
		}
	
	  }
}

function zys(field, rules, i, options){
	  if ($.trim(field.val()).length>0) {
		  if ($.trim(field.val()).length<2) {
			  return "不合法，两位以上的中文英文或数字" ; 
		   }	
	  }
}


function take_courier_no(field, rules, i, options){
	if($.trim($('input[name=take_courier_no]').val()).length>0){
		 if(!reg[4].test($('input[name=take_courier_no]').val())){
			  return "请输入正确的取件快递员" ; 
		   }	
	}
}


function send_courier_no(field, rules, i, options){
	if($.trim($('input[name=send_courier_no]').val()).length>0){
		 if(!reg[4].test($('input[name=send_courier_no]').val())){
			  return "请输入正确的派件快递员" ; 
		   }	
	}
}

function xiaoshu(field, rules, i, options){
	if($.trim(field.val()).length>0){
		 if(!reg[6].test(field.val())){
			  return "请输入正确的小数,保留两位小数" ; 
		   }
	}
}

function yesno(field, rules, i, options){
	if($.trim(field.val()).length>0){
		 if(field.val()!='0'&&field.val()!='1'&&field.val()!='是'&&field.val()!='否'){
			  return "请输入0：否，1：是" ; 
		   }else{
			   if($.trim(field.val())=='0'||$.trim(field.val())=='否'){
				   field.val("否") ;
				}else{
					 field.val("是") ;	
				} 
		   }
	}
}

