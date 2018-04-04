<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryChannel.do";
		dataTableConfig.columns = [{
		    	"data": "channel"
		    }, {
		    	"data": "uvCount"	
		    }, {
		    	"data": "downloadCount"
		    }, {
		    	"data": "registerCount"
		    }, {
		    	"data": "firstLoginCount"
		    }, {
		    	"data": "applyCount"	
	      	}, {
		    	"data": "applyUserCount"
		    }, {
		    	"data": "applySuccessCount"  	
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			var date=$('#date').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryChannel.do?statisticsDate="+encodeURI(encodeURI(date));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>每日渠道统计列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
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
									<td>渠道</td>
									<td>UV</td>
									<td>激活数（下载）</td>
									<td>注册数</td>
									<td>首次登陆数</td>
									<td>申请数</td>
									<td>申请人数</td>
									<td>申请成功数</td>
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


