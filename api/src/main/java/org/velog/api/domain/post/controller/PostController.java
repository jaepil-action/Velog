package org.velog.api.domain.post.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.PostRegisterRequest;
import org.velog.api.domain.post.controller.model.PostResponse;
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "PostApiController", description = "Post Api 서비스 컨트롤러")
public class PostController {

    private final PostBusiness postBusiness;

    @Operation(summary = "Post 생성 API", description = "사용자블로그의 Post 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<PostResponse>> createSeries(
            HttpServletRequest request,
            @Valid @RequestBody PostRegisterRequest postRegisterRequest
    ){
        PostResponse response = postBusiness.register(request, postRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }
}
