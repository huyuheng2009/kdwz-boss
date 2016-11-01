function sprovince(id, name, sid, cid,cityFlag,areaFlag,selectFlag) {	
	$("#cityId").val(id);
	$("#province").val(name);	
	$("#" + sid).val(name);	
	$("#" + sid).focus();
	if(id === "1"){
		$("#" + sid).val(name+"-"+"北京市");
		$("#areaId").val(35);
		$("#cityName").val("北京市");
		
		cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
	}else if(id === "30"){
		$("#" + sid).val(name+"-"+"澳门");
		$("#areaId").val(342);
		$("#cityName").val("澳门");
		cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
	}else if(id === "2"){
		$("#" + sid).val(name+"-"+"上海市");
		$("#areaId").val(36);
		$("#cityName").val("上海市");
		cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
	}else if(id === "3"){
		$("#" + sid).val(name+"-"+"天津市");
		$("#areaId").val(37);
		$("#cityName").val("天津市");
		cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
	}else if(id === "4"){
		$("#" + sid).val(name+"-"+"重庆市");
		$("#areaId").val(38);
		$("#cityName").val("重庆市");
		cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
	}else{
	   cit.selectArea('scity', sid, selectFlag, cid, 'cityId',cityFlag, areaFlag,cityFlag);
	}
	
}



function scity(id, name, sid, cid,cityFlag,areaFlag,selectFlag) {
	$("#areaId").val(id);
	$("#cityName").val(name);
	var province = $("#province").val();
	$("#" + sid).val(province + "-" + name);		
	cit.selectArea('sarea', sid, selectFlag, cid, 'areaId',cityFlag, areaFlag,areaFlag);
}

function sarea(id, name, sid, cid,cityFlag,areaFlag,selectFlag) {	
	var province = $("#province").val() + "-" + $("#cityName").val();
	
	$("#" + sid).val(province + "-" + name);
	$("#proId").val(0);	
	$("#cityId").val('');
	$("#areaId").val('');
	$("#"+ cid).hide();	
}

function yinchan(id){
	$("#"+id).hide();
}

function selectProvince() {
   
}
selectProvince.prototype.selectArea = function(clickTag,areaTag,idDiv,cid,pcaId,cityFlag,areaFlag,obj) {	
	
	var id = $.trim($("#"+pcaId).val());

	if(id == null || id == ''){
		id=1000000;		
	}	
	$("#"+ obj).addClass("li_on");
	$("#"+obj).parent().prevAll().find("a").each(function(){
		$(this).removeClass("li_on");
	});
	$("#"+ obj).parent().nextAll().find("a").each(function(){
		$(this).removeClass("li_on");
	});	
	$.ajax({
		type: 'post',
		url: webPath+"/batch/queryByParentId",
		data:{"id":id,"clickTag":clickTag,"areaTag":areaTag,"cid":cid,"cityFlag":cityFlag,"areaFlag":areaFlag,"selectFlag":idDiv},
		dataType: 'json',
		success: function(data) {				
			$("#"+idDiv).html(data.value);
		}
	});
}
selectProvince.prototype.fouse=function(cid,tid){
	$("#"+cid).show();	
}


