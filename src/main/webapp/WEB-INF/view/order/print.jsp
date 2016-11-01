<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link type="text/css" rel="stylesheet" href="${ ctx}/themes/default/jquery-ui-1.10.4.custom.css" />
<script type="text/javascript" src="${ ctx}/scripts/jquery-1.9.1.min.js"></script> 
<script type="text/javascript" src="${ ctx}/scripts/jquery-barcode.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery.PrintArea.js"></script>

<link rel="stylesheet" type="text/css" href="${ ctx}/themes/default/printer1.css" />
<script type="text/javascript">

function spitWrite(lgcNo){
	var len = lgcNo/3 + 1 ;
	
	for(var i=0;i<len;i++){
		
	}
 }

$(function(){
	$(".barcode").each(function(){
		$(this).barcode($(this).attr('no'), "code128",{barWidth:2, barHeight:34,showHRI:false});
   	}); 
	
	 $('#submit').click(function(){
		 var extraCss = '' ;
		 var keepAttr = [];
		 keepAttr.push('id') ;
		 keepAttr.push('class') ;
		 keepAttr.push('style') ;
		  var headElements =  '<style></style>';
		 var options = {popHt : 600 ,popWd  : 860, mode : "popup", popClose : false, extraCss : extraCss, retainAttr : keepAttr, extraHead : headElements };
	      $(".po").printArea( options ); 
	 });
	
}); 




 
</script>
</head>
<body>
	<div class="content">
		<div style="width: 100%;text-align: center;position: fixed;padding: 10px 0 ;float: left;background-color: #f5f5f5;;z-index:100;margin: 0;"><input class="sear_butt" type="button" id="submit" value="打印"/></div>
<div style="padding: 20px 0;"></div>

 <c:forEach items="${list}" var="item" varStatus="status">
<div class="po">

<div class="print_all">
 <div class="print_all_one"> 
   <div class="print_com print_aa"> 	 
     <div class=" logo_left"> <c:if test="${!empty lgcConfig.res.logo_new}"><img src="${lgcConfig.res.logo_new}" ></img></c:if>
     <c:if test="${empty lgcConfig.res.logo_new}"> <img src="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_logo_new.png"></img></c:if>
     </div>
     
     
   <div class="logo_right">
        	电话：${lgc1.contact}<br />
			公司名称：${lgc1.name}
     </div>     
   </div>
     <div class="print_com print_bb font_big" >
     <div class="barcode "  no="${item.lgc_order_no }"></div>${item.lgc_order_no }</div>
   <div class="print_com print_yy"><div class="ji_a"><strong>寄件方：</strong>${item.send_name}，${item.send_phone }</div></div>
   <div class="print_com print_yy">        				
        	<div class="ji_a"><strong>目的地：</strong>${item.rev_area }</div>
   </div>
   
   <div class="print_com print_dd ">
 		<strong>收件方</strong><div class="ji_b font_big"><span>${item.rev_area }&#12288;${item.rev_addr }</span>${item.rev_name }，${item.rev_phone }</div>
   </div>
   <div class="print_zz">
   		<div class="ji_c">业务类型：<c:out value="${item.good_price>0?'代收货款':'标准类型'}"/>&#12288;&#12288;付款方式：<c:out value="${item.freight_type==1?'寄付':'到付'}"/><u:dict name="PAY_TYPE" key="${item.pay_type}" /></div>
   </div>
   <div class="po_span">收件客户存根</div>
 </div>
 <div class="print_all_two"> 
   <div class="print_com print_bb font_big" >
     <div class="barcode"  no="${item.lgc_order_no }"></div>
         ${item.lgc_order_no }
   </div>
  <div class="print_com print_yy"><div class="ji_a"><strong>寄件方：</strong>${item.send_name}，${item.send_phone }</div></div>
   <div class="print_com print_yy">        				
        	<div class="ji_a"><strong>目的地：</strong>${item.rev_area }</div>
   </div>
   
   <div class="print_com print_dd ">
 		<strong>收件方</strong><div class="ji_b font_big"><span>${item.rev_area }&#12288;${item.rev_addr }</span>${item.rev_name }，${item.rev_phone }</div>
   </div>
   <div class="print_com print_ee">
 		<div class="ji_left">
        	<div class="ji_a"><strong>托寄物品信息</strong><br />${item.item_Status }&#12288;<br />件数：${item.item_count }</div>		
        </div>
        <div class="ji_right">
        	<div class="ji_a"><strong>附加服务</strong><br />保价费：${item.good_valuation }<br />
        	<span class="font_big">代收货款：${item.good_price }</span></div>
        </div>
   </div>
   <div class="print_com print_ff">
 		<div class="ji_left">
        <div class="ji_d">业务类型：<c:out value="${item.good_price>0?'代收货款':'标准类型'}"/><br />
        	付款方式：<c:out value="${item.freight_type==1?'寄付':'到付'}"/><u:dict name="PAY_TYPE" key="${item.pay_type}" /><br />
        	月结账号：${item.month_settle_no }<br />
        	<span style="height: 3.1em;overflow: hidden;display: inline-block;">备&#12288;&#12288;注：<u:dict name="ORDER_NOTE" key="${item.id}" /></span>
        	</div>	
        </div>
        <div class="ji_right">
        	<div class="ji_e font_big_14"><span class="line_hidden"><b>重量：</b>${item.item_weight }kg</span>
        	<span class="line_hidden"><b>附加费：</b>${item.vpay }元</span>
        	<span class="line_hidden"><b>运费：</b>${item.freight }元</span>
        	<span class="line_hidden"><b>总计：</b>${item.freight+item.vpay+item.good_price }元</span></div>
        </div>
   </div>
   <div class="print_gg">请 签 收</div>
   <div class="po_span">公司存根</div>
 </div>
</div>

</div>


</c:forEach>


	</div>
</body>
</html>