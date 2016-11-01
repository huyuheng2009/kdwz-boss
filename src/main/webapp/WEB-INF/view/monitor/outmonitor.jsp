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
    
        
        $(function () {
            //new TableSorter("t2");
            trHover('t2');
            
            jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
  		     // var slist = ${slist}; 
              var slist =  tmjs.slist ;
              // $.getJSON("${ctx}/lgc/calllist", function(data1) {
               	var slists = [];
                   $.each(slist, function(i, item) {
                   	var inner_no = "" ;
                   	if(item.inner_no){inner_no=item.inner_no+','}
                   	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                   });
                   val1 = '';
                     $jqq("#substationNo").autocomplete(slists, {
                  		minChars: 0,
                  		max: 12,
                  		autoFill: true,
                  		mustMatch: false,
                  		matchContains: true,
                  		scrollHeight: 220,
                  		formatItem: function(slists, i, total) {
                  			return slists[0];
                  		}
                  	}).result(function(event, data, formatted) {
                   		if(data[0].indexOf(')')>-1){
                  			 $("#substationNo").val(data[0]) ;
            			       } 
                  	});	
             
            }); 	   
            
        });
        
    </script>
</head>
<body>
<div class="search">
      <div class="soso">
        <div class="soso_left search_cont">
    <form:form id="trans" action="${ctx}/monitor/outmonitor" method="post" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value="monitor_outmonitor"/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
        <textarea  cols="18" rows="4"   maxlength="1400" value="${orderNo}" placeholder="请输入运单号" name="orderNo"  class="order_area">${orderNo}</textarea> 							
			 <li><span>扫描时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:100px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span>~</span>
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:100px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
             <li>扫描网点：<input type="text" value="${params['cur_no']}" name="cur_no" id="substationNo" style="width: 300px;margin-right: 100px;"></input></li>
           <li>&#12288;操作人：<input type="text" placeholder=""		value="${params['opname']}" name="opname" id="opname" style="width: 226px;"> </input> </li>
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
            <th align="center"> 扫描时间</th>
            <th align="center">运单号</th>
            <th align="center">网点编号</th>
            <th align="center">网点名称</th>
             <th align="center">操作人</th>
             <th align="center">下一站编号</th>
             <th align="center">下一站名称</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.order_time}</td>
                <td>${item.lgc_order_no}</td>
               <td>${item.curNo}</td>
               <td>${item.curName}</td>
                <td>${item.opname}</td>
               <td>${item.nextNo}</td>
                <td>${item.nextName}</td>
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