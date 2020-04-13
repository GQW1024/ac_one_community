package ac_one.gqw1024.community.ac_one_community.enums;

/**
 * 评论类型枚举类
 * @author GQW1024
 * @version 1.0
 * @date 2020/4/4 22:11
 */
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer Type;

    /**
     * 检测该类型是否有在本枚举类中注册
     * @param type
     * @return
     */
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return Type;
    }

    CommentTypeEnum(Integer type) {
        Type = type;
    }
}
