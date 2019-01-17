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
      <a href="#" class="current">${functionTitle}添加渠道</a>
    </div>
    <h1>添加${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/allocateChannel.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <span class="icon"> <i class="icon-th"></i>
              </span>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered table-striped" id="">
                <tbody>
					<input type="hidden" name="activityId" style="width:60%;" value="${activityId}">                
                  <tr>
                    <td style="width:20%;">活动名称：</td>
                    <td>
                      <input type="text" name="" style="width:60%;" readonly="readonly" value="${activityName}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td style="width:20%;">渠道名称：</td>
                    <td>
                      	<div style="width:60%;">
							<select name="channelId" id="channelId" class="selectpicker form-control" data-live-search="true">
								<#list channelList as channel>
									<option value="${channel.channelId}">${channel.key}</option>
								</#list>
							</select>
						</div>
                    </td>
                  </tr>
                  <tr>
                    <td style="width:20%;">是否需要独立配置：</td>
                    <td>
                      	<div style="width:60%;">
							<select name="independent" id="independent" class="selectpicker form-control">
								<#list independentList as independent>
									<option value="${independent.key}">${independent.value}</option>
								</#list>
							</select>
						</div>
                    </td>
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

<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />