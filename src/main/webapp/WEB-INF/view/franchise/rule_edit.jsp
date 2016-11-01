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
              			$('input[name=tno]').each(function(){
              				if(!$(this).prop('disabled')){
              					$(this).prop('checked',true); 
              				}
                      		
                      	}); 
              	 }else{
              		 $('input[name=tno]').each(function(){
                   		$(this).prop('checked',false); 
                   	}); 
              	 }
              
              });   
            
            $('.s_select_all').click(function() {
             	 if($(this).prop("checked"))	{
             			$('input[name=sno]').each(function(){
             				if(!$(this).prop('disabled')){
             					$(this).prop('checked',true); 
             				}
                     		
                     	}); 
             	 }else{
             		 $('input[name=sno]').each(function(){
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
            
	  $(".cmount").on('input',function(e){  
        		var ename = $(this).attr("name") ;
                 if("fweight"==ename){
                	 $('input[name=dw1]').val($(this).val()) ;  
                 }
                 if("w1"==ename){
                	 $('input[name=dw2]').val($(this).val()) ;  
                 }
                 if("w2"==ename){
                	 $('input[name=dw3]').val($(this).val()) ;  
                 }
                 if("w3"==ename){
                	 $('input[name=dw4]').val($(this).val()) ;  
                 }
           }); 
            
            
            
        });   
        
        function _submit(){
        	
        	
        	var tno = '';
        	$('.tno:checked').each(function(){
        		tno += $(this).val()+','; 
        	});
        	
        	if(tno.length>0){
        		tno = tno.substring(0, tno.length-1) ;
        	}else{
        		alert("请选择寄件网点！");
        		return ;
        	}
        	
        	var sno = '';
        	$('.sno:checked').each(function(){
        		sno += $(this).val()+','; 
        	});
        	
        	if(sno.length>0){
        		sno = sno.substring(0, sno.length-1) ;
        	}else{
        		alert("请选择派件网点！");
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
        	var createTimeBegin =  $('input[name=createTimeBegin]').val() ;
        	var createTimeEnd =  $('input[name=createTimeEnd]').val() ;
        	var moneyType = $('select[name=moneyType]').val() ;
        	var weightType = $('select[name=weightType]').val() ;
        	var wval1 =  $('input[name=wval1]').val() ;
        	var wval2 =  $('input[name=wval2]').val() ;
        	
        	var vpay =  $('input[name=vpay]').val() ;
        	var zpay =  $('input[name=zpay]').val() ;
        	var fweight =  $('input[name=fweight]').val() ;
        	var fmoney =  $('input[name=fmoney]').val() ;
        	
        	
        	if('L'==weightType){
        		if((1-wval1)<0){ alert("参数1请输入正确的值");return false;}
        		if((1-wval2)<0){alert("参数2请输入正确的值");return false;}
        		if(wval1>wval2){alert("参数2必须大于参数1");return false;}
        	}
        
        	
        	var fweight =  $('input[name=fweight]').val() ;
        	var w1 =  $('input[name=w1]').val() ; r_w1=w1;
        	var w2 =  $('input[name=w2]').val() ; r_w2=w2;if(w2!=''&&w1==''){alert("范围1不能空");return false;} 
        	var w3 =  $('input[name=w3]').val() ; r_w3=w3;if(w3!=''&&w2==''){alert("范围2不能空");return false;}
        	var w4 =  $('input[name=w4]').val() ; r_w4=w4;if(w4!=''&&w3==''){alert("范围3不能空");return false;}
        	if(r_w1-fweight<0){alert("范围1必须大于首重");return false;}
        	if(r_w2 != '' && r_w1 != ''){
        		if(r_w2-r_w1<0){alert("范围2必须大于范围1");return false;}
        	}
        	if(r_w3 != '' && r_w2 != ''){
	        	if(r_w3-r_w2<0){alert("范围3必须大于范围2");return false;}
        	}
        	if(r_w4 != '' && r_w3 != ''){
        	if(r_w4-r_w3<0){alert("范围4必须大于范围3");return false;}
        	}
        	
        	var p1 =  $('input[name=p1]').val() ; 
        	var p2 =  $('input[name=p2]').val() ; 
        	var p3 =  $('input[name=p3]').val() ;
        	var p4 =  $('input[name=p4]').val() ; 
        	
        	
  		  if($("#vf").validationEngine("validate")){  
  			var api = frameElement.api, W = api.opener;
  			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据   'itemStatus':itemStatus,
		           url: "/franchise/ruleSave",//要访问的后台地址
		            data: {'id':id,'tno':tno,'sno':sno,'createTimeBegin':createTimeBegin,'createTimeEnd':createTimeEnd,
		            	   'moneyType':moneyType,'itype':itype,'weightType':weightType,'wval1':wval1,'wval2':wval2,'vpay':vpay,
		            	   'zpay':zpay,'fweight':fweight,'fmoney':fmoney,'w1':w1,'p1':p1,'w2':w2,'p2':p2,
		            	   'w3':w3,'p3':p3,'w4':w4,'p4':p4
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
     }     
        
    </script>
</head>
<body style="background:#fff; font-size:12px;color:#333;">
 <form:form id="vf" action="${ctx}/order/input" method="get"  onsubmit="return false;">
 <input  type="hidden" id="id" name="id" value="${params.id }"/>
<div class="count_pop">
  <div class="count_pop_cont">
    <div class="shou_li"><strong><b>★</b>寄件网点</strong>
      <div class="count_ch">
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
              <label><input name="tno" type="checkbox" value="${item.substation_no}" <c:out value="${item.checked eq 'show'?'checked':'' }"/> class="count_chinput tno"  /><i title="${item.sname}">${item.sname}</i></label>
              </li>
            </c:forEach>   
          </ul>
        </div>
      </div>
    </div>
    <div class="shou_li"><strong><b>★</b>派件网点</strong>
      <div class="count_ch">
        <div class="count_ch_li">          
          <ul>
            <li class="count_cli">
              <label><input type="checkbox" class="count_chinput s_select_all" /><i>全选</i></label>
            </li>
          </ul>
        </div>
        <div class="count_ch_li">
          <ul>
               <c:forEach items="${slist}" var="item" varStatus="status">
              <li class="count_cli">
              <label><input name="sno" type="checkbox" value="${item.substation_no}" <c:out value="${item.checked eq 'show'?'checked':'' }"/> class="count_chinput sno" /><i  title="${item.sname}">${item.sname}</i></label>
              </li> 
            </c:forEach>  
          </ul>
        </div>
      </div>
    </div>
    <div class="shou_li"><strong><b>★</b>开始时间</strong>
<input id="dateBegin" class="validate[required]" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m:%s\'}'})"   type="text"   name="createTimeBegin" value="${rule.begin_time}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
</div>
    <div class="shou_li"><strong><b>★</b>结束时间</strong>
<input id="dateEnd" class="validate[required]"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}')"  type="text"  name="createTimeEnd" value="${rule.end_time}"onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
</div>
    <div class="shou_li"><strong><b>★</b>费用类型</strong>
                  <select class="" name="moneyType" >
                         <option value="Z"  ${rule.money_type eq 'Z'?'selected':''}>中转费</option>
                         <option value="P"  ${rule.money_type eq 'P'?'selected':''}>派件费</option>
                  </select>
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
    <div class="shou_li aa"><strong><b>★</b>重量模式</strong>
             <select class="" name="weightType" >
                          <option value="R"  ${rule.weight_type eq 'R'?'selected':''}>实际重量</option>
                          <option value="W"  ${rule.weight_type eq 'W'?'selected':''}>四舍五入</option>
                          <option value="L"  ${rule.weight_type eq 'L'?'selected':''}>0.5进位</option>
                  </select></div> 
    <div class="shou_li"><strong><b></b>参数1</strong><input type="text" class="validate[funcCall[xiaoshu]]" value="${rule.wval1}"  placeholder="作用于0.5进位" name="wval1"/></div>
    <div class="shou_li"><strong><b></b>参数2</strong><input type="text" class="validate[funcCall[xiaoshu]]"  value="${rule.wval2}"  placeholder="大于参数1" name="wval2"/></div>
    <div class="shou_li"><strong><b></b>附加费</strong><input type="text" class="validate[funcCall[xiaoshu]]" value="${rule.vpay}" name="vpay"/>元</div>
    <div class="shou_li"><strong><b></b>最低费用</strong><input type="text" class="validate[funcCall[xiaoshu]]" value="${rule.zpay}" name="zpay"/>元</div>
    <div class="shou_li"><strong><b>★</b>首重范围≤</strong><input type="text" class="validate[required,funcCall[xiaoshu]] cmount" value="${rule.fweight}" name="fweight"/>Kg</div>
    <div class="shou_li"><strong><b>★</b>首重金额</strong><input type="text" class="validate[required,funcCall[xiaoshu]]" value="${rule.fmoney}" name="fmoney"/>元</div>
    <div class="shou_li"><strong><b></b>范围1</strong><input type="text" name="dw1" value="${rule.fweight}" class="count_put01" disabled="disabled" />&nbsp;&nbsp;≤<input type="text" class="count_put01 cmount  validate[funcCall[xiaoshu]]" value="${xweight.w1}"  name="w1" />Kg</div>
    <div class="shou_li"><strong><b></b>单价</strong><input type="text" class="validate[funcCall[xiaoshu]]" value="${xweight.p1}" name="p1"/>元</div>
    <div class="shou_li"><strong><b></b>范围2</strong><input type="text" name="dw2" value="${xweight.w1}" class="count_put01 validate[funcCall[xiaoshu]]"  disabled="disabled"/>&nbsp;&nbsp;≤<input type="text" class="count_put01 cmount validate[funcCall[xiaoshu]]" value="${xweight.w2}"  name="w2"/>Kg</div>
    <div class="shou_li"><strong><b></b>单价</strong><input type="text" class="validate[funcCall[xiaoshu]]" value="${xweight.p2}"  name="p2"/>元</div>
    <div class="shou_li"><strong><b></b>范围3</strong><input type="text" name="dw3" value="${xweight.w2}" class="count_put01 validate[funcCall[xiaoshu]]"  disabled="disabled"/>&nbsp;&nbsp;≤<input type="text" class="count_put01 cmount validate[funcCall[xiaoshu]]" value="${xweight.w3}"  name="w3"/>Kg</div>
    <div class="shou_li"><strong><b></b>单价</strong><input type="text" class="validate[funcCall[xiaoshu]]"  value="${xweight.p3}" name="p3"/>元</div>
    <div class="shou_li"><strong><b></b>范围4</strong><input type="text" name="dw4" value="${xweight.w3}" class="count_put01 validate[funcCall[xiaoshu]]"  disabled="disabled"/>&nbsp;&nbsp;≤<input type="text" class="count_put01 validate[funcCall[xiaoshu]]" value="${xweight.w4}"  name="w4"/>Kg</div>
    <div class="shou_li"><strong><b></b>单价</strong><input type="text" class="validate[funcCall[xiaoshu]]"  value="${xweight.p4}" name="p4"/>元</div>
  </div> 
</div>

<div class="online_button">
  <input  id="submit" name="submit" type="button" class="sear_butt" value="保存" /><input type="" id="bqx" class="input_gary" value="取消" />
</div>
 </form:form>
</body>

</html>