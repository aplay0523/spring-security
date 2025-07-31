package com.api.dataHub.controller.vo.request;

import com.api.dataHub.controller.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL) // null, length 0, isEmpty = true 제외
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private UUID uuid;
    private String userId;
    private String userPwd;
    private String userName;
    private String groupRole;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String useYn;

    public UserDetailDto(User user) {
        this.uuid = user.getUuid();
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.groupRole = user.getGroupRole();
        this.createDate = user.getCreateDate();
        this.updateDate = user.getUpdateDate();
        this.useYn = user.getUseYn();
    }
}
