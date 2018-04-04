$(function() {
	$('#closeBrokerTipButton').click(function() {
		TipWindow.hide('#distributeBrokerPopup');
	});
	$('#closeMerchantTipButton').click(function() {
		TipWindow.hide('#distributeMerchantPopup');
	});
});
// 获取经理人列表
function getBrokerList(orderId) {
	var orderIds = null;
	if (orderId == null) {
		orderIds = getOrderId();
	}
	if (orderId != null || (orderIds != "" && orderIds != null)) {
		var data = {}
		$
				.ajax({
					type : 'POST',
					url : "/ms/admin/order/queryBroker.do",
					data : data,
					success : function(data, status) {
						var brokerTable = $('#brokerTable');
						brokerTable.find("tbody").html("");
						if (orderId != null) {
							$('#userNum').html("订单" + orderId);
						} else {
							$('#userNum').html(
									orderIds.split(',').length + "个用户");
							$('#userNumInput').val(orderIds.split(',').length);
							$('#orderIdInput').val(orderIds);
						}
						var userIds = [];
						var tempHtml = "";
						for (var i = 0; i < data.list.length; i++) {
							tempHtml += "<tr>" + "<td>" + data.list[i].userId
									+ "</td>" + "<td>" + data.list[i].realName
									+ "</td>" + "<td>"
									+ data.list[i].institution + "</td>";
							if (orderId != null) {
								tempHtml += "<td><button type='button'  class='btn btn-blue' onclick='distribution("
										+ orderId
										+ ","
										+ data.list[i].userId
										+ ")' >分配</button></td>" + "</tr>";
							} else {
								tempHtml += "<td><input type='text' id='distribution"
										+ data.list[i].userId
										+ "' value=''/></td>" + "</tr>";
							}
							userIds.push(data.list[i].userId);
						}
						brokerTable.find("tbody").html(tempHtml);
						$('#userIdInput').val(userIds);
						if (orderId != null) {
							$('#quantityBrokerDistribute').addClass('hide');
						} else {
							$('#quantityBrokerDistribute').removeClass('hide');
						}
						TipWindow.showTip('#distributeBrokerPopup');
					}
				});
	} else {
		alert("没有可分配的订单！！");
	}
}

// 获取合作方列表
function getMerchantList(orderId) {
	var orderIds = null;
	if (orderId == null) {
		orderIds = getOrderId();
	}
	if (orderId != null || (orderIds != "" && orderIds != null)) {
		var data = {}
		$
				.ajax({
					type : 'POST',
					url : "/ms/admin/order/queryMerchant.do",
					data : data,
					success : function(data, status) {
						var merchantTable = $('#merchantTable');
						merchantTable.find("tbody").html("");
						if (orderId != null) {
							$('#userNumMerchant').html("订单" + orderId);
						} else {
							$('#userNumMerchant').html(
									orderIds.split(',').length + "个用户");
							$('#userNumMerchantInput').val(
									orderIds.split(',').length);
							$('#orderIdMerchantInput').val(orderIds);
						}
						var merchantIds = [];
						var tempHtml = "";
						for (var i = 0; i < data.list.length; i++) {
							tempHtml += "<tr>" + "<td>"
									+ data.list[i].merchantId + "</td>"
									+ "<td>" + data.list[i].name + "</td>";
							if (orderId != null) {
								tempHtml += "<td><button type='button'  class='btn btn-blue' onclick='distribution("
										+ orderId
										+ ",null,"
										+ data.list[i].merchantId
										+ ")' >分配</button></td>" + "</tr>";
							} else {
								tempHtml += "<td><input type='text' id='distribution"
										+ data.list[i].merchantId
										+ "' value=''/></td>" + "</tr>";
							}
							merchantIds.push(data.list[i].merchantId);
						}
						merchantTable.find("tbody").html(tempHtml);
						$('#merchantIdInput').val(merchantIds);
						if (orderId != null) {
							$('#quantityMerchantDistribute').addClass('hide');
						} else {
							$('#quantityMerchantDistribute')
									.removeClass('hide');
						}
						TipWindow.showTip('#distributeMerchantPopup');
					}
				});
	} else {
		alert("没有可分配的订单！！");
	}
}

function getSearchContent() {
	var searchContent = "";
	searchContent += getParams("orderId", $('#orderId').val())
	searchContent += getParams("orderSource", $('#orderSource').val())
	searchContent += getParams("status", $('#status').val())
	searchContent += getParams("createTimeStart", $('#createTimeStart').val()
			+ " 00:00:00")
	searchContent += getParams("createTimeEnd", $('#createTimeEnd').val()
			+ " 00:00:00")
	searchContent += getParams("city", $('#city').val())
	searchContent += getParams("searchContent", encodeURI($('#name').val()))
	searchContent += getParams("gender", $("input[name='gender']:checked")
			.val())
	searchContent += getParams("maxAge", $('#maxAge').val())
	searchContent += getParams("minAge", $('#minAge').val())
	searchContent += getParams("acceptablePreium", $('#acceptablePreium').val())
	searchContent += getParams("orderSearch.communicateWay", $('#communicateWay').val())
	searchContent += getParams("orderSearch.insuredPerson", $('#insuredPerson').val())
	searchContent += getParams("orderSearch.interestInsurance", $('#interestInsurance').val())
	searchContent += getParams("insuranceConsumption", $('#insuranceConsumption').val())
	return searchContent;
}

