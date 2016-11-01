<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/Upload.js"></script>
<style>
            #uploadXLS {display: none;z-index: 100;background: white;border-bottom: 1px solid #DCDCDC;}
            #uploadXLS input:first-child {display: none;}
            #uploadXLS .a {height: 2em;line-height: 2em;text-indent: 20px;}
            #uploadXLS .a span.a1{font-weight: bolder;}
            #uploadXLS .p{height: 20px;width: calc(100% - 40px);margin: 10px auto;
                          box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -webkit-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -ms-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -o-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
                          -moz-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);}
            #uploadXLS .p2 {height: 100%;width: 0;background: #E25A48;}
             #uploadXLS .p2_green {height: 100%;width: 0;background:green;}
</style>
    <script type="text/javascript">
    $(function(){
    	 var u = new Upload($("#uploadXLS"), "", "");
         $("#importFile").click(function(evt) {
        	 var rtype = $("#rtype").val().split("|")[0];
         	var fix = $("#rtype").val().split("|")[1];
         	var rtext = $("#rtype").find("option:selected").text();
         	u.accept(fix) ;
         	u.uploadUrl("/lgc/resource_upload");
         	u.setParams({params: {"rtype": rtype,"rtext": rtext}});
             if (!u.lock) {
                 u.$file.click();
             }else{
            	alert("等待上一个上传完毕");
             }
             evt.preventDefault();
         });
         
         $("#saveFile").click(function(evt) {
        	 var r = /^[a-zA-Z0-9]+$/ ; 
        	 var lgc_key = $("#lgc_key").val() ;
        	 if(!r.test(lgc_key)){
        		 alert("请输入正确的app验证码") ;
        		 return false;
        	 }
        	 var web_xd_addr = $("#web_xd_addr").val() ;
        	  $.ajax({url: '/lgc/resource_save', data: {'lgc_key':lgc_key,'web_xd_addr':web_xd_addr}, success: function (msg) {
                  if (msg == 1) {
                      alert('保存成功');
                  } else {
                      alert(msg);
                  }
              }});
         });
    	
    });   
    </script>
</head>
<body>

<div style="margin-top: 50px;">
<div style="text-align: center;font-size: 16px;">

<select id="rtype" name="rtype" style="width: 300px;height: 45px; font-size: 16px;margin-right: 30px;padding-left: 10px;">
							<option value="logo_login|.png,.jpg,.jpeg">登录页面logo</option>
							<option value="logo_old|.png,.jpg,.jpeg">旧版后台logo</option>
							<option value="logo_new|.png,.jpg,.jpeg">新版后台logo</option>
							<option value="title|.png,.jpg,.jpeg">页面标题图片</option>
							<option value="login|.png,.jpg,.jpeg" >app登录logo</option>
							<option value="index|.png,.jpg,.jpeg">app个人中心</option>
							<option value="help_word|.doc,.docx" >后台使用说明</option>
			</select>
	 <input class="button input_text  medium blue_flat sear_butt" style="font-size: 16px;width: 130px; height: 45px;line-height: 45px;margin: 0 auto 40px;border-radius: 5px;" id="importFile" type="button" value="选择文件"/>
		 <input class="button input_text  medium blue_flat sear_butt" style="font-size: 16px;width: 220px; height: 45px;line-height: 45px;margin: 0 auto 40px;margin-left:30px;border-radius: 5px;" id="saveFile" type="button" value="全部保存"/>	
</div>
    <div id="uploadXLS">
                        <input type="file" />
         <div class="a"> 上传　<span class="a1"></span><span class="a2"></span></div>
         <div class="p"><div class="p2"></div></div>
     </div>
     
</div>
 <div style="margin-top: 50px;text-align: center;">
  	
  </div>                  
       <div><span style="width: 200px;display: inline-block;text-align: right;">app验证码：</span><input type="text" value="${cMap.lgc_key}" id="lgc_key" name="lgc_key" style="width: 300px;height: 45px;margin: 20px 0;"/></div>
  	<div><span style="width: 200px;display: inline-block;text-align: right;">批量下单地址：</span><input type="text" value="${cMap.web_xd_addr}"  id="web_xd_addr" name="web_xd_addr" style="width: 300px;height: 45px;"/></div>         
</body>

</html>