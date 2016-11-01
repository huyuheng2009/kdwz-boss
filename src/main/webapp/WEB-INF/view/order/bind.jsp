<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${ ctx}/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
     	   
     		 $('#submit').click(function(){
     	   		 var api = frameElement.api, W = api.opener;
     	   	 var orderNo  = $('input[name=orderNo]').val();
     	   	var lgcNo  = $('input[name=lgcNo]').val();
   		     var lgcOrderNo  = $.trim($('input[name=lgcOrderNo]').val());
   		     var r = /^[a-zA-Z0-9]{2,25}$/ ;
		  if(!r.test(lgcOrderNo)){
			  alert("请输入正确的运单号，2位以上数字或英文") ;
			  return false ;
		   }	
     	   	 $.ajax({
    			 type: "post",//使用get方法访问后台
    	            dataType: "text",//返回json格式的数据
    	           url: "${ctx }/order/bind_save",//要访问的后台地址
    	            data: {'orderNo':orderNo,'lgcNo':lgcNo,'lgcOrderNo':lgcOrderNo
    	            	},//要发送的数据
    	            success: function(msg){//msg为返回的数据，在这里做数据绑定
    	            	if(msg==1){
    	            		alert('保存成功');
    	            		// api.reload();
    						api.close();
    	            	}else{
    	            		alert(msg);
    	            	}
    	            }
    		 });  //ajax
     	   	 
     	   	 
     	  });
     		 
     		 
        });

    </script>
</head>
<body>
<div style="padding-top: 30px;padding-left: 15px;">
 <input type="hidden" name="orderNo" value="${params['orderNo']}" />
 <input type="hidden" name="lgcNo" value="${params['lgcNo']}" style="margin-left: 30px;"/>
   运单号：<input type="text" name="lgcOrderNo"  placeholder="8-15数字或英文" value="${params.lgcOrderNo}" />
 </div> 
 <div style="padding-top: 10px;padding-left: 55px;">        
      <input class="button input_text  medium blue_flat" type="button" id="submit" value="修改"/> 
   </div>
</body>

</html>