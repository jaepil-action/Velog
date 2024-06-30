package org.velog.api.domain.comment.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRegisterRequest {

    @NotBlank
    @Schema(description = "입력할 댓글 내용", example = "Hi~")
    private String contents;
}
