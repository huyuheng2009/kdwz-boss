<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/table_new.css"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    
  
       
    
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload()
       	});
           
            
            $('#print').click(function(){
            	console.log($("form:first").serialize());
          		$.dialog({lock: true,title:'订单分配',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/scan/revPrintPage?'+$("form:first").serialize()+'&layout=no',close: function(){
              	 }}); 
          	});
            
        });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/scan/revPrint" method="get">
        <ul>
             <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="takeTimeBegin" value="${params['takeTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d\'}'})"  type="text" style="width:112px" name="takeTimeEnd" value="${params['takeTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			   <li>快递员：<input type="text" value="${params['realName']}" placeholder="快递员" name="realName"></input></li>
	  	<li>网点：<select id="substationNo" name="substationNo" style="width: 210px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
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
	    <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>
	     <div class="search_new"><input class="sear_butt" type="submit" value="打印" id="print"/> </div>
	    </div>
   </div>   <!-- tableble_search end  -->   
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >序号</th>
            <th  align="center" >寄件时间</th>
            <th  align="center" >运单号</th>
            <th  align="center" >快递员</th>
            <th  align="center" >备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
               <td><fmt:formatDate value="${item.take_order_time}" type="both"/></td>
               <td>${item.lgc_order_no}</td>
               <td>${item.real_name}</td>
               <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



</body>

</html>