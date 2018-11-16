<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/plan/queryMaterial.do?planId=${planId}&isSelect=false";
	dataTableConfig.columns = [
	    {
	    	"data": "materialId",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="unselectMaterial" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
      	},{
      		"data": "title"
    	},{
	      	"data": "description"
	    },{
	      	"data": "imageUrl",
	      	"render": function ( data, type, full, meta ) {
	        	return '<img src="'+data+'" style="max-height: 100px;"/>';
	        }
	    },{
	      	"data": "createTimeContent"
	    }];
	
	$('#unselectTable').DataTable(dataTableConfig);
	
	dataTableConfig = initConfig();
	dataTableConfig.ajax = "${rc.contextPath}/admin/plan/queryMaterial.do?planId=${planId}&isSelect=true";
	dataTableConfig.columns = [
	    {
	    	"data": "materialId",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="selectMaterial" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
      	},{
      		"data": "title"
    	},{
	      	"data": "description"
	    },{
	      	"data": "imageUrl",
	      	"render": function ( data, type, full, meta ) {
	        	return '<img src="'+data+'" style="max-height: 100px;"/>';
	        }
	    },{
	      	"data": "createTimeContent"
	    }];
	$('#selectTable').DataTable(dataTableConfig);
	
});

function addSelectRights() {
	var rightIds = "";
	$("input[name='unselectMaterial']").each(function(checkbox){
		var checked = $(this).is(':checked');
		if (checked) {
			if (rightIds != "") {
	        	rightIds = rightIds + "," + $(this).val();
	        } else {
	        	rightIds = $(this).val();
	        }
        }
	})
	sendAjax('${rc.contextPath}/admin/plan/addMaterial.do?planId=${planId}&material=' + rightIds,null,true);
}

function deleteSelectRights() {
	var rightIds = "";
	$("input[name='selectMaterial']").each(function(){
		var checked = $(this).is(':checked');
		if (checked) {
			if (rightIds != "") {
	        	rightIds = rightIds + "," + $(this).val();
	        } else {
	        	rightIds = $(this).val();
	        }
		}
	})
	sendAjax('${rc.contextPath}/admin/plan/deleteMaterial.do?planId=${planId}&material=' + rightIds,null,true);
}
function reload(){
		var noSelectUrl="${rc.contextPath}/admin/plan/queryMaterial.do?planId=${planId}&isSelect=false";
		var hasSelectUrl="${rc.contextPath}/admin/plan/queryMaterial.do?planId=${planId}&isSelect=true";
		unselectTable.ajax.url(noSelectUrl);
		selectTable.ajax.url(hasSelectUrl);
		unselectTable.ajax.reload();
		selectTable.ajax.reload();
}
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="${rc.contextPath}/admin/${functionName}/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
			<a href="#" class="current">设置创意列表</a>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
    	<span style="float:right;margin:3px 8px 10px 0"><a href="javascript:;" class="btn btn-green" onclick="addSelectRights()">添加</a></span>
        <div class="widget-box">
          <div class="widget-title">
            <h5>未选创意</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="unselectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>名称</th>
                  <th>标题</th>
                  <th>描述</th>
                  <th>图片</th>
                  <th>创建日期</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
        <span style="float:right;margin:3px 8px 10px 0"><a href="javascript:;"  class="btn btn-red" onclick="deleteSelectRights()">删除</a></span>
        <div class="widget-box">
          <div class="widget-title">
            <h5>已选创意</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="selectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>名称</th>
                  <th>标题</th>
                  <th>描述</th>
                  <th>图片</th>
                  <th>创建日期</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/plan/setMaterialWindow.do?planId=${planId}" />
<@footPart />
<@htmlFoot />