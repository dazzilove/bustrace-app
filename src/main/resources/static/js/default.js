
function goHistoryBack() {
    history.back();
}

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

function setInputCheckedByValue() {
    $(":input").each(function() {
        var obj = $(this);
        var type = obj.attr('type');

        if (type == "radio") {
            var val = obj.val();
            var dataInputValue = obj.attr("data-input-value");
            if(val == dataInputValue) {
                obj.attr("checked", "checked");
            }
        }
        if (type == "checkbox") {
            var val = obj.val();
            var dataInputValue = obj.attr("data-checked-YN");
            if(dataInputValue == "Y") {
                obj.attr("checked", "checked");
            }
        }
        if (type == "date") {
            var dataInputValue = obj.attr("data-input-value");
            obj.val(dataInputValue);
        }
        if (type == "text") {
            var dataInputValue = obj.attr("data-input-value");
            obj.val(dataInputValue);
        }
    });

    $("select").each(function() {
        var dataInputValue = $(this).attr("data-input-value");
        $(this).val(dataInputValue);
    });
}