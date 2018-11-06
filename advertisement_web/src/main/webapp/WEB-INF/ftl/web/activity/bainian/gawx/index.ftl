<#include "../../../common/core.ftl" />
<@htmlHead title="一份保单在手，全家幸福无忧" description="">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js","plugins/bootstrap/js/bootstrap.min.js"] />
	<@cssFile file=["plugins/jquery.datetimepicker.css", "../js/plugins/bootstrap/css/bootstrap.min.css","web/activity/bainian/gawx-web.css"] />
</@htmlHead>

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<div class="banner">
				
			</div>
		</div>
	</div>
	<div class="row parent">
		<div class="col-xs-12">
			<div class="first-area" id="form-area">
				<div class="input-areas">
					<div class="input-title">免费测算一下保障终身健康需要多少钱？</div>
					<div class="form-wrap clearfix">
						<div class="form-area">
							<@embededTitleZengXianForm/>
							<input type="hidden" id="${smsId}" value="${smsToken}" />
							<div class="submit-button-area">
								<div class="submit-button" id="submitCheckBtn">
									<img src="${cdnUrl}/img/web/activity/bainian/gawx/button@2x.png" alt="">
								</div>
							</div>

							<#-- 条款 -->
							<div class="protocal">
								<label for="agreeCheck" class="agree-check-label">
									<input type="checkbox" id="agreeCheck" class="agree-check" checked="checked">
									<em class="agree-icon"></em>	
									<span>
										本人同意百年人寿后续致电联系进行保险产品介绍及精准报价，同意接受<a href="javascript:;" onclick="showProtocol();">《个人信息使用条款》</a>
									</span>
								</label>
								<@commonProtocolContent merchantName="" merchantDomain="" />
							</div>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
 	<div class="row">
 		<div class="col-xs-12">
 			<div class="second-area">
 			</div>
 		</div>
 	</div>
 	<div class="row">
 		<div class="col-xs-12">
 			<div class="three-area">
 				<a href="#form-area">
 					<img class="btn" src="${cdnUrl}/img/web/activity/bainian/gawx/button@2x.png" alt="">	
 				</a>
 			</div>
 		</div>
 	</div>
 	<div class="row">
 		<div class="col-xs-12">
 			<div class="four-area">
 				<a href="#form-area">
 					<img class="btn" src="${cdnUrl}/img/web/activity/bainian/gawx/button@2x.png" alt="">	
 				</a>
 			</div>
 		</div>
 	</div>
	<div>
	 	<div class="row">
	 		<div class="col-xs-9 col-xs-offset-2">
	 			可投保地区：
	 		</div>
	 	</div>
	 	<div class="row">
	 		<div class="col-xs-9 col-xs-offset-2">
	 			辽宁、黑龙江、河北、河南、吉林、广东、山东、浙江、内蒙古、山西、陕西、湖北、安徽、江西、四川
	 		</div>
	 	</div>
 	</div>

 	<div class="footer">
 		<div class="row">
 			<div class="footer-brand"></div>
 		</div>
 		<div class="row">
 			<div class="footer-logo"></div>
 		</div>
 		<div class="row">
 			<div class="col-xs-8 col-xs-offset-2">
 				百年人寿保险股份有限公司成立于2009年，公司注册资本77.948亿元，公司总资产达687亿元，目前全国开设20家省级分公司，累计拥有分支机构330余家，连续被评为行业最具发展潜力和最具成长性的保险公司、最佳保险企业和最佳责任典范公司；2015年、2016年、2017年连续三年荣膺“年度中国价值成长性十佳寿险公司”
 			</div>
 		</div>
 		<div class="row">
 			<div class="col-sm-offset-8 col-xs-offset-5 footer-tree"></div>
 		</div>
 	</div>
 	<input type="hidden" id="activityPath" value="${activityPath}"/>
</div>



<#-- 结果弹窗 -->
<div id="resultTip" class="tip-area result-tip hide">
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
<#-- <@resultSurveyPopWindow /> -->
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<@emailPopWindow />
</#if>
<@surveyPopWindow />
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
<@htmlFoot/>