package ac_one.gqw1024.community.ac_one_community.service.impl;

import ac_one.gqw1024.community.ac_one_community.cache.TagCache;
import ac_one.gqw1024.community.ac_one_community.dao.QuestionExtMapper;
import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.model.QuestionExample;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
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
 * Question表的service层
 */
@Service
@Transactional//为这个service中的所有事务添加事务处理
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public int createOrUpdate(Question question) {
        
        if (question.getId()!=null){//如果id不为空，则此次操作的目的是修改
            //通过用户id与问题id，确认该用户是否与该问题对应，如果为true，则执行修改操作
            QuestionExample questionexample = new QuestionExample();
            questionexample.createCriteria()
                    .andCreatorEqualTo(question.getCreator())
                    .andIdEqualTo(question.getId());
            List<Question> questions = questionMapper.selectByExample(questionexample);
            if(questions.size() == 1 && questions.get(0) != null){//如果通过作者ID与问题ID确定了唯一的问题
                question.setGmtCreate(null);//由于仅需要更新，所以并不需要修改【创建日期】，所以将创建日期设为空
                QuestionExample updateexample = new QuestionExample();
                updateexample.createCriteria()
                        .andCreatorEqualTo(question.getCreator())
                        .andIdEqualTo(question.getId());//通过作者ID以及问题的ID索引到具体要修改的问题
                return questionMapper.updateByExampleSelective(question, updateexample);//更新该问题
            }
        }
        else{//否则执行创建操作
               return questionMapper.insertSelective(question);
        }
        return 0;
    }

    /**
     * 查询当前页问题的数据，并与对应的用户数据进行整合，最后整合成页面信息返回给Controller层
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PaginationDto LimitQuestionDtolist(int page, int pageSize) {
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();//页面信息类

        //计算总页数   (目前位置信息量还不算太大，所以先使用int)
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());//获取数据总数
        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数

        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
                                                                               //空的条件代表查询所有/    逆向生成的分页插件，指定页码偏移量以及数据数量后直接使用即可
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(pageOffect,pageSize));//获取分页后的问题列表
        //获取所有用户的信息后转为集合后去重
        Set<User> disusers = questions.stream().map(question -> userMapper.selectByPrimaryKey(question.getCreator())).collect(Collectors.toSet());
        Map<Long,User> userMap = disusers.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//之后转为Map

        List<QuestionDto> questionDtoList = questions.stream().map(question -> {
            User user = userMap.get(question.getCreator());//之后通过作者id获取问题作者信息
            QuestionDto questionDto = new QuestionDto();//question传输工具类
            BeanUtils.copyProperties(question, questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
            questionDto.setGmtNow(System.currentTimeMillis() - question.getGmtCreate());
            questionDto.setQuestionCreater(user);//之后，整合【问题】与【用户】信息
            return questionDto;//返回questionDto对象
        }).collect(Collectors.toList());
        paginationDto.setData(questionDtoList);//存入问题列表
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
    public PaginationDto UserQuestionlist(Long userid, Integer page, Integer pageSize) {
        PaginationDto<QuestionDto> paginationDto = new PaginationDto<>();//页面信息类

        //计算总页数
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userid);//查询该用户的问题总数
        Integer totalCount = (int)questionMapper.countByExample(example);//获取数据总数
        paginationDto.setPaginationDto(totalCount,page,pageSize);//设置总页数  //设置当前页码 //设置每页显示数

        Integer pageOffect = (paginationDto.getPage()-1)*pageSize;//计算页码偏移量【即当前页面中最后一条数据在数据库中位置】

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userid);//查找该用户的所有问题并分页
                                                                                            //逆向生成的分页插件，指定页码偏移量以及数据数量即可
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(pageOffect,pageSize));//获取分页后用户本人的问题列表
        List<QuestionDto> questionDtoList = new ArrayList<>();//需要返回到前端的数据列表

        //获取所有用户的信息后转为集合后去重
        Set<User> disusers = questions.stream().map(question -> userMapper.selectByPrimaryKey(question.getCreator())).collect(Collectors.toSet());
        Map<Long,User> userMap = disusers.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//之后转为Map
        for (Question q : questions) {
            User user = userMap.get(q.getCreator());//之后通过作者id获取问题作者信息
            QuestionDto questionDto = new QuestionDto();//question传输工具类
            BeanUtils.copyProperties(q,questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
            questionDto.setGmtNow(System.currentTimeMillis()-q.getGmtCreate());
            questionDto.setQuestionCreater(user);//之后，整合【问题】与【用户】信息
            questionDtoList.add(questionDto);//将整合好的信息添加到列表中去
        }

        paginationDto.setData(questionDtoList);//存入问题列表
        return paginationDto;
    }
    /**
     * 使用问题id来获取Question对象
     * @param id
     * @return
     */
    @Override
    public Question getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return question;//根据问题id查询问题信息
    }

    /**
     * 使用问题id来获取整合了用户与问题信息的QuestionDto对象
     * @param id
     * @return
     */
    @Override
    public QuestionDto getQuestionDtoById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);//根据问题id查询问题信息
        QuestionDto questionDto = null;
        if (question != null) {//防止持有该id的问题不存在，报空指针异常
            questionDto = new QuestionDto();
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            //整合用户与用户发布的问题的信息
            questionDto.setQuestionCreater(user);
            BeanUtils.copyProperties(question , questionDto);//这个方法的作用是快速的将【参数1】对象与【参数2】对象上的相同名字的属性值拷贝到【参数2】对象上
        }else{
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        return questionDto;
    }

    /**
     * 获取问题列表的数据总条数
     * @return
     */
    @Override
    public int QuestionListSize() {
        return (int)questionMapper.countByExample(new QuestionExample());
    }

    /**
     * 增加问题的阅读数
     * @param id
     * @return
     */
    @Override
    public synchronized Integer incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);//每次阅读数+1
        return questionExtMapper.incView(question);
    }

    @Override
    public List<Question> findQuestionByTag(Question question) {
        return questionExtMapper.queryQuestionByTag(question);
    }

}
