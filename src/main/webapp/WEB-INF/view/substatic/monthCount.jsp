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
				$('#email_send').click(function(){
					var lgcOrderNoList='';
					$('.ids:checked').each(function(){
						lgcOrderNoList +=    '\''+$(this).val()+'\','; 
	            	});
				if(lgcOrderNoList.length<1){				
				alert('请选择需要发送邮件的订单');
				return ;				
				}				
				
				var beginTime = $('input[name=beginTime]').val();
				var endTime = $('input[name=endTime]').val();
				
// 					 var api = frameElement.api, W = api.opener;
					$.ajax({
					    type: "GET",
					    async:"true",
						url: '/substatic/sendEmail',				
						data: {
							'lgcOrderNoList': lgcOrderNoList,
							'beginTime': beginTime,
							'endTime': endTime
						}
// 						dataType:'text',
// 						success: function() {			
// 							   alert('成功发送邮件');	
// 							   location.reload();				
// 						}
					});							
					  alert('成功发送邮件');	
					   location.reload();	
					
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
	                		$(this).prop('checked',false); 
	                	}); 
	           	 }
	           
	           });	
		
				
				
				$('#sixMonth').click(function() {
					var now = new Date();
					var begin = getToday(now, 180);
					var end = getToday(now, 0);
					$('#dateBegin').val(begin);
					$('#dateEnd').val(end);
				});
        
 jQuery.ajax({ url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
				      dataType: "script",
				      cache: true
				}).done(function() {	 
				var mlist = tmjs.mlist;					
				var mres = [];
				var couriList = tmjs.clist;		
				var cres = [];
				
				$.each(mlist, function(i, item) {				   
		            var name = item.month_name;
					if (name.length < 1) {
						name = item.month_sname;
					}
					mres[i] = item.month_settle_no.replace(/\ /g, "") + '(' + name.replace(/\ /g, "") + ')';
				});					
				   $.each(couriList, function(i, item) {
		            	var inner_no = "" ;
    	              	if(item.inner_no){inner_no=item.inner_no+','}
    	              	cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
		             }); 
				$jqq("#codName").autocomplete(mres, {
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
					if (data[0].indexOf(')') > -1) {
						$("#monthSettleNo").val(data[0].substring(0, data[0].indexOf('(')));
						$("#monthSname").val(data[0].substring(data[0].indexOf('(') + 1, data[0].indexOf(')')));
					}
				});
				
				$jqq("#revNo").autocomplete(cres, {
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
					if (data[0].indexOf(')') > -1) {
						$("#revNo").val(data[0].substring(0, data[0].indexOf('('))+data[0].substring(data[0].indexOf('('), data[0].indexOf(')')+1));						
					}
				});
				
				$jqq("#sendNo").autocomplete(cres, {
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
					if (data[0].indexOf(')') > -1) {
						$("#sendNo").val(data[0].substring(0, data[0].indexOf('('))+data[0].substring(data[0].indexOf('('), data[0].indexOf(')')+1));						
					}
				});
				});				
						
			});
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
			//导出
			$(function() {
				$('#derive_btn').click(function() {
					var fla = false;
					var  month = $('input[name=monthSettleNo]').val();
					if(month.length<1|| month ==''){
						alert("导出选项必须输入月结号！");
						return;
					}
					
					 $.ajax({
						 type: "post",//使用get方法访问后台
				           dataType: "json",//返回文本格式
							  async: false,
				           url: "<%=request.getContextPath()%>/substatic/checkMonth",//要访问的后台地址
						data : {
							'monthNo' : month				
						},//要发送的数据
// 						beforeSend : function() { //请求成功前触发的局部事件
// 							alert("正在请求后台数据，请稍候......");
// 						},
						success : function(msg) {//msg为返回的数据，在这里做数据绑定         						
							if (msg.code == 0) {
								fla = "true";
							} else if (msg.code == 1) {
								alert(msg.message);
								return;
							} 

						}
					});
					if(fla){
						var action = $("form:first").attr("action");
						$("form:first input[name=serviceName]").val('monthCountExport');
						$("form:first").attr("action", "${ctx}/substatic/export").submit();
						$("form:first").attr("action", action);
					}	
				});
			});
			function rev(orderNo) {
				//         	 if(confirm('确认快件已经到达分站？')){
				//            		 $.ajax({url:'/scan/srev',data:{'orderNo':orderNo},success:function(msg){
				//            			 if(msg==1){
				//            				 alert('操作成功');
				//            				 location.reload();
				//            			 }else{
				//            				 alert(msg);
				//            			 }
				//            		 }});
				//            	 }
			}

			$(function() {
				trHover('t2');
				$('#reload_btn').click(function() {
					location.reload();
				});
			});

			function checkCNa(courierNo) {
				//         	$.post('/substatic/courierNoCheck',{'courierNo':courierNo},function(msg){
				//            		$('#ConNa').val(msg.courierNam);        
				//        		 },"JSON");
				$.ajax({
					url: '/substatic/checkMonthUser',
					data: {
						'courierNo': courierNo
					},
					success: function(msg) {
						$('#ConNa').val(msg.courierNam);
					}
				});
			}

			function checkMonthSettleNo(monthNo) {
				$.ajax({
					url: '/substatic/checkMonthUser',
					type: 'get',
					data: {
						'monthSettleNo': monthNo
					},
					success: function(msg) {
						$('#company').val(msg.company);
						$('#name').val(msg.name);
						$('#phone').val(msg.phone);
						$('#address').val(msg.address);
					}
				});
			}
			
			function mchange(e)
			{
				var mode = $(e).val();		  
				if("T" == mode ){
					$('#sendTime').hide();		
					$('#takeTime').show();		
				}else{
					$('#sendTime').show();		
					$('#takeTime').hide();	
				}
			}
			
			function month(lgcNo,orderNote){	
				$.dialog({
						width: '680px',
						height: '280px',
						max: false,
						min: false,
						drag: false,
						resize: false,
						title: '月结备注',
						content: "url:<%=request.getContextPath()%>/substatic/monthNote?layout=no&lgcOrderNo="+lgcNo+"&orderNote="+orderNote,
						lock: true
					}); 
				} 
			    function showAddr(e){
	     	   	$(".out_address").css("display","none");
	    		    	$(e).siblings(".out_address").css("display","block");
	  	      }
			    
			    $(function(){
			    	var s ='${params.takeOrSend}';
			    	console.info(s);
			    	if(	s == 'T'){
//			     	 document.getElementById('checkMonth').checked=true;
			    	 $('#take').prop('checked',true);
			    	 $('#takeTime').show();		
			    	$('#sendTime').hide();	
			    	}else{
//			     	 document.getElementById('checkDay').checked=true;
			    	 $('#send').prop('checked',true);
			    	 $('#takeTime').hide();		
			    	 $('#sendTime').show();
			    	}			    	
			    });  
			    
			    
		</script>
	</head>
	<body>
		<div class="search">
			<div class="tableble_search">
				<div class=" search_cont">
					<form:form id="trans" action="${ctx}/substatic/monthCount"
							   method="get" onsubmit="loaddata()">
						<input type="hidden" name="serviceName" value="" />
						<input type="hidden" name="ff" value="${params['ff']}" />
						<ul>
							<li><input type="radio" name="takeOrSend"  style="vertical-align:middle;"  id ="take" value ="T"  onchange="mchange(this);"/>寄件时间</li>
							<li><input type="radio" name="takeOrSend"   style="vertical-align:middle;" id ="send" value ="S"  checked="checked"   onchange="mchange(this);"/>签收时间</li>
						<li  id="takeTime" style="display:none;"><span></span> <input id="dateBegin"
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
						
						
						
						
						
							<li>月结号：<input type="text" id="codName" placeholder="月结号"	value="${params['monthSettleNo']}" name="monthSettleNo"></input> 
	<!--						<input	readonly="readonly" type="text" value="${params['company']}"	placeholder="月结客户公司" name="company" id="company"></input>
							<input	readonly="readonly" type="text" value="${params['name']}"	placeholder="月结客户名称" name="name" id="name"></input>
							<input	readonly="readonly" type="text" value="${params['phone']}"	placeholder="月结客户电话" name="phone" id="phone"></input>
							<input	readonly="readonly" type="text" value="${params['address']}"	placeholder="月结客户地址" name="address" id="address"></input>						-->
							</li>	
							<li><span>运单号：</span><input type="text" placeholder="运单号"	value="${params['orderLgc']}" name="orderLgc"></li>
							<li>收件员：<input type="text" id="revNo" placeholder="收件员"	value="${params['revNo']}" name="revNo"></input> 
							</li>
							<li>派件员：<input type="text" id="sendNo" placeholder="派件员"	value="${params['sendNo']}" name="sendNo"></input> 
							</li>
					
