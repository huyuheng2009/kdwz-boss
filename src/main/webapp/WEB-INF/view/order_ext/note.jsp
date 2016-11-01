<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
.note{font-size:10px;margin-bottom: 20px;margin-top:0px;;width: 380px;min-width: 380px;min-height: 150px;max-width: 380px;max-height: 150px;}
.note_c {
    width: 75%;
    margin: 40px 90px;
    float: initial;
    border: 1px;
    border-style: solid;
    border-color: grey;
    min-height: 300px;
}
</style>
    <script type="text/javascript">
        $(function () {
     	   
     		 $('#submit').click(function(){
     	   		 var api = frameElement.api, W = api.opener;
     	   	 var id  = $('input[name=id]').val();
     	    var note = $('#note').val();
      		 if(note.length<2){
      			 alert('请输入备注');
      			 return false;
      		 }
     	   	 $.ajax({
    			 type: "post",//使用get方法访问后台
    	            dataType: "text",//返回json格式的数据
    	           url: "${ctx }/order_ext/note_save",//要访问的后台地址
    	            data: {'id':id,'note':note},//要发送的数据
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
 <input type="hidden" name="id" value="${params['id']}" />
 <ul>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">运单号：</span><input type="text" style="width: 378px;" name="lgcOrderNo" disabled="disabled"  placeholder="8-15数字或英文" value="${params.lgcOrderNo}" /></li>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">备注：</span><textarea cols="18" rows="4" id="note"  name="note" class="note" ></textarea></li>
   
 </ul>  
 </div> 
 <div style="padding-left: 270px;">        
      <input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/> 
   </div>
 <div class="note_c" style="">
	<c:forEach items="${noteList}" var="item" varStatus="status">
     <ul>
       <li  style="width:100%;">备注：${item.note}</li>
       <li style="width:40%;float: left;">备注人：${item.operator}</li>
       <li class="tt"  style="width:60%;float: left;">时间：<fmt:formatDate value="${item.create_time}" type="both"/></li>
     </ul>
     <div style="float: left;width: 100%;height: 10px;"></div>
   </c:forEach>

    </div>  
   
</body>

</html>