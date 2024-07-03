package org.velog.api.domain.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Tag(name = "BlogApiController", description = "블로그 Api 서비스 컨트롤러")
public class BoardApiController {

    private final BlogBusiness blogBusiness;

    @Operation(summary = "블로그 생성 API", description = "로그인한 사용자 블로그 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<BlogResponse>> createBlog(
            HttpServletRequest request,
            @Valid @RequestBody BlogRegisterRequest blogRegisterRequest
    ){
        BlogResponse response = blogBusiness.register(blogRegisterRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "특정 블로그 조회 API", description = "LoginId 블로그 조회")
    @PostMapping("/{loginId}")
    public ResponseEntity<Api<BlogDetailResponse>> retrieveBlogByLoginId(
            @PathVariable String loginId
    ){
        BlogDetailResponse response = blogBusiness.retrieveBlogByLoginId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }


    @Operation(summary = "블로그 삭제 API", description = "로그인한 사용자 블로그 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Api<String>> deleteBlog(
            HttpServletRequest request
    ){
        blogBusiness.delete(request);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("블로그 삭제 성공"));
    }
}
