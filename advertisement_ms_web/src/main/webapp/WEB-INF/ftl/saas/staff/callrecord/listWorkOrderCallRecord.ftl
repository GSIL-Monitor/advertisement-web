<#include "../core.ftl" />
<@htmlHead title="拨打录音列表"/>
<@cssFile file=["page/list-order.css"] />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/staff/callrecord/workorder/query.do?workOrderId=${workOrderId?c}";
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.columns = [{
		    	"data": "callRecordId"
		    }, {
		    	"data": "startTimeContent"
		    }, {
		    	"data": "endTimeContent"
		    }, {
				"data": null,
				"render": function ( data, type, full, meta ) {
					return '<span style="margin-right:.2rem;">' + data.callTime + '秒</span><span class="audio-btn"></span><audio src="' + data.callRecordUrl + '">您的浏览器不支持audio，建议使用Chrome。</audio>';
				}
		    }, {
		    	"data": null,
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a href="' + data.callRecordUrl + '" class="btn btn-green">下载录音</a>';
				}
		   	}];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
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
	    	<a href="#" title="外呼中心" class="tip-bottom"><i class="icon-book"></i>外呼中心</a> 
	    	<a href="#" title="录音管理" class="tip-bottom">录音管理</a> 
	    	<a href="#" class="current">拨打录音列表</a>
	    	<span class="add">
	    		<a href="${rc.contextPath}/admin/staff/callrecord/download.do?workOrderId=${workOrderId?c}" target="_blank"><button class="btn btn-cyan">批量下载录音</button></a>
	    	</span>
	    </div>
	</div>
  	<div class="container-fluid">
	    <div class="row-fluid">
	        <div class="widget-box">
	          	<div class="widget-title"></span>
	            	<h5>拨打录音列表</h5>
	          	</div>
		        <div class="widget-content nopadding">
		            <table class="table table-bordered data-table" id="dataTable">
		              	<thead>
			                <tr>
			                  	<th>编号</th>
			                  	<th>开始时间</th>
			                  	<th>结束时间</th>
			                  	<th>播放录音</th>
			                  	<th>下载录音</th>                 			               
			                </tr>
		              	</thead>
		              	<tbody></tbody>
		            </table>
		        </div>
	        </div>
	    </div>
	</div>
</div>
<@footPart />
<@htmlFoot />