<#include '../common/core.ftl'/>
<@htmlHead title="" > 
	<@cssFile file=["wap/site/index.css","wap/site/insurance.css", "plugins/unslider.css"] />
	<@jsFile file=["plugins/unslider.min.js","plugins/jquery.event.move.js", "plugins/jquery.event.swipe.js", "common/site/index.js"] />
</@htmlHead>
<@calculatePage/>
	<section class="carousel-wrap">
		<div class="carousel-inner" id="carouselInner">
			<ul>
				<li>
					<a href="/m/activity/pingan/xxjhb.html?channel=wxgzhxxjhb">
						<img src="${cdnUrl}/img/wap/site/banner.png" alt="">
					</a>
				</li>
			</ul>
		</div>
	</section>
	<section class="main-nav-wrap">
		<ul class="main-nav clearfix">
			<li>
				<a href="/m/insurance/free.html">
					<img src="${cdnUrl}/img/wap/site/main_nav_umbrella.png" alt="">
					<span>0元享保障</span>
				</a>
			</li>
			<#-- <li>
				<a href="/m/insurance/one.html">
					<img src="${cdnUrl}/img/wap/site/main_nav_coin.png" alt="">
					<span>1元起特惠</span>
				</a>
			</li> -->
			<li>
				<a href="/m/gift/list.html">
					<img src="${cdnUrl}/img/wap/site/main_nav_gift.png" alt="">
					<span>福利活动</span>
				</a>
			</li>
		</ul>
	</section>
	<section class="hot-guide-wrap">
		<div class="hot-guide-head">
			<a href="">
				<span class="title">0元享保障</span>
				<span class="arrow">&gt;</span>
			</a>
		</div>
		<ul class="hot-guide-container">
			<li>
				<a href="/m/activity/pingan/xyacx.html?channel=wxgzhyacx" class="clearfix">
					<div class="cont">
						<p class="title">平安100万出行险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/free1.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="/m/activity/pingan/xzhx.html?channel=wxgzhxzhx">
					<div class="cont">
						<p class="title">平安10000元账户保障险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/free2.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="/m/activity/pingan/yazj.html?channel=wxgzhyazj">
					<div class="cont">
						<p class="title">平安50000元自驾险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/free3.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="/m/activity/tk/wafd.html?channel=wxgzhwafd">
					<div class="cont">
						<p class="title">泰康100万航空意外险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/free4.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="/m/activity/metlife/jt.html?channel=wxgzhjt">
					<div class="cont">
						<p class="title">中美大都会100万交通意外险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/free5.png" alt="">
					</div>
				</a>
			</li>
		</ul>
	</section>
	<#-- <section class="hot-guide-wrap">
		<div class="hot-guide-head">
			<a href="">
				<span class="title">一元起特惠</span>
				<span class="arrow">&gt;</span>
			</a>
		</div>
		<ul class="hot-guide-container">
			<li>
				<a href="" class="clearfix">
					<div class="cont">
						<p class="title">平安100万出行险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/mfl01.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="">
					<div class="cont">
						<p class="title">平安10000元账户保障险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/mfl02.png" alt="">
					</div>
				</a>
			</li>
			<li>
				<a href="">
					<div class="cont">
						<p class="title">阳光10万出行险</p>
						<p class="price"><b class="icon">￥</b><b class="font">0</b>免费领</p>
					</div>
					<div class="pic">
						<img src="${cdnUrl}/img/wap/site/mfl03.png" alt="">
					</div>
				</a>
			</li>
		</ul>
	</section> -->
	<section class="blank"></section>
	<section class="tabbar">
		<a href="" class="act"><span class="icon lingbx"></span><p>首页</p></a>
		<a href="/m/mine.html" class=""><span class="icon mybd"></span><p>我</p></a>
	</section>
<@htmlFoot />
