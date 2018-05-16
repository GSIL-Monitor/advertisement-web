
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
function change(id,status){
		var url = "${rc.contextPath}/admin/advertisement/updateStatus.do?advertisementId="+id;
		var message = "";
		if(status=="投放中"){
			message="您确认将状态更改为未投放吗";
		}else if(status=="未投放"){
			message="您确认将状态更改为投放吗";
		}
		var r=confirm(message);
		if(r==true){
			$.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: "",
                success: function (data) {
                	alert("修改成功");
			    	reload();
				}
        	});
		}
	}
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/viewAd.do?advertiserId=${advertiserId}";
		dataTableConfig.columns = [{
	      		"data": "advertisementId"
	    	}, {
	      		"data": "title"
	    	}, {
	      		"data": "advertiser.companyName"
	    	}, {
	      		"data": "createTimeContent"
	    	}, {
		    	"data": "typeContent"	
		    }, {
		    	"data": "statusValue"
		    },  {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		        	if(data.statusValue =="投放中"){
		            	return "<a href='javascript:;' onclick=\"change('"+data.advertisementId+"','"+data.statusValue+"')\" class='btn btn-red' target='_blank'>下线</a>";
		        	}else if(data.statusValue =="未投放"){
		        		return "<a href='javascript:;' onclick=\"change('"+data.advertisementId+"','"+data.statusValue+"')\" class='btn btn-cyan' target='_blank'>投放</a>";
		        	}else if(data.statusValue =="失效"){
		        		return "<a href='javascript:;'  class='btn btn-cyan' target='_blank'>投放</a>";
		        	}
		        }
		    }, {
		    	"data": "advertisementId",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/advertisement/updateWindow.do?advertisementId='+data+'"  class="btn btn-cyan" target="_blank">广告配置</a>';
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
			var params = "";
			if(isNotEmpty($('#title').val())){
				params += "title=" + encodeURI(encodeURI($('#title').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/viewAd.do?advertiserId=${advertiserId}&" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a><a href="#" class="current">列表</a></div>
		<h1>${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>广告列表</h5>
						<div style="float:right;margin:3px 8px 10px 0">
							<input type="text" name="search" id="title" placeholder="名称查询"/>
							<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>广告ID</th>
									<th>广告名称</th>
									<th>广告主名称</th>
									<th>上线日期</th>
									<th>广告类型</th>
									<th>状态</th>
									<th>操作</th>
									<th>配置</th>
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
