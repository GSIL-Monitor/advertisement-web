<#include "/common/core.ftl" />
<@htmlHead title="权限管理"/>
<@sideBar />
<div id="content">
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
        <a href="#" class="current">权限管理</a>
        <span class="add">
          <a href="${rc.contextPath}/admin/right/insertRightWindow.do" target="_blank"><button>+添加权限</button></a>
        </span>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
              <div class="btn-group">
                <select name="moduleId" id="moduleId" class="selectpicker form-control" data-live-search="true">
                  <option value="">全部</option>
                    <#list modules as module>
                      <option value="${module.id}">${module.name}</option>
                    </#list>
                </select>
                <input type="text" name="search" id="search" placeholder="搜索权限" />
              </div>
              <div class="btn btn-green" id="queryButton">确定</div>
              <div class="btn btn-white" id="queryReset">重置</div>
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>权限名</th>
                  <th>描述</th>
                  <th>所属模块</th>
                  <th>URL</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/right/adminRights.do" />
<@footPart />
<script>
  $(document).ready(function(){
    dataTableConfig.ajax = "${rc.contextPath}/admin/right/queryRights.do";
    dataTableConfig.columns = [{
            "data": "name"
        }, {
          "data": "description"
        }, {
          "data": "module_name"
        }, {
          "data": "url"
        }, {
          "data": "id",
            "render": function ( data, type, full, meta ) {
              var deleteUrl = '${rc.contextPath}/admin/right/deleteRight.do?id='+data;
                return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
            }
        }];
    
    var dataTable = $('#dataTable').DataTable(dataTableConfig);
    
    $('#queryButton').on('click', function(){
      var moduleId=$('#moduleId').val();
      var searchText=$('#search').val();
      var newUrl="${rc.contextPath}/admin/right/queryRights.do?name="+encodeURI(encodeURI(searchText));
      if (moduleId.length > 0) {
        newUrl = newUrl + "&module_id="+moduleId
      }
      dataTable.ajax.url(newUrl);
      dataTable.ajax.reload();
    });
  });
</script>
<@htmlFoot />




