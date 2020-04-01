//package ac_one.gqw1024.community.ac_one_community.provider;
//
//import ac_one.gqw1024.community.ac_one_community.dao.UserMapper;
//import ac_one.gqw1024.community.ac_one_community.model.User;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//public class CookieUserProvider {
//
//    /**
//     * 通过cookie判断用户是否登录过，防止用户因服务器重启而需重新登录，如果用户有登录过，则直接将用户信息传入session
//     * 【使用synchronized关键子为方法加锁，防止同时调用请求】
//     * @param request
//     * @param userMapper
//     */
//    public synchronized void getuserByCookie(HttpServletRequest request, UserMapper userMapper){
//        Cookie[] cookies = request.getCookies();//前端的cookie集合
//        if (cookies!=null){//如果前端cookie不为空【防止空指针异常】
//            for (Cookie c : cookies) {//则遍历cookie
//                if(c.getName().equals("token")){//当找到name为token的cookie时，证明以前有用户在该浏览器中登陆过
//                    User user = userMapper.findByToken(c.getValue());//通过token查找用户数据
//                    request.getSession().setAttribute("user",user);
//                    //modelAndView.addObject("user", user);//返回到前端
//                    break;//退出循环
//                }
//            }
//        }
//    }
//
//}
