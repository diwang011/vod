
var gLanguageUsedCookieName = "languageUsed";
var gSkinUsedCookieName = "skinUsed";
var gDefaultLanguage = "zh-cn";
var g_applicationContext = "/omniv4/";
var g_htmlPath = "resources/html/";
var g_imagesPath = "resources/image/";
var g_cssPath = "resources/css/";
var g_jsPath = "resources/js/";
var g_mockPath = "resources/mock/";
var g_modelPath = "resources/js/models/";
var g_helpURL = "http://www.edayun.cn/omni_document/systemhelp/";
var g_resouceURL = "http://www.edayun.cn/omni_document/resourcehelp/";
var g_millisecondsForTimeout = 60 * 60 * 1000;
var g_currentPageId = "myomni";
var g_currentPageNames = [];
var g_animate_duration = 500;
var g_sessionUser = null;
var g_sessionId = null;
var g_datePattern = "yyyy-MM-dd";
var g_dateTimePattern = "yyyy-MM-dd hh:mm:ss";
var g_nonceToken = "";
var g_defaultPrintLang = "";
var g_debugMode = false;
var g_silientRefreshFlag = false;
var g_reconnectionFlag = false;
var ELEMENT_OPTION = "<option value=''> - </option>";
var timeOutSetting;

breakpoint = function(){
  	//put your breakpoint here, you can turn it on or off
    if (g_debugMode){
			debugger;
		}
}
$.fn.hasAttr = function (name, val) {
	if (val) {
		return $(this).attr(name) === val;
	}
	return $(this).attr(name) !== undefined;
};

function resetTheTimeout() {
	clearTimeout(timeOutSetting);
	timeOutSetting = setTimeout(function () {
		showAlertWindow(i18nmsg.text("text.alert.timeout"));
		location.assign(g_loginURL);
	}, g_millisecondsForTimeout);
}

function ajaxCallForEventWithJsonData(that, url, data, waitContainer, callbackforsuccessful, resetPageTimeout,	withAjaxwating,callbackforfail) {
  debugger;
	withAjaxwating = (withAjaxwating === null || withAjaxwating === undefined) ? true : withAjaxwating;
	var waitingFlag = $("#ajaxRequestWaiting");
	if (withAjaxwating) {
		waitingFlag.show();
	}
	if (waitContainer && withAjaxwating) {
		waitContainer.append(waitingFlag.clone().show().removeAttr("id"));
	}

	if (that) {
		url = that.baseUrl + url;
	}

	if (resetPageTimeout == true) {
		resetTheTimeout();
	}
	debugger;
	var req = {};
	req.nonceToken = g_nonceToken;
	req.data = data;
	$.ajax({
		type: "post",
		url: url, // 写后台给的地址
		data: JSON.stringify(req),
		contentType: "application/json",
		dataType: "json",
		xhrFields: { withCredentials: true },
	}).success(process).error(ajaxError);

	function process(ajaxResponse, status, xhr) {debugger;
		waitingFlag.hide();

		if (!ajaxResponse) {
			debugger;
			showAlertWindow(i18nmsg.text("system.error"));
			return;
		}
		// g_nonceToken = ajaxResponse.nonceToken; //in the future the nonce token
		// will base on request. it is base on session for now.
		if (ajaxResponse.errors == null || ajaxResponse.errors.length == 0 || ajaxResponse.errors[0] == null) {
			callbackforsuccessful(that, ajaxResponse.data, ajaxResponse.modelName);
		} else {
			var failWav = $("#voiceFail");
			if (failWav && failWav.length) {
				failWav[0].play();
			}
			var errorMsg = "";
			var errorList = ajaxResponse.errors;
			for (var i = 0; i < errorList.length; i++) {
				errorMsg += errorList[i].refNum+"@"+ errorList[i].code + ": " + errorList[i].desc + "\n";
			}
			showAlertWindow(errorMsg);
			if (callbackforfail)
			  callbackforfail(that,ajaxResponse, ajaxResponse.modelName);
		}
		if (waitContainer) {
			waitContainer.find(".sys-waiting").remove();
		}
	}

	function ajaxError(xhrReq, textStatus, errorThrown) {

		var rs; //error response
		if (xhrReq.responseText && IsJsonString(xhrReq.responseText)) {
			rs = JSON.parse(xhrReq.responseText);
		}
		if(rs && rs.errors){
			//unblock
			removeWait();
			var errorObj=rs.errors[0];
			debugger;
			var content = errorObj.refNum + "@" + errorObj.code +"\n"+errorObj.desc;
			art.dialog({
				title: i18nmsg.text("system.error"),
				content:  "<textarea class='js-copytextarea' rows='5' cols='50'>" + content +"</textarea>",
				okValue: i18nmsg.text('common.btn.copyclose'),
				ok: function () {
					var textarea = document.querySelector('.js-copytextarea');
					textarea.select();
					document.execCommand('copy')
				},
			});
		} else {
			//其他问题302/redirect
			//debugger;
			console.log("Error StatusCode=" + xhrReq.status);
			debugger;
			if (xhrReq.status !=0 && textStatus != 'parsererror'){
				if (g_reconnectionFlag){
					//ignore
					//showErrorDialog();
					return;
				}
				g_reconnectionFlag = true;
//				var dlg = alert({
//					title: i18nmsg.text("common.dlg.ttl.tips"),
//					content:  i18nmsg.text("system.resume.connection"),
//					okValue: i18nmsg.text('common.btn.manualrefresh'),
//					ok: function () {
//						location.reload();
//					},
//				});
//				var curUserId = g_sessionUser?g_sessionUser.accountId:null;
//				silentRefresh(function(){
//					refreshSessionUser(curUserId,
//						function(){ //refresh success
//							showMessageWindow(i18nmsg.text("system.resume.success"),i18nmsg.text("common.dlg.ttl.msg"),5000);
//							dlg.close();
//							removeWait();
//							g_reconnectionFlag = false;
//						},
//						function(){ //refresh fail
//						dlg.close();
//						//showErrorDialog();
//						});
//				});
				//10秒后允许手动刷新
				setTimeout(function() {
					removeWait();
					//dlg.close();
				},10000);
			}else {
				//showErrorDialog();
			}
		}
		return;
		//----------------
		function removeWait(){
			//unblock
			waitingFlag.hide();

			if (waitContainer) {
				waitContainer.find(".sys-waiting").remove();
			}
		}
		function showErrorDialog(){
			removeWait();
			var content = i18nmsg.text("system.error.do") + ",HTTP_SC=" + xhrReq.status;
			art.dialog({
				title: i18nmsg.text("system.error"),
				content:  "<textarea class='js-copytextarea' rows='6' cols='50'>" + content +"</textarea>",
				okValue: i18nmsg.text('common.btn.manualrefresh'),
				ok: function () {
					var textarea = document.querySelector('.js-copytextarea');
					textarea.select();
					document.execCommand('copy')
					location.assign(g_loginURL);
				},
				cancelValue: i18nmsg.text('common.btn.cancel'),
				cancel: function(){},
			});
		}
	}

}

function isString(s) {
	return typeof (s) === 'string' || s instanceof String;
}

function removeNulls(obj) {
	if (obj instanceof Array) {
		// don't pass in arrays
		return obj;
	}
	var objClone = {};
	for (var i in obj) {
		if (obj[i]) {
			objClone[i] = obj[i];
			continue;
		}
		objClone[i] = "";
	}
	return objClone;
}

