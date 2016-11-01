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
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>

   <script type="text/javascript">
   
   function showAddr(e){
	  
	   if("block"==$(e).siblings(".out_address").css("display")){
		   $(".out_address").css("display","none"); 
	   }else{
		   $(".out_address").css("display","none");
		   	$(e).siblings(".out_address").css("display","block");
	   }
   	
   }
   
	function edit(id){
		$.dialog({lock: true,title:'编辑报价规则',cache:false,drag: true,width:800,height:550,resize: false,max: false,content: 'url:${ctx}/freightRule/redit?id='+id+'&layout=no',close: function(){
    		//location.reload();
    	 }});
	}
	
 	function del(id){
		 if(confirm('确定删除规则吗？')){
			 $.ajax({url:'rdel',data:{'id':id},success:function(msg){
				 if(msg==1){
					 alert('删除成功');
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
                //location.reload()
           	 $("form:first").submit();
       	});
  
        	$('#exportData').click(function(){
        		 var action= $("form:first").attr("action");
         	   $("form:first").attr("action","${ctx}/export/service").submit();
         	   $("form:first").attr("action",action); 
        	});
        	
        	$('#add').click(function(){
        		$.dialog({lock: true,title:'新增运费报价',cache:false,drag: true,width:800,height:550,resize: false,max: false,content: 'url:${ctx}/freightRule/redit?layout=no',close: function(){
            		//location.reload();
            	 }});
        	});
        	
        	$('#trys').click(function(){
        		$.dialog({lock: true,title:'试算',cache:false,drag: true,width:700,height:350,resize: false,max: false,content: 'url:${ctx}/freightRule/rtrys?layout=no',close: function(){
            		//location.reload();
            	 }});
        	});
        	
        	
        });            
    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/freightRule/rule" method="post">
     <input type="hidden" name="serviceName" value="freightRule"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
        <li>时效类型： <u:select id="ttype" sname="ttype" stype="AGING_TYPE"  value="${params.ttype}"/></li> 
        <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
          <div class="search_new"><input class="sear_butt" type="submit" id="add" value="新增" /></div>
          <div class="search_new"><input class="sear_butt" type="submit" id="trys" value="试算" /></div>
	     <!-- <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>  -->
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th  align="center">序号</th>
					<th width="10%"  align="center">时效类型</th>
					<th  width="10%" align="center">物品类型</th>
                   <th align="center">附加费</th>
                  <th align="center">计算公式</th>
                  <th align="center">创建时间</th>
                  <th align="center">创建人</th>
                  <th align="center">操作</th>
	</tr>
				
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td>${status.count}</td>
            <td>${item.ttype}</td> 
            <td>${item.itype}</td> 
            <td>${item.vpay}</td> 
            <td>${item.freight_text}</td> 
           <%--  <c:set var="sendaddr" value="${item.weight_text}" />   
            <td><a onclick="showAddr(this)">首重：${item.fweight}</a><div class="out_address">${sendaddr}</div></td> --%>
            <td><fmt:formatDate value="${item.create_time}" type="both"/></td> 
            <td>${item.create_name}</td> 
             <td> <a href="javascript:edit('${item.id}');">编辑</a>
                         |
                  <a href="javascript:del('${item.id}');">删除</a>  </td> 
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