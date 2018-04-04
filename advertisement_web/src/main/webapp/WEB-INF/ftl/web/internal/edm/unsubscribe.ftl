<#include "../../common/core.ftl" />
<@htmlHead title="退订" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		alert('退订成功');
	});
</script>
<@failPopWindow/>
<@htmlFoot/>