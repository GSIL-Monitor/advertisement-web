<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@jsFile file=["g6.min.js", "page/structure.js"] />
<@cssFile file=["page/structure.css"] />
<@sideBar />
<div id="content">
	<@headerPart />
    <div id="content-header">
        <div id="breadcrumb">
            <a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
            <a href="#" class="current">组织架构</a>
        </div>
    </div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title clearfix"></span>
						<h5 class="fl">外呼团队组织结构图</h5>
					</div>
					<div class="widget-content nopadding">
						<div id="mountNode"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<@footPart />
<@htmlFoot />