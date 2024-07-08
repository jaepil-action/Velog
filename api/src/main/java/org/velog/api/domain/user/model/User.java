package org.velog.api.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.blog.BlogEntity;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private Long blogId;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private String profileImageUrl;

    private LocalDateTime registrationDate;

    private LocalDateTime lastModifiedDate;
}
