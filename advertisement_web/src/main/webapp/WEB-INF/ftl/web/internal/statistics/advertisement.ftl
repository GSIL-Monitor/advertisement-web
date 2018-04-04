<#include "../common/core.ftl" />
<@htmlHead title="统计" description="">
</@htmlHead>
<table border="1" style="font-size: 30px; line-height: 50px; margin: 50px;">
<tr>
	<td>广告名称</td>
	<td>总数</td>
	<#list positionList as position>
		<td>${position}</td>
	</#list>
</tr>
<#list ret as stat>
	<tr>
		<td>${stat.advertisement.description}</td>
		<td>${stat.total}</td>
		<#list stat.positionClickList as positionClick>
			<td>${positionClick.count}</td>
		</#list>
	</tr>
</#list>
</table>
<@htmlFoot/>