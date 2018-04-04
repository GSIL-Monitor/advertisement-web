<#include "core.ftl" />
<@htmlHead title="系统错误" >
	<@cssFile file=["wap/40x.css"] />
</@htmlHead>
<@wrapper>
	<div class="page-title-bar">
		<a href="javascript:window.history.back(-1);"><i class="icon-back"></i></a>
		<span class="page-title">系统错误</span>
		<a href="http://www.ilanchou.com/m/job/list.html"><i class="icon-home"></i></a>
	</div>
	<@container>
		<div class="pic-box">
			<div class="server-error-cont">
				<#if retDesc??>
					${retDesc}
				<#else>
					系统错误！
				</#if>
			</div>
			<img src="${cdnUrl}/img/wap/50x.png" alt="">
		</div>
	</@container>
	<@listFootNav toPC="true"/>
</@wrapper>
<@htmlFoot />