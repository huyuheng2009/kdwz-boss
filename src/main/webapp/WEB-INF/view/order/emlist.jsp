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
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function track(orderNo){
        	$.dialog({lock: true,title:'物流动态',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/order/track?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }

        $(function () {
        	trHover1('t2');
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
            
            var data1 = ${clist}; 
            // $.getJSON("${ctx}/lgc/calllist", function(data1) {
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
            
        });
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/order/mamager" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
        <textarea  cols="18" rows="4"  maxlength="75" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" >${params['orderNo']}</textarea> 
         	      <li><span>取件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			    <li><span>派件时间：</span>
			 	<input id="dateBegin1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="sendTimeBegin" value="${params['sendTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="sendTimeEnd" value="${params['sendTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			  <li>月结号：<input type="text" value="${params['monthSettleNo']}" name="monthSettleNo"></input></li>
			<li>代收客户号：<input type="text" value="${params['codName']}" name="codName"></input></li>
			 <li>会员号：<input type="text" value="${params['disUserNo']}" name="disUserNo"></input></li>
            <li>收件员/派件员：<input type="text" value="${params['realName']}" name="realName" id="courier"></input></li>
            <li><input class="button input_text  medium blue_flat"
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat" 
                       type="reset" value="重置"/>
            </li>
        </ul>
    </form:form>
   </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
  	 <div class="operator">
	    <div class="search_new">   <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>
	   <!--  <div class="search_new">   <input style="width: 100px;" class="button input_text  medium blue_flat" type="submit" style="float: right;" value="批量审核" id="batch_examine"/>  </div> -->
	    </div>
  </div>   <!-- tableble_search end  -->    
</div>


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >序号</th>
            <th  align="center" >运单号</th>
            <th  align="center" >收件员</th>
            <th  align="center" >派件员</th>
            <th  align="center" >运费合计</th>
            <th  align="center" >代收款</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >月结账号</th>
            <th  align="center" >代收客户号</th>
            <th  align="center" >会员号</th>
            <th  align="center" >取件时间</th>
            <th  align="center" width="10%" >操作</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
             <tr class="over_${status.count}" over="over_${status.count}">
                <td  align="center">${status.count}</td>
                <td> <a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a></td>
                <td><u:dict name="C" key="${item.take_courier_no}" /></td>
                <td><u:dict name="C" key="${item.send_courier_no}" /></td>
                <td>${item.freight+item.vpay+item.dpay}</td>
                <td>${item.good_price}</td>
                <td><u:dict name="FREIGHT_TYPE" key="${item.freight_type}" /></td>
                <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td>
                <td>${item.month_settle_no }</td>
                <td>${item.cod_name }</td>
                <td>${item.dis_user_no}</td>
                <td><fmt:formatDate value="${item.take_order_time}" type="both"/></td>
                <td align="center"><a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
                </td> 
              </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
<pagebar:pagebar total="${list.pages}"  current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>