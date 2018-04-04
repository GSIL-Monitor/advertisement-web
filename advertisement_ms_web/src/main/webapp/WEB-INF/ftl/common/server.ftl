<#include "core.ftl" />
<@htmlHead title="服务器管理"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	function refreshConstants() {
		confirmSend('${rc.contextPath}/admin/server/refresh.do', '确定刷新生产环境吗');
	}
	function refreshConfirmConstants() {
		sendAjax('${rc.contextPath}/admin/server/refreshConfirm.do');
	}
</script>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="服务器管理" class="tip-bottom"> <i class="icon-book"></i>
       	 服务器管理
      </a>
    </div>
    <h1>服务器管理</h1>
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
                  <tr height="100px">
                    <td style="text-align:center">
                      <input type="button" name="" value="刷新在线确认服务器常量" class=" btn btn-green" style="width: 300px;border: 0;margin-top:30px;" id="allInputBtn" onclick="refreshConfirmConstants();">
                    </td>
                  </tr>
                  <tr>
                    <td style="text-align:center">
                      <input type="button" name="" value="刷新生产环境服务器常量" class=" btn btn-red" style="width: 200px;border: 0;" id="allInputBtn" onclick="refreshConstants();">
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/server/config.do" />
<@footPart />
<@htmlFoot />