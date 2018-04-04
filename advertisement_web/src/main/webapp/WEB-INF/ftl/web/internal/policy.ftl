<#include "config.ftl" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body screen_capture_injected="true" ryt11773="1">
	<div style="display:none;">
		远山保险-爱自己，更为家人 
		远山保险-爱自己，更为家人 
		远山保险-爱自己，更为家人 
	</div>
	<table width="700px" style="font-size: 16px; font-family: '微软雅黑'; color:#555;">
		<tr>
			<td width="20px"></td>
			<td align="left" height="40px" colspan="2">
				<img src="${cdnUrl}/img/common/email/logo/${merchant.englishName}-logo.png"/>
			</td>
		</tr>
	</table>
	<table width="650px" style="font-size: 14px; font-family: '微软雅黑'; color:#555; background-color: #f1f1f1;">
		<tr>
			<td colspan="4" height="20px"></td>
		</tr>
		<tr>
			<td width="20px"></td>
			<td colspan="2" height="40px"><h3 style="margin:0;">尊敬的${insurant.name}：</h3></td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td width="20px"></td>
			<td colspan="2" height="10px"><p style="margin:0; font-size:14px;">恭喜您成功参与远山保险送免费保障的活动，您领取的保险信息如下：</p><p></p></td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td width="20px"></td>
			<td width="300px" height="60px" style="background-color:#FFF;" align="center"><p>投保人信息</p></td>
			<td width="300px" style="background-color:#FFF;" align="center"><p>保险信息</p></td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td width="20px"></td>
			<td height="60px" style="background-color:#FFF;padding-left:40px;">
				<#if insurant.childName??>
					<p>宝宝姓名：${insurant.childName}</p>
					<p>宝宝生日：${insurant.childBirthdayValue}</p>
					<p>投保人：${insurant.name}</p>
					<p>联系方式：${insurant.mobile}</p>
				<#else>
					<p>投保人：${insurant.name}</p>
					<p>联系方式：${insurant.mobile}</p>
					<p>生日：${insurant.birthdayValue}</p>
					<#if insurant.identityCard??>
					<p>身份信息：${insurant.identityCard}</p>
					</#if>
				</#if>
			</td>
			<td style="background-color:#FFF;padding-left:40px;">
				<p>保险公司：${merchant.name}</p>
				<p>保险产品：${insurance.name}</p>
				<p>保单号：<#if insurant.policyNumber?? && merchant.englishName!="zhongying">${insurant.policyNumber}<#else>确认信息后1-2个工作日提供</#if></p>
				<p>保险有效期：即日起至${insurant.endTimeValue}</p>
			</td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td colspan="4" height="30px"></td>
		</tr>
		<tr>
			<td></td>
			<td height="60px" style="background-color:#fdfbf3;" align="center"><p>保险范围</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p>保险金额</p></td>
			<td></td>
		</tr>
		<#if insurance.detailList??>
			<#list insurance.detailList as detail>
			<tr>
				<td></td>
				<td style="background-color:#fdfbf3;" align="center" height="40px"><p>${detail.cover}</p></td>
				<td style="background-color:#fdfbf3;" align="center"><p>${detail.amount}</p></td>
				<td></td>
			</tr>
			</#list>
		</#if>
		<tr>
			<td colspan="4" width="700px">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png" style="width: 700px;" />
			</td>
		</tr>
		<tr>
			<td colspan="4" height="10px" style="background-color: #FFF;"></td>
		</tr>
		<tr>
			<td colspan="4">
				<img src="${cdnUrl}/img/common/email/email-banner.png"/>
			</td>
		</tr>
	</table>
</body>
</html>