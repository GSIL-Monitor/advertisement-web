<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
	      		"data": "callBackId"
	    	}, {
		    	"data": "workOrder.informationInsurance.name"
		    }, {
		    	"data": "workOrder.informationInsurance.genderValue"
		    }, {
		    	"data": "workOrder.informationInsurance.age"
		   	}, {
		   		"data": "workOrder.score"
		    }, {
		    	"data": "workOrder.callRecordCount"
		   	}, {
		    	"data": "workOrder.informationInsurance.userCompleteness"
		    }, {
		    	"data": "callBackTimeContent"
		   	}, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		        	return '<div class="list-btn"><a href="#"  class="btn btn-green">拨打</a></div>';
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
			                  	<th>回访ID</th>
			                  	<th>姓名</th>
			                  	<th>性别</th>
			                  	<th>年龄</th>
			                  	<th>订单评分</th>
			                  	<th>拨打次数</th>
			                  	<th>用户完整度</th>             
			                  	<th>回访时间</th>
			                  	<th>拨打</th>
			                 
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