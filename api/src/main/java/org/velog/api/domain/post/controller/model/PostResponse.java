package org.velog.api.domain.post.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {

    private String title;

    private String content;

    private String excerpt;

    private String blogTitle;

    private String seriesTitle;

    private String tagTitle;

}
