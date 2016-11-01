<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/scripts/Sortable.js"></script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>

<style>
.main_div{width:600px;margin:10px 0;}
.left{float:left;width:260px;margin:10px 10px;}
.right{float:right;}
.clear{clear:both;}
.cursor{cursor:move;}
.title{text-align:center;font-size:16px;}
.txt-center{text-align:center;padding-top:10px;}
ul{width:250px;margin:0 auto;}
ul li{margin-left:5px;cursor:move;}
.divborder{border:1px solid #ddd;border-radius:5px;margin:0 30px;font-size:15px;height:400px;overflow-x:hidden;overflow-Y:scroll;}
.btn-div{text-align:center;padding-top:10px;}
.btn-div input{margin-left:30px;width:80px;height:25px;margin-top:30px;background:#28a1e6;border-radius:3px;border:1px solid #666;}
</style>
    <script type="text/javascript">
    	$(function(){
    		var api = frameElement.api, W = api.opener;
    		var e1 = document.getElementById('ulshow');
    		new Sortable(e1,{
				onStart:function(evt){ evt.item.style.background="#b2c4d6";},
				onEnd: function(evt){ evt.item.style.background="#f5f5f5";}
    		});
    		var e2 = document.getElementById('ulhidden');
    		new Sortable(e2,{
				onStart:function(evt){ evt.item.style.background="#b2c4d6";},
				onEnd: function(evt){ evt.item.style.background="#f5f5f5";}
    		});
    		
    		
    		
    			$("li").each(function(){
    				$(this).bind("dblclick",function(){
        				$(this).parents(".leftul").siblings(".leftul").find("ul").append($(this));
        				changeColor("#ulshow li","0");
        				changeColor("#ulhidden li","1");
        			});
    			});
    			
    			function changeColor(selector,status){
    				$(selector).each(function(){
        				if($(this).attr("status")==status){
        					$(this).css("color","red");
        				}else{
        					$(this).css("color","#333333");
        				}
        			});
    			}
    			
    		
    		$("#close").click(function(){
    			api.close();
    		});
    		$("#back").click(function(){
    			$.ajax({
    				   type: "POST",
    				   url: "${ctx}/tabelFiled/delete",
    				   data: "tab=${tab}",
    				   success: function(msg){
    				     alert( msg );
    				     window.location.reload();
    				   }
    				});
    		});
    		
    		$("#save").click(function(){
    			var str ="";
    			var index=0;
    			$("#ulshow li").each(function(){
    				str+=$(this).attr("value")+",1,"+index+","+$(this).text()+";";
    				index++;
    			});
    			$("#ulhidden li").each(function(){
    				str+=$(this).attr("value")+",0,"+index+","+$(this).text()+";";
    				index++;
    			});
    			$.ajax({
    				   type: "POST",
    				   url: "${ctx}/tabelFiled/addOrUpdate",
    				   data: "tab=${tab}&tabName=${tabName}&filed="+str,
    				   success: function(msg){
    				     alert( msg );
    				   }
    				});
    		});
    		
    	})
    	
    </script>
</head>
<body>
  <div class="main_div" id="multi">
  		<div>
  			<div class="left leftul">
  				<p class="title">隐藏列</p>
  				<div class="divborder">
  				<ul id="ulhidden">
  					<c:forEach items="${tableFieldSorts}" var="item">
  						<c:if test="${item.isShow==0 }">
  							<li value="${item.col }" status="0">${item.colName }</li>
  						</c:if>
  					</c:forEach>
  				</ul>
  				</div>
  				<div class="txt-center">通过鼠标双击调整显示/隐藏字段</div>
  			</div>
  			<div class="left" style="width:20px;">
  				
  			</div>
  			<div class="left leftul">
  				<p class="title">显示列</p>
  				<div class="divborder">
  				<ul id="ulshow">
  					<c:forEach items="${tableFieldSorts}" var="item">
  						<c:if test="${item.isShow==1 }">
  							<li value="${item.col }" status="1">${item.colName }</li>
  						</c:if>
  					</c:forEach>
  				</ul>
  				</div>
  				<div class="txt-center">通过鼠标拖动调整字段顺序</div>
  			</div>
  			<div class="clear"></div>
  			<div  class="btn-div">
  				<input type="button" value="恢复默认"style="margin-left:0px;" id="back"/>
  				<input type="button" value="保存" id="save"/>
  				<input type="button" value="取消" style="background:none;" id="close"/>
  			</div>
  		</div>
  </div>
</body>
</html>