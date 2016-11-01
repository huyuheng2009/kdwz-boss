var ids = [];
var values = [];

Array.prototype.indexof = function(val) {
	for (var i = 0; i < ids.length; i++) {
		if (ids[i] == val)
			return i
	}
	return -1;
};

Array.prototype.remove = function(val) {
	var index = ids.indexof(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

function switchSelect(obj) {
	if ($(obj).attr("checked")) {
		var id = $(obj).attr("id");
		var val = $(obj).val();
		if (id != "") {
			if (ids.indexof(id) <= -1) {
				ids[ids.length] = id;
				values[values.length] = val;
			}
		}
	}
	else {
		var id = $(obj).attr("id");
		var val = $(obj).val();
		ids.remove(id);
		values.remove(val);
	}
	//checkBtn();
}


function switchMuchSelect(obj, id1, id2) {
	if ($(obj).attr("checked")) {
		var id = $(obj).attr("id");		
		$("#" + id1).attr("checked", "checked");
		if (ids.indexof(id1) <= -1) {
			ids[ids.length] = id1;
		}
		if (ids.indexof(id2) <= -1) {
			ids[ids.length] = id2;
		}
		if (id != "") {
			if (ids.indexof(id) <= -1) {
				ids[ids.length] = id;				
			}
		}
	}
	else {
		var id = $(obj).attr("id");
		var val = $(obj).val();
		ids.remove(id);		
	}
}

function switchSingSelect(obj, id1, id2) {
	if ($(obj).attr("checked")) {
		var id = $(obj).attr("id");
		var val = $(obj).val();
		$("#" + id1).attr("checked", "checked");
		if (ids.indexof(id1) <= -1) {
			ids[ids.length] = id1;
		}
		if (ids.indexof(id2) <= -1) {
			ids[ids.length] = id2;
		}
		if (id != "") {
			if (ids.indexof(id) <= -1) {
				ids[ids.length] = id;
				values[values.length] = val;
			}
		}
	}
	else {
		var id = $(obj).attr("id");
		var val = $(obj).val();
		ids.remove(id);
		values.remove(val);
		var flag = false;
		$("input[name=checkBox_" + id1 + "]").each(function(index) {
			if ($(this).attr("checked")) {
				flag = true;
			}
		});
		if (flag == true) {
			$("#" + id1).attr("checked", "checked");
		} else {
			$("#" + id1).removeAttr("checked");
			ids.remove(id1);
			//ids.remove(id2);
		}
		flag = false;
		$(".ul_" + id2).find("input[type=checkbox]").each(function() {
			if ($(this).attr("checked")) {
				flag = true;
			}
		})
		if (flag == false) {
			ids.remove(id2);
		}
	}	
}

function switchTopSelect(obj, id2) {
	var id = $(obj).attr("id");
	if ($(obj).attr("checked")) {			
		if (ids.indexof(id2) <= -1) {
			
			ids[ids.length] = id2;
		}
		if (id != "") {
			if (ids.indexof(id) <= -1) {
				
				ids[ids.length] = id;
			}
		}
	}
	else {
		ids.remove(id);
		var flag = false;
		$("input[name=checkBox_" + id + "]").each(function(index) {
			ids.remove($(this).attr("id"));
			$(this).removeAttr("checked");
		});		
		$(".ul_" + id2).find("input[type=checkbox]").each(function() {
			if ($(this).attr("checked")) {
				flag = true;
			}
		})
		if (flag == false) {
			ids.remove(id2);
		}
	}	
}




//全选
function selectAll() {
	var all = jQuery("#allcheck");
	jQuery("input[name=checkBox]").each(function() {
		if (jQuery(this).attr("checked") != all.attr("checked")) {
			jQuery(this).attr("checked", all.attr("checked"));
			switchSelect(jQuery(this));
		}
	});
}
;

//function checkBtn(){
//	var selectBtn=jQuery("#selectBtn");
//	var size=jQuery(":checked[name=checkedBox]").size();
//	size==0?selectBtn.attr("disabled", true):selectBtn.attr("disabled", false);
//	
//	if(size == 0){
//		jQuery("#checkAll").attr("checked",false);
//	}
//}


