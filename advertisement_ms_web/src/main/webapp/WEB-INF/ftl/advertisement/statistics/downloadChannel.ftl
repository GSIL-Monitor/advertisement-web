<#include "core.ftl" />
<@htmlHead title="下载报名表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<style>
	.downsa{
		display: inline-block;
		padding: 10px 30px;
		margin: 30px 20px 0 10px;
		color: #fff;
		font-size: 16px;
		background-color: #28b779;
		border: 1px solid #ddd;
		border-radius: 5px;
	}
	
	.downsa:hover {
		color: #fff;
		opacity: 0.8;
	}
	
	.back {
		background-color: #27a9e3;
	}
</style>

<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="网站内容管理" class="tip-bottom"> <i class="icon-book"></i>
        活动管理
      </a>

    </div>
    <h1>表单数据</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
     <a href="${path}" class="downsa">下载统计表</a>
     <a href="${rc.contextPath}/admin/${functionName}/channelStatistics.do" class="downsa back">返回</a>
    </div>
  </div>
</div>
<@footPart />
<@htmlFoot />