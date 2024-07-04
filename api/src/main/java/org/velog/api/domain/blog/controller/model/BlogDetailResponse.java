package org.velog.api.domain.blog.controller.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

import java.util.List;

@Data
@Builder
public class BlogDetailResponse {

    private List<TagDto> tags;

    private List<PostDto> posts;

    private List<SeriesDto> series;

    private String introduction;

}
