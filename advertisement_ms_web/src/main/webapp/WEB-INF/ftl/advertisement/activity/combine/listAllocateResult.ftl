<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryPrizeAllocate.do?activityId=${activityId}&channel=${channel}";
		dataTableConfig.columns = [{
	      		"data": "advertisementId"
	    	}, {
		    	"data": "channel"
		    }, {
		    	"data": "activityId"
		    },{
		    	"data": "probability"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		     	"data": "statusValue"
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>广告Id</th>
									<th>渠道</th>
									<th>分配后所属活动Id</th>
									<th>概率</th>
									<th>上线时间</th>
									<th>状态</th>
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
