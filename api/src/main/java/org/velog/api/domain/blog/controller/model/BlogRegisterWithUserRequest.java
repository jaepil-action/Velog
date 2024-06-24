package org.velog.api.domain.blog.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.velog.db.user.UserEntity;

@Data
public class BlogRegisterWithUserRequest {

    @NotBlank
    private UserEntity userEntity;

    private String blogTitle;

    @NotBlank
    private String introduction;
}
