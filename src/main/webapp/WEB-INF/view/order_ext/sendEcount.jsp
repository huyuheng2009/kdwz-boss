<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/table_new.css"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>

$(function(){
	var api = frameElement.api, W = api.opener;
	    api.lock();
	    var style = W.getStyle() ;
	    if(style==1){
	    	$("table").addClass("ta_ta");
		$("table th").addClass("num_all");
		$(".tableble_search").addClass("soso");
	    }
}); 
</script>
</head>
<body>

<div class="tbdata">
    <table width="98%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >票数</th>
            <th align="center" >付款人</th>
            <th align="center" >付款方式</th> 
            <th  align="center" >运费</th>
            <th  align="center" >运费合计</th>
        </tr>
        </thead>
        <tbody>
                <c:set var="sum_piao" value="0"></c:set>
                <c:set var="sum_cfreight" value="0"></c:set>

				<c:set var="sum_scash" value="0"></c:set>
				<c:set var="sum_smonth" value="0"></c:set>
				<c:set var="sum_rcash" value="0"></c:set>
				<c:set var="sum_rmonth" value="0"></c:set>
		<c:forEach items="${orderList}" var="item" varStatus="status">
              <c:set var="sum_piao" value="${sum_piao+item.piao}"></c:set>
               <c:set var="sum_cfreight" value="${sum_cfreight+item.cfreight}"></c:set>
                <c:if test="${item.freight_type ne 2}">
                    <c:if test="${item.pay_type eq 'CASH'}">
                            <c:set var="sum_scash" value="${sum_scash+item.cfreight}"></c:set>
                    </c:if>
                    <c:if test="${item.pay_type eq 'MONTH'}">
                            <c:set var="sum_smonth" value="${sum_smonth+item.cfreight}"></c:set>
                     </c:if>
                </c:if>
               <c:if test="${item.freight_type eq 2}">
                    <c:if test="${item.pay_type eq 'CASH'}">
                            <c:set var="sum_rcash" value="${sum_rcash+item.cfreight}"></c:set>
                    </c:if>
                    <c:if test="${item.pay_type eq 'MONTH'}">
                            <c:set var="sum_rmonth" value="${sum_rmonth+item.cfreight}"></c:set>
                     </c:if>
                </c:if>
          <tr>
            <td>${item.piao}</td>
            <td><c:if test="${item.freight_type eq 2 }">收方付</c:if><c:if test="${item.freight_type ne 2 }">寄方付</c:if></td>       
            <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td>
            <td>${item.freight}</td>
            <td>${item.cfreight}</td>
            </tr>
        </c:forEach>
         <tr style="background: #f5f5f5;border: none;" >
        <td style="background: #f5f5f5;border: none;color: red;font-size: 14px;"  colspan="5">
          寄付现金：${sum_scash }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;寄付月结：${sum_smonth }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到付现金：${sum_rcash }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到付月结：${sum_rmonth }
        </td>
            </tr>
          <tr style="background: #f5f5f5;border: none;" >
                   <td style="background: #f5f5f5;border: none;"> 总票数：${sum_piao }</td>
                 <td style="background: #f5f5f5;border: none;"  colspan="3"></td>
                   <td style="background: #f5f5f5;border: none;"> 总运费：${sum_cfreight }</td>
            </tr>
        </tbody>
    </table>
</div>
</body>

</html>