<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
	dataTableConfig.columns = [{
      		"data": "userId"
    	}, {
	    	"data": "mobile"
	    }, {
	    	"data": "statusValue"
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a href="${rc.contextPath}/admin/apply/list.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看投递简历</a>';
	        }
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-blue" target="_blank">修改</a>';
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
    <div id="breadcrumb"> <a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> <a href="#" class="current">${functionTitle}列表</a> </div>
    <h1>${functionTitle}列表</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
    <span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/${functionName}/insertWindow.do" target="_blank"><button class="btn btn-yellow">添加${functionTitle}</button></a></span>
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <div style="float:right;margin:3px 8px 10px 0">
				<input type="text" name="search" id="search"/>
				<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
			</div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>手机号</th>
                  <th>状态</th>
                  <th>查看投递简历</th>
                  <th>修改</th>
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




