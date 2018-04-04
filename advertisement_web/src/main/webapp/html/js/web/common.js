$(function(){
    $('#countryFlag').click(function() {
        var isSelect = $('#countrySelectUl').attr('name');
        if(isSelect == 1) {
            $('#countrySelectUl').css({'display': 'block', 'width': $('.form_group').width()}).attr('name', '0');
        }
        else {
            $('#countrySelectUl').css('display', 'none').attr('name', '1');
        }
    });

    $('#countrySelectUl li').each(function() {
        $(this).click(function() {
            var areaCode = $(this).val();
            var areaName = $(this).attr('name');
            $('#iconSelect').removeClass().addClass('icon-' + areaName);
            $('#areaCode').text('+' + areaCode);
            $('#countryCode').val(areaName);
            $('#countrySelectUl').css('display', 'none').attr('name', '1');
        });
    });
});
var nengbunengbleshuale;
var smsTokenPrefix = '';
function setSmsTokenPrefix(value) {
    if (isNotNull(value)) {
       smsTokenPrefix += value + ''; 
    }
}
function getEducationIndex() {
    return parseInt($('#educationIndex').val());
}

function getWorkExperienceIndex() {
    return parseInt($('#workExperienceIndex').val());
}

function getMobile() {
    var areaCode = $('#areaCode').text();
    if (areaCode != '+86') {
        return areaCode.replace('+', '00') + $('#username').val();
    } else {
        return $('#username').val();
    }
}

function logout() {
    $.ajax({
            type : 'POST',
            url : '/user/logout.html',
            data : {
            },
            success : function(data) {
                if (data.retCode == '200') {
                    location.reload();
                }
            }
        });
}

function checkLogin(handleSuccess, handleFail) {
	$.ajax({
            type : 'POST',
            url : '/user/hasLogin.html',
            data : {
            },
            success : function(data) {
                if (data.retCode == '200') {
                    handleSuccess();
                } else {
                	handleFail();
                }
            }
        });
}

function webLogin() {
	location.href='/user/login.html?returnUrl=' + encodeURI(location.href);
}

function wapLogin() {
	location.href='/m/user/login.html?returnUrl=' + encodeURI(location.href);
}

function ajaxLogin() {

}

function isMobile() {
	return isNotNull($('#isMobilePage')) && $('#isMobilePage').val() == 'true';
}

function favorite(jobId, add) {
    if (add) {
        var url = '/applicant/favorite/add.html';
    } else {
        var url = '/applicant/favorite/remove.html';
    }
    var loginFunction = webLogin;
    if (isMobile()) {
    	loginFunction = wapLogin;
    } 
    checkLogin(function(){
        $.ajax({
            type : 'POST',
            url : url,
            data : {
                jobId : jobId
            },
            success : function(data) {
                if (data.retCode == '200') {
                    if (add) {
                        $('#favorite_block').css('display', 'none');
                        $('#has_favorite_block').css('display', 'block');
                    } else {
                        $('#favorite_block').css('display', 'block');
                        $('#has_favorite_block').css('display', 'none');
                    }
                } else {
                    popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                }
            }
        });
    }, webLogin);
}

function checkPublishJob() {
    checkLogin(function(){
        $.ajax({
            type : 'POST',
            url : '/recruiter/checkPublishJob.html',
            data : {
            },
            success : function(data) {
                if (data.retCode == '200') {
                    location.href="/recruiter/job/new.html"
                } else {
                    if (data.retCode == '358' || data.retCode == '359') {
                        location.href="/recruiter/info/index.html";
                    } else if (data.retCode == '377') {
                        $('#warningHandleOK').attr('onclick', 'location.href="/recruiter/info/index.html"');
                        popWindow.showPopTipWithContent('#warningTip', data.retDesc);
                    }else {
                        popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                    }
                }
            }
        });
    }, webLogin);
}


function sendFeedback() {
    $.ajax({
        type: 'POST',
        url: '/common/feedback/commit.html',
        data: {
            mobile: $('#feedbackMobile').val(),
            name: $('#feedbackName').val(),
            email: $('#feedbackEmail').val(),
            message: $('#feedbackMessage').val()
        },
        beforeSend: function() {

        },
        success: function(data) {
            if (data.retCode == '200') {
                popWindow.hidePopTip('#feedbackPopTip');
                popWindow.showPopTipWithContent('#okTip', "谢谢您的反馈，工作人员会尽快与您联系");
            } else {
                popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                return false;
            }
        }
    });
}

function checkPublishJob() {
    $.ajax({
        type: 'POST',
        url: '/recruiter/info/checkPublishJob.html',
        data: {
        },
        beforeSend: function() {

        },
        success: function(data) {
            if (data.retCode == '200') {
                location.href = "/recruiter/job/new.html";
            } else {
                popWindow.showPopTipWithContent('#errorTip', data.retDesc);
                return false;
            }
        }
    });
}


function changeEdit(edit, showId, hideId) {
    if (edit) {
        if (edit_lock) {
            return false;
        }
        edit_lock = true;
    } else {
        edit_lock = false;
    }
    $(hideId).css('display', 'none');
    $(showId).css('display', 'block');
}


//60秒倒计时
var $t = 60;

function showTime() {
    $('#getIcode').attr('disabled', 'disabled');
    $('#getIcode')[0].value = $t + '秒后重新发送';
    $t -= 1;
    if ($t <= 0) {
        $('#getIcode').removeAttr('disabled');
        $('#getIcode')[0].value = '获取验证码';
        $t = 60;
        return false;
    }
    setTimeout("showTime()", 1000);
}
