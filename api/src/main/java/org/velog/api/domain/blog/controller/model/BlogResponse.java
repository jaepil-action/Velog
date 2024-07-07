package org.velog.api.domain.blog.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

import java.util.List;

@Getter
@Setter
@Builder
public class BlogResponse {

    private String blogTitle;
    private String introduction;
    private List<TagEntity> tagEntityList;
    private List<PostEntity> postEntityList;
    private List<SeriesEntity> seriesEntityList;
}
