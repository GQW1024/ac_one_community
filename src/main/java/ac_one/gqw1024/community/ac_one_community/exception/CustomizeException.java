package ac_one.gqw1024.community.ac_one_community.exception;

/**
 * 自定义异常
 * 目的，方便提示
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(CustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();//获取枚举类CustomizeErrorCode中对应的错误信息。
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
