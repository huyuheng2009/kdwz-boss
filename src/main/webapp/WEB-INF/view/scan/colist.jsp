<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:820,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }
        
        function send(orderNo){
        	$.dialog({lock: true,title:'发往下一站',drag: true,width:600,height:250,resize: false,max: false,content: 'url:${ctx}/scan/ssend_page?orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }
        
        function rev(orderNo){
        	 if(confirm('确认快件已经到达分站？')){
           		 $.ajax({url:'/scan/srev',data:{'orderNo':orderNo},success:function(msg){
           			 if(msg==1){
           				 alert('操作成功');
           				 location.reload();
           			 }else{
           				 alert(msg);
           			 }
           		 }});
           	 }
        }
        
       
    
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload()
       	});
           
            
            
            jQuery.ajax({
    		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
    		      dataType: "script",
    		      cache: true
    		}).done(function() {
    			console.log(tmjs.clist) ;	
    			var data1 = tmjs.clist ;
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
    	             		if(data[0].indexOf(')')>-1){
    	           			 $("#courierNo").val(data[0].substring(0,data[0].indexOf('('))) ;
    	     			    } 
    	           	     });	          	  
    	                 	  
    		});		  //动态加载
      		
      		
            
            
            
        });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/scan/colist" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>运单号：<input type="text" value="${params['lgcOrderNo']}" placeholder="物流单号" name="lgcOrderNo"></input></li>
            <li>快递员：<input type="text" value="${params['courierNo']}" placeholder="快递员编号" name="courierNo" id="courierNo"></input></li>
            <%--
             <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d\'}'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
             --%>
            
			    <li><span>签收时间：</span>
			 	<input id="dateBegin1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="sendTimeBegin" value="${params['sendTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="sendTimeEnd" value="${params['sendTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			 <li>快件状态：<select name="orderStatus" style="width: 110px">
                <option value="">全部</option>
                <option value="1" ${params['orderStatus'] eq '1'?'selected':''}>待派件</option>
                <option value="2" ${params['orderStatus'] eq '2'?'selected':''}>已派件</option>
             </select></li>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
            <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li>
        </ul>
    </form:form>
     </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
   <div class="operator">
	   <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
	    </div>
   </div>   <!-- tableble_search end  --> 
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >序号</th>
            <th  align="center" >运单号</th>
            <th  align="center" >快递员编号</th>
            <th  align="center" >快递员名称</th>
            <th  align="center" >收件人地址</th>
            <%--
            
            <th  align="center" >寄件时间</th>
             --%>
            <th  align="center" >签收时间</th>
            <th  align="center" >联系电话</th>
            <th  align="center" >运单状态</th>
            <th  align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
               <td>${item.lgc_order_no}</td>
               <td>${item.take_courier_no}</td>
               <td>${item.real_name}</td>
               <td>${item.addr}</td>
               <%--
               <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                --%>
                <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>
                <td>${item.send_phone}</td>
               <td><c:if test="${item.status==2 }"><span style="color:orange;">待派件<span></c:if>
                    <c:if test="${item.status!=2 }">已派件</c:if></td>
                <td align="center">
                  <%--  <a href="javascript:show('${item.id}','${item.order_no}');">详情</a> --%>
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