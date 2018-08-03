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
                    <td style="width:20%;">商家：</td>
                    <td>${itemEdit.merchant.name}</td>
                  </tr> 
                  
                   <tr>
                    <td style="width:20%;">保险：</td>
                    <td>${insurance.fullName}【保险id:${insurance.insuranceId}】【公司:${insurance.merchant.name}】【供应商:${insurance.description}】</td>
                  </tr> 
                
                  <tr>
                    <td>channel：</td>
                    <td>${itemEdit.channel}</td>
                  </tr>
                  
                  <tr>
                    <td>locationCode：</td>
                    <td>${itemEdit.locationCode}</td>
                  </tr>
                  
                  <tr>
                    <td>类型：</td>
                    <td>${itemEdit.typeValue}</td>
                  </tr>
                
                  <tr>
                    <td>quota：</td>
                    <td>${itemEdit.quota}</td>
                  </tr>
                  
                  <tr>
                    <td>库存：</td>
                    <td>${itemEdit.stock}</td>
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