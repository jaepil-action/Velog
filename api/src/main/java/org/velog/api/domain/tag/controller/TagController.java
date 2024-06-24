package org.velog.api.domain.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.tag.business.TagBusiness;
import org.velog.api.domain.tag.controller.model.TagRegisterRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "TagApiController", description = "Tag Api 서비스 컨트롤러")
public class TagController {

    private final TagBusiness tagBusiness;

    @Operation(summary = "Tag 생성 API", description = "사용자 블로그의 Tag 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<TagResponse>> createTag(
            HttpServletRequest request,
            @Valid @RequestBody TagRegisterRequest tagRegisterRequest
    ){
        TagResponse response = tagBusiness.register(request, tagRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }
}
