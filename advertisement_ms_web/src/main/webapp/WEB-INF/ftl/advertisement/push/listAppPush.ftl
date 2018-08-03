
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
	      		"data": "${functionId}"
	    	}, {
		    	"data": "title"
		    }, {
		    	"data": "content"
		    }, {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		        	if(data.userId == null || data.userId==""){
		        		return '全部用户';
		        	}else{	
		            	return '【'+data.userId+'】';
		            }
		        }
		    }, {
		    	"data": "url"
		    }, {
		    	"data": "isSimpleStr"
		    }, {
		    	"data": "isOfflineStr"
		    }, {
		    	"data": "clientStr"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="javascript:void(0)" onclick="sendTestAppPush('+data+')" class="btn btn-yellow">测试发送</a>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="javascript:void(0)" onclick="sendAppPush('+data+')"  class="btn btn-red">正式发送</a>';
		        }
		    },{
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
		dataTableConfig.initComplete = function(settings, json) {
			var th =  $('#dataTable').find("th")[2];
			th.setAttribute('style', 'width:20%');
	  	}
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?description="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
	
	function sendAppPush(data) {
		confirmSend('${rc.contextPath}/admin/${functionName}/push.do?${functionId}='+data, '确定发送推送吗');
	}
	
	function sendTestAppPush(data) {
		var url = '${rc.contextPath}/admin/${functionName}/testPush.do?${functionId}='+data;
		sendAjax(url, function() {
			$('#refreshPageListButton').click();
		});
	}
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?type=1" target="_blank"><button class="btn btn-yellow">添加个人${functionTitle}</button></a></span>
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?type=2" target="_blank"><button class="btn btn-yellow">添加群体${functionTitle}</button></a></span>
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
									<th>ID</th>
									<th>推送标题</th>
									<th>推送内容</th>
									<th>用户</th>
									<th>推送跳转链接</th>
									<th>推送版本</th>
									<th>是否离线</th>
									<th>设备类型</th>
									<th>创建时间</th>
									<th>测试</th>
									<th>发送</th>
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
