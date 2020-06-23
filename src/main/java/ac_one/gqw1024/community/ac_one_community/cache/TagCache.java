package ac_one.gqw1024.community.ac_one_community.cache;

import ac_one.gqw1024.community.ac_one_community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/16 16:49
 */
public class TagCache {
    public static List<TagDto> get(){
        List<TagDto> tagDtos = new ArrayList<>();

        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","java","node","python","js","php","css","html","java","node","python","js","php","css","html","java","node","python","js","php","css","html","java","node","python"));

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring","MyBatis","SpringMVC","SSM","SpringBoot","BootStrap"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","nginx","docker","apache","centos","tomcat","unix","windows-server"));

        tagDtos.add(program);
        tagDtos.add(framework);
        tagDtos.add(server);
        return tagDtos;
    }

    /**
     *判断前端传回的tags是否合规
     * @param tags
     * @return
     */
    public static boolean isValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        //使用流将每一个tagDto中的tags合并为一个List之后判断前端传回的列表是否包含在其中
        return tagDtos.stream().map(tag -> tag.getTags().stream()).collect(Collectors.toList()).contains(split);
    }
}
