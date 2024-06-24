package org.velog.api.domain.blog.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.velog.db.user.UserEntity;

@Data
public class BlogRegisterRequest {

    private String blogTitle;

    @NotBlank
    private String introduction;
}

