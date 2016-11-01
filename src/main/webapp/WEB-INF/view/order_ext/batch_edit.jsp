<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<link rel="stylesheet" href="${ctx}/themes/default/check.css"/>
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/scripts/Sortable.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>

<style>
tr{height:50px;}
.readonly{background:#ddd;}
.tableble_table tr td {border:0px;}
.myinput{height:25px !important;}
</style>
    <script type="text/javascript">
    	$(function(){
    		var api = frameElement.api, W = api.opener;
    		var codMax = "${codRule.maxv}";
    		var codMin = "${codRule.minv}";
    		var valRate ="${valRule.rate}";
    		var valMin = "${valRule.minv}";
    		var valMax = "${valRule.maxv}";
    		$("input[name=good_price]").blur(function(){
    			if($(this).val().trim()==''){
    				return false;
    			}
    			if(isNaN($(this).val().trim())){
    				$(this).val("");
    				return false;
    			}
    			if(parseFloat($(this).val())>codMax||parseFloat($(this).val())<codMin){
    				alert("代收款请输入"+codMin+"-"+codMax);
    				$(this).val("");
    				return false;
    			}
    			
    		});
    		
    		$("input[name=good_valuation]").blur(function(){
    			if($(this).val().trim()==''){
    				$("input[name=vpay]").val('');
    				return false;
    			}
    			if(isNaN($(this).val().trim())){
    				$(this).val('');
    				$("input[name=vpay]").val('');
    				return false;
    			}
    			if(NaNValue($(this).val())>valMax||NaNValue($(this).val())<valMin){
    				$(this).val("");
    				$("input[name=vpay]").val('');
    				alert("代收款请输入"+valMin+"-"+valMax);
    				return false;
    			}else{
    				var tmp=parseFloat($(this).val())*valRate/1000;
    				$("input[name=vpay]").val(tmp);
    			}
    			
    		});
    		
    		
    		$("input[name=freight],input[name=good_valuation],input[name=good_price],input[name=vpay]").blur(function(){
    			var val1 = $("input[name=good_price]").val();
    			var val2 = $("input[name=good_valuation]").val();
    			var val3 = $("input[name=vpay]").val();
    			var val4 = $("input[name=freight]").val();
    			if(val1==''&&val2==''&&val3==''&&val4==''){
    				$("input[name=pay_acount]").val('');
    				return false;
    			}
    			var v1 = NaNValue(val1);
    			var v2 = NaNValue(val2);
    			var v3 = NaNValue(val3);
    			var v4 = NaNValue(val4);
    			$("input[name=pay_acount]").val(v1+v2+v3+v4);
    			
    		});
    		
    		/*
    		
			$("input[name=month_settle_no]").blur(function(){
				if($.trim($(this).val()).length>0){
	   		    	 $.getJSON("${ctx}/mobile/getbyno", {'monthSettleNo':$(this).val()},function(data) {  
	   		    		 if(data!=null){
	   		    			 $('#month_name').val(data.month_name) ;
	   		    		 }else{
	   		    			 alert("无此月结号") ;
	   		    			 $('input[name=month_settle_no]').focus(); 
	   		    		 }
	   		    	 });
	   		    }  
    			
    		});*/
    		
    		 /*
    		$("input[name=take_courier_no]").blur(function(){
    			var no =$(this).val();
	    		if($.trim(no).length>0){
	    			$.ajax({
	    				   type: "POST",
	    				   url: "${ctx}/lgc/queryByCourierNo",
	    				   data: "courierNo="+no,
	    				   success: function(msg){
	    					 if(msg.status=='1'){
	    						 alert("快递员不存在");
	    						 return false;
	    					 }
	    					 if(msg.courierNo==''){
	    						 alert("不存在该快递员");
	    						 $(this).val("");
	      						 $("#take_courier_name").val("");
	      						$("input[name=sub_station_no]").val("");
	    						 return false;
	    					 }
	    				     $("#take_courier_name").val(msg.innerNo+","+msg.realName);
	    				     $("input[name=sub_station_no]").val(msg.substationNo);
	    				   }
	    				});
	    			}
    		});
    		
    		$("input[name=send_courier_no]").blur(function(){
    			var no =$(this).val();
    			if($.trim(no).length>0){
	    			$.ajax({
	    				   type: "POST",
	    				   url: "${ctx}/lgc/queryByCourierNo",
	    				   data: "courierNo="+no,
	    				   success: function(msg){
	    					   if(msg.status=='1'){
	      						 alert("快递员不存在");
	      						 return false;
	      					 }
	    					   if(msg.courierNo==''){
	      						 alert("不存在该快递员");
	      						 $(this).val("");
	      						 $("#send_courier_name").val("");
	      						$("input[name=send_substation_no]").val("");
	      						 return false;
	      					 }
	    				     $("#send_courier_name").val(msg.innerNo+","+msg.realName);
	    				     $("input[name=send_substation_no]").val(msg.substationNo);
	    				   }
	    				});
    			}
    		});
    		
    		*/
    		
    		
    		
    		
    		jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
  			console.log(tmjs.clist) ;	
  			var data1 = tmjs.clist ;
  	          	var availablesrcKey1 = [];
  	              $.each(data1, function(i, item) {
  	              	var inner_no = "" ;
  	              	if(item.inner_no){inner_no=item.inner_no+','}
  	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
  	              });
  	              val1 = '';
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
  	             	}).result(function(event, data, formatted) {
  	             		if(data[0].indexOf(')')>-1){
  	           			 $("#take_courier_no").val(data[0].substring(0,data[0].indexOf('('))) ;
  	           			 $("#take_courier_name").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
  	           			 
  	           			    var k=data[0].substring(0,data[0].indexOf('('));
	  	           			for(var j=0;j<data1.length;j++){
	  	           				if(k==data1[j].courier_no){
	  	           					$("input[name=sub_station_no]").val(data1[j].substation_no+","+data1[j].substation_name);
	  	           					break;
	  	           				}
	  	           			}
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
	             	}).result(function(event, data, formatted) {
	             		if(data[0].indexOf(')')>-1){
	           			 $("#send_courier_no").val(data[0].substring(0,data[0].indexOf('('))) ;
	           			 $("#send_courier_name").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
	           			 var k=data[0].substring(0,data[0].indexOf('('));
	  	           			for(var j=0;j<data1.length;j++){
	  	           				if(k==data1[j].courier_no){
	  	           					$("input[name=send_substation_no]").val(data1[j].substation_no+","+data1[j].substation_name);
	  	           					break;
	  	           				}
	  	           			}
	     			       } 
	           	     });	
	
  		///快递员
  	                var slist =  tmjs.slist ;
  	                    			 
  	              //分站      			 
  	                  var mlist =  tmjs.mlist ;
  	                 	var mres = [];
  	                 	$.each(mlist, function(i, item) {
  	                 		var name = item.month_sname ;
  	                 		if(name.length<1){name = item.month_name ;}
  	                      	mres[i]= item.month_settle_no.replace(/\ /g,"")+'!'+name.replace(/\ /g,"")+'#'+"!"+item.month_name+"|"+item.contact_addr+"|"+item.contact_phone+"#";
  	                      }); 
  	                 	  $jqq("#monthSettleNo").autocomplete(mres, {
  	                   		minChars: 0,
  	                   		max: 12,
  	                   		autoFill: true,
  	                   		mustMatch: false,
  	                   		matchContains: true,
  	                   		scrollHeight: 220,
  	                   		formatItem: function(data, i, total) {
  	                   			return data[0].substring(0,data[0].indexOf('#')+1).replace("!","(").replace("#",")");
  	                   		}
  	                   	}).result(function(event, data, formatted) {
  	                   		if(data[0].indexOf('#')>-1){
  	                   			 $("#monthSettleNo").val(data[0].substring(0,data[0].indexOf('!'))) ;
  	                   			 $("#month_name").val(data[0].substring(data[0].indexOf('!')+1,data[0].indexOf('#')));
  	             			  } 
  	                   		//_submit() ;
  	                   	}); 	          	  
  	                 	  
  		});		  //动态加载
    		
    		
    		
    		
    		
    		
    		
    		
    		
			  
    		$("#submit").click(function(){
    			var ids =$("input[name=ids]").val();
    			var item_weight = $("input[name=item_weight]").val();
    			var good_price = $("input[name=good_price]").val();
    			
    			var good_valuation = $("input[name=good_valuation]").val();
    			var freight = $("input[name=freight]").val();
    			var freight_type = $("input[name=freight_type]").val();
    			var pay_type = $("input[name=pay_type]").val();
    			var month_settle_no = $("input[name=month_settle_no]").val();
    			var take_courier_no = $("input[name=take_courier_no]").val();
    			var send_courier_no = $("input[name=send_courier_no]").val();
    			var sub_station_no = $("input[name=sub_station_no]").val();
    			var send_substation_no = $("input[name=send_substation_no]").val();
    			var take_order_time = $("input[name=take_order_time]").val();
    			var send_order_time = $("input[name=send_order_time]").val();
    			$.ajax({
 				   type: "POST",
 				   url: "${ctx}/order_ext/batchedit_order",
 				   data: "ids="+ids+"&item_weight="+item_weight+"&good_price="+good_price+"&good_valuation="+good_valuation+"&freight="+freight+"&freight_type="+freight_type+"&pay_type="+pay_type+"&month_settle_no="+month_settle_no+"&take_courier_no="+take_courier_no+"&send_courier_no="+send_courier_no+"&take_order_time="+take_order_time+"&send_order_time="+send_order_time,
 				   success: function(msg){
 					   alert(msg)
 					   setTimeout(function(){
 						  api.close();
 					   }, 1000);
 				   }
 				});
    		});
    		
    	})
    	
    	
    	function NaNValue(a){
    		if(a==''||isNaN(a)||typeof(a) == "undefined"){
    			return 0;
    		}else{
    			return parseFloat(a);
    		}
    	}
    </script>
