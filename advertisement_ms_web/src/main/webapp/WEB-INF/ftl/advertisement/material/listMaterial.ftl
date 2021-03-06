
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	function reload() {
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	}
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTableConfig.columns = [{
			    	"data": "materialId"
			    },{
			    	"data": "name"
			    },{
			    	"data": "typeContent"
			    },{
			    	"data": "title"
			    },{
			    	"data": "description"
			    },{
			    	"data": "sizeContent"
			    },{
			    	"data": "statusValue"
			    },{
			    	"data": "remark"
			    },{
			    	"data": "imageUrl",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="'+data+'"  class="btn btn-green" target="_blank">查看</a></div>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">修改</a></div>';
			       }
		        }];
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var params = "";
			if (isNotEmpty($('#advertiserId').val())) {
				params += "advertiserId=" + encodeURI(encodeURI($('#advertiserId').val())) + "&";
			}
			if (isNotEmpty($('#type').val())) {
				params += "type=" + encodeURI(encodeURI($('#type').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
	});
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
	    <div id="breadcrumb">
	    	<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
	    	<a href="#" class="current">${functionTitle}列表</a>
	    	<span class="add">
	    		<a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}" target="_blank"><button>+添加${functionTitle}</button></a>
	    	</span>
	    </div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
        <div class="widget-box">
          	<div class="widget-title"></span>
            	<h5>${functionTitle}列表</h5>
            	<div class="filter-box">
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>素材类型：</h6>
							<select name="type" id="type" class="selectpicker form-control">
								<#list typeList as type>
									<option value="${type.key}">${type.value}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn btn-green" id="queryButton">确定</div>
					<div class="btn btn-white" id="queryReset">重置</div>
				</div>
          	</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="dataTable">
					<thead>
						<tr>
							<th>ID</th>
							<th>创意名称</th>
							<th>创意类型</th>
							<th>标题</th>
							<th>描述</th>
							<th>素材尺寸</th>
							<th>状态</th>
							<th>备注</th>
							<th>查看图片</th>
							<th>修改</th>
						</tr>
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
