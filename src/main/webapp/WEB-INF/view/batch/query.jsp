<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/tag.jsp"%>
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
			function selectValue(a, b, c, d) {
				if (addrType === "1") {
					W.document.getElementById("sendName").value = a;
					W.document.getElementById("sendPhone").value = b;
					W.document.getElementById("sendArea").value = c;
					W.document.getElementById("sendAddr").value = d;
				} else {
					W.document.getElementById("revName").value = a;
					W.document.getElementById("revPhone").value = b;
					W.document.getElementById("revArea").value = c;
					W.document.getElementById("revAddr").value = d;
				}
				dg.close();
			}
		</script>			
    </head>
    <body>
		<div class="table" style="width:800px;">
			<div class="ta_header">
				<form action="queryRadiaoAddr?layout=no" method="post">
					<div class="ta_h_search"><input class="put_cont" type="text" name="message" placeholder="请输入姓名/电话/地址" value="${param.message}"><input class="button_2" type="submit" value="查询"></div>
					<input type="hidden" name="userName" value="${params.userName}">
					<input type="hidden" name="addrType" value="${params.addrType}">
					<input type="hidden" name="uid" value="${uid}">
				</form>
			</div>
			<div class="ta_table" style="max-height: none;">
				<table class="ta_ta"  width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th class="num_all">
					<div class="re_check"></div><strong>序号</strong>
					</th>
					<th width="120">姓名</th>
					<th width="120">联系方式</th>
					<th width="500">详细地址</th>					  
					</tr>
					<c:forEach items="${data.list}" var="d" varStatus="s">
						<tr>
							<td class="num_all"><div class="re_check"><input name="2" type="radio" value="" id="radio" onclick="selectValue('${d.name}', '${d.phone}', '${d.area}', '${d.addr}')"></div><strong>${s.count}</strong></td>
							<td>${d.name}</td>
							<td>${d.phone}</td>
							<td>${d.area}${d.addr}</td>						       
						</tr>
					</c:forEach>					
				</table>
			</div>
				<div id="page">
	    	<pagebar:pagebar total="${data.pages}"
			current="${data.pageNum}" sum="${data.total}" />
	     </div>
    </body>
</html>
