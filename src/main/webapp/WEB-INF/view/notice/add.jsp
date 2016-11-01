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
ul,li{margin:0px;padding:0px;}
li{margin:0 5px;height:30px;line-height:30px;}
.cursor{cursor:pointer;}
.left{float:left;}
.right{float:right;}
.main_div{margin:25px;}
#title{height:30px;width:560px;}
textarea{max-width:600px;max-height:300px;min-width:600px;min-height:300px;}
.foot li{}
.foot{position:relative;top:300px;}
.foot li{margin:3px 20px;}
.foot li input{padding:3px 10px;}
</style>
    <script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    	function save(flag){
    		var title =$("#title").val();
    		var ischeck = document.getElementById("isRedTitle").checked;
    		var isRedTitle ="0";
    		if(ischeck){
    			isRedTitle="1";
    		}else{
    			isRedTitle="0";
    		}
    		var content = $("#content").val();
    		
    		if(title.length<=0){
    			alert("标题必输");
    			return fasle;
    		}
    		if(content.length<=0){
    			alert("内容必输");
    			return fasle;
    		}
    		
    		

    		$.ajax({
    			type: "POST",
				url: "${ctx}/notice/sadd",
    			data: "title="+title+"&content="+content+"&isRedTitle="+isRedTitle+"&isSend="+flag,
    			success: function(msg){
    				 
    			     alert(  msg );
    			     api.close();
    			   }   			
    		});
    		
    	}
    	
    	function reset(){
    		$("#title,#content").val("");
    		$("#isRedTitle").attr("checked",false);
    	}
    	function exit(){
    		api.close();
    	}
    </script>
</head>
<body>
   <div class="main_div">
   		<ul>
   		   <li class="left">标题</li>
   		   <li class="left"><input type="text" name="title" id="title"/></li>
   		   <li class="clear_fix"></li>
   		</ul>
   		<ul>
   		   <li class="left"><label for="isRedTitle">选项</label></li>
   		   <li class="left"><input type="checkbox" name="isRedTitle" id="isRedTitle" value="1"/><label for="isRedTitle">标题显示为红色</label></li>
   		   <li class="clear_fix"></li>
   		</ul>
   		<ul>
   		   <li> <textarea rows="20" cols="90" name="content" id="content" resize="no" placeholder="请输入60个字符以内"></textarea></li>
   		</ul>
   		<ul class="foot">
   		   <li class="left" style="margin-left:0px;"><input type="button" value="发布" onclick="save(1)"/></li>
   		   <li class="left"><input type="button" value="保存" onclick="save(0)"/></li>
   		   <li class="left"><input type="button" value="重置" onclick="reset()"/></li>
   		   <li class="left"><input type="button" value="退出" onclick="exit()"/></li>
   		   <li class="clear_fix"></li>
   		</ul>
   </div>
</body>

</html>