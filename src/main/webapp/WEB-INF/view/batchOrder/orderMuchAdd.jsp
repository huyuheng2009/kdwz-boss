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
<!--		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/cityHP.js"></script>  -->
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/Upload.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/city.js"></script>
        <c:if test="${msg != null}">
            <script>
                $(function() {
                    jQuery("#addressMsg").validationEngine('showPrompt', "* ${msg}", null, null, true);
                })
            </script>
        </c:if>
        <style>
            #uploadXLS {display: none;z-index: 100;background: white;border-bottom: 1px solid #DCDCDC;}
            #uploadXLS input:first-child {display: none;}
            #uploadXLS .a {height: 2em;line-height: 2em;text-indent: 20px;}
            #uploadXLS .a span.a1{font-weight: bolder;}
            #uploadXLS .p{height: 20px;width: calc(100% - 40px);margin: 10px auto;
                          box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -webkit-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -ms-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -o-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -moz-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);}
            #uploadXLS .p2 {height: 100%;width: 0;background: #E25A48;}
        </style>
        <script>
            var uid = "${uid}";
            var a = "${hm}";
            $(function() {
                $("#down_addrSend").hide();
                $("#down_addrRev").hide();

//				SelectCity($("input[name='sendArea']"));
                jQuery("#myform").validationEngine();
                var u = new Upload($("#uploadXLS"), [".xls"], {params: {"uid": uid}});
                $("#importExcel").click(function(evt) {
                    if (!u.lock) {
                        u.$file.click();
                    }
                    evt.preventDefault();
                });

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
            var webPath = "<%=request.getContextPath()%>";
            function address(vale) {
                $.dialog({
                    width: '920px',
                    height: '540px',
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                    title: '地址蒲',
                    content: "url:<%=request.getContextPath()%>/batch/queryRadiaoAddr?layout=no",
                    lock: true
                });
            }

            function addressTwo(vale) {
                $.dialog({
                    width: '920px',
                    height: '540px',
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                    title: '地址蒲',
                    content: "url:<%=request.getContextPath()%>/front/address/queryCheckBoxAddr?addrType=" + vale + "&userName=${user.userName}&uid=" + uid,
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

            function selectSaveAddr(obj) {
                var t = $(obj).attr("checked");
                if (t === "checked") {
                    $("#saveAddr").val("Y");
                } else {
                    $("#saveAddr").val("N");
                }
            }

            function importExcel() {
                var x = document.getElementById("frame").contentWindow;
                location.href = "<%=request.getContextPath()%>/batchOrder/orderAddExport?ids=" + x.ids.join(",");
            }

            function updownExcel() {
                location.href = "<%=request.getContextPath()%>/template/orderAddr.xls";
            }

            function updownYxExcel() {
                location.href = "<%=request.getContextPath()%>/template/yxOrderAddr.xls";
            }

            function getFouse() {
                $("#timeDay").css("display", "block");
            }

            function saveAddr() {
                var flat = true;
                var error = "";
                if ($('input[name=sendName]').val() == "" || $('input[name=sendName]').val().length < 1) {
                    error = "请输入姓名";
                    flat = false;
                }
                if ($('input[name=sendPhone]').val() == "" || $('input[name=sendPhone]').val().length < 1) {
                    error = "请输入联系电话";
                    flat = false;
                }
                if ($('input[name=sendArea]').val() == "" || $('input[name=sendArea]').val().length < 1) {
                    error = "请输入大区地址";
                    flat = false;
                }
                if ($('input[name=sendAddr]').val() == "" || $('input[name=sendAddr]').val().length < 1) {
                    error = "请输入详细地址";
                    flat = false;
                }

                if (!flat) {
                    alert(error);
                    return;
                } else {

                    $.ajax({
                        url: '/batch/saveAddr',
                        type: 'get',
                        data: {
                            'sendName': $('input[name=sendName]').val(),
                            'sendPhone': $('input[name=sendPhone]').val(),
                            'sendArea': $('input[name=sendArea]').val(),
                            'sendAddr': $('input[name=sendAddr]').val(),
                        },
                        success: function(msg) {
                            alert('保存成功');
                        }
                    });
                }
            }

        </script>
    </head>
    <body>

        <form id="myform" action="addOrderMuch" method="post">
            <div class="gdt">
                <div class="cont_li">
                    <div class="cont_tit">
                        <img src="<%=request.getContextPath()%>/statics/front/images/xia_icon.png" width="40" height="36">
                        <strong>寄件人信息</strong>
                    </div>
                    <div class="cont_cont">
                        <div class="input_li">
                            <div class="input_name"><b>*</b>姓名</div>
                            <div class="input_wri"><input type="text" id="sendName" name="sendName" placeholder="请输入寄件人姓名" class="validate[required]" value="${params.sendName}" data-errormessage-value-missing="* 请输入寄件人姓名"></div>
                            <a href="javascript:void(0);" onclick="address(1)" class="input_span">从地址簿中选择</a>
                        </div>
                        <div class="input_li">
                            <div class="input_name"><b>*</b>联系电话</div>
                            <div class="input_wri"><input type="text" id="sendPhone" name="sendPhone" placeholder="请输入联系电话" class="validate[required,funcCall[mobilePhone]]" value="${params.sendPhone}" data-errormessage-value-missing="* 请输入手机号" data-errormessage-custom-error="* 请输入正确的手机号"></div>        
                        </div>
                        <div class="input_li">
                            <div class="input_name"><b>*</b>省市区</div>
                            <div class="input_wri">
                                <input class="validate[required]" id="sendArea" name="sendArea" type="text" placeholder="请选择省/市/区" value="${params.sendArea}" onfocus="cit.fouse('down_addrSend', 'sendUl')" data-errormessage-value-missing="* 请选择省/市/区">
                                <input type="hidden" id="areaId">
                                <input type="hidden" id="proId" value="0">
                                <input type="hidden" id="cityId">
                                <input type="hidden" id="province">
                                <input type="hidden" id="cityName">	
                                <input type="hidden" name="layout" value="no">
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
                            <div class="input_wri"><input type="text" id="sendAddr" name="sendAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${params.sendAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>    
                            <a href="javascript:void(0);" onclick="saveAddr();" class="button_2">保存到地址薄</a>
                        </div>      
                    </div>
                    <input type="hidden" id="ids" name="ids" />
                    <input type="hidden" id="lgcNo" name="lgcNo" value="${lgcNo}"/>		
                    <input type="hidden" name="uid"  value="${uid}">
                    <input type="hidden" id="saveAddr" name="saveAddr" value="N"/>	
                </div>
                <div class="cont_li square">
                    <div class="cont_tit">
                        <img src="<%=request.getContextPath()%>/statics/front/images/shou_icon.png" width="40" height="36">
                        <strong>收件人信息</strong>
                        <div class="ta_butt">							
                            <a id="importExcel" href="javascript:void(0);" class="button_2">从Excel导入</a>
                            <a href="javascript:void(0)" onclick="updownExcel();" class="button_2">导入模版下载</a>
                            <a href="javascript:void(0);" onclick="importExcel();" class="button_2">导出到Excel</a>      
                        </div>
                    </div>
                    <div id="uploadXLS">
                        <input type="file" />
                        <div class="a">
                            上传　<span class="a1">账户授权标准表格（南昌）.xls</span>　<span class="a2"></span>
                        </div>
                        <div class="p"><div class="p2"></div></div>
                    </div>
                    <div class="cont_table">
                        <iframe id="frame" class="ta_iframe" name="frame" frameborder=0 scrolling=auto src="<%=request.getContextPath()%>/batchOrder/selectOrderAddr?layout=no"></iframe>
                    </div>
                </div>  
                <div class="reser">
                    <div class="reser_li">
                        <div class="re_check"><input name="" type="checkbox" value="" id="checkboxInput" onclick="selectCheckbox(this);"><label  for="checkboxInput"></label></div>
                        <div class="input_name02">预约取件时间</div>
                    </div>
                    <!--					<div class="reser_li">
                                                                    <div class="re_check"><input name="" type="checkbox" value="" id="checkboxInputAddr"><label  for="checkboxInputAddr"></label></div>
                                                                    <div class="input_name02">保存到地址簿中</div>
                                                            </div>    -->
                    <div id="tme" class="input_wri input_wri05" style="display: none;">
                        <!--						<input id="tme" type="text" name="takeTimeBegin" style="display: none;" class="bg_time" readonly="true" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '%y-%M-%d %H:\u0023{%m+30}:%s'})">-->
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
                        </div>
                    </div>
                </div>
                <div class="cont_button"><input class="button" type="submit" value="提交"></div>
            </div>
        </form>

    </body>
</html>
