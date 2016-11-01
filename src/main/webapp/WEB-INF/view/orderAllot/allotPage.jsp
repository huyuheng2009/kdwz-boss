<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
			<script>
var $jqq = jQuery.noConflict(true);
</script> 
		<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
		<link  rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/jquery.autocomplete.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.6.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	
	
		   
        $(function () {
    var substationList = ${substations};
    var sres = [];
    $.each(substationList, function(i, item) {
		 var inner_no = "" ;
		sres[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
   }); 
     $jqq("#substationNo").autocomplete(sres, {
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
			 $("#substationNo").val(data[0]) ;
			 $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	});	  
});

	function add(){
		 var api = frameElement.api, W = api.opener;
		var	reg = /^\d+(.[0-9]{1,2})?$/i;//两位小数
		 var substationNo = $.trim($('input[name=substationNo]').val());
		 var beginOrder = $.trim($('input[name=beginOrder]').val());
		 var endOrder = $.trim($('input[name=endOrder]').val());
		 var unitCost = $.trim($('input[name=unitCost]').val());
		 var cost = $.trim($('input[name=cost]').val());
		 var number = $.trim($('input[name=number]').val());
		 var orderNote = $.trim($('textarea[name=orderNote]').val());

		 if(substationNo.length <1 || substationNo ==""){
			 alert('请输入网点信息');
			 return false;
		 }
	

		 if(beginOrder.length <1 || beginOrder ==""){
			 alert('请输入起始票段');
			 return false;
		 }
	

		 if(endOrder.length <1 || endOrder ==""){
			 alert('请输入结束票段');
			 return false;
		 }
		 
		 if(unitCost.length>1|| unitCost!=""){	
			 if(!reg.test(unitCost)){
    			 alert("请输入正常的小数,保留两位小数");  		
    			return false;
    		 }			
		}	
		 
	 
		if(beginOrder.length>1&& endOrder.length>1){   
			var count  = parseInt(endOrder)-parseInt(beginOrder)+1;	
			if(count<1){
				alert("请输入正确的票段,结束票段必须大于起始票段");	
    			return;	
			}
		}
		 
		if(unitCost.length>0 && number.length>0	){
			var checkMoney = parseInt(endOrder)-parseInt(beginOrder)+1;	
			var sumCostMoney = parseFloat(checkMoney)*(parseFloat(unitCost)*100)/100;
			var costVlue = parseFloat(cost);
			if(sumCostMoney!=costVlue){
				alert("总金额计算错误,请重新输入单价");	
    			return;	
			}
		}
		
		
		
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "json",//返回文本格式
	            url: "<%=request.getContextPath()%>/orderAllot/allotOrder ",//要访问的后台地址
	            data: {'substationNo':substationNo,
	            	'beginOrder':beginOrder,
	            	'endOrder':endOrder,
	            	'unitCost':unitCost,
	            	'cost':cost,
	            	'number':number,
	            	'orderNote':orderNote,
	            	'status':'Y'
	            },//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定         
	            	if(msg.msgNo==1){
// 	            		alert(msg.msg);           	
	            		$('#context').html(msg.msg);
	            	}else if(msg.msgNo==2){
	            		$('#subNo').html(msg.msg);	      
      		
	            	}else{
	            		alert(msg.msg);
	            		api.reload();
						api.close();
	            	}
	            }
		 });
	}
		
	function xiaoshu(){
	var	reg = /^\d+(.[0-9]{1,2})?$/i;//两位小数
		var unitCost = $('input[name=unitCost]').val() ;
		if(unitCost.length>1|| unitCost!=""){	
			 if(!reg.test(unitCost)){
    			 alert("请输入正常的小数,保留两位小数"); 			 
    		 }			
		}	
	}
	function cacel(){
		 var api = frameElement.api, W = api.opener;
		 api.close();
	}
	function deleteAll(){
		
		$('input[name=substationNo]').val("");
		$('input[name=beginOrder]').val("");
		$('input[name=endOrder]').val("");
		$('input[name=unitCost]').val("");
		 $('input[name=cost]').val("");
		$('input[name=number]').val("");
		 $('textarea[name=orderNote]').val("");
		 $('#subNo').hide();
		 $('#context').hide();
		
		
	}

	
	$(function(){
		$(".cmount").on('input',function(e){  
    		var reg1=  /^\d$/;
    		var beginOrder = $('input[name=beginOrder]').val() ;
    		var endOrder = $('input[name=endOrder]').val() ; 	 	
    		var acount ;
    		if(beginOrder.length<1 && endOrder.length<1){   			
    			acount= 0;			
    		}
    		if(beginOrder.length<1 || endOrder.length<1){   			
    			acount= 0;			
    		}		
    		if(beginOrder.length>1&& endOrder.length>1){   		  			
    			if(beginOrder.length == endOrder.length){      	
        			if(parseInt(beginOrder)>parseInt(endOrder)){
        				acount= 0;		
        				alert("请输入正确的票段,结束票段必须大于起始票段");	
        				return;
        			}else{
        				acount = parseInt(endOrder)-parseInt(beginOrder)+1;
        			}		
    			}
    			if(beginOrder.length < endOrder.length){
    				acount = parseInt(endOrder)-parseInt(beginOrder)+1;	
    				if(acount<1){
    					alert("请输入正确的票段,结束票段必须大于起始票段");	
        				return;	
    				}
    			}
    		}	
             $('input[name=number]').val(acount) ; 
    		}); 
		
		
		$(".cost").on('input',function(e){  
			var costVlue =0;
			
			var unitCost = $.trim($('input[name=unitCost]').val()) ;
    		var number = $.trim($('input[name=number]').val()) ; 	
    		if(unitCost.length>0 && number.length>0){
    			costVlue = parseFloat(unitCost) *100 * parseFloat(number) /100;		
    		}
    	    $('input[name=cost]').val(costVlue) ;
		});	
	});

		</script>
		<style>
		.input_li{width:600px;}
		.out_button{width:600px; text-align:center;      margin: 20px;}
		.out_button .button{ float:inherit; display:inline-block;   margin: 0 20px 0 0;}
		.out_button .button_2{ float:inherit; display:inline-block;}
		</style>
    </head>
    <body>
		<form id="myform">
			
				<div class="out_new_li" style="margin: 0;">
					<div class="outnew_cont"  style="margin: 0;">
						<div class="input_li">
							<div class="input_name"><b>*</b>网点</div>
							<div class="input_wri"><input type="text" id="substationNo"   name ="substationNo" ></div>					
						</div>
						<div class="input_li" style="text-align: center;color:red; " id ="div1"> <span id= "subNo"></span></div>	
						<div class="input_li">
							<div class="input_name"><b>*</b>起始单号</div>
							<div class="input_wri"><input onkeyup="value=value.replace(/[^\d]/g,'') "   
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"   name="beginOrder"    id = "beginOrder" class="cmount"> </div>        
						</div>
						
						<div class="input_li">
							<div class="input_name"><b>*</b>结束单号</div>
							<div class="input_wri"><input  onkeyup="value=value.replace(/[^\d]/g,'') "   
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"    name="endOrder"    id="endOrder" class="cmount cost"></div>        
						</div> 
								<div class="input_li" style="text-align: center;color:red;" id ="div2" ><span id= "context"></span></div>	
						<div class="input_li">
							<div class="input_name"><b></b>单价</div>
							<div class="input_wri"><input onkeyup="value=value.replace(/[^\d.]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))"       name="unitCost" id ="unitCost"  onblur ="xiaoshu();"  class= "cost"  ></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b></b>数量</div>
							<div class="input_wri"><input type="text" name="number"  id ="number"  readonly style="background:#ccc;"></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b></b>总金额</div>
							<div class="input_wri"><input type="text" name="cost"  id = "cost"  readonly  style="background:#ccc;"></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b></b>备注</div>
							<div class="input_wri"><textarea type="text" name="orderNote"  style="width:400px;height:100px	"  ></textarea>        
						</div>  
						</div>
				<div class="input_li" style="color:red; font-size:18px; display:block; text-align: center; margin-bottom: 20px;">
							<span style="color:red; font-size:18px; display:block;">注释:起始票段与结束票段只能输入纯数字。请勿使用0开头！</span>          											
				</div>
				<div class="out_button">
					<a href="javascript:void(0);" onclick="add()" class="button">保存</a>
					<a href="javascript:void(0);" onclick="deleteAll();" class="button">重置</a>
					<a href="javascript:void(0);" onclick="cacel();" class=" button_2">取消</a>	
				</div>
			
		</form>
	</body>
</html>
