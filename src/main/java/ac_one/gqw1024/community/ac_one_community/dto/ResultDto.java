package ac_one.gqw1024.community.ac_one_community.dto;

import ac_one.gqw1024.community.ac_one_community.exception.CustomizeErrorCode;
import ac_one.gqw1024.community.ac_one_community.exception.CustomizeException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDto<T> {

    private Integer code;
    private String message;
    private T data;

    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    /**
     * 错误之后返回
     * @param errorCode
     * @return
     */
    public static ResultDto errorOf(CustomizeErrorCode errorCode) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(errorCode.getCode());
        resultDto.setMessage(errorCode.getMessage());
        return resultDto;
    }

    /**
     * 在Customize错误拦截器中的返回Json的处理
     * @param e
     * @return
     */
    public static ResultDto errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    /**
     * 成功之后返回
     * @return
     */
    public static ResultDto successOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }
    /**
     * 成功之后返回
     * @return
     */
    public static <T> ResultDto successOf(T data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(data);
        return resultDto;
    }
}
