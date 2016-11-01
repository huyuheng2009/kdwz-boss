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
		 if(bankName.length<2){
			 alert('请输入银行名称');
			 return false;
		 }
         var cardName = $.trim($('input[name=cardName]').val());
         $('input[name=cardName]').val(cardName);
		 if(cardName.length<2){
			 alert('请输入卡名称');
			 return false;
		 }
		 var cardLength = $.trim($('input[name=cardLength]').val());
         $('input[name=cardLength]').val(cardLength);
		 if(cardLength.length<1){
			 alert('请输入卡号长度');
			 return false;
		 }
		 var verifyLength = $.trim($('input[name=verifyLength]').val());
         $('input[name=verifyLength]').val(verifyLength);
		 if(verifyLength.length<1){
			 alert('请输入卡标识符长度');
			 return false;
		 }
		 var cardType = $("#cardType  option:selected").val()
		 var verifyCode = $.trim($('input[name=verifyCode]').val());
         $('input[name=verifyCode]').val(verifyCode);
		 if(verifyCode.length<1){
			 alert('请输入卡标识号');
			 return false;
		 }
		 if(verifyCode.length!=verifyLength){
			 alert('卡标识号长度错误');
			 return false; 
		 }
		 var imageName = $.trim($('input[name=imageName]').val());
         $('input[name=imageName]').val(imageName);
		 /* if(imageName.length<1){
			 alert('请输入银行图片名字');
			 return false;
		 } */
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/dict/card_save",//要访问的后台地址
	            data: {'id':$('#id').val(),'bankName':bankName,'cardName':cardName,'cardLength':cardLength,'verifyLength':verifyLength,
	            	   'cardType':cardType,'verifyCode':verifyCode,'imageName':imageName},//要发送的数据
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
	<input  type="hidden" value="${card.id}" id="id" />
		<li>&#12288;&#12288;银行名称：<input class="ltdlen_30" type="text" value="${card.bank_name}" name="bankName" style="width: 240px"></input></li>
		<li>&#12288;&#12288;&#12288;卡名称：<input class="ltdlen_30" type="text" value="${card.card_name}" name="cardName" style="width: 240px"></input></li>
		<li>&#12288;&#12288;卡号长度：<input class="noltd ltdlen_2" type="text" value="${card.card_length}" name="cardLength" style="width: 50px"></input>
		             &#12288;&#12288;卡类型：<select id="cardType" name="cardType" id="cardType" style="width: 115px">
							   <c:forEach items="${cardDist}" var="item" varStatus="status">
                    <option value="${item.dictValue }" <c:out value="${card['card_type'] eq item.dictValue?'selected':'' }"/>>${item.dictValue }</option>
                </c:forEach>
					</select></li>
			<li>卡标识符长度：<input type="text" class="noltd ltdlen_2" value="${card.verify_length}" name="verifyLength" style="width: 50px"></input>
		                          &#12288;卡标识号：<input type="text" value="${card.verify_code}" name="verifyCode" style="width: 115px"></li>			
			<li>银行图片名字：<input type="text" class="ltdlen_20" value="${card.image_name}" name="imageName" style="width: 240px"></input></li>			
							
		<li><input  id="ok" class="button input_text  big blue_flat" style="width: 100px;margin-top: 10px;margin-left:15%;" type="button" value="保存修改" /></li>
	</ul>
</body>
</html>