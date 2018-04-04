<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />

<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">${functionTitle}详情</a>
    </div>
    
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
    <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.statisticsId?c}"/>
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
                    <td style="width:20%;">渠道：</td>
                    <td>${itemEdit.channel}</td>
                  </tr> 
                  
                 <!--    <tr>
                    <td>pv：</td>
                    <td><input type="text" name="pvCount" style="width:60%;" value="${itemEdit.pvCount?c}"></td>
                  </tr> 
                  -->
                  
                 <tr>
                    <td>uv：</td>
                    <td><input type="text" name="uvCount" style="width:60%;" value="${itemEdit.uvCount?c}"></td>
                  </tr> 
                  
                  <tr>
                    <td>激活数（下载）：</td>
                    <td><input type="text" name="downloadCount" style="width:60%;" value="${itemEdit.downloadCount?c}"></td>
                  </tr>
                
                  <tr>
                    <td>注册数：</td>
                    <td><input type="text" name="registerCount" style="width:60%;" value="${itemEdit.registerCount?c}"></td>
                  </tr>
                  
                   <tr>
                    <td>首次登陆数数：</td>
                    <td><input type="text" name="firstLoginCount?c" style="width:60%;" value="${itemEdit.firstLoginCount?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>申请数：</td>
                    <td><input type="text" name="applyCount" style="width:60%;" value="${itemEdit.applyCount?c}"></td>
                  </tr>
                  
                   <tr>
                    <td>申请人数：</td>
                    <td><input type="text" name="applyUserCount" style="width:60%;" value="${itemEdit.applyUserCount?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>申请成功数：</td>
                    <td><input type="text" name="applySuccessCount" style="width:60%;" value="${itemEdit.applySuccessCount?c}"></td>
                  </tr>
             
                  <tr>
                    <td colspan="2" style="text-align:center">
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