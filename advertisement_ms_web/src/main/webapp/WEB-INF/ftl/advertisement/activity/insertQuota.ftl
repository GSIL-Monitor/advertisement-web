<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}" />
<@headerPart />
<@topHeaderMenu />
<@sideBar />

<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
    </div>
    <h1>添加${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/insertQuota.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <span class="icon"> <i class="icon-th"></i>
              </span>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered table-striped" id="">
                <tbody>
                	<input type="hidden" name="probabilityId" value="${probabilityId}" readonly="readonly" style="width:60%;">
                	<input type="hidden" name="quotaId" value="${quota.quotaId}" readonly="readonly" style="width:60%;">
                	<tr>
						<td>活动：</td>
						<td>
							<input type="text" name="activityId" value="${activityId}" readonly="readonly" style="width:60%;">
						</td>
					</tr>
					<tr>
						<td>渠道：</td>
						<td>
							<input type="text" name="channel" value="${channel}" readonly="readonly" style="width:60%;">
						</td>
					</tr>
					<tr>
						<td>奖品：</td>
						<td>
							<input type="text" name="advertisementId" value="${advertisementId?c}" readonly="readonly" style="width:60%;">
						</td>
					</tr>
                	<tr>
						<td>数量：</td>
						<td>
							<input type="text" name="count" style="width:60%;" value="${quota.count?c}">
						</td>
					</tr>
					<tr>
						<td>单价：</td>
						<td>
							<input type="text" name="unitPrice" style="width:60%;" value="${quota.unitPrice}">
						</td>
					</tr>
					<!--
					<tr>
						<td>每日最高限额：</td>
						<td>
							<input type="text" name="count" style="width:60%;">
						</td>
					</tr>
					<tr>
						<td>广告总预算：</td>
						<td>
							<input type="text" name="count" style="width:60%;">
						</td>
					</tr>
					 -->
					<tr>
						<td>收費方式：</td>
						<td>
							<div style="width:60%;">
								<select name="quotaType" class="selectpicker form-control">
									<#list quotaTypeList as type>
										<option value="${type.key}">${type.value}</option>
									</#list>
								</select>
							</div>
						</td>
					</tr>
					
					<!-- 
					<tr>
						<td>状态：</td>
						<td>
							<div style="width:60%;">
								<select name="status" class="selectpicker form-control">
									<#list statusList as status>
										<option value="${status.key}">${status.value}</option>
									</#list>
								</select>
							</div>
						</td>
					</tr>
					-->
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

<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />