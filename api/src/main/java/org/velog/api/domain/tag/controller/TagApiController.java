package org.velog.api.domain.tag.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.tag.business.TagBusiness;
import org.velog.api.domain.tag.controller.model.TagRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(name = "TagApiController", description = "Tag Api 서비스 컨트롤러")
public class TagApiController {

    private final TagBusiness tagBusiness;

    @Operation(summary = "Tag 생성 API", description = "사용자 블로그의 Tag 생성")
    @PostMapping("")
    public ResponseEntity<Api<TagResponse>> createTag(
            HttpServletRequest request,
            @Valid @RequestBody TagRequest tagRequest
    ){
        TagResponse response = tagBusiness.register(request, tagRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "Tag 조회 API", description = "블로그의 Tag 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<List<TagResponse>>> retrieveAllTag(
            @PathVariable Long blogId
    ){
        List<TagResponse> response = tagBusiness.retrieveAllTag(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }

    @Operation(summary = "Tag 수정 API", description = "사용자 블로그의 Tag 수정")
    @PutMapping("/{tagId}")
    public ResponseEntity<Api<String>> editTag(
            HttpServletRequest request,
            @PathVariable Long tagId,
            @Valid @RequestBody TagRequest tagRequest
    ){
        tagBusiness.edit(request, tagId, tagRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("태그 수정 성공"));
    }

    @Operation(summary = "Tag 삭제 API", description = "사용자 블로그의 Tag 수정")
    @DeleteMapping("/{tagId}")
    public ResponseEntity<Api<String>> deleteTag(
            HttpServletRequest request,
            @PathVariable Long tagId
    ){
        tagBusiness.delete(request, tagId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("태그 삭제 성공"));
    }
}
