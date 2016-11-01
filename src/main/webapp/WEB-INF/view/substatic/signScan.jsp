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
</style>
    <script type="text/javascript">
         
    function track(orderNo){
    	$.dialog({lock: true,title:'物流动态',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/order/track?orderNo='+orderNo+'&layout=no',close: function(){
    	 }});
    }
    
        $(function () {
            trHover('t2');
            new TableSorter("t2");
            $('#reload_btn').click(function(){
       		 location.reload()
       	});
  		  jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {	 
	    var data1 = tmjs.clist;
             	var availablesrcKey1 = [];
                 $.each(data1, function(i, item) {
                 	var inner_no = "" ;
                 	if(item.inner_no){inner_no=item.inner_no+','}
                 	availablesrcKey1[i]=inner_no+'('+item.real_name.replace(/\ /g,"")+')';
                 });
                 val1 = '';
                   $jqq("#courier").autocomplete(availablesrcKey1, {
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
                		if(data[0].indexOf(')')>-1){
              			 $("#courier").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
        			       } 
              	});	
  		  
                   var slist =  tmjs.slist ;
	                // $.getJSON("${ctx}/lgc/calllist", function(data1) {
	                 	var slists = [];
	                     $.each(slist, function(i, item) {
	                     	var inner_no = "" ;
	                     	if(item.inner_no){inner_no=item.inner_no}
	                     	slists[i]=inner_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
	                     });
	                     val1 = '';
	                       $jqq("#substationName").autocomplete(slists, {
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
	                    			 $("#substationName").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
	              			       } 
	                    	});	
  		  
               });     //done              
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('signScan');
        	   $("form:first").attr("action","${ctx}/export/service").submit();
        	   $("form:first").attr("action",action);
        	   loaddata_end() ;
        	});
            
  });
        
        

    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/substatic/signScan" method="get" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value=""/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
			      <li><span>扫描时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
             
             <li class="soso_li">扫描网点：<input type="text" value="${params['substationName']}" id="substationName" name="substationName"></input></li>
             <li class="search_cont_input">&#12288;派件员：<input type="text" value="${params['courier']}" name="courier" id="courier"></input></li>

           <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="signed" value="Y" <c:out value="${params.signed eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">只显示未签收</span>
          </label>
           </li> 
           
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
	     <shiro:hasPermission name="DEFAULT">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
	        </shiro:hasPermission>
	       
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	
<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >序号</th>
            <th align="center" >扫描时间</th>
            <th align="center" >运单号码</th>
            <th align="center" >扫描网点</th> 
            <th align="center" >扫描类型</th> 
            <th align="center" >派件员</th>
            <th align="center" >签收时间</th>
            <th align="center" >签收人</th>
            <th align="center" >付款人</th>  
            <th  align="center" >付款方式</th>
            <th  align="center" >运费</th>
            <th  align="center" >代收款</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''}">
             <td align="center">${status.count}</td>
           <td><fmt:formatDate value="${item.order_time}" type="both"/></td>
            <td><a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a></td>    
            <td>${item.substation_name}</td> 
             <td>派件</td>
            <td>${item.real_name}</td>
             <td><c:if test="${item.status eq 3 }"><fmt:formatDate value="${item.send_order_time}" type="both"/></c:if></td>
             <td>${item.sign_name}</td>
             <td><c:if test="${item.freight_type eq 2 }">收方付</c:if><c:if test="${item.freight_type eq 1 }">寄方付</c:if></td> 
             <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td>  
             <td>${item.freight}</td>
             <td>${item.good_price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${orderList.pages}"
                     current="${orderList.pageNum}" sum="${orderList.total}"/>
</div>
</body>

</html>