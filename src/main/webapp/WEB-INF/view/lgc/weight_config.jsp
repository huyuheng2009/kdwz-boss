<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
$(function(){
	var api = frameElement.api, W = api.opener;
	$("#save").click(function(){
		var rev_minv =$("input[name=rev_minv]").val();
		var warehouse_minv =$("input[name=warehouse_minv]").val();
		var select_v=$("select[name=select_v] option:selected").val();
		$.ajax({
			   type: "POST",
			   url: "${ctx }/lgc/weightConfEdit",
			   data: {rev_minv:rev_minv,warehouse_minv:warehouse_minv,select_v:select_v},
			   success: function(msg){
			   		if(msg=="1"){
			   		 alert('保存成功');
			   		 api.close();
					 location.reload();
			   		}
			   }
			});
	})
	
	
})


</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
		   <form action="${ctx }/lgc/weightConfEdit" method="post">
			<div class="content">
					 	<div class="item">
				 			<ul style="padding-top:30px;">
					 			<li style="width:100%;">
					 				<div style="width:120px;float:left;">收件最小重量：</div>
					 				<input  maxlength="20" type="text" name="rev_minv" value='<fmt:formatNumber value="${weightConfig.rev_minv }" pattern="0.00"></fmt:formatNumber>'  style="width: 300px"></input>&nbsp;kg
					 				<div class="clear"></div>
					 			</li>
					 			<li style="width:100%;">
					 				<div style="width:120px;float:left;">中心入仓最低重量：</div>
					 				<input  maxlength="20" type="text" name="warehouse_minv" value='<fmt:formatNumber value="${weightConfig.warehouse_minv }" pattern="0.00"></fmt:formatNumber>' style="width: 300px"></input>&nbsp;kg
					 				<div class="clear"></div>
					 			</li>
					 			<li style="width:100%;">
					 				<div style="width:120px;float:left;">加盟账单重量：</div>
					 				<select style="width:307px;height:23px;" name="select_v">
					 					<option value="0" <c:out value="${weightConfig.select_v eq '0'?'selected':'' }"></c:out> >以收件为准</option>
					 					<option value="1" <c:out value="${weightConfig.select_v eq '1'?'selected':'' }"></c:out>>中心入仓重量为准</option>
					 					<option value="2" <c:out value="${weightConfig.select_v eq '2'?'selected':'' }"></c:out>>收件入仓以重的为准</option>
					 				</select>
					 				<div class="clear"></div>
					 			</li>
				 			</ul>
					 	</div>
					 	<div class="operator"  style="padding-top: 8px">
					 	<shiro:hasPermission name="DEFAULT">
				 		<input class="button input_text  big blue_flat" type="button" id="save" value="保存" />
				 		</shiro:hasPermission>
				 	</div>
				</div>
		</form>
    </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
 
  
</div>


</body>

</html>