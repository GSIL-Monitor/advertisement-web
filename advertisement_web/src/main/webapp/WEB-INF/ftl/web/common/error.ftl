<#include "core.ftl" />
<!doctype html>
<html class="server-error-page">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
	<meta name="baidu-site-verification" content="3QzDHZVkvx" />
	<title>系统错误</title>
	<link rel="stylesheet" type="text/css" href="/html/css/pages/common.css">
	<link rel="stylesheet" type="text/css" href="/html/css/pages/base.css">
	<link rel="stylesheet" type="text/css" href="/html/css/pages/40x.css">
	<link rel="shortcut icon" type="image/ico" href="/favicon.ico">
</head>
<body class="server-error-page">
<script type="text/javascript">
	$(function(){
		coreConfig = {
			cdnUrl : '${cdnUrl}'
		}
	});
</script>
<@wrapper>
	<@nav />
	<@container>
		<div class="pic-box">
			<div class="error-cont">
				<#if retDesc??>
					${retDesc}
				<#else>
					系统错误！
				</#if>
			</div>
			<img src="/html/img/web/50x.png" alt="50x">
		</div>
	</@container>
	<@footerBar />
</@wrapper>
</body>
</html>