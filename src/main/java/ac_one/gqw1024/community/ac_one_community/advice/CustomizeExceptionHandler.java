package ac_one.gqw1024.community.ac_one_community.advice;

import ac_one.gqw1024.community.ac_one_community.dto.ResultDto;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 定制异常处理
 * 使发生的异常能够进行统一的处理
 */
//设置需要处理请求错误的包名
@ControllerAdvice(basePackages = {"ac_one.gqw1024.community.ac_one_community.controller"})
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        String contentType = request.getContentType();
        //处理页面的json错误，包括单独指定contentType 以及 指定了contentType和charset两种可能的json请求错误
        if("application/json".equals(contentType)||"application/json; charset=UTF-8".equals(contentType)){
            ResultDto resultDto = null;
            if(e instanceof CustomizeException){//如果是自己处理的JSON错误
                resultDto = ResultDto.errorOf((CustomizeException) e);//设置返回这个
            }else{
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);//如果不是自己处理的JSON错误，则返回这个
            }

            try {
                response.setContentType(contentType);
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");//设置响应字符集
                PrintWriter writer = response.getWriter();
                //在响应中写入JSON格式的错误信息，为的是传给前端的ajax异步请求.
                writer.write(JSON.toJSONString(resultDto));//使用write方法后，此次响应就会直接输出写入的数据
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        else{
            if(e instanceof CustomizeException){//如果这个错误已被自定义处理过
                modelAndView.addObject("message",e.getMessage());
            }else{
                modelAndView.addObject("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
        }
        return modelAndView;
    }

}
