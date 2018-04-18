<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.columns = [{
		    	"data": "date"
		    }, {
		    	"data": "name"
		    }, {
		    	"data": "onlineStatusContent"
		    }, {
		    	"data": "workingDays"
		    }, {
		    	"data": "newDataCount"
		    }, {
		    	"data": "callCount"
		   	}, {
		    	"data": "totalCallTimeContent"
		    }, {
		    	"data": "insuredSum"
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			var date=$('#date').val();
			var type=$('#type').val();
			if (type ==1) {
				$(date).datetimepicker({
					format:"Y-m-d"
				});
			} else {
				$(date).datetimepicker({
					format:"Y-m"
				});
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?statisticsDate="+encodeURI(encodeURI(date));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#type').change(function(){
			var type=$('#type').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type="+type;
			if (type ==1) {
				$(date).datetimepicker({
					format:"Y-m-d"
				});
			} else {
				$(date).datetimepicker({
					format:"Y-m"
				});
			}
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#queryButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?isTimeRegion=true&queryStartTime="+queryStartTime + "&queryEndTime="+queryEndTime;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#downloadButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
			var url="${rc.contextPath}/admin/${functionName}/download.do?isTimeRegion=true&queryStartTime="+queryStartTime + "&queryEndTime="+queryEndTime;
		
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
	});
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
	    <div id="breadcrumb">
	    	<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
	    	<a href="#" class="current">${functionTitle}列表</a>
	    	<span class="add">
	    		<a href="${rc.contextPath}/admin/${functionName}/insertWindow.do" target="_blank"><button>+添加${functionTitle}</button></a>
	    	</span>
	    </div>
	</div>
  	<div class="container-fluid">
	    <div class="row-fluid">
	        <div class="widget-box">
	          	<div class="widget-title"></span>
	            	<h5>${functionTitle}列表</h5>
	            	<div class="filter-box">
						<div class="btn-group">
	            			<div class="filter-component">
								<h6>日期：</h6>
								<@timeRangeSearchBar/>
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
			                <tr>
			                  	<th>日期</th>
			                  	<th>姓名</th>
			                  	<th>状态</th>
			                  	<th>累计工作日</th>
			                  	<th>新发数据量</th>
			                  	<th>拨打次数</th>
			                  	<th>通话时长</th>
			                  	<th>受理保额</th>			                 			               
			                </tr>
		              	</thead>
		              	<tbody></tbody>
		            </table>
		        </div>
	        </div>
	    </div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />

<@htmlFoot />