<!-- 					<li> -->
<!-- 					<span>是否有邮箱：</span> <select name ="isEmail" > -->
<!-- 					<option  value="全部">-全部-</option> -->
<%-- 					<option  value="有邮箱"  ${params.isEmail eq '有邮箱' ? 'selected':''}>有邮箱</option> --%>
<%-- 					<option  value="无邮箱"  ${params.isEmail eq '无邮箱' ? ' selected':''}>无邮箱</option>				 --%>
<!-- 					</select>								 -->
<!-- 					</li> -->
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
					<input class="button input_text  big gray_flat" type="submit"
						   value="刷新" id="reload_btn" />
				</div>
				<shiro:hasPermission name="DEFAULT">
					<div class="search_new">
						<input class="button input_text  big gray_flat" type="submit"
							   value="导出" id="derive_btn" />
					</div>
					<div class="search_new"> 
						<input class="button input_text  big gray_flat"  style="width:200px;"type="submit"
							   value="批量发送邮件" id="email_send" />
					</div>
				</shiro:hasPermission>	
			</div>
		</div>
		<!-- tableble_search end  -->
		</div>


	<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
				<thead>
					<tr>
					<th  width="50" align="center">&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;
					</th>
						<th width="50" >运单号</th>
						<th width="30" align="center">寄件日期</th>
						<th width="50" align="center">月结客户</th>				
