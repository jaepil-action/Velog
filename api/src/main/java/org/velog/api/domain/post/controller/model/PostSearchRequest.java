package org.velog.api.domain.post.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostSearchRequest {

    @Schema(description = "작성자ID", example = "user")
    private String loginId;

    @Schema(description = "Post 제목", example = "Spring Boot Guide")
    private String title;
}
