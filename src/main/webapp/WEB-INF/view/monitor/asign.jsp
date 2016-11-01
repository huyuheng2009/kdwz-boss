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
    
    function mexport() {
        var action = $("form:first").attr("action");
        $("form:first").attr("action", "${ctx}/export/service").submit();
        $("form:first").attr("action", action);
        loaddata_end() ;
    }
    
        function show(asing_date,asign_no,noType){
        	$.dialog({lock: true,title:'分配明细',drag: true,width:1000,height:520,resize: false,max: false,content: 'url:${ctx}/monitor/asign_detail?asing_date='+asing_date+'&asign_no='+asign_no+'&noType='+noType+'&layout=no',close: function(){
        	 }});
        }
        
        $(function () {
            //new TableSorter("t2");
            trHover('t2');
            
        });
        
    </script>
</head>
<body>
<div class="search">
      <div class="soso">
        <div class="soso_left search_cont">
    <form:form id="trans" action="${ctx}/monitor/asign" method="post" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value="monitor_asign"/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
			<li><label><input type="radio" name="noType" <c:out value="${params.noType eq '1'?'checked':'' }"/>  value="1">&nbsp;分配日期&nbsp;</input></label><label><input type="radio" name="noType" value="2" <c:out value="${params.noType eq '2'?'checked':'' }"/> >&nbsp;下单日期&nbsp;</input></label></li>
			 <li><span></span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:100px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span>~</span>
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:100px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
              <li>分配人：<input type="text" value="${params['asignName']}" name="asignName"></input></li>
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
 
  <div class="tableble_search scoll_search" id="scoll_search">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" value="导出"  onclick="mexport();"/> </div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <c:if test="${params.noType eq '1'}"><th align="center">分配日期</th></c:if>
            <c:if test="${params.noType eq '2'}"><th align="center">下单日期</th></c:if>
            <th align="center">分配人编号</th>
            <th align="center">分配人</th>
            <th align="center">分配数量</th>
             <th align="center">平均分配时长（分钟）</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <c:if test="${params.noType eq '1'}"><td>${item.asing_date}</td></c:if>
                <c:if test="${params.noType eq '2'}"><td>${item.order_date}</td></c:if>
                <td>${item.asign_no}</td>
               <td>${item.asign_name}</td>
               <td><c:if test="${params.noType eq '1'}">
                  <a href="javascript:show('${item.asing_date}','${item.asign_no}','${params.noType}');">${item.asignCount}</a>
                  </c:if>
                  <c:if test="${params.noType eq '2'}">
                  <a href="javascript:show('${item.order_date}','${item.asign_no}','${params.noType}');">${item.asignCount}</a>
                  </c:if>
                  </td>
               <td>${item.asignDuration}</td>
            </tr>
        </c:forEach>
           <tr>
                <td></td>
                <td></td>
                <td></td>
               <td></td>
               <td>合计：${total.asignCount}</td>
               <td>平均：${total.asignDuration}</td>
            </tr>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>