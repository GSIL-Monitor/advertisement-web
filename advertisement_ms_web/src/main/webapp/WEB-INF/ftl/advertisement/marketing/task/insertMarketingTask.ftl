<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
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
      <form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                      <input type="text" name="productName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>短信通道：</td>
                    <td>
                      <input type="text" name="smsPlatform" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>短信文案：</td>
                    <td>
                      <textarea type="text" name="smsContent" style="width:60%;height:80px;"></textarea></td>
                  </tr>
                  
                  <tr>
                    <td>发送时间：</td>
                    <td>
                      <input type="text" id="effect" name="sendTimeStr" style="width:60%;"></td>
                  </tr>                    
                             
                  <tr>
                    <td>去除省份(,分隔)：</td>
                    <td>
                      <input type="text" name="rejectProvince" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>去除城市(,分隔)：</td>
                    <td>
                      <input type="text" name="rejectCity" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>取出数：</td>
                    <td>
                      <input type="text" name="exportCount" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>需要的运营商类型：</td>
                    <td>
                      <input type="checkbox" name="downloadMobileType" value="联通"  checked="checked">联通
                      <input type="checkbox" name="downloadMobileType" value="移动">移动
                      <input type="checkbox" name="downloadMobileType" value="电信">电信   
                       </td>
                  </tr>
                  
                   <tr>
                    <td>手机号文件：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;">
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