var reg = new Array() ;
reg[0] = /^\d{17}[0-9x]$/i ;      //身份证正则
reg[1] = /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/ ;      //手机号码
reg[2] = /^(\d{3,4}-)?\d{7,12}(-\d{3,4})?$/;     //座机号码

reg[3] = /^[\u4e00-\u9fa5]{2,50}$/ ;              //中文
reg[4] = /^[\u4e00-\u9fa5a-zA-Z0-9]{2,500}$/ ;   //中文和大小写英文字母数字都包括
reg[5] = /^\d+$/i ;   //数字
reg[6] = /^\d+(.[0-9]{1,2})?$/i ;   //两位小数



//验证输入框
function regText(tid){
	 $("#"+tid+" tr").mouseover(function(){
	        $(this).addClass("over");
	       }).mouseout(function(){
	        $(this).removeClass("over")
	       });
};

//验证下拉框
function regSelect(tid){
	 $("#"+tid+" tr").mouseover(function(){
	        $(this).addClass("over");
	       }).mouseout(function(){
	        $(this).removeClass("over")
	       });
};

function trHover(tid){
	 $("#"+tid+" tr").mouseover(function(){
	        $(this).addClass("over");
	       }).mouseout(function(){
	        $(this).removeClass("over")
	       });
}


function trClick(tid){
	 $("#"+tid+" tr").click(function(){
		 $("#"+tid+" tr").removeClass("tar");
	        $(this).addClass("tar");
	   });
}

function trHover1(tid){
	 $("#"+tid+" tr").mouseover(function(){
	        $("."+$(this).attr("over")).addClass("over");
	       }).mouseout(function(){
	    	   $("."+$(this).attr("over")).removeClass("over");
	       });
}


function loading(){
	 $(".loading").css("display","inline-table") ;
}

function loaded(){
	 $(".loading").css("display","none") ;
}

function loaddata(){
	 $(".loaddata").css("display","block") ;
}

function loaddata_end(){
	 $(".loaddata").css("display","none") ;
}


//复写alert
function alert(text,obj,callback){
	jAlert(text, ' 温馨提示',callback,obj);
	//if( callback ) callback(obj);
};