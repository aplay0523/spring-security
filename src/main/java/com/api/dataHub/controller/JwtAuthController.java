package com.api.dataHub.controller;

import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.JwtRequestVo;
import com.api.dataHub.controller.vo.response.JwtResponseVo;
import com.api.dataHub.controller.vo.response.ResponseHeadVo;
import com.api.dataHub.controller.vo.response.ResponseVo;
import com.api.dataHub.security.provider.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "엑세스 토큰 발급", description = "사용자 엑세스 토큰 발급")
@RestController
@RequiredArgsConstructor
public class JwtAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /*
    * 사용자 엑세스 Jwt 토큰 발급
    */
    @Operation(
            summary = "사용자 엑세스 토큰 발급",
            description = """
                    - 사용자 엑세스 토큰을 발급합니다.
                    
                    - Request Body 작성 방법
                    1. userId : 사용자 아이디를 입력해주세요.
                    2. userPwd : 사용자 비밀번호를 입력해주세요. 
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OAuth2AccessTokenResponse.class)
                        )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping(value = "/user/get-token", produces = "application/json")
    public ResponseEntity<?> userAccessToken(@RequestBody JwtRequestVo jwtRequestVo) throws Exception {

        HttpStatus resMsg = HttpStatus.OK;

        int resCode = resMsg.value();

        ResponseVo responseVo = new ResponseVo();
        ResponseHeadVo responseHeadVo = new ResponseHeadVo();

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        jwtRequestVo.getUserId(),
                        jwtRequestVo.getUserPwd()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtTokenProvider.createToken(user);
            responseHeadVo.setCode(resCode);
            responseHeadVo.setMessage(resMsg.getReasonPhrase());
            responseHeadVo.setDetail("정상");

            responseVo.setHead(responseHeadVo);
            responseVo.setBody(new JwtResponseVo(jwtRequestVo.getUserId(), token));

            return ResponseEntity.status(resMsg).body(responseVo);

        } catch (BadCredentialsException e) {
            resMsg = HttpStatus.BAD_REQUEST;
            responseHeadVo.setCode(resMsg.value());
            responseHeadVo.setMessage(resMsg.getReasonPhrase());
            responseHeadVo.setDetail("회원정보가 올바르지 않음");
            responseVo.setHead(responseHeadVo);
            responseVo.setBody(null);

            return ResponseEntity.status(resMsg).body(responseVo);
        }

    }
}
