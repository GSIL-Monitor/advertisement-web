<#compress><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【网易贵金属】转账请求提交成功！</title>
<base target="_blank">
</head>
<#assign txt = ["银行卡划转到交易商账户中","交易商账户划转到银行卡中"]/>
<body style="background: #0e2947;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" style="text-align: center; table-layout: fixed" background="http://pimg1.126.net/mail/silver/noticemail20141121/rpt_bg.png">
  <tr>
    <td>
      <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" style="table-layout:fixed">
        <tr>
          <td width="700" height="100" align="center" valign="bottom">
            <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" style="table-layout:fixed">
              <tr>
                <td width="460" align="right"><img src="http://pimg1.126.net/mail/silver/noticemail20150107/logo.png" alt="网易贵金属" width="230" height="60" border="0"/></td>
                <td width="240" style="font-size:12px;color:#fff;font-family: 'Microsoft YaHei';" align="right" valign="bottom">客服电话:020-83568090</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td width="700" height="20" >&nbsp;</td>
        </tr>
        <tr>
          <td width="700" height="65" valign="bottom" bgcolor="#ffffff" style="font-size: 30px;font-family: 'Microsoft YaHei';color:#333; " align="center">转账请求提交成功</td>
        </tr>
        <tr>
          <td width="700" height="50" bgcolor="#ffffff" style="font-size: 14px;font-family: 'Microsoft YaHei';color:#333; " align="left"><div style="margin: 0 20px;">亲爱的${(username!'用户')?split('@')[0]}：</div></td>
        </tr>
        <tr>
          <td width="700" bgcolor="#ffffff" style="line-height:34px;" align="left"><div style="font-size:16px;font-family: 'Microsoft YaHei';color:#333;margin:0 40px; ">您的转账请求提交成功<br/><strong style="color:#f03f3f;">${money!'-'}元</strong> 将从您的
          <#if type=='1'>${txt[0]}<#elseif type=='2'>${txt[1]}</#if></div></td>
        </tr>
        <tr>
          <td width="700" height="130" align="center" bgcolor="#ffffff" >
            <div style="width:620px;height:110px;border-bottom:1px dashed #e0e0e0;">
              <img src="http://pimg1.126.net/mail/silver/noticemail20150107/trans_bg${type}.png" alt="转账" border="0" width="313" height="89">
            </div>
          </td>
        </tr>
        <tr>
          <td width="700" height="40" align="left" valign="middle" bgcolor="#ffffff" style="line-height:40px;"><div style="font-size:16px;font-family: 'Microsoft YaHei';color:#333;margin:0 40px;line-height:40px;">绑定银行卡：<img src="${bankLogo}" alt="${bankName}" width="40" height="40" border="0" style="width:40px;height:40px;vertical-align:middle;"/> ${bankName}，尾号${bankCard}</div></td>
        </tr>
        <tr>
          <td width="700" height="40" align="left" bgcolor="#ffffff" style="line-height:34px;"><div style="font-size:16px;font-family: 'Microsoft YaHei';color:#333;margin:0 40px; ">绑定的网易账号：${username!''}</div></td>
        </tr>
        <tr>
          <td width="700" height="20" bgcolor="#ffffff" >&nbsp;</td>
        </tr>        
        <tr>
          <td width="700" height="40" align="left" bgcolor="#ffffff" style="font-size: 12px;font-family: 'Microsoft YaHei';color:#999;"><div style="font-size: 12px;font-family: 'Microsoft YaHei';color:#999;margin:0 40px;">如非本人操作，请致电020-83568090&#12288;或者访问fa.163.com咨询在线客服。</div></td>
        </tr>
        <tr>
          <td width="700" height="34" bgcolor="#ffffff" align="center"><img src="http://pimg1.126.net/mail/silver/noticemail20141121/line.png" alt="网易贵金属" border="0"/></td>
        </tr>       
        <tr>
          <td width="700" height="35" align="center" valign="top" bgcolor="#ffffff" style="font-size: 12px;font-family: 'Microsoft YaHei';color:#999;">fa.163.com</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="150">&nbsp;</td>
  </tr>
</table>
</body>
</html></#compress>