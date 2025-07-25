package com.api.dataHub.controller.vo.request;

import lombok.Data;

@Data
public class JwtRequestVo {
    private String userId;
    private String userPwd;
}
