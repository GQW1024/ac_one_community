package ac_one.gqw1024.community.ac_one_community.exception;

/**
 * 自定义异常
 * 目的，方便提示
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();//获取枚举类CustomizeErrorCode中对应的错误信息。
    }

    @Override
    public String getMessage() {
        return message;
    }
}
