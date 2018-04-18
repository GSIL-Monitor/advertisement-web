<#include "core.ftl" />
<@htmlHead title="${functionMobileTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/${functionMobileName}/query.do";
	dataTableConfig.columns = [{
      		"data": "username"
    	}, {
	    	"data": "mobile"
	    }, {
	    	"data": "userIp"
	    }, {
	    	"data": "randomCode"
	    }, {
	    	"data": "type"
	    }, {
	    	"data": "smsReceiveTimeValue"
	    }, {
	    	"data": "statusValue"
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	function url(data) {
		var url = '${rc.contextPath}/admin/${functionMobileName}/send.do?mobile=' + data.mobile;
		url += '&userIp=' + data.userIp;
		return url;
	}
	
	
	$('#countryId').change(function(){
		var countryId=$('#countryId').val();
		var newUrl="${rc.contextPath}/admin/${functionMobileName}/query.do?mobile="+ countryId;
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
	
	$('#queryButton').on('click', function(){
		var searchText=$('#search').val();
		var newUrl="${rc.contextPath}/admin/${functionMobileName}/query.do?mobile="+encodeURI(encodeURI(searchText));
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
});
</script>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="${functionMobileTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionMobileTitle}管理</a> <a href="#" class="current">${functionMobileTitle}列表</a> </div>
    <h1>${functionMobileTitle}列表</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <div style="float:right;margin:3px 8px 10px 0">
				<input type="text" name="search" value="手机号查询" id="search"/>
				<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
			</div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>username</th>
                  <th>手机号</th>  
                  <th>ip</th> 
                  <th>短信验证码</th>                                                                                                                  
                  <th>类型</th>
                  <th>创建时间</th>
                  <th>状态</th>
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
<@sendTip retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />