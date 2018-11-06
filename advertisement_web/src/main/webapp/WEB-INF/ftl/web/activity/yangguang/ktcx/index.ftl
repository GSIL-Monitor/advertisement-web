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
<@resultCalculatePopWindow/>
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
				<h4 class="question">1.<span id="question1">请问您是否已经有子女？</span></h4>
				<ul class="options">
					<div class="row clearfix">
						<li><input type="radio" name="answer1" value="1个" id="option11"/><label for="option11">A.1个</label></li>
						<li><input type="radio" name="answer1" value="2个或以上" id="option12"/><label for="option12">B.2个或以上</label></li>
						<li><input type="radio" name="answer1" value="没有" id="option13"/><label for="option13">C.没有</label></li>
					</div>
				</ul>
				<h4 class="question">2.<span id="question2">请问平时您或家人出行多以哪种方式？</span></h4>
				<ul class="options clearfix">
					<li><input type="radio" name="answer2" value="自驾车" id="option21"/><label for="option21">A.自驾车</label></li>
					<li><input type="radio" name="answer2" value="火车或公车" id="option22"/><label for="option22">B.火车或公车</label></li>
					<li><input type="radio" name="answer2" value="飞机" id="option23"/><label for="option23">C.飞机</label></li>
				</ul>
				<h4 class="question">3.<span id="question3">如果让您选择，您会更倾向于购买哪种商业保障？</span></h4>
				<ul class="options clearfix">
					<li><input type="radio" name="answer3" value="意外保障" id="option31"/><label for="option31">A.意外保障</label></li>
					<li><input type="radio" name="answer3" value="重疾保障" id="option32"/><label for="option32">B.重疾保障</label></li>
					<li><input type="radio" name="answer3" value="医疗保障" id="option33"/><label for="option33">C.医疗保障</label></li>
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
	<@footer activityPath="${activityPath}" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</div>
<@htmlFoot/>