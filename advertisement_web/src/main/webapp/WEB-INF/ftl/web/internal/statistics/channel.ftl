<#include "../common/core.ftl" />
<@htmlHead title="统计" description="">
</@htmlHead>
<table border="1" style="font-size: 30px; line-height: 50px; margin: 50px;">
	<tr>
		<td>渠道</td>
		<td>提交数</td>
		<td>赠险数</td>
		<td>有效赠险1</td>
		<td>有效赠险2</td>
		<td>身份证号</td>
		<td>问卷数</td>
		<td>测保数</td>
		<td>邮箱数</td>
	</tr>
	<#list ret as stat>
	<tr>
		<td>${stat.channel}</td>
		<td>${stat.submitCount}</td>
		<td>${stat.insuredCount}</td>
		<td>${stat.effectCount}</td>
		<td>${stat.showEffectCount}</td>
		<td>${stat.identityCardCount}</td>
		<td>${stat.surveyCount}</td>
		<td>${stat.calculateCount}</td>
		<td>${stat.emailCount}</td>
	</tr>
	</#list>
</table>
<@htmlFoot/>