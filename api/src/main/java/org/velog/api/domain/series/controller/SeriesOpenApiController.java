package org.velog.api.domain.series.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesResponse;

import java.util.List;

@RestController
@RequestMapping("/open-api/series")
@RequiredArgsConstructor
@Tag(name = "SeriesOpenApiController", description = "시리즈 OpenApi 서비스 컨트롤러")
public class SeriesOpenApiController {

    private final SeriesBusiness seriesBusiness;

    @Operation(summary = "시리즈 조회 API", description = "블로그의 시리즈 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<List<SeriesResponse>>> retrieveAllTag(
            @PathVariable Long blogId
    ){
        List<SeriesResponse> response = seriesBusiness.retrieveAllTag(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }
}
