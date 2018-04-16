<#include 'config.ftl'/>
<#macro cssFile file=[]>
    <#list file as x><link rel="stylesheet" href="${cdnUrl}/css/${x}?${cdnFileVersion}"/></#list>
</#macro>
<#macro fontsomeCssFile file=[]>
    <#list file as x><link rel="stylesheet" href="${cdnUrl}/font-awesome/css/${x}?${cdnFileVersion}"/></#list>
</#macro>

<#macro jsFile file=[]>
    <#list file as x><script src="${cdnUrl}/js/${x}?${cdnFileVersion}"></script></#list>
</#macro>
<#macro htmlHead title="">
	<!doctype html>
	<html>
		<head>
			<meta charset="utf-8">
			<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
			<title>${title}-${systemTitle}</title>
			<@cssFile file=["bootstrap.min.css", "bootstrap-responsive.min.css","uniform.css","select2.css","matrix-media.css","bootstrap-select.css","matrix-style.css", "common.css", "activity-css.css", "jquery.treetable.css", "jquery.datetimepicker.css", "activity-css.css"] />
      		<@fontsomeCssFile file=["font-awesome.css"] />
			<@jsFile file=["jquery.min.js","jquery.ui.custom.js","flexible.js","bootstrap.min.js","jquery.uniform.js","select2.min.js","jquery.dataTables.js", "dataTables.responsive.min.js","matrix.js","matrix.tables.js","matrix.login.js","common.dialog.js","bootstrap-select.js", "core.js",  "timeEffect.js", "jquery.treetable.js", "jquery.mousewheel.min.js", "php-date-formatter.min.js", "jquery.datetimepicker.js", "activity-tip.js"] />
      		<#nested>
		</head>
		<body>
</#macro>

<#macro headerPart>
	<div id="header">
		<input type="hidden" id="absolutePathPrefix" value="${rc.contextPath}">
		<div id="danmakuArea" class="danmaku-area">
			<em class="danmaku-icon"></em>
			<div class="danmaku-list" id="danmakuListContainer">
				<ul id="danmakuList">
				</ul>
			</div>
		</div>
	  	<div id="user-nav" class="navbar navbar-inverse">
			<ul class="nav">
				<li  class="dropdown" id="profile-messages" >
					<a title="${rc.contextPath}/admin/main.do" href="" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"> <i class="icon icon-home"></i>
						<span class="text">首页</span>
					</a>
				</li>
				<li class="">
					<a title="" href="${rc.contextPath}/admin/updateUserPasswordWindow.do"> <i class="icon icon-cog"></i>
						<span class="text">修改密码</span>
					</a>
				</li>
				<li class="">
					<a title="" href="${rc.contextPath}/logout.do">
						<i class="icon icon-share-alt"></i>
						<span class="text">立即退出</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
</#macro>

<#macro topHeaderMenu>
</#macro>
<#macro sideBar >
<div id="sidebar">
	<div class="role-box">
		<div class="company">${user.companyName}</div>
	</div>
  	<ul>
	    <li><a href="${rc.contextPath}/admin/main.do"><i class="icon icon-list-alt"></i> <span>首页</span><em></em></a></li>
	    <#list categoryList as category>
	    <li class="submenu<#if category.selected> open</#if>"><a href=""><i class="icon icon-hdd"></i><span>${category.name}</span><em></em></a>
	      <ul>
	      	<#list category.menuList as menu>
	        	<li<#if menu.selected> class="menu-select"</#if>><a href="${rc.contextPath}${menu.right_url}"><span>${menu.name}</span><b></b></a></li>
	        </#list>
	      </ul>
	    </li>
	    </#list>
  	</ul>
</div>
</#macro>
<#macro loading>
	<div class="loading-window hide">
		<div class="loading-effect">
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		</div>
	</div>
</#macro>
<#macro footPart>
	<input type="hidden" id="pageIndex"/>
	<div class="row-fluid">
  		<div id="footer" class="span12"> <a href=""></a> </div>
	</div>
</#macro>
<#macro htmlFoot>
	<@loading/>
	<@commonTip/>
	</body>
	</html>
</#macro>

