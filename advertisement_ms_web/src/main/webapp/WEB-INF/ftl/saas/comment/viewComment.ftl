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
										<td style="width:20%;">产品id：</td>
										<td>${itemEdit.productId}</td>
									</tr>
									<tr>
										<td>手机号：</td>
										<td>${itemEdit.user.mobile}</td>
									</tr>
									<tr>
										<td>申请状态：</td>
										<td>${itemEdit.applyStatusValue}</td>
									</tr>
									<tr>
										<td>费率满意度：</td>
										<td>${itemEdit.rateSatisfaction}星</td>
									</tr>
									<tr>
										<td>贷款满意度：</td>
										<td>${itemEdit.advertisementSatisfaction}星</td>
									</tr>
									<tr>
										<td>流程满意度：</td>
										<td>${itemEdit.processSatisfaction}星</td>
									</tr>
									<tr>
										<td>内容：</td>
										<td>${itemEdit.content}</td>
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