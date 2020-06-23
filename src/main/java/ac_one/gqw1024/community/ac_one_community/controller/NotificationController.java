package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dto.NotificationDto;
import ac_one.gqw1024.community.ac_one_community.dto.PaginationDto;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/6/7 20:51
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public ModelAndView notification(
            @PathVariable(value = "id",required = false)Long id,
            HttpServletRequest request,
            ModelAndView modelAndView){
        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            modelAndView.setViewName("index");
            return modelAndView;
        }
        //将通知设为已读，并跳转到对应问题的界面。
        NotificationDto notificationDto = notificationService.read(id,user);
        modelAndView.setViewName("redirect:/question/"+notificationDto.getOuterid());
        return modelAndView;
    }
}
