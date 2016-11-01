<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<link rel="stylesheet" href="${ctx}/themes/default/check.css"/>
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/scripts/Sortable.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>

<style>
tr{height:50px;}
.readonly{background:#ddd;}
.tableble_table tr td {border:0px;}
.myinput{height:25px !important;}
</style>
    <script type="text/javascript">
    	$(function(){
    		var api = frameElement.api, W = api.opener;
    		$("#_submit").click(function(){
    			$.ajax({
    				   type: "POST",
    				   url: "${ctx}/judge/sadd",
    				   data: {"labelName":$("#_labelName").val(),"id":$("#_id").val()},
    				   success: function(msg){
    				     alert(msg );
    				     setTimeout(function(){
    				    	 api.close();
    				    	 location.reload();
    				     }, 1000)
    				   }
    				});
    		});
    	});
    </script>
</head>
<body>
  <table width="300px" style="margin:10px auto;background:none;" border="0" cellspacing=0 cellpadding=0 >
  	<tr>
  		<td>标签</td>
  		<td>
  		<input type="hidden" value="${judgeLabel.id }" name="id" class="myinput" style="width:200px;" id="_id"/>
  		<input type="text" name="labelName" class="myinput" style="width:200px;" id="_labelName" value="${judgeLabel.labelName}"/>
  		</td>
  	</tr>
  	<tr>
  		<td colspan="2" >
  			<input  type="button" id="_submit" value="提交" style="width:200px;height:30px;margin-top:30px;background:#0866c6;color:#ffffff;border:0;margin-left:34px;"/>
  		</td>
  	</tr>
  </table>
</body>
</html>