package org.velog.api.domain.blog.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostOfSeriesDto {

    private Long postId;

    private String title;
}
