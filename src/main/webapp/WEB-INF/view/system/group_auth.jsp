<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

				<script type="text/javascript">
				
				$(function(){
					  $(".checked_all").change(function(){
					    var $this=$(this);
					    var checked=$this.prop("checked");
					    if(checked){
					      $(".sactor_cc",  $this.parents(".sactor_cli")).removeClass("sactor_cc_no");
					      $(".sactor_cc input",  $this.parents(".sactor_cli")).attr("disabled",false);
					    }else{
					      $(".sactor_cc",  $this.parents(".sactor_cli")).addClass("sactor_cc_no");
					      $(".sactor_cc input",  $this.parents(".sactor_cli")).attr("disabled",true);
					      $this.parents(".sactor_cli").find(".sactor_cc").find("input").prop("checked",false);
					    }
					  });
					});
				
					$("a[name='selItem']")
							.click(
									function() {
										$(this)
												.parent()
												.next("ul")
												.find("input")
												.each(
														function(i, n) {
															if (!$(n).attr(
																	"disabled")) {
																$(this)
																		.prop(
																				"checked",
																				!$(
																						this)
																						.prop(
																								"checked"));
															}

														});
										$(this).parent().find("input").prop(
												"checked",
												!$(this).parent().find("input")
														.prop("checked"));
									});

                    $("a[name='allItem']")
                            .click(
                            function() {
                                $(this)
                                        .parent()
                                        .next("ul")
                                        .find("input")
                                        .each(
                                        function(i, n) {
                                            if (!$(n).attr(
                                                    "disabled")) {
                                                $(this)
                                                        .prop(
                                                        "checked",
                                                        true);
                                            }

                                        });
                                $(this).parent().find("input").prop(
                                        "checked",true);
                            });

                    function check(){
                        var group_name = $("#group_name").val();
                        var group_desc = $('#group_desc').val();
                        if($.trim(group_name)==''){
                            alert('群组名称不能为空');
                            return false;
                        }
                        if($.trim(group_desc)==''){
                            alert('群组介绍不能为空');
                            return false;
                        }
                        var ids = '';
                    	$("input[type='checkbox']:checked").each(function(){
                        	ids += $(this).val(); 
                    	});
                    	if(ids.length<1){
                    		alert("请选择权限列表！");
                    		return false;
                    	}
                        return true;
                    }
				</script>

</head>
<body>
	<form:form id="userGroupAdd" action="groupauthsave" 	method="post" onsubmit="return check()">
	<input type="hidden" id="id" name="id" value="${params['id']}" />
<div class="content" style="margin: 0;">
  <div class="sactor">
    <div class="sactor_search">
      <div class="soso_left">
        <div class="soso_li">
          <div class="soso_b">角色名称</div>
          <div class="soso_input"><input type="text" id="group_name"
							name="group_name" value="${params['group_name']}"
							class="required" style=" width: 220px; "/></div>
        </div>
        <div class="soso_li">
          <div class="soso_b">角色介绍</div>
          <div class="soso_input"><input type="text" id="group_desc"
							name="group_desc" value="${params['group_desc']}" style=" width: 220px; "/> </div>
        </div>    
        <div class="soso_search_b">
          <div class="soso_button"><input type="submit" value="保存"/></div>
        </div>
      </div>
    </div>
    <div class="sactor_cont">
    
    	<c:forEach items="${list}" var="authItem" varStatus="status">
    
       <div class="sactor_cli">
      
        <div class="sactor_ctit">
          <div class="count_cli">
            <label><input type="checkbox" name="authGroup" <c:out value="${authItem.checked eq 'show'?'checked':'' }"/>  class="count_chinput checked_all"  value="${authItem.id}"  /> <i>${authItem.parent_name}</i></label>
          </div>
        </div>
        
        
        <div class="sactor_cc  <c:out value="${authItem.checked eq 'show'?'':'sactor_cc_no' }"/>">
          <ul>
            <c:forEach items="${authItem.nodeList}" var="nodeItem" varStatus="status">
             <li>
              <div class="count_cli">
                <label><input type="checkbox" name="authGroup" class="count_chinput"  <c:out value="${authItem.checked eq 'show'?'':'disabled' }"/> value="${nodeItem.id}" <c:out value="${nodeItem.checked eq 'show'?'checked':'' }"/> /><i>${nodeItem.parent_name}</i></label>
              </div>
              </li>
           	</c:forEach> 
          </ul>
        </div>
        
        
      </div>
</c:forEach>



    

    </div>
  </div>
</div>
		</form:form>

</body>

</html>