function makeElmCenter(elmId, top) {
	if (elmId) {
		var el = $("#" + elmId), waitingFlag = $("#ajaxRequestWaiting");
		var paddingLeft = el.css("padding-left");
		paddingLeft = paddingLeft.substring(0, paddingLeft.indexOf("px"));
		var paddingRight = el.css("padding-right");
		paddingRight = paddingRight.substring(0, paddingRight.indexOf("px"));

		var elmWidth = el.width() + parseInt(paddingLeft) + parseInt(paddingRight);
		var windowWidth = waitingFlag.width();
		el.css({
			'left': (windowWidth - elmWidth) / 2,
			'width': elmWidth + 'px'
		});
		if (top) {
			el.css({
				'position': 'relative',
				"top": top
			});
		}
	}
}

function popupWindowOpen(contentId, focusedElmId) {
	$("#" + contentId + "Overlay").show();
	// $("#" + contentId + "Wrap").animate({
	// opacity : 'toggle'
	// }, {
	// duration : g_animate_duration
	// });
	$("#" + contentId + "Wrap").show();
	if (focusedElmId != null && focusedElmId.length > 0) {
		$("#" + focusedElmId).focus();
	}
	makeElmCenter(contentId, "50px");
}

function popupWindowClose(contentId) {
	if ($("#" + contentId + "Wrap:visible").length == 1) {
		$("#" + contentId + "Wrap").hide();
		$("#" + contentId + "Overlay").animate({
			opacity: 'toggle'
		}, {
			duration: g_animate_duration
		});
	}
}

function breakpoint() {
	if (g_debugMode) {
		debugger;
	}
}

function showAlertWindow(message, title) {

	var winWrap = $("#main-alert-win"),
		titleWrap = $("#main-alert-title"),
		msgWrap = $("#main-alert-message"),
		confirmWrap = $("#main-alert-btn-confirm");

	if (winWrap.length == 0) {
		art.dialog({
			title: title || i18nmsg.text("common.dlg.ttl.warn"),
			content: message,
			fixed: true,
			lock: true
		}).time(1500);
		return;
	}
	if (title) {
		titleWrap.html(title);
	} else {
		titleWrap.html(i18nmsg.text("alert.title"));
	}
	if (message) {
		msgWrap.html(message);
	}
	var f = msgWrap.css("font");
	var w = message.width(f) > 400 ? message.width(f) : 400;
	w = message.width(f) > 700 ? 700 : w;
	winWrap.css({
		"width": (w + 40) + "px"
	});
	winWrap.show();
	popupWindowOpen("main-alert-win");
	confirmWrap.unbind("click");
	confirmWrap.focus();
	confirmWrap.keypress(function (event) {
		if (event.keyCode == 13) {
			confirmWrap.click();
		}
	});
	confirmWrap.click(function () {
		popupWindowClose("main-alert-win");
	});
}

function showConfirmWindow(message, title, confirmHandler, cancelHandler) {

	var titleWrap = $("#main-confirm-title"),
		msgWrap = $("#main-confirm-message");

	if (title) {
		titleWrap.text(title);
	} else {
		titleWrap.text(i18nmsg.text("confirm.title"));
	}
	if (message) {
		msgWrap.text(message);
	}
	var f = msgWrap.css("font");
	var w = message.width(f) > 400 ? message.width(f) : 400;
	w = message.width(f) > 700 ? 700 : w;
	$('#main-confirm-win').css({
		"width": (w + 40) + "px"
	});
	popupWindowOpen("main-confirm-win");

	$("#main-confirm-btn-cancel").unbind("click").click(function () {
		popupWindowClose("main-confirm-win");
		if (cancelHandler && (typeof cancelHandler) === "function") {
			cancelHandler();
		}
	});
	$("#main-confirm-btn-confirm").unbind("click").click(function () {
		popupWindowClose("main-confirm-win");
		if (confirmHandler && (typeof confirmHandler) === "function") {
			confirmHandler();
		}
	});

}

function showMessageWindow(message, title, delay) {

	var titleWrap = $("#main-information-title"),
		msgWrap = $("#main-information-message"),
		infoWrap = $('#main-information');

	if (title) {
		titleWrap.text(title);
	} else {
		titleWrap.text(i18nmsg.text("information.title"));
	}
	message = "" || message;
	msgWrap.text(message);
	var f = msgWrap.css("font");
	var w = message.width(f) > 400 ? message.width(f) : 400;
	w = message.width(f) > 700 ? 700 : w;
	infoWrap.css({
		"width": (w + 40) + "px"
	});
	if (!delay)
		delay=6000;
	makeElmCenter('main-information');
	infoWrap.animate({
		bottom: '0px'
	}, 1000, function () {
		infoWrap.delay(delay).animate({
			bottom: '-145px'
		}, 500);
	});
}

function gotoPageByNavIndex(topIndex, subIndex, lv3Index) {
	DnmcMenuInitTool.frameInit();
	DnmcMenuInitTool.dnmcMenuInit($("#contentSubMenu").find(".m-snav-item"), $("#contentLv3Menu").find(".m-snav-item"), topIndex,
		subIndex, lv3Index);
}
function addAllOmenu() {
	var allMenuLst = $("#menu").find("[i18nmsg]");
	var cnt = allMenuLst.length;
	if (allMenuLst == null || cnt < 1) {
		return;
	}
	for (var i = 0; i < cnt; i++) {
		var a = $(allMenuLst[i]);
		var i18ntext = a.attr("i18nmsg");
		a.closest("li").attr("omenu", i18ntext);
	}
}

function hideAllMenuRelatedElement() {
	
	if(!g_sessionUser["menuList"]){
		g_sessionUser["menuList"] = ["menu.momni", "menu.momni.usrfl.usbsi", "menu.front", "menu.momni.usrfl.usbpwd"];
	}
	
	var menuListForAccount = g_sessionUser["menuList"];
	var menu = $("#menu");
	var allMenuLst = menu.find("[omenu]");
	if (menuListForAccount == null) {
		if (g_sessionUser["accountType"] == "SUB") {
			allMenuLst.addClass("noOmenu").remove();
		} else if (g_sessionUser["accountType"] == "MAIN") {
			$("#usmg").addClass("noOmenu").remove();
		} else {
			menu.find("[omenu]:not([omenu*='usmg'])").addClass("noOmenu").remove();
			menu.find("[omenu*='usmg']").addClass("hasOmenu").show();
		}
	} else {
		for (var i = 0; i < allMenuLst.length; i++) {
			if (checkMenuAuthority(allMenuLst[i])) {
				$(allMenuLst[i]).addClass("hasOmenu");
			} else {
				$(allMenuLst[i]).addClass("noOmenu");
			}
		}
		menu.find(".noOmenu").remove();
	}
}

function checkMenuAuthority(crtObj) {
	var menuListForAccount = g_sessionUser["menuList"];
	/*if (g_sessionUser.accountType == "MAIN") {
	 return true;
	 }*/
	var omenuAttr = $(crtObj).attr("omenu");
	var label = false;
	for (var i = 0; i < menuListForAccount.length; i++) {
		if (menuListForAccount[i] === omenuAttr) {
			label = true;
			break;
		}
	}
	return label;
}

