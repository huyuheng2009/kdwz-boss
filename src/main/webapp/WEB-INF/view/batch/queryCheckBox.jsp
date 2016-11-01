<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">		
        <title>${constants['backend_title']}</title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/checkboxArray.js"></script>
		<script>
			var dg = frameElement.api, W = dg.opener;
			var addrType = "${params.addrType}";
			var uid="${uid}";
			$(function() {
				$("#checkboxInput").click(function() {
					var t = $(this).attr("checked");
					$("input[name=checkBox]").each(function(index) {
						if (t === "checked") {
							$(this).attr("checked", t);
						} else {
							$(this).removeAttr("checked")
						}
						switchSelect(this);
					});
				});
			});

			function add() {
				if (ids.length <= 0) {
					alert("请选择要新增的项");
					return false;
				}
				$.ajax({
					type: 'post',
					url: "<%=request.getContextPath()%>/front/order/addMuchOrderAddr",
					data: {"addrId": ids.join(","), "uid": uid, "addrType": 2},
					dataType: 'json',
					success: function(data) {
						if (data.errorCode == "0") {							
							W.$("#frame").attr("src", "<%=request.getContextPath()%>/front/order/selectOrderAddr?userName=${user.userName}&uid="+uid);
							dg.close();
						} else {
							alert(data.message);
						}
					}
				});
			}
		</script>			
    </head>
    <body>
		<div class="table">
			<div class="ta_header">
				<form action="queryCheckBoxAddr" method="post">
					<div class="ta_h_search"><input class="put_cont" type="text" name="message" placeholder="请输入姓名/电话/地址" value="${param.message}"><input class="button_2" type="submit" value="查询"></div>
					<input type="hidden" name="userName" value="${params.userName}">
					<input type="hidden" name="addrType" value="${params.addrType}">
					<input type="hidden" name="uid" value="${uid}">
				</form>
			</div>
			<div class="ta_table" style="max-height: none;">
				<table class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th class="num_all">
					<div class="re_check"><input  type="checkbox" id="checkboxInput"><label  for="checkboxInput"></label></div><strong>序号</strong>
					</th>
					<th>姓名</th>
					<th>联系方式</th>
					<th>详细地址</th>					  
					</tr>
					<c:forEach items="${data.data}" var="d" varStatus="s">
						<tr>
							<td class="num_all"><div class="re_check"><input id="${d.id}" name="checkBox" type="checkbox" value="${t.name}" onclick="switchSelect(this)" id="${d.id}"><label  for="${d.id}"></label></div><strong>${s.count}</strong></td>
							<td>${d.name}</td>
							<td>${d.phone}</td>
							<td>${d.area}${d.addr}</td>						       
						</tr>
					</c:forEach>					
				</table>
			</div>
			<div class="height02"></div>
			<div class="page" style="border-radius:0;border-bottom: 1px solid #cdcdcd;">
				<zh:page pn="${data}"></zh:page>
			</div>
		</div>
		<div class=" contect_butt"><input class="button_2" type="button" value="确定" onclick="add();">

			</body>
			</html>
