<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/themes/default/check.css"/>
<script  type="text/javascript" src="/scripts/city_hp.js"></script> 
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style type="text/css">
.wi{ width:140px; text-align:right; margin-right:14px;}
.form_bottom ul li{ float:none;}
.yundan_in .yundan_input{ margin-top:8px;}
.yundan_in select{ margin-top:10px;}
.form_left{ width:38%;}
</style>

<script type="text/javascript">
$().ready(function() {
});

function inputCheck1()
{
	 return false ;
} 
    function inputCheck()
    {
    	var lgcOrderNo = $('input[name=lgcOrderNo]').val();
		 if(lgcOrderNo.length<2){
			 alert('请输入快递公司运单号');
			 return false;
		 }
		 return true ;
    }



        $(function () {
        	
        	var lgcOrderNo = $('input[name=lgcOrderNo]').val();
   		    if(lgcOrderNo.length<1){
   			     $("#lgcOrderNo").focus();
        	 }else{
        		 $("#send_courier_no").focus();
        	 }
        	
          	$("#vf").validationEngine("attach",{
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
            	 if(e.which == 13) {
            		 var nn = true ;
            	   var fname = $(this).attr("name") ;
            	   
            	   
            	   if("lgcOrderNo"==fname&&$.trim($(this).val()).length>0){
            		   $("form:first").submit();
                       return ;
         		    }
            	   
            	   if("orderNote"==fname){
            		   _submit() ;
                       return ;
         		    }
            	  
            	   
           		    if("monthSettleNo"==fname&&$.trim($(this).val()).length>0){
           		    	 $.getJSON("${ctx}/mobile/getbyno", {'monthSettleNo':$(this).val()},function(data) {  
           		    		 if(data!=null){
           		    			 $('input[name=month_name]').val(data.month_name) ;
           		    			 $('input[name=month_lx]').val(data.contact_name) ;
           		    			 $('input[name=month_ph]').val(data.contact_phone) ;
           		    			$('input[name=month_addr]').val(data.contact_addr) ;
           		    		 }else{
           		    			 alert("无此月结号") ;
           		    			 $('input[name=monthSettleNo]').focus(); 
           		    		 }
           		    	 });
           		    }  
           		    
           		    
           		    
           		    
           		    	var $inp = $('input:text');
            		      e.preventDefault();
                       var nxtIdx = $inp.index(this) + 1;
                       if(!nn){nxtIdx = $inp.index(this) ;}
                       for(var i=1;i<5;i++){
                    	   if($(":input:text:eq(" + nxtIdx + ")").prop('disabled')){
                    		   nxtIdx = nxtIdx + 1 ; 
                    	   }else{
                    		   break;
                    	   }
                       }
                       $(":input:text:eq(" + nxtIdx + ")").focus(); 
              		    
                 }  

            });

            
 	$(".cmount").on('input',function(e){  
        		
        		var amount = $('input[name=payAcount]').val() ;
        		var good_price1 = $('input[name=good_price]').val() ;
        		var cpay1 = $('input[name=cpay]').val() ;
        		var goodValuation1 = $('input[name=goodValuation]').val() ;
        		var vpay1 = $('input[name=vpay]').val() ;
        		var freight1 = $('input[name=freight]').val() ;
        		
        		var freight_type1 = $('input[name=freight_type]').val() ;
        		var payType1 = $('input[name=payType]').val() ;
        		
        		 if(!reg[6].test(good_price1)){
        			 good_price1 = 0 ;
        		 }
        		 if(!reg[6].test(cpay1)){
        			 cpay1 = 0 ;
        		 }
        		 if(!reg[6].test(goodValuation1)){
        			 goodValuation1 = 0 ;
        		 }
        		 if(!reg[6].test(vpay1)){
        			 vpay1 = 0 ;
        		 }
        		 if(!reg[6].test(freight1)){
        			 freight1 = 0 ;
        		 }
        		 
        		 var tnpay = 0 ;
        		 if(freight_type1=='1'||freight_type1=='寄方付'&&payType1.length>0){
        			 tnpay = good_price1 ;
        		 }
        		 if(freight_type1=='2'||freight_type1=='到方付'){
        			 tnpay =  freight1 - (-vpay1) - (-good_price1) ;
        				switch(payType1)
        				{
        				case '1':case '现金':case '3':case '会员卡':
        				 tnpay = freight1 - (-vpay1) - (-good_price1) ;
        				  break;
        				case '2':case '月结':
        				  tnpay =  freight1 - (-vpay1) - (-good_price1) ;
          				  break;
        				default:break;
        				}
        		 }
        		
        		 amount = good_price1-(-vpay1)-(-freight1);
        		 
        		 $('input[name=tnpay]').val(tnpay) ;
                 $('input[name=payAcount]').val(amount) ;
        		  
       	});    //回车绑定
 	
        jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {	 
	    var clist = tmjs.clist;
        	var availablesrcKey1 = [];
            $.each(clist, function(i, item) {
            	var inner_no = "" ;
            	if(item.inner_no){inner_no=item.inner_no+','}
            	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
            });
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
        			 $("#send_courier_no").val(data[0].substring(0,data[0].indexOf('('))) ;
        			 $("#send_courier_name").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
  			       } 
                $("#freight_type").focus();
        	});	
		});       	 	
     	  
     	   $('#submit').click(function(){
     		    _submit() ;
     	   });
     	   
            
        });
        
    function _submit(){
    	 if($("#vf").validationEngine("validate")){  
    		 var tdate =  $('input[name=tdate]').val() ;
    		 var noFix =  $('input[name=noFix]').val() ;
             var lgcOrderNo = $('input[name=lgcOrderNo]').val();
    		  if(lgcOrderNo.length<2){
    			 alert('请输入快递公司运单号');
    			 return false;
    		 }
    		 lgcOrderNo = noFix + lgcOrderNo ;   
    		 var orderNote = $('input[name=orderNote]').val() ; 
   			 var send_courier_no = $('input[name=send_courier_no]').val();
   			 if(send_courier_no.length<2){
    		    	$('input[name=send_courier_no]').focus();
    				 return false;
    			 } 
  
   			 if(send_courier_no.indexOf(')')>-1){
   				 send_courier_no = send_courier_no.substring(0,send_courier_no.indexOf('(')); 
   			 } 

    			var freight_type = $('input[name=freight_type]').val() ;
    			 if(freight_type.length<1){
      				 return false;
      			 }
    			if('寄方付'==freight_type){freight_type = 1 ;}
    			if('到方付'==freight_type){freight_type = 2 ;}
    			
    			var monthSettleNo = $('input[name=monthSettleNo]').val() ;
    			var payType = $('input[name=payType]').val() ;
    			if('现金'==payType||'1'==payType){payType = 'CASH' ;}
    			if('月结'==payType||'2'==payType){payType = 'MONTH';}
    			if('会员卡'==payType||'3'==payType){payType = 'HUIYUAN' ;}
    			
    			var signName = $('input[name=signName]').val() ;
    			
    			 //$("form:first").submit();
    			 $.ajax({
    				 type: "post",//使用get方法访问后台
    		            dataType: "text",//返回json格式的数据
    		           url: "/order/sign_update",//要访问的后台地址
    		            data: {'lgcOrderNo':lgcOrderNo,'orderNote':orderNote,'sendCourierNo':send_courier_no,'tdate':tdate,
    		            	'freightType':freight_type,'monthSettleNo':monthSettleNo,'payType':payType,'signName':signName
    		            },//要发送的数据
    		           beforeSend:loading,
    		            success: function(msg){//msg为返回的数据，在这里做数据绑定
    		            	if('1'==msg){
    		            		alert("录入成功");
    		            		var n=window.location.href.indexOf("?") ;
   		            		var herf = location.href ;
   		            		if(n>0){
   		  					    herf=window.location.href.substr(0,n); 
   		  					    location.href = herf + "?tdate="+tdate + "&noFix="+noFix;
   		  					}else{
   		  						//location.reload();
   		  						 location.href = herf + "?tdate="+tdate + "&noFix="+noFix;
   		  					}
    		            }else{
    		            		loaded() ;
    		            		alert(msg);
    		            	}
    		            }
    			 }); 
    		  } 
    }

    </script>
