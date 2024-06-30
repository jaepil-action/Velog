package org.velog.api.domain.role.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.role.RoleEntity;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRegisterRequest {

    @NotBlank
    private Long userId;

    @NotBlank
    @Schema(description = "ID 1.ROLE_ID 2. ROLE_USER", example = "1")
    private Long roleId;

}
