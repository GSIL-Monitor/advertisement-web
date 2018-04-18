<@headerPart />
<div id="content-header">
    <div id="breadcrumb">
    	<a href="javascript:;" onclick="returnToList()" title="返回列表" class="tip-bottom"><i class="icon-book"></i>返回列表</a>
    	<div class="fr clearfix">
    		<ul class="call-list fr">
    			<a href="javascript:;" onclick="call('${itemEdit.mobile}')">
    			<li>
    				<span id="callButton" class="start act"></span>
    				<p>拨打</p>
    			</li>
    			</a>
    			<a href="javascript:;" onclick="hold()">
    			<li>
    				<span id="holdButton" class="pause"></span>
    				<p>暂停</p>
    			</li>
    			</a>
    			<a href="javascript:;" onclick="disconnect()">
    			<li>
    				<span id="disconnectButton" class="stop"></span>
    				<p>挂机</p>
    			</li>
    			</a>
    		</ul>
    		<div class="call-time fr">
    			<i></i>
    			<span class="state"><a id="callStatus" href="#"></a></span>
    			<#-- <span class="time">02'52''</span> -->
    		</div>
    	</div>
    </div>
</div>
	<div class="container-fluid">
    <div class="clearfix">
    	<div class="row-left fl">
    		<div class="widget-box">
	          	<div class="widget-title clearfix"></span>
	            	<h5 class="fl">客户资料</h5><div class="title-text fr">保障等级：<span>${itemEdit.scoreLevel}</span></div><div class="title-text fr">综合评分：<span>${itemEdit.score}</span></div>
	          	</div>
		        <div class="widget-content nopadding">
		        	<div class="base-info clearfix">
		        		<div class="portrait fl">
		        			<div class="info-block clearfix">
		        				<div class="avatar fl"><img src="${cdnUrl}/ms/html/img/page/detailWorkOrder/avatar.png" alt=""></div>
		        				<div class="info fl">
		        					<div class="name">${itemEdit.informationInsurance.name}</div>
		        					<div class="gender">
		        						<span>${itemEdit.informationInsurance.genderValue}</span>/
		        						<span>${itemEdit.informationInsurance.age}</span>/
		        						<span>${itemEdit.informationInsurance.userLocation}</span>
		        					</div>
		        					<div class="tel"><span>${itemEdit.informationInsurance.mobile}</span><em></em></div>
		        				</div>
		        			</div>
	        				<ul class="tag-list clearfix">
        						<#list itemEdit.userTagsVoList as tag>
									<li>${ tag.name }</li>
        						</#list>
        					</ul>
		        		</div>
						<div class="radar fl" id="radar">
							<div class="zongfen">${itemEdit.score}</div>
							<input type="hidden" id="workOrderId" value="${itemEdit.workOrderId?c}">
							<div class="radar-wrap">
								<div class="radar-label ccqk">财产情况</div>
								<div class="radar-label zy">职业</div>
								<div class="radar-label blxg">不良习惯</div>
								<div class="radar-label jtcy">家庭成员</div>
								<div class="radar-label nsr">年收入</div>
								<div id="radarContainer"></div>
							</div>
							<div class="progress-box clearfix">
								<div class="title fl">保险需求指数：</div>
								<div class="progress fr">
									<div class="bar" style="width: ${ itemEdit.score / 10}%;"></div>
									<div class="lb" style="left: ${ itemEdit.score / 10}%">${ itemEdit.score}</div>
									<ul class="kedu clearfix">
										<li class="fl <#if (itemEdit.score > 168.77637)>act</#if>"></li>
										<li class="fl <#if (itemEdit.score > 208.15752)>act</#if>"></li>
										<li class="fl <#if (itemEdit.score > 399.43741)>act</#if>"></li>
										<li class="fl <#if (itemEdit.score > 590.71729)>act</#if>"></li>
										<li class="fl <#if (itemEdit.score > 781.99718)>act</#if>"></li>
										<li class="fl <#if (itemEdit.score > 973.27707)>act</#if>"></li>
									</ul>
									<ul class="biaozhu clearfix">
										<li class="fl">危险</li>
										<li class="fl">很差</li>
										<li class="fl">一般</li>
										<li class="fl">及格</li>
										<li class="fl">优秀</li>
										<li class="fl">极好</li>
									</ul>	
								</div>
							</div>
						</div>
		        	</div>
		        </div>
	        </div>
    	</div>
       	<div class="row-right fr">
       		<div class="widget-box">
	          	<div class="widget-title clearfix"></span>
	            	<h5 class="sjly">数据来源：<span>${itemEdit.informationInsurance.channel.name}</span></h5>
	            	<h5 class="hdzt">活动主题：<span>${itemEdit.informationInsurance.channel.activityDescription}</span></h5>
	          	</div>
		        <div class="widget-content nopadding">
		        	<div class="process" id="procedureList">
		        		<div class="title">获客流程：</div>
		        		<ul class="process-list clearfix">
		        			<#list itemEdit.procedureList as process>
								<li class="fl">
									<div class="img">
										<img src="${process.imageUrl}" alt="">
									</div>
									<div class="step">步骤<span>${process_index + 1}</span><em class="fr"></em></div>
									<div class="desc">${process.description}</div>
								</li>
		        			</#list>
		        		</ul>
		        	</div>
		        </div>
	        </div>
       	</div>
    </div>
    <div class="row-fluid second-row">
	    <div class="clearfix">
	    	<div class="row-left fl">
	    		<div class="widget-box">
			        <div class="widget-content nopadding">
			        	<div class="note-box clearfix">
			        		<form action="${rc.contextPath}/admin/staff/workorder/remark.do" method="post" name="form" enctype="multipart/form-data" target="remarkIframe">
				        		<div class="note-area fl">
				        			<div class="title">备注：</div>
				        			<textarea name="content" row="5">${itemEdit.latestRemark}</textarea>
				        		</div>
				        		<div class="posi fl">
				        			<div class="title">结束码：</div>
				        			<div>
										<select name="workOrderStatus" class="selectpicker form-control">
											<#list statusList as status>
					                        <option value="${status.key}" <#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
					                        </#list>
					                    </select>
				        			</div>
				        			<div class="title">下次回拨时间：</div>
				        			<div class="next-call-time">
				        				<input type="text" name="callBackTime" id="nextCallTime">
										<script>
											$(function() {
												var date = new Date();
												date.setHours(date.getHours() + 2);
												jQuery.datetimepicker.setLocale('zh');
												jQuery('#nextCallTime').datetimepicker({
													value: date,
												  	format:'Y-m-d H:i',
												  	minDate: 0,
												  	maxDate:'+1970/01/08',
												  	defaultDate: date,
												  	yearStart: 2018,
												  	yearEnd: 20
												});
											});
										</script>
				        			</div>
				        			<input type="hidden" name="workOrderId" value="${itemEdit.workOrderId?c}">
				        			<input type="submit" class="save" value=""/>
				        			<div class="next" id="nextWorkOrder" onclick="goDetail($('#nextWorkOrderId').val())">
				        				<input type="hidden" id="nextWorkOrderId" value="${nextWorkOrderId}">
				        			</div>
				        		</div>
				        	</form>
			        	</div>
			        </div>
		        </div>
	    	</div>
	       	<div class="row-right fr">
		        <div class="widget-box product-widget-box">
		          	<div class="widget-title"></span>
		            	<h5>推荐产品</h5><p class="btn-open" id="openProduct"><span>展开</span><em></em></p>
		          	</div>
			        <div class="widget-content nopadding chanpin">
			        	<ul class="product-list">
			        		<#list itemEdit.workOrderRecommendList as item>
			        		<li class="product">
			        			<div class="img fl"><img src="${item.product.workOrderImageUrl}" alt=""></div>
			        			<div class="fl title">
			        				<div class="name">${item.product.name} | <span>成功率：${item.successRateValue}</span></div>
			        				<ul class="tags clearfix">
			        					<#list item.product.featureTagsList as tag>
			        					<li class="fl">${tag.name}</li>
			        					</#list>
			        				</ul>
			        			</div>
			        			<div class="fr cesuan"></div>
			        		</li>
			        		</#list>
			        	</ul>
			        </div>
		        </div>
	       	</div>
	    </div>
	</div>
    <div class="row-fluid user-box" id="userBox">
    	<div class="user-btn" id="userBtn"></div>
        <div class="widget-box">
          	<div class="widget-title"></span>
            	<h5>用户详细信息</h5>
          	</div>
	        <div class="widget-content nopadding">
	        	<form action="${rc.contextPath}/admin/staff/workorder/information/add.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
	        	<#list itemEdit.userDetailStepList as item>
	        		<div class="info-title"><span class="vl"></span>${item.name}</div>
			            <ul class="info-area clearfix">
							<#list item.fieldList as field>
								<li class="fl">
									<span class="field-name">${field.name}</span>
									<#if field.type == 1>
									<input class="input-area" name="${field.fieldName}" type="text" placeholder="${field.placeholder}" value="${field.defaultValue}">
									<#elseif field.type == 2>
									<input class="input-area" name="${field.fieldName}" type="number" placeholder="${field.placeholder}" value="${field.defaultValue}">
									<#elseif field.type == 4>
									<input class="input-area" name="${field.fieldName}" id="${field.fieldName}" type="text" placeholder="${field.placeholder}" value="${field.defaultValue}">
									<script>
										jQuery('#' + '${field.fieldName}').datetimepicker({
										  	format:'Y-m-d',
										  	maxDate: 0,
										  	value: new Date(),
										  	defaultDate: new Date(),
										  	yearStart: 1950,
										  	timepicker: false
										});
									</script>
									<#elseif field.type == 6>
									<select class="selectpicker form-control" name="${field.fieldName}">
										<option value="">请选择${field.placeholder!""}</option>
										<#list field.valueFieldTags as tag>
				                        <option value="${tag.tagsId}" <#if tag.hasSelected>selected="selected"</#if>>${tag.name}</option>
				                        </#list>
				                    </select>
									</#if>
								</li>
							</#list>
			            </ul>
	            </#list>
	            	<input type="hidden" name="workOrderId" value="${itemEdit.workOrderId?c}">
	            	<input type="hidden" name="projectId" value="${itemEdit.projectId?c}">
	            	<input type="hidden" name="informationId" value="${itemEdit.informationId?c}">
	            	<input type="submit" class="submit-userinfo" value="提交" onclick="checkResult();">
			    </form>
	        </div>
        </div>
    </div>
