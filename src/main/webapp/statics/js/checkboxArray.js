var ids = [];
var values = [];

Array.prototype.indexof = function(val){
	for(var i = 0;i<ids.length; i++){
	   if(ids[i] == val)
		   return i
	}
	return -1;
};

Array.prototype.remove = function(val){
	   var index = ids.indexof(val);
	   if(index > -1){
		    this.splice(index, 1);
	   }
};

function switchSelect(obj){
	 if($(obj).attr("checked")){
		 var id = $(obj).attr("id");
		 var val = $(obj).val();		 
		 if(id != ""){
			  ids[ids.length]=id;
			  values[values.length]=val;
		 }
	 }
	 else{
		   var id = $(obj).attr("id");
		   var val = $(obj).val();		   
		   ids.remove(id);
		   values.remove(val);
	 }
	 //checkBtn();
}



//全选
function selectAll(){
	 var all = jQuery("#allcheck");
	 jQuery("input[name=checkBox]").each(function(){		 
		  if(jQuery(this).attr("checked") != all.attr("checked")){
			    jQuery(this).attr("checked",all.attr("checked"));
			    switchSelect(jQuery(this));
		  }
	 });
};

//function checkBtn(){
//	var selectBtn=jQuery("#selectBtn");
//	var size=jQuery(":checked[name=checkedBox]").size();
//	size==0?selectBtn.attr("disabled", true):selectBtn.attr("disabled", false);
//	
//	if(size == 0){
//		jQuery("#checkAll").attr("checked",false);
//	}
//}


