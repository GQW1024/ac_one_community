package ac_one.gqw1024.community.ac_one_community.provider;

import ac_one.gqw1024.community.ac_one_community.dto.AccessTockenDTO;
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
        MediaType json = MediaType.get("application/json; charset=utf-8");//请求体类型，以及编码格式

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(json, JSON.toJSONString(accessTockenDTO));//创建请求体
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();//创建请求
            try (Response response = client.newCall(request).execute()) {//发出请求
                System.out.println(response.body().string());
                return response.body().string();    //请求成功，返回信息
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;   //请求失败，返回null
    }
}
