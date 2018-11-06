<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取1万元账户安全保障" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/xzhx-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安账户险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />
<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<@commonHeader description=""/>
<@commonBanner path="pingan/xzhx" />
<div class="container">
	<div class="wrapper">
		<div class="form-wrap clearfix">
			<div class="form-area left">
				<div class="description-title">
					<span>
						填写信息 免费领取
					</span>
				</div>
				<@commonZengXianForm/>
				<#-- 条款 -->
				<@pinganProtocol/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">免费领取</div>
				</div>		
			</div>
			<div class="insurance-detail right">
				<div class="description-title">
					<span>
						赠险详情
					</span>
				</div>
				<div class="product-description">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="60%">
								<p><span class="product-title">保险名称</span>网银e护</p>
							</td>
							<td width="40%">
								<p><span class="product-title">保险年龄</span>18-50岁</p>
							</td>
						</tr>
						<tr>
							<td>
								<p><span class="product-title">保障期限</span>30天</p>
							</td>
							<td>
								<p><span class="product-title">保障金额</span>10000元</p>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<p><span class="product-title">保障范围</span>支付宝/微信支付/信用卡/银联卡全保障</p>
							</td>
						</tr>
					</table>
				</div>
				<img src="${cdnUrl}/img/web/activity/pingan/xzhx/right-icon.png"/>
			</div>
		</div>
	</div>
</div>
<div class="wrapper">
	<div class="insurance-description clearfix">
			<div class="box box-left">
				<img src="${cdnUrl}/img/web/activity/pingan/xzhx/insurance-description1.png"/>
				<img src="${cdnUrl}/img/web/activity/pingan/xzhx/insurance-description2.png"/>
				<img src="${cdnUrl}/img/web/activity/pingan/xzhx/insurance-description3.png"/>
			</div>
		</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}" chineseName=""/>
</div>
<@htmlFoot/>