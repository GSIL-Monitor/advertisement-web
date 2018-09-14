<#include "core.ftl" />
<@htmlHead title="修改活动"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
      ${functionTitle}管理
      </a>
      <a href="#" class="current">修改${functionTitle}</a>
    </div>
    <h1>修改${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/updateProbability.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="probabilityId" value="${itemEdit.probabilityId?c}"/>
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
                    <td style="width:20%;">活动名称：</td>
                    <td>
                      <input type="text" name="" disabled="disabled" style="width:60%;" value="${activity.name}">
                    </td>
                  </tr>
                  <tr>
                    <td style="width:20%;">广告：</td>
                    <td>
                      <input type="text" name="" disabled="disabled" style="width:60%;" value="${advertisement.advertisementId}${advertisement.title}">
                    </td>
                  </tr>
                  <tr>
                    <td style="width:20%;">概率：</td>
                    <td>
                      <input type="text" name="probability" style="width:60%;" value="${itemEdit.probability}">
                    </td>
                  </tr>
                  
                 <tr>
					<td>开始时间：</td>
					<td>
						<input type="text" name="startTimeValue" id="startTimeValue" style="width:60%;" value="${itemEdit.startTime}"></td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<input type="text" name="endTimeValue" id="endTimeValue" style="width:60%;" value="${itemEdit.endTime}"></td>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/activity/list.do" />
<@footPart />
<@htmlFoot />