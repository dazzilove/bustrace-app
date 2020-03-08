

function checkAndRemoveClass(obj, className) {
    if ($(obj).hasClass(className)) {
        $(obj).removeClass(className);
    }
}

function changeTab(obj) {
    $(".subTab li a").each(function() {
        var id = $(this).attr("id");
        $(this).removeClass("active");
        $("#" + id + "Area").css("display", "none");
    });

    var tabId = $(obj).attr("id");
    $("#" + tabId).addClass("active");
    $("#" + tabId + "Area").css("display", "block");
}