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
            "data": "showCount"
        }, {
            "data": "clickCount"
        }, {
            "data": "clickRate"
        }, {
            "data": "avgPrice"
        }, {
            "data": "totalAmount"
        }];
    
    var dataTable = $('#dataTable').DataTable(dataTableConfig);
    
    $('#queryButton').on('click', function(){
        var params = "";
        if (isNotEmpty($('#createTimeStart').val())) {
			params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
		}
		if (isNotEmpty($('#createTimeEnd').val())) {
			params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
		}
        var newUrl="${rc.contextPath}/admin/query.do?" + params;
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
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12 span-ajust">
				<h1>欢迎使用蚂蚁互动广告平台</h1>
			</div>
		</div>
	</div>
    <div class="container-fluid top-container">
        <div class="row-fluid">
            <div class="span4">
                <div class="widget-box">
                    <div class="widget-title">
                        <h5 class="curr">广告主</h5>
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
                        <div class="data-piece clearfix"><span class="fl">总数</span><span class="fr">${advertisement.total}</span></div>
                        <div class="data-piece clearfix"><span class="fl">投放中</span><span class="fr">${advertisement.use}</span></div>
                        <div class="data-piece clearfix"><span class="fl">已失效</span><span class="fr">${advertisement.down}</span></div>
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
                    <div class="filter-box">
						<div class="btn-group">
	            			<div class="filter-component">
								<h6>日期：</h6>
								<@timeRangeSearchBar/>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@htmlFoot />