</div>
<input type="hidden" id="currentCallRecordId">
<div class="overlay" id="telRecordMask"></div>
<div class="tel-record-tip" id="telRecordTip">
	<div class="tel-record-btn" id="telRecordBtn"></div>
	<div class="main">
		<ul class="nav clearfix" id="telRecordNav">
			<li class="fl act">通话记录</li>
			<li class="fl">备注记录</li>
			<li class="fl">评价记录</li>
			<li class="fl">状态变更</li>
		</ul>
		<div class="cont">
			<ul id="telRecordCont">
				<li id="slide0">
					<table class="call-list">
						<thead>
							<tr>
								<th>时间</th>
								<th>通话类型</th>
								<th>坐席</th>
								<th>通话结果</th>
								<th>通话时长</th>
							</tr>
						</thead>
						<tbody>
							<#if itemEdit.callRecordList?? && (itemEdit.callRecordList?size>0)>
							<#list itemEdit.callRecordList as call>
								<tr>
									<td>${call.startTime}</td>
									<td>${call.callType}</td>
									<td>${call.staff.name}</td>
									<td>${call.statusValue}</td>
									<td><span class="call-time">${call.callTime}</span><span class="audio-btn"></span><audio src="${call.callRecordUrl}">您的浏览器不支持audio，建议使用Chrome。</audio></td>
								</tr>
							</#list>
							<#else>
								<tr><td colspan="5">无通话记录</td></tr>
							</#if>
						</tbody>
					</table>
				</li>
				<li class="hide" id="slide1">
					<ul class="note-list">
						<#if itemEdit.remarkList?? && (itemEdit.remarkList?size>0)>
						<#list itemEdit.remarkList as remark>
							<li>
								<p><span>${remark.staff.name}:</span>${remark.content}</p>
								<p>${remark.createTimeContent}</p>
							</li>
						</#list>
						<#else>
							<li>
								无备注记录
							</li>
						</#if>
					</ul>
				</li>
				<li class="hide" id="slide2">
					<ul class="comment-list">
						<#if itemEdit.commentList?? && (itemEdit.commentList?size>0)>
						<#list itemEdit.commentList as comment>
							<li>
								<div class="head clearfix">
									<span class="time">${comment.createTimeContent}</span>
									<span class="name">${comment.staff.name}</span>
									<span class="score fr">${comment.averageScore}</span>
									<span class="fr">综合评分：</span>
								</div>
								<div class="body">
									<p class="user clearfix"><span class="fl">【用户评价信息】</span><span class="fr">${comment.userSatisfactionLevelValue}</span></p>
									<p class="user-tags">${comment.userTagsContent}</p>
									<p class="user-info">${comment.userInfoEvaluate}</p>
									<p class="order clearfix"><span class="fl">【订单信息评价】</span><span class="fr">${comment.orderSatisfactionLevelValue}</span></p>
									<p class="order-tags">${comment.orderTagsContent}</p>
									<p class="order-info">${comment.orderInfoEvaluate}</p>
								</div>
							</li>
						</#list>
						<#else>
							<li><p>无评价记录</p></li>
						</#if>
					</ul>
				</li>
				<li class="hide" id="slide3">
					<table class="call-list">
						<thead>
							<tr>
								<th>时间</th>
								<th>坐席</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<#if itemEdit.stateList?? && (itemEdit.stateList?size>0)>
							<#list itemEdit.stateList as state>
								<tr>
									<td>${state.createTimeContent}</td>
									<td>${state.staff.name}</td>
									<td>${state.updateStateValue}</td>
								</tr>
							</#list>
							<#else>
								<tr><td colspan="3">无状态变更记录</td></tr>
							</#if>
						</tbody>
					</table>
				</li>
			</ul>
		</div>
	</div>
