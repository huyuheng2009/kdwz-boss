<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>

   <script type="text/javascript">
 
   
        $(function () {
       
            $('#submit').click(function(){
       		  _submit() ;
       	   });
           
            $('#bqx').click(function(){
            	var api = frameElement.api, W = api.opener;
            	api.close() ;
         	   }); 
        });   
        
        function _submit(){
        	var ttype = $('select[name=ttype]').val() ;
        	var itemStatus = $('select[name=itemStatus]').val() ;
        	var distance = $('input[name=distance]').val() ;
        	var weight = $('input[name=weight]').val() ;
        	
  		  if($("#vf").validationEngine("validate")){  
  			var api = frameElement.api, W = api.opener;
  			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据   'itemStatus':itemStatus,
		           url: "/freightRule/ruleTry",//要访问的后台地址
		            data: {'ttype':ttype,'itemStatus':itemStatus,'distance':distance,'weight':weight
		            },//要发送的数据
		            success: function(msg){//msg为返回的数据，在这里做数据绑定
		            	if('none'==msg){
		            		$('input[name=ret]').val("") ;
		            		$("#tips").html("没有找到符合要求的报价！") ;
		            	}else{
		            		$("#tips").html("");
		            		$('input[name=ret]').val(msg) ;
		            	}
  			     }
			 }); 
  		    
         } 
     }     
        
    </script>
</head>
<body style="background:#fff; font-size:12px;color:#333;">
 <form:form id="vf" action="${ctx}/order/input" method="get"  onsubmit="return false;">
<div class="exppart_pop">
  <div class="exppart_pop_cont">
    <div class="shou_li"><strong><b>★</b>时效类型</strong><u:select id="ttype" classv="validate[required]" sname="ttype" style="width: 159px;" stype="AGING_TYPE"  value=""/></div>
    <div class="shou_li"><strong><b>★</b>物品类型</strong><u:select id="itemStatus" classv="validate[required]" sname="itemStatus"  style="width: 159px;" stype="ITEM_TYPE"  value=""/></div>
    <div class="shou_li"><strong><b>★</b>距离</strong><input type="text" name="distance" class="exp_put01 validate[required,funcCall[xiaoshu]]" /></div>
    <div class="shou_li"><strong><b>★</b>重量</strong><input type="text" name="weight" class="exp_put01 validate[required,funcCall[xiaoshu]]" /></div>
    <div class="shou_li"><strong><b></b>计算结果</strong><input name="ret" type="text" class="exp_put01" disabled /><span id="tips"></span></div>
  </div> 
</div>

<div class="online_button">
  <input type="submit" id="submit" class="" value="试算" /><input type="button"  id="bqx" class="input_gary" value="取消" />
</div>
 </form:form>
</body>

</html>