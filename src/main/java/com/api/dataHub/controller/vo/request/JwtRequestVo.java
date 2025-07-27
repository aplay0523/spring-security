package com.api.dataHub.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(
        requiredProperties = {"userId", "userPwd"}
)
public class JwtRequestVo {
    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String userPwd;
}
