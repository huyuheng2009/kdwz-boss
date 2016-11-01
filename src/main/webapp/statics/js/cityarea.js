function initSelect(provinceId, cityId, areaId, pageCityId, pageAreaId, webPath) {
	$.ajax({
		type: 'post',
		url: webPath + "/backend/common/queryCityArea",
		data: {"parentId": provinceId},
		dataType: 'json',
		success: function(data) {
			if (data.errorCode == 0) {
				var html = [];
				for (var key in data.value) {
					if (cityId === key) {
						html.push("<option value='" + key + "' selected>" + data.value[key] + "</option>");
					} else {
						html.push("<option value='" + key + "'>" + data.value[key] + "</option>");
					}
				}
				$("#" + pageCityId).append(html.join(""));
				$.ajax({
					type: 'post',
					url: webPath + "/backend/common/queryCityArea",
					data: {"parentId": cityId},
					dataType: 'json',
					success: function(data) {
						var html = [];
						if (data.errorCode == 0) {
							for (var key in data.value) {
								if (areaId === key) {
									html.push("<option value='" + key + "' selected>" + data.value[key] + "</option>");
								} else {
									html.push("<option value='" + key + "'>" + data.value[key] + "</option>");
								}
							}
							$("#" + pageAreaId).append(html.join(""));
						}
					}
				});
			}
		}
	});
}

function selectProvince(provinceId, pageCityId, pageAreaId, webPath) {
	if(provinceId == null || provinceId == ''){
		$("#" + pageCityId).html("<option value=''></option>");
		$("#" + pageAreaId).html("<option value=''></option>");
		return;
	}
	$.ajax({
		type: 'post',
		url: webPath + "/backend/common/queryCityArea",
		data: {"parentId": provinceId},
		dataType: 'json',
		success: function(data) {
			if (data.errorCode == 0) {
				var html = [];
				$("#" + pageCityId).html("<option value=''></option>");
				$("#" + pageAreaId).html("<option value=''></option>");
				for (var key in data.value) {
					html.push("<option value='" + key + "'>" + data.value[key] + "</option>");
				}
				$("#" + pageCityId).append(html.join(""));
			} else {

			}
		}
	});
}

function selectCity(cityId,pageAreaId,webPath) {
   if(cityId == null || cityId == ''){	
		$("#" + pageAreaId).html("<option value=''></option>");
		return;
	}
	$.ajax({
		type: 'post',
		url: webPath + "/backend/common/queryCityArea",
		data: {"parentId": cityId},
		dataType: 'json',
		success: function(data) {
			if (data.errorCode == 0) {				
				var html = [];
				$("#"+pageAreaId).html("<option value=''></option>");
				for (var key in data.value) {
					html.push("<option value='" + key + "'>" + data.value[key] + "</option>");
				}
				$("#"+pageAreaId).append(html.join(""));
			}
		}
	});
}


