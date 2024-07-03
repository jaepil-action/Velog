package org.velog.api.domain.blog.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostDto {

    private Long postId;

    private String title;

    private String excerpt;

    private LocalDateTime createAt;

    private String series;
}
