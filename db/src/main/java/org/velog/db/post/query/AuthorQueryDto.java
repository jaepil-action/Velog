package org.velog.db.post.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.velog.db.user.ProfileImage;

@AllArgsConstructor
@Data
public class AuthorQueryDto {

    private String username;

    private ProfileImage profileImage;
}
