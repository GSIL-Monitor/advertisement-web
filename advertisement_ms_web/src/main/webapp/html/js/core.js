var coreConfig;

/*
 * 错误提示
 */
var failErrorMessage = '网络错误，请稍后再试';
var mobileErrorMessage = '请填写正确的手机号码';
var smsCodeErrorMessage = '请填写手机验证码';
var passwordErrorMessage = '请填写6-16位密码';
var usesageErrorMessage = '请选择注册用途';
var nameErrorMessage = '请填写正确的姓名';
var genderErrorMessage = '请选择正确的性别';
var birthdayErrorMessage = '请选择正确的生日';
var emailErrorMessage = '请填写正确的邮箱地址';
var cityErrorMessage = '请填写正确的城市';
var provinceErrorMessage = '请填写正确的省份';
var identityCardErrorMessage = '请填写正确的身份证号';
var insuredSumErrorMessage = '请选择保额';


/*
 * 正常提示
 */
var sendSMSMessage = '验证码已发送，请注意查收';

function errorTip(id, message) {
    clearError(id);
    // var errorTip = $('<div class="error-tip clearfix"></div>');
    // errorTip.text(message);
    var errorTip = $(id).parent().next('.error-tip');
    errorTip.text(message);
    $(id).focus(function(){
        clearErrorByObject($(this));
    });
    if (isNotEmpty(selectInputMap.get(id))){
        $(selectInputMap.get(id)).focus(function(){
            clearErrorByObject($(this));
        });
        $(selectInputMap.get(id)).click(function(){
            clearErrorByObject($(this));
        });
    }
}

function errorCommit(id, message) {
    if ($(id).length <= 0) {
        TipWindow.showSingle(message);
        return;
    }
    errorTip(id, message);
    if (isNotEmpty(selectInputMap.get(id))){
        $(selectInputMap.get(id)).addClass('field-input-error');
    }
    $(id).parent().addClass('field-input-error');
}

function clearError(id) {
    $(id).parent().removeClass('field-input-error');
    $(id).parent().next('.error-tip').text('');
}

function clearErrorByObject(object) {
    object.parent().removeClass('field-input-error');
    object.parent().next('.error-tip').text('');
}

function isNumber(value) {
    var regex = /^(\-|\+)?[0-9]+.?[0-9]*$/;
    return regex.test(value);
}

/*
 * 校验模块
 */

//验证为空提示错误
function checkBlank(id, message) {
    clearError(id);
    if (isEmpty($(id).val())) {
        errorCommit(id, message);
        return false;
    } else {
        return true;
    }
}
//验证为空提示错误
function checkNormalInput(id, message) {
    return checkLength(id, 50, false, message);
}
//验证为空提示错误
function checkLength(id, length, empty, message) {
    clearError(id);
    var value = $(id).val();
    if ((!empty && isEmpty(value)) || value.length > length) {
        errorCommit(id, message);
        return false;
    } else {
        return true;
    }
}
//验证手机号码格式是否正确
function checkMobile(id) {
    clearError(id);
    var mobile = $(id).val();
    var mobileReg = {
        cn: /^1[2|3|4|5|7|8|9]\d{9}$/g,
        hk: /^[968]\d{7}$/,
        mo: /^[68]\d{7}$/,
        tw: /^09\d{8}$/,
        us: /^\d{10}$/,
        ca: /^\d{10}$/,
        uk: /^7\d{9}$/,
        de: /^\d{6,15}$/,
        au: /^\d{5,15}$/,
        jp: /^\d{5,15}$/,
        nz: /^\d{5,15}$/,
        fr: /^\d{5,15}$/,
        es: /^\d{5,15}$/,
        sg: /^\d{5,15}$/,
        kp: /^\d{5,15}$/,
        defReg: /^\d{5,15}$/
    };
    var regex = mobileReg.cn;
    var selectedCountry = $('#countryCode').val();
    switch (selectedCountry) {
        case 'cn':
            regex = mobileReg.cn;
            break;
        case 'hk':
            regex = mobileReg.hk;
            break;
        case 'mo':
            regex = mobileReg.mo;
            break;
        case 'tw':
            regex = mobileReg.tw;
            break;
        case 'us':
            regex = mobileReg.us;
            break;
        case 'ca':
            regex = mobileReg.ca;
            break;
        case 'uk':
            regex = mobileReg.uk;
            break;
        case 'de':
            regex = mobileReg.de;
            break;
        case 'au':
            regex = mobileReg.au;
            break;
        case 'jp':
            regex = mobileReg.jp;
            break;
        case 'nz':
            regex = mobileReg.nz;
            break;
        case 'fr':
            regex = mobileReg.fr;
            break;
        case 'es':
            regex = mobileReg.es;
            break;
        case 'sg':
            regex = mobileReg.sg;
            break;
        case 'kp':
            regex = mobileReg.kp;
            break;
        default:
            regex = mobileReg.cn;
    }
    if (!regex.test(mobile)) {
        errorCommit(id, mobileErrorMessage);
        return false;
    } else {
        return true;
    }
}

