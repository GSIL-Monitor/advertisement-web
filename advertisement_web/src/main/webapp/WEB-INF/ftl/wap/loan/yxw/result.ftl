<#include "../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/loan/yxw/result.css"] />
</@htmlHead>
<@calculatePage/>
	<div class="result-box">
		<div class="result-tip-logo"></div>
		<div class="title"><img class="check-pic" src="${cdnUrl}/img/wap/loan/yxw/check@3x.png" alt=""><span class="text-success">提交成功！</span></div>
		<div class="text">稍后会有客服与您联系，落实课程事宜，请您注意接听！</div>
		<div class="kefu"></div>
		<div class="kefu-time">工作日：24小时内与您联系</div>
		<div class="kefu-time">休息日：48小时内与您联系</div>
		<#if advertisementList?? && (advertisementList?size>0)>
		<div class="advertisementList">
			<div class="recommend"><img src="${cdnUrl}/img/wap/loan/yxw/star@3x.png" alt="">相关推荐</div>
			<div class="more"><img src="${cdnUrl}/img/wap/loan/yxw/gift@3x.png" alt="">更多</div>
		</div>
		</#if>
		
		<#list advertisementList as advertisement>
			<div class="coupon-area">
				<a href="${advertisement.link}"><img src="${advertisement.imageUrl}" alt=""></a>
			</div>
		</#list>
	</div>
<@htmlFoot/>