<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
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
.form_left{ width:46%;}
.yundan_inputlong {width: 140px;}
.yundan_b {width: 90px;}
</style>

<script type="text/javascript">
$().ready(function() {
});


        $(function () {
        	
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
        			 tnpay =  freight1 - (-vpay1) ;
        		 }else{
        			 tnpay = 0 ;
        		 }
        		
        		 amount = good_price1-(-vpay1)-(-freight1);
        		 
        		 $('input[name=tnpay]').val(tnpay) ;
                 $('input[name=payAcount]').val(amount) ;
        		  
        }); 
 	
	$(".cmount1").on('input',function(e){  
		
		var amount = $('input[name=si_payAcount]').val() ;
		var good_price1 = $('input[name=si_good_price]').val() ;
		var cpay1 = $('input[name=si_cpay]').val() ;
		var goodValuation1 = $('input[name=si_goodValuation]').val() ;
		var vpay1 = $('input[name=si_vpay]').val() ;
		var freight1 = $('input[name=si_freight]').val() ;
		
		var freight_type1 = $('input[name=si_freight_type]').val() ;
		var payType1 = $('input[name=si_payType]').val() ;
		
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
			 tnpay = good_price1 ;
		 }
		 if(freight_type1=='2'||freight_type1=='到方付'){
			 tnpay =  freight1 - (-vpay1) - (-good_price1) ;
				switch(payType1)
				{
				case '1':case '现金':case '3':case '会员卡':
				 tnpay = freight1 - (-vpay1)  - (-good_price1);
				  break;
				case '2':case '月结':
				  tnpay =  freight1 - (-vpay1)  - (-good_price1);
  				  break;
				default:break;
				}
		 }
		
		 amount = good_price1-(-vpay1)-(-freight1);
		 
		 $('input[name=si_snpay]').val(tnpay) ;
         $('input[name=si_payAcount]').val(amount) ;
		  
}); 
 	

 	       var clist = ${clist} ; 	 
        	var availablesrcKey1 = [];
            $.each(clist, function(i, item) {
            	var inner_no = "" ;
            	if(item.inner_no){inner_no=item.inner_no+','}
            	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
            });
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
              $jqq("#send_courier_no").autocomplete(availablesrcKey1, {
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
              
              
        	 	
             	var mlist = ${mlist} ; 	 
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
               
               	$jqq("#si_monthSettleNo").autocomplete(mres, {
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
               			 $("#si_monthSettleNo").val(data[0].substring(0,data[0].indexOf('('))) ;
               			 $("#si_monthSname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
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
          	  $jqq("#si_cod_no").autocomplete(codres, {
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
             			 $("#si_cod_no").val(data[0].substring(0,data[0].indexOf('('))) ;
             			 $("#si_cod_sname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
       			       } 
             	}); 	 

        
     	  
     	   $('#submit').click(function(){
     		  var api = frameElement.api, W = api.opener;
     		  if($("#vf").validationEngine("validate")){  
     			
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
     		   
    			 var send_courier_no = $('input[name=send_courier_no]').val();
    			 if(send_courier_no.length<2){
     		    	$('input[name=send_courier_no]').focus();
     				 return false;
     			 } 
    			 if(send_courier_no.length>1&&send_courier_no.length<5){
    				 alert('派件快递员信息错误');
    				 return false;
    			 }
    			 if(send_courier_no.indexOf(')')>-1){
    				 send_courier_no = send_courier_no.substring(0,send_courier_no.indexOf('(')); 
    			 } 
    			 var orderNo =  $('input[name=orderNo]').val() ;
    			 var lgcOrderNo =  $('input[name=lgcOrderNo]').val() ;
    			 var freight_type = $('input[name=freight_type]').val() ;
    			 var payType = $('input[name=payType]').val() ;
    			 var monthSettleNo = $('input[name=monthSettleNo]').val() ;
    			 if(monthSettleNo.indexOf(')')>-1){
    				 monthSettleNo = monthSettleNo.substring(0,monthSettleNo.indexOf('(')); 
    			 } 
     			 var disUserNo = $('input[name=disUserNo]').val() ;
     			 var cod_name = $('input[name=cod_name]').val() ;
     			 var good_price = $('input[name=good_price]').val() ;
     			 var cpay = $('input[name=cpay]').val() ;
     			 var goodValuation = $('input[name=goodValuation]').val() ;
     			 var vpay = $('input[name=vpay]').val() ;
     			 var freight = $('input[name=freight]').val() ;
     			 var tnpay = $('input[name=tnpay]').val() ;
     			 var payAcount = $('input[name=payAcount]').val() ;
     			 var cod = 0 ;
     			 if(good_price>0){
     				cod = 1 ;
     			 }
     			
     			 if(freight_type.length<1){
       				 return false;
       			 }
     			if('寄方付'==freight_type){freight_type = 1 ;}
     			if('到方付'==freight_type){freight_type = 2 ;}
     			//
     			if('现金'==payType||'1'==payType){payType = 'CASH' ;}
     			if('月结'==payType||'2'==payType){payType = 'MONTH';}
     			if('会员卡'==payType||'3'==payType){payType = 'HUIYUAN' ;}
     			//
     			 if(freight.length<1){
       		    	$('input[name=freight]').focus();
       				 return false;
       			 }
     			if(freight<=0){
     				alert("邮费必须大于0") ;
     				$('input[name=freight]').focus();
     				 return false;
     			}
     			 if(payAcount.length<1){
      		    	$('input[name=payAcount]').focus();
      				 return false;
      			 }
     			 
     			 var si_freight_type = $('input[name=si_freight_type]').val() ;
    			 var si_payType = $('input[name=si_payType]').val() ;
    			 var si_monthSettleNo = $('input[name=si_monthSettleNo]').val() ;
    			 if(si_monthSettleNo.indexOf(')')>-1){
    				 si_monthSettleNo = si_monthSettleNo.substring(0,si_monthSettleNo.indexOf('(')); 
    			 } 
     			 var si_disUserNo = $('input[name=si_disUserNo]').val() ;
     			 var si_cod_name = $('input[name=si_cod_name]').val() ;
     			 var si_good_price = $('input[name=si_good_price]').val() ;
     			 var si_cpay = $('input[name=si_cpay]').val() ;
     			 var si_goodValuation = $('input[name=si_goodValuation]').val() ;
     			 var si_vpay = $('input[name=si_vpay]').val() ;
     			 var si_freight = $('input[name=si_freight]').val() ;
     			 var si_snpay = $('input[name=si_snpay]').val() ;
     			 var si_payAcount = $('input[name=si_payAcount]').val() ;
     			 
     			if(si_freight_type.length<1){
      				 return false;
      			 }
    			if('寄方付'==si_freight_type){si_freight_type = 1 ;}
    			if('到方付'==si_freight_type){si_freight_type = 2 ;}
    			//
    			if('现金'==si_payType||'1'==si_payType){si_payType = 'CASH' ;}
    			if('月结'==si_payType||'2'==si_payType){si_payType = 'MONTH';}
    			if('会员卡'==si_payType||'3'==si_payType){si_payType = 'HUIYUAN' ;}
    			//
    			 if(si_freight.length<1){
      		    	$('input[name=si_freight]').focus();
      				 return false;
      			 }
    			if(si_freight<=0){
    				alert("邮费必须大于0") ;
    				$('input[name=si_freight]').focus();
    				 return false;
    			}
    			 if(si_payAcount.length<1){
     		    	$('input[name=si_payAcount]').focus();
     				 return false;
     			 }
     			 
   			
     			 $.ajax({
     				 type: "post",//使用get方法访问后台
     		        dataType: "text",//返回json格式的数据
     		        url: "/order/examine_update",//要访问的后台地址
     		         data: {'takeCourierNo':take_courier_no,'sendCourierNo':send_courier_no,'cod':cod,'orderNo':orderNo,'lgcOrderNo':lgcOrderNo, 
     		            	'freight_type':freight_type,'payType':payType,'monthSettleNo':monthSettleNo,'disUserNo':disUserNo,'cod_name':cod_name,
     		            	 'good_price':good_price,'cpay':cpay,'goodValuation':goodValuation,'vpay':vpay,'freight':freight,'tnpay':tnpay,'payAcount':payAcount,
     		            	'si_freight_type':si_freight_type,'si_payType':si_payType,'si_monthSettleNo':si_monthSettleNo,'si_disUserNo':si_disUserNo,'si_cod_name':si_cod_name,'si_good_price':si_good_price,
    		            	 'si_cpay':si_cpay,'si_goodValuation':si_goodValuation,'si_vpay':si_vpay,'si_freight':si_freight,'si_snpay':si_snpay,'si_payAcount':si_payAcount
     		            },//要发送的数据
     		            success: function(msg){//msg为返回的数据，在这里做数据绑定
     		            	if('1'==msg){
     		            		alert("修改成功");
     		            		api.close() ;
     		            		//api.reload() ;
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
 <div  class="split">信息一致方可审核通过</div>    
<div class="form_list">
    
   <form:form id="vf" action="${ctx}/order/einput" method="get">   
   <input type="hidden" name="orderNo" value="${params.orderNo}"/>
    <input type="hidden" name="lgcOrderNo" value="${params.lgcOrderNo}"/>
  <div class="form_left">
 <ul>
      <li>
        <div class="form_tit">收入补录</div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>收件员：</b>
        <div class="yundan_in">
         <input id="take_courier_no" class="yundan_input yundan_inputlong validate[required,funcCall[zys]]" name="take_courier_no" value="${oMap.take_courier_no}"  type="text"  maxlength="50">
        </div>
      </li>

      <li> <b class="yundan_b"><span style="color: red;">★</span>付款人：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount validate[required,funcCall[freight_type1]]"  name="freight_type" type="text"   value="${oMap.freight_type}"  maxlength="20"/>
         <span style="line-height: 35px;"></br>1.寄方付    2.到方付</span>
        </div>
      </li>
      <li> <b class="yundan_b">付款方式：</b>
        <div class="yundan_in">
         <input class="yundan_input yundan_inputlong cmount validate[funcCall[paytype]]" name="payType" type="text"   value="${oMap.pay_type}"  maxlength="20"/>
          <span style="line-height: 35px;"></br>1.现金  2.月结  3.会员卡</span>
        </div>
      </li>
      <li> <b class="yundan_b">月结帐号：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong validate[funcCall[monthSettle]]" id="monthSettleNo" name="monthSettleNo"   value="${oMap.month_settle_no}"  type="text"  maxlength="50"></input>
        </div>
      </li>
         <li> <b class="yundan_b">月结名称：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong " id="monthSname" name="monthSname" disabled  value="${mMap.month_sname}" type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">会员号：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong validate[funcCall[phones]]" name="disUserNo" type="text"  value="${oMap.dis_user_no}"  >
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">代收客户：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong validate[funcCall[zys]]" id="cod_no" name="cod_name" value="${oMap.cod_name}"   type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">公司简称：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong " id="cod_sname" name="cod_sname" value="${codMap.cod_sname}"  disabled  type="text" mMap maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">代收货款：</b>
        <div class="yundan_in">
        <input name="good_price" class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]" value="${oMap.good_price}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">代收手续费：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]"  type="text"   name="cpay" value="${oMap.cpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">保价费：</b>
        <div class="yundan_in">
        <input name="goodValuation"  class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]" value="${oMap.good_valuation}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">保价手续费：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount validate[funcCall[xiaoshu]]"  name="vpay"   type="text" value="${oMap.vpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>邮费：</b>
        <div class="yundan_in">
        <input name="freight"   class="yundan_input yundan_inputlong cmount validate[required,funcCall[xiaoshu]]" value="${oMap.freight}" type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">应付：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong"  disabled="disabled" value="${oMap.tnpay}" name="tnpay" type="text" placeholder="">
        </div>
      </li>
      <li> <b class="yundan_b">总额：</b>
        <div class="yundan_in">
          <input  name="payAcount" disabled="disabled" class="yundan_input yundan_inputlong validate[funcCall[xiaoshu]]"  value="${oMap.pay_acount}" type="text"  maxlength="50"></input>
        </div>
      </li>
    </ul>
  </div>
  
  
  
  <div class="form_right">
    <ul>
      <li>
        <div class="form_tit">签收录入</div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>派件员：</b>
        <div class="yundan_in">
         <input id="send_courier_no" class="yundan_input yundan_inputlong validate[required,funcCall[zys]]" name="send_courier_no" value="${oMap.send_courier_no}"  type="text"  maxlength="50">
        </div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>付款人：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount1 validate[required,funcCall[freight_type2]]" name="si_freight_type" type="text"   value="${sMap.si_freight_type}"  maxlength="20"/>
         <span style="line-height: 35px;"></br>1.寄方付    2.到方付</span>
        </div>
      </li>
      <li> <b class="yundan_b">付款方式：</b>
        <div class="yundan_in">
         <input class="yundan_input yundan_inputlong cmount1 validate[funcCall[paytype1]]" name="si_payType" type="text"   value="${sMap.si_pay_type}"  maxlength="20"/>
          <span style="line-height: 35px;"></br>1.现金  2.月结  3.会员卡</span>
        </div>
      </li>
      <li> <b class="yundan_b">月结帐号：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong validate[funcCall[monthSettle]]" id="si_monthSettleNo" name="si_monthSettleNo"   value="${sMap.si_month_settle_no}"  type="text"  maxlength="50"></input>
        </div>
      </li>
         <li> <b class="yundan_b">月结名称：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong " id="si_monthSname" name="si_monthSname"  value="${si_mMap.month_sname}"   disabled  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">会员号：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong validate[funcCall[phones]]" name="si_disUserNo" type="text"  value="${sMap.si_dis_user_no}"  >
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">代收客户：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong validate[funcCall[zys]]" id="si_cod_no" name="si_cod_name" value="${sMap.si_cod_name}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">公司简称：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong " id="si_cod_sname" name="si_cod_sname" value="${si_codMap.cod_sname}"  disabled  type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">代收货款：</b>
        <div class="yundan_in">
        <input name="si_good_price" class="yundan_input yundan_inputlong cmount1 validate[funcCall[xiaoshu]]"  value="${sMap.si_good_price}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">代收手续费：</b>
        <div class="yundan_in">
        <input  class="yundan_input yundan_inputlong cmount1 validate[funcCall[xiaoshu]]"  type="text"   name="si_cpay" value="${sMap.si_cpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b">保价费：</b>
        <div class="yundan_in">
        <input name="si_goodValuation"  class="yundan_input yundan_inputlong cmount1 validate[funcCall[xiaoshu]]"   value="${sMap.si_good_valuation}"  type="text"  maxlength="50"></input>
        </div>
      </li>
      <li> <b class="yundan_b">保价手续费：</b>
        <div class="yundan_in">
        <input class="yundan_input yundan_inputlong cmount1 validate[funcCall[xiaoshu]]"  name="si_vpay"   type="text" value="${sMap.si_vpay}"  maxlength="20"/><span></span>
        </div>
      </li>
      <li>
        <div class="border_form"></div>
      </li>
      <li> <b class="yundan_b"><span style="color: red;">★</span>邮费：</b>
        <div class="yundan_in">
        <input name="si_freight"   class="yundan_input yundan_inputlong cmount1 validate[required,funcCall[xiaoshu]]"   value="${sMap.si_freight}" type="text"  maxlength="50"></input>
        </div>
      </li>
      
      <li> <b class="yundan_b">应付：</b>
        <div class="yundan_in">
          <input  class="yundan_input yundan_inputlong"  disabled="disabled" value="${sMap.si_snpay}" name="si_snpay" type="text" placeholder="">
        </div>
      </li>
      <li> <b class="yundan_b">总额：</b>
        <div class="yundan_in">
          <input  name="si_payAcount" disabled="disabled" class="yundan_input yundan_inputlong validate[funcCall[xiaoshu]]"  value="${sMap.si_pay_acount}" type="text"  maxlength="50"></input>
        </div>
      </li>
    </ul>
  </div>
    <div class="button_form">
    <input id="submit" name="submit" type="button" value="提交"   width="100" height="50" class="box_but box_but_on"/>
  <input name="reset" type="reset"  roload="N"  value="重置" width="100" class="box_but" height="50" />
  </div>
 </form:form>
</div>
</body>


</html>