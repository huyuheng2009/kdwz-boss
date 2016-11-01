<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>

</style>
    <script type="text/javascript">
        $(function () {
            trHover('t2');
            new TableSorter("t2");
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
            
     });
        
        

    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/order/ehistory" method="get">
        <ul>
         <textarea  cols="18" rows="4"  maxlength="75" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" >${params['orderNo']}</textarea> 
			      <li><span>修改时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
            <li class="search_cont_input">修改人：<input type="text" value="${params['operator']}" name="operator"></input></li>
            <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat"
                       type="reset" value="重置"/>
            </li>
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" value="刷新" id="reload_btn"/> </div>
	   </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >序号</th>
            <th  align="center" >运单号</th> 
            <th  align="center" >取件员</th>
            <th  align="center" >寄件时间</th>
            <th  align="center" >派件员</th>
            <th  align="center" >签收时间</th>
            <th  align="center" >快递运费</th>
            <th  align="center" >保价手续费</th>
            <th  align="center" >代收款</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >月结号</th>
            <th  align="center" >贷款号</th>
            <th  align="center" >修改人</th>
            <th  align="center" >修改时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <td align="center">${status.count}</td>
            <td>${item.lgc_order_no}</td>  
             
            <td>${item.takeName}</td>
            <td><fmt:formatDate value="${item.take_order_time}" type="both"/></td>
            <td>${item.sendName}</td>
            <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>
            <td>${item.si_freight}</td>
            <td>${item.si_vpay}</td>
            <td>${item.si_good_price}</td>
            <td><c:if test="${item.si_freight_type eq 2 }">收方付</c:if><c:if test="${item.si_freight_type eq 1 }">寄方付</c:if></td> 
           <td><u:dict name="PAY_TYPE" key="${item.si_pay_type}" /></td>
           <td>${item.si_month_settle_no }</td>
           <td>${item.si_cod_name}</td>
            <td>${item.operator}</td>
            <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>