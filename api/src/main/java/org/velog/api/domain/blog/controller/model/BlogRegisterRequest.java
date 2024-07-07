package org.velog.api.domain.blog.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogRegisterRequest {

    @Schema(description = "제목을 입력하지 않으면 '@LoinId'로 생성됩니다. / 255자 이하 입력")
    private String blogTitle;

    @NotBlank
    @Schema(description = "블로그 설명란, 필수값", example = "My Blog ~")
    private String introduction;
}

