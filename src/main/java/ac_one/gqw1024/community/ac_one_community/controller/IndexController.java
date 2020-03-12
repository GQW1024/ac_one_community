package ac_one.gqw1024.community.ac_one_community.controller;

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
    public ModelAndView meeting(@RequestParam(name = "name",required = false,defaultValue = "GQW") String name, ModelAndView modelAndView){
        modelAndView.setViewName("index");
        //modelAndView.addObject("name",name);
        return modelAndView;
    }
}
