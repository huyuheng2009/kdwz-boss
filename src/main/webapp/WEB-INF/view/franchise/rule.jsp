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
		$.dialog({lock: true,title:'编辑报价规则',cache:false,drag: true,width:850,height:950,resize: false,max: false,content: 'url:${ctx}/franchise/redit?id='+id+'&layout=no',close: function(){
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
                       $jqq("#takeNo").autocomplete(slists, {
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
                    			 $("#takeNo").val(data[0]) ;
              			       } 
                    	});	
                
                       $jqq("#sendNo").autocomplete(slists, {
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
                   			 $("#sendNo").val(data[0]) ;
             			       } 
                   	});
               
               
         }); 	      
            
            
        	$('#exportData').click(function(){
        		 var action= $("form:first").attr("action");
         	   $("form:first").attr("action","${ctx}/export/service").submit();
         	   $("form:first").attr("action",action); 
        	});
        	
        	$('#add').click(function(){
        		$.dialog({lock: true,title:'新增报价规则',cache:false,drag: true,width:850,height:950,resize: false,max: false,content: 'url:${ctx}/franchise/redit?layout=no',close: function(){
            		//location.reload();
            	 }});
        	});
        	
        	$('#trys').click(function(){
        		$.dialog({lock: true,title:'试算',cache:false,drag: true,width:700,height:350,resize: false,max: false,content: 'url:${ctx}/franchise/rtrys?layout=no',close: function(){
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

    <form:form id="trans" action="${ctx}/franchise/rule" method="post">
     <input type="hidden" name="serviceName" value="jmrule"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
       <li>寄件网点：<input type="text" value="${params['takeNo']}" name="takeNo" id="takeNo" style="width: 200px;"></input></li>
       <li>派件网点：<input type="text" value="${params['sendNo']}" name="sendNo" id="sendNo" style="width: 200px;"></input></li>
       <li>费用类型：<select name="mtype" style="width: 110px">
                <option value="">--全部--</option>
                <option value="Z" ${params['mtype'] eq 'Z'?'selected':''}>中转费</option>
                <option value="P" ${params['mtype'] eq 'P'?'selected':''}>派件费</option>
             </select></li>  
        <li>物品类型：<u:select id="itype" sname="itype" stype="ITEM_TYPE"  value="${params.itype}"/></li> 
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
	     <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div> 
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th  align="center">序号</th>
					<th width="10%"  align="center">寄件网点</th>
					<th  width="10%" align="center">派件网点</th>
					<th  align="center">费用类型</th>
					<th  width="10%" align="center">物品类型</th>
					<th  align="center">算费模式</th>
                    <th align="center">参数1</th>
                   <th align="center">参数2</th>
                   <th align="center">附加费</th>
                  <th align="center">最低费用</th>
                  <th align="center">开始时间</th>
                  <th align="center">结束时间</th>
                  <th align="center">计费公式</th>
                  <th align="center">创建时间</th>
                  <th align="center">创建人</th>
                  <th align="center">操作</th>
	</tr>
				
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td>${status.count}</td>
            <td>
            	<c:set var="tsname" value="${item.tsname}" />
            		<a onclick="showAddr(this)">${fn:substring(tsname,0,10)}</a><div class="out_address">${tsname}</div>
            </td> 
            <td>
            	<c:set var="ssname" value="${item.ssname}" />
            	<a onclick="showAddr(this)">${fn:substring(ssname,0,10)}</a><div class="out_address">${ssname}</div>
            </td>
            <td><c:if test="${item.money_type eq 'Z'}">中转费</c:if><c:if test="${item.money_type eq 'P'}">派件费</c:if>  </td>              
            <td>${item.citype}</td> 
           <td><c:if test="${item.weight_type eq 'R'}">实际重量</c:if><c:if test="${item.weight_type eq 'W'}">四舍五入</c:if><c:if test="${item.weight_type eq 'L'}">0.5进位</c:if>  </td>          
            <td>${item.wval1}</td> 
            <td>${item.wval2}</td> 
            <td>${item.vpay}</td> 
            <td>${item.zpay}</td> 
            <td><fmt:formatDate value="${item.begin_time}" type="both"/></td> 
            <td><fmt:formatDate value="${item.end_time}" type="both"/></td> 
            <c:set var="sendaddr" value="${item.weight_text}" />   
            <td><a onclick="showAddr(this)">首重：${item.fweight}</a><div class="out_address">${sendaddr}</div></td>
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