<#include "common/core.ftl" />
<@htmlHead title="首页"/>
<@cssFile file=["page/welcome.css"] />
<@sideBar />
<script>
$(document).ready(function(){
    dataTableConfig.ajax = "${rc.contextPath}/admin/main/query.do";
    dataTableConfig.columns = [{
            "data": "workOrderId"
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
            "data": "callRecordCount"   
        }, {
            "data": "lastCallTime"
        }, {
            "data": "nextCallBackTime"
        }, {
            "data": "statusValue"
        }, {
            "data": "stageValue"
        }, {
            "data": "createTimeContent"
        }, {
            "data": "${functionId}",
            "render": function ( data, type, full, meta ) {
                return '<div class="list-btn"><a href="#"  class="btn btn-green">拨打</a></div>';
            }
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
                        <h5 class="curr">今日统计</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <div class="data-piece clearfix"><span class="fl">新发数据</span><span class="fr">${currentStatistics.newDataCount}条</span></div>
                        <div class="data-piece clearfix"><span class="fl">已拨数据</span><span class="fr">${currentStatistics.callCount}次</span></div>
                        <div class="data-piece clearfix"><span class="fl">受理保费额</span><span class="fr">${currentStatistics.insuredSum}万</span></div>
                    </div>
                </div>
            </div>
            <div class="span4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5 class="week">本周统计</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <div class="data-piece clearfix"><span class="fl">新发数据</span><span class="fr">${weekStatistics.newDataCount}条</span></div>
                        <div class="data-piece clearfix"><span class="fl">已拨数据</span><span class="fr">${weekStatistics.callCount}次</span></div>
                        <div class="data-piece clearfix"><span class="fl">受理保费额</span><span class="fr">${weekStatistics.insuredSum}万</span></div>
                    </div>
                </div>
            </div>
            <div class="span4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5 class="month">本月统计</h5>
                    </div>
                    <div class="widget-content nopadding">
                        <div class="data-piece clearfix"><span class="fl">新发数据</span><span class="fr">${monthStatistics.newDataCount}条</span></div>
                        <div class="data-piece clearfix"><span class="fl">已拨数据</span><span class="fr">${monthStatistics.callCount}次</span></div>
                        <div class="data-piece clearfix"><span class="fl">受理保费额</span><span class="fr">${monthStatistics.insuredSum}万</span></div>
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
                                <th>编号</th>
                                <th>批次</th>
                                <th>姓名</th>
                                <th>性别</th>
                                <th>年龄</th>
                                <th>所在城市</th>
                                <th>拨打次数</th>
                                <th>最新拨打时间</th>
                                <th>下次回拨时间</th>
                                <th>客户状态</th>
                                <th>客户阶段</th>
                                <th>创建时间</th>
                                <th>拨打</th>
                                <th>详情</th>
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