package org.velog.api.domain.series.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class SeriesRegisterRequest {
    @NotBlank
    private String title;
}
