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
.cursor{cursor:pointer;}
.left{float:left;}
.right{float:right;}
.notice_main{width:45%;border:1px solid #dfdfdf;border-radius:5px;padding:10px;padding-bottom:20px;backgroud:#fff;float:left;margin:0 3px;height:850px;}
.notice_main .title_div .icon{width:17px;cursor:pointer;margin:0 3px;}
.notice_main .title_div{height:30px;line-height:30px;padding-bottom:10px;border-bottom:1px solid #cdcdcd;}
.notice_main .title_div .left{float:left;font-size:16px;font-weight:500;}
.notice_main .title_div .right{float:right;}
.notice_main .title{height:40px;line-height:40px;border-bottom:1px solid #cdcdcd;font-size:15px;}
.notice_main  .title_icon{width:16px;margin:0 3px;position:relative;top:3px;}
.color_red{color:#e84f51;}
.pager_bar{margin-top:20px;}
</style>
    <script type="text/javascript">
    	function add(){
	    	$.dialog({lock: true,title:'消息新增',drag: false,width:700,height:600,resize: false,max: false,content: 'url:${ctx}/notice/init?layout=no',close: function(){
	   			 location.reload();
	   	 	 }});
    	}
    	
    	function editInit(id){
	    	$.dialog({lock: true,title:'消息修改',drag: false,width:700,height:600,resize: false,max: false,content: 'url:${ctx}/notice/editInit?id='+id+"&layout=no",close: function(){
	   			 location.reload();
	   	 	 }});
    	}
    	
    	function deleteById(id){
    		if(confirm("确认删除该条消息吗？")){
	    		if(id==''){
	    			alert("id错误");
	    		}
	    		$.ajax({
	    			type: "POST",
					url: "${ctx}/notice/delete",
	    			data: "id="+id,
	    			success: function(msg){
	    				 
	    			     alert(  msg );
	    			     location.reload();
	    			   }   			
	    		});
    		}
    	}
    	function reload(){
    		 location.reload();
    	}
    	
    	function detail(id){
    		$.dialog({lock: true,title:'详情',drag: false,width:800,height:600,resize: false,max: false,content: 'url:${ctx}/notice/detail?id='+id+"&layout=no",close: function(){
	   			 
	   	 	 }});
    	}
    	
    	$(function(){
    		$(".pagebar a").click(function(){
    			var link = $(this).attr("tt");
    			window.location.href="${ctx}/notice/list"+link;
    		});
    		$("#pageGo").click(function(){
    			var curPage = $(".pagebar span[class=current]").text();
    			var v = $(".pageText").val();
    			var total = "${total}";
    			if(parseInt(v)>=parseInt(total)){
    				v = total;
    				$(".pageText").val(v);
    			}
    			if(parseInt(v)<=0){
    				v = 1;
    				$(".pageText").val(v);
    			}
    			if(parseInt(curPage)!=parseInt(v)){
    				window.location.href="${ctx}/notice/list?p="+v;
    			}else{
    				return false;
    			}
    		});
    		
    	})
    </script>
</head>
<body>
  
    <div class="notice_main" style="background:#fff;">
       <div class="title_div">
       		<div class="left">推送给快递员</div>
       		<div class="right">
       		   <shiro:hasPermission name="NOTIFY_LIST">
       			<img src="${ctx }/statics/images/plusSign.png" alt="" class="icon" onclick="add()"/>
       			</shiro:hasPermission>
       			<img src="${ctx }/statics/images/refresh.png" alt="" class="icon" onclick="reload()"/>
       		</div>
       		<div class="clear_fix"></div>
       </div>
       <ul>
		    <c:forEach items="${noticeList.list }" var="notice">
				<li class="title">
				    <ul>
				      <li style="display:inline-block;" calss="left">
					      	<c:if test="${notice.isSend=='0' }">
							<img src="${ctx }/statics/images/u46.png" alt="" class="title_icon"/>
							</c:if>
							<c:if test="${notice.isSend=='1' }">
								<img src="${ctx }/statics/images/u9.png" alt="" class="title_icon"/>
							</c:if>
							<a href="javascript:detail(${notice.id})">
								<c:if test="${notice.isRedTitle=='1'}">
									<span class="color_red">${notice.title }</span>
								</c:if>
								<c:if test="${notice.isRedTitle=='0'}">
									<span style="color:#0c5fbf">${notice.title }</span>
								</c:if>
							</a>
				      </li>
				      <li style="display:inline-block;" class="right">
				      	<fmt:formatDate value="${notice.lastUpdateTime }" pattern="MM-dd"/>
				      	 <shiro:hasPermission name="NOTIFY_LIST">
				      	<img src="${ctx }/statics/images/delete.png" alt="删除" class="title_icon cursor" onclick="deleteById(${notice.id})"/>
				      	<img src="${ctx }/statics/images/u92.png" alt="编辑" class="title_icon cursor" onclick="editInit(${notice.id})"/>
				      	</shiro:hasPermission>
				      </li>
				      <li class="clear_fix"></li>
				    </ul>
	          	</li>
			</c:forEach>
		</ul>
		
    </div>
    
    
    
    <div class="notice_main" style="background:#fff;">
       <div class="title_div">
       		<div class="left">系统推送消息</div>
       		<div class="right">
       		</div>
       </div>
       <ul>
		    <c:forEach items="${noticeList1.list}" var="notice">
				<li class="title">
				    <ul>
				      <li style="display:inline-block;" calss="left">
							<img src="${ctx }/statics/images/u9.png" alt="" class="title_icon"/>
							<a href="javascript:detail(${notice.id})">
								<c:if test="${notice.isRedTitle=='1'}">
									<span class="color_red">${notice.title }</span>
								</c:if>
								<c:if test="${notice.isRedTitle=='0'}">
									<span style="color:#0c5fbf">${notice.title }</span>
								</c:if>
							</a>
				      </li>
				      <li style="display:inline-block;" class="right">
				      	<fmt:formatDate value="${notice.lastUpdateTime }" pattern="MM-dd"/>
				      	<shiro:hasPermission name="NOTIFY_LIST">
				      	<img src="${ctx }/statics/images/delete.png" alt="删除" class="title_icon cursor" onclick="deleteById(${notice.id})"/>
				      	</shiro:hasPermission>
				      </li>
				      <li class="clear_fix"></li>
				    </ul>
	          	</li>
			</c:forEach>
		</ul>
		
    </div>
	<div class="clear_fix"></div>
    <div>
      <div class="pager_bar" id="pager_bar">
			<pagebar:pagebar total="${noticeList.pages<noticeList1.pages?noticeList1.pages:noticeList.pages}"  
			current="${noticeList.pageNum<noticeList1.pageNum?noticeList1.pageNum:noticeList.pageNum}" 
			sum="${noticeList.total<noticeList1.total?noticeList1.total:noticeList.total}" />
		</div>
    </div>
</body>
</html>