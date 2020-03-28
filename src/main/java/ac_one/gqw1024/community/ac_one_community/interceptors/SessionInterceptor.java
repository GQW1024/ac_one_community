package ac_one.gqw1024.community.ac_one_community.interceptors;

import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component//将拦截器注入到IOC容器
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    /**
     * 在执行实际处理程序之前执行预处理
     * 设置拦截器，在所有请求执行之前先确定Session中有无user信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user") == null){//若Session中没有用户信息 则执行用户登录的一系列操作。
            Cookie[] cookies = request.getCookies();//前端的cookie集合
            User user = null;
            if (cookies!=null){//如果前端cookie不为空【防止空指针异常】
                for (Cookie c : cookies) {//则遍历cookie
                    if(c.getName().equals("token")){//当找到name为token的cookie时，证明以前有用户在该浏览器中登陆过
                        user = userMapper.findByToken(c.getValue());//通过token查找用户数据
                        request.getSession().setAttribute("user",user);
                        break;//退出循环
                    }
                }
            }
//            //如果执行了获取用户信息的一系列操作之后，还没有获取到用户信息，则直接重定向到首页。
//            if(user==null){
//                response.sendRedirect("/");
//            }
        }
        return true;//返回false代表不继续执行拦截器的后续处理
    }

    /**
     * 执行处理程序后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
