<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>body{background: white;}</style>
<!-- <style>body,.online_cont,.online_button{background: #e5ebf2;}</style> -->
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
   <script type="text/javascript">
        $(function () {
            trHover('t2');
            
            var api = frameElement.api, W = api.opener;
            
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
            
            var $inp = $('input:text');
            $inp.bind('keydown', function (e) {
           	 if(e.which == 13) {
           		 var classv = $(this).attr("class") ;  
           		   if(classv&&classv.indexOf("required")>=0){
                         if($(this).val().length<1){
                   			 alert("必填项，不允许为空",$(this),function(obj) {
                   				$(obj).focus(); 
                  			     });
                   			 return ;
                         }
           		   }
           		   if(classv&&classv.indexOf("submit")>=0){
           			   $("#submit").trigger("click"); 
           			   return ;
           		   }
           		   
           		   
           		      e.preventDefault();
                      var nxtIdx = $inp.index(this) + 1;
                       if($(":input:text:eq(" + nxtIdx + ")").prop('disabled')){
                      	 nxtIdx = nxtIdx + 1;
                       }
                       if($(":input:text:eq(" + nxtIdx + ")").prop('disabled')){
                        	 nxtIdx = nxtIdx + 1;
                         }
                      $(":input:text:eq(" + nxtIdx + ")").focus(); 
           		   
           	 }
            });
            
          

        	
        	$("#submit").click(function(e){ 
        		  if($("#vf").validationEngine("validate")){  
              		  var dateMonth =  $('input[name=dateMonth]').val() ;
           			 var fdata = $("form:first").serialize(); 
           			 console.log(fdata);
           	  		 $.ajax({
           	  			 type: "post",//使用get方法访问后台
           	  	            dataType: "text",//返回json格式的数据
           	  	          url: "${ctx }/wages/editSave",//要访问的后台地址
           		            data: fdata,//要发送的数据
           	  	            success: function(data){//msg为返回的数据，在这里做数据绑定
           	  	            	if(data=='1'){
           	  	            	alert("修改成功","",function(){
           	  	            	api.close() ;
           	  	            	});
     		            		
           	  	            	}else{
           	  	            		alert(data) ;
           	  	            	}

           	  	            }
           	  		 });
           	  }
               return false ;	
          	});
        	
        	
        });            
    </script>
</head>
<body>
<form:form id="vf" action="" method="get" >
 <input type="hidden" name="batch_id" value="${courierCost.batch_id}"/>
<div class="online_cont">
  <div class="shoujian_cont salary_head">
    <div class="shou_li"><strong><b></b>费用产生时间</strong><input type="text" class="sinput01 validate[required]"    name="dateMonth" value="${courierCost.cost_month}" readonly="readonly"/></div>
    <div class="shou_li"><strong><b></b>快递员</strong><input class="validate[required]" type="text" style="width: 250px;" name="courierNo" value="${courierCost.courier_no}" id="courierNo" readonly="readonly"/>
    <input type="text" class="sinput02" disabled="disabled" value="${courierCost.real_name}" name="cname" id="cname" /></div>
  </div>
  <div class="salary_cont">
    <div class="salary_cli left">
     <div class="salary_tit">工资增加项</div>
     
     	 <c:forEach items="${inList}" var="item0" varStatus="status0">     
     	 <div class="shou_li"><strong><b></b>${item0.cn_name}</strong><input name="${item0.name}" value="${courierCost[item0.name]}" type="text" class="validate[funcCall[xiaoshu]] "/>元</div>
        </c:forEach> 
     
     <!--  <div class="salary_tips">
          <p>1.&nbsp;&nbsp;所发生的房贷首付多少个地方</p>
      </div> -->
    </div>
    <div class="salary_cli right">
     <div class="salary_tit">工资扣除项</div>
     
   <c:forEach items="${outList}" var="item1" varStatus="status1">     
     	 <div class="shou_li"><strong><b></b>${item1.cn_name}</strong><input name="${item1.name}"  value="${courierCost[item1.name]}" type="text" class="validate[funcCall[xiaoshu]] ${status1.count==fn:length(outList) ? 'submit ' : ''}" />元</div>
      </c:forEach> 
    </div>
  </div>
  <div class="online_button">
  	 <input  class="button input_text  medium blue_flat" type="button" id="submit" value="提交"/><input type="reset" roload="N" class="" value="重置"/>
  </div>
</div>
</form:form>
</body>

</html>