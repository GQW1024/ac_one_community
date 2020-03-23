package ac_one.gqw1024.community.ac_one_community.service;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionMapper;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Question表的service接口
 */
public interface QuestionService {

    int createQuestion(Question question);

    Question findQuestionById(int id);

    PaginationDto LimitQuestionDtolist(int pageOffect, int pageSize);

    int QuestionListSize();


}
