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
     	   	 var id  = $('input[name=id]').val();
     	   	var note  = $('input[name=note]').val();
		  if(note.length<2){
			  alert("请输入无效单原因") ;
			  return false ;
		   }	
			 $.ajax({
       			 type: "post",//使用get方法访问后台
       	            dataType: "text",//返回json格式的数据
       	            url: "${ctx}/order/deled",//要访问的后台地址
       	            data: {'id':id,'note':note},//要发送的数据
       	            success: function(msg){//msg为返回的数据，在这里做数据绑定
       	            	if(msg==1){
       	            		alert('修改成功');
       	            		api.close();
//        	            		api.reload() ;
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
<div style="padding-top: 30px;padding-left: 15px;">
 <input type="hidden" name="id" value="${params['id']}" style="margin-left: 30px;"/>
   无效单原因：<input type="text" name="note"  placeholder=""  style="width: 300px;"/>
 </div> 
 <div style="padding-top: 10px;padding-left: 55px;">        
      <input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/> 
   </div>
</body>

</html>