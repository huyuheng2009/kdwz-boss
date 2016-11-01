<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
$(function(){
	//trHover('t2');
	$('#reload_btn').click(function(){
		 location.reload()
	});
	$('#exportData').click(function(){
		if('${(empty total.total_count) ? 0:total.total_count}'>=20000){
			alert('数据列表记录太多，请修改查询条件');
			return;
		}
		var action= $("form:first").attr("action");
        $("form:first input[name=serviceName]").val('exportTrans');
	   $("form:first").attr("action","${ctx}/trans/export").submit();
	   $("form:first").attr("action",action);
	});
    $('#exportSettle').click(function(){
        if('${(empty total.total_count) ? 0:total.total_count}'>=20000){
            alert('数据列表记录太多，请修改查询条件');
            return;
        }
        var action= $("form:first").attr("action");
        $("form:first input[name=serviceName]").val('exportSettle');
        $("form:first").attr("action","${ctx}/trans/export").submit();
        $("form:first").attr("action",action);
    });
});

function show(id,merchantNo,voucherNo){
	$.dialog({lock: true,title:'交易详情',drag: true,width:760,height:350,resize: false,max: false,content: 'url:${ctx}/trans/detail?id='+id+'&merchantNo='+merchantNo+'&voucherNo='+voucherNo+'&layout=no',close: function(){
	 }});
}

function viewsignature(create_time,terminal_no,acq_batch_no,acq_voucher_no){
    $.dialog({lock: true,title:'签名',drag: true,width:760,height:350,resize: false,max: false,content: 'url:${ctx}/trans/viewsignature?create_time='+create_time+'&terminal_no='+terminal_no+'&acq_batch_no='+acq_batch_no+'&acq_voucher_no='+acq_voucher_no+'&layout=no',close: function(){
    }});
}
 
