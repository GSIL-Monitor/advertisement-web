<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取688元英语外教课程" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/vipkid.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/vipkid/mfl.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
			<img src="${customBannerConfig}">
		<#else>
			<img src="${ossUrl}/img/wap/loan/vipkid/mfl/banner.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<div class="counter-bg">
					<div class="applied-count-area left">
						<div class="applied-count-content">
							已有
							<span class="applied-count" id="appliedCount"><b class="animated tada">${appliedCount}</b></span>
							位用户成功领取
						</div>
					</div>
					<div class="count-backwards right">
						<span class="l">剩余</span>
						<ul class="cards">
							<#list reverseCount as count>
								<li>${count}</li>
							</#list>
						</ul>
						<span class="r">份</span>
					</div>
				</div>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<div class="form-item">
					<div class="left input-area">
						<div class="left premium-input" id="ageSelectControl">
							<div class="field-input">
								<span class="field-embeded-name">孩子年龄</span>
								<i class="sj"></i>
								<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="4-12岁儿童适用" disabled></div>
							</div>
						</div>
					</div>
					<input type="hidden" name="question1" value="您孩子的年龄">
					<input type="radio" name="answer1" id="answer1">
				</div>
				<div id="agePopTipList" class="poptip">
					<div class="poptip-select-head">请选择孩子的年龄<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
					<ul>
						<li><label for="option11">4岁以下</label><input type="radio" name="answer1" id="option11" value="4岁以下"></li>
						<li><label for="option12">4岁</label><input type="radio" name="answer1" id="option12" value="4岁"></li>
						<li><label for="option13">5岁</label><input type="radio" name="answer1" id="option13" value="5岁"></li>
						<li><label for="option14">6岁</label><input type="radio" name="answer1" id="option14" value="6岁"></li>
						<li><label for="option15">7岁</label><input type="radio" name="answer1" id="option15" value="7岁"></li>
						<li><label for="option16">8岁</label><input type="radio" name="answer1" id="option16" value="8岁"></li>
						<li><label for="option17">9岁</label><input type="radio" name="answer1" id="option17" value="9岁"></li>
						<li><label for="option18">10岁</label><input type="radio" name="answer1" id="option18" value="10岁"></li>
						<li><label for="option19">11岁</label><input type="radio" name="answer1" id="option19" value="11岁"></li>
						<li><label for="option110">12岁</label><input type="radio" name="answer1" id="option110" value="12岁"></li>
						<li><label for="option111">12岁以上</label><input type="radio" name="answer1" id="option111" value="12岁以上"></li>
					</ul>
				</div>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<input type="hidden" id="shareMobile" value="${shareMobile}">
				<input type="hidden" id="shareActivityId" value="${shareActivityId}">
				<#if channel?? && channel.key != 'test1mfl'>
				<div class="protocal">
					<label for="agreeCheck" class="agree-check-label">
						<input type="checkbox" id="agreeCheck" class="agree-check">
						<em class="agree-icon"></em>	
						<span><a href="javascript:;">免费领</a>由华夏人寿提供的少儿保险定制服务</span>
					</label>
				</div>
				<input type="hidden" name="question2" value="是否免费领">
				<input type="radio" name="answer2" id="answer2" value="否" checked>
				<input type="hidden" name="question3" value="是否有问卷">
				<input type="radio" name="answer3" id="answer3" value="否" checked>
				</#if>
				<#-- 提交按钮 -->
				<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton"></div>
				</div>
			</div>
		</div>
	</div>
	<#if weChatConfig??&&switch?? && weChatConfig=='true'&&switch=='true'>
		<div class="fanxian"><span>购买课程可获得1000元助学金</span></div>
	</#if>
	<#if channel?? && channel.key=='test1mfl'>
		<div class="desc"><img src="${ossUrl}/img/wap/loan/vipkid/mfl/detail1.png" alt=""></div>
	<#else>
		<div class="desc"><img src="${ossUrl}/img/wap/loan/vipkid/mfl/detail.png" alt=""></div>
	</#if>
	<@randomSmsToken/>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}" />
	<input type="hidden" id="activityPath" value="${activityPath}"/>
	<div class="bottom-btn" id="bottomBtn">
		<img src="${ossUrl}/img/wap/loan/vipkid/mfl/bottom_btn.png" alt="">
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">vipkid</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>