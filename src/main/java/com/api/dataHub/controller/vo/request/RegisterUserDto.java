package com.api.dataHub.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Schema(
        description = "사용자 DTO",
        requiredProperties = {"userId", "userPwd", "groupRole"}
)
public class RegisterUserDto {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;
    @NotBlank(message = "패스워드는 필수입니다.")
    private String userPwd;
    private String name;
    @NotBlank(message = "권한은 필수입니다.")
    private String groupRole;

}