//验证密码格式是否正确
function checkPassword(id) {
    clearError(id);
    var psw = $(id).val();
    var regex = /^.{6,16}$/;
    if (!regex.test(psw)) {
        errorCommit(id, passwordErrorMessage);
        return false;
    } else {
        return true;
    }
}

//验证邮箱格式是否正确
function checkEmail(id) {
    clearError(id);
    var email = $(id).val();
    var regex = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if (!regex.test(email)) {
        errorCommit(id, emailErrorMessage);
        return false;
    } else {
        return true;
    }
}

//验证数字格式是否正确
function checkNumber(id, message) {
    clearError(id);
    var number = $(id).val();
    var regex = /^[0-9]+.?[0-9]*$/;
    if (!regex.test(number)) {
        errorCommit(id, message);
        return false;
    } else {
        return true;
    }
}

//验证数字格式是否正确
function checkInteger(id, message, min, max) {
    clearError(id);
    var number = $(id).val();
    try {
        var regex = /^[0-9]+$/;
        if (!regex.test(number)) {
            errorCommit(id, message);
            return false;
        }
        var value = parseInt(number);
        if (isNotEmpty(min) && value < min) {
            errorCommit(id, message);
            return false;
        }
        if (isNotEmpty(max) && value > max) {
            errorCommit(id, message);
            return false;
        }
        return true;
    } catch (e) {
        errorCommit(id, message);
        return false;
    }
}

//验证薪水格式是否正确
function checkSalary(id) {
    clearError(id);
    var value = $(id).val();
    var regex = /^[0-9]+$/;
    if (!regex.test(value)) {
        errorCommit(id, salaryErrorMessage);
        return false;
    } else {
        var salary = parseInt(value);
        if (salary > 100) {
            errorCommit(id, salaryErrorMessage);
            return false;
        }
        if (salary < 0) {
            errorCommit(id, salaryErrorMessage);
            return false;
        }
        return true;
    }
}

//验证身份证是否正确
function checkIdentityCard(id) {
    clearError(id);
    var value = $(id).val();
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (!reg.test(value)) {
        errorCommit(id, identityCardErrorMessage);
        return false;
    }
    return true;
}

//验证文本文件是否正确
function checkTextFileName(fileName) {
    var re = /\.doc$|\.docx$|\.pdf$|\.txt$|\.wps$/i;
    if (re.test(fileName)) {
        return true;
    }
    return false;
}

//验证图片文件是否正确
function checkImageFileName(fileName) {
    var re = /\.png$|\.jpg$|\.jpeg$/i;
    if (re.test(fileName)) {
        return true;
    }
    return false;
}

/**
 * 格式化方法，将{1}，{2}等替换成变量
 * 
 */
$.format = function(source, params) {
    if (arguments.length == 1)
        return function() {
            var args = $.makeArray(arguments);
            args.unshift(source);
            return $.format.apply(this, args);
        };
    if (arguments.length > 2 && params.constructor != Array) {
        params = $.makeArray(arguments).slice(1);
    }
    if (params.constructor != Array) {
        params = [params];
    }
    $.each(params, function(i, n) {
        source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
    });
    return source;
};

