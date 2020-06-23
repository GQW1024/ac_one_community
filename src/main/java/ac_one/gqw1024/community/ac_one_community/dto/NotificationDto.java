package ac_one.gqw1024.community.ac_one_community.dto;

import ac_one.gqw1024.community.ac_one_community.model.User;
import lombok.Data;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/31 18:16
 */
@Data
public class NotificationDto {
    private Long id;

    private Long notifier;

    private Long receiver;

    private Long outerid;

    private String outerTitle;

    private Integer type;

    private String typename;

    private Long gmtCreate;

    private Integer status;

    private String notifierName;
}
