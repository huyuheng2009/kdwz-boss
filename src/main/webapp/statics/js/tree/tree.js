
/*
 * 曾铁树
 * 加载树结构
 */
function reloadTree(enableCheckBoxes){	
	
	rightTree=new dhtmlXTreeObject("treeBox","100%","100%",0);	
	
	rightTree.setSkin('dhx_skyblue');	
	
	rightTree.setImagePath(url + "/statics/js/dhtmltree/imgs/csh_bluebooks/");
	
	if(enableCheckBoxes!=undefined)
		rightTree.enableCheckBoxes(enableCheckBoxes);
	else
		rightTree.enableCheckBoxes(1);
	
	rightTree.enableThreeStateCheckboxes(true);
	rightTree.loadJSArray(rightArray);
	rightTree.openItem(1);
}

//角色树形结构复选框不可选
function readOnlyTree(){
    var rgElements=document.getElementById("treeBox").getElementsByTagName("img");
    var length=rgElements.length;
    for(var i = 0; i < length;i++){
        rgElements[i].style.color="gray";
        rgElements[i].onclick=null;
    }
}

/*
获取选中的节点id拼接的字符串
*/
function getSelMids() {
	var mids_checked = rightTree.getAllCheckedBranches().split(",");
	var cnt = 1;
	var midsWantStr = "";//所有选中的子节点id拼接的字符串	
	for ( var i = 0; i < mids_checked.length; i++) {
				if (cnt == 1) {
					midsWantStr = mids_checked[i];
				} else {
					midsWantStr += "," + mids_checked[i];
				}
				cnt++;
				
	}

	return midsWantStr;
}


function refresh(actionUrl,updateItemId,dg){
		var itemId=dg.opener.parent.rightTree.getSelectedItemId();	
			if(!itemId){
				if(actionUrl=='findAllForJSON'){
					itemId=0;
				}else{
					itemId = 1;
				}
			}
		var url=pathUrl + "/backend/menu/"+actionUrl+".action?itemId=" + itemId + "&current=" + Number(new Date());
		var itemOpenState=dg.opener.parent.rightTree.getOpenState(itemId);
		var childItemId=dg.opener.parent.rightTree.getSubItems(itemId);		
		var arrChildItemId;
		var childItemLength=0;
			
		if(childItemId){		
			
			if(childItemId.length > 1){
				arrChildItemId=childItemId.split(","); 
			}else{
				arrChildItemId=[childItemId]; 
			}		      	    
		    childItemLength=arrChildItemId.length;		  
		}		
		
		jQuery.ajax({
		    type : 'GET',
		    url : url,
		    async:false,
		    dataType : 'json',
		    success : function(result) {			    
		     if(result){ 	    	 
		         var rightArray=result;        
		         var length=rightArray.length;		      
		         //修改或删除
		          
		         if(updateItemId){
		             var updateState=false;                 
		        	 for(var i=0;i<length;i++){
		                 if(rightArray[i][0]==updateItemId){
		                	 updateState=true;                    	 
		                	 //如果不存在这个节点
		                	 if(dg.opener.parent.rightTree.getItemText(updateItemId)==0){
		                		 
		                		 dg.opener.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);
		                		 getChildItem(actionUrl,updateItemId,updateItemId);
		                      }
		                	 dg.opener.parent.rightTree.setItemText(updateItemId,rightArray[i][2]);                    	
		                     break;
		                 }
		             }
		            
		             if(!updateState){
		            	
		            	 dg.opener.parent.rightTree.deleteChildItems(updateItemId);
		            	 dg.opener.parent.rightTree.deleteItem(updateItemId);
		             }
		             
		          //添加      	  
		         }else{
		        	
		             if(childItemLength>0){
		            	 
		                 for(var i=0;i<length;i++){
		                     
		                     for(var j=0;j<childItemLength;j++){
		                         
		                         if(rightArray[i][0]==arrChildItemId[j]){
			                         break;
		                         }
		                         
		                         if( j == childItemLength-1 && rightArray[i][0] != arrChildItemId[j] ){        
		                        	 
		                        	 dg.opener.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);                        	 
		                             break ;
		                         }
		                     }
		                  } 
		             } else{                	
		            	 for(var i=0;i<length;i++){          
		            		
		            		 dg.opener.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);
		                  } 
		             }
		         }
		        
		     }else{
		    	 dg.opener.parent.rightTree.deleteChildItems(updateItemId);
		    	 dg.opener.parent.rightTree.deleteChildItems(itemId);
		     }
		     
		     if(itemOpenState==-1){
		    	 dg.opener.parent.rightTree.closeItem(itemId);
		     }
	     
		    },error:function(result){
		        alert(result.responseText);
		    }
		});
}



