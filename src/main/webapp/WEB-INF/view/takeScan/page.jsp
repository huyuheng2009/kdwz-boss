<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style>
.shoujian_cont { overflow:auto; zoom:1;}
.shoujian_cont .shoujian_cli { float:left; display:block; padding:10px; text-align:left;}
.shoujian_cont .shoujian_cli.left { width:340px;}
.shoujian_cont .shoujian_cli.center { width:340px;}
.shoujian_cont .shoujian_cli.right { width:260px;}
.shoujian_cli .shou_li { line-height:40px;}
.shoujian_cli .shou_li strong { display:inline-block; width:90px; text-align:right; font-weight:normal;}
.shoujian_cli .shou_li b { color:red; margin-right:2px; font-weight:normal;}
.shoujian_cli .shou_li > input { width:190px; height:28px; padding:0 8px; margin:0 10px 0 20px; border:1px solid #aaa;}
.shoujian_cli .shou_li label { display:inline-block; margin-left:20px; }
.shoujian_cli .shou_li label > input { vertical-align:middle; margin-right:5px;}
.shoujian_cli .shou_li label > i { font-style:normal; }
.shoujian_cli .shou_li > input.binput01 { width:112px; margin:0 0 0 20px;}
.shoujian_cli .shou_li > input.binput02 { background:#aaa; width:60px; margin:0 10px 0 0; text-align:right;}
.shoujian_cli .shou_li > input.binput03 { width:60px;}
.shoujian_cli .shou_li > input.binput02:focus { border:1px solid #aaa; box-shadow:none; -webkit-box-shadow:none;}
.shoujian_cli .shou_li > input:focus { border:1px solid #74b9f0;}
.shou_span{ font-size:24px; margin:40px 0 0 20px;}
.shou_span b{ color:red; font-size:24px;}
.shoujian_table { padding:20px 10px; border-top:1px solid #999; margin:0 20px;}
.shoujian_p{ padding:20px 10px;}
.shoujian_p p{line-height:2em; font-size:13px;}
@media screen and (min-width: 1201px) { 
.shoujian_cont .shoujian_cli.left { width:430px;}
.shoujian_cont .shoujian_cli.center { width:430px;}
.shoujian_cont .shoujian_cli.right { width:280px;}
.shoujian_cli .shou_li { line-height:50px;}
.shoujian_cli .shou_li strong { width:130px; }
.shoujian_cli .shou_li > input { width:220px;}
.shoujian_cli .shou_li > input.binput01 { width:132px;}
.shoujian_cli .shou_li > input.binput02 { width:70px; }
.shoujian_cli .shou_li > input.binput03 { width:70px;}
.shou_span{  margin:55px 0 0 20px;}
</style>
    <script type="text/javascript">
    var minWeight ="${WeightConfig.rev_minv}";
    
    var c = 0 ;
    var list = [] ;
    Array.prototype.contains = function(item){
        return RegExp(item).test(this);
    };
    function submits(){
    	  if($("#vf").validationEngine("validate")){  
    		  
    		  var take_courier_no = $('input[name=takeCourier]').val();
    		  if(take_courier_no.indexOf(')')>-1){
    				 take_courier_no = take_courier_no.substring(0,take_courier_no.indexOf('(')); 
    			 } 
    		  
    		 var freight_type = $('input[name=freight_type]').val() ;
 			 if(freight_type.length<1){
   				 return false;
   			 }
 			 
 			 var fname = "" ;
 			if('寄方付'==freight_type){freight_type = 1 ;fname = "寄方付" ;}
 			if('到方付'==freight_type){freight_type = 2 ;fname = "到方付" ;}
 			
 			
 			var payType = $('input[name=payType]').val() ;
 			
 			var pname = "" ;
 			
 			if('现金'==payType||'1'==payType){payType = 'CASH' ;pname="现金";}
 			if('月结'==payType||'2'==payType){payType = 'MONTH';pname="月结";}
 			if('会员卡'==payType||'3'==payType){payType = 'HUIYUAN' ;pname="会员卡";}
 			
 			
 			var freight = $('input[name=freight]').val() ;
			if(freight<0){
				alert("邮费必须大于=0") ;
				$('input[name=freight]').focus();
				 return false;
			}
			var item_weight= $('input[name=item_weight]').val() ;
			if(parseFloat(item_weight)<0){ 
				alert("重量必须大于=0") ;
				return false;
			}
			if(minWeight!=''){
				if(parseFloat(item_weight)<parseFloat(minWeight)){ 
					$('input[name=item_weight]').val(minWeight.trim()) ;
				}
			}
			
			var lgcOrderNo = $('input[name=lgcOrderNo]').val() ;
			
			var good_price = $('input[name=good_price]').val() ;
			if(!good_price){good_price=0;}
			
			var cod_name = $('input[name=codNo]').val() ;
			var monthSettleNo = $('input[name=monthNo]').val() ;
			
			var cod = 0 ;
			if(good_price>0){
				cod = 1 ;
			}
    		  
			var goodValuation = $('input[name=good_valuation]').val() ;  
			if(!goodValuation){goodValuation=0;}
			var vpay = $('input[name=vpay]').val() ;    
			if(!vpay){vpay=0;}
			
    			 
    	  		 $.ajax({
    	  			 type: "post",//使用get方法访问后台
    	  	            dataType: "json",//返回json格式的数据
    	  	          url: "${ctx }/takeScan/tsave",//要访问的后台地址
    		            data: {'lgcOrderNo':lgcOrderNo,'cod':cod,'takeCourierNo':take_courier_no,'goodPrice_':good_price,
    		            	'codName':cod_name,'goodValuation_':goodValuation,'vpay_':vpay,'freight_':freight,'freightType':freight_type,
    		            	'monthSettleNo':monthSettleNo,'payType':payType,"itemWeight_":item_weight,"source":"BOSS_REV"
 		            },//要发送的数据
    	  	            success: function(data){//msg为返回的数据，在这里做数据绑定
    	  	            	if(data.ret==1){
    	  	            		c = c - (-1);
    		   	            	 var html = '<tr>' ;
    		   	            	    html += '<td align="center">'+data.data.takeTime+'</td>';
    		   	            	    html += '<td align="center">'+lgcOrderNo+'</td>';
    		   	                    html += '<td align="center">'+data.data.tname+'</td>';
    		   	                    html += '<td align="center">'+fname+'</td>';
    		   	                    html += '<td align="center">'+pname+'</td>';
    		   	                    html += '<td align="center">'+data.data.itemStatus+'</td>';
    		   	                    html += '<td align="center">'+good_price+'</td>';
    		   	                    var cname = "" ;
    		   	                    if(data.data.cname){cname=data.data.cname; }
    		   	                    html += '<td align="center">'+cname+'</td>';
    		   	                    html += '<td align="center">'+freight+'</td>';
    		   	                    html += '<td align="center">'+goodValuation+'</td>';
    		   	                    html += '<td align="center">'+vpay+'</td>';
    		   	                    html += '<td align="center">'+data.data.scanName+'</td>';
    		   	                    html += '</tr>';
    		   	               $("#tb1").prepend(html) ; 
    		   	                 $('#tips').html("已扫描，共扫描"+c+"条记录");
    		   	                 $('#piao').html(c);
    		   	                 $('input[name=lgcOrderNo]').val("");	  
    		   	    	         $('input[name=lgcOrderNo]').focus(); 
    		   	                 
    		   	            	}else{
    		   	            	alert(data.ret,$('input[name=lgcOrderNo]'),function(obj){
    		   	                    $('input[name=lgcOrderNo]').focus(); 
    		   	                 return false ;	 
    		   	            	});
    		   	            	}
    	  	            }
    	  		 });
    	  }
     return false ;	   
   	   
   }
   
   $(function(){
	   trHover('t2');
   	 $('input[name=takeCourier]').focus(); 
   	 
  	$("#submit").click(function(e){ 
  		submits() ;
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
               $(":input:text:eq(" + nxtIdx + ")").focus(); 
    		   
    	 }
     });
     
     
		jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {
			var data1 = tmjs.clist ;
	          	var availablesrcKey1 = [];
	              $.each(data1, function(i, item) {
	              	var inner_no = "" ;
	              	if(item.inner_no){inner_no=item.inner_no+','}
	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
	              });
	              val1 = '';
	                $jqq("#takeCourier").autocomplete(availablesrcKey1, {
	             		minChars: 0,
	             		max: 12,
	             		autoFill: true,
	             		mustMatch: false,
	             		matchContains: true,
	             		scrollHeight: 220,
	             		formatItem: function(data11, i, total) {
	             			return data11[0];
	             		}
	             	}).result(function(event, data, formatted) {
	             		if(data[0].indexOf(')')>-1){
	           			 $("#takeCourier").val(data[0]) ;
	     			       } 
	           	});	
			
	                var mlist =  tmjs.mlist ;
	                 	var mres = [];
	                 	$.each(mlist, function(i, item) {
	                 		var name = item.month_sname ;
	                 		if(name.length<1){name = item.month_name ;}
	                      	mres[i]= item.month_settle_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+")";
	                      }); 
	                 	  $jqq("#monthNo").autocomplete(mres, {
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
	                   			 $("#monthNo").val(data[0].substring(0,data[0].indexOf('('))) ;
	             			  } 
	                   	}); 
			
	                  	var codList = tmjs.codlist ;
	                  	console.log(codList);
	                   	var codres = [];
	                   	$.each(codList, function(i, item) {
	                   		var name = item.cod_sname ;
	                   		if(name.length<1){name = item.cod_name ;}
	                   		codres[i]=item.cod_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
	                        }); 
	                   	  $jqq("#codNo").autocomplete(codres, {
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
	                     			 $("#codNo").val(data[0].substring(0,data[0].indexOf('('))) ;
	                     			// $("#cod_sname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
	               			       } 
	                     	}); 			 
	                   	          	  
	                 	  
		});		  //动态加载
		var vPercentPay = "${valuationRule.rate}";//保价手续费百分比
	    var maxPay = "${valuationRule.maxv}";//最高保价手续费
	    var minPay = "${valuationRule.minv}";
		$("input[name=good_valuation]").change(function(){  
			var v= $(this).val();
			if(v==''){
				$("input[name=vpay]").val('');
				return false;
			}
			var minvPay = parseFloat(minPay);
			var maxvPay = parseFloat(maxPay);
			if(v<minvPay){
				$("input[name=good_valuation]").val(minvPay);
				v=minvPay;
				alert("保价费不能小于"+minPay);
			}else if(v>maxvPay){
				$("input[name=good_valuation]").val(maxvPay);
				v=maxvPay;
				alert("保价费不能大于"+maxPay);
			}
			var pay = parseFloat(vPercentPay)*parseFloat(v)/1000;
			$("input[name=vpay]").val(pay);
			
			
		 });
		
		$("input[name=item_weight]").change(function(){
			var item_weight =$('input[name=item_weight]').val().trim() ;
			$('input[name=item_weight]').val(item_weight);
			if(minWeight!=''){
				if(parseFloat(item_weight)<parseFloat(minWeight)){ 
					$('input[name=item_weight]').val(minWeight.trim()) ;
				}
			}
			
		});
}); 
   

    </script>
</head>
<body>
  <form:form id="vf" action="" method="get" >
<div class="shoujian_cont">
	<div class="shoujian_cli left">
    	<div class="shou_li"><strong><b>*</b>取件员</strong><input type="text" placeholder="必填"  class="validate[required]" name="takeCourier" id="takeCourier"/></div>
	<div class="shou_li"><strong><b>*</b>收款人</strong><input type="text" placeholder="必填"   name="freight_type"  class="binput03 validate[required,funcCall[freight_type]]"/>
            <b>1. 寄方付&nbsp&nbsp&nbsp</b>
            <b>2. 到方付</b>
        </div>
        <div class="shou_li"><strong><b>*</b>付款方式</strong><input type="text" name="payType" class="binput03  validate[funcCall[paytype]]"/>
            <b>1. 现金&nbsp&nbsp&nbsp</b>
            <b>2. 月结</b>
        </div>
        <div class="shou_li"><strong><b>*</b>重量</strong><input type="text" name="item_weight" class="binput03  validate[required]"  value="${WeightConfig.rev_minv}"/>kg</div>
        <div class="shou_li"><strong><b>*</b>运费</strong><input  class="validate[required,funcCall[xiaoshu]]"  name="freight" id="freight"  placeholder="必填"  type="text"/>元</div>
        <div class="shou_li"><strong><b>*</b>运单号码</strong><input  class="validate[required,funcCall[lgcOrderNo]] submit" name="lgcOrderNo"  placeholder="必填" type="text" />
                  
        </div>
        
    </div>
    <div class="shoujian_cli center">
    	<div class="shou_li"><strong>代收货款</strong><input type="text"  name="good_price"  id="good_price" class="validate[funcCall[xiaoshu]]" />元</div>  
        <div class="shou_li"><strong>代收货款客户</strong><input type="text" name="codNo" id="codNo"/></div>
        <div class="shou_li"><strong>月结客户</strong><input type="text" name="monthNo" id="monthNo"/></div> 	
        <div class="shou_li"><strong>保价费</strong><input type="text" class="binput01 validate[funcCall[xiaoshu]]" name="good_valuation" />
                                             <input type="text" class="binput02 validate[funcCall[xiaoshu]] submit" name="vpay"  value="" readonly="readonly"/>元</div> 
        <div class="shou_li"><strong></strong> <input class="button input_text  medium blue_flat" type="button" id="submit" value="发送"/> 	</div> 
    </div>
    <div class="shoujian_cli right">
    	<div class="shou_span">扫描票数：<b id="piao">0</b></div>  	
    </div>
</div>
<div class="shoujian_table">
<div style="width:100%;height: 30px;line-height: 30px;text-align: center;color: red;" id="tips" name="0" value="0"></div>
	<div class="ta_table">
     <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">取件时间</th>
            <th align="center">运单号码</th>
            <th align="center">取件员</th>
            <th align="center">付款人</th>
            <th align="center">付款方式</th>
            <th align="center">物品类型</th>
           <th align="center">代收货款</th>
           <th align="center">代收货款客户</th>
           <th align="center">运费</th>
           <th align="center">保价费</th>
           <th align="center">保价手续费</th>
          <th align="center">扫描员</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
  	</div>
   
</div>

</form:form>
</body>

</html>