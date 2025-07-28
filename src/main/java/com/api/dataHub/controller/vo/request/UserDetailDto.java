package com.api.dataHub.controller.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;
import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class UserDetailDto {
    private UUID uuid;
    private String userId;
    private String userPwd;
    private String userName;
    private String groupRole;
}
