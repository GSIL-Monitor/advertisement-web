<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?productId=" + ${productId};
		dataTableConfig.columns = [{
	      		"data": "${functionId}"
	    	}, {
		    	"data": "productId"
		    }, {
		    	"data": "user.mobile"
		    }, {
		       "data": null,
		       "render": function ( data, type, full, meta ) {
		            return data.rateSatisfaction +'星';
		        }
		 	}, {
		 	   "data": null,
		       "render": function ( data, type, full, meta ) {
		            return data.advertisementSatisfaction+'星';
		        }
		    }, {
		       "data": null,
		       "render": function ( data, type, full, meta ) {
		            return data.processSatisfaction+'星';
		        }
		    }, {
		    	"data": "content"
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/view.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看详情</a>';
		        }
		    }, {
		    	"data": null,
	             "render": function ( data, type, full, meta ) {
	        	    if (data.status==1) {
	        		  return "已审核";
	        	    } else {
	            	  var updateUrl = '${rc.contextPath}/admin/${functionName}/update.do?status=1&${functionId}='+data.${functionId};
	            	  return '<a href="#" class="btn btn-yellow" onclick="confirmSend(\''+updateUrl+'\', \'确认审核通过吗？\');">审核数据</a>';
	                }
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
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?mobile="+encodeURI(encodeURI(searchText));
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
				<span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?productId=${productId}" target="_blank"><button class="btn btn-yellow">添加${functionTitle}</button></a></span>
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div style="float:right;margin:3px 8px 10px 0">
							<input type="text" name="search" id="search" value="手机号查询"/>
							<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>评论ID</th>
									<th>产品ID</th>
									<th>手机号</th>
									<th>费率满意度</th>
									<th>贷款满意度</th>
									<th>流程满意度</th>
									<th>内容</th>
									<th>状态</th>
									<th>详情</th>
									<th>审核</th>
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
