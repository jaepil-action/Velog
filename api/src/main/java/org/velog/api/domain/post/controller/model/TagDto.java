package org.velog.api.domain.post.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagDto {
    @NotNull
    private Long tagId;
}
