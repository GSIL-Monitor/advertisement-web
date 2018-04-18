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
                    <td style="width:20%;">key：</td>
                    <td>${itemEdit.key}</td>
                  </tr> 
                
                  <tr>
                    <td>类型：</td>
                    <td>${itemEdit.type}</td>
                  </tr>
                  
                  <tr>
                    <td>名称：</td>
                    <td>${itemEdit.name}</td>
                  </tr>
                  
                  <tr>
                    <td>描述：</td>
                    <td>${itemEdit.description}</td>
                  </tr>

                  <tr>
                    <td>入口地址：</td>
                    <td>${itemEdit.entranceUrl}</td>
                  </tr>
                   
                  <tr>
                    <td>bonus：</td>
                    <td>${itemEdit.bonus}</td>
                  </tr>
                  
                  <tr>
                    <td>单价：</td>
                    <td>${itemEdit.unitPrice?c}</td>
                  </tr> 
                  
                  <tr>
                    <td>统计显示类型：</td>
                    <td>${itemEdit.showTypeValue}</td>
                  </tr> 
                  
                  <tr>
                    <td>承保接口类型：</td>
                    <td>${itemEdit.deliverTypeValue}</td>
                  </tr> 
                  
                   <tr>
                    <td>显示字段：</td>
                    <td>${itemEdit.showFields}</td>
                  </tr>     
                  <tr>
                    <td>渠道回调：</td>
                    <td>${itemEdit.notifyHandler}</td>
                  </tr>
                  
                  <tr>
                    <td>密钥：</td>
                    <td>${itemEdit.encryptKey}</td>
                  </tr>
                  
                  <tr>
                    <td>标记：</td>
                    <td>${itemEdit.mark}</td>
                  </tr> 
                  
                  
                  <tr>
                    <td>渠道编码：</td>
                    <td>${itemEdit.deliverMediaCode}</td>
                  </tr>
                  
                   <tr>
                    <td>ios下载链接：</td>
                    <td>${itemEdit.iosDownloadUrl}</td>
                  </tr>  
                  
                  <tr>
                    <td>安卓下载链接：</td>
                    <td>${itemEdit.androidDownloadUrl}</td>
                  </tr>                         
                            
                  <tr>
                    <td>logo：</td>
                    <td>
                   	<#if itemEdit.imageUrl??>
                     <image src="${itemEdit.imageUrl}" width="500">
                     </image>
                    </#if>
                    </td>
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