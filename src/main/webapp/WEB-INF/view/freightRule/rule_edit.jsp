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

            $('.t_select_all').click(function() {
              	 if($(this).prop("checked"))	{
              			$('input[name=ttype]').each(function(){
              				if(!$(this).prop('disabled')){
              					$(this).prop('checked',true); 
              				}
                      		
                      	}); 
              	 }else{
              		 $('input[name=ttype]').each(function(){
                   		$(this).prop('checked',false); 
                   	}); 
              	 }
              
              });   
             
            
            $('.i_select_all').click(function() {
             	 if($(this).prop("checked"))	{
             			$('input[name=itype]').each(function(){
             				if(!$(this).prop('disabled')){
             					$(this).prop('checked',true); 
             				}
                     		
                     	}); 
             	 }else{
             		 $('input[name=itype]').each(function(){
                  		$(this).prop('checked',false); 
                  	}); 
             	 }
             
             });  
            
            $('#submit').click(function(){
       		  _submit() ;
       	   });
            
            $('#bqx').click(function(){
            	var api = frameElement.api, W = api.opener;
            	api.close() ;
         	   });
           
            
        });   
        
        function _submit(){
        	
        	var ttype = '';
        	$('.ttype:checked').each(function(){
        		ttype += $(this).val()+','; 
        	});
        	
        	if(ttype.length>0){
        		ttype = ttype.substring(0, ttype.length-1) ;
        	}else{
        		alert("请选择时效类型！");
        		return ;
        	}
        	
        	var itype = '';
        	$('.itype:checked').each(function(){
        		itype += $(this).val()+','; 
        	});
        	
        	if(itype.length>0){
        		itype = itype.substring(0, itype.length-1) ;
        	}else{
        		alert("请选择物品类型！");
        		return ;
        	}
        	var id =  $('input[name=id]').val() ;
        	var vpay =  $.trim($('input[name=vpay]').val()) ;
        	var fmoney =  $.trim($('input[name=fmoney]').val()) ;
        	var fdistance =  $.trim($('input[name=fdistance]').val()) ;
        	var step_distance =  $.trim($('input[name=step_distance]').val()) ;
        	var step_distance_money =  $.trim($('input[name=step_distance_money]').val()) ; 
        	var fweight =  $.trim($('input[name=fweight]').val()) ; 
        	var step_weight =  $.trim($('input[name=step_weight]').val()) ;
        	var step_weight_money =  $.trim($('input[name=step_weight_money]').val()) ; 
        	
        	var r = /^\d+(.[0-9]{1,2})?$/i ;
			if(vpay!=''&&!r.test(vpay)){
				alert('请输入正确的附加费');
				return ;
			}
			if(fmoney==''||!r.test(fmoney)){
				alert('请输入正确的起步金额');
				return ;
			}
			if(fdistance!=''&&!r.test(fdistance)){
				alert('请输入正确的首距离');
				return ;
			}
			if(step_distance!=''&&!r.test(step_distance)){
				alert('请输入正确的增加距离');
				return ;
			}
			if(step_distance_money!=''&&!r.test(step_distance_money)){
				alert('请输入正确的距离费用');
				return ;
			}
			if(fweight!=''&&!r.test(fweight)){
				alert('请输入正确的首重');
				return ;
			}
			if(step_weight!=''&&!r.test(step_weight)){
				alert('请输入正确的重量');
				return ;
			}
			if(step_weight_money!=''&&!r.test(step_weight_money)){
				alert('请输入正确的重量费用');
				return ;
			}
			
        	
        	
  			var api = frameElement.api, W = api.opener;
  			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据   'itemStatus':itemStatus,
		           url: "/freightRule/ruleSave",//要访问的后台地址
		            data: {'id':id,'ttype':ttype,'itype':itype,'vpay':vpay,'fmoney':fmoney,'fdistance':fdistance,'step_distance':step_distance,
		            	   'step_distance_money':step_distance_money,'fweight':fweight,'step_weight':step_weight,'step_weight_money':step_weight_money
		            },//要发送的数据
		            success: function(msg){//msg为返回的数据，在这里做数据绑定
		            	if('1'==msg){
		            		alert('保存成功','',function(obj){
		            			api.reload();
								api.close();
		            		});
		            	}else{
		            		alert(msg);
		            	}
		            }
			 }); 
     }     
        
    </script>
</head>
<body style="background:#fff; font-size:12px;color:#333;">
 <!--页面背景-->
  <input  type="hidden" id="id" name="id" value="${params.id }"/>
<div class="count_pop cost_pop">
  <div class="count_pop_cont">
    <div class="shou_li"><strong><b>★</b>时效类型</strong>
      <div class="count_ch aa">
        <div class="count_ch_li">          
          <ul>
            <li class="count_cli">
              <label><input type="checkbox" class="count_chinput t_select_all" /><i>全选</i></label>
            </li>
          </ul>
        </div>
        <div class="count_ch_li">
          <ul>
                <c:forEach items="${tlist}" var="item" varStatus="status">
              <li class="count_cli">
              <label><input name="ttype" type="checkbox" value="${item.item_text}"  <c:out value="${item.checked eq 'show'?'checked':'' }"/> class="count_chinput ttype" /><i  title="${item.item_text}">${item.item_text}</i></label>
              </li>
            </c:forEach>  
          </ul>
        </div>
      </div>
    </div>    
    <div class="shou_li"><strong><b>★</b>物品类型</strong>
      <div class="count_ch aa">
        <div class="count_ch_li">          
          <ul>
            <li class="count_cli">
              <label><input type="checkbox" class="count_chinput i_select_all" /><i>全选</i></label>
            </li>
          </ul>
        </div>
        <div class="count_ch_li">
          <ul>
           <c:forEach items="${ilist}" var="item" varStatus="status">
              <li class="count_cli">
              <label><input name="itype" type="checkbox" value="${item.item_text}"  <c:out value="${item.checked eq 'show'?'checked':'' }"/> class="count_chinput itype" /><i  title="${item.item_text}">${item.item_text}</i></label>
              </li>
            </c:forEach>  
          </ul>
        </div>
      </div>
    </div>
    <div class="shou_li"><strong><b></b>附加费</strong><input type="text" class=""  value="${rule.vpay}"  name="vpay"/>元</div>
    <div class="shou_li"><strong><b>★</b>起步金额</strong><input type="text" class=""   value="${rule.fmoney}"  name="fmoney"/>元</div>
    <div class="shou_li san"><strong><b></b>首距离</strong><input type="text" class=""   value="${rule.fdistance}"  name="fdistance"/>km，每增加<input type="text" class=""   value="${rule.step_distance}"  name="step_distance"/>km，增加<input type="text" class=""   value="${rule.step_distance_money}"  name="step_distance_money"/>元</div>
    <div class="shou_li san"><strong><b></b>首重</strong><input type="text" class=""   value="${rule.fweight}"  name="fweight"/>kg ，每增加<input type="text" class=""   value="${rule.step_weight}"  name="step_weight"/>kg ，增加<input type="text" class=""   value="${rule.step_weight_money}"  name="step_weight_money"/>元</div>
  </div> 
</div>

<div class="online_button">
  <input type="submit" id="submit" class="" value="保存" /><input type="" id="bqx" class="input_gary" value="取消" />
</div>
</body>

</html>