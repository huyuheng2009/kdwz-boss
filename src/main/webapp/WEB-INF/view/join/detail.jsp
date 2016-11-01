<%@ page contentType="text/html;charset=UTF-8"
		 trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<style>
/*********地址展开样式**********/
.out_address {
	position: absolute;
	width: 200px;
	padding: 6px;
	background: #fff;
	border: 1px solid #cdcdcd;
	margin: -28px 0 0 -4px;
	display: none;
}
</style>
		<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
		<script>
			var $jqq = jQuery.noConflict(true);
		</script> 
		<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
		<script type="text/javascript">
			//获取时间段，单位、天
			function getToday(date, less) {
				var yesterday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24 * less;
				var yesterday = new Date();
				yesterday.setTime(yesterday_milliseconds);

				var strYear = yesterday.getFullYear();
				var strDay = yesterday.getDate();
				var strMonth = yesterday.getMonth() + 1;
				if (strMonth < 10) {
					strMonth = "0" + strMonth;
				}
				datastr = strYear + "-" + strMonth + "-" + strDay;
				return datastr;
			}
		
			$(function() {
				$('#month').click(function() {
					var now = new Date();
					var begin = getToday(now, 30);
					var end = getToday(now, 0);
					$('#dateBegin').val(begin);
					$('#dateEnd').val(end);
				});
			});
			$(function() {
				$('#threeMonth').click(function() {
					var now = new Date();
					var begin = getToday(now, 90);
					var end = getToday(now, 0);
					$('#dateBegin').val(begin);
					$('#dateEnd').val(end);
				});
			});
		
	
			$(function() {
				
		           
	        	$('#derive_btn').click(function(){
	        		var action= $("form:first").attr("action");
// 	               $("form:first input[name=serviceName]").val('codMonthCount');
	        	   $("form:first").attr("action","${ctx}/join/export1").submit();
	        	   $("form:first").attr("action",action);
	        	});
				
				
				
				trHover('t2');
				$('#reload_btn').click(function() {
					location.reload();
				});
			});

		

			
			function mchange(e)
			{
				var mode = $(e).val();		  
				if("C" == mode ){
					$('#sendTime').hide();		
					$('#createTime').show();		
				}else{
					$('#sendTime').show();		
					$('#createTime').hide();	
				}
			}
				
			    
			    $(function(){
			    	var s ='${params.timeType}';
			    	console.info(s);
			    	if(	s == 'C'){
//			     	 document.getElementById('checkMonth').checked=true;
			    	 $('#create').prop('checked',true);
			    	 $('#createTime').show();		
			    	$('#sendTime').hide();	
			    	}else{
//			     	 document.getElementById('checkDay').checked=true;
			    	 $('#send').prop('checked',true);
			    	 $('#createTime').hide();		
			    	 $('#sendTime').show();
			    	}			    	
			    });  
			    
			    
			    
			    
			    
			    
			    
		</script>
		<style type="text/css">

		</style>
	</head>
	<body>
		<div class="search">
			<div class="tableble_search">
				<div class=" search_cont">
					<form:form id="trans" action="${ctx}/join/detail"
							   method="get">
						<input type="hidden" name="serviceName" value="" />
						<input type="hidden" name="ff" value="${params['ff']}" />
					
						<ul>
						<li style="height: 120px;"><span style="vertical-align:top;">运单号</span>
						<textarea rows="10" cols="20" 	style="height: 120px; width: 200px; padding:0 2px ;" name ="lgcOrderNo" >${params.lgcOrderNo}</textarea></li>
						
							<li><input type="radio" name="timeType"  style="vertical-align:middle;"  id ="create" value ="C"  onchange="mchange(this);" />产生时间</li>
							<li><input type="radio" name="timeType"   style="vertical-align:middle;" id ="send" value ="S"  checked="checked"   onchange="mchange(this);"/>寄件时间</li>
		
						<li  id="createTime" style="display:none;"><span></span> <input id="dateBegin"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
														  type="text" style="width: 112px" name="beginTime"
														  value="${params['beginTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /> ~ <input
														  id="dateEnd"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'dateBegin\')}', maxDate: '%y-%M-%d '})"
														  type="text" style="width: 112px" name="endTime"
														  value="${params['endTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /></li>
							<li  id="sendTime"><span></span> <input id="sendOrderBeginTime"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'SendOrderEndTime\')||\'%y-%M-%d \'}'})"
														  type="text" style="width: 112px" name="sendOrderBeginTime"
														  value="${params['sendOrderBeginTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /> ~ <input
														  id="SendOrderEndTime"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'sendOrderBeginTime\')}', maxDate: '%y-%M-%d '})"
														  type="text" style="width: 112px" name="sendOrderEndTime"
														  value="${params['sendOrderEndTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /></li>
					<li>		
						
											
							<li>网点名称：		<select id="substationNo" name="substationNo" class="time_bg"
								style="width: 180px;">
								<option value="">--全部--</option>
								<c:forEach items="${substationList}" var="item"
									varStatus="status">
									<option value="${item.substation_no}"	${params.substationNo eq item.substation_no?'selected':''}>${item.substation_name }</option>
								</c:forEach>
							</select>
							</li>	
							<li>操作员：<input type="text" id="operater" placeholder="操作员"	value="${params['operater']}" name="operater"></input> 
							</li>
							<li>来源：<select name="source" style="width: 120px;">
									<option value="" ${params.source eq ''?'selected':''}>全部</option>
									<option value="BOSS" ${params.source eq 'BOSS'?'selected':''}>系统</option>
									<option value="USER" ${params.source eq 'USER'?'selected':''}>用户</option>
							</select> 
							</li>
							<li>付款方式：<select name="payType" style="width: 120px;">
									<option value="" ${params.payType eq ''?'selected':''}>全部</option>
									<option value="CASH" ${params.payType eq 'CASH'?'selected':''}>现金</option>
									<option value="POS" ${params.payType eq 'POS'?'selected':''}>转账</option>
							</select> 
							</li>
						<li>类型：<select name="type" style="width: 120px;">
									<option value="" ${params.type eq ''?'selected':''}>全部</option>
									<option value="S" ${params.type eq 'S'?'selected':''}>充值</option>
									<option value="Z" ${params.type eq 'Z'?'selected':''}>冲账</option>
									<option value="ZZ" ${params.type eq 'ZZ'?'selected':''}>中转费</option>
									<option value="PJ" ${params.type eq 'PJ'?'selected':''}>派件费</option>
							</select> 
							</li>

							
					<li><input class="button input_text  medium blue_flat"
							   type="submit" value="查询" /></li>
					<li><input class="button input_text  medium blue_flat"
							   type="reset" value="重置" />
							 </li>
					</ul>
				</form:form>
			</div>
			<!-- search_cont end  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator" >	
					<div class="search_new">
						<input class="button input_text  big gray_flat" type="button"
							   value="导出" id="derive_btn" />
					</div>			
			</div>
		</div>
		<!-- tableble_search end  -->
		</div>


		<div class="tbdata">

			<table width="100%" cellspacing="0" class="t2" id="t2">
				<thead>
					<tr>			
						<th width="30" >序号</th>
						<th>产生时间</th>
						<th>寄件时间</th>
						<th>网点编号</th>
						<th>网点名称</th>
						<th>运单号</th>
						<th>类型</th>
						<th>付款方式</th>
						<th>前余额</th>
						<th>发生金额</th>
						<th>后余额</th>
						<th>来源</th>
						<th>操作人</th>
						<th>备注</th>				
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.list}" var="item" varStatus="status">
						<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td>${status.count}</td>
						<td>${item.createTime}</td>
						<td>${item.take_order_time}</td>
						<td>${item.innerNo}</td>
						<td>${item.substationName}</td>
						<td>${item.lgcOrderNo}</td>
						<td>
							<c:choose>
						<c:when test="${item.type eq 'S'}">充值</c:when>
						<c:when test="${item.type eq 'Z'}">冲账</c:when>	
						<c:when test="${item.type eq 'ZZ'}">中转费</c:when>	
						<c:when test="${item.type eq 'PJ'}">派件费</c:when>	
						<c:otherwise></c:otherwise>				
						</c:choose></td>
						<td>
					<c:choose>
						<c:when test="${item.payType eq 'CASH'}">现金</c:when>
						<c:when test="${item.payType eq 'POS'}">转账</c:when>	
						<c:otherwise></c:otherwise>				
						</c:choose>			
					</td>
						<td>${item.beforeBalance}</td>
						<td>${item.useBalance}</td>
						<td>${item.afterBalance}</td>
						<td>
						<c:choose>
						<c:when test="${item.source eq 'BOSS'}">系统</c:when>
						<c:when test="${item.source eq 'USER'}">用户</c:when>		
						<c:otherwise></c:otherwise>			
						</c:choose>					
					</td>
						<td>${item.operater}</td>
						<td>${item.note}</td>				
						</tr>
					</c:forEach>
				
				
				</tbody>
			</table>
		</div>
		<div id="page">
			<pagebar:pagebar total="${list.pages}"
							 current="${list.pageNum}" sum="${list.total}" />
		</div>
	</body>

</html>