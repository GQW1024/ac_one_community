package ac_one.gqw1024.community.ac_one_community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author GQW1024
 * @version 1.0
 * @date 2020/5/16 16:46
 */
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;

}
