<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${constants['backend_title']}</title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/css/validationEngine.jquery.css" type="text/css">
		<%--<jsp:include page="/WEB-INF/view/include/select_city.jsp" />--%>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine.js"></script>

        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/city.js"></script>
		<c:if test="${msg != null}">
			<script>
				$(function() {
					jQuery("#sendName").validationEngine('showPrompt', "* ${msg}", null, null, true);
				})
			</script>
		</c:if>
		<script>
			var webPath = "<%=request.getContextPath()%>";
			var uid = "${uid}";
			var a = "${hm}";
			$(function() {
//				SelectCity($("input[name='sendArea']"));
//				SelectCity($("input[name='revArea']"), 'city_dataTwo');
				//InitOption('pro', 'city', 'areat', '广东', '深圳市', '南山区');
				//$(function(){ $('input, textarea').placeholder();});
				$("#down_addrSend").hide();
				$("#down_addrRev").hide();
				jQuery("#myform").validationEngine();

				init();

				$(".time_day").click(function() {
					$("#timeDay").css("display", "block");
					if ($(this).attr("dt") === "1") {
						var oDate = new Date();
						a = oDate.getHours() + ":" + oDate.getMinutes();
						init();
					} else {
						$(".time_li").each(function(i, e) {
							$(e).show();
						});
					}
					$(".time_day").each(function() {
						$(this).removeClass("li_on");
					})
					$(this).addClass("li_on");
				});

				$(".time_li").click(function() {
					var t;
					var oDate = new Date();
					var year = oDate.getFullYear();
					var month = oDate.getMonth() + 1;
					var day = oDate.getDate();
					var hourse = oDate.getHours();
					var minus = oDate.getMinutes();
					var vt = $(this).text().split(":");
					$(".time_day").each(function() {
						if ($(this).hasClass("li_on")) {
							t = $(this).attr("id");
						}
					});
					var y = t.split("-");
					if (year == parseInt(y[0])) {
						if (month == parseInt(y[1])) {
							if (day == parseInt(y[2])) {
								if (hourse > parseInt(vt[0])) {
									alert("你选择的时间已过去");
									return false;
								} else if (hourse == parseInt(vt[0])) {
									if (minus >= parseInt(vt[1])) {
										alert("你选择的时间已过去");
										return false;
									}
								}
							}
						}
					}
					$(".time_li").each(function() {
						$(this).removeClass("li_on");
					});
					$(this).addClass("li_on");
					t = t + " " + $(this).text() + ":00";
					$("#takeTimeBegin").val(t);
					$("#timeDay").css("display", "none");
				});
			});

			function init() {
				var arry = a.split(":");
				var x;
				if (parseInt(arry[1]) === 0) {
					x = parseInt(arry[0] + arry[1]);
				} else if (parseInt(arry[1]) >= 1 && parseInt(arry[1]) <= 30) {
					x = parseInt(arry[0] + arry[1]) + 29;
				} else {
					x = (parseInt(arry[0]) + 1) + "" + 20;
				}
				$(".time_li").each(function(i, e) {
					if (parseInt($(e).data("tf")) <= parseInt(x)) {
						$(e).hide();
					}
				});
			}
			var cit = new selectProvince();

			function address(vale) {
				$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '地址蒲',
					content: "url:<%=request.getContextPath()%>/front/address/queryRadiaoAddr?addrType=" + vale + "&userName=${user.userName}&uid=" + uid,
					lock: true
				});
			}

			function selectCheckbox(obj) {
				var t = $(obj).attr("checked");
				$("#tme").val('');
				if (t === "checked") {
					$("#tme").css("display", "block");
				} else {
					$("#tme").css("display", "none");
				}
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

			function getFouse() {
				$("#timeDay").css("display", "block");
			}

			function closeDate() {
				$("#timeDay").css("display", "none");
			}

			function cleanDate() {
				$("#takeTimeBegin").val('');
			}

		</script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/view/include/headPage.jsp" />
		<form id="myform" action="addOrderSimle" method="post">
			<div class="gdt">
				<div class="cont_li">
					<div class="cont_tit">
						<img src="<%=request.getContextPath()%>/statics/front/images/xia_icon.png" width="40" height="36">
						<strong>寄件人信息</strong>
					</div>				
					<div class="cont_cont">
						<div class="input_li">
							<div class="input_name"><b>*</b>姓名</div>
							<div class="input_wri"><input type="text" class="validate[required,maxSize[15]]" id="sendName" name="sendName" placeholder="请输入寄件人姓名" value="${params.sendName}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>
							<a href="javascript:void(0);" ${user.userName == null ? '':'onclick="address(1);"'} class="input_span">从地址簿中选择</a>							
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>联系电话</div>
							<div class="input_wri"><input type="text" id="sendPhone" name="sendPhone" placeholder="请输入联系电话" class="validate[required,funcCall[mobilePhone]]" value="${params.sendPhone}" data-errormessage-value-missing="* 请输入联系电话" data-errormessage-custom-error="* 请输入正确的联系电话"></div>        
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>省市区</div>
							<div class="input_wri">
								<input type="text" id="sendArea" name="sendArea" placeholder="请选择寄件地址" class="validate[required]" value="${params.sendArea}" onfocus="cit.fouse('down_addrSend', 'sendUl')" data-errormessage-value-missing="* 请选择寄件地址">
                                <input type="hidden" id="areaId">
								<input type="hidden" id="proId" value="0">
								<input type="hidden" id="cityId">
								<input type="hidden" id="province">
								<input type="hidden" id="cityName">
								<div id="down_addrSend" class="down_addr" ><!--下拉地区-->
									<div class="addr_tit">
										<ul id="sendUl">
											<li><a href="javascript:void(0);" id="spro" onclick="cit.selectArea('sprovince', 'sendArea', 'addr_contSend', 'down_addrSend', 'proId', 'scity', 'sarea', 'spro')" class="addr_tit_li li_on">省份</a></li>
											<li><a href="javascript:void(0);" id="scity" onclick="cit.selectArea('scity', 'sendArea', 'addr_contSend', 'down_addrSend', 'cityId', 'scity', 'sarea', 'scity');" class="addr_tit_li">城市</a></li>
											<li><a href="javascript:void(0);" id="sarea" onclick="cit.selectArea('sarea', 'sendArea', 'addr_contSend', 'down_addrSend', 'areaId', 'scity', 'sarea', 'sarea');" class="addr_tit_li">区县</a></li>
										</ul>
										<a href="javascript:void(0);" class="off" onclick="yinchan('down_addrSend')">×</a>
									</div>
									<div id="addr_contSend" class="addr_cont">
										${procity}
									</div>
								</div>        
							</div>
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>详细地址</div>
							<div class="input_wri"><input type="text" id="sendAddr" name="sendAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${params.sendAddr}" data-errormessage-value-missing="* 请输入详细地址（精确到门牌号）"></div>        
						</div> 
						<input type="hidden" name="lgcNo" value="${lgcNo}">
						<input type="hidden" name="userName" value="${user.userName}">
						<input type="hidden" name="uid"  value="${uid}">
					</div>
				</div>

				<div class="cont_li">
					<div class="cont_tit">
						<img src="<%=request.getContextPath()%>/statics/front/images/shou_icon.png" width="40" height="36">
						<strong>收件人信息</strong>
					</div>
					<div class="cont_cont">
						<div class="input_li">
							<div class="input_name"><b>*</b>姓名</div>
							<div class="input_wri"><input type="text" id="revName" name="revName" class="validate[required]" placeholder="请输入收件人姓名" value="${params.revName}" data-errormessage-value-missing="* 请输入收件人姓名"></div>
							<a href="javascript:void(0);" ${user.userName == null ? '':'onclick="address(2);"'} class="input_span">从地址簿中选择</a>
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>联系电话</div>
							<div class="input_wri"><input type="text" id="revPhone" name="revPhone" placeholder="请输入联系电话" class="validate[required,funcCall[mobilePhone]]" value="${params.revPhone}" data-errormessage-value-missing="* 请输入联系电话" data-errormessage-custom-error="* 请输入正确的联系电话"></div>        
						</div>
						<div class="input_li">
							<div class="input_name"><b>*</b>省市区</div>
							<div class="input_wri">
								<input type="text" id="revArea" name="revArea" placeholder="请选择收件地址" class="validate[required]" value="${params.revArea}" readonly="readonly" onfocus="cit.fouse('down_addrRev', 'revUl')" data-errormessage-value-missing="* 请选择收件地址">
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
						<div class="input_li">
							<div class="input_name"><b>*</b>详细地址</div>
							<div class="input_wri"><input type="text" id="revAddr" name="revAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${params.revAddr}" data-errormessage-value-missing="* 请输入详细地址（精确到门牌号）"></div>        
						</div>      
					</div>
				</div>
				<div class="cont_li">
					<div class="cont_tit">
						<img src="<%=request.getContextPath()%>/statics/front/images/other_icon.png" width="40" height="36">
						<strong>其他信息</strong>
					</div>
					<div class="cont_cont">
						<div class="input_li">
							<div class="input_name">物品类别</div>
							<div class="input_wri input_wri02">
								<select name="itemStatus" >
									<zh:option map="${WTYPE}" addBlank="true" value="${params.itemStatus}" pdNull="true" tagNameShow="---请选择---"></zh:option>
									</select>
								</div>        
							</div>
							<div class="input_li">
								<div class="input_name">物品重量</div>
								<div class="input_wri input_wri02">
									<input type="text" id="itemWeight" name="itemWeight" class="validate[custom[decimal]]" data-errormessage-custom-error="* 请输入正确的数据">
								</div>  
								<div class="input_span02">（单位:kg）</div>
							</div>
							<div class="input_li">
								<div class="input_name">付款人</div>
								<div class="input_wri input_wri02">
									<select  name="freightType">
									<zh:option map="${PAYP}" addBlank="true" value="${params.freightType}" pdNull="true" tagNameShow="---请选择---"></zh:option>
									</select>
								</div>        
							</div>
							<div class="input_li">
								<div class="input_name">付款方式</div>
								<div class="input_wri input_wri02">
									<select  name="payType">
									<zh:option map="${PAYTYPE}" addBlank="true" value="${params.payType}" pdNull="true" tagNameShow="---请选择---"></zh:option>
									</select>
								</div>        
							</div>

							<div class="input_li">
								<div class="input_name">保价金额</div>
								<div class="input_wri input_wri02"><input type="text" id="goodValuation" name="goodValuation" class="validate[custom[isMoney]]" onblur="money()" data-errormessage-custom-error="* 请输入正确的金额"></div>
								<div class="input_span02">（单位:元）保价手续费5‰（保费<span id="bmoney">0</span>元）</div>							
							</div>
						<c:if test="${lgcNo eq '1131'}">
							<div class="input_li">
								<div class="input_name">运费</div>
								<div class="input_wri input_wri02"><input type="text" id="freight" name="freight" value="${orderAddr.freight}" class="validate[custom[isMoney]]" data-errormessage-custom-error="* 请输入运费"></div>															
							</div>
							<div class="input_li">
								<div class="input_name">运单号</div>
								<div class="input_wri input_wri02"><input type="text" id="lgcOrderNo" name="lgcOrderNo" minlen="0" maxlen="20" class="validate[funcCall[numberzm]]" data-errormessage-custom-error="* 请输入运单号"></div>															
							</div>
						</c:if>
						<div class="input_li">
							<div class="input_name">备注</div>
							<div class="input_wri input_wri03"><input type="text" name="orderNote"></div>
						</div>      
					</div>
				</div>
				<div class="reser">
					<div class="re_check"><input name="" type="checkbox" value="" id="checkboxInput" onclick="selectCheckbox(this);"><label  for="checkboxInput"></label></div>
					<div class="input_name">预约取件时间</div>
					<div id="tme" class="input_wri input_wri04" style="display: none;">
						<!--							<input id="tme" type="text" name="takeTimeBegin" style="display: none;" class="bg_time" readonly="readonly" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '%y-%M-%d %H:\u0023{%m+30}:%s'})">-->
						<input id="takeTimeBegin"  class="bg_arr" readonly="true" type="text" placeholder="请选择时间" name="takeTimeBegin" onfocus="getFouse();">
						<div class="down_addr down_addr02" id="timeDay" style="display: none;">
							<div class="time_left">
								<ul>
									<li class="time_t">日期</li>
									<li><a id="${jty}" class="time_day li_on" dt="1">${jt}</a></li>
									<li><a id="${mty}" class="time_day" dt="2">${mt}</a></li>
									<li><a id="${hty}" class="time_day" dt="3">${ht}</a></li>
								</ul>
							</div>
							<div class="time_right">
								<ul>
									<li class="time_t">时分</li>
									<li>
										<a class="time_li" data-tf="800">08:00</a><a class="time_li" data-tf="830">08:30</a><a class="time_li" data-tf="900">09:00</a><a class="time_li" data-tf="930">09:30</a><a class="time_li" data-tf="1000">10:00</a><a class="time_li" data-tf="1030">10:30</a><a class="time_li" data-tf="1100">11:00</a><a class="time_li" data-tf="1130">11:30</a><a class="time_li" data-tf="1200">12:00</a><a class="time_li" data-tf="1230">12:30</a>
										<a class="time_li" data-tf="1300">13:00</a><a class="time_li" data-tf="1330">13:30</a><a class="time_li" data-tf="1400">14:00</a><a class="time_li" data-tf="1430">14:30</a><a class="time_li" data-tf="1500">15:00</a><a class="time_li" data-tf="1530">15:30</a><a class="time_li" data-tf="1600">16:00</a><a class="time_li" data-tf="1630">16:30</a><a class="time_li" data-tf="1700">17:00</a><a class="time_li" data-tf="1730">17:30</a>
										<a class="time_li" data-tf="1800">18:00</a><a class="time_li" data-tf="1830">18:30</a><a class="time_li" data-tf="1900">19:00</a><a class="time_li" data-tf="1930">19:30</a><a class="time_li" data-tf="2000">20:00</a><a class="time_li" data-tf="2030">20:30</a><a class="time_li" data-tf="2100">21:00</a>
									</li>
								</ul>
							</div>
							<div class="time_butt">
								<div class="time_button"><input class="button_2" type="button" value="关闭" onclick="closeDate();"></div>
								<div class="time_button"><input class="button_2" type="button" value="清空" onclick="cleanDate();"></div>
							</div>
							<!--							<table class="time_table" cellpadding="0" cellspacing="0" border="0" width="100%">
															<tr>
																<th width="140">日期</th>
																<th>时分</th>
															</tr>
															<tr>
																<td><a id="${jty}" class="time_day li_on">${jt}</a></td>
																<td><a class="time_li" data-tf="800">08:00</a><a class="time_li" data-tf="830">08:30</a><a class="time_li" data-tf="900">09:00</a><a class="time_li" data-tf="930">09:30</a><a class="time_li" data-tf="1000">10:00</a><a class="time_li" data-tf="1030">10:30</a><a class="time_li" data-tf="1100">11:00</a><a class="time_li" data-tf="1130">11:30</a><a class="time_li" data-tf="1200">12:00</a><a class="time_li" data-tf="1230">12:30</a></td>
															</tr>
															<tr>
																<td><a id="${mty}" class="time_day">${mt}</a></td>
																<td><a class="time_li" data-tf="1300">13:00</a><a class="time_li" data-tf="1330">13:30</a><a class="time_li" data-tf="1400">14:00</a><a class="time_li" data-tf="1430">14:30</a><a class="time_li" data-tf="1500">15:00</a><a class="time_li" data-tf="1530">15:30</a><a class="time_li" data-tf="1600">16:00</a><a class="time_li" data-tf="1630">16:30</a><a class="time_li" data-tf="1700">17:00</a><a class="time_li" data-tf="1730">17:30</a></td>
															</tr>
															<tr>
																<td><a id="${hty}" class="time_day">${ht}</a></td>
																<td><a class="time_li" data-tf="1800">18:00</a><a class="time_li" data-tf="1830">18:30</a><a class="time_li" data-tf="1900">19:00</a><a class="time_li" data-tf="1930">19:30</a><a class="time_li" data-tf="2000">20:00</a><a class="time_li" data-tf="2030">20:30</a><a class="time_li" data-tf="2100">21:00</a></td>
															</tr>         
														</table>-->
						</div>
					</div>
				</div>
				<div class="cont_button"><input class="button" type="button" value="提交" onclick="submitone();"></div>
			</div>

		</form>
<%-- 		<jsp:include page="/WEB-INF/view/include/footerPage.jsp" /> --%>
    </body>
</html>
