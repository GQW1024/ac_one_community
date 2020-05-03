package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.CommentCreateDto;
import ac_one.gqw1024.community.ac_one_community.dto.CommentDto;
import ac_one.gqw1024.community.ac_one_community.dto.ResultDto;
import ac_one.gqw1024.community.ac_one_community.enums.CommentTypeEnum;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.model.Comment;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

     @Autowired
     CommentService commentService;

     /**
     * 由于调用这个Mapping的是ajax的请求，所以需要使用@ResponseBody返回Json格式
     * @RequestBody 修饰后，将请求过来的JSON通过其中的key与被修饰的类中的属性名一一对应，转换为Java中的对象
     * @ResponseBody 修饰后，将响应返回的值转换为JSON格式返回
      * @return
     */
     @ResponseBody
    @PostMapping("/comment")
    public Object commentPost(@RequestBody CommentCreateDto commentCreateDto,
                                 HttpServletRequest request){
         User user = (User) request.getSession().getAttribute("user");
         if(user == null){
             return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
         }

         Comment comment = new Comment();
         comment.setParentId(commentCreateDto.getParentId());
         comment.setCommentator(user.getId());
         //System.out.println("ssssssssssssssssss"+commentDto.getContent());
         comment.setContent(commentCreateDto.getContent());
         comment.setType(commentCreateDto.getType());
         comment.setGmtCreate(System.currentTimeMillis());
         comment.setGmtModified(comment.getGmtCreate());
         commentService.insert(comment, commentCreateDto.getQuestionId());//存入回复数据，并为改问题回复数加1
         return ResultDto.successOf();
    }

    //请求获取某个父类的回复列表
    @ResponseBody
    @GetMapping("/commentListById")
    public List<CommentDto> commentListById(@RequestParam("parentId") Long parentId,
                                                    @RequestParam("type") Integer type,
                                                  @RequestParam(value = "page",defaultValue = "1")int page,
                                                  @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,
                                                  HttpServletRequest request){
        List<CommentDto> commentDtoList = null;
         //在这里不判断用户是否为空，是因为这个方法是【在用户提交回复成功时】用来异步刷新回复列表的，所以在调用这个GetMapping时，用户一定不是空的。
        if(CommentTypeEnum.QUESTION.getType() == type){
            commentDtoList = commentService.listByQuestionIdAndType(parentId, page, pageSize, CommentTypeEnum.QUESTION);
        }else {
            commentDtoList = commentService.listByQuestionIdAndType(parentId, page, pageSize, CommentTypeEnum.COMMENT);
        }

         return commentDtoList;
    }

//    //请求某个回复的二级回复列表
//    @ResponseBody
//    @GetMapping(value = "/comment/{parentId}")
//    public ResultDto<List<CommentDto>> SecondCommentList(@PathVariable Long parentId,//父类回复的ID
//                                       @RequestParam(value = "page",defaultValue = "1")int page,
//                                       @RequestParam(value = "pageSize",defaultValue = "5")int pageSize
//                                        ){
//        List<CommentDto> SecondCommentDtoList = commentService.listByQuestionIdAndType(parentId, page, pageSize, CommentTypeEnum.COMMENT);
//         return ResultDto.successOf(SecondCommentDtoList);
//    }
}
