<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@sideBar />

<div id="content">
<@headerPart />
    <div id="content-header">
        <div id="breadcrumb">
            <a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
            ${functionTitle}管理
            </a>
            <a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <form action="${rc.contextPath}/admin/${functionName}/insertBankOrder.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
                <div class="span12">
                    <div class="widget-box">
                        <div class="widget-title">
							<span class="icon"><i class="icon-th"></i>
							</span>
                        </div>
                        <div class="widget-content nopadding">
                            <table class="table table-bordered table-striped" id="">
                                <tbody>
                                <tr>
                                    <td>选择银行：</td>
                                    <td>
                                        <div style="width:60%;">
                                            <select id="productId" name="productId" class="selectpicker form-control">
                                            <#list productList as product>
                                                <option value="${product.productId}">${product.name}</option>
                                            </#list>
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>导入文件：</td>
                                    <td>
                                        <input id="importFile" type="file" name="file" />
                                    </td>
                                </tr>
                                <tr>
                                   
                                </tr>
                                <tr>
                                    <td colspan="4" style="text-align:center">
                                        <input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult()">
                                    </td>
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
<script>
    $(function() {
        timer('#startTimeValue');
        $('#startTimeValue').datetimepicker({
            maxDate:0,
            onShow:function( ct ){
                this.setOptions({
                    maxDate:$('#endTimeValue').val()?$('#endTimeValue').val():false
                })
            },
            step: 15,
            defaultTime: '08:00',
            format:'Y-m-d H:i'
        });
        timer('#endTimeValue');
        $('#endTimeValue').datetimepicker({
            minDate:0,
            onShow:function( ct ){
                this.setOptions({
                    minDate:$('#startTimeValue').val()?$('#startTimeValue').val():false
                })
            },
            step: 15,
            defaultTime: '08:00',
            format:'Y-m-d H:i'
        });
    });
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />