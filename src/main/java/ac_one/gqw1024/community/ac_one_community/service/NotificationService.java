package ac_one.gqw1024.community.ac_one_community.service;

import ac_one.gqw1024.community.ac_one_community.dto.NotificationDto;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.model.User;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/31 20:35
 */
public interface NotificationService {
    PaginationDto UserNotificationlist(Long userid, Integer page, Integer pageSize);

    Long unReadCount(Long id);

    NotificationDto read(Long id, User user);
}
