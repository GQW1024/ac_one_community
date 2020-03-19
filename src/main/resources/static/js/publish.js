$(function () {//publish.html 用

    $("#submit").click(function(){
        if ($("#title").val() == "" || $("#title").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标题不能为空");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else if($("#description").val() == "" || $("#description").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("问题内容不能为空");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else if($("#tag").val() == "" || $("#tag").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标签不能为空");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else{
            $("#alert_box").attr("class","alert alert-success col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("当前状态可提交");
            $("#submit").attr("disabled",false); //设置不可点击为false
        }
    })
    $("#title").blur(function () {
        if ($("#title").val() == "" || $("#title").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标题不能为空×");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else{
            $("#alert_box").attr("class","alert alert-success col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标题可用√");
            $("#submit").attr("disabled",false); //设置不可点击为false
        }
    })
    $("#description").blur(function () {
        if ($("#description").val() == "" || $("#description").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("问题内容不能为空×");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else{
            $("#alert_box").attr("class","alert alert-success col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("问题内容√");
            $("#submit").attr("disabled",false); //设置不可点击为false
        }
    })
    $("#tag").blur(function () {
        if ($("#tag").val() == "" || $("#tag").val() == null){
            $("#alert_box").attr("class","alert alert-warning col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标签不能为空×");
            $("#submit").attr("disabled",true); //设置不可点击为true
        }else{
            $("#alert_box").attr("class","alert alert-success col-lg-9 col-md-12 col-sm-12 col-xs-12");
            $("#alert_box").html("标签可用√");
            $("#submit").attr("disabled",false); //设置不可点击为false
        }
    })
})