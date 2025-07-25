package com.api.dataHub.controller.vo.response;

import lombok.Data;

@Data
public class JwtResponseVo {
    private final String userId;
    private final String accessToken;
}
