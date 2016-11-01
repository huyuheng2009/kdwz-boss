<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
	 
		function add(name){
			 $.ajax({
				 type: "post",//使用get方法访问后台
		            dataType: "text",//返回json格式的数据
		            url: "${ctx }/forcpn/save",//要访问的后台地址
		            data: {'cpnName':name},//要发送的数据
		            success: function(msg){//msg为返回的数据，在这里做数据绑定
		            	if(msg==0){
		            		alert('数据有误');
		            	}else if(msg==1){
		            		alert('添加成功');
		            	}else{
		            		alert('数据有误');
		            	}
		            }
			 });

		}

 
</script>
</head>
<body>
	<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">转出公司</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td align="center">${item.name}</td>
                <td align="center">
                  <a href="javascript:add('${item.name}');">添加</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>