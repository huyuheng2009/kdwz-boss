<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    function adds(){
   	 var lgcNo =  $('select[name=lgcNo]').val() ;  
   	 var lgcOrderNo = $.trim($('input[name=lgcOrderNo]').val()) ;
   	 var substationNo =  $('select[name=substationNo]').val() ;  
   	 var c =  parseInt($('#tips').attr("name"))+1  ;
   	 if(lgcOrderNo.length<5||lgcOrderNo.length>15){
   		 alert("请输入正确的快递单号");
   		 $('input[name=lgcOrderNo]').focus(); 
   	      return false ;	   
   	 }
   	 if(substationNo.length<1){
   		 alert("请选择分站");
   	      return false ;	   
   	 }
   	   $.ajax({
	   			 type: "post",//使用get方法访问后台
	   	            dataType: "json",//返回json格式的数据
	   	            url: "${ctx}/scan/srev",//要访问的后台地址
	   	            data: {'lgcNo':lgcNo,'lgcOrderNo':lgcOrderNo,'substationNo':substationNo},//要发送的数据
	   	            success: function(data){//msg为返回的数据，在这里做数据绑定
	   	            	if(data.ret==1){
	   	            	 $("#tb1").prepend('<tr><td align="center">'+lgcOrderNo+'</td><td align="center">'+data.track.preNo+'</td><td align="center">'+data.track.preType+'</td><td align="center"></td></tr>');
	   	                 $('#tips').html("已扫描，共扫描"+c+"条记录");
	   	                 $('#tips').attr("name",c);
	   	            	}else if(data.ret==2){
	   	            	 $('#tips').html(lgcOrderNo+"该单号已经处理过了");
	   	            	}else{
	   	            	alert(data.ret);
	   	            	}
	   	            	
	   	            }
	   		 }); 
   $('input[name=lgcOrderNo]').val("");	  
   $('input[name=lgcOrderNo]').focus(); 
   return false ;	   
   	   
   }
   
   $(function(){
	   trHover('t2');
   	 $('input[name=lgcOrderNo]').focus(); 
   	 var lgcNo =  $('select[name=lgcNo]').val() ;  
    	$("."+lgcNo).css("display","block");
}); 
   
function chl(lgcNo){
          $('#substationNo').prop('selectedIndex', 0);
           $(".s_none").css("display","none");
  		$("."+lgcNo).css("display","block");
} ;       
    

    </script>
</head>
<body>
<div class="search">
    <form:form id="trans" action=""  onsubmit="return adds();">
        <ul style="margin-left:20px;"> 
        <li>快递公司：<select id="lgcNo" name="lgcNo" style="width: 110px" onchange="chl(this.value);">
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
					</select></li>
            <li>运单号：<input type="text" id="lgcOrderNo" name="lgcOrderNo" size=30></input></li>
            <li>到达站点：<select id="substationNo" name="substationNo" style="width: 220px">
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option style="display: none;"  value="${item.substation_no }" id="${item.substation_name }"  class="s_none ${item.lgc_no}">${item.substation_name }</option>
							</c:forEach>
					</select></li>
             <input type="hidden" name="layout" value="no"/>
            <li><input class="button input_text  medium blue_flat" type="submit" value="到件"/>
            </li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>
<div style="width:100%;height: 30px;line-height: 30px;text-align: center;color: red;" id="tips" name="0" value="0"></div>
<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="6%" align="center">运单号</th>
            <th width="12%" align="center">上一站编号</th>
            <th width="12%" align="center">上一站名称</th>
            <th width="12%" align="center">操作</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
</div>
</body>

</html>