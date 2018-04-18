<#include "core.ftl" />
<@htmlHead title="实时${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/queryRealTimeData.do";
		dataTableConfig.columns = [{
	      	<#list fieldList as list>
	      		<#if list == 'channel'>
	      			"data": "${list}"
	      		<#else>
	    			},{
	    			"data": "${list}"
	    		</#if>
	   		 </#list>
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryChannel').change(function(){
			reload();
		});
		
		function reload() {
				var queryChannel=$('#queryChannel').val();
				var newUrl="${rc.contextPath}/admin/${functionName}/queryRealTimeData.do?queryChannel=" + queryChannel;
				dataTable.ajax.url(newUrl);
				dataTable.ajax.reload();
		}
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">实时${functionTitle}列表</a></div>
		<h1>实时${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据表格</h5>
						<div style="float:right;margin:3px 8px 10px 0">
							<select name="queryChannel" id="queryChannel">
								<option value="">默认</option>
								<#list channelList as date>
									<option value="${date.key}">${date.name}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
                  					<#list values as param>
	    								<th>${param}</th>
	   		 						</#list>
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