<#macro resultTipDialog retUrl="">
<iframe id='formCommitIframe' name='formCommitIframe' style='display: none;'></iframe>
<div id="tipDialog" class="tipDialog">
  <div class="tipInnerText">
    <span><img src="" id="tipIcon"></span>
      <span id="tipInnerText">网络异常，请稍后再试</span>
  </div>
  <div class="tipBtnBox">
    <a href="javascript:;" id="tipCancelBtn" class="btn btn-cyan" onclick="hideTipDialog('#tipDialog')">返回</a>
    <a href="javascript:;" id="tipCloseBtn" class="btn btn-green" onclick="window.opener=null;window.close();">关闭页面</a>
  </div>
</div>
</#macro>

<#macro commonTip>
	<input type="hidden" id="tipHtml" value='<@tipHtml/>'/>
	<div id="tipArea" class="tip-area"></div>
	<div class="tip-overlay" onclick="TipWindow.hide()"></div>
	<div class="overlay"></div>
</#macro>

<#macro tipHtml>
<div name="tipWindow" class="tip-window center">
	<div class="tip-content">
		<#-- <span class="tip-close-icon"><img src="${cdnUrl}/img/wap/common/tip-close.png" onclick="TipWindow.hide();" /></span> -->
		<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
		<div class="tip-title"></div>
		<div class="tip-text hide" name="tipText"></div>
		<div class="tip-button">
			<a href="javascript:;" name="tipLeftButton" class="tip-left-button btn-cyan" onclick="TipWindow.hide();">
				<div class="tip-left-button-text left" name="tipLeftButtonText">返回</div>
			</a>
			<a href="javascript:;" name="tipRightButton" class="tip-right-button btn-green" onclick="location.reload();">
				<div name="tipRightButtonText" class="tip-right-button-text right">确定</div>
			</a>
			<a href="javascript:;" name="tipSingleButton" class="tip-single-button btn-green center" onclick="TipWindow.hide();">
				<div name="tipSingleButton" class="tip-single-button-text">好的</div>
			</a>
		</div>
	</div>
</div>
</#macro>

<#macro radioButton name="" labels="" values="" defaultValue="">
	<script type="text/javascript">
		$(function() {
			$('#radio-group-${name} label').each(function(){
				$(this).click(function(){
					$('#radio-group-${name} em').removeClass('radio-checked');
					$(this).find('em').addClass('radio-checked');
				});
			});
		});
	</script>
	<div class="radio-group" id="radio-group-${name}">
	<#list values?split(",") as value>
		<label for="radio-button${value_index}" class="radio-label left" value="${value}">
			<em class="radio-icon<#if defaultValue==value> radio-checked</#if>"></em>
			<div class="radio-title"><p>${labels?split(",")[value_index]}</p></div>
			<input id="radio-button${value_index}" type="radio" name="${name}" value="${value}" <#if defaultValue==value> checked="checked"</#if>>
		</label>
	</#list>
	</div>
</#macro>

<#macro allocateInformationPopup>
<div class="common-popup-tip hide" id="commonPopupTip">
	<div class="close-icon" id="closeTipButton"><img src="${cdnUrl}/img/close-white.png" alt=""></div>
	<div class="tip-title">分配数据</div>
	<div class="tip-body">
		<div class="left-count">
			<div>总数据量：<span class="count" id="totalCount"></span></div>
			<div>未分配数据量：<span class="count" id="notAllocateCount"></span></div>
			<div>分配后剩余：<span class="count" id="leftCount"></span></div>
		</div>
		<div class="retake-count">
			<div>回收数据量：<span class="count" id="retakeTotalCount"></span></div>
		</div>
		<div class="staff-select">
			<span>筛选分配人员：</span>
			<select id="allocateStaffRole"></select>
			<input type="text" id="allocateStaff" name="" placeholder="搜索人员姓名">
		</div>
		<div class="staff-area" id="staffArea">
			<table>
				<thead>
					<tr>
						<th width="20%">人员编号</th>
						<th width="20%">姓名</th>
						<th width="20%">职位</th>
						<th width="20%">已分配</th>
						<th width="20%" id="allocateInputTitle">分配数据</th>
					</tr>
				</thead>
				<tbody id="staffContent">
					
				</tbody>
			</table>
		</div>
		<div class="submit-error" id="submitErrorContent"></div>
		<div class="submit-btn btn-green" id="submitButton">提交</div>
	</div>
