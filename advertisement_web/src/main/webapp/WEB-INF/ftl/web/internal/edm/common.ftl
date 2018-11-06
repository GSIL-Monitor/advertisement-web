<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body screen_capture_injected="true" ryt11773="1">
		<div style="display:none;">
			<img src="http://www.dachuanbao.cn/html/img/edm/1.jpg?from=${channel}"/>
		</div>
		<#if name??>
		<table width="700px" height="700px" style="font-size: 14px; font-family: '微软雅黑'; color:#555; background-color: #f1f1f1; border: none;" border="0" cellspacing="0" cellpadding="0">
			<#list pointList as rows>
			<tr>
				<#list rows as point>
				<td>
					<a href="http://www.dachuanbao.cn/activity/${merchant}/${name}.html?channel=${channel}&positionCode=${point.mailCode}" target="_blank">
						<img src="http://yuanshanbao.oss-cn-beijing.aliyuncs.com/html/img/edm/common/${merchant}/${name}.png?x-oss-process=image/crop,x_${point.x?c},y_${point.y?c},w_${point.w?c},h_${point.h?c}" style="display:block;" />
					</a>
				</td>
				</#list>
			</tr>
			</#list>
		</table>
		</#if>
		<table width="700px" style="font-size: 13px; line-height: 20px; font-family: '微软雅黑'; color:#777; border: none;" border="0" cellspacing="0" cellpadding="0">
			<#if advertisementImage?? && advertisementLink??>
			<tr>
				<td>
					<a href="http://www.dachuanbao.cn${advertisementLink}" target="_blank">
						<img src="${advertisementImage}" style="display:block;" />
					</a>
				</td>
			</tr>
			</#if>
			<tr height="10px">
				<td></td>
			</tr>
			<tr>
				<td>${randomText}</td>
			</tr>
			<tr height="20px">
				<td align="center" style="color: #999">如不需要，可以点击<a href="http://www.dachuanbao.cn/internal/email/unsubscribe.html" target="_blank" style="color: #67b3fe">退订</td>
			</tr>
		</table>
	</body>
</html>