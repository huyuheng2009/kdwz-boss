<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script  type="text/javascript" src="/scripts/city_hp.js"></script> 
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style type="text/css">
.wi{ width:140px; text-align:right; margin-right:14px;}
.form_bottom ul li{ float:none;}
.yundan_in .yundan_input{ margin-top:8px;}
.yundan_in select{ margin-top:10px;}
.form_left{ width:44%;}
</style>

<script type="text/javascript">
$().ready(function() {
  /*   var ddd = ['aaa','bbb'] ;
    $('#').autocomplete(ddd).result(function(event, data11, formatted) {
  	                   alert(data11);
  	    }); */
  
  	    /*  */

});

function inputCheck1()
{
	 return false ;
} 
    function inputCheck()
    {
    	lgcOrderNo = $('input[name=lgcOrderNo]').val();
		 if(lgcOrderNo.length<2){
			 alert('请输入快递公司运单号');
			 return false;
		 }
		 return true ;
    }

    function ChangeProvincel(objTip,objOption,provinceValue)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val();
    	}
    	ChangeProvince(objOption,provinceValue,provinceValue);
    	obj.innerHTML=tip;
    }
    
    function ChangeCityl(objTip,objOption,provinceValue)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val()+'-'+ $('select[name=scity]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val()+'-'+ $('select[name=rcity]').val();
    	}
    	ChangeCity(objOption,provinceValue,provinceValue);
    	obj.innerHTML=tip;
    }
    
    function ChangeAreal(objTip)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val()+'-'+ $('select[name=scity]').val()+'-'+$('select[name=sarea]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val()+'-'+ $('select[name=rcity]').val()+'-'+$('select[name=rarea]').val();
    		$('input[name=revAddr]').focus();
    	}else{
    		$('input[name=sendAddr]').focus();
    	}
    	obj.innerHTML=tip;
    }
    
    function sarea(inid){
    	$.dialog({lock: true,title:'选择区域',drag: true,width:650,height:120,resize: false,max: false,content: 'url:/order/sarea?inid='+inid+'&layout=no',close: function(){
    	}});
    	
 } 
    
        $(function () {
        	 $('#itemStatus').addClass("validate[required]");
        	$("#vf").validationEngine("attach",{
        		ajaxFormValidation:false,
        		onBeforeAjaxFormValidation:function (){
        			alert("before ajax validate");
        		},
        		onAjaxFormComplete:
                function (){
        			
        		}
        		
        	}); 
        	 
        	$(".cmount").on('input',function(e){  
        		
        		var amount = $('input[name=payAcount]').val() ;
        		var good_price1 = $('input[name=good_price]').val() ;
        		var cpay1 = $('input[name=cpay]').val() ;
        		var goodValuation1 = $('input[name=goodValuation]').val() ;
        		var vpay1 = $('input[name=vpay]').val() ;
        		var freight1 = $('input[name=freight]').val() ;
        		
        		var freight_type1 = $('input[name=freight_type]').val() ;
        		var payType1 = $('input[name=payType]').val() ;
        		
        		 if(!reg[6].test(good_price1)){
        			 good_price1 = 0 ;
        		 }
        		 if(!reg[6].test(cpay1)){
        			 cpay1 = 0 ;
        		 }
        		 if(!reg[6].test(goodValuation1)){
        			 goodValuation1 = 0 ;
        		 }
        		 if(!reg[6].test(vpay1)){
        			 vpay1 = 0 ;
        		 }
        		 if(!reg[6].test(freight1)){
        			 freight1 = 0 ;
        		 }
        		 
        		 var tnpay = 0 ;
        		 if(freight_type1=='1'||freight_type1=='寄方付'&&payType1.length>0){
        			 tnpay = freight1 - (-vpay1) ;
        				switch(payType1)
        				{
        				case '1':case '现金':case '3':case '会员卡':
        				 tnpay = freight1 - (-vpay1) ;
        				  break;
        				case '2':case '月结':
        					 tnpay =  freight1 - (-vpay1) ;
          				  break;
        				default:break;
        				}
        		 }
        		 if(freight_type1=='2'||freight_type1=='到方付'){
        			 tnpay = 0 ;
        		 }
        		
        		// amount = good_price1-(-cpay1)-(-vpay1)-(-freight1);
        		amount = good_price1-(-vpay1)-(-freight1);
        		 
        		 $('input[name=tnpay]').val(tnpay) ;
                 $('input[name=payAcount]').val(amount) ;
        		  
        		});  
            var $inp = $('input:text');
            $inp.bind('keydown', function (e) {
            	 if(e.which == 13) {
              	   var isFocus=$(".pf").is(":focus");  
              	   if(isFocus){
              		   var l = $('select[name=lgcNo]').val() ;
              		   if(l.length<2){
              			   alert("请选择快递公司");
              			   return ;
              		   }
              		   var  sPhone = $('input[name=sendPhone]').is(":focus");  
              		   var phone = $('input[name=sendPhone]').val() ;
              		   if(sPhone){
              			   if(phone.length<2){
              				   alert("请输入寄件人电话号码");
                  			   return ; 
              			   }
              			   $.dialog({lock: true,title:'地址簿',drag: true,width:800,height:470,resize: false,max: false,content: 'url:${ctx}/order/lgc_addr?inid=S&lgcNo='+l+'&phone='+phone+'&layout=no',close: function(){
                           	 }});
              			   
              		   }else{
              			   phone = $('input[name=revPhone]').val() ;
              			   if(phone.length<2){
              				   alert("请输入收件人电话号码");
                  			   return ; 
              			   }
              			   $.dialog({lock: true,title:'地址簿',drag: true,width:800,height:470,resize: false,max: false,content: 'url:${ctx}/order/lgc_addr?inid=R&lgcNo='+l+'&phone='+phone+'&layout=no',close: function(){
                           	 }}); 
              		   }
              	   }else{
              		   var $inp = $('input:text');
              		      e.preventDefault();
                         var nxtIdx = $inp.index(this) + 1;
                         $(":input:text:eq(" + nxtIdx + ")").focus();
                         
              	   }
                 }  
            	


            });

        	
      	  $.ui.autocomplete.prototype._renderItem = function (ul, item) {  
      	    	value  = item.label;
      	        return $("<li></li>")   
      	                .data("item.autocomplete", item)   
      	                .append("<a>" + value +"</a>")   
      	                .appendTo(ul);   
      		};  
      		

           $("#take_courier_no").autocomplete({
      		change: function( event, ui ) {
    		}
    	 });
          
          $("#send_courier_no").autocomplete({
        		change: function( event, ui ) {
      		}
      	 }); 
          
          
        $.getJSON("${ctx}/lgc/calllist", function(data1) {
        	var availablesrcKey1 = [];
            $.each(data1, function(i, item) {
            	var inner_no = "" ;
            	if(item.inner_no){inner_no=item.inner_no+','}
            	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
            });
            val1 = '';
           /* 	 	$("#take_courier_no").autocomplete({
                	source: availablesrcKey1,
                	autoFocus: true,
                	select: function( event, ui ) {
                		val1=ui.item.value;
          		  	},
          		  close: function( event, ui ) {
              		$(this).val(val1) ;
        		  	}
             	 }); */
           	 	
             	 
             	 
             	 
             	 
              $jqq("#take_courier_no").autocomplete(availablesrcKey1, {
           		minChars: 0,
           		max: 12,
           		autoFill: true,
           		mustMatch: false,
           		matchContains: true,
           		scrollHeight: 220,
           		formatItem: function(data11, i, total) {
           			return data11[0];
           		}
           	});	
           	 	
             	 
             	var mlist = ${mlist}; 	 
             	var mres = [];
             	$.each(mlist, function(i, item) {
             		var name = item.month_sname ;
             		if(name.length<1){name = item.month_name ;}
                  	mres[i]=item.month_settle_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
                  }); 
             	  $jqq("#monthSettleNo").autocomplete(mres, {
               		minChars: 0,
               		max: 12,
               		autoFill: true,
               		mustMatch: false,
               		matchContains: true,
               		scrollHeight: 220,
               		formatItem: function(data, i, total) {
               			return data[0];
               		}
               	}).result(function(event, data, formatted) {
               		if(data[0].indexOf(')')>-1){
               			 $("#monthSettleNo").val(data[0].substring(0,data[0].indexOf('('))) ;
               			 $("#monthSname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
         			       } 
               	}); 
               			 
            	var codList = ${codList}; 	 
             	var codres = [];
             	$.each(codList, function(i, item) {
             		var name = item.cod_sname ;
             		if(name.length<1){name = item.cod_name ;}
             		codres[i]=item.cod_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
                  }); 
             	  $jqq("#cod_no").autocomplete(codres, {
               		minChars: 0,
               		max: 12,
               		autoFill: true,
               		mustMatch: false,
               		matchContains: true,
               		scrollHeight: 220,
               		formatItem: function(data, i, total) {
               			return data[0];
               		}
               	}).result(function(event, data, formatted) {
               		if(data[0].indexOf(')')>-1){
               			 $("#cod_no").val(data[0].substring(0,data[0].indexOf('('))) ;
               			 $("#cod_sname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
         			       } 
               	}); 				 
             	 
          /*  	 	
             	  $jqq("#send_courier_no").autocomplete(availablesrcKey1, {
                 		minChars: 0,
                 		max: 12,
                 		autoFill: true,
                 		mustMatch: false,
                 		matchContains: true,
                 		scrollHeight: 220,
                 		formatItem: function(data22, i, total) {
                 			return data22[0];
                 		}
                 	});	 */
           	 	
           	 	
           	 	
           	 	
           	 	
           	  val2 = '';
         	 	/* $("#send_courier_no").autocomplete({
              	source: availablesrcKey1,
            	autoFocus: true,
              	select: function( event, ui ) {
              		val2=ui.item.value;
        		  	},
        		  close: function( event, ui ) {
            		$(this).val(val2) ;
      		  	}
           	 }); */
         });

        	var areas = spit1('${orderMap.send_area}').split("-") ;
        	var rareas = spit1('${orderMap.rev_area}').split("-") ;
        	if(!areas[2]){
        		areas = '${lgcConfig.defaultCity}'.split("-") ;
        		  if(areas[1]){
        			InitOption('sprovince','scity','sarea',areas[0],areas[1]);
               	    InitOption('rprovince','rcity','rarea',areas[0],areas[1]); 
        		  }else{
        			  InitOption('sprovince','scity','sarea','广东省','深圳市');
                 	  InitOption('rprovince','rcity','rarea','广东省','深圳市');  
        		  }
        		  
        		  
        	}else{
        		 InitOption('sprovince','scity','sarea',areas[0],areas[1],areas[2]);
           	     InitOption('rprovince','rcity','rarea',rareas[0],rareas[1],rareas[2]);
        	}
     	  
     	   $('#submit').click(function(){
     		 
     		  if($("#vf").validationEngine("validate")){  
     		   
     		   var lgcNo = 	$('select[name=lgcNo]').val() ;
     		   
     		   var sendName = $('input[name=sendName]').val() ;
     		   var sendAddr = $('input[name=sendAddr]').val() ;
     		   var sendPhone = $('input[name=sendPhone]').val() ;
     		   
     		   
     		  if(sendPhone.length<2){
   		    	$('input[name=sendPhone]').focus();
   				 return false;
   			 }
     		 if(sendName.length<2){
  		    	$('input[name=sendName]').focus();
  				 return false;
  			 }
     		 if(sendAddr.length<2){
  		    	$('input[name=sendAddr]').focus();
  				 return false;
  			 }

     		  lgcOrderNo = $('input[name=lgcOrderNo]').val();
     		  if(lgcOrderNo.length<2){
     			 alert('请输入快递公司运单号');
     			 return false;
     		 }
     	 	   
                   var revName = $('input[name=revName]').val() ;
     			   var revAddr = $('input[name=revAddr]').val() ;
     			   var revPhone = $('input[name=revPhone]').val() ;
     			   
     			   
          		  if(revPhone.length<2){
        		    	$('input[name=revPhone]').focus();
        				 return false;
        			 }
          		 if(revName.length<2){
       		    	$('input[name=revName]').focus();
       				 return false;
       			 }
          		 if(revAddr.length<2){
       		    	$('input[name=revAddr]').focus();
       				 return false;
       			 }
          		 
          		 /* 
     			   var sprovince = 	$('select[name=sprovince]').val() ;
        		   var scity = 	$('select[name=scity]').val() ;
        		   var sarea = 	$('select[name=sarea]').val() ;
        		   
     			   if(sprovince.length<1){
        		    	alert("请选择寄件人区域信息");
        		    	$('select[name=sprovince]').focus();
        				 return false;
        			 }
        		    if(scity.length<1){
        		    	alert("请选择寄件人区域信息");
        		    	$('select[name=scity]').focus();
        				 return false;
        			 }
        		    if(sarea.length<1){
        		    	alert("请选择寄件人区域信息");
        		    	$('select[name=sarea]').focus();
        				 return false;
        			 }
        		   
        		    
        		  var rprovince = 	$('select[name=rprovince]').val() ;
         		   var rcity = 	$('select[name=rcity]').val() ;
         		   var rarea = 	$('select[name=rarea]').val() ;
      			   if(rprovince.length<1){
         		    	alert("请选择收件人区域信息");
         		    	$('select[name=rprovince]').focus();
         				 return false;
         			 }
         		    if(rcity.length<1){
         		    	alert("请选择收件人区域信息");
         		    	$('select[name=rcity]').focus();
         				 return false;
         			 }
         		    if(rarea.length<1){
         		    	alert("请选择收件人区域信息");
         		    	$('select[name=rarea]').focus();
         				 return false;
         			 }
        		    
     			   var sendArea = sprovince + '-' + scity + '-' + sarea ;   
     			   var revArea = rprovince + '-' + rcity + '-' + rarea ;    */
     			 
     			  var itemStatus =  $('#itemStatus').val() ;  
     			 var reqRece = $('input[name=reqRece]').val() ;
     			 var receNo = $('input[name=receNo]').val() ;
     			 var orderNote = $('input[name=orderNote]').val() ; 
     			 
     			/* var send_substation_no = $('input[name=send_substation_no]').val();
     			 if(send_substation_no.length>1&&send_substation_no.length<5){
     				 alert('分站编号错误');
     				 return false;
     			 }
     			 if(send_substation_no.indexOf(')')>-1){
     				send_substation_no = send_substation_no.substring(0,send_substation_no.indexOf('(')); 
     			 }  */
     	    
     			
     			var take_courier_no = $('input[name=take_courier_no]').val();
     			 if(take_courier_no.length<2){
     		    	$('input[name=take_courier_no]').focus();
     				 return false;
     			 }
    			 if(take_courier_no.length>1&&take_courier_no.length<5){
    				 alert('取件快递员信息错误');
    				 return false;
    			 }
    			 if(take_courier_no.indexOf(')')>-1){
    				 take_courier_no = take_courier_no.substring(0,take_courier_no.indexOf('(')); 
    			 } 
     	         
    			// var send_courier_no = $('input[name=send_courier_no]').val();
    			/*  if(send_courier_no.length<2){
     		    	$('input[name=send_courier_no]').focus();
     				 return false;
     			 } */
    			/*  if(send_courier_no.length>1&&send_courier_no.length<5){
    				 alert('派件快递员信息错误');
    				 return false;
    			 }
    			 if(send_courier_no.indexOf(')')>-1){
    				 send_courier_no = send_courier_no.substring(0,send_courier_no.indexOf('(')); 
    			 }  */
     			
     			
     			
     			 var item_count = $('input[name=item_count]').val() ;
     			var item_weight = $('input[name=item_weight]').val() ;
     			var good_price = $('input[name=good_price]').val() ;
     			//var return_type = $('input[name=return_type]').val() ;
     			//if('现金'==return_type){return_type = 1 ;}
     			//if('转账'==return_type){return_type = 2 ;}
     			
     			var cod_name = $('input[name=cod_name]').val() ;
     			var freight = $('input[name=freight]').val() ;
     			
     			if(freight<=0){
     				alert("邮费必须大于0") ;
     				$('input[name=freight]').focus();
     				 return false;
     			}
     			
     			var cod = 0 ;
     			if(good_price>0){
     				cod = 1 ;
     			}
     			
     			var payAcount = $('input[name=payAcount]').val() ;
     			
     			var cpay = $('input[name=cpay]').val() ;
     			var vpay = $('input[name=vpay]').val() ;
     			var goodValuation = $('input[name=goodValuation]').val() ;
     			
     			 if(freight.length<1){
      		    	$('input[name=freight]').focus();
      				 return false;
      			 }
     			 if(payAcount.length<1){
      		    	$('input[name=payAcount]').focus();
      				 return false;
      			 }
     			
     			
     			
     			var freight_type = $('input[name=freight_type]').val() ;
     			 if(freight_type.length<1){
       				 return false;
       			 }
     			if('寄方付'==freight_type){freight_type = 1 ;}
     			if('到方付'==freight_type){freight_type = 2 ;}
     			
     			var monthSettleNo = $('input[name=monthSettleNo]').val() ;
     			var payType = $('input[name=payType]').val() ;
     			if('现金'==payType||'1'==payType){payType = 'CASH' ;}
     			if('月结'==payType||'2'==payType){payType = 'MONTH';}
     			if('会员卡'==payType||'3'==payType){payType = 'HUIYUAN' ;}
     			
     			var disUserNo = $('input[name=disUserNo]').val() ;
     			var pwd = $('input[name=pwd]').val() ;
     			var tnpay = $('input[name=tnpay]').val() ;
     			
     			var itemName  = $('input[name=itemName]').val() ;
     			var edited  = $('input[name=edited]').val() ;
     			
     			 //$("form:first").submit();
     			 $.ajax({
     				 type: "post",//使用get方法访问后台
     		            dataType: "text",//返回json格式的数据
     		           url: "/order/take_update",//要访问的后台地址
     		            data: {'lgcNo':lgcNo,'lgcOrderNo':lgcOrderNo,'sendName':sendName,'sendPhone':sendPhone,'sendArea':sendArea,'sendAddr':sendAddr,
     		            	   'revName':revName,'revPhone':revPhone,'revArea':revArea,'revAddr':revAddr,'itemName':itemName,'cod':cod,
     		            	   'itemStatus':itemStatus,'receNo':receNo,'orderNote':orderNote,'takeCourierNo':take_courier_no,'itemCount':item_count,
     		            	  'itemWeight_':item_weight,'goodPrice_':good_price,'cpay_':cpay,'codName':cod_name,'vpay_':vpay,'goodValuation_':goodValuation,
     		            	 'freight_':freight,'payAcount_':payAcount,'freightType':freight_type,'monthSettleNo':monthSettleNo,'edited':edited,
     		            	'payType':payType,'disUserNo':disUserNo,'pwd':pwd,'tnpay':tnpay,'reqRece':reqRece
     		            },//要发送的数据
     		           beforeSend:loading,
     		            success: function(msg){//msg为返回的数据，在这里做数据绑定
     		            	if('1'==msg){
     		            		alert("录入成功");
     		            		location.reload() ;
     		            	}else{
     		            		loaded() ;
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
 <div  class="split">现在位置：收件补录</div>    
<div class="form_list">
  <form:form id="trans" action="${ctx}/order/input" method="get" onsubmit="return inputCheck();">
  <div class="form_top">
    <ul>
     <%--  <li>
        <div class=" "><b>快递公司：</b>
         <select id="lgcNo" name="lgcNo" style="width: 180px">
           	<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/>/> ${item.name }</option>
				</c:forEach>
          </select>
        </div>
      </li> --%>
      <li> <b class="">运单号：</b>
       <input style="width: 70%;height:50px;" value="${params.lgcOrderNo}" class="validate[required,funcCall[lgcOrderNo]]" name="lgcOrderNo" type="text"  maxlength="60" />
        <input value="<c:out value="${orderMap.fpay_status eq 'SUCCESS'?'N':'Y' }"/>"  name="edited" type="hidden"  maxlength="60" />
       <input class="button input_text  medium blue_flat" type="submit" value="查询"/>
       <span style="color:red;">${params.msg}</span>  
      </li>
            
    </ul>
  </div>
    </form:form>
    
   <form:form id="vf" action="${ctx}/order/input" method="get" onsubmit="return inputCheck1();">        
  <div class="form_left">
    <ul>
      <li>
        <div class="form_tit">寄件信息</div>
      </li>
      <li>
        <div class="yundan_b"><span style="color: red;">★</span>手机号码/电话号码：</div>
        <div class="yundan_in">
       <input class="yundan_input pf validate[required,funcCall[phoneCall]]" name="sendPhone" id="sendPhone"  value="${orderMap.send_phone}"  type="text"  maxlength="15"/>  
        </div>
      </li>
      <li> <b class="yundan_b yundan_onb"><span style="color: red;">★</span>联系人/公司：</b>
        <div class="yundan_in">
        <input id="sendName" name="sendName" class="yundan_input validate[required,funcCall[zys]]" value="${orderMap.send_name}"   id="sendName" type="text"   maxlength="20"/>  
        </div>
      </li>
      <li> <b class="yundan_b yundan_onb"><span style="color: red;">★</span>地址：</b>
        <div class="yundan_in">
            <select name="sprovince" style="width: 27%;margin-left: 3%;"  class="validate[required]"  id="sprovince" onchange="javascript:ChangeProvincel('stips','scity',this.options[this.selectedIndex].value)"></select>
          <select name="scity"  style="width:27%;margin-left: 2%;"  class="validate[required]" id="scity" onchange="javascript:ChangeCityl('stips','sarea',this.options[this.selectedIndex].value)"></select>
           <select name="sarea" style="width:27%;margin-left: 2%;" class="validate[required]"  id="sarea" onchange="javascript:ChangeAreal('stips')"></select>
        </div>
      </li>
      <li>
        <div class="yundan_in margin_left">
        <input  class="yundan_input yundan_inputlong validate[required,funcCall[zys]]"  name="sendAddr" id="sendAddr"  value="${orderMap.send_addr}"  type="text"  maxlength="60"/>
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li>
        <div class="form_tit">收件信息</div>
      </li>
      <li>
        <div class="yundan_b"><span style="color: red;">★</span>手机号码/电话号码：</div>
        <div class="yundan_in">
         <input class="yundan_input pf validate[required,funcCall[phoneCall]]" name="revPhone"  id="revPhone"  value="${orderMap.rev_phone}"  type="text" maxlength="15"/>
        </div>
      </li>
      <li> <b class="yundan_b yundan_onb"><span style="color: red;">★</span>联系人/公司：</b>
        <div class="yundan_in">
        <input name="revName" class="yundan_input validate[required,funcCall[zys]]" type="text" id="revName"  value="${orderMap.rev_name}"   maxlength="20"/> 
        </div>
      </li>
      <li> <b class="yundan_b yundan_onb"><span style="color: red;">★</span>地址：</b>
        <div class="yundan_in">
        <select name="rprovince" style="width: 27%;margin-left: 3%;"  class="validate[required]"  id="rprovince" onchange="javascript:ChangeProvincel('rtips','rcity',this.options[this.selectedIndex].value)"></select>
         <select name="rcity" style="width:27%;margin-left: 2%;" id="rcity" class="validate[required]"  onchange="javascript:ChangeCityl('rtips','rarea',this.options[this.selectedIndex].value)"></select>
          <select name="rarea"  style="width:27%;margin-left: 2%;" id="rarea"  class="validate[required]" onchange="javascript:ChangeAreal('rtips')"></select>
        </div>
      </li>
      <li>
        <div class="yundan_in margin_left">
        <input name="revAddr" class="yundan_input yundan_inputlong validate[required,funcCall[zys]]"  id="revAddr" type="text"  value="${orderMap.rev_addr}"    maxlength="60"/>
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
    </ul>
    <div class="form_bottom">
      <div class="form_tit">其他信息</div>
      <ul>
        <li >
          <div class="line_block wi"><span style="color: red;">★</span>物品类型：</div>
          <div class="line_block">
          <%--  <select name="itemStatus" id="itemStatus" >
               <option value="其他" ${orderMap.item_Status eq '其他'?'selected':''}>其他</option>
               <option value="文件"  ${orderMap.item_Status eq '文件'?'selected':''}>文件</option>
               <option value="有机蔬果" ${orderMap.item_Status eq '有机蔬果'?'selected':''}>有机蔬果</option>
               <option value="贵重易碎品" ${orderMap.item_Status eq '贵重易碎品'?'selected':''}>贵重易碎品</option>
               <option value="物流大件" ${orderMap.item_Status eq '物流大件'?'selected':''}>物流大件</option>
               <option value="商务件" ${orderMap.item_Status eq '商务件'?'selected':''}>商务件</option>
               <option value="食品" ${orderMap.item_Status eq '食品'?'selected':''}>食品</option>
                <option value="普通物品" ${orderMap.item_Status eq '普通物品'?'selected':''}>普通物品</option>
               <option value="鲜花" ${orderMap.item_Status eq '鲜花'?'selected':''}>鲜花</option>
             </select> --%>
             <u:select id="itemStatus" sname="itemStatus" stype="ITEM_TYPE" classv="validate[required]" value="${orderMap.item_Status}"/>
          </div>
        </li>
        <li style="">
          <div class="line_block wi"><span style="color: red;"></span>物品名称：</div>
          <div class="line_block">
           <input name="itemName"  class="validate[funcCall[zys]]"  id="itemName" type="text"  value="${orderMap.item_name}"    maxlength="60"/> 
          </div>
        </li>
        <li >
          <div class="line_block wi">包裹件数：</div>
          <div class="line_block">
           <input name="item_count" class="validate[custom[integer]] text-input" id="item_count" type="text"   value="${orderMap.item_count}"  maxlength="20"/>个
             </div>
        </li>
        <li>
          <div class="line_block wi">&#12288;&#12288;&#12288;重量：</div>
          <div class="line_block">
           <input  class="validate[funcCall[xiaoshu]]"  value="${orderMap.item_weight}" name="item_weight" type="text"  maxlength="11"/>KG
          </div>
        </li>
          <li>
          <div class="line_block wi">&#12288;</div>
          <div class="line_block">
          <label><input style="float: left;display: inline-block;margin: 5px 5px 0 0; " type="checkbox" name="reqRece" value="Y" <c:out value="${orderMap.req_rece eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">是否有回单</span>
          </label>
          </div>
        </li>
        
        <li>
          <div class="line_block wi">回单编号：</div>
          <div class="line_block">
            <input name="receNo" value="${orderMap.rece_no}"  type="text"  maxlength="50"></input>
          </div>
        </li>
        <li>
          <div class="line_block wi">&#12288;&#12288;&#12288;备注：</div>
          <div class="line_block">
           <input name="orderNote" value="${orderMap.order_note}"  type="text"  maxlength="50"></input>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <div class="form_right">
    <ul>
      <li>
        <div class="form_tit">财务信息</div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>收件员：</b>
        <div class="yundan_in">
         <input id="take_courier_no" class="yundan_input yundan_inputlong validate[required,funcCall[zys]]" name="take_courier_no" value="${orderMap.take_courier_no}" <c:out value="${empty orderMap.take_courier_no?'':'disabled' }"/> type="text"  maxlength="50">
        </div>
      </li>
<%--       <li> <b class="yundan_b">派件员：</b>
        <div class="yundan_in">
        <input id="send_courier_no" class="yundan_input yundan_inputlong validate[funcCall[zys]]"   name="send_courier_no" value="${orderMap.send_courier_no}"  type="text"  maxlength="50"></input>
        </div>
      </li> --%>
      <li> <b class="yundan_b"><span style="color: red;">★</span>付款人：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount validate[required,funcCall[freight_type]]" <c:out value="${orderMap.status>1?'disabled':'' }"/> name="freight_type" type="text"   value="${orderMap.freight_type}"  maxlength="20"/>
         <span style="line-height: 35px;"></br>1.寄方付    2.到方付</span>
        </div>
      </li>
      <li> <b class="yundan_b">付款方式：</b>
        <div class="yundan_in">
         <input class="yundan_input yundan_inputlong cmount validate[funcCall[paytype]]" name="payType" type="text"  <c:out value="${orderMap.status>1?'disabled':'' }"/>  value="${orderMap.pay_type}"  maxlength="20"/>
          <span style="line-height: 35px;"></br>1.现金  2.月结  3.会员卡</span>
        </div>
      </li>
      <li> <b class="yundan_b">月结帐号：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong validate[funcCall[monthSettle]]" id="monthSettleNo" name="monthSettleNo" <c:out value="${orderMap.status>1?'disabled':'' }"/>  value="${orderMap.month_settle_no}"  type="text"  maxlength="50"></input>
        </div>
      </li>
         <li> <b class="yundan_b">月结名称：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong " id="monthSname" name="monthSname" disabled  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">会员号：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong validate[funcCall[phones]]" name="disUserNo" type="text"   value="${orderMap.dis_user_no}" <c:out value="${orderMap.status>1 ?'disabled':'' }"/> >
        </div>
      </li>
      <li> <b class="yundan_b">会员密码：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong validate[minSize[4]]" name="pwd" type="text" <c:out value="${orderMap.status>1 ?'disabled':'' }"/> >
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">代收客户：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong validate[funcCall[zys]]" id="cod_no" name="cod_name" value="${orderMap.cod_name}" <c:out value="${orderMap.status>1 ?'disabled':'' }"/>  type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">公司简称：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong " id="cod_sname" name="cod_sname" disabled  type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">代收货款：</b>
        <div class="yundan_in">
        <input name="good_price" class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]" <c:out value="${orderMap.status>1 ?'disabled':'' }"/>  value="${orderMap.good_price}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">代收手续费(元)：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]"  type="text" <c:out value="${orderMap.status>1 ?'disabled':'' }"/>  name="cpay" value="${orderMap.cpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">保价费：</b>
        <div class="yundan_in">
        <input name="goodValuation"  class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]" <c:out value="${orderMap.status>1 ?'disabled':'' }"/>  value="${orderMap.good_valuation}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">保价手续费(元)：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]"  name="vpay" <c:out value="${orderMap.status>1?'disabled':'' }"/>  type="text" value="${orderMap.vpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>邮费：</b>
        <div class="yundan_in">
        <input name="freight"   class="yundan_input yundan_inputlong cmount validate[required,funcCall[xiaoshu]]" <c:out value="${orderMap.status>1 ?'disabled':'' }"/>    value="${orderMap.freight}" type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">应付：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong"  disabled="disabled" value="${orderMap.tnpay}" name="tnpay" type="text" placeholder="">
        </div>
      </li>
      <li> <b class="yundan_b">总额：</b>
        <div class="yundan_in">
          <input  name="payAcount" disabled="disabled" class="yundan_input yundan_inputlong validate[funcCall[xiaoshu]]"  value="${orderMap.pay_acount}" type="text"  maxlength="50"></input>
        </div>
      </li>
    </ul>
  </div>
  <div class="button_form">
    <input id="submit" name="submit" type="button" value="提交"   width="100" height="50" class="box_but box_but_on"/>
  <input name="reset" type="reset" value="重置" width="100" class="box_but" height="50" />
  </div>
 </form:form>
</div>
</body>


</html>