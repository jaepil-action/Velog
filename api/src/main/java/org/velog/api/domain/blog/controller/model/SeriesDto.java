package org.velog.api.domain.blog.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SeriesDto {

    private Long seriesId;

    private String title;

    private List<PostOfSeriesDto> posts;
}
