
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?&userId="+$('#userId').val();
		dataTableConfig.columns = [{
	      		"data": "productId"
	    	}, {
		    	"data": "name"
		    }, {
		    	"data": "merchant.name"
		    }, {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
			        if(data.status == 0){
			        	return '待绑定';
			        }
		            return data.createTimeContent;
		        }
		    }, {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/broker/product/view.do?productId='+data.productId+'"  class="btn btn-cyan" target="_blank">查看详情</a></div>';
		        }
		    }, {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
			        if(data.status == 0){
			        	var bindUrl = '${rc.contextPath}/admin/broker/bind/confirmBind.do?productId='+data.productId+'&userId='+$('#userId').val();
			        	return '<div class="list-btn"><a href="#" class="btn btn-green" onclick="confirmBind(\''+bindUrl+'\');">确认解绑</a></div>';
			        }else{
				        var bindUrl = '${rc.contextPath}/admin/broker/bind/unbind.do?productId='+data.productId+'&userId='+$('#userId').val();
			            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmUnBind(\''+bindUrl+'\');">解绑定</a></div>';
			        }
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
				<h5>${broker.realName}${functionTitle}列表</h5>
				<input hidden id="userId" value="${userId}"/>
				</div>
				<div class="widget-content nopadding">
					<table class="table table-bordered data-table" id="dataTable">
						<thead>
							<tr>
								<th>产品ID</th>
								<th>产品名称</th>
								<th>所属机构</th>
								<th>绑定日期</th>
								<th>产品详情</th>
								<th>操作</th>
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

