<#include "../../../common/core.ftl" />
<@htmlHead title="免费领10万阳光出行险" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/yangguang/ktcx${template!''}-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>信息已成功提交，<span class="money">阳光客服</span>后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<div class="container">
	<@commonHeaderYangGuang/>
	<div class="banner">
		<img src="${cdnUrl}/img/web/activity/yangguang/ktcx/banner${template!''}.png">
	</div>

	<div class="wrapper">
		<div class="form-wrap clearfix">
			<div class="box-left left">
				<div class="title-l"><img src="${cdnUrl}/img/web/activity/yangguang/ktcx/title_left.png" alt=""></div>
				<h4 class="question">1.<span id="question1">请问您为自己买过哪些商业保险？</span></h4>
				<ul class="options">
					<div class="row clearfix">
						<li><input type="radio" name="answer1" value="意外险" id="option11"/><label for="option11">A.意外险</label></li>
						<li><input type="radio" name="answer1" value="重疾险" id="option12"/><label for="option12">B.重疾险</label></li>
					</div>
					<div class="clearfix">
						<li><input type="radio" name="answer1" value="年金险" id="option13"/><label for="option13">C.年金险</label></li>
						<li><input type="radio" name="answer1" value="未购买过" id="option14"/><label for="option14">D.未购买过</label></li>
					</div>
				</ul>
				<h4 class="question">2.<span id="question2">请问您现阶段最关心的是生活中哪部分的保障？</span></h4>
				<ul class="options clearfix">
					<li><input type="radio" name="answer2" value="意外保障" id="option21"/><label for="option21">A.意外保障</label></li>
					<li><input type="radio" name="answer2" value="健康保障" id="option22"/><label for="option22">B.健康保障</label></li>
					<li><input type="radio" name="answer2" value="养老保障" id="option23"/><label for="option23">C.养老保障</label></li>
				</ul>
				<h4 class="question">3.<span id="question3">您可以接受每月花多少钱来获得上述保障？</span></h4>
				<ul class="options clearfix">
					<li><input type="radio" name="answer3" value="300-500元" id="option31"/><label for="option31">A.300-500元</label></li>
					<li><input type="radio" name="answer3" value="500-1000元" id="option32"/><label for="option32">B.500-1000元</label></li>
					<li><input type="radio" name="answer3" value="1000元以上" id="option33"/><label for="option33">C.1000元以上</label></li>
				</ul>
			</div>
			<div class="box-right right">
				<div class="title-r"><img src="${cdnUrl}/img/web/activity/yangguang/ktcx/title_right.png" alt=""></div>
				<div class="form-inner">
					<@commonZengXianForm/>
					<div class="submit-button-area" id="submitButton"></div>
					<#-- 条款 -->
					<@yangguangProtocol/>
				</div>	
				<@warmTipYangGuang/>
			</div>
		</div>
		<div class="cont-box clearfix">
			<div class="box left">
				<div class="detail">
					<img class="title" src="${cdnUrl}/img/web/activity/yangguang/ktcx/detail_title.png" alt="">
					<img class="detail-cont" src="${cdnUrl}/img/web/activity/yangguang/ktcx/detail.png?20161116" alt="">
				</div>
			</div>
			<div class="box right">
				<div class="rule">
					<img class="title" src="${cdnUrl}/img/web/activity/yangguang/ktcx/rule_title.png" alt="">
					<@activityRuleYangGuang/>
				</div>
			</div>
		</div>
		<div class="line foot-line"></div>
	</div>
	<#-- 尾部 -->
	<div class="footer">
		<input type="hidden" id="activityPath" value="${activityPath}"/>
		<span>
			<p>版权所有 © 阳光人寿保险股份有限公司，未经许可不得复制、转载或摘编，违者必究!</p>
		</span>
	</div>
</div>
<@htmlFoot/>