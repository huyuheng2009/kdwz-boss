<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/*********地址展开样式**********/
.out_address {
	position: absolute;
	width: 200px;
	padding: 6px;
	background: #fff;
	border: 1px solid #cdcdcd;
	margin: -28px 0 0 -4px;
	display: none;
}
</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script>
			var $jqq = jQuery.noConflict(true);
		</script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
			//获取时间段，单位、天
			function getToday(date, less) {
				var yesterday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24 * less;
				var yesterday = new Date();
				yesterday.setTime(yesterday_milliseconds);

				var strYear = yesterday.getFullYear();
				var strDay = yesterday.getDate();
				var strMonth = yesterday.getMonth() + 1;
				if (strMonth < 10) {
					strMonth = "0" + strMonth;
				}
				datastr = strYear + "-" + strMonth + "-" + strDay;
				return datastr;
			}
		
			$(function() {
				$('#month').click(function() {
					var now = new Date();
					var begin = getToday(now, 30);
					var end = getToday(now, 0);
					$('#dateBegin').val(begin);
					$('#dateEnd').val(end);
				});
			});
			$(function() {
				$('#threeMonth').click(function() {
					var now = new Date();
					var begin = getToday(now, 90);
					var end = getToday(now, 0);
					$('#dateBegin').val(begin);
					$('#dateEnd').val(end);
				});
			});
		
	
			$(function() {
				
		           
	        	$('#derive_btn').click(function(){
	        		var action= $("form:first").attr("action");
// 	               $("form:first input[name=serviceName]").val('codMonthCount');
	        	   $("form:first").attr("action","${ctx}/join/export2").submit();
	        	   $("form:first").attr("action",action);
	        	});
				
							
				trHover('t2');
				$('#reload_btn').click(function() {
					location.reload();
				});
			});

		
	    
			    
			    
			    
		</script>
<style type="text/css">
</style>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/join/acount" method="get">
					<input type="hidden" name="serviceName" value="" />
					<input type="hidden" name="ff" value="${params['ff']}" />

					<ul>



						<li id="createTime"><span>产生时间：</span> <input id="dateBegin"
							onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"
							value="${params['beginTime']}"
							onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"
							onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'dateBegin\')}', maxDate: '%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"
							value="${params['endTime']}"
							onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /></li>

						<li>网点名称： <select id="substationNo" name="substationNo"
							class="time_bg" style="width: 180px;">
								<option value="">--全部--</option>
								<c:forEach items="${substationList}" var="item"
									varStatus="status">
									<option value="${item.substation_no}"
										${params.substationNo eq item.substation_no?'selected':''}>${item.substation_name }</option>
								</c:forEach>
						</select>
						</li>
						<li>操作员：<input type="text" id="operater" placeholder="操作员"
							value="${params['operater']}" name="operater"></input>
						</li>
						<li><input class="button input_text  medium blue_flat"
							type="submit" value="查询" /></li>
						<li><input class="button input_text  medium blue_flat"
							type="reset" value="重置" /></li>
					</ul>
				</form:form>
			</div>
			<!-- search_cont end  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="button"
						value="导出" id="derive_btn" />
				</div>
			</div>
		</div>
		<!-- tableble_search end  -->
	</div>


	<div class="tbdata">

		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="30">序号</th>
					<th>产生时间</th>
					<th>网点编号</th>
					<th>网点名称</th>
					<th>充值</th>
					<th>冲账</th>
					<th>中转费</th>
					<th>派件费</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td>${status.count}</td>
						<td>${item.createTime}</td>
						<td>${item.innerNo}</td>
						<td>${item.substationName}</td>
						<td>${item.chongzhi}</td>
						<td>${item.chongzhang}</td>
						<td>${item.zhongzhuan}</td>
						<td>${item.paijian}</td>
					</tr>
				</c:forEach>
				<tr>
					<td>总计</td>
					<td></td>
					<td></td>
					<td></td>
					<td>${map.allchongzhi}</td>
					<td>${map.allchongzhang}</td>
					<td>${map.allzhongzhuan}</td>
					<td>${map.allpaijian}</td>
				</tr>
			</tbody>

		</table>

	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}" current="${list.pageNum}"
			sum="${list.total}" />
	</div>
</body>

</html>