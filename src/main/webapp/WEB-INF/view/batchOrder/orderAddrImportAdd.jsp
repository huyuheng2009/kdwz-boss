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
<!--		<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/cityHP.js"></script>-->
        <script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/jquery.form.min.js"></script>
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
				if ($("#myform").validationEngine("validate")) {
					var money = $.trim($("#goodValuation").val());
					if (money != null && money != '') {
						if (money <= 300) {
							jQuery("#goodValuation").validationEngine('showPrompt', "* 保价金额必须大于300元", null, null, true);
							return false;
						}
					}
					var options = {
						url: '<%=request.getContextPath()%>/batchOrder/editOrderImportAddr',
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
                    <div class="outnew_tit" style="margin: 0;"> <img src="<%=request.getContextPath()%>/statics/front/images/shou_icon.png" width="40" height="36"> <strong>寄件信息</strong> </div>
                    <div class="outnew_cont"  style="margin: 0;">
						 <div class="input_li">
                            <div class="input_name"><b>*</b>运单号</div>
                            <div class="input_wri"><input type="text" id="lgcOrderNo" name="lgcOrderNo" placeholder="请输入寄件人姓名" class="validate[required,maxSize[15]]" value="${orderAddr.lgcOrderNo}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
                        </div>
                        <div class="input_li">
                            <div class="input_name"><b>*</b>寄件人姓名</div>
                            <div class="input_wri"><input type="text" id="sendName" name="sendName" placeholder="请输入寄件人姓名" class="validate[required,maxSize[15]]" value="${orderAddr.sendName}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
                        </div>
                        <div class="input_li">
                            <div class="input_name"><b>*</b>寄件人手机号</div>
                            <div class="input_wri"><input type="text" name="sendPhone" placeholder="请输入寄件人手机号" class="validate[required,custom[mobilePhone]]" value="${orderAddr.sendPhone}" data-errormessage-value-missing="* 请输入寄件人手机号" data-errormessage-custom-error="* 请输入寄件人手机号"></div>        
                        </div>
                        <c:choose>
                            <c:when test="${orderAddr.id != 0 && (orderAddr.sendArea == null || orderAddr.sendArea == '')}">
                            </c:when>
                            <c:otherwise>
                                <div class="input_li">
                                    <div class="input_name"><b>*</b>省市区</div>
                                    <div class="input_wri">
                                        <input type="text" id="revArea" name="sendArea" placeholder="请选择收件地址" class="validate[required]" value="${orderAddr.sendArea}" onfocus="cit.fouse('down_addrRev', 'revUl')" data-errormessage-value-missing="* 请选择收件地址">
                                        <input type="hidden" id="areaId">
                                        <input type="hidden" id="proId" value="0">
                                        <input type="hidden" id="cityId">
                                        <input type="hidden" id="province">
                                        <input type="hidden" id="cityName">									
                                        <div id="down_addrRev" class="down_addr"><!--下拉地区-->
                                            <div class="addr_tit">
                                                <ul id="revUl">
                                                    <li><a href="javascript:void(0);" id="sproTwo" onclick="cit.selectArea('sprovince', 'revArea', 'addr_contRev', 'down_addrRev', 'proId', 'scityTwo', 'sareaTwo', 'sproTwo')" class="addr_tit_li li_on">省份</a></li>
                                                    <li><a href="javascript:void(0);" id="scityTwo" onclick="cit.selectArea('scity', 'revArea', 'addr_contRev', 'down_addrRev', 'cityId', 'scityTwo', 'sareaTwo', 'scityTwo');" class="addr_tit_li">城市</a></li>
                                                    <li><a href="javascript:void(0);" id="sareaTwo" onclick="cit.selectArea('sarea', 'revArea', 'addr_contRev', 'down_addrRev', 'areaId', 'scityTwo', 'sareaTwo', 'sareaTwo');" class="addr_tit_li">区县</a></li>
                                                </ul>
                                                <a href="javascript:void(0);" class="off" onclick="yinchan('down_addrRev')">×</a>
                                            </div>
                                            <div id="addr_contRev" class="addr_cont">
                                                ${procityone}
                                            </div>
                                        </div>
                                    </div>        
                                </div>
                            </c:otherwise>
                        </c:choose>					
                        <div class="input_li">
                            <div class="input_name"><b>*</b>寄件人详细地址</div>
                            <div class="input_wri"><input type="text" name="sendAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${orderAddr.sendAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>        
                        </div>  
						<div class="input_li">
                            <div class="input_name"><b>*</b>收件人姓名</div>
                            <div class="input_wri"><input type="text" id="revName" name="revName" placeholder="请输入寄件人姓名" class="validate[required,maxSize[15]]" value="${orderAddr.revName}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
                        </div>
                        <div class="input_li">
                            <div class="input_name"><b>*</b>收件人手机号</div>
                            <div class="input_wri"><input type="text" name="revPhone" placeholder="请输入寄件人手机号" class="validate[required,custom[mobilePhone]]" value="${orderAddr.revPhone}" data-errormessage-value-missing="* 请输入寄件人手机号" data-errormessage-custom-error="* 请输入正确的联系电话"></div>        
                        </div>
						<div class="input_li">
                            <div class="input_name"><b>*</b>收件人详细地址</div>
                            <div class="input_wri"><input type="text" name="revAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${orderAddr.revAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>        
                        </div>
						<div class="input_li">
                            <div class="input_name"><b>*</b>寄件时间</div>
                            <div class="input_wri"><input type="text" id="sendTime" name="sendTime" placeholder="请输入寄件时间" class="validate[required,maxSize[30]]" value="${orderAddr.sendTime}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
                        </div>
						<div class="input_li">
                            <div class="input_name"><b>*</b>运费</div>
                            <div class="input_wri"><input type="text" id="freight" name="freight" placeholder="请输入寄件人姓名" class="validate[required,custom[isMoney]]" value="${orderAddr.freight}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>					
                        </div>
                        <input type="hidden" name="id"  value="${orderAddr.id}">
                        <input type="hidden" name="uid"  value="${uid}">
                    </div>
                </div>
                <div class="out_new_li">
                    <div class="outnew_tit"> <img src="<%=request.getContextPath()%>/statics/front/images/other_icon.png" width="40" height="36"> <strong>其他信息</strong> </div>
                    <div class="cont_cont">
						<div class="input_li">
                            <div class="input_name">寄件网点</div>
                            <div class="input_wri input_wri06">
								<input type="text" id="sendSubstation" name="sendSubstation" value="${orderAddr.sendSubstation}" class="validate[maxSize[50]]" data-errormessage-custom-error="* 请输入寄件网点">
							</div>        
						</div>
						<div class="input_li">
                            <div class="input_name">寄件快递员</div>
                            <div class="input_wri input_wri06">
                                <input type="text" id="sendCourier" name="sendCourier" value="${orderAddr.sendCourier}" class="validate[maxSize[50]]" data-errormessage-custom-error="* 请输入寄件快递员">
							</div>        
						</div>
						<div class="input_li">
                            <div class="input_name">收件网点</div>
                            <div class="input_wri input_wri06">
                                <input type="text" id="revSubstation" name="revSubstation" value="${orderAddr.revSubstation}" class="validate[maxSize[50]]" data-errormessage-custom-error="* 请输入收件网点">
							</div>        
						</div>
						<div class="input_li">
                            <div class="input_name">收件快递员</div>
                            <div class="input_wri input_wri06">
                                <input type="text" id="revCourier" name="revCourier" value="${orderAddr.revCourier}" class="validate[maxSize[50]]" data-errormessage-custom-error="* 请输入收件快递员">
							</div>        
						</div>
                        <div class="input_li">
                            <div class="input_name">物品类别</div>
                            <div class="input_wri input_wri06">
                                <select name="itemStatus" >
                                    <zh:option map="${WPTYPE}" addBlank="true" value="${orderAddr.itemStatus}" pdNull="true" tagNameShow="---请选择---"></zh:option>
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
                                    <zh:option map="${PAYP}" addBlank="true" value="${orderAddr.freightType}" pdNull="true" tagNameShow="---请选择---"></zh:option>
                                    </select>
                                </div>        
                            </div>
                            <div class="input_li">
                                <div class="input_name">付款方式</div>
                                <div class="input_wri input_wri06">
                                    <select  name="payType">
                                    <zh:option map="${PAYTYPE}" addBlank="true" value="${orderAddr.payType}" pdNull="true" tagNameShow="---请选择---"></zh:option>
                                    </select>
                                </div>        
                            </div>

                            <div class="input_li">
                                <div class="input_name">保价金额</div>
                                <div class="input_wri input_wri06">
                                    <input type="text" id="goodValuation" name="goodValuation" class="validate[custom[isMoney]]" value="${orderAddr.goodValuation}" onblur="money();">

                            </div>
							<!--                            <div class="input_span02">（单位：元）保价手续费5‰（保费<span id="bmoney">0</span>元）</div>		-->
                        </div>
<!--                        <c:if test="${lgcNo eq '1131'}">
                            <div class="input_li">
                                <div class="input_name">运费</div>
                                <div class="input_wri input_wri06"><input type="text" id="freight" name="freight" value="${orderAddr.freight}" class="validate[custom[isMoney]]" data-errormessage-custom-error="* 请输入运费"></div>															
                            </div>							
                        </c:if>
                        <div class="input_li">
                            <div class="input_name">运单号</div>
                            <div class="input_wri input_wri06"><input type="text" id="lgcOrderNo" name="lgcOrderNo" value="${orderAddr.lgcOrderNo}" minlen="0" maxlen="20" class="validate[funcCall[numberzm]]" data-errormessage-custom-error="* 请输入运单号"></div>															
                        </div>-->
                        <div class="input_li">
                            <div class="input_name">备注</div>
                            <div class="input_wri">
                                <input type="text" name="orderNote" value="${orderAddr.orderNote}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="out_button">
                    <a href="javascript:void(0);" onclick="add()" class="button">保存</a>
                    <a href="javascript:void(0);" onclick="cacel();" class=" button_2">取消</a>
                </div>
            </div>
        </form>
    </body>
</html>
