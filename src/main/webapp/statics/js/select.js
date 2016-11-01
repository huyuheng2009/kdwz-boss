//操作全部
function moveAllOption(e1, e2){ 
	  var fromObjOptions=e1.options; 
	  for(var i=0;i<fromObjOptions.length;i++){ 
		   fromObjOptions[0].selected=true; 
		   e2.appendChild(fromObjOptions[i]); 
		   i--; 
	  } 
	 document.myform.roleIds.value=getvalue(document.myform.list2); 
}


//操作单个
function moveOption(e1, e2){ 
	 var fromObjOptions=e1.options; 
	  for(var i=0;i<fromObjOptions.length;i++){ 
		   if(fromObjOptions[i].selected){ 
		    e2.appendChild(fromObjOptions[i]); 
		    i--; 
		   } 
	  } 
	 document.myform.roleIds.value=getvalue(document.myform.list2); 
} 


function getvalue(geto){ 
	var allvalue = ""; 
	for(var i=0;i<geto.options.length;i++){ 
		allvalue +=geto.options[i].value + ","; 
	} 
	if(allvalue != "" && allvalue != null){
		allvalue = allvalue.substring(0,allvalue.length-1);
	}
	return allvalue; 
} 


function changepos1111(obj,index) 
{ 
	if(index==-1){ 
			if (obj.selectedIndex>0){ 
			obj.options(obj.selectedIndex).swapNode(obj.options(obj.selectedIndex-1)) 
			} 
	} 
	else if(index==1){ 
		if (obj.selectedIndex<obj.options.length-1){ 
		    obj.options(obj.selectedIndex).swapNode(obj.options(obj.selectedIndex+1)) 
		} 
	} 
} 

function getSelectValue(){
	document.myform.roleIds.value = getValue(document.myform.list2);
}