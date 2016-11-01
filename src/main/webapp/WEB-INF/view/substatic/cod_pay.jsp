<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    $(function(){
   	 $('#submit').click(function(){
   		 var api = frameElement.api, W = api.opener;
   		 var orderNos = $('input[name=orderNos]').val();
   		 var payType =  $('select[name=payType]').val();
   		 $.ajax({
   			 type: "post",//使用get方法访问后台
   	            dataType: "text",//返回json格式的数据
   	            url: "${ctx }/substatic/cod_pay_save",//要访问的后台地址
   	            data: {'orderNos':orderNos,'payType':payType},//要发送的数据
   	            success: function(msg){//msg为返回的数据，在这里做数据绑定
   	            	if(msg==1){
   	            		alert('操作成功',$(this),function(obj) {
   	            			api.close();
   	 			     });
   	            	}else{
   	            		alert(msg);
   	            	}
   	            }
   		 });
   	 });
   	
   }); 
        
    </script>
</head>
<body>
    <form:form id="trans" action="" method="get">
     <input type="hidden" name="orderNos" value="${params['orderNos']}"></input>
        <ul  style="margin:80px 140px;"> 
			 <li>付款类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <select style="width: 100px;" name="payType">
			 <option value="现金">现金</option>
			  <option value="转账">转账</option>
			   <option value="POS票">POS票</option>
			 </select></li>		
            <li style="margin: 30px 0;"><input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/>
            </li>
        </ul>
    </form:form>
    <div class="clear"></div>


</body>

</html>