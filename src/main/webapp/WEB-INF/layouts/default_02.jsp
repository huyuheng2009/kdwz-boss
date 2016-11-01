<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header_01.jsp"%>
  <c:if test="${!empty lgcConfig.res.title}"><link rel="shortcut icon" href="${lgcConfig.res.title}"></c:if>
  <c:if test="${empty lgcConfig.res.title}"><link rel="shortcut icon" href="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_title.png"></c:if>
<title>${cmenu.position_text}<sitemesh:title />
</title>
<style>
.news_pop{ position: fixed; right:20px; bottom:20px; width:300px; height:200px; border:3px solid #afafaf; background:#fff; z-index:999; 
animation:myfirst 3s;
-moz-animation:myfirst 3s; /* Firefox */
-webkit-animation:myfirst 3s; /* Safari and Chrome */
-o-animation:myfirst 3s; /* Opera */}
.news_pop_tit{ height:40px; line-height:40px; position:relative; background:#f5f4f2; font-weight:bold; padding-left:10px;}
.news_pop_tit a{ position:absolute; right:0; top:0; width:40px; height:40px; background: url(/themes/default/images/off.png) no-repeat center; background-size:16px 16px; display:block;}
.news_pop_cont{ padding:10px;}
.news_pop_cont p{ max-height:64px;overflow: hidden;text-overflow: ellipsis;-o-text-overflow:ellipsis; -webkit-text-overflow:ellipsis; position:relative; }
.news_pop_cont p:first-child::after{ content:'......'; position:absolute; bottom:0; right:0; padding:0;background:#fff;}
.news_pop_cont a{ height:34px; background:#1c7ad8; color:#fff; display:block; width:100px; margin:4px auto 0; line-height:34px; text-align:center;}

@media screen and (min-width: 1201px) { 
.news_pop_tit{ font-size:14px;}
.news_pop_cont p{ font-size:14px;}
}
 
@keyframes myfirst
{
0%   { right:20px; bottom:-200px;}
100% {right:20px; bottom:20px;}
}
@-moz-keyframes myfirst /* Firefox */
{
0%   { right:20px; bottom:-200px;}
100% {right:20px; bottom:20px;}
}
@-webkit-keyframes myfirst /* Safari and Chrome */
{
0%   { right:20px; bottom:-200px;}
100% {right:20px; bottom:20px;}
}
@-o-keyframes myfirst /* Opera */
{
0%   { right:20px; bottom:-200px;}
100% {right:20px; bottom:20px;}
}

</style>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<sitemesh:head />
<script language="javascript">
var close = false ;
$(function() {
	setSubMenu() ;
	$('.header_navli').hover(function() {
		 $('.sx_nav_down').css("display","none");
            $(this).find('.sx_nav_down').css("display","block");
        	$(this).addClass('hover_on');
        	$(this).find('.sx_nav_down').offset({left:$(this).offset().left});
		}, function() {
			 $('.sx_nav_down').css("display","none");
			 $(this).removeClass('hover_on');
		});
	
	var mid = 0 ;
	  $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "json",//返回json格式的数据
	           url: "/bossMsg",//要访问的后台地址
	            data: {},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg){
	            		$(".pop_con").html(msg.content) ;
	            		$(".pop_name").html("联系人："+msg.contact_name) ;
	            		$(".pop_phone").html("联系电话："+msg.contact_phone) ;
	            		var tips = msg.tips ;
	            		if(mid!=msg.id&&mid!=0){
	            			$(".news_pop").css("display","block");
	            			 document.getElementById('ttips').play();
	            		}else{
	                         if(!close&&'N'!=tips){
	                        	 $(".news_pop").css("display","block");
	                        	 document.getElementById('ttips').play();
	            			}
	            		}
	            		mid = msg.id ;
	            	}
	            }
		 });
	setInterval(function () {
	 	  $.ajax({
	 			 type: "post",//使用get方法访问后台
	 	            dataType: "json",//返回json格式的数据
	 	           url: "/bossMsg",//要访问的后台地址
	 	            data: {},//要发送的数据
	 	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	 	            	if(msg){
	 	            		$(".pop_con").html(msg.content) ;
	 	            		$(".pop_name").html("联系人："+msg.contact_name) ;
	 	            		$(".pop_phone").html("联系电话："+msg.contact_phone) ;
	 	            		var tips = msg.tips ;
	 	            		if(mid!=msg.id&&mid!=0){
	 	            			if($(".news_pop").css("display")!='block'){
	                        		 $(".news_pop").css("display","block"); 
	                        	 }
	 	            			 document.getElementById('ttips').play();
	 	            		}else{
	 	                         if(!close&&'N'!=tips){
	 	                        	 if($(".news_pop").css("display")!='block'){
	 	                        		 $(".news_pop").css("display","block"); 
	 	                        		 document.getElementById('ttips').play();
	 	                        	 }
	 	            			}
	 	            		}
	 	            		mid = msg.id ;
	 	            	}
	 	            }
	 		 });
	}, 20000);

	
	//两分钟后继续提示
	setInterval(function () {
		close = false ;
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "json",//返回json格式的数据
	           url: "/closeMsg",//要访问的后台地址
	            data: {'tips':'Y'},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            }
		 }); 
	}, 120000);
	
	  $('#menu_left').click(function(){
		 // $('.sx_header_nav_cont').scrollLeft(0);
		  setMoreMenu(1) ;
   	   }); 
	  $('#menu_right').click(function(){
		 /*  var ul_width = $(".sx_header_nav_cont_ul").width() ;
		  var sx_header_nav_cont = $(".sx_header_nav_cont").width() ;
		  $('.sx_header_nav_cont').scrollLeft(sx_header_nav_cont); 
		  setMoreMenu(2)  */
		  
		  var w = 0 ;
			var p = 1 ;
			 var sx_header_nav_cont = $(".sx_header_nav_cont").width() ;
			 $('.header_navli').each(function(){
		  		w += $(this).width() ;
		  	});
			if(w-sx_header_nav_cont-0.01>0) {
				p = 2 ;
			}
			setMoreMenu(p) ;
   	   }); 
	
});	