function hideAllFunctionRelatedElement() {
	$("html [ofun]").hide();
}
function showFunctionRelatedElementForLoginAccount() {
	var functionListForAccount = g_sessionUser["functionalityList"];
	if (functionListForAccount == null) {
		if (g_sessionUser["accountType"] == "SUB") {
		} else {
			$("html [class*='fun-']").show();
		}
	} else {
		for (var i = 0; i < functionListForAccount.length; i++) {
			var authedFun = "fun-" + functionListForAccount[i].replace(/\./g, "-");
			$("html [class*='" + authedFun + "']").show();
		}
	}
}

function checkAutorFunct(checkRequrieLabelAuthor) {
	var functionListForAccount = g_sessionUser["functionalityList"];
	var authority = false;
	if (functionListForAccount == null) {
		if (g_sessionUser["accountType"] == "SUB") {
			return authority;
		} else {
			authority = true;
			return authority;
		}
	} else {
		for (var i = 0; i < functionListForAccount.length; i++) {
			var authedFun = functionListForAccount[i];
			if (checkRequrieLabelAuthor.length != null) {
				if (authedFun == checkRequrieLabelAuthor) {
					authority = true;
					break;
				}
			}
		}
	}
	return authority;
}

/*
 * function oDoNothing(event){ event.stopPropagation(); event.preventDefault();
 * return false; }
 */

function oDoNothing(event) {
	var e = (event) ? event : window.event;
	if (window.event) {
		e.cancelBubble = true;
	} else {
		e.stopPropagation();
	}
	return false;
}

function floatMenuInit() {
	$("#main-float-control").click(function () {
		var crtClass = $(this).prop("class");
		if (crtClass.indexOf("showMenu") != -1) {
			$(this).removeClass("showMenu");
			$(this).addClass("hideMenu");
			$("#main-float-menus").hide();
		} else {
			$(this).removeClass("hideMenu");
			$(this).addClass("showMenu");
			$("#main-float-menus").show();
		}
	});

}

function currentCountryAndState(countryArray, countryId, stateId, countryVal, stateValue, editmode) {
	var crtCountryAry = countryArray;
	var cnt = crtCountryAry.length;
	var countryOptionsHtml = "";
	var crtCountryOptionsHtml = "";
	var crtStateAry = [];
	var stateOptionsHtml = "";
	var crtStateOptionsHtml = "";
	if (crtCountryAry != null && crtCountryAry.length > 0) {

		if (editmode) {
			crtStateAry = crtCountryAry[0].provState;
		}
		for (var i = 0; i < cnt; i++) {
			if ((crtCountryAry[i].name == countryVal || crtCountryAry[i].label == countryVal) && countryVal != null) {
				crtCountryOptionsHtml = "<option id='" + crtCountryAry[i].name + "' value=" + crtCountryAry[i].name + ">"
					+ crtCountryAry[i].label + "</option>";
				crtStateAry = crtCountryAry[i].provState;
			} else {
				countryOptionsHtml += "<option id='" + crtCountryAry[i].name + "' value=" + crtCountryAry[i].name + ">"
					+ crtCountryAry[i].label + "</option>";
			}
		}
		if (crtStateAry != null) {
			for (var key in crtStateAry) {
				if (key == stateValue || crtStateAry[key] == stateValue) {
					crtStateOptionsHtml = "<option value=" + key + ">" + crtStateAry[key] + "</option>";
				} else {
					stateOptionsHtml += "<option value=" + key + ">" + crtStateAry[key] + "</option>";
				}
			}
			/*
			 * for (var j = 0; j < crtStateAry.length; j++) { if (crtStateAry[j] ==
			 * stateValue) { crtStateOptionsHtml = "<option value=" + crtStateAry[j] +
			 * ">" + crtStateAry[j] + "</option>"; } else { stateOptionsHtml += "<option
			 * value=" + crtStateAry[j] + ">" + crtStateAry[j] + "</option>"; } }
			 */
		}
	}
	if ((countryVal == null || countryVal == "") && (!editmode)) {
		crtCountryOptionsHtml = "<option>-------</option>";
	}
	if ((stateValue == null || stateValue == "") && (!editmode)) {
		crtStateOptionsHtml = "<option>-------</option>";
	}
	if (countryId != null) {
		$("#" + countryId).html("").append(crtCountryOptionsHtml + countryOptionsHtml);
	}
	if (stateId != null) {
		$("#" + stateId).html("").append(crtStateOptionsHtml + stateOptionsHtml);
	}

	if (countryId != null && stateId != null){
		// Cascade Country and City
		$("#" + countryId).bind('change', function () {
			for (var i = 0; i < cnt; i++) {
				if (crtCountryAry[i].name == $(this).val()) {
					crtStateAry = crtCountryAry[i].provState;
				}
			}
			crtStateOptionsHtml = '';
			if (crtStateAry != null) {
				for (var j in crtStateAry) {
					crtStateOptionsHtml += '<option value="' + j + '">' + crtStateAry[j] + '</option>';
				}
			} else {
				crtStateOptionsHtml = "<option>-------</option>";
			}
			$("#" + stateId).empty().append(crtStateOptionsHtml);
		});
	}

}

function enumValuesOptionHtml(crtDataObj) {
	var enumValuesObj = crtDataObj.enumValues;
	var firstValue = "";
	var enumFirstValueHtml = "";
	var enumValuesHtml = "";
	if (crtDataObj.value != null) {
		firstValue = crtDataObj.value;
	} else if (crtDataObj.defaultValue != null) {
		firstValue = crtDataObj.defaultValue;
	} else {
		firstValue = null;
	}
	if (enumValuesObj != null) {
		for (var key in enumValuesObj) {
			if (firstValue != null && firstValue == key) {
				enumFirstValueHtml = "<option value='" + key + "'>" + enumValuesObj[key] + "</option>";
			} else {
				enumValuesHtml += "<option value='" + key + "'>" + enumValuesObj[key] + "</option>";
			}
		}
	}
	return enumFirstValueHtml + enumValuesHtml;
}

function getRptValidationClass(crtDataObj) {
	var validateHtml = "";
	var criteria = crtDataObj;
	// validate[required,custom[phone]]
	if (criteria.enumFlag) {
		return;
	}
	if (criteria.dataType == "Boolean") {
		validateHtml += "";
		return validateHtml;
	}
	if (criteria.required) {
		validateHtml += 'validate[required,';
	} else {
		validateHtml += 'validate[';
	}
	/*
	 * if("Double" === criteria.dataType){ validateHtml +=",custom[float]";
	 *
	 * }else
	 */
	if ("Date" == criteria.dataType) {
		if (criteria.groupId != null) {
			var groupName = criteria.groupId.substring(0, criteria.groupId.indexOf("-"));
			validateHtml += "onlyRange[" + groupName + "]";
		}
	}

	if ("Integer" === criteria.dataType) {
		validateHtml += ",custom[integer]";
	}
	if (criteria.maxLength != null) {
		validateHtml += 'maxSize[' + criteria.maxLength + '],';
	}
	if (criteria.maxVal != null) {
		validateHtml += 'max[' + criteria.maxVal + '],';
	}
	if (criteria.minVal != null) {
		validateHtml += 'min[' + criteria.minVal + '],';
	}
	if (criteria.pattern != null) {
	}
	validateHtml += ']';
	return validateHtml;
}

