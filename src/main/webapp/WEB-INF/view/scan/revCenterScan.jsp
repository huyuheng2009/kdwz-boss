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
    var min_weight ="${weightConfig.warehouse_minv}";
    var select_v = "${weightConfig.select_v}";
    function adds(){
   	 var lgcOrderNo = $.trim($('input[name=lgcOrderNo]').val()) ;
   	 var substationNo =  $('input[name=substationNo]').val() ;  
   	/*  var substationName =  $('select[name=substationNo]').find("option:selected").text() ;   */
   	 var center_warehouse_weight = $("input[name=center_warehouser_wight]").val();
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
	   	            url: "${ctx}/scan/centerWareHouseRev",//要访问的后台地址
	   	            data: {'lgcOrderNo':lgcOrderNo,'substationNo':substationNo,'center_warehouse_weight':center_warehouse_weight},//要发送的数据
	   	            success: function(data){//msg为返回的数据，在这里做数据绑定
	   	            	if(data.ret==1){
	   	            	 $("#tb1").prepend('<tr><td align="center">'+data.time+'</td><td align="center">'+lgcOrderNo+'</td><td align="center">'+data.center_warehouse_weight
	   	            			+'</td><td align="center">'+data.itemStatus+'</td><td align="center">'+data.track.preNo+'</td><td align="center">'+data.track.preType+'</td><td align="center">'
	   	            			 +data.track.curNo+'</td><td align="center">'+data.track.curType+'</td><td align="center">'
	   	            			 +data.track.scanIno+'</td><td align="center">'+data.track.scanIname+'</td><td align="center"></td></tr>');
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
   $('#weight').val("");	  
   $('input[name=lgcOrderNo]').focus(); 
   // $('#weight').focus();
   return false ;	   
   	   
   }
   
   $(function(){
	   trHover('t2');
   	$('input[name=lgcOrderNo]').focus(); 
   //	$('#weight').focus();
   	 var lgcNo =  $('select[name=lgcNo]').val() ;  
    	$("."+lgcNo).css("display","block");
    	
    
    	jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {
          var slist =  tmjs.slist ;
           	var slists = [];
               $.each(slist, function(i, item) {
               	var inner_no = "" ;
               	if(item.inner_no){inner_no=item.inner_no+','}
               	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
               });
               val1 = '';
                 $jqq("#substationNo").autocomplete(slists, {
              		minChars: 0,
              		max: 12,
              		autoFill: true,
              		mustMatch: false,
              		matchContains: true,
              		scrollHeight: 220,
              		formatItem: function(slists, i, total) {
              			return slists[0];
              		}
              	}).result(function(event, data, formatted) {
               		if(data[0].indexOf(')')>-1){
              			 $("#substationNo").val(data[0]) ;
        			       } 
              	});	
   }); 	
    	
    $("#weight").change(function(){
    	var weight = $(this).val().trim();	
    		if(parseFloat(weight)<parseFloat(min_weight)){
        		$(this).val(min_weight);
        	}else{
        		$(this).val(weight);
        	}
    }).blur(function(){
    	document.getElementById("myAudio").play();
    });
    
}); 
   
function chl(lgcNo){
          $('#substationNo').prop('selectedIndex', 0);
           $(".s_none").css("display","none");
  		$("."+lgcNo).css("display","block");
} ;       
    
	document.onkeydown = function(e){
	   if(!e){
	    e = window.event;
	   }
	   if((e.keyCode || e.which) == 13){
	    if(getTargetEle(e).name=="center_warehouser_wight"){
	    	if($("#lgcOrderNo").val()==''){
	    		$("#lgcOrderNo").focus();
	    		return false;
	    	}
	    	if($("#weight").val()==''){
	    		$("#weight").val(min_weight);
	    	}
	    }
	    if(getTargetEle(e).name=="lgcOrderNo"){
	    	if($("#lgcOrderNo").val()==''){
	    		$("#lgcOrderNo").focus();
	    		return false;
	    	}
	    	$("#weight").val("");
	    	if($("#weight").val()==''){
	    		$("#weight").focus();
	    		return false;
	    	}
	    	return false;
	    }
	   }
	  }
	
	function getTargetEle(e){
		 var ev =window.event || e; 
		 var srcelement = ev.srcElement||ev.target
		 return srcelement;
	}
    </script>
</head>
<body>
<div class="search">
    <form:form id="trans" action=""  onsubmit="return adds();">
        <ul style="margin-left:20px;"> 
 <%--        <li style="display:none">快递公司：<select id="lgcNo" name="lgcNo" style="width: 110px" onchange="chl(this.value);">
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
					</select></li> --%>
            <li style="width:100%;">上一站点：<input type="text" value="${params['substationNo']}" name="substationNo" id="substationNo" style="width: 200px;"></input></li>
            <li>&nbsp;&nbsp;&nbsp;运单号：<input type="text" id="lgcOrderNo" name="lgcOrderNo" size=30 style="width:212px;"></input></li>
		   <li>入仓重量：<input type="text" id="weight" name="center_warehouser_wight" size=30 style="width:212px;" value ="${weightConfig.warehouse_minv}"></input>&nbsp;&nbsp;kg</li>
            <li><input type="hidden" name="layout" value="no"/><input class="button input_text  medium blue_flat" type="submit" value="到件"/></li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>
<div style="width:100%;height: 30px;line-height: 30px;text-align: center;color: red;" id="tips" name="0" value="0"></div>
<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
        	<th width="12%" align="center">扫描时间</th>
            <th width="6%" align="center">运单号</th>
            <th width="6%" align="center">入仓重量</th>
             <th width="6%" align="center">物品类型</th>
            <th width="12%" align="center">上一站编号</th>
            <th width="12%" align="center">上一站名称</th>
            <th width="12%" align="center">到件网点编号</th>
             <th width="12%" align="center">到件网点</th>
             <th width="12%" align="center">扫描员编号</th>
             <th width="12%" align="center">扫描员</th>
            <th width="12%" align="center">操作</th>
        </tr>
        </thead>
        <tbody id="tb1">
        </tbody>
    </table>
<div style="width:0px;height:0px;overflow:hodden;display:none;">
<audio id="myAudio" controls style="display:none;" width="0" height="0">
  <source src="${ctx}/music/d.ogg" type="audio/ogg">
  <source src="${ctx}/music/d.mp3" type="audio/mpeg">
  Your browser does not support this audio format. 
</audio>
</div>

</body>

</html>