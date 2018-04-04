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
				<img src="${cdnUrl}/img/common/email/logo/pingan-logo.png"/>
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
			<td colspan="2" height="10px"><p style="margin:0; font-size:14px;">您参与的平安鸿运随行返还型意外保障试算结果为：</p><p></p></td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td width="20px"></td>
			<td colspan="2" height="60px" style="background-color:#FFF;padding-left:40px;font-size:16px;">
				<#if payWay=="month">
					<p>您首期需支付三个月保费：<span style="color:#e34b57;">￥${calculator.threeMonthPremium}元</span></p>
					<p>以后每月支付：<span style="color:#e34b57;">￥${calculator.monthPremium}元</span></p>
					<p>折合每天仅：<span style="color:#e34b57;">￥${calculator.dayForMonthPremium}元</span></p>
				<#else>
					<p>您每年需支付保费：<span style="color:#e34b57;">￥${calculator.yearPremium}元</span></p>
					<p>折合每天仅：<span style="color:#e34b57;">￥${calculator.dayForYearPremium}元</span></p>
				</#if>
			</td>
			<td width="20px"></td>
		</tr>
		<tr>
			<td colspan="4" height="30px"></td>
		</tr>
		<tr>
			<td></td>
			<td width="300px" height="60px" style="background-color:#fdfbf3;" align="center"><p>保险范围</p></td>
			<td width="300px" style="background-color:#fdfbf3;" align="center"><p>保险金额</p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>意外身故保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>意外伤残保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span>＊评定伤残等级对应比例</p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>自驾车意外身故特别保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>自驾车意外伤残特别保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span>＊评定伤残等级对应比例</p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>公共交通意外身故特别保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>公共交通意外伤残特别保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.insuredSum}元</span>＊评定伤残等级对应比例</p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>意外伤害住院日额津贴</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p><span style="color:#e34b57;">${calculator.dailyAllowance}元/天</span></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>身故保障</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p>所交保费的<span style="color:#e34b57;">112%</span></p></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="background-color:#fdfbf3;" align="center" height="40px"><p>满期生存保险金</p></td>
			<td style="background-color:#fdfbf3;" align="center"><p>所交保费的<span style="color:#e34b57;">112%</span></p></td>
			<td></td>
		</tr>
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