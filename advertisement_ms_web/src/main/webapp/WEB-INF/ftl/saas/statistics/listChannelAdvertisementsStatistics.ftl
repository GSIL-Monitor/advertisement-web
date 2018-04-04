
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryChannelAdvertisements.do?channelKey=${channelkey}";
		dataTableConfig.columns = [
			{
		    	"data": null,
		    	 "render": function ( data, type, full, meta ) {
		            return '【'+data.advertisement.advertisementId+'】'+ data.advertisement.description;
		        }
	      	}, {
		    	"data": "total"
		    }, {
		    	"data": "promotionCount"
		    }, {
		    	"data": "failCount"
		    }, {
		    	"data": "invalidCount"
		    }, {
		    	"data": "edmCount"
		    }, {
		    	"data": "gameCount"
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			reload();
		});
		$('#pv').change(function(){
			reload();
		});
		function reload() {
			var date=$('#date').val();
			var pv=$('#pv').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryChannelAdvertisements.do?channelKey=${channelkey}&statisticsDate="+encodeURI(encodeURI(date))+"&isPv="+pv;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		}
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>每日${channelkey}渠道统计列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div style="float:right;margin:3px 8px 3px 0">
							<select name="pv" id="pv">
								<option value="false" selected>uv</option>
								<option value="true">pv</option>
							</select>
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
									<td>广告名称</td>
									<td>总数</td>
									<#list positionList as position>
										<td>${position}</td>
									</#list>
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
