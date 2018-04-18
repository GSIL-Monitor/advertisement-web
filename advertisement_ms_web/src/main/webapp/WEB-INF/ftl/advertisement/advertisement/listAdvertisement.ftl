
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTableConfig.columns = [{
	      		"data": "advertiserId"
	    	},{
	      		"data": "advertiser.description"
	    	},{
	      		"data": "${functionId}"
	    	},{
	      		"data": "typeContent"
	    	},{
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		            return '【'+data.description+'】'+data.advertiser.description;
		        }
		    },  {
		    	"data": "title"
		    },{
		    	"data": "url"	
		    },{
		    	"data": "appUrl"	
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/view.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看详情</a>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-blue" target="_blank">修改</a>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		        	var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?${functionId}='+data;
		            return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
		        }
		    }];
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?title="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=1" target="_blank"><button class="btn btn-cyan">添加福利${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=2" target="_blank"><button class="btn btn-green">添加轮播图${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=3" target="_blank"><button class="btn btn-yellow">添加标签${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=4" target="_blank"><button class="btn btn-red">添加弹窗${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=5" target="_blank"><button class="btn btn-cyan">添加角标${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}&type=6" target="_blank"><button class="btn btn-green">添加产品${functionTitle}</button></a></span>
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div style="float:right;margin:3px 8px 10px 0">
							<input type="text" name="search" id="search" placeholder="名称查询"/>
							<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>广告主ID</th>
									<th>广告主描述</th>
									<th>广告ID</th>
									<th>广告类型</th>
									<th>广告描述</th>
									<th>文案</th>
									<th>链接</th>
									<th>APP链接</th>
									<th>状态</th>
									<th>创建时间</th>
									<th>详情</th>
									<th>修改</th>
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
