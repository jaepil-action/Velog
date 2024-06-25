package org.velog.api.domain.post.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String excerpt;

    private String tagId;

    private String seriesId;
}
