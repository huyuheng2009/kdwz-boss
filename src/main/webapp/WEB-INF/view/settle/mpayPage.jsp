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
     	    var ids =  api.data.ids ;
     	    var settleType =  $.trim($("select[name=settleType]").val());
     	    var settleCount =   $.trim($("input[name=settleCount]").val());
     	    var note = $.trim($('#note').val());
            
     	     var r= /^\d+(.[0-9]{1,2})?$/i ; 
                 if(!r.test(settleCount)){
                	 alert("请输入正确的实收金额");
             		return ;
                 }
            
     	   	 $.ajax({
    			 type: "post",//使用get方法访问后台
    	            dataType: "text",//返回json格式的数据
    	           url: "${ctx }/settle/mbatchPay",//要访问的后台地址
    	            data: {'ids':ids,'settleType':settleType,'settleCount':settleCount,'note':note},//要发送的数据
    	            success: function(msg){//msg为返回的数据，在这里做数据绑定
    	            	if(msg==1){
    	            		alert('保存成功',this,function(){
    	            			api.close();
    	            		});
    						
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
<input type="hidden" name="len" value="${params.len}"/>
 <ul>
  <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">账单月份：</span><input type="text" style="width: 378px;text-align: center;" disabled="disabled"    value="${monthSettle.settleMonth}" /></li>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">月结账号：</span><input type="text" style="width: 378px;text-align: center;" disabled="disabled"   value="${monthSettle.month_no}" /></li>
    <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">客户简称：</span><input type="text" style="width: 378px;text-align: center;" disabled="disabled"  value='<u:dict name="MONTHUSER" key="${monthSettle.month_no}" />' /></li>
     <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">应收金额：</span><input type="text" style="width: 378px;text-align: center;"  disabled="disabled"  value="${monthSettle.month_count}" /></li>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">收款类型：</span>
      <select name="settleType" style="width: 378px;">
      <option value="CASH">现金</option>
      <option value="WEIXIN">微信</option>
      <option value="BANK">转账</option>
      </select>
   </li>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">实收金额：</span><input type="text" style="width: 378px;" name="settleCount"  placeholder="请输入实收金额"  /></li>
   <li style="width: 100%;margin-bottom: 20px;"><span style="width: 100px;float: left;text-align: right;">收款说明：</span><textarea cols="18" rows="4" id="note"  name="note" class="note" ></textarea></li>
   
 </ul>  
 </div> 
 <div style="padding-left: 270px;">        
      <input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/> 
   </div>
   
</body>

</html>