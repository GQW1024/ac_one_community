package ac_one.gqw1024.community.ac_one_community.controller;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 首页Cntroller
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView meeting(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
