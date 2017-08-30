$(function () {
    $(".api_refresh").on('click',function () {
        $.ajax({
            url: ctx+'/api/refresh',
            type: 'post',
            success: function (data) {
                if (data && data.success) {
                    showInfo(data.message,"Hello",5000,function () {
                        location.reload();
                    });
                }
            }
        });
    });
});
