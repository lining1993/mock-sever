var load_modal = '<!-- 加载动画 -->'
    + '<div class="modal loading-modal" id="loading-modal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" keyboard="false">'
    + '<div class="modal_wrapper">'
    + '<div class="modal-dialog">'
    + '<div class="modal-body">'
    + '<div class="sk-wave">'
    + '<div class="sk-rect sk-rect1"></div>'
    + '<div class="sk-rect sk-rect2"></div>'
    + '<div class="sk-rect sk-rect3"></div>'
    + '<div class="sk-rect sk-rect4"></div>'
    + '<div class="sk-rect sk-rect5"></div>'
    + '</div>'
    + '</div> <!-- ./modal-body -->'
    + '</div> <!-- ./modal-dialog -->'
    + '</div> <!-- ./modal_wrapper -->'
    + '</div> <!-- ./modal -->';

function getParent(parent_url, child_url, parent_obj, child_obj, parent_name, child_name, parent_value, child_value,ctx) {
    parent_value = parent_value || 0;
    child_value = child_value || 0;
    $.ajax({
        url: parent_url,
        type: 'GET',
        dataType: 'json',
        /*functionbeforeSend: function () {
            $('.loading-modal').modal('show');
            parent_obj.show();
            parent_obj.shosen();
            parent_obj.hide();
        },*/
        success: function (data) {
            parent_obj.empty();
            parent_obj.append('<option value="">' + parent_name + '</option>');
            $.each(data, function (index, element) {
                var dplacehoder = '';
                if (parent_value != 0 && parent_value == element.code) {
                    dplacehoder = 'data-placeholder="' + element.name + '" selected';
                    getChildren(child_url, parent_obj, child_obj, child_name, parent_value, child_value,ctx);
                }
                parent_obj.append('<option value="' + element.code + '" ' + dplacehoder + '>' + element.name + '</option>');
            })
        }
    }).done(function () {
        parent_obj.trigger("chosen:updated");
        $('.loading-modal').modal('hide');
    }).fail(function () {
        console.log('获取' + parent_name + '失败！');
    });
}

// 获取子类函数
function getChildren(child_url, parent_obj, child_obj, child_name, parent_value, child_value,ctx) {
    parent_value = parent_value || 1;
    child_value = child_value || 0;
    $.ajax({
        url: child_url,
        type: 'get',
        dataType: 'json',
        data: {districtCode: parent_value},
        /*beforeSend: function () {
            $('.loading-modal').modal('show');
            child_obj.show();
            child_obj.chosen();
            child_obj.hide();
        },*/
        success: function (data) {
            child_obj.empty();
            child_obj.append('<option value="">' + child_name + '</option>');
            $.each(data, function (index, element) {
                var dplacehoder = '';
                if (child_value != 0 && child_value == element.code) {
                    dplacehoder = 'data-placeholder="' + element.name + '" selected';
                }
                child_obj.append('<option value="' + element.code + '"' + dplacehoder + '>' + element.name + '</option>');
            })
        }
    })
        .done(function () {
            child_obj.trigger("chosen:updated");
            $('.loading-modal').modal('hide');
        })
        .fail(function () {
            console.log('获取' + child_name + '失败！');
        });
}


function getProvinces(province, city,ctx) {
    getParent(ctx+'/district/provinces', ctx+'/district/newcities', $('.province'), $(".city"), '所有省份', '请选择城市', province, city)
}

function getCities(province, ctx,city) {
    getChildren(ctx+'/district/newcities', $('.province'), $(".city"), '请选择城市', province, city);
}

function getChannelParent(parent, child) {
    getParent('/channel/parent', '/channel/children', $('.channel-parent'), $(".channel-children"), '所有一级渠道', '请选择二级渠道', parent, child);
}

function getChannelChilren(parent, child) {
    getChildren('/channel/children', $('.channel-parent'), $(".channel-children"), '请选择二级渠道', parent, child);
}

$(document).ready(function () {
    /*$('body').append(load_modal);
    if (localStorage.getItem(window.location.pathname) != '' && localStorage.getItem(window.location.pathname) == "display") {
        $('.search-options i').removeClass('fa-arrow-circle-down');
        $('.search-options i').addClass('fa-arrow-circle-up');
        $('.search-options-content').show();
        $('.chosen-container-single').css('width', '200px');
    } else {
        $('.search-options i').removeClass('fa-arrow-circle-up');
        $('.search-options i').addClass('fa-arrow-circle-down');
        $('.search-options-content').hide();
    }
    $('.search-options').click(function () {
        if ($('.search-options-content').is(":hidden")) {
            $('.search-options i').removeClass('fa-arrow-circle-down');
            $('.search-options i').addClass('fa-arrow-circle-up');
            $('.search-options-content').show();
            $('.chosen-container-single').css('width', '200px');
            localStorage.setItem(window.location.pathname, "display");
        } else {
            $('.search-options i').removeClass('fa-arrow-circle-up');
            $('.search-options i').addClass('fa-arrow-circle-down');
            $('.search-options-content').hide();
            localStorage.setItem(window.location.pathname, "hide");
        }
    });*/
    $('.reset-btn').click(function () {
        $('.search-options-content input[type="text"]').val('');
        $('.search-options-content input[type="input"]').val('');
        $('.search-options-content input[type="number"]').val('');
        $(".search-options-content select").select2().val(null).trigger("change");
       /* $(".chosen-select").chosen().val('').trigger("chosen:updated");
        $(".city").chosen().val('').trigger("chosen:updated");
        $(".city > option:gt(0)").remove();
        $(".city").chosen("destroy").val('').trigger("chosen:updated");
        $(".channel-children").empty();
        $(".channel-children").hide();
        $(".channel-children").next().remove();*/
    });
    $('.province').change(function () {
        var province = $(this).val();
        if (province) {
            getCities(province,ctx);
        } else {
            $(".city").empty();
          // $(".city").chosen("destroy");
            $(".city").hide();
        }
    });
    $('.channel-parent').change(function () {
        var channelParent = $(this).val();
        if (channelParent) {
            getChannelChilren(channelParent);
        } else {
            $(".channel-children").empty();
            $(".channel-children").chosen("destroy");
            $(".channel-children").hide();
        }
    });
});