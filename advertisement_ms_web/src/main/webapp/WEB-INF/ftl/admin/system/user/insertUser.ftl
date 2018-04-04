<#include "/common/core.ftl" />
<@htmlHead title="用户管理"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
			<a href="${rc.contextPath}/admin/user/adminUsers.do" title="系统管理" class="tip-bottom">用户管理</a>
			<a href="#" class="current">新增用户</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/user/insertUser.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td style="width:30%;">用户名：</td>
										<td>
											<input type="text" name="username" style="width:60%;"></td>
									</tr>
									<tr>
										<td>密码：</td>
										<td>
											<input type="text" id="passwordStr" name="password" style="width:60%;"><button onclick="createPassword()" class="btn btn-cyan" style="margin-left: 10px;">生成密码</button></td>
									</tr>
									<tr>
										<td>邮箱地址：</td>
										<td>
											<input type="text" name="email" style="width:60%;"></td>
									</tr>
									<tr>
										<td>备注：</td>
										<td>
											<input type="text" name="remark" style="width:60%;"></td>
									</tr>
									<tr>
										<td>真实姓名：</td>
										<td>
											<input type="text" name="name" style="width:60%;"></td>
									</tr>
									<tr>
										<td>所属项目：</td>
										<td>
											<div style="width:62%;">
												<select name="projectId" id="projectId" class="selectpicker form-control">
												<#list projectList as project>
													<option value="${project.projectId?c}">${project.name}</option>
												</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>公司id（选填）：</td>
										<td>
											<input type="text" name="bindCompanyId" style="width:60%;"></td>
									</tr>
									<input type="hidden" name="user_level" value="3"/>
									<tr>
										<td>密码有效期：</td>
										<td>
											<input type="text" name="pwd_keep_time" style="width:60%;"></td>
									</tr>
									<tr>
										<td>首次登陆：</td>
										<td><input type="checkbox" id="first_login_force" name="first_login_force" value="1" />&nbsp;首次登陆要求修改密码</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/user/adminUsers.do" />
<@footPart />
<script>
	function createPassword() {
		var seed = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
										'a','b','c','d','e','f','g','h','i','j','k','m','n','p','Q','r','s','t','u','v','w','x','y','z',
										'2','3','4','5','6','7','8','9');//数组
				seedLength = seed.length;//数组长度
				var randomPassword = '';
				for (i = 0; i < 8; i++) {
					j = Math.floor(Math.random()*seedLength);
				randomPassword += seed[j];
			}
			$("#passwordStr").attr("value",randomPassword);
	}
</script>
<@htmlFoot />