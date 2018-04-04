//  *****************************************************************************
//  文 件 名：	jbardisplay.js
//  作    者：  wsj
//  版    本：  1.0.0.0
//  日    期：  2014-07-15
//  文件描述：
// 		 调用电话条的辅助函数
//  说    明：
//		 调用电话条的辅助函数
//  修改说明：
// *****************************************************************************

var g_msgseq = -1;
//--------------------------------------------------------------------------------------------------
// 辅助函数
//--------------------------------------------------------------------------------------------------
//window.onunload = window_onunload;
//function window_onunload() {
 //   applicationUnLoad();
//}
//创建对象成功，绑定电话条事件
function setVccBarEvent()
{
	setTimeout("funInitial()",2000);
    //3
	//呼叫事件
	application.oJVccBar.OnCallRing = onOnCallRing;
	//解答
	application.oJVccBar.AnswerCall = onOnAnswerCall;
	application.oJVccBar.OnCallEnd = onOnCallEnd;
	//18 //提示事件（信息提示）	
	application.oJVccBar.OnPrompt = onOnPrompt;
	//按钮状态报告，返回目前坐席可用的按钮
	application.oJVccBar.OnReportBtnStatus = onReportBtnStatus;
	//若初始化成功，触发事件 OnInitalSuccess（初始化成功）；
	application.oJVccBar.OnInitalSuccess = onOnInitalSuccess;
	//若初始化失败，自动触发事件 OnInitalFailure（初始化失败）；
	application.oJVccBar.OnInitalFailure = onOnInitalFailure;
	//电话条底层事件报告
	application.oJVccBar.OnEventPrompt = onOnEventPrompt;
	//工作信息报告，电话条会返回坐席目前的状态
	application.oJVccBar.OnAgentWorkReport = onOnAgentWorkReport;
	//电话条随路数据事件报告--当座席随路数据发生变化时候触发
	application.oJVccBar.OnCallDataChanged = onOnCallDataChanged;
	//电话条退出事件报告
	application.oJVccBar.OnBarExit = onOnBarExit;
	//电话条排队信息事件报告，是 CallQueueQuery 对应的事件
    application.oJVccBar.OnCallQueueQuery = onOnCallQueueQuery;
	//电话条某个状态座席组事件报告，是 QueryGroupAgentStatus 对应的事件
	application.oJVccBar.OnQueryGroupAgentStatus = onOnQueryGroupAgentStatus;
	//系统消息事件报告
	application.oJVccBar.OnSystemMessage  = onOnSystemMessage;
    //接收到微博事件报告
	application.oJVccBar.OnRecvWeiboMsg = onOnRecvWeiboMsg;
	//接收到座席即时消息事件报告
	application.oJVccBar.OnIMMessage = onOnIMMessage
	//application.oJVccBar.OnRecvWeChatMessage = onOnRecvWeChatMessage;
	//application.oJVccBar.OnSendWeChatMsgReport = onOnSendWeChatMsgReport;
	//application.oJVccBar.OnUploadFileToMMSReport = onOnUploadFileToMMSReport;
	//application.oJVccBar.OnDownloadFileToMMSReport = onOnDownloadFileToMMSReport;

	//座席工作信息统计和座席所在服务工作情况统计，适用于 MinotorVersion 为 4.0
	application.oJVccBar.OnWorkStaticInfoReport = onOnWorkStaticInfoReport;
	//电话条查询某些组某些状态座席事件报告，是 QuerySPGroupList 对应的事件
    application.oJVccBar.OnQuerySPGroupList = onOnQuerySPGroupList;

   /*------------------监控事件----------------------------*/
	//14
	//座席实时状态报告，适用于 MinotorVersion 为 3.0/4.0，具体信息不一样
	application.oJVccBar.OnAgentReport = onOnAgentReport;
	//分机实时状态报告，适用于 MinotorVersion 为 3.0
	application.oJVccBar.OnTelReport = onOnTelReport;
	//服务信息报告
	application.oJVccBar.OnServiceReport = onOnServiceReport;
	//IVR 信息报告
	application.oJVccBar.OnIvrReport = onOnIvrReport;
	//外呼任务呼叫统计报告，适用于 MinotorVersion 为 3.0
	application.oJVccBar.OnTaskReport = onOnTaskReport;
	//外呼任务状态报告，适用于 MinotorVersion 为 3.0
	application.oJVccBar.OnOutboundReport = onOnOutboundReport;
	//人工服务的统计信息汇总报告，适用于 MinotorVersion 为 3.0
	application.oJVccBar.OnCallReportInfo = onOnCallReportInfo;
	//排队信息报告，适用于 MinotorVersion 为 3.0/4.0
	application.oJVccBar.OnQueueReport = onOnQueueReport;
	//查询指定参数的统计信息查询事件报告，查询 QueryMonitorSumInfo 对应的事件
	application.oJVccBar.OnQueryMonitorSumReport = onOnQueryMonitorSumReport;
	//人工服务工作信息大屏显示，适用于 MinotorVersion 为 4.0
	application.oJVccBar.OnWallServiceReport = onOnWallServiceReport;
	//人工服务排队信息大屏显示，适用于 MinotorVersion 为 4.0
	application.oJVccBar.OnWallQueueReport = onOnWallQueueReport;
	//15 分钟呼入服务情况，从午夜零点开始到目前的统计值，适用于 MinotorVersion 为 4.0
	application.oJVccBar.OnServiceStaticReport = onOnServiceStaticReport;
	//15 分钟座席统计信息，从午夜零点开始到目前的统计值，适用于 MinotorVersion 为 4.0
	application.oJVccBar.OnAgentStaticReport = onOnAgentStaticReport;

/*-------------------------微信-----------------------------------*/
    application.oWechatManager.OnBeginSession = OnBeginSession;
    application.oWechatManager.OnEndSession = OnEndSession;
    application.oWechatManager.OnRecvMessage = OnRecvMessage;
    application.oWechatManager.OnSendMessageReport = OnSendMessageReport;
    application.oWechatManager.OnUploadFileStatus = OnUploadFileStatus;
    application.oWechatManager.OnDowndFileStatus = OnDowndFileStatus;

	
	 application.oJVccBar.OnMethodResponseEvent = onOnMethodResponseEvent;
	/*application.oJVccBar.OnMethodResponseEvent=function(cmdName,param)
   {
    showLog("【OnMethodResponseEvent】：\r\n");
    showLog(" cmdName:【"+cmdName+"】\r\n");
    showLog(" param:【"+param+"】\r\n");
    showLog(" *******************************************************************\r\n");
     if(cmdName=='SetCallData'){
	    alert(param);
	 }
    if (!(typeof (oMakeCallDlg) == "undefined" || oMakeCallDlg == null))
        oMakeCallDlg.Display(param);
}
*/
//初始化时，程序1
/////////////////////////////////////
	if(application.oJBarDisplayer != null)
        application.oJBarDisplayer.show(1);
	showLog(application.oBrowserSys.expression+"\r\n\r\n");
    displayCtrl();
}

