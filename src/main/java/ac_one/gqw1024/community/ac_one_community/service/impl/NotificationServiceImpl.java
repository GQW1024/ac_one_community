package ac_one.gqw1024.community.ac_one_community.service.impl;

import ac_one.gqw1024.community.ac_one_community.dao.CommentMapper;
import ac_one.gqw1024.community.ac_one_community.dao.NotificationMapper;
import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.NotificationDto;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.enums.NotificationStatusEnum;
import ac_one.gqw1024.community.ac_one_community.enums.NotificationTypeEnum;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import ac_one.gqw1024.community.ac_one_community.model.*;
import ac_one.gqw1024.community.ac_one_community.service.NotificationService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/31 20:36
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Override
    public PaginationDto UserNotificationlist(Long userid, Integer page, Integer pageSize) {
        PaginationDto<NotificationDto> paginationDto = new PaginationDto();//页面信息类

        //计算总页数
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userid);//查询该用户的通知总数
        Integer totalCount = (int)notificationMapper.countByExample(example);//获取数据总数
        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数

        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】

        //查找该用户的所有问题并分页
        //逆向生成的分页插件，指定页码偏移量以及数据数量即可
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(pageOffect,pageSize));//获取分页后用户本人的问题列表
        if (notifications.size() == 0){
            return paginationDto;
        }else {
            //需要返回到前端的通知dto列表
            List<NotificationDto> notificationDtoList = notifications.stream().map(notification -> {
                NotificationDto notificationDto = new NotificationDto();//notification传输工具类
                BeanUtils.copyProperties(notification, notificationDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
                notificationDto.setTypename(NotificationTypeEnum.nameOf(notification.getType()));//设置被回复对象的类型的名称
                return notificationDto;//将整合好的信息添加到列表中去
            }).collect(Collectors.toList());
            paginationDto.setData(notificationDtoList);//存入问题列表
        }

        return paginationDto;
    }

    @Override
    public Long unReadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    @Override
    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        //如果当前的用户和该通知的具体接收用户不是同一个人
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);//抛错。。。
        }
        else if(notification.getReceiver() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);//抛错。。。
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);//修改阅读装态
        /**
         *之后优化界面显示，通知功能基本OK
         */

        NotificationDto notificationDto = new NotificationDto();//notification传输工具类
        BeanUtils.copyProperties(notification, notificationDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
        notificationDto.setTypename(NotificationTypeEnum.nameOf(notification.getType()));//设置被回复对象的类型的名称

        return notificationDto;//将整合好的信息添加到列表中去

    }
}
