<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
</head>
<body>
	<div class=" soso">
		<!--按钮上下结构-->
		<!--按钮左右结构-->
		<div class="soso_left">
			<form id="myform" action="/join/balance" method="post">
				<div class="soso_li">

					<div class="soso_input">
						<input type="hidden" name="serviceName" value="" />

						<div class="soso_b" style="margin-left: 10px;">网点</div>
						<div class="soso_input">
							<select id="substationNo" name="substationNo" class="time_bg"
								style="width: 180px">
								<option value="">--全部--</option>
								<c:forEach items="${substationList}" var="item"
									varStatus="status">
									<option value="${item.substation_no}"
										${params.substationNo eq item.substation_no?'selected':''}>${item.substation_name }</option>
								</c:forEach>
							</select>
						</div>

						<div class="soso_b" style="margin-left: 10px;">账户状态</div>
						<div class="soso_input">
							<select id="status" name="status" class="time_bg"
								style="width: 180px">
								<option value="" ${params.status eq ''?'selected':''}>--全部--</option>
								<option value="1" ${params.status eq '1'?'selected':''}>启用</option>
								<option value="0" ${params.status eq '0'?'selected':''}>停用</option>
							</select>
						</div>
					</div>
					<div class="soso_search_b">
						<div class="soso_button" style="margin: 0px 10px;">
							<input type="submit" value="查询" />
						</div>
					</div>
					<div class="soso_search_b" style="width: 100%"></div>
			</form>
		</div>
	</div>
	<div class="table">
		<div class="ta_table">
			<table class="ta_ta" width="100%" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<th width="120px;">账户状态</th>
					<th width="120px;">启用时间</th>
					<th width="120px;">网点编号</th>
					<th width="120px;">网点名称</th>
					<th width="120px;">当前余额</th>
					<th width="120px;">警戒金额</th>
					<th width="140px;">备注</th>

				</tr>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr>
						<td><c:if test="${item.status eq '1' }">启用</c:if> <c:if
								test="${item.status eq '0' }">停用</c:if></td>
						<td><fmt:formatDate	value="${item.start_time}" type="both" /></td>
						<td>${item.inner_no}</td>
						<td>${item.substation_name}</td>
						<td>${item.cur_balance}</td>
						<td id="warning${status.count}">${item.warning_balance}</td>
						<td id="note${status.count}">${item.note}</td>

					</tr>
				</c:forEach>

			</table>
		</div>
	</div>
	<!--                                         <div class="height02"></div> -->
	<div id="page">
		<pagebar:pagebar total="${list.pages}" current="${list.pageNum}"
			sum="${list.total}" />
	</div>
</body>
</html>
