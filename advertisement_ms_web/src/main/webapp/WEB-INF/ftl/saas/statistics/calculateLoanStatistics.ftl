<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/dealQuery.do";
	dataTableConfig.columns = [{
      		"data": "${functionId}"
      	}, {
      		"data": "date"	
      	}, {
	    	"data": "channel"
	    }, {
	    	"data": "uvCount"
	    }, {
	    	"data": "downloadCount"
	    }, {
	    	"data": "registerCount"
	    }, {
	    	"data": "firstLoginCount"
	    }, {
	    	"data": "applyCount"
	    }, {
	    	"data": "applyUserCount"
	    }, {
	    	"data": "applySuccessCount"
	    }, {
	    	"data": "statusValue"
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
	    	"data": null,
	        "render": function ( data, type, full, meta ) {
	        	if (data.status==1) {
	        		return "已确认";
	        	} else {
	            	var updateUrl = '${rc.contextPath}/admin/${functionName}/update.do?${functionId}='+data.${functionId};
	            	return '<a href="#" class="btn btn-yellow" onclick="confirmSend(\''+updateUrl+'\', \'确认数据无误？\');">确认数据</a>';
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
		var newUrl="${rc.contextPath}/admin/${functionName}/dealQuery.do?queryChannel="+encodeURI(encodeURI(searchText));
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
});
</script>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> <a href="#" class="current">${functionTitle}列表</a> </div>
    <h1>处理后${functionTitle}列表</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <div style="float:right;margin:3px 8px 10px 0">	            
				<input type="text" name="search" id="search" value="渠道查询"/>
				<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
			</div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>日期</th>
                  <th>渠道</th>
                  <th>UV</th>
                  <th>激活数（下载）</th>
                  <th>注册数</th>
                  <th>首次登陆数</th> 
                  <th>申请数</th>             
                  <th>申请用户数</th>  
                  <th>申请成功数</th>      
                  <th>状态</th>
                  <th>创建时间</th>
                  <th>详情</th>
                  <th>修改</th>
                  <th>确认数据</th>
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




