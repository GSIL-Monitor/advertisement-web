<#include "../../../common/core.ftl" />
<#assign functionName="staff/workorder"/>
<#assign functionTitle="工单">
<#assign functionId="workOrderId"/>

<#macro callComponent>
<input type="hidden" id="callCenterId" value="${project.callCenterId}">
<input type="hidden" id="callAgentId" value="${staff.callAgentId}">
<script type="text/javascript">
	$(function(){
		applicationLoad(50,10,1200,66,"","${cdnUrl}/js/page/call/", setVccBarEvent2);
	});
	function call(mobile){
		$.ajax({
			type: 'POST',
			url: "${rc.contextPath}/admin/staff/workorder/startCall.do?workOrderId=" + $('#workOrderId').val(),
			data: {},
			success:function(data,status){
				if (data.retCode == 200) {
					$('#currentCallRecordId').val(data.callRecord.callRecordId);
					application.oJVccBar.MakeCall(mobile);
					$('#callButton').removeClass('act');
					$('#disconnectButton').addClass('act');
					$('#holdButton').addClass('act');
				}
			},
			error : function(data) {
				//...
				console.log("query error...");
			}
		});
	}
	function disconnect() {
		application.oJVccBar.Disconnect();
	}
	function hold() {
		application.oJVccBar.Hold();
	}
	function setVccBarEvent2(){ 
		funInitial();
		application.oJVccBar.OnCallRing = onOnCallRing;
		application.oJVccBar.AnswerCall = onOnAnswerCall;
		application.oJVccBar.OnCallEnd = onOnCallEnd;
		application.oJVccBar.OnMethodResponseEvent = onOnMethodResponseEvent;
		application.oJVccBar.OnCallDataChanged = onOnCallDataChanged;
		application.oJVccBar.OnQuerySPGroupList = onOnQuerySPGroupList;
		displayM(1); 
	}

	function displayM(flag){//alert(application.oJMonitor.Show);
		// application.oJMonitor.Display(flag);
		_display = flag;
		if(_display == 0){
			btnUnShow.disabled = true;
			btnShow.disabled = false;
			divInput.style.display = "block";
			divMonitor.style.display = "block";
			divTextInfo.style.top = "130px";
			application.oJMonitor.SetAppMode(2);
		}
		else{
			btnUnShow.disabled = false;
			btnShow.disabled = true;
			divInput.style.display = "none";
			divMonitor.style.display = "none";
			divTextInfo.style.top = "95px";
			application.oJMonitor.SetAppMode(1);
		}
	}

	function funInitial() {
		var serverIP ="101.201.142.12" ;
		var SipServerIP="101.201.142.12";
		var vccID ="100160" ;		   
		var agentID ="1001" ;
		var sipPort = "5066";
		if (isNotEmpty($('#callCenterId').val())) {
			vccID = $('#callCenterId').val();
		}
		if (isNotEmpty($('#callAgentId').val())) {
			agentID = $('#callAgentId').val();
		}
	
		application.oJVccBar.SetAttribute("MainIP",serverIP);  //MainIP
		application.oJVccBar.SetAttribute("MainPortID",14800);  //MainPort
		application.oJVccBar.SetAttribute("BackIP",serverIP);  //BackIP
		application.oJVccBar.SetAttribute("BackPortID",14800);  //BackPort
		application.oJVccBar.SetAttribute("MonitorIP",serverIP);  //MonitorIP
		application.oJVccBar.SetAttribute("MonitorPort",4502);  //MainPort
		application.oJVccBar.SetAttribute("SipServerIP",SipServerIP);
		 //所在域
	  	//application.oJVccBar.SetAttribute("SipDomain",sipDomain);
		application.oJVccBar.SetAttribute("SipServerPort",parseInt(sipPort));
		application.oJVccBar.SetAttribute("SipProtocol","UDP"); 
		application.oJVccBar.SetAttribute("PhonType",1);  //0:内置坐席卡；1：内置Sip；2：外置其他终端
		application.oJVccBar.SetAttribute("AgentType",0);
		application.oJVccBar.SetAttribute("SelfPrompt",1);
		application.oJVccBar.SetAttribute("MediaFlag",vccID);
		application.oJVccBar.SetAttribute("AppType",0);
		application.oJVccBar.SetAttribute("PassWord","111111");
		application.oJVccBar.SetAttribute("AgentID","000010"+vccID+agentID);
		application.oJVccBar.SetAttribute("Dn","000002"+vccID+agentID);
		application.oJVccBar.SetAttribute("SipPassWord", "00000000");
  
		application.oJVccBar.SetAttribute("SipAuthType", 1);
		//application.oJVccBar.SetAttribute("TaskID",'000001'+vccID+'11160918');
		//初始化完成后显示的按钮
		application.oJVccBar.SerialBtn("0,1,2,3,6,9,12","12");
		application.oJVccBar.Initial();
	}
</script>
</#macro>
