<%@ page pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>快递王子</title>
		<meta name="keywords" content="快递王子" />
		<meta name="description" content="快递王子" />

		<link rel="stylesheet" href="<%=request.getContextPath()%>/statics/backend/css/main_new.css"/>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/statics/backend/css/check.css"/>		
		<!--		<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.3.min.js"></script>-->
		<!--[if lt IE 9]> 
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script> 
		<![endif]--> 
		<style>
			.hide {display: none !important;}
			.soso_search_b {height: 40px;line-height: 40px;}
			.progress {vertical-align: middle;}
			.re_check input{width: inherit;height: inherit;}
			#view th{width: 25%;}
		</style>
		<script>
			var Uploader = {
				files: [],
				start: function() {
					var fs = [];
					var map = {};
					$("#view ._selected:checked").each(function(i, e) {
						var index = parseInt($(e).val());
						var k = "index_" + index;
						if (map[k])
							return;
						if (index >= 0 && index < Uploader.files.length) {
							fs.push(Uploader.files[index]);
						}
						map[k] = true;
					});
					if (fs.length === 0) {
						alert("请选择一张图片");
						return;
					}
					if (!$(".soso_input input:checked").val()) {
						alert("请选择面单类型");
						return;
					}
					var type = $(".soso_input input:checked").val();
//					for (var i = 0; i < fs.length; i++) {
//						this.post(fs[i], type);
//					}
					Uploader.post(type, 0);
					$("#upmess").html("正在上传.......");
				},
				post: function(type, index) {
					var next = function() {
						setTimeout(function() {
							Uploader.post(type, index + 1);
						}, 1);
					};
					var $e = null;
					$("._new").each(function(i, e) {
						if (i === index) {
							$e = $(e);
							return false;
						}
					});
					if ($e === null)
						return;
					if (!$e.find(".re_check input").prop("checked")) {
						next();
						return;
					}
					var f = $e.data("file");
					var formData = new FormData();
					formData.append('type', type);
					formData.append('file', f);
					//
					var xhr = new XMLHttpRequest();
					xhr.open("POST", "upload_img", true);
					xhr.responseType = "text";//IE不支持 JSON 类型
					//
					xhr.upload.addEventListener("loadstart", function(evt) {
//						$(".soso_search_b:eq(0)").addClass("hide");
//						$(".soso_search_b:eq(1)").removeClass("hide");   						
						if (index === ($("._new").length-1)) {
							$(window).scrollTop($e.offset().top);
							$("#upmess").html("上传完成");
						}
						$e.find("progress").siblings("span").addClass("hide");
						$e.find("progress").removeClass("hide");
					}, false);
					xhr.upload.addEventListener("loadend", function(evt) {
					}, false);
					xhr.upload.addEventListener("progress", function(evt) {
						if (evt.lengthComputable) {
//							$(".progress").attr("value", Math.floor(evt.loaded * 100 / evt.total));
							$e.find("progress").attr("value", Math.floor(evt.loaded * 100 / evt.total));
						}
					}, false);
					xhr.addEventListener("readystatechange", function(evt) {
						if (this.readyState === this.DONE) {
							var res = null;
							if (this.response !== null)
								res = $.parseJSON(this.response);
							if (res === null) {
								alert("没有响应");
							} else {
								var msg = res.message;
								if (res.errorCode != 0) {
									msg += "[" + res.errorCode + "]";
								} else {
									$e.find(".re_check input").prop("checked", false);
								}
								$e.find("progress").siblings("span").text(msg);
								$e.find("progress").siblings("span").removeClass("hide");
//								alert(msg);
							}
							$e.find("progress").addClass("hide");
							next();
//							$(".soso_search_b:eq(0)").removeClass("hide");
//							$(".soso_search_b:eq(1)").addClass("hide");
						}
					});
					xhr.send(formData);
				}
			};
			$(function() {
				$("#input_file").change(function() {
					$("._new").remove();
					var files = Uploader.files = this.files;
					for (var i = 0; i < files.length; i++) {
						var f = files[i];
						var url = URL.createObjectURL(f);
						var tr = $("#view tr.hide").clone();
						tr.data("file", f);
						tr.removeClass("hide");
						tr.addClass("_new");
						tr.find("img").attr("src", url);
						var no = f.name;
						var k = no.indexOf(".");
						if (k >= 0) {
							no = no.substring(0, k);
						}
						tr.find("td:eq(2)").text(no);
						var checkbox = tr.find(".re_check input");
						checkbox.val(i);
						checkbox.addClass("_selected");
						$("#view").append(tr);
					}
					$("#select_all").trigger("select", [true]);
				});
				// 
				$("#select_all").bind("select", function(evt, select) {
					if (select) {
						$("#select_all, ._selected").prop("checked", true);
					} else {
						$("#select_all, ._selected").prop("checked", false);
					}
				});
				$("#select_all").change(function() {
					var checked = $(this).prop("checked");
					$(this).trigger("select", [checked]);
				});
			});
		</script>
	</head>
	<body style="background:#e5ebf2; font-size:12px;color:#333;"><!--页面背景-->
		<!--		<div style="background:#fff;">页眉背景
					<div class="sx_header">
						<div class="sx_header_l"><img src="../images/logo.png" width="80" height="30"></div>
						<div class="sx_header_r">欢迎您，黄文婷！<a href="#">[修改密码]</a><a href="#">[退出登录]</a></div>
					</div>
				</div>
				<div class="sx_header_nav">
					<ul>
						<li class="header_navli li_on"><a href="#" class="one">系统</a></li>
						<li class="header_navli"><a href="#" class="one">公司</a></li>
						<li class="header_navli"><a href="#" class="one">客户</a></li>
						<li class="header_navli hover_on"><a href="#" class="one">订单管理</a></li>
						<li class="header_navli"><a href="#" class="one">扫描</a></li>
						<li class="header_navli"><a href="#" class="one">追踪</a></li>
						<li class="header_navli"><a href="#" class="one">问题件</a></li>
						<li class="header_navli"><a href="#" class="one">会员</a></li>
					</ul>
				</div>-->
		<!--内容背景-->
		<div class="king">
			<!--			<div class="king_position">
							<a href="#">亿翔后台</a>　＞　<a href="#">面单</a>　＞　<a href="#">面单上传</a>
						</div>-->
			<div class=" soso"><!--按钮上下结构-->    
				<!--按钮左右结构-->
				<div class="soso_left"> 
					<div class="soso_search_b">
						<!--<div class="soso_button"><input type="submit" value="图片浏览"></div>-->
						<div class="soso_button"><input type="button" value="图片上传" onclick="Uploader.start()" /></div>
						<div class="soso_button"><input type="button" value="选择图片" onclick="document.getElementById('input_file').click();" /></div>
						<input id="input_file" type="file" style="visibility: hidden;" accept=".jpg,.png,.gif,.bmp" multiple="" />
					</div>
					<div class="soso_search_b hide">
						上传中：
						<progress class="progress" value="0" max="100"></progress>
					</div>
				</div>
			</div>
			<div class="table">
				<div class="ta_header">
					<div class="check_li" style="margin-left:20px;">
						<div class="soso_b">面单类型</div>
						<div class="soso_input">
							<label><input name="type" value="1"  type="radio" checked="checked">派件单</label>
							<label><input name="type" value="2" type="radio">收件单</label>
							<!--							<label><input name="type" value="3" type="radio">其他</label>-->
						</div>						
					</div>
					<div id="upmess" class="soso_b" style="float:right;margin-top:20px;font-size: 20px;color: red;margin-right: 50px;"></div>
				</div>
				<div class="ta_table">
					<table id="view" class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th class="num_all"><div class="re_check"><input id="select_all" type="checkbox" value="" ></div><strong>全选</strong></th>
						<th>缩略图</th>
						<th>运单编号</th>
						<th></th>
						</tr>
						<tr class="hide">
							<td class="num_all"><div class="re_check"><input type="checkbox" value="" /></div></td>
							<td><div class="table_img"><img alt="" width="182" height="182"></div></td>
							<td>
							</td>
							<td>
								<span class="hide"></span>
								<progress value="0" max="100" class="hide"></progress>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!--			<div class="height02"></div>-->
			<!--			<div class="sx_page">
							<div class="sx_page_p">共100条记录，每页10条，共99页</div>
							<div class="sx_page_list">
								<ul>
									<li><a href="#" class="sx_page_li"><span class="sx_page_ex"></span></a></li>
									<li><a href="#" class="sx_page_li li_on">1</a></li>
									<li><a href="#" class="sx_page_li">2</a></li>
									<li><a href="#" class="sx_page_li">3</a></li>
									<li>···</li>
									<li><a href="#" class="sx_page_li">4</a></li>
									<li><a href="#" class="sx_page_li">5</a></li>
									<li><a href="#" class="sx_page_li"><span class="sx_page_ne"></span></a></li>
								</ul>
							</div>
						</div>-->
		</div>
		<!--		<div class="height02"></div>-->
	</body>
</html>