<!-- 						<th width="50" align="center">邮箱</th>				 -->
						<th width="30" align="center">寄件人</th>
						<th width="10" align="center">重量</th>
						<th width="80" align="center">目的地</th>
						<th width="30" align="center">签收时间</th>
						<th width="50" align="center">签收人</th>
						<th width="50" align="center">寄付月结</th>
						<th width="50" align="center">到付月结</th>
						<th width="50" align="center">应收费用</th>
						<th width="50" align="center">折扣费用</th>
						<th width="50" align="center">实收费用</th>
						<th width="50" align="center">收件员</th>
						<th width="50" align="center">派件员</th>
						<th width="50" align="center">是否已发送邮件</th>						
						<th width="80" align="center">备注</th>
						<th width="50" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${monthList.list}" var="item" varStatus="status">
						<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						  <td><input class="ids " name="ids"   type="checkbox" value="${item.lgcOrderNo}" /></td>
							<td>${item.lgcOrderNo}</td>
							<td>${item.takeOrderTime}</td>
							<td>${item.contactName}</td>										
<%-- 							<td>${item.email}</td>										 --%>
							<td>${item.sendName}</td>
							<td>${item.itemWeight}</td>
							<td>${item.revArea}</td>
							<td>${item.completeTime}</td>
							<td>${item.revName}</td>
							<td>${item.takeMoney}</td>
							<td>${item.sendMoney}</td>
							<td>${item.mpay}</td>
							<td>${item.remainderMoney}</td>
							<td>${item.discountMoney}</td>
							<td>${item.realName1}</td>
							<td>${item.realName2}</td>
							<td><c:choose>
										<c:when test="${item.isEmail eq '1'}">已发送</c:when>
										<c:otherwise>未发送</c:otherwise>						
							</c:choose></td>			
							<c:set var="context" value="${item.monthNote}" />
							<td><a onclick="showAddr(this)">${fn:substring(context,fn:length(context)-12,fn:length(context))}</a>
							<div class="out_address">${item.monthNote}</div></td>
							<td><a href="javascript:void();" onclick="month('${item.lgcOrderNo}','${item.monthNote}');">备注</a></td>
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
						<td>${allTake}</td>
						<td>${allSend}</td>
						<td>${allMpay}</td>
						<td>${allRemainderMoney}</td>
						<td>${allDiscountMoney}</td>			
						<td></td>
						<td></td>
						<td></td>
						<td></td>
			
					
					</tr>		
					
				</tbody>
			</table>
		</div>
		<div id="page">
			<pagebar:pagebar total="${monthList.pages}"
							 current="${monthList.pageNum}" sum="${monthList.total}" />
		</div>
	</body>

</html>