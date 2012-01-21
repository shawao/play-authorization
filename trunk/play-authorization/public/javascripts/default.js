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


function requireActionByPost(action, entityId,confirmation) {
    if (entityId.trim() == '')
        alert('无效的ID');
    jqConfirm(confirmation,function(){
        //create　a　form　
        var tempForm = document.createElement("form");
        tempForm.action = action;
        tempForm.method = "post";
        document.body.appendChild(tempForm);
        //create　a　submit　button
        var tempInput = document.createElement("input");
        tempInput.type = "hidden";
        tempInput.name = "id";
        tempInput.value = entityId;
        //the　parameter　of　method　in　the　code　of　DispatchAction.
        tempForm.appendChild(tempInput);
        //submit　the　form　
        tempForm.submit();
    });
}