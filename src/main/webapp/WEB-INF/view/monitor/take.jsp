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
    
        function show(take_courier_no,takeDate){
        	$.dialog({lock: true,title:'收件明细',drag: true,width:1000,height:520,resize: false,max: false,content: 'url:${ctx}/monitor/take_detail?take_courier_no='+take_courier_no+'&takeDate='+takeDate+'&layout=no',close: function(){
        	 }});
        }
        
        function show1(take_courier_no,takeDate){
        	$.dialog({lock: true,title:'分配明细',drag: true,width:1000,height:520,resize: false,max: false,content: 'url:${ctx}/monitor/take_detail?sid=1&take_courier_no='+take_courier_no+'&takeDate='+takeDate+'&layout=no',close: function(){
        	 }});
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
             
                 	var data1 = tmjs.clist ;
     	         //var data1 = ${clist}; 
     	         // $.getJSON("${ctx}/lgc/calllist", function(data1) {
     	          	var availablesrcKey1 = [];
     	              $.each(data1, function(i, item) {
     	              	var inner_no = "" ;
     	              	if(item.inner_no){inner_no=item.inner_no+','}
     	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
     	              });
                          val1 = '';
                            $jqq("#courierNo").autocomplete(availablesrcKey1, {
                         		minChars: 0,
                         		max: 12,
                         		autoFill: true,
                         		mustMatch: false,
                         		matchContains: true,
                         		scrollHeight: 220,
                         		formatItem: function(data11, i, total) {
                         			return data11[0];
                         		}
                         	}).result(function(event, data, formatted) {
                       			 $("#courierNo").val(data[0]);
                       	});	 
                        }); 	   
            
        });
        
    </script>
</head>
<body>
<div class="search">
      <div class="soso">
        <div class="soso_left search_cont">
    <form:form id="trans" action="${ctx}/monitor/take" method="post" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value="monitor_take"/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
			 <li><span>收件时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:100px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span>~</span>
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:100px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
             <li>网点名称：<input type="text" value="${params['subNo']}" name="subNo" id="substationNo" style="width: 200px;"></input></li>
           <li>&#12288;快递员：<input type="text" value="${params['courierNo']}" name="courierNo" id="courierNo" style="width: 200px;"></input></li> 
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
            <th align="center">收件日期</th>
            <th align="center">网点编号</th>
            <th align="center">网点名称</th>
            <th align="center">快递员编号</th>
             <th align="center">快递员</th>
             <th align="center">收件票数</th>
             <th align="center">分配票数</th>
             <th align="center">平均收件时长（分钟）</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.takeDate}</td>
                <td>${item.sno}</td>
               <td>${item.substation_name}</td>
               <td>${item.cno}</td>
               <td>${item.real_name}</td>
               <td><a href="javascript:show('${item.take_courier_no}','${item.takeDate}');">${item.takeCount}</a></td>
               <td><a href="javascript:show1('${item.take_courier_no}','${item.takeDate}');">${item.asignCount}</a></td>
               <td><c:if test="${item.asignCount>0.01 }"><fmt:formatNumber type="number" value="${item.takeSum/item.asignCount}" maxFractionDigits="0"/></c:if>
               <c:if test="${item.asignCount<0.01 }">0</c:if>
               </td>
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