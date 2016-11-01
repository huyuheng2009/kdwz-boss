<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<style>.noneDisplay{display: none;}</style>
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/table_new.css"/>
    <script type="text/javascript">
    var api = frameElement.api, W = api.opener;
	 api.lock();
	  var style = W.getStyle() ;
 	    if(style==1){
 	    	$("table").addClass("ta_ta");
  	   	$("table th").addClass("num_all");
  		//$("table td").addClass("num_all");
  		
  		$(".tableble_search").addClass("soso");
 	    }
  $(function () {	    
    	jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {
			var data1 = tmjs.clist ;
           // $.getJSON("${ctx}/lgc/calllist", function(data1) {
            	var availablesrcKey1 = [];
                 $.each(data1, function(i, item) {
                	var inner_no = "" ;
                	if(item.inner_no){
                		inner_no=item.inner_no
                		availablesrcKey1[i]=inner_no+'('+item.real_name.replace(/\ /g,"")+')';
                	}else{
                		availablesrcKey1[i]=item.real_name.replace(/\ /g,"");
                	}
                }); 
                val1 = '';
                  $jqq("#realName").autocomplete(availablesrcKey1, {
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
             			 $("#realName").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
       			       } 
             	});	

	    });  	    
    }); 	    
 	    
 	    
 	    
 	    
        function asign(id,orderNo,t,sno,substationNo,cid){
        if(confirm('确定分配？')){
   		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx}/order/asign_save",//要访问的后台地址
	            data: {'ids':id,'orderNo':orderNo,'t':t,'sno':sno,'substationNo':substationNo,'cid':cid},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('分配失败');
	            	}else{
	            		 $("form:first").submit();
	            	/* 	 if(t==2){
	            			 $('.sno_1').each(function(){
	            				  var idd = $(this).attr("id") ;
	            				  var html = "<a href=\"javascript:asign('"+id+"','"+orderNo+"','1','"+sno+"','"+substationNo+"','"+idd+"')\">分配到网点</a>" ;
	            				  $(this).html(html); 
	                     	}); 
	            			
	            		 }else{
	            			 
	            			  $('.sno_2').each(function(){
	            				  var idd = $(this).attr("id") ;
	            				  var html = "<a href=\"javascript:asign('"+id+"','"+orderNo+"','2','"+sno+"','"+substationNo+"','"+idd+"')\">分配给他</a>" ;
	            				  $(this).html(html);  
	                     	}); 
	            			 
	            			
	            		 }
	            		 $('#'+cid).html("已分配"); */
	            	}
	            		
	            }
		 });
       }	
    }
    
        
    </script>
 <style>
 .info_all{max-height: 260px;overflow-y: scroll;overflow-x:hidden;}
 .info_cont{ overflow:auto; zoom:1; margin-bottom:10px; padding:10px; border-bottom:1px solid #999;}
.info_cleft{ float:left; border-right:1px solid #999; padding:0 10px 0 0;width: 45%;}
.info_ctit{ line-height:26px; font-size:14px; margin-bottom:10px; font-weight:bold;}
.info_cc{ line-height:22px;}
.info_cright{ float:left;  padding:0 0 0 10px;}
 .info_ctit span{color: blue;margin-left: 15px;font-weight: normal;}

 </style>   
    
</head>
<body>
<div class="info_all">
 <c:forEach items="${oList}" var="orderMap" varStatus="status">
<div class=" info_cont">
	<div class="info_cleft">
    	<div class="info_ctit">寄件信息 <span>发件区域：${orderMap.send_area}</span></div>
        <div class="info_cc">
        手机号码/电话号码：${orderMap.send_phone}<br>
		寄件人/公司：${orderMap.send_name}<br>
		地址：${orderMap.send_area},${orderMap.send_addr}
        </div>        
    </div>
    <div class="info_cright">
        <div class="info_ctit">收件信息<span>派件区域：${orderMap.rev_area}</span></div>
        <div class="info_cc">
        手机号码/电话号码：${orderMap.rev_phone}<br>
		收件人/公司：${orderMap.rev_name}<br>
		地址：${orderMap.rev_area},${orderMap.rev_addr}
        </div>        
    </div>
</div>
</c:forEach>
</div>

<%-- <div class=" info_cont">
	物品类型：${orderMap.item_Status}<br>
	预计费用：${orderMap.freight}<br>
	备注/描述：${orderMap.order_note}
</div> --%>



<div class="search">
    <form:form id="trans" action="${ctx}/order/asign_ss" method="post">
     <input type="hidden" name="orderNo" value="${params['orderNo']}"/>
       <input type="hidden" name="ids" value="${params['ids']}"/>
        <%-- <input type="hidden" name="p" value="${params['p']}"/> --%>
        <ul> 
           <li>分配到：<select name="mode" style="width: 110px">
                <option value="">--全部--</option>
                <option value="1" ${params['mode'] eq '1'?'selected':''}  class="${params['AUTO_ALL'] eq '1'?'noneDisplay':''}"  >网点</option>
                <option value="2" ${params['mode'] eq '2'?'selected':''}>快递员</option>
             </select></li>
            <li>网点：<input type="text" value="${params['substationName']}" name="substationName"></input></li>
             <li>快递员：<input type="text" value="${params['realName']}" id="realName" name="realName"></input></li>
              <li>收派范围：<input type="text" value="${params['sarea']}" style="width: 250px;" name="sarea"></input></li>
             <input type="hidden" name="layout" value="no"/>
            <li><input   class="button input_text  medium blue_flat"
                       type="submit" value="查询"/>
            </li>
           <!--  <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li> -->
        </ul>
    </form:form>
    <div class="clear"></div>
</div>

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">分站名称</th>
            <th align="center">快递员名称</th>
            <th  align="center">联系电话</th>
            <th  align="center">收派范围</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td align="center">${item.substation_name}</td>
                <td align="center">${item.courier_name}</td>
                <td align="center">${item.phone}</td>
                <td width="50%" align="center">${item.sarea}</td>
                <td align="center" id="count_${status.count}" class="sno_${item.t}">
           <c:choose>
             <c:when test="${fn:contains(stations,item.sno)}">已分配</c:when>
			<c:otherwise>
				<c:if test="${item.t eq '1'}">
				     <a href="javascript:asign('${params.ids}','${params.order_no}','${item.t}','${item.sno}','${item.substation_no}','count_${status.count}')">分配到网点</a>
				</c:if>
				<c:if test="${item.t eq '2'}">
				     <a href="javascript:asign('${params.ids}','${params.order_no}','${item.t}','${item.sno}','${item.substation_no}','count_${status.count}')">分配给他</a>
				</c:if>
			</c:otherwise>
				</c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${cList.pages}"
                     current="${cList.pageNum}" sum="${cList.total}"/>
</div>
</body>

</html>