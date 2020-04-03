package ac_one.gqw1024.community.ac_one_community.advice;

import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 定制异常处理
 * 使发生的异常能够进行统一的处理
 */
//设置需要处理请求错误的包名
@ControllerAdvice(basePackages = {"ac_one.gqw1024.community.ac_one_community.controller"})
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e) {
        //HttpStatus status = getStatus(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        if(e instanceof CustomizeException){//如果这个错误已被自定义处理过
            modelAndView.addObject("message",e.getMessage());
        }else{
            modelAndView.addObject("message","服务器要冒烟啦! 稍后再试试?");
        }
        return modelAndView;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
