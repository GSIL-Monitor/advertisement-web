<#include "core.ftl" />
<@htmlHead title="修改${itemEdit.activity.name}${functionTitle}"/>
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
    <h1>修改${itemEdit.activity.name}${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.bannerId?c}"/>
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
						<td>活动：</td>
						<td>
							【${itemEdit.activityId}】${itemEdit.activity.name}
						</td>
					</tr>
                  	<tr>
						<td>channel：</td>
						<td>${itemEdit.channel}</td>
					</tr>
					<tr>
						<td>Banner描述：</td>
						<td>
							<input type="text" name="description" style="width:60%;" value="${itemEdit.description}"/>
						</td>
					</tr>
					<tr>
						<td>Banner链接：</td>
						<td>
							<input type="text" name="link" style="width:60%;" value="${itemEdit.link}"/>
						</td>
					</tr>
					<tr>
						<td>排序：</td>
						<td>
							<input type="text" name="sort" style="width:60%;" value="${itemEdit.sort}"/>
						</td>
					</tr>
					<tr>
						<td>图片：</td>
						<td>
							<image name="imageUrl" src="${itemEdit.imageUrl}"/>
							<input type="file" name="image" style="width:60%;"/>
						</td>
					</tr>
                
                  <tr>
                    <td>状态：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="status" class="selectpicker form-control">
                        <#list statusList as status>
                        <option value="${status.key}" <#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
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