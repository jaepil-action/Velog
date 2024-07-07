package org.velog.api.domain.role.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRegisterRequest {

    @NotNull
    private Long userId;

    @NotNull
    @Schema(description = "ID 1.ROLE_ID 2. ROLE_USER", example = "1")
    private Long roleId;

}
