<#include "../../../common/core.ftl" />
<@htmlHead title="掌门1对1" description="">
	<@jsFile file=["common/zengxian.js","wap/loan/zhangmen/ydy.js" "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/zhangmen/ydy.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="banner">
		<img src="${ossUrl}/img/wap/loan/zhangmen/ydy/logo.png" alt="">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${ossUrl}/img/wap/loan/zhangmen/ydy/banner.png">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<div class="youdian">
					<img src="${cdnUrl}/img/wap/loan/zhangmen/ydy/title.png" alt="">
				</div>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<div class="form-item form-selectpicker">
					<div class="input-area select-item">
						<div class="premium-input" id="gradeSelectControl">
							<div class="field-input">
								<span class="field-embeded-name special-name">年级</span>
								<i class="sj">
									<img src="${cdnUrl}/img/wap/loan/zhangmen/ydy/sj.jpg" alt="">
								</i>
								<div class="field-input-control field-select field-center-align"><input class="addr-input" id="ageInput" type="text" placeholder="" disabled></div>
							</div>
						</div>
					</div>
					<div class="input-area">
						<div class="premium-input" id="courseSelectControl">
							<div class="field-input">
								<span class="field-embeded-name special-name">课程</span>
								<i class="sj">
									<img src="${ossUrl}/img/wap/loan/zhangmen/ydy/sj.jpg" alt="">
								</i>
								<div class="field-input-control field-select field-center-align"><input class="addr-input" id="courseInput" type="text" placeholder="" disabled></div>
							</div>
						</div>
					</div>
					<input type="hidden" name="question1" value="年级">
					<input type="radio" name="answer1" id="answer1">
					<input type="hidden" name="question2" value="课程">
					<input type="radio" name="answer2" id="answer2">
				</div>
				<div id="agePopTipList" class="poptip poptip-list">
					<div class="poptip-select-head">请选择年级<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
					<ul>
						<li><label for="option11">幼儿园中班</label><input type="radio" name="answer1" id="option11" value="kindergarten1"></li>
						<li><label for="option12">幼儿园大班</label><input type="radio" name="answer1" id="option12" value="kindergarten2"></li>
						<li><label for="option13">学前班</label><input type="radio" name="answer1" id="option13" value="preschool"></li>
						<li><label for="option14">小一</label><input type="radio" name="answer1" id="option14" value="little1"></li>
						<li><label for="option15">小二</label><input type="radio" name="answer1" id="option15" value="little2"></li>
						<li><label for="option16">小三</label><input type="radio" name="answer1" id="option16" value="little3"></li>
						<li><label for="option17">小四</label><input type="radio" name="answer1" id="option17" value="little4"></li>
						<li><label for="option18">小五</label><input type="radio" name="answer1" id="option18" value="little5"></li>
						<li><label for="option19">小六</label><input type="radio" name="answer1" id="option19" value="little6"></li>
						<li><label for="option20">初一</label><input type="radio" name="answer1" id="option20" value="junior1"></li>
						<li><label for="option21">初二</label><input type="radio" name="answer1" id="option21" value="junior2"></li>
						<li><label for="option22">初三</label><input type="radio" name="answer1" id="option22" value="junior3"></li>
						<li><label for="option23">初四</label><input type="radio" name="answer1" id="option23" value="junior4"></li>
						<li><label for="option24">高一</label><input type="radio" name="answer1" id="option24" value="high1"></li>
						<li><label for="option25">高二</label><input type="radio" name="answer1" id="option25" value="high2"></li>
						<li><label for="option26">高三</label><input type="radio" name="answer1" id="option26" value="high3"></li>
					</ul>
				</div>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitInformationButton">
						<img src="${ossUrl}/img/wap/loan/zhangmen/ydy/submit_btn.png" alt="">
					</div>
				</div>
				<div class="protocal-desc">*官网承诺您的信息将不被泄露</div>
			</div>
		</div>
	</div>
	<div class="desc"><img src="${ossUrl}/img/wap/loan/zhangmen/ydy/detail1.png" alt=""></div>
	<div class="desc"><img src="${ossUrl}/img/wap/loan/zhangmen/ydy/detail2.png" alt=""></div>
	<div class="desc"><img src="${ossUrl}/img/wap/loan/zhangmen/ydy/detail3.png" alt=""></div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}" />
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
	<div class="bottom-btn" id="bottomBtn">
		<img src="${ossUrl}/img/wap/loan/zhangmen/ydy/bottom_btn.png" alt="">
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
	<h1 class="result-title">报名成功</h1>
	<p>请您手机保持畅通，稍后请留意<span class="money">021</span>开头客服电话会与您联系</p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>