package org.velog.api.domain.post.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.velog.db.post.enums.PostStatus;

@Data
public class PostRequest {

    @NotBlank
    @Schema(description = "PUBLIC, PRIVATE, TEMPORARY 입력가능", example = "PUBLIC")
    private PostStatus postStatus;

    @NotBlank
    @Schema(description = "Post 제목", example = "Spring Boot Guide")
    private String title;

    @NotBlank
    @Schema(description = "Post 내용", example = "This is a guide to Spring Boot.")
    private String content;

    @Schema(description = "Post 요약", example = "Guide to Spring Boot")
    private String excerpt;

    @Schema(description = "태그 ID", example = "1")
    private String tagId;

    @Schema(description = "TEMPORARY 상태일때 입력 불가능", example = "2")
    private String seriesId;
}
