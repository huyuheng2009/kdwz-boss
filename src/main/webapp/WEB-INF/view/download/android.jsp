<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>下载</title>
 <meta charset="utf-8">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <meta content="fullscreen=yes,preventMove=no" name="ML-Config">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
    <style type="text/css">
        *{margin: 0; padding:0; font-family: "Microsoft YaHei";}
        html{ font-size: 16px; height: 100%;}
        body{ width: 100%; height: 100%;}  
        .dl{position: relative; max-width: 720px;margin: auto; height: 100%;} 
        .dl_bg{ width: 100%; height: 100%;  background: url(/themes/default/images/dl-bg.jpg) no-repeat; background-size: 100% 100%;}
        .dl_cont{ position: absolute; bottom: 0; left: 0; height: 36%; width: 100%; text-align: center; color: #333;
            display: -webkit-box; 
            display: -webkit-flex; 
            display: flex; 
            box-sizing: border-box; 
            -webkit-box-sizing: border-box;
            flex-direction:column; 
            -webkit-flex-direction:column;
            -webkit-box-orient:vertical;
            -webkit-box-direction: normal;
            -webkit-flex-direction: column;
            flex-direction: column;
        }
        .dl_p{ padding: 0 0 14px;-webkit-box-flex: 1; -moz-box-flex: 1;  -webkit-flex: 1; -ms-flex: 1; flex: 1;}
        .dl_tit{font-size: 22px; line-height: 2em;}
        .dl_p p{ font-size: 13px; }
        .dl_down{-webkit-box-flex: 1; -moz-box-flex: 1;  -webkit-flex: 1; -ms-flex: 1; flex: 1; padding: 0 0 20px;}
        .dl_down_no{  }
        .dl_p_icon{ height: 20px; }
        .dl_p_icon img{ width: auto; height: 100%; vertical-align: middle; display: block; margin: auto;}
        .dl_button{ padding: 10px; }
        .button{ padding: 0 2em; height: 2em; line-height: 1.8em; background: none; border: 1px solid #1b89ea; border-radius:1em;-moz-border-radius:1em; color: #1b89ea; font-size: 16px; display: block; margin: 0 auto;}
        .dl_down_yes{ display: none; }
        .dl_success{ color: #319f2c; font-size: 16px; line-height: 72px; }
        @media screen and (max-height: 500px){/*i4*/
            .dl_bg{  background: url(/themes/default/images/dl-bg4.jpg) no-repeat; background-size: 100% 100%;}
            .dl_down{ padding: 0 0 6px;}
        }
        @media screen and (min-width: 480px){/*ipad*/
            .dl_bg{  background: url(/themes/default/images/dl-bg4.jpg) no-repeat; background-size: 100% 100%;}
        }
        @media screen and (max-width: 360px){/*i4,i5*/
            .dl_p p{font-size: 12px;}
        }

    </style>
    <script type="text/javascript">
    $(function(){
    	
   
    });   
    </script>
</head>
<body>
<div class="dl">
    <div class="dl_bg"></div>
    <div class="dl_cont">
        <div class="dl_p">
            <div class="dl_tit">${app.platform }</div>
            <p>${app.version }</p>
            <p>更新于：${app.publishTime }</p>
        </div>
        <div class="dl_down">
            <div class="dl_down_no">
                <div class="dl_p_icon"><img src="/themes/default/images/android.png"></div>
                <div class="dl_button"><a  href="${app.address}" class="button" >下载安装</a></div>
            </div>
            <div class="dl_down_yes">
                <div class="dl_success">按住HOME键，返回桌面查看</div>
            </div>
        </div>
    </div>
</div>
</body>

</body>

</html>