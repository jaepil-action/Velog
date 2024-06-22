package org.velog.api.domain.blog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.velog.api.common.api.Api;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.blog.controller.model.BlogResponse;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Tag(name = "BlogApiController", description = "블로그 Api 서비스 컨트롤러")
public class BoardApiController {

    private final BlogBusiness blogBusiness;

/*    @Operation(summary = "블로그 생성 API", description = "블로그를 생성 할 사용자 Id 입력")
    public ResponseEntity<Api<BlogResponse>> createBlog(

    ){

    }*/
}
