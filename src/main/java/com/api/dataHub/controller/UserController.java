package com.api.dataHub.controller;

import com.api.dataHub.common.ErrorCode;
import com.api.dataHub.common.exception.BusinessException;
import com.api.dataHub.controller.vo.request.UserDetailDto;
import com.api.dataHub.controller.vo.response.ResponseBodyVo;
import com.api.dataHub.controller.vo.response.ResponseListVo;
import com.api.dataHub.controller.vo.response.ResponseSimpleVo;
import com.api.dataHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자", description = "API 키(15968aad-ff43-484f-b5b5-b741fda1f521), 토큰 필요")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "사용자 업데이트",
            description = """
                    - 사용자 정보를 업데이트합니다.
                    
                    - Body
                    - 변경할 객체만 작성해주세요.(uuid 또는 userId는 필수입니다.)
                    1. uuid : 사용자 키
                    2. userId : 사용자 아이디
                    3. userPwd : 사용자 비밀번호
                    4. userName : 사용자 명
                    5. groupRole : 권한 (ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    
                    - API 키, JWT 토큰이 필요합니다.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping(value = "/dataHub/user", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @Operation(
            summary = "사용자 조회",
            description = """
                    - 사용자를 조회합니다.
                    
                    - Body
                    1. uuid : 사용자 키
                    
                    - API 키, JWT 토큰이 필요합니다.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/dataHub/user/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable("uuid") UUID uuid) {

        return ResponseEntity.ok().body(
                new ResponseBodyVo(userService.getDetailUserByUuid(uuid),
                        HttpStatus.OK.value(), "성공"
                )
        );
    }

    @Operation(
            summary = "사용자 목록 조회",
            description = """
                    - 사용자를 목록 조회합니다.
                    
                    - Body
                    1. groupRole : 권한 (ROLE_ADMIN, ROLE_MANAGER, ROLE_USER)
                    2. useYn : 계정 잠금 유무 (Y, N)
                    
                    - API 키, JWT 토큰이 필요합니다.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/dataHub/users")
    public ResponseEntity<?> getUserByUuid(@RequestParam("groupRole") String groupRole, @RequestParam("useYn") String useYn) {

        if(groupRole.equals("ROLE_ADMIN") || groupRole.equals("ROLE_MANAGER") || groupRole.equals("ROLE_USER")) {
            return ResponseEntity.ok().body(
                    new ResponseBodyVo(new ResponseListVo<>(userService.getDetailUserList(groupRole, useYn)),
                            HttpStatus.OK.value(), "성공"
                    )
            );
        } else {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
    }

    @Operation(
            summary = "사용자 삭제",
            description = """
                    - 사용자 삭제 진행합니다.
                    
                    - Body
                    1. uuid : 사용자 키
                    
                    - API 키, JWT 토큰이 필요합니다.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @DeleteMapping(value = "/dataHub/user/{uuid}")
    public ResponseEntity<?> delUserByUuid(@PathVariable("uuid") UUID uuid) {

        userService.deleteByUserUuid(uuid);

        return ResponseEntity.ok().body(
                new ResponseSimpleVo(
                        HttpStatus.OK.value(), "성공"
                )
        );
    }
}
