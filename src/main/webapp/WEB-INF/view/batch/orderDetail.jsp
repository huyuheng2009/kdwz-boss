<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${constants['backend_title']}</title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />		
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/checkboxArray.js"></script>		
    </head>
    <body>
<%-- 		<jsp:include page="/WEB-INF/view/include/headPage.jsp" /> --%>
		<div class="gdt">
			<div class="order_history">
				<div class="his_left">
					<ul>
						<li><a href="javascript:void(0);" class="his_l_01">全部订单</a></li>
						<li><a href="<%=request.getContextPath()%>/front/order/query?pageSize=1&lgcNo=${params.lgcNo}&orderType=1&uid=${uid}" class="his_l_02">寄件订单</a></li>
					</ul>
				</div>
				<div class="his_right">
					<div class="his_info_tit">订单详情
					  <a href="<%=request.getContextPath()%>/front/order/query?${paramstr}">返回</a>
					</div>
					<div class="his_info_cont">
						<div class="info_li">            	
							<div class="info_cont">			
								<div class="info_list">订单号：${orderInfo.orderNo}</div>
								<div class="info_list">运单号：${orderInfo.lgcOrderNo}</div>
<!--								<div class="info_list">二次投递费：${orderInfo.dpay}元</div>                    -->
							</div>
						</div>
						<div class="info_li">            	
							<div class="info_cont">
<!--								<div class="info_list">物品名称：${orderInfo.itemName}</div>-->
								<div class="info_list">物品类型：${orderInfo.itemStatus}</div>
								<div class="info_list">物品重量：${orderInfo.itemWeight}kg</div>
								<div class="info_list">快递公司：${lgcName}</div>
<!--								<div class="info_list">取件快递员：${orderInfo.takeCourierNo}</div>
								<div class="info_list">派件快递员：${orderInfo.sendCourierNo}</div>-->
								<div class="info_list">订单状态：<zh:option type="text" map="${ORDERSTATUS}" value="${orderInfo.status}"></zh:option></div>
								<div class="info_list">付款人：<zh:option type="text" map="${PAYP}" value="${orderInfo.freightType}"></zh:option></div>
								<div class="info_list">邮　　费：${orderInfo.freight}元</div>
								<div class="info_list">下单时间：${orderInfo.createTime}</div>
								<div class="info_list">预约时间：${orderInfo.takeTimeBegin}</div>
<!--								<div class="info_list">取件时间：${orderInfo.takeOrderTime}</div>                    -->
							</div>
						</div>
						<div class="info_li">
							<div class="info_tit">寄件人信息</div>
							<div class="info_cont">
								<div class="info_list">姓　　名：${orderInfo.sendName}</div>
								<div class="info_list">手机号码：${orderInfo.sendPhone}</div>
								<div class="info_list width01">寄件地址：${orderInfo.sendArea}${orderInfo.sendAddr}</div>                    
							</div>
						</div>
						<div class="info_li">
							<div class="info_tit">收件人信息</div>
							<div class="info_cont">
								<div class="info_list">姓　　名：${orderInfo.revName}</div>
								<div class="info_list">手机号码：${orderInfo.revPhone}</div>
								<div class="info_list　width01">寄件地址：${orderInfo.revArea}${orderInfo.revAddr}</div>                    
							</div>
						</div>            
						<div class="info_li">
							<div class="info_tit">其他消息</div>
							<div class="info_cont">
								<div class="info_list">月结卡号：${user.userName}</div>
								<div class="info_list">月结金额：${orderInfo.mpay}元</div>
								<div class="info_list">保价金额：${orderInfo.goodValuation}元</div>
								<div class="info_list">保价手续费(5‰)：${orderInfo.vpay}元</div>
								<div class="info_list">是否代收货款：${orderInfo.cod == 1 ? '是':'否'}</div>
								<div class="info_list">代收货款金额：${orderInfo.cpay}元</div>
								<!--								<div class="info_list">姓　　名：李轩豪</div>
																<div class="info_list">银行卡号：62222225****4563</div>
																<div class="info_list">开户行：福田分行</div>                    -->
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
<%-- 		<jsp:include page="/WEB-INF/view/include/footerPage.jsp" /> --%>
    </body>
</html>
