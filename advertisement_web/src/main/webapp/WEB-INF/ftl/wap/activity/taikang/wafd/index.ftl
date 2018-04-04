<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万元意外保障" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/taikang/wafd.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<script type="text/javascript">
	$(function() {
		$('#productRecommendTab div').each(function(){
			$(this).click(function() {
				$('#productRecommendTab div').each(function(){
					$(this).removeClass('select');
				});
				$('#productRecommentArea div').each(function(){
					$(this).addClass('hide');
				});
				$(this).addClass('select');
				$('#product'+$(this).attr('value')).removeClass('hide');
			});
		});
		$('#productRecommentArea div').each(function(){
			$(this).click(function() {
				TipWindow.showTip('#favoriteTip');
				$('.tip-overlay').click(function() {
					TipWindow.hide('#favoriteTip');
				});
			});
		});
	});
</script>
<div class="container">
	<@commonHeaderTaiKang/>
	<@commonBanner path="taikang/wafd"/>
	<div class="wrapper">
		<div class="form-description">
			<p>爱奋斗是逝去光阴最好的诠释，</p>
			<p>而健康平安恰是它最有力的庇护！ </p>
		</div>
		<div class="form-title">
			<p>完善以下信息,免费领取<span class="money">100万元</span>意外险</p>
		</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">100万元</span>交通意外险
				</div>
				<@surveyForm/>
				<div class="form-title" style="margin-top:2rem;">
					填写信息，保单生效
				</div>
				</#if>
				<#-- 字段 -->
				<@commonZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />

				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">
						免费领取
					</div>
				</div>

				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/common/check.png"/>
						我同意泰康人寿致电确认免费保障生效事宜及提供咨询解答。
					</span>
				</div>
			</div>
		</div>

		<div class="product-recommend">
			<div class="product-recommend-title">
				健康、平安、富足全方位保障
			</div>
			<div class="product-recommend-area" id="productRecommentArea">
				<div id="product1">
					<img src="${cdnUrl}/img/wap/activity/taikang/wafd/product1.png">
				</div>
				<div id="product2" class="hide">
					<img src="${cdnUrl}/img/wap/activity/taikang/wafd/product2.png">
				</div>
				<div id="product3" class="hide">
					<img src="${cdnUrl}/img/wap/activity/taikang/wafd/product3.png">
				</div>
			</div>
			<div class="product-recommend-tab clearfix" id="productRecommendTab">
				<div value="1" class="product1 select">年年有余</div>
				<div value="2" class="">平安是福</div>
				<div value="3" class="product3">健康第一</div>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footerTaiKang activityPath="${activityPath}"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<div id="identityCardTip" class="tip-area email-tip identity-card-tip hide">
	<div class="tip-window center">
		<span class="tip-close-icon"><img id="emailTipClose" src="${cdnUrl}/img/wap/activity/taikang/wafd/close.png"/></span>
		<span class="tip-title-icon"><img src="${cdnUrl}/img/wap/activity/taikang/wafd/id-tip-title.png"/></span>
		<div class="tip-content">
			<div class="tip-title">
				${title}
			</div>
			<div class="form-item">
				<div class="left field-name">
					身份证：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="用于成功投保身份验证"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			<div class="tip-button">
				<a href="javascript:;" id="identityCardTipButton" name="tipSingleButton" class="tip-single-button center">
					<div name="tipSingleButton" class="tip-single-button-text">提交</div>
				</a>
			</div>
			<div class="warm-tips">
				在核实您的身份信息后，泰康人寿将给您下发成功承保 的保单信息，请留意短信！
			</div>
		</div>
	</div>
</div>
<#-- 结果弹窗 -->
<div id="surveyTip" class="tip-area result-tip hide">
	<script type="text/javascript">
		$(function(){
			$('#resultTipClose').click(function(){
				TipWindow.hide('#surveyTip');
			});
			$('.tip-overlay').click(function() {
				if (TipWindow.isShow('#surveyTip')) {
					TipWindow.hide('#surveyTip');
				}
			});
		});
	</script>
	<div class="tip-window center">
		<span class="tip-close-icon"><img id="resultTipClose" src="${cdnUrl}/img/wap/activity/taikang/wafd/close.png"/></span>
		<div class="tip-content">
			<div class="result-tip-icon">
				<img src="${cdnUrl}/img/wap/activity/taikang/wafd/success.png"/>
				<p>提交成功</p>
			</div>
			<div class="tip-title">
				恭喜！您已成功领取泰康赠送的<span class="money">100万元</span>意外保障。客服专员将尽快与您联系确认赠险事宜，来电为<span class="money">95522</span>，敬请留意，祝您生活愉快。
			</div>
			<div class="survey-title">
				为给您提供更贴心的保障选择，泰康人寿 诚邀您完成以下调查：
			</div>
			<form action="" method="post" id="surveyTipForm" class="survey-tip-form">
				<div id="surveyForm">
					<@surveyFormTaiKang/>
				</div>
				<div class="tip-button">
					<a href="javascript:;" id="surveyButton" name="surveyButton" class="tip-single-button center">
						<div name="tipSingleButton" class="tip-single-button-text">提交</div>
					</a>
				</div>
			</form>
			<input type="hidden" id="resultGorderId" />
			<input type="hidden" id="gorderId" />
			<input type="hidden" id="resultGorderKey" />
		</div>
	</div>
</div>
<#-- 感兴趣弹窗 -->
<div id="favoriteTip" class="tip-area result-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img src="${cdnUrl}/img/wap/activity/taikang/wafd/favorite-tip.png">
		</div>
	</div>
</div>
<@htmlFoot/>