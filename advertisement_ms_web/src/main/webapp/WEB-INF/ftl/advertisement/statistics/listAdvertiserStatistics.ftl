
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryAdvertiserStatisticToday.do";
		dataTableConfig.columns = [
			{
		    	"data": "advertiserId"
		    }, {
		    	"data": "companyName"
		    }, {
		    	"data": "showCount"
		    }, {
		    	"data": "clickCount"
		    }, {
		    	"data": "clickRate"
		    }, {
		    	"data": "avgPrice"
		    }, {
		    	"data": "totalAmount"
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
		
			var isAdvertiser=false;
			var isPosition=true;
			var isDate=true;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "companyName=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/queryStatisticByAdvertiser.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#downloadButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
		
			var isAdvertiser=false;
			var isPosition=true;
			var isDate=true;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "companyName=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			var url="${rc.contextPath}/admin/${functionName}/download.do?" + params;
			 $.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: "",
                success: function (data) {
                	var newUrl = data.path;
                	if (isNotEmpty(newUrl)){
                    	window.location.href = newUrl;
                	} else {
                		alert("无数据！");
                	}
                }
            });
		});
		
		function reload() {
			var date=$('#date').val();
			var pv=$('#pv').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryAdvertisement.do?advertisementId=${advertisementId?default(1)}&statisticsDate="+encodeURI(encodeURI(date))+"&isPv="+pv;
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
							<div class="btn-group">
		            			<div class="filter-component">
									<h6>日期：</h6>
									<@timeRangeSearchBar/>
								</div>	  
							</div>
							<div class="btn-group">
		            			<div class="filter-component">
									<h6>广告主名称：</h6>
									<input type="text" name="name" id="name" placeholder="搜索广告主" />
								</div>	  
							</div>
							<div class="btn btn-green" id="queryButton">确定</div>
							<div class="btn btn-white" id="queryReset">重置</div>
							<div class="btn btn-red" id="downloadButton" style="float:right">下载</div>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<th>ID</th>
			                  	<th>客户公司名称(广告主名称)</th>
			                  	<th>曝光量(次)</th>
			                  	<th>点击量(次)</th>
			                  	<th>点击率</th>
			                  	<th>点击均价</th>
			                  	<th>总消耗(元)</th>
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


