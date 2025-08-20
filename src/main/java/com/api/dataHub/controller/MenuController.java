package com.api.dataHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메뉴")
@RestController
@RequiredArgsConstructor
public class MenuController {

    @Operation(
            summary = "메뉴 목록 조회",
            description = """
                    - 사용자 권한에 허용된 메뉴를 조회합니다.
                    
                    - Body
                    1. uuid : 사용자 키
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping(value = "/dataHub/menu/{uuid}")
    public ResponseEntity<?> getMenus(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok().body("");
    }
}
