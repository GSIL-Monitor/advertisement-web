<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryCombine.do?parentId=${activityId}";
		dataTableConfig.columns = [{
	      		"data": "${functionId}"
	    	}, {
		    	"data": "activity.name"
		    }, {
		    	"data": "sort"
		    }, {
		    	"data": "activity.typeValue"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		     	"data": "activity.entranceUrl"
		    },  {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		            return '<a target="_blank" href="${rc.contextPath}/admin/activity/giftList.do?activityId='+data.activityId+'"  class="btn btn-green">活动奖品列表</a>';
		        }
		    },  {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		            return '<a target="_blank" href="${rc.contextPath}/admin/activity/listChannel.do?activityId='+data.activityId+'"  class="btn btn-green">渠道列表</a>';
		        }
		    },  {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a target="_blank" href="${rc.contextPath}/admin/activity/channel/list.do?${functionId}='+data+'"  class="btn btn-green">修改</a>';
		        }
		    }, {
		    	"data": "activityCombineId",
		        "render": function ( data, type, full, meta ) {
		            var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?activityCombineId='+data;
					return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
		        }
		  
		   
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?name="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}详情</a></div>
		<h1>${functionTitle}详情</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/addActivityWindow.do?activityId=${activityId}" target="_blank"><button class="btn btn-cyan">添加活动</button></a></span>
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div style="float:right;margin:3px 8px 10px 0">
							<input type="text" name="search" id="search"/>
							<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>活动ID</th>
									<th>活动名称</th>
									<th>组合排序</th>
									<th>活动类型</th>
									<th>添加时间</th> 
									<th>入口地址</th>
									<th>奖品</th>
									<th>渠道</th>
									<th>操作</th>									
									<th>删除</th>									
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
