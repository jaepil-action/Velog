package org.velog.api.domain.user.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.user.ProfileImage;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String loginId;

    private String name;

    private String email;

    private ProfileImage profileImage;

    private LocalDateTime registrationDate;
}
