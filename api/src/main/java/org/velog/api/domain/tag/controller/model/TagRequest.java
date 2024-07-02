package org.velog.api.domain.tag.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TagRequest {

    @NotBlank
    private String title;
}
