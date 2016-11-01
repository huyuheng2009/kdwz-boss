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
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/UploadImport.js"></script>
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
			});

		

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
				location.href = "<%=request.getContextPath()%>/batchOrder/orderAddExportImport?ids=" + x.ids.join(",");
			}

			function updownExcel() {
				location.href = "<%=request.getContextPath()%>/template/orderAddrImport.xls";
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

        <form id="myform" action="addOrdeImport" method="post">
            <div class="gdt">               
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
                        <iframe id="frame" class="ta_iframe" name="frame" frameborder=0 scrolling=auto src="<%=request.getContextPath()%>/batchOrder/queryOrderAddrImport?layout=no"></iframe>
                    </div>
                </div>                  
                <div class="cont_button"><input class="button" type="submit" value="提交"></div>
            </div>
        </form>

    </body>
</html>
