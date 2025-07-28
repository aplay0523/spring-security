package com.api.dataHub.controller;

import com.api.dataHub.common.ErrorCode;
import com.api.dataHub.common.exception.BusinessException;
import com.api.dataHub.controller.entity.User;
import com.api.dataHub.controller.vo.request.RegisterUserDto;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import com.api.dataHub.controller.vo.response.ResponseHeadVo;
import com.api.dataHub.controller.vo.response.ResponseSimpleVo;
import com.api.dataHub.controller.vo.response.ResponseVo;
import com.api.dataHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2-회원가입")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        User user = new User();
        user.setUserId(registerUserDto.getUserId());
        user.setUserPwd(passwordEncoder.encode(registerUserDto.getUserPwd()));
        user.setUserName(registerUserDto.getUserName());
        user.setGroupRole(registerUserDto.getGroupRole());
        userService.createUser(user);

        return ResponseEntity.ok().body(
                new ResponseSimpleVo(
                        HttpStatus.OK.value(), "성공"
                )
        );
    }

    @Operation(
            summary = "사용자 업데이트",
            description = """
                    - 업데이트
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(value = "/dataHub/updateUser", produces = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody UserDetailDto userDetailDto) {

        if(userDetailDto.getUserId() != null) {
            userService.updateUser(userDetailDto);
        } else if(userDetailDto.getUuid() != null) {
            userService.updateByUserUuid(userDetailDto);
        }

        return ResponseEntity.ok().body(
                new ResponseSimpleVo(
                        HttpStatus.OK.value(), "성공"
                )
        );
    }
}
