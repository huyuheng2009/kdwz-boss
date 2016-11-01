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
<%@ include file="/header_01.jsp"%>

<sitemesh:head />

<script language="javascript">
$(function() {
	setSubMenu() ;
	$('.header_navli').hover(function() {
		 $('.sx_nav_down').css("display","none");
            $(this).find('.sx_nav_down').css("display","block");
        	$(this).addClass('hover_on');
		}, function() {
			 $('.sx_nav_down').css("display","none");
			 $(this).removeClass('hover_on');
		});
});	

function getStyle() {
	return 1 ;
	}

function setSubMenu() {
	var url = window.location.href;
	//二级菜单

 
	////////////////////////////////////
	if (url.indexOf('system/user') > -1) {
		$('#SYSTEM_MANAGE').addClass("li_on");
		$('#pos').html("系统＞系统用户") ;
		return ;
	}
	if (url.indexOf('system/ugroup') > -1) {
		$('#SYSTEM_MANAGE').addClass("li_on");
		$('#pos').html("系统＞角色管理") ;
		return ;
	}
	if (url.indexOf('system/groupauth') > -1) {
		$('#SYSTEM_MANAGE').addClass("li_on");
		$('#pos').html("系统＞角色管理") ;
		return ;
	}
	if (url.indexOf('system/auth') > -1) {
		$('#SYSTEM_MANAGE').addClass("li_on");
		$('#pos').html("系统＞权限管理") ;
		return ;
	}
	if (url.indexOf('system/log') > -1) {
		$('#SYSTEM_MANAGE').addClass("li_on");
		$('#pos').html("系统＞系统日志") ;
		return ;
	}
	
	//////////////////////////////////////////////	
   if (url.indexOf('order/add') > -1) {
	   $('#ORDER_MANAGE').addClass("li_on");
	   $('#pos').html("运单＞在线下单") ;
	   $('.split').css("display","none") ;
		return ;
	}
	if (url.indexOf('/order/list') > -1) {
		 $('#ORDER_MANAGE').addClass("li_on");
		 $('#pos').html("运单＞订单列表") ;
			return ;
	}
	if (url.indexOf('order/plane') > -1) {
		 $('#ORDER_MANAGE').addClass("li_on");
		 $('#pos').html("运单＞面单上传") ;
			return ;
	}
	if (url.indexOf('order/input') > -1) {
		 $('#ORDER_MANAGE').addClass("li_on");
		 $('#pos').html("运单＞收件补录") ;
		 $('.split').css("display","none") ;
			return ;
	}
	if (url.indexOf('order/sinput') > -1) {
		 $('#ORDER_MANAGE').addClass("li_on");
		 $('#pos').html("运单＞签收补录") ;
		 $('.split').css("display","none") ;
			return ;
	}
	
	if (url.indexOf('order/examine') > -1) {
		 $('#ORDER_MANAGE').addClass("li_on");
		 $('#pos').html("运单＞订单审核") ;
			return ;
	}
	if (url.indexOf('order/tracks') > -1) {
		 $('#ORDER_TRACKS').addClass("li_on");
		 $('#pos').html("运单追踪＞查询") ;
			return ;
	}
	
	
//////////////////////////////////////////////
   if (url.indexOf('mobile/userlist') > -1) {
	   $('#MUSER_MANAGE').addClass("li_on");
	   $('#pos').html("客户＞普通用户") ;
		return ;
	}

   if (url.indexOf('mobile/muser_list') > -1) {
	   $('#MUSER_MANAGE').addClass("li_on");
	   $('#pos').html("客户＞月结客户") ;
		return ;
	}
   if (url.indexOf('mobile/cuser_list') > -1) {
	   $('#MUSER_MANAGE').addClass("li_on");
	   $('#pos').html("客户＞代收货款客户") ;
		return ;
	}
//////////////////////////////////////////////
	 if (url.indexOf('scan/clist') > -1) {
		 $('#SCAN_MANAGE').addClass("li_on");
		 $('#pos').html("扫描＞快递员收件") ;
			return ;
	 }	
	if (url.indexOf('scan/colist') > -1) {
		 $('#SCAN_MANAGE').addClass("li_on");
		 $('#pos').html("扫描＞快递员派件") ;
			return ;
	}	
							
	if (url.indexOf('scan/slist') > -1) {
		 $('#SCAN_MANAGE').addClass("li_on");
		 $('#pos').html("扫描＞网点到站扫描") ;
			return ;
	 }	
							
	if (url.indexOf('scan/solist') > -1) {
		 $('#SCAN_MANAGE').addClass("li_on");
		 $('#pos').html("扫描＞网点出站扫描") ;
			return ;
		 }	
	if (url.indexOf('scan/exchange') > -1) {
		 $('#SCAN_MANAGE').addClass("li_on");
		 $('#pos').html("扫描＞转件扫描") ;
			return ;
		 }	
	///////////////////////////////////
	if (url.indexOf('porder/list') > -1) {
		 $('#PRO_ORDER').addClass("li_on");
		 $('#pos').html("问题件＞问题件查询") ;
			return ;
		 }							

	if (url.indexOf('porder/rlist') > -1) {
		 $('#PRO_ORDER').addClass("li_on");
		 $('#pos').html("问题件＞问题件原因配置") ;
			return ;
		 }	
	

/////////////////////////////////////////////

	if (url.indexOf('lgc/lgclist') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快递公司") ;
			return ;
	}
	if (url.indexOf('lgc/slist') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快递分站") ;
			return ;
	}
	if (url.indexOf('lgc/clist') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快递员") ;
			return ;
	}
	if (url.indexOf('lgc/ccount') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快递员") ;
			return ;
	}
	if (url.indexOf('lgc/csarea') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快递员收派范围") ;
			return ;
	}
	
	if (url.indexOf('lgc/freight_rule') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞快运费规则") ;
			return ;
	}
	if (url.indexOf('lgc/valuation_rule') > -1) {
		 $('#LGC_MANAGE').addClass("li_on");
		 $('#pos').html("公司＞保价规则") ;
			return ;
	}
	
	
////////////////////////////////////
    if (url.indexOf('dict/area') > -1) {
    	 $('#DICT_MANAGE').addClass("li_on");
    	 $('#pos').html("字典＞区域字典") ;
			return ;
	}
    if (url.indexOf('dict/company') > -1) {
    	 $('#DICT_MANAGE').addClass("li_on");
    	 $('#pos').html("字典＞公司字典") ;
			return ;
	}
 ////////////////////////////////   
   
    if (url.indexOf('client/product') > -1) {
    	 $('#MOBILE_MANAGE').addClass("li_on");
    	 $('#pos').html("客户端＞软件产品") ;
			return ;
	}
    if (url.indexOf('client/appversion') > -1) {
    	 $('#MOBILE_MANAGE').addClass("li_on");
    	 $('#pos').html("客户端＞软件版本") ;
			return ;
	}
/////////////////////////////////////    
    if (url.indexOf('gather/sinput') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞网点录入") ;
    	 $('.split').css("display","none") ;
			return ;
	}

    if (url.indexOf('gather/list') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞流水记录") ;
			return ;
	}

    
    if (url.indexOf('gather/slist') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞网点收款登记表") ;
			return ;
	}
    
    if (url.indexOf('gather/clist') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞公司收款登记表") ;
			return ;
	}
    
    if (url.indexOf('gather/cshow') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞网点录入") ;
			return ;
	}
    
    if (url.indexOf('gather/cexam') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞快递员收款审核") ;
			return ;
	}
    
  
    
    if (url.indexOf('gather/mshow') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
			return ;
	}
    
    if (url.indexOf('gather/mexam') > -1) {
    	 $('#GATHER_MANAGE').addClass("li_on");
    	 $('#pos').html("收款＞月结客户收款审核") ;
			return ;
	}
///////////////////////////

    if (url.indexOf('/matter/matter_pro') > -1) {
    	 $('#MATTER_MANAGE1').addClass("li_on");
			return ;
	}

    if (url.indexOf('/matter/enter') > -1) {
    	 $('#MATTER_MANAGE1').addClass("li_on");
			return ;
	}

/////////////////////


////////////////////////////////
	   if (url.indexOf('substatic/dayCount') > -1) {
		   $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    if (url.indexOf('substatic/pdayCount') > -1) {
    	 $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    if (url.indexOf('substatic/monthCount') > -1) {
    	 $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    if (url.indexOf('substatic/monthUserCount') > -1) {
    	 $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    if (url.indexOf('substatic/monthUserRevCount') > -1) {
    	 $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    if (url.indexOf('substatic/substationDayCount') > -1) {
    	 $('#SUBSTATIC_MANAGE').addClass("li_on");
			return ;
	}
    
    /////////////////////
   if (url.indexOf('/disuser/uadd') > -1) {
	   $('#DISUSER_MANAGE').addClass("li_on");
	   $('#pos').html("会员＞会员新增") ;
	   $('.split').css("display","none") ;
		return ;
	}
    
    
   if (url.indexOf('/disuser/urecharge') > -1) {
	   $('#DISUSER_MANAGE').addClass("li_on");
	   $('#pos').html("会员＞会员充值") ;
	   $('.split').css("display","none") ;
		return ;
	}
    
    if (url.indexOf('/disuser/list') > -1) {
    	 $('#DISUSER_MANAGE').addClass("li_on");
    	  $('#pos').html("会员＞会员列表") ;
			return ;
	}
    
    if (url.indexOf('/disuser/tlist') > -1) {
    	 $('#DISUSER_MANAGE').addClass("li_on");
    	  $('#pos').html("会员＞优惠类型") ;
			return ;
	}
    
    if (url.indexOf('/disuser/rlist') > -1) {
    	 $('#DISUSER_MANAGE').addClass("li_on");
    	  $('#pos').html("会员＞充值流水") ;
			return ;
	}
    
    if (url.indexOf('/disuser/clist') > -1) {
    	 $('#DISUSER_MANAGE').addClass("li_on");
    	  $('#pos').html("会员＞扣款流水") ;
			return ;
	}
    
//////////////////    
      if (url.indexOf('/download/client') > -1) {
    	  $('#DOWNLOAD').addClass("li_on");
    	  $('#pos').html("下载＞客户端下载") ;
			return ;
	}
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
            data: {},//要发送的数据
            success: function(msg){//msg为返回的数据，在这里做数据绑定
            if(msg==1){
                   location.reload() ; 
            	}else{
            		alert('切换有误');
            	}
            }
	 }); 

}



</script>




</head>
<body style="background:#e5ebf2; font-size:12px;color:#333;">
<div style="background:#fff; min-width:1000px;"><!--页眉背景-->
<div class="sx_header">
  <div class="sx_header_l"><img src="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_logo_new.png" width="80" height="30"></div>
  <div class="sx_header_r"><a href="javascript:cstyle();">[切换旧版]</a>&nbsp;欢迎您，<shiro:user><shiro:principal property="realName" /></shiro:user>！
  <a href="javascript:changePwd();">[修改密码]</a><a  href="${ctx}/logout">[退出登陆]</a></div>
</div>
</div>
<div class="sx_header_nav">
  <ul>
   <shiro:hasPermission name="SYSTEM_MANAGE">
    <li class="header_navli" id="SYSTEM_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">系统</a>
     <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="SYSTEM_USER">
     	<div class="sx_nav_downli"><a href="${ctx}/system/user" class="two"><span>系统用户</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="ADMIN">
     	<div class="sx_nav_downli"><a href="${ctx }/system/ugroup" class="two"><span>角色管理</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="PERMISSION_MANAGE">
     	<div class="sx_nav_downli"><a href="${ctx }/system/auth" class="two"><span>权限管理</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="OPERATE_LOG">
     	<div class="sx_nav_downli"><a href="${ctx }/system/log" class="two"><span>操作日志</span></a></div>
     	</shiro:hasPermission>
     	
     	
     </div>
    </li>
      </shiro:hasPermission>
      
      
      	<shiro:hasPermission name="LGC_MANAGE">
         <li class="header_navli" id="LGC_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">公司</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="LGC_MANAGE_LIST">
     	<div class="sx_nav_downli"><a href="${ctx}/lgc/lgclist" class="two"><span>快递公司</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="LGC_SUBSTATION">
     	<div class="sx_nav_downli"><a href="${ctx }/lgc/slist" class="two"><span>快递分站</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="LGC_COURIER">
     	<div class="sx_nav_downli"><a href="${ctx }/lgc/clist" class="two"><span>快递员</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="LGC_COURIER">
     	<div class="sx_nav_downli"><a href="${ctx }/lgc/csarea" class="two"><span>快递员收派范围</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="LGC_FRULE1">
     	<div class="sx_nav_downli"><a href="${ctx }/lgc/freight_rule" class="two"><span>运费规则</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="LGC_VRULE1">
     	<div class="sx_nav_downli"><a href="${ctx }/lgc/valuation_rule" class="two"><span>保价规则</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>
    
<shiro:hasPermission name="MUSER_MANAGE">
         <li class="header_navli" id="MUSER_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">客户</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="MOBILE_USER">
     	<div class="sx_nav_downli"><a href="${ctx}/mobile/userlist" class="two"><span>普通用户</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="MUSER_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/mobile/muser_list" class="two"><span>月结用户</span></a></div>
     	</shiro:hasPermission>

      <shiro:hasPermission name="MUSER_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/mobile/cuser_list" class="two"><span>代收货款用户</span></a></div>
     	</shiro:hasPermission>
     	
     	</div>
     	
          </li>
      </shiro:hasPermission>
      

    	<shiro:hasPermission name="ORDER_MANAGE">
         <li class="header_navli" id="ORDER_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">运单</a>
          <div class="sx_nav_down"  style="display: none;">
     	<shiro:hasPermission name="ORDER_ADD">
     	<div class="sx_nav_downli"><a href="${ctx }/order/add" class="two"><span>在线下单</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="ORDER_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/order/list" class="two"><span>运单列表</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="ORDER_LIST1">
     	<div class="sx_nav_downli"><a href="${ctx }/order/plane" class="two"><span>面单上传</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="ORDER_INPUT">
     	<div class="sx_nav_downli"><a href="${ctx }/order/input" class="two"><span>收件补录</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="ORDER_SINPUT">
     	<div class="sx_nav_downli"><a href="${ctx }/order/sinput" class="two"><span>签收录入</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="ORDER_EXAMINE">
     	<div class="sx_nav_downli"><a href="${ctx }/order/examine" class="two"><span>运单审核</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>      
      
      <shiro:hasPermission name="SCAN_MANAGE">
         <li class="header_navli" id="SCAN_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">扫描</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="SCAN_CREV">
     	<div class="sx_nav_downli"><a href="${ctx }/scan/clist" class="two"><span>快递员收件</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SCAN_CSEND">
     	<div class="sx_nav_downli"><a href="${ctx }/scan/colist" class="two"><span>快递员派件</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SCAN_SREV">
     	<div class="sx_nav_downli"><a href="${ctx }/scan/slist" class="two"><span>网点到站扫描</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SCAN_SSEND">
     	<div class="sx_nav_downli"><a href="${ctx }/scan/solist" class="two"><span>网点出站扫描</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="SCAN_EXCHANGE">
     	<div class="sx_nav_downli"><a href="${ctx }/scan/exchange" class="two"><span>转件扫描</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>      
      
      
        <shiro:hasPermission name="ORDER_TRACKS">
         <li class="header_navli" id="ORDER_TRACKS"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">运单追踪</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="ORDER_TRACKS_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/order/tracks" class="two"><span>查&#12288;&#12288;询</span></a></div>
     	</shiro:hasPermission>
 

     	</div>
     	
          </li>
      </shiro:hasPermission>   

      
        <shiro:hasPermission name="PRO_ORDER">
         <li class="header_navli" id="PRO_ORDER"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">问题件</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="PRO_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/porder/list" class="two"><span>问题件查询</span></a></div>
     	</shiro:hasPermission>
     	<shiro:hasPermission name="PRO_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/porder/rlist" class="two"><span>问题件原因配置</span></a></div>
     	</shiro:hasPermission>

     	</div>
     	
          </li>
      </shiro:hasPermission>   


 <shiro:hasPermission name="DISUSER_MANAGE">
         <li class="header_navli" id="DISUSER_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">会员</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="DISUSER_ADD">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/uadd" class="two"><span>会员新增</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="DISUSER_URECHARGE">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/urecharge" class="two"><span>会员充值</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="DISUSER_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/list" class="two"><span>会员列表</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="DISUSER_TLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/tlist" class="two"><span>优惠管理</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="DISUSER_RLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/rlist" class="two"><span>充值流水</span></a></div>
     	</shiro:hasPermission>
     	<shiro:hasPermission name="DISUSER_CLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/disuser/clist" class="two"><span>扣款流水</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>   
      
      
      <shiro:hasPermission name="GATHER_MANAGE">
         <li class="header_navli" id="GATHER_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">收款</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="GATHER_SINPUT">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/sinput" class="two"><span>网点录入</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="GATHER_LIST">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/list" class="two"><span>流水记录</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="GATHER_SLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/slist" class="two"><span>网点收款登记表</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="GATHER_CLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/clist" class="two"><span>公司收款登记表</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="GATHER_CEXAM">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/cexam" class="two"><span>快递员收款审核</span></a></div>
     	</shiro:hasPermission>
     	<shiro:hasPermission name="GATHER_MEXAM">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/mexam" class="two"><span>月结客户收款审核</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>  
      
      <shiro:hasPermission name="MATTER_MANAGE1">
         <li class="header_navli" id="MATTER_MANAGE1"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">物料</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="MATTER_ENTER">
     	<div class="sx_nav_downli"><a href="${ctx }/matter/enter" class="two"><span>物料新增</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="MATTER_">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/list" class="two"><span>物料发放</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="GATHER_SLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/slist" class="two"><span>面单使用率</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="GATHER_CLIST">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/clist" class="two"><span>库存盘点</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="GATHER_CEXAM">
     	<div class="sx_nav_downli"><a href="${ctx }/gather/cexam" class="two"><span>实时库存</span></a></div>
     	</shiro:hasPermission>
     	<shiro:hasPermission name="GATHER_MEXAM">
     	<div class="sx_nav_downli"><a href="${ctx }/matter/matter_pro" class="two"><span>品名维护</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>   
      
       <shiro:hasPermission name="DICT_MANAGE">
         <li class="header_navli" id="DICT_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">数据字典</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="DICT_AREA">
     	<div class="sx_nav_downli"><a href="${ctx }/dict/area" class="two"><span>地区字典</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="DICT_CPN">
     	<div class="sx_nav_downli"><a href="${ctx }/dict/company" class="two"><span>公司字典</span></a></div>
     	</shiro:hasPermission>
     
     	</div>
     	
          </li>
      </shiro:hasPermission>   
      
       
        <shiro:hasPermission name="MOBILE_MANAGE">
         <li class="header_navli" id="MOBILE_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">客户端</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="MOBILE_PRODUCT">
     	<div class="sx_nav_downli"><a href="${ctx }/client/product" class="two"><span>软件产品</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="MOBILE_VERSION">
     	<div class="sx_nav_downli"><a href="${ctx }/client/appversion" class="two"><span>软件版本</span></a></div>
     	</shiro:hasPermission>
     	</div>
          </li>
      </shiro:hasPermission>   
      
            <shiro:hasPermission name="SUBSTATIC_MANAGE">
         <li class="header_navli" id="SUBSTATIC_MANAGE"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">财务统计</a>
          <div class="sx_nav_down" style="display: none;">
     	<shiro:hasPermission name="SUBSTATIC_DAYCOUNT">
     	<div class="sx_nav_downli"><a href="${ctx }/substatic/dayCount" class="two"><span>快递员日统计</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SUBSTATIC_PDAYCOUNT">
     	<div class="sx_nav_downli"><a href="${ctx}/substatic/pdayCount" class="two"><span>快递员每日收派</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SUBSTATIC_MONTHCOUNT">
     	<div class="sx_nav_downli"><a href="${ctx}/substatic/monthCount" class="two"><span>月结客户对账单——加回单</span></a></div>
     	</shiro:hasPermission>
     	
     	<shiro:hasPermission name="SUBSTATIC_MONTHUSERCOUNT">
     	<div class="sx_nav_downli"><a href="${ctx}/substatic/monthUserCount" class="two"><span>月结客户明细汇总表</span></a></div>
     	</shiro:hasPermission>
     	 	<shiro:hasPermission name="SUBSTATIC_MUSERRC">
     	<div class="sx_nav_downli"><a href="${ctx}/substatic/monthUserRevCount" class="two"><span>月结客户每月发货统计</span></a></div>
     	</shiro:hasPermission>
     	<shiro:hasPermission name="SUBSTATIC_SDC">
     	<div class="sx_nav_downli"><a href="${ctx}/substatic/substationDayCount" class="two"><span>网点每日收派件统计</span></a></div>
     	</shiro:hasPermission>
     	</div>
     	
          </li>
      </shiro:hasPermission>  
       
              
         <li class="header_navli" id="DOWNLOAD"><a href="#" class="one" onclick="javascript:qiehuan(2,0)">下载</a>
          <div class="sx_nav_down" style="display: none;">
     	<div class="sx_nav_downli"><a href="${ctx}/download/client" class="two"><span>客户端下载</span></a></div>
     	</div>
          </li>

  </ul>
</div>


<div class="king">
  <div class="king_position">
   当前位置：<span id="pos"></span>
  </div>
			<sitemesh:body />

</div>


	<div class="height02"></div>
	
</body>
</html>