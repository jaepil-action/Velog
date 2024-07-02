package org.velog.api.domain.series.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeriesRequest {
    @NotBlank
    private String title;
}
