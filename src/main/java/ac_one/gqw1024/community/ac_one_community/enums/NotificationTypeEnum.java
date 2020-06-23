package ac_one.gqw1024.community.ac_one_community.enums;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/30 17:37
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了您的问题"),
    REPLY_COMMENT(2,"回复了您的回复"),
    QUESTION_THUMBS_UP(3,"点赞了您的问题"),
    COMMENT_THUMBS_UP(4,"点赞了您的回复")
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOf(int type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
