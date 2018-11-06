<#include "../common/core.ftl" />
<#assign title="嘉日商城-优惠便捷的购物商城">
<@htmlHead title="${title}" description="">
	<meta http-equiv="keywords" content="嘉日商城,在线购物,商家,购物,电子烟">
	<meta http-equiv="description" content="让您拥有最优惠、最专业、最便捷的消费服务体验；商品特惠、天天特价、拼团让利">
	<@jsFile file=["plugins/unslider.min.js","common/site/index.js"] />
	<@cssFile file=["web/site/index.css", "plugins/unslider.css"] />
	<style>
		body{
			min-width: 1200px;
		}
		.container {
			position: relative;
			padding-bottom: 0;
		}
		.container img {
			display: block;
    		width: 100%;
		}
		.footer {
			margin: -6.5rem auto 0;
    		position: relative;
    		color: #fff;
		}
		.footer-info {
			width: 750px;
		}
	</style>
</@htmlHead>
	<div class="container">
		<img src="${cdnUrl}/img/web/site/jrsc/jrsc@2x.png" alt="">
	</div>
	<div class="footer">
		<div class="footer-info">
			<dl class="content">
				<dd>公司名称：北京嘉日盛达电子商务有限公司</dd>
				<dd>联系邮箱：<a style="color: #fff;" href="mailto:zc@xingdk.com">zc@xingdk.com</a></dd>
				<dd>联系电话：010-64010062</dd>
				<dd>地址：北京市朝阳区望京西路48号院6号楼15层1502室</dd>
				<dd>网址：51lexuewang.cn     京ICP备18029761号-1 Copyright  2017-2018 北京嘉日盛达电子商务有限公司版权所有</dd>
			</dl>
		</div>
	</div>
<@htmlFoot/>