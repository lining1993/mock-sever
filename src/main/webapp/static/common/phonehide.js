$(function () {
    $(".phonehide").each(function (index, element) {
        var li = $(this);
        var phonehide = li.text();
        li.text("");
        var src = $(this).attr('data-src');
        if(src =='idnum') {
            var content = "<span>"+ changeIdNum(phonehide) +"</span>  ";
            content += "<a class='glyphicon glyphicon-eye-open' data-num='"+phonehide+"' data-src='"+src+"' ></a>";
            li.append(content);
        }else{
            var content = "<span>"+ changeIdPhone(phonehide) +"</span>  ";
            content += "<a class='glyphicon glyphicon-eye-open' data-num='"+phonehide+"' data-src='"+src+"' ></a>";
            li.append(content);
            //li.text(changeIdPhone(phonehide));
        }
    });

    $(".phonehide").delegate('.glyphicon-eye-open', 'click', function(index, element){
        eyeOpenClick(this);
    });

    function eyeOpenClick(obj){
        $(".phonehide").each(function (index, element) {
            var value = $(this).find("span").first().text();
            var src = $(this).attr('data-src');
            if(src =='idnum') {

                $(this).find("span").first().text(changeIdNum(value));
            }else{

                $(this).find("span").first().text(changeIdPhone(value));
            }
        });
        var num= $(obj).attr("data-num");
        var span = $(obj).prev();
        span.text(num);
        //var phonehide = $(this).attr('data-count');
        //$(this).text(phonehide);
    }

    function changeIdNum(value){
        if(value.length < 7){
            return value;
        }
        var len = (value.length - 4) - 3;
        var xing = '********************';
        return value.substr(0, 3) + xing.substr(0,len) + value.substr(value.length - 4);
    }
    function changeIdPhone(value){
        if(value.length < 5){
            return value;
        }
        var len = (value.length - 3) - 2;
        var xing = '********************';
        return value.substr(0, 2) + xing.substr(0,len) + value.substr(value.length - 3);
    }

});
