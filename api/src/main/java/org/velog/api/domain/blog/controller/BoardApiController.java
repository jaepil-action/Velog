package org.velog.api.domain.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.velog.api.domain.blog.business.BlogBusiness;
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
}
