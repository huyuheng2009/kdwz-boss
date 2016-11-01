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
		 var mcc = $.trim($('input[name=mcc]').val());
         $('input[name=mcc]').val(mcc);
		 if(mcc.length<1){
			 alert('请输入MCC码');
			 return false;
		 }
         var name = $.trim($('input[name=name]').val());
         $('input[name=name]').val(name);
		 if(name.length<1){
			 alert('请输入MCC名称');
			 return false;
		 }
		 var c1_name = $.trim($('input[name=c1_name]').val());
		 var c2_name = $.trim($('input[name=c2_name]').val());
		 var rate = $.trim($('input[name=rate]').val());
		 var max = $.trim($('input[name=max]').val());
		 var standard_rate = '无' ;
		 if(rate!=''){
			 if(/^\d+\.?\d*$/.test(rate)){
				 standard_rate = rate +'%' ;
			 }else{
				 $('input[name=rate]').val("");
				 alert("扣率输入错误！请重新输入");
			 }
			 if(max!=''){
				 if(/^\d+\.?\d*$/.test(max)){
					 standard_rate += '-' + max +'元' ;
				 }else{
					 $('input[name=max]').val("");
					 alert("扣率输入错误！请重新输入");
				 }
			 }
		 }
		 
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/dict/mcc_save",//要访问的后台地址
	            data: {'id':$('#id').val(),'mcc':mcc,'name':name,'c1_name':c1_name,'c2_name':c2_name,
	            	   'standard_rate':standard_rate}, //要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else{
		            		alert('保存失败');
		            	
	            	}
	            	
	            }
		 });
	 });
	
}); 
 
</script>
</head>
<body>
	<ul>
	<input  type="hidden" value="${mcc.id}" id="id" />
		<li>&#12288;&#12288;&nbsp;MCC码：<input class="noltd ltdlen_4" type="text" value="${mcc.mcc}" name="mcc" style="width: 200px"></input></li>
		<li>&#12288;&nbsp;MCC名称：<input class="ltdlen_30" type="text" value="${mcc.name}" name="name" style="width: 200px"></input></li>
		<li>&#12288;类型名称1：<input class="ltdlen_30" type="text" value="${mcc.c1_name}" name="c1_name" style="width: 200px"></input></li>
		<li>&#12288;类型名称2：<input class="ltdlen_30" type="text" value="${mcc.c2_name}" name="c2_name" style="width: 200px"></input></li>
		<li>&#12288;&nbsp;&nbsp;标准扣率：<input type="text" class="ltdlen_6" value="${mcc.rate}" name="rate" style="width: 87px"></input>%~
		<input type="text" class="ltdlen_6" value="${mcc.max}" name="max" style="width: 87px"></input>元
		</li>			
							
		<li><input  id="ok" class="button input_text  big blue_flat" style="width: 100px;margin-top: 10px;margin-left:15%;" type="button" value="保存修改" /></li>
	</ul>
</body>
</html>