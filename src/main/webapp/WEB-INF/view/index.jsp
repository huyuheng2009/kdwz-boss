<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript" src="${ ctx}/scripts/esl.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
var fileLoadcation = '${ctx}/scripts/echarts' ;
require.config({
    paths:{ 
    	 'echarts' : fileLoadcation,
        'echarts/chart/line' : fileLoadcation,
        'echarts/chart/bar' : fileLoadcation,
        'echarts/chart/pie' : fileLoadcation
    }
});

require(
	    [
	        'echarts',
	       'echarts/chart/line',   // 按需加载所需图表
	      'echarts/chart/bar',
	      'echarts/chart/pie'
	    ],
	    function (ec) {
	    	var d = ${transRinkRes} ;
	    	 var transRinkChart = ec.init(document.getElementById('transRinkChart'));
	    	 var mccChart = ec.init(document.getElementById('mccChart'));
	         var transOfDayChart = ec.init(document.getElementById('transOfDayChart'));
	         var cdata1 = new Array();
	         var vdata1 = new Array();
	       var res = ${transRinkRes};
	       for(var i=res.transRinkList.length-1;i>=0;i--){
	    	   cdata1.push(res.transRinkList[i].merchant_name);
	    	   vdata1.push(res.transRinkList[i].trans_amount);
	       }
if(res.transRinkList.length==0){
	    		cdata1.push('暂无数据');
	    		vdata1.push(0);
}
////////////////////////////////////
transRinkChartOption = {
			    title : {
			        text: '这月截止昨天交易排名前十商户',
			        subtext: '数据单位(万元)'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value',
			            boundaryGap : [0, 0.01]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
			            data : cdata1
			        }
			    ],
			    series : [
			        {
			            name:'交易总额(万元)',
			            type:'bar',
			            data:vdata1,
			            itemStyle: {
			                normal: {          
			                    label : {
			                        show : true,
			                        textStyle : {
			                            fontSize : '12',
			                            fontFamily : '微软雅黑'
			                        }, 
			                        formatter: "{c}万元"
			                    }
			                }
			            }
			        }
			    ]
			};
//////////////////////////////////
var cdata2 = new Array();
var vdata2 = new Array();
res = ${mccTransRes};
for(var i=res.mccTranList.length-1;i>=0;i--){
	if(res.mccTranList[i].type==null){
		cdata2.push('手机和付通');
		vdata2.push({value:res.mccTranList[i].trans_amount, name:'手机和付通'});
	}else{
		cdata2.push(res.mccTranList[i].type);
		vdata2.push({value:res.mccTranList[i].trans_amount, name:res.mccTranList[i].type});
	}
}
if(res.mccTranList.length==0){
	cdata2.push('暂无数据');
	vdata2.push({value:0, name:'暂无数据'});
}
mccChartOption = {
    title : {
        text: '行业在总交易量的占比',
        subtext: '数据单位(万元)',
        x:'left'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}万元 ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'right',
        data:cdata2
    },
    toolbox: {
        show : false,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'行业类别',
            type:'pie',
            radius : '45%',
            center: ['50%', '60%'],
            data:vdata2,
            itemStyle: {
                normal: {          
                    label : {
                        show : true,
                        textStyle : {
                            fontSize : '12',
                            fontFamily : '微软雅黑'
                        },  
                        formatter: "{c}万元"
                    }
                }
            }
        }
    ]
};
 /////////////////////////////////
var cdata3 = new Array();
var vdata3 = new Array();
res = ${lastTenDayTransRes};
var date = new Date();
date.setDate(date.getDate()-10); 
var trans_date ;
for(var i=0,l=res.lastTenDayTranList.length-1;i<10;i++){
	if(l<0){
		trans_date = -1 ;
	}else{
		trans_date = res.lastTenDayTranList[l].trans_day.substr(res.lastTenDayTranList[l].trans_day.length-2,2);
	}
	if(date.getDate()==trans_date){
		cdata3.push(res.lastTenDayTranList[l].trans_day);
		vdata3.push(res.lastTenDayTranList[l].trans_amount);
		l-- ;
	}else{
		var m = date.getMonth()-(-1)+'' ;
		if(m.length<2){
			m='0'+m;
		}
		cdata3.push(date.getFullYear()+'-'+m+'-'+date.getDate());
		vdata3.push(0.00);
	}
	date.setDate(date.getDate()+1); 
}


 transOfDayChartOption = {
    title : {
        text: '最近十天交易数据情况',
        subtext: '数据单位(万元)',
        x:'left'
    },
    tooltip : {
        trigger: 'axis'
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : cdata3
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {   name:'交易总额(万元)',
            type:'bar',
            data:vdata3,
            itemStyle: {
                normal: {          
                    label : {
                        show : true,
                        textStyle : {
                            fontSize : '12',
                            fontFamily : '微软雅黑'
                        },  
                        formatter: "{c}万元"
                    }
                }
            }
        }]
};
	        /////////////////////////////////
	        transRinkChart.setOption(transRinkChartOption);
	        mccChart.setOption(mccChartOption);
	        transOfDayChart.setOption(transOfDayChartOption);
	    }
);







</script>

</head>
<body>
<div id="all_charts">

    <div id="fline" style="width:auto;height:460px;margin-top:20px;">
          <div id="transRinkChart" style="width:60%;height:460px;float:left;"></div>
          <div id="mccChart" style="width:40%;height:460px;float:left;"></div>
    </div>  <!-- fline end  -->
    <div id="sline" style="width:100%;height:460px;margin-top:20px;">
         <div id="transOfDayChart" style="width:1100px;height:450px;"></div>
    </div>

</div>   <!-- all_chart end  -->

</body>

</html>