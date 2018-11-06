<#-- 配置文件 -->

<#-- 域 -->
<#assign domainUrl = domainUrl!'' />

<#-- cdn -->
<#assign cdnUrl = cdnUrl!'/html' />

<#if isHttps?? && isHttps=="false">
<#assign ossUrl = "http://yuanshanbao.oss-cn-beijing.aliyuncs.com/html" />
<#else>
<#assign ossUrl = "https://yuanshanbao.oss-cn-beijing.aliyuncs.com/html" />
</#if>

<#-- 时间戳 -->
<#assign cdnFileVersion = cdnFileVersion!(.now?string('yyyyMMddhhmmss'))/>

<#assign sloganTitle = "" />

<#assign configPageSize=9 />