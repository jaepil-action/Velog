package org.velog.api.domain.role.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.role.Admin;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    @NotBlank
    private Long userRoleId;

    @NotBlank
    private String userEmail;

    @NotBlank
    private Admin admin;

    @NotBlank
    private LocalDateTime authorizationDate;
}
