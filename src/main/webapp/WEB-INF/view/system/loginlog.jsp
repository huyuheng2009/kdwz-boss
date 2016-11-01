<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<div class="search">
  <div class="tableble_search">
        <div class=" search_cont">
		<form:form id="trans" action="${ctx}/system/log" method="get">
			<ul>
			<li>操作关键字: <input type="text" value="${params['operation']}" name="operation"></input></li>
			      <li><span>操作时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="last_login_time_begin" value="${params['last_login_time_begin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="last_login_time_end" value="${params['last_login_time_end']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			    </li>
			 	<li><input class="button input_text  medium blue_flat"
					type="submit" value="查询" />
				</li>
			 	<li><input class="button input_text  medium blue_flat"
					type="reset" value="重置" />
				</li>
			</ul>
		</form:form>
		 </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  
  
	</div>
	<div class="tbdata">
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="50" align="center">序号</th>
					<th align="center">用户真实姓名</th>
					<th align="center">登入IP</th>
					<th align="center">地址位置</th>
					<th align="center">操作</th>
					<th align="center">登入时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.user_name }</td>
						<td>${item.login_ip }</td>
						<td>${item.location }</td>
						<td>${item.operation }</td>
						<td><fmt:formatDate value="${item.last_login_time}"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
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