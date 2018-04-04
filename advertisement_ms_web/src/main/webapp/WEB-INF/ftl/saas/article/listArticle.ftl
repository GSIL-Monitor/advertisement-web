<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表">
	<@jsFile file=["live-tip.js"] />
	<@cssFile file=["poster-tip.css"] />
</@htmlHead>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
	dataTableConfig.columns = [{
      		"data": "${functionId}"
    	}, {
	    	"data": "title"
	    }, {
	    	"data": "buttonLabel"
	    }, {
	    	"data": "typeValue"
	    }, {
	    	"data": "buttonLink"
	    }, {
	    	"data": "linkValue"
	    }, {
	    	"data": "createTimeContent"
	    }, {
	    	"data": "categoryValue"
	    }, {
	    	"data": "statusValue"
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a target="_blank" href="${rc.contextPath}/admin/${functionName}/detail.do?${functionId}='+data+'"  class="btn btn-blue">详情</a>';
	        }
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a target="_blank" href="${rc.contextPath}/admin/article/section/list.do?${functionId}='+data+'"  class="btn btn-green">查看section</a>';
	        }
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	            return '<a target="_blank" href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-cyan">修改</a>';
	        }
	    }, {
	    	"data": "${functionId}",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?status=-1&${functionId}='+data;
	            return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">下线</a>';
	        }
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var searchText=$('#search').val();
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?title="+encodeURI(encodeURI(searchText));
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
        <span style="float:right;margin:3px 8px 10px 0"><a onclick="show()"><button class="btn btn-yellow">添加${functionTitle}</button></a></span>
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
                  <th>标题</th>
                  <th>按钮文字</th>              
                  <th>按钮样式</th>
                  <th>按钮链接</th> 
                  <th>网址标识</th>
                  <th>创建时间</th>
                  <th>文章类型</th>  
                  <th>状态</th>
                  <th>详情</th>
                  <th>查看section</th>
                  <th>修改</th>
                  <th>下线</th>
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
  
  <div id="poster-css"></div>
	<div class="tip" id="pop-tip">
		<div>
			<a id="bod">请选择版本 :</a>
		</div>
		<div id="selects">
			 <select onChange="getValue(this)" name="select">
			 	<option value="${rc.contextPath}/admin/article/list.do" selected>请选择版本</option> 
            	<option value="${rc.contextPath}/admin/article/simpleArticle.do">简单版</option>
            	<option value="${rc.contextPath}/admin/article/insertWindow.do">复杂版</option>	
             </select>
		</div>
		<form id="memOverlayForm">	
			
			<div class="tip-pop">
				<input type="button" value="关 闭" id="close-mem"  class="btn-hover">
		 	</div>
		</form>
	</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<script>
	function getValue(httpUrl) {
		window.open(httpUrl.options[httpUrl.selectedIndex].value);
	}
</script>
<@htmlFoot />