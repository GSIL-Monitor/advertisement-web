<#include "../../common/core.ftl" />
<@htmlHead title="领取礼包" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<script type="text/javascript">
	$(function(){
		setTimeout("TipWindow.showTip('#failTip')", 100);
		$('.overlay').addClass('dark-overlay');
		$('.overlay').click(function() {
			javascript:history.go(-1);
		});
		$('#failTipClose').click(function() {
			javascript:history.go(-1);
		});
	});
</script>
<@failPopWindow/>
<@htmlFoot/>