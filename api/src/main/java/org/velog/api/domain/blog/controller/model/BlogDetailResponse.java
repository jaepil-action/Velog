package org.velog.api.domain.blog.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlogDetailResponse {

    private List<TagDto> tags;

    private List<PostDto> posts;

    private List<SeriesDto> series;

    private String introduction;

}
