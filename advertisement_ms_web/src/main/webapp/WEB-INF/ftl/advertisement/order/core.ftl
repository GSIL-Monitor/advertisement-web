
<#include "/common/core.ftl" />
<#assign functionName="order"/>
<#assign functionTitle="订单"/>
<#assign functionId="orderId"/>
<#macro orderSearchForm>
	<div class="filter-box">
		<table border="0">
			<tr>
				<td><span style="font-size: 16px;color: #1ab394;">基本资料：</span></td>
			</tr>
			<tr>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
			</tr>
			<tr>
				<td>
					<span class="field-name">订单ID</span>
					<input type="text" id="orderId"/>
				</td>
				<td>
					<span class="field-name">订单来源</span>
					<select name="orderSource" id="orderSource" class="selectpicker" data-live-search="true">
						<option value> 请选择</option>
						<#list channelList as channel>
							<option value="${channel.name}">【${channel.key}】${channel.name}</option>
						</#list>
					</select>
				</td>
				<td>
					<span class="field-name">分配状态</span>
					<select name="status" id="status" class="selectpicker">
						<option value> 请选择</option>
						<#list statusList as status>
							<option value="${status.key}">${status.value}</option>
						</#list>
					</select>
				</td>
				<td colspan="2">
					<span class="field-name">订单时间</span>
					<input type="text" id="createTimeStart"/>到
					<input type="text" id="createTimeEnd"/>
				</td>
			</tr>
			<tr>
				<td>
					<span class="field-name">客户城市</span>
					<select name="city" id="city" class="selectpicker" data-live-search="true">
						<option value> 请选择</option>
						<#list merchantLocationList as merchantLocation>
							<option value="${merchantLocation.key}">${merchantLocation.value.locationName}</option>
						</#list>
					</select>
				</td>
				<td>
					<span class="field-name">客户姓名</span>
					<input type="text" id="name"/>
				</td>
				<td>
					<span class="field-name">性别</span>
					<#-- <input type="radio" id="gender1" name="gender" value="1"/>男
            <input type="radio" id="gender2" name="gender" value="2"/>女 -->
					<@radioButton name="gender" labels="男,女" values="1,2"/>
				</td>
				<td colspan="2">
					<span class="field-name">年龄</span>
					<input type="text" id="minAge"/>到
					<input type="text" id="maxAge"/>
				</td>
			</tr>
			<tr>
				<td>
					<span class="field-name">希望沟通方式</span>
					<select name="communicateWay" id="communicateWay" class="selectpicker" data-live-search="true">
						<option value> 请选择</option>
						<#list contactTagsList as contact>
							<option value="${contact.tagsId}">${contact.name}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td><span style="font-size: 16px;color: #1ab394;">意向标签：</span></td>
			</tr>
			<tr>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
				<th width="20%"></th>
			</tr>
			<tr>
				<td>
					<span class="field-name">近期购买过何种保险</span>
					<select name="insuranceConsumption" id="insuranceConsumption" class="selectpicker">
						<option value> 请选择</option>
						<#list insuranceCategoryTagsList as insurance>
							<option value="${insurance.tagsId}">${insurance.name}</option>
						</#list>
					</select>
				</td>
				<td>
					<span class="field-name">对何种保险感兴趣</span>
					<select name="interestInsurance" id="interestInsurance" class="selectpicker">
						<option value> 请选择</option>
						<#list interestInsuranceTagsList as insurance>
							<option value="${insurance.tagsId}">${insurance.name}</option>
						</#list>
					</select>
				</td>
				<td>
					<span class="field-name">保障对象</span>
					<select name="insuredPerson" id="insuredPerson" class="selectpicker">
						<option value> 请选择</option>
						<#list insuranceRangeTagsList as insuranceRange>
							<option value="${insuranceRange.tagsId}">${insuranceRange.name}</option>
						</#list>
					</select>
				</td>
				<td colspan="2">
					<span class="field-name">年度购买保险预算</span>
					<select name="acceptablePreium" id="acceptablePreium" class="selectpicker">
						<option value> 请选择</option>
						<#list insuranceRangeTagsList as insuranceRange>
							<option value="${insuranceRange.tagsId}">${insuranceRange.name}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<div class="btn btn-green" id="queryButton">确定</div>
					<div class="btn btn-blue" id="queryReset">重置</div>
				</td>
			</tr>
		</table>
	</div>
</#macro>