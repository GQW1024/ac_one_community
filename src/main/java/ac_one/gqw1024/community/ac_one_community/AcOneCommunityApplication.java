package ac_one.gqw1024.community.ac_one_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
public class AcOneCommunityApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(AcOneCommunityApplication.class, args);
    }

    /**
     * 配置在Spring启动时，扫描static下的所有文件，不然在导入css等文件时，会导入失败
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //静态文件的虚拟映射            //虚拟路径                                       //物理路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        //上传的文件的虚拟映射                    //虚拟路径                                  //物理路径
//        registry.addResourceHandler("/uploadfile/**").addResourceLocations("file:D:/ACG_Package/");//使用方式，例:http://localhost:8080/uploadfile/Videoface/JoTaRo.jpeg
        System.out.println("路径映射完毕");
    }
}
