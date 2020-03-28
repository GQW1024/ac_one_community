package ac_one.gqw1024.community.ac_one_community.service.impl;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Question表的service层
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int createQuestion(Question question) {
        return questionMapper.create(question);
    }

    /**
     * 查询当前页问题的数据，并与对应的用户数据进行整合，最后整合成页面信息返回给Controller层
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PaginationDto LimitQuestionDtolist(int page, int pageSize) {
        PaginationDto paginationDto = new PaginationDto();//页面信息类

        //计算总页数
        Integer totalCount = questionMapper.listCount();//获取数据总数
        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数

        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】
        List<Question> questions = questionMapper.pageList(pageOffect,pageSize);//获取分页后的问题列表
        List<QuestionDto> questionDtoList = new ArrayList<>();//需要返回到前端的数据列表

        for (Question q : questions) {
            User user = userMapper.findByID(q.getCreator());//之后通过作者id获取问题作者信息
            QuestionDto questionDto = new QuestionDto();//question传输工具类
            BeanUtils.copyProperties(q,questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
            questionDto.setGmtNow(System.currentTimeMillis()-q.getGmtCreate());
            questionDto.setQuestionCreater(user);//之后，整合【问题】与【用户】信息
            questionDtoList.add(questionDto);//将整合好的信息添加到列表中去
        }

        paginationDto.setQuestionDtoList(questionDtoList);//存入问题列表
        return paginationDto;
    }

    /**
     * 获取用户本人发布的问题列表
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PaginationDto UserQuestionlist(Integer userid, Integer page, Integer pageSize) {
        PaginationDto paginationDto = new PaginationDto();//页面信息类

        //计算总页数
        Integer totalCount = questionMapper.listCountByUserID(userid);//获取数据总数
        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数

        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】
        List<Question> questions = questionMapper.userpageList(userid,pageOffect,pageSize);//获取分页后用户本人的问题列表
        List<QuestionDto> questionDtoList = new ArrayList<>();//需要返回到前端的数据列表

        for (Question q : questions) {
            User user = userMapper.findByID(q.getCreator());//之后通过作者id获取问题作者信息
            QuestionDto questionDto = new QuestionDto();//question传输工具类
            BeanUtils.copyProperties(q,questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
            questionDto.setGmtNow(System.currentTimeMillis()-q.getGmtCreate());
            questionDto.setQuestionCreater(user);//之后，整合【问题】与【用户】信息
            questionDtoList.add(questionDto);//将整合好的信息添加到列表中去
        }

        paginationDto.setQuestionDtoList(questionDtoList);//存入问题列表
        return paginationDto;
    }

    /**
     * 使用问题id来获取整合了用户与问题信息的QuestionDto对象
     * @param id
     * @return
     */
    @Override
    public QuestionDto getQuestionDtoById(Integer id) {
        Question question = questionMapper.findQuestion(id);//根据问题id查询问题信息
        QuestionDto questionDto = null;
        if (question != null) {//防止持有该id的问题不存在，报空指针异常
            questionDto = new QuestionDto();
            User user = userMapper.findByID(question.getCreator());
            //整合用户与用户发布的问题的信息
            questionDto.setQuestionCreater(user);
            BeanUtils.copyProperties(question , questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
        }
        return questionDto;
    }

    /**
     * 获取问题列表的数据总条数
     * @return
     */
    @Override
    public int QuestionListSize() {
        return questionMapper.listCount();
    }


}
