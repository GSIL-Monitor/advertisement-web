<#include "common/core.ftl" />
<@htmlHead title="首页"/>
<@cssFile file=["page/welcome.css"] />
<@sideBar />
<script>
$(document).ready(function(){
    dataTableConfig.ajax = "${rc.contextPath}/admin/query.do";
    dataTableConfig.columns = [{
            "data": "date"
        }, {
            "data": "taskName"
        }, {
            "data": "informationInsurance.name"
        }, {
            "data": "informationInsurance.genderValue"
        }, {
            "data": "informationInsurance.age"
        }, {
            "data": "informationInsurance.address"
        }, {
            "data": "${functionId}",
            "render": function ( data, type, full, meta ) {
                return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/detail.do?${functionId}='+data+'"  class="btn btn-yellow">详情</a></div>';
            }
        }];
    
    var dataTable = $('#dataTable').DataTable(dataTableConfig);
    
    $('#queryButton').on('click', function(){
        var params = "";
        if (isNotEmpty($('#informationTaskId').val())) {
            params += "informationTaskId=" + $('#informationTaskId').val() + "&";
        }
        if (isNotEmpty($('#name').val())) {
            params = "name="+encodeURI(encodeURI($('#name').val())) + "&";
        }
        if (isNotEmpty($('#gender').val())) {
            params += "gender=" + $('#gender').val() + "&";
        }
        if (isNotEmpty($('#status').val())) {
            params += "status=" + $('#status').val() + "&";
        }
        if (isNotEmpty($('#stage').val())) {
            params += "stage=" + $('#stage').val() + "&";
        }
        if (isNotEmpty($('#createTimeStart').val())) {
            params += "startCreateTimeValue=" + $('#createTimeStart').val() + "&";
        }
        if (isNotEmpty($('#createTimeEnd').val())) {
            params += "endCreateTimeValue=" + $('#createTimeEnd').val() + "&";
        }
        if (isNotEmpty($('#startCallBackTime').val())) {
            params += "startCallBackTimeValue=" + $('#startCallBackTime').val() + "&";
        }
        if (isNotEmpty($('#endCallBackTime').val())) {
            params += "endCallBackTimeValue=" + $('#endCallBackTime').val() + "&";
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
            <a href="#" title="我的首页" class="tip-bottom">我的首页</a> 
        </div>
    </div>
    <#if currentStatistics??>
    <div class="container-fluid top-container">
        <div class="row-fluid">
            <div class="span4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5 class="curr">广告主概况</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <div class="data-piece clearfix"><span class="fl">总数</span><span class="fr">${advertiser.total}</span></div>
                        <div class="data-piece clearfix"><span class="fl">使用中</span><span class="fr">${advertiser.use}</span></div>
                        <div class="data-piece clearfix"><span class="fl">冻结中</span><span class="fr">${advertiser.down}</span></div>
                    </div>
                </div>
            </div>
            <div class="span4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5 class="week">广告概况</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <div class="data-piece clearfix"><span class="fl">总数</span><span class="fr">${advertisement.total}条</span></div>
                        <div class="data-piece clearfix"><span class="fl">投放中</span><span class="fr">${advertisement.use}次</span></div>
                        <div class="data-piece clearfix"><span class="fl">已失效</span><span class="fr">${advertisement.down}万</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="widget-box">
                <div class="widget-title">
                    <h5>今日新发数据</h5>
                </div>
                <div class="widget-content nopadding">
                    <table class="table table-bordered data-table" id="dataTable">
                        <thead>
                            <tr>
                                <th>日期</th>
                                <th>曝光量(次)</th>
                                <th>点击量(次)</th>
                                <th>点击率</th>
                                <th>点击均价(元)</th>
                                <th>总消耗(元)</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <#else>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12 span-ajust">
				<h1>欢迎使用${project.name}智能SaaS平台</h1>
			</div>
		</div>
	</div>
    </#if>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@htmlFoot />