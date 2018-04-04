<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
    </div>
    <h1>添加${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td>名字：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  
                <tr>
               	    <td>身份证号：</td>
                    <td>
                      <input type="text" name="identityCard" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>最高学历：</td>
                    <td>
                      <input type="text" name="education" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>居住城市：</td>
                    <td>
                      <input type="text" name="location" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>居住详细地址：</td>
                    <td>
                      <input type="text" name="address" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>公司名称：</td>
                    <td>
                      <input type="text" name="companyName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>公司所在城市：</td>
                    <td>
                      <input type="text" name="companyLocation" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>公司详细地址：</td>
                    <td>
                      <input type="text" name="companyAddress" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>岗位：</td>
                    <td>
                      <input type="text" name="position" style="width:60%;"></td>
                  </tr>
                  
                   <tr>
                    <td>学校名称：</td>
                    <td>
                      <input type="text" name="schoolName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>学制：</td>
                    <td>
                      <input type="text" name="educationYears" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>月收入：</td>
                    <td>
                      <input type="text" name="salary" style="width:60%;"></td>
                  </tr>
                  
                   <tr>
                    <td>：</td>
                    <td>
                      <input type="text" name="realtyInfo" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>：</td>
                    <td>
                      <input type="text" name="carInfo" style="width:60%;"></td>
                  </tr>
                  
                  
                  <tr>
                    <td>信用卡额度，月额度：</td>
                    <td>
                      <input type="text" name="creditLimit" style="width:60%;"></td>
                  </tr>
                  
                   <tr>
                    <td>最快放款时间：</td>
                    <td>
                      <input type="text" name="advertisementPurpose" style="width:60%;"></td>
                  </tr>              
                  
                  <tr>
                    <td>要求：</td>
                    <td>
                      <input type="text" name="requirements" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>审核条件：</td>
                    <td>
                      <input type="text" name="auditMethod" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>还款方式：</td>
                    <td>
                      <input type="text" name="repaymentMethod" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>申请接口：</td>
                    <td>
                      <input type="text" name="applyInterface" style="width:60%;"></td>
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
                        <option value="${item.key}">${item.value}</option>
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
                        <option value="${status.key}">${status.value}</option>
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