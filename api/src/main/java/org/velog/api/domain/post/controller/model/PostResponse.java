package org.velog.api.domain.post.controller.model;

import lombok.Builder;
import lombok.Data;
import org.velog.db.post.enums.PostStatus;

@Data
@Builder
public class PostResponse {

    private PostStatus postStatus;

    private String title;

    private String content;

    private String excerpt;

    private String blogTitle;

    private String seriesTitle;

    private String tagTitle;

}
