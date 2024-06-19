package org.velog.api.domain.role.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velog.db.role.Admin;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    @Enumerated(value = EnumType.STRING)
    @NotBlank
    @Schema(description = "Role type", example = "ROLE_USER")
    private Admin admin;
}
