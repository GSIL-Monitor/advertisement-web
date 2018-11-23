
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/dsp/query.do";
		dataTableConfig.columns = [{
	      		"data": "advertiserId"
	    	}, {
	      		"data": "bindUserName"
	    	}, {
		    	"data": "companyName"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		    	"data": "balance"
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/dsp/rechargeWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">充值</a>';
		        }
		    }];
		dataTableConfig.initComplete = function(settings, json) {
			var th =  $('#dataTable').find("th")[2];
			th.setAttribute('style', 'width:20%');
	  	}
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?companyName="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
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
						<div class="filter-box">
							<input type="text" name="search" id="search" placeholder="公司名称"/>
							<input type="button" class="btn btn-green" value="查询" id="queryButton"/>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>广告主ID</th>
									<th>用户名</th>
									<th>广告主公司名称</th>
									<th>创建时间</th>
									<th>余额</th>
									<th>状态</th>
									<th>充值</th>
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

