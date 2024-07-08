package org.velog.api.domain.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.blog.controller.model.BlogDetailResponse;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;

@RestController
@RequestMapping("/open-api/blogs")
@RequiredArgsConstructor
@Tag(name = "BlogOpenApiController", description = "블로그 OpenApi 서비스 컨트롤러")
public class BlogOpenApiController {

    private final BlogBusiness blogBusiness;

    @Operation(summary = "특정 블로그 조회 API", description = "LoginId 블로그 조회")
    @GetMapping("/{loginId}")
    public ResponseEntity<Api<BlogDetailResponse>> retrieveBlogByLoginId(
            @PathVariable String loginId
    ){
        BlogDetailResponse response = blogBusiness.retrieveBlogByLoginId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }
}
