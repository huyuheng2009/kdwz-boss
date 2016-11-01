<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${constants['backend_title']}</title>
        <jsp:include page="/WEB-INF/view/batch/head.jsp" />		
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/cityHP.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/checkboxArray.js"></script>
        <script>
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
            function print() {
                if (ids.length == 0) {
                    alert("请选择要打印的订单");
                    return false;
                }
                $.dialog({
                    width: '540px',
                    height: '400px',
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                            title: '打印',
                    content: "url:<%=request.getContextPath()%>/batchOrder/printOrder?orderNo=" + ids.join(","),
                    lock: true
                });
            }
            function del() {
                if (ids.length == 0) {
                    alert("请选择要删除的项");
                    return false;
                }
                        $.dialog.confirm('您真的确定要删除订单吗？', function() {
                    location.href = "<%=request.getContextPath()%>/batchOrder/delOrderAddr?ids=" + ids.join(",");
                }, function() {

                });
            }

            function cleanAll() {
                        $.dialog.confirm('您真的确定要清空吗？', function() {
                    location.href = "<%=request.getContextPath()%>/batchOrder/cleanOrderAddr?uid=${uid}";
                }, function() {

                });
            }

            function addAddr(path) {
                $.dialog({
                    width: '750px',
                    height: '570px',
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                    title: '编辑',
                    content: "url:" + path,
                    lock: true
                });
            }

            function singleDel(path) {
                $.dialog.confirm('您真的确定要删除吗？', function() {
                    location.href = path;
                }, function() {

                });
            }
        </script>
    </head>
    <body style="background: #fff;">
        <div class="table">
            <div class="ta_header">
                <form action="selectOrderAddr?layout=no" method="post">
                    <div class="ta_h_search"><input class="put_cont" name="commonName" type="text" placeholder="请输入姓名/电话/地址" value="${param.commonName}"><input class="button_2" type="submit" value="查询"></div>

                </form>
                <div class="ta_butt">
                    <a href="javascript:void(0);" onclick="del()" class="button_2">删除选中地址</a>
                    <a href="javascript:void(0);" onclick="cleanAll();" class="button_2">清空所有</a>
                    <a href="javascript:void(0);" class="button_2" onclick="addAddr('<%=request.getContextPath()%>/batchOrder/editOrderAddrPage?layout=no');">新增</a>					
                </div>
            </div>
            <div class="ta_table">
                <table class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <th class="num_all"><div class="re_check"><input  type="checkbox" id="checkboxInput"><label  for="checkboxInput"></label></div><strong>序号</strong></th>
                    <th>姓名</th>
                    <th>联系电话</th>
                    <th>收件地址</th>
                    <th>物品类型</th>
                    <th>物品重量</th>
                    <th>付款人</th>
                    <th>付款方式</th>
                    <th>保价金额</th>
                        <c:if test="${lgcNo eq '1131'}">
                        <th>运费</th>					
                        </c:if>
                    <th>运单号</th>
                    <th>备注</th>
                    <th>操作</th>        
                    </tr>
                    <c:forEach items="${list}" var="t" varStatus="s">
                        <tr>
                            <td class="num_all"><div class="re_check"><input id="${t.id}" name="checkBox" type="checkbox" value="${t.revName}" onclick="switchSelect(this)" id="${t.id}"><label  for="${t.id}"></label></div><strong>${s.count}</strong></td>
                            <td>${t.revName}</td>
                            <td>${t.revPhone}</td>
                            <td>${t.revArea}${t.revAddr}</td>
                            <td>${t.itemStatus}</td>
                            <td><c:if test="${t.itemWeight != null && t.itemWeight != ''}">${t.itemWeight}kg</c:if> </td>
                            <td><zh:option type="text" map="${PAYP}" value="${t.freightType}"></zh:option></td>
                            <td><zh:option type="text" map="${PAYTYPE}" value="${t.payType}"></zh:option></td>
                            <td>${t.goodValuation}<c:if test="${t.goodValuation != null && t.goodValuation != ''}">元</c:if></td>
                            <c:if test="${lgcNo eq '1131'}">
                                <td>${t.freight}</td>								
                            </c:if>
                            <td>${t.lgcOrderNo}</td>
                            <td>${t.orderNote}</td>
                            <td><a href="javascript:void(0);" onclick="addAddr('<%=request.getContextPath()%>/batchOrder/editOrderAddrPage?id=${t.id}&layout=no')">修改</a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="singleDel('<%=request.getContextPath()%>/batchOrder/delOrderAddr?ids=${t.id}')">删除</a></td>        
                        </tr>	
                    </c:forEach>
                </table>
                <c:if test="${empty list}">
                    <div class="table_p">您未添加填写收件人信息！</div>
                </c:if>
            </div>
        </div>
    </body>
</html>
