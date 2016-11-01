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
<script type="text/javascript" src="${ ctx}/scripts/JsBarcode.all.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ ctx}/themes/default/printer1.css" />
<script type="text/javascript">

function spitWrite(lgcNo){
	var len = lgcNo/3 + 1 ;
	
	for(var i=0;i<len;i++){
		
	}
 }

$(function(){
	/*$(".barcode").each(function(){
		var ono = $(this).attr('no') ;
		var w = 2 ;
		if(ono.length>10||ono.length==9){
			w=1 ;
		}
		$(this).barcode(ono, "code128",{barWidth:w, barHeight:32,showHRI:false});
   	}); */

    options = {
        format:"CODE128",
        displayValue:false,
        fontSize:18,
        height:35,
        width:3
    };
   	$(".barcode").each(function(){
   		$(this).JsBarcode($(this).attr("v"),options);
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
<style type="text/css">
 .barcode{
            width:100%;
            max-width:340px;
            max-height:80px;
        }
</style>

</head>
<body>
	<div class="content">
		<div style="width: 86%;text-align: center;position: fixed;padding: 10px 0 ;float: left;background-color: #f5f5f5;;z-index:100;margin: 0;"><input class="sear_butt" type="button" id="submit" value="打印"/></div>
<div style="padding: 20px 0;"></div>

 <c:forEach items="${list}" var="item" varStatus="status">
	<div class="po" style="padding-top:25px;">
		<div style="width:395px;margin:0 0 0 -7px;background: #fff;font-size: 10px;border:1px solid #666666;height:778px;background:#fff;">
				<div style="width:395px;border-bottom:1px solid #666666;height:35px;line-height:35px;">
						<div style="width:200px;font-size:22px;float:left;">
						&nbsp;&nbsp;${lgc1.name}
						</div>
						<div style="width:165px;font-size:22px;text-align:right;float:left;">
						 ${lgc1.contact} 
						</div>
						<div style="clear:both;"></div>
					
				</div>
				<div>
					 <div style="font-size:16px;border-bottom:1px solid #666666;height:45px;line-height:45px;">
				   	    <div style="width:122px;float:left;">&nbsp;&nbsp;<fmt:formatDate value="${item.take_order_time }" pattern="yyyy-MM-dd"/></div>
				   	    <div style="width:122px;float:left;"><c:out value="${item.freight_type==1?'寄付':'到付'}"/><u:dict name="PAY_TYPE" key="${item.pay_type}" /></div>
				   	    <div style="width:122px;float:left;"><c:out value="${item.good_price>0?'代收货款':''}"/></div>
				   	    <div style="clear:both;"></div>
				   	 </div>
					
				   <div style="width:373px;float:left;text-align:center;">
				   	 <!-- 收件人信息 -->
				   	 <div style="width:373px;border-bottom:1px solid #666666;">
				   	    <div style="width:21px;float:left;height:90px;border-right:1px solid #666;line-height:45px;font-weight:300;">
				   	    	收件
				   	    </div>
				   	    <div style="width:351px;float:left;height:90px;font-size:20px;">
				   	    	<div style="height:21px;">
					   	    	<div style="width:170px;float:left;text-align:left;">
					   	    	<b>${item.rev_name }</b>
					   	    	</div>
					   	    	<div style="width:170px;float:left;text-align:right;">
					   	    	<b>${item.rev_phone }</b>
					   	    	</div>
					   	    	<div style="clear:both;"></div>
				   	    	</div>
				   	    	<div style="height:58px;text-align:left;text-align:left;">
								${item.rev_area } &nbsp;${item.rev_addr }
							</div>
				   	    </div>
				   	    <div style="clear:both;"></div>
				   	 </div>
				   	 <!-- 寄件人信息 -->
				   	  <div style="width:373px;border-bottom:1px solid #666666;">
				   	    <div style="width:21px;float:left;height:70px;border-right:1px solid #666;line-height:35px;font-weight:300;">
				   	    	寄件
				   	    </div>
				   	    <div style="width:351px;float:left;height:70px;font-size:15px;">
				   	    	<div style="height:18px;">
					   	    	<div style="width:170px;float:left;font-weight:300;text-align:left;">
					   	    	${item.send_name }
					   	    	</div>
					   	    	<div style="width:170px;float:left;font-weight:300;text-align:right;">
					   	    	${item.send_phone }
					   	    	</div>
					   	    	<div style="clear:both;"></div>
				   	    	</div>
				   	    	<div style="height:50px;font-weight:300;text-align:left;">
								${item.send_area } &nbsp;${item.send_addr }
							</div>
				   	    </div>
				   	    <div style="clear:both;"></div>
				   	 </div>
				   	 
				   	  <div style="height:85px;border-bottom:1px solid #666666;">
				   	 	<img class="barcode" v="${item.lgc_order_no }"  width="340" style="max-height:80px;"/>
				   	 	<div style="height:20px;letter-spacing:0.6em;line-height:20px;font-size:14px;position:relative;top:-10px;">${item.lgc_order_no }</div>
				   	 </div>
				   	 
				   	 <div style="font-size:16px;border-bottom:1px solid #666666;height:45px;line-height:45px;">
				   	    <div style="width:122px;float:left;"><fmt:formatDate value="${item.take_order_time }" pattern="yyyy-MM-dd"/></div>
				   	    <div style="width:122px;float:left;"><c:out value="${item.freight_type==1?'寄付':'到付'}"/><u:dict name="PAY_TYPE" key="${item.pay_type}" /></div>
				   	    <div style="width:122px;float:left;"><c:out value="${item.good_price>0?'代收货款':''}"/></div>
				   	    <div style="clear:both;"></div>
				   	 </div>
				   	 
				   	 <div>
				   	 	<div style="width:373px;border-bottom:1px solid #666666;">
				   	    <div style="width:21px;float:left;height:90px;border-right:1px solid #666;line-height:45px;font-weight:300;">
				   	    	收件
				   	    </div>
				   	    <div style="width:351px;float:left;height:90px;font-size:20px;">
				   	    	<div style="height:21px;">
					   	    	<div style="width:170px;float:left;text-align:left;">
					   	    	<b>${item.rev_name }</b>
					   	    	</div>
					   	    	<div style="width:170px;float:left;text-align:right;">
					   	    	<b>${item.rev_phone }</b>
					   	    	</div>
					   	    	<div style="clear:both;"></div>
				   	    	</div>
				   	    	<div style="height:58px;text-align:left;">
								<div>
								${item.rev_area } &nbsp;${item.rev_addr }
								</div>
							</div>
				   	    </div>
				   	    <div style="clear:both;"></div>
				   	 </div>
				   	 <!-- 寄件人信息 -->
				   	  <div style="width:373px;border-bottom:1px solid #666666;">
				   	    <div style="width:21px;float:left;height:70px;border-right:1px solid #666;line-height:35px;font-weight:300;">
				   	    	寄件
				   	    </div>
				   	    <div style="width:351px;float:left;height:70px;font-size:15px;">
				   	    	<div style="height:18px;">
					   	    	<div style="width:170px;float:left;font-weight:300;text-align:left;">
					   	    	${item.send_name }
					   	    	</div>
					   	    	<div style="width:170px;float:left;font-weight:300;text-align:right;">
					   	    	${item.send_phone }
					   	    	</div>
					   	    	<div style="clear:both;"></div>
				   	    	</div>
				   	    	<div style="height:50px;font-weight:300;text-align:left;">
								${item.send_area } &nbsp;${item.send_addr }
							</div>
				   	    </div>
				   	    <div style="clear:both;"></div>
				   	 </div>
				   	 	
					   	 <div style="height:30px;line-height:30px;border-bottom:1px solid #666666;">
					   	    <div style="width:122px;float:left;border-right:1px solid #666666;height:30px;">${item.time_type} </div>
					   	    <div style="width:122px;float:left;border-right:1px solid #666666;height:30px;">${item.item_Status }</div>
					   	    <div style="width:122px;float:left;height:30px;">${item.item_count }件</div>
					   	    <div style="clear:both;"></div>
					   	 </div>
					   	 <div style="height:30px;line-height:30px;border-bottom:1px solid #666666;">
					   	    <div style="width:122px;float:left;border-right:1px solid #666666;height:30px;">运费&nbsp;${item.freight} </div>
					   	    <div style="width:122px;float:left;border-right:1px solid #666666;height:30px;">保价费&nbsp;${item.good_valuation }</div>
					   	    <div style="width:122px;float:left;height:30px;">代收款&nbsp;${item.good_price }</div>
					   	    <div style="clear:both;"></div>
					   	 </div>
					   	 <div style="height:85px;">
					   	 		<img class="barcode" v="${item.lgc_order_no }" width="340" style="max-height:80px;"/>
					   	 		<div style="height:20px;letter-spacing:0.6em;line-height:20px;font-size:14px;position:relative;top:-10px;">${item.lgc_order_no }</div>
					   	 </div>
				   	 </div>
				   	 
				   </div>
				   
				   
				   <div style="width:21px;float:left;border-left:1px solid #666666;height:604px;text-align:center;">
				   		<div style="width:21px;height:247px;border-bottom:1px solid #666666;overflow:hidden;">
				   			<div style="margin-top:100px;font-weight:300; ">收件客户联</div>
				   		</div>
				   	    <div style="width:21px;height:356px;overflow:hidden; text-align:center;">
				   	    	<div style="margin-top:100px;font-weight:300;">公司存根联</div>
				   	    </div>
				   	    <div style="clear:both;"></div>
				   </div>
				   <div style="clear:both;"></div>
					<div style="border-top:1px solid #666666;">
					   	 	<div style="width:200px;float:left;border-right:1px solid #666666;height:92px;text-align:left;">
						   	    	备注:${item.order_note }
							</div>
					   	    <div style="width:170px;float:left;height:92px;color:#efefef;text-align:center;line-height:92px;font-size:30px;">
					   	    请签收
					   	    </div>
					   	    <div style="clear:both;"></div>
					   	 </div>
					</div>
		</div>
	</div>

</c:forEach>


	</div>
</body>
</html>