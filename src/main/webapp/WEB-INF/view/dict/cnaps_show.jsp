<%@page pageEncoding="utf8"%>
<%@include file="/tag.jsp"%>
<%@ include file="/header.jsp"%>
<html>
<head>
<style type="text/css">
li{
margin-bottom: 5px;
width:90%;
margin-left:10%;
heigth:80px;
}
body{
padding-top:4%;
}

</style>
<script type="text/javascript">
$(function(){
	
	$("input[class*='noltd']").keyup(function(){     
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.replace(/\D|^0/g,''));     
    }).bind("paste",function(){     
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.replace(/\D|^0/g,''));     
    }).css("ime-mode", "disabled");   
	
	$("input[class*='ltdlen_']").keyup(function(){  
		var array = $(this).attr("class").split(" ");
		var len = array[array.length-1].substr(7,array[array.length-1].length) ;
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.substr(0,len));     
    }).bind("paste",function(){     
    	var array = $(this).attr("class").split(" ");
		var len = array[array.length-1].substr(7,array[array.length-1].length) ;
        var tmptxt=$(this).val();     
        $(this).val(tmptxt.substr(0,len));   
    }).css("ime-mode", "disabled");  
	
	
	 $('#ok').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var bankName = $.trim($('input[name=bankName]').val());
         $('input[name=bankName]').val(bankName);
		 if(bankName.length<1){
			 alert('请输入银行名称');
			 return false;
		 }
         var cnapsNo = $.trim($('input[name=cnapsNo]').val());
         $('input[name=cnapsNo]').val(cnapsNo);
		 if(cnapsNo.length<1){
			 alert('请输入联行号');
			 return false;
		 }
		 var bankAddr =  $.trim($('input[name=bankAddr]').val());
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/dict/cnaps_save",//要访问的后台地址
	            data: {'id':$('#id').val(),'bankName':bankName,'cnapsNo':cnapsNo,'bankAddr':bankAddr}, //要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else{
		            		alert('保存失败');
		            	
	            	}
	              }
		 });   //ajax
	 });  //ok
	
}); 
 
</script>
</head>
<body>
	<ul>
	<input  type="hidden" value="${cnaps.id}" id="id" />
		<li>银行名称：<input class="ltdlen_200" type="text" value="${cnaps.bank_name}" name="bankName" style="width: 200px"></input></li>
		<li>&#12288;联行号：<input class="noltd ltdlen_12" type="text" value="${cnaps.cnaps_no}" name="cnapsNo" style="width: 200px"></input></li>
		<li>银行地址：<input class="ltdlen_200" type="text" value="${cnaps.bank_addr}" name="bankAddr" style="width: 200px"></input></li>
		<li><input  id="ok" class="button input_text  big blue_flat" style="width: 100px;margin-top: 10px;margin-left:15%;" type="button" value="保存修改" /></li>
	</ul>
</body>
</html>