function getCriteriaHtml(crtDataObj, that) {
	var criteriaHtml = "";
	var crtCriteria = crtDataObj;
	if (crtCriteria == null || crtCriteria.length == 0) {
		return false;
	}
	var crrentType = crtCriteria.dataType;
	var crtVldtn = getRptValidationClass(crtCriteria);
	var tips = crtCriteria.tip || "";
	var stareKey = "";
	var crtLabel = crtCriteria.label.indexOf("-") == -1 ? crtCriteria.label : crtCriteria.label
		.substring(crtCriteria.label.indexOf("-") + 1);
	if (crtCriteria.required) {
		stareKey = '<span class="z-red">*</span><span>' + crtLabel + '</span>';
	} else {
		stareKey = '<span>' + crtLabel + '</span>';
	}

	if (crtCriteria.enumFlag) {
		criteriaHtml = "<tr><td class='otitle'>" + stareKey + "</td><td ><select id='" + crtCriteria.id
			+ "' class='" + crtVldtn + " u-ipt biground'>" + enumValuesOptionHtml(crtCriteria)
			+ "</select></td></tr>";
	} else {
		switch (crrentType) {
			case "String":
				criteriaHtml += '<tr><td class="otitle" >' + stareKey + '</td><td><input name= "' + crtCriteria.name + '" id="'
					+ crtCriteria.id + '" type="text" placeholder = "' + tips + '"  class="' + crtVldtn
					+ ' u-ipt criteria"></td></tr>';
				break;
			case "Integer":
				criteriaHtml += '<tr><td class="otitle" >' + stareKey + '</td><td><input name= "' + crtCriteria.name + '" id="'
					+ crtCriteria.id + '" type="text" placeholder = "' + tips + '" class="' + crtVldtn
					+ ' u-ipt criteria"></td></tr>';
				break;
			case "Boolean":
				criteriaHtml += '<tr><td class="otitle" >' + stareKey + '</td><td' + ' "><input  id="' + crtCriteria.id
					+ '"  name= "' + crtCriteria.name + '" type="checkbox" class="' + crtVldtn + ' criteria"></td></tr>';
				break;
			case "Date":
				var dateClass = "";
				if ("Y-m-d" == crtCriteria.formatter) {
					dateClass = " odatepicker ";
				} else {
					dateClass = " odatetimepicker ";
				}
				criteriaHtml += '<tr><td class="otitle" >' + stareKey + '</td><td' + ' "><input  id="' + crtCriteria.id
					+ '" name= "' + crtCriteria.name + '" type="text" readonly  class="' + crtVldtn + dateClass
					+ ' oheight25 datacenter criteria biground "></td></tr>';
				break;
			case "DateTime":
				criteriaHtml += '<tr><td class="otitle" >' + stareKey + '</td><td' + ' "><input  id="' + crtCriteria.id
					+ '"  name= "' + crtCriteria.name + '" type="text" readonly  class="' + crtVldtn
					+ ' criteria odatetimepicker oheight25 datacenter biground "></td></tr>';
				break;
			default:
				showAlertWindow("this type is not difine, please update the code.");
		}
	}
	return criteriaHtml;
}

function getCriteriaDiv(crtDataObj, containId) {
	var criteriaHtml = "";
	var crtCriteria = crtDataObj;
	if (crtCriteria == null || crtCriteria.length == 0) {
		return false;
	}
	var crrentType = crtCriteria.dataType;
	var crtVldtn = getRptValidationClass(crtCriteria);
	var tips = crtCriteria.tip || "";
	var crtriaId = crtCriteria.id || (containId + '-' + crtCriteria.name);
	var starKey = "";
	var crtLabel = crtCriteria.label.indexOf("-") == -1 ? crtCriteria.label : crtCriteria.label
		.substring(crtCriteria.label.indexOf("-") + 1);
	if (crtCriteria.required) {
		starKey = '<span class="z-red">*</span><span>' + crtLabel + '</span>';
	} else {
		starKey = '<span>' + crtLabel + '</span>';
	}

	if (crtCriteria.enumFlag) {
		var mltipLabel = "";
		var mlticlass = " u-sel";
		var height = "";
		if (crtCriteria.multipleFlag) {
			mltipLabel = " multiple";
			mlticlass = " u-mulsel";
			height = " style='height:120px'";
		}
		criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>' + '<div class="item-c '
			+ mlticlass + '" ' + height + '><select ' + mltipLabel + height + ' name="' + crtCriteria.name + '" id="'
			+ crtriaId + '" >' + enumValuesOptionDiv(crtCriteria) + '</select></div>' + '</div>';
	} else {
		switch (crrentType) {
			case "String":
				criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>'
					+ '<div class="item-c"><input class="u-ipt criteria ' + crtVldtn + '" name="' + crtCriteria.name
					+ '" type="text" value="" id="' + crtriaId + '" placeholder = "' + tips + '"></div>' + '</div>';
				break;
			case "Integer":
				criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>'
					+ '<div class="item-c"><input class="u-ipt criteria ' + crtVldtn + '" name="' + crtCriteria.name
					+ '" type="text" value="" id="' + crtriaId + '" placeholder = "' + tips + '"></div>' + '</div>';
				break;
			case "Boolean":
				criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>'
					+ '<div class="item-c"><input class="criteria ' + crtVldtn + '" name="' + crtCriteria.name
					+ '" type="checkbox" value="" id="' + crtriaId + '" placeholder = "' + tips + '"></div>' + '</div>';
				break;
			case "Date":
				var dateClass = "";
				if ("Y-m-d" == crtCriteria.formatter) {
					dateClass = " odatepicker ";
				} else {
					dateClass = " odatetimepicker ";
				}
				criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>'
					+ '<div class="item-c"><input class="u-ipt criteria ' + crtVldtn + dateClass + '" readonly name="'
					+ crtCriteria.name + '" id="' + crtriaId + '" placeholder = "' + tips + '"></div>' + '</div>';
				break;
			case "DateTime":
				criteriaHtml = '<div class="item">' + '<label class="item-l">' + starKey + '</label>'
					+ '<div class="item-c u-sel"><input class="u-ipt criteria ' + crtVldtn + ' odatetimepicker" readonly name="'
					+ crtCriteria.name + '" type="checkbox" id="' + crtriaId + '" placeholder = "' + tips + '"></div>'
					+ '</div>';
				break;
			default:
				showAlertWindow("this type is not difine, please update the code.");
		}
	}
	return criteriaHtml;
}

function enumValuesOptionDiv(crtDataObj) {
	var enumValuesObj = crtDataObj.enumValues;
	var firstValue = "";
	var enumFirstValueHtml = "";
	var enumValuesHtml = "";
	if (crtDataObj.value != null) {
		firstValue = crtDataObj.value;
	} else if (crtDataObj.defaultValue != null) {
		firstValue = crtDataObj.defaultValue;
	} else {
		firstValue = null;
	}
	if (enumValuesObj != null) {
		for (var key in enumValuesObj) {
			if (firstValue != null && firstValue == key) {
				enumFirstValueHtml = "<option value='" + key + "'>" + enumValuesObj[key] + "</option>";
			} else {
				enumValuesHtml += "<option value='" + key + "'>" + enumValuesObj[key] + "</option>";
			}
		}
	}
	return enumFirstValueHtml + enumValuesHtml;
}

