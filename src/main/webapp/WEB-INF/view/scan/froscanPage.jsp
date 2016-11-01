<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    var c = 0 ;
    var list = [] ;
    Array.prototype.contains = function(item){
        return RegExp(item).test(this);
    };

    function submits(){
    	var curLgcOrderNo = $('input[name=curLgcOrderNo]').val();
		 if(curLgcOrderNo.length<2){
			 alert('请输入转出前运单号');
			 return false;
		 }
		 var ioName = $('select[name=ioName]').val();
		 if(ioName.length<2){
			 alert('请输入转出后快递公司名称');
			 return false;
		 }
		 var ioLgcOrderNo = $('input[name=ioLgcOrderNo]').val();
		 if(ioLgcOrderNo.length<2){
			 alert('请输入转出后运单号');
			 return false;
		 }
		 if(list.contains(curLgcOrderNo)){
			 alert('此单号已扫描过了');
			 return false;
		 }
		 var note = $('input[name=note]').val();
  		 $.ajax({
  			 type: "post",//使用get方法访问后台
  	            dataType: "json",//返回json格式的数据
  	          url: "${ctx }/scan/foscanSave",//要访问的后台地址
	            data: {'ioName':ioName,'ioLgcOrderNo':ioLgcOrderNo,'curLgcOrderNo':curLgcOrderNo,'note':note},//要发送的数据
  	            success: function(data){//msg为返回的数据，在这里做数据绑定
  	            	if(data.ret==1){
  	            		c = c - (-1);
  	            		list.push(curLgcOrderNo);
	   	            	 var html = '<tr>' ;
	   	            	    html += '<td align="center">'+curLgcOrderNo+'</td>';
	   	            	    html += '<td align="center">'+ioLgcOrderNo+'</td>';
	   	                    html += '<td align="center">'+ioName+'</td>';
	   	                    html += '<td align="center">'+note+'</td>';
	   	                    html += '<td align="center">'+data.scanName+'</td>';
	                        html += '<td align="center">'+data.scanTime+'</td>';
	   	                    html += '</tr>';
	   	               $("#tb1").prepend(html) ; 
	   	                 $('#tips').html("已扫描，共扫描"+c+"条记录");
	   	                 $('#piao').html(c);
	   	            	}else{
	   	            	alert(data.ret,$('input[name=curLgcOrderNo]'),function(obj){
	   	                    $('input[name=curLgcOrderNo]').focus(); 
	   	                 return false ;	 
	   	            	});
	   	            	}
  	            }
  		 });
   $('input[name=curLgcOrderNo]').val("");	  
   $('input[name=ioLgcOrderNo]').val("");
   $('input[name=curLgcOrderNo]').focus(); 
     return false ;	   
   	   
   }
   
   $(function(){
	   trHover('t2');
   	 $('input[name=curLgcOrderNo]').focus(); 
   	 
  	$("#submit").click(function(e){ 
  		submits() ;
  	});
   	 
     var $inp = $('input:text');
     $inp.bind('keydown', function (e) {
    	 if(e.which == 13) {
    		 var classv = $(this).attr("class") ;  
    		   if(classv.indexOf("required")>=0){
                  if($(this).val().length<1){
            			 alert("必填项，不允许为空",$(this),function(obj) {
            				$(obj).focus(); 
           			     });
            			 return ;
                  }
    		   }
    		   if(classv.indexOf("submit")>=0){
    			   $("#submit").trigger("click"); 
    			   return ;
    		   }
            
    		   e.preventDefault();
               var nxtIdx = $inp.index(this) + 1;
               $(":input:text:eq(" + nxtIdx + ")").focus(); 
    		   
    	 }
     });
   	 
}); 
   

    </script>
</head>
<body>
<div class="search">
        <ul style="margin-left:20px;"> 
        <li><div class="shou_li"><b style="color: red;">★</b>转出公司：<select class="required" id="ioName" name="ioName" style="width: 154px">
							<c:forEach items="${list.list}" var="item" varStatus="status">
								<option value="${item.cpn_name }" />${item.cpn_name }</option>
							</c:forEach>
					</select>
        </li>
        <li style="margin-right: 600px;">&#12288;&#12288;&#12288;备注： <input  maxlength="30" type="text" name="note" style="width: 150px"></input> </input></li>		
        <li>&#12288;<b style="color: red;">★</b>运单号：<input class="required" maxlength="30" type="text" name="curLgcOrderNo"  style="width: 150px"></input></li>		
         <li>&#12288;<b style="color: red;">★</b>转出单号：<input class="required submit"  maxlength="20" type="text" name="ioLgcOrderNo" style="width: 150px"></input></li>
        <li><input class="button input_text  medium blue_flat" type="submit" id="submit" value="发送"/> 
       </li> <span style="color: red;font-size: 30px;margin-left:120px;float: left; " id="piao">0</span>
        </ul>
    <div class="clear"></div>
</div>
<div style="width:100%;height: 30px;line-height: 30px;text-align: center;color: red;" id="tips" name="0" value="0"></div>
<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">运单号</th>
            <th align="center">转出单号</th>
            <th align="center">转出公司</th>
            <th align="center">备注</th>
            <th align="center">扫描员</th>
            <th align="center">扫描时间</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
</div>

</body>

</html>