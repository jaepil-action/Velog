package org.velog.api.domain.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.tag.business.TagBusiness;
import org.velog.api.domain.tag.controller.model.TagResponse;

import java.util.List;

@RestController
@RequestMapping("/open-api/tags")
@RequiredArgsConstructor
@Tag(name = "TagOpenApiController", description = "Tag OpenApi 서비스 컨트롤러")
public class TagOpenApiController {

    private final TagBusiness tagBusiness;

    @Operation(summary = "Tag 조회 API", description = "블로그의 Tag 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<List<TagResponse>>> retrieveAllTag(
            @PathVariable Long blogId
    ){
        List<TagResponse> response = tagBusiness.retrieveAllTag(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }
}
