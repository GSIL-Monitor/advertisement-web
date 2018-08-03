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
                    <td>公司全称：</td>
                    <td>${itemEdit.companyName}</td>
                  </tr>
                  <tr>
                    <td>公司营业执照注册号：</td>
                    <td>${itemEdit.businessNumber}</td>
                  </tr>
                  <tr>
                    <td>公司地址：</td>
                    <td>${itemEdit.address}</td>
                  </tr>
                  <tr>
                    <td>联系人姓名：</td>
                    <td>${itemEdit.name}</td>
                  </tr>
                  <tr>
                    <td>手机：</td>
                    <td>${itemEdit.phone}</td>
                  </tr>
                  <tr>
                    <td>微信号：</td>
                    <td>${itemEdit.wechat}</td>
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