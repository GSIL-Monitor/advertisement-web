
<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">${functionTitle}详情</a>
		</div>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"><i class="icon-th"></i>
							</span>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td>用户ID：</td>
										<td>【${itemEdit.userId?if_exists}】</td>
									</tr>
									<tr>
										<td>消息标题：</td>
										<td>${itemEdit.title}</td>
									</tr>
									<tr>
										<td>消息内容：</td>
										<td>${itemEdit.content}</td>
									</tr>
									<tr>
										<td>跳转地址：</td>
										<td>${itemEdit.url}</td>
									</tr>
									<tr>
										<td>过期时间：</td>
										<td>${itemEdit.expireTime}</td>
									</tr>
									<tr>
										<td>是否简洁信息：</td>
										<td>${itemEdit.isSimpleStr}</td>
									</tr>
									<tr>
										<td>是否离线发送：</td>
										<td>${itemEdit.isOfflineStr}</td>
									</tr>
									<tr>
										<td>发送平台：</td>
										<td>${itemEdit.clientStr}</td>
									</tr>
									<tr>
										<td>状态：</td>
										<td>${itemEdit.statusStr}</td>
									</tr>
									<tr>
										<td>创建时间：</td>
										<td>${itemEdit.createTimeContent}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	</div>
</div>
<@footPart />
<@htmlFoot />