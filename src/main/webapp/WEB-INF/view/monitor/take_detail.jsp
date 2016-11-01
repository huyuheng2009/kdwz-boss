<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:200px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin:-28px 0 0 -4px;display: none;}
.scoll_search_fixed{position:fixed;margin:0 auto;top:0;width: 1180px;z-index:99;}
.tableble_table tr.el td{
	background: #8cf6ff;
}
</style>
    <script type="text/javascript">
    
    function dexport() {
        var action = $("form:first").attr("action");
        $("form:first").attr("action", "${ctx}/export/service").submit();
        $("form:first").attr("action", action);
    }
        
        $(function () {
            //new TableSorter("t2");
            trHover('t2');
            
        });
        
    </script>
</head>
<body>

 <form:form id="trans" action="${ctx}/export/service" method="post">
  <input type="hidden" name="serviceName" value="monitor_take_detail"/>
      <input type="hidden" name="asing_date" value="${params['asing_date']}"/>
      <input type="hidden" name="asign_no" value="${params['asign_no']}"/>
       <input type="hidden" name="sid" value="${params['sid']}"/>
  </form:form>
<div class="search">
  <div class="tableble_search scoll_search" id="scoll_search">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" value="导出"  onclick="dexport();"/> </div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">运单号</th>
            <th align="center">分配时间</th>
            <th align="center">收件时间</th>
             <th align="center">快递员编号</th>
              <th align="center">快递员</th>
               <th align="center">收件时长（分钟）</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.lgc_order_no}</td>
                <td>${item.asign_time}</td>
                <td>${item.take_order_time}</td>
                <td>${item.inner_no}</td>
               <td>${item.real_name}</td>
               <td>${item.takeSum}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>