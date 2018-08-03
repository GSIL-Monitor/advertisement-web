<#include "../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/loan/lxw/result.css"] />
</@htmlHead>
<@calculatePage/>
	<div class="result-tip-logo"></div>
	<div class="title">
		<input type="hidden" value="${age}">
		<span>恭喜您获得</span>
		<#if age == 3>
		<span class="prize">¥688元</span>
		<#elseif age == 5 || age == 8 || age == 11>
		<span class="prize">¥688元VIPKID</span>
		<#elseif age == 15>
		<span class="prize">¥388元vipjr</span>
		<#elseif age == 19>
		<span class="prize">¥388元tutorabc</span>
		</#if>
		<span>英语试听课！</span>
	</div>
	<div class="text">稍后会有客服与您联系，落实课程事宜，请您注意接听！</div>
	<div class="kefu"></div>
	<div class="kefu-time">工作日：24小时内与您联系</div>
	<div class="kefu-time">休息日：48小时内与您联系</div>
	<div class="ganxie">感谢您的支持，祝您生活愉快！</div>
	<div class="coupon-list">
		<#if age == 5 || age == 8 || age == 11>
		<div class="gap"></div>
		<div class="title"><span class="xunzhang"></span>乐学网还为您推荐以下课程，均可免费试听</div>
		<ul class="list">
			<#list quotaIdList as quota>
			<li onclick="apply(${informationId?c}, ${quota.quotaId?c}, '${channel}')"><img src="${quota.imageUrl}" alt=""></li>
			</#list>
		</ul>
		<#else>
		<div class="title"><span class="star"></span>相关推荐</div>
		<ul class="list">
			<#list advertisementList as adver>
			<li class="gap-li"><a href="${adver.link}"><img src="${adver.imageUrl}" alt=""></a></li>
			</#list>
		</ul>	
		</#if>
	</div>
	<script>
		function apply(informationId, quotaId, channel) {
			$.ajax({
		        type : 'POST',
		        url : '/m/ed/lxw/commitAgain.html',
		        data : {
		            informationId: informationId, 
		            quotaId: quotaId, 
		            channel: channel
		        },
		        success : function(data) {
		        	if(data.retCode == 200) {
		        		TipWindow.showSingle('申请成功');
		        	}
		        }
		    });
		}
	</script>
<@htmlFoot/>