</div>
<script type="text/javascript">
	var staffList = [];
	var allocateInformationTaskId = "";
	var queryParams = "";
	var selectRecordIds = "";
	var queryType = 1;
	function preAllocate(informationTaskId, params, recordIds, type) {
		allocateInformationTaskId = informationTaskId;
		queryParams = params;
		selectRecordIds = recordIds;
		var url = '${rc.contextPath}/admin/task/record/preAllocate.do';
		if (isNotEmpty(params)) {
			url += '?' + params;
		}
		var data = {};
		data['informationTaskId'] = informationTaskId;
		if (isNotEmpty(recordIds)) {
			data['recordIds'] = recordIds;
		}
		if (isNotEmpty(type)) {
			data['type'] = type;
			queryType = type;
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			url: url,
			async:false,
			data:data,
			success:function(data){
				if(data.retCode == "200"){
					staffList = data.staffList;
					if (queryType==2) {
						$('.left-count').addClass('hide');
						$('.staff-select').addClass('hide');
						$('.retake-count').removeClass('hide');
						$('#retakeTotalCount').text(data.totalCount);
						$('#allocateInputTitle').addClass('hide');
						$('#submitButton').addClass('btn-red');
						$('#submitButton').text('回收当前' + data.totalCount + '条数据');
					} else {
						$('.left-count').removeClass('hide');
						$('.staff-select').removeClass('hide');
						$('.retake-count').addClass('hide');
						if (queryType == 3) {
							$('#notAllocateCount').text(data.leftCount + '%');
							$('#totalCount').text(data.totalCount + '%');
							$('#leftCount').text(data.leftCount + '%');
						} else {
							$('#notAllocateCount').text(data.leftCount);
							$('#totalCount').text(data.totalCount);
							$('#leftCount').text(data.leftCount);
						}
						$('#allocateInputTitle').removeClass('hide');
						$('#submitButton').removeClass('btn-red');
						$('#submitButton').text('提交');
					}
					var staffContent = '';
					for (var index in data.staffList) {
						var staff = data.staffList[index];
						staffContent += '<tr name="' + staff.name + '">';
						staffContent += '<td>' + staff.staffId + '</td>';
						staffContent += '<td>' + staff.name + '</td>';
						staffContent += '<td>' + staff.roleName + '</td>';
						staffContent += '<td>' + staff.allocatedCount + '</td>';
						if (queryType != 2) {
							staffContent += '<td><input type="text" id="count_' + staff.staffId + '" placeholder="请填写数量"';
							if (queryType == 3 && staff.allocatedCount > 0) {
								staffContent += 'value="'+staff.allocatedCount+'" orginalValue="'+staff.allocatedCount+'">%';
							} else {
								staffContent += '>';
							}
							staffContent += '</td>';
						}
						staffContent += '</tr>';
					}
					$('#staffContent').empty().html(staffContent);
					$('#staffContent').find('input').each(function(){
						$(this).blur(function(){
							checkAndChangeLeftCount();
						});
					});
					if ($('#allocateStaffRole').children().length == 0) {
						var staffRoleList = '';
						for (var index in data.staffRoleList) {
							staffRoleList += '<option value="' + data.staffRoleList[index].staffRoleId + '">' + data.staffRoleList[index].name + '</option>';
						}
						$('#allocateStaffRole').html(staffRoleList);
						$('#allocateStaffRole').selectpicker('refresh');
					}
					TipWindow.showTip('#commonPopupTip');
					// $('#commonPopupTip').attr('style', 'display: block;');
				} else{
					errorCommit('#mobile', data.retDesc);
					return;
				}
			}
		});
	}
	function checkAndChangeLeftCount() {
		var totalCount = 0;
		var notAllocateCount = parseInt($('#notAllocateCount').text());
		for (var index in staffList) {
			var staff = staffList[index];
			var orginalValue = $('#count_' + staff.staffId).attr('orginalValue');
			if (isNumber(orginalValue)) {
				notAllocateCount += parseInt(orginalValue);
			}
		}
		for (var index in staffList) {
			var staff = staffList[index];
			var count = $('#count_' + staff.staffId).val();
			if (isNumber(count)) {
				totalCount += parseInt(count);
				if (totalCount > notAllocateCount) {
					$('#count_' + staff.staffId).val(notAllocateCount - totalCount + parseInt(count));
				}
			}
		}
		if (totalCount > notAllocateCount) {
			$('#submitErrorContent').text('数据量不足，请减少分配数量');
			$('#leftCount').text(0);
		} else {
			$('#submitErrorContent').text('');
			$('#leftCount').text(notAllocateCount - totalCount);
		}
		if (queryType == 3) {
			$('#leftCount').text($('#leftCount').text() + '%');
		}
	}
	$(function() {
		$('#submitButton').click(function(){
			var data = [];
			var url = "${rc.contextPath}/admin/task/record/retakeWorkOrders.do";
			if (queryType != 2) {
				url = "${rc.contextPath}/admin/task/record/allocatingWorkOrders.do";
				for (var index in staffList) {
					var staff = staffList[index];
					var count = $('#count_' + staff.staffId).val();
					if (isNumber(count)) {
						data.push({staffId: staff.staffId, count: parseInt(count), startCall: staff.startCall});
					}
				}
				if (totalCount < parseInt($('#notAllocateCount'))) {
					$('#submitErrorContent').text('数据量不足，请减少分配数量');
					return;
				}
			}
			url += "?informationTaskId=" + allocateInformationTaskId + "&type=" + queryType + "&";
			if (isNotEmpty(selectRecordIds)) {
				url += 'recordIds=' + selectRecordIds + "&";
			}
			if (isNotEmpty(queryParams)) {
				url += queryParams;
			}
			
			$.ajax({
				type:"POST",
				dataType:"json",
				contentType : "application/json",
				url: url,
				async:false,
				data:JSON.stringify(data),
				success:function(data){
					if(data.retCode == "200"){
						TipWindow.hide('#commonPopupTip');
						var tipContent = '提交成功，正在进行数据分配，请稍后...';
						if (queryType == 2) { 
							tipContent = '提交成功，正在进行数据回收，请稍后...';
						} else if (queryType == 3) {
							tipContent = '提交成功，正在进行数据分配策略调整，请稍后...';
						}
						TipWindow.showSingleWithContent(tipContent);
					} else{
						$t = 0;
						errorCommit('#mobile', data.retDesc, 2);
						return;
					}
				}
			});
		});
		$('#closeTipButton').click(function(){
			TipWindow.hide('#commonPopupTip');
		});
		$('#allocateStaffRole').change(function(){
			preAllocate(allocateInformationTaskId, 'staffRoleId=' + $('#allocateStaffRole').val() + '&', null, queryType);
		});
		$('#allocateStaff').bind('input propertychange', function(){
			$('#staffContent').find('tr').each(function(){
				var value = $(this).attr('name');
				var search = $('#allocateStaff').val();
				if (isEmpty(search) || value.indexOf(search)>-1) {
					$(this).removeClass('hide');
				} else {
					$(this).addClass('hide');
				}
			});
		});
	});
</script>
</#macro>

<#macro timeRangeSearchBar startId="createTimeStart" endId="createTimeEnd" hasTime="false">
<input type="text" id="${startId}" /><h6>到</h6>
<input type="text" id="${endId}"/>
<script>
	$(function() {
		timer('#${endId}');
		jQuery('#${endId}').datetimepicker({
			minDate:0,
			onShow:function( ct ){
				this.setOptions({
			minDate:jQuery('#${startId}').val()?jQuery('#${startId}').val():false
				})
			},
			<#if hasTime=="false">
			format:'Y-m-d',
			timepicker:false
			<#else>
			format:'Y-m-d H:i'
			</#if>
		});
		timer('#${startId}');
		jQuery('#${startId}').datetimepicker({
			maxDate:0,
			onShow:function( ct ){
				this.setOptions({
			maxDate:jQuery('#${endId}').val()?jQuery('#${endId}').val():false
				})
			},
			<#if hasTime=="false">
			format:'Y-m-d',
			timepicker:false
			<#else>
			format:'Y-m-d H:i'
			</#if>
		});
	});
</script>
</#macro>