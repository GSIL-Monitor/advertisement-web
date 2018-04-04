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