function getStyle() {
	return 1 ;
	}

function close_() {
	 close = true ;
	 $(".news_pop").css("display","none");
	 $.ajax({
		 type: "post",//使用get方法访问后台
           dataType: "json",//返回json格式的数据
          url: "/closeMsg",//要访问的后台地址
           data: {'tips':'N'},//要发送的数据
           success: function(msg){//msg为返回的数据，在这里做数据绑定
           }
	 }); 
	}
	
function readed_() {
	 close = true ;
	 $(".news_pop").css("display","none");
	 $.ajax({
		 type: "post",//使用get方法访问后台
           dataType: "json",//返回json格式的数据
          url: "/msgRead",//要访问的后台地址
           data: {},//要发送的数据
           success: function(msg){//msg为返回的数据，在这里做数据绑定
           }
	 }); 
}
	

function setSubMenu() {
	////////////////////////////////////
	 var sx_header_nav_cont = $(".sx_header_nav_cont").width() ;
	var pid = $('.curi').attr('pid') ;
	if(pid.length>1){
		$('#'+pid).addClass("li_on");
		/* if($('#'+pid).offset().left-$('.header_navli_1').offset().left-sx_header_nav_cont-0.01>0){
			$('.sx_header_nav_cont').scrollLeft(sx_header_nav_cont);
			setMoreMenu(1) ;
		} */
		
		var w = 0 ;
		var p = 1 ;
		 var sx_header_nav_cont = $(".sx_header_nav_cont").width() ;
		 $('.header_navli').each(function(){
	  		w += $(this).width() ;
               if($(this).attr("id")==pid){
            	  return false ;
               }
	  	});
		if(w-sx_header_nav_cont-0.01>0) {
			p = 2 ;
		}
		setMoreMenu(p) ;
	}
}


function setMoreMenu(p) {
	////////////////////////////////////
	var w = 0 ;
	 var sx_header_nav_cont = $(".sx_header_nav_cont").width() ;
	 $('.header_navli').each(function(){
  		w += $(this).width() ;
  		if(p==2){
  			if((w-sx_header_nav_cont-0.01>0)){
  	  			$(this).css("display","block"); 
  	  		}else{
  	  			$(this).css("display","none"); 
  	  		}
  		}else{
  			if((w-sx_header_nav_cont-0.01>0)){
  	  			$(this).css("display","none"); 
  	  		}else{
  	  			$(this).css("display","block"); 
  	  		}
  		}
  			
  		
  	});
 	 


}



function changePwd() {
	$.dialog({
		lock : true,
		drag : false,
		resize : false,
		width : 470,
		height : 180,
		title : '修改密码',
		max : false,
		content : 'url:${ctx}/changePwd?layout=no',
		close : function() {
		}
	});

}

function cstyle() {
	 $.ajax({
		 type: "post",//使用get方法访问后台
            dataType: "text",//返回json格式的数据
           url: "${ctx }/cstyle",//要访问的后台地址
           data: {'layoutPage':'01'},//要发送的数据
            success: function(msg){//msg为返回的数据，在这里做数据绑定
            if(msg==1){
            	var url = window.location.href;
            	var href = location.href ;
            	if (url.indexOf('/html/') > -1) {
            		href = href + "?"+new Date().toUTCString() ;
            	}
            	location.href = href ;
                 //location.reload() ; 
            	}else{
            		alert('切换有误');
            	}
            }
	 }); 

}
function lgc_edit(){
	 $.dialog({lock: true,title:'编辑快递公司',drag: false,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/lgc/lgcedit?layout=no',close: function(){
		 
	 }});
}


