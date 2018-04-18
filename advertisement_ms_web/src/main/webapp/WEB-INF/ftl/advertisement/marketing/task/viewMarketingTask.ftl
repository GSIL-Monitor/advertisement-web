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
                    <td style="width:20%;">产品名称：</td>
                    <td>${itemEdit.productName}</td>
                  </tr> 
                
                  <tr>
                    <td>通道：</td>
                    <td>${itemEdit.smsPlatform}</td>
                  </tr>
                  
                  <tr>
                    <td>短信内容：</td>
                    <td>${itemEdit.smsContent}</td>
                  </tr>
                  
                   <tr>
                    <td>发送时间：</td>
                    <td>${itemEdit.sendTimeContent}</td>
                  </tr>
                  
                  <tr>
                    <td>上传地址：</td>
                    <td>${itemEdit.uploadUrl}</td>
                  </tr>
                  
                  <tr>
                    <td>下载地址：</td>
                    <td>${itemEdit.downloadUrl}</td>
                  </tr>
                  
                  <tr>
                    <td>排除省份：</td>
                    <td>${itemEdit.rejectProvince}</td>
                  </tr>
                  
                  <tr>
                    <td>排查城市：</td>
                    <td>${itemEdit.rejectCity}</td>
                  </tr>
                  
                   <tr>
                    <td>需要的手机运营商：</td>
                    <td>${itemEdit.downloadMobileType}</td>
                  </tr>
                  
                   <tr>
                    <td>需要数量：</td>
                    <td>${itemEdit.exportCount}</td>
                  </tr>
                  
                  <tr>
                    <td>进度：</td>
                    <td>${itemEdit.progress}</td>
                  </tr> 
                  
                   <tr>
                    <td>状态：</td>
                    <td>${itemEdit.statusValue}</td>
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