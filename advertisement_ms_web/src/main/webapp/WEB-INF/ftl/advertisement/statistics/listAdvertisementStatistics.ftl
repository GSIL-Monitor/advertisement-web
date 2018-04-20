
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryStatisticToday.do";
		dataTableConfig.columns = [
			{
		    	"data": "title"
		    }, {
		    	"data": "companyName"
		    }, {
		    	"data": "positionName"
		    }, {
		    	"data": "date"
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
		
			var isAdvertiser=document.getElementById("isAdvertiser").checked;
			var isPosition=document.getElementById("isPosition").checked;
			var isDate=document.getElementById("isDate").checked;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "name=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/queryStatisticFromDB.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#downloadButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
		
			var isAdvertiser=document.getElementById("isAdvertiser").checked;
			var isPosition=document.getElementById("isPosition").checked;
			var isDate=document.getElementById("isDate").checked;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "name=" +encodeURI(encodeURI($('#name').val())) + "&";
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
	var checkNum = 0;
	function countChoices(obj) {
		var max = 2;
		obj.checked?checkNum++:checkNum--;
   		if(checkNum>max){
   			alert("对不起，你只能选择" + max + "个选项!");
   			checkNum--;
   			obj.checked=false;
		}
	}
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
						<div class="filter-box" style="float:right;margin:3px 8px 3px 0">
							<div class="btn-group">
		            			<div class="filter-component">
									<h6>日期：</h6>
									<@timeRangeSearchBar/>
								</div>	  
							</div>
							<div class="btn-group">
		            				  <h6>广告主：</h6><input type="checkbox" name="box1" id="isAdvertiser" onClick="countChoices(this)">
		            				  <h6>位置：</h6><input type="checkbox" name="box2" id="isPosition" onClick="countChoices(this)">
		            				  <h6>日期：</h6><input type="checkbox" name="box3" id="isDate" onClick="countChoices(this)">
							</div>
							<div class="btn btn-green" id="queryButton">确定</div>
							<div class="btn btn-white" id="queryReset">重置</div>
							<div class="btn btn-red" id="downloadButton" style="float:right">下载</div>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<th>广告名称</th>
			                  	<th>广告主名称</th>
			                  	<th>位置</th>
			                  	<th>时间</th>
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


