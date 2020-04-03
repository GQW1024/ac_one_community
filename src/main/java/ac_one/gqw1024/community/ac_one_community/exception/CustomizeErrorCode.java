package ac_one.gqw1024.community.ac_one_community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("您找的问题不存在呢，要不换个试试？");

    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }


}
