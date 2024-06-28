package org.velog.api.domain.comment.controller.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class CommentResponse {

    private String commentWriter;
    private String contents;
    private Long parentCommentId;
    private LocalDateTime writeDateTime;
}
