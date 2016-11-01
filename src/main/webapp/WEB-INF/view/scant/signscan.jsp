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
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
    <script type="text/javascript">

    $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
       		 location.reload();
       	});
         var $inp = $('input:text');
         var piao = 0 ;
         $inp.bind('keydown', function (e) {
         	 if(e.which == 13) {
            		    e.preventDefault();
            		    var idv = $(this).attr("id") ; 
                       var nxtIdx = $inp.index(this) + 1;
                       if("lgcOrderNo"==idv){
                    	   var signName =  $('select[name=signName]').val() ;
                    	   if('INP'==signName){
                    		   signName = $('input[name=signName_input]').val() ;
                    	   }
                    	   
                    	   if(signName.length<1){
                    		   alert("请输入签收人");
                    		   $('input[name=signName_input]').focus(); 
                    		   return false;
                    	   }
                    	   
                    	   var sendCourierNo =  $('input[name=sendCourierNo]').val() ;
                    	   var  lgcOrderNo = $(this).val();
                    	   if(sendCourierNo.length<1){
                    		   alert("请输入签收派件员");
                    		   $('input[name=sendCourierNo]').focus(); 
                    		   return false;
                    	   }
                    	   if(lgcOrderNo.length<1){
                    		   alert("请输入运单号");
                    		   $(this).focus(); 
                    		   return false;
                    	   }
                    	   
                    	   $.ajax({
                	   			 type: "post",//使用get方法访问后台
                	   	            dataType: "json",//返回json格式的数据
                	   	            url: "${ctx}/scant/ssign",//要访问的后台地址
                	   	            data: {'lgcOrderNo':lgcOrderNo,'sendCourierNo':sendCourierNo,'signName':signName},//要发送的数据
                	   	            success: function(data){//msg为返回的数据，在这里做数据绑定
                	   	            	if(data.ret==1){
                	   	            	 var html = '<tr>' ;
                	   	            	    html += '<td align="center">'+data.track.orderTime+'</td>';
                	   	            	    html += '<td align="center">'+lgcOrderNo+'</td>';
                	   	            	    html += '<td align="center">'+signName+'</td>';
                	   	            	    html += '<td align="center">'+data.courier.courierNo+'</td>';
                	   	                    html += '<td align="center">'+data.courier.realName+'</td>';
                	   	                    html += '<td align="center">'+data.substation.substationName+'</td>';
              	   	                        html += '<td align="center">'+data.track.scanOno+'</td>';
              	   	                        html += '<td align="center">'+data.track.scanOname+'</td>';
                	   	                    html += '</tr>';
                	   	                    $("#tb1").prepend(html) ; 
                	   	                    piao = piao +1 ;
                	   	                 $('.piao').html(piao);
                	   	            	}else if(data.ret==2){
                	   	            	 $('#tips').html(lgcOrderNo+data.msg);
                	   	            	}else{
                	   	            	alert(data.ret);
                	   	            }
                	   	        }
                	   		 }); 
                    	   
                    	  $('input[name=lgcOrderNo]').val("");	  
                    	  $('input[name=lgcOrderNo]').focus(); 
                       }else{
                    	   $(":input:text:eq(" + nxtIdx + ")").focus(); 
                       }
              }  
         });
         
         var data = ${clist}; 
          	var availablesrcKey1 = [];
              $.each(data, function(i, item) {
              	var inner_no = "" ;
              	if(item.inner_no){inner_no=item.inner_no+','}
              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
              });
              val1 = '';
                $jqq("#send_courier_no").autocomplete(availablesrcKey1, {
             		minChars: 0,
             		max: 12,
             		autoFill: true,
             		mustMatch: false,
             		matchContains: true,
             		scrollHeight: 220,
             		formatItem: function(data11, i, total) {
             			return data11[0];
             		}
             	}).result(function(event, data, formatted) {
             		if(data[0].indexOf(')')>-1){
           			 $("#send_courier_no").val(data[0]) ;
     			       } 
           	});	
         
            
        });
        
        function chl(signName){
           if('INP'==signName){
        	   $('.signName_input').css("display","inline-block");
           }else{
        	   $('.signName_input').css("display","none");
           }
  } ;       

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
     <input type="hidden" name="serviceName" value=""/>
        <ul style="width: 865px;">
             <li style="width: 80%;">&#12288;&#12288;签收人：<select style="width: 258px;" name="signName" onchange="chl(this.value);">
                <option value="本人签收">本人签收</option>
                <option value="前台代收">前台代收</option>
                <option value="同事代收">同事代收</option>
                <option value="其他">其他</option>
                <option value="INP">手动输入签收人</option>
             </select>&#12288;&#12288;&#12288;<span class="signName_input" style="display: none;">签收人：<input type="text" name="signName_input" style="width: 250px;"></input></span></li>
             <li style="width: 80%;">签收派件员：<input type="text" name="sendCourierNo" id="send_courier_no" style="width: 250px;"></input></li>
             <li style="width: 80%;">&#12288;&#12288;运单号：<input type="text" name="lgcOrderNo" id="lgcOrderNo" style="width: 250px;"></input>&nbsp;&nbsp;&nbsp;&nbsp;按回车确定</li>
        </ul>
         <div style="padding: 30px 0 ;font-size: 22px;">当前扫描票数：<span style="color: red;" class="piao">0</span></div>
  </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
   <div class="operator">
	   <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/></div> 
  </div>
   </div>   <!-- tableble_search end  -->   
  
</div>


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="6%" align="center">签收时间</th>
            <th width="6%" align="center">运单号</th>
            <th width="6%" align="center">签收人</th>
            <th width="12%" align="center">签收快递员编号</th>
            <th width="12%" align="center">签收快递员</th>
            <th width="12%" align="center">签收站</th>
            <th width="12%" align="center">扫描员编号</th>
            <th width="12%" align="center">扫描员</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
</div>
</body>

</html>