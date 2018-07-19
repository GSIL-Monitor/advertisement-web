<#include "../../common/core.ftl" />
<@htmlHead title="免费领478元学习礼包" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/sayabc.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/sayabc.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="need-device" id="needDevice"></div>
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/sayabc/banner@3x.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<div class="form-item">
					<div class="left input-area">
						<div class="left premium-input" id="ageSelectControl">
							<div class="field-input">
								<span class="field-embeded-name">孩子年龄</span>
								<i class="sj"></i>
								<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="5~12岁儿童适用" disabled></div>
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
				<#-- 提交按钮 -->
				<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="desc"><img src="${cdnUrl}/img/wap/loan/sayabc/details@3x.png" alt=""></div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}" />
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
	<div class="bottom-btn" id="bottomBtn"></div>
</div>
<#-- 保险详情弹窗 -->
<div id="needDeviceTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<div class="close"></div>
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/loan/sayabc/need-device-tip@3x.png">
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