
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryAdvertisementStatistic.do";
		dataTableConfig.columns = [
			{
		    	"data": null,
		    	 "render": function ( data, type, full, meta ) {
		            return '【'+data.advertisement.advertisementId+'】'+ data.advertisement.subTitle;
		        }
	      	},  {
		    	"data": "date"
		    },{
		    	"data": "total"
		    }, {
		    	"data": "clickCount"
		    }, {
		    	"data": "showCount"
		    }, {
		    	"data": "advertisement",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/advertisementChannel.do?advertisementId='+data.advertisementId+'"  class="btn btn-cyan" target="_blank">查看渠道统计</a>';
		        }
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			reload();
		});
		$('#pv').change(function(){
			reload();
		});
		$('#queryButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
			var pv=$('#pv').val();
			
			var params = "isPv=" + pv+"&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "companyName=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			if (isNotEmpty($('#positionName').val())) {
				params += "positionName=" +encodeURI(encodeURI($('#positionName').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/queryAdvertisementStatistic.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		function reload() {
			var date=$('#date').val();
			var pv=$('#pv').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryAdvertisementStatistic.do?advertisementId=${advertisementId?c}&statisticsDate="+encodeURI(encodeURI(date))+"&isPv="+pv;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		}
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>每日广告统计列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div class="filter-box">
							<select name="pv" id="pv">
								<option value="false" selected>uv</option>
								<option value="true">pv</option>
							</select>
							<div class="filter-component">
									<h6>日期：</h6>
									<@timeRangeSearchBar/>
							</div>
							<div class="btn btn-green" id="queryButton">确定</div>
							<div class="btn btn-white" id="queryReset">重置</div>
							<div class="btn btn-red" id="downloadButton" style = "float:right">下载</div>	  
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<td>广告名称</td>
									<td>日期</td>
									<td>总数</td>
									<td>点击量</td>
									<td>曝光量</td>
									<td>渠道统计</td>
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


