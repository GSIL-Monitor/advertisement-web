<#include "../../../common/core.ftl" />
<#assign title="惠学啦免费领1000元学习礼包"/>
<@htmlHead title="${title}" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/hxl/mfl.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/hxl/mfl.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="banner">
		<img src="${cdnUrl}/img/wap/loan/hxl/mfl/banner@3x.png?${cdnFileVersion}">
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="top-header">惠学啦已累计为<span class="top-header-num">${vipkidCount+zhangmenCount+dadaCount+talkCount+yingfuCount+yixueCount}</span>人成功提供服务</div>
		<div class="ques">
			<div class="ques-title">
				<img src="${cdnUrl}/img/wap/loan/hxl/mfl/ques-title@3x.png" alt="">
			</div>
			<div class="improve">
				<div class="improve-item">
					<label class="checkbox-inline">
						<div class="inprove-check improve-agreed"></div>
						<input type="checkbox" id="inlineCheckbox1" value="option1" checked="checked">
						<span>提高学分</span>
					</label>
				</div>
				<div class="improve-item">
					<label class="checkbox-inline">
						<div class="inprove-check improve-agree"></div>
						<input type="checkbox" id="inlineCheckbox2" value="option2">
						<span>提高能力</span>
					</label>
				</div>
			</div>
		</div>
		<div class="form-background">
			<div class="form-area">
				<#-- 字段 -->
				<div class="form-item">
					<div class="input-area">
						<div class="field-input">
							<#-- <span class="field-embeded-name">手机</span> -->
							<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="请输入手机号领取大礼包"<#if mobile??>value="${mobile}"</#if>/>
						</div>
						<div class="error-tip"></div>
					</div>
				</div>
				<div class="form-item">
					<div class="input-area">
						<div class="left mobile-input">
							<div class="field-input">
								<input class="field-input-control" type="number" id="smsCode" name="smsCode" placeholder="输入短信验证码"/>
							</div>
							<div class="error-tip"></div>
						</div>
						<div class="right sms-button" id="sendSmsButton">获取验证码</div>
						<input type="hidden" id="smsTipContent" value="赠险"/>
					</div>
				</div>
				<div class="form-flex">
					<div class="form-item">
						<div class="input-area clearfix">
							<div class="">
								<div class="field-input">
									<input class="field-input-control" type="text" id="name" name="name" placeholder="孩子姓名"<#if name??>value="${name}"</#if>/>
								</div>
								<div class="error-tip">
								</div>
							</div>
						</div>
						<input type="hidden" id="noIdentityCard" value="no"/>
						<input type="hidden" name="question1" value="孩子的年龄">
						<input type="radio" name="answer1" id="answer1">
						<input type="hidden" name="question2" value="推荐机构">
						<input type="radio" name="answer2" id="answer2">
					</div>
					<div class="form-item">
						<div class="input-area clearfix">
							<div class="premium-input" id="ageSelectControl">
								<div class="field-input">
									<#-- <span class="field-embeded-name">孩子年龄</span> -->
									<i class="sj"></i>
									<div class="field-input-control field-select field-center-align"><input id="ageInput" placeholder="孩子年龄" type="text" disabled></div>
								</div>
							</div>
						</div>
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
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<div class="recommend">
					<div class="recommend-title"><img src="${cdnUrl}/img/wap/loan/hxl/mfl/tuijian@3x.png" alt=""></div>
					<div class="box-items">
						<div class="recommend-box">
							<div class="item-container">
								<input type="checkbox" class="recommend-checkbox" value="vipkid">
								<div class="box-agree box-check"></div>
								<span class="box-title">VIPKID</span>
								<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
							</div>
							<p class="box-content">已报名<span>${vipkidCount}</span>人</p>
						</div>
						<div class="recommend-box">
						<div class="item-container">
							<input type="checkbox" class="recommend-checkbox" value="zhangmen">
							<div class="box-agree box-check"></div>
							<span class="box-title">掌门1对1</span>
							<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
						</div>
						<p class="box-content">已报名<span>${zhangmenCount}</span>人</p>
					</div>
					<div class="recommend-box">
						<div class="item-container">
							<input type="checkbox" class="recommend-checkbox" value="dada">
							<div class="box-agree box-check"></div>
							<span class="box-title">哒哒英语</span>
							<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
						</div>
						<p class="box-content">已报名<span>${dadaCount}</span>人</p>
					</div>
					<div class="recommend-box">
						<div class="item-container">
							<input type="checkbox" class="recommend-checkbox" value="yixue">
							<div class="box-agree box-check"></div>
							<span class="box-title">乂学教育</span>
							<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
						</div>
						<p class="box-content">已报名<span>${yixueCount}</span>人</p>
					</div>
					<div class="recommend-box">
						<div class="item-container">
							<input type="checkbox" class="recommend-checkbox" value="yingfu">
							<div class="box-agree box-check"></div>
							<span class="box-title">英孚教育</span>
							<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
						</div>
						<p class="box-content">已报名<span>${yingfuCount}</span>人</p>
					</div>
					<div class="recommend-box">
						<div class="item-container">
							<input type="checkbox" class="recommend-checkbox" value="51talk">
							<div class="box-agree box-check"></div>
							<#-- <img src="${cdnUrl}/img/wap/loan/hxl/mfl/check@3x.png" alt=""> -->
							<span class="box-title">51talk</span>
							<img class="box-discount" src="${cdnUrl}/img/wap/loan/hxl/mfl/discount@3x.png" alt="">
						</div>
						<p class="box-content">已报名<span>${talkCount}</span>人</p>
					</div>
				</div>
				<div class="helpful-hints">*友情提示：VIPKID、哒哒英语和51talk报名年龄4-12岁，掌门1对1为四年级到高中三年级，乂学为小学1年级—初三。英孚为成人英语
				</div>
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitInformationButton">
						<img src="${cdnUrl}/img/wap/loan/hxl/mfl/button@3x.png" alt="">
					</div>
				</div>
				<#-- 条款 -->
				<div class="protocal">
					<label for="agreeCheck" class="agree-check-label">
						<input type="checkbox" id="agreeCheck" class="agree-check" checked>
						<em class="agree-icon">
						</em>	
						<span>我已阅读并同意<a href="javascript:;" target="_blank">《注册协议》</a></span>
					</label>
					<@commonProtocolContent/>
					<div id="activityRulePopWindow" class="hide">
						<@activityRule/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="desc"></div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}" />
		<input type="hidden" id="activityPath" value="${activityPath}"/>
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