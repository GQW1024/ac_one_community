package ac_one.gqw1024.community.ac_one_community.provider;

import ac_one.gqw1024.community.ac_one_community.dto.AccessTockenDTO;
import ac_one.gqw1024.community.ac_one_community.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component //将这个类扫描注入到IOC容器，以便于后续使用
public class GithubProvider {
    /**
     *
     * @param accessTockenDTO 请求access_token所需要的参数，主要有code等...
     * @return
     * @throws IOException
     */
    public String getAccessToken(AccessTockenDTO accessTockenDTO) throws IOException{
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");//请求体类型，以及编码格式

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTockenDTO));//创建请求体
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();//创建请求
            try (Response response = client.newCall(request).execute()) {//发出请求
                //response.body().string()的到的结果为access_token=...&scope=user&token_type=bearer
                String access_tocken = response.body().string().split("&")[0].split("=")[1];  //通&字符截取字符串成数组{"access_token=...","scope=user","token_type=bearer"}
                //String[] access_tocken = bodyarry[0].split("=");//由于bodyarry的第一个参数是我们所需要的access_tocken,所以用等号截取她
                System.out.println(access_tocken);
                return access_tocken;    //请求成功，截取完毕，返回access_tocken
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;   //如果请求失败，返回null
    }

    /**
     *  用于获取github用户的个人信息，且只能使用get方式请求
     * @param access_token  //【必要参数】获取用户信息的令牌（KEY）
     * @return
     */
    public GithubUser getUser(String access_token){
        //get请求发送步骤
        OkHttpClient client = new OkHttpClient();// 1 创建 OkHttpClient
        Request request = new Request.Builder() // 2 创建请求[Request]
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();// 3 利用OkHttpClient来将请求提交发送出去
            //由于接收过来的已经是JSON格式的数据了，所以这里直接用parseObject()方法直接转为GithubUser类型返回。
            return JSON.parseObject(response.body().string(),GithubUser.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;   //如果请求失败，返回null
    }
}
