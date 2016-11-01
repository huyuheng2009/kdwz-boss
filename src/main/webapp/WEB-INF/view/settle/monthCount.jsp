<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
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

//导出
	$(function() {
		$('#derive_btn').click(function() {
			var action = $("form:first").attr("action");
			$("form:first input[name=serviceName]").val('monthUserCountExport');
			$("form:first").attr("action", "${ctx}/substatic/export").submit();
			$("form:first").attr("action", action);
		});
	});




$(function () {	
	trHover('t2');
	$('#reload_btn').click(function() {
		location.reload();
	});
	
	$('#settleCreate').click(function() {
		
		var beginTimeM =  $('input[name=beginTimeM]').val();
		var courierNo =  $('input[name=courierNo]').val();
		var monthSettleNo =  $('input[name=monthSettleNo]').val();
		
		var ids = '';
		$('.ids').each(function(){
            if($(this).prop("checked"))	{
             	if(!$(this).prop('disabled')){
             		 ids += $(this).val()+','; 
          	   }
 			 }
  	    });
		 if(ids.length>0){
		  ids = ids.substring(0, ids.length-1) ;
	    }else{
		 alert("请新添加一项或多项！");
		return ;
	 } 
		
	if(confirm("是否生成账单？")){
   		  $.ajax({	 
   			 type: "post",//使用get方法访问后台
 	             dataType: "text",//返回json格式的数据
 	            url: '/settle/monthSettleCreate',
 	            data: {'ctime':ids,'beginTimeM': beginTimeM,'courierNo': courierNo,'monthSettleNo': monthSettleNo}, 
 	            success: function (msg) {
                if (msg == 1) {
                    alert('生成成功');
                } else {
                    alert(msg);
                }
            }});
      } 
		

	});
	
	
    $('.select_all').click(function() {
      	 if($(this).prop("checked"))	{
      			$('input[name=ids]').each(function(){
      				if(!$(this).prop('disabled')){
      					$(this).prop('checked',true); 
      				}
      				
              	}); 
      	 }else{
      		 $('input[name=ids]').each(function(){
      			if(!$(this).prop('disabled')){
  					$(this).prop('checked',false); 
  				}
           	}); 
      	 }
      
      });
	
	
	
	 jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {	
            var courierList =  tmjs.clist;			
            var monthList =  tmjs.mlist;			
            var sres = [];
        	var cres = [];
            $.each(monthList, function(i, item) {
            	
        		sres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_sname.replace(/\ /g,"")+')';
           });
          $.each(courierList, function(i, item) {
        	  var inner_no = "" ;
             	if(item.inner_no){
             		inner_no=item.inner_no ;
             		cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+","+item.real_name.replace(/\ /g,"")+')';
             	}else{
             		cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.real_name.replace(/\ /g,"")+')';
             	}
           }); 
          
          
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
        			 $("#courierNo").val(data[0].substring(0,data[0].indexOf('('))) ;    
        		       } 
        	}); 
          
             $jqq("#monthSettleNo").autocomplete(sres, {
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
        			 $("#monthSettleNo").val(data[0].substring(0,data[0].indexOf('('))) ;
        		       } 
        	});	
	});
      
 });


</script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/settle/monthCount"
					method="get"  onsubmit="loaddata()">
					<input type="hidden" name="serviceName" value="" />
					  <input type="hidden" name="ff" value="${params['ff']}"/>
					<input type ="hidden"  name ="orderBy" value="${params['orderBy']}"/>
					<ul>			
					<li><span style="float: left; ">账单日期：</span>
					<input id="dateBeginM" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true})"
							type="text"  name="beginTimeM" value="${params['beginTimeM']}" onClick="WdatePicker({dateFmt:'yyyy-MM '})" />
							
				  </li>
						<li>快递员：<input type="text" placeholder="快递员编号"value="${params['courierNo']}" name="courierNo"	id="courierNo"></input> </li>
						<li>月结号：<input type="text" placeholder="月结客户编号"	value="${params['monthSettleNo']}" name="monthSettleNo"	id="monthSettleNo"></input> 	</li>	
						<li><input class="button input_text  medium blue_flat" type="submit" value="查询"  /></li>
						<li><input class="button input_text  medium blue_flat" type="reset" value="重置" /></li>
					</ul>
				</form:form>
			</div>
			<!-- search_cont end  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator">
					<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit" value="生成账单" id="settleCreate" />
				</div>
			</div>
		</div>
		<!-- tableble_search end  -->
	</div>


	<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
				    <th align="center" class="nosort"><input class="select_all" name="select_all" type="checkbox"  /></th>
					<th  align="center">序号</th>
					<th  align="center">月份</th>
					<th  align="center">所属网点</th>		
					<th  align="center">所属快递员</th>		
					<th  align="center">月结号</th>	
					<th  align="center">公司简称</th>	
					<th  align="center">邮箱</th>	
					<th  align="center">是否开票</th>	
					<th  align="center">联系电话</th>	
					<th  align="center">结算方式</th>	
					<th  align="center">总票数</th>
					<th  align="center">应收款项</th>
			     	<th  align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td align="center"><input  class="ids" name="ids" type="checkbox" <c:out value="${!empty item.musid?'checked':''}" /> value="${item.ctime}" /></td>
						<td align="center">${status.count}</td>
						<td>${item.settleMonth}</td>
						<td>${item.substationName}</td>
						<td>${item.courierName}</td>
						<td>${item.TmonthSettleNo}</td>
						<td>${item.monthSname}</td>
						<td>${item.email}</td>
						<td><c:if test="${item.liced eq 'N'}"><span style="color: red;">否</span></c:if> 
                           <c:if test="${item.liced eq 'Y'}"><span style="color:green;">是</span></c:if></td>
						<td>${item.contactPhone}</td>
						<td>${item.settleType}</td>			
						<td  style="${item.sumNew eq 'Y'?'color: red':''}">${item.sumCount}</td>
						<td style="${item.monthNew eq 'Y'?'color: red':''}">${item.TSALL}</td>
						<td><c:if test="${!empty item.musid}">已生成</c:if>    </td>									
					</tr>
				</c:forEach>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>	
					<td>${count}</td>
					<td>实收总额:${AllsumMoney}</td>		
					<td></td>	
				</tr>			
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>