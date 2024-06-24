package org.velog.api.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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

    private Long id;

    private BlogEntity blogEntity;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private LocalDateTime registrationDate;
}
