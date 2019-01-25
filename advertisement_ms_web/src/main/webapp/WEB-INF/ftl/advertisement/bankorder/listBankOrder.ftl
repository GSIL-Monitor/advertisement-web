
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
            "data": "bankId"
        }, {
            "data": "name"
        }, {
            "data": "mobile"
        }, {
            "data": "bankName"
        }, {
            "data": "statusValue"
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
	    		<a href="${rc.contextPath}/admin/${functionName}/insertBankOrderWindow.do?advertiserId=${advertiserId}" target="_blank"><button>添加${functionTitle}</button></a>
	    	</span>
	    	 <span class="add">
	    		<a href="${rc.contextPath}/admin/${functionName}/insertAgentBankOrderWindow.do?advertiserId=${advertiserId}" target="_blank"><button>添加(代理版)${functionTitle}</button></a>
	    	</span>
	    	 <span class="add">
	    		<a href="${rc.contextPath}/admin/${functionName}/insertActivityProductsWindow.do?advertiserId=${advertiserId}" target="_blank"><button>添加(其他活动)${functionTitle}</button></a>
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
                                <h6>银行名称：</h6>
                                <input type="text" name="title" id="title" placeholder="输入银行名称" />
                            </div>
                        </div>
                        <div class="btn-group">
                            <div style="width:60%;">
                                <h6>用户名：</h6>
                                <select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list advertiserList as advertiser>
                                    <option value="${advertiser.advertiserId}">${advertiser.companyName}</option>
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
                            <th>订单ID</th>
                            <th>办卡姓名</th>
                            <th>办卡手机号</th>
                            <th>办卡银行</th>
                            <th>状态</th>             
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
