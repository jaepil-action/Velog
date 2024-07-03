package org.velog.api.domain.post.controller.model;

import lombok.Builder;
import lombok.Data;
import org.velog.api.domain.post.controller.model.AuthorDto;
import org.velog.db.post.enums.PostStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PostAdminResponse {

    private Long postId;

    private String title;

    private AuthorDto author;

    private PostStatus postStatus;

    private LocalDateTime createdAt;
}
