package org.velog.db.post.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostQueryDto {
    private Long postId;
    private String title;
    private String excerpt;
    private AuthorQueryDto author;
    private Long likeCount;

    public PostQueryDto(Long postId, String title, String excerpt, Long likeCount) {
        this.postId = postId;
        this.title = title;
        this.excerpt = excerpt;
        this.likeCount = likeCount;
    }
}
