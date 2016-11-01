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
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">

 $(function () {
	 
	 jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {	
           var monthList =  tmjs.mlist;			
           var sres = [];
           $.each(monthList, function(i, item) {
       		sres[i]=item.month_sname;
          });
            $jqq("#monthName").autocomplete(sres, {
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
       			 $("#monthName").val(data[0]) ;
       		       } 
       	});	
	});
	 
	 
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload()
       	});     
            
         $('#add').click(function(){
        	 $.dialog({lock: true,title:'月结客户新增',drag: true,width:750,height:750,resize: false,max: false,content: 'url:${ctx}/mobile/muser_edit?layout=no',close: function(){
        	 }});
         	});
});
 
 
 function mshow(id){
	 $.dialog({lock: true,title:'月结客户详情',drag: true,width:550,height:650,resize: false,max: false,content: 'url:${ctx}/mobile/muser_show?id='+id+'&layout=no',close: function(){
	 }});
  }
 
 function medit(id){
	 $.dialog({lock: true,title:'月结客户编辑',drag: true,width:750,height:750,resize: false,max: false,content: 'url:${ctx}/mobile/muser_edit?id='+id+'&layout=no',close: function(){
	 }});
  }

 
 function files(id){
	 $.dialog({lock: true,title:'查看附件',drag: true,width:1200,height:440,resize: false,max: false,content: 'url:${ctx}/mobile/muser_files?id='+id+'&layout=no',close: function(){
	 }});
  }
  function del(id){
  	if(confirm('确定要删除此用户吗?')){
  		$.ajax({url:'/userManager/del_muser',data:{'id':id},success:function(msg){
  			 if(msg==1){
  				 alert('删除成功');
  				 location.reload();
  			 }else{
  				 alert(msg);
  			 }
  		 }});
  	}
  }    
  
  function reset(id){
	  		$.ajax({url:'/mobile/reset_muser',data:{'id':id},success:function(msg){
	  			 if(msg==1){
	  				 alert('重置成功');
	  				 location.reload();
	  			 }else{
	  				 alert(msg);
	  			 }
	  		 }});
	  }   
  function stopStatus(id,realName){
		if(confirm('您确认要停用['+realName+']分站吗?停')){
			$.ajax(
				{
				url:'/userManager/mstop'	,
				dataType:'text',
				data:{
					'id':id				
				},
			success:function(msg){			
				 if(msg==1){
					 alert('停用成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
						}
			});		
		}	
	}
	
	function startStatus(id,realName){
		if(confirm('您确认要启用['+realName+']分站吗?')){
			$.ajax(
				{
				url:'/userManager/mstart'	,
				dataType:'text',
				data:{
					'id':id				
				},
			success:function(msg){			
				 if(msg==1){
					 alert('启用成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
				}
			});		
		}	
	}

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/mobile/muser_list" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>&#12288;月结号：<input type="text" value="${params['monthSettleNo']}"  name="monthSettleNo"></input></li>
            <li>所属网点：<select id="substationNo" name="substationNo" style="width: 210px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select></li>
            <li>公司简称：<input type="text" value="${params['monthName']}" id="monthName" name="monthName"></input></li>
                   <li>合同状态：<select id="hts" name="hts" >
							<option value="">--全部--</option>
								<option value="UU" <c:out value="${params['hts'] eq 'UU'?'selected':'' }"/> >未过期</option>
								<option value="EE" <c:out value="${params['hts'] eq 'EE'?'selected':'' }"/> >已过期</option>
					</select></li>  
                <li><span>注册时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
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
  <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>  
     <shiro:hasPermission name="DEFAULT">
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="新增" id="add"/> </div>  
      </shiro:hasPermission>
  </div>   <!-- tableble_search end  -->    
</div>


<div class="tbdata">
    	 
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">月结号</th>
            <th  align="center">公司简称</th>
             <th  align="center">优惠类型</th>
            <th align="center">联系人</th>
            <th  align="center">联系电话</th>
            <th  align="center">财务电话</th>
            <th  align="center">所属快递员</th>
            <th  align="center">所属网点</th>
              <th  align="center">市场员</th>
            <th align="center">结算方式</th>
            <th  align="center">结算日期</th>
            <th  align="center">合同开始日期</th>
            <th  align="center">合同结束日期</th>
            <th  align="center">邮箱</th>
            <th  align="center">注册时间</th>
            <th width="10%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.month_settle_no}</td>
                <td>${item.month_sname}</td>
                <td><u:dict name="MSTYPE" key="${item.mstype}" /></td>
                 <td>${item.contact_name}</td>
                <td>${item.contact_phone}</td>
                <td>${item.settle_phone}</td>
                <td><u:dict name="C" key="${item.courier_no}" /></td>
                <td><u:dict name="S" key="${item.substation_no}" /></td>
                  <td>${item.mark_name}</td>
                <td>${item.settle_type}</td>
                <td>${item.settle_date}</td>
                <td>${item.ht_date_begin}</td>
                <td>${item.ht_date_end}</td>
                 <td>${item.email}</td>
                <td><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center">
                <a href="javascript:mshow('${item.id}');">详情</a>
                <shiro:hasPermission name="DEFAULT">
                         |
                <a href="javascript:medit('${item.id}');">编辑</a>
                </shiro:hasPermission>
                   |
                 <c:if test="${item.status==1}">   <a href="javascript:stopStatus('${item.id}', '${item.month_sname}');"">停用</a> </c:if>                 
             	    <c:if test="${item.status==0}">   <a href="javascript:startStatus('${item.id}', '${item.month_sname}');"">启用</a> </c:if>    
                  <shiro:hasPermission name="DEFAULT">
                         |
                 <a href="javascript:del('${item.id}');">删除</a>
                 </shiro:hasPermission>
                         |
                 <a href="javascript:reset('${item.id}');">重置密码</a>
                   |
                 <a href="javascript:files('${item.id}');">查看附件</a>
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