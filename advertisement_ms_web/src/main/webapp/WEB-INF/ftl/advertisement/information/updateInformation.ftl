<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">添加${functionTitle}</a>
    </div>
    <h1>修改${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.informationId?c}"/>
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
                    <td>
                      <div style="width:60%;">
                      <select name="merchantId" class="selectpicker form-control" data-live-search="true">
                        <#list merchantList as item>
                        <option value="${item.merchantId?c}" <#if itemEdit.merchantId == item.merchantId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  
                  <tr>
                    <td>名字：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
                  </tr>
                  
                <tr>
               	    <td>标题：</td>
                    <td>
                      <input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
                  </tr>
                  
                  <tr>
                    <td>描述：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;" value="${itemEdit.description}"></td>
                  </tr>
                  
                  <tr>
                    <td>特征标签：</td>
                    <td>
                      <input type="text" name="featureTags" style="width:60%;" value="${itemEdit.featureTags}"></td>
                  </tr>
                  
                  <tr>
                    <td>推荐标签：</td>
                    <td>
                      <input type="text" name="recommendTags" style="width:60%;" value="${itemEdit.recommendTags}"></td>
                  </tr>
                  
                  <tr>
                    <td>授权标签：</td>
                    <td>
                      <input type="text" name="authorizeTags" style="width:60%;" value="${itemEdit.authorizeTags}"></td>
                  </tr>
                  
                  <tr>
                    <td>最少贷款金额：</td>
                    <td>
                      <input type="text" name="minadvertisementAmount" style="width:60%;" value="${itemEdit.minadvertisementAmount}"></td>
                  </tr>
                  
                  <tr>
                    <td>最多贷款金额：</td>
                    <td>
                      <input type="text" name="maxadvertisementAmount" style="width:60%;" value="${itemEdit.maxadvertisementAmount}"></td>
                  </tr>
                  
                  <tr>
                    <td>最少还款期限：</td>
                    <td>
                      <input type="text" name="minDays" style="width:60%;" value="${itemEdit.minDays}"></td>
                  </tr>
                  
                  <tr>
                    <td>最多还款期限：</td>
                    <td>
                      <input type="text" name="maxDays" style="width:60%;" value="${itemEdit.maxDays}"></td>
                  </tr>
                  
                   <tr>
                    <td>最少月利率：</td>
                    <td>
                      <input type="text" name="minMonthRate" style="width:60%;" value="${itemEdit.minMonthRate}"></td>
                  </tr>
                  
                  <tr>
                    <td>最多月利率：</td>
                    <td>
                      <input type="text" name="maxMonthRate" style="width:60%;" value="${itemEdit.maxMonthRate}"></td>
                  </tr>
                  
                  <tr>
                    <td>最少月服务费：</td>
                    <td>
                      <input type="text" name="minMonthService" style="width:60%;" value="${itemEdit.minMonthService}"></td>
                  </tr>
                  
                  <tr>
                    <td>最少月服务费：</td>
                    <td>
                      <input type="text" name="maxMonthService" style="width:60%;" value="${itemEdit.maxMonthService}"></td>
                  </tr>
                  
                  <tr>
                    <td>最少一次性服务费：</td>
                    <td>
                      <input type="text" name="minServiceCharge" style="width:60%;" value="${itemEdit.minServiceCharge}"></td>
                  </tr>
                  
                  
                  <tr>
                    <td>最多一次性服务费：</td>
                    <td>
                      <input type="text" name="maxServiceCharge" style="width:60%;" value="${itemEdit.maxServiceCharge}"></td>
                  </tr>
                  
                   <tr>
                    <td>最快放款时间：</td>
                    <td>
                      <input type="text" name="approvalTime" style="width:60%;" value="${itemEdit.approvalTime}"></td>
                  </tr>              
                  
                  <tr>
                    <td>要求：</td>
                    <td>
                      <input type="text" name="requirements" style="width:60%;" value="${itemEdit.requirements}"></td>
                  </tr>
                  
                  <tr>
                    <td>审核条件：</td>
                    <td>
                      <input type="text" name="auditMethod" style="width:60%;" value="${itemEdit.auditMethod}"></td>
                  </tr>
                  
                  <tr>
                    <td>还款方式：</td>
                    <td>
                      <input type="text" name="repaymentMethod" style="width:60%;" value="${itemEdit.repaymentMethod}"></td>
                  </tr>
                  
                  <tr>
                    <td>申请接口：</td>
                    <td>
                      <input type="text" name="applyInterface" style="width:60%;" value="${itemEdit.applyInterface}"></td>
                  </tr>
                    
                  <tr>
                    <td>图片：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;">
                    </td>
                  </tr>   
                    
                   <tr>
                    <td>月利率类型：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="monthRateType" class="selectpicker form-control">
                        <#list monthRateTypeList as item>
                        <option value="${item.key}" <#if itemEdit.monthRateType == item.key>selected</#if>>${item.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>            
                                                
                  <tr>
                    <td>状态：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="status" class="selectpicker form-control">
                        <#list statusList as status>
                        <option value="${status.key}" <#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="4" style="text-align:center">
                      <input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />