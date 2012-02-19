/**
 * Desc:
 * --------
 * User: zou bo
 * Date: 12-1-19 上午11:06
 */

String.prototype.trim= function(){
    // 用正则表达式将前后空格
    // 用空字符串替代。
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * jQuery 警告窗口
 * @param message
 */
function jqAlert(message) {
    if ($("#_dialog_alert").length == 0) {
        $("body").append('<div id="_dialog_alert"></div>');
        $("#_dialog_alert").dialog({
            autoOpen:false,
            title:'提示信息',
            modal:true,
            width:400,
            //  closeOnEscape:true,
            buttons:{
                '关闭':function () {
                    $(this).dialog('close');
                }
            }
        });
    }
    $("#_dialog_alert").html(message);
//    $("#_dialog_alert").css("display","");
    $("#_dialog_alert").dialog('open');
}

/**
 * 带函数参数的jQuery警告窗口
 * @param message
 * @param customFunction
 */
function jqAlert2(message,customFunction) {
    if ($("#_dialog_alert").length == 0) {
        $("body").append('<div id="_dialog_alert"></div>');
        $("#_dialog_alert").dialog({
            autoOpen:false,
            title:'提示信息',
            modal:true,
            width:400,
            //  closeOnEscape:true,
            buttons:{
                '关闭':function () {
                    $(this).dialog('close');
                    customFunction($);
                }
            }
        });
    }
    $("#_dialog_alert").html(message);
    $("#_dialog_alert").dialog('open');
}


/**
 * 带函数参数的jQuery确认窗口
 * @param message
 * @param customFunction
 */
function jqConfirm(message,customFunction) {
    if ($("#_dialog_confirm").length == 0) {
        $("body").append('<div id="_dialog_confirm"></div>');
        $("#_dialog_confirm").dialog({
            autoOpen:false,
            title:'提示信息',
            modal:true,
            width:400,
            closeOnEscape:false,
            buttons:{
                '确认':function () {
                    $(this).dialog('close');
                    customFunction($);
                },
                '取消':function () {
                    $(this).dialog('close');
                    return false;
                }
            }
        });
    }
    $("#_dialog_confirm").html(message);
    $("#_dialog_confirm").dialog('open');
}


/*
 * Logout system
 */
function logout(logoutUrl){
    jqConfirm("确认退出系统？",function(){
        window.location=logoutUrl;
    });
}



/**
 * 动态创建form，以post方法请求服务器action
 * @param action
 * @param entityId 单个或批量ID----进一步，可以允许定义参数名称
 * @param confirmation
 */
function requireActionByPost(action, entityId, confirmation) {
    requireActionByPostWithSpecifiedInputName(action,"id",entityId,confirmation);
}


/**
 * 动态创建form，以post方法请求服务器action
 * @param action
 * @param inputName 指定参数名
 * @param inputValue 值/值数组
 * @param confirmation
 */
function requireActionByPostWithSpecifiedInputName(action, inputName ,inputValue, confirmation) {
    var inputValueArray = new Array();
    if (inputValue.constructor != Array) {
        inputValueArray[0] = inputValue;
    } else {
        inputValueArray = inputValue;
    }

    jqConfirm(confirmation, function () {
        //create　a　form　
        var tempForm = document.createElement("form");
        tempForm.action = action;
        tempForm.method = "post";
        document.body.appendChild(tempForm);

        for (var ei=0; ei < inputValueArray.length; ei++) {
            //create　a　input item
            var tempInput = document.createElement("input");
            tempInput.type = "hidden";
            tempInput.name = inputName;
            tempInput.value = inputValueArray[ei];
            //the　parameter　of　method　in　the　code　of　DispatchAction.
            tempForm.appendChild(tempInput);
        }
        //submit　the　form　
        tempForm.submit();
    });
}

/*
下面方法提供常量下拉输入菜单动作支持
 */
function constSelectInputChange(inputName) {
    var selValue = $("#" + inputName + "Sel").val().trim();

    $("#" + inputName + "Input").val(selValue.split("|")[1].trim());
    $("#" + inputName).val(selValue.split("|")[0]);
}

function constSelectInputBlur(inputName) {
    var selValue = $("#" + inputName + "Sel").val();

    if ($("#" + inputName + "Input").val() != null && selValue.split("|")[1] != $("#" + inputName + "Input").val().trim()) {
        $("#" + inputName).val("");
    }
}


/*
下面方法提供省市区下拉菜单联动支持
 */

function provinceChange(inputName, citySelectUrl) {
    var provSelValue = $("#" + inputName + "_provinceId").val().trim();

    if (provSelValue != '') {
        $.get(citySelectUrl + '?provinceId=' + provSelValue, function (data) {
            $("#" + inputName + "_cityId").html(data);
            $("#" + inputName + "_cityId").get(0).selectedIndex = 0;
        });
    }
}

function cityChange(inputName,countySelectUrl) {
    var citySelValue = $("#" + inputName + "_cityId").val();
    $.get(countySelectUrl+'?cityId='+citySelValue, function (data) {
        $("#" + inputName + "_countyId").html(data);
        $("#" + inputName + "_countyId").get(0).selectedIndex = 0;
    });
}

function initDistrictSelect(inputName, citySelectUrl, countySelectUrl) {
    var provSelValue = $("#" + inputName + "_provinceId").val().trim();

    $.get(citySelectUrl + '?provinceId=' + provSelValue, function (data) {
        $("#" + inputName + "_cityId").append(data);
        $("#" + inputName + "_cityId").get(0).selectedIndex = 0;

        $.get(countySelectUrl + '?cityId=' + $("#" + inputName + "_cityId").val(), function (data) {
            $("#" + inputName + "_countyId").html(data);
        });
    });
}

function changeSecurityCodePhoto(photoId){
    // 增加时间参数强制浏览器请求服务器刷新
    var append = '?' + new Date().getTime() + '_' + Math.random();
    $("#"+photoId).attr("src",$("#"+photoId).attr("src")+append);
}
