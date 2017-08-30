/**
 * Created by lining on 2017/5/31.
 */
/**
 * 消息框
 * @param msg 消息
 * @param title 标题
 * @param autoCloseTime 自动关闭时间单位毫秒,不传为5000毫秒
 * @param callBack 回调函数
 * @return 消息框对象
 */
function showInfo(msg,title,autoCloseTime,callBack){
    if(autoCloseTime === null || autoCloseTime === undefined || autoCloseTime === ''){
        autoCloseTime = 5000;
    }
    if(autoCloseTime != 0){
        autoCloseTime = 'confirm|'+autoCloseTime;
    }else{
        autoCloseTime = false;
    }
    var alertBox = $.alert({
        title: title,
        animation: 'zoom',
        backgroundDismiss: false,
        keyboardEnabled: true,
        autoClose: autoCloseTime,
        content: '<h4 class="text-center" style="word-break: break-all;"><i class="fa fa-exclamation-triangle fa-lg"></i>' + msg + '</h4>',
        confirmButtonClass: 'btn btn-primary pull-right',
        confirmButton: '确定',
        confirm: function(){
            if (typeof callBack === 'function') {
                callBack();
            }
        }
    });
    return alertBox;
}
/**
 * 确认框
 * @param msg 确认消息
 * @param title 标题
 * @param autoCloseTime 自动关闭时间单位毫秒,不传为5000毫秒
 * @param confirmCallBack 确认回调
 * @param cancelCallBack  取消回调
 */
function showConfirm(msg, title, autoCancelTime, confirmCallBack, cancelCallBack) {
    if(autoCancelTime === null || autoCancelTime === undefined || autoCancelTime === ''){
        autoCancelTime = 5000;
    }
    if(autoCancelTime != 0){
        autoCancelTime = 'cancel|'+autoCancelTime;
    }else{
        autoCancelTime = false;
    }
    var confitmBox = $.confirm({
        title: title,
        animation: 'zoom',
        backgroundDismiss: false,
        keyboardEnabled: true,
        autoClose: autoCancelTime,
        content: '<h4 class="text-center" style="word-break: break-all;"><i class="fa fa-exclamation-triangle fa-lg"></i>' + msg + '</h4>',
        cancelButtonClass: 'btn btn-default pull-left',
        confirmButtonClass: 'btn btn-primary pull-right',
        confirmButton: '确定',
        cancelButton: '取消',
        confirm: function () {
            if (typeof confirmCallBack === 'function') {
                confirmCallBack();
            }
        },
        cancel: function(){
            if (typeof cancelCallBack === 'function') {
                cancelCallBack();
            }
        }
    });
    return confitmBox;
}