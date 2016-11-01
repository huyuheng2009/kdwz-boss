<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${constants['backend_title']}</title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/statics/js/jquery.engine.aster/css/validationEngine.jquery.css" type="text/css">
		<%--<jsp:include page="/WEB-INF/view/include/select_city.jsp" />--%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/jquery.form.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/jquery.engine.aster/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/jquery.engine.aster/js/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/city.js"></script>
		<script>
			var dg = frameElement.api, W = dg.opener;
			dg.lock();
			$(function() {
				$("#down_addrSend").hide();
				$("#down_addrRev").hide();
				jQuery("#myform").validationEngine();
//				SelectCity($("input[name='revArea']"));
			});
			var cit = new selectProvince();
			var webPath = "<%=request.getContextPath()%>";
			function add() {
					var options = {
						url: '<%=request.getContextPath()%>/batch/editOrderAddr',
						dataType: 'json',
						type: 'post',
						success: function(data) {
							if (data.errorCode == "0") {
								//W.location.reload();
								dg.reload();
								dg.close();
							} else {
								jQuery("#revName").validationEngine('showPrompt', "* " + data.message, null, null, true);
							}
						},
						error: function(data) {
							alert(data.responseText);
						}
					};
					jQuery("#myform").ajaxSubmit(options);
				
			}

			function cacel() {
				dg.close();
			}

			function money() {
				var t = $("#goodValuation").val();
				if ($.trim(t) != null || $.trim(t) != '') {
					var b = t * 0.005 + "";
					var x = b.substr(b.indexOf(".") + 1, b.length);
					if (x.length > 2) {
						b = b.substr(0, b.indexOf(".") + 3);
					}
					$("#bmoney").html(b);
				}
			}

			function submitone() {
				var money = $.trim($("#goodValuation").val());
				if (money != null && money != '') {
					if (money <= 300) {
						jQuery("#goodValuation").validationEngine('showPrompt', "* 保价金额必须大于300元", null, null, true);
						return false;
					}
				}
				$("#myform").submit();
			}
		</script>
    </head>
    <body>
		<form id="myform">
			<div class="out_new" style="padding: 0 0;">
				<div class="out_new_li" style="margin: 0;">
					<div class="outnew_tit" style="margin: 0;"> <img src="<%=request.getContextPath()%>/statics/front/images/shou_icon.png" width="40" height="36"> <strong>修改信息</strong> </div>
					<div class="outnew_cont"  style="margin: 0;">
					<div class="input_li">
							<div class="input_name"><b>*</b>寄件人姓名</div>
							<div class="input_wri"><input type="text" id="sendName" name="sendName" placeholder="请输入寄件人姓名" class="validate[required,maxSize[15]]" value="${orderAddr.sendName}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>寄件人电话</div>
							<div class="input_wri"><input type="text" name="sendPhone" placeholder="请输入寄件人联系电话" class="validate[required,funcCall[mobilePhone]]" value="${orderAddr.sendPhone}" data-errormessage-value-missing="* 请输入联系电话" data-errormessage-custom-error="* 请输入正确的联系电话"></div>        
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>寄件人地址</div>
							<div class="input_wri"><input type="text" name="sendAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${orderAddr.sendAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b>*</b>收件人姓名</div>
							<div class="input_wri"><input type="text" id="revName" name="revName" placeholder="请输入收件人姓名" class="validate[required,maxSize[15]]" value="${orderAddr.revName}" data-errormessage-value-missing="* 请输入收件人姓名"></div>					
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>收件人电话</div>
							<div class="input_wri"><input type="text" name="revPhone" placeholder="请输入联系电话" class="validate[required,funcCall[mobilePhone]]" value="${orderAddr.revPhone}" data-errormessage-value-missing="* 请输入联系电话" data-errormessage-custom-error="* 请输入正确的联系电话"></div>        
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>收件人地址</div>
							<div class="input_wri"><input type="text" name="revAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${orderAddr.revAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b>*</b>寄件时间</div>       
							<div class="input_wri"><input type="text" name="sendOrderTime" placeholder="请填写寄件时间"  value="${orderAddr.sendOrderTime}" data-errormessage-value-missing="* 请填写寄件时间"></div>        
						<div class="input_span02">*例如:1999-09-09</div>		
						</div>  
						<div class="input_li">
							<div class="input_name"><b>*</b>收件快递员</div>
							<div class="input_wri"><input type="text" name="takeCourierNo" placeholder="请填写收件人姓名（精确）"  value="${orderAddr.takeCourierNo}" data-errormessage-value-missing="* 请填写收件人姓名（精确）"></div>        
						</div>  
						<div class="input_li">
							<div class="input_name"><b>*</b>派件快递员</div>
							<div class="input_wri"><input type="text" name="sendCourierNo" placeholder="请填写派件人姓名（精确）"  value="${orderAddr.sendCourierNo}" data-errormessage-value-missing="* 请填写派件人姓名（精确）"></div>        
						</div>  
							<div class="input_li">
							<div class="input_name"><b>*</b>月结账户号</div>
							<div class="input_wri"><input type="text" id="monthNo" name="monthNo" placeholder="请输入月结账户号" class="validate[required,maxSize[15]]" value="${orderAddr.monthNo}" data-errormessage-value-missing="* 请输入月结账户号"></div>					
						</div>
						<input type="hidden" name="id"  value="${orderAddr.id}">
					</div>
				</div>
						<div class="input_li">
							<div class="input_name">物品类别</div>
							<div class="input_wri input_wri06">
								<select name="itemStatus" >
                              <option value="${orderAddr.itemStatus}">${orderAddr.itemStatus}</option>
							<c:forEach items="${itemType}" var="item" varStatus="status">				
								<option value="${item.itemText }" >${item.itemText }</option>
							</c:forEach>	
									</select>
								</div>        
							</div>
							<div class="input_li">
								<div class="input_name">物品重量</div>
								<div class="input_wri input_wri06">
									<input type="text" id="itemWeight" name="itemWeight" value="${orderAddr.itemWeight}" class="validate[custom[decimal]]" data-errormessage-custom-error="* 请输入正确的数据">
							</div>
							<div class="input_span02">（单位：kg）</div>		
						</div>
						<div class="input_li">
							<div class="input_name">付款人</div>
							<div class="input_wri input_wri06">
								<select  name="freightType">
				 			<option value="寄方付">寄方付</option>
					           	<option value="收方付">收方付</option>
									</select>
								</div>        
							</div>
							<div class="input_li">
								<div class="input_name">付款方式</div>
								<div class="input_wri input_wri06">
									<select  name="payType">
 								<option value="月结">月结</option>
									</select>
								</div>        
							</div>

							<div class="input_li">
								<div class="input_name">保价金额</div>
								<div class="input_wri input_wri06">
									<input type="text" id="goodValuation" name="goodValuation" class="validate[custom[isMoney]]" value="${orderAddr.goodValuation}" onblur="money();">
							</div>
							<div class="input_name">代收货款</div>
								<div class="input_wri input_wri06">
									<input type="text" id="goodPrice" name="goodPrice" class="validate[custom[isMoney]]" value="${orderAddr.goodPrice}" onblur="money();">		
						</div>				
							<div class="input_li">
								<div class="input_name">运费</div>
								<div class="input_wri input_wri06"><input type="text" id="freight" name="freight" value="${orderAddr.freight}" class="validate[custom[isMoney]]" data-errormessage-custom-error="* 请输入运费"></div>															
							</div>
							<div class="input_li">
								<div class="input_name">运单号</div>
								<div class="input_wri input_wri06"><input type="text" id="lgcOrderNo" name="lgcOrderNo" value="${orderAddr.lgcOrderNo}" minlen="0" maxlen="20" class="validate[funcCall[numberzm]]" data-errormessage-custom-error="* 请输入运单号"></div>															
							</div>					
						<div class="input_li">
							<div class="input_name">备注</div>
							<div class="input_wri">
								<input type="text" name="orderNote" value="${orderAddr.orderNote}">
				
		
				<div class="out_button">
					<a href="javascript:void(0);" onclick="add()" class="button">保存</a>
					<a href="javascript:void(0);" onclick="cacel();" class=" button_2">取消</a>
				</div>
			</div>
		</form>
	</body>
</html>
