package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.QuestionExtMapper;
import ac_one.gqw1024.community.ac_one_community.dto.CommentCreateDto;
import ac_one.gqw1024.community.ac_one_community.dto.CommentDto;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.dto.QuestionDto;
import ac_one.gqw1024.community.ac_one_community.enums.CommentTypeEnum;
import ac_one.gqw1024.community.ac_one_community.model.Question;
import ac_one.gqw1024.community.ac_one_community.service.CommentService;
import ac_one.gqw1024.community.ac_one_community.service.QuestionService;
import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public ModelAndView question(
            @PathVariable("id") Long id ,
            @RequestParam(value = "page",defaultValue = "1")int page,
            @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,
            HttpServletRequest request,
            ModelAndView modelAndView){

        QuestionDto questionDto = questionService.getQuestionDtoById(id);//使用问题id来获取整合了用户与问题信息的QuestionDto对象
        if(questionDto != null){//如果信息不为空，则将信息添加至model,返回到正常页面
            questionService.incView(id);
            questionDto.setViewCount(questionDto.getViewCount()+1);
            modelAndView.addObject("questionDto",questionDto);

            Question question = new Question();
            BeanUtils.copyProperties(questionDto,question);//将Question信息从questionDto中提取出来
            System.out.println(question.getQuestionTag().replace(",","|"));
            question.setQuestionTag(question.getQuestionTag().replace(",","|"));//重新组装tag,将','号改为'|',使它变为MySQL正则表达式
            List<Question> questions = questionService.findQuestionByTag(question);//获取相关问题列表
            modelAndView.addObject("likeQuestions",questions);
            //现在已经获取到了相关问题的列表，剩下前端显示

            //获取该问题的回复列表
            List<CommentDto> commentDtoList = commentService.listByQuestionIdAndType(id,page,pageSize, CommentTypeEnum.QUESTION);
            modelAndView.addObject("comments",commentDtoList);

            modelAndView.setViewName("question");
        }else{//否则直接返回到首页
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
