<#include "../../common/core.ftl" />
<@htmlHead title="" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
</@htmlHead>
<script type="text/javascript">
	<#if url?? && (url?length>0)>
	$(function() {
		location.href='${url}';
	});
	</#if>
</script>
<@htmlFoot/>