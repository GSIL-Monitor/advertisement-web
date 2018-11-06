<#include "../common/core.ftl" />
<@htmlHead title="红红彩" description="">
	<@jsFile file=["plugins/unslider.min.js","common/site/index.js"] />
	<@cssFile file=["web/download/index.css", "plugins/unslider.css"] />
</@htmlHead>
	<div class="header">
		<div class="nav-area clearfix">
			<a href="#" class="logo"></a>
		</div>
	</div>
	<div class="carousel-wrap">
		<div class="carousel-inner" id="carouselInner">
			<ul>
				<li class="item"></li>
			</ul>
		</div>
		<div class="phone">
			<div class="qr-code"><img src="${cdnUrl}/img/web/download/qr_code.png" alt=""></div>
			<div class="guide"></div>
			<div class="desc">
				<p>扫描二维码 立即下载</p>
			</div>
		</div>
	</div>
	<div class="footer">
		<div class="copyright">
			<div><@copyRight /></div>
			<div><@beianNumber /></div>
		</div>
	</div>
<@htmlFoot/>