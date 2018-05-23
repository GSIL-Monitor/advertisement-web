
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	function reload() {
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	}
	function change(id,status){
		var url = "${rc.contextPath}/admin/${functionName}/updateStatus.do?${functionId}="+id;
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
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTableConfig.columns = [{
	      		"data": "key"
	    	}, {
	      		"data": "name"
	    	}, {
	      		"data": "typeValue"
	    	}, {
	      		"data": "createTimeContent"
	    	}, {
		    	"data": "statusValue"
		    },  {
		    	"data": "advertiserId"
		    },	{
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
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">广告配置</a>';
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
			if (isNotEmpty($('#advertiserId').val())) {
				params += "advertiserId=" + encodeURI(encodeURI($('#advertiserId').val())) + "&";
			}
			if (isNotEmpty($('#title').val())) {
				params += "title=" + encodeURI(encodeURI($('#title').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?" + params;
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
	    		<a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}" target="_blank"><button>+添加${functionTitle}</button></a>
	    	</span>
	    </div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
        <div class="widget-box">
          	<div class="widget-title"></span>
            	<h5>${functionTitle}列表</h5>
            	<div class="filter-box">
					<div class="btn-group">
            			<div class="filter-component">
							<h6>奖品名称：</h6>
							<input type="text" name="title" id="title" placeholder="输入广告名称" />
						</div>	  
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>广告主名称：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list advertiserList as advertiser>
									<option value="${advertiser.advertiserId}">${advertiser.companyName}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>奖品类型：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list typeList as type>
									<option value="${type.key}">${type.value}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>奖品状态：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list statusList as status>
									<option value="${status.key}">${status.value}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn btn-green" id="queryButton">确定</div>
					<div class="btn btn-white" id="queryReset">重置</div>
				</div>
          	</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="dataTable">
					<thead>
						<tr>
							<th>奖品ID</th>
							<th>奖品名称</th>
							<th>奖品类型</th>
							<th>上线时间</th>
							<th>状态</th>
							<th>所属广告主</th>
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
