<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
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
            	var s = ${list.total} ;
            	if(s>200){
            		alert("结果集大于200，去修改查询条件") ;
            		return ;
            	}else{
            		$.dialog({lock: true,title:'清单打印',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/scan/sendPrintPage?'+$("form:first").serialize()+'&layout=no',close: function(){
                 	 }}); 
            	}
          		
          	});
           
            
        });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/scan/sendPrint" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            
              <li><span>出站时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="scanTimeBegin" value="${params['scanTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d\'}'})"    type="text" style="width:112px" name="scanTimeEnd" value="${params['scanTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			 <li>当前站点：<select id="substationNo" name="substationNo" style="width: 220px">
				<c:forEach items="${substations}" var="item" varStatus="status">
								<option   value="${item.substation_no }" id="${item.substation_name }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
				</select>
			</li>	
			<li> 下一站：<select id="nextNo" name="nextNo" style="width: 220px">
					 <option value="">请选择</option>
							<c:forEach items="${nextNo}" var="item" varStatus="status">
								<option   value="${item.substation_no }" id="${item.substation_name }" <c:out value="${params['nextNo'] eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
					</select>
			</li>	
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
            <th  align="center" >下一站名称</th>
            <th  align="center" >备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
                <td><fmt:formatDate value="${item.order_time}" type="both"/></td>
               <td>${item.lgc_order_no}</td>
               <td><u:dict name="${item.next_type}" key="${item.next_no}" /></td>
               <td align="center"></td>
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