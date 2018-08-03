<#include "../../common/core.ftl" />
<@htmlHead title="免费领取388元双师辅导课礼包" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/yxw.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/yxw/yxw.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<script>
	$(function() {
		var $deviceList = $('#deviceList label>div');
		for(var i = 0; i < $deviceList.length; i++) {
			(function(j) {
				$deviceList.eq(j).click(function() {
					$('#answer2').val($(this).next().attr('value'));
					$(this).find('span.device-icon').css({
						'background-image': 'url(../../../../../html/img/wap/loan/yxw/check.png)'
					})
					$(this).parent().siblings().find('div span.device-icon').css({
						'background-image': 'url(../../../../../html/img/wap/loan/yxw/uncheck.png)'
					})
				});
			})(i)
		}
	})
</script>
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/yxw/banner.png?${cdnFileVersion}">
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
								<span class="field-embeded-name">学龄</span>
								<i class="sj"></i>
								<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="请选择学生的学龄" disabled></div>
							</div>
						</div>
					</div>
					<input type="hidden" name="question1" value="学龄">
					<input type="radio" name="answer1" id="answer1">
				</div>
				<div id="agePopTipList" class="poptip">
					<div class="poptip-select-head">请选择学生的学龄<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
					<ul>
						<li><label for="option11">小学一年级</label><input type="radio" name="answer1" id="option11" value="小学一年级"></li>
						<li><label for="option12">小学二年级</label><input type="radio" name="answer1" id="option12" value="小学二年级"></li>
						<li><label for="option13">小学三年级</label><input type="radio" name="answer1" id="option13" value="小学三年级"></li>
						<li><label for="option14">小学四年级</label><input type="radio" name="answer1" id="option14" value="小学四年级"></li>
						<li><label for="option15">小学五年级</label><input type="radio" name="answer1" id="option15" value="小学五年级"></li>
						<li><label for="option16">小学六年级</label><input type="radio" name="answer1" id="option16" value="小学六年级"></li>
						<li><label for="option17">初中一年级</label><input type="radio" name="answer1" id="option17" value="初中一年级"></li>
						<li><label for="option18">初中二年级</label><input type="radio" name="answer1" id="option18" value="初中二年级"></li>
						<li><label for="option19">初中三年级</label><input type="radio" name="answer1" id="option19" value="初中三年级"></li>
					</ul>
				</div>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<div class="device-content">
					<div class="device-title">有无电脑设备及网络?</div>
					<div class="device-list clearfix" id="deviceList">
						<label class="left" for="ggjt" value="cxwy">
							<div class="name"><span class="device-icon"></span><span>有</span></div>
							<input id="ggjt" type="radio" name="answer2" value="有">
						</label>
						<label for="zj" class="right" value="cxtx">
							<div class="name"><span class="device-icon"></span><span>没有</span></div>
							<input id="zj" type="radio" name="answer2" value="没有">
						</label>
					</div>
				</div>
				<input type="hidden" name="question2" value="有无电脑设备及网络">
				<input type="radio" name="answer2" id="answer2" value="" style="display: none">
				<#-- 提交按钮 -->
				<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="desc">
		<img src="${ossUrl}/img/wap/loan/yxw/detail01.png" alt="">
		<img src="${ossUrl}/img/wap/loan/yxw/detail02.png" alt="">
		<img src="${ossUrl}/img/wap/loan/yxw/detail03.png" alt="">
	</div>
	<div class="bottom-btn" id="bottomBtn">立即免费领取</div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<#-- <@footer activityPath="${activityPath}" /> -->
		<input type="hidden" id="activityPath" value="${activityPath}"/>
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
	<p>已成功领取<span class="money">双师辅导课礼包</span>，客服后续将致电以落实课程事宜。</p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>