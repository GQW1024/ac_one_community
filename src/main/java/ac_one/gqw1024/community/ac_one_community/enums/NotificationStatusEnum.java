package ac_one.gqw1024.community.ac_one_community.enums;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/30 19:24
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
