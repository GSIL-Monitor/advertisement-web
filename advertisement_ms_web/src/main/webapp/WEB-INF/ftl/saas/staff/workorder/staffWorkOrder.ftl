<#include "core.ftl" />
<@htmlHead title="我的工单"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/myQuery.do";
		dataTableConfig.columns = [{
	      	"data": "workOrderId"
    	}, {
	    	"data": "informationInsurance.name"
	    }, {
	    	"data": "informationInsurance.genderValue"
	    }, {
	    	"data": "informationInsurance.age"
	    }, {
	    	"data": "informationInsurance.userLocation"
	    }, {
	    	"data": "informationInsurance.name"	    	
	    }, {
	    	"data": "informationInsurance.userCompleteness"
	    }, {
	    	"data": "informationInsurance.updateTimeContent"
	    }, {
	    	"data": "insuranceName"
	    }, {
	    	"data": "score"
	    }, {
			"data": "workOrderId",
			"render": function ( data, type, full, meta ) {
				return '<div class="list-btn"><a href="#"  class="btn btn-green">编辑</a></div>';
			}
	    }, {
	    	"data": "workOrderId",
			"render": function ( data, type, full, meta ) {
				return '<div class="list-btn"><a href="#"  class="btn btn-blue">导出</a></div>';
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
	    	<a href="#" class="current">我的工单</a>
	    </div>
	</div>
  	<div class="container-fluid">
    	<div class="row-fluid">
        	<div class="widget-box">
          		<div class="widget-title">
          			<h5>${functionTitle}列表</h5>
	          	</div>
	            	<div class="widget-content nopadding">
		            <table class="table table-bordered data-table" id="dataTable">
		              	<thead>
		                	<tr>
			                   <th>工单ID</th>
                  			   <th>姓名</th>  
                  		   	   <th>性别</th> 
                  			   <th>年龄</th>
                 			   <th>归属地</th>    
                  			   <th>投保人</th>
                  			   <th>资料完整度</th>
                		 	   <th>最新提交时间</th>                                                                                               
                 		 	   <th>保险名称</th>
                 	 		   <th>订单评分</th>  
                 			   <th>编辑</th>  
                			   <th>导出</th>      
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