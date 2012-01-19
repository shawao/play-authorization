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
}


function deleteByPost(action, entityId,confirmation) {
    if (entityId.trim() == '')
        alert('Invalid ID given');
    if (confirm(confirmation)) {
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
    }
}