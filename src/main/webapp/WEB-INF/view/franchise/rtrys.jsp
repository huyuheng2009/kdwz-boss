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
            
        	jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
  		     // var slist = ${slist}; 
              var slist =  tmjs.slist ;
              // $.getJSON("${ctx}/lgc/calllist", function(data1) {
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
            
            
        });   
        
        function _submit(){
        	
        	var takeNo = $('input[name=takeNo]').val() ;
        	var sendNo = $('input[name=sendNo]').val() ;
        	var moneyType = $('select[name=moneyType]').val() ;
        	var weightType = $('select[name=weightType]').val() ;
        	var weight = $('input[name=weight]').val() ;
        	var itemStatus = $('select[name=itemStatus]').val() ;
        	
  		  if($("#vf").validationEngine("validate")){  
  			var api = frameElement.api, W = api.opener;
  			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据   'itemStatus':itemStatus,
		           url: "/franchise/ruleTry",//要访问的后台地址
		            data: {'takeNo':takeNo,'sendNo':sendNo,'moneyType':moneyType,'weightType':weightType,'weight':weight,'itemStatus':itemStatus
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
    <div class="shou_li"><strong><b>★</b>寄件网点</strong><input name="takeNo" id="takeNo" type="text" class="exp_put01 validate[required]" /></div>
    <div class="shou_li"><strong><b>★</b>费用类型</strong><select  class="exp_put01" name="moneyType" >
                         <option value="Z">中转费</option>
                         <option value="P">派件费</option>
                  </select></div>
    <div class="shou_li"><strong><b>★</b>派件网点</strong><input name="sendNo" id="sendNo" type="text" class="exp_put01  validate[required]" /></div>
    <div class="shou_li"><strong><b>★</b>算费模式</strong><select  class="exp_put01" name="weightType" >
                          <option value="R">实际重量</option>
                          <option value="W">四舍五入</option>
                          <option value="L">0.5进位</option>
                  </select></div>
    <div class="shou_li"><strong><b>★</b>重量</strong><input type="text" name="weight" class="exp_put01 validate[required,funcCall[xiaoshu]]" /></div>
    <div class="shou_li"><strong><b>★</b>物品类型</strong><u:select id="itemStatus" classv="validate[required]" sname="itemStatus" stype="ITEM_TYPE"  value=""/></div>
    <div class="shou_li"><strong><b></b>计算结果</strong><input name="ret" type="text" class="exp_put01" disabled /><span id="tips"></span></div>
  </div> 
</div>

<div class="online_button">
  <input type="submit" id="submit" class="" value="试算" /><input type="button"  id="bqx" class="input_gary" value="取消" />
</div>
 </form:form>
</body>

</html>