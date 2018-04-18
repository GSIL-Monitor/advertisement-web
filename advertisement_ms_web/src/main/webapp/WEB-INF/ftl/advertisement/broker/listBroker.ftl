<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?departmentId=${departmentId?c}";
		dataTableConfig.columns = [{
	      		"data": "userId"
	    	}, {
		    	"data": "realName"
		    }, {
		    	"data": "mobile"
		    }, {
		    	"data": "position"
		    }, {
		    	"data": "userId",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/broker/order/list.do?userId='+data+'"  class="btn btn-blue" target="_blank">查看客户</a></div>';
		        }
		    }, {
		    	"data": "userId",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/broker/bind/list.do?userId='+data+'"  class="btn btn-cyan" target="_blank">查看产品</a></div>';
		        }
		    }, {
		    	"data": "userId",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">编辑</a></div>';
		        }
		    }, {
		    	"data": "userId",
		        "render": function ( data, type, full, meta ) {
		        	var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?userId='+data;
		            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
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
	            	<h5><#if department?? && department.name??>${department.name}</#if>${functionTitle}列表</h5>
	          	</div>
		        <div class="widget-content nopadding">
		            <table class="table table-bordered data-table" id="dataTable">
		              	<thead>
			                <tr>
			                  	<th>代理人ID</th>
			                  	<th>姓名</th>
			                  	<th>联系方式</th>
			                  	<th>职位</th>
			                  	<th>代理人客户</th>
			                  	<th>代理人产品</th>
			                  	<th>编辑</th>
			                  	<th>删除</th>
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