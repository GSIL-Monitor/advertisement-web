<#include "../../common/core.ftl" />
<#assign title="免费领取688元英语外教课程"/>
<#if channel?? && channel.key=="qqhyvipkid">
	<#assign title="免费领取388元英语外教课程"/>
</#if>
<@htmlHead title="${title}" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/vipkid.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/vipkid.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="need-device" id="needDevice"></div>
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/vipkid/banner.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<div class="youdian"></div>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<div class="form-item">
					<div class="">
						<div class="premium-input" id="ageSelectControl">
							<div class="field-input">
								<span class="field-embeded-name">孩子年龄</span>
								<i class="sj"></i>
								<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="4-12岁儿童适用" disabled></div>
							</div>
						</div>
					</div>
					<input type="hidden" name="question1" value="您孩子的年龄">
					<input type="radio" name="answer1" id="answer1">
					<#-- 条款 -->
					<div class="protocal">
						<span>
							<img src="${cdnUrl}/img/wap/loan/vipkid/check.png"/>
							我已同意<a href="${ossUrl}/img/wap/loan/vipkid/VIPKID用户协议.pdf" target="_blank">《VIPKID用户协议》</a>及<a href="${ossUrl}/img/wap/loan/vipkid/VIPKID隐私政策.pdf" target="_blank">《隐私政策》</a>
						</span>
						<@commonProtocolContent/>
						<div id="activityRulePopWindow" class="hide">
							<@activityRule/>
						</div>
					</div>
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
				<#-- <div class="form-input-split"></div>
				<div class="form-item device">
					<div class="device-title">本课程为在线教学，您家是否有<span class="money">电脑或iPad</span>等设备？</div>
					<div class="input-area">
						<div class="input-box">
							<div class="input s-input left"><input type="radio" name="answer2" id="option21" value="是" checked><label for="option21">是</label></div>
							<div class="input s-input right"><input type="radio" name="answer2" id="option22" value="否"><label for="option22">否</label></div>
							<input type="hidden" name="question2" value="是否有视频聊天设备">
						</div>
					</div>
				</div>
				<div class="form-item device">
					<div class="device-title">您近期是否有为宝宝定制英语课程的打算？</div>
					<div class="input-area">
						<div class="input-box">
							<div class="input s-input left"><input type="radio" name="answer3" id="option31" value="是" checked><label for="option31">是</label></div>
							<div class="input s-input right"><input type="radio" name="answer3" id="option32" value="否"><label for="option32">否</label></div>
							<input type="hidden" name="question3" value="您近期是否有为宝宝定制英语课程的打算？">
						</div>
					</div>
				</div> -->
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				
				<#-- 提交按钮 -->
				<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="desc"><img src="${cdnUrl}/img/wap/loan/vipkid/detail.png" alt=""></div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}" />
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
	<div class="bottom-btn" id="bottomBtn">
		<#if channel??&&channel.key=='qqhyvipkid'>
			<img src="${ossUrl}/img/wap/loan/vipkid/bottom_btn1.png" alt="">
		<#else>
		<img src="${ossUrl}/img/wap/loan/vipkid/bottom_btn.png" alt="">
		</#if>
	</div>
</div>
<#-- 保险详情弹窗 -->
<div id="needDeviceTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<div class="close"></div>
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/loan/vipkid/need-device-tip.png">
		</div>
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
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>