package com.api.dataHub.controller;

import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.JwtRequestVo;
import com.api.dataHub.controller.vo.request.RegisterUserDto;
import com.api.dataHub.controller.vo.response.JwtResponseVo;
import com.api.dataHub.controller.vo.response.ResponseBodyVo;
import com.api.dataHub.controller.vo.response.ResponseHeadVo;
import com.api.dataHub.controller.vo.response.ResponseIdVo;
import com.api.dataHub.controller.vo.response.ResponseVo;
import com.api.dataHub.security.provider.JwtTokenProvider;
import com.api.dataHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "엑세스 토큰 발급" /*description = "사용자 엑세스 토큰 발급"*/)
@RestController
@RequiredArgsConstructor
public class JwtAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /*
    * 사용자 엑세스 Jwt 토큰 발급
    */
    @Operation(
            summary = "2. 사용자 엑세스 토큰 발급",
            description = """
                    - 사용자 엑세스 토큰을 발급합니다.
                    
                    - Request Body 작성 방법
                    1. userId : 사용자 아이디를 입력해주세요.
                    2. userPwd : 사용자 비밀번호를 입력해주세요.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                        content = @Content(mediaType = "application/json"
                        )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping(value = "/public/get-token", produces = "application/json")
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

    @Operation(
            summary = "1. 회원가입",
            description = """
                    - 회원가입을 진행합니다.
                    
                    - Request Body 작성 방법
                    1. userId : 사용자 아이디를 입력해주세요.
                    2. userPwd : 사용자 비밀번호를 입력해주세요.
                    3. userName : 사용자 이름을 입력해주세요.
                    4. groupRole : 사용자 권한을 입력해주세요.(ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping(value = "/public/regist", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDto registerUserDto) throws Exception {

        HttpStatus resMsg = HttpStatus.OK;
        ResponseVo responseVo = new ResponseVo();
        ResponseHeadVo responseHeadVo = new ResponseHeadVo();
        User setUser = new User();

        setUser.setUserId(registerUserDto.getUserId());
        setUser.setUserPwd(passwordEncoder.encode(registerUserDto.getUserPwd()));
        setUser.setName(registerUserDto.getName());
        setUser.setGroupRole(registerUserDto.getGroupRole());
        userService.createUser(setUser);

        User user = userService.getUserById(setUser.getUserId()).get();

        responseHeadVo.setCode(resMsg.value());
        responseHeadVo.setMessage(resMsg.getReasonPhrase());
        responseHeadVo.setDetail("정상");
        responseVo.setHead(responseHeadVo);
        responseVo.setBody(new ResponseIdVo(user.getUuid()));

        return ResponseEntity.status(resMsg).body(responseVo);
    }
}
