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
   		lastDesc = $('input[name=lastDesc]').val();
   		 if(lastDesc.length<2){
   			 alert('请输入完成情况');
   			 return false;
   		 }
   		 orderNo = $('input[name=orderNo]').val();
   		 $.ajax({
   			 type: "post",//使用get方法访问后台
   	            dataType: "text",//返回json格式的数据
   	            url: "${ctx }/porder/pro_deal_save",//要访问的后台地址
   	            data: {'orderNo':orderNo,'lastDesc':lastDesc},//要发送的数据
   	            success: function(msg){//msg为返回的数据，在这里做数据绑定
   	            	if(msg==1){
   	            		alert('操作成功');
   	            		api.reload();
						api.close();
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
<div class="search">
    <form:form id="trans" action="${ctx}/porder/pro_deal_save" method="get">
        <ul  style="margin:10px 30px;"> 
            <input type="hidden" name="orderNo" value="${params['orderNo']}" />
            <li>请填写完成情况：</li>
			 <li><input type="text" value="${pOrder.last_desc}" name="lastDesc" style="width: 280px"></input></li>		
             <input type="hidden" name="layout" value="no"/>
            <li><input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/>
            </li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>


</body>

</html>