/*
    通用Map
*/
var Map = function(){
    this._entrys = new Array();
    this.put = function(key, value){
        if (key == null || key == undefined) {
            return;
        }
        var index = this._getIndex(key);
        if (index == -1) {
            var entry = new Object();
            entry.key = key;
            entry.value = value;
            this._entrys[this._entrys.length] = entry;
        }else{
            this._entrys[index].value = value;
        }
    };
    this.get = function(key){
        var index = this._getIndex(key);
        return (index != -1) ? this._entrys[index].value : null;
    };
    this.remove = function(key){
        var index = this._getIndex(key);
        if (index != -1) {
            this._entrys.splice(index, 1);
        }
    };
    this.clear = function(){
        this._entrys.length = 0;;
    };
    this.contains = function(key){
        var index = this._getIndex(key);
        return (index != -1) ? true : false;
    };
    this.getCount = function(){
        return this._entrys.length;
    };
    this.getEntrys =  function(){
        return this._entrys;
    };
   this._getIndex = function(key){
        if (key == null || key == undefined) {
            return -1;
        }
        var _length = this._entrys.length;
        for (var i = 0; i < _length; i++) {
            var entry = this._entrys[i];
            if (entry == null || entry == undefined) {
                continue;
            }
            if (entry.key === key) {//equal
                return i;
            }
        }
        return -1;
    };
}

/*
    通用弹窗
*/
var TipWindow = {
    //激活弹窗和遮罩时调用
    showTip: function (tipId) {
        if (isNull(tipId)) {
            tipId = '#tipArea';
        }
        function windowHeight() {
            var de = document.documentElement;
            return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
        }

        function windowWidth() {
            var de = document.documentElement;
            return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
        }

        function scrollY() {
            var de = document.documentElement;
            return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
        }

        function scrollX() {
            var de = document.documentElement;
            return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
        }

        $('.overlay').css('display', 'block');
        $('.tip-overlay').css('display', 'block');
        $(tipId).css('display', 'block');

        var w = $(tipId).width();
        var h = $(tipId).height();
        if (h == 0) {
            h = $(tipId).find('.tip-window').height();
        }
        var t = scrollY() + (windowHeight() / 2) - (h / 2);

        if (t < 0) {
            t = 0;
        }

        var l = scrollX() + (windowWidth() / 2) - (w / 2);

        if (l < 0) {
            l = 0;
        }

        // var overlayDiv = $('<div onclick="TipWindow.hide(\'' + tipId + '\')"></div>');
        // overlayDiv.addClass('overlay');
        // $('body').append(overlayDiv);
        
        $(tipId).css({
            left: l + 'px',
            top: t + 'px'
        });
        $('.tip-close').click(function(){
            TipWindow.hide(tipId);
        });
        $('.tip-cancel').click(function() {
            TipWindow.hide(tipId);
        });
    },

    show: function(title, leftText, rightText, leftHandler, rightHandler, timeout){
        var tipId = '#tipArea';
        $(tipId).empty();
        $(tipId).append($('.tip-window').clone());
        $(tipId).find('.tip-title').html(title);
        if (isNotEmpty(leftText)) {
            $(tipId).find('.tip-left-button-text').html(leftText);
        }
        if (isNotEmpty(rightText)) {
            $(tipId).find('.tip-right-button-text').html(rightText);
        }
        if (isNotNull(leftHandler)) {
            $(tipId).find('.tip-left-button').attr('onclick', leftHandler);
        }
        if (isNotNull(rightHandler)) {
            $(tipId).find('.tip-right-button').attr('onclick', rightHandler);
        }
        $(tipId).find('.tip-single-button').addClass('hide');
        this.showTip(tipId);
        if (timeout) {
            setTimeout(function() {
                TipWindow.hide();
            }, timeout);
        }
    },

    showSingle: function(title, text, handler, timeout) {
        var tipId = '#tipArea';
        $(tipId).empty();
        $(tipId).html($('#tipHtml').val());
        $(tipId).find('.tip-title').html(title);
        if (isNotEmpty(text)) {
            $(tipId).find('.tip-single-button-text').html(text);
        }
        if (isNotNull(handler)) {
            $(tipId).find('.tip-single-button').attr('onclick', handler);
        }
        $(tipId).find('.tip-left-button').addClass('hide');
        $(tipId).find('.tip-right-button').addClass('hide');
        this.showTip(tipId);
        if (timeout) {
            setTimeout(function() {
                TipWindow.hide();
            }, timeout);
        }
    },

    showSingleWithContent: function(title, text, content, handler, timeout) {
        var tipId = '#tipArea';
        $(tipId).empty();
        $(tipId).html($('#tipHtml').val());
        $(tipId).find('.tip-title').html(title);
        $(tipId).find('.tip-text').html(content);
        $(tipId).find('.tip-text').removeClass('hide');
        if (isNotEmpty(text)) {
            $(tipId).find('.tip-single-button-text').html(text);
        }
        if (isNotNull(handler)) {
            $(tipId).find('.tip-single-button').attr('onclick', handler);
        }
        $(tipId).find('.tip-left-button').addClass('hide');
        $(tipId).find('.tip-right-button').addClass('hide');
        this.showTip(tipId);
        if (timeout) {
            setTimeout(function() {
                TipWindow.hide();
            }, timeout);
        }
    },

    //关闭弹窗和遮罩时调用
    hide: function(tipId) {
        if (isNull(tipId)) {
            tipId = '#tipArea';
            $(tipId).empty();
        }
        $(tipId).attr('style', '');
        // $(tipId).css({
        //     display: 'none'
        // });
        // $(tipId).empty();
        
        $('.overlay').css('display', 'none');
        $('.tip-overlay').css('display', 'none');
    },

    onclickHide: function(tipId, button) {
        if (isNull(tipId)) {
            tipId = '#tipArea';
        }
        $(button).click(function(){
            $(tipId).css({
                display: 'none'
            });
            $('.overlay').css('display', 'none');
            $('.tip-overlay').css('display', 'none');
        });
    },

    isShow: function(tipId) {
        var style = $(tipId).attr('style');
        if (isNotNull(style) && style.indexOf("display: block") != -1) {
            return true;
        }
        return false;
    }
}

