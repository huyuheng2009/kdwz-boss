<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
.item_content li {width: 160px;padding-left: 30px;margin: 6px;}
.item_content1 li {padding:5px;}

</style>
   <script type="text/javascript">
   $(function(){
		 $('#submit').click(function(){
			 var api = frameElement.api, W = api.opener;
			 var tid = $('#tid').val();
			 var ids = '0' ;
				$('input[name=ids]:checked').each(function(){
	               ids+= ','+$(this).val();
				});
			var freight_type = $('input[name=freight_type]:checked').val() ;	
			var weight_type = $('input[name=weight_type]:checked').val() ;	
			var count_type = $('input[name=count_type]:checked').val() ;	
			var courier_type = $('input[name=courier_type]:checked').val() ;	
			
			var submit = true ;

			var r = /^\d+(.[0-9]{1,2})?$/i ;
			if('NULL'==freight_type){
				var FREIGHT = $('input[name=FREIGHT]').val() ;
				if(!r.test(FREIGHT)){
					alert('请输入正确的金额基数');
					submit = false ;
					return ;
				}
				if(FREIGHT>100||FREIGHT<0){
					alert('请输入正确的金额基数,0-100');
					submit = false ;
					return ;
				}
				freight_type = 	FREIGHT ;	
			}
			
			if('NULL'==weight_type){
				var weight = $('input[name=WEIGHT]').val() ;
				if(!r.test(weight)){
					alert('请输入正确的首重');
					submit = false ;
					return ;
				}
				if(weight>100||weight<0){
					alert('请输入正确的首重,0-100');
					submit = false ;
					return ;
				}
					weight_type = 	weight ;	
			}
			
			var r1 = /^\d+$/i ;
			if('NULL'==count_type){
				var COUNT = $('input[name=COUNT]').val() ;
				if(!r1.test(COUNT)){
					alert('请输入正确的件数');
					submit = false ;
					return ;
				}
				if(COUNT>100||COUNT<0){
					alert('请输入正确的件数,0-100');
					submit = false ;
					return ;
				}
				count_type = 	COUNT ;	
			}
			
			if(submit){
				 $.ajax({
					 type: "get",//使用get方法访问后台
			            dataType: "text",//返回json格式的数据
			            url: "wages_base_save",//要访问的后台地址
			            data: {'FREIGHT':freight_type,'WEIGHT':weight_type,'COUNT':count_type,'COURIER_STATUS':courier_type},//要发送的数据
			            success: function(msg){//msg为返回的数据，在这里做数据绑定
							if(msg==1){
								alert('保存成功','',function(){
									api.reload();
									api.close();
								});
							}else{
								alert(msg);
							}
			            }
				 }); 
			}	
			
		 });
		
	}); 
	           
    </script>
</head>
<body>
 <div class="shoujian_cont salary_head"> 工资计算基数标准</div>
 <div class="content">
	 	
	 	 	<div class="item_content1" style="width: 97%;float: left;">
	 	    
	 		    <ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin: 10px;">
	 		    <li style="border: 1px;border-bottom-style: solid;border-color:#D4CDCD;">金额
	 		    <span style="color: #D4CDCD;text-align: right;float: right;">按照快递运费基数（暂时不计入保价费和代收款等）</span></li>
	 		    <li><label><input type="radio"  name="freight_type"  value="NONE" <c:out value="${configMap.FREIGHT eq 'NONE'?'checked':''}"/> />&nbsp;按实际产生金额计算</label></li>
	 		    <li><label><input type="radio"  name="freight_type"  value="NULL" <c:out value="${configMap.FREIGHT ne 'NONE'?'checked':''}"/> />&nbsp;扣除一定基数 
	 		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基数=<input type="text" name="FREIGHT" 
	 		    value="<c:out value="${configMap.FREIGHT ne 'NONE'?configMap.FREIGHT:''}"/>" style="width:60px; "/>元</label></li> 
	 	    	</ul>
	 	    	
	 	    	<ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin: 10px;">
	 		    <li style="border: 1px;border-bottom-style: solid;border-color:#D4CDCD;">重量
	 		    <span style="color: #D4CDCD;text-align: right;float: right;">按照重量计算（暂时不计算体积重）</span></li>
	 		    <li><label><input type="radio"  name="weight_type"  value="NONE" <c:out value="${configMap.WEIGHT eq 'NONE'?'checked':''}"/> />&nbsp;按实际产生重量计算</label></li>
	 		    <li><label><input type="radio"  name="weight_type"  value="NULL" <c:out value="${configMap.WEIGHT ne 'NONE'?'checked':''}"/> />&nbsp;扣除首重&#12288;&#12288; 
	 		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首重=<input type="text" name="WEIGHT" 
	 		    value="<c:out value="${configMap.WEIGHT ne 'NONE'?configMap.WEIGHT:''}"/>" style="width:60px; "/>KG</label></li> 
	 	    	</ul>
	 	    	
	 	    	
	 	    	<ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin: 10px;">
	 		    <li style="border: 1px;border-bottom-style: solid;border-color:#D4CDCD;">件数
	 		    <span style="color: #D4CDCD;text-align: right;float: right;">按照件数计算（暂时不计算子单）</span></li>
	 		    <li><label><input type="radio"  name="count_type"  value="NONE" <c:out value="${configMap.COUNT eq 'NONE'?'checked':''}"/> />&nbsp;按实际产生件数计算</label></li>
	 		    <li><label><input type="radio"  name="count_type"  value="NULL" <c:out value="${configMap.COUNT ne 'NONE'?'checked':''}"/> />&nbsp;扣除基数件数
	 		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件数=<input type="text" name="COUNT" 
	 		    value="<c:out value="${configMap.COUNT ne 'NONE'?configMap.COUNT:''}"/>" style="width:60px; "/>元</label></li> 
	 	    	</ul>
	 	    	
	 	    	
	 	    	<ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin: 10px;">
	 		    <li style="border: 1px;border-bottom-style: solid;border-color:#D4CDCD;">计入快递员
	 		    <span style="color: #D4CDCD;text-align: right;float: right;"></span></li>
	 		    <li><label><input type="radio"  name="courier_type"  value="1" <c:out value="${configMap.COURIER_STATUS eq '1'?'checked':''}"/> />&nbsp;不计入已经停用的快递员</label></li>
	 		    <li><label><input type="radio"  name="courier_type"  value="0" <c:out value="${configMap.COURIER_STATUS ne '1'?'checked':''}"/> />&nbsp;计入已经停用的快递员 </label></li> 
	 	    	</ul>
	 	    	
	 	    	<ul style="border: 1px;border-style: solid;border-color:#D4CDCD;padding: 10px;margin: 10px;">
	 		    <li style="border: 1px;border-bottom-style: solid;border-color:#D4CDCD;">其他默认项
	 		    <span style="color: #D4CDCD;text-align: right;float: right;"></span></li>
	 		    <li><label>收件票数不计入拒收单，但计入拒签单</label></li>
	 		    <li><label>派件票数不计入拒签单</label></li>
	 	    	</ul>
	 	    	
	 	
	 	</div>
	 	
	 	<div class="operator"  style="padding-top: 30px;padding-left: 30px;">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	 </div>
</body>

</html>