</head>
<body>
   <div class=" soso"><!--按钮上下结构--> 
    <!---->
    
     <form:form id="trans" action="${ctx}/order/sinput" method="get" onsubmit="return inputCheck();">
    <div class="soso_left aa">
      <div class="soso_li">
        <div class="soso_b">签收时间</div>
        <div class="soso_input">
          <input  class="time_bg"  id="tdate" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd  HH:mm',readOnly:true,maxDate:'#F{\'%y-%M-%d %H:%m\'}'})"   type="text"  name="tdate" value="${params['tdate']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
        </div>
      </div>
      <div class="soso_li">
        <div class="soso_b">单号前缀</div>
        <div class="soso_input">
          <input class="" type="text" name="noFix" value="${params['noFix']}" placeholder=" ">
        </div>
      </div>
    </div>
    
  </div>
  <div class="xy_check_cont">
    <div class="check_code">
      <div class="check_code_name">运单号</div>
      <input value="${params.lgcOrderNo}" class="validate[required,funcCall[lgcOrderNo]]" id="lgcOrderNo" name="lgcOrderNo" type="text"  maxlength="60" />
           <div class="soso_button" style="margin: 0;">
        <input type="submit" value="查询" style="width: 120px;height: 30px;line-height: 20px;">
      </div>
      <div class="soso_button" style="margin: 0;">
        <input type="reset" value="重置 " style="width: 120px;height: 30px;line-height: 20px;">
      </div>
        <span style="color:red;line-height: 36px;">${params.msg}</span>  
    </div>
      </form:form>
       <form:form id="vf" action="${ctx}/order/input" method="get" onsubmit="return inputCheck1();">  
    <div class="check_cont">
      <div class="check_cont_left check_cont_left02">
        <div class="check_tit">财务信息</div>
        <div class="check_li">
          <div class="soso_b">&nbsp;&nbsp;派件员&nbsp;</div>
          <div class="soso_input">
          <input id="send_courier_no" class="validate[required,funcCall[zys]]" name="send_courier_no" value="${orderMap.send_courier_no}"  type="text"  maxlength="50">
                  <input style="width: 123px;" type="text" name="send_courier_name" id="send_courier_name" disabled="disabled"  value='<u:dict name="C" key="${orderMap.send_courier_no}" />' >
          </div>
        </div>        
        <div class="check_li aa" style="min-width: 240px;">
          <div class="soso_b">&nbsp;&nbsp;付款人&nbsp;</div>
          <div class="soso_input">
            <input class="three cmount validate[required,funcCall[freight_type1]]" ${orderMap.freight_type ne null ? 'disabled':''} id="freight_type"  name="freight_type" type="text"   value="${orderMap.freight_type}"  maxlength="20"/><span style="color:red;">1：寄付，2：到付</span>
          </div>
        </div><!--check_li end-->
        <div class="check_li">
          <div class="soso_b">付款方式</div>
          <div class="soso_input">
            <input class="three cmount validate[required,funcCall[paytype]]" <c:out value="${orderMap.freight_type eq '1' ?'disabled':'' }"/> name="payType" type="text"    value="${orderMap.pay_type}"  maxlength="20"/><span style="color:red;">1：现金，2：月结</span>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">&nbsp;&nbsp;月结号&nbsp;</div>
          <div class="soso_input">
            <input  class="two validate[funcCall[monthSettle]]" id="monthSettleNo" name="monthSettleNo" <c:out value="${orderMap.freight_type eq '1' ?'disabled':'' }"/>  value="${orderMap.month_settle_no}"  type="text"  maxlength="50"></input>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">&nbsp;&nbsp;签收人&nbsp;</div>
          <div class="soso_input">
            <input name="signName" class="two validate[funcCall[zys]]" type="text" id="signName"  value="${orderMap.sign_name}"   maxlength="20"/>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">备　　注</div>
          <div class="soso_input">
            <input class="two" name="orderNote" value="${orderMap.order_note}"  type="text"  maxlength="50"></input>
          </div>
        </div>        
        <div class="check_li" style="width: 300px;">
        	<div class="check_tit">寄件信息</div>  
        </div>      
        <%-- <div class="check_li">
          <div class="soso_b">客　　户</div>
          <div class="soso_input">
             <input class=" two" type="text" name="" value="${orderMap.send_kehu}" disabled="disabled" placeholder=" "/>
          </div>
        </div> --%>
        <div class="check_li aa">
          <div class="soso_b">&nbsp;&nbsp;寄件人&nbsp;</div>
          <div class="soso_input">
            <input class="" type="text" name="" value="${orderMap.send_name}" disabled="disabled" placeholder=" "/>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">手机号</div>
          <div class="soso_input">
             <input class="one " type="text" name=""value="${orderMap.send_phone}" disabled="disabled"  placeholder=" "/>
          </div>
        </div>
        <div class="check_li aa">
          <div class="soso_b">件　　数</div>
          <div class="soso_input">
            <input class=" " type="text" name="" value="${orderMap.item_count}" disabled="disabled" placeholder=" "/>
          </div>
        </div>  
      
        <div class="check_li ">
          <div class="soso_b">&nbsp;&nbsp;重量&nbsp;</div>
          <div class="soso_input">
            <input class=" one" type="text" name="" value="${orderMap.item_weight}" disabled="disabled" placeholder=" "/>
          </div>
        </div>  
        
          <div class="check_li">
          <div class="soso_b">快递运费</div>
          <div class="soso_input">
            <input class="" type="text" name="" value="${orderMap.freight}" disabled="disabled" placeholder=" "/>
          </div>
        </div>  
        
         <div class="check_li">
          <div class="soso_b">手续费</div>
          <div class="soso_input">
             <input class="one " type="text" name=""value="${orderMap.vpay}" disabled="disabled"  placeholder=" "/>
          </div>
        </div>
        
        <div class="check_li">
          <div class="soso_b">&nbsp;&nbsp;代收款&nbsp;</div>
          <div class="soso_input">
             <input class=" two" type="text" name="" value="${orderMap.good_price}" disabled="disabled" placeholder=" "/>
          </div>
        </div> 
        
         <div class="check_li">
          <div class="soso_b">应　　收</div>
          <div class="soso_input">
             <input class=" two" type="text" name="" value="<c:out value="${orderMap.freight_type eq '1' ?orderMap.good_price:orderMap.good_price+orderMap.freight+orderMap.vpay }"/>" disabled="disabled" placeholder=" "/>
          </div>
        </div> 
        
             
      </div><!--check_cont_left end-->
      <div class="check_cont_left">
        <div class="check_tit">月结信息</div>
        <div class="check_li">
          <div class="soso_b">公司名称</div>
          <div class="soso_input">
             <input class=" two" type="text" name="month_name"  disabled="disabled" placeholder=" " />
          </div>
        </div>
          <div class="check_li">
          <div class="soso_b">&#12288;&#12288;地址</div>
          <div class="soso_input">
            <input class="two " type="text" name="month_addr" disabled="disabled" placeholder=" "/>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">&#12288;联系人</div>
          <div class="soso_input">
            <input class="two" type="text" name="month_lx" disabled="disabled" placeholder=" "/>
          </div>
        </div>
        <div class="check_li">
          <div class="soso_b">联系电话</div>
          <div class="soso_input">
             <input class=" two" type="text" name="month_ph" disabled="disabled" placeholder=" "/>
          </div>
        </div>
      
      </div><!--check_cont_left end-->  
      <div class="check_cont_left check_cont_left03" style="overflow: auto;">
      	  <div style="background:#C30;">
      	  
      	  <div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr >
            <th align="center" >运单号</th>
            <th align="center" >签收人</th> 
            <th align="center" >签收时间</th> 
           <!--  <th align="center" >录入时间</th> -->
            <th align="center" >派件员</th>
            <th align="center" >代收款</th>
            <th align="center" >快递运费</th> 
            <th align="center" >手续费</th> 
            <th align="center" >付款人</th> 
            <th align="center" >付款方式</th> 
            <th  align="center" >月结客户</th>
            <th  align="center" >月结账号</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${signList}" var="item" varStatus="status">
          <tr>
            <td>${item.lgc_order_no}</td>    
            <td>${item.sign_name}</td>   
            <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>
            <%-- <td><fmt:formatDate value="${item.input_time}" type="both"/></td> --%>
            <td><u:dict name="C" key="${item.send_courier_no}" /></td>   
            <td>${item.good_price}</td> 
            <td>${item.freight}</td>
             <td>${item.vpay}</td>
            <td><c:if test="${item.si_freight_type eq 2 }">收方付</c:if><c:if test="${item.si_freight_type ne 2 }">寄方付</c:if></td> 
            <td><u:dict name="PAY_TYPE" key="${item.si_pay_type}" /></td>  
             <td><u:dict name="MONTHUSER" key="${item.si_month_settle_no}" /></td> 
            <td>${item.month_settle_no}</td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
      	  
      	  
      	  
          </div>
      </div><!--check_cont_left end-->     
    </div><!--check_cont end-->
    <div class="check_button">
    	<input  id="submit" name="submit" type="button" value="提交" >
    </div>
      </form:form>
  </div><!--xy_check_cont end-->
</body>


</html>