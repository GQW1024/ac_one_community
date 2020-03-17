package ac_one.gqw1024.community.ac_one_community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublishController {

    @RequestMapping("/publish")
    public String publish(){
        return "publish";
    }
}