var selectInputMap = new Map();

function selectInput(inputId,hiddenId,selectList){
    selectInputMap.put(hiddenId, inputId);
    $(inputId).attr('readonly', 'true');
    $(inputId).focus(function(){
        $(hiddenId).focus();
        $(selectList).slideDown('slow');
        $(selectList).find('li').click(function(){
            $(inputId).val($(this).text());
            $(hiddenId).val($(this).val());
        });
    });
    $(inputId).blur(function(){
        $(selectList).slideUp('slow');
    });
}

function popupInput(inputId, hiddenId, selectList){
    selectInputMap.put(hiddenId, inputId);
    $(inputId).click(function(){
        clearError(hiddenId);
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

function showLoading() {
    TipWindow.showTip('.loading-window');
    $('.tip-overlay').attr('onclick', '');
    $('.overlay').css('display', 'none');
    setTimeout(function(){
        $('.tip-overlay').attr('onclick', 'hideLoading();');
    }, 3000);
}

function hideLoading() {
    TipWindow.hide('.loading-window');
    $('.tip-overlay').attr('onclick', 'TipWindow.hide();');
}

function isLoading() {
	return TipWindow.isShow('.loading-window');
}

var nengbunengbleshuale;
var smsTokenPrefix = '';
function setSmsTokenPrefix(value) {
    if (isNotNull(value)) {
       smsTokenPrefix += value + ''; 
    }
}

/**
 * [stopEventBubble 可抽离到公共js文件，暂时先放这里]
 * @param  {[type]} event
 * @return {[type]}         [description]
 */
function stopEventBubble(event){
    var e = event || window.event;
    if(e && e.stopPropagation) {
        e.stopPropagation();
    } else {
        e.cancelBubble = true;
    }
}

function setTimeConfig(startId, endId){
	if (isNotNull(startId)) {
		dateSelector(startId);
	}
	if (isNotNull(endId)) {
		dateSelector(endId);
	}
}

/**
 * [dateSelector 日期输入函数]
 * @param  {[type]} inputId [要填入日期的输入框的ID]
 * @return {[type]}         [description]
 */
var currDate = new Date(); //获取即时日期
var currYear = currDate.getFullYear(); //获取即时年份
var currMonth = currDate.getMonth() + 1; //获取即时月份
var workMinAge = 16;
var workMaxAge = 35;
var commonAddYear = 5;
var dateSelectorContent = '<div class="dateselector-ul"><ul class="year-area">{0}</ul><div class="month-area">{1}</div></div>';
function dateSelector(inputId){
    var yearContent = "";
    var maxYear, //日期下拉列表年份最大值
        minYear; //日期下拉列表年份最小值
    var detailDate, //用来存用户要填入的完整年月
        yearDate, //用来存用户要填入的年
        monthDate; //用来存用户要填入的月
    $(inputId).attr('readonly', 'true'); //日期的input不可以编辑，只能选择
    if (inputId == '#birthday') {
        maxYear = currYear - workMinAge; //16周岁可以找工作
    } else {
        maxYear = currYear + commonAddYear; //根据服务的受众的年龄做相应修改
    }
    minYear = currYear - workMaxAge; //根据服务的受众的年龄做相应修改
    for (var yearIndex = maxYear; yearIndex >= minYear; yearIndex--) {
        yearContent = yearContent + '<li>' + yearIndex + '</li>';
    }
    var monthContent = "";
    for (var monthIndex = 1; monthIndex <= 12; monthIndex++) {
        monthContent = monthContent + '<span class="date-disable">' + monthIndex + '月</span>';
    }
    //加载日期模块
    var area = $($.format(dateSelectorContent, yearContent, monthContent));
    //设置日期模块id，使用<inputId>_date_selector的方式
    area.attr('id', inputId.substring(1) + '_date_selector');
    $(inputId).parent('div').after(area);
    //点击input显示下拉框
    $(inputId).click(function(evt) {
        stopEventBubble(evt);
        $(inputId + '_date_selector').css('display', 'block');
        //当input有值时，根据值定位下拉框的滚动位置
        if ($(inputId).val()) {
            var defaultDate = $(inputId).val(); //后台传回到input中的完整年月
            var defaultDateArr = defaultDate.split('-'); //拆分后台传回年月并存入数组
            var defaultYear = defaultDateArr[0]; //从数组中取出年份
            var defaultMonth = defaultDateArr[1]; //从数组中取出月份
            var h; //滚动条滚动距离
            yearDate = defaultYear; //设置默认的年份
            monthDate = defaultMonth; //设置默认的月份
            if (defaultYear > (maxYear - 3)) {
                h = 0;
            }
            if (defaultYear < (minYear + 3)) {
                h = (maxYear - minYear - 7) * 26; //滚动到底，总li数 = maxYear - minYear, 7为能显示的li个数, 26为一个li的高度
            } else {
                h = (maxYear - defaultYear - 3) * 26;
	        }
            $('.year-area').scrollTop(h);
            //标记显示默认年份
            $.each(area.find('.year-area').children('li'), function() {
                if ($(this).text() == defaultYear) {
                    $(this).addClass('date-select');
                }
            });
            //标记显示默认月份
            $.each(area.find('.month-area').children('span'), function() {
                var thisMonth = $(this).text().replace('月', '');
                if (thisMonth < 10) {
                    thisMonth = '0' + thisMonth;
                }
                if (thisMonth == defaultMonth) {
                    $(this).removeClass('date-disable').addClass('date-select');
                } else if(isNotEmpty(defaultYear)) {
                    $(this).removeClass('date-disable').addClass('date-enable');
                }
            });
        }
        //当input值为空时，判断是否为出生日期input,如果是默认拉到底部，否则滚动·定位到当前年份
        else {
            if (inputId == '#birthday') {
                $('.year_area').scrollTop((maxYear - minYear - 15) * 26);
            } else {
                $('.year_area').scrollTop((maxYear - currYear - 3) * 26);
            }
        }
    });
    //单击下拉框以外任意位置，隐藏下拉框
    $(document).click(function() {
        $(inputId + '_date_selector').css('display', 'none');
    });
    //选择填入年份
    var yearArea = area.find('.year-area');
    var yearLi = yearArea.children('li');
    var monthArea = area.find('.month-area');
    var monthLi = monthArea.children('span');
    for (i = 0; i < yearLi.length; i++) {
        yearLi[i].onclick=function(evt) {
            stopEventBubble(evt);
            for (i = 0; i < yearLi.length; i++) {
                yearLi[i].className = '';
            }
            for (j = 0; j < monthLi.length; j++) {
                monthLi[j].className = 'date-enable';
            }
            $(this).addClass('date-select');
            yearDate = $(this).text();
        };
    }
    //选择填入月份
    for (j = 0; j < monthLi.length; j++) {
        monthLi[j].onclick=function(evt) {
            if (isNull(yearDate)) {
                stopEventBubble(evt);
                return;
            }
            for (j = 0; j < monthLi.length; j++) {
                monthLi[j].className = '';
            }
            $(this).addClass('date-select');
            monthDate = $(this).text().replace('月','');
            if(monthDate < 10) {
                monthDate = '0' + monthDate;
            }
            detailDate = yearDate + '-' + monthDate;
            $(inputId + '_date_selector').css('display', 'none');
            $(inputId).val(detailDate);
        };
    }
}

/**
 * [ueditorContent 编辑器]
 * @type {String}
 */
var ueditorContent = '<iframe id="workEditor{0}" src="/html/umeditor/resumeEditor.htm" frameborder="0" style="width: 440px; height: 210px;" ></iframe>';

function createUEditor(index) {
    return $($.format(ueditorContent, index));
}

function getUEditorContent(iframeId) {
    var childIFrame = document.getElementById(iframeId);
    if (isNull(childIFrame)) {
        return;
    }
    return childIFrame.contentWindow.getText();
}

function setUEditorContent(iframeId, content) {
    var childIFrame = document.getElementById(iframeId);
    return childIFrame.contentWindow.setText();
}

function isNull(value) {
    if (typeof(value) == 'undefined') {
        return true;
    }
    if (value == null) {
        return true;
    }
    return false;
}

function isNotNull(value) {
    return !isNull(value);
}

function isEmpty(value) {
    if (isNull(value)) {
        return true;
    }
    if (value == '') {
        return true;
    }
    return false;
}

function isNotEmpty(value) {
    return !isEmpty(value);
}

function endWith(content, end) {
    var value = content.substring(content.length - end.length, content.length);
    return value == end;
}

$.uploadImage = function(control, uploadFormId, iframeId, loadSuccess, handleFail) {
    if(navigator.appName == "Microsoft Internet Explorer") {
        control.select();
        var fileName = document.selection.createRange().text;
    }
    else {
        var file = control.files[0];
        var fileName = file.name;
    }
    popWindow.showPopTipWithContent('#reminderTip', '上传中，请稍后...');
    if (checkImageFileName(fileName)) {
        $(uploadFormId).submit();
        $.checkUploadFile(iframeId, function(data){
            popWindow.hidePopTip('#reminderTip');
            loadSuccess(data);
        });
    } else {
        handleFail();
        popWindow.showPopTipWithContent('#errorTip', '请上传png、jpg格式的头像');
    }
}

/*
 *  无刷新上传文件
 */
$.checkUploadFile = function(iframeId, handleSuccess, handleFail){
    var intervalTimes = 0;
    var errorTimes = 0
    intervalTimes = 0;
    var interval =setInterval(function(){
        if (intervalTimes++ == 60) {
            showTipDialogAndSetContent('网络错误', true);
            destroyCheckFile(true);
        } else {
            try{
                if (checkResultAndShowDialog(iframeId, handleSuccess, handleFail)){
                    destroyCheckFile(false);
                }
            } catch (e) {
                if (errorTimes++ == 10) {
                    showTipDialogAndSetContent('网络错误，请稍后再试', true);
                    destroyCheckFile(true);
                }
            }
        }
    }, 500);

    function destroyCheckFile(isFail) {
        clearInterval(interval);
        if (isFail && isNotNull(handleFail)) {
            handleFail();
        }
        var innerDoc = getCommitIFrame(iframeId);
        var body = findFirstElement(innerDoc, 'body');
        body.innerHTML = '';
    }

    function getCommitIFrame(iframeId) {
        var formCommitIframe = document.getElementById(iframeId);
        return formCommitIframe.contentDocument || formCommitIframe.contentWindow.document;
    }

    function checkResultAndShowDialog(iframeId, handleSuccess, handleFail) {
        var innerDoc = getCommitIFrame(iframeId);
        var body = findFirstElement(innerDoc, 'body');
        if (body.innerHTML.length == 0) {
            return false;
        }
        var pre = findFirstElement(innerDoc, 'pre');
        if (pre == null) {
            pre = findFirstElement(innerDoc, 'textarea');
        }
        if (pre != null) {
            var data = JSON.parse(pre.innerHTML);
            var content = data.html;
            if (isEmpty(content)) {
                content = data.error_msg;
            }
            if (isEmpty(content)) {
                content = data.retDesc;
            }
            if (data.retcode == '200' || data.retCode == '200') {
                if (isNull(handleSuccess)) {
                    showTipDialogAndSetContent(content, false);
                } else {
                    handleSuccess(data);
                }
                return true;
            } else {
                showTipDialogAndSetContent(content, true);
                if (isNotNull(handleFail)) {
                    handleFail();
                }
                return true;
            }
        }
        showTipDialogAndSetContent('网络错误，请稍后再试', true);
        destroyCheckFile(true);
        return true;
    }

    function showTipDialogAndSetContent(content, error) {
        popWindow.hidePopTip('#uploadResumeTip');
        if (error) {
            popWindow.showPopTipWithContent('#errorTip', content);
        }else {
            popWindow.showPopTipWithContent('#okTip', content);
        }
    }

    function findFirstElement(root, tagName){
        var tags = root.getElementsByTagName(tagName);
        if (tags.length > 0) {
            return tags[0];
        }
        return null;
    }

}


$.checkImageLoad = function(imageControl, callback, imagePath) {
    var t_img; // 定时器
    var times = 0;
    var isLoad = true;
    isImgLoad(imageControl, callback, imagePath);
        // 判断图片加载的函数
    function isImgLoad(imageControl, callback, imagePath){
        $(imageControl).attr('src', imagePath + '?t=' + Math.random());
        // $('#avatarPreview').attr('src', imagePath + '?t=' + Math.random());
        // 注意我的图片类名都是cover，因为我只需要处理cover。其它图片可以不管。
        // 查找所有封面图，迭代处理
        // 25秒图片加载不出来，就返回错误提示
        if (times++ == 50) {
            clearTimeout(t_img);
            popWindow.hidePopTip('#reminderTip');
            popWindow.showPopTipWithContent('#errorTip', '网络错误，请稍后再试');
            return;
        }
        $(imageControl).each(function(){
            // 找到为0就将isLoad设为false，并退出each
            if(this.height === 0){
                isLoad = false;
                return false;
            }
        });
        // 为true，没有发现为0的。加载完毕
        if(isLoad){
            clearTimeout(t_img); // 清除定时器
            // 回调函数
            callback();
        // 为false，因为找到了没有加载完成的图，将调用定时器递归
        }else{
            isLoad = true;
            t_img = setTimeout(function(){
                isImgLoad(imageControl, callback, imagePath); // 递归扫描
            },500); // 我这里设置的是500毫秒就扫描一次，可以自己调整
        }
    }
}

/*
 *
 * 发送短信验证码
 *
*/

//60秒倒计时
var $t = 60;

function showTime() {
    $('#sendSmsButton').attr('disabled', 'disabled');
    $('#sendSmsButton').addClass('sms-button-disabled');
    $('#sendSmsButton').html($t + '秒');
    $t -= 1;
    if ($t <= 0) {
        $('#sendSmsButton').removeAttr('disabled');
        $('#sendSmsButton').removeClass('sms-button-disabled')
        $('#sendSmsButton').html('获取验证码');
        $t = 60;
        return false;
    }
    setTimeout("showTime()", 1000);
}


function sendSmsCode(voice, handleFunction) {
    if (checkMobile('#mobile')){
        if (voice == 'true') {
            TipWindow.showSingle('已获取语音验证码，请注意接听电话');
        } else {
            showTime();
        }
        $.ajax({
            type:"POST",
            dataType:"json",
            url: "/interfaces/sms/send.html",
            async:false,
            data:{
                mobile: $('#mobile').val(),
                voice: voice,
                tip: $('#smsTipContent').val(),
                smsToken: $('#' + $('#smsTokenId').val()).val() + smsTokenPrefix
            },
            success:function(data){
                if (typeof(handleFunction) == 'undefined') {
                    if(data.retCode === "200"){
                        errorTip('#username', sendSMSMessage, 2);
                        $('#voiceCode').css('display', 'block');
                        //sendSms(phoneNumber);
                    } else{
                        $t = 0;
                        errorCommit('#mobile', data.retDesc, 2);
                        return;
                    }
                } else {
                    handleFunction(data);
                }
            }
        });
    }
}