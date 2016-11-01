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
		 			<li style="width:450px"><span  class="lleft">月结号：</span>${muserMap.month_settle_no }</li>
		 			<li style="width:450px"><span  class="lleft">公司名称：</span>${muserMap.month_name }
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">公司简称：</span>${muserMap.month_sname }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">优惠类型：</span><u:dict name="MSTYPE" key="${muserMap.mstype}" />
		 			</li>
		 			<li style="width:450px"><span  class="lleft">联系人：</span>${muserMap.contact_name }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">联系电话：</span>${muserMap.contact_phone }
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">客户地址：</span>${muserMap.contact_addr }
		 			</li>
		 			
		 				<li style="width:450px"><span  class="lleft">财务电话：</span>${muserMap.settle_phone }
		 			</li>
		 			
		 			  <li style="width:450px"><span  class="lleft">所属快递员：</span><u:dict name="C" key="${muserMap.courier_no}" />
					</li>
					
					  <li style="width:450px"><span  class="lleft">所属网点：</span><u:dict name="S" key="${muserMap.substation_no}" />
					</li>
					
					<li style="width:450px"><span  class="lleft">市场员：</span>${muserMap.mark_name }
		 			</li>
		 			
		 			
		 			<li style="width:450px"><span  class="lleft">结算方式：</span>${muserMap.settle_type }
		 			</li>
		 			<!--  <li style="width:450px"><span  class="lleft">结算银行：</span> -->
		 				<input  maxlength="20" type="hidden" name="settleBank" value="${muserMap.settle_bank }" style="width: 300px"></input>
		 			<!-- </li>  -->
		 			<li style="width:450px"><span class="lleft">结算户名：</span>${muserMap.settle_card_no }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">结算日期：</span>${muserMap.settle_date }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">营业执照/身份证号：</span>${muserMap.biz_lic }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">合同开始日期：</span>${muserMap.ht_date_begin }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">合同结束日期：</span>${muserMap.ht_date_end }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">电子邮箱：</span>${muserMap.email }
		 			</li>
		 			<li style="width:450px"><span  class="lleft">是否开票：</span>${muserMap.liced eq 'Y'?'是':'否'}</li>
		 			 
                    <li style="width:450px"><span  class="lleft">备注：</span>${muserMap.note }
		 			</li>
	 			</ul>
		 	</div>
	</div>
</body>
</html>