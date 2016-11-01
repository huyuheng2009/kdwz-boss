<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script>
			var $jqq = jQuery.noConflict(true);
        </script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script>
			function importSubstation() {
				var action = $("form:first").attr("action");
				$("form:first input[name=serviceName]").val('tcRecord');
				$("form:first").attr("action", "${ctx}/salary/export").submit();
				$("form:first").attr("action", action);
			}
			$(function() {

//                var courierList = ${courierList};
//                var cres = [];
//                $.each(courierList, function(i, item) {
//                    cres[i] = item.courier_no.replace(/\ /g, "") + '(' + item.real_name.replace(/\ /g, "") + ')';
//                });
//
//                $jqq("#courierNo").autocomplete(cres, {
//                    minChars: 0,
//                    max: 12,
//                    autoFill: true,
//                    mustMatch: false,
//                    matchContains: true,
//                    scrollHeight: 220,
//                    formatItem: function(data, i, total) {
//                        return data[0];
//                    }
//                }).result(function(event, data, formatted) {
//                    if (data[0].indexOf(')') > -1) {
//                        $("#courierNo").val(data[0].substring(0, data[0].indexOf('(')));
//                    }
//                });

				var data1 = ${courierList};
				// $.getJSON("${ctx}/lgc/calllist", function(data1) {
				var availablesrcKey1 = [];
				$.each(data1, function(i, item) {
					var inner_no = "";
					if (item.inner_no) {
						inner_no = item.inner_no + ','
					}
					availablesrcKey1[i] = inner_no + '(' + item.real_name.replace(/\ /g, "") + ')';
				});
				val1 = '';
				$jqq("#courierNo").autocomplete(availablesrcKey1, {
					minChars: 0,
					max: 12,
					autoFill: true,
					mustMatch: false,
					matchContains: true,
					scrollHeight: 220,
					formatItem: function(data11, i, total) {
						return data11[0];
					}
				}).result(function(event, data, formatted) {
					if (data[0].indexOf(')') > -1) {
						$("#courierNo").val(data[0].substring(data[0].indexOf('(') + 1, data[0].indexOf(')')));
					}
				});
			})
			
			function courierNo(){
				var orderBy = $('input[name=orderBy]').val();
				if('1'==orderBy){
					$('input[name=orderBy]').val('2');
				}else{
					$('input[name=orderBy]').val('1');
				}
				$("form:first").submit();
			}
			function courierName(){
				var orderBy = $('input[name=orderBy]').val();
				if('3'==orderBy){
					$('input[name=orderBy]').val('4');
				}else{
					$('input[name=orderBy]').val('3');
				}
				$("form:first").submit();
			}
		
        </script>
</head>
<body>
	<div class=" soso">
		<!--按钮上下结构-->
		<!--按钮左右结构-->
		<div class="soso_left">
			<form id="myform" action="crlist" method="post">
				<div class="soso_li">
					<input type="hidden" name="serviceName" value="" />
				<input type="hidden" name="orderBy" value="${params.orderBy}" />
					<div class="soso_b" style="margin-left: 10px;">网点</div>
					<div class="soso_input">
						<select id="substationNo" name="substationNo" class="time_bg"
							style="width: 180px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }"
									<c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="soso_b" style="margin-left: 10px;">快递员</div>
					<div class="soso_input">
						<input class="time_bg" type="text" id="courierNo" name="courierNo"
							value="${params['courierNo']}">
					</div>

				</div>
				<div class="soso_search_b">
					<!--                                    <div class="soso_button"><button>7天</button></div>
                                                                            <div class="soso_button"><button>8天</button></div>-->
					<div class="soso_button">
						<input type="submit" value="查询">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="table">
		<div class="ta_header">
			<div class="ta_butt">
				<a href="javascript:void(0);" id="importSubstation" class="button_2"
					onclick="importSubstation();">导出</a>
			</div>
		</div>
		<div class="ta_table">
			<table class="ta_ta" width="100%" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<th rowspan="2" width="40">序号</th>
					<th rowspan="2" width="50"><a onclick="courierName()">网点名称</a></th>
					<th rowspan="2" width="60"><a onclick="courierNo()">快递员编号</a></th>
					<th rowspan="2"  width="60">快递员名称</th>
					<th rowspan="2" width="50">联系电话</th>
					<th colspan="3" width="80;" >同城收件</th>
					<th colspan="3" width="80;" >同城派件</th>
					<th colspan="3" width="80;" >外件</th>
					<th rowspan="2"	>登记人</th>		
						<th rowspan="2"	>登记时间</th>		
				</tr>
				<tr>
				<th>金额</th>
				<th>重量</th>
				<th>件数</th>
				<th>金额</th>
				<th>重量</th>
				<th>件数</th><th>金额</th>
				<th>重量</th>
				<th>件数</th>
				</tr>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.substationName}</td>
						<td>${item.innerNo}</td>
						<td>${item.realName}</td>
						<td>${item.phone}</td>
						<td>${item.take_tcf}</td>
						<td><fmt:formatNumber value="${item.take_tcw}" pattern="0.00"/> </td>
						<td><fmt:formatNumber value="${item.take_tcc}" pattern="0.00"/> </td>
						<td>${item.send_tcf}</td>
						<td><fmt:formatNumber value="${item.send_tcw}" pattern="0.00"/></td>
						<td><fmt:formatNumber value="${item.send_tcc}" pattern="0.00"/></td>
						<td>${item.for_tcf}</td>
						<td><fmt:formatNumber value="${item.for_tcw}"  pattern="0.00"/></td>
						<td><fmt:formatNumber value="${item.for_tcc}"  pattern="0.00"/></td>						
						<td>${item.operator}</td>
						<td>${item.create_time}</td>				
					</tr>
				</c:forEach>
		</table>
		</div>
	</div>
	<div class="height02"></div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}" current="${list.pageNum}"
			sum="${list.total}" />
	</div>
</body>
</html>
