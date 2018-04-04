<#include '../common/core.ftl'/>
<@htmlHead title="" > 
	<@cssFile file=["wap/site/insurance.css", "wap/activity/fail/fail.css", "wap/site/mine.css"] />
	<@jsFile file=[] />
</@htmlHead>
<@calculatePage/>
	<div class="wrapper">
		<section class="user-info">
			<#if mobile??>
			<div class="user-avatar"><img src="${cdnUrl}/img/wap/site/user_default_avatar.png" alt=""></div>
			<p class="user-tel">${mobile}</p>
			<#else>
			<a href="/m/user/login.html?returnUrl=/m/mine.html"><div class="user-avatar"><img src="${cdnUrl}/img/wap/site/user_default_avatar.png" alt=""></div></a>
			<a href="/m/user/login.html?returnUrl=/m/mine.html"><p class="user-tel">绑定手机号</p></a>
			</#if>
		</section>
		<section class="my-container">
			<div class="title">我的保障</div>
			<div class="detail clearfix">
				<a href="/m/policy/list.html" class="item left clearfix">
					<div class="icon icon-text left"></div>
					<div class="cont left">
						<div class="cell">
							<div class="name">我的保障</div>
							<#-- <div class="desc">有效：<span>2</span></div> -->
						</div>
					</div>
				</a>
				<a href="/m/policy/claim.html" class="item right clearfix">
					<div class="icon icon-note left"></div>
					<div class="cont left">
						<div class="cell">
							<div class="name">一键理赔</div>
						    <div class="desc">快速申请理赔</div>
						</div>
					</div>
				</a>
			</div>
		</section>
		<section class="my-container">
			<div class="title">福利活动</div>
			<ul class="libao-list clearfix">
				<#list advertisementList as advertisement>
				<li>
					<a href="/m/j/common.html?position=${position}&id=${advertisement.advertisementId?c}">
						<img src="${advertisement.imageUrl}" alt="">
						<div class="cont-box">
							<#if advertisement.tags??>
							<img class="hot" src="${advertisement.tags.image}" alt="">
							</#if>
							<div class="inner">
								<div class="cont-head">${advertisement.title}</div>
								<div class="cont-body">${advertisement.subTitle}</div>
							</div>
						</div>
					</a>
				</li>
				</#list>
			</ul>
		</section>
		<section class="blank"></section>
		<section class="tabbar">
			<a href="/m/index.html" class=""><span class="icon lingbx"></span><p>首页</p></a>
			<a href="" class="act"><span class="icon mybd"></span><p>我</p></a>
		</section>
	</div>
<@htmlFoot />
