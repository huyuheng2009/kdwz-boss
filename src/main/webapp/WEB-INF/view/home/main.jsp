<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style>
.clear_fix{clear:both;}
.cursor{cursor:pointer;}
.left{float:left;}
.right{float:right;}
.detail_main_div{margin:10px 60px}
.detail_tc{background: #f8f8f8;border:1px solid #cdcdcd;width:96%;padding: 2%;;min-height:600px;}
.detail_main_div .detail_title{font-size:20px;text-align:center;width:100%;height:50px;line-height:50px;}
.detail_main_div .detail_content,.detail_foot{}
.detail_content{line-height:30px;font-size:15px; }
.detail_foot{}
table{width:100%;margin-top:20px;}
table td{padding:0 3px;}
</style>
    <script type="text/javascript">
    	
    </script>
</head>
<body>
   <div class="detail_main_div">
   		<div class="detail_tc">
   		<div class="detail_title">
   			${notice.title }
   		</div>
   		<div class="detail_content">
   			${notice.content }
   		</div>
   		
   		<div style="text-align:right;margin-top:50px;height:30px;line-height:30px;">
   		发布人：快递王子  &nbsp;&nbsp;发布时间：${notice.createTime }
   		</div>
   		
   		</div>
   		<%--
   		<div class="detail_foot">
   			<table>
   			   <tr>
   			   		<td style="width:60px;">发布人：</td>
   			   		<td style="width:200px;">${notice.pushName }</td>
   			   		<td style="width:100px;">发布时间：</td>
   			   		<td style="width:200px;">
   			   			${notice.createTime }
   			   		</td>
   			   </tr>
   			   <tr>
   			   		<td>修改人：</td>
   			   		<td>${notice.editName }</td>
   			   		<td>最后修改时间：</td>
   			   		<td>
   			   			${notice.lastUpdateTime }
   			   		</td>
   			   </tr>
   			</table>
   		</div>
   		 --%>
   		
   </div>
</body>

</html>