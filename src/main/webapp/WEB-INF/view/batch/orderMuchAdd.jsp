<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${constants['backend_title']}</title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/css/validationEngine.jquery.css" type="text/css">
		<%--<jsp:include page="/WEB-INF/view/include/select_city.jsp" />--%>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.6.3.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine.js"></script>
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
		
		   $(function () {      
		      	$('#derive_btn').click(function(){
		      		var action= $("form:first").attr("action");
		              $("form:first input[name=serviceName]").val('dayCountExport');
		      	   $("form:first").attr("action","${ctx}/substatic/export").submit();
		      	   $("form:first").attr("action",action);
		      	});        
		      });
		
		
		
		
		
			$(function() {
				
			
				  

				$("#down_addrSend").hide();

				
				//$("#down_addrRev").hide();

//				SelectCity($("input[name='sendArea']"));
				jQuery("#myform").validationEngine();
			 var u = new Upload($("#uploadXLS"), [".xls"], {params: {}});
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

		 	function saveAddr() {
		 		var flat = true;
		 		var error = "";
		 		if($('input[name=sendName]').val()=="" || $('input[name=sendName]').val().length<1){
		 			error = "请输入姓名";
		 			flat=false;
		 		}	
		 		if($('input[name=sendPhone]').val()=="" || $('input[name=sendPhone]').val().length<1){
		 			error = "请输入联系电话";
		 			flat=false;
		 		}	
		 		if($('input[name=sendArea]').val()=="" || $('input[name=sendArea]').val().length<1){
		 			error = "请输入大区地址";
		 			flat=false;
		 		}	
		 		if($('input[name=sendAddr]').val()=="" || $('input[name=sendAddr]').val().length<1){
		 			error = "请输入详细地址";
		 			flat=false;
		 		}	
		
		 		if(!flat){
		 			alert(error);
		 			return;
		 		}else{
		 			
	 		$.ajax({
	 			url : '/batch/saveAddr',
	 			type:'get',
	 			data : {
	 				'sendName':$('input[name=sendName]').val(),
	 				'sendPhone':$('input[name=sendPhone]').val(),
	 				'sendArea':$('input[name=sendArea]').val(),
	 				'sendAddr':$('input[name=sendAddr]').val(), 					
	 			},
	 			success : function(msg) {
	 			alert('保存成功');
	 			}
	 		});
		 		}	  	}

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
	      		var action= $("form:first").attr("action");
	             $("form:first input[name=serviceName]").val('batchExport');
	      	   $("form:first").attr("action","${ctx}/batch/export").submit();
	      	   $("form:first").attr("action",action);
			}       
			
		

			function updownExcel() {
				location.href = "<%=request.getContextPath()%>/template/batchOrder.xls";
			}
			
			function CheckExcel() {
				  var mime = document.getElementById('Excelfile').value;
				  mime = mime.toLowerCase().substr(mime.lastIndexOf("."));
				  if (!(mime == ".xls")) {
				   alert("请导入正确的EXCEL文件，仅支持xls格式!");
				   return false;
				  }
				 }
			
			   function mchange(e){
		        	var mode = $(e).val();		        		        
		        	if("A"==mode){  	        	
		        		$('#firstDiv').hide();
		        		$('#firstDiv1').hide();		        		
		        	}else{	        	
		        		$('#firstDiv').show();
		        		$('#firstDiv1').show();
		        	}
		        } 

		</script>
	</head>
	<body>
		<form id="myform" action="addOrderMuch" method="post">
			<div class="gdt">
				<div class="cont_li">
					<div class="cont_tit">		
					<div id="firstDiv">			
						<strong>&nbsp;&nbsp;寄件人信息</strong>
						</div>
						
						<div style="float: left;height: 30px;margin: 15px 40px;">
						<div class="reser_li">
						<div class="re_check"><input type="radio" checked="checked" name="check" value="A" onchange="mchange(this)"/> </div>
						<div class="input_name02">表格导入</div>
					</div>
					<div class="reser_li">
						<div class="re_check"><input type="radio" checked="checked" name="check" value="B" onchange="mchange(this)"/> </div>
						<div class="input_name02">地址簿选择</div>
					</div>  
					</div>
						
					</div>
							
					<div class="cont_cont"  id="firstDiv1">
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
								<input type="hidden" id="ids" value=""/>
								<input type="hidden" name="serviceName" value="" />
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
							<div class="input_wri"><input type="text" id="sendAddr" name="sendAddr" placeholder="请填写详细地址（精确到门牌号）" class="validate[required,maxSize[50]]" value="${params.sendAddr}" data-errormessage-value-missing="* 请填写详细地址（精确到门牌号）"></div>        
					      <a href="javascript:void(0);" onclick="saveAddr();" class="button_2">保存到地址薄</a>
						</div>     
						
							      
					
					</div>
					<input type="hidden" id="ids" name="ids" />
			
				</div>
				<div class="cont_li square">
					<div class="cont_tit">
		
						<strong>&nbsp;&nbsp;收件人信息</strong> <strong style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;${msg}</strong>
						<div class="ta_butt">

							<a id="importExcel" href="javascript:void(0);" class="button_2">从Excel导入</a>
						
							<a href="javascript:void(0)" onclick="updownExcel();" class="button_2">导入模版下载</a>
	
							<a href="javascript:void(0);" onclick="importExcel();" class="button_2">导出到Excel</a>      
						</div>
					</div>
					<div id="uploadXLS">
						<input type="file" />
						<div class="a">
							上传　<span class="a1">标准表格.xls</span>　<span class="a2"></span>
						</div>
						<div class="p"><div class="p2"></div></div>
					</div>
					<div class="cont_table">
						<iframe id="frame" class="ta_iframe" name="frame" frameborder=0 scrolling=auto src="<%=request.getContextPath()%>/batch/selectOrderAddr?layout=no"></iframe>
					</div>
				</div>  				
				<div class="cont_button" style="margin: 20px 0 ;"><input class="button" type="submit" value="提交"></div>
			</div>
		</form>
    </body>
</html>
