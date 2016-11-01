<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function(){
		trHover('t2');

		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});

    function mexport() {
        var action = $("form:first").attr("action");
        $("form:first").attr("action", "${ctx}/export/service").submit();
        $("form:first").attr("action", action);
        loaddata_end() ;
    }
	
	function edit(id){
		 $.dialog({lock: true,title:'编辑客户信息',drag: false,width:750,height:520,resize: false,max: false,content: 'url:${ctx}/customer/edit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id){
		 if(confirm('确定删除吗？')){
			 $.ajax({url:'del',data:{'id':id},success:function(msg){
				 if(msg==1){
					 alert('删除成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
			 }});
		 }
	}
</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/customer/list" method="get">
      <input type="hidden" name="serviceName" value="customer_list"/>
    	<ul>
            <li>联系电话：<input type="text" value="${params['concat_phone']}" name="concat_phone"></input></li>
             <li>公司简称：<input type="text" value="${params['cpn_sname']}" name="cpn_sname"></input></li>
             <li>来源：<select name="source" style="width: 110px">
              <option value="">--全部--</option>
                 <option value="BOSS" ${params['source'] eq 'BOSS'?'selected':''}>后台下单</option>
                <option value="WEIXIN" ${params['source'] eq 'WEIXIN'?'selected':''}>微信</option>
                <option value="COURIER" ${params['source'] eq 'COURIER'?'selected':''}>快递端</option>
                <option value="WEB" ${params['source'] eq 'WEB'?'selected':''}>网页下单</option>
             </select></li>
                <li><span>注册时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
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
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
	 <div class="search_new">  <input id="export" class="button input_text  big gray_flat" type="submit" value="导出"  onclick="mexport();"/></div>
	 </div>   <!-- tableble_search end  -->   
  
</div>
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">客户号</th>
            <th align="center">公司简称</th>
             <th align="center">联系人</th>
              <th align="center">联系电话</th>
               <th align="center">手机号</th>
                <th align="center">客服负责人</th>
                 <th align="center">月结号</th>
                  <th align="center">来源</th>
                   <th align="center">注册时间</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.customer_no}</td>
                 <td>${item.cpn_sname}</td>
                   <td>${item.concat_name}</td>
                    <td>${item.concat_phone}</td>
                    <td>${item.cell_phone}</td>
                     <td>${item.kefu_name}</td>
                <td>${item.month_no}</td>
                <td><u:dict name="ORDER_SRC" key="${item.source}" /></td>
                 <td>${item.create_time}</td>
                <td align="center">
                  <a href="javascript:edit('${item.id}');">编辑</a>
	                 | 
	                <a href="javascript:del('${item.id}');">删除</a>   
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