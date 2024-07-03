package org.velog.api.domain.post.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsDetailPageResponse {

    private int totalPages;

    private long totalElements;

    private int offset;

    private int limit;

    private int likeCount;

    private List<PostDetailResponse> contents;
}
