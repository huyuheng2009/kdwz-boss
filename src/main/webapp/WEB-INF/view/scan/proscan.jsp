<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    var c = 0 ;
    function adds(){
   		var descb = $('input[name=descb]').val();
   		var lgcOrderNo = $('input[name=lgcOrderNo]').val();
   		var proType = $('select[name=proType]').val();
   		var d = $('select[name=proType]').find("option:selected").text();
  		if(proType.length<1){
 			 alert('请选择问题原因');
 			 return false;
 		 }
  		if(lgcOrderNo.length<1){
			 alert('请输入运单号');
			 return false;
		 }
  	    /*  if(descb.length<1){
			 alert('请输入详细原因');
			 return false;
		 } */
  		 $.ajax({
  			 type: "post",//使用get方法访问后台
  	            dataType: "json",//返回json格式的数据
  	            url: "${ctx }/porder/psave",//要访问的后台地址
  	            data: {'lgcOrderNo':lgcOrderNo,'proReason':proType,'descb':descb},//要发送的数据
  	            success: function(data){//msg为返回的数据，在这里做数据绑定
  	            	if(data.ret==1){
  	            		c = c - (-1);
	   	            	 var html = '<tr>' ;
	   	            	    html += '<td align="center">'+lgcOrderNo+'</td>';
	   	            	    html += '<td align="center">'+d+'</td>';
	   	                    html += '<td align="center">'+descb+'</td>';
	   	                    html += '<td align="center">'+data.scanName+'</td>';
 	                        html += '<td align="center">'+data.scanTime+'</td>';
	   	                    html += '</tr>';
	   	               $("#tb1").prepend(html) ; 
	   	                 $('#tips').html("已扫描，共扫描"+c+"条记录");
	   	            	}else if(data.ret==2){
	   	            	 $('#tips').html(lgcOrderNo+data.msg);
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
        <li>问题原因： <u:select id="proType" sname="proType" stype="PRO_REASON" value="OTHER"/></li>
        <li style="margin-right: 400px;">详细原因：<input type="text"  name="descb" style="width: 280px"></input></li>		
         <li>&#12288;运单号：
         <input type="text" id="lgcOrderNo" name="lgcOrderNo" size=30 style="width: 495px;"></input></li>
        <li><input class="button input_text  medium blue_flat" type="submit" value="发送"/> </li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>
<div style="width:100%;height: 30px;line-height: 30px;text-align: center;color: red;" id="tips" name="0" value="0"></div>
<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">运单号</th>
            <th align="center">问题原因</th>
            <th align="center">详情</th>
            <th align="center">扫描人</th>
            <th align="center">扫描时间</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
</div>

</body>

</html>