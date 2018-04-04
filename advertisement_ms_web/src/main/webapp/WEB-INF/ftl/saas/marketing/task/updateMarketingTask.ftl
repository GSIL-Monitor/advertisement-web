<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">添加${functionTitle}</a>
    </div>
    <h1>修改${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.marketingTaskId?c}"/>
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
                    <td width="20%">产品名称：</td>
                    <td>
                      <input type="text" name="productName" style="width:60%;" value="${itemEdit.productName}"></td>
                  </tr>
                  
                  <tr>
                    <td>短信通道：</td>
                    <td>
                      <input type="text" name="smsPlatform" style="width:60%;" value="${itemEdit.smsPlatform}"></td>
                  </tr>
                  
                  <tr>
                    <td>短信文案：</td>
                    <td>
                      <textarea type="text" name="smsContent" style="width:60%;height:80px;" >${itemEdit.smsContent}</textarea></td>
                  </tr>
                  
                   <tr>
                    <td>发送时间：</td>
                    <td>
                      <input type="text" id="effect" name="sendTimeStr" style="width:60%;" value="${itemEdit.sendTime}"></td>
                  </tr> 
                  
                  <tr>
                    <td>去除省份(,分隔)：</td>
                    <td>
                      <input type="text" name="rejectProvince" style="width:60%;" value="${itemEdit.rejectProvince}"></td>
                  </tr>
                  
                  <tr>
                    <td>去除城市(,分隔)：</td>
                    <td>
                      <input type="text" name="rejectCity" style="width:60%;" value="${itemEdit.rejectCity}"></td>
                  </tr>
                  
                  <tr>
                    <td>取出数：</td>
                    <td>
                      <input type="text" name="exportCount" style="width:60%;" value="${itemEdit.exportCount?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>需要的运营商类型：</td>
                    <td>
                      <input type="checkbox" name="downloadMobileType" value="联通" <#if itemEdit.downloadMobileType?contains("联通")>checked="checked"</#if>>联通
                      <input type="checkbox" name="downloadMobileType" value="移动" <#if itemEdit.downloadMobileType?contains("移动")>checked="checked"</#if>>移动
                      <input type="checkbox" name="downloadMobileType" value="电信" <#if itemEdit.downloadMobileType?contains("电信")>checked="checked"</#if>>电信   
                       </td>
                  </tr>
                  
                  <tr>
                    <td>手机号：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;">
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
<script>
	$(function() {
		timer('#effect');
	});
</script>
<@htmlFoot />