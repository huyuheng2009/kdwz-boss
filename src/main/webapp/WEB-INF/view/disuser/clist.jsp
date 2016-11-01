<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
#export{margin-left:10px;}
</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(function(){
		trHover('t2');
		 $('#reload_btn').click(function(){
			 location.reload();
		});
		 
		  jQuery.ajax({
		      url: 'http://kdwz.upt-boss.yogapay.com/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {	 
	    var slist = tmjs.slist;
        var clist =tmjs.clist;
       	var sres = [];
        var cres = [];
       	$.each(slist, function(i, item) {
       	 var inner_no = "" ;
        	if(item.inner_no){inner_no=item.inner_no+',';}
       		sres[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
           });
           $.each(clist, function(i, item) {
        	   var inner_no = "" ;
              	if(item.inner_no){inner_no=item.inner_no+',';}  
           	cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
           });  
          
    /*      $jqq("#substationNo").autocomplete(sres, {
        		minChars: 0,
        		max: 12,
        		autoFill: true,
        		mustMatch: false,
        		matchContains: true,
        		scrollHeight: 220,
        		formatItem: function(data, i, total) {
        			return data[0];
        		}
        	}).result(function(event, data, formatted) {
      		if(data[0].indexOf(')')>-1){
     			 $("#substationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
     			$("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
 			       } 
     	});	
     	 */
  			 
        $jqq("#courierNo").autocomplete(cres, {
     		minChars: 0,
     		max: 12,
     		autoFill: true,
     		mustMatch: false,
     		matchContains: true,
     		scrollHeight: 220,
     		formatItem: function(data, i, total) {
     			return data[0];
     		}
     	}).result(function(event, data, formatted) {
   		if(data[0].indexOf(')')>-1){
  			 $("#courierNo").val(data[0]) ;
  			//$("#gtips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
			       } 
  	});	
		}); 
		  
		  
		  $("#export").click(function(){
	    	   var form = $("#trans");
	    	   var action = form.attr("action");
	    	   form.attr("action","/disuser/export2");
	    	   form.submit();
	    	   form.attr("action",action);
	       });
		 
	});

	function edit(id){
		 $.dialog({lock: true,title:'编辑会员信息',drag: false,width:800,height:600,resize: false,max: false,content: 'url:${ctx}/system/ushow?userName='+username+'&id='+id+'&layout=no',close: function(){
			 location.reload();
	  	 }});
	}
	
	function recharge(disUserNo){
			 location.href="/disuser/urecharge?userNo="+disUserNo;
	}

	function reset(id){
		 if(confirm('确定重置会员密码吗？')){
			$.ajax({url:'${ctx}/disuser/ureset',data:{'id':id},success:function(msg){
				 if(msg=='1'){
					 alert('重置成功，密码：1234');
					 location.reload();
				 }else{
					 alert(msg);
					 location.reload();
				 }
			 }});
		}
	}

	function status(id,dis_user_name,s){
		tip='';
		if(s==0){
			tip='禁用';
		}else{
			tip='启用';
		}
		if(confirm('确定要'+tip+' ['+dis_user_name+'] 会员吗?')){
			$.ajax({url:'ustatus',data:{'id':id,'s':s},success:function(msg){
				 if(msg==1){
					 alert('修改成功');
					 location.reload();
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
    <form:form id="trans" action="${ctx}/disuser/clist" method="get">
    	<ul>
    	    <li><span>扣款时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
    	    <li>会员号：<input type="text" value="${params['disUserNo']}" name="disUserNo"></input></li>
            <li>登记人：<input type="text" value="${params['operator']}" name="operator"></input></li>
		<%-- 	
			  <div class="clear"></div>
            <li>收款网点：<input  name="substationNo" id="substationNo"  value="${params['substationNo']}" style="width: 200px;"  type="text"/>
            <div  style="color: white;background-color: gray;height:30px;display: inline-block;line-height: 30px;margin-left: 10px;padding: 0 10px;min-width: 100px;" id="stips">&#12288;&#12288;&#12288;&#12288;</div>
            </li> --%>
            
             <li>快递员：<input  name="courierNo" id="courierNo"  value="${params['courierNo']}" style="width: 140px;"  type="text"/>
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
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> 
  <input class="button input_text  big gray_flat" type="submit" value="导出" id="export"/>
  </div>
	 </div>   <!-- tableble_search end  -->   
  
</div>
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center">序号</th>
            <th align="center">运单号</th>
            <th align="center">会员号</th>
            <th align="center">会员名称</th>
            <th align="center">扣款金额</th>
           <!--  <th align="center">应扣金额</th> 
            <th align="center">优惠</th>
            <th align="center">是否开具发票</th>-->
            <th align="center">快递员</th>
            <!-- <th align="center">收款网点</th> -->
            <th align="center">登记人</th>
            <th align="center">扣款时间</th>
            <th align="center">备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.lgc_order_no}</td>
                <td>${item.dis_user_no}</td>
                <td>${item.dis_user_name}</td>
                <td>${item.rmoney}</td>
            <%--     <td>${item.omoney}</td>
                <td>${item.discount_text}</td>
                <td><c:if test="${item.lied eq 'Y' }">是</c:if><c:if test="${item.lied ne 'Y' }">否</c:if></td> --%>
                <td>${item.real_name}</td>
                <%-- <td><u:dict name="S" key="${item.substation_no}"></u:dict></td> --%>
                <td>${item.operator}</td>
                <td><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.note}</td>
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