package com.api.dataHub.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RegisterUserDto {


    private String userId;
    private String userPwd;
    private String userName;

    @Schema(description = "권한", example = "ADMIN")
    private String groupRole;
}