</div>
<div id="procedureStepTip">
	<ul class="process-list clearfix">
		<#list itemEdit.procedureList as process>
			<li class="">
				<div class="step-img">
					<img src="${process.imageUrl}" alt="">
				</div>
				<div class="step">步骤<span>${process_index + 1}</span><em class="fr"></em></div>
				<div class="desc">${process.description}</div>
			</li>
		</#list>
	</ul>
	<div class="close-process" id="closeProcess"></div>
</div>
<script>
	var myChart = document.getElementById('radarContainer');

	var myChart = echarts.init(myChart);
	var jsonUrl = '/ms/admin/staff/workorder/radar.json?workOrderId=' + $('#workOrderId').val();
	$.get(jsonUrl).done(function(data) {
		if(!data) {
			data = [0, 0, 0, 0, 0];
		}
	    myChart.setOption({
		    polar: [
		        {
		        	name: {
		        		show: true,
		        		formmatter: null,
		        		textStyle: {
		        			color: '#ff0000'
		        		}
		        	},
		            indicator: [
		                { text: '财产情况', max: 200 },
		                { text: '年收入', max: 200 },
		                { text: '家庭成员' , max: 200},
		                { text: '不良习惯' ,max: 200},
		                { text: '职业' , max: 200}
		            ],
		            startAngle: 90,
		            splitNumber: 3,
		            radius: '100%',
		            splitArea: {
		                areaStyle: {
		                     color: ['rgba(36,198,200, 0.54)', 'rgba(36,198,200, 0.41)',
		                    'rgba(36,198,200, 0.18)'],
		                    shadowColor: 'rgba(0, 0, 0, 0)',
		                    shadowBlur: 0
		                }
		            },
		            axisLine: {
		                lineStyle: {
		                    color: 'rgba(36,198,200, 0)'
		                },
		                textStyle: {
		                	color: '#666'
		                }
		            },
		            splitLine: {
		                lineStyle: {
		                    color: 'rgba(36,198,200, 0)'
		                }
		            }
		        }
		    ],
		    series: [
		        {
		            type: 'radar',
		            itemStyle : {  
	                    normal : {  
	                        color: 'rgba(36,198,200, 0.72)',
	                        lineStyle: {  
	                            color: 'rgba(36,198,200, 0)'
	                        }  
	                    }  
	                },  
		            data: [
		                {
		                    value: data,
		                    areaStyle: {
		                        normal: {
		                            color: 'rgba(36,198,200, 0.72)'
		                        }
		                    }
		                }
		            ]
		        }
		    ]
	    });
	});

	setTimeout(function (){
	    window.onresize = function () {
	        myChart.resize();
	    }
	},200)

	$('.selectpicker').selectpicker('refresh');

	$('#procedureList').click(function() {
		$('body').addClass('modal-open');
		$('#procedureStepTip').css('display', 'block');
	})

	$('#closeProcess').click(function() {
		$('body').removeClass('modal-open');
		$('#procedureStepTip').css('display', 'none');
	})

	$('#openProduct').click(function() {
		if($(this).parent().next('.widget-content').hasClass('open-widget-content')) {
			$(this).children('span').text('展开');
			$(this).parent().next('.widget-content').removeClass('open-widget-content');
			$(this).parent().parent().css('z-index', 'auto');
		} else {
			$('.widget-box').css('z-index', 'auto');
			$('.widget-content').removeClass('open-widget-content');
			$(this).children('span').text('收起');
			$(this).parent().next('.widget-content').addClass('open-widget-content');
			$(this).parent().parent().css('z-index', 11);
		}
	});

	$('#telRecordBtn').click(function() {
		if($('#telRecordBtn').hasClass('open')) {
			$('#telRecordMask').removeClass('fadein');
			$('body').removeClass('modal-open');
			$('#telRecordBtn').removeClass('open');
			$('#telRecordTip').removeClass('show-tel-record-tip');
		} else {
			$('#telRecordBtn').addClass('open');
			$('#telRecordMask').addClass('fadein');
			$('body').addClass('modal-open');
			$('#telRecordTip').addClass('show-tel-record-tip');
			$('.btn-open').children('span').text('展开');
			$('.widget-content').removeClass('open-widget-content');
			$('.widget-box').css('z-index', 'auto');
		}
	});

	$('#userBtn').click(function() {
		if($('#userBtn').hasClass('info-up')) {
			$('#userBtn').removeClass('info-up');
			$('#userBox').removeClass('user-box-up');
		} else {
			$('#userBtn').addClass('info-up');
			$('#userBox').addClass('user-box-up');
			$('.btn-open').children('span').text('展开');
			$('.widget-content').removeClass('open-widget-content');
			$('.widget-box').css('z-index', 'auto');
		}
	});

	$('#telRecordNav li').each(function(index) {
		$(this).click(function() {
			$('#telRecordNav li').removeClass('act');
			$(this).addClass('act');
			$('#telRecordCont > li').addClass('hide');
			$('#slide'+index).removeClass('hide');
		})
	});

	$('.audio-btn').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('play')) {
				$(this).next('audio').get(0).pause();
				$(this).removeClass('play');
			} else {
				document.addEventListener('play', function(e){
				    var audios = document.getElementsByTagName('audio');
				    for(var i = 0, len = audios.length; i < len;i++){
				        if(audios[i] != e.target){
				            audios[i].pause();
				        }
				    }
				}, true);

				$('.audio-btn').removeClass('play');
				$(this).next('audio').get(0).play();
				$(this).addClass('play');
			}
		});
	});

	$('#noteBtn').click(function() {
		$('#noteTip').css('display', 'block');
		$('#noteBtn').css('display', 'none');
	});

	$('#minimize').click(function() {
		$('#noteBtn').css('display', 'block');
		$('#noteTip').css('display', 'none');
	});

	$('#submitNote').click(function() {
		$.ajax({
            type:"POST",
            dataType:"json",
            url: "/ms/admin/staff/workorder/remark.do",
            async:false,
            data:{
                
            },
            success:function(data){
                if(data.retCode === "200"){
                    popWindow.showPopTipWithContent('#okTip', content);
                } else{
                    $t = 0;
                    errorCommit('#mobile', data.retDesc, 2);
                    return;
                }
            }
        });
	});

	$('#userStarList li').each(function(index) {
		$(this).click(function() {
			$('#userStarList li').removeClass('stared');
			var clickedStarIndex = index;
			$('#userStarList li').each(function(index) {
				if(index <= clickedStarIndex) {
					$(this).addClass('stared');
					$('#userStarVal').val(clickedStarIndex + 1);
				}
			})
		})
	});

	$('#orderStarList li').each(function(index) {
		$(this).click(function() {
			$('#orderStarList li').removeClass('stared');
			var clickedStarIndex = index;
			$('#orderStarList li').each(function(index) {
				if(index <= clickedStarIndex) {
					$(this).addClass('stared');
					$('#orderStarVal').val(clickedStarIndex + 1);
				}
			})
		})
	});

	var selectedUserTagArr = [];
	$('#userTagList li').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				selectedUserTagArr.splice($.inArray($(this).attr('index'), selectedUserTagArr),1);
			} else {
				$(this).addClass('selected');
				selectedUserTagArr.push($(this).attr('index'));
			}

			var userTagsStr = selectedUserTagArr.join(',');
			$('#userTagsVal').val(userTagsStr);
		});
	});

	var selectedOrderTagArr = [];
	$('#orderTagList li').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				selectedOrderTagArr.splice($.inArray($(this).attr('index'), selectedOrderTagArr),1);
			} else {
				$(this).addClass('selected');
				selectedOrderTagArr.push($(this).attr('index'));
			}

			var orderTagsStr = selectedOrderTagArr.join(',');
			$('#orderTagsVal').val(orderTagsStr);
		});
	});
</script>