function switchToTableData(data, formate) {

	if (!data || data.length == 0) {
		return;
	}
	var len = data.length, jlen = formate['head'].length;
	var head = [], rows = [];
	for (var i = 0; i < len; i++) {
		var tr = [];
		for (var j = 0; j < jlen; j++) {

			var name = formate['head'][j].name, label = formate['head'][j].label;

			var td = {
				label: label,
				name: name,
				value: data[i][name]
			};
			tr.push(td);
		}
		rows.push(tr);
	}

	return {
		pageHeader: formate['head'],
		rows: rows,
		total: 0,
		rowsPerPage: 0,
		offset: 0
	}
}

function listReportType(topObj) {
	ajaxCallForEventWithJsonData(topObj, "listreporttype", null, null, listReportTypeSuccessfully, true, true);
	function listReportTypeSuccessfully(that, data) {
		if (data == null || data.length == 0) {
			return;
		}
		var reportTypeHtml = "";
		var searchTypeHtml = "<option value='null' >" + i18nmsg.text("OmniOrderStatus.All") + "</option>";
		for (var i = 0; i < data.length; i++) {
			reportTypeHtml += "<option name=" + data[i].reportName + " value=" + data[i].id + " class='task'>"
				+ data[i]["reportLabel"] + "</option>";
			searchTypeHtml += "<option name=" + data[i].reportName + " value=" + data[i].id + " class='task'>"
				+ data[i]["reportLabel"] + "</option>";
		}
		$("#" + that.schKeytaslTypeSlctId).html("").append(searchTypeHtml);
		$("#" + that.chsRprtTypId).html("").append(reportTypeHtml).change();
	}
}

function getReportCriteria(rprtId, topObj) {
	var that = topObj;
	var rprtCndtnHtml = '<div class="item">' + '<label class="item-l"><span class="z-red">*</span><span>'
		+ i18nmsg.text("research.task.label.name") + '</label>' + '<div class="item-c"><input type="text" id="'
		+ that.rprtConditionTblId + '-name"' + 'name="taskName" class="validate[required] u-ipt "></div>'
		+ '</div>';

	var groupCriteria = {};
	if (rprtId == null) {
		showAlertWindow(i18nmsg.text("shipment.report.alert.noreporttype"));
		return;
	}
	ajaxCallForEventWithJsonData(that, "getreportcriteria", rprtId, null, getReportCriteriaSuccessfully, true, true);
	function getReportCriteriaSuccessfully(that, data) {
		that.crtCriteriaArray = [];
		that.crtGrpCriteriaArray = [];
		for (var i = 0; i < data.length; i++) {
			var obj = data[i];
			if(obj){
				obj.id = that.rprtConditionTblId + "-" + obj.name;
				that.crtCriteriaArray.push(obj);
				if (obj.groupId == null) {
					rprtCndtnHtml += getCriteriaDiv(obj, that);
				} else {
					var groupId = obj.groupId;
					var groupName = groupId.substring(0, groupId.indexOf("-"));
					var groupSriId = parseInt(groupId.substring(groupId.indexOf("-") + 1));
					if (groupCriteria[groupName] == null) {
						groupCriteria[groupName] = [];
						groupCriteria[groupName][groupSriId] = obj;
					} else {
						groupCriteria[groupName][groupSriId] = obj;
					}
				}
			}
		}

		for (var key in groupCriteria) {
			var groupname = "ReportGroup.Label."
				+ groupCriteria[key][0].groupId.substring(0, groupCriteria[key][0].groupId.indexOf("-"));
			var groupLabel = i18nmsg.text(groupname) == null ? "" : i18nmsg.text(groupname);
			that.crtGrpCriteriaArray.push(groupCriteria);
			rprtCndtnHtml += "<tr><td class='opeddingLeft20 otitle oleft' colspan='2'><b>" + groupLabel + "</b></td></tr>"
				+ getGroupCriteriaHtml(groupCriteria[key]);
		}

		rprtCndtnHtml += '<tr><td class="otitle" >' + i18nmsg.text("research.task.label.note") + '</td><td><textarea id="'
			+ that.rprtConditionTblId + '-note" name="description" class="u-tarea"></textarea></td></tr>';

		$("#" + that.rprtConditionTblId).html("").append(rprtCndtnHtml);

		var c = g_sessionUser.language == "zh-cn" ? "ch" : "en";
		$("#" + that.rprtConditionTblId + " .odatepicker").datetimepicker({
			lang: c,
			closeOnDateSelect: true,
			timepicker: false,
			format: 'Y-m-d',
			formatDate: 'Y-m-d '
		});

		$("#" + that.rprtConditionTblId + " .odatetimepicker").datetimepicker({
			lang: c,
			closeOnDateSelect: true,
			timepicker: true,
			format: 'Y-m-d H:i:s',
			formatDate: 'Y-m-d '
		});

		$("#" + that.rprtConditionTblId + ",#" + that["rsltTheadId"]).validationEngine({
			validationEventTrigger: "",
			inlineValidation: true,
			success: false,
			autoHidePrompt: true,
			autoHideDelay: 5000,
			scroll: false,
			promptPosition: "centerRight", // "topRight",
			binded: true
		});
	}
}

function getGroupCriteriaHtml(groupCriteria) {
	var that = this;
	var groupCriteriaHtml = "";
	if (groupCriteria == null || groupCriteria.length == 0) {
		return false;
	}
	for (var key in groupCriteria) {
		if (groupCriteria[key] != null) {
			groupCriteriaHtml += getCriteriaDiv(groupCriteria[key]);
		}
	}
	return groupCriteriaHtml;
}

