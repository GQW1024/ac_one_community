package ac_one.gqw1024.community.ac_one_community.configuation;


import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SpringConfigution implements WebMvcConfigurer {
    /**
     * 使static中的静态对象可以被解析
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        //上传的文件的虚拟映射                    //虚拟路径                                  //物理路径
//        registry.addResourceHandler("/uploadfile/**").addResourceLocations("file:D:/ACG_Package/");//使用方式，例:http://localhost:8080/uploadfile/Videoface/JoTaRo.jpeg
        System.out.println("路径映射完毕");
    }
}
