
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryChannel.do?hasAll=true";
		dataTableConfig.columns = [{
		    	"data": "channel"
		    }, {
		    	"data": "uvCount"	
	      	}, {
		    	"data": "submitCount"
		    }, {
		    	"data": "insuredCount"	
	      	}, {
		    	"data": "effectCount"
	    	}, {
		    	"data": "showEffectCount"
		    }, {
		    	"data": "identityCardCount"
		    }, {
		    	"data": "surveyCount"
		    }, {
		    	"data": "calculateCount"
		    }, {
		    	"data": "emailCount"
		    }, {
		    	"data": "failCount"
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			var date=$('#date').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryChannel.do?hasAll=true&statisticsDate="+encodeURI(encodeURI(date));
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
									<td>提交数</td>
									<td>赠险数</td>
									<td>有效赠险1</td>
									<td>有效赠险2</td>
									<td>身份证号</td>
									<td>问卷数</td>
									<td>测保数</td>
									<td>邮箱数</td>
									<td>承保失败数</td>
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


