package ac_one.gqw1024.community.ac_one_community.dto;

import lombok.Data;

/**
 * 此类是，从Github上获取用户的accesstoken所必须的参数集合
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
