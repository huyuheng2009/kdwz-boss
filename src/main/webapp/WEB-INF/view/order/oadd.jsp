<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script  type="text/javascript" src="/scripts/city_hp.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style>
.online_cont {	width: 1000px;	margin: auto;	padding: 10px 0 0 0;}
.online_header {	background: #fff;	border-radius: 5px 5px 0 0;	height: 40px;	padding: 10px 0 10px 90px;}
.online_header > strong {	line-height: 40px;	font-size: 14px;}
.online_header > select {	width: 180px;	border: 1px solid #cdcdcd;	margin-left: 20px;	font-size: 14px;	height: 30px;	line-height: 30px;}
.online_c_cont {	background: #fff; text-align:center;}
.online_cont_li {	width: 350px;	border: 1px solid #cdcdcd;	border-radius: 2px;	background: #fafafa;	padding: 8px 26px;	margin-bottom:20px;	display: inline-block; vertical-align:top; text-align:left;}
.online_cont_li.online_c_left { margin-right:60px;}
.online_cont_li.online_c_full{ width:818px;}
.online_cont_li > .online_c_tit {	font-weight: bold;	line-height: 36px;}
.online_cont_li > .online_cont_cont {}
.online_cont_cont > .online_c_li {	min-height:44px;}
.online_cont_cont > .online_c_li > strong {	width: 76px;	line-height: 44px;	font-weight: normal;	display: inline-block;	text-align: right; margin-right:12px;}
.online_cont_cont > .online_c_li > strong i {	color:red;	font-style:normal;	margin-right:2px;}
.online_cont_cont > .online_c_li > span {	margin-left:95px;	color:#999;}
.online_cont_cont > .online_c_li > b { margin-left:5px;font-weight:normal;}
.online_cont_cont > .online_c_li > input {	width: 230px;	border: 1px solid #cdcdcd;	padding: 0 10px;	height: 26px;	line-height: 28px;}
.online_cont_cont > .online_c_li > select {	border: 1px solid #cdcdcd;	height: 28px;}
.online_cont_cont > .online_c_li > .select_a {	width: 75px;	margin-right: 10px;}
.online_cont_cont > .online_c_li > .select_a:nth-of-type(3) {	margin-right: 0;}
.online_cont_cont > .online_c_li > .input_a {	margin: 5px 0 5px 92px;}
.online_cont_cont > .online_c_li > .select_b {	width: 180px;}
.online_cont_cont > .online_c_li > input.input_b {	width:698px;}
.online_button { background:#fff; padding:0 0 20px 0; text-align:center;}
.online_button input { width:80px; text-align:center; height:30px; border:0; display:inline-block; margin:0 20px;}
.online_button input[type=reset] { background:#C9C9C9; color:#333;}
.online_button input[type=submit] { color:#fff; background:#0866c6;}
@media screen and (min-width: 1201px) { 
.online_cont{ width:1200px;}
.online_cont_li { width:448px;}
.online_cont_li.online_c_full{ width:1014px;}
.online_cont_cont > .online_c_li > input { width: 320px;}
.online_cont_cont > .online_c_li > .select_a {	width:104px; margin-right:12px;}
.online_cont_cont > .online_c_li > input.input_b {	width:887px;}
} 
</style>

<style type="text/css">




.split {
height: 30px;
line-height: 30px;
border-bottom: 1px dashed #d2d2d2;
clear: both;
font-weight: bold;
color: #454545;
margin-top: 14px;
}

ul {
list-style: none;
padding: 0;
margin: 0;
outline: none;
text-indent: 0;
}
.lileft,.lileft1,.liright {
text-indent: 0;
padding: 0;
margin: 0;
margin-top: 20px;
}
.lileft{
display:block;
width: 25%;text-align: right;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-right: 3%;
}
.lileft1{
display:block;
width: 90%;text-align: right;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-left: 1%;
}
.lileft2{
display:block;
width: 90%;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-left: 5%;
margin-top: 15px;
}

.liright{
width: 70%;text-align: left;float: left;
padding: 0;
margin-left: 0%;
}



input[type="text"],select{
font-size: 1em;
width: 77%;
padding: .3em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
}
select{
height:32px;
display:inline;
line-height: 32px;
}
/*  background-color:#C4C4C4;  */
.title{
width: 100%;height:30px;line-height:30px;margin-top: 10px;font-size:14px;

}
.title1{
width: 140px;float: left;margin-left: 70px;font-weight: bold;
}

</style>
    <script type="text/javascript">


    function ChangeProvincel(objTip,objOption,provinceValue)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val();
    	}
    	ChangeProvince(objOption,provinceValue,provinceValue);
    	obj.innerHTML=tip;
    }
    
    function ChangeCityl(objTip,objOption,provinceValue)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val()+'-'+ $('select[name=scity]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val()+'-'+ $('select[name=rcity]').val();
    	}
    	ChangeCity(objOption,provinceValue,provinceValue);
    	obj.innerHTML=tip;
    }
    
    function ChangeAreal(objTip)
    {
    	var obj = document.getElementById(objTip);
    	var tip = $('select[name=sprovince]').val()+'-'+ $('select[name=scity]').val()+'-'+$('select[name=sarea]').val();
    	if('rtips'==objTip){
    		tip = $('select[name=rprovince]').val()+'-'+ $('select[name=rcity]').val()+'-'+$('select[name=rarea]').val();
    		$('input[name=revAddr]').focus();
    	}else{
    		$('input[name=sendAddr]').focus();
    	}
    	obj.innerHTML=tip;
    }
    
  
    
    
    
    $(function(){
    	
    	$(".validate_form").validationEngine("attach",{
    		ajaxFormValidation:false,
    		onBeforeAjaxFormValidation:function (){
    			alert("before ajax validate");
    		},
    		onAjaxFormComplete:
            function (){
    			
    		}
    		
    	}); 
    	
    	  var $inp = $('input:text');
    	    $inp.bind('keydown', function (e) {
    	        // 回车键事件  
    	           if(e.which == 13) {  
    	        	   var focused = $(':focus');
    	        	   var isFocus=$(".pf").is(":focus");  
    	        	   if(isFocus){
    	        		   var l = $('select[name=lgcNo]').val() ;
    	        		   if(l.length<2){
    	        			   alert("请选择快递公司");
    	        			   return ;
    	        		   }
    	        		   var  sPhone = $('input[name=sendPhone]').is(":focus");  
    	        		   var phone = $('input[name=sendPhone]').val() ;
    	        		   var  sinPhone = $('input[name=callPhone]').is(":focus");  
    	        		   var  inPhone = $('input[name=callPhone]').val();  
    	        		   if(sinPhone){
    	        			   if(inPhone.length<2){
    	        				   alert("请输入来电电话号码");
    	            			   return ; 
    	        			   }
    	        			   $.dialog({lock: true,title:'地址簿',drag: true,width:800,height:470,resize: false,max: false,content: 'url:${ctx}/order/lgc_addr1?inid=S&lgcNo='+l+'&incomingPhone='+inPhone+'&layout=no',close: function(){
    	                     	 }});
    	        		   }
    	        		   else if(sPhone){
    	        			   if(phone.length<2){
    	        				   alert("请输入寄件人电话号码");
    	            			   return ; 
    	        			   }
    	        			   $.dialog({lock: true,title:'地址簿',drag: true,width:800,height:470,resize: false,max: false,content: 'url:${ctx}/order/lgc_addr?inid=S&lgcNo='+l+'&phone='+phone+'&layout=no',close: function(){
    	                     	 }});
    	        		   }else{
    	        			   phone = $('input[name=revPhone]').val() ;
    	        			   if(phone.length<2){
    	        				   alert("请输入收件人电话号码");
    	            			   return ; 
    	        			   }
    	        			   $.dialog({lock: true,title:'地址簿',drag: true,width:800,height:470,resize: false,max: false,content: 'url:${ctx}/order/lgc_addr?inid=R&lgcNo='+l+'&phone='+phone+'&layout=no',close: function(){
    	                     	 }}); 
    	        		   }
    	        	   }else{
                  		   var $inp = $('input:text');
               		      e.preventDefault();
                          var nxtIdx = $inp.index(this) + 1;
                          $(":input:text:eq(" + nxtIdx + ")").focus();
                          
               	   }
    	           }  
    	       }); 
    	   var  areas = '${lgcConfig.defaultCity}'.split("-") ;
  		  if(areas[1]){
  			InitOption('sprovince','scity','sarea',areas[0],areas[1]);
         	    InitOption('rprovince','rcity','rarea',areas[0],areas[1]); 
  		  }else{
  			InitOption('sprovince','scity','sarea','广东省','深圳市');
      	    InitOption('rprovince','rcity','rarea','广东省','深圳市');  
  		  }
     	   
     	   $('.submit').click(function(){
     		  if($(".validate_form").validationEngine("validate")){  
     			 
     		   var lgcNo = 	$('select[name=lgcNo]').val() ;
     		   var sendName = $('input[name=sendName]').val() ;
     		   var sendAddr = $('input[name=sendAddr]').val() ;
     		   var sendPhone = $('input[name=sendPhone]').val() ;
     		   var sprovince = 	$('select[name=sprovince]').val() ;
     		   var scity = 	$('select[name=scity]').val() ;
     		   var sarea = 	$('select[name=sarea]').val() ;
     		 
     		   
     		   if(lgcNo.length<2){
     			   $('select[name=lgcNo]').focus();
     				 return false;
     			 }
     		    if(sendName.length<2){
     		    	$('input[name=sendName]').focus();
     				 return false;
     			 }
     		    if(sendPhone.length<2){
     		    	$('input[name=sendPhone]').focus();
     				 return false;
     			 }
     		    if(sprovince.length<2){
     		    	alert("请选择寄件人区域信息");
     		    	$('select[name=sprovince]').focus();
     				 return false;
     			 }
     		    if(scity.length<2){
     		    	alert("请选择寄件人区域信息");
     		    	$('select[name=scity]').focus();
     				 return false;
     			 }
     		    if(sarea.length<2){
     		    	alert("请选择寄件人区域信息");
     		    	$('select[name=sarea]').focus();
     				 return false;
     			 }
     		    
     		    if(sendAddr.length<2){
     		    	$('input[name=sendAddr]').focus();
     				 return false;
     			 }
     		    
     		  
     		    
     		       var revName = $('input[name=revName]').val() ;
     			   var revAddr = $('input[name=revAddr]').val() ;
     			   var revPhone = $('input[name=revPhone]').val() ;
     			   var rprovince = 	$('select[name=rprovince]').val() ;
     			   var rcity = 	$('select[name=rcity]').val() ;
     			   var rarea = 	$('select[name=rarea]').val() ;

     
     			 var sendArea = sprovince +'-'+ scity +'-'+ sarea ;   
     			 var revArea = rprovince  +'-'+ rcity +'-'+ rarea ;  
     			 var itemStatus =  $('#itemStatus').val() ;  
     			 //if(itemStatus.length<1){
      		    //	alert("请选择物品类型");
      		    //	$('select[name=itemStatus]').focus();
      			//	 return false;
      			// }
     			 
     			 var courier = $('select[name=courier]').val() ;
     			 var orderNote = $('input[name=orderNote]').val() ;
     			 var freightType = $('select[name=freightType]').val() ;
     			 var freight = $('input[name=freight]').val() ;
     			 var callPhone =$('input[name=callPhone]').val() ;
     			var message =$('input[name=message]:checked').val() ;
     			if ($.trim(freight).length>0) {
     				if(!reg[6].test(freight)){
     					alert("请输入正确的邮费,保留两位小数");
     					  return  false; 
     				   }
     			  }else{
     				 freight = '0' ; 
     			  }
     				  
     			 /* var takeTime = $('select[name=takeDay]').val()+$('select[name=take_time]').val() ; */
     			  var takeTime = '' ;
     			 if(takeTime.length!=2){
     				takeTime='';
     			 }
     			// var takeAddr = $('input[name=takeAddr]').val() ;
     			var takeAddr = '' ;
     			 $('input[name=sendArea]').val(sendArea) ;
     			 $('input[name=revArea]').val(revArea)  ;
     			 $('input[name=takeTime]').val(takeTime)  ;
     			 
     			 var data =  {'lgcNo':lgcNo,'sendName':sendName,'sendPhone':sendPhone,'sendArea':sendArea,'sendAddr':sendAddr,incomingPhone:callPhone,
	            	   'revName':revName,'revPhone':revPhone,'revArea':revArea,'revAddr':revAddr,'freight':freight,'message':message,
		            	   'itemStatus':itemStatus,'orderNote':orderNote,'freightType':freightType,'takeTime':takeTime,'takeAddr':takeAddr
		            } ;
     			 
     			 if(courier.length>1){
     				data =  {'lgcNo':lgcNo,'sendName':sendName,'sendPhone':sendPhone,'sendArea':sendArea,'sendAddr':sendAddr,incomingPhone:callPhone,
     	            	   'revName':revName,'revPhone':revPhone,'revArea':revArea,'revAddr':revAddr,'freight':freight,'takeCourierNo':courier,'message':message,
     		            	   'itemStatus':itemStatus,'orderNote':orderNote,'freightType':freightType,'takeTime':takeTime,'takeAddr':takeAddr
     		            } ;
     			 }
     			 var isChecked = $("#isSave").is(':checked');
     			var btn = $(this).attr("name") ;
     			 //$("form:first").submit();
     			 $.ajax({
     				 type: "post",//使用get方法访问后台
     		            dataType: "text",//返回json格式的数据
     		           url: "/order/save",//要访问的后台地址
     		            data: data,//要发送的数据
     		           beforeSend:loading,
     		            success: function(msg){//msg为返回的数据，在这里做数据绑定
     		            	if('0'!=msg){
     		            		if(btn=='asign'){
     		            	        	$.dialog({lock: true,title:'订单分配',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/order/asign_ss?ids='+msg+'&layout=no',
     		            	        			close: function(){
     		            	        			location.href="/order/list";		
     		            	        	 }});
     		            		}else{
     		            			alert("下单成功");
     		            			if(isChecked){
     		            				$(".loading").remove();
     		            				$("#revPhone").val("");
     		            				$("#revName").val("");
     		            				$("#rarea option:eq(0)").attr("selected","selected");
     		            				$("#revAddr").val("");
     		            			}else{
     		            				location.reload() ;
     		            			}
     		            		}
     		            	}else{
     		            		loaded();
     		            		alert("下单失败");
     		            	}
     		            }
     			 }); 
     		    
     		    
     		  }  
     		   
     	   });
     	   
            
        });

    </script>
</head>
<body>

   
<div class="online_cont">
 <form action="msave" method="post"  class="validate_form" id="validate_form">
 <input type="hidden" name="sendArea" value=""/>
       <input type="hidden" name="revArea" value=""/>
        <input type="hidden" name="takeTime" value=""/>
  <div class="online_header"><strong>快递公司</strong>
    <select name="lgcNo" id="lgcNo">
      <c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
    </select>
  </div>
  <div class="online_c_cont">
    <div class="online_cont_li online_c_left">
      <div class="online_c_tit">寄件信息</div>
      <div class="online_cont_cont">
      	 <div class="online_c_li"><strong><i></i>来电电话</strong>
          <input class="pf validate[funcCall[phoneCall]]" name="callPhone" id="callPhone" />
        </div>
        <div class="online_c_li"><strong><i>★</i>联系电话</strong>
          <input class="pf validate[required,funcCall[phoneCall]]" name="sendPhone" id="sendPhone" />
          <span>提示：输入号码后请点击回车</span>
        </div>
        <div class="online_c_li"><strong><i>★</i>&#12288;寄件人</strong>
          <input id="sendName"  class="validate[required,funcCall[zys]]"  name="sendName" id="sendName"  />
        </div>
        <div class="online_c_li"><strong><i>★</i>寄件地址</strong>
          <select  name="sprovince"  class="validate[required] select_a"  id="sprovince" onchange="javascript:ChangeProvincel('stips','scity',this.options[this.selectedIndex].value)">
          </select>
          <select  name="scity"  class="validate[required] select_a"  id="scity" onchange="javascript:ChangeCityl('stips','sarea',this.options[this.selectedIndex].value)">
          </select>
          <select name="sarea"  class="validate[required] select_a"  id="sarea" onchange="javascript:ChangeAreal('stips')">
          </select>
          <input type="text" name="sendAddr"  id="sendAddr"  class="validate[required,funcCall[zys]] input_a"    />
        </div>
        <div class="online_c_li">  <strong>快&nbsp;递&nbsp;员&nbsp;</strong>
         <select class="select_b"  name="courier"  id="courier">
           <option value="">暂不分配</option>
          </select> <label><input type="checkbox" id="isSave"/>提交后保留寄件信息</label>
        </div>
      </div>
    </div>
    <div class="online_cont_li" style="height:312px;">
    	<div class="online_c_tit">收件信息</div>
      	<div class="online_cont_cont">
        	<div class="online_c_li"><strong>联系电话</strong>
          		<input type="text"   class="pf" name="revPhone" id="revPhone"/>
                <span>提示：输入号码后请点击回车</span>
        	</div>
        	<div class="online_c_li"><strong>&#12288;收件人</strong>
          		<input type="text" name="revName"  id="revName" />
        	</div>
        	<div class="online_c_li"><strong>收件地址</strong>
          		<select class="select_a" name="rprovince"   id="rprovince" onchange="javascript:ChangeProvincel('rtips','rcity',this.options[this.selectedIndex].value)">
          		</select>
          		<select class="select_a" name="rcity" id="rcity" onchange="javascript:ChangeCityl('rtips','rarea',this.options[this.selectedIndex].value)">
          		</select>
          		<select class="select_a" name="rarea"   id="rarea" onchange="javascript:ChangeAreal('rtips')">
          		</select>
          		<input type="text" name="revAddr" id="revAddr" class="input_a" />
        	</div>
        	<div class="online_c_li"></div>          		
        </div>
    </div>
    <div class="online_cont_li online_c_full">
      <div class="online_c_tit">其他信息</div>
      <div class="online_cont_cont">
        <div class="online_c_li"><strong>物品类型</strong> <u:select id="itemStatus" sname="itemStatus" stype="ITEM_TYPE"  value="其他"/> 
            <c:if test="${isMessage eq '1' }">
            <span>
                <label><input type="checkbox" name="message" value="1"/> 是否短信通知寄件人</label>
            </span>
            </c:if>
        </div>
        <div class="online_c_li"><strong>预计运费</strong>
          <input type="text" name="freight" class="" /><b>元</b>
        </div>
        <div class="online_c_li"><strong>备　　注</strong>
          <input type="text" name="orderNote" class="input_b" />
        </div>
      </div>
    </div>
  </div>
  </form>
  <div class="online_button">
  	 <input name="reset" type="reset" class="" value="重置"/>
  	 <input  id="submit" type="button" class="button input_text  big gray_flat submit" value="提交"/>
  	 <input id="asign"  name="asign"  type="button" class="button input_text  big gray_flat submit" value="分配"/>
  </div>
</div>
</body>

</html>