package org.velog.api.domain.blog.controller.model;

import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

public class BlogResponse {
    private TagEntity tagEntity;
    private PostEntity postEntity;
    private SeriesEntity seriesEntity;
    private String introduction;
}