function updata() {
	 $.ajax({
		 type: "post",//使用get方法访问后台
          dataType: "json",//返回json格式的数据
          beforeSend:loading,
          url: "${ctx }/updata",//要访问的后台地址
          data: {},//要发送的数据
           success: function(data){//msg为返回的数据，在这里做数据绑定
        	   loaded() ;
           if(data.su==1){
        	    jQuery.ajax({
        	    	  type: "get",
        		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js?t='+ new Date().getTime(),
        		      dataType: "script",
        		      cache: false
        		  }).done(function() {
        		    
        		 });
                }else{
                alert(data.su) ;
           	}
           }
	 });  
	 

}


function qbalance() {
	 $.ajax({
		 type: "post",//使用get方法访问后台
         dataType: "text",//返回json格式的数据
         url: "${ctx }/balance",//要访问的后台地址
         data: {},//要发送的数据
          success: function(data){//msg为返回的数据，在这里做数据绑定
          if('err'==data){
        	   alert('刷新失败') ;
            }else{
               $("#balance").html(data);
               alert('刷新成功') ;
          	}
          }
	 });  
	 

}



</script>




</head>

<body style="background:#e5ebf2; font-size:12px;color:#333;">
<div id="dataCache" style="display: none;"></div>
<table class="loading" style="display: none;">
<tr>
<td><img src="${ctx }/themes/default/images/loading.gif" alt="" /></td>
</tr>
</table>

<div style="background:#fff; min-width:1000px;"><!--页眉背景-->
<div class="sx_header">
  <div class="sx_header_l"><a href="${lgcConfig.webUrl}" target="_blank">
  <c:if test="${!empty lgcConfig.res.logo_new}"><img src="${lgcConfig.res.logo_new}" width="80" height="30"></c:if>
  <c:if test="${empty lgcConfig.res.logo_new}"><img src="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_logo_new.png" width="80" height="30"></c:if></a></div>
  <div class="sx_header_r">
  <c:if test="${'1' eq jm}">预付款余额：
   <span id="balance">${balance}</span>
  <a href="javascript:qbalance();" style="margin-right: 400px;">[刷新余额]</a>
  </c:if>
  <c:if test="${'1' eq warming}">您的余额已不多</c:if>
  <a href="javascript:updata();">[更新缓存]</a><!-- <a href="javascript:cstyle();">[切换旧版]</a> -->&nbsp;欢迎您，<shiro:user><shiro:principal property="realName" /></shiro:user>！
  <shiro:hasPermission name="ADMIN"><a href="javascript:lgc_edit();">[公司信息]</a></shiro:hasPermission>  <a href="javascript:changePwd();">[修改密码]</a> <a  href="${ctx}/logout">[退出登陆]</a></div>
</div>
</div>
<div class="sx_header_nav">
  <div class="sx_h_cont">
<div class="sx_header_nav_cont" >
  <ul class="sx_header_nav_cont_ul"  style=" min-width:2300px;">
 <c:forEach items="${menuList}" var="item" varStatus="status">
   <shiro:hasPermission name="${item.permisionCode}">
    <li class="header_navli header_navli_${status.count}" id="${item.permisionCode}_${status.count}"><a href="#" class="one">${item.menuText}</a>
     <div class="sx_nav_down" style="display: none;">
     
      <c:forEach items="${item.nodeList}" var="item1" varStatus="status1">
     	<shiro:hasPermission name="${item1.permisionCode}">
     	<div class="sx_nav_downli"><a href="${ctx}${item1.menuUri}" class="two <c:out value="${item1.menuUri eq cmenu.menu_uri?'curi':''}"></c:out>"  pid="${item.permisionCode}_${status.count}"><span>${item1.menuText}</span></a></div>
     	</shiro:hasPermission>
     	 </c:forEach>
 
     </div>
    </li>
      </shiro:hasPermission>
 
 </c:forEach>
 
  </ul>
  </div>
   <div class="hnav_arr" style="vertical-align: top;">
      <button class="hnav_arr_l" id="menu_left"><img src="/themes/default/images/arr_l.png"/></button>
      <button class="hnav_arr_l" id="menu_right"><img src="/themes/default/images/arr_r.png"/></button>
    </div>
  </div>
</div>


<div class="king">
  <div class="king_position">
   当前位置：<span id="pos">${cmenu.position_text}</span>
  </div>
			<sitemesh:body />

</div>
<audio controls="controls" id="ttips" style="display:none;" >
 <source src="/themes/default/tips_girl.wav" type="audio/wav" />
  <source src="/themes/default/tips_girl.ogg" type="audio/ogg" />  
  <source src="/themes/default/tips_girl.mp3" type="audio/mp3" /> 
 <embed height="100" width="100" src="/themes/default/tips_girl.wav" />
Your browser does not support this audio format.
</audio>
	<div class="height02"></div>
		<div class="news_pop" style="display: none;">
	<div class="news_pop_tit">
    	消息提醒<a href="javascript:void(0);" onclick="close_()"></a>
    </div>
    <div class="news_pop_cont">
    	<p class="pop_con"></p>
        <p  class="pop_name"></p>
<p  class="pop_phone"></p>
        <a href="javascript:void(0);" onclick="readed_()">不再提示</a>
    </div>
</div>
</body>
</html>