////////////////////////--辅助函数---////////////////////////////
function displayCtrl(){
    if(isIE())
    {
        if( getLocalLanguage() != lg_zhcn )
            showLog("browser is IE\r\n");
        else
            showLog("IE系列浏览器\r\n");
    }
    else{
        if( getLocalLanguage() != lg_zhcn )
            showLog("browser is not IE\r\n");
        else
            showLog("非IE系列浏览器\r\n");
    }
    if(application.oJVccBar.GetJVccBarType() == vccBarTypeHTML5 )
    {
        if( getLocalLanguage() != lg_zhcn )
            showLog("the vccbar is 【javascript】 version!\r\n");
        else
            showLog("当前使用【纯JS】版本电话条!\r\n");
    }
    else if(application.oJVccBar.GetJVccBarType() == vccBarTypeSILVERLIGHT )
    {
        if( getLocalLanguage() != lg_zhcn )
            showLog("the vccbar is 【silverlight】 version!\r\n");
        else
            showLog("当前使用【silverlight】版本电话条!\r\n");
    }
    else
    {
        if( getLocalLanguage() != lg_zhcn )
            showLog("the vccbar is 【ocx】 version\r\n");
        else
            showLog("当前使用【OCX】版本电话条!\r\n");
    }
}
function showLog(Text) 
{
    console.log(Text);
    var oTextareaInfo= document.getElementById("TextareaInfo");
    if(oTextareaInfo != null)
	    oTextareaInfo.value = oTextareaInfo.value + Text;
}
function emptyLog()
{
    var oTextareaInfo= document.getElementById("TextareaInfo");
    if(oTextareaInfo != null)
        oTextareaInfo.value = "";
}
//--------------------------------------------------------------------------------------------------
// 电话条重载事件函数
//--------------------------------------------------------------------------------------------------

