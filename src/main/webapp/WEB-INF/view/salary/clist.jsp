<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
   <script type="text/javascript">
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
                 
            $('#add1').click(function(){
            	$.dialog({
					width: '500px',
					height: '180px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '新增工资增加项',
					content: "url:<%=request.getContextPath()%>/salary/add1?layout=no",
					lock: true
				});      
          	});  
  		 $('#add2').click(function(){
	   			$.dialog({
	   				width: '500px',
					height: '180px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '新增工资扣除项',
					content: "url:<%=request.getContextPath()%>/salary/add2?layout=no",
					lock: true	  				
	   			});	   
   });
                          	
        });      
       function del(name,id,type){
    	   var flg = false;
    	  if(type=='1'){
    		  if(confirm('确认要删除工资增加项['+name+']吗？删除后将无法恢复！')){
    			  flg = true;
    		  }		  
    	  }
    	  if(type=='2'){
    		  if(confirm('确认要删除工资扣除项['+name+']吗？删除后将无法恢复！')){
    			  flg = true;  
    		  }		   
    	  }
    	   if(flg){
    	  $.ajax({
				 type: "post",//使用get方法访问后台
		           dataType: "json",//返回文本格式
		           url: "<%=request.getContextPath()%>/salary/del",//要访问的后台地址
				data : {
					'id' : id			
				},//要发送的数据
				beforeSend : function() { //请求成功前触发的局部事件
					alert("正在努力请求数据，请稍候......");
				},
				success : function(msg) {//msg为返回的数据，在这里做数据绑定         						
					if (msg.code == 0) {
						alert(msg.message, '', function() {
							location.reload();					
						})
					} else if (msg.code == 1) {
						alert(msg.message);
					} else {
						alert('删除失败', '', function() {
							location.reload();					
						})
					}
				}
			});   	   
    	   } 	     	   
       }
        function change(id){
        	$.dialog({
   				width: '500px',
				height: '180px',
				max: false,
				min: false,
				drag: false,
				resize: false,
				title: '工资项修改',
				content: "url:<%=request.getContextPath()%>/salary/change?layout=no&id="+id,
				lock: true	  				
   			});	   
        	
        	
        	
        }
        
        
    </script>
</head>
<body>
<div class="online_cont">
  
  <div class="salary_cont">
    <div class="salary_cli left">
      <div class="table">
        <div class="salary_tit">工资增加项</div>
        <div class="ta_header">
          <div class="salary_add">
            <a href="#" class="" id="add1">新增</a>
          </div>
        </div>
        <div class="ta_table" style="margin-left:20px;">
          <table class="ta_ta" width="96%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <th>序号</th>
              <th>名称</th>              
              <th>操作</th>        
            </tr>
            
         <c:forEach items="${inList}" var="item0" varStatus="status0">  
          <tr>
              <td>${status0.count}</td>
              <td>${item0.cn_name}</td>
              <td><a href="#" onclick="change('${item0.id}');">编辑</a></td>        
            </tr>   
        </c:forEach> 
            
          </table>
        </div>
      </div>
     <!--  <div class="salary_tips">
          <p>1.&nbsp;&nbsp;所发生的房贷首付多少个地方</p>
      </div> -->
    </div>
    <div class="salary_cli right">
      <div class="table">
        <div class="salary_tit">工资扣除项</div>
        <div class="ta_header">
          <div class="salary_add">
            <a href="#" class=""id="add2">新增</a>
          </div>
        </div>
        <div class="ta_table" style="margin-left:20px;">
          <table class="ta_ta" width="96%" cellpadding="0" cellspacing="0" border="0">
            <tr>
              <th>序号</th>
              <th>名称</th>              
              <th>操作</th>        
            </tr>
            <c:forEach items="${outList}" var="item1" varStatus="status1">  
          <tr>
              <td>${status1.count}</td>
              <td>${item1.cn_name}</td>
              <td><a href="#" onclick="change('${item1.id}');">编辑</a></td>        
            </tr>   
        </c:forEach> 
          </table>
        </div>
      </div>
    </div>
  </div>
<!--   <div class="online_button"> -->
<!--   	 <input type="submit" class="" value="保存"> -->
<!--   </div> -->
</div>
</body>

</html>