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

        	jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
              var slist =  tmjs.slist ;
               	var slists = [];
                   $.each(slist, function(i, item) {
                   	var inner_no = "" ;
                   	if(item.inner_no){inner_no=item.inner_no+','}
                   	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                   });
                   val1 = '';
                     $jqq("#takeNo").autocomplete(slists, {
                  		minChars: 0,
                  		max: 12,
                  		autoFill: true,
                  		mustMatch: false,
                  		matchContains: true,
                  		scrollHeight: 220,
                  		formatItem: function(slists, i, total) {
                  			return slists[0];
                  		}
                  	}).result(function(event, data, formatted) {
                   		if(data[0].indexOf(')')>-1){
                  			 $("#takeNo").val(data[0]) ;
            			       } 
                  	});	
              
                     $jqq("#sendNo").autocomplete(slists, {
                 		minChars: 0,
                 		max: 12,
                 		autoFill: true,
                 		mustMatch: false,
                 		matchContains: true,
                 		scrollHeight: 220,
                 		formatItem: function(slists, i, total) {
                 			return slists[0];
                 		}
                 	}).result(function(event, data, formatted) {
                  		if(data[0].indexOf(')')>-1){
                 			 $("#sendNo").val(data[0]) ;
           			       } 
                 	});
             
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
        	var tno_check = $('input[name=tno_check]:checked').val() ;
        	var itype_check = $('input[name=itype_check]:checked').val() ;
        	var weight_check = $('input[name=weight_check]:checked').val() ;
        	var sno_check = $('input[name=sno_check]:checked').val() ;
        
        	var takeNo = $('input[name=takeNo]').val()
        		if('Y'==tno_check){
        	        if(takeNo.length<1){
        		       alert("请选择寄件网点！");
        		       return ;
        	         }
        		}
        	
       var itype = $('select[name=itype]').val() ;
        if('Y'==itype_check){  	
        	if(itype.length<1){
        		alert("请选择物品类型！");
        		return ;
        	}}
       	
        	var weight =  $('input[name=weight]').val() ;
        	if('Y'==weight_check){
      	        if(weight.length<1){
      		       alert("请选输入重量！");
      		       return ;
      	         }
      	      if(!reg[6].test(weight)){
      	    	 alert("请选输入正确的重量！");
    		       return ;
      	    	  } 
      		}	
        	
        	  var sendNo = $('input[name=sendNo]').val()
      		if('Y'==sno_check){
      	        if(sendNo.length<1){
      		       alert("请选择派件网点！");
      		       return ;
      	         }
      		}
        	
        	var ids =  $('input[name=ids]').val() ;
        	
  			var api = frameElement.api, W = api.opener;
  			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据   'itemStatus':itemStatus,
		            url: '/franOrder/batchSave',
		            data: {'ids':ids,'tno_check':tno_check,'itype_check':itype_check,'weight_check':weight_check,'sno_check':sno_check,
		            	   'takeNo':takeNo,'itype':itype,'weight':weight,'sendNo':sendNo
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
<body style=" font-size:12px;color:#333;">
 <!--页面背景-->
<div class="edit_pop">
  <h4>请选择需要修改的信息</h4>
  <div class="edit_pop_cont">
     <input  type="hidden" id="ids" name="ids" value="${params.ids}"/>
    <div class="shou_li"><em><input type="checkbox" name="tno_check" value="Y" class="count_chinput" /></em><strong><b></b>寄件网点</strong><input type="text"  name="takeNo" id="takeNo"  class="" /></div>
    <div class="shou_li aa"><em><input type="checkbox" value="Y"  name="itype_check" class="count_chinput" /></em><strong><b></b>物品类型</strong><u:select id="itype" sname="itype" stype="ITEM_TYPE"  value="请选择"/></div>
    <div class="shou_li"><em><input type="checkbox" value="Y"  name="weight_check" class="count_chinput" /></em><strong><b></b>重量</strong><input type="text" class="" name="weight"/></div>
    <div class="shou_li"><em><input type="checkbox" value="Y" name="sno_check" class="count_chinput" /></em><strong><b></b>派件网点</strong><input type="text" class=""  name="sendNo" id="sendNo" /></div>
  </div> 
</div>

<div class="online_button">
  <input type="submit" id="submit" class="" value="保存" /><input type="" id="bqx" class="input_gary" value="取消" />
</div>
</body>

</html>