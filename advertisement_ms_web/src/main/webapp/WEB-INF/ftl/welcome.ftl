<#include "common/core.ftl" />
<@htmlHead title="首页"/>
<#-- <@cssFile file=["page/welcome.css"] /> -->
<link rel="stylesheet" href="${cdnUrl}/css/page/welcome.css">
<style>
    .widget-lists li {
        float: left;
        min-width: 1rem;
        border-right: 1px solid #ccc;
        padding: 0 0.2rem 0;
        font-size: 20px;
        width: 2rem;
    }
    .widget-lists li:last-child {
        border: none;
    }
</style>
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
				<h1>欢迎使用蚂蚁智投广告平台</h1>
			</div>
		</div>
	</div>
    <div class="container-fluid top-container">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="top-title">
                        <h4 class="curr">广告主</h4>
                    </div>
                    <div class="widget-content item-content">
                        <div class="item-box orange-box">
                            <div class="item-icon orange-icon">
                                <img src="${cdnUrl}/img/page/welcome/balance@2x.png" alt="">     
                            </div>
                            <div class="item-text">
                                <span>余额(元)：</span>
                                <p>${balance}</p>
                            </div>
                        </div>
                        <div class="item-box blue-box">
                            <div class="item-icon blue-icon">
                                <img src="${cdnUrl}/img/page/welcome/consume@2x.png" alt="">
                            </div>
                            <div class="item-text">
                                <span>今日消耗：</span>
                                <p>${today}</p>
                            </div>
                        </div>
                        <div class="item-box green-box">
                            <div class="item-icon green-icon">
                                <img src="${cdnUrl}/img/page/welcome/consume@2x.png" alt="">
                            </div>
                            <div class="item-text">
                                <span>昨日消耗：</span>
                                <p>${yesterday}</p>
                            </div>
                        </div>
                        <div class="item-box blue-box">
                            <div class="item-icon blue-icon">
                                <img src="${cdnUrl}/img/page/welcome/consume@2x.png" alt="">
                            </div>
                            <div class="item-text">
                                <span>7日消耗：</span>
                                <p>${seven}</p>
                            </div>
                        </div>
                        <div class="item-box green-box">
                            <div class="item-icon green-icon">
                                <img src="${cdnUrl}/img/page/welcome/consume@2x.png" alt="">
                            </div>
                            <div class="item-text">
                                <span>当月消耗：</span>
                                <p>${month}</p>
                            </div>
                        </div>
                        <div class="item-box blue-box">
                            <div class="item-icon blue-icon">
                                <img src="${cdnUrl}/img/page/welcome/consume@2x.png" alt="">
                            </div>
                            <div class="item-text">
                                <span>最近30天消耗：</span>
                                <p>${thirtyDay}</p>
                            </div>
                        </div>
                       <#--  <ul class="widget-lists clearfix">
                            <li>余额(元)：<br><br><p><span style="color: #ff784f;">${balance}</span></p></li>
                            <li>今日消耗：<br><br><p><span style="color: #ff784f;">${today}</span></p></li>
                            <li>昨日消耗：<br><br><p><span style="color: #ff784f;">${yesterday}</span></p></li>
                            <li>7日消耗：<br><br><p><span style="color: #ff784f;">${seven}</span></p></li>
                            <li>当月消耗：<br><br><p><span style="color: #ff784f;">${month}</span></p></li>
                            <li>最近30天消耗：<br><br><p><span style="color: #ff784f;">${thirtyDay}</span></p></li>
                        </ul> -->
                    </div>
                </div>
            </div>
           <#--  <div class="span12">
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
            </div> -->
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="widget-box">
                <div class="widget-title">
                    <div class="widget-subtitle">数据统计 <span style="font-size: 14px;">数据统计包含代理商旗下所有广告计划</span></div>
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