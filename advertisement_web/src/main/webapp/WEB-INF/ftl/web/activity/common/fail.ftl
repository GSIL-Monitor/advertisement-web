<#include "../../common/core.ftl" />
<@htmlHead title="领取礼包" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		TipWindow.showTip('#failTip');
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