</script>
</head>
<body>

	<div class="search">
		<form:form id="trans" action="${ctx}/default" method="get">
            <input type="hidden" name="serviceName" value=""/>
			<ul>
			<li>交易来源：<select name="merchantType" style="width: 110px">
				<option value="">--全部--</option>
				<option value="P" ${params['merchantType'] eq 'P'?'selected':'' }>POS交易</option>
				<option value="M" ${params['merchantType'] eq 'M'?'selected':'' }>手机交易</option>
			</select></li>
			<li>代理名称：<select id="agentNo" name="agentNo" style="width: 110px">
							<option value="">--全部--</option>
							<c:forEach items="${agent}" var="item" varStatus="status">
								<option value="${item.agentNo }" <c:out value="${params['agentNo'] eq item.agentNo?'selected':'' }"/> >${item.agentName }</option>
							</c:forEach>
					</select></li>
                <li>商户类型：<select name="type" style="width: 110px">
                    <option value="">--全部--</option>
                    <option value="餐娱类" ${params['type'] eq '餐娱类'?'selected':'' }>餐娱类</option>
                    <option value="批发类" ${params['type'] eq '批发类'?'selected':'' }>批发类</option>
                    <option value="民生类" ${params['type'] eq '民生类'?'selected':'' }>民生类</option>
                    <option value="一般类" ${params['type'] eq '一般类'?'selected':'' }>一般类</option>
                    <option value="房车类" ${params['type'] eq '房车类'?'selected':'' }>房车类</option>
                    <option value="其他" ${params['type'] eq '其他'?'selected':'' }>其他</option>
                </select></li>
                <li><select name="sk">
						<option value="order_no"
							<c:out value="${params['sk'] eq 'order_no'?'selected':'' }"/>>订单编号</option>
						<option value="merchant_name"
							<c:out value="${params['sk'] eq 'merchant_name'?'selected':'' }"/>>商户名称</option>
						<option value="merchant_no"
							<c:out value="${params['sk'] eq 'merchant_no'?'selected':'' }"/>>商户编号</option>
						<option value="terminal_no"
							<c:out value="${params['sk'] eq 'terminal_no'?'selected':'' }"/>>终端编号</option>
						<option value="card_no"
							<c:out value="${params['sk'] eq 'card_no'?'selected':'' }"/>>交易卡号</option>
						
				</select> <input type="text" value="${params['sv']}" name="sv"></input>
				</li>
				<li><span>交易时间：</span>
			 	<input onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li>
			 <li>交易类型：<select id="transType" name="transType" style="width: 110px">
							<option value="">--全部--</option>
                 <option value="SALE" ${params['transType'] eq 'SALE'?'selected':''}>--消费--</option>
                 <option value="QUERY" ${params['transType'] eq 'QUERY'?'selected':''}>--查询--</option>
                     <option value="ROLLBACK" ${params['transType'] eq 'ROLLBACK'?'selected':''}>--冲正--</option>
							<option value="REFUND" ${params['transType'] eq 'REFUND'?'selected':''}>--退货--</option>
							<option value="REVERSAL" ${params['transType'] eq 'REVERSAL'?'selected':''}>--撤销--</option>
					</select>
				</li>
				<li>交易状态：<select id="transStatus" name="transStatus" style="width: 110px">
							<option value="">--全部--</option>
							<option value="INIT" ${params['transStatus'] eq 'INIT'?'selected':''}>--初始化--</option>
							<option value="SUCCESS" ${params['transStatus'] eq 'SUCCESS'?'selected':''}>--已成功--</option>
							<option value="FAIL" ${params['transStatus'] eq 'FAIL'?'selected':''}>--已失败--</option>
					</select>
				</li>
                <li>清算类型：<select name="payType" style="width: 110px">
                    <option value="">--全部--</option>
                    <option value="A" ${params['payType'] eq 'A'?'selected':''}>--A类--</option>
                    <option value="B" ${params['payType'] eq 'B'?'selected':''}>--B类--</option>
                </select></li>
			 	<li><input class="button input_text  medium blue_flat"
					type="submit" value="查询" />
				</li>
			 	<li><input class="button input_text  medium blue_flat"
					type="reset" value="重置" />
				</li>
			</ul>
		</form:form>
		<div class="clear"></div>
	</div>


	<div class="tbdata">
	    <div class="operator">
	    <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> 
	    <shiro:hasPermission name="DEFAULT"> 
	  	  <input class="button input_text  big gray_flat" type="submit" id="exportData" value="导出交易数据" />
	    </shiro:hasPermission>
	      <shiro:hasPermission name="DEFAULT"> 
	  	  <input class="button input_text  big gray_flat" type="submit" id="exportSettle" value="导出结算清单" />
	  	   </shiro:hasPermission>
	    </div>
	    <div class="tip">
			<ul>
				<li>总笔数(消费成功): ${(empty total.total_count) ? 0:total.total_count}笔</li>
				<li>总金额(消费成功):${(empty total.total_amount) ? 0:total.total_amount } 元</li>
				<li>POS端总金额(消费成功):${(empty total.pos_amount) ? 0:total.pos_amount } 元</li>
				<li>POS端A类总金额(消费成功):${(empty total.pos_amount_a) ? 0:total.pos_amount_a } 元</li>
				<li>POS端B类总金额(消费成功):${(empty total.pos_amount_b) ? 0:total.pos_amount_b } 元</li>
				<li>手机端总金额(消费成功):${(empty total.mobile_amount) ? 0:total.mobile_amount } 元</li>
				<li>手机端A类总金额(消费成功):${(empty total.mobile_amount_a) ? 0:total.mobile_amount_a } 元</li>
				<li>手机端B类总金额(消费成功):${(empty total.mobile_amount_b) ? 0:total.mobile_amount_b } 元</li>
			</ul>
		</div>
		<div id="tbb" style="">
		<table width="100%" cellspacing="0" class="t2 tableble_table" id="t2">
			<thead">
				<tr>
					<th width="3%" align="center">序号</th>
					<th align="center">商户名称</th>
					<th width="8%" align="center">商户类型</th>
					<th width="9%" align="center">终端编号</th>
					<th width="6%" align="center">交易类型</th>
					<th width="15%" align="center">交易卡号</th>
					<th width="6%" align="center">卡类型</th>
					<th width="10%" align="center">交易金额</th>
					<th width="6%" align="center">交易状态</th>
					<th width="12%" align="center">创建时间</th>
					<th width="6%" align="center">操作</th>
				</tr>
			</thead>
			<tbody">
				<c:forEach items="${list.content}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td><a href="${ctx }/merchant/mview?mno=${item.merchant_no}">${item.merchant_name}</a></td>
						<td>${item.type}</td>
						<td>${item.terminal_no}</td>
						<td><u:dict name="trans_type" key="${item.trans_type}" /></td>
						<td><u:cardNoHidden cardNo="${item.card_no}" /></td>
						<td><u:dict name="CARD_TYPE" key="${item.card_type}" /></td>
						<td>${item.trans_amount} 元</td>
						<td><span title='<c:if test="${item.acq_response_code !=null }">
						${item.acq_response_code}(<u:substring length="19" content="${item.acq_response_msg }" />)
						</c:if>'>
							<c:choose>
								<c:when test="${item.trans_status ==  'INIT' }">
									<span style="color:red"><u:dict name='trans_status' key='${item.trans_status}'/></span>
								</c:when>
								<c:otherwise>
									<u:dict name='trans_status' key='${item.trans_status}'/>
								</c:otherwise>
							</c:choose>
						 </span>
						
						
						</td>
						<td><fmt:formatDate   value="${item.create_time}"   pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
							<td align="center">
                            <c:if test="${item.merchant_type=='M' &&item.trans_type=='SALE' &&  item.trans_status=='SUCCESS' }">
                                <a href="javascript:viewsignature('${item.create_time}','${item.terminal_no}','${item.terminal_batch_no}','${item.terminal_voucher_no}')">查看签名</a>
                                |
                            </c:if>
							<shiro:hasPermission name="DEFAULT">
								<a href="javascript:show('${item.id}','${item.merchant_no}','${item.terminal_voucher_no}');">详情</a>
							</shiro:hasPermission>
							</td>
					</tr>
				</c:forEach>
			</tbody>
		</table></div>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.totalPages}"
			current="${list.number + 1}" sum="${list.totalElements}"/>
	</div>
</body>

</html>