function listReportTask(that) {
	var resObj = getReportSearchkey(that);
	ajaxCallForEventWithJsonData(that, "listreporttask", resObj, null, listreporttaskSuccessfully, true, true);
	function listreporttaskSuccessfully(that, data) {

		var tbodyIdWrap = $("#" + that.rsltTbodyId);

		if (data == null) {
			tbodyIdWrap.html("");
			return;
		}
		var totalRows = parseInt(data.total);
		that.rowsPerPage = data.rowsPerPage;
		that.searchCriteria.value = data.offset + that.rowsPerPage;
		if (that.searchCriteria.value >= totalRows) {
			that.searchCriteria.value = totalRows;
		}
		$("#" + that.orderPageObj.totalPageId).html(totalRows);
		if (totalRows == "0") {
			$("#" + that.orderPageObj.startPageId).html(0);
		} else {
			$("#" + that.orderPageObj.startPageId).html(data.offset + 1);
		}
		$("#" + that.orderPageObj.endPageId).html(that.searchCriteria.value);
		// var sllingOrderListHtml = "";
		var rowsdata = data.rows;
		if (rowsdata == null) {
			return;
		}
		var rsltTbodyHtml = "";
		var crtOfun = checkAutorFunct(that.names.join(".") + ".all");

		for (var i = 0; i < rowsdata.length; i++) {
			var rptName = rowsdata[i].taskName;
			var rptDate = rowsdata[i].createdDate.substring(0, 10);
			var rptType = rowsdata[i]["reportTypeLabel"];
			var rptKeywordsArray = rowsdata[i].criList;
			var rptKywSpans = "";
			var rptNote = rowsdata[i].description;
			var rptSts = rowsdata[i].status;
			var rptRstId = rowsdata[i]["reportResultId"];
			var rptRstHtml = "";
			var crtGrpKyws = {};
			var rptId = rowsdata[i]["taskId"];
			var deletImg = "";
			var redoImg = "";
			var downloadImg = "";
			if (crtOfun) {
				deletImg = '<img class="deltreport" id="' + rptRstId + '-dlt" width="23" name="' + rptId
					+ '" src="resources/image/delt.png" title="delete" >';
				redoImg = '<img class="redoreport" id="' + rptRstId + '-redo" width="23" name="' + rptId
					+ '" src="resources/image/redo.png" title="redo" >';
				downloadImg = '<img class="getresult" id="' + rptRstId + '-download" width="25" name="' + rptRstId
					+ '" src="resources/image/Download.png" title="download" >';
				downloadImg += '<img class="listreport" id="' + rptRstId + '-listreport" width="23" name="' + rptRstId
					+ '" src="resources/image/listreport.png" title="check online" >';
			}
			if (rptKeywordsArray != null && rptKeywordsArray.length > 0) {
				for (var j = 0; j < rptKeywordsArray.length; j++) {
					if (rptKeywordsArray[j].groupId != null) {
						var groupId = rptKeywordsArray[j].groupId;
						var groupName = groupId.substring(0, groupId.indexOf("-"));
						var groupOrdr = parseInt(groupId.substring(groupId.indexOf("-") + 1));
						if (crtGrpKyws[groupName] == null) {
							crtGrpKyws[groupName] = [];
							crtGrpKyws[groupName][groupOrdr] = rptKeywordsArray[j];
						} else {
							crtGrpKyws[groupName][groupOrdr] = rptKeywordsArray[j];
						}
					} else {
						var realString = getRealValue(rptKeywordsArray[j]);
						rptKywSpans += "<span title='"+ getRealValue(rptKeywordsArray[j]) +"'>" 
						rptKywSpans += 		rptKeywordsArray[j].label + " : ";
						if(realString.length > 100){
							realString = realString.substring(0, 100) + "...";
						}
						rptKywSpans += 		realString;
						rptKywSpans += "</span><br>";
					}
				}
				for (var key in crtGrpKyws) {
					groupName = crtGrpKyws[key][0].label.split("-");
					// rptKywSpans +="<span>" + groupName[0] + "</span><br>";
					for (var kw in crtGrpKyws[key]) {
						var kywName = crtGrpKyws[key][kw].label;
						rptKywSpans += "<span title='" + getRealValue(crtGrpKyws[key][kw]) + "'>" 
						
						+ 					kywName.substring(kywName.indexOf("-") + 1) + " : " 
						
						+ 					getRealValue(crtGrpKyws[key][kw]).substring(0, 100);
						
						+ 				"</span><br>";
					}
				}
			}
			if ("ReportStatus.COMPLETE" === rptSts) {
				rptRstHtml = downloadImg + redoImg + deletImg;
			} else if ("ReportStatus.INPROGRESS" === rptSts) {
				rptRstHtml = deletImg;
			} else if ("ReportStatus.ERROR" === rptSts) {
				rptRstHtml = redoImg + deletImg;
			} else if ("ReportStatus.NONE" === rptSts) {
				rptRstHtml = redoImg + deletImg;
			}

			rsltTbodyHtml += "<tr><td>" + rptName + "</td>" + "<td>" + rptType + "</td>" + "<td>" + rptDate + "</td>"
				+ "<td>" + rptKywSpans + "</td>" + "<td>" + rptNote + "</td>" + "<td>" + i18nmsg.text(rptSts) + "</td>"
				+ "<td>" + rptRstHtml + "</td></tr>";
		}


		tbodyIdWrap.html("").append(rsltTbodyHtml);
		tbodyIdWrap.find(".getresult").click(function () {
			downLoadRslt(parseInt($(this).attr("name")), that);
		});
		tbodyIdWrap.find(".redoreport").click(function () {
			var reportId = parseInt($(this).attr("name"));
			art.dialog({
				title: i18nmsg.text("report.confirm.title.redo"),
				content: i18nmsg.text("report.confirm.content.redo"),
				okValue: i18nmsg.text("common.btn.confirm"),
				ok: function () {
					reDoReport(reportId, that);
				},
				cancelValue: i18nmsg.text("common.btn.cancel"),
				cancel: function () {
				}
			});
		});
		tbodyIdWrap.find(".listreport").click(function () {
			listReportResult(that, parseInt($(this).attr("name")));
		});
		tbodyIdWrap.find(".deltreport").click(function () {
			var reportId = parseInt($(this).attr("name"));
			art.dialog({
				title: i18nmsg.text("report.confirm.title.delete"),
				content: i18nmsg.text("report.confirm.content.delete"),
				okValue: i18nmsg.text("common.btn.confirm"),
				ok: function () {
					deltReport(reportId, that);
				},
				cancelValue: i18nmsg.text("common.btn.cancel"),
				cancel: function () {
				}
			});
		});
	}
}

function getReportSearchkey(topObj) {
	var reportCriteria = [];
	var offsetObj = {};
	offsetObj.name = "offset";
	offsetObj.value = topObj.searchCriteria.value;
	reportCriteria.push(offsetObj);
	// set the status value
	var sts = $("#" + topObj.schKeyTaslStsSlctId),
		type = $("#" + topObj.schKeytaslTypeSlctId);


	var stsObj = {};
	var statusKey = sts.get(0).selectedIndex;
	stsObj.name = "status";
	if (statusKey == -1 || statusKey == 0) {
		stsObj.value = null;
	} else {
		stsObj.value = sts.val();
	}
	reportCriteria.push(stsObj);

	// set the rptTypeId value
	var rptTypeObj = {};
	var rptTypeKey = type.get(0).selectedIndex;
	rptTypeObj.name = "rptTypeId";
	if (rptTypeKey == -1 || rptTypeKey == 0) {
		rptTypeObj.value = null;
	} else {
		rptTypeObj.value = type.val();
	}
	reportCriteria.push(rptTypeObj);

	// set the startDate value
	var dateFrom = $("#" + topObj.dateFromId),
		dateTo = $("#" + topObj.dateToId);

	var startDateObj = {};
	startDateObj.name = "startDate";
	startDateObj.value = dateFrom.val() || null;
	reportCriteria.push(startDateObj);

	// set the endDate value
	var endDateObj = {};
	endDateObj.name = "endDate";
	endDateObj.value = dateTo.val() || null;
	reportCriteria.push(endDateObj);
	dateTo.val("");
	dateFrom.val("");
	return reportCriteria;
}

function downLoadRslt(resultId, that) {
	ajaxCallForEventWithJsonData(that, "generatereport", resultId, null, downLoadRsltSuccessfully, true, true);
	function downLoadRsltSuccessfully(that, data) {
		var url = that.baseUrl + "downloadreport?downloadId=" + data;
		window.open(url, "_blank");
	}
}

function reDoReport(reportId, that) {
	ajaxCallForEventWithJsonData(that, "resetreporttask", reportId, null, resetrpttaskSuccessfully, true, true);
	function resetrpttaskSuccessfully(that) {
		$("#" + that.freshId).click();
	}
}

