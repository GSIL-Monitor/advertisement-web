<#include "../common/core.ftl" />
<#assign title="远山保险，爱自己更为家人">
<#if isHuhabao?? && isHuhabao=="true">
	<#assign title="呼哈保险，爱自己更为家人">
</#if>
<@htmlHead title="${title}" description="">
	<@jsFile file=["plugins/unslider.min.js","common/site/index.js"] />
	<@cssFile file=["web/site/index.css", "plugins/unslider.css"] />
</@htmlHead>
	<div class="header <#if isHuhabao?? && isHuhabao=='true'>huhabao-header</#if>">
		<div class="nav-area clearfix">
			<#if isHuhabao?? && isHuhabao=="true">
			<a href="/" class="logo huha"></a>
			<#else>
			<a href="/" class="logo yuanshan"></a>
			</#if>
		</div>
	</div>
	<div class="carousel-wrap <#if isHuhabao?? && isHuhabao=='true'>huhabao-carousel-wrap</#if>">
		<div class="carousel-inner" id="carouselInner">
			<ul>
				<#if isHuhabao?? && isHuhabao=="true">
				<li class="item huha"></li>
				<#else>
				<li class="item yuanshan"></li>
				</#if>
			</ul>
		</div>
		<#if isHuhabao?? && isHuhabao=="true">
		<#else>
		<div class="phone">
			<div class="name">远山保险</div>
			<div class="qr-code"><img src="${cdnUrl}/img/web/site/qr.png" alt=""></div>
			<div class="desc">
				<p>微信扫一扫上方二维码</p>
				<p>或搜索远山保</p>
				<p>yuanshanbao关注我们</p>
			</div>
		</div>
		</#if>
	</div>
	<#if isHuhabao?? && isHuhabao=='true'>
		<div class="huhabao-footer">
			<div class="huhabao-footer-info">
				<dl class="huhabao-content">
					<dd><i class="c1"></i><span>服务热线：010-84046190</span></dd>
					<dd><i class="c2"></i><span>服务邮箱：<a href="mailto:service@huhabao.com">service@huhabao.com</a></span></dd>
					<dd><i class="c3"></i><span>客服 QQ：<a href="tencent://message/?uin=2404351236&amp;Site=Sambow&amp;Menu=yes">2404351236</a></span></dd>
				</dl>
			</div>
			<div class="copyright">
				<div><@copyRight /></div>
				<div><@beianNumber /></div>
			</div>
		</div>
	<#else>
		<div class="footer">
			<div class="footer-info">
				<dl class="content">
					<dt>联系方式</dt>
					<dd><i class="c1"></i>服务热线：010-84083556</dd>
					<dd><i class="c2"></i>服务邮箱：<a href="mailto:service@yuanshanbao.com">service@yuanshanbao.com</a></dd>
					<dd><i class="c3"></i>客服 QQ：<a href="tencent://message/?uin=2013780348&amp;Site=Sambow&amp;Menu=yes">2013780348</a></dd>
				</dl>
			</div>
			<div class="copyright">
				<div><@copyRight /></div>
				<div><@beianNumber /></div>
			</div>
		</div>
	</#if>
<@htmlFoot/>