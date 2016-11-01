<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>

<style>
.note_c{width: 40%;margin-left: 2%;float: left;border: 1px;border-style: solid;border-color: grey;min-height: 300px;}
.note_c ul{}
.note_c ul li{width:40%;  float: left;padding: 2px 1px ;}


* {
-webkit-text-size-adjust: none;
margin: 0 auto ;
}

.result-info2 {
width: 100%;
border: 1px solid #90bfff;
}
table {
border-collapse: collapse;
border-spacing: 0;
}

tr {
display: table-row;
vertical-align: inherit;
border-color: inherit;
}

.result-info2 .row1 {
width: 140px;
text-align: right;
}
.result-info2 .status {
width: 30px;
background: url("${ctx}/themes/default/images/ico_status.gif") -50px center no-repeat #fbfbfb;
}

.result-info2 .status-first {
background: url("${ctx}/themes/default/images/ico_status.gif") 0px center no-repeat #fbfbfb;
}

.result-info2 .last td {
color: #FF8c00;
border-bottom: none;
background-color: #ffffff !important;
}

.result-info2 .status-onway {
background: url("${ctx}/themes/default/images/ico_status.gif") -100px center no-repeat #fbfbfb;
}

.result-info2 .status-check {
background: url("${ctx}/themes/default/images/ico_status.gif") -150px center no-repeat #fbfbfb;
}
.result-info2 td {
padding: 10px;
color: #878787;
border-bottom: 1px solid #d8d8d8 !important;
background-color: #fbfbfb !important;
}

</style>


<script type="text/javascript">
 
</script>
</head>
<body style="height:90%;padding-top: 3%;">
<div style="width: 55%;float: left;margin-left: 2%;">
	<table id="queryResult2" class="result-info2" cellspacing="0" style="width:100%;">
		<tbody>
		      <tr>
				<td class="row1">快递公司：${lgcName}</td>
			<!-- 	<td >&nbsp;</td> -->
				<td>物流单号：${lgcOrderNo}</td>
			   </tr>
			<c:forEach items="${trackList}" var="item" varStatus="status" >
			<c:if test="${status.count eq f:length(trackList)}">
              <tr class="last">
				<td class="row1">${item.time}</td>
			<%-- 	<td class="status ${ischeck eq 0 ? 'status-onway' : 'status-check'}">&nbsp;</td> --%>
				<td>${item.context}</td>
			   </tr>
           </c:if>
			<c:if test="${status.count ne f:length(trackList)}">
                <tr>
				<td class="row1">${item.time}</td>
				<%-- <td class="status ${status.count eq 1 ? 'status-first' : ''}">&nbsp;</td> --%>
				<td>${item.context}</td>
			   </tr>
            </c:if>
			 </c:forEach>
			
		<!-- 	<tr class="last">
				<td class="row1">2015-01-25 12:55:44</td>
				<td class="status status-onway">&nbsp;</td>
				<td>已签收,签收人是本人</td>
			</tr> -->
		</tbody>
	</table>
		<table id="queryResult2" class="result-info2" cellspacing="0" style="width:100%;">
		<tbody>
			<c:if test="${f:length(take_plane)>1}">
		      <tr>
				<td>
                 <img src="${take_plane}" alt="" width="100%" height="400"/>
                 </td>
			   </tr>
			 </c:if>  
			 <c:if test="${f:length(send_plane)>1}"> 
			   <tr>
				<td >
                 <img src="${send_plane}" alt="" width="100%" height="400"/>
                 </td>
			   </tr>
			   </c:if>   
		</tbody>
	</table>
</div>	
	<div class="note_c" style="">
	<c:forEach items="${noteList}" var="item" varStatus="status">
     <ul>
       <li  style="width:100%;">备注：${item.note}</li>
       <li>备注人：${item.operator}</li>
       <li class="tt"  style="width:58%;">时间：<fmt:formatDate value="${item.create_time}" type="both"/></li>
     </ul>
     <div style="float: left;width: 100%;height: 10px;"></div>
   </c:forEach>

    </div>
</body>

</html>