</head>
<body>
<form action="${ctx }/order_ext/batchedit_order" id="batch_form">
  <table width="560px" style="margin:10px auto;background:none;" border="0" cellspacing=0 cellpadding=0 >
  	<tr>
  		<td>重量</td>
  		<td><input name="ids" value="${ids }" type="hidden"/><input type="text" name="item_weight" class="myinput"/></td>
  		<td>代收款</td>
  		<td><input type="text" name="good_price" class="myinput" value=""/></td>
  	</tr>
  	<tr>
  		<td>保价费</td>
  		<td><input type="text" name="good_valuation" class="myinput" value=""/></td>
  		<td>手续费</td>
  		<td><input type="text" name="vpay" readonly="readonly" value="" class="readonly myinput"/></td>
  	</tr>
  	<tr>
  		<td>快递运费</td>
  		<td><input type="text" name="freight" class="myinput" value=""/></td>
  		<td>费用合计</td>
  		<td><input type="text" name="pay_acount" readonly="readonly" value="" class="readonly myinput"/></td>
  	</tr>
  	<tr>
  		<td>付款人</td>
  		<td><input type="text" name="freight_type" class="myinput" style="width:80px;"/><span style="color:red;">1.寄付 2.到付</span></td>
  		<td>付款方式</td>
  		<td><input type="text" name="pay_type" class="myinput"  style="width:80px;"/><span style="color:red;">1.现金 2.月结</span></td>
  	</tr>
  	<tr>
  		<td>月结号</td>
  		<td><input type="text" name="month_settle_no" class=" myinput" id="monthSettleNo"/></td>
  		<td></td>
  		<td><input type="text"  name="" readonly="readonly"  class="readonly myinput" id="month_name"/></td>
  	</tr>
  	<tr>
  		<td>取件员</td>
  		<td><input type="text" name="take_courier_no" class="myinput" id="take_courier_no"/></td>
  		<td></td>
  		<td><input type="text" name="" readonly="readonly"  class="readonly myinput" id="take_courier_name"/></td>
  	</tr>
  	<tr>
  		<td>派件员</td>
  		<td><input type="text" name="send_courier_no" class="myinput" id="send_courier_no"/></td>
  		<td></td>
  		<td><input type="text" name="" readonly="readonly"  class="readonly myinput" id="send_courier_name"/></td>
  	</tr>
  	<tr>
  		<td>寄件网点</td>
  		<td><input type="text" name="sub_station_no" readonly="readonly"  class="readonly myinput"/></td>
  		<td>签收网点</td>
  		<td><input type="text" name="send_substation_no" readonly="readonly"  class="readonly myinput"/></td>
  	</tr>
  	<tr>
  		<td>寄件时间</td>
  		<td><input type="text" name="take_order_time" class="myinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
  		<td>签收时间</td>
  		<td><input type="text" name="send_order_time" class="myinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
  	</tr>
  	<tr>
  		<td colspan="4" style="text-align:center">
  			<input  type="button" id="submit" value="提交" style="width:300px;height:30px;margin-top:30px;background:#0866c6;color:#ffffff;border:0;"/>
  		</td>
  	</tr>
  </table>
</form>
</body>
</html>