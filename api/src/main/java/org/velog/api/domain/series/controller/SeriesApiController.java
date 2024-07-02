package org.velog.api.domain.series.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.series.business.SeriesBusiness;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@Tag(name = "SeriesApiController", description = "시리즈 Api 서비스 컨트롤러")
public class SeriesApiController {

    private final SeriesBusiness seriesBusiness;

    @Operation(summary = "시리즈 생성 API", description = "사용자블로그의 시리즈 생성")
    @PostMapping("/create")
    public ResponseEntity<Api<SeriesResponse>> createSeries(
            HttpServletRequest request,
            @Valid @RequestBody SeriesRequest seriesRequest
    ){
        SeriesResponse response = seriesBusiness.register(seriesRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "시리즈 조회 API", description = "블로그의 시리즈 전체 조회")
    @GetMapping("/{blogId}")
    public ResponseEntity<Api<List<SeriesResponse>>> retrieveAllTag(
            @PathVariable Long blogId
    ){
        List<SeriesResponse> response = seriesBusiness.retrieveAllTag(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }

    @Operation(summary = "시리즈 수정 API", description = "사용자 블로그의 시리즈 수정")
    @PutMapping("/{seriesId}")
    public ResponseEntity<Api<String>> editTag(
            HttpServletRequest request,
            @PathVariable Long seriesId,
            @Valid @RequestBody SeriesRequest seriesRequest
    ){
        seriesBusiness.edit(request, seriesId, seriesRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("시리즈 수정 성공"));
    }

    @Operation(summary = "시리즈 삭제 API", description = "사용자 블로그의 시리즈 수정")
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<Api<String>> deleteTag(
            HttpServletRequest request,
            @PathVariable Long seriesId
    ){
        seriesBusiness.delete(request, seriesId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("시리즈 삭제 성공"));
    }
}