function delRefresh(actionUrl,updateItemId){
	var itemId=window.parent.rightTree.getSelectedItemId();	
		if(!itemId){
			if(actionUrl=='findAllForJSON'){
				itemId=0;
			}else{
				itemId = 1;
			}
		}
	var url=pathUrl + "/backend/menu/"+actionUrl+".action?itemId=" + itemId + "&current=" + Number(new Date());
	var itemOpenState=window.parent.rightTree.getOpenState(itemId);
	var childItemId=window.parent.rightTree.getSubItems(itemId);		
	var arrChildItemId;
	var childItemLength=0;
		
	if(childItemId){		
		
		if(childItemId.length > 1){
			arrChildItemId=childItemId.split(","); 
		}else{
			arrChildItemId=[childItemId]; 
		}		      	    
	    childItemLength=arrChildItemId.length;		  
	}		
	
	jQuery.ajax({
	    type : 'GET',
	    url : url,
	    async:false,
	    dataType : 'json',
	    success : function(result) {			    
	     if(result){ 	    	 
	         var rightArray=result;        
	         var length=rightArray.length;             
	         
	         //修改或删除
	          
	         if(updateItemId){
	             var updateState=false;                 
	        	 for(var i=0;i<length;i++){
	                 if(rightArray[i][0]==updateItemId){
	                	 updateState=true;                    	 
	                	 //如果不存在这个节点
	                	 if(window.parent.rightTree.getItemText(updateItemId)==0){
	                		 
	                		 window.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);
	                		 getChildItem1(actionUrl,updateItemId,updateItemId);
	                      }
	                	 window.parent.rightTree.setItemText(updateItemId,rightArray[i][2]);                    	
	                     break;
	                 }
	             }
	            
	             if(!updateState){
	            	
	            	 window.parent.rightTree.deleteChildItems(updateItemId);
	            	 window.parent.rightTree.deleteItem(updateItemId);
	             }
	             
	          //添加      	  
	         }else{
	        	
	             if(childItemLength>0){
	            	 
	                 for(var i=0;i<length;i++){
	                     
	                     for(var j=0;j<childItemLength;j++){
	                         
	                         if(rightArray[i][0]==arrChildItemId[j]){
		                         break;
	                         }
	                         
	                         if( j == childItemLength-1 && rightArray[i][0] != arrChildItemId[j] ){        
	                        	 
	                        	 window.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);                        	 
	                             break ;
	                         }
	                     }
	                  } 
	             } else{                	
	            	 for(var i=0;i<length;i++){          
	            		
	            		 window.parent.rightTree.insertNewChild(rightArray[i][1],rightArray[i][0],rightArray[i][2]);
	                  } 
	             }
	         }
	        
	     }else{
	    	 window.parent.rightTree.deleteChildItems(updateItemId);
	    	 window.parent.rightTree.deleteChildItems(itemId);
	     }
	     
	     if(itemOpenState==-1){
	    	 window.parent.rightTree.closeItem(itemId);
	     }
     
	    },error:function(result){
	        alert(result.responseText);
	    }
	});
}


/*
 * 添加子节点
 * @param itemId
 * @return
 */
function getChildItem(actionUrl,itemId,updateItemId){
	url=top.basePath + "/backend/menu/"+actionUrl+".action?itemId=" + itemId + "&current=" + Number(new Date());
	 jQuery.ajax({
	        type : 'GET',
	        url : url,
	        dataType : 'json',
	        success : function(result) {
	         if(result){
	        	 var childArray=result;
	        	 var childLength=childArray.length;
	        	 for(var j=0;j<childLength;j++){
	        		 dg.opener.parent.rightTree.insertNewChild(childArray[j][1],childArray[j][0],childArray[j][2]);
	        		 getChildItem(actionUrl,childArray[j][0],updateItemId);
	        	 }
	         }else{
	        	 if(updateItemId){
	        		 dg.opener.parent.rightTree.closeAllItems(updateItemId);
	        	 }
	         }
	 		}
	 });
}


function getChildItem1(actionUrl,itemId,updateItemId){
	url=top.basePath + "/backend/menu/"+actionUrl+".action?itemId=" + itemId + "&current=" + Number(new Date());
	 jQuery.ajax({
	        type : 'GET',
	        url : url,
	        dataType : 'json',
	        success : function(result) {
	         if(result){
	        	 var childArray=result;
	        	 var childLength=childArray.length;
	        	 for(var j=0;j<childLength;j++){
	        		 window.parent.rightTree.insertNewChild(childArray[j][1],childArray[j][0],childArray[j][2]);
	        		 getChildItem(actionUrl,childArray[j][0],updateItemId);
	        	 }
	         }else{
	        	 if(updateItemId){
	        		 window.parent.rightTree.closeAllItems(updateItemId);
	        	 }
	         }
	 		}
	 });
}