function deltReport(reportId, that) {
	ajaxCallForEventWithJsonData(that, "deletereporttask", reportId, null, deletereporttaskSuccessfully, true, true);
	function deletereporttaskSuccessfully(that) {
		$("#" + that.freshId).click();
	}
}

function listReportResult(that, rptRstId) {
	ajaxCallForEventWithJsonData(that, "showresult", rptRstId, null, listReportResultSuccessfully, true, true);
	function listReportResultSuccessfully(data) {
		var reportContent = "";
		if (data == null || data.length < 1) {
			reportContent = "";
		} else {
			reportContent = ReportContent(data);
		}
		$("#main-report-result-table").html("").html(reportContent);
		art.dialog({
			title: i18nmsg.text("research.query.btn.showonline"),
			// content: document.getElementById("main-report-result-table"),
			content: document.getElementById("main-report-result"),
			okValue: i18nmsg.text("common.btn.confirm"),
			fixed: true,
			lock: true,
			ok: function () {
			}
		});
	}
}

function ReportContent(data) {
	var tableHead = "";
	var tableBody = "";
	var rptInf = data;
	var head = rptInf["fieldMap"];
	var headKeyArray = [];
	var body = rptInf["reportResultList"];
	if (rptInf == null || body == null || body.length < 1 || head == null) {
		return i18nmsg.text("main.report.dialog.error.noresult");
	}
	tableHead += "<thead><tr class='dlgtitle ocenter'>";
	tableHead += "<td class='ocenter'>total#:<br>" + body.length + "</td>";
	for (var key in head) {
		tableHead += "<td class='ocenter'>" + key + "</td>";
		headKeyArray.push(head[key]);
	}
	tableHead += "</tr></thead>";
	tableBody += "<tbody>";
	for (var ri = 0; ri < body.length; ri++) {
		var crtRow = body[ri];
		var rowEO = "";
		if (ri % 2 == 0) {
			rowEO = "even";
		} else {
			rowEO = "oddrow";
		}
		tableBody += "<tr class='" + rowEO + " ocenter'>";
		tableBody += "<td>" + (ri + 1) + "</td>";
		for (var rj = 0; rj < headKeyArray.length; rj++) {
			var rowData = crtRow[headKeyArray[rj]];
			if (rowData === null) {
				rowData = "";
			}
			tableBody += "<td>" + rowData + "</td>";
		}
		tableBody += "</tr>";
	}
	tableBody += "</tbody>";
	return tableHead + tableBody;
}

function getRealValue(obj) {
	var value = "";
	if (obj != null && obj.value != null) {
		value = obj.value;
	} else {
		return value;
	}
	if (obj.enumFlag) {
		if (obj.multipleFlag) {
			var valueArray = value.substring(1, (value.length - 1)).split(",");
			if (valueArray != null && valueArray.length > 0) {
				value = "";
				for (var i in valueArray) {
					var no = parseInt(valueArray[i].trim());
					value += "<br>" + obj.enumValues[no] + ",";
				}
			}
		} else {
			if (obj.dataType == "Integer" || obj.dataType == "int")
				value = obj.enumValues[parseInt(obj.value.trim())];
			else {
				value = obj.enumValues[obj.value.trim()];
			}
		}
	}
	return value;
}

function getCriteriaValueObj(rprtConditionTblId, chsRprtTypId, crtCriteriaArray) {
	var taskObj = {};
	var criList = [];
	taskObj.reportTypeId = $("#" + chsRprtTypId).val();
	taskObj.taskName = $("#" + rprtConditionTblId + "-name").val();
	taskObj.description = $("#" + rprtConditionTblId + "-note").val();
	if (crtCriteriaArray != null) {
		for (var i = 0; i < crtCriteriaArray.length; i++) {
			var obj = {};
			obj.name = crtCriteriaArray[i].name;
			if (crtCriteriaArray[i].multipleFlag) {
				obj.multipleFlag = true;
				obj.valMultipleList = $("#" + crtCriteriaArray[i].id).val();
			} else {
				obj.value = $("#" + crtCriteriaArray[i].id).val();
			}
			criList.push(obj);
		}
	}
	taskObj.criList = criList;
	if (null == taskObj.reportTypeId) {
		showAlertWindow(i18nmsg.text("research.task.alert.noreporttype"));
		return;
	}
	return taskObj;
}

function getSnglCretiaValue(crtObj) {
	var winDataItem = $("#" + crtObj.id);
	var value = "";
	if (winDataItem.get(0) == null) {
		return value;
	}
	var winDataItemTagName = winDataItem.get(0).tagName;
	var windDataItem = winDataItem;
	var optionVal = "";
	// get the value from the window by item;
	if (winDataItemTagName == "SELECT") {
		optionVal = $(windDataItem).find("option:selected").text();
	}
	if (winDataItemTagName == "TEXTAREA") {
		// optionVal = $(windDataItem).text();
		optionVal = windDataItem.get(0).value;
	}
	if (winDataItemTagName == "TD") {
		optionVal = $(windDataItem).html();
		if (optionVal.indexOf("span") != -1) {
			// optionVal = $("#"+winDataItemId).children();
			optionVal = winDataItem.find(" span");
		}
	}
	if (winDataItemTagName == "INPUT") {
		var winDataItemType = winDataItem.get(0).type;
		if (winDataItemType == "text") {
			optionVal = $(windDataItem).val();
		}
		if (winDataItemType == "checkbox") {
			optionVal = $(windDataItem).prop("checked");
		}
	}
	return optionVal;
}

function listSnglCretiaValue(crtObj, crtValue) {
	var dlgDataItem = $("#" + crtObj.id);
	if (dlgDataItem.get(0) == null) {
		return;
	}
	var optionVal = crtValue;
	var dlgDataItemTagName = dlgDataItem.get(0).tagName;
	if (dlgDataItemTagName == "SELECT") {
		currentoption(crtObj.id, optionVal);
	}
	if (dlgDataItemTagName == "TD") {
		dlgDataItem.html(optionVal);
	}
	if (dlgDataItemTagName == "TEXTAREA") {
		dlgDataItem.val(optionVal);
	}
	if (dlgDataItemTagName == "INPUT") {
		var dlgDataItemType = dlgDataItem.get(0).type;
		if (dlgDataItemType == "text") {
			dlgDataItem.val(optionVal);
		}
		if (dlgDataItemType == "checkbox") {
			dlgDataItem.prop("checked", optionVal != 'false');
		}
		if (dlgDataItemType == "radio") {
		}
	}
}

function currentoption(optionId, optionVal) {
	var select = $("#" + optionId);
	var count = select.find("option").length;
	var selectedoption = "";
	for (var i = 0; i < count; i++) {
		var currentOptionValue = select.get(0).options[i];
		if ($(currentOptionValue).text() == optionVal) {
			$(currentOptionValue).prop("selected", true);
			selectedoption = currentOptionValue;
			break;
		}
	}
}

