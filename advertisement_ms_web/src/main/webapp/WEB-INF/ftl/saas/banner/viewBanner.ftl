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
                    <td style="width:20%;">名称：</td>
                    <td>${itemEdit.title}</td>
                 </tr> 
                 
                 <tr>
                    <td>活动类型：</td>
                    <td>${itemEdit.actionType}</td>
                  </tr> 
                
                  <tr>
                    <td>详细链接：</td>
                    <td>${itemEdit.detailUrl}</td>
                  </tr>
                               
                  <tr>
                    <td>开始时间：</td>
                    <td>${itemEdit.startTimeContent}</td>
                  </tr>
                  
                  <tr>
                    <td>结束时间：</td>
                    <td>${itemEdit.endTimeContent}</td>
                  </tr>
                  
                  <tr>
                    <td>权重：</td>
                    <td>${itemEdit.weight}</td>
                  </tr>
                  
                 <tr>
                    <td>图片：</td>
                    <td>
                   	<#if itemEdit.imgUrl??>
                     <image src="${itemEdit.imgUrl}" width="500">
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