<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	function reload() {
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do";
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	}
	function change(id,status){
		var url = "${rc.contextPath}/admin/${functionName}/updateStatus.do?${functionId}="+id;
		var message = "";
		if(status==1){
			message="您确认将状态更改为未投放吗";
		}else if(status==2){
			message="您确认将状态更改为投放吗";
		}
		var r=confirm(message);
		if(r==true){
			$.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: "",
                success: function (data) {
                	alert("修改成功");
			    	reload();
				}
        	});
		}
	}
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
      		"data": "${functionId}"
    	}, {
	    	"data": "activityIdValue"
	    }, {
	    	"data": "name"
	    },{
		    	"data": "statusValue"
		}, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-blue" target="_blank">修改</a>';
	        }
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?${functionId}='+data;
	            return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
	        }
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var activity=$('#activityId').val();
		var newUrl="${rc.contextPath}/admin/${functionName}/queryProductDB.do?activityId="+encodeURI(encodeURI(activity));
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
});
</script>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> <a href="#" class="current">${functionTitle}列表</a> </div>
    <h1>${functionTitle}列表</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
    <span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do" target="_blank"><button class="btn btn-yellow">添加${functionTitle}</button></a></span>
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <div class="filter-box">
							<table border="0">
								<tr>
									<th></th>
									<th></th>
									<th></th>
								</tr>
								<tr>
								 <td>
                      <div style="width:60%;">
                      <select  id ="activityId" name="activityId" class="selectpicker form-control">
                        <#list activityList as activity>
                        	<option value="${activity.activityId}">${activity.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
						</tr>
				</div>
          </div>
          <td>
					<div class="btn btn-green" id="queryButton">确定</div>
		  </td>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
              
                <tr>
                  <th>产品ID</th>
                   <th>活动产品</th>
                  <th>产品名称</th>
				  <th>操作</th>
                  <th>修改</th>
                  <th>删除</th>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />




