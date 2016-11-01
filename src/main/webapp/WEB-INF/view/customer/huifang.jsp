<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:500px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin: -40px 0px 0 50px;display: none;}
</style>
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
	
	function edit(id,customer_no,cpn_sname){
		 $.dialog({lock: true,title:'客户回访',drag: false,width:750,height:300,resize: false,max: false,content: 'url:${ctx}/customer/hfedit?id='+id+'&customer_no='+customer_no+'&cpn_sname='+cpn_sname+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/customer/huifang" method="get">
      <input type="hidden" name="serviceName" value="customer_huifang"/>
    	<ul>
            <li>联系电话：<input type="text" value="${params['concat_phone']}" name="concat_phone"></input></li>
             <li>公司简称：<input type="text" value="${params['cpn_sname']}" name="cpn_sname"></input></li>
              <li>客服负责人：<input type="text" value="${params['kefu_name']}" name="kefu_name"></input></li>
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
                  <th  align="center">客户回访</th>
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
                 <td><c:set var="huifang" value="${item.huifang}" />
            	<a onclick="showAddr(this)">${fn:substring(huifang,0,10)}</a><div class="out_address">${huifang}</div></td>
                <td align="center">
                  <a href="javascript:edit('${item.id}','${item.customer_no}','${item.cpn_sname}');"> 客户回访</a>
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