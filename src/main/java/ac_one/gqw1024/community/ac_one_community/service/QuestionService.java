package ac_one.gqw1024.community.ac_one_community.service;

import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.model.Question;

/**
 * Question表的service接口
 */
public interface QuestionService {

    int createOrUpdate(Question question);
    /**
     * 获取当前页的问题列表，并存入【页面信息传输类】，然后返回
     * @param pageOffect
     * @param pageSize
     * @return
     */
    PaginationDto LimitQuestionDtolist(int pageOffect, int pageSize);

    int QuestionListSize();

    /**
     * 根据用户id获取用户发布的问题数据，并分页包装成PaginationDto对象后返回
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    PaginationDto UserQuestionlist(Long id, Integer page, Integer pageSize);

    /**
     * 使用问题id来获取整合了用户与问题信息的QuestionDto对象
     * @param id
     * @return
     */
    QuestionDto getQuestionDtoById(Long id);

    /**
     * 通过ID查找Question
     * @param id
     * @return
     */
    Question getById(Long id);

    /**
     * 增加阅读数量
     * @param id
     * @return
     */
    Integer incView(Long id);
}
