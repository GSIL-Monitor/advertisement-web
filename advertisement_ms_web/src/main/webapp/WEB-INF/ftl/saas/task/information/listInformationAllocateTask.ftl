<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/allocate/query.do";
		dataTableConfig.columns = [{
	      		"data": "informationTaskId"
	    	}, {
		    	"data": "informationTask.name"
		    }, {
		    	"data": "count"
		    }, {
		    	"data": null,
		    	"render": function(data, type, full, meta) {
		    		return data.allocateProgress + " / " + data.count;
		    	}
		    }, {
		    	"data": null,
		    	"render": function(data, type, full, meta) {
		    		return data.callProgress + " / " + data.count;
		    	}
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a onclick="preAllocate('+data+')"  class="btn btn-green" target="_blank">分配</a></div>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/task/record/list.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看数据</a></div>';
		        }
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?name="+encodeURI(encodeURI(searchText));
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
	    </div>
	</div>
  	<div class="container-fluid">
	    <div class="row-fluid">
	        <div class="widget-box">
	          	<div class="widget-title"></span>
	            	<h5>${functionTitle}列表</h5>
	          	</div>
		        <div class="widget-content nopadding">
		            <table class="table table-bordered data-table" id="dataTable">
		              	<thead>
			                <tr>
			                  	<th>任务编号</th>
			                  	<th>批次名称</th>
			                  	<th>数据量</th>
			                  	<th>分配进度</th>
			                  	<th>拨打进度</th>
			                  	<th>状态</th>
			                  	<th>创建时间</th>
			                  	<th>分配</th>
			                  	<th>查看数据</th>
			                </tr>
		              	</thead>
		              	<tbody></tbody>
		            </table>
		        </div>
	        </div>
	    </div>
	</div>
</div>
<@allocateInformationPopup/>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />