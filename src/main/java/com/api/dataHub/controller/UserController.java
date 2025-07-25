package com.api.dataHub.controller;

import com.api.dataHub.controller.vo.request.RegisterUserDto;
import com.api.dataHub.controller.vo.response.ResponseHeadVo;
import com.api.dataHub.controller.vo.response.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) throws Exception {

        HttpStatus resMsg = HttpStatus.OK;

        ResponseVo responseVo = new ResponseVo();
        ResponseHeadVo responseHeadVo = new ResponseHeadVo();
        responseVo.setHead(responseHeadVo);

        return ResponseEntity.status(resMsg).body(responseVo);
    }
}
