package ac_one.gqw1024.community.ac_one_community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"您找的问题不存在呢，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004,"服务器要冒烟啦! 稍后再试试?"),
    TYPE_PARAM_WRONG(2005,"当前回复类型错误或不存在!"),
    COMMENT_NOT_FOUND(2006,"抱歉，您当前回复的评论不存在!"),
    NULL_COMMENT_CONTENT(2007,"请输入回复内容后进行回复!"),
    USER_NOT_REGISTER(2008,"当前用户未注册，请注册后回复!" ),
    READ_NOTIFICATION_FAIL(2009,"兄弟你这是读别人的信息呢？" ),
    NOTIFICATION_NOT_FOUND(2010,"消息莫非是不翼而飞了？" );


    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
