<#include "../common/core.ftl" />
<@htmlHead title="远山保险，爱自己更为家人" description="">
	<@jsFile file=["plugins/unslider.min.js","common/site/index.js"] />
	<@cssFile file=["web/site/index.css", "plugins/unslider.css"] />
</@htmlHead>
	<div class="header">
		<div class="nav-area clearfix">
			<a href="/" class="logo"></a>
		</div>
	</div>
	<div class="carousel-wrap">
		<div class="carousel-inner" id="carouselInner">
			<ul>
				<li class="item"></li>
			</ul>
		</div>
		<div class="phone">
			<div class="name">远山保险</div>
			<div class="qr-code"><img src="${cdnUrl}/img/web/site/qr.png" alt=""></div>
			<div class="desc">
				<p>微信扫一扫上方二维码</p>
				<p>或搜索远山保</p>
				<p>yuanshanbao关注我们</p>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="footer-info">
			<dl class="content">
				<dt>联系方式</dt>
				<dd><i class="c1"></i>服务热线：010-64167126</dd>
				<dd><i class="c2"></i>服务邮箱：<a href="mailto:service@heiniubao.com">service@yuanshanbao.com</a></dd>
				<dd><i class="c3"></i>客服 QQ：<a href="tencent://message/?uin=2013780348&amp;Site=Sambow&amp;Menu=yes">2013780348</a></dd>
			</dl>
		</div>
		<div class="copyright">
			<div>版权所有&copy;2017北京远山保科技有限公司</div>
			<div>京ICP备16055004号-1</div>
		</div>
	</div>
<@htmlFoot/>