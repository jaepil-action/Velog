package org.velog.api.domain.post.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.velog.db.user.ProfileImage;

@AllArgsConstructor
@Data
public class AuthorDto {

    private String username;

    private ProfileImage profileImage;
}
