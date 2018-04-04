<#include "../../common/core.ftl" />
<@htmlHead title="" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/pingan3in1.css", "web/activity/pingan3in1-web.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
<#-- 	<div class="header">
	</div> -->
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/shy/calculate-banner.png">
	</div>
	<#-- 分割 -->
	<div class="split"></div>
	<#-- 已有多少人领取 -->
	<div class="form-title-area">
		<span class="form-title-content">
			<p>参与问卷调查，即送100元滴滴红包</p>
		</span>
		<i class="form-title-left-icon"></i>
		<i class="form-title-right-icon"></i>
	</div>
	<#-- 填写赠险表单信息区域 -->
	<div class="form-area">
		<#-- 字段 -->
		<input type="hidden" name="gorderId" value="${gorderId?c}"/>
		<div class="form-item">
			<div>1. <span id="question1">下列健康险产品哪个是您比较关注的？</span></div>
			<div class="field-radio">
				<input type="radio" name="answer1" value="人身意外保障"/>人身意外保障
			</div>
			<div class="field-radio">
				<input type="radio" name="answer1" value="重大疾病保障"/>重大疾病保障
			</div>
			<div class="field-radio">
				<input type="radio" name="answer1" value="养老年金保障或者其他"/>养老年金保障或者其他
			</div>
		</div>

		<div class="form-item">
			<div>2. <span id="question2">如果您有购买健康险的需求，愿意支付月收入多少比例？</span></div>
			<div class="field-radio">
				<input type="radio" name="answer2" value="10%"/>10%
			</div>
			<div class="field-radio">
				<input type="radio" name="answer2" value="20%"/>20%
			</div>
			<div class="field-radio">
				<input type="radio" name="answer2" value="更多"/>更多
			</div>
		</div>

		<div class="form-item">
			<div>3. <span id="question3">您认为在家庭中谁最需要购买保险？</span></div>
			<div class="field-radio">
				<input type="radio" name="answer3" value="处于家里收入支柱的中青年人"/>处于家里收入支柱的中青年人
			</div>
			<div class="field-radio">
				<input type="radio" name="answer3" value="小孩"/>小孩
			</div>
			<div class="field-radio">
				<input type="radio" name="answer3" value="老人"/>老人
			</div>
		</div>
		<input type="hidden" id="gorderId" value="${gorderId?c}" />
		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button-shadow">
				<div class="submit-button" id="surveyButton">
					领取滴滴红包
				</div>
			</div>
		</div>
	</div>
	
	<div class="footer-button-placeholder"></div>
	<#-- 尾部 -->
	<div class="footer">
		
	</div>
</div>
<@htmlFoot />