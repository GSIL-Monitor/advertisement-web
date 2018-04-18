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
                    <td style="width:20%;">保险名称：</td>
                    <td>${itemEdit.insurance.name}</td>
                  </tr>
                  <tr>
                    <td>保险公司：</td>
                    <td>${itemEdit.merchant.name}</td>
                  </tr>
                  
                  <tr>
                    <td>活动名称：</td>
                    <td>${itemEdit.activity.name}</td>
                  </tr>
                  
                  <tr>
                    <td>渠道：</td>
                    <td>${itemEdit.channel}</td>
                  </tr>
                
                  <tr>
                    <td>action：</td>
                    <td>${itemEdit.action}</td>
                  </tr>
                  
                   <tr>
                    <td>排序等级：</td>
                    <td>${itemEdit.sort}</td>
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