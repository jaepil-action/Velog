package org.velog.api.domain.comment.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRegisterRequest {

    @NotBlank
    private String contents;
}
