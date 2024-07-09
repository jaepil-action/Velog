package org.velog.api.domain.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;
import org.velog.api.domain.user.model.UserDto;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Tag(name = "BlogApiController", description = "블로그 Api 서비스 컨트롤러")
public class BlogApiController {

    private final BlogBusiness blogBusiness;

    @Operation(summary = "블로그 생성 API", description = "로그인한 사용자 블로그 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<BlogResponse>> createBlog(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Valid @RequestBody BlogRegisterRequest blogRegisterRequest
    ){
        BlogResponse response = blogBusiness.register(blogRegisterRequest, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }


    @Operation(summary = "블로그 삭제 API", description = "로그인한 사용자 블로그 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Api<String>> deleteBlog(
            @Parameter(hidden = true) @Login UserDto userDto
    ){
        blogBusiness.delete(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("블로그 삭제 성공"));
    }
}
