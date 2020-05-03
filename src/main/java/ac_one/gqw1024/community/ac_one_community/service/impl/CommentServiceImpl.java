package ac_one.gqw1024.community.ac_one_community.service.impl;

import ac_one.gqw1024.community.ac_one_community.dao.CommentMapper;
import ac_one.gqw1024.community.ac_one_community.dao.QuestionExtMapper;
import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.CommentDto;
import ac_one.gqw1024.community.ac_one_community.enums.CommentTypeEnum;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import ac_one.gqw1024.community.ac_one_community.model.*;
import ac_one.gqw1024.community.ac_one_community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 【回复】业务接口实现类
 * @author GQW1024
 * @version 1.0
 * @date 2020/4/4 22:20
 */
@Service
@Transactional//为这个service中的所有事务添加事务处理
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 插入回复事务，其中包含插入回复，以及修改回复数两个事务
     * 使用@Transactional注解添加的事务处理，使这两个操作要么都成功，要么都失败
     * @param comment
     * @param questionId
     * @return
     */
    @Override
    public int insert(Comment comment, Long questionId) {
        //如果回复的父类id为空，或等于0
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //如果回复类型为空或没有在CommentTypeEnum（回复类型枚举类）中注册类型
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        //StringUtils.isBlank()方法，可以同时判断字符串为null或""
        if (StringUtils.isBlank(comment.getContent()) || comment.getContent().length() == 0){
            System.out.println("发生了错误！");
            throw new CustomizeException(CustomizeErrorCode.NULL_COMMENT_CONTENT);//空的回复内容
        }
        //如果回复内容为空或为空字符串
        if(comment.getCommentator() == null || comment.getCommentator() == 0){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        //查询当前评论的用户
        User user = userMapper.selectByPrimaryKey(comment.getCommentator());
        //如果用户未注册,这里是第二次验证用户，防止意外
        if(user == null){
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_REGISTER);
        }
        //如果用户回复的是另一个回复
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //判断该回复是否存在
             Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
             if(dbcomment == null){
                 throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
             }
             Question question = new Question();
             question.setId(questionId);
             question.setCommentCount(1);
             questionExtMapper.incCommentCount(question);//评论数加1
        }
        else{
            //如果用户回复的是某个问题,判断该问题是否存在
            Question dbquestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbquestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            dbquestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbquestion);//问题评论数加1
        }
        return commentMapper.insertSelective(comment);
    }

    /**
     * 分页回复列表
     * 此方法基本照搬问题列表的分页方法
     * @param id
     * @param type
     * @return
     */
    @Override
    public List<CommentDto> listByQuestionIdAndType(Long id, int page, int pageSize, CommentTypeEnum type) {
//        PaginationDto paginationDto = new PaginationDto();//页面信息类
//
//        //计算总页数   (目前位置信息量还不算太大，所以先使用int)
//        CommentExample example = new CommentExample();
//        Integer totalCount = (int) commentMapper.countByExample(example);//获取数据总数
//        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数
//
//        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】
//        //空的条件代表查询所有/    逆向生成的分页插件，指定页码偏移量以及数据数量后直接使用即可
//        List<Comment> comments = commentMapper.selectByExampleWithRowbounds(new CommentExample(),new RowBounds(pageOffect,pageSize));//获取分页后的问题列表
//        List<CommentDto> commentDtoList = new ArrayList<>();//需要返回到前端的数据列表
//        for (Comment c : comments) {
//            User user = userMapper.selectByPrimaryKey(c.getCommentator());//之后通过作者id获取问题作者信息
//            CommentDto commentDto = new CommentDto();//question传输工具类
//            BeanUtils.copyProperties(c,commentDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
//            commentDto.setGmtNow(System.currentTimeMillis()-c.getGmtCreate());//设置距离创建时间过了多少时间
//            commentDto.setCommentCreator(user);//之后，整合【回复】与【用户】信息
//            commentDtoList.add(commentDto);//将整合好的信息添加到列表中去
//        }
//
//        paginationDto.setCommentDtoList(commentDtoList);//存入【回复】列表
//        return paginationDto;
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
                //.andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");//按时间降序
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //转为Set结构将所有回复过问题的用户id去重,获取去重的评论人
        Set<Long> commentators = comments.stream().map(m -> m.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);//再将去重后的Set转为list

        //获取所有评论人的信息并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);//查询出所有评论人的信息
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//将user列表转为Map,key为user的id,valuse就是user本身。
        //转换 commit 为 commentDto
        List<CommentDto> commentDtoList = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);//复制comment中的所有同名属性
            commentDto.setGmtNow(System.currentTimeMillis()-comment.getGmtCreate());//设置距离创建时间过了多少时间

            commentDto.setCommentCreator(userMap.get(comment.getCommentator()));//设置用户
            return commentDto;
        }).collect(Collectors.toList());//需要返回到前端的【回复】数据列表

        return commentDtoList;
    }
}
