package org.velog.api.domain.post.controller.model;

import lombok.Builder;
import lombok.Data;
import org.velog.db.post.enums.PostStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {

    private Long postId;

    private String title;

    private LocalDateTime createdAt;

    private PostStatus postStatus;
}
