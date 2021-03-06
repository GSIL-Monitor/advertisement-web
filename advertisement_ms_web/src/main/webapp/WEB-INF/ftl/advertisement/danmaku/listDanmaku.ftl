<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
	      		"data": "danmakuId"
	    	}, {
		    	"data": "createStaff.name"
		    }, {
		    	"data": "showStaff.name"
		    }, {
		    	"data": "content"
		    }, {
		    	"data": "sort"
		    }, {
		    	"data": "startTimeContent"
		    }, {
		    	"data": "endTimeContent"
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
	            	<h5>${functionTitle}列表</h5>
	          	</div>
		        <div class="widget-content nopadding">
		            <table class="table table-bordered data-table" id="dataTable">
		              	<thead>
			                <tr>
			                  	<th>广播消息编号</th>
			                  	<th>创建人</th>
			                  	<th>显示范围</th>
			                  	<th>内容</th>
			                  	<th>优先级</th>
			                  	<th>开始时间</th>	
			                  	<th>结束时间</th>			                  	      
			                  	<th>创建时间</th>
			                  	<th>查看</th>
			                  	<th>修改</th>
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