package ac_one.gqw1024.community.ac_one_community.interceptors;


import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
import ac_one.gqw1024.community.ac_one_community.model.User;
import ac_one.gqw1024.community.ac_one_community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component//将拦截器注入到IOC容器
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    /**
     * 在执行实际请求之前执行预处理
     * 设置拦截器，在所有请求执行之前先确定session中有无user信息，如果为空则直接通过cookie查询
     * 用户信息，之后直接存入session
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
            if (cookies!=null){//如果前端cookie不为空【防止空指针异常】
                for (Cookie c : cookies) {//则遍历cookie
                    if(c.getName().equals("token")){//当找到name为token的cookie时，证明以前有用户在该浏览器中登陆过
                        UserExample userExample = new UserExample();//创建用户工具类（该类为自动生成）
                        userExample.createCriteria()//创建查询条件
                                .andTokenEqualTo(c.getValue());//添加【等于目标token】条件，以此来查询用户
                        List<User> users = userMapper.selectByExample(userExample);//由于返回的是列表，所以我没接下来直接比对size就可以了
                        if(users.size() == 1) {//如果该用户存在
                            request.getSession().setAttribute("user",users.get(0));//存入session并返回
                        }
                        break;//退出循环
                    }
                }
            }
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