function printWindow(url,pos,params,closeDelay,closeWindow){
  if (!pos){
    pos = "_blank";
  }
  if (!params){
    params ="width=400, height=600";
  }  
  if (!closeDelay){
    closeDelay =14000;
  }
  //  $('#print-iframe').remove();
//  var iframe = '<iframe style = "visibility: hidden" src="'+url + '" id="print-iframe" name="print-iframe"></iframe>';
//  $('body').append(iframe);
//  var frame = window.frames["print-iframe"];
//  frame.onload=function(){
//    frame.focus();
//    frame.print(); 
//  };
  var mywin = window.open(url, pos, params);
  mywin.onload=function(){
    mywin.focus();
    mywin.print();
      if(closeWindow){
          setTimeout(function(){ mywin.close(); },closeDelay);
      }
		return mywin;
  };
}


function formatDateToStr(dateObj,format) {
	if (!dateObj)
		return "";
	var date = new Date(dateObj);
	if (!format){
		format = g_datePattern; //default
	}
	return date.format(format);
};

function formatDateTimeToStr(dateObj,format) {
	if (!format){
		format = g_dateTimePattern; //default
	}
	return formatDateToStr(dateObj,format);
};

/**
 *
 * @param date
 * @returns {string}
 */
function createDateCell(date) {
	var tdStr = "<td>";
	var todayStr= formatDateToStr(new Date());
	var dateStr= formatDateToStr(date);
	var retDate = formatDateTimeToStr(date);
	if (dateStr == todayStr){
		tdStr += "<span style='color: green; font-weight: bold;' title='Today!'>" +retDate+"</span>";
	} else {
		tdStr += retDate;
	}
	tdStr+="</td>";
	return tdStr;
};

function replaceNull(data,replacement) {
	return data?data : replacement || "--";
};

function activateFocusSelect(){
	$(this).find(".fselect").on("focus",function(e){
		$(this).select();
	});

	$(this).find(".fselect").on("mouseup",function(e){
		e.preventDefault();
	});
}

function roundToTwo(num) {
	return +(Math.round(num + "e+2")  + "e-2");
}

function IsJsonString(str) {
	try {
		JSON.parse(str);
	} catch (e) {
		return false;
	}
	return true;
}

function silentRefresh(openSuccess) {
	if (g_silientRefreshFlag){
		return false;
	}
	g_silientRefreshFlag = true;
	var fname="silentRefresh-iframe";
  var iframe = '<iframe style = "visibility: hidden" src="'+ g_loginURL + '" id="'+fname+'"></iframe>';
 $('body').append(iframe);
 var frame = window.frames[fname];
 frame.onload=function(){
	 $('#'+fname).remove();
	 openSuccess();
	 g_silientRefreshFlag = false;
 };
}
function refreshSessionUser(curUserId,successCallback,failedCallback){
	ajaxCallForEventWithJsonData(null, g_applicationContext + 'getSessionUser', null, null, getSeesionUserSuccessFully,null, null,failedCallback);
	function getSeesionUserSuccessFully(that, ajaxResponse) {
		if (successCallback) {
			successCallback();
		}
		var obj = ajaxResponse, menu = $("#menu");
		if (obj == null || (typeof obj) == 'string' || curUserId && curUserId!=obj.accountId) {
			location.assign(g_loginURL);
			if (failedCallback) {
				failedCallback();
			}
		}	else {
			g_sessionUser = obj;
			$("#loginUserName").text(obj.name);
			populateThePageWithLanguageSetting(null, obj.language);
			$("#header-language-sel").val( obj.language);
			$("#header-menu-slctLanguange").val( obj.language);
			$(".userName").html(g_sessionUser.email);
			$("#header").find("[name='"+ g_sessionUser.language +"']").addClass("current-language");
			addAllOmenu();
			hideAllFunctionRelatedElement();
			hideAllMenuRelatedElement();
			showFunctionRelatedElementForLoginAccount();

			//load validation
			//FileLoadTools.loadJsFileByJQ(['i18n','jquery.validationEngine.'+g_sessionUser.language]);

			//menu
			/*add menu click outlook*/
			menu.find(".m-lv3menu").find("li").hover(function(){
				$(this).addClass('li_hover');
			},function(){
				$(this).removeClass('li_hover');
			});


			menu.find(".m-menu-item").hover(function(){
				var liwidth = $(this).width();
				var width = $(this).find(".m-menu-item-body").width();
				var offset = -((width-liwidth-24)/2);
				$(this).find(".m-menu-item-body").css({"left": offset+"px"}).show();
				$(this).find(".m-menu-item-body").prepend("<div class='current'></div>");
			},function(){
				$(this).find(".m-submenu-item-body").hide();
				$(this).find(".m-menu-item-body").hide();
				$(this).find(".current").remove();
			});

			menu.find(".m-menu").children("li").find("span").click(function(){
				DnmcMenuInitTool.frameInit();
				var topIndex = $(this).parent().index();
				DnmcMenuInitTool.dnmcMenuInit($("#contentSubMenu").find(".m-snav-item"),$("#contentLv3Menu").find(".m-snav-item"),topIndex,0,0);
			});

			menu.find(".m-submenu-item-title").each(function(){
				if($(this).siblings("div").length!==0){
					$(this).css({
						"background-image": "url('resources/image/Arrow-Pull-down-16x16-1.png')"
					});
					var menulen = menu.find(".m-menu-item").length;
					var mainmenuindex = $(this).parents(".m-menu-item").index();

					$(this).parent().unbind("hover").hover(function(){
						$(this).find(".m-submenu-item-title").css({
							"background-image": "url('resources/image/Arrow-Pull-down-16x16-2.png')"
						});

						var width = $(this).parent().width()+10;
						var height = $(this).parent().height()+7;
						if(mainmenuindex +1 >= menulen){
							var lv3menuwidth = $(this).find(".m-submenu-item-body").width()+15;
							width = -(0 + lv3menuwidth);
						}

						$(this).find(".m-submenu-item-body").css({
							"top": "-1px",
							"left": width+"px",
							"min-height": height+"px"
						}).show();
					},function(){
						$(this).find(".m-submenu-item-title").css({
							"background-image": "url('resources/image/Arrow-Pull-down-16x16-1.png')"
						});
						$(this).find(".m-submenu-item-body").hide();
					});
				}else{
					$(this).css({
						"background-image": "none"
					});
				}

			});
			/*
			 *load files needed when subMenu items clicked
			 * */
			menu.find(".m-submenu-item-title").click(function(){
				DnmcMenuInitTool.frameInit();
				var li = $(this).parent();
				var subIndex = li.index();
				var topIndex = li.parents("li").index();
				DnmcMenuInitTool.dnmcMenuInit($("#contentSubMenu").find(".m-snav-item"),$("#contentLv3Menu").find(".m-snav-item"),topIndex,subIndex,0);
			}).hover(function(){
				var li = $(this).parent();
				li.find(".m-submenu-item-body").show();
			},function(){});
			/*
			 * load files needed when lv3Menu items clicked
			 * */
			menu.find(".m-submenu-item-body").find("a").click(function(){
				DnmcMenuInitTool.frameInit();
				var parent = $(this).parent("li");
				var SubMenuItem = $(this).parents(".m-submenu-item-body").parent("li");
				var topIndex = SubMenuItem.parents("li").index();
				var subIndex = SubMenuItem.index();
				var lv3Index = parent.index();
				DnmcMenuInitTool.dnmcMenuInit($("#contentSubMenu").find(".m-snav-item"),$("#contentLv3Menu").find(".m-snav-item"),topIndex,subIndex,lv3Index);
			});
		}
	}

}