function getParams(name, param) {
	if (param != null && param != "" && param != " 00:00:00") {
		return name + "=" + param + "&";
	} else {
		return "";
	}
}

function distribution(orderId, userId, merchantId) {
	var data = []
	data.push({
		userId : userId,
		orderIds : orderId,
		count : 1
	});
	var url = "/ms/admin/order/bindOrder.do?distributionType=2";
	var i = 0;
	if (userId != null) {
		url += "&userId=" + userId;
		i = 1;
	}
	if (merchantId != null) {
		url += "&merchantId=" + merchantId;
		i = 2;
	}
	$.ajax({
		type : 'POST',
		url : url,
		contentType : "application/json",
		async : false,
		data : JSON.stringify(data),
		success : function(data, status) {
			if (data.retCode == "200") {
				TipWindow.hide('#distributeBrokerPopup');
				TipWindow.hide('#distributeMerchantPopup');
				TipWindow.showSingleWithContent('提交成功!');
			}else{
				errorCommit('#submitErrorContent'+i, data.retDesc);
				return;
			}
		}
	});
}

function getQuantityMerchantList() {
	var orderIds = getOrderId();
	if (orderIds != "" && orderIds != null) {
		var data = {}
		$.ajax({
			type : 'POST',
			url : "/ms/admin/order/queryMerchant.do",
			data : data,
			success : function(data, status) {
				var brokerTable = $('#brokerTable-mb');
				brokerTable.find("tbody").html("");
				$('#userNum-mb').html(orderIds.split(',').length + "个用户");
				$('#userNumInput').val(orderIds.split(',').length);
				$('#orderIdInput').val(orderIds);
				var merchantIds = [];
				for (var i = 0; i < data.list.length; i++) {
					var tempHtml = "<tr>" + "<td>" + data.list[i].merchantId
							+ "</td>" + "<td>" + data.list[i].name + "</td>"
							+ "<td><input type='text' id='distribution"
							+ data.list[i].merchantId + "' value=''/></td>"
							+ "</tr>";
					merchantIds.push(data.list[i].merchantId);
					brokerTable.append(tempHtml);
				}
				$('#merchantIdInput').val(merchantIds);
			}
		});
	} else {
		alert("没有可分配的订单！！");
	}
}

function getOrderId() {
	var searchContent = getSearchContent();
	var data = {}
	var orderIds = "";
	$.ajax({
		type : 'POST',
		url : "/ms/admin/order/queryOrder.do?type=1&"
				+ searchContent.substr(0, searchContent.length - 1),
		data : data,
		async : false,
		success : function(data, status) {
			for (var i = 0; i < data.list.length; i++) {
				orderIds += data.list[i].orderId + ","
			}
		}
	});
	return orderIds.substr(0, orderIds.length - 1);
}

function getQuantityDistribution() {
	var searchContent = getSearchContent();
	var userIds = $('#userIdInput').val().split(',');
	var orderIds = $('#orderIdInput').val().split(',');
	var userNum = $('#userNumInput').val();

	var data = []
	for (var i = 0; i < userIds.length; i++) {
		var userId = userIds[i];
		var count = parseInt($("#distribution" + userId).val());
		if (count > userNum) {
			alert("超过可分配个数！！")
			return;
		}
		var orders = "";
		for (var j = 0; j < count; j++) {
			var orderId = orderIds[0];
			orders += orderId + ",";
			orderIds.shift()
		}
		data.push({
			userId : userId,
			orderIds : orders,
			count : count
		});
		userNum = userNum - count;
	}

	$.ajax({
		type : 'POST',
		url : "/ms/admin/order/bindOrder.do?distributionType=1&"
				+ searchContent,
		contentType : "application/json",
		async : false,
		data : JSON.stringify(data),
		success : function(data, status) {
			if (data.retCode == "200") {
				TipWindow.hide('#distributeBrokerPopup');
				TipWindow.showSingleWithContent('提交成功!');
			}else{
				errorCommit('#submitErrorContent1', data.retDesc);
				return;
			}
		}
	});
}

function getMerchantQuantityDistribution() {
	var searchContent = getSearchContent();
	var merchantIds = $('#merchantIdInput').val().split(',');
	var orderIds = $('#orderIdInput').val().split(',');
	var userNum = $('#userNumInput').val();
	
	var data = []
	for (var i = 0; i < merchantIds.length; i++) {
		var merchantId = merchantIds[i];
		var count = parseInt($("#distribution" + userId).val());
		if (count > userNum) {
			alert("超过可分配个数！！")
			return;
		}
		var orders = "";
		for (var j = 0; j < count; j++) {
			var orderId = orderIds[0];
			orders += orderId + ",";
			orderIds.shift()
		}
		data.push({
			merchantId : merchantId,
			orderIds : orders,
			count : count
		});
		userNum = userNum - count;
	}
	
	$.ajax({
		type : 'POST',
		url : "/ms/admin/order/bindOrder.do?distributionType=1&"
				+ searchContent,
		contentType : "application/json",
		async : false,
		data : JSON.stringify(data),
		success : function(data, status) {
			if (data.retCode == "200") {
				TipWindow.hide('#distributeMerchantPopup');
				TipWindow.showSingleWithContent('提交成功!');
			}else{
				errorCommit('#submitErrorContent2', data.retDesc);
				return;
			}
		}
	});
}