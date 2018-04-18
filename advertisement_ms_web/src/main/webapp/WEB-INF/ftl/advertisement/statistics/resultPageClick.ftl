
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryResultPageClick.do";
		dataTableConfig.columns = [{
		    	"data": "channel"
		    }, {
		    	"data": "date"	
	      	}, {
		    	"data": "successUvCount"	
	      	}, {
		    	"data": "failUvCount"	
	      	}];
		
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			var date=$('#date').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryResultPageClick.do?statisticsDate="+encodeURI(encodeURI(date));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>每日结果页面统计列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>结果页面数据表格</h5>
						<div style="float:right;margin:3px 8px 3px 0">
							<select name="date" id="date">
								<#list dateList as date>
									<option value="${date}"<#if date_index == 0>selected</#if>>${date}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<td>渠道名称</td>
									<td>时间</td>
									<td>领取成功页面UV</td>
									<td>领取失败页面UV</td>
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
