package org.velog.db.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.post.enums.PostStatus;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostSearchDto {

    private String loginId;
    private String title;

    private PostStatus postStatus;
}
