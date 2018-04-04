$(function() {
    $('.filterBoxLi').each(function() {
        $(this).click(function(e) {
            e.stopPropagation();
            var id = this.id.replace('filterBoxLi', '');
            if ($('#filter-list' + id).css('display') == 'block') {
                $('.filter-list').css('display', 'none');
            } else {
                $('.filter-list').css('display', 'none');
                $('#filter-list' + id).css('display', 'block');
            }
        });
        $(this).blur(function(e){
            e.stopPropagation();
            $('.filter-list').css('display', 'none');
        })
    });

    //公司详情页隐藏显示更多
    $('.company-detail').each(function() {
        if ($(this).height() > 13) {
            $(this).css('height', '13rem');
            $(this).find('.company-detail-more').css('display', 'block');
            $(this).find('.company-detail-more').attr('name','1');
        }
    });
    $('.company-detail-more').click(function() {
        if($(this).attr('name') == '1'){
            $(this).attr('name','0');
            $('.company-detail').css('height', 'auto');
            $(this).html('收起').css('background-position', '0 0');
        }
        else if($(this).attr('name') == '0'){
            $(this).attr('name','1');
            $('.company-detail').css('height', '13rem');
            $(this).html('展开').css('background-position', '0 -16px');
        }
    });


    $('div[name="job_item"]').click(function(){
        var id = $(this).attr('value');
        if (isNotEmpty(id)) {
            location.href="/m/job/detail/" + id + ".html";
        }
    })


    $('#countryFlagBtn').click(function() {
        var isSelect = $('#countrySelectList').attr('name');
        if(isSelect == 1) {
            $('#countrySelectList').attr('name', '0');
            popWindow.showPopTip('#countrySelectPopTip');
        }
    });

    // $('.poptip-select-head').click(function() {
    //     $('#countrySelectList').attr('name', '1');
    //     popWindow.hidePopTip('#countrySelectPopTip');
    // });

    $('#countrySelectList li').each(function() {
        $(this).click(function() {
            var areaCode = $(this).val();
            var areaName = $(this).attr('name');
            $('#iconSelect').removeClass().addClass('icon-' + areaName);
            $('#areaCode').text('+' + areaCode);
            $('#countryCode').val(areaName);
            $('#countrySelectList').attr('name', '1');
            popWindow.hidePopTip('#countrySelectPopTip');
        });
    });

    if (isHideDownloadApp()) {
        $('#appDownloadArea').addClass('hide');
    }

});

var currentSelectorParams;
var totalPage;
var currentPage;
var pageSize;

function loadJobData() {
    currentPage ++;
    if (currentPage <= totalPage) {
        $.ajax({
            type : 'POST',
            url : '/m/job/ajaxList.html?'+currentSelectorParams,
            data : {
                pn : currentPage,
                ps : pageSize
            },
            success : function(data) {
                handleLoadDataSuccess(data);
            }
        });
    }
}

function loadData(url) {
    currentPage ++;
    if (currentPage <= totalPage) {
        $.ajax({
            type : 'POST',
            url : url,
            data : {
                page : currentPage,
                limit : pageSize
            },
            success : function(data) {
                handleLoadDataSuccess(data);
            }
        });
    }
}

function handleLoadDataSuccess(data){
    var jobList = $(data);
    var pageNoNode = jobList.find('input[name="pageNo"]');
    var totalPageNode = jobList.find('input[name="totalPage"]');
    if (typeof(pageNoNode) == 'undefined') {
        alert('加载失败');
    }
    currentPage = parseInt(pageNoNode.val());
    totalPage = parseInt(totalPageNode.val());
    jobList.find('div[name="job_item"]').each(function(){
        $('#jobContainer').append($(this));
        // $('#jobContainer').find('div[name="job_item"]').last().after($(this));
        var id = $(this).attr('value');
        $(this).click(function(){
            if (isNotEmpty(id)) {
                location.href="/m/job/detail/" + id + ".html";
            }
        })
    });
    if (currentPage >= totalPage) {
        $('#load_data').text('没有更多数据了');
    }
}

function applyJob() {
    checkLogin(function(){
        $.ajax({
            type : 'POST',
            url : '/m/applicant/apply/check.html',
            data : {
                jobId : $('#jobId').val()
            },
            dataType : 'json',
            beforeSend : function() {

            },
            success : function(data) {
                if (data.retCode == '200') {
                    $('#infoHandleOK').attr('onclick', 'popWindow.hidePopTip("#infoTip");doApplyJob();');
                    popWindow.showPopTipWithContent('#infoTip', '是否确认投递？');
                } else if (data.retCode == '338') {
                    $('#warningOKContent').text('继续完善');
                    $('#warningHandleBack').attr('onclick', 'popWindow.hidePopTip("#warningTip");');
                    $('#warningHandleOK').attr('onclick', 'popWindow.hidePopTip("#warningTip");window.open("/m/resume/detail.html?check=false");');
                    popWindow.showPopTipWithContent('#warningTip', data.retDesc);
                    return false;
                } else if (data.retCode == '389') {
                    $('#warningOKContent').text('去填写');
                    $('#warningHandleBack').attr('onclick', 'popWindow.hidePopTip("#warningTip");');
                    $('#warningHandleOK').attr('onclick', 'popWindow.hidePopTip("#warningTip");window.open("/m/resume/baseInfo.html");');
                    popWindow.showPopTipWithContent('#warningTip', data.retDesc);
                } else {
                    popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                    return false;
                }
            }
        });
    }, wapLogin);
}

function doApplyJob() {
    $.ajax({
        type : 'POST',
        url : '/m/applicant/apply/do.html',
        data : {
            jobId : $('#jobId').val()
        },
        success : function(data) {
            if (data.retCode == '200') {
                popWindow.showPopTipWithContent('#okTip', '投递成功');
            } else {
                popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                return false;
            }
        }
    });
}

function isHideDownloadApp() {
    return document.cookie.indexOf('closeDownload') != -1;
}

function closeAppDownloadArea() {
    $('#appDownloadArea').addClass('hide');
    var d = new Date();
    d.setTime(d.getTime() + (24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = "closeDownload=true; " + expires;
}


function selectInput(inputId, hiddenId, selectList){
    selectInputMap.put(hiddenId, inputId);
    $(inputId).click(function(){
        TipWindow.showTip(selectList);
        $(selectList).find('li').click(function(){
            $(inputId).text($(this).text());
            $(hiddenId).val($(this).attr('value'));
            TipWindow.hide(selectList);
        });
        $('.poptip-select-head').click(function() {
            TipWindow.hide(selectList);
        });
    });
}