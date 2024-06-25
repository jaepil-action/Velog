package org.velog.api.domain.tag.controller.model;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class TagResponse {

    private String title;
    private Long blogId;
    private Integer count;
}
