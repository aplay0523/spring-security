package com.api.dataHub.controller.vo;

import com.api.dataHub.controller.entity.User;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserSessionVo {

    private UUID uuid;
    private String userId;
    private String userName;
    private String groupRole;
    private String useYn;

    public UserSessionVo(User user) {
        this.uuid = user.getUuid();
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.groupRole = user.getGroupRole();
        this.useYn = user.getUseYn();
    }
}
