package org.velog.api.domain.tag.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRegisterRequest {

    @NotBlank
    private String title;
}
