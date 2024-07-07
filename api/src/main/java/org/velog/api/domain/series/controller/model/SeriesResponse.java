package org.velog.api.domain.series.controller.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SeriesResponse {

    private String title;
    private Long blogId;

}