///////////////////////////////////////////////////
//呼叫事件
function onOnCallRing(CallingNo,CalledNo,OrgCalledNo,CallData,SerialID,ServiceDirect,CallID,UserParam,TaskID,UserDn,AgentDn,AreaCode,fileName,networkInfo,queueTime,opAgentID)
{
	
    showLog("【OnCallRing】：\r\n");
    showLog("         CallingNo：【"+CallingNo+"】\r\n");
    showLog("         CalledNo：【"+CalledNo+"】\r\n");
    showLog("         OrgCalledNo：【"+OrgCalledNo+"】\r\n");
    showLog("         CallData：【"+CallData+"】\r\n");
    showLog("         CallID ：【"+CallID+"】\r\n");
    showLog("         SerialID ：【"+SerialID+"】\r\n");
    showLog("         ServiceDirect ：【"+ServiceDirect+"】\r\n");
    showLog("         UserParam ：【"+UserParam+"】\r\n");
    showLog("         TaskID ：【"+TaskID+"】\r\n");
    showLog("         UserDn ：【"+UserDn+"】\r\n");
    showLog("         AgentDn ：【"+AgentDn+"】\r\n");
    showLog("         AreaCode ：【"+AreaCode+"】\r\n");
    showLog("         fileName ：【"+fileName+"】\r\n");
    showLog("         networkInfo：【"+networkInfo+"】\r\n");
    showLog("         queueTime ：【"+queueTime+"】\r\n");
    showLog("         opAgenID ：【"+opAgentID+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnAnswerCall(UserNo,AnswerTime,SerialID,ServiceDirect,CallID,UserParam,TaskID)
{

    showLog(" 【OnAnswerCall】:\r\n");
    showLog("         AnswerTime ：【"+AnswerTime+"】\r\n");
    showLog("        UserNo ：【"+UserNo+"】\r\n");
    showLog("        CallID ：【"+CallID+"】\r\n");
    showLog("        SerialID ：【"+SerialID+"】\r\n");
    showLog("        ServiceDirect ：【"+ServiceDirect+"】\r\n");
    showLog("        UserParam ：【"+UserParam+"】\r\n");
    showLog("        TaskID ：【"+TaskID+"】\r\n");
    showLog(" *******************************************************************\r\n");
}	
function onOnCallEnd(callID,serialID,serviceDirect,userNo,bgnTime,endTime,agentAlertTime,userAlertTime,fileName,directory,disconnectType,userParam,taskID,serverName,networkInfo)
{
	
    showLog(" 【OnCallEnd】:\r\n");
    showLog("         fileName   ：【"+fileName+"】\r\n");
    showLog("         directory：【"+directory+"】\r\n");
    showLog("         bgnTime  ：【"+bgnTime+"】\r\n");
    showLog("         endTime  ：【"+endTime+"】\r\n");
    showLog("         userNo ：【"+userNo+"】\r\n");
    showLog("         CallID   ：【"+callID+"】\r\n");
    showLog("         SerialID ：【"+serialID+"】\r\n");
    showLog("         ServiceDirect  ：【"+serviceDirect+"】\r\n");
    showLog("         userAlertTime  ：【"+userAlertTime+"】\r\n");
    showLog("         agentAlertTime ：【"+agentAlertTime+"】\r\n");
    showLog("         userParam      ：【"+userParam+"】\r\n");
    showLog("         taskID         ：【"+taskID+"】\r\n");
    showLog("         serverName         ：【"+serverName+"】\r\n");
    showLog("         networkInfo         ：【"+networkInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");

    $('#callButton').addClass('act');
    $('#disconnectButton').removeClass('act');
    $('#holdButton').removeClass('act');
    $.ajax({
        type: 'POST',
        url: "endCall.do",
        data: {
            callRecordId: $('#currentCallRecordId').val(),
            callRecordUrl: 'http://101.201.142.12:9999/media/100160/' + directory + '/' + fileName,
            startTime: bgnTime,
            endTime: endTime
        },
        success:function(data,status){
            
        },
        error : function(data) {
            //...
            console.log("query error...");
        }
    });
}
//----------------------初始化程序2
//提示事件	
function onOnPrompt(code,description)
{
    showLog("【OnPrompt】：\r\n");
    showLog(" code:【"+code+"】 description:【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
//-------------------------------初始化成功后的状态显示一
function onReportBtnStatus(btnIDS)
{
    if(application.oVccBarAssist.oBarBtnControl != null)
    {
        application.oVccBarAssist.oBarBtnControl.UpdateUI(btnIDS);
    }
    if(application.oVccBarAssist.oBarAgentStatus != null)
    {
       application.oVccBarAssist.oBarAgentStatus.SetAgentStatus(application.oJVccBar.GetAgentStatus());
    }

    showLog("【ReportBtnStatus】：\r\n");
    var agentStatus = application.oJVccBar.GetAgentStatus();
    if(agentStatus == 1)
    {
        if( getLocalLanguage() != lg_zhcn )
            agentStatus = agentStatus + " agent subStatus:【"+application.oJVccBar.GetAgentSubBusyStatus()+"】";
        else
            agentStatus = agentStatus + " 子状态:【"+application.oJVccBar.GetAgentSubBusyStatus()+"】";
    }
    if( getLocalLanguage() != lg_zhcn )
        showLog("         usefull ids  ：【"+btnIDS+"】\r\n agent status：【"+agentStatus+"】\r\n");
    else
        showLog("         可现状态值   ：【"+btnIDS+"】\r\n 当前座席状态：【"+agentStatus+"】\r\n");
    showLog(" *******************************************************************\r\n");
}


function onOnEventPrompt(code,description)
{
    showLog("【OnEventPrompt】：");
    showLog(" code:【"+code+"】 description:【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
//---------------------初始化成功后的状态显示三
function onOnInitalSuccess()
{
    if(application.oVccBarAssist.oBarAgentStatus != null)
    {
        application.oVccBarAssist.oBarAgentStatus.SetSubBusyStatus(application.oJVccBar.GetBusySubStatus());
    }
    showLog("【OnInitalSuccess】\r\n ");
    if( getLocalLanguage() != lg_zhcn )
        showLog("        used phoneType:【"+application.oJVccBar.GetAttribute("PhonType")+"】\r\n        其中 1：inside sipphone 2：outer device；3：remote sipphone;4：soft switch pretransfer;5：yealink phone\r\n");
    else
        showLog("        当时使用phoneType:【"+application.oJVccBar.GetAttribute("PhonType")+"】\r\n        其中 1：内置Sip电话 2：外置其他终端；3：远程sip电话;4：软交换前传号码;5：yealink话机\r\n");
    showLog(" exitCause:" + application.oJVccBar.GetExitCause() + "\r\n ");
    showLog(" *******************************************************************\r\n");
//    alert(application.oJVccBar.GetActiveService());
}
function onOnInitalFailure(code,description)
{
    showLog("【OnInitalFailure】\r\n 【"+code+"】 【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnBarExit(code,description)
{
    showLog("【OnBarExit】 \r\n【"+code+"】 【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
//--------------------------初始化成功后的状态显示二
function onOnAgentWorkReport(workStatus,description)
{
    if( getLocalLanguage() != lg_zhcn )
        showLog("【OnAgentWorkReport】 sceneid：【"+workStatus+"】 scene description：【"+description+"】\r\n");
    else
        showLog("【OnAgentWorkReport】 场景编号：【"+workStatus+"】 场景描述：【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnCallDataChanged(callData)
{
    showLog("【OnCallDataChanged】：\r\n【"+callData+"】\r\n");
    showLog(" *******************************************************************\r\n");
}

function onOnCallQueueQuery(QueueInfo)
{
    showLog("【OnCallQueueQuery】：\r\n param:【"+QueueInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}

function onOnQueryGroupAgentStatus(QueryInfo,type)
{
    showLog("【OnQueryGroupAgentStatus】：\r\n param:【"+QueryInfo+"】\r\n type:【"+type+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnSystemMessage(code,description)
{
    showLog("【OnSystemMessage】：\r\n");
    showLog(" code:【"+code+"】 description:【"+description+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnRecvWeiboMsg(message)
{
    showLog("【OnRecvWeiboMsg】 \r\n msgtype:【"+msgtype+"】 \r\n message:【"+message+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnIMMessage(msgtype,message)
{
    showLog("【OnIMMessage】  \r\n msgtype:【"+msgtype+"】 message:【"+message+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnRecvWeChatMessage(sessionId,msgseq,type,userId,vccPublicId,msgType,content,sessionUrl,recongnition,msgevent,eventKey,title,data,timeStamp)
{
    showLog("【OnRecvWeChatMessage】：\r\n");
    showLog("         sessionId   ：【"+sessionId+"】\r\n");
    showLog("         type        ：【"+type+"】\r\n");
    showLog("         msgseq      ：【"+msgseq+"】\r\n");
    showLog("         userId      ：【"+userId+"】\r\n");
    showLog("         vccPublicId ：【"+vccPublicId+"】\r\n");
    showLog("        msgType      ：【"+msgType+"】\r\n");
    showLog("        content      ：【"+content+"】\r\n");
    showLog("        sessionUrl   ：【"+sessionUrl+"】\r\n");
    showLog("        recongnition ：【"+recongnition+"】\r\n");
    showLog("        event        ：【"+msgevent+"】\r\n");
    showLog("        eventKey     ：【"+eventKey+"】\r\n");
    showLog("        title        ：【"+title+"】\r\n");
    showLog("        data         ：【"+data+"】\r\n");
    showLog("        timeStamp    ："+timeStamp+"\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnSendWeChatMsgReport(userId, sessionId, msgseq, code, des, timeStamp)
{
    showLog("【OnSendWeChatMsgReport】：\r\n");
    showLog("         userId   ：【"+userId+"】\r\n");
    showLog("         sessionId：【"+sessionId+"】\r\n");
    showLog("         msgseq   ：【"+msgseq+"】\r\n");
    showLog("         code     ：【"+code+"】\r\n");
    showLog("         des      ：【"+des+"】\r\n");
    showLog("         timeStamp：【"+timeStamp+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnUploadFileToMMSReport(strFileName,status,strUrl)
{
    showLog("【OnUploadFileToMMSReport】：\r\n");
    showLog("         strFileName   ：【"+strFileName+"】\r\n");
    showLog("         status        ：【"+status+"】\r\n");
    showLog("         strUrl        ：【"+strUrl+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnDownloadFileToMMSReport(strUrl,status,strFileName)
{
    showLog("【OnDownloadFileToMMSReport】：\r\n");
    showLog("         strUrl        ：【"+strUrl+"】\r\n");
    showLog("         status        ：【"+status+"】\r\n");
    showLog("         strFileName   ：【"+strFileName+"】\r\n");
    showLog(" *******************************************************************\r\n");
}

//监控事件
function onOnAgentReport(AgentReportInfo)
{
    showLog("【OnAgentReport】：\r\n param：【"+AgentReportInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnIvrReport(IvrReportInfo)
{
    showLog("【OnIvrReport】：\r\n"+IvrReportInfo+")\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnTelReport(TelReportInfo)
{
    showLog("【OnTelReport】：\r\n"+TelReportInfo+")\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnServiceReport(ServiceReportInfo)
{
    showLog("【OnServiceReport】(\r\n"+ServiceReportInfo+")\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnTaskReport(TaskReportInfo)
{
    showLog("【OnTaskReport】\r\n"+TaskReportInfo+")\r\n");
    showLog(" *******************************************************************\r\n");
}

function onOnOutboundReport(TaskInfo)
{
    showLog("【OnOutboundReport】\r\n param：【"+TaskInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");

}
function onOnCallReportInfo(CallInfo)
{
    showLog("【OnCallReportInfo】\r\n param：【"+CallInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnQueueReport(QueueInfo)
{
    showLog("【OnQueueReport】：\r\n"+QueueInfo+"\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnQueryMonitorSumReport(cmdName,reportInfo)
{
    showLog("【OnQueryMonitorSumReport】\r\n name:【"+cmdName+"】\r\n reportInfo:【"+reportInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnWallServiceReport(message)
{return;
    showLog("【OnWallServiceReport】\r\n  【"+message+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnWallQueueReport(message)
{return;
    showLog("【OnWallQueueReport】\r\n 【"+message+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnWorkStaticInfoReport(message)
{return;
    showLog("【OnWorkStaticInfoReport】 \r\n 【"+message+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function onOnQuerySPGroupList(type,ctiInfo){
	
    showLog("【onOnQuerySPGroupList】 \r\n type:【"+type+"】\r\n");
    showLog(" ctiInfo:【"+ctiInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnServiceStaticReport(StaticInfo)
{return;
    showLog("【OnServiceStaticReport】 \r\n 【"+StaticInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function  onOnAgentStaticReport(StaticInfo)
{return;
    showLog("【OnAgentStaticReport】 \r\n 【"+StaticInfo+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
//-----------------------------初始化2（OnPrompt）正在向CTI服务器注册.时走
//命令异步返回事件
function onOnMethodResponseEvent(cmdName,param)
{
	 /*var aa = [];// 创建数组
  
	if(cmdName=='QuerySPGroupList'){
		var params=param.split('$');
		for(var i=0;i<params.length;i++){
			  // alert(params[i]+"---"+i);
		      if(params[i].split('|')[0]==01){
                   aa.push(params[i]); // 添加到最后
                   aa.unshift(); // 添加到第一个位置
			  }
		}
		//显示到页面上
        if(aa!=null){
		   var html=""; 
		   html+="<select id='txtDestNum' name='styles' >";
		   for(var i=0;i<aa.length;i++){
               if(i==7){
				    html+="<option value='"+aa[i].split('|')[1]+"' selected='selected'>"+aa[i].split('|')[2]+"</option>"; 
			   }else{
				    html+="<option value='"+aa[i].split('|')[1]+"' >"+aa[i].split('|')[2]+"</option>"; 
			   
			   }
		      
		   }
		   html+="</select>";
		   $('#QuerySPGroup_List').html(html);
		}
	}else if(cmdName=='SetCallData'){
		   Transferr();
	
	}*/
	
	
    showLog("【OnMethodResponseEvent】：\r\n");
    showLog(" cmdName:【"+cmdName+"】\r\n");
    showLog(" param:【"+param+"】\r\n");
    showLog(" *******************************************************************\r\n");
   
    if (!(typeof (oMakeCallDlg) == "undefined" || oMakeCallDlg == null))
        oMakeCallDlg.Display(param);

   
}

function Transferr(){
   var transferRtn = -1;
    transferRtn = application.oJVccBar.TransferOut(2, "000001100020009999");
    if(transferRtn == 0) {
	   alert("转接成功!");
	}
}
 
function numchange(){
	  var num= $('#selType').val();
	  if(num==2){
		  application.oJVccBar.QuerySPGroupList('',-1,8,1,2,10);
	  }else{
		  var html="<input type='text' id='txtDestNum' size=\29\ >";
	      $('#QuerySPGroup_List').html(html);
	  }
	
}
function OnBeginSession(sessionId)
{
    showLog("开始微信会话：【OnBeginSession】：\r\n");
    showLog(" sessionId:【"+sessionId+"】\r\n");
    showLog(" *******************************************************************\r\n");
}
function OnEndSession(sessionId)
{
    showLog("结束微信会话：【OnEndSession】：\r\n");
    showLog(" *******************************************************************\r\n");
}
function OnRecvMessage(sessionId,msgseq)
{
    showLog("接收对方消息：【OnRecvMessage("+sessionId+","+msgseq+")】：\r\n");
    g_msgseq = msgseq;
    var oSession = application.oWechatManager.GetSessionItem(sessionId,msgseq);
    if(oSession != null){
        var strValue = "";
        if(oSession.msgType == "text"){
            strValue = oSession.content;
        }
        else{
            strValue = oSession.sessionUrl;
        }
        showLog("接收对方内容：【"+strValue+"】\r\n");
    }
    showLog(" *******************************************************************\r\n");
}
function OnSendMessageReport(sessionId,msgseq)
{
    showLog("发送消息：【OnSendMessageReport("+sessionId+","+msgseq+")】：\r\n");
    g_msgseq = msgseq;
    var oSession = application.oWechatManager.GetSessionItem(sessionId,msgseq);
    if(oSession != null){
        var strValue = "";
        if(oSession.msgType == "text"){
            strValue = oSession.content;
        }
        else{
            strValue = oSession.sessionUrl;
        }
        showLog("发送内容：【"+strValue+"】\r\n");
    }
    showLog(" *******************************************************************\r\n");
}
function OnUploadFileStatus(sessionID,status,strUrl){
    showLog("上传文件状态事件：【OnUploadFileStatus("+sessionID+","+status+","+strUrl+")】：\r\n");
    showLog(" *******************************************************************\r\n");
}

function OnDowndFileStatus(sessionID,msgseq,status,strUrl){
    showLog("下载文件状态事件：【OnDowndFileStatus("+sessionID+","+msgseq+","+status+","+strUrl+")】：\r\n");
    showLog(" *******************************************************************\r\n");

}