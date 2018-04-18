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
                    <td>${itemEdit.appName}</td>
                  </tr>
                  
                 <tr>
                    <td style="width:20%;">渠道key：</td>
                    <td>${itemEdit.key}</td>
                  </tr> 
                
                 <!--  <tr>
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
                    <td>承保接口类型：</td>
                    <td>${itemEdit.deliverTypeValue}</td>
                  </tr> 
                  
                   <tr>
                    <td>来源-只在平安接口传数使用（如：移动端微信支付）：</td>
                    <td>${itemEdit.mark}</td>
                  </tr> 
                  
                  
                  <tr>
                    <td>渠道编码-只在平安接口区分渠道编码使用（如qinglan:01）：</td>
                    <td>${itemEdit.deliverMediaCode}</td>
                  </tr>                        
                            
                  <tr>
                    <td>图片：</td>
                    <td>
                   	<#if itemEdit.imageUrl??>
                     <image src="${itemEdit.imageUrl}" width="500">
                     </image>
                    </#if>
                    </td>
                  </tr> --> 
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