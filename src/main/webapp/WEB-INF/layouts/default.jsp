<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>${lgcConfig.webTitle}<sitemesh:title />
</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/main_new.css"/>
<%@ include file="/header.jsp"%>
<style>
html, body {
	width: 100%;
	height: 100%;
	min-width:1400px;
	min-height:100%;
	margin: 0;
	padding: 0;
	
}
body{background: none !important;}
input{
overflow: hidden;
padding-left: 5px;
}

</style>
<script language="javascript">
	function qiehuan(pid,flag) {
		if(flag==1){
			 $(".menu_nav_two").css("display", "none");
		}
		if(pid.length>1){
			if("block"==$('.'+pid).css("display")){
				$('.'+pid).css("display", "none");
			}else{
				$('.'+pid).css("display", "block");
			}
		}
		
		setSize();
	}
	var menuHover = false ;
	var fn ;
	var fm ;
	$(function() {
		setSubMenu();
		setTable();
		setSize();
		//var bodyh = $("body").css("height").substr(0,$("body").css("height").length-2) ;
		//$("#rr").css("min-height",bodyh-110+'px'); 
		
	  });

	$(window).resize(function(){
		 setSize();
		//var bodyh = $("body").css("height").substr(0,$("body").css("height").length-2) ;
		//$("#rr").css("min-height",bodyh-110+'px'); 
		});
	
	function setTable() {
		$("table").addClass("tableble_table");
		$("table th").addClass("tableble_tit center");
		$("table td").addClass("center");
	}
	
	function getStyle() {
	return 0 ;
	}
	/* 设置左边菜单高度 */
	function setSize() {


		$(".l_list").css("min-height",$(".mul").css("height")); 
		var bodyh = $("body").css("height").substr(0,$("body").css("height").length-2) ;
		$("#map").css("height",bodyh - 130+'px');  
		var l_listh = $(".l_list").css("height").substr(0,$(".l_list").css("height").length-2) ;
		var rc = $("#rc").css("height").substr(0,$("#rc").css("height").length-2) ;
		if((bodyh-1)<(rc-1)){
			$("body").css("height",$("#rc").css("height"));
			if(rc-l_listh>0){
				 var s = rc-l_listh ;
				 if(s-110>0){
					 $(".l_list").css("height",rc-110+'px'); 
				 }else{
					 $(".l_list").css("height",rc-s+'px'); 
				 }
			}
		}else{
			if((bodyh-1)<(l_listh-1)){
				$("body").css("height",l_listh-(-110)+'px');
			}else{
				 var s1 = bodyh-l_listh ; 
				 if(s1-110>0){
					 $(".l_list").css("height",bodyh-110+'px'); 
				 }else{
					  //$(".l_list").css("height",bodyh-110+'px'); 
					  $("body").css("height",l_listh-(-110)+"px");
					  //$(".l_list").css("height", $("body").css("height")); 
					 // l_listh = $(".l_list").css("height").substr(0,$(".l_list").css("height").length-2) ;
					// $("body").css("height",l_listh-(-s1)+"px");
				 }
			}
		}
		
	}
	
	function setSubMenu() {
			var pid = $('.dropdown_on').attr('pid') ;
			if(pid.length>1){
				$('#'+pid).addClass("navbg_on");
				$('.'+pid).css("display", "block");
			}
			setSize();	
	}
	function cstyle() {
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/cstyle",//要访问的后台地址
	            data: {},//要发送的数据
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
</script>
<sitemesh:head />
</head>
<body>
<table class="loading" style="display: none;">
<tr>
<td><img src="${ctx }/themes/default/images/loading.gif" alt="" /></td>
</tr>
</table>
<div class="header"  style="">
	<div class="logo"><a href="${lgcConfig.webUrl}" target="_blank"><img src="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_logo.png" id="logo_img" style="" alt="logo" /></a></div>

    <div class="my_info">
    
    	<div class="myinfo_img">
        	<div class="myinfo_pic">
        	
        	<img src="${ctx }/themes/default/images/himg.png" id="himg" style="" alt="头像" />
        	</div>
        </div>
       
        <div class="myinfo_cont">
        	<a class="myinfo_tit" href="###"> 欢迎您：<shiro:user><shiro:principal property="realName" /></shiro:user></a>
            <a class="myinfo_li" href="###">邮箱：<shiro:user><shiro:principal property="email" /></shiro:user></a>
          <!--   <a class="myinfo_li" href="javascript:changePwd();">修改密码</a> -->
            <a class="myinfo_li" href="${ctx}/logout">退出登陆</a>
        </div>
    </div>
    	<div style="margin-top: 75px;float: right;text-align: right;width: 500px;"><a style="color: white;" href="javascript:cstyle();">[进入新版]</a>&nbsp;&nbsp;</div>
</div>

<div style="" id="rc">
<div class="left_list" style="">
    <div class="l_list">
      <ul class="mul">
        <li class="l_navtit">导航</li>
   <c:forEach items="${menuList}" var="item" varStatus="status">      
        <shiro:hasPermission name="${item.permisionCode}">
		<li id="mynav2" class="menu_nav_one"><a href="###"  onclick="javascript:qiehuan('${item.permisionCode}_${status.count}',0)" class="l_navli navbg_user" id="${item.permisionCode}_${status.count}">${item.menuText}<span class="l_nav_arr"></span></a>
		  <ul class="menu_nav_two ${item.permisionCode}_${status.count}" style="display:none;">
		    <c:forEach items="${item.nodeList}" var="item1" varStatus="status1">
							<shiro:hasPermission name="${item1.permisionCode}">
								<li id="system_user"><a href="${ctx}${item1.menuUri}" class="dropdown <c:out value="${item1.menuUri eq cmenu.menu_uri?'dropdown_on':''}"></c:out>"   pid="${item.permisionCode}_${status.count}"><span>${item1.menuText}</span></a></li>
							</shiro:hasPermission>
		  </c:forEach>				

							
			 </ul>
		</li>
	  </shiro:hasPermission>
	  
 </c:forEach>
 
					
      </ul>
    </div>
  </div>
<div id="rr" style="">
<div class="right_cont" style="height: 100%;">

  <div class="box_right" style="padding-bottom: 60px;">
				<sitemesh:body />
	</div>
 </div>
</div>
</div>
</body>
</html>