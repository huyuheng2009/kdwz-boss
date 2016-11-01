<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/themes/default/map.css"/>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=50b92cafd039403ab7e117e9153dc66d"></script> 
    <script type="text/javascript">
  
        function lists(e){
        	if($(e).parent(".people_one").siblings('.people_two').css("display")=='none'){
        		$(e).parent(".people_one").siblings('.people_two').css("display","block") ;
        		$(e).find('.bt').removeClass("people_one_off") ;
        		$(e).find('.bt').addClass("people_one_on") ;
        	}else{
        		$(e).parent(".people_one").siblings('.people_two').css("display","none") ;
        		$(e).find('.bt').removeClass("people_one_on") ;
        		$(e).find('.bt').addClass("people_one_off") ;
        	}
        }  

        $(function () {
        	
        	Date.prototype.Format = function (fmt)
			{ //author: meizz   
				var o = {
					"M+": this.getMonth() + 1, //月份   
					"d+": this.getDate(), //日   
					"h+": this.getHours(), //小时   
					"m+": this.getMinutes(), //分   
					"s+": this.getSeconds(), //秒   
					"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
					"S": this.getMilliseconds()             //毫秒   
				};
				if (/(y+)/.test(fmt))
					fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
				for (var k in o)
					if (new RegExp("(" + k + ")").test(fmt))
						fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				return fmt;
			};
			function UpdateDate() {
				var date = new Date();
				var _date = date.Format("yyyy-MM-dd hh:mm:ss");
				$(".udate").html(_date);
			}
			setInterval(function () {
				UpdateDate();
			}, 1000);
        	
        	var mapObj = new AMap.Map('map');
        	mapObj.setZoom(10);
            var cno = ${cno};
            var pointList = ${pointList} ;
            var  lineArr = [];
            markers = []  ;
            for(var c in pointList){
           	 var lngX = pointList[c].track_longitude, latY = pointList[c].track_latitude;   
             lineArr.push([lngX, latY]);
         	if(c==0){
         		 var marker = new AMap.Marker({
                     icon : '/themes/default/images/map_begin.png',
                     position: [lngX, latY],
                     /* offset : new AMap.Pixel(-12,-12), */
                     map : mapObj
                   });
            	 markers.push(marker);
         	  }
         	if(c!=0&&c==pointList.length-1){
         		 var marker = new AMap.Marker({
                     icon : '/themes/default/images/map_end.png',
                     position: [lngX, latY],
                     map : mapObj
                   });
            	 markers.push(marker);
         	}
         	
           }
            
          /*   var lngX = 113.99012, latY = 22.596655;        
            lineArr.push([lngX, latY]);
            lineArr.push([113.991963,22.596143]);
            lineArr.push([113.992317,22.595915]);
            lineArr.push([113.99207,22.595162]);
            lineArr.push([113.991555,22.593637]);
            lineArr.push([113.991866,22.5923]);
            lineArr.push([113.990943,22.592438]);
            lineArr.push([113.99296,22.592111]);
            
            lineArr.push([113.996222,22.592022]);
            lineArr.push([114.000084,22.592508]);
            lineArr.push([114.00502,22.595707]);
            lineArr.push([114.008254,22.596772]);
            lineArr.push([114.010572,22.598367]); */
            // 绘制轨迹
            var polyline = new AMap.Polyline({
                map: mapObj,
                path: lineArr,
                strokeColor: "#db0000",  //线颜色
                strokeOpacity: 1,     //线透明度
                strokeWeight: 4,      //线宽
                strokeStyle: "solid"  //线样式
            });
             
           	setInterval(function () {
				$.post("/map/lineUpdate?cno="+cno, function (res) {
					mapObj.remove(markers);   //清除点
					markers = []  ;
					lineArr = [];
					   for(var c in res){
				           	 var lngX = res[c].track_longitude, latY = res[c].track_latitude;   
				             lineArr.push([lngX, latY]);
				         	if(c==0){
				         		 var marker = new AMap.Marker({
				                     icon : '/themes/default/images/map_begin.png',
				                     position: [lngX, latY],
				                     /* offset : new AMap.Pixel(-12,-12), */
				                     map : mapObj
				                   });
				            	 markers.push(marker);
				         	  }
				         	if(c!=0&&c==res.length-1){
				         		 var marker = new AMap.Marker({
				                     icon : '/themes/default/images/map_end.png',
				                     position: [lngX, latY],
				                     map : mapObj
				                   });
				            	 markers.push(marker);
				         	}
				         	
				           }
					   mapObj.remove(polyline);
				     polyline = new AMap.Polyline({
			                map: mapObj,
			                path: lineArr,
			                strokeColor: "#db0000",  //线颜色
			                strokeOpacity: 1,     //线透明度
			                strokeWeight: 4,      //线宽
			                strokeStyle: "solid"  //线样式
			            });
				}, "json");
			}, 50000);           //50秒更新
            
            var newCenter = mapObj.setFitView();     
        });
   
        
        

    </script>
</head>
<body>
<div  class="map" style="margin: 20px 0 0 0;">
<div id="map" style="width: 100%;height: 620px;padding: 20px 0 0 0;overflow: hidden;"></div>
  <div class="map_list" >
    <p class="udate"></p>
     <form:form  action="${ctx}/map/index" method="post">
         <div class="map_search"><input type="text" name="realName" value="${params.realName}" placeholder="快递员姓名">
         <input class="button" type="submit" value=""/>
         </div>
     </form:form>
    <div class="plist" style="height:480px; overflow-y: scroll;">
     <div class="map_people">
    	<div class="people_one"><button class="bt people_one_off"></button><a href="/map/index">返回首页</a></div>
    </div>
 <c:forEach items="${list}" var="item" varStatus="status">
    <div class="map_people">
    	<div class="people_one"><a onclick="lists(this)"><button class="bt people_one_on"></button>${item.substation_name}</a></div>
     
        <div class="people_two">
        	<ul>
        	<c:forEach items="${item.clist}" var="item1" varStatus="status1">
            <li class="<c:out value="${item1.online ne 'Y'?'people_no':'' }"/>"><a href="/map/polyline?cno=${item1.courier_no}">${item1.real_name}</a></li>
             </c:forEach> 
            </ul>
        </div>
         
     </div>
   </c:forEach>   
  </div>  <!--  plist end    --> 
    
  </div>
</div>  
</body>

</html>