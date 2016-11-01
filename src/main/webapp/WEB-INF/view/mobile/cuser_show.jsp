<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
.lleft{width: 140px;text-align: right;float: left;}
</style>
<script type="text/javascript">


</script>
</head>
<body>
	<div class="content">
		 	<div class="item">
	 			<ul style="padding-top:30px;padding-left: 25px;">
		 			<li style="width:450px"><span  class="lleft">代收客户用户号：</span>${cuserMap.cod_no }</li>
		 			<li style="width:450px"><span  class="lleft">公司名称：</span>${cuserMap.month_name }
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">公司简称：</span>${cuserMap.month_sname }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">费率类型：</span><u:dict name="CSTYPE" key="${cuserMap.cstype}" />‰
		 			</li>
		 			<li style="width:450px"><span  class="lleft">联系人：</span>${cuserMap.contact_name }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">联系电话：</span>${cuserMap.contact_phone }
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">客户地址：</span>${cuserMap.contact_addr }
		 			</li>
		 			
		 				<li style="width:450px"><span  class="lleft">财务电话：</span>${cuserMap.settle_phone }
		 			</li>
		 			
		 			  <li style="width:450px"><span  class="lleft">所属快递员：</span><u:dict name="C" key="${cuserMap.courier_no}" />
					</li>
					
					  <li style="width:450px"><span  class="lleft">所属网点：</span><u:dict name="S" key="${cuserMap.substation_no}" />
					</li>
					
					<li style="width:450px"><span  class="lleft">市场员：</span>${cuserMap.mark_name }
		 			</li>
		 			
		 			
		 			<li style="width:450px"><span  class="lleft">结算方式：</span>${cuserMap.settle_type }
		 			</li>
		 			 <li style="width:450px"><span  class="lleft">结算银行：</span>${cuserMap.settle_bank }
		 			</li>  
		 			<li style="width:450px"><span class="lleft">结算户名：</span>${cuserMap.settle_card_no }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">银行联行号：</span>${cuserMap.bank_cpns }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">结算日期：</span>${cuserMap.settle_date }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">营业执照/身份证号：</span>${cuserMap.biz_lic }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">电子邮箱：</span>${cuserMap.email }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">是否开票：</span>${cuserMap.liced eq 'Y'?'是':'否'}</li>
		 			 
                    <li style="width:450px"><span  class="lleft">备注：</span>${cuserMap.note }
		 			</li>
	 			</ul>
		 	</div>
	</div>
</body>
</html>