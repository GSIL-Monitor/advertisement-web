<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body screen_capture_injected="true" ryt11773="1">
	<div style="display:none;">
		<img src="http://www.yuanshanbao.com/html/img/edm/1.jpg?from=${channel}"/>
	</div>
	<table width="700px" style="font-size: 14px; font-family: '微软雅黑'; color:#555; background-color: #f1f1f1; border: none;" border="0" cellspacing="0" cellpadding="0">
		<#list 1..6 as index>
		<tr>
			<td>
				<a href="http://www.yuanshanbao.com/edm/xfdc.html?channel=${channel}" target="_blank">
					<img src="http://yuanshanbao.oss-cn-beijing.aliyuncs.com/html/img/edm/axtx/axtx_${index}_l.jpg" style="display:block;" />
				</a>
			</td>
			<td>
				<a href="http://www.yuanshanbao.com/edm/xfdc.html?channel=${channel}" target="_blank">
					<img src="http://yuanshanbao.oss-cn-beijing.aliyuncs.com/html/img/edm/axtx/axtx_${index}_r.jpg" style="display:block;"/>
				</a>
			</td>
		</tr>
		</#list>
	</table>
</body>
</html>