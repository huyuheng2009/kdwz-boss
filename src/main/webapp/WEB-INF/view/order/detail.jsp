<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>

<style>
.item_content li {width: 190px;}
</style>
<script type="text/javascript">
$(function(){
	 $('#button').click(function(){
		 var api = frameElement.api, W = api.opener;
		 $.ajax({
			 type: "get",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "psbind",//要访问的后台地址
	            data: {'sn':$('#sn').val(),'merchantNo':$('#merchantNo').val(),'id':$('#id').val()},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	alert(msg);
					if(msg.indexOf('成功')!=-1){
						api.reload();
						api.close();
					}
	            }
		 });
		 });
	
}); 
 
</script>
</head>
<body>
	 <div class="content">
	 	<div class="item_title" style="padding-top:8px">订单编号：${orderInfo.order_no }
	 	<span style="margin-left: 240px;">外部单号：${orderInfo.cm_order_no}</span>
	 	</div>
	 	<div class="item_content">
	 		<ul>
	 		   <li style="width:200px;">运单号：${orderInfo.lgc_order_no}</li> 
	 		   <li></li>
	 		    <li style="width:300px;">转单号：${orderInfo.for_no}</li> 
	 			<li>物品名称：${orderInfo.item_name}</li>
	 			<%-- <li>物品类型：${orderInfo.item_Status}</li> --%>
	 			<li>&#12288;&#12288;重量：${orderInfo.item_weight}</li>
	 			<li style="margin-right: 0px;">&#12288;&#12288;件数：${orderInfo.item_count}</li>
	 			
	 			
	 		    <li>运单状态：<u:dict name="ORDER_STATUS" key="${orderInfo.status}" /></li>
	 		   <li>分配状态：
	 		         <c:if test="${orderInfo.asign_status ne 'S' and orderInfo.asign_status ne 'C' and orderInfo.status eq '1' }">未分配</c:if>
                    <c:if test="${orderInfo.asign_status eq 'S'}">已分配</c:if>
                    <c:if test="${orderInfo.asign_status eq 'C' or orderInfo.status  eq '2' or orderInfo.status eq '3' or orderInfo.status eq '7' or orderInfo.status eq '8'}">已分配</c:if>
                </li>
	 		     <li>&#12288;取件员：${orderInfo.real_name}</li>
	 		     <li style="margin-right: 0px;">&#12288;派件员：<u:dict name="C" key="${orderInfo.send_courier_no}" /></li>
	 		    <li>物品类型：${orderInfo.item_Status}</li>
	 		    <li>时效类型：${orderInfo.time_type}</li>
	 		    <li>入仓重量：${orderInfo.center_warehouse_weight}</li>
	 		</ul>
	 	</div>
	 	
	   <div class="clear"></div>
	   <div class="item_title"></div>
	 	<div class="item_content">
	 		<ul>
	 			<li>付款人：<c:if test="${orderInfo.freight_type eq 2 }">收方付</c:if><c:if test="${orderInfo.freight_type ne 2 }">寄方付</c:if></li> 
	 			<li style="width:450px;">付款方式：<u:dict name="PAY_TYPE" key="${orderInfo.pay_type}" /></li> 
	 			<li>快递运费：${orderInfo.freight}</li> 
	 			<li>附加费：${orderInfo.vpay}</li> 
	 			<li>&#12288;保价费：${orderInfo.good_valuation}</li> 
	 			<li>费用合计：${orderInfo.freight+orderInfo.vpay}</li> 
	 			<li>代收款：${orderInfo.good_price}</li> 
	 			<%-- <li>返款方式：<c:if test="${orderInfo.return_type eq 2 }">转账</c:if><c:if test="${orderInfo.return_type ne 2 }">现金</c:if></li>  --%>
	 		</ul>
	 	</div>
	 	
	 	 	<div class="clear"></div>
	 	<div class="item_title"></div>
	 	<div class="item_content">
	 		<ul>
	 			<li>月结号：${orderInfo.month_settle_no}</li> 
	 			<li style="width:450px;">公司简称：${monthMap.month_sname}</li> 
	 			<li style="width:670px;">会员号：${orderInfo.dis_user_no}</li> 
	 			<li>贷款号：${orderInfo.cod_name}</li> 
	 			<li style="width:450px;">公司简称：${codMap.cod_sname}</li> 
	 		</ul>
	 	</div>
	 	
	 	<div class="clear"></div>
	 	<div class="item_title"></div>
	 	<div class="item_content">
	 		<ul>
	 			<li>寄件人：${orderInfo.send_name}</li> 
	 			<li>联系电话：${orderInfo.send_phone}</li> 
	 			<li>寄件时间：<fmt:formatDate value="${orderInfo.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></li>
	 			<li>寄件录入人：${orderInfo.inputer}</li> 
	 			<li style="width:670px;">地址：${orderInfo.send_area}&#12288;${orderInfo.send_addr}</li> 
	 			
	 			
	 			<li>收件人：${orderInfo.rev_name}</li> 
	 			<li>联系电话：${orderInfo.rev_phone}</li> 
	 			<li>签收时间：<fmt:formatDate value="${orderInfo.send_order_time}" pattern="yyyy-MM-dd HH:mm:ss" /></li>
	 			<li>签收录入人：${orderInfo.sign_inputer}</li> 
	 			<li style="width:670px;">地址：${orderInfo.rev_area}&#12288;${orderInfo.rev_addr}</li> 
	 			
	 		</ul>
	 	</div>
	 	<div class="clear"></div>
        	<div class="item_title"></div>
	 	<div class="item_content">
	 		<ul>
	 		     <li style="width:670px;">回单号：${orderInfo.rece_no}</li>
	 		      <li style="width:670px;">子单号：${orderInfo.zidan_order}</li>
	 		     <li style="width:670px;">备注： <u:dict name="ORDER_NOTE" key="${orderInfo.id}" /></li>
	 		</ul>
	 	</div>
	 </div>
</body>

</html>