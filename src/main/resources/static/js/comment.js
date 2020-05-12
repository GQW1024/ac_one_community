
/**
 * 用户提交问题后异步刷新问题
 */
function postComment(parentId,content,type) {
    var questionId = $("#questionID").val();
    if($("#ifTourist").val() != "true"){//如果当前用户不是游客
        if(!content){
            alert("请填写具体回复内容后重试");
            return;
        }else {
            $.ajax({
                type: "POST",
                url: "/comment",
                data: JSON.stringify({
                    "questionId": questionId,
                    "parentId": parentId,
                    "content": content,
                    "type": type
                }),
                dataType: "json",
                contentType: "application/json; charset=UTF-8",
                success: function (response) {
                    if (response.code == 200) {//如果回复问题成功则刷新回复列表
                        if (type == 1) {
                            $("#questionCommentContent").val("");//回复成功后将问题回复框中的内容清除
                            $("#comment_count").text(parseInt($("#comment_count").text())+1);//让问题的回复数加1
                            ReloadQuestionComments(parentId, type);//回复成功后加载问题列表
                        }else if (type == 2){
                            $("#parentSecCommentList"+parentId).text(parseInt($("#parentSecCommentList"+parentId).text())+1)
                            ReloadCommentComments(parentId,type,0);//如果回复的是二级回复，则加在对应回复的二级回复列表
                        }

                    } else {
                        if (response.code == 2003) {//这个返回code代表用户未登录
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

/**
 * 回复问题
 */
function QuestionComment() {
    var questionId = $("#questionID").val();//具体被回复的问题的id
    var questionContent = $("#questionCommentContent").val();//回复的内容
    postComment(questionId,questionContent,1);
    //var questionCommentCreatorID = $("#questionCommentCreatorID").val();//具体被回复问题的创建者ID
    //向被回复的用户发送通知
    //被回复的问题
    //被回复的用户
}

/**
 * 回复回复
 */
function CommentComment(e) {
    var parentCommentID = e.getAttribute("data-parentId");
    var parentCommentName= e.getAttribute("data-parentName");
    var content = $("#input-"+parentCommentID).val();
    if(parentCommentName != null){//如果被回复的二级回复的创建者的名字不为空
        content = parentCommentName + content;//@被回复的用户名：+ 回复内容
    }
    postComment(parentCommentID,content,2);
}

/**
 * 将java的long型格式化为时间
 */
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

/**
 * 点击二级回复按钮，选择是否加载二级折叠评论
 */
function collapseComments(e) {
    var parentId = e.getAttribute("data-comment-id");//被点击的该回复的ID
    var comments = $("#comment-id-"+parentId);//对应该回复的二级回复列表ID
    var isopen = e.getAttribute("data-collapse");//二级回复列表是否打开

    //默认，如果没有特数指定条件，且变量不是boolean类型，那么值为空就是false,值不为空就是true
    if(isopen){//如果有值，则代表示当前二级回复列表已经打开，那么就关闭二级评论
        //调用Jquery中的基本方法时，千万记得加上" $() "，否则默认调用JS中的方法，这样子会报 xxx is not a function 异常
        //$(e).css("color","#999999");
        e.classList.remove("active");//取消设置颜色
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    }else{//否则打开二级评论
        //$(e).css("color","#499ef3");
        e.classList.add("active");//设置颜色
        comments.addClass("in");
        e.setAttribute("data-collapse","in");//添加打开二级评论的变量标识
    }
    ReloadCommentComments(parentId,2,0);//重新加载二级回复列表
    console.log(parentId);
}

/**
 * 重新加载问题的回复
 * @param parentId
 * @param type
 * @constructor
 */
function ReloadQuestionComments(parentId,type){
    $.ajax({
        type: "GET",
        url: "/commentListById?parentId="+parentId+"&type="+type,
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            if (type == 1 && data != null && data.length != 0) {
                console.log(data.length);
                var html = "";
                $("#commentList").html("");//先清空列表
                for (var i = 0; i < data.length; i++) {
                    if (data[i].commentCreator != null) {
                        html += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments\">" +
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
                            "<div>" + data[i].content + "</div>" +
                            "<div class=\"comment-menu\">" +
                            "<span class=\"glyphicon glyphicon-thumbs-up icon\"></span>" +
                            "<span class=\"glyphicon glyphicon-thumbs-down icon\"></span>" +
                            "<span class=\"glyphicon glyphicon-comment icon\" data-comment-id=\""+data[i].id+"\" onclick=\"collapseComments(this)\"></span>" +
                            "<span class=\"icon-comment-count\" id=\"parentSecCommentList"+data[i].id+"\">"+data[i].commentCount+"</span>" +
                            "<span class=\"float-right icon\">" + moment(data[i].gmtCreate).format("YYYY-MM-DD") + "</span>" +//${#dates.format(comment.gmtCreate,'YYYY-MM-dd')}
                            "</div>" +
                            "<!-- 二级评论列表 -->"+
                            "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse second-comment-list\" id=\"comment-id-"+data[i].id+"\"></div>"+
                            "</h5>" +
                            "</div>" +
                            "</div>" +
                            "</div>"
                    }
                }
                $("#commentList").html(html);//将重新获取的列表插入标签中
            }
        }
    });
}

/**
 * 重新加载二级回复列表
 * @param parentId
 * @param type
 * @param thisCommentId  指定点击的二级回复的ID,作用为是否异步将回复框刷新到指定位置，并设置对应信息，1为设置，0为取消
 * @constructor
 */
function ReloadCommentComments(parentId, type, thisCommentId) {
    var html = "";
    $.ajax({
        type: "GET",
        url: "/commentListById?parentId="+parentId+"&type="+type,
        contentType: "application/json; charset=UTF-8",
        success: function (data) {
            if (data != null && data.length != 0) {
                console.log(data.length);
                $("#comment-id-"+parentId).html("");//先清空列表
                for (var i = 0; i < data.length; i++) {
                    if (data[i].commentCreator != null) {
                        ifListNull = 0;//二级列表不为空
                        html += " <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment\">"+
                        "<div class=\"media\">"+
                            "<div class=\"media-left\">"+
                            "<a  href=\"#\">"+
                            "<img class=\"media-object img-rounded main_list_item_imgSize\" src=\""+data[i].commentCreator.avatarUrl+"\" alt=\"...\"/>"+
                            "</a>"+
                            "</div>"+
                            "<div class=\"media-body comment-username\">"+
                            "<h5 class=\"media-heading\">"+
                            "<input type=\"hidden\" value=\""+data[i].commentCreator.id+"\">"+
                            "<a href=\"#\">"+data[i].commentCreator.name+"</a>"+
                            "</h5>"+
                            "<div>"+data[i].content+"</div>"+
                            "<!--一些社交操作-->"+
                            "<div class=\"comment-menu\">"+
                            "<span class=\"glyphicon glyphicon-thumbs-up icon\"></span>"+
                            "<span class=\"glyphicon glyphicon-thumbs-down icon\"></span>"+
                            "<!--二级回复按钮-->"+
                            //后续制作点击后回复指定用户的功能，点击这个按钮后刷星列表，并在这个回复的下方显示回复框
                            "<span class=\"glyphicon glyphicon-comment icon\" onclick=\"ReloadCommentComments("+parentId+","+2+","+data[i].id+")\"></span>"+
                            "<span class=\"float-right icon\">" + moment(data[i].gmtCreate).format("YYYY-MM-DD") + "</span>"+
                            "</div>"+
                            "</div>"+
                            "</div>"+
                            "</div>"
                        //如果是从二级列表点击回复后所引发的刷新，则将回复框设置在该二级回复的下面，并显示对应的用户信息
                        if(thisCommentId !=0 && thisCommentId == data[i].id){
                            html += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">" +
                                //通过判断文本框中的data-parentName是否为空，如果不为空则后续向指定用户发送被回复通知
                                "<input type=\"text\" class=\"form-control\" autofocus=\"autofocus\" placeholder=\"@"+data[i].commentCreator.name+"：\" id=\"input-" + parentId + "\">" +
                                "<button type=\"button\" class=\"btn btn-success pull-right\" data-parentId=\""+parentId+"\" data-parentName=\"@"+data[i].commentCreator.name+"：\" onclick=\"CommentComment(this)\">回复</button>\n" +
                                "</div>"
                        }
                    }
                }
            }
            if(thisCommentId == 0) {//如果为0，则代表这次异步刷新要么是以及列表点击的刷新，要么是提交回复后的刷新，这时，将回复框设置在二级列表的底部
                //最后记得将回复框也加上，防止用户还需回复或者该回复没有二级列表，则只显示回复框。
                html += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">" +
                    "<input type=\"text\" class=\"form-control\" autofocus=\"autofocus\" placeholder=\"评论一下···\" id=\"input-" + parentId + "\">" +
                    "<button type=\"button\" class=\"btn btn-success pull-right\" data-parentId=\""+parentId+"\"  onclick=\"CommentComment(this)\">回复</button>\n" +
                    "</div>"
            }
            //在这里记得把回复框也加上
            $("#comment-id-"+parentId).html(html);//将重新获取的列表插入标签中
        }
    });

}