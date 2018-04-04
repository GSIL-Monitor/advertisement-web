<#include "core.ftl" />
<@htmlHead />
<@headerPart />
<@topHeaderMenu />
<@sideBar />

<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="首页" class="tip-bottom"><i class="icon-home"></i> 首页</a> <a href="#" class="current">首页</a> </div>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <div class="span12 span-ajust">
          <h1>欢迎访问${systemName}</h1>
      </div>
    </div>
  </div>
</div>
<@footPart />
<@htmlFoot />