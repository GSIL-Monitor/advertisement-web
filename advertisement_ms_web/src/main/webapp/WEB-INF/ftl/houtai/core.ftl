<#include 'config.ftl'/>
<#macro cssFile file=[]>
    <#list file as x><link rel="stylesheet" href="${rc.contextPath}/css/${x}?${cdnFileVersion}"/></#list>
</#macro>
<#macro fontsomeCssFile file=[]>
    <#list file as x><link rel="stylesheet" href="${rc.contextPath}/font-awesome/css/${x}?${cdnFileVersion}"/></#list>
</#macro>

<#macro jsFile file=[]>
    <#list file as x><script src="${rc.contextPath}/js/${x}?${cdnFileVersion}"></script></#list>
</#macro>
<#macro htmlHead title="金融博物馆后台管理系统">
	<!doctype html>
	<html>
		<head>
			<meta charset="utf-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0" />
			<title>${title}</title>
			<@cssFile file=["bootstrap.min.css", "bootstrap-responsive.min.css","uniform.css","select2.css","matrix-style.css","matrix-media.css"] />
      <@fontsomeCssFile file=["font-awesome.css"] />
      <#nested>
		</head>
		<body>
</#macro>

<#macro headerPart>
	<div id="header">
	  	<h1><a href="">后台管理系统</a></h1>
	</div>
</#macro>

<#macro topHeaderMenu>
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li  class="dropdown" id="profile-messages" >
				<a title="" href="" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"> <i class="icon icon-home"></i>
					<span class="text">首页</span>
				</a>
			</li>
			<li class="">
				<a title="" href=""> <i class="icon icon-cog"></i>
					<span class="text">修改密码</span>
				</a>
			</li>
			<li class="">
				<a title="" href="login.html">
					<i class="icon icon-share-alt"></i>
					<span class="text">立即退出</span>
				</a>
			</li>
		</ul>
	</div>
</#macro>

<#macro sideBar>
<div id="sidebar"><a href="" class="visible-phone"><i class="icon icon-home"></i>主菜单</a>
  <ul>
    <li><a href=""><i class="icon icon-list-alt"></i> <span>首页</span></a> </li>
    <li class="submenu"><a href=""><i class="icon icon-hdd"></i> <span>系统管理</span></a>
      <ul>
        <li><a href="">权限管理</a></li>
        <li><a href="">模块管理</a></li>
        <li><a href="">栏目管理</a></li>
        <li><a href="">菜单管理</a></li>
        <li><a href="">分组管理</a></li>
        <li><a href="">用户管理</a></li>
        <li><a href="">图标管理</a></li>
      </ul>
    </li>
    <li class="submenu"><a href=""><i class="icon icon-wrench"></i> <span>系统监控</span></a>
      <ul>
        <li><a href="">日志监控</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href=""><i class="icon icon-th-list"></i> <span>爬虫数据管理</span></a>
      <ul>
        <li><a href="">全部爬虫数据</a></li>
        <li><a href="">全部爬虫计划</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href=""><i class="icon icon-file"></i> <span>活动管理</span></a>
      <ul>
        <li><a href="">活动列表</a></li>
        <li><a href="">账号列表</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href=""><i class="icon icon-book"></i> <span>网站内容管理</span> </a>
      <ul>
        <li><a href="">目录管理</a></li>
        <li><a href="">中国金融博物馆文章管理</a></li>
      </ul>
    </li>
  </ul>
</div>
</#macro>

<#macro footPart>
	<div class="row-fluid">
  		<div id="footer" class="span12"> 2015 &copy; <a href="">金融博物馆</a> </div>
	</div>
</#macro>
<#macro htmlFoot>
	<@jsFile file=["jquery.min.js","jquery.ui.custom.js","bootstrap.min.js","jquery.uniform.js","select2.min.js","jquery.dataTables.min.js","matrix.js","matrix.tables.js","matrix.login.js","script.js"] />
	</body>
	</html>
</#macro>