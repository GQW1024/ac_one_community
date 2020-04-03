package ac_one.gqw1024.community.ac_one_community.controller;

import org.junit.rules.ErrorCollector;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * 这个Controller主要处理通用异常处理不了的异常
 */
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})//覆盖BasicErrorController中的处理
public class CustomizeErroController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, ModelAndView modelAndView) {
        HttpStatus status = this.getStatus(request);
        if (status.is4xxClientError()) {//如果是客户端的问题
            modelAndView.addObject("message", "您要找的页面不存在呢，要不换个姿势?");
        } else if (status.is5xxServerError()) {//如果是服务端的问题
            modelAndView.addObject("message", "服务器要冒烟啦! 稍后再试试?");
        }
        modelAndView.setViewName(getErrorPath());//设置默认返回的错误页面
        return modelAndView;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
