

function checkAndRemoveClass(obj, className) {
    if ($(obj).hasClass(className)) {
        $(obj).removeClass(className);
    }
}