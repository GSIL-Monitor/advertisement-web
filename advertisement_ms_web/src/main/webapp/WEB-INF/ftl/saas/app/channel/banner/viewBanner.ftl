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
						<td>活动：</td>
						<td>
							【${itemEdit.activityId}】${itemEdit.activity.name}
						</td>
					</tr>
	              	<tr>
						<td>channel：</td>
						<td><#if (itemEdit.channel)??>${itemEdit.channel}</#if></td>
					</tr>
					<tr>
						<td>Banner描述：</td>
						<td>${itemEdit.description}</td>
					</tr>
					<tr>
						<td>Banner链接：</td>
						<td>${itemEdit.link}</td>
					</tr>
					<tr>
						<td>排序：</td>
						<td>${itemEdit.sort}</td>
					</tr>
					<tr>
						<td>图片：</td>
						<td>
							<image name="imageUrl" src="${itemEdit.imageUrl}"/>
						</td>
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