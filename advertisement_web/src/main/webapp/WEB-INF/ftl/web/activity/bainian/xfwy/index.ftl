<#include "../../../common/core.ftl" />
<@htmlHead title="一份保单在手，全家幸福无忧" description="">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js"] />
	<@cssFile file=["web/activity/bainian/xfwy-web.css", "plugins/jquery.datetimepicker.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<div id="resultTip" class="tip-area result-tip hide">
	<script type="text/javascript">
		$(function(){
			$('#resultTipButton').click(function(){
				TipWindow.hide('#resultTip');
			});
			$('#resultTipClose').click(function(){
				TipWindow.hide('#resultTip');
			});
			$('.tip-overlay').click(function() {
				if (TipWindow.isShow('#resultTip')) {
					TipWindow.hide('#resultTip');
				}
			});
		});
	</script>
	<div class="tip-window center">
		<div class="tip-content">
			<span class="tip-close-icon"><img id="resultTipClose" src="${cdnUrl}/img/wap/common/tip-close.png"/></span>
			<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
			<div class="result-tip-icon">
				<img src="${cdnUrl}/img/web/activity/bainian/xfwy/success-icon.png">
			</div>
			<div class="tip-title">
				<p>您的预约咨询已成功提交！百年人寿高级顾问会尽快通过<span class="money">95542</span>与您联系，祝您生活愉快！</p>
			</div>
			<div class="tip-button">
				<a href="javascript:;" id="resultTipButton" name="tipSingleButton" class="tip-single-button center" onclick="${buttonFunction}">
					<div name="tipSingleButton" class="tip-single-button-text">确定</div>
				</a>
			</div>
			<input type="hidden" id="resultGorderId" />
			<input type="hidden" id="resultGorderKey" />
		</div>
	</div>
</div>
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<@emailPopWindow />
</#if>
<@vertifyCodePopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-close" id="insuranceLessonClose">
			<img src="${cdnUrl}/img/web/activity/bainian/xfwy/lesson-close.png">
		</div>
	<div class="tip-window center">
		<div class="favorite-tip">
			<img src="${ossUrl}/img/web/activity/bainian/xfwy/lesson-detail.png">
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$('#insuranceLessonButton').click(function(){
			TipWindow.showTip('#insuranceDetailTip');
		});
		$('.tip-overlay').click(function() {
			if (TipWindow.isShow('#insuranceDetailTip')) {
				TipWindow.hide('#insuranceDetailTip');
			}
		});
		$('#insuranceLessonClose').click(function(){
			TipWindow.hide('#insuranceDetailTip');
		});
	});
</script>
<div class="container">
	<@commonHeader insurance="bainian" description=""/>
	<div class="wrapper">
		<div class="banner-wrap">
			<div class="banner">
				<#if customBannerConfig?? && (customBannerConfig?length > 0)>
				<img src="${customBannerConfig}">
				<#else>
				<img src="${ossUrl}/img/web/activity/bainian/xfwy/banner.jpg?${cdnFileVersion}">
				</#if>
			</div>
		</div>
		<div class="form-wrap clearfix">
			<div class="insurance-lesson" id="insuranceLessonButton">
				<img src="${cdnUrl}/img/web/activity/bainian/xfwy/lesson-icon.png">
			</div>
			<div class="survey-area left">
				<div class="form-item">
					<div class="question">1. <span id="question1">
							<@questionInput question="谁最需要购买保险？" index="1"/>
						</span></div>
					<div class="answer-list">
						<div class="field-radio">
							<input type="radio" name="answer1" value="自己" id="option11"/><label for="option11">自己<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer1" value="子女" id="option12"/><label for="option12">子女<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer1" value="父母" id="option13"/><label for="option13">父母<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer1" value="配偶" id="option14"/><label for="option14">配偶<i></i></label>
						</div>
					</div>
				</div>
				<div class="form-item four-item">
					<div class="question">2. <span id="question2">
							<@questionInput question="对哪类型保险感兴趣" index="2"/>
						</span></div>
					<div class="answer-list">
						<div class="field-radio">
							<input type="radio" name="answer2" value="重疾险" id="option21"/><label for="option21">重疾险<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer2" value="意外险" id="option22"/><label for="option22">意外险<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer2" value="医疗险" id="option23"/><label for="option23">医疗险<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer2" value="儿童险" id="option24"/><label for="option24">儿童险<i></i></label>
						</div>
					</div>
				</div>
				<div class="form-item">
					<div class="question">3. <span id="question3">
							<@questionInput question="每年购买保险的预算？" index="3"/>
						</span></div>
					<div class="answer-list">
						<div class="field-radio">
							<input type="radio" name="answer3" value="5000元以下" id="option31"/><label for="option31">5000元以下<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer3" value="5000-10000元" id="option32"/><label for="option32">5000-10000元<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer3" value="10000-20000元" id="option33"/><label for="option33">10000-20000元<i></i></label>
						</div>
						<div class="field-radio">
							<input type="radio" name="answer3" value="看方案再定" id="option34"/><label for="option34">看方案再定<i></i></label>
						</div>
					</div>
				</div>
			</div>
			<div class="form-area right">
				<@embededTitleZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取</div>
				</div>		
				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/yangguang/wnhh/checked.png"/>
						我已阅读<a href="javascript:;" onclick="showProtocol();">《服务协议》</a>，并同意百年人寿客服致电沟通保险定制服务事宜
					</span>
					<@commonProtocolContent merchantName="" merchantDomain="" />
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<input type="hidden" id="activityPath" value="${activityPath}"/>
		<span>
			<p>版权所有 © 百年人寿保险股份有限公司，未经许可不得复制、转载或摘编，违者必究!</p>
			<#-- <p>Copyright © ${englishName} All Rights Reserved</p> -->
			<p>本DMP服务活动由<@companyName/>提供，<@companyName/>为合法授权服务商</p>
			<p><@beianNumber/></p>
		</span>
	</div>
</div>
<@htmlFoot/>