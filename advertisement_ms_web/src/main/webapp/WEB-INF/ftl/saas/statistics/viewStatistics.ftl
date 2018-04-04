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
                    <td style="width:20%;">数据日期：</td>
                    <td>${itemEdit.date}</td>
                  </tr>
           
                 <tr>
                    <td>商家：</td>
                    <td>${itemEdit.merchant.name}</td>
                  </tr> 
                  <tr>
                    <td>产品：</td>
                    <td>${itemEdit.product.name}</td>
                  </tr> 
                  
                  <tr>
                    <td>渠道：</td>
                    <td>${itemEdit.channel}</td>
                  </tr> 
                  
                 <tr>
                    <td>uv：</td>
                    <td>${itemEdit.uvCount}</td>
                  </tr>
                  
                   <tr>
                    <td>激活数（下载）：</td>
                    <td>${itemEdit.downloadCount}</td>
                  </tr>
                
                  <tr>
                    <td>注册数：</td>
                    <td>${itemEdit.registerCount}</td>
                  </tr>
                  
                   <tr>
                    <td>首次登陆数：</td>
                    <td>${itemEdit.firstLoginCount}</td>
                  </tr>
                  
                  <tr>
                    <td>提交数：</td>
                    <td>${itemEdit.applyCount}</td>
                  </tr>            
                  
                  <tr>
                    <td>申请用户数：</td>
                    <td>${itemEdit.applyUserCount}</td>
                  </tr>
                  
                  <tr>
                    <td>申请成功数：</td>
                    <td>${itemEdit.applySuccessCount}</td>
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