function postComment_Q() {
    var questionId = $("#questionID").val();//具体回复对象的id，这里的是被回复的问题的id
    var questionContent = $("#questionCommentContent").val();//回复的内容
    //var commentator = $("#commentator").val();回复者的id也可以从后端session中取出
    var commentType = 1;//回复的类型，1代表为回复问题的回复。
    if($("#ifTourist").val() != "true"){//如果当前用户不是游客
        if(!questionContent){
            alert("请填写具体回复内容后重试");
            return;
        }else {
            $.ajax({
                type: "POST",
                url: "/comment",
                data: JSON.stringify({
                    "questionId": questionId,
                    "parentId": questionId,
                    "content": questionContent,
                    "type": commentType
                }),
                dataType: "json",
                contentType: "application/json; charset=UTF-8",
                success: function (response) {
                    if (response.code == 200) {//如果回复问题成功则刷新回复列表
                        $("#questionCommentContent").val("");
                        $.ajax({
                            type: "GET",
                            url: "/commentListByQuestionId?questionId="+questionId,
                            contentType: "application/json; charset=UTF-8",
                            success: function (data){
                                if (data != null && data.length != 0){
                                    console.log(data.length+"  "+data[0].avatarUrl+"  "+data[0].commentCreator.name+"  "+data[0].gmtCreate);
                                    $("#comment_count").text(data.length);
                                    var html = "";
                                    $("#commentList").html("");//先清空列表
                                    for(var i = 0 ; i < data.length ; i++) {
                                        if (data[i].commentCreator != null) {
                                            html+="<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments\">"+
                                                "<div class=\"media\">" +
                                                "<div class=\"media-left\">" +
                                                "<a  href=\"#\">" +
                                                "<img class=\"media-object img-rounded main_list_item_imgSize\" src=\"" + data[i].commentCreator.avatarUrl + "\" alt=\"...\">" +
                                                "</a>" +
                                                "</div>" +
                                                "<div class=\"media-body comment-username\">" +
                                                "<h5 class=\"media-heading\">" +
                                                "<input type=\"hidden\" value=\"" + data[i].commentCreator.id + "\">" +
                                                "<a href=\"#\">" + data[i].commentCreator.name + "</a>" +
                                                "<div>"+data[i].content+"</div>" +
                                                "<div class=\"comment-menu\">" +
                                                "<span class=\"glyphicon glyphicon-thumbs-up icon\"></span>" +
                                                "<span class=\"glyphicon glyphicon-thumbs-down icon\"></span>" +
                                                "<span class=\"glyphicon glyphicon-comment icon\"></span>" +
                                                "<span class=\"float-right icon\">"+formatDate(data[i].gmtCreate)+"</span>" +//${#dates.format(comment.gmtCreate,'YYYY-MM-dd')}
                                                "</div>" +
                                                "</h5>" +
                                                "</div>"+
                                                "</div>"+
                                                "</div>"
                                        }
                                    }
                                    $("#commentList").html(html);//将重新获取的列表插入标签中
                                }
                            }
                        })


                    } else {
                        if (response.code == 2003) {
                            var ifAccepted = confirm(response.message);//弹出确认框
                            if (ifAccepted) {//返回值为true,代表点击了弹出框的确认按钮
                                //获取gituser信息，登录用户
                                window.open("https://github.com/login/oauth/authorize?client_id=68f471c8ef16a67e27ad&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                                window.localStorage.setItem("closable", true);
                            }
                        } else {//?
                            console.log(response.message);
                            alert(response.message);
                        }
                    }
                }
            });
        }
    }else{
        var ifAccepted = confirm("请登陆后重试");//弹出确认框
        if(ifAccepted){//返回值为true,代表点击了弹出框的确认按钮
            //获取gituser信息，登录用户
            window.open("https://github.com/login/oauth/authorize?client_id=68f471c8ef16a67e27ad&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
            window.localStorage.setItem("closable",true);
        }
    }
}

//格式化时间
function formatDate(longDate) {
    var date = new Date(longDate);
    var yyyy = date.getFullYear();
    var mm = date.getMonth() + 1;
    if (mm < 10) {
        mm = "0" + mm;
    }
    var dd = date.getDate();
    if (dd < 10) {
        dd = "0" + dd;
    }
    return yyyy + "-